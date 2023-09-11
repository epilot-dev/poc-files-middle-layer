# poc-files-middle-layer

Proof of concept application to act as a middle layer for external file storage.

# Getting started

- Build the application: `./mvnw install`
- Run the application: `mvn spring-boot:run`
- Clean the build target: `./mvnw clean`

# Token authorization

In the `src/main/java/com/epilot/files/auth`:

- AccessTokenFilter.java shows an example filter implementation that reads the Bearer token and verifies it. The example uses `[java-jwt](https://github.com/auth0/java-jwt)` library for token validation.
- AwsCognitoRSAKeyProvider.java implements a custom RSA key provider that is initialized with a URL to the JSON Web Key Sets. It provides the public key needed for the token validation.
- src/main/resources/application.properties contains the configuration file where the JWKS URL is configured


