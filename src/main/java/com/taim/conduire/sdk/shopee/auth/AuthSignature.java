package com.taim.conduire.sdk.shopee.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AuthSignature {

    public AuthSignature() {
    }

    public static Map<String,Object> getAsMap(AuthConfig config, Map<String, Object> params){
        if (config != null){
            Map<String,Object> authParams= new HashMap<>();
            authParams.put("partner_id",config.getPartnerId());
            authParams.put("shopid",config.getShopId());
            authParams.put("timestamp",config.getTimestamp());
            authParams.putAll(params);
            return authParams;
        }
        return Collections.emptyMap();
    }

    public static Map<String,Object> getAsMap(AuthConfig config){
        Map<String,Object> authParams= new HashMap<>();
        authParams.put("partner_id",config.getPartnerId());
        authParams.put("shopid",config.getShopId());
        authParams.put("timestamp",config.getTimestamp());
        return authParams;
    }

    private static String mapToString(Map<String,Object> map) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }

    public static byte[] generateAuthSignature(byte[] partnerSecret,String endpoint, Map<String, Object> params) throws JsonProcessingException, UnsupportedEncodingException {
        byte[] signatureBaseString= generateSignatureBaseString(endpoint,params);
        return signBaseString(partnerSecret,signatureBaseString);
    }

    public static byte[] generateSignatureBaseString(String url, Map<String,Object> params) throws JsonProcessingException, UnsupportedEncodingException {
        String signParams = mapToString(params);
        return (url+"|"+signParams).getBytes("UTF-8");
    }

    private static byte[] signBaseString(byte[] secret,byte[] signatureBaseString){
        try {
            Mac macInstance= Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey=  new SecretKeySpec(secret,"HmacSHA256");
            macInstance.init(secretKey);
            return macInstance.doFinal(signatureBaseString);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
