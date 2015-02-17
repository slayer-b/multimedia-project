package com.adv.controller.block;

import com.adv.controller.block.types.IContentTypeHandler;
import com.adv.core.model.BlockContent;
import com.adv.core.model.BlockLocation;
import com.adv.core.model.ContentLink;
import com.adv.core.model.ItemLink;
import com.adv.core.types.ContentType;
import com.adv.core.types.PaymentMethod;
import com.adv.order.model.OrderUnits;
import com.adv.order.domain.OrderItemLink;
import com.adv.service.block.IBlockService;
import com.adv.service.payment.PaymentHandler;
import common.utils.RequestUtils;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * actually draws a html for block.
 * @author demchuck.dima@gmail.com
 */
@Controller("blockController")
@RequestMapping("/adv.htm")
public class BlockController {
    private final Logger logger = LoggerFactory.getLogger(BlockController.class);

    private IBlockService blockService;

    private IContentTypeHandler<ItemLink, ContentLink> contentLinkHandler;

    private PaymentHandler paymentHandler;

    @RequestMapping
    public final String generateBlock(Map<String, Object> model,
            @RequestParam("pub_id") String pub_id,
            @RequestParam(value = "adv_p", defaultValue = "0") Integer page_number,
            @RequestHeader("Referer") String referer)
    {
        BlockLocation location = blockService.getBlockLocation(referer, pub_id);
        if (location == null) {
            return null;
        } else {
            BlockContent content = location.getBlockContent();
            ContentType type = content.getContent_type();
            model.put("pub_id", pub_id);
            model.put("content", content);
            switch (type) {
                case LINK:
                    return createModelForContentLink((ContentLink) content, page_number, model);
                default:
                    logger.error("cannot render content for pub_id[" + pub_id + "]. referer [" + referer + "]");
            }
            return null;
        }
    }

    /**
     * create data for content link and save it to model.
     * @param model map for storing data
     * @return url for content or null if error
     */
    private String createModelForContentLink(ContentLink content, Integer page_number, Map<String, Object> model) {
        return contentLinkHandler.createModelForContentLink(content, page_number, model);
    }

    @RequestMapping(params = {"do=addLink"} )
    public final String addAdvertisementForBlockPrepare(Map<String, Object> model,
            @RequestParam("pub_id") String pub_id, @RequestHeader("Referer") String referer)
    {
        if (blockService.checkBlock(pub_id)) {
            BlockLocation location = blockService.getBlockLocation(referer, pub_id);
            if (location == null) {
                return null;
            }
            model.put("pub_id", pub_id);
            model.put("content_data", contentLinkHandler.getAdvertisementOrder((ContentLink) location.getBlockContent()));
            model.put("paymentMethods", PaymentMethod.values());
            return contentLinkHandler.addAdvertisementPrepare((ContentLink) location.getBlockContent(), model);
        } else {
            return null;
        }
    }

    @RequestMapping(params = {"do=addLink", "action=addLink"} )
    public final String addAdvertisementForBlock(Map<String, Object> model,
            @RequestParam("pub_id") String pub_id,
            @RequestHeader("Referer") String referer,
            @Valid OrderItemLink order, BindingResult res,
            HttpServletRequest req)
    { //TODO: make here Order<ItemLink>
        if (blockService.checkBlock(pub_id)) {
            BlockLocation location = blockService.getBlockLocation(referer, pub_id);
            if (location == null) {
                return null;
            }
            model.put("pub_id", pub_id);
            model.put("content_data", order);
            ContentLink content = (ContentLink) location.getBlockContent();
            if (res.hasErrors()) {
                model.put(BindingResult.MODEL_KEY_PREFIX + "content_data", res);
                model.put("paymentMethods", PaymentMethod.values());
                common.utils.CommonAttributes.addErrorMessage("form_errors", model);
            } else {
                OrderUnits orderUnits = contentLinkHandler.addAdvertisementForBlock(content, order, model);
                if (orderUnits != null) {
                    String url = paymentHandler.getUrl(order.getPaymentMehod());
                    if (url!=null) {
                        model.put("price", orderUnits.getPrice()); //for all payment methods we need price
                        model.put("payment_no", orderUnits.getPaymentNo()); //inner number of payment
                        paymentHandler.initModel(order.getPaymentMehod(), model, getCurrentUrl(req).toString());
                        return url;
                    }
                }
            }
            return contentLinkHandler.addAdvertisementPrepare(content, model);
        } else {
            return null;
        }
    }

    /** returns an url for this site: http://host:port/contextPath. */
    private StringBuilder getCurrentUrl(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder("http://");
        sb.append(request.getServerName()).append(":").append(request.getServerPort());
        sb.append(request.getContextPath());
        while (sb.charAt(sb.length() - 1) == '/') {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb;
    }

    @RequestMapping(params = "do=track")
    public final void logClick(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        String pub_id = request.getParameter("pub_id");
        Long item_id = RequestUtils.getLongParam(request, "item_id");
        if (referer != null && item_id != null){
            BlockLocation location = blockService.getBlockLocation(referer, pub_id);
            if (location != null) {
                switch (location.getBlockContent().getContent_type()) {
                    case LINK:
                        contentLinkHandler.increaseClickCount(item_id);
                        break;
                    default:
                        logger.error("cannot render content for pub_id[" + pub_id + "]. referer [" + referer + "]");
                }
            }
        }
    }

    //------------------------------------ dependency injection -----------------------

    @Required
    @Resource(name = "blockService")
    public final void setBlockService(IBlockService value) {
        this.blockService = value;
    }
    @Required
    @Resource(name = "ContentLinkHandler")
    public final void setContentLinkHandler(IContentTypeHandler<ItemLink, ContentLink> value) {
        this.contentLinkHandler = value;
    }
    @Required
    @Resource(name = "paymentHandler")
    public final void setPaymentHandler(PaymentHandler value) {
        this.paymentHandler = value;
    }

}
