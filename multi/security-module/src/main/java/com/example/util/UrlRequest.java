package com.example.util;

import com.example.enumeration.ModuleInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UrlRequest {

    private ModuleInfo moduleInfo;
    private String path;
    private Map<String, Object> queryParams;
    private Map<String, String> headers;
}
