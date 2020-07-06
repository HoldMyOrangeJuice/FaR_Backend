


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerHttpHandler implements HttpHandler
{

    public static ArrayList<String>playersOnServer = new ArrayList<String>();
    public static ArrayList<String> playersOnDiscord = new ArrayList<String>();

    public static HashMap<String, String>players = new HashMap<String, String>();


    public static HashMap<String, String>playerCodes = new HashMap<String, String>();


    public void handle(HttpExchange httpExchange) throws IOException
    {
        String requestParamValue=null;

        if("GET".equals(httpExchange.getRequestMethod()))
        {
            requestParamValue = handleGetRequest(httpExchange);
        }
        handleResponse(httpExchange,requestParamValue);
    }




    private String handleGetRequest(HttpExchange httpExchange)
    {
        System.out.println("GOT REQUEST: " + httpExchange.getRequestURI().toString());
        return httpExchange.getRequestURI().toString().split("\\?")[1].split("=")[1];

    }

        private void handleResponse(HttpExchange httpExchange, String requestParamValue)  throws  IOException {


            System.out.println("players1 " + players);

            String request = httpExchange.getRequestURI().toString();

            String s = request.replace("/test/?", "");
            String key = s.split("=")[0];
            String val = s.split("=")[1];

            System.out.println(key + " " + val);

            String out = "";

            if (key.equals("unattach_player"))
            {
                System.out.println("unattaching " + val);
                if (!players.keySet().contains(val))
                {
                    out="not_in_list";
                }
                else
                {
                    players.remove(val);
                    out="removed";
                }
            }

            if (key.equals("player_joined"))
            {
                playersOnServer.add(val);
                if (playerInDiscord(val))
                {
                    out = "in_discord:true";
                }
                else
                {
                    out = "in_discord:false";
                }
            }

            if (key.equals("player_left"))
            {
                if (playersOnServer.contains(val))
                    playersOnServer.remove(val);
            }

            //System.out.println("key " + key + " ? " + key  +  " == confirm_player_code "  + key.equals("player_send_code"));
            if (key.equals("player_send_code"))
            {
                System.out.println(playerCodes);
                // plugin generated this code for player
                String uuid = val.split(":")[0];
                String code = val.split(":")[1];


                if (players.containsKey(uuid))
                {
                    out="player_in_database";
                }
                else
                {
                    System.out.println("saving  player code" + code);
                    playerCodes.put(uuid, code);
                    out="player_added";
                }


            }

            System.out.println("players3 " + players);
            if (key.equals("confirm_player_code"))
            {
                System.out.println("players4 " + players);
                System.out.println("confirming player code");
                System.out.println(playerCodes);

                String ds_id = val.split(":")[0];
                String code_ds = val.split(":")[1];
                for (String uuid : playerCodes.keySet())
                {
                    String code = playerCodes.get(uuid);
                    if (code.equals(code_ds))
                    {
                        System.out.println("code CONFIRMED");
                        playerCodes.remove(uuid);
                        System.out.println("players5 " + players);
                        System.out.println("ds id " + ds_id + " uuid " + uuid);
                        System.out.println(players);

                        players.put(uuid, ds_id);
                        Main.save();
                        System.out.println("saved to REG" + players);
                        out = "code_confirmed";

                        break;
                    }
                }


                 if (out.equals(""))
                 {
                        if (players.values().contains(ds_id))
                        {
                        out = "already_confirmed";
                        }
                        else
                        {
                            out="code_not_confirmed";
                        }
                }
            }


            if (key.equals("is_player_on_discord"))
            {
                if (playerInDiscord(val))
                {
                    out = "in_discord:true";
                }
                else
                {
                    out = "in_discord:false";
                }
            }

            if (key.equals("user_joined_voice"))
            {
                playersOnDiscord.add(val);
            }
            if (key.equals("user_left_voice"))
            {
                if (playersOnDiscord.contains(val))
                playersOnDiscord.remove(val);
            }

            OutputStream outputStream = httpExchange.getResponseBody();

            String htmlResponse = out;
            // this line is a must
            System.out.println( "RESPONDING: " + htmlResponse);
            httpExchange.sendResponseHeaders(200, htmlResponse.length());
            outputStream.write(htmlResponse.getBytes());
            outputStream.flush();
            outputStream.close();
        }

    public boolean playerInDiscord(String uuid)
    {
        return (players.containsKey(uuid) && playersOnDiscord.contains(players.get(uuid)));
    }

}
