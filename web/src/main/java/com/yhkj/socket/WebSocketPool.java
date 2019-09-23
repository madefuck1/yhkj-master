package com.yhkj.socket;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import org.java_websocket.WebSocket;

/**
 * @Auther: chen
 * @Date: 2019/2/28
 * @Description:
 */
public class WebSocketPool {

    private static Map<String, WebSocket> map = new HashMap<>();
    private static Map<String,String> webSocketUserMap = new HashMap<>();

    /**
     * 存入socket连接
     */
    public static void addConn(String name, WebSocket conn) {
        map.put(name,conn);
        webSocketUserMap.put(getKey(conn),name);
    }

    /**
     * 获取 socket连接对应的用户
     */
    public static String getUserBySocket(WebSocket webSocket){
        return webSocketUserMap.get(getKey(webSocket));
    }

    /**
     * 获取 webSocket唯一连接
     * @param webSocket
     * @return
     */
    private static String getKey(WebSocket webSocket){
        InetSocketAddress remoteSocketAddress = webSocket.getRemoteSocketAddress();
        return remoteSocketAddress.getAddress().getHostAddress()+":"+remoteSocketAddress.getPort();
    };

    /**
     * 取出socket连接
     */
    public static WebSocket getConn(String name) {
        WebSocket webSocket = map.get(name);
        return webSocket;
    }

    /**
     * 给所有用户发信息
     */
    public static void sendToAll(WebSocket webSocket,String msg){
        for (Map.Entry<String, WebSocket> stringWebSocketEntry : map.entrySet()) {
            String key = stringWebSocketEntry.getKey();
            WebSocket value = stringWebSocketEntry.getValue();
            int port = webSocket.getRemoteSocketAddress().getPort();
            if (!String.valueOf(port).equals(key)){
                value.send(port+":"+msg);
            }

        }
    }
}
