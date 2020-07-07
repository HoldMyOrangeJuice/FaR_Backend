package HoldMyAppleJuice.client.discord;

public enum DiscordUserStatus
{
    USER_JOINED_VOICE(),
    USER_SEND_CODE(),
    USER_UNMUTED_HEADPHONES,
    USER_UNMUTED_MIC,
    USER_END_STREAM,
    USER_START_STREAM,
    USER_MUTED_HEADPHONES,
    USER_MUTED_MIC,
    USER_LEFT_VOICE;

    public static boolean isStatus(String header)
    {
        for (DiscordUserStatus status : DiscordUserStatus.values())
        {
            if (status.toString().equals(header))return true;
        }
        return false;
    }
}
