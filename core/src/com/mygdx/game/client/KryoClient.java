package com.mygdx.game.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.objects.Enemy;
import com.mygdx.game.objects.Teammate;
import com.mygdx.game.packets.*;
import com.mygdx.game.screens.NicknameScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class KryoClient extends Listener {

    private final Client client;  // Client object.
    public static boolean isNicknameUnique = false;
    public boolean isToServerConnected = false;
    public static String nickname;

    public static Map<String, Teammate> teammates = new HashMap<>();
    public static Map<Integer, float[]> enemiesData = new HashMap<>();

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
        client.getKryo().register(java.util.ArrayList.class);
        client.getKryo().register(PacketPlayerConnected.class);
        client.getKryo().register(PacketPlayerDisconnected.class);
        client.getKryo().register(PacketUpdateMobsPos.class);
        client.getKryo().register(java.util.HashMap.class);
        client.getKryo().register(float[].class);
        client.getKryo().register(PacketBulletShot.class);
    }

    public Map<String, Teammate> getTeammates() {
        return teammates;
    }

    public Map<Integer, float[]> getEnemiesData() {
        return enemiesData;
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

    public void sendPacketRequestAllPlayersConnected() {
        PacketRequestConnectedPlayers packetRequestConnectedPlayers = new PacketRequestConnectedPlayers();
        packetRequestConnectedPlayers.allPlayers = new ArrayList<>();
        client.sendTCP(packetRequestConnectedPlayers);
    }

    public void sendPacketBulletShot(float bulletRotation) {
        PacketBulletShot packetBulletShot = new PacketBulletShot();
        packetBulletShot.playerWhoShot = nickname;
        packetBulletShot.bulletRotation = bulletRotation;
        client.sendTCP(packetBulletShot);
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

        // Update players' position packet.
        if (p instanceof PacketUpdatePlayers) {
            PacketUpdatePlayers packet = (PacketUpdatePlayers) p;

            // Takes only other player's coordinates.
            if (!packet.playerNickname.equals(nickname)) {
                if (teammates.get(packet.playerNickname) != null) {
                    teammates.get(packet.playerNickname).polygon.setPosition(packet.playerPositionX, packet.playerPositionY);
                    teammates.get(packet.playerNickname).polygon.setRotation(packet.playerRotation);
                }
            }
        }

        // Receive all players that are connected to the server. We use this packet to discover all players.
        if (p instanceof PacketRequestConnectedPlayers) {
            PacketRequestConnectedPlayers packet = (PacketRequestConnectedPlayers) p;

            for (String teammateNickname : packet.allPlayers) {
                if (!teammateNickname.equals(nickname)) {
                    // If players nickname is not the same as this player's nickname (we cannot be a teammate of ourselves :D)
                    addTeammate(teammateNickname);
                }
            }
        }

        // Receive if someone connects to the server after us. We use this packet for others to discover us.
        if (p instanceof PacketPlayerConnected) {
            PacketPlayerConnected packet = (PacketPlayerConnected) p;

            if (!packet.teammateNickname.equals(nickname)) {
                addTeammate(packet.teammateNickname);
            }
        }

        // Receive if someone disconnects from the server.
        if (p instanceof PacketPlayerDisconnected) {
            PacketPlayerDisconnected packet = (PacketPlayerDisconnected) p;
            removeTeammate(packet.disconnectedPlayerNickname);
        }

        // Receive packet that updates all mobs' positions.
        if (p instanceof PacketUpdateMobsPos) {
            PacketUpdateMobsPos packet = (PacketUpdateMobsPos) p;

            // Iterate through updated mobs received from the server.
            for (int mobId : packet.allEnemies.keySet()) {
                float mobPosX = packet.allEnemies.get(mobId)[0];
                float mobPosY = packet.allEnemies.get(mobId)[1];
                float mobType = packet.allEnemies.get(mobId)[2];
                if (!enemiesData.containsKey(mobId)) {
                    // If there is no mob with this ID added yet -> create new key and new float array.
                    enemiesData.put(mobId, new float[]{mobPosX, mobPosY, mobType});
                }
                else {
                    // If mob is already added -> simply update it's data.
                    float[] mobNewData = enemiesData.get(mobId);
                    mobNewData[0] = mobPosX;
                    mobNewData[1] = mobPosY;
                    enemiesData.put(mobId, mobNewData);
                }
            }
        }
    }

    /**
     * Add teammate to teammates hashmap.
     *
     * @param teammateNickname nickname of the teammate
     */
    public void addTeammate(String teammateNickname) {
        if (!teammates.containsKey(teammateNickname)) {
            teammates.put(teammateNickname, null);
        }
    }

    /**
     * Remove teammate from player's teammates.
     *
     * @param teammateNickname teammate to remove
     */
    public void removeTeammate(String teammateNickname) {
        teammates.remove(teammateNickname);
    }
}
