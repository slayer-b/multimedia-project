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
 * Represents ordering of views for advertising. Paying for each view(show).
 * @author demchuck.dima@gmail.com
 */
@Entity(name = "OrderViews")
@Table(name = "order_views")
@DiscriminatorValue("VIEWS")
public class OrderViews extends OrderUnits {
    private static final long serialVersionUID = -4210156870946429406L;
    private Long quantity;

    @Column(name = "quantity", nullable = false, updatable = false)
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long value) {
        this.quantity = value;
    }

    @Override
    @Transient
    public PaymentAdvUnitsType getPaymentUnits() {
        return PaymentAdvUnitsType.VIEWS;
    }
}
