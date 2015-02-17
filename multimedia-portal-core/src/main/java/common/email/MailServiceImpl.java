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

package common.email;

import common.utils.FileUtils;
import gallery.model.command.SendEmail;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

/**
 * @author demchuck.dima@gmail.com
 */
public class MailServiceImpl implements IMailService {
    private final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    private final JavaMailSender mailSender;
    private final String templatePath;

    public MailServiceImpl(JavaMailSender mailSender, Resource templatePath) {
        this.mailSender = mailSender;
        this.templatePath = FileUtils.createDirectory(templatePath, "Path to templates not found.");
    }

    /**
     * this method sends email using a given template
     *
     * @param subject        subject of a mail
     * @param recipientEmail email receiver address
     * @param mail           mail text to send
     * @param from           will be set
     * @return true if send succeed
     */
    protected boolean postMail(String subject, String recipientEmail, String from, String mail)
            throws IOException {
        SimpleMailMessage message = new SimpleMailMessage();
        if (from != null) {
            message.setFrom(from);
        }
        message.setTo(recipientEmail);
        message.setSubject(subject);
        message.setSentDate(new Date());
        message.setText(mail);
        mailSender.send(message);
        return true;
    }

    /**
     * this method loads email template into StringBuilder
     * this method sends email using a given template
     *
     * @param params         names of parameters to replace in template
     * @param values         values by which to replace names
     * @param pathToTemplate path to template for email message
     * @return stringBuilder this email
     */
    protected static StringBuilder getMailMsg(
            List<String> params,
            List<String> values,
            String pathToTemplate)
            throws IOException {
        String line;
        StringBuilder rez = new StringBuilder();
        if (params != null && values != null) {
            BufferedReader read = null;
            try {
                read = new BufferedReader(new InputStreamReader(new FileInputStream(pathToTemplate), "UTF-8"));
                while ((line = read.readLine()) != null) {
                    for (int i = 0; i < params.size(); i++) {
                        line = line.replaceAll(params.get(i), values.get(i));
                    }
                    rez.append(line).append('\n');
                }
            } catch (IOException e) {
                throw new IOException("Failed to read mail template.", e);
            } finally {
                IOUtils.closeQuietly(read);
            }
        } else {
            BufferedReader read = null;
            try {
                read = new BufferedReader(new InputStreamReader(new FileInputStream(pathToTemplate), "UTF-8"));
                while ((line = read.readLine()) != null) {
                    rez.append(line).append('\n');
                }
            } catch (IOException e) {
                throw new IOException("Failed to read mail template.", e);
            } finally {
                IOUtils.closeQuietly(read);
            }
        }
        return rez;
    }

    /**
     * this method loads email template into StringBuilder
     * this method sends email using a given template
     *
     * @param params         names of parameters to replace in template
     * @param values         values by which to replace names
     * @param pathToTemplate path to template for email message
     * @return stringBuilder this email
     */
    protected static StringBuilder getMailMsg(
            String[] params,
            String[] values,
            String pathToTemplate)
            throws IOException {
        String line;
        StringBuilder rez = new StringBuilder();
        if (params != null && values != null) {
            BufferedReader read = null;
            try {
                read = new BufferedReader(new InputStreamReader(new FileInputStream(pathToTemplate), "UTF-8"));
                while ((line = read.readLine()) != null) {
                    for (int i = 0; i < params.length; i++) {
                        line = line.replaceAll(params[i], values[i]);
                    }
                    rez.append(line).append('\n');
                }
            } catch (IOException e) {
                throw new IOException("Failed to read mail template.", e);
            } finally {
                IOUtils.closeQuietly(read);
            }
        } else {
            BufferedReader read = null;
            try {
                read = new BufferedReader(new InputStreamReader(new FileInputStream(pathToTemplate), "UTF-8"));
                while ((line = read.readLine()) != null) {
                    rez.append(line).append('\n');
                }
                read.close();
            } catch (IOException e) {
                throw new IOException("Failed to read mail template.", e);
            } finally {
                IOUtils.closeQuietly(read);
            }
        }
        return rez;
    }


    /**
     * this method loads email template into StringBuilder
     * this method sends email using a given template
     *
     * @param subject        subject of a mail
     * @param recipientEmail email receiver address
     * @param params         names of parameters to replace in template
     * @param values         values by which to replace names
     * @param from           will be set
     * @param pathToTemplate path to template for email message
     * @return stringBuilder this email
     */
    @Override
    public boolean postMail(
            String subject,
            String recipientEmail,
            String from,
            List<String> params,
            List<String> values,
            String pathToTemplate)
            throws IOException {
        StringBuilder m = getMailMsg(params, values, templatePath + pathToTemplate);
        return postMail(subject, recipientEmail, from, m.toString());
    }


    /**
     * this method loads email template into StringBuilder
     * this method sends email using a given template
     *
     * @param subject        subject of a mail
     * @param recipientEmail email receiver address
     * @param params         names of parameters to replace in template
     * @param values         values by which to replace names
     * @param from           will be set
     * @param pathToTemplate path to template for email message
     * @return stringBuilder this email
     */
    @Override
    public boolean postMail(
            String subject,
            String recipientEmail,
            String from,
            String[] params,
            String[] values,
            String pathToTemplate)
            throws IOException {
        StringBuilder m = getMailMsg(params, values, templatePath + pathToTemplate);
        return postMail(subject, recipientEmail, from, m.toString());
    }

    @Override
    public String getAutoanswerEmail() {
        return "admin@download-multimedia.com";
    }

    @Override
    public boolean postMail(String subject, SendEmail command)
            throws IOException {
        return postMail(subject, command.getEmail_to(), getAutoanswerEmail(), command.getText());
    }
}
