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
@Entity(name = "LocationSite")
@Table(name = "location_site")
@DynamicInsert(true)
@DiscriminatorValue("SITE")
public class LocationSite extends BlockLocation {
    private static final long serialVersionUID = -526350400682955336L;

    private String siteUrl;

    @Column(name = "site_url", length = 5120)
    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String url) {
        this.siteUrl = url;
    }

    @Override
    @Transient
    public LocationType getLocation_type() {
        return LocationType.SITE;
    }

}
