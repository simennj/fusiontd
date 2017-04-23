package no.fusiontd.MPAlternative;

import com.badlogic.ashley.core.Entity;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import no.fusiontd.MPAlternative.Packet.*;
import no.fusiontd.components.Geometry;
import no.fusiontd.components.Value;
import no.fusiontd.game.EntityComponentManager;
import no.fusiontd.game.Player;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


public class MPServer extends Listener {

    private static Connection connection;
    private Server server;
    private EntityComponentManager engine;
    private Player mulPlayer;

    public MPServer() {
        server = new Server();
        server.addListener(this);


        try {
            int tcpPort = 54555;
            int udpPort = 54556;
            server.bind(tcpPort, udpPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Packet.registerPackets(server.getKryo());
        server.start();

    }

    public void connected(Connection conn) {
        System.out.println("[SERVER] Someone is trying to connect");
        if (connection != null) {
            System.out.println("Only one active connection allowed!");
        } else {
            connection = conn;
        }
    }

    public void disconnected(Connection conn) {
        System.out.println("[SERVER] Someone is trying to disconnect");
        Packet2Message mPacket = PacketCreator.createMessage("[SERVER] Someone is trying to disconnect (feeling guilty?) ");
        conn.sendUDP(mPacket);
        connection = null;
    }

    public void received(Connection c, Object o) {
        if (o instanceof Packet.Packet0LoginRequest) {
            System.out.println(((Packet0LoginRequest) o).playerName + " connected to the server");
            c.sendUDP(PacketCreator.createLoginAnswer(true));
        } else if (o instanceof Packet2Message) {
            String message = ((Packet2Message) o).message;
            System.out.println(message);
            c.sendUDP(o);
        } else if (o instanceof Packet10Ready) {
            if (((Packet10Ready) o).ready) {
                System.out.println("Received readyPacket");
            }
        } else if (o instanceof Packet7TowerPlaced) {
            System.out.println("Received towerPacket");
            String type = ((Packet7TowerPlaced) o).type;
            float towerSettingX = ((Packet7TowerPlaced) o).xpos;
            float towerSettingY = ((Packet7TowerPlaced) o).ypos;
            engine.spawnTower(type, new Geometry(towerSettingX, towerSettingY, 0, .5f));
            Entity towerEntity = engine.getTowerAt(towerSettingX, towerSettingY);
            mulPlayer.addCash(-towerEntity.getComponent(Value.class).cost);
        } else if (o instanceof Packet9TowerUpgrade) {
            Entity towerEntity = engine.getTowerAt(((Packet9TowerUpgrade) o).xpos, ((Packet9TowerUpgrade) o).ypos);
            engine.upgradeEntity(towerEntity);
            mulPlayer.addCash(-towerEntity.getComponent(Value.class).cost);
        } else if (o instanceof Packet.Packet3Creep) {
            //Create creep or something
        } else if (o instanceof FrameworkMessage.KeepAlive) {
            //System.out.println("Stayin' Aliiiiiiiiiiiiiiiiiive!!!!!!!!!!!");
        }
    }

    public String getIp() {
        String ipAddress = null;
        Enumeration<NetworkInterface> net = null;
        try {
            net = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        while (net.hasMoreElements()) {
            NetworkInterface element = net.nextElement();
            Enumeration<InetAddress> addresses = element.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress ip = addresses.nextElement();
                if (ip instanceof Inet4Address) {

                    if (ip.isSiteLocalAddress()) {

                        ipAddress = ip.getHostAddress();
                    }

                }

            }
        }
        return ipAddress;
    }

    public void sendMetaData(String mapName, String mapAsString) {
        System.out.println("Sending metadata: " + mapName);
        Packet8Meta metaPacket = PacketCreator.createMetaPacket(mapName, mapAsString);
        connection.sendUDP(metaPacket);
    }

    public void sendTower(String towerType, float xpos, float ypos) {
        System.out.println("sending towerPacket to Client");
        Packet7TowerPlaced towerPacket = PacketCreator.createTowerPacket(towerType, xpos, ypos);
        connection.sendUDP(towerPacket);
    }

    public void sendUpgradeTower(float xpos, float ypos) {
        Packet9TowerUpgrade upgrade = PacketCreator.createTowerUpgradePacket(xpos, ypos);
        connection.sendUDP(upgrade);
    }

    public void sendCreepWaveStarted() {
        Packet3Creep creepPacket = PacketCreator.createCreepPacket();
        connection.sendUDP(creepPacket);
    }

    public void initEngine(EntityComponentManager engine) {
        this.engine = engine;
    }

    public Connection getConnection() {
        return connection;
    }

    public Player getMulPlayer() {
        return mulPlayer;
    }

    public void setMulPlayer(Player mulPlayer) {
        this.mulPlayer = mulPlayer;
    }

    public void stopServer() {
        server.stop();
    }
}