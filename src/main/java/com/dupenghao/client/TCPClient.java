package com.dupenghao.client;

import com.dupenghao.client.handler.TCPClientHandler;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * Created by 杜鹏豪 on 2022/8/23.
 */
public class TCPClient {
    public static void main(String[] args) {
        IoConnector connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(30000);
        connector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"),
                        LineDelimiter.WINDOWS.getValue(),
                        LineDelimiter.WINDOWS.getValue())));

        connector.setHandler(new TCPClientHandler("你好！\r\n 大家好！"));

        ConnectFuture future = connector.connect(new InetSocketAddress("localhost", 9124));
        future.awaitUninterruptibly();
        IoSession session = future.getSession();
        session.write("中国加油!\r\n");
        Scanner scanner = new Scanner(System.in);
        while (true){
            String line = scanner.nextLine();
            session.write(line);
            if(line.equals("quit")){
                session.close(true).awaitUninterruptibly();
                connector.dispose();
                break;
            }
        }
    }
}
