package com.adv.core.model;

import com.adv.core.types.ContentType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Entity(name = "DefaultsConLink")
@Table(name = "defaults_con_link")
@DiscriminatorValue("LINK")
public class DefaultsConLink extends DefaultsConAdv {
    private static final long serialVersionUID = -5410188010881472588L;

    private String css;

    @Column(name = "css", nullable = false, length = 5120)
    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    @Override
    @Transient
    public ContentType getContent_type() {
        return ContentType.LINK;
    }
}
