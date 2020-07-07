package HoldMyAppleJuice.client.discord;

import java.util.HashMap;

public class DiscordUser
{
    public String id;
    public static HashMap<String, DiscordUser> users = new HashMap<>();
    public boolean streaming = false;
    public String channel = null;
    public boolean speak = true;
    public boolean listen = true;

    public DiscordUser(String id)
    {
        this.id = id;
        users.put(id, this);
    }

    public static DiscordUser getOrCreate(String id)
    {
        if (users.containsKey(id))
        {
            return users.get(id);
        }
        return new DiscordUser(id);
    }



    public void mute_mic()
    {
        speak = false;
    }

    public void mute_headphones()
    {
        listen = false;
    }

    public void leave_channel()
    {
        channel = null;
    }

    public void join_channel(String channel)
    {
        channel = id;
    }

    public void start_stream()
    {
        streaming = true;
    }

    public void stop_stream()
    {
        streaming = false;
    }

    public void unmute_mic()
    {
        speak = true;
    }

    public void unmute_headphones()
    {
        listen = true;
    }
    public boolean is_listening()
    {
        return listen;
    }

    public boolean is_talking()
    {
        return speak;
    }

    public boolean is_streaming()
    {
        return streaming;
    }
}
