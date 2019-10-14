package org.eclipse.ecf.example.endpoint.impl;

import org.eclipse.ecf.example.endpoint.api.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author moquinr
 */
@Component(immediate = true, property = { "service.exported.interfaces=*",
		"service.exported.configs=ecf.jms.hazelcast.member" })
public class EndpointImpl implements Endpoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(EndpointImpl.class);
	private volatile Registrar registrar;

	private String hostname;
	private String address;

	@Activate
	public void activate() {
		LOGGER.info("activate");
		try {
			InetAddress ia = InetAddress.getLocalHost();
			this.hostname = ia.getHostName();
			this.address = ia.getHostAddress();
		} catch (UnknownHostException ex) {
			LOGGER.error("Error retrieving IP and Hostname information for this endpoint.", ex);
		}
		if (this.hostname == null) {
			this.hostname = UUID.randomUUID().toString();
		}
		// Now register us with endpoint
		LOGGER.info("Registering example endpoint hostname {} with registrar", this.hostname);
		this.registrar.registerEndpoint(this.hostname, "Message source was: " + this.hostname);
	}

	@Deactivate
	public void deactivate() {
		LOGGER.info("deactivate");
		try {
			// The reason this is in try/catch block is that it's
			// possible for this remote call to the registrar to fail
			// simply because the registrar has disconnected before this
			// gets called
			this.registrar.unregisterEndpoint(this.hostname);
		} catch (Exception e) {
			LOGGER.error("call to unregisterEndpoint failed", e);
		}
	}

	@Reference(policy = ReferencePolicy.DYNAMIC)
	public void bindRegistrar(Registrar service) {
		LOGGER.info("bindRegistrar");
		this.registrar = service;
	}

	public void unbindRegistrar(Registrar service) {
		LOGGER.info("unbindRegistrar");
		this.registrar = null;
	}

	@Override
	public void callback(String message) {
		LOGGER.info("Received callback message from registrar: {}", message);
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		LOGGER.info("endpoint.getAddress");
		return address;
	}

	/**
	 * @return the hostname
	 */
	@Override
	public String getHostname() {
		LOGGER.info("endpoint.getHostname");
		return hostname;
	}
}
