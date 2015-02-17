package com.adv.dao;

import com.adv.core.model.ItemLink;
import common.dao.GenericDAOHibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Dmitriy_Demchuk
 */
@Repository("itemLinkDAO")
public class ItemLinkDAOImpl extends GenericDAOHibernate<ItemLink, Long> implements ItemLinkDAO {

    public ItemLinkDAOImpl() {
        super(ItemLink.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemLink> getItemLinkClickAds(Long id_location, Integer first, Integer quantity) {
        return getCurrentSession()
                .createQuery("select il from ItemLink il, OrderClicks oc where oc.id=il.order.id and il.id_location=:id_location and il.paid=true and il.stats.stats.clicks < oc.quantity order by il.date_paid desc")
                .setLong("id_location", id_location)
                .setFirstResult(first).setMaxResults(quantity)
                .list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemLink> getItemLinkSupplantationAds(Long id_location, Integer first, Integer quantity) {
        return getCurrentSession()
                .createQuery("select il from ItemLink il, OrderSupplantation os where os.id=il.order.id and il.id_location=:id_location and il.paid=true order by il.date_paid desc")
                .setLong("id_location", id_location)
                .setFirstResult(first).setMaxResults(quantity)
                .list();
    }

}
