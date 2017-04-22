package no.fusiontd.MPAlternative;


class Packet {
    static class Packet0LoginRequest {String playerName;}
    static class Packet1LoginAnswer {boolean accepted = false;}
    static class Packet2Message { String message;}
    static class Packet3Creep {}
    static class Packet4Lives { int lives;}
    static class Packet5score { int score;}
    static class Packet6HighScore{ String player; int finalScore;}
    static class Packet7TowerPlaced{ String type; float xpos; float ypos;}
    static class Packet8Meta{String mapName; String mapAsString;}
    static class Packet9TowerUpgrade{float xpos; float ypos;}
    static class Packet10Ready{boolean ready;}

    //might not need these
}
