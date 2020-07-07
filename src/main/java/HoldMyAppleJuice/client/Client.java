package HoldMyAppleJuice.client;


public enum Client
{

    DISCORD("ds"),
    PLUGIN("pl");

    String prefix;

    Client(String s)
    {
        prefix = s;

    }


    String toString(Client c)
    {
        return c.prefix;
    }
    public static Client get(String s)
    {
        for (Client c : Client.values())
        {
            if (c.toString().equals(s))return c;
        }
        return null;
    }


}
