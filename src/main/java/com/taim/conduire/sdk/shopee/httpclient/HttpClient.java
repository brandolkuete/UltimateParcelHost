package com.taim.conduire.sdk.shopee.httpclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.taim.conduire.sdk.shopee.auth.AuthConfig;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface HttpClient {

    Map post_with_no_params(AuthConfig config, String url) throws JsonProcessingException, UnsupportedEncodingException;

    Map post_with_params(AuthConfig config, String url, Map<String, Object> params) throws JsonProcessingException, UnsupportedEncodingException;
}
