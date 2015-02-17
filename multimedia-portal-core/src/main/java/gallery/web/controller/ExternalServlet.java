package gallery.web.controller;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: slayer
 * Date: 19.12.12
 */
public class ExternalServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ExternalServlet.class);
    private static final String PLAYFRAMEWORK_URL = "http://127.0.0.1:9000";

    private final HttpClient httpClient = new DefaultHttpClient();
    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpUriRequest uriRequest = createRequest(request);
            copyHeaders(uriRequest, request);
            AsyncContext asyncContext = request.startAsync(request, response);
            executor.submit(new MyConsumer(asyncContext, uriRequest));
        } catch (Exception e) {
            logger.error("e", e);
        }
    }

    private static void copyHeaders(HttpUriRequest uriRequest, HttpServletRequest request) {
//        Enumeration<String> names = request.getHeaderNames();
//        while (names.hasMoreElements()) {
//            String name = names.nextElement();
//            if (!name.equals("Content-Length") && !name.equals("Content-Type")) {
//                uriRequest.addHeader(name, request.getHeader(name));
//            }
//            logger.info("Adding header [{}] = [{}]", name, request.getHeader(name));
//        }
    }

    private static HttpUriRequest createRequest(HttpServletRequest request)
            throws MethodNotSupportedException, IOException {
        String method = request.getMethod();
        String url = createUrl(request);
        if (HttpGet.METHOD_NAME.equals(method)) {
            return new HttpGet(url);
        } else if (HttpPost.METHOD_NAME.equals(method)) {
            ServletInputStream inputStream = request.getInputStream();
            int contentLength = request.getContentLength();
            ContentType contentType = ContentType.create(request.getContentType());

            HttpPost httpPost = new HttpPost(url);
            InputStreamEntity entity = new InputStreamEntity(inputStream, contentLength, contentType);
            httpPost.setEntity(entity);

            return httpPost;
        } else {
            throw new MethodNotSupportedException("Only post and get methods are supported.");
        }
    }

    private static String createUrl(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder(PLAYFRAMEWORK_URL);
        sb.append(request.getServletPath());

        String pathInfo = request.getPathInfo();
        if (pathInfo != null) {
            sb.append(pathInfo);
        }

        String queryString = request.getQueryString();
        if (queryString != null) {
            sb.append("?").append(queryString);
        }
        return sb.toString();
    }

    class MyConsumer implements Runnable {
        final AsyncContext asyncContext;
        final HttpUriRequest uriRequest;

        MyConsumer(AsyncContext asyncContext, HttpUriRequest uriRequest) {
            this.asyncContext = asyncContext;
            this.uriRequest = uriRequest;
        }

        @Override
        public void run() {
            try {
                logger.debug("Including uriRequest [{}]", uriRequest.getURI().toString());
                HttpResponse httpResponse = httpClient.execute(uriRequest);
                ServletResponse response = asyncContext.getResponse();
//                ServletOutputStream outputStream = response.getOutputStream();
                PrintWriter writer = response.getWriter();
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream inputStream = httpEntity.getContent();
                copyHeaders(httpEntity, response);
//                IOUtils.copyLarge(inputStream, outputStream);
                IOUtils.copy(inputStream, writer, Charset.forName("UTF-8"));
//                outputStream.flush();
//                outputStream.close();
                writer.flush();
                writer.close();
                inputStream.close();
                asyncContext.complete();
            } catch (Exception e) {
                logger.error("e", e);
            }
        }
    }

    private static void copyHeaders(HttpEntity httpEntity, ServletResponse response) {
        Header encoding = httpEntity.getContentEncoding();
        if (encoding != null) {
            response.setCharacterEncoding(encoding.getValue());
        }
        Header contentType = httpEntity.getContentType();
        if (contentType != null) {
            response.setContentType(contentType.getValue());
        }
    }
}
