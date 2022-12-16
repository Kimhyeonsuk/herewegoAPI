package com.herewego.herewegoapi.common;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Component
public class WebClientBuilderManager {
    public WebClient.Builder makeCommonWebclientBuilder() {
        ReactorClientHttpConnector connector = new ReactorClientHttpConnector(HttpClient.create());
        return WebClient.builder().clientConnector(connector);
    }
}
