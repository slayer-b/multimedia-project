package com.adv.service.block;

import com.adv.core.model.Block;
import common.cms.services2.AGenericCmsService;
import common.services.generic.IGenericService;
import java.util.Collection;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Service("blockGenericCmsService")
public class CmsBlockServiceImpl extends AGenericCmsService<Block, Long> {

    private final String[] ORDER_BY = new String[]{"id"};
    private final String[] ORDER_HOW = new String[]{"asc"};

    @Override
    public int saveOrUpdateCollection(Collection<Block> c) {
        throw new UnsupportedOperationException("Block can not be updated");
        //return service.updateCollection(c, "width", "height");
    }

    @Override
    public Block getInsertBean(Block a) {
        throw new UnsupportedOperationException("Not implemented yet");
        /*if (a==null)
            a = new Block();
        return a;*/
    }

    /*@Override
    public void initUpdate(Map<String, Object> model) {
        model.put("positions", this.advertisement_service.getPositions());
    }

    @Override
    public void initInsert(Map<String, Object> model) {
        model.put("positions", this.advertisement_service.getPositions());
    }

    @Override
    public void initView(Map<String, Object> model) {
        model.put("positions", this.advertisement_service.getPositions());
    }*/

    @Override
    public String[] getListOrderBy() {
        return ORDER_BY;
    }

    @Override
    public String[] getListOrderHow() {
        return ORDER_HOW;
    }

    /*@Override
    public List<String> getListPropertyNames() {return CMS_SHORT_ALIAS;}

    @Override
    public List<String> getListPropertyAliases() {return CMS_SHORT_ALIAS;}*/

    //------------------------------------ initialization ----------------------------------
    @Resource(name = "blockServiceGeneric")
    public void setService(IGenericService<Block, Long> value) {
        this.service = value;
    }
}
