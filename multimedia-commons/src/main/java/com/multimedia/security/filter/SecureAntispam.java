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

package com.multimedia.security.filter;

import common.web.filters.Antispam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Set;

/**
 * this filter is checking an anti-spam code for user to get protected resource
 * but if user is logined and is in an appropriate role the code is not checked ...
 *
 * @author demchuck.dima@gmail.com
 */
public class SecureAntispam extends Antispam {
    private final Logger logger = LoggerFactory.getLogger(SecureAntispam.class);

    /**
     * name of parameters with valid roles.
     */
    private static final String ROLES_PARAM = "roles";
    /**
     * valid roles to access without anti-spam code.
     */
    private Set<String> roles;

    @Override
    public String canAccess(HttpServletRequest request) {
        String rez = super.canAccess(request);
        if (rez != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getAuthorities() != null) {
                Set<String> granted = AuthorityUtils.authorityListToSet(auth.getAuthorities());
                if (granted.retainAll(roles) && !granted.isEmpty()) {
                    logger.debug("User gains access without antispam.");
                    return null;
                }
            }
        }
        return rez;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        super.init(filterConfig);
        if (filterConfig != null) {
            setRoles(filterConfig.getInitParameter(ROLES_PARAM));
        }
    }

    /**
     * parses given String.
     *
     * @param s roles to set
     */
    void setRoles(String s) {
        if (StringUtils.hasText(s)) {
            roles = StringUtils.commaDelimitedListToSet(s);
        } else {
            roles = Collections.emptySet();
        }
    }
}
