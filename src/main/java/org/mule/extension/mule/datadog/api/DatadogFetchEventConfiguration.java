package org.mule.extension.mule.datadog.api;

import org.mule.extension.mule.datadog.internal.DatadogFetchEventsOperation;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.Optional;

/**
 * This class represents an extension configuration, values set in this class
 * are commonly used across event related operations.
 */
@Operations(DatadogFetchEventsOperation.class)
public class DatadogFetchEventConfiguration {
	
	@Parameter
	@DisplayName("Priority")
	@Optional(defaultValue = "normal")
	private String priority;
	
	@Parameter
	@DisplayName("Sources")
	@Optional(defaultValue = "")
	private String sources;

	@Parameter
	@DisplayName("Tags")
	@Optional(defaultValue = "")
	private String tags;

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getSources() {
		return sources;
	}

	public void setSources(String sources) {
		this.sources = sources;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
}