package com.gwtfb.client;


import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Displays links on the left siden on the HomeView page
 * @author ola
 */
public class HomeSideBarPanel extends Composite {
    
    VerticalPanel linkPanel = new VerticalPanel ();
    
    public HomeSideBarPanel () {
        linkPanel.getElement().setId("SideBarPanel");
        linkPanel.add ( new HTML ( "<h3>Methods</h3>" ) );
        linkPanel.add( new Hyperlink ( "Stream Publish", "example/stream.publish" ) );
        linkPanel.add ( new Hyperlink ( "Friends", "example/friends" ) );
        linkPanel.add ( new Hyperlink ( "FqlQuery", "example/fql.query" ));
        initWidget ( linkPanel );
    }

}
