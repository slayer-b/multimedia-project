
package com.adv.core.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Embeddable
public class ConAdvProps implements Serializable {
    private static final long serialVersionUID = -8801479818398984783L;

    private Integer advQuantity;
    private Integer maxPages;
    private Boolean navVisible;

    @Column(name = "adv_quantity", nullable = false)
    public Integer getAdvQuantity() {
        return advQuantity;
    }

    public void setAdvQuantity(Integer advQuantity) {
        this.advQuantity = advQuantity;
    }

    @Column(name = "max_pages", nullable = false)
    public Integer getMaxPages() {
        return maxPages;
    }

    public void setMaxPages(Integer maxPages) {
        this.maxPages = maxPages;
    }

    @Column(name = "nav_visible", nullable = false)
    public Boolean getNavVisible() {
        return navVisible;
    }

    public void setNavVisible(Boolean navVisible) {
        this.navVisible = navVisible;
    }

}
