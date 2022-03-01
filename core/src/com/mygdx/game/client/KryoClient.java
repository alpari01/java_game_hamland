package com.mygdx.game.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.packets.PacketMessage;

import java.io.IOException;


public class KryoClient extends Listener {

    static Client client;  // Client object.
    static String ip = "localhost";  // Change this IP later

    // Ports to connect on.
    static int tcpPort = 27960;
    static int udpPort = 27960;

    static boolean gotPacket = false;

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Connecting to the server...");

        client = new Client();  // Create the client.

        // Register all the packets here
        client.getKryo().register(PacketMessage.class);  // Register the packet object.

        client.start();  // Start the client.

        client.connect(5000, ip, tcpPort, udpPort);  // 5000 (ms) - connection timeout.

        client.addListener(new KryoClient());

        // For debug
        System.out.println("Connection successful!\nI am now connected to the server.\nServer IP is: " + ip);

        while (!gotPacket) {

        }
    }

    // Run this method when client receives any packet.
    public void received(Connection c, Object p) {
        if (p instanceof PacketMessage) {
            // Cast the received packet object to receive its message.
            PacketMessage packet = (PacketMessage) p;
            // Also for debug
            System.out.println("Server reply: " + packet.message);
        }

        gotPacket = true;
    }
}
