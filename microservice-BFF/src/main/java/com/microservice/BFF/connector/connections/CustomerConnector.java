package com.microservice.BFF.connector.connections;

import com.microservice.BFF.connector.config.EndpointConfiguration;
import com.microservice.BFF.connector.config.HostConfiguration;
import com.microservice.BFF.connector.config.HttpConnectorProperties;
import com.microservice.BFF.connector.payload.CustomerDTO;
import com.microservice.BFF.exception.ApiErrorEnum;
import com.microservice.BFF.exception.BFFException;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerConnector {

    static Logger LOGGER = LoggerFactory.getLogger(CustomerConnector.class);

    String HOST = "microservice-customer";
    String ENDPOINT = "get-customer";

    HttpConnectorProperties httpConnectorProperties;

    public Mono<CustomerDTO> getCustomer(String customerEncryptedCode){
        LOGGER.info("calling to microservice customer");
        HostConfiguration hostConfiguration = httpConnectorProperties.getHosts().get(HOST);
        EndpointConfiguration endpointConfiguration = hostConfiguration.getEndpoints().get(ENDPOINT);

        HttpClient httpClient = HttpClient.create()
                .option(
                        ChannelOption.CONNECT_TIMEOUT_MILLIS,
                        Math.toIntExact(endpointConfiguration.getConnectionTimeOut())
                )
                .doOnConnected(conn -> conn
                        .addHandler(
                                new ReadTimeoutHandler(
                                        endpointConfiguration.getReadTimeOut(),
                                        TimeUnit.MILLISECONDS
                                )
                        )
                        .addHandler(
                                new WriteTimeoutHandler(
                                        endpointConfiguration.getWriteTimeOut(),
                                        TimeUnit.MILLISECONDS
                                )
                        )
                );

        WebClient client = WebClient.builder()
                .baseUrl(
                        "https://"
                                +hostConfiguration.getHost()
                                + ":"
                                + hostConfiguration.getPort()
                                + endpointConfiguration.getUrl()
                )
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        return client.get()
                .uri(uriBuilder -> uriBuilder.build(customerEncryptedCode))
                .retrieve()
                .bodyToMono(CustomerDTO.class)
                .onErrorMap(
                        RuntimeException.class, ex ->
                                new BFFException(ApiErrorEnum.BAD_FORMAT)
                )
                .doOnError(error ->
                        LOGGER.error("Error when performing request: {}", error.getMessage())
                )
                .onErrorResume(error -> {
                    LOGGER.error("Retrying after error: {}", error.getMessage());
                    return client.get()
                            .uri(uriBuilder -> uriBuilder.build(customerEncryptedCode))
                            .retrieve()
                            .bodyToMono(CustomerDTO.class);
                });
    }
}
