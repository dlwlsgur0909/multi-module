package com.example.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ModuleInfo {

    MEMBER_MODULE("member-module", "http"),
    BOARD_MODULE("board-module", "http")
    ;

    private final String moduleName;
    private final String protocol;


    public String getBaseUrl() {
        return protocol + "://" + moduleName;
    }

}
