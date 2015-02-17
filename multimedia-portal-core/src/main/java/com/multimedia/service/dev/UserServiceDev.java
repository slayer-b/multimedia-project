package com.multimedia.service.dev;

import com.multimedia.dao.UserDAO;
import com.multimedia.security.model.User;
import com.multimedia.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userServiceDev")
@Profile("dev")
public class UserServiceDev implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceDev.class);

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = userDAO.getAllShortOrdered(null, null, null);
        if (users.size() == 0) {
            logger.debug("User with name [{}] not found", username);
            throw new UsernameNotFoundException("User [" + username + "] not found.");
        } else if (users.size() > 1) {
            throw new UsernameNotFoundException("Found " + users.size() + " users for [" + username + "] username.");
        }
        logger.debug("User with email [{}] found", users.get(0).getEmail());
        return UserServiceImpl.convert(users.get(0));
    }
}
