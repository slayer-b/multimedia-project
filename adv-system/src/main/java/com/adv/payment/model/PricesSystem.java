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

package com.adv.payment.model;

import com.adv.core.types.PaymentAdvUnitsType;
import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * bean contains minimal prices for a system.
 * @author demchuck.dima@gmail.com
 */
@Entity
@Table(name = "system_prices")
@Cacheable(true)
@org.hibernate.annotations.Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="MinPricesDB")
public class PricesSystem implements Serializable {
    private static final long serialVersionUID = -2590015731818042206L;
    @Id
    private Long id;

    @Column(nullable = false)
    private Double clickCost;
    @Column(nullable = false)
    private Double viewCost;
    @Column(nullable = false)
    private Double supplantationCost;
    @Column(nullable = false)
    private Double durationCost;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentAdvUnitsType defaultUnits;

    public Long getId() {
        return id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public Double getClickCost() {
        return clickCost;
    }

    public void setClickCost(Double value) {
        this.clickCost = value;
    }

    public Double getViewCost() {
        return viewCost;
    }

    public void setViewCost(Double value) {
        this.viewCost = value;
    }

    public Double getSupplantationCost() {
        return supplantationCost;
    }

    public void setSupplantationCost(Double value) {
        this.supplantationCost = value;
    }

    public Double getDurationCost() {
        return durationCost;
    }

    public void setDurationCost(Double value) {
        this.durationCost = value;
    }

    /**
     * @return the defaultUnits
     */
    public PaymentAdvUnitsType getDefaultUnits() {
        return defaultUnits;
    }

    /**
     * @param defaultUnits the defaultUnits to set
     */
    public void setDefaultUnits(PaymentAdvUnitsType value) {
        this.defaultUnits = value;
    }
}
