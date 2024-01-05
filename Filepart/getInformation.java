package Filepart;

import java.util.List;

public class getInformation {
    public static String getNames(List<String> list) {
        String str = (String) list.get(1);
        String[] tokens = str.split(" ");
        String name = tokens[0];
        return name;
    }

    public static int getStage(List<String> list) {
        String str = (String) list.get(1);
        String[] tokens = str.split(" ");
        int stage = Integer.parseInt(tokens[1]);
        return stage;
    }

    public static int getCoin(List<String> list) {
        String str = (String) list.get(1);
        String[] tokens = str.split(" ");
        int coin = Integer.parseInt(tokens[2]);
        return coin;
    }
    public static int getRemainingStep(List<String> list) {
        String str = (String) list.get(1);
        String[] tokens = str.split(" ");
        int coin = Integer.parseInt(tokens[3]);
        return coin;
    }
    public static int getScore1(List<String> list) {
        String str = (String) list.get(1);
        String[] tokens = str.split(" ");
        int score1 = Integer.parseInt(tokens[4]);
        return score1;
    }

    public static int getScore2(List<String> list) {
        String str = (String) list.get(1);
        String[] tokens = str.split(" ");
        int score2 = Integer.parseInt(tokens[5]);
        return score2;
    }

    public static int getScore3(List<String> list) {
        String str = (String) list.get(1);
        String[] tokens = str.split(" ");
        int score3 = Integer.parseInt(tokens[6]);
        return score3;
    }

    public static char[][] getChars(List<String> list) {
        int wid=list.get(2).length();
        char[][] chars=new char[list.size()-2][wid];

        for (int i = 2; i < list.size(); i++) {
            String str=(String) list.get(i);
            for (int j = 0; j < str.length(); j++) {
                chars[i-2][j]=str.charAt(j);
            }
        }
        return chars;
    }
    public static boolean isMode1(List<String> list){
        /*//char[][] chars=list.getChars();
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars[0].length; j++) {
                if(chars[i][j]=='+')return true;
            }
        }*/
        int coins=getCoin(list);
        if (coins>0)return true;
        //要求刚开始一定要有位置有金币
        return false;
    }
    public static boolean isMode2(List<String> list){
        return true;
    }
}
