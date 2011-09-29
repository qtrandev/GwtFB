package com.gwtfb.client.examples;

import java.util.HashMap;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtfb.client.Callback;
import com.gwtfb.client.DataObject;
import com.gwtfb.client.JSOModel;
import com.gwtfb.client.overlay.Paging;
import com.gwtfb.client.overlay.Post;
import com.gwtfb.sdk.FBCore;
import com.gwtfb.sdk.FBXfbml;

/**
 * Class that shows person api call
 */
public class FriendsExample extends Example {


    private HashMap<String,String> suggestionWorkaround = new HashMap<String,String> ();
    
    /*
     * Decide what to render in response
     */
    enum Ui { INPUT, JSON, FEED };

    

    /*
     * Generic callback class
     */
    class FacebookCallback extends Callback<JavaScriptObject> {
        
   
        private String path;
        private Ui ui;
        private VerticalPanel result;
        
        public FacebookCallback ( String path, Ui ui, VerticalPanel result ) {
            this.path = path;
            this.ui = ui;
            this.result = result;
        }

        public void onSuccess ( JavaScriptObject response ) {
            
            switch ( ui )
            {
                case INPUT :
                    renderSuggestBox ( response );
                    break;
                    
                case JSON :
                    result.add( new HTML ( new JSONObject ( response ).toString() ) );
                    break;
                    
                case FEED : 
                    renderFeed ( response, result );
                    break;
                
            }
            if ( ui == Ui.INPUT ) {
                renderSuggestBox ( response );
            }
        }
    }

    // Private fields
    private VerticalPanel mainPanel = new VerticalPanel ();
    private SimplePanel suggestPanel = new SimplePanel ();
    private VerticalPanel content = new VerticalPanel ();
    private FBCore fbCore;
    

    public FriendsExample ( FBCore fbCore ) {
        
        this.fbCore = fbCore;

        /*
         * Display number of friends
         */
        suggestPanel.add( new HTML ( "Getting friend list..." ) );
        fbCore.api ( "/me/friends", new FacebookCallback ( "/me/friends", Ui.INPUT, null ) );
        
        mainPanel.add ( suggestPanel );
        mainPanel.add ( content );
        initWidget ( mainPanel );
    }
    
    private void handleError ( JavaScriptObject response )
    {
        Window.alert ( "Handle error ");
    }

    /*
     * Render suggesbox to let user choose a friend
     */
    private void renderSuggestBox ( JavaScriptObject response ) {
        
        suggestPanel.clear();
        
        JSOModel jso = response.cast ();
        if ( jso.hasKey ( "error" ) ) {
            handleError ( response );
            return;
        }
        
        JsArray array = jso.getArray("data");
        MultiWordSuggestOracle oracle = new MultiWordSuggestOracle ();
        
       
        for ( int i = 0 ; i < array.length(); i++ ) {
            JSOModel j = array.get(i).cast();

            String name = j.get("name");
            String id = j.get("id");
            oracle.add(name);
            suggestionWorkaround.put ( name, id);
        }

        HorizontalPanel panel = new HorizontalPanel ();
        panel.getElement().setAttribute ("style", "padding: 10px; border: 1px solid #cccccc" );
        panel.add ( new HTML ( "Type friends name and hit return to see available methods: " ) );
       
        SuggestBox box = new SuggestBox (oracle);
        box.addSelectionHandler( new SelectionHandler<Suggestion> () {
            public void onSelection(SelectionEvent<Suggestion> event) {
                clear ();
                displaySelectedName ( event.getSelectedItem().getDisplayString() );
                doGetFriendData ( new Long ( suggestionWorkaround.get ( event.getSelectedItem().getReplacementString() ) ) );
               FBXfbml.parse();
            }
        });
        
        panel.add ( box);
        suggestPanel.add (panel);
    }

    /**
     * Render user posts
     */
    public void renderFeed ( JavaScriptObject response, VerticalPanel resultPanel ) {
        try {
            DataObject dataObject = response.cast();
            JsArray<Post> posts = dataObject.getData().cast();
            
            for ( int i = 0; i < posts.length(); i++ ) {
                DecoratorPanel dp = new DecoratorPanel ();
                dp.addStyleName("post");
                dp.add(posts.get(i).toHTML());
                resultPanel.add (dp);
            }
            
            HorizontalPanel pagingPanel = new HorizontalPanel();
            pagingPanel.setSpacing(10);
            
            Paging paging = dataObject.getObject("paging").cast();
            Anchor previousLink = new Anchor ( "<< Previous" );
            previousLink.setHref(paging.getPrevious());
            
            Anchor nextLink = new Anchor ( "Next >>" );
            nextLink.setHref(paging.getNext());
            
            previousLink.setTarget("blank");
            nextLink.setTarget("blank");
            
            pagingPanel.add(previousLink);
            pagingPanel.add(nextLink);
            
            resultPanel.add(pagingPanel);
            
        } catch ( Exception e ) {
            Window.alert ( "Could not render response: " + e.getMessage() );
        }
    }
 
    /**
     * Clear previous data
     */
    private void clear () {
        content.clear();
    }
    
    private void displaySelectedName ( String name ) {
        
        content.add ( new HTML ( "<h1>" + name + "</h1>" ) );
    }
    
    /**
     * Loop all methods that is accessible to users.
     */
    private void doGetFriendData ( Long id ) {

        renderMethod ( id, "feed", Ui.FEED );
        
        
        String [] methods = { "albums", "friends", "home", "likes", "movies", "books", 
                              "notes", "photos", "videos", "events", "groups" }; 
        
        for ( String method : methods ) {
            renderMethod(id, method, Ui.JSON );
        }
    }

    private void renderMethod(final Long userId, String method, final Ui render) {
        String fields = null;
        
        if ( method.split(":").length == 2 ) {
            fields = method.split(":")[1];
            method = method.split(":")[0];
        }
        final String fieldsInner = fields;
        final String m = "/" + userId + "/" + method;
        
        HTML header = new HTML ( "<div class='smallheader'>/" + method + "</div>");
        // Add link
        final Anchor anchor = new Anchor ( "Click to see result" + (render == Ui.JSON ? " (json)" : "" ) );
        content.add( header );
        content.add ( anchor );
        content.add ( new HTML ( "<p/>" ) );
        
        // Where to put the result from the method
        final VerticalPanel result = new VerticalPanel ();
        content.add ( result );
        
        anchor.addClickHandler(new ClickHandler () {
            public void onClick(ClickEvent event) {
                if ( fieldsInner != null ) {
                    // Add fields parameter
                    JSONObject filter = new JSONObject ();
                    filter.put("fields",new JSONString ( fieldsInner) );
                    fbCore.api (m , filter.getJavaScriptObject(),   new FacebookCallback ( m , render, result ) );
                } else {
                    fbCore.api (m ,  new FacebookCallback ( m , render, result ) );
                }
            }
        });
    }

    
    
    /**
     * Display method
     */
    @Override
    public String getMethod() {
        return "fbCore.api ( '/me/friends' .. )";
    }

    /**
     * Get simple name of class 
     */
    @Override
    public String getSimpleName() {
        return "FriendsExample";
    }
    
 }
