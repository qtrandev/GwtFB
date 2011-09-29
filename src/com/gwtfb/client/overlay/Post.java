package com.gwtfb.client.overlay;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * Wrapper class
 * @see http://developers.facebook.com/docs/reference/api/post
 *
 */
public class Post extends JavaScriptObject {
    
    protected Post () { }
    
    public final native String getId() /*-{ return this.id; }-*/;
    public final native JavaScriptObject getFrom() /*-{ return this.from; }-*/;
    public final native String getMessage() /*-{ return this.message; }-*/;
    public final native String getType() /*-{ return this.type; }-*/;
    public final native String getCreatedTime() /*-{ return this.created_time;  }-*/;
    public final Date getCreatedTimeDate() { return DateTimeFormat.getFormat("yyyy-MM-dd'T'hh:mm:ssZ").parse(getCreatedTime()); }
    public final native String getUpdatedTime() /*-{ return this.updated_time;  }-*/;
    public final Date getUpdatedTimeDate() { return DateTimeFormat.getFormat("yyyy-MM-dd'T'hh:mm:ssZ").parse(getUpdatedTime()); }
    public final native String getPicture() /*-{ return this.picture; }-*/;
    public final native String getName() /*-{ return this.name; }-*/;
    public final native String getLink() /*-{ return this.link; }-*/;
    public final native String getDescription() /*-{ return this.description; }-*/;
    public final native String getCaption() /*-{ return this.caption; }-*/;
    public final native String getIcon() /*-{ return this.icon; }-*/;
    public final native String getAttribution() /*-{ return this.attribution; }-*/;
    public final native Integer getLikes() /*-{ return this.likes; }-*/;

    public final HTML toHTML () {
        String h = "Id: " + getId() + "<br/>" +
               "Message: " + getMessage () ;
        return new HTML (h);
    }
    
}
