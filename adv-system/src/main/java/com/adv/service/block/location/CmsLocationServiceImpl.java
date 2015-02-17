package com.adv.service.block.location;

import com.adv.core.model.BlockLocation;
import com.adv.core.model.ItemLinkStats;
import com.adv.core.model.Stats;
import common.cms.services2.AGenericCmsService;
import common.dao.IGenericDAO;
import common.services.generic.IGenericService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Service("locationGenericCmsService")
public class CmsLocationServiceImpl extends AGenericCmsService<BlockLocation, Long> {
    private IGenericDAO<ItemLinkStats, Long> itemLinkStatsDAO;

    @Override
    public List<BlockLocation> getFilteredByPropertiesValue(List<String> propertyName, List<Object> propertyValue, int firstItem, int quantity) {
        List<BlockLocation> locations = super.getFilteredByPropertiesValue(propertyName, propertyValue, firstItem, quantity);
        //for all locations counting clicks + views
        for (BlockLocation location : locations) {
            switch(location.getBlockContent().getContent_type()) {
                case LINK:
                    location.setStats(statsForLink(location.getId()));
                    break;
                default:
                    throw new UnsupportedOperationException("Not supported yet.");
            }
        }
        return locations;
    }

    /**
     * get stats for link.
     * @param id id of link
     */
    private Stats statsForLink(Long id) {
        long views = 0;
        long clicks = 0;
        //TODO: mb optimize
        List<Stats> statses = itemLinkStatsDAO.getSingleProperty("stats", "item.id_location", id);
        for (Stats stats:statses) {
            views += stats.getViews();
            clicks += stats.getClicks();
        }
        return createStats(views, clicks);
    }

    private Stats createStats(Long views, Long clicks) {
        Stats s = new Stats();
        s.setViews(views);
        s.setClicks(clicks);
        return s;
    }
//------------------------------------ initialization ----------------------------------

    @Required
    @Resource(name = "locationServiceGeneric")
    public final void setService(IGenericService<BlockLocation, Long> value) {
        this.service = value;
    }

    @Required
    @Resource(name = "itemLinkStatsDAO")
    public final void setItemLinkStatsDAO(IGenericDAO<ItemLinkStats, Long> value) {
        this.itemLinkStatsDAO = value;
    }
}
