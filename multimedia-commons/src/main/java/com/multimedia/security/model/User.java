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

package com.multimedia.security.model;

import org.hibernate.validator.constraints.Email;
import test.annotations.HtmlSpecialChars;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author demchuck.dima@gmail.com
 */
public class User implements Serializable, Cloneable {

    private static final long serialVersionUID = 6032437077720755050L;

    private Long id;
    @HtmlSpecialChars
    private String name;
    @Email
    private String email;
    @HtmlSpecialChars
    private String login;
    private Date last_accessed;
    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<String> getRolesStr() {
        if (roles == null) {
            return null;
        } else {
            Iterator<Role> items = roles.iterator();
            Set<String> rolesNew = new HashSet<String>();
            while (items.hasNext()) {
                Role r = items.next();
                rolesNew.add(r.getRole());
            }
            return rolesNew;
        }
    }

    public void setRolesStr(Set<String> roles) {
        //transforming to set of Role objects
        if (roles != null) {
            HashSet<Role> newItems = new HashSet<Role>();
            for (String role : roles) {
                Role r = new Role();
                r.setRole(role);
                r.setUser(this);
                newItems.add(r);
            }

            if (this.roles == null) {
                this.roles = newItems;
            } else {
                this.roles.retainAll(newItems);
                this.roles.addAll(newItems);
            }
        }
    }

    /**
     * replace all values that are in both sets by values in second set
     */
    public void setNewRoles(Set<Role> newItems) {
        if (this.roles != null) {
            newItems.retainAll(roles);
            newItems.addAll(roles);
            roles = newItems;
        }
    }

    /**
     * copies only simple types i.e. strings, long ...
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        User o = new User();
        o.email = this.email;
        o.id = this.id;
        o.login = this.login;
        o.name = this.name;
        //o.roles = this.roles;
        return o;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.login != null ? this.login.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return !((this.login == null) ? (other.login != null) : !this.login.equals(other.login));
    }

    public Date getLast_accessed() {
        return last_accessed;
    }

    public void setLast_accessed(Date last_accessed) {
        this.last_accessed = last_accessed;
    }
}
