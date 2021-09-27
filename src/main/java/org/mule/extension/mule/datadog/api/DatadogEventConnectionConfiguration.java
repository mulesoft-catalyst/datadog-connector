package org.mule.extension.mule.datadog.api;

import org.mule.extension.mule.datadog.internal.DatadogConnectionProvider;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.Optional;

/**
 * This class represents an extension configuration, values set in this class
 * are commonly used across event related operations.
 */
@ConnectionProviders(DatadogConnectionProvider.class)
public class DatadogEventConnectionConfiguration {

	@Parameter
	@DisplayName("Priority")
	@Optional(defaultValue = "normal")
	private String priority;

	@Parameter
	@DisplayName("Source")
	@Optional(defaultValue = "MULESOFT")
	private String source;

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}