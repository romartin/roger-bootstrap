package org.roger600.uberfire.testapp.api.model.impl;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.roger600.uberfire.testapp.api.model.property.Property;

@Portable
public class PropertyImpl implements Property {
    
    private final String id;
    private final String name;

    public PropertyImpl(@MapsTo("id") String id,
                        @MapsTo("name") String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isMandatory() {
        return false;
    }

}
