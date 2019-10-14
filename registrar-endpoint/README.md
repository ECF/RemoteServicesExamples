# Registrar-Endpoint Example

This example is composed of two remote services:  A single Registrar service, and multiple Endpoint services.   The Registar remote service is exported via RSA discovery, and discovered, imported, and injected into Endpoint components (EndpointImpl class).

Endpoint remote services are then registered and exported, and the Registrar discovers these Endpoint remote services, imports and injects them into the registrar.

In the simplest case 

1. A single Registrar is started (resulting in export and output of endpoint description to console).
1. A single Endpoint is started.  When the Registrar remote service is discovered, it is imported and injected via DS into unsatisfied EndpointImpl component.   The Registrar inject by DS satisfies the EndpointImpl component, and DS activates and exports the EndpointImpl component as an Endpoint remote service.
1. The Registrar discovers the exported Endpoint, imports and DS injects the Endpoint into the Registrar.

This results in the Endpoint having a reference to the Registrar remote service (and being able to call the Registrar methods), and the Registrar having 1 or more Endpoints (and being able to call the Endpoint methods).

Multiple Endpoints may be started, and after the sequence above for each one, the registrar will maintain references to all Endpoint remote services, and each Endpoint will have a reference to the Registrar.



