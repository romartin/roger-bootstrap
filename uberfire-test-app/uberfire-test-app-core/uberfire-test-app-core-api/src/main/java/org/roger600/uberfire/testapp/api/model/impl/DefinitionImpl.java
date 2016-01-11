package org.roger600.uberfire.testapp.api.model.impl;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.roger600.uberfire.testapp.api.model.Definition;

@Portable
public class DefinitionImpl implements Definition {
    
    private final String id;
    private final String name;

    public DefinitionImpl(@MapsTo("id") String id, 
                          @MapsTo("name") String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
    
}
