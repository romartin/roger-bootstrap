package org.roger600.uberfire.testapp.model.api.impl;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.roger600.uberfire.testapp.api.model.annotation.property.Property;
import org.roger600.uberfire.testapp.api.model.annotation.property.PropertyName;

@Portable
@Property(identifier = "myProperty1")
public class MyProperty1 {
    
    @PropertyName
    public String getName() {
        return "My Property 1";
    }
    
}
