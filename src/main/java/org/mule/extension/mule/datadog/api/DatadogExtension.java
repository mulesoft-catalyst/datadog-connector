package org.mule.extension.mule.datadog.api;

import static org.mule.runtime.api.meta.Category.COMMUNITY;

import org.mule.extension.mule.datadog.internal.DatadogConnectionProvider;
import org.mule.extension.mule.datadog.internal.DatadogGetEventOperation;
import org.mule.extension.mule.datadog.internal.DatadogSendEventOperation;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;
	
/**
 * This is the main class of an extension, is the entry point from which
 * configurations, connection providers, operations and sources are going to be
 * declared.
 */
@Xml(prefix = "datadog")
@Extension(name = "Datadog", vendor = "Acme Corp.", category = COMMUNITY)
@ConnectionProviders(DatadogConnectionProvider.class)
@Operations({ DatadogSendEventOperation.class, DatadogGetEventOperation.class })
public class DatadogExtension {

}