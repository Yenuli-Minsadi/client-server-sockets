package edu.clientserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

            try {
                ServerSocket serverSocket = new ServerSocket(3000);
                System.out.println("server started");

                Socket localSocket = serverSocket.accept();//accepted request has been assigned to local socket after creating local socket
                System.out.println("client accepted");
                DataInputStream dataInputStream = new DataInputStream(localSocket.getInputStream());//get and read received data stream
                DataOutputStream dataOutputStream = new DataOutputStream(localSocket.getOutputStream());

                Thread readThread = new Thread(()->{

                boolean flag = true;
                while (flag) {

                    String message = null;//convert data to readable string
                    try {
                        message = dataInputStream.readUTF();
                        if (message.equalsIgnoreCase("exit")) {
                            localSocket.close();
                            System.exit(0);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Client: " + message);
                }
                });

                Thread writeThread = new Thread(()->{
                    boolean flag = true;
                    while (flag) {
                        System.out.print("Input message: ");
                        String input = scanner.next();
                        try {
                            dataOutputStream.writeUTF(input);
                            dataOutputStream.flush();

                            if (input.equalsIgnoreCase("exit")) {
                                localSocket.close();
                                System.exit(0);
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                    readThread.start();
                    writeThread.start();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
}
