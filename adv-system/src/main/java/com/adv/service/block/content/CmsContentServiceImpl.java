package com.adv.service.block.content;

import com.adv.core.model.BlockContent;
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
 * @author Dmitriy_Demchuk
 */
@Service("contentGenericCmsService")
public class CmsContentServiceImpl extends AGenericCmsService<BlockContent, Long> {
    private IGenericDAO<ItemLinkStats, Long> itemLinkStatsDAO;

    @Override
    public List<BlockContent> getFilteredByPropertiesValue(List<String> propertyName, List<Object> propertyValue, int firstItem, int quantity) {
        List<BlockContent> contents = super.getFilteredByPropertiesValue(propertyName, propertyValue, firstItem, quantity);
        //for all locations counting clicks + views
        for (BlockContent content : contents) {
            switch (content.getContent_type()) {
                case LINK:
                    content.getBlockLocation().setStats(statsForLink(content.getBlockLocation().getId()));
                    break;
                default:
                    throw new UnsupportedOperationException("Not supported yet.");
            }
        }
        return contents;
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
    @Resource(name = "contentServiceGeneric")
    public final void setService(IGenericService<BlockContent, Long> value) {
        this.service = value;
    }

    @Required
    @Resource(name = "itemLinkStatsDAO")
    public final void setItemLinkStatsDAO(IGenericDAO<ItemLinkStats, Long> value) {
        this.itemLinkStatsDAO = value;
    }
    
}
