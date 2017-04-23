package no.fusiontd.MPAlternative;


class PacketCreator {

    static Packet.Packet0LoginRequest createLoginRequest(String playerName) {
        Packet.Packet0LoginRequest loginRequest = new Packet.Packet0LoginRequest();
        loginRequest.playerName = playerName;
        return loginRequest;
    }

    static Packet.Packet1LoginAnswer createLoginAnswer(boolean accept) {
        Packet.Packet1LoginAnswer loginAnswer = new Packet.Packet1LoginAnswer();
        loginAnswer.accepted = accept;
        return loginAnswer;
    }

    static Packet.Packet2Message createMessage(String message) {
        Packet.Packet2Message mPacket = new Packet.Packet2Message();
        mPacket.message = message;
        return mPacket;
    }

    static Packet.Packet3Creep createCreepPacket() {
        Packet.Packet3Creep creepPacket = new Packet.Packet3Creep();
        return creepPacket;
    }

    static Packet.Packet7TowerPlaced createTowerPacket(String towerType, float xpos, float ypos) {
        Packet.Packet7TowerPlaced towerPlaced = new Packet.Packet7TowerPlaced();
        towerPlaced.type = towerType;
        towerPlaced.xpos = xpos;
        towerPlaced.ypos = ypos;
        return towerPlaced;
    }

    static Packet.Packet9TowerUpgrade createTowerUpgradePacket(float xpos, float ypos) {
        Packet.Packet9TowerUpgrade upgrade = new Packet.Packet9TowerUpgrade();
        upgrade.xpos = xpos;
        upgrade.ypos = ypos;
        return upgrade;
    }

    static Packet.Packet8Meta createMetaPacket(String metadata, String mapString) {
        Packet.Packet8Meta metaPacket = new Packet.Packet8Meta();
        metaPacket.mapName = metadata;
        metaPacket.mapAsString = mapString;
        return metaPacket;
    }

    static Packet.Packet10Ready createReadyPacket(boolean ready) {
        Packet.Packet10Ready packet10Ready = new Packet.Packet10Ready();
        packet10Ready.ready = ready;
        return packet10Ready;
    }
}

