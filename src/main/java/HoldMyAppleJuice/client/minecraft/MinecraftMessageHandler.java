package HoldMyAppleJuice.client.minecraft;

import HoldMyAppleJuice.*;
import HoldMyAppleJuice.client.Client;
import HoldMyAppleJuice.client.SyncUser;
import HoldMyAppleJuice.communication.ClientMessage;
import HoldMyAppleJuice.communication.ReceivedMessage;
import HoldMyAppleJuice.communication.ServerMessage;

import java.nio.channels.SocketChannel;

public class MinecraftMessageHandler extends Handler
{
    public ServerMessage handle(ReceivedMessage message, SocketChannel client)
    {
        if (message.getHeader().equals(ClientMessage.HANDSHAKE))
        {
            Server.registerConsumer(Client.get(message.getValue(0)), client);
        }
        if (message.getHeader().equals(ClientMessage.PLAYER_JOINED))
        {
            String uuid = message.getValue(0);
            MinecraftPlayer.getOrCreate(uuid).join();
        }
        if (message.getHeader().equals(ClientMessage.PLAYER_LEFT))
        {
            String uuid = message.getValue(0);
            MinecraftPlayer.getOrCreate(uuid).leave();
        }

        if (message.getHeader().equals(ClientMessage.PLUGIN_GENERATED_CODE))
        {
            String uuid = message.getValue(0);
            String code = message.getValue(1);
            MinecraftPlayer player = MinecraftPlayer.getOrCreate(uuid);
            player.code = code;
        }
        if (message.getHeader().equals(ClientMessage.IS_PLAYER_REGISTERED))
        {
            String uuid = message.getValue(0);
            MinecraftPlayer player = MinecraftPlayer.getOrCreate(uuid);
            if (SyncUser.playerIsSynced(player))
            {
                return new ServerMessage(Client.PLUGIN, ServerMessage.PLAYER_IS_REGISTERED, "true");
            }
            else
            {
                return new ServerMessage(Client.PLUGIN, ServerMessage.PLAYER_IS_REGISTERED, "false");
            }

        }
        return null;
    }
}
