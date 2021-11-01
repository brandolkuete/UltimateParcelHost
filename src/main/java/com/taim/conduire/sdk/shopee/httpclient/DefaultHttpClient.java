package com.taim.conduire.sdk.shopee.httpclient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.taim.conduire.sdk.shopee.auth.AuthConfig;
import com.taim.conduire.sdk.shopee.auth.AuthSignature;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class DefaultHttpClient implements HttpClient {
    private CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    private  ObjectMapper mapper = new ObjectMapper();

    public DefaultHttpClient() {
    }

    public Map post_with_params(AuthConfig config, String url, Map<String, Object> params) throws JsonProcessingException, UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        Map<String,Object> finalParams= AuthSignature.getAsMap(config,params);
        byte[] signBase= AuthSignature.generateAuthSignature(config.getPartnerKey().getBytes("UTF-8"),url,finalParams);
        httpPost.setHeader("Authorization",(String.format("%032x", new BigInteger(1, signBase))));
        httpPost.setHeader("Content-Type","application/json");
        return this.postEntity(finalParams,httpPost);
    }

    public Map post_with_no_params(AuthConfig config, String url) throws JsonProcessingException, UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        Map<String,Object> finalParams= AuthSignature.getAsMap(config);
        byte[] signBase= AuthSignature.generateAuthSignature(config.getPartnerKey().getBytes("UTF-8"),url,finalParams);
        httpPost.setHeader("Authorization",(String.format("%032x", new BigInteger(1, signBase))));
        httpPost.setHeader("Content-Type","application/json");
        return this.postEntity(finalParams,httpPost);
    }

    private Map postEntity(Map<String, Object> objectForJson, HttpEntityEnclosingRequestBase httpPost) {
        try {
            HttpEntity entity = new ByteArrayEntity(this.mapper.writeValueAsBytes(objectForJson), ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            return this.getEntityAndReleaseConnection(httpPost, Map.class);
        } catch (JsonProcessingException var4) {
            throw new RuntimeException(var4);
        }
    }

    private <T> T getEntityAndReleaseConnection(HttpRequestBase httpRequest, Class<T> objectClass) {
        Object var6;
        try {
            HttpResponse httpResponse = this.httpClient.execute(httpRequest);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity == null) {
                throw new RuntimeException("Error retrieving results from http request");
            }

            Object result = this.mapper.readValue(httpEntity.getContent(), Object.class);
            if (!objectClass.isInstance(result)) {
                throw new RuntimeException("Can't parse retrieved object: " + result.toString());
            }

            var6 = objectClass.cast(result);
        } catch (IOException var10) {
            throw new RuntimeException(var10);
        } finally {
            httpRequest.releaseConnection();
        }

        return (T) var6;
    }
}

