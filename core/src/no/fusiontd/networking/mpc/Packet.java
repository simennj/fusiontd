package no.fusiontd.networking.mpc;


import java.util.List;

public class Packet {
    public static class Packet0LoginRequest {String playerName;}
    public static class Packet1LoginAnswer {boolean accepted = false;}
    public static class Packet2Message { String message;}
    public static class Packet3Creep { int creepNumber;}
    public static class Packet4Lives { int lives;}
    public static class Packet5score { int score;}
    public static class Packet6HighScore{ String player; int finalScore;}
    public static class Packet7TowerPlaced{ String towerType; float x; float y;}
    public static class Packet8Meta{String metadata;}
    public static class Packet9PlayerList{ List<String> availablePlayers;}
    public static class Packet10RequestPlayerList{}
    public static class Packet11RequestOpponent{int receivingPlayerId; int sendingPlayerId; String sendingPlayer;}
    public static class Packet12OpponentAnswer{boolean accepted; int playerId; String playerName;}
}
