package HoldMyAppleJuice.client.minecraft;

import java.util.HashMap;

public class MinecraftPlayer
{

    public String uuid;
    public static HashMap<String, MinecraftPlayer> players = new HashMap<>();
    public boolean online = false;
    public String code = null;

    public MinecraftPlayer(String uuid)
    {
        this.uuid = uuid;
        players.put(uuid, this);
    }

    public static MinecraftPlayer getOrCreate(String uuid)
    {
        if (players.containsKey(uuid))
        {
            return players.get(uuid);
        }
        return new MinecraftPlayer(uuid);
    }

    public void join()
    {
        online = true;
    }

    public void leave()
    {
        online = false;
    }

    public void registerCode(String code)
    {
        this.code = code;
    }


}
