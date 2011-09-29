package com.gwtfb.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

/**
 * Display Top Menu
 * @author ola
 */
public class TopMenuPanel extends Composite {

	private HorizontalPanel outer = new HorizontalPanel ();
	
	public TopMenuPanel () {
	    AppImageBundle images = GWT.create( AppImageBundle.class);
	    
		outer.getElement().setId("TopMenu");
		outer.add ( new Image ( images.logo() ) );
        outer.add ( new HTML ( "<div style='margin-top: 2px; float: right;'><fb:login-button autologoutlink='true' perms='publish_stream,read_stream' /> </div>" ) );
        
        initWidget ( outer );
	}
	
	
}
