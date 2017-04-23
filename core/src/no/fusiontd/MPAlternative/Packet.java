package no.fusiontd.MPAlternative;


import com.esotericsoftware.kryo.Kryo;

class Packet {
    static void registerPackets(Kryo kryo) {
        kryo.register(Packet0LoginRequest.class);
        kryo.register(Packet1LoginAnswer.class);
        kryo.register(Packet2Message.class);
        kryo.register(Packet3Creep.class);
        kryo.register(Packet7TowerPlaced.class);
        kryo.register(Packet8Meta.class);
        kryo.register(Packet9TowerUpgrade.class);
        kryo.register(Packet10Ready.class);
    }

    static class Packet0LoginRequest {String playerName;}

    static class Packet1LoginAnswer {boolean accepted = false;}

    static class Packet2Message { String message;}

    static class Packet3Creep {}

    static class Packet7TowerPlaced{ String type; float xpos; float ypos;}

    static class Packet8Meta{String mapName; String mapAsString;}

    static class Packet9TowerUpgrade{float xpos; float ypos;}

    static class Packet10Ready{boolean ready;}

}
