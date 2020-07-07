package HoldMyAppleJuice.client.minecraft;

import HoldMyAppleJuice.*;

import HoldMyAppleJuice.client.SyncUser;

import ProtocolPackage.Client;
import ProtocolPackage.Protocol;
import ProtocolPackage.communication.ClientMessage;
import ProtocolPackage.communication.ServerMessage;

import java.nio.channels.SocketChannel;

public class MinecraftMessageHandler extends Handler
{
    public ServerMessage handle(ClientMessage message, SocketChannel client)
    {
        if (message.getHeader().equals(Protocol.HANDSHAKE))
        {
            Server.registerConsumer(Client.get(message.getValue(0)), client);
        }
        if (message.getHeader().equals(Protocol.PLAYER_JOINED))
        {
            String uuid = message.getValue(0);
            MinecraftPlayer.getOrCreate(uuid).join();
        }
        if (message.getHeader().equals(Protocol.PLAYER_LEFT))
        {
            String uuid = message.getValue(0);
            MinecraftPlayer.getOrCreate(uuid).leave();
        }

        if (message.getHeader().equals(Protocol.PLUGIN_GENERATED_CODE))
        {
            String uuid = message.getValue(0);
            String code = message.getValue(1);
            MinecraftPlayer player = MinecraftPlayer.getOrCreate(uuid);
            player.code = code;
        }
        if (message.getHeader().equals(Protocol.IS_PLAYER_REGISTERED))
        {
            String uuid = message.getValue(0);
            MinecraftPlayer player = MinecraftPlayer.getOrCreate(uuid);
            if (SyncUser.playerIsSynced(player))
            {
                return new ServerMessage(Client.PLUGIN, Protocol.PLAYER_IS_REGISTERED, "true");
            }
            else
            {
                return new ServerMessage(Client.PLUGIN, Protocol.PLAYER_IS_REGISTERED, "false");
            }

        }
        return null;
    }
}
