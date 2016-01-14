package org.roger600.uberfire.testapp.api.model;

import org.roger600.uberfire.testapp.api.model.property.Property;

import java.util.List;

public interface RuntimeDefinition extends Definition {
    
    List<Property> getProperties();
    
}
