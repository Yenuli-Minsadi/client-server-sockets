package edu.clientserver;

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
            System.out.print("Input message: ");
            String input = scanner.next();
            dataOutputStream.writeUTF(input);

            dataOutputStream.flush();
            remoteSocket.close();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}