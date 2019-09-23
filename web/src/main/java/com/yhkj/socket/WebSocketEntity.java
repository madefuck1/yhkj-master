package com.yhkj.socket;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

/**
 * @Auther: chen
 * @Date: 2019/2/28
 * @Description: 前端传参  AA|BB|CC
 * AA: register,chat  注册，聊天
 * BB：注册的用户名(买家id,卖家id)  卖家就是用accountID 买家使用买家id+卖家id
 * CC：聊天内容
 */
public class WebSocketEntity extends WebSocketServer {

    public WebSocketEntity(int port) {
        super(new InetSocketAddress(port));
    }

    public WebSocketEntity(InetSocketAddress address) {
        super(address);
    }


    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        System.out.println();
        System.out.println("==============================打开连接===============================");
    }



    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        System.out.println();
        System.out.println("================================关闭连接==============================");
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        System.out.println();
        System.out.println("==============================接收消息================================");
        System.out.println("接受到的消息："+s);

        InetSocketAddress remoteSocketAddress = webSocket.getRemoteSocketAddress();
        int port = remoteSocketAddress.getPort();
        String[] split = s.split("\\|");
        if (s.startsWith("register")){ // 注册

            WebSocketPool.addConn(split[1],webSocket);
        }else if (s.startsWith("chat")){// 聊天
            WebSocket conn = WebSocketPool.getConn(split[1]);
            // 根据连接获取用户

            conn.send("response|"+WebSocketPool.getUserBySocket(webSocket)+"|"+split[2]);
        }

        if ("@heart".equals(s)){
            System.out.println("-------------接受到心跳包"
            );
        }
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        System.out.println();
        System.out.println("==============================错误==================================");
    }

}
