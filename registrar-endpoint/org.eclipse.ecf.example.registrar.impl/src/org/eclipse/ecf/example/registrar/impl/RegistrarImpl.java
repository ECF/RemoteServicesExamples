package org.eclipse.ecf.example.registrar.impl;

import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.ecf.example.endpoint.api.Endpoint;
import org.eclipse.ecf.example.endpoint.api.Registrar;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true, property = { "service.exported.interfaces=*",
		"service.exported.configs=ecf.jms.hazelcast.manager" })
public class RegistrarImpl implements Registrar {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrarImpl.class);
	private final ConcurrentHashMap<Endpoint,String> endpoints = new ConcurrentHashMap<Endpoint,String>();
	private final ConcurrentHashMap<String, String> endpointMessages = new ConcurrentHashMap<String, String>();

	@Activate
	public void init() {
		LOGGER.info("activate");
	}

	@Deactivate
	public void destroy() {
		LOGGER.info("deactivate");
		endpoints.clear();
		endpointMessages.clear();
	}

	@Override
	public void registerEndpoint(String hostname, String message) {
		LOGGER.info("Registering endpoint name {} with message {}", hostname, message);
		endpointMessages.put(hostname, message);
	}

	@Override
	public void unregisterEndpoint(String hostname) {
		if (endpointMessages.remove(hostname) == null) {
			LOGGER.info("Unregistered example endpoint {} and it's message", hostname);
		} else {
			LOGGER.error("No endpoint message exists to unregister for hostname {}", hostname);
		}
	}

	@Reference(policy = ReferencePolicy.DYNAMIC, cardinality = ReferenceCardinality.MULTIPLE, target = "(service.imported=*)")
	public void register(Endpoint endpoint) {
		LOGGER.info("Attemptng to get name of example endpoint {} with registrar");
		String hostname = endpoint.getHostname();
		LOGGER.info("Registering example endpoint hostname={} with registrar", hostname);
		endpoints.put(endpoint, hostname);
		String message = endpointMessages.get(hostname);
		if (message != null) {
			LOGGER.info("Calling back example endpoint {} with message {}", hostname, message);
			endpoint.callback(message);
		} else {
			LOGGER.info("Informing example endpoint {} registrar hasn't received a message from it yet.", hostname);
			endpoint.callback("I have no registered message from you yet " + hostname);
		}
	}

	public void unregister(Endpoint service) {
		LOGGER.info("Service {} was unregistered from manager.", service);
		String hostname = endpoints.get(service);
		if (hostname != null) {
			LOGGER.info("Unregistering example endpoint {} from registrar", hostname);
			if (endpointMessages.remove(hostname) != null) {
				LOGGER.warn("Unregistered example endpoint {} and it's message", hostname);
			}
			if (endpoints.remove(service) != null) {
				LOGGER.info("Unregistered reference for example endpoint {}", hostname);
			}
		}
	}

}
