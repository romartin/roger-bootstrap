package org.roger600.uberfire.testapp.model.api.impl;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.roger600.uberfire.testapp.api.model.annotation.property.Property;
import org.roger600.uberfire.testapp.api.model.annotation.property.PropertyName;

@Property(identifier = "myProperty2")
public class MyProperty2 {
    
    @PropertyName
    public String getName() {
        return "My Property 2";
    }
    
}
