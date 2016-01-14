package org.roger600.uberfire.testapp.model.api.impl;

import org.roger600.uberfire.testapp.api.model.annotation.property.Property;
import org.roger600.uberfire.testapp.api.model.annotation.property.DefaultValue;

@Property(identifier = "myProperty", name = "My Property")
public interface MyProperty extends org.roger600.uberfire.testapp.api.model.property.Property {

    @DefaultValue
    String defaultValue = "My Property Default Value";

}
