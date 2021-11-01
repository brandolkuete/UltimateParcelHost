package com.taim.conduire.sdk.shopee.orders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.taim.conduire.sdk.shopee.auth.ApiVersionType;
import com.taim.conduire.sdk.shopee.httpclient.DefaultHttpClient;
import com.taim.conduire.sdk.shopee.httpclient.HttpClient;
import com.taim.conduire.sdk.shopee.auth.AuthConfig;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class Orders implements IOrders {

    private HttpClient client;
    private AuthConfig config;
    private String version;

    public Orders(AuthConfig config, ApiVersionType apiVersion) {
        this.client=new DefaultHttpClient();
        this.config=config;
        this.version= apiVersion.getValue();
    }

    @Override
    public Map getOrderList(String endpointBase) throws JsonProcessingException, UnsupportedEncodingException {
        return getMapWithoutParams(endpointBase);
    }

    @Override
    public Map getShopInfo(String endpointBase) throws JsonProcessingException, UnsupportedEncodingException {
        return getMapWithoutParams(endpointBase);
    }

    @Override
    public Map getAll(String endpoindBase, Map<String, Object> params) throws JsonProcessingException, UnsupportedEncodingException {
        return getMapWithParams(endpoindBase, params);
    }

    @Override
    public Map getOrderDetails(String endpoindBase, Map<String, Object> params) throws JsonProcessingException, UnsupportedEncodingException {
        return getMapWithParams(endpoindBase, params);
    }

    @Override
    public Map addOrderNote(String endpoindBase, Map<String, Object> params) throws JsonProcessingException, UnsupportedEncodingException {
        return getMapWithParams(endpoindBase, params);
    }

    @Override
    public Map cancelOrder(String endpoindBase, Map<String, Object> params) throws JsonProcessingException, UnsupportedEncodingException {
        return getMapWithParams(endpoindBase,params);
    }

    private Map getMapWithParams(String endpoindBase, Map<String, Object> params) throws JsonProcessingException, UnsupportedEncodingException {
        String finalUrl = String.format("%s/%s/%s", config.getUrl(), this.version, endpoindBase);
        return this.client.post_with_params(config, finalUrl, params);
    }

    private Map getMapWithoutParams(String endpointBase) throws JsonProcessingException, UnsupportedEncodingException {
        String finalUrl = String.format("%s/%s/%s", config.getUrl(), this.version, endpointBase);
        return this.client.post_with_no_params(config, finalUrl);
    }
}
