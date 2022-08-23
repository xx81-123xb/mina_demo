package com.dupenghao.demo;

import com.dupenghao.server.handler.EchoHandler;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by 杜鹏豪 on 2022/8/23.
 */
public class TCPServerDemo_1 {

    public static void main(String[] args) throws IOException {
        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);


        //编写过滤器
        acceptor.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("utf-8")
                        , LineDelimiter.WINDOWS.getValue()
                        , LineDelimiter.WINDOWS.getValue()
                )));

        //设置handler
        acceptor.setHandler(new EchoHandler());

        //绑定端口
        acceptor.bind(new InetSocketAddress(9124));
    }

}
