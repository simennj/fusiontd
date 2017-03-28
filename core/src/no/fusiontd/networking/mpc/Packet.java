package no.fusiontd.networking.mpc;


public class Packet {
    public static class Packet0LoginRequest {}
    public static class Packet1LoginAnswer {boolean accepted = false;}
    public static class Packet2Message { String message;}
    public static class Packet3Creep { int creepnumber;}
    public static class Packet4Lives { int lives;}
    public static class Packet5score { int score;}
    public static class Packet6HighScore{ String player; int finalScore;}
}
