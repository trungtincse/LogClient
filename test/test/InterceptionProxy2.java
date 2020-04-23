/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author tindang
 */
public class InterceptionProxy2 {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;
        int port = 1234;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Port Error");
            System.exit(-1);
        }
        while (listening) {
            new ProxyThread(serverSocket.accept()).start();
        }
        serverSocket.close();
    }
}

class ProxyThread extends Thread {

    private final Socket clientSocket;

    public ProxyThread(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
            // Read request
            InputStream incommingIS = clientSocket.getInputStream();
            byte[] b = new byte[8196];
            int len = incommingIS.read(b);

            if (len > 0) {
                System.out.println("REQUEST"
                        + System.getProperty("line.separator") + "-------");
                System.out.println(new String(b, 0, len));

                // Write request
                Socket socket = new Socket("10.30.80.220", 5601);
                OutputStream outgoingOS = socket.getOutputStream();
                outgoingOS.write(b, 0, len);

                // Copy response
                OutputStream incommingOS = clientSocket.getOutputStream();
                InputStream outgoingIS = socket.getInputStream();
                for (int length; (length = outgoingIS.read(b)) != -1;) {
                    incommingOS.write(b, 0, length);
                }

                incommingOS.close();
                outgoingIS.close();
                outgoingOS.close();
                incommingIS.close();

                socket.close();
            } else {
                incommingIS.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
