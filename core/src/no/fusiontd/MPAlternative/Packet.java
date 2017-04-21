package no.fusiontd.MPAlternative;

import com.badlogic.ashley.core.Entity;

import java.util.List;

class Packet {
    static class Packet0LoginRequest {String playerName;}
    static class Packet1LoginAnswer {boolean accepted = false;}
    static class Packet2Message { String message;}
    static class Packet3Creep { int creepnumber;}
    static class Packet4Lives { int lives;}
    static class Packet5score { int score;}
    static class Packet6HighScore{ String player; int finalScore;}
    static class Packet7TowerPlaced{ String type; Entity tower;}
    static class Packet8Meta{String mapName;}
    static class Packet9TowerUpgrade{int towerID;/*smth*/}
    static class Packet10Ready{boolean ready;}

    //might not need these
    static class Packet9PlayerList{ List<String> availablePlayers;}
    static class Packet10RequestPlayerList{}
    static class Packet11RequestOpponent{int receivingPlayerId; int sendingPlayerId; String sendingPlayer;}
    static class Packet12OpponentAnswer{boolean accepted; int playerId; String playerName;}
}
