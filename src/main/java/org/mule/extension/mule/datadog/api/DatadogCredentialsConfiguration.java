package org.mule.extension.mule.datadog.api;

import org.mule.extension.mule.datadog.internal.DatadogConnectionProvider;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

/**
 * This class represents an extension configuration, values set in this class
 * are commonly used across datadog operations.
 */
@ConnectionProviders(DatadogConnectionProvider.class)
public class DatadogCredentialsConfiguration {

	@Parameter
	@DisplayName("API key")
	private String apiKey;

	@Parameter
	@DisplayName("Application key")
	private String appKey;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
}