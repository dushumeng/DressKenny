package com.fcdream.dress.kenny.bus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shmdu on 2017/9/3.
 */

public class ParamInfo {

    private Map<String, String> params = new HashMap<>();

    public static ParamInfo create() {
        return new ParamInfo();
    }

    public ParamInfo addParam(String key, String value) {
        params.put(key, value);
        return this;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
