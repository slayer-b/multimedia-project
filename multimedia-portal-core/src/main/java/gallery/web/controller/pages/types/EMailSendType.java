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

package gallery.web.controller.pages.types;

import common.bind.ABindValidator;
import common.email.IMailService;
import common.utils.CommonAttributes;
import gallery.model.command.SendEmail;
import gallery.web.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public class EMailSendType extends ASingleContentType {
    /** string constant that represents type for this page */
    public static final String TYPE="system_send_email";
	/** rus type */
	public static final String TYPE_RU="---Обратная связь---";

	@Override
	public String getType() {return TYPE;}

	@Override
	public String getTypeRu() {return TYPE_RU;}

	@Autowired
	private IMailService emailServices;
	private ABindValidator validator;
	private String email_addresses;

	@Override
	public UrlBean execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException
	{
		SendEmail command = new SendEmail();
		BindingResult res =  validator.bindAndValidate(command, request);
		if (res.hasErrors()){
			request.setAttribute(BindingResult.MODEL_KEY_PREFIX+config.getContentDataAttribute(), res);
			CommonAttributes.addErrorMessage("form_errors", request);
		}else{
			command.setEmail_to(email_addresses);
			emailServices.postMail(Config.SITE_NAME+": "+command.getEmail_from(), command);
			CommonAttributes.addHelpMessage("operation_succeed", request);
		}
		request.setAttribute(config.getContentDataAttribute(), command);

		UrlBean url = new UrlBean();
		url.setContent(contentUrl);
		return url;
	}

	@Required
	public void setValidator(ABindValidator validator) {this.validator = validator;}
    @Required
	public void setEmail_addresses(String email_addresses) {this.email_addresses = email_addresses;}

}
