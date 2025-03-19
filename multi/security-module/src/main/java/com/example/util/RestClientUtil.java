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

@Component
@RequiredArgsConstructor
public class RestClientUtil {

    private final RestClient restClient;

    public <T> T getMono(ServiceUrl serviceUrl, String path,
                         @Nullable Map<String, String> queryParams,
                         @Nullable Map<String, String> headers,
                         Class<T> responseClass) {

        RestClient client = restClient.mutate()
                .baseUrl(serviceUrl.getBaseUrl())
                .build();

        return client.get()
                .uri(uriBuilder -> {
                    uriBuilder.path(path);
                    if(queryParams != null) {
                        queryParams.forEach(uriBuilder::queryParam);
                    }

                    return uriBuilder.build();
                })
                .headers(httpHeaders ->  {
                    if(headers != null) {
                        headers.forEach(httpHeaders::set);
                    }
                })
                .exchange((request, response) -> {
                    if(!response.getStatusCode().isSameCodeAs(HttpStatus.OK)) {
                        throw new RuntimeException(serviceUrl.getBaseUrl() + path +"에 대한 요청에 실패했습니다");
                    }

                    return response.bodyTo(responseClass);
                });
    }

    public <T> List<T> getFlux(ServiceUrl serviceUrl, String path,
                               @Nullable Map<String, Object> queryParams,
                               @Nullable Map<String, String> headers,
                               Class<T> responseClass) {

        RestClient client = restClient.mutate()
                .baseUrl(serviceUrl.getBaseUrl())
                .build();

        return client.get()
                .uri(uriBuilder -> {
                    uriBuilder.path(path);
                    if(queryParams != null) {
                        queryParams.forEach((key, value) -> {

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
                    if(headers != null) {
                        headers.forEach(httpHeaders::set);
                    }
                })
                .exchange((request, response) -> {
                    if(!response.getStatusCode().isSameCodeAs(HttpStatus.OK)) {
                        throw new RuntimeException(serviceUrl.getBaseUrl() + path +"에 대한 요청에 실패했습니다");
                    }

                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(
                            response.getBody(), objectMapper.getTypeFactory().constructCollectionType(List.class, responseClass)
                    );
                });
    }

}
