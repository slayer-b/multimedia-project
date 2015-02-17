
package com.adv.core.model;

import com.adv.core.types.ContentType;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Entity(name = "ContentLink")
@DynamicInsert(true)
@Table(name = "content_link")
@DiscriminatorValue("LINK")
public class ContentLink extends ContentAdv {
    private static final long serialVersionUID = -5444831558521213367L;

    private String css;

    @Column(name = "css", length = 5120)
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
