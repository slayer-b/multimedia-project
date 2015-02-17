/*
 *  Copyright 2010 demchuck.dima@gmail.com.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package test.annotations.formatter;

import test.annotations.HtmlSpecialChars;
import java.util.HashSet;
import java.util.Set;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public class HtmlSpecialCharsFormatterFactory implements AnnotationFormatterFactory<HtmlSpecialChars> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<Class<?>>(java.util.Arrays.asList(new Class<?>[]{String.class}));
    }

    @Override
    public Printer<String> getPrinter(HtmlSpecialChars annotation, Class<?> fieldType) {
        return configureFormatterFrom();
    }

    @Override
    public Parser<String> getParser(HtmlSpecialChars annotation, Class<?> fieldType) {
        return configureFormatterFrom();
    }

    /**
     * get new HtmlSpecialCharsFormatter.
     * @return new HtmlSpecialCharsFormatter object
     */
    private Formatter<String> configureFormatterFrom() {
        return new HtmlSpecialCharsFormatter();
    }
}
