package com.gwtfb.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DocSideBarPanel extends Composite {

    private VerticalPanel outer = new VerticalPanel ();
    
    
    public DocSideBarPanel () {
        outer.getElement().setId( "SideBarPanel");
        outer.add ( new HTML ( "This is a public wave to let you ask question and read docs. As long as wave lives we'll keep it here " ) );
        initWidget(outer);
    }
}
