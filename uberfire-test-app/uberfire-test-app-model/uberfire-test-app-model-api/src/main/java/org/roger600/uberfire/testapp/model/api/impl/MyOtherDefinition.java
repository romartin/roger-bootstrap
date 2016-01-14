package org.roger600.uberfire.testapp.model.api.impl;

import org.roger600.uberfire.testapp.api.model.annotation.definition.Definition;
import org.roger600.uberfire.testapp.api.model.annotation.definition.IsProperty;
import org.roger600.uberfire.testapp.api.model.annotation.rule.ContainmentRule;

@Definition(identifier = "myOtherDefinition", name = "My Other Definition")
@ContainmentRule(identifier = "myOtherDefinitionContainmentRule", 
                role = "myOtherDefinition", 
                permittedRoles = {"myOtherDefinitionPermittedRole1",
                                    "myOtherDefinitionPermittedRole2"} )
public interface MyOtherDefinition extends org.roger600.uberfire.testapp.api.model.Definition {
    
    @IsProperty
    MyProperty myProperty();

    @IsProperty
    MyOtherProperty myOtherProperty();
    
}
