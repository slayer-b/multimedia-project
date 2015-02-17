/*
 *  Copyright 2010 demchuck.dima@gmail.com
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
package common.utils;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public final class StringUtils {

    /** its an utility class. */
    private StringUtils() {
    }

    /**
     * makes first letter of a string an upper case letter(capital letter).
     * @param s value
     * @return the string with first letter upper case
     */
    public static String firstLetterUpperCase(String s) {
        char[] str = new char[s.length()];
        s.getChars(0, s.length(), str, 0);
        str[0] = Character.toUpperCase(str[0]);
        return String.copyValueOf(str);
    }

    /**
     * return name of getter like get+PropertyName.
     * @param propertyName name of property
     * @return getter name
     */
    public static String getterNameForProperty(String propertyName) {
        char[] str = new char[propertyName.length()];
        propertyName.getChars(0, propertyName.length(), str, 0);
        str[0] = Character.toUpperCase(str[0]);
        return "get" + String.copyValueOf(str);
    }

    /**
     * return name of setter like set+PropertyName.
     * @param propertyName name of property
     * @return getter name
     */
    public static String setterNameForProperty(String propertyName) {
        char[] str = new char[propertyName.length()];
        propertyName.getChars(0, propertyName.length(), str, 0);
        str[0] = Character.toUpperCase(str[0]);
        return "set" + String.copyValueOf(str);
    }

    /**
     * converts String to long and returns null if any fail.
     * @param value value to convert
     * @return long or null if cannot parse
     */
    public static Long getLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * converts String to double and returns null if any fail.
     * @param value value to convert
     * @return double or null if cannot parse
     */
    public static Double getDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * converts String to long and returns null if any fail.
     * @param value value to convert
     * @return integer or null if cannot parse
     */
    public static Integer getInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return null;
        }
    }
}
