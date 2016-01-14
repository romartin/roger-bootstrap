package org.roger600.uberfire.testapp.processor;

public class ProcessingElement {

    private final String propertyClassName;
    private final String methodName;

    public ProcessingElement(String propertyClassName, String methodName) {
        this.propertyClassName = propertyClassName;
        this.methodName = methodName;
    }

    public String getPropertyClassName() {
        return propertyClassName;
    }

    public String getMethodName() {
        return methodName;
    }
    
}
