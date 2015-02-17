package com.adv.service.block;

import com.adv.core.model.ContentLink;
import com.adv.core.model.ItemLink;
import com.adv.order.model.OrderUnits;
import java.util.List;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public interface IContentLinkService {

    /**
     * get advertisements for block with given id.
     * @param block block for which find advertisements
     * @param page_num number of page with advertisements
     * @return
     */
    List<ItemLink> getAdvertisements(ContentLink content, Integer page_num);

    /**
     * checks whether block of advertisements has page with this number.
     * @param block block for whitch find advertisements
     * @param page_num number of page with advertisements
     * @return true if valid
     */
    boolean isAdvertisementPageValid(ContentLink content, Integer page_num);

    /**
     * add advertisement for given content object(location).
     * The advertisement is inactive, until you pay for it.
     * @param content content where it will be
     * @param adv advertisement to add
     */
    void addAdvertisement(ContentLink content, ItemLink adv, OrderUnits order);

    void increaseAdvertisementViews(Long id_adv);

    void increaseAdvertisementClicks(Long id_adv);
}
