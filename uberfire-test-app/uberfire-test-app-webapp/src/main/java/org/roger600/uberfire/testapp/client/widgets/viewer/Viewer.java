package org.roger600.uberfire.testapp.client.widgets.viewer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.roger600.uberfire.testapp.model.api.impl.MyProperty;
import org.uberfire.client.mvp.UberView;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class Viewer implements IsWidget {

    public interface View extends UberView<Viewer> {
        
    }

    MyProperty myProperty;
    View view;

    @Inject
    public Viewer(View view, MyProperty myProperty) {
        this.view = view;
        this.myProperty = myProperty;
    }

    @PostConstruct
    public void init() {
        view.init(this);
        GWT.log("property1Property: " + myProperty.getId() );
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }
    
}
