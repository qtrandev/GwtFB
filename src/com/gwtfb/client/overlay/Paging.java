package com.gwtfb.client.overlay;

import com.google.gwt.core.client.JavaScriptObject;

public class Paging  extends JavaScriptObject {
    
    protected Paging() {}
    
    public final native String getPrevious() /*-{ return this.previous; }-*/;
    public final native String getNext() /*-{ return this.next; }-*/;
}