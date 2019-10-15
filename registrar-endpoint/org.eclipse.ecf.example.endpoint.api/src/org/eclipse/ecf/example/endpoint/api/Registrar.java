/*******************************************************************************
 * Copyright (c) 2019 Composent, Inc, Ryan Moquin, and Elsivier Inc. All rights 
 * reserved. This  program and the accompanying materials are made available 
 * under the terms of the Apache  Public License v2.0 which accompanies this 
 * distribution, and is available at https://www.apache.org/licenses/LICENSE-2.0
 * 
 * Contributors: Ryan Moquin and Scott Lewis - initial API and implementation
 ******************************************************************************/
package org.eclipse.ecf.example.endpoint.api;

public interface Registrar {

	void registerEndpoint(String name, String message);

 	void unregisterEndpoint(String name);

}
