package HoldMyAppleJuice.client;


import HoldMyAppleJuice.client.discord.DiscordUser;
import HoldMyAppleJuice.client.minecraft.MinecraftPlayer;

import java.util.ArrayList;

public class SyncUser
{
    public MinecraftPlayer player;
    public DiscordUser user;
    public static ArrayList<SyncUser>synced = new ArrayList<>();

    public SyncUser(MinecraftPlayer player, DiscordUser user)
    {
        this.player = player;
        this.user = user;
        synced.add(this);
    }

    public static void sync(MinecraftPlayer player, DiscordUser user)
    {
        new SyncUser(player, user);
    }

    public static boolean playerIsSynced(MinecraftPlayer player)
    {
        for (SyncUser su : synced)
        {
            if (su.player == player)
            {
                return true;
            }
        }
        return false;
    }

    public static boolean userIsSynced(DiscordUser user)
    {
        for (SyncUser su : synced)
        {
            if (su.user == user)
            {
                return true;
            }
        }
        return false;
    }

    public static SyncUser getSyncUser(DiscordUser user)
    {
        for (SyncUser su : synced)
        {
            if (su.user == user)
            {
                return su;
            }
        }
        return null;
    }

    public static SyncUser getSyncUser(MinecraftPlayer player)
    {
        for (SyncUser su : synced)
        {
            if (su.player == player)
            {
                return su;
            }
        }
        return null;
    }

    public static MinecraftPlayer getMcPlayer(DiscordUser user)
    {
        SyncUser su = getSyncUser(user);
        if (su == null) return null;
        return su.player;
    }
    public static DiscordUser getDsUser(MinecraftPlayer player)
    {
        SyncUser su = getSyncUser(player);
        if (su == null) return null;
        return su.user;
    }

}
