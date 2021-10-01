package org.mule.extension.mule.datadog.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import org.mule.extension.mule.datadog.api.DatadogSendEventConfiguration;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.Connection;

import java.io.InputStream;

/**
 * This class is a container for operations, every public method in this class
 * will be taken as an extension operation.
 */
public class DatadogSendEventOperation {

	@ParameterGroup(name = "Configuration")
	DatadogSendEventConfiguration eventConfig;

	@MediaType(value = ANY, strict = false)
	@DisplayName("Send Event")
	public InputStream sendEvent(@Connection DatadogConnection connection, @Alias("Title") String title,
			@Alias("Text") String text) {
		return connection.sendEvent(title, text, eventConfig);
	}
}
