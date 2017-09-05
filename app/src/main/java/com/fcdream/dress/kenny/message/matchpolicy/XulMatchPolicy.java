package com.fcdream.dress.kenny.message.matchpolicy;


import com.fcdream.dress.kenny.message.XulMessage;

import java.util.List;

public interface XulMatchPolicy {

    List<XulMessage> findMatchMessageTypes(XulMessage message);
}
