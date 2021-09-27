package org.mule.extension.mule.datadog.api;

import org.mule.extension.mule.datadog.internal.DatadogConnectionProvider;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.Optional;

/**
 * This class represents an extension configuration, values set in this class
 * are commonly used across datadog operations.
 */
@ConnectionProviders(DatadogConnectionProvider.class)
public class DatadogConnectionConfiguration {

	@Parameter
	@DisplayName("Host")
	@Optional(defaultValue = "https://api.datadoghq.com")
	private String host;

	@Parameter
	@DisplayName("Port")
	@Optional(defaultValue = "443")
	private Integer port;

	@Parameter
	@DisplayName("Timeout")
	@Optional(defaultValue = "10000")
	private Integer timeout;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
}