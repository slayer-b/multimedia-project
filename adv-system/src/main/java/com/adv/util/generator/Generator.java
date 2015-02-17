
package com.adv.util.generator;

import java.util.Random;

/**
 * Generates only digits + upper and down case letters.
 * @author demchuck.dima@gmail.com
 */
public class Generator implements IGenerator {
    /** size of a generated key. */
    private final int size;
    /** random digit generator for key. */
    private Random random = new Random();

    /**
     * create a new instance of Generator, which generates keys of a given size.
     * @param newSize length of generated keys
     */
    public Generator(final int newSize) {
        this.size = newSize;
    }

    /** table of allowed characters in an output sequence of this generator. */
    private char[] table = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    @Override
    public final String generate() {
        return generate(new StringBuilder()).toString();
    }

    @Override
    public final StringBuilder generate(final StringBuilder rez) {
        rez.setLength(size);
        for (int i = 0; i < size; i++) {
            rez.setCharAt(i, table[random.nextInt(table.length)]);
        }
        return rez;
    }
}
