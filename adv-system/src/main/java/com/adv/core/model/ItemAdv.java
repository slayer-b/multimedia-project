package com.adv.core.model;

import com.adv.core.types.PaymentMethod;
import com.adv.order.model.OrderUnits;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * An advertisement item to be put into a block location.
 * @author Dmitriy_Demchuk
 */
@Entity(name = "ItemAdv")
@Table(name = "item_adv")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, length=60)
public abstract class ItemAdv implements Serializable {
    private static final long serialVersionUID = 7890267932125649806L;

    private Long id;
    //TODO: mb move some methods to the superclass
    private Date date_paid;
    /** indicates whether this link is paid */
    private Boolean paid;
    /** order for this link */
    private OrderUnits order;
    /** payment method used to pay for this advertisement */
    private PaymentMethod paymentMethod;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the date_paid
     */
    @Column(name = "date_paid", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getDate_paid() {
        return date_paid;
    }

    /**
     * @param date_paid the date_paid to set
     */
    public void setDate_paid(Date date_paid) {
        this.date_paid = date_paid;
    }

    /**
     * indicates whether this link is paid
     * @return the paid
     */
    @Column(name = "paid", nullable = false)
    public Boolean getPaid() {
        return paid;
    }

    /**
     * indicates whether this link is paid
     * @param paid the paid to set
     */
    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    /**
     * order for this link
     * @return the order
     */
    @OneToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "item_link_order")
    public OrderUnits getOrder() {
        return order;
    }

    /**
     * order for this link
     * @param order the order to set
     */
    public void setOrder(OrderUnits order) {
        this.order = order;
    }

    @Column(name = "payment_method", nullable = true)
    @Enumerated(EnumType.STRING)
    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}
