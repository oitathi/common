package com.b2wdigital.fazemu.enumeration;

public enum RequestHeaderEnum {

    USER("X-B2W-UserId"),
    USER_TOKEN("X-B2W-Token"),
    SYSTEM_NAME("X-B2W-System"),
    API_NAME("X-B2W-ApiName"),
    AUTHORIZATION("Authorization"),
    TID("X-TID"),
    CUSTOMER_TOKEN("Access-Token"),
    AUTHORIZATION_USER_PERMISSIONS("X-Authorization-UserPermissions");

    private final String value;

    private RequestHeaderEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
