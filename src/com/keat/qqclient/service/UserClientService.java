package com.keat.qqclient.service;

import com.keat.qqcommon.Message;
import com.keat.qqcommon.MessageType;
import com.keat.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Keat-Jie
 * @version 1.0
 * 完成用户登录验证和用户注册等功能
 */
public class UserClientService {
    //可能在其他地方用user信息
    private User u = new User();
    private Socket socket;

    //到服务器验证该用户是否合法
    public boolean checkUser(String userId, String pwd) {
        boolean b = false;
        u.setUserId(userId);
        u.setPasswd(pwd);
        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(u);//发送User对象

            //读取message对象
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message) ois.readObject();
            if (ms.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {
                ClientConnectServerThread clientConnectServerThread =
                        new ClientConnectServerThread(socket);
                clientConnectServerThread.start();
                ManageClientConnectServerThread.addClientConnectServerThread(userId, clientConnectServerThread);
                b = true;

            } else {
                //登录失败
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    //请求在线用户列表
    public void onlineFriendList() {
        Message message = new Message();
        message.setSender(u.getUserId());
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        try {
            ObjectOutputStream oos = new ObjectOutputStream
                    (ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId()).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(u.getUserId());
        try {
//            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId()).getSocket().getOutputStream());
            oos.writeObject(message);
            System.out.println(u.getUserId() + " 退出系统");
            System.exit(0);//结束进程

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
