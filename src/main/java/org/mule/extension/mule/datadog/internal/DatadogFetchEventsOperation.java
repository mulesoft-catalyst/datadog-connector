package org.mule.extension.mule.datadog.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import org.mule.extension.mule.datadog.api.DatadogFetchEventConfiguration;
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
public class DatadogFetchEventsOperation {

	@ParameterGroup(name = "Configuration")
	DatadogFetchEventConfiguration eventConfig;

	@MediaType(value = ANY, strict = false)
	@DisplayName("Fetch Events")
	public InputStream fetchEvents(@Connection DatadogConnection connection, @Alias("Start") Long start,
			@Alias("End") Long end) {
		return connection.fetchEvents(start, end, eventConfig);
	}
}
