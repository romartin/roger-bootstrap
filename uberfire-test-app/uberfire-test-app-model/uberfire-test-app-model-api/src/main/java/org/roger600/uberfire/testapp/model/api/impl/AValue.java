package org.roger600.uberfire.testapp.model.api.impl;

public class AValue {
    
    private final String id;
    private final String value;
    private final AnotherValue anotherValue;

    public AValue(String id, String value, AnotherValue anotherValue) {
        this.id = id;
        this.value = value;
        this.anotherValue = anotherValue;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public AnotherValue getAnotherValue() {
        return anotherValue;
    }
}
