package com.adv.service.block;

import com.adv.core.model.Block;
import common.dao.IGenericDAO;
import common.services.generic.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Service("blockServiceGeneric")
public class GenericBlockServiceImpl extends GenericServiceImpl<Block, Long> implements IGenericBlockService {
    private final Random random = new Random();

    @Autowired
    public GenericBlockServiceImpl(IGenericDAO<Block, Long> blockDAO) {
        super(blockDAO);
    }

    @Override
    public synchronized void save(Block entity) {
        entity.setPub_id(generateId());
        super.save(entity);
    }

    /**
     * generates an uniq String with public id for a new block.
     */
    private String generateId() {
        String id = Long.toString(random.nextLong());
        while (dao.getRowCount("pub_id", id) > 0) {
            id = Long.toString(random.nextLong());
        }
        return id;
    }

}
