package com.taim.conduire.sdk.shopee.auth;

public final class AuthConfig {
    private final String url;
    private final Long partnerId;
    private final String partnerKey;
    private final Long shopId;
    private final Long timestamp;

    public AuthConfig(String url, Long partnerId, String partnerKey, Long shopId, Long timestamp) {
        if (url != null && !url.isEmpty() && partnerId != null && partnerKey != null && shopId != null && timestamp != null){
            this.url=url;
            this.partnerId=partnerId;
            this.partnerKey=partnerKey;
            this.shopId = shopId;
            this.timestamp = timestamp;
        } else {
            throw new IllegalArgumentException("All arguments are required");
        }
    }

    public String getUrl() {
        return url;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public String getPartnerKey() {
        return partnerKey;
    }

    public Long getShopId() {
        return shopId;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
