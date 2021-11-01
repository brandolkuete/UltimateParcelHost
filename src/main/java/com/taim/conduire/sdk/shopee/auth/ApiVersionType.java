package com.taim.conduire.sdk.shopee.auth;

public enum ApiVersionType {
    V1("v1"),
    V2("v2");

    private String value;

    private ApiVersionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
