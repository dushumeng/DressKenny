package com.fcdream.dress.kenny.speech;

/**
 * Copyright (c) 2017, 北京视达科科技有限责任公司 All rights reserved.
 * author：shumeng.du
 * date：2017/9/4
 * description：
 */
public class SpeechSynthesizerError {

    public enum Error {

    }

    final public int code;
    final public String description;

    public SpeechSynthesizerError(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String toString() {
        return "(" + this.code + ")" + this.description;
    }
}
