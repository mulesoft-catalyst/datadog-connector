package org.mule.extension.mule.datadog.api;

import org.mule.runtime.api.util.MultiMap;

/**
 * This is the extension helper class, with generic Datadog functionality.
 */
public class DatadogUtils {
	public static final String DD_NAME = "datadog";

	public static final String DD_API_KEY_HEADER = "DD-API-KEY";
	public static final String DD_APP_KEY_HEADER = "DD-APP-KEY";
	
	public static final String DD_API_KEY_QPARAM = "api_key";
	public static final String DD_APP_KEY_QPARAM = "application_key";

	public static final String DD_EVENTS_PATH = "/api/v1/events";
	
	public static final String DD_EVENTS_ALERT_TYPE = "alert_type";
	public static final String DD_EVENTS_PRIORITY = "priority";
	public static final String DD_EVENTS_SOURCE = "source_type_name";
	public static final String DD_EVENTS_SOURCES = "source";
	public static final String DD_EVENTS_TAGS = "tags";
	public static final String DD_EVENTS_TITLE = "title";
	public static final String DD_EVENTS_TEXT = "text";
	public static final String DD_EVENTS_START = "start";
	public static final String DD_EVENTS_END = "end";

	public static MultiMap<String, String> getGetEventParameters(String apiKey, String appKey) {
		MultiMap<String, String> headers = new MultiMap<String, String>();
		headers.put(DD_API_KEY_QPARAM, apiKey);
		headers.put(DD_APP_KEY_QPARAM, appKey);
	
		return headers;
	}
	
	public static MultiMap<String, String> getPostEventHeaders(String apiKey, String appKey) {
		MultiMap<String, String> headers = new MultiMap<String, String>();
		headers.put(DD_API_KEY_HEADER, apiKey);
		headers.put(DD_APP_KEY_HEADER, appKey);
		headers.put("Content-Type", "application/json");

		return headers;
	}
}
