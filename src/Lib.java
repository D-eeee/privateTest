import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Lib {
    public static void parseMale() throws IOException {
        String path = "src/data/stats.json"; //路径
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path) , "UTF-8"));
        Gson gson = new Gson(); //利用Gson解析数据
        Stats stats = gson.fromJson(reader , Stats.class);
        ArrayList<HashMap<String,String>> male = new ArrayList<HashMap<String,String>>();
        for (int i = 0; i < stats.statistics.rankings.get(0).players.size(); i++) { //男子单打
           String str = "";
           HashMap<String, String> map = new HashMap<String, String>();
           for (int j = 0; j < stats.players.size(); j++) {
                if (stats.players.get(j).uuid.equals(stats.statistics.rankings.get(0).players.get(i).player_id)) {
                    str = stats.players.get(j).hero_image_240.url;
                    map.put("image", str);
                    str = stats.players.get(j).full_name;
                    map.put("name", str);
                }
           }
           str = stats.statistics.rankings.get(0).players.get(i).rank;
           map.put("rank", str);
           str = stats.statistics.rankings.get(0).players.get(i).matches;
           map.put("matches", str);
           str = stats.statistics.rankings.get(0).players.get(i).value;
           map.put("aces", str);
           male.add(map);
        }
        String str = gson.toJson(male);
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/data/male.json"));
        bw.write(str);//转化成字符串再写
        bw.close();
    }

    public static void parseFemale() throws IOException {
        String path = "src/data/stats.json"; //路径
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path) , "UTF-8"));
        Gson gson = new Gson(); //利用Gson解析数据
        Stats stats = gson.fromJson(reader , Stats.class);
        ArrayList<HashMap<String,String>> female = new ArrayList<HashMap<String,String>>();
        for (int i = 0; i < stats.statistics.rankings.get(1).players.size(); i++) { //女子单打
            String str = "";
            HashMap<String, String> map = new HashMap<String, String>();
            for (int j = 0; j < stats.players.size(); j++) {
                if (stats.players.get(j).uuid.equals(stats.statistics.rankings.get(1).players.get(i).player_id)) {
                    str = stats.players.get(j).hero_image_240.url;
                    map.put("image", str);
                    str = stats.players.get(j).full_name;
                    map.put("name", str);
                }
            }
            str = stats.statistics.rankings.get(1).players.get(i).rank;
            map.put("rank", str);
            str = stats.statistics.rankings.get(1).players.get(i).matches;
            map.put("matches", str);
            str = stats.statistics.rankings.get(1).players.get(i).value;
            map.put("aces", str);
            female.add(map);
        }
        String str = gson.toJson(female);
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/data/female.json"));
        bw.write(str);//转化成字符串再写
        bw.close();
    }

    public static void parseResult(String date) throws IOException {
        String path = "src/data/result/"+ date + ".json"; //路径
        Gson gson = new Gson(); //利用Gson解析数据
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path) , "UTF-8"));
        Result result = gson.fromJson(reader , Result.class);
        ArrayList<HashMap<String,String>> re = new ArrayList<HashMap<String,String>>();
        for (int i = 0; i < result.matches.size(); i++) {
            String str = "";
            HashMap<String, String> map = new HashMap<String, String>();
            str = result.matches.get(i).duration;
            map.put("time", str);
            for (int j = 0; j < result.events.size(); j++) { //比赛类型
                if (result.matches.get(i).event_uuid.equals(result.events.get(j).uuid)) {
                    str = result.events.get(j).name;
                    map.put("type", str);
                }
            }
            for (int k = 0; k < result.courts.size(); k++) { //比赛地点
                if (result.matches.get(i).court_id.equals(result.courts.get(k).uuid)) {
                    str = result.courts.get(k).name;
                    map.put("court", str);
                }
            }
            str = getShortname(result.matches.get(i).teams.get(0).team_id, result);
            map.put("player1", str);
            str = getShortname(result.matches.get(i).teams.get(1).team_id, result);
            map.put("player2", str);
            str = getFlag(result.matches.get(i).teams.get(0).team_id, result);
            map.put("flag1", str);
            str = getFlag(result.matches.get(i).teams.get(1).team_id, result);
            map.put("flag2", str);
            for (int j = 0; j < result.matches.get(i).teams.size(); j++) {
                String st = "";
                String s = String.valueOf(j);
                s = "game" + s;
                for (int k = 0; k < result.matches.get(i).teams.get(j).score.size(); k++) {
                    st = st + result.matches.get(i).teams.get(j).score.get(k).game + "                ";
                }
                map.put(s, st);
            }
            re.add(map);
        }
        String str = gson.toJson(re);
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/data/29.json"));
        bw.write(str);//转化成字符串再写
        bw.close();
    }

    public static String getShortname(String team_id, Result result) {
        List<String> shortname = new ArrayList<>();
        for (int i = 0; i < result.teams.size(); i++) {
            if (result.teams.get(i).uuid.equals(team_id)){ //利用team_id找到选手相对应的队
                for (int j = 0; j < result.teams.get(i).players.size(); j++) {
                    for (int k = 0; k < result.players.size(); k++) {
                        if (result.players.get(k).uuid.equals(result.teams.get(i).players.get(j))) //利用选手的uuid找到相应的选手
                            shortname.add(result.players.get(k).short_name);
                    }
                }
            }
        }
        String name = shortname.get(0);
        if (shortname.size() > 1) { //该场为团体比赛
            for (int m = 1; m < shortname.size(); m++) {
                name += " & " + shortname.get(m);
            }
        }
        return name;
    }

    public static String getFlag(String team_id, Result result) {
        String flag = "";
        for (int i = 0; i < result.teams.size(); i++) {
            if (result.teams.get(i).uuid.equals(team_id)){ //利用team_id找到选手相对应的队
                for (int j = 0; j < result.teams.get(i).players.size(); j++) {
                    for (int k = 0; k < result.players.size(); k++) {
                        flag = "https://ausopen.com";
                        if (result.players.get(k).uuid.equals(result.teams.get(i).players.get(j))) {
                            flag+=result.players.get(k).nationality.flag.url;
                            return flag;
                        }
                    }
                }
            }
        }
        return "0";
    }

    public static void parseScore(String date) throws IOException {
        String path = "src/data/result/"+ date + ".json"; //路径
        Gson gson = new Gson(); //利用Gson解析数据
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path) , "UTF-8"));
        Result result = gson.fromJson(reader , Result.class);
        ArrayList<HashMap<String,String>> score = new ArrayList<HashMap<String,String>>();
        for (int i = 0; i < result.matches.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            for (int j = 0; j < result.matches.get(i).teams.size(); j++) {
                String str = "";
                String s = String.valueOf(j);
                for (int k = 0; k < result.matches.get(i).teams.get(j).score.size(); k++) {
                    str = str + result.matches.get(i).teams.get(j).score.get(k).game + "     ";
                }
                map.put(s, str);
            }
            score.add(map);
        }
        String str = gson.toJson(score);
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/data/29score.json"));
        bw.write(str);//转化成字符串再写
        bw.close();
    }

    public static void main(String[] args) throws IOException {
        parseMale();
        parseFemale();
        parseResult("0129");
        parseScore("0129");
    }
}

