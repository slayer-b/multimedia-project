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
package common.beans;

import java.io.Serializable;

/**
 * @author demchuck.dima@gmail.com
 */
public class AntispamBean implements Serializable {
    private static final long serialVersionUID = 2121942119095456155L;

    /**
     * quantity of unsuccessful anti-spam made
     */
    private long attempts = 0;
    /**
     * quantity of successfully entered anti-spam codes
     */
    private long count = 0;
    /**
     * datetime of next anti-spam enter
     */
    private long timeSpam = 0;
    /**
     * right answer on anti-spam code
     */
    private String result;

    /**
     * quantity of unsuccessful attempts made
     *
     * @return the attempts
     */
    public long getAttempts() {
        return attempts;
    }

    /**
     * quantity of successfully entered anti-spam codes
     *
     * @return the count
     */
    public long getCount() {
        return count;
    }

    /**
     * datetime of next anti-spam enter
     *
     * @return the time_spam
     */
    public long getTime_spam() {
        return timeSpam;
    }

    /**
     * datetime of next anti-spam enter
     *
     * @param time_spam the time_spam to set
     */
    public void setTime_spam(long time_spam) {
        this.timeSpam = time_spam;
    }

    /**
     * right answer on anti-spam code
     *
     * @return the result
     */
    public String getResult() {
        return result;
    }

    /**
     * right answer on anti-spam code
     *
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * increases quantity of failed attempts by 1
     */
    public void increaseAttempts() {
        attempts++;
    }

    /**
     * sets quantity of made attempts to 0
     */
    public void resetAttempts() {
        attempts = 0;
    }

    /**
     * increases quantity of successful attempts by 1
     */
    public void increaseCount() {
        count++;
    }

    /**
     * decreases quantity of successful attempts to 0
     */
    public void resetCount() {
        count = (count > 0) ? count-- : 0;
    }
}
