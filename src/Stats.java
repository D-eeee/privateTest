import java.util.List;

public class Stats {
    public List<Players> players;
    public Statistics statistics;
    public class Players {
        public String uuid;
        public String full_name;

        public Hero_image_240 hero_image_240;

        public class Hero_image_240 {
            public String url;
        }
    }

    public class Statistics {
        public List<Rankings> rankings; //对局列表
        public class Rankings {
            public List<Players> players; //对局列表
        }

        public class Players {
            public String player_id;
            public String matches;
            public String value;
            public String rank;
        }
    }

}
