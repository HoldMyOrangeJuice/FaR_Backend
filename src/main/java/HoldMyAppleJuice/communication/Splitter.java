package HoldMyAppleJuice.communication;

public enum Splitter
{
    HEADER("::"),
    VALUE_SPLITTER("!!");

    String val;

    Splitter(String s)
    {
        val = s;
    }

    public static String toString(Splitter s)
    {
        return s.val;
    }
}
