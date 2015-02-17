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

package common.utils;

import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author demchuck.dima@gmail.com
 */
public final class FileUtils {

    private FileUtils() {
    }

    public static final String DEFAULT_CHAR = "_";

    private static final Character[] PATTERN = {
            '_', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's',
            'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm',
            '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
            'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'к', 'л', 'м', 'н', 'о',
            'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'y',
            'э', 'ю', 'я', 'ы',
            '@', '^', ':', '.', ',', ';', '!', '(', ')', '-', '[', ']'};
    private static final String[] REPLACEMENT = {
            "_", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s",
            "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
            "a", "b", "v", "g", "d", "e", "j", "z", "i", "k", "l", "m", "n", "o",
            "p", "r", "s", "t", "u", "f", "h", "c", "ch", "sh", "sh", "y",
            "ye", "yu", "ya", "i",
            "@", "^", ":", ".", ",", ";", "!", "(", ")", "-", "[", "]"};

    private static final Map<Character, String> DICTIONARY = new Hashtable<Character, String>();

    static {
        for (int i = 0; i < PATTERN.length; i++) {
            DICTIONARY.put(PATTERN[i], REPLACEMENT[i]);
        }
    }

    /**
     * Проверяет правописание и производит коррекцию. Переводит все символы
     * в нижний регистр, убирает пробелы слева и справа, заменяет кирилицу, и символы которые
     * не входят в шаблон на "_".
     */
    public static String toTranslit(String value) {
        //2-nd prepare string (lowercase, trim)
        String temp = value.trim().toLowerCase();
        //replacing
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < temp.length(); i++) {
            Character c = temp.charAt(i);
            String s = DICTIONARY.get(c);
            if (s == null) {
                sb.append(DEFAULT_CHAR);
            } else {
                sb.append(s);
            }
        }
        return sb.toString();
    }

    /**
     * Проверяет правописание в названии файла и производит коррекцию. Переводит все символы
     * в нижний регистр, убирает пробелы слева и справа, заменяет кирилицу, и символы которые
     * не входят в шаблон на "_".
     *
     * @param fileName имя файла, or path
     * @return новое имя файла, excluding any path separators if present
     */
    public static String checkFileNameSpelling(String fileName) {
        //1-st excluding path if any
        String newFileName = fileName;
        int val = newFileName.lastIndexOf(File.separator);
        if (val >= 0) {
            newFileName = newFileName.substring(val + 1);
        }
        return toTranslit(newFileName);
    }

    /**
     * deletes all folder's content
     * if it is a folder deletes all sub folders, subdirectories ...
     * if it is file delete it
     *
     * @param delSelf true if delete also given file if it is a directory
     */
    public static boolean deleteFiles(File f, boolean delSelf) {
        boolean rez = true;
        if (f.exists()) {
            if (f.isDirectory()) {
                File[] files = f.listFiles();
                for (File file : files) {
                    rez = deleteFiles(file, true) & rez;
                }
                if (delSelf) {
                    rez = f.delete() & rez;
                }
            } else {
                rez = f.delete() & rez;
            }
        }
        return rez;
    }

    /**
     * If resource points to an existing directory do nothing.
     * Else create a directory for given resource.
     *
     * @return true if succeed
     */
    public static String createDirectory(Resource resource) throws IOException {
        File f = resource.getFile();
        if (f.exists()) {
            if (f.isFile() && (!f.delete() || !f.mkdir())) {
                throw new FileNotFoundException(f.getCanonicalPath());
            }
        } else {
            if (!f.mkdirs()) {
                throw new FileNotFoundException(f.getCanonicalPath());
            }
        }
        assert f.exists() && f.isDirectory();
        return f.getCanonicalPath();
    }

    /**
     * If resource points to an existing directory do nothing.
     * Else create a directory for given resource.
     *
     * @param errMsg error message to be added to thrown exception
     * @return true if succeed
     */
    public static String createDirectory(Resource resource, String errMsg) {
        try {
            return createDirectory(resource);
        } catch (IOException e) {
            throw new RuntimeException(errMsg, e);
        }
    }

}
