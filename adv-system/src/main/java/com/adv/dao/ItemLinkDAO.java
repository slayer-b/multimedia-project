package com.adv.dao;

import com.adv.core.model.ItemLink;
import common.dao.IGenericDAO;
import java.util.List;

/**
 *
 * @author Dmitriy_Demchuk
 */
public interface ItemLinkDAO extends IGenericDAO<ItemLink, Long> {
    /**
     * get all paid advertisements with given id_location sorted.
     */
    List<ItemLink> getItemLinkClickAds(Long id_location, Integer first, Integer quantity);
    /**
     * get all paid advertisements with given id_location sorted.
     */
    List<ItemLink> getItemLinkSupplantationAds(Long id_location, Integer first, Integer quantity);
}
