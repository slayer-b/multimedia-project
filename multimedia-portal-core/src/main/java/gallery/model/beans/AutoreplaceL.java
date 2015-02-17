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

package gallery.model.beans;

import com.netstorm.localization.LocalizedBean;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Entity
@Table(name = "autoreplace_loc")
public class AutoreplaceL extends LocalizedBean<Autoreplace> implements Serializable {
    private static final long serialVersionUID = 7910608311466760325L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "lang", length = 50, nullable = false)
	private String lang;
    @Column(name = "text", length = 255, nullable = false)
	private String text;

	public String getLang() {return lang;}
	public void setLang(String lang) {this.lang = lang;}

	public String getText() {return text;}
	public void setText(String text) {this.text = text;}

	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	@Override
	public String toString() {
		return getClass().getName()+'('+(id==null?"":id.toString())+"), ("+(getLocaleParent()==null?"":getLocaleParent().toString())+"), text = "+text;
	}

}
