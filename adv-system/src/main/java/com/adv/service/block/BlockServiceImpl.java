package com.adv.service.block;

import com.adv.core.model.Block;
import com.adv.core.model.BlockContent;
import com.adv.core.model.BlockLocation;
import com.adv.core.model.LocationSite;
import com.adv.core.model.User;
import com.adv.core.model.UserDefaults;
import com.adv.payment.model.BlockAdvCost;
import com.adv.payment.model.UserAdvCost;
import com.adv.service.user.IUserDefaultsService;
import com.adv.util.generator.Generator;
import com.adv.util.generator.IGenerator;
import common.dao.IGenericDAO;
import java.sql.Timestamp;
import java.util.List;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

/**
*
* @author demchuck.dima@gmail.com
*/
@Service("blockService")
public class BlockServiceImpl implements IBlockService {
    private Logger logger = LoggerFactory.getLogger(BlockServiceImpl.class);

    private IGenericDAO<Block, Long> blockDAO;

    private IGenericDAO<LocationSite, Long> locationSiteDAO;
    private IGenericDAO<BlockLocation, Long> locationDAO;

    private IUserDefaultsService userDefaultsService;
    
    private static final int GENERATED_STRING_SIZE = 64;

//----------------------------------------------- methods -------------------------------------



    @Override
    public BlockLocation getBlockLocation(String referer, String pub_id) {
            Block block = getBlock(pub_id);
            if (block == null) {
                    return null;
            } else {
                    return getBlockLocation(referer, block);
            }
    }

    @Override
    public BlockLocation createBlockLocationBlock(String referer, String pub_id) {
            Block block = getBlock(pub_id);
            if (block == null) {
                    return null;
            }
            UserDefaults u = userDefaultsService.getUserDefaults(block.getUser());
            BlockLocation location = createBlockLocation(referer, block, u);
            if (location == null && u.getUseDefaults()) {
                    logger.error("can not generate default location for pub_id" + pub_id);
            }
            return location;
    }

    /**
     * try go get block with given pub_id.
     * @param pub_id public id of block you want to retrieve.
     * @return block of null if no blocks found.
     */
    private Block getBlock(String pub_id) {
            List<Block> blocks = blockDAO.getByPropertyValue("pub_id", pub_id);
            if (blocks == null || blocks.isEmpty()) {
                    logger.error("can not find block with pub_id: " + pub_id);
                    return null;
            }
            return blocks.get(0);
    }

    /**
     * try to get block location for given block and referrer.
     * @param block block for which you want to get location.
     * @param referer site url for which you want get location.
     * @return block location or null if not exists.
     */
    private BlockLocation getBlockLocation(String referer, Block block) {
            List<LocationSite> locations = locationSiteDAO.getByPropertiesValuePortionOrdered(
                            null, null, new String[]{"siteUrl", "block"}, new Object[]{getUrl(referer), block},
                            0, 0, null, null);
            if (locations.isEmpty()) {
                    return null;
            } else {
                    return locations.get(0);
            }
    }

    private BlockLocation createBlockLocation(String referer, Block block, UserDefaults u) {
            //TODO: make it work not only for site, but for certain pages
            BlockLocation location = getBlockLocation(referer, block);

            if (location == null) {
                if (u.getUseDefaults()) {
                        return generateDefaultLocation(referer, block, u);
                } else {
                        return null;
                }
            } else {
                return location;
            }
    }

    @Override
    public Block getDefaultBlock(User user) {
        //TODO: insert ordering here
            List<Block> blocks = blockDAO.getByPropertyValue("id_user", user.getId());
            if (blocks.size() > 0) {
                    return blocks.get(0);
            } else {
                    return createBlock(user);
            }
    }

    /**
     * generates a block for user with given id.
     * @param user user for which to find block
     */
    @Override
    public synchronized Block createBlock(User user) {
            return createBlockWithDefaults(user, userDefaultsService.getUserDefaults(user));
    }

    @Override
    public synchronized Block createBlockWithDefaults(User user, UserDefaults defaults) {
            Block block = new Block();
            block.setId_user(user.getId());
            block.setName("generated - " + new Timestamp(System.currentTimeMillis()));
            block.setPub_id(generateBlockKey());

            block.setAdvCost(getBlockCost(defaults.getAdvCost()));

            blockDAO.makePersistent(block);
            return block;
    }

    private BlockAdvCost getBlockCost(UserAdvCost user) {
        BlockAdvCost blockCost = new BlockAdvCost();
        blockCost.setDefaultUnit(user.getDefaultUnit());
        blockCost.setClickCost(user.getClickCost());
        blockCost.setDurationCost(user.getDurationCost());
        blockCost.setSupplantationCost(user.getSupplantationCost());
        blockCost.setViewCost(user.getViewCost());
        return blockCost;
    }

    private IGenerator generator = new Generator(GENERATED_STRING_SIZE);
    private String generateBlockKey() {
            StringBuilder rez = new StringBuilder();
            generator.generate(rez);
            while (blockDAO.getRowCount("pub_id", rez.toString()) > 0) {
                    generator.generate(rez);
            }
            return rez.toString();
    }

    protected BlockLocation generateDefaultLocation(String referer, Block block, UserDefaults u) {
        LocationSite location = new LocationSite();
        location.setSiteUrl(getUrl(referer));
        location.setBlock(block);

        BlockContent content = userDefaultsService.getDefaultContent(u, block);
        if (content == null) {
            //TODO: mb remove this log, or change
                logger.error("can not generate default content for pub_id [" + block.getPub_id()
                        + "] user [" + u.getUser().getEmail() + "]");
                return null;
        } else {
                location.setBlockContent(content);
                content.setBlockLocation(location);
        }

        locationDAO.makePersistent(location);

        //location_dao.refresh(location);
        //content.setId(location.getId());
        //content_dao.makePersistent(content);
        return location;
    }

    private String getUrl(String referer) {
                    int start = referer.indexOf("//") + 2;
                    int end1 = referer.indexOf(':', start);
                    int end2 = referer.indexOf('/', start);
                    int end;
                    if (end1 != -1 && end1 < end2) {
                            end = end1;
                    } else {
                            end = end2;
                    }
                    String ref;
                    if (end == -1) {
                            ref = referer.substring(start);
                    } else {
                            ref = referer.substring(start, end);
                    }
                    return ref;
    }

    @Override
    public boolean checkBlock(String pub_id) {
            return blockDAO.getRowCount("pub_id", pub_id) == 1;
    }

// ------------------------------------------- dependency injection --------------------------------
    @Required
    @Resource(name = "blockDAO")
    public void setBlockDAO(IGenericDAO<Block, Long> blockDAO) {
            this.blockDAO = blockDAO;
    }
    @Required
    @Resource(name = "userDefaultsService")
    public void setUserDefaultsService(IUserDefaultsService value) {
            this.userDefaultsService = value;
    }
    @Required
    @Resource(name = "locationSiteDAO")
    public void setLocationSiteDAO(IGenericDAO<LocationSite, Long> value) {
            this.locationSiteDAO = value;
    }
    @Required
    @Resource(name = "blockLocationDAO")
    public void setLocationDAO(IGenericDAO<BlockLocation, Long> value) {
            this.locationDAO = value;
    }

}
