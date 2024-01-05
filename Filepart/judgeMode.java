package Filepart;

import java.util.List;

public class judgeMode {
    public static int judgemode(List<String> gamelist){
        int mode=0;
        for (int i = 2; i < gamelist.size() ; i++) {
            String str=(String) gamelist.get(i);
            for (int j = 0; j < str.length(); j++) {
                if(str.charAt(j)=='+')mode=1;
                if(str.charAt(j)=='-')mode=2;
            }
        }
        return mode;
    }
}
