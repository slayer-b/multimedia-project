package com.adv.core.model;

import com.adv.core.types.LocationType;
import java.io.Serializable;
import javax.persistence.CascadeType;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Entity(name = "BlockLocation")
@Table(name = "block_location")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "location_type", discriminatorType = DiscriminatorType.STRING, length=60)
public abstract class BlockLocation implements Serializable {
    private static final long serialVersionUID = 7713542475229707950L;

    private Long id;
    private Block block;
    private BlockContent blockContent;

    //TODO: mb delete
    /** stats for current location */
    private Stats stats;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_block_location", updatable = false, nullable = false)
    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_location_content")
    public BlockContent getBlockContent() {
        return blockContent;
    }

    public void setBlockContent(BlockContent blockContent) {
        this.blockContent = blockContent;
    }

    @Transient
    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    @Transient
    public abstract LocationType getLocation_type();

}