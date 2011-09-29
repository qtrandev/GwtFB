package com.gwtfb.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;

/**
 * Wraps public google wave in an iframe 
 */
public class WaveView extends Composite {

    
    public  WaveView () {
        
        Frame frame = new Frame ();
        frame.setWidth( "100%");
        frame.setHeight( "710px");
      
        frame.setUrl("./Wave.html");
        initWidget ( frame );
    }
    

}
