package org.mule.extension.mule.datadog.api;

import org.mule.extension.mule.datadog.internal.DatadogOperations;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.Optional;

/**
 * This class represents an extension configuration, values set in this class
 * are commonly used across event related operations.
 */
@Operations(DatadogOperations.class)
public class DatadogEventConfiguration {

	@Parameter
	@DisplayName("Type")
	@Optional(defaultValue = "info")
	private String alertType;

	@Parameter
	@DisplayName("Tags")
	@Optional(defaultValue = "")
	private String tags;

	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
}