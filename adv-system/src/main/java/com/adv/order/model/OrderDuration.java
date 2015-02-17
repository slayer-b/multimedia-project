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
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * represents ordering duration. Paying for an advertisement to stay for a constant time.
 * @author demchuck.dima@gmail.com
 */
@Entity(name = "OrderDuration")
@DiscriminatorValue("DURATION")
@Table(name = "order_duration")
public class OrderDuration extends OrderUnits {
    private static final long serialVersionUID = -3035859815070105346L;
    private Long duration;

    /** in seconds. */
    @Column(name = "duration", nullable = false, updatable = false)
    public Long getDuration() {
        return duration;
    }
    /** in seconds. */
    public void setDuration(Long value) {
        this.duration = value;
    }

    @Override
    @Transient
    public PaymentAdvUnitsType getPaymentUnits() {
        return PaymentAdvUnitsType.DURATION;
    }
}
