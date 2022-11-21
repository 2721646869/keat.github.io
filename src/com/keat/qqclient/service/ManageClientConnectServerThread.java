package com.keat.qqclient.service;

import java.util.HashMap;

/**
 * @author Keat-Jie
 * @version 1.0
 * 管理客户端连接到服务器端的线程
 */
public class ManageClientConnectServerThread {
    //key:用户id
    private static HashMap<String,ClientConnectServerThread> hm = new HashMap<>();
    public static void addClientConnectServerThread(String userId,ClientConnectServerThread clientConnectServerThread){
        hm.put(userId,clientConnectServerThread);
    }
    public static ClientConnectServerThread getClientConnectServerThread(String userId){
        return hm.get(userId);
    }
}
