package com.bluecatcode.common.security;

import com.bluecatcode.common.annotations.Beta;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import static java.lang.String.format;

@Beta
public class KeyStores {

    public static KeyStore loadKeyStore(String keyStorePath, String keyStorePassword) {
        return loadKeyStore(KeyStore.getDefaultType(), keyStorePath, keyStorePassword);
    }

    public static KeyStore loadKeyStore(String type, String path, String password) {
        try (InputStream resourceAsStream = new FileInputStream(path)) {
            KeyStore keyStore = KeyStore.getInstance(type);
            keyStore.load(resourceAsStream, password.toCharArray());
            return keyStore;
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new IllegalStateException(format("Cannot load the key store: '%s'", path), e);
        }
    }

    public static KeyManager[] loadKeyManagers(String keyStorePath, String keyStorePassword) {
        KeyStore keyStore = KeyStores.loadKeyStore("JKS", keyStorePath, keyStorePassword);
        String keyAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
        KeyManagerFactory keyFactory;
        try {
            keyFactory = KeyManagerFactory.getInstance(keyAlgorithm);
            keyFactory.init(keyStore, keyStorePassword.toCharArray());
        } catch (UnrecoverableKeyException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException | KeyStoreException ex) {
            throw new IllegalStateException(ex);
        }
        return keyFactory.getKeyManagers();
    }

    public static TrustManager[] loadTrustManagers(String trustStorePath, String trustStorePassword) {
        KeyStore trustStore = KeyStores.loadKeyStore("JKS", trustStorePath, trustStorePassword);
        String trustAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory trustFactory;
        try {
            trustFactory = TrustManagerFactory.getInstance(trustAlgorithm);
            trustFactory.init(trustStore);
        } catch (NoSuchAlgorithmException | KeyStoreException ex) {
            throw new IllegalStateException(ex);
        }
        return trustFactory.getTrustManagers();
    }

    public static KeyStore emptyKeyStore() {
        try {
            return KeyStore.getInstance(KeyStore.getDefaultType());
        } catch (KeyStoreException e) {
            throw new IllegalStateException(e);
        }
    }

    private KeyStores() {
        throw new UnsupportedOperationException();
    }
}