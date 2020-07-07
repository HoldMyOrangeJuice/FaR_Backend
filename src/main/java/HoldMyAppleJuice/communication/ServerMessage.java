package HoldMyAppleJuice.communication;

import HoldMyAppleJuice.client.Client;

import java.util.ArrayList;
import java.util.Arrays;

public class ServerMessage
{
    public static final String PLAYER_IS_REGISTERED = "1";
    public static final String SYNC_SUCCESS = "2";
    public static final String ALREADY_SYNCED = "3";
    public static final String WRONG_CODE = "4";

    public Client receiver;
    public String header;

    public ArrayList<String> values = new ArrayList<>();


    public ServerMessage(Client receiver, String header, String... values)
    {
        this.receiver = receiver;
        this.header = header;
        this.values.addAll(Arrays.asList(values));
    }

    public String format()
    {
        String out = header + Splitter.HEADER;

        for (String val : values)
        {
            out += Splitter.VALUE_SPLITTER + val;
        }
        return out;
    }

}
