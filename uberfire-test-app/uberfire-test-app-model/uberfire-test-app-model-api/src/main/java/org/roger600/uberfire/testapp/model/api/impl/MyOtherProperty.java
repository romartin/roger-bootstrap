package org.roger600.uberfire.testapp.model.api.impl;

import org.roger600.uberfire.testapp.api.model.annotation.property.DefaultValue;
import org.roger600.uberfire.testapp.api.model.annotation.property.Property;

@Property(identifier = "myOtherProperty", name = "My Other Property")
public interface MyOtherProperty extends org.roger600.uberfire.testapp.api.model.property.Property {
    
    @DefaultValue
    AValue defaultValue = new AValue("avId1","avValue1", new AnotherValue("ovId1","ovValue1"));
    
}
