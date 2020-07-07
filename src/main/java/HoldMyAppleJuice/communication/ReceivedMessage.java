package HoldMyAppleJuice.communication;

public class ReceivedMessage
{
    String raw;
    String header;
    String[] values;

    public ReceivedMessage(String message)
    {
        raw = message;
        header = message.split("::")[0];
        values = message.split("::")[1].split("!!");
    }

    public String getValue(int i)
    {
        if (! (i < values.length)) return "";
        return values[i];
    }

    public String getHeader()
    {
        return header;
    }


}
