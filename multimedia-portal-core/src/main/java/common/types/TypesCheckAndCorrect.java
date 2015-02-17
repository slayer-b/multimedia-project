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

package common.types;

import common.types.typesCheck.classes.DateChecker;
import common.types.typesCheck.classes.EmailChecker;
import common.types.typesCheck.classes.HtmlChecker;
import common.types.typesCheck.classes.ITypeChecker;
import common.types.typesCheck.classes.IntArrayChecker;
import common.types.typesCheck.classes.PhoneChecker;
import common.types.typesCheck.classes.TextChecker;
import common.types.typesCheck.classes.UrlChecker;
import common.types.typesCorrect.classes.HtmlCorrector;
import common.types.typesCorrect.classes.ITypeCorrector;
import common.types.typesCorrect.classes.TextCorrector;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author demchuck.dima@gmail.com
 */
public final class TypesCheckAndCorrect {
    private final Map<String, ITypeChecker> check = new Hashtable<String, ITypeChecker>();
    private final Map<String, ITypeCorrector> correct = new Hashtable<String, ITypeCorrector>();

    public TypesCheckAndCorrect() {
        //checkers
        check.put("email", new EmailChecker());
        check.put("phone", new PhoneChecker());
        check.put("text", new TextChecker());
        check.put("html", new HtmlChecker());
        check.put("url", new UrlChecker());
        check.put("date", new DateChecker());
        check.put("intArray", new IntArrayChecker());
        //correctors
        correct.put("text", new TextCorrector());
        correct.put("html", new HtmlCorrector());
    }

    public ITypeChecker getChecker(String name) {
        return check.get(name);
    }

    public ITypeCorrector getCorrecter(String name) {
        return correct.get(name);
    }

}
