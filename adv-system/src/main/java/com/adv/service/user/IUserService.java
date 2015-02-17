/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.adv.service.user;

import com.adv.core.model.User;
import security.services.ISecurityService;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public interface IUserService extends ISecurityService {
    /**
     * register given user in the application.
     * @param user user to register
     * @return true if succeed
     */
    boolean registerUser(User user);
}
