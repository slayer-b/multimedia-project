package com.multimedia.service.dev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("passwordEncoderDev")
@Profile("dev")
public class PasswordEncoderDev implements PasswordEncoder {
    private final Logger logger = LoggerFactory.getLogger(PasswordEncoderDev.class);
    @Override
    public String encodePassword(String rawPass, Object salt) {
        logger.debug("encode password = {}", rawPass);
        return null;
    }

    @Override
    public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
        logger.debug("password valid ? {} , {}", encPass, rawPass);
        return true;
    }
}
