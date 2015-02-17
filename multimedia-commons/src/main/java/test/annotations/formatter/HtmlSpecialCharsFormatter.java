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
package test.annotations.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author demchuck.dima@gmail.com
 */
public class HtmlSpecialCharsFormatter implements Formatter<String> {
    //characters to be replaced

    private static final String[] SPEC_CHARS = {"&", ">", "<", "\"", "'"};
    /**
     * characters will be replaced by these characters
     */
    private static final String[] REPLACE_BY = {"&amp;", "&gt;", "&lt;", "&quot;", "&#039;"};

    //protected static Logger logger = Logger.getLogger(HtmlSpecialCharsFormatter.class);
    @Override
    public String print(String object, Locale locale) {
        //logger.info("print");
        return object;
    }

    @Override
    public String parse(String text, Locale locale) throws ParseException {
        //logger.info("parse value ["+text+"]");
        String res = text;
        for (int i = 0; i < SPEC_CHARS.length; i++) {
            res = res.replaceAll(SPEC_CHARS[i], REPLACE_BY[i]);
        }
        //logger.info("parsed value ["+res+"]");
        return res;
    }
}
