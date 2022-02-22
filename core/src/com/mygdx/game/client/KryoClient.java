package com.mygdx.game.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.packets.PacketMessage;

import java.io.IOException;


public class KryoClient extends Listener {

    static Client client;  // Client object.
    static String ip = "localhost";  // Client default ip.

    // Ports to connect on.
    static int tcpPort = 27960;
    static int udpPort = 27960;

    static boolean messageReceived = false;

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Connecting to the server...");

        client = new Client();  // Create the client.

        client.getKryo().register(PacketMessage.class);  // Register the packet object.

        client.start();  // Start the client.

        client.connect(5000, ip, tcpPort, udpPort);  // 5000 (ms) - connection timeout.

        client.addListener(new KryoClient());

        System.out.println("Connection successful!\nThe client program is now waiting for a packet...\n");

        while (!messageReceived) {
            Thread.sleep(1000);
        }
        System.out.println("Client will now disconnect.");
    }

    public void received(Connection c, Object p) {
        if (p instanceof PacketMessage) {
            // Cast the received packet object to receive its message.
            PacketMessage packet = (PacketMessage) p;
            System.out.println("received message from the server, message is: " + packet.message);
            messageReceived = true;
        }
    }
}
