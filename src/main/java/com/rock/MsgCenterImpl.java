package com.rock;

import com.rock.classLoader.MsgValidater;

public class MsgCenterImpl implements MsgCenter {
    @Override
    public boolean sendMsg(String msg) {
        return new MsgValidater().validate(msg);
    }
}
