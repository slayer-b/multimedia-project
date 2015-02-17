package com.adv.service.block.location;

import com.adv.core.model.BlockLocation;
import common.dao.IGenericDAO;
import common.services.generic.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Service("locationServiceGeneric")
public class GenericLocationService extends GenericServiceImpl<BlockLocation, Long> implements IGenericLocationService {
    @Autowired
    public GenericLocationService(IGenericDAO<BlockLocation, Long> blockLocationDAO) {
        super(blockLocationDAO);
    }
}
