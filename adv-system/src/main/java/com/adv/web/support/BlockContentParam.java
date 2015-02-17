package com.adv.web.support;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface BlockContentParam {
    /**
     * name of request parameter with contentType.
     * defaults to "content_type".
     */
    String value() default "content_type";
}
