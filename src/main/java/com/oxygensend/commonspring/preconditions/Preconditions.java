package com.oxygensend.commonspring.preconditions;

public class Preconditions {
    private Preconditions() {
    }

    public static void performActionIfNull(Object reference, Runnable action) {
        if (reference == null) {
            action.run();
        }
    }

    public static void checkNotNull(Object reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
    }

    public static void checkNotNull(Object reference, String errorMessage) {
        if (reference == null) {
            throw new NullPointerException(errorMessage);
        }
    }

    public static void checkArgument(boolean expression, String errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(errorMessage);
        }
    }


}
