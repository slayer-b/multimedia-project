/*
 * Copyright 2012 demchuck.dima@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.adv.service.user;

import com.adv.core.model.User;
import com.adv.core.model.UserDefaults;
import com.adv.service.block.IBlockService;
import common.dao.IGenericDAO;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    private IGenericDAO<User, Long> userDAO;
    private IUserDefaultsService userDefaults;
    private IBlockService blockService;

    @Override
    public boolean registerUser(User user) {
        user.setLast_accessed(new java.sql.Timestamp(System.currentTimeMillis()));
        //TODO: mb make it somewhere else
        String md5 = org.apache.catalina.realm.RealmBase.Digest(user.getPassword(), "MD5", "UTF-8");
        user.setPassword(md5);
        //TODO: insert roles
        userDAO.makePersistent(user);

        UserDefaults defaults = userDefaults.createUserDefaults(user);

        blockService.createBlockWithDefaults(user, defaults);
        return true;
    }

    @Override
    public User getUser(Long id) {
        return userDAO.getById(id);
    }

    protected static final String[] SECURITY_UPDATE = new String[]{"last_accessed"};
    @Override
    public void userEntered(com.multimedia.security.model.User user) {
        if (user != null) {
            userDAO.updatePropertiesById(SECURITY_UPDATE, new Object[]{new java.util.Date()}, user.getId());
        }
    }

    //------------------------------- dependency injection -----------------------
    @Required
    @Resource(name = "userDAO")
    public final void setUserDao(IGenericDAO<User, Long> value) {
        this.userDAO = value;
    }
    @Required
    @Resource(name = "userDefaultsService")
    public final void setUserDefaultsService(IUserDefaultsService value) {
        this.userDefaults = value;
    }
    @Required
    @Resource(name = "blockService")
    public final void setBlockService(IBlockService value) {
        this.blockService = value;
    }

}
