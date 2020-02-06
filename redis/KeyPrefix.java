package com.ygc.miaosha.redis;

public interface KeyPrefix {

    int expireSeconds();

    String getPrefix();
}
