package no.fusiontd.MPAlternative;

import no.fusiontd.FusionTD;
import no.fusiontd.MPAlternative.Packet.*;

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
import java.util.Scanner;

public class MPServer extends Listener {

    private int timeout = 5000;
    private int udpPort = 54556;
    private int tcpPort = 54555;
    private Server server;
    private static Connection connection;
    private static PacketCreator packetCreator;
    private FusionTD game;
    private String playerName;

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
        kryo.register(Packet.Packet0LoginRequest.class);
        kryo.register(Packet.Packet1LoginAnswer.class);
        kryo.register(Packet2Message.class);
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

        else if( o instanceof FrameworkMessage.KeepAlive){
            System.out.println("Stayin' Aliiiiiiiiiiiiiiiiiive!!!!!!!!!!!");
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
        Packet8Meta metaPacket = packetCreator.createMetaPacket(mapName);
        connection.sendUDP(metaPacket);
    }
    public void sendTower(String towerType, float xpos, float ypos){
        Packet7TowerPlaced towerPacket = packetCreator.createTowerPacket(towerType, xpos, ypos);
        connection.sendUDP(towerPacket);
    }

    public void sendLives(int lives){
        Packet4Lives lPacket = packetCreator.createLivesPacket(lives);
        connection.sendUDP(lPacket);
    }

    public void sendCreeps(int creepnum){
        Packet3Creep creepPacket = packetCreator.createCreepPacket(creepnum);
        connection.sendUDP(creepPacket);
    }
}