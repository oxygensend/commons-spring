package com.oxygensend.commonspring;

public class DefaultView {
    public final String description;

    private DefaultView(String description) {
        this.description = description;
    }

    public static DefaultView of(String description) {
        return new DefaultView(description);
    }

}
