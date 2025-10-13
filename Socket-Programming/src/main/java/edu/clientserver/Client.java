package edu.clientserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Client {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

            try {
                Socket remoteSocket = new Socket("localhost", 3000);
                DataOutputStream dataOutputStream = new DataOutputStream(remoteSocket.getOutputStream());
                DataInputStream dataInputStream = new DataInputStream(remoteSocket.getInputStream());//get and read received data stream

                Thread writeThread = new Thread(()->{
                boolean flag = true;
                while (flag) {

                    System.out.print("Input message: ");
                    String input = scanner.next();
                    try {
                        dataOutputStream.writeUTF(input);
                        dataOutputStream.flush();

                        if (input.equalsIgnoreCase("exit")) {
                            remoteSocket.close();
                            System.exit(0);
                        }
                    } catch (IOException e) {
                        System.exit(0);
                    }
                }
                });

                Thread readThread = new Thread(()-> {
                    boolean flag = true;
                    while (flag) {
                        String message = null;//convert data to readable string
                        try {
                            message = dataInputStream.readUTF();
                            if (message.equalsIgnoreCase("exit")) {
                                remoteSocket.close();
                                System.exit(0);
                            }
                        } catch (IOException e) {
                            System.exit(0);

                        }
                        System.out.println("Server: " + message);
                    }
                });

                readThread.start();
                writeThread.start();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
}