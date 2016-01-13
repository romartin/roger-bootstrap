package org.roger600.uberfire.testapp.client.widgets.viewer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
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

    
    @Inject
    MyProperty myProperty;

    @Inject
    MyOtherProperty myOtherProperty;

    @Inject
    MyDefinition myDefinition;
    
    /*
    
    @Inject
    @Named("myProperty")
    Property myProperty;

    @Inject
    @Named("myOtherProperty")
    Property myOtherProperty;
     */

    @Inject
    View view;

    public Viewer() {
    }

    @PostConstruct
    public void init() {
        view.init(this);
        GWT.log("myProperty: " + myProperty.getId() );
        GWT.log("myOtherProperty: " + myOtherProperty.getId() );
        GWT.log("myDefinition: " + myDefinition.getId() );
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }
    
}
