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

package com.multimedia.service.impl;


import com.multimedia.dao.UserDAO;
import com.multimedia.security.dto.MyUser;
import com.multimedia.security.model.Role;
import com.multimedia.security.model.User;
import com.multimedia.service.IUserService;
import common.services.generic.GenericServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author demchuck.dima@gmail.com
 */
@Service("userService")
public class UserServiceImpl extends GenericServiceImpl<User, Long>
        implements IUserService<User, Long>, UserDetailsService,
                    AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public static final String[] ORDER_BY = null;
    public static final String[] ORDER_HOW = null;
    public static final String[] ALL_SHORT_CMS = {"id", "login", "name", "last_accessed"};

    @Override
    public List<User> getShortOrderedByPropertyValueCms(String propertyName, Object propertyValue) {
        if (propertyValue == null) {
            return dao.getByPropertyValuePortionOrdered(ALL_SHORT_CMS, ALL_SHORT_CMS, null, null, -1, -1, ORDER_BY, ORDER_HOW);
        } else {
            return dao.getByPropertyValuePortionOrdered(ALL_SHORT_CMS, ALL_SHORT_CMS, propertyName, propertyValue, -1, -1, ORDER_BY, ORDER_HOW);
        }
    }

    protected static final String[] SECURITY_UPDATE = {"last_accessed"};

    @Override
    public void userEntered(User user) {
        if (user != null) {
            dao.updatePropertiesById(SECURITY_UPDATE, new Object[]{new java.util.Date()}, user.getId());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = dao.getByPropertyValue("login", username);
        if (users.size() == 0) {
            logger.debug("User with name [{}] not found", username);
            throw new UsernameNotFoundException("User [" + username + "] not found.");
        } else if (users.size() > 1) {
            throw new UsernameNotFoundException("Found " + users.size() + " users for [" + username + "] username.");
        }
        logger.debug("User with email [{}] found", users.get(0).getEmail());
        return convert(users.get(0));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserDetails(OpenIDAuthenticationToken token) {
        String id = token.getIdentityUrl();

        List<User> users = dao.getByPropertyValue("login", id);
        logger.info("Search user with id: [{}]", id);

        if (!CollectionUtils.isEmpty(users)) {
            return convert(users.get(0));
        }

        String email = null;
        for (OpenIDAttribute attribute : token.getAttributes()) {
            if (attribute.getName().equals("email")) {
                email = attribute.getValues().get(0);
            }
        }
        logger.info("Creating user with email [{}]", email);

        User user = new User();
        user.setLogin(id);
        user.setEmail(email);
        if (email == null) {
            user.setName(id.substring(id.lastIndexOf("/")));
        } else {
            user.setName(email);
        }

        dao.makePersistent(user);
        return convert(user);
    }

    public static MyUser convert(User user) {
        List<GrantedAuthority> authorities;
        if (user.getRoles() == null) {
            authorities = Collections.emptyList();
        } else {
            Set<Role> roles = user.getRoles();
            authorities = new ArrayList<GrantedAuthority>(roles.size());
            for (Role role : roles) {
                authorities.add(new SimpleGrantedAuthority(role.getRole()));
            }
        }
        return new MyUser(user.getLogin(), user.getName(), user.getId(), authorities);
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.dao = userDAO;
    }
}
