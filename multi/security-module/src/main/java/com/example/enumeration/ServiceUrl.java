package com.example.enumeration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ServiceInfo {

    MEMBER_MODULE("http", "localhost", "9100"),
    BOARD_MODULE("http", "localhost", "9200")
    ;

    private final String protocol;
    private final String domain;
    private final String port;

    public String getBaseUrl() {
        return protocol + "://" + domain + ":" + port;
    }

}
