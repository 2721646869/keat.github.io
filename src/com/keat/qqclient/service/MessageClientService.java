package com.keat.qqclient.service;

import com.keat.qqcommon.Message;
import com.keat.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * @author Keat-Jie
 * @version 1.0
 * 提供和消息相关的服务和方法
 */
public class MessageClientService {
    /**
     *
     * @param content 内容
     * @param senderId 发送者
     */
    public void sendMessageToAll(String content,String senderId){
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_TO_ALL_MES);
        message.setSender(senderId);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        System.out.println(senderId + " 对大家说 " + content);
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param content  内容
     * @param senderId 发送用户id
     * @param getterId 接收用户id
     */



    public void sendMessageToOne(String content, String senderId, String getterId) {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_COMM_MES);
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        System.out.println(senderId + " 对 " + getterId + " 说 " + content);
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
