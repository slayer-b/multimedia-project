
package com.adv.service.user;

import com.adv.core.model.Block;
import com.adv.core.model.BlockContent;
import com.adv.core.model.DefaultsConLink;
import com.adv.core.model.User;
import com.adv.core.model.UserDefaults;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public interface IUserDefaultsService {
    /**
     * creates defaults for given user.
     * if defaults already exists return existing.
     * @param user which to retrieve defaults
     * @return defaults for current user
     */
    UserDefaults createUserDefaults(User user);
    /**
     * gets object that represents defaults for user with given id.
     * @param user which to retrieve defaults
     * @return defaults for current user
     */
    UserDefaults getUserDefaults(User user);

    /**
     * get defaults for user with given u_id.
     * @param u_id an uniq user code
     * @return defaults for user with given u_id
     */
    UserDefaults getUserDefaults(String u_id);

    /**
     * saves defaults for user.
     * @param defaults the defaults to save
     * @param user user for which to save defaults
     * @param use if to generate default block/location
     * @return true if succeed
     */
    boolean saveDefaultsForUser(DefaultsConLink defaults, Boolean use, User user);

    /**
     * generate default content using given defaults.
     * @param defaults defaults for generating Content
     */
    BlockContent getDefaultContent(UserDefaults defaults, Block block);
}
