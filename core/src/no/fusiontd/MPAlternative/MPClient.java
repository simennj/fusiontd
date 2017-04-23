package no.fusiontd.MPAlternative;

import com.badlogic.ashley.core.Entity;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import no.fusiontd.FusionTD;
import no.fusiontd.MPAlternative.Packet.Packet7TowerPlaced;
import no.fusiontd.MPAlternative.Packet.Packet8Meta;
import no.fusiontd.MPAlternative.Packet.Packet9TowerUpgrade;
import no.fusiontd.components.Geometry;
import no.fusiontd.components.Value;
import no.fusiontd.game.CreepSpawner;
import no.fusiontd.game.EntityComponentManager;
import no.fusiontd.game.Player;

import java.io.IOException;


public class MPClient extends Listener {
    private static Client client;
    private int timeout = 5000;
    private int tcpPort = 54555;
    private int udpPort = 54556;
    private String serverIP;
    private String playerName, mapName, mapAsString;
    private FusionTD game;
    private EntityComponentManager engine;
    private CreepSpawner creepSpawner;
    private Player mulPlayer;
    private int creepWaveNumber;

    public MPClient(String serverIP, FusionTD game, String playerName) {
        this.serverIP = serverIP;
        this.game = game;
        this.mapAsString = "";
        this.mapName = "";
        this.playerName = playerName;
        client = new Client();
        client.addListener(this);
        Packet.registerPackets(client.getKryo());

        new Thread(client).start();

        try {
            client.connect(timeout, this.serverIP, tcpPort, udpPort);
        } catch (IOException e) {
            e.printStackTrace();
            client.stop();
        }
    }

    public void received(Connection c, Object o) {
        if (o instanceof Packet.Packet1LoginAnswer) {
            if (((Packet.Packet1LoginAnswer) o).accepted) {
                System.out.println("Logged in");
                Packet.Packet10Ready ready = PacketCreator.createReadyPacket(true);
                client.sendUDP(ready);
            }
        } else if (o instanceof Packet.Packet2Message) {
            String message = ((Packet.Packet2Message) o).message;
            //System.out.println(message);
            client.sendUDP(o);
        } else if (o instanceof Packet7TowerPlaced) {
            System.out.println("Received towerPacket");
            String type = ((Packet7TowerPlaced) o).type;
            float towerSettingX = ((Packet7TowerPlaced) o).xpos;
            float towerSettingY = ((Packet7TowerPlaced) o).ypos;
            engine.spawnTower(type, new Geometry(towerSettingX, towerSettingY, 0, .5f));
            Entity towerEntity = engine.getTowerAt(((Packet7TowerPlaced) o).xpos, ((Packet7TowerPlaced) o).ypos);
            mulPlayer.addCash(-towerEntity.getComponent(Value.class).cost);
        } else if (o instanceof Packet9TowerUpgrade) {
            Entity towerEntity = engine.getTowerAt(((Packet9TowerUpgrade) o).xpos, ((Packet9TowerUpgrade) o).ypos);
            engine.upgradeEntity(towerEntity);
            mulPlayer.addCash(-towerEntity.getComponent(Value.class).cost);
        } else if (o instanceof Packet.Packet3Creep) {
            creepWaveNumber++;
            creepSpawner.startNextWave();
        } else if (o instanceof Packet.Packet8Meta) {
            this.mapName = ((Packet.Packet8Meta) o).mapName;
            this.mapAsString = ((Packet8Meta) o).mapAsString;
            System.out.println("Launching game on map: " + ((Packet.Packet8Meta) o).mapName);
        }
        /*else if( o instanceof FrameworkMessage.KeepAlive){
            //System.out.println("Stayin' Aliiiiiiiiiiiiiiiiiive!!!!!!!!!!!");
        }*/
    }

    public void login() {
        Packet.Packet0LoginRequest loginRequest = PacketCreator.createLoginRequest(playerName);
        client.sendUDP(loginRequest);
    }


    public void sendTower(String towerType, float xpos, float ypos) {
        System.out.println("sending towerPacket to Server");
        Packet7TowerPlaced towerPacket = PacketCreator.createTowerPacket(towerType, xpos, ypos);
        client.sendUDP(towerPacket);
    }

    public void sendUpgradeTower(float xpos, float ypos) {
        Packet9TowerUpgrade upgrade = PacketCreator.createTowerUpgradePacket(xpos, ypos);
        client.sendUDP(upgrade);
    }

    public String getMapName() {
        return mapName;
    }

    public void initEngine(EntityComponentManager engine) {
        this.engine = engine;
    }

    public void initCreepSpawner(CreepSpawner creepSpawner) {
        this.creepSpawner = creepSpawner;
    }

    public void stopClient() {
        client.stop();
    }

    public Player getMulPlayer() {
        return mulPlayer;
    }

    public void setMulPlayer(Player mulPlayer) {
        this.mulPlayer = mulPlayer;
    }

    public void close() {
        client.close();
    }

    public String getMapAsString() {
        return mapAsString;
    }

    public int getCreepWaveNumber() {
        return creepWaveNumber;
    }
}
