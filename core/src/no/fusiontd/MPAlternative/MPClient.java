package no.fusiontd.MPAlternative;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

import no.fusiontd.FusionTD;


public class MPClient extends Listener{
    private static Client client;
    private int timeout = 5000;
    private int tcpPort = 54555;
    private int udpPort = 54556;
    private String serverIP;
    private static PacketCreator packetCreator;
    private String playerName, mapName;
    private FusionTD game;

    public MPClient(String serverIP, FusionTD game, String playerName) {
        this.serverIP = serverIP;
        this.game = game;
        this.playerName = playerName;
        packetCreator = new PacketCreator();
        client = new Client();
        client.addListener(this);
        registerPackets();

        new Thread(client).start();

        try {
            client.connect(timeout, this.serverIP, tcpPort, udpPort);
        } catch (IOException e) {
            e.printStackTrace();
            client.stop();
        }
    }

    private void registerPackets(){
        Kryo kryo = client.getKryo();
        kryo.register(java.util.ArrayList.class);
        kryo.register(Packet.Packet0LoginRequest.class);
        kryo.register(Packet.Packet1LoginAnswer.class);
        kryo.register(Packet.Packet2Message.class);
        kryo.register(Packet.Packet3Creep.class);
        kryo.register(Packet.Packet4Lives.class);
        kryo.register(Packet.Packet5score.class);
        kryo.register(Packet.Packet6HighScore.class);
        kryo.register(Packet.Packet7TowerPlaced.class);
        kryo.register(Packet.Packet8Meta.class);
        kryo.register(Packet.Packet9PlayerList.class);
        kryo.register(Packet.Packet10RequestPlayerList.class);
        kryo.register(Packet.Packet11RequestOpponent.class);
        kryo.register(Packet.Packet12OpponentAnswer.class);
    }

    public void received(Connection c, Object o) {
        if( o instanceof Packet.Packet1LoginAnswer){
            if(((Packet.Packet1LoginAnswer) o).accepted){
                System.out.println("Logged in");
            }
        }
        else if (o instanceof Packet.Packet2Message) {
            String message = ((Packet.Packet2Message) o).message;
            //System.out.println(message);
            c.sendUDP(o);
        }

        else if( o instanceof FrameworkMessage.KeepAlive){
            //System.out.println("Stayin' Aliiiiiiiiiiiiiiiiiive!!!!!!!!!!!");
        }

        else if( o instanceof Packet.Packet3Creep){
            //Create creep or something
        }

        else if(o instanceof Packet.Packet4Lives){
            //Update lives
        }

        else if ( o instanceof Packet.Packet8Meta){
            this.mapName = ((Packet.Packet8Meta) o).mapName;
            System.out.println("Launching game on map: " + ((Packet.Packet8Meta) o).mapName);
        }
    }

    public void login(){
        Packet.Packet0LoginRequest loginRequest = packetCreator.createLoginRequest(playerName);
        client.sendUDP(loginRequest);
    }


    public void sendTower(String towerType, float xpos, float ypos){
        Packet.Packet7TowerPlaced towerPacket = packetCreator.createTowerPacket(towerType, xpos, ypos);
        client.sendUDP(towerPacket);
    }

    public void sendLives(int lives){
        Packet.Packet4Lives lPacket = packetCreator.createLivesPacket(lives);
        client.sendUDP(lPacket);
    }

    public void sendCreeps(int creepnum){
        Packet.Packet3Creep creepPacket = packetCreator.createCreepPacket(creepnum);
        client.sendUDP(creepPacket);
    }

    public String getMapName(){
        return mapName;
    }
}
