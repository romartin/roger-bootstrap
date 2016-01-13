package org.roger600.uberfire.testapp.model.api.impl;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.roger600.uberfire.testapp.api.model.annotation.definition.Definition;
import org.roger600.uberfire.testapp.api.model.annotation.definition.DefinitionName;

import javax.inject.Named;

@Definition(identifier = "myDefinition")
@Named("")
public class MyDefinition {
    
    @DefinitionName
    public String getName() {
        return "My Definition";
    }
    
}
