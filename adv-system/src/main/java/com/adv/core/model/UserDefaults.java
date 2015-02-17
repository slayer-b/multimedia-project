package com.adv.core.model;

import com.adv.payment.model.UserAdvCost;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Entity(name = "UserDefaults")
@Table(name = "user_defaults")
public class UserDefaults implements Serializable {
    private static final long serialVersionUID = 761562428589522857L;

    private Long id;

    private User user;

    private DefaultsContent defaultsContent;

    private UserAdvCost advCost;
    /** create a new block if nothing is found for this site. */
    private Boolean useDefaults;

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @MapsId
    @OneToOne(cascade = {}, targetEntity = com.adv.core.model.User.class)
    @JoinColumn(name = "id_user")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    public DefaultsContent getDefaultsContent() {
        return defaultsContent;
    }

    public void setDefaultsContent(DefaultsContent defaultsContent) {
        this.defaultsContent = defaultsContent;
    }

    @Embedded
    public UserAdvCost getAdvCost() {
        return advCost;
    }

    public void setAdvCost(UserAdvCost userAdvPayment) {
        this.advCost = userAdvPayment;
    }

    /**
     * create a new block if nothing is found for this site.
     * @return the useDefaults
     */
    @Column(name = "use_defaults", nullable = false)
    public Boolean getUseDefaults() {
        return useDefaults;
    }

    /**
     * create a new block if nothing is found for this site.
     * @param useDefaults the useDefaults to set
     */
    public void setUseDefaults(Boolean useDefaults) {
        this.useDefaults = useDefaults;
    }

}
