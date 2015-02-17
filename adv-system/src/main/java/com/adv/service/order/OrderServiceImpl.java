package com.adv.service.order;

import com.adv.core.model.ItemAdv;
import com.adv.core.types.PaymentMethod;
import com.adv.order.model.OrderUnits;
import common.dao.IGenericDAO;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dmitriy_Demchuk
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private IGenericDAO<OrderUnits, Long> orderUnitsDAO;
    @Autowired
    private IGenericDAO<ItemAdv, Long> itemAdvDAO;

    @Override
    public boolean orderPaid(Integer payment_no, Double price, PaymentMethod paymentMethod) {
        List<OrderUnits> orders = orderUnitsDAO.getByPropertyValue("paymentNo", payment_no);
        if (orders.size() > 0) {
            OrderUnits order = orders.get(0);
            if (order.getPrice().equals(price)) {
                List<ItemAdv> items = itemAdvDAO.getByPropertyValue("order.id", order.getId());
                if (items.size() > 0) {
                    ItemAdv item = items.get(0);
                    item.setPaid(Boolean.TRUE);
                    item.setDate_paid(new Timestamp(System.currentTimeMillis()));
                    item.setPaymentMethod(paymentMethod);
                    itemAdvDAO.makePersistent(item);
                    return true;
                }
            }
        }
        return false;
    }
}
