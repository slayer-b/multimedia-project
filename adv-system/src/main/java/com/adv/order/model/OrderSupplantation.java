
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
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * represents ordering of supplantation. Paying once for an advertisement, until it will be supplanted by another advertisement
 * @author demchuck.dima@gmail.com
 */
@Entity(name = "OrderSupplantation")
@DiscriminatorValue("SUPPLANTATION")
@Table(name = "order_supplantation")
public class OrderSupplantation extends OrderUnits {

    private static final long serialVersionUID = 8869171199621123227L;

    @Override
    @Transient
    public PaymentAdvUnitsType getPaymentUnits() {
        return PaymentAdvUnitsType.SUPPLANTATION;
    }

}
