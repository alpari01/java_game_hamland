package com.mygdx.game.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.packets.PacketCheckPlayerNicknameUnique;
import com.mygdx.game.packets.PacketMessage;

import java.io.IOException;


public class KryoClient extends Listener {

    static Client client;  // Client object.

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
     * @throws IOException exception in case error occurs
     */
    public void connectToServer() throws IOException {
        client.start();  // Start the client.

        client.connect(5000, ip, tcpPort, udpPort);  // 5000 (ms) - connection timeout.

        client.addListener(new KryoClient());  // Add new listener.

        // For debug
        System.out.println("Connection successful!\nI am now connected to the server.\nServer IP is: " + ip);
    }

    public void sendPacketCheckNickname(PacketCheckPlayerNicknameUnique packet) {

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
