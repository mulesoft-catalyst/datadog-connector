package org.mule.extension.mule.datadog.api;

import org.mule.runtime.api.util.MultiMap;

/**
 * This is the extension helper class, with generic Datadog functionality.
 */
public class DatadogUtils {
	public static final String DD_NAME = "datadog";

	public static final String DD_API_KEY = "DD-API-KEY";
	public static final String DD_APP_KEY = "DD-APP-KEY";

	public static final String DD_EVENTS_PATH = "/api/v1/events";
	
	public static final String DD_EVENTS_ALERT_TYPE = "alert_type";
	public static final String DD_EVENTS_PRIORITY = "priority";
	public static final String DD_EVENTS_SOURCE_TYPE_NAME = "source_type_name";
	public static final String DD_EVENTS_TAGS = "tags";
	public static final String DD_EVENTS_TITLE = "title";
	public static final String DD_EVENTS_TEXT = "text";

	public static MultiMap<String, String> getPostEventHeaders(String apiKey, String appKey) {
		MultiMap<String, String> headers = new MultiMap<String, String>();
		headers.put(DatadogUtils.DD_API_KEY, apiKey);
		headers.put(DatadogUtils.DD_APP_KEY, appKey);
		headers.put("Content-Type", "application/json");

		return headers;
	}
}
