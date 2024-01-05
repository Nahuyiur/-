package Filepart;

import Core.threeMatch;

import java.util.ArrayList;
import java.util.List;

import static Filepart.TimeRecording.nowTime;

public class createList {
    public static List<String> createGameList(String name, int stage, int coin, int remainingstep, int score1, int score2, int score3, threeMatch threematch){
        List<String> gamelist=new ArrayList<>();
        String str0=new String(name+" "+stage+" "+coin+" "+remainingstep+" "+score1+" "+score2+" "+score3);

        gamelist.add(nowTime());
        gamelist.add(str0);

        for (int i = 0; i < threematch.getRow(); i++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < threematch.getCol(); j++) {
                stringBuilder.append(threematch.chars[i][j]);
            }
            String resultString = stringBuilder.toString();
            gamelist.add(resultString);
        }
        return gamelist;
    }

    public static void main(String[] args) {
        threeMatch threeMatch = new threeMatch(8,8);
        char[][] c={{'1','2','3','4','5','6','7','8'},{'1','2','3','4','5','6','7','8'},{'1','2','3','4','5','6','7','8'},{'1','2','3','4','5','6','7','8'},{'1','2','3','4','5','6','7','8'},{'1','2','3','4','5','6','7','8'},{'1','2','3','4','5','6','7','8'},{'1','2','3','4','5','6','7','8'}};
        threeMatch.chars=c;
        List<String> list=createGameList("12",1,1,1,1,1,1,threeMatch);
        System.out.println(list);
    }
}
