package HoldMyAppleJuice;


import HoldMyAppleJuice.client.SyncUser;
import HoldMyAppleJuice.client.discord.DiscordUser;
import HoldMyAppleJuice.client.minecraft.MinecraftPlayer;

import ProtocolPackage.Client;
import ProtocolPackage.Protocol;
import ProtocolPackage.communication.ServerMessage;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;

public class Server
{
    public static ArrayList<MinecraftPlayer>players;
    public static ArrayList<DiscordUser>users;
    public static HashMap<Client, SocketChannel>consumers;


    public static void registerConsumer(Client type, SocketChannel client)
    {
        consumers.put(type, client);
    }

    public static ServerMessage confirmCode(DiscordUser user, String code)
    {
        for (MinecraftPlayer player : players)
        {
            if (player.code.equals(code))
            {
                if (SyncUser.userIsSynced(user))
                {
                    return new ServerMessage(Client.DISCORD, Protocol.SYNC_SUCCESS.toString(), "");
                }
                SyncUser.sync(player, user);
                return new ServerMessage(Client.DISCORD, Protocol.ALREADY_SYNCED.toString(), "");
            }
        }
        return new ServerMessage(Client.DISCORD, Protocol.WRONG_CODE.toString(), "");
    }
}
