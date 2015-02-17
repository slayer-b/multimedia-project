package com.adv.service.block.content;

import com.adv.core.model.BlockContent;
import common.dao.IGenericDAO;
import common.services.generic.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dmitriy_Demchuk
 */
@Service("contentServiceGeneric")
public class GenericContentServiceImpl extends GenericServiceImpl<BlockContent, Long> implements IGenericContentService {

    @Autowired
    public GenericContentServiceImpl(IGenericDAO<BlockContent, Long> blockContentDAO) {
        super(blockContentDAO);
    }
    
}
