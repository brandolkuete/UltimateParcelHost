package com.taim.conduire.sdk.shopee.orders;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface IOrders {

    Map getOrderList(String endpointBase) throws JsonProcessingException, UnsupportedEncodingException;
    Map getShopInfo(String endpointBase) throws JsonProcessingException, UnsupportedEncodingException;
    Map getAll(String endpoindBase, Map<String, Object> var2) throws JsonProcessingException, UnsupportedEncodingException;
    Map getOrderDetails(String endpoindBase, Map<String, Object> params) throws JsonProcessingException, UnsupportedEncodingException;
    Map addOrderNote(String endpoindBase, Map<String, Object> params) throws JsonProcessingException, UnsupportedEncodingException;
    Map cancelOrder(String endpoindBase, Map<String, Object> params) throws JsonProcessingException, UnsupportedEncodingException;
}
