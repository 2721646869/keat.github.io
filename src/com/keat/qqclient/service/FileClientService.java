package com.keat.qqclient.service;

import com.keat.qqcommon.Message;
import com.keat.qqcommon.MessageType;

import java.io.*;

/**
 * @author Keat-Jie
 * @version 1.0
 * 文件传输服务
 */
public class FileClientService {
    /**
     *
     * @param src 源文件
     * @param dest 传输到哪里
     * @param senderId 发送者
     * @param getterId 接收者
     */
    public void sendFileToOne(String src,String dest,String senderId,String getterId){
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_FILE_MES);
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setSrc(src);
        message.setDest(dest);
        FileInputStream fileInputStream = null;
        byte[] fileBytes = new byte[(int) new File(src).length()];
        try {
            fileInputStream = new FileInputStream(src);
            fileInputStream.read(fileBytes);//读入
            message.setFileBytes(fileBytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("\n" + senderId + " 给 " + getterId + " 发送文件: " + src
                + " 到对方电脑的目录 " + dest);
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
