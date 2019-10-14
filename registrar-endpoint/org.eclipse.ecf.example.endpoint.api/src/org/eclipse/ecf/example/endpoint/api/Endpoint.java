package org.eclipse.ecf.example.endpoint.api;

public interface Endpoint {

	void callback(String message);
    
    String getHostname();

}
