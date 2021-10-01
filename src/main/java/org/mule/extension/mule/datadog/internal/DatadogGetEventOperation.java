package org.mule.extension.mule.datadog.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.Connection;

import java.io.InputStream;

/**
 * This class is a container for operations, every public method in this class
 * will be taken as an extension operation.
 */
public class DatadogGetEventOperation {

	@MediaType(value = ANY, strict = false)
	@DisplayName("Get Event")
	public InputStream getEvent(@Connection DatadogConnection connection, @Alias("Id") Long id) {
		return connection.getEvent(id);
	}
}
