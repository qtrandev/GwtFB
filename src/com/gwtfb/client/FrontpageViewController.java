package com.gwtfb.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FrontpageViewController extends Composite {
	
	private VerticalPanel outer = new VerticalPanel ();
	
	public FrontpageViewController () {

		outer.getElement().setId ( "FrontpageViewController" );
		outer.setSpacing(10);
		outer.add ( new HTML ( "This demo uses Facebook Connect. Please click to login " ) );
		outer.add ( new HTML ( "<fb:login-button autologoutlink='true' perms='publish_stream,read_stream' /> " ) );
		outer.add ( new HTML ( "<hr/><fb:comments xid='gwtfb' />" ) );
		initWidget ( outer );
	}

}
