package com.adv.controller.block;

import com.adv.core.model.BlockLocation;
import com.adv.service.block.IBlockService;
import java.io.IOException;
import java.io.Writer;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Controller("jsController")
@RequestMapping("/ads")
public class JSController {

    private IBlockService service;

    @RequestMapping("/adv-{pub_id}.js")
    public final void generateJS(@PathVariable("pub_id") String pub_id,
            HttpServletRequest request, HttpServletResponse response)
        throws IOException
    {
        //throw new UnsupportedOperationException("not implemented yet");
        String referer = request.getHeader("Referer");
        if (referer == null) {
            return;
        }
        BlockLocation location = service.createBlockLocationBlock(referer, pub_id);
        if (location == null) {
            return;
        }
        //last write block to response as a frame
        response.setContentType("text/javascript");
        Writer writer = response.getWriter();
        writer.write("document.write(\"<iframe height='");
        writer.write(location.getBlockContent().getHeight());
        writer.write("' width='");
        writer.write(location.getBlockContent().getWidth());
        writer.write("' scrolling='no' src='http://");
        writer.write(request.getServerName());
        writer.write(":");
        writer.write(Integer.toString(request.getServerPort()));
        writer.write(request.getContextPath());
        writer.write("/adv.htm?pub_id=");
        writer.write(location.getBlock().getPub_id());
        writer.write("'></iframe>\")");
        writer.close();
    }

    //---------------------------------------- dependency injection -------------------------------------

    @Resource(name = "blockService")
    public final void setService(IBlockService value) {
        this.service = value;
    }

}
