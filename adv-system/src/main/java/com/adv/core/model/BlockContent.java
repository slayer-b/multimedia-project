
package com.adv.core.model;

import com.adv.core.types.ContentType;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Entity(name = "BlockContent")
@Table(name = "block_content")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "content_type", discriminatorType = DiscriminatorType.STRING)
public abstract class BlockContent implements Serializable {
    private static final long serialVersionUID = 5152807899494982067L;

    private Long id;

    private String width;
    private String height;

    private BlockLocation blockLocation;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "width", length = 10)
    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    @Column(name = "height", length = 10)
    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @OneToOne(cascade = {}, fetch = FetchType.LAZY, optional = false, mappedBy = "blockContent")
    @JoinColumn(name = "FK_content_location")
    public BlockLocation getBlockLocation() {
        return blockLocation;
    }

    public void setBlockLocation(BlockLocation blockLocation) {
        this.blockLocation = blockLocation;
    }

    @Transient
    public abstract ContentType getContent_type();

}
