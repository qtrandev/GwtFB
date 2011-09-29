package com.gwtfb.client.examples;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtfb.client.Callback;
import com.gwtfb.sdk.FBCore;

/**
 * Fql Query Example
 * 
 * @author olamariussagli
 */
public class FqlQueryExample extends Example {

	private VerticalPanel mainPanel = new VerticalPanel();
	private SimplePanel result = new SimplePanel();

	public FqlQueryExample(FBCore fbCore) {

		String method = "fql.query";
		String fql = "SELECT uid, first_name, last_name FROM user WHERE uid = 744450545";

		JSONObject query = new JSONObject();
		query.put("method", new JSONString(method));
		query.put("query", new JSONString(fql));
		mainPanel.add(new HTML(fql));
		mainPanel.add(result);
		result.setWidget(new HTML("Getting result..."));

		// Execute query
		fbCore.api(query.getJavaScriptObject(),
				new Callback<JavaScriptObject>() {
					public void onSuccess(JavaScriptObject response) {
						result.setWidget(new HTML(new JSONObject(response)
								.toString()));
					}
				});

		initWidget(mainPanel);
	}

	@Override
	public String getMethod() {
		return "fql.query";
	}

	@Override
	public String getSimpleName() {
		return "FqlQueryExample";
	}
}
