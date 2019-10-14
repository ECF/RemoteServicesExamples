package org.eclipse.ecf.example.endpoint.api;

public interface Registrar {

	void registerEndpoint(String name, String message);

 	void unregisterEndpoint(String name);

}
