package com.luxoft.springsecurity;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.Timeout;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        File certFile = new File("src/test/resources/admin.p12");
        char[] password = "changeit".toCharArray();

        try (FileInputStream fis = new FileInputStream(certFile)) {
            keyStore.load(fis, password);
        }

        // Создаём SSL-контекст
        SSLContext sslContext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, password)
                .loadTrustMaterial((chain, authType) -> true) // Доверенные сертификаты
                .build();

        // Создаём фабрику соединений
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext);

        // Регистратор схем подключения
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslSocketFactory)
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .build();

        // Менеджер соединений
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cm.setDefaultMaxPerRoute(10);
        cm.setMaxTotal(50);

        // HTTP клиент
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm) // Заменили setSSLContext()!
                .evictExpiredConnections()
                .evictIdleConnections(Timeout.ofMinutes(5))
                .build();

        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
    }
}
