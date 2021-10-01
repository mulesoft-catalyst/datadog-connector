package org.mule.extension.mule.datadog.internal;

import org.mule.extension.mule.datadog.api.DatadogConnectionConfiguration;
import org.mule.extension.mule.datadog.api.DatadogCredentialsConfiguration;
import org.mule.extension.mule.datadog.api.DatadogFetchEventConfiguration;
import org.mule.extension.mule.datadog.api.DatadogSendEventConfiguration;
import org.mule.extension.mule.datadog.api.DatadogEventConnectionConfiguration;
import org.mule.extension.mule.datadog.api.DatadogUtils;
import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.http.api.HttpService;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpClientConfiguration;
import org.mule.runtime.http.api.domain.entity.ByteArrayHttpEntity;
import org.mule.runtime.http.api.domain.entity.HttpEntity;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

import java.io.InputStream;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents an extension connection to the Datadog API
 * (https://docs.datadoghq.com/api/latest/).
 */
public final class DatadogConnection {

	private static final Logger LOGGER = LoggerFactory.getLogger(DatadogConnection.class);
	private DatadogConnectionConfiguration hostConfig;
	private DatadogCredentialsConfiguration credConfig;
	private DatadogEventConnectionConfiguration conEventConfig;

	private HttpClient httpClient;

	public DatadogConnection(HttpService httpService, DatadogConnectionConfiguration hostConfig,
			DatadogCredentialsConfiguration credConfig, DatadogEventConnectionConfiguration conEventConfig) {
		this.hostConfig = hostConfig;
		this.credConfig = credConfig;
		this.conEventConfig = conEventConfig;

		initHttpClient(httpService);
	}

	public void initHttpClient(HttpService httpService) {
		HttpClientConfiguration.Builder builder = new HttpClientConfiguration.Builder();
		builder.setName(DatadogUtils.DD_NAME);
		httpClient = httpService.getClientFactory().create(builder.build());
		httpClient.start();
	}

	public void invalidate() {
		httpClient.stop();
	}

	public boolean isConnected() throws Exception {
		return true;
	}

	/*
	 * See https://docs.datadoghq.com/api/latest/events/
	 */
	public InputStream getEvent(Long id) {
		String strUri = hostConfig.getHost() + ":" + hostConfig.getPort() + DatadogUtils.DD_EVENTS_PATH + "/" + id;
		MultiMap<String, String> params = DatadogUtils.getGetEventParameters(credConfig.getApiKey(),
				credConfig.getAppKey());
		HttpRequest httpRequest = HttpRequest.builder().method("GET").uri(strUri).queryParams(params).build();
		LOGGER.debug("Http Request: [" + httpRequest.toString() + "]");
		try {
			HttpResponse httpResponse = httpClient.send(httpRequest, hostConfig.getTimeout(), false, null);
			LOGGER.debug("Http Response: [" + httpResponse.toString() + "]");
			return httpResponse.getEntity().getContent();
		} catch (IOException e) {
			LOGGER.error("IOException sending Event to Dadadog", e);
		} catch (TimeoutException e) {
			LOGGER.error("TimeoutException sending Event to Dadadog", e);
		} catch (Exception e) {
			LOGGER.error("Exception sending Event to Dadadog", e);
		}

		return null;
	}

	/*
	 * See https://docs.datadoghq.com/api/latest/events/
	 */
	public InputStream fetchEvents(Long start, Long end, DatadogFetchEventConfiguration opsEventConfig) {
		String strUri = hostConfig.getHost() + ":" + hostConfig.getPort() + DatadogUtils.DD_EVENTS_PATH;
		MultiMap<String, String> params = DatadogUtils.getGetEventParameters(credConfig.getApiKey(),
				credConfig.getAppKey());
		params = getFetchEventParameters(params, start, end, opsEventConfig);
		HttpRequest httpRequest = HttpRequest.builder().method("GET").uri(strUri).queryParams(params).build();
		LOGGER.debug("Http Request: [" + httpRequest.toString() + "]");
		try {
			HttpResponse httpResponse = httpClient.send(httpRequest, hostConfig.getTimeout(), false, null);
			LOGGER.debug("Http Response: [" + httpResponse.toString() + "]");
			return httpResponse.getEntity().getContent();
		} catch (IOException e) {
			LOGGER.error("IOException sending Event to Dadadog", e);
		} catch (TimeoutException e) {
			LOGGER.error("TimeoutException sending Event to Dadadog", e);
		} catch (Exception e) {
			LOGGER.error("Exception sending Event to Dadadog", e);
		}

		return null;
	}

	/*
	 * See https://docs.datadoghq.com/api/latest/events/
	 */
	public InputStream sendEvent(String title, String text, DatadogSendEventConfiguration opsEventConfig) {
		String strUri = hostConfig.getHost() + ":" + hostConfig.getPort() + DatadogUtils.DD_EVENTS_PATH;
		MultiMap<String, String> headers = DatadogUtils.getPostEventHeaders(credConfig.getApiKey(),
				credConfig.getAppKey());
		String eventRequest = getPostEventRequest(title, text, opsEventConfig);
		LOGGER.debug("Event Request: [" + eventRequest + "]");
		HttpEntity httpEntity = new ByteArrayHttpEntity(eventRequest.getBytes());
		HttpRequest httpRequest = HttpRequest.builder().method("POST").uri(strUri).entity(httpEntity).headers(headers)
				.build();
		LOGGER.debug("Http Request: [" + httpRequest.toString() + "]");
		try {
			HttpResponse httpResponse = httpClient.send(httpRequest, hostConfig.getTimeout(), false, null);
			LOGGER.debug("Http Response: [" + httpResponse.toString() + "]");
			return httpResponse.getEntity().getContent();
		} catch (IOException e) {
			LOGGER.error("IOException sending Event to Dadadog", e);
		} catch (TimeoutException e) {
			LOGGER.error("TimeoutException sending Event to Dadadog", e);
		} catch (Exception e) {
			LOGGER.error("Exception sending Event to Dadadog", e);
		}

		return null;
	}
	
	private MultiMap<String, String>  getFetchEventParameters(MultiMap<String, String> params, Long start, Long end, DatadogFetchEventConfiguration opsEventConfig) {
		params.put(DatadogUtils.DD_EVENTS_START, "" + start);
		params.put(DatadogUtils.DD_EVENTS_END, "" + end);
		params.put(DatadogUtils.DD_EVENTS_PRIORITY, conEventConfig.getPriority());
		params.put(DatadogUtils.DD_EVENTS_SOURCES, opsEventConfig.getSources());
		params.put(DatadogUtils.DD_EVENTS_TAGS, opsEventConfig.getTags());
	
		return params;
	}

	private String getPostEventRequest(String title, String text, DatadogSendEventConfiguration eventConfig) {
		String[] tags = eventConfig.getTags().trim().split(",");
		StringBuilder postEventRequest = new StringBuilder();
		postEventRequest.append("{ ").append("\"").append(DatadogUtils.DD_EVENTS_ALERT_TYPE).append("\": ").append("\"")
				.append(eventConfig.getAlertType()).append("\"").append(", ").append("\"")
				.append(DatadogUtils.DD_EVENTS_PRIORITY).append("\": ").append("\"")
				.append(conEventConfig.getPriority()).append("\"").append(", ").append("\"")
				.append(DatadogUtils.DD_EVENTS_SOURCE).append("\": ").append("\"")
				.append(conEventConfig.getSource()).append("\"").append(", ");

		if (tags.length > 0) {
			postEventRequest.append("\"").append(DatadogUtils.DD_EVENTS_TAGS).append("\": ").append("[ ");
			for (int i = 0; i < tags.length; i++) {
				postEventRequest.append("\"").append(tags[i]).append("\"");
				if (i < tags.length - 1) {
					postEventRequest.append(", ");
				}
			}

			postEventRequest.append(" ]").append(", ");
		}

		postEventRequest.append("\"").append(DatadogUtils.DD_EVENTS_TEXT).append("\": ").append("\"").append(text)
				.append("\"").append(", ").append("\"").append(DatadogUtils.DD_EVENTS_TITLE).append("\": ").append("\"")
				.append(title).append("\"").append(" }");

		return postEventRequest.toString();
	}
}
