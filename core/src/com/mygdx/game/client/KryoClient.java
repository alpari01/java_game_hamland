package com.mygdx.game.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.packets.PacketCheckPlayerNicknameUnique;
import com.mygdx.game.packets.PacketMessage;

import java.io.IOException;


public class KryoClient extends Listener {

    private final Client client;  // Client object.

    // Ports to connect on.
    static int tcpPort = 27960;
    static int udpPort = 27960;
    static String ip = "localhost";  // Change this IP later.

    public KryoClient() {
        client = new Client();  // Create the client.

        // Register all the packets here
        client.getKryo().register(PacketMessage.class);  // Register the packet object.
        client.getKryo().register(PacketCheckPlayerNicknameUnique.class);
    }

    /**
     * Connect the client to specified server.
     *
     */
    public void connectToServer() {
        client.start();  // Start the client.

         try {
             client.connect(5000, ip, tcpPort, udpPort);  // 5000 (ms) - connection timeout.
         } catch (IOException e) {
             System.out.println("Could not connect to the server :(");
             e.printStackTrace();
         }

        client.addListener(new KryoClient());  // Add new listener.

        // For debug
        System.out.println("Connection successful!\nI am now connected to the server.\nServer IP is: " + ip);
    }

    public void sendPacketCheckNickname(String playerNickname) {
        PacketCheckPlayerNicknameUnique packetCheckNickname = new PacketCheckPlayerNicknameUnique();
        packetCheckNickname.playerNickname = playerNickname;
        client.sendTCP(packetCheckNickname);
    }

    // Run this method when client receives any packet from the server.
    public void received(Connection c, Object p) {
        if (p instanceof PacketMessage) {
            // Cast the received packet object to receive its message.
            PacketMessage packet = (PacketMessage) p;

            // Also for debug
            System.out.println("Server reply: " + packet.message);
        }
    }
}
