package HoldMyAppleJuice;

import ProtocolPackage.communication.ClientMessage;

import ProtocolPackage.communication.ServerMessage;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public abstract class Handler
{
    public static ArrayList<Handler> clientMessageHandlerList = new ArrayList<>();

    public Handler()
    {
        clientMessageHandlerList.add(this);
    }

    public static ArrayList<ServerMessage> handleAll(ClientMessage message, SocketChannel client)
    {
        ArrayList<ServerMessage> result = new ArrayList<>();
        for (Handler handler : clientMessageHandlerList)
        {
            ServerMessage handled = handler.handle(message, client);
            if (handled != null)
            {
                result.add(handled);
            }
        }
        return result;
    }

    public abstract ServerMessage handle(ClientMessage message, SocketChannel client);

}
