package org.roger600.uberfire.testapp.client.widgets.viewer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.roger600.uberfire.testapp.api.model.Definition;
import org.roger600.uberfire.testapp.api.model.RuntimeDefinition;
import org.roger600.uberfire.testapp.api.model.property.HasDefaultValue;
import org.roger600.uberfire.testapp.api.model.property.Property;
import org.roger600.uberfire.testapp.model.api.impl.MyDefinition;
import org.roger600.uberfire.testapp.model.api.impl.MyOtherProperty;
import org.roger600.uberfire.testapp.model.api.impl.MyProperty;
import org.uberfire.client.mvp.UberView;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

@Dependent
public class Viewer implements IsWidget {

    public interface View extends UberView<Viewer> {
        
    }

    
    /*@Inject
    MyProperty myProperty;

    @Inject
    MyOtherProperty myOtherProperty;

    @Inject
    MyDefinition myDefinition;*/
    
    
    @Inject
    @Named("myProperty")
    Property myProperty;

    @Inject
    @Named("myOtherProperty")
    Property myOtherProperty;

    @Inject
    @Named("myDefinition")
    Definition myDefinition;
    
    @Inject
    View view;

    public Viewer() {
    }

    @PostConstruct
    public void init() {
        view.init(this);
        GWT.log("myProperty Id: " + myProperty.getId() );
        GWT.log("myProperty DefaultValue: " + ( (HasDefaultValue<String>) myProperty).getDefaultValue() );
        GWT.log("myOtherProperty: " + myOtherProperty.getId() );
        GWT.log("myDefinition Id: " + myDefinition.getId() );
        GWT.log("myDefinition Properties Size: " + ( (RuntimeDefinition) myDefinition).getProperties().size() );
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }
    
}
