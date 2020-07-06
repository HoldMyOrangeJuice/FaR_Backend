import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

public class Main
{
    public static void main(String[] args)
    {
        new Server();
        load();

    }
    public static void save()
    {
        JSONObject obj = new JSONObject();
        for (String key : ServerHttpHandler.players.keySet())
        {
            obj.put(key, ServerHttpHandler.players.get(key));
        }

        try
        {
            FileWriter file = new FileWriter("data.json");
            file.write(obj.toJSONString());
            file.close();
        }
        catch (Exception e)
        {

        }

    }

    public static void load()
    {
        try {
            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader("data.json");
            Object obj = jsonParser.parse(reader);
            ServerHttpHandler.players = (HashMap<String, String>) obj;
            System.out.println("loaded players: " + ServerHttpHandler.players);

        }catch (Exception e){}
    }
}
