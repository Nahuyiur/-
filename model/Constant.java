package model;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public enum Constant {
    CHESSBOARD_ROW_SIZE(8),CHESSBOARD_COL_SIZE(8);

    private final int num;
    Constant(int num){
        this.num = num;
    }

    public int getNum() {
        return num;
    }
    static Map<String, Color> colorMap = new HashMap<>(){{
        put("💎",Color.blue);
        put("⚪",Color.white);
        put("▲",Color.green);
        put("🔶",Color.orange);
        put("💰", Color.yellow);
        put("❌", Color.red);
        put("knock", Color.red);
        put("bomb", Color.red);
        put("all",Color.red);
        put("",Color.black);
        put("icon5",Color.red);
        put("icon6",Color.red);
        //由名字映射到颜色表
    }};
    static Map<String, ImageIcon> imageMap = new HashMap<>(){{
        put("💎", new ImageIcon("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\view\\pattern\\1.jpg"));
        put("⚪", new ImageIcon("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\view\\pattern\\2.jpg"));
        put("▲", new ImageIcon("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\view\\pattern\\3.jpg"));
        put("🔶", new ImageIcon("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\view\\pattern\\4.jpg"));
        put("💰", new ImageIcon("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\view\\pattern\\+.png"));
        put("❌", new ImageIcon("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\view\\pattern\\-.png"));
        put("", new ImageIcon("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\view\\pattern\\背景.jpg"));
        put("knock",new ImageIcon("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\view\\pattern\\hammer.png"));
        put("bomb",new ImageIcon("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\view\\pattern\\bomb.png"));
        put("all",new ImageIcon("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\view\\pattern\\all.jpg"));
        put("icon5",new ImageIcon("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\view\\pattern\\5.jpg"));
        put("icon6",new ImageIcon("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\view\\pattern\\6.png"));
        // 由名字映射到图像
    }};
    public static Map<String, Color> getColorMap() {
        return colorMap;
    }

    public static void setColorMap(Map<String, Color> colorMap) {
        Constant.colorMap = colorMap;
    }
    public static Map<String, ImageIcon> getImageMap() {
        return imageMap;
    }

    public static void setImageMap(Map<String, ImageIcon> imageMap) {
        Constant.imageMap = imageMap;
    }
}
