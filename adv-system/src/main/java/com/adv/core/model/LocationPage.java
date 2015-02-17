package com.adv.core.model;

import com.adv.core.types.LocationType;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author demchuck.dima@gmail.com
 */
@Entity(name = "LocationPage")
@Table(name = "location_page")
@DynamicInsert(true)
@DiscriminatorValue("PAGE")
public class LocationPage extends BlockLocation {
    private static final long serialVersionUID = -4712055238928232279L;

    private String pageUrl;

    @Column(name = "page_url", length = 5120)
    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String url) {
        this.pageUrl = url;
    }

    @Override
    @Transient
    public LocationType getLocation_type() {
        return LocationType.PAGE;
    }

}
