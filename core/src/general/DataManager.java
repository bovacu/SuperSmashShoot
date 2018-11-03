package general;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    public static boolean connected;
    public static boolean playing;
    public static int partyID = -1;
    public static int music;
    public static int effects;
    public static String textColor;
    public static String uiColor;
    public static String userName;
    public static String font;

    public static void loadData(){
        try(BufferedReader br = new BufferedReader(new FileReader(new File("data/settings.dat")))){
            String line;

            while((line = br.readLine()) != null){
                String keyValue[] = line.split(":");
                switch (keyValue[0]){
                    case "music" :      DataManager.music = Integer.valueOf(keyValue[1]);
                        break;
                    case "effects" :    DataManager.effects = Integer.valueOf(keyValue[1]);
                        break;
                    case "textColor" :  DataManager.textColor = keyValue[1];
                        break;
                    case "ui" :         DataManager.uiColor = keyValue[1];
                        break;
                    case "font" :       DataManager.font = keyValue[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeData(){
        try{
            String line;
            List<String> lines = new ArrayList<>();
            File f1 = new File("data/settings.dat");
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr);
            lines.add(br.readLine());
            lines.add(br.readLine());

            line = br.readLine();
            line = line.replace(line.split(":")[1], String.valueOf(music));
            lines.add(line);

            line = br.readLine();
            line = line.replace(line.split(":")[1], String.valueOf(effects));
            lines.add(line);

            line = br.readLine();
            line = line.replace(line.split(":")[1], textColor);
            lines.add(line);

            line = br.readLine();
            line = line.replace(line.split(":")[1], uiColor);
            lines.add(line);

            fr.close();
            br.close();

            FileWriter fw = new FileWriter(f1);
            BufferedWriter out = new BufferedWriter(fw);
            for(String s : lines) {
                out.write(s);
                out.newLine();
            }
            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String colorToHex(String color){
        switch (color){
            case "WHITE" :  return "ffffffff";
            case "GREEN" :  return "00ff00ff";
            case "BLACK" :  return "000000ff";
            case "YELLOW" : return "ffff00ff";
            case "GRAY" :   return "7f7f7fff";
            case "BROWN" :  return "8b4513ff";
            case "VIOLET" : return "ee82eeff";
            default :       return "";
        }
    }

    public static String hexToColor(String hex){
        switch (hex){
            case "ffffffff" :   return "WHITE";
            case "00ff00ff" :   return "GREEN";
            case "000000ff" :   return "BLACK";
            case "ffff00ff" :   return "YELLOW";
            case "7f7f7fff" :   return "GRAY";
            case "8b4513ff" :   return "BROWN";
            case "ee82eeff" :   return "VIOLET";
            default :       return "";
        }
    }
}
