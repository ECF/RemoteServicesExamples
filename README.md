# Remote Service Examples

This repo contains examples of OSGi Remote Services that use ECF to discover and provide distribution.

Each example has 1 or more remote services

## Registrar-Endpoint

This example is composed of two remote services:  A single Registrar service, and multiple Endpoint services.   The Registar remote service is exported via RSA discovery, and discovered, imported, and injected into Endpoint components (EndpointImpl class).

See the [Registrar-Endpoint README.md for more information](registrar-endpoint/README.md)

