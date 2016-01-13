package org.roger600.uberfire.testapp.client.widgets.viewer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

/**
 * @see <a>http://www.gwtproject.org/javadoc/latest/com/google/gwt/user/client/ui/Tree.html</a>
 */
public class ViewerView extends Composite implements Viewer.View {

    interface ViewBinder extends UiBinder<Widget, ViewerView> {

    }

    private static ViewBinder uiBinder = GWT.create( ViewBinder.class );
    
    Viewer presenter;

    @UiField
    Tree tree;
    
    @Override
    public void init(Viewer presenter) {
        this.presenter = presenter;
        initWidget( uiBinder.createAndBindUi( this ) );
        doInit();
    }
    
    private void doInit() {
        
        TreeItem root = new TreeItem();
        root.setText("root");
        root.addTextItem("item0");
        root.addTextItem("item1");
        root.addTextItem("item2");

        // Add a CheckBox to the tree
        TreeItem item = new TreeItem(new CheckBox("item3"));
        root.addItem(item);

        tree.addItem(root);
    }

}
