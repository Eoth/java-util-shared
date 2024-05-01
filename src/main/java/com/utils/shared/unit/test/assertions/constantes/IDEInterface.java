package com.utils.shared.unit.test.assertions.constantes;

public class IDEInterface {
    public static final Character OPENING_CHARACTER = '[';
    public static final Character CLOSING_CHARACTER = ']';
    public static final String ASSERT_LEFT = "expected " + OPENING_CHARACTER;
    public static final String ASSERT_MIDDLE = CLOSING_CHARACTER + " but found " + OPENING_CHARACTER;
    public static final String ASSERT_RIGHT = Character.toString(CLOSING_CHARACTER);

}
