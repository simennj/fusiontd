package no.fusiontd.MPAlternative;

import no.fusiontd.FusionTD;
import no.fusiontd.MPAlternative.Packet.*;
import no.fusiontd.components.Buyable;
import no.fusiontd.components.Geometry;
import no.fusiontd.game.EntityComponentManager;
import no.fusiontd.game.Player;

import com.badlogic.ashley.core.Entity;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MPServer extends Listener {

    private int timeout = 5000;
    private int udpPort = 54556;
    private int tcpPort = 54555;
    private Server server;
    private static Connection connection;
    private static PacketCreator packetCreator;
    private FusionTD game;
    private String playerName;
    private EntityComponentManager engine;
    private Player mulPlayer;

    public MPServer(FusionTD game, String playerName){
        this.game = game;
        this.playerName = playerName;
        server = new Server();
        packetCreator = new PacketCreator();
        packetCreator = new PacketCreator();
        server.addListener(this);


        try{
            server.bind(tcpPort, udpPort);
        } catch(IOException e){
            e.printStackTrace();
        }

        registerPackets();
        server.start();

    }

    private void registerPackets(){
        Kryo kryo = server.getKryo();
        kryo.register(java.util.ArrayList.class);
        kryo.register(Packet0LoginRequest.class);
        kryo.register(Packet1LoginAnswer.class);
        kryo.register(Packet2Message.class);
        kryo.register(Packet3Creep.class);
        kryo.register(Packet4Lives.class);
        kryo.register(Packet5score.class);
        kryo.register(Packet6HighScore.class);
        kryo.register(Packet7TowerPlaced.class);
        kryo.register(Packet8Meta.class);
        kryo.register(Packet9TowerUpgrade.class);
        kryo.register(Packet10Ready.class);
    }

    public void connected(Connection conn){
        System.out.println("[SERVER] Someone is trying to connect");
        if(connection != null){
            System.out.println("Only one active connection allowed!");
        }
        else{
            connection = conn;
        }
    }

    public void disconnected(Connection conn){
        System.out.println("[SERVER] Someone is trying to disconnect");
        Packet2Message mPacket = new Packet2Message();
        conn.sendUDP(mPacket);
        connection = null;
    }

    public void received(Connection c, Object o) {
        if (o instanceof Packet.Packet0LoginRequest){
            Packet.Packet1LoginAnswer lPacket = new Packet.Packet1LoginAnswer();
            lPacket.accepted = true;
            c.sendUDP(lPacket);
        }
        else if (o instanceof Packet2Message) {
            String message = ((Packet2Message) o).message;
            System.out.println(message);
            c.sendUDP(o);
        }
        else if( o instanceof Packet10Ready){
            if(((Packet10Ready) o).ready){
                System.out.println("Received readyPacket");
            }
        }
        else if( o instanceof Packet7TowerPlaced){
            System.out.println("Received towerPacket");
            String type = ((Packet7TowerPlaced) o).type;
            float towerSettingX = ((Packet7TowerPlaced) o).xpos;
            float towerSettingY = ((Packet7TowerPlaced) o).ypos;
            engine.spawnTower(type , new Geometry(towerSettingX, towerSettingY, 0, .5f));
            Entity towerEntity = engine.getTowerAt(towerSettingX, towerSettingY);
            mulPlayer.addCash(-towerEntity.getComponent(Buyable.class).cost);
        }

        else if ( o instanceof Packet9TowerUpgrade){
            Entity towerEntity = engine.getTowerAt(((Packet9TowerUpgrade) o).xpos, ((Packet9TowerUpgrade) o).ypos);
            engine.upgradeEntity(towerEntity);
            mulPlayer.addCash(-towerEntity.getComponent(Buyable.class).cost);
        }

        else if( o instanceof Packet.Packet3Creep){
            //Create creep or something
        }

        else if(o instanceof Packet.Packet4Lives){
            //Update lives
        }

        else if( o instanceof FrameworkMessage.KeepAlive){
            //System.out.println("Stayin' Aliiiiiiiiiiiiiiiiiive!!!!!!!!!!!");
        }
    }

    public String getIp(){
        String ipAddress = null;
        Enumeration<NetworkInterface> net = null;
        try {
            net = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        while(net.hasMoreElements()){
            NetworkInterface element = net.nextElement();
            Enumeration<InetAddress> addresses = element.getInetAddresses();
            while (addresses.hasMoreElements()){
                InetAddress ip = addresses.nextElement();
                if (ip instanceof Inet4Address){

                    if (ip.isSiteLocalAddress()){

                        ipAddress = ip.getHostAddress();
                    }

                }

            }
        }
        return ipAddress;
    }

    public void sendMetaData(String mapName){
        System.out.println("Sending metadata: " + mapName);
        Packet8Meta metaPacket = packetCreator.createMetaPacket(mapName);
        connection.sendUDP(metaPacket);
    }
    public void sendTower(String towerType, float xpos, float ypos){
        System.out.println("sending towerPacket to Client");
        Packet7TowerPlaced towerPacket = packetCreator.createTowerPacket(towerType, xpos, ypos);
        connection.sendUDP(towerPacket);
    }

    public void sendUpgradeTower(float xpos, float ypos){
        Packet9TowerUpgrade upgrade = packetCreator.createTowerUpgradePacket(xpos, ypos);
        connection.sendUDP(upgrade);
    }

    public void sendLives(int lives){
        Packet4Lives lPacket = packetCreator.createLivesPacket(lives);
        connection.sendUDP(lPacket);
    }

    public void sendCreepWaveStarted(){
        Packet3Creep creepPacket = packetCreator.createCreepPacket();
        connection.sendUDP(creepPacket);
    }

    public void initEngine(EntityComponentManager engine){
        this.engine = engine;
    }

    public Connection getConnection(){
        return connection;
    }

    public Player getMulPlayer(){
        return mulPlayer;
    }

    public void setMulPlayer(Player mulPlayer){
        this.mulPlayer = mulPlayer;
    }
}