package com.epilot.files;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.interfaces.RSAKeyProvider;

public class AwsCognitoRSAKeyProvider implements RSAKeyProvider {
    private final URL jwksUrl;
    private final JwkProvider provider;
    
    public AwsCognitoRSAKeyProvider(String wellKnownUrl) {
        try {
            jwksUrl = new URL(wellKnownUrl);
            provider = new JwkProviderBuilder(jwksUrl).build();
        } catch (MalformedURLException e) {
            throw new RuntimeException(String.format("Invalid URL provided: %s", e.getMessage()));
        }
    }

    @Override
    public RSAPublicKey getPublicKeyById(String kid) {
        try {
            return (RSAPublicKey) provider.get(kid).getPublicKey();
        } catch (JwkException e) {
            throw new RuntimeException(String.format("Failed to get JWT kid=%s from awsJwksUr=%s", kid, jwksUrl));
        }
    }

    @Override
    public RSAPrivateKey getPrivateKey() {
        return null;
    }

    @Override
    public String getPrivateKeyId() {
        return null;
    }
}