package HoldMyAppleJuice.client.discord;

import HoldMyAppleJuice.*;
import HoldMyAppleJuice.client.Client;
import HoldMyAppleJuice.client.SyncUser;
import HoldMyAppleJuice.client.minecraft.MinecraftPlayer;
import HoldMyAppleJuice.communication.ReceivedMessage;
import HoldMyAppleJuice.communication.ServerMessage;
import ProtocolPackage.DiscordUserStatus;
import ProtocolPackage.Protocol;

import java.nio.channels.SocketChannel;

public class DiscordMessageHandler extends Handler {

    @Override
    public ServerMessage handle(ReceivedMessage message, SocketChannel client)
    {

        for (DiscordUserStatus status : DiscordUserStatus.values())
        {
            if (message.getHeader().equals(status.toString()))
            {
                String user_id = message.getValue(0);
                String chanel_id = message.getValue(0);
                DiscordUser user = DiscordUser.getOrCreate(user_id);
                user.join_channel(chanel_id);
                return new ServerMessage(Client.PLUGIN, message.getHeader(), SyncUser.getMcPlayer(user).uuid, chanel_id);
            }
        }



        if (message.getHeader().equals(DiscordUserStatus.USER_LEFT_VOICE.toString()))
        {
            String id = message.getValue(0);
            DiscordUser user =  DiscordUser.getOrCreate(id);
            user.leave_channel();
        }

        if (message.getHeader().equals(DiscordUserStatus.USER_MUTED_MIC.toString()))
        {
            String id = message.getValue(0);
            DiscordUser user = DiscordUser.getOrCreate(id);
            user.mute_mic();
        }

        if (message.getHeader().equals(DiscordUserStatus.USER_MUTED_HEADPHONES.toString()))
        {
            String id = message.getValue(0);
            DiscordUser user = DiscordUser.getOrCreate(id);
            user.mute_headphones();

        }

        if (message.getHeader().equals(DiscordUserStatus.USER_START_STREAM.toString()))
        {
            String id = message.getValue(0);
            DiscordUser user = DiscordUser.getOrCreate(id);
            user.start_stream();
        }

        if (message.getHeader().equals(DiscordUserStatus.USER_END_STREAM.toString()))
        {
            String id = message.getValue(0);
            DiscordUser.getOrCreate(id).stop_stream();
        }

        if (message.getHeader().equals(DiscordUserStatus.USER_UNMUTED_MIC.toString()))
        {
            String id = message.getValue(0);
            DiscordUser.getOrCreate(id).unmute_mic();
        }

        if (message.getHeader().equals(DiscordUserStatus.USER_UNMUTED_HEADPHONES.toString()))
        {
            String id = message.getValue(0);
            DiscordUser.getOrCreate(id).unmute_headphones();
        }

        if (message.getHeader().equals(Protocol.USER_SEND_CODE.toString()))
        {
            String id = message.getValue(0);
            String code = message.getValue(1);
            return Server.confirmCode(DiscordUser.getOrCreate(id), code);
        }

        if (DiscordUserStatus.isStatus(message.getHeader()))
        {
            String id = message.getValue(0);
            DiscordUser user = DiscordUser.getOrCreate(id);
            if (user == null)return null;
            updateMinecraftPlayerStatus(user, message.getHeader());
        }

        return null;
    }

    public ServerMessage updateMinecraftPlayerStatus(DiscordUser user, String header)
    {
        MinecraftPlayer player = SyncUser.getMcPlayer(user);
        if (player!=null)
        {
            return new ServerMessage(Client.PLUGIN, header, SyncUser.getMcPlayer(user).uuid);
        }
        return null;
    }
}
