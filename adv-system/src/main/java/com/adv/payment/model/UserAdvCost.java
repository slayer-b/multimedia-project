
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
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * User cost for payment units.
 * @author Dmitriy_Demchuk
 */
@Embeddable
public class UserAdvCost implements Serializable {
    private static final long serialVersionUID = 2984198848681719606L;
    /** default unit type to use for this user. */
    private PaymentAdvUnitsType defaultUnit;
    /** cost of a click. */
    private Double clickCost;
    /** cost of a millisecond. */
    private Double durationCost;
    /** cost before it gets supplanted by another one. */
    private Double supplantationCost;
    /** cost of one view. */
    private Double viewCost;

    /**
     * cost of a click.
     * @return the clickCost
     */
    @Column(name = "click_cost", nullable = false)
    public Double getClickCost() {
        return clickCost;
    }

    /**
     * cost of a click.
     * @param clickCost the clickCost to set
     */
    public void setClickCost(Double value) {
        this.clickCost = value;
    }

    /**
     * cost of a millisecond.
     * @return the durationCost
     */
    @Column(name = "duration_cost", nullable = false)
    public Double getDurationCost() {
        return durationCost;
    }

    /**
     * cost of a millisecond.
     * @param durationCost the durationCost to set
     */
    public void setDurationCost(Double value) {
        this.durationCost = value;
    }

    /**
     * cost before it gets supplanted by another one.
     * @return the supplantationCost
     */
    @Column(name = "supplantation_cost", nullable = false)
    public Double getSupplantationCost() {
        return supplantationCost;
    }

    /**
     * cost before it gets supplanted by another one.
     * @param supplantationCost the supplantationCost to set
     */
    public void setSupplantationCost(Double value) {
        this.supplantationCost = value;
    }

    /**
     * cost of one view.
     * @return the viewCost
     */
    @Column(name = "view_cost", nullable = false)
    public Double getViewCost() {
        return viewCost;
    }

    /**
     * cost of one view.
     * @param viewCost the viewCost to set
     */
    public void setViewCost(Double value) {
        this.viewCost = value;
    }

    /**
     * @return the defaultUnit
     */
    @Column(name = "payment_type", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    public PaymentAdvUnitsType getDefaultUnit() {
        return defaultUnit;
    }

    /**
     * @param defaultUnit the defaultUnit to set
     */
    public void setDefaultUnit(PaymentAdvUnitsType value) {
        this.defaultUnit = value;
    }
}
