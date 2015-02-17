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

package core.model.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author demchuck.dima@gmail.com
 */
@Entity
@Table(name = "site_config")
public class SiteConfig implements Serializable {
    private static final long serialVersionUID = 1037329467770054098L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "optimization_phrase", columnDefinition = "TEXT")
    private String optimization_phrase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptimization_phrase() {
        return optimization_phrase;
    }

    public void setOptimization_phrase(String value) {
        this.optimization_phrase = value;
    }
}
