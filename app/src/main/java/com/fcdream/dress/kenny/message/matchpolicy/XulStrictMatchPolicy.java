package com.fcdream.dress.kenny.message.matchpolicy;


import com.fcdream.dress.kenny.message.XulMessage;

import java.util.LinkedList;
import java.util.List;


public class XulStrictMatchPolicy implements XulMatchPolicy {

    @Override
    public List<XulMessage> findMatchMessageTypes(XulMessage message) {
        List<XulMessage> result = new LinkedList<XulMessage>();
        result.add(message);
        return result;
    }
}
