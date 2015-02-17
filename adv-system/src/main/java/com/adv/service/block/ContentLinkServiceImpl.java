
package com.adv.service.block;

import com.adv.dao.ItemLinkDAO;
import com.adv.core.model.ConAdvProps;
import com.adv.core.model.ContentLink;
import com.adv.core.model.ItemLink;
import com.adv.core.model.ItemLinkStats;
import com.adv.core.model.Stats;
import com.adv.order.model.OrderUnits;
import common.dao.IGenericDAO;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Service("contentLinkService")
public class ContentLinkServiceImpl implements IContentLinkService {
    private ItemLinkDAO itemLinkDAO;
    private IGenericDAO<ItemLinkStats, Long> advStatsDAO;

    //TODO: mb customize ordering here
    //private final String[] ADV_ORDER_BY = new String[]{"date_paid"};
    //private final String[] ADV_ORDER_HOW = new String[]{"desc"};

    //private final String[] ADV_WHERE = new String[]{"id_location", "paid"};
    @Override
    public List<ItemLink> getAdvertisements(ContentLink content, Integer page_num) {
        ConAdvProps props = content.getProperties();
        Integer first = (page_num + 1 > props.getMaxPages()) ? 0 : props.getAdvQuantity() * page_num;
        List<ItemLink> ads;
        switch(content.getPaymentUnits().getType()) {
            case CLICKS:
                ads = getAdsLinkClicks(content.getId(), props, first);
                break;
            case SUPPLANTATION:
                ads = getAdsLinkSupplantation(content.getId(), props, first);
                break;
            default:
                throw new UnsupportedOperationException("not implemented yet.");
        }
        for (ItemLink item : ads) {
            increaseAdvertisementViews(item.getId());
        }
        return ads;
    }

    private List<ItemLink> getAdsLinkClicks(Long id_location, ConAdvProps props, Integer first) {
        //Object[] adv_values = new Object[]{content.getId(), Boolean.TRUE};
        //ads = adv_dao.getByPropertiesValuePortionOrdered(null, null, ADV_WHERE, adv_values, props.getAdvQuantity()*page_num, props.getAdvQuantity(), ADV_ORDER_BY, ADV_ORDER_HOW);
        return itemLinkDAO.getItemLinkClickAds(id_location, first, props.getAdvQuantity());
    }

    private List<ItemLink> getAdsLinkSupplantation(Long id_location, ConAdvProps props, Integer first) {
        //Object[] adv_values = new Object[]{content.getId(), Boolean.TRUE};
        //ads = adv_dao.getByPropertiesValuePortionOrdered(null, null, ADV_WHERE, adv_values, props.getAdvQuantity()*page_num, props.getAdvQuantity(), ADV_ORDER_BY, ADV_ORDER_HOW);
        return itemLinkDAO.getItemLinkSupplantationAds(id_location, first, props.getAdvQuantity());
    }

    @Override
    public boolean isAdvertisementPageValid(ContentLink content, Integer page_num) {
        if ((page_num < 0) || (page_num + 1 > content.getProperties().getMaxPages())) {
            return false;
        }
        return itemLinkDAO.getRowCount("id_location", content.getId()) - (page_num * content.getProperties().getAdvQuantity()) > 0;
    }

    @Override
    public void addAdvertisement(ContentLink content, ItemLink adv, OrderUnits order) {
        Long sort = (Long) itemLinkDAO.getSinglePropertyU("max(sort)");
        if (sort == null) {
            adv.setSort(Long.valueOf(1));
        } else {
            adv.setSort(Long.valueOf(sort + 1));
        }
        adv.setId_location(content.getId());
        adv.setPaid(Boolean.FALSE);
        adv.setOrder(order);

        Stats s = new Stats();
        s.setClicks(Long.valueOf(0));
        s.setViews(Long.valueOf(0));
        ItemLinkStats stats = new ItemLinkStats();
        stats.setStats(s);
        //stats.setItem(adv);
        //TODO: ensure that it is working
        adv.setStats(stats);

        itemLinkDAO.makePersistent(adv);
        itemLinkDAO.refresh(adv);
        advStatsDAO.makePersistent(stats);
    }

    @Override
    public void increaseAdvertisementViews(Long id_adv) {
        advStatsDAO.updatePropertyById(" stats.views = stats.views + 1", id_adv);
    }

    @Override
    public void increaseAdvertisementClicks(Long id_adv) {
        advStatsDAO.updatePropertyById(" stats.clicks = stats.clicks + 1", id_adv);
    }

    //---------------------------------------------------  dependency injection ----------------------------
    @Required
    @Resource(name = "itemLinkDAO")
    public final void setItemLinkDAO(ItemLinkDAO value) {
        this.itemLinkDAO = value;
    }
    @Required
    @Resource(name = "itemLinkStatsDAO")
    public final void setItemLinkStatsDAO(IGenericDAO<ItemLinkStats, Long> value) {
        this.advStatsDAO = value;
    }

}
