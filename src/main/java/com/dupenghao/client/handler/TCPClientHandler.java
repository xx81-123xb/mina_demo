package com.dupenghao.client.handler;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * Created by 杜鹏豪 on 2022/8/23.
 */
@Slf4j
@Data
public class TCPClientHandler extends IoHandlerAdapter {

    private final String values;
//    private IoSession session;

    public TCPClientHandler(String values) {
        this.values = values;
    }

    @Override
    public void sessionOpened(IoSession session) {
        session.write(values);
//        this.session=session;
    }
}
