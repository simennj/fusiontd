package no.fusiontd.MPAlternative;


/**
 * Created by Odd on 14.04.2017.
 */
class PacketCreator {

    Packet.Packet0LoginRequest createLoginRequest(String playerName){
        Packet.Packet0LoginRequest loginRequest = new Packet.Packet0LoginRequest();
        loginRequest.playerName = playerName;
        return loginRequest;
    }

    Packet.Packet1LoginAnswer createLoginAnswer(boolean accept){
        Packet.Packet1LoginAnswer loginAnswer = new Packet.Packet1LoginAnswer();
        loginAnswer.accepted = accept;
        return loginAnswer;
    }

    Packet.Packet2Message createMessage(String message){
        Packet.Packet2Message mPacket = new Packet.Packet2Message();
        mPacket.message = message;
        return mPacket;
    }

    Packet.Packet3Creep createCreepPacket(int creepNumber){
        Packet.Packet3Creep creepPacket = new Packet.Packet3Creep();
        creepPacket.creepnumber = creepNumber;
        return creepPacket;
    }

    Packet.Packet4Lives createLivesPacket(int lives){
        Packet.Packet4Lives livesPacket = new Packet.Packet4Lives();
        livesPacket.lives = lives;
        return livesPacket;
    }

    Packet.Packet5score createScorePacket(int score){
        Packet.Packet5score scorePacket = new Packet.Packet5score();
        scorePacket.score = score;
        return scorePacket;

    }

    Packet.Packet6HighScore createHighScorePacket(String playerName, int finalScore){
        Packet.Packet6HighScore highScore = new Packet.Packet6HighScore();
        highScore.finalScore = finalScore;
        highScore.player = playerName;
        return highScore;
    }

    Packet.Packet7TowerPlaced createTowerPacket(String towerType, float x, float y){
        Packet.Packet7TowerPlaced towerPlaced = new Packet.Packet7TowerPlaced();
        towerPlaced.towerType = towerType;
        towerPlaced.x = x;
        towerPlaced.y = y;
        return towerPlaced;
    }

    Packet.Packet8Meta createMetaPacket(String metadata){
        Packet.Packet8Meta metaPacket = new Packet.Packet8Meta();
        metaPacket.mapName = metadata;
        return metaPacket;
    }

    Packet.Packet10Ready createReadyPacket(boolean ready){
        Packet.Packet10Ready packet10Ready = new Packet.Packet10Ready();
        packet10Ready.ready = ready;
        return packet10Ready;
    }
}

