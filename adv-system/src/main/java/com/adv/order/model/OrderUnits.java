/*
 * Copyright 2012 demchuck.dima@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.adv.order.model;

import com.adv.core.types.PaymentAdvUnitsType;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * represents ordering of units for advertisement. Payment units type for advertisements
 * @author demchuck.dima@gmail.com
 */
@Table(name = "order_units")
@Entity(name = "OrderUnits")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, length=60)
public abstract class OrderUnits implements Serializable {
    private static final long serialVersionUID = 4942181967801049446L;
    private Long id;
    private Date created;
    private Integer paymentNo;
    private Double price;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    /**
     * @return the created
     */
    @Column(name="created", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(Date value) {
        this.created = value;
    }

    @Column(name="payment_no", nullable=false, unique=true, updatable=false)
    public Integer getPaymentNo() {
        return paymentNo;
    }
    public void setPaymentNo(Integer value) {
        this.paymentNo = value;
    }

    @Column(name="price", nullable=false, updatable=false)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double value) {
        this.price = value;
    }

    @Transient
    public abstract PaymentAdvUnitsType getPaymentUnits();
}