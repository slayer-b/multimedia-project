package com.adv.service.order;

import com.adv.core.model.ContentAdv;
import com.adv.order.model.Order;
import com.adv.order.model.OrderClicks;
import com.adv.order.model.OrderSupplantation;
import com.adv.order.model.OrderUnits;
import com.adv.payment.webmoney.WebMoneyConfig;
import common.dao.IGenericDAO;
import java.sql.Timestamp;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dmitriy_Demchuk
 */
@Service("orderBuilder")
public class OrderBuilderImpl implements OrderBuilder {
    @Autowired
    private IGenericDAO<OrderUnits, Long> orderUnitsDAO;
    private Random random = new Random();

    @Override
    public synchronized OrderUnits createOrder(Order<?> order, ContentAdv content) {
        //TODO: mb replace with EnumMap
        OrderUnits result;
        switch (content.getPaymentUnits().getType()) {
            case CLICKS:
                OrderClicks clicks = new OrderClicks();
                clicks.setQuantity(order.getQuantity());
                clicks.setPrice(order.getQuantity() * content.getPaymentUnits().getCost());
                result = clicks;
                break;
            case SUPPLANTATION:
                OrderSupplantation supplantation = new OrderSupplantation();
                supplantation.setPrice(content.getPaymentUnits().getCost());
                result = supplantation;
                break;
            default:
                throw new UnsupportedOperationException("cannot handle [" + content.getPaymentUnits().getType() + "] payment unit type");
        }
        result.setCreated(new Timestamp(System.currentTimeMillis()));
        result.setPaymentNo(generatePaymentNo());
        orderUnitsDAO.makePersistent(result);
        return result;
    }

    private Integer generatePaymentNo() {
        Integer value;
        do {
            value = random.nextInt(WebMoneyConfig.WEB_MONEY_MAX_NUMBER);
        } while (orderUnitsDAO.getRowCount("paymentNo", value) > 0);
        return value;
    }
}
