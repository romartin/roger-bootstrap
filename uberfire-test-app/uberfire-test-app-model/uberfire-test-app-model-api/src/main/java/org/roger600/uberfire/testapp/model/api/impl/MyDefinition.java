package org.roger600.uberfire.testapp.model.api.impl;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.roger600.uberfire.testapp.api.model.annotation.definition.Definition;
import org.roger600.uberfire.testapp.api.model.annotation.definition.DefinitionName;

@Portable
@Definition(identifier = "myDefinition")
public class MyDefinition {
    
    @DefinitionName
    public String getName() {
        return "My Definition";
    }
    
}
