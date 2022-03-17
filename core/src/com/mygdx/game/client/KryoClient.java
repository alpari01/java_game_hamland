package com.mygdx.game.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.packets.*;
import com.mygdx.game.screens.NicknameScreen;

import java.io.IOException;


public class KryoClient extends Listener {

    private final Client client;  // Client object.
    public static boolean isNicknameUnique = false;
    public boolean isToServerConnected = false;
    public static String nickname;
    public static float teammatePositionX = 50f;
    public static float teammatePositionY = 50f;
    public static float teammateRotation;


    // Ports to connect on.
    static int tcpPort = 27960;
    static int udpPort = 27960;
    static String ip = "localhost";  // Change this IP later.

    public KryoClient() {
        client = new Client();  // Create the client.

        // Register all the packets here
        client.getKryo().register(PacketMessage.class);  // Register the packet object.
        client.getKryo().register(PacketCheckPlayerNicknameUnique.class);
        client.getKryo().register(PacketSendPlayerMovement.class);
        client.getKryo().register(PacketUpdatePlayers.class);
        client.getKryo().register(PacketRequestConnectedPlayers.class);
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

        isToServerConnected = true;

        client.addListener(new KryoClient());  // Add new listener.

        // For debug
        System.out.println("Connection successful!\nI am now connected to the server.\nServer IP is: " + ip);
    }

    public void sendPacketCheckNickname(String playerNickname) {
        PacketCheckPlayerNicknameUnique packetCheckNickname = new PacketCheckPlayerNicknameUnique();
        packetCheckNickname.playerNickname = playerNickname;
        client.sendTCP(packetCheckNickname);
    }

    public void sendPlayerMovementInformation(float posX, float posY, float rotation) {
        PacketSendPlayerMovement packetSendPlayerMovement = new PacketSendPlayerMovement();
        packetSendPlayerMovement.playerNickname = nickname;
        packetSendPlayerMovement.playerCurrentPositionX = posX;
        packetSendPlayerMovement.playerCurrentPositionY = posY;
        packetSendPlayerMovement.playerCurrentRotation = rotation;
        client.sendUDP(packetSendPlayerMovement);
    }

    // Run this method when client receives any packet from the server.
    public void received(Connection c, Object p) {
        if (p instanceof PacketMessage) {
            // Cast the received packet object to receive its message.
            PacketMessage packet = (PacketMessage) p;

            // Also for debug
            System.out.println("Server reply: " + packet.message);
        }

        // Server response if player's nickname is unique.
        if (p instanceof PacketCheckPlayerNicknameUnique) {
            PacketCheckPlayerNicknameUnique packet = (PacketCheckPlayerNicknameUnique) p;
            if (packet.isNicknameUnique) {
                isNicknameUnique = true;
                nickname = packet.playerNickname;
            }
            if (!packet.isNicknameUnique) {
                System.out.println("Nickname " + packet.playerNickname + " is not unique -> change it.");
                isNicknameUnique = false;
                NicknameScreen.isWindowOpened = false;
            }
        }

        // Server update players' position packet.
        if (p instanceof PacketUpdatePlayers) {
            PacketUpdatePlayers packet = (PacketUpdatePlayers) p;

            // Takes only other player's coordinates.
            if (!packet.playerNickname.equals(nickname)) {
                teammatePositionX = packet.playerPositionX;
                teammatePositionY = packet.playerPositionY;
                teammateRotation = packet.playerRotation;
            }
        }
    }
}
