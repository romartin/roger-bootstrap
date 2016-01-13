package org.roger600.uberfire.testapp.model.api.impl;

import org.roger600.uberfire.testapp.api.model.annotation.definition.Definition;
import org.roger600.uberfire.testapp.api.model.annotation.definition.IsProperty;

@Definition(identifier = "myDefinition", name = "My Definition")
public interface MyDefinition extends org.roger600.uberfire.testapp.api.model.Definition {
    
    @IsProperty
    MyProperty getMyProperty();
    
}
