package com.adv.service.user;

import com.adv.core.model.DefaultsConLink;
import com.adv.core.model.DefaultsContent;
import com.adv.core.model.User;
import com.adv.core.model.UserDefaults;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 *
 * @author slayer
 */
@RunWith(SpringJUnit4ClassRunner.class)
// ApplicationContext will be loaded from "/applicationContext.xml" and "/applicationContext-test.xml"
// in the root of the classpath
@ContextConfiguration(locations = {"/com/adv/applicationContext.xml",
                                   "/com/adv/applicationContext-hibernate.xml"})
public class UserDefaultsServiceTest {
    
    @Autowired
    private IUserDefaultsService userDefaultsService;
    
    @Autowired
    private IUserService userService;

    @Test
    @Ignore
    public void testInsertSelect() {
        User user = new User();
        user.setLogin("slayer");
        user.setName("slayer");
        user.setPassword("1");
        user.setEmail("demchuck.dima@gmail.com");
        user.setLast_accessed(new Date());
        userService.registerUser(user);

        UserDefaults defaults = userDefaultsService.createUserDefaults(user);
        DefaultsContent contendDefaults = defaults.getDefaultsContent();
        switch (contendDefaults.getContent_type()) {
            case LINK: 
                DefaultsConLink linkDefaults = (DefaultsConLink) contendDefaults;
                assertNotNull(linkDefaults.getProperties());
        }
    }
    
}
