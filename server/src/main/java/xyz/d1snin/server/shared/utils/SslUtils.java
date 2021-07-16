package xyz.d1snin.server.shared.utils;

import lombok.SneakyThrows;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

public class SslUtils {

    @SneakyThrows
    public static SSLContext createSslContext(InputStream jksFileInput, String pass) {
        final KeyStore trustStore = KeyStore.getInstance("jks");
        trustStore.load(jksFileInput, pass.toCharArray());

        final KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(trustStore, pass.toCharArray());

        final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);

        final SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());

        return sslContext;
    }
}
