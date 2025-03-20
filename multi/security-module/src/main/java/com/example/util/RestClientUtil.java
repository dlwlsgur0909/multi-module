package com.example.util;

import com.example.enumeration.ServiceUrl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RestClientUtil {

    private final RestClient restClient;

    public <T> T getMono(UrlRequest urlRequest,
                         Class<T> responseClass) {

        RestClient client = restClient.mutate()
                .baseUrl(urlRequest.getServiceUrl().getBaseUrl())
                .build();

        return client.get()
                .uri(uriBuilder -> {
                    uriBuilder.path(urlRequest.getPath());

                    if(urlRequest.getQueryParams() != null) {
                        urlRequest.getQueryParams().forEach((key, value) -> {

                            if(value instanceof List<?> list) {
                                list.forEach(item -> {
                                    uriBuilder.queryParam(key, item);
                                });
                            }else {
                                uriBuilder.queryParam(key, value);
                            }
                        });
                    }

                    return uriBuilder.build();
                })
                .headers(httpHeaders ->  {
                    if(urlRequest.getHeaders() != null) {
                        urlRequest.getHeaders().forEach(httpHeaders::set);
                    }
                })
                .exchange((request, response) -> {
                    if(!response.getStatusCode().isSameCodeAs(HttpStatus.OK)) {
                        throw new RuntimeException(urlRequest.getServiceUrl().getBaseUrl() + urlRequest.getPath() +"에 대한 요청에 실패했습니다");
                    }

                    return response.bodyTo(responseClass);
                });
    }

    public <T> List<T> getFlux(UrlRequest urlRequest,
                               Class<T> responseClass) {

        RestClient client = restClient.mutate()
                .baseUrl(urlRequest.getServiceUrl().getBaseUrl())
                .build();

        return client.get()
                .uri(uriBuilder -> {
                    uriBuilder.path(urlRequest.getPath());
                    if(urlRequest.getQueryParams() != null) {
                        urlRequest.getQueryParams().forEach((key, value) -> {

                            if(value instanceof List<?> list) {
                                list.forEach(item -> {
                                    uriBuilder.queryParam(key, item);
                                });
                            }else {
                                uriBuilder.queryParam(key, value);
                            }
                        });
                    }

                    return uriBuilder.build();
                })
                .headers(httpHeaders ->  {
                    if(urlRequest.getHeaders() != null) {
                        urlRequest.getHeaders().forEach(httpHeaders::set);
                    }
                })
                .exchange((request, response) -> {
                    if(!response.getStatusCode().isSameCodeAs(HttpStatus.OK)) {
                        throw new RuntimeException(urlRequest.getServiceUrl().getBaseUrl() + urlRequest.getPath() +"에 대한 요청에 실패했습니다");
                    }

                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(
                            response.getBody(), objectMapper.getTypeFactory().constructCollectionType(List.class, responseClass)
                    );
                });
    }

}
