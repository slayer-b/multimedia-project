/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.adv.service.block;

import com.adv.core.model.Block;
import com.adv.core.model.BlockLocation;
import com.adv.core.model.User;
import com.adv.core.model.UserDefaults;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public interface IBlockService {

    /**
     * returns a block for given referer, or creates a new one if none exists.
     * @param referer url that requests javascript
     * @param pub_id pub_id of block, may be null
     * @return a valid block
     */
    BlockLocation createBlockLocationBlock(String referer, String pub_id);

    /**
     * returns a block for given referer, null if none exists.
     * @param referer url that requests javascript
     * @param pub_id pub_id of block, may be null
     * @return a valid block or null if none exists
     */
    BlockLocation getBlockLocation(String referer, String pub_id);

    /**
     * checks weather block with given pub_id exists.
     * @param pub_id pub_id of block, may be null
     * @return true if exists
     */
    boolean checkBlock(String pub_id);

    /**
     * creates a block for user with given id.
     * @param user that owns block
     * @return a newly generated block
     */
    Block createBlock(User user);

    /**
     * creates a block for user with given id with given user defaults.
     * @param user that owns block
     * @param defaults defaults to use for block creation
     * @return a newly generated block
     */
    Block createBlockWithDefaults(User user, UserDefaults defaults);

    /**
     * searches for block for user with given id.
     * creates new if none found.
     * @param user for which to find block
     */
    Block getDefaultBlock(User user);

}
