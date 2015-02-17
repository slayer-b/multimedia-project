package com.adv.core.model;

import com.adv.payment.model.BlockAdvCost;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Index;

/**
 * config of a block
 * block of advertisements
 * @author demchuck.dima@gmail.com
 */
@Entity(name = "Block")
@Table(name = "block")
public class Block implements Serializable {
    private static final long serialVersionUID = -9031724726663999369L;

    private Long id;
    private Long id_user;

    private User user;

    private String name;

    private String pub_id;

    private BlockAdvCost advCost;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "id_user", nullable = false)
    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    @ManyToOne(optional = false, targetEntity = com.adv.core.model.User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "pub_id", nullable = false, unique = true, updatable = false, length = 255)
    @Index(name = "block_pub_id")
    public String getPub_id() {
        return pub_id;
    }

    public void setPub_id(String pub_id) {
        this.pub_id = pub_id;
    }

    @Column(name = "name", length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Embedded
    public BlockAdvCost getAdvCost() {
        return advCost;
    }

    public void setAdvCost(BlockAdvCost advCost) {
        this.advCost = advCost;
    }

}
