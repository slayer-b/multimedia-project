package com.adv.util.generator;

/**
 * An interface for generating a key.
 * @author demchuck.dima@gmail.com
 */
public interface IGenerator {

    /**
     * Generate a key as a string.
     * @return a key
     */
    String generate();

    /**
     * Generate a key to a StringBuilder and returns it.
     * Is used for optimization, i.e. if you need to generate more then one key,
     * or check all generated keys.
     * @param rez string builder for optimization
     * @return a StringBuilder with required key
     */
    StringBuilder generate(StringBuilder rez);
}
