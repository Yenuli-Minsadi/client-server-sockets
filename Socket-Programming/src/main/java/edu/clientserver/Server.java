package edu.clientserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(3000);
            System.out.println("server started");

            Socket localSocket = serverSocket.accept();//accepted request has been assigned to local socket after creating local socket
            System.out.println("client accepted");

            DataInputStream dataInputStream = new DataInputStream(localSocket.getInputStream());//get and read received data stream
            String message = dataInputStream.readUTF();//convert data to readable string
            System.out.println("Client: "+message);

            localSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
