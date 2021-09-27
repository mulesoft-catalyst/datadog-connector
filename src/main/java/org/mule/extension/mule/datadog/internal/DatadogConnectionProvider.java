package org.mule.extension.mule.datadog.internal;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.extension.mule.datadog.api.DatadogConnectionConfiguration;
import org.mule.extension.mule.datadog.api.DatadogCredentialsConfiguration;
import org.mule.extension.mule.datadog.api.DatadogEventConnectionConfiguration;
import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.http.api.HttpService;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class (as it's name implies) provides connection instances and the
 * functionality to disconnect and validate those connections.
 * <p>
 * All connection related parameters (values required in order to create a
 * connection) must be declared in the connection providers.
 * <p>
 * This particular example is a {@link PoolingConnectionProvider} which declares
 * that connections resolved by this provider will be pooled and reused. There
 * are other implementations like {@link CachedConnectionProvider} which lazily
 * creates and caches connections or simply {@link ConnectionProvider} if you
 * want a new connection each time something requires one.
 */
public class DatadogConnectionProvider implements PoolingConnectionProvider<DatadogConnection> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DatadogConnectionProvider.class);

	@ParameterGroup(name = "Host")
	DatadogConnectionConfiguration hostConfig;

	@ParameterGroup(name = "Credentials")
	DatadogCredentialsConfiguration credConfig;

	@ParameterGroup(name = "Events")
	DatadogEventConnectionConfiguration eventConfig;

	@Inject
	private HttpService httpService;

	@Override
	public DatadogConnection connect() throws ConnectionException {
		return new DatadogConnection(httpService, hostConfig, credConfig, eventConfig);
	}

	@Override
	public void disconnect(DatadogConnection connection) {
		try {
			connection.invalidate();
		} catch (Exception e) {
			LOGGER.error("Exception disconnecting from Dadadog", e);
		}
	}

	@Override
	public ConnectionValidationResult validate(DatadogConnection connection) {
		ConnectionValidationResult result;
		try {
			if (connection.isConnected()) {
				result = ConnectionValidationResult.success();
			} else {
				result = ConnectionValidationResult.failure("Connection to Datadog failed", new Exception());
			}
		} catch (Exception e) {
			result = ConnectionValidationResult.failure("Connection to Datadog failed", new Exception());
		}
		return result;
	}
}
