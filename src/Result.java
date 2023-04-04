import java.util.List;

//赛程类
public class Result {
    public List<Courts> courts; //
    public List<Events> events; //
    public List<Matches> matches; //赛程列表
    public List<Teams> teams; //球员所属队的列表
    public List<Players> players; //选手列表

    public class Courts {
        public String uuid;
        public String name;
    }

    public class Events {
        public String name;
        public String uuid;
    }

    public class Matches {
        public String duration; //比赛时间
        public String court_id;
        public List<Teams> teams; //对局列表
        public String event_uuid;
        public Match_status match_status;

        public class Teams {
            public String team_id; //标识一个team的id，为了与另外一个Teams列表关联
            public List<Score> score; //比分列表
            public String status; //标识是否为胜利者

            public class Score {
                public String game; //比分
            }
        }

        public class Match_status {
            public String code; //“C”为正常完成比赛，“W”为弃赛，“R”为退役
        }
    }

    public class Teams {
        public String uuid; //标识一个team的id
        public List<String> players; //球员uuid的列表
    }

    public class Players {
        public String uuid;
        public String short_name; //姓名缩写

        public Nationality nationality;

        public class Nationality {
            public Flag flag;

            public class Flag {
                public String url;
            }
        }

    }
}
