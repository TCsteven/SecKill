package com.ygc.miaosha.redis;

public class UserKey extends BaseKeyPrefix {

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
    private UserKey(String prefix) {
        super(prefix);
    }
}
