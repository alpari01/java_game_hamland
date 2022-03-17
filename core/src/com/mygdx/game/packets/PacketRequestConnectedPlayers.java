package com.mygdx.game.packets;

import com.mygdx.game.objects.Player;

import java.util.Map;

public class PacketRequestConnectedPlayers {
    public Map<String, Player> allPlayers;
}
