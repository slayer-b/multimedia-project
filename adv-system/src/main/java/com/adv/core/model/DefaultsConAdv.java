
package com.adv.core.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Entity(name = "DefaultsConAdv")
@Table(name = "defaults_con_adv")
public abstract class DefaultsConAdv extends DefaultsContent {
    private static final long serialVersionUID = -6781926107923713667L;

    private ConAdvProps properties;

    @Embedded
    public ConAdvProps getProperties() {
        return properties;
    }

    public void setProperties(ConAdvProps properties) {
        this.properties = properties;
    }
}
