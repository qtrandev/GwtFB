package com.gwtfb.client;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * 
 * Class that returns JsArray from json array 'data' 
 *
 */
public class DataObject extends JavaScriptObject {
    
    protected DataObject () {}
    
    public final native JavaScriptObject getData () /*-{
        return this.data;
    
    }-*/;
    
    public final native JavaScriptObject getObject ( String name ) /*-{
        return this[name];
    }-*/;
    
    
    public final native String getString ( String name ) /*-{
        return this[name];
    }-*/;

}
