package model;


import view.ChessComponent;
import view.ChessboardComponent;

import java.awt.*;

public class ChessPiece {
    // Diamond, Circle, ...
    private String name;
    public char c;

    private Color color;
    public ChessComponent chessComponent;
    //棋子给了name，color属性
    public static String nameDecide(char c){
        String name;
        switch (c){
            case '1':
                name="💎";
                break;
            case '2':
                name="⚪";
                break;
            case '3':
                name="▲";
                break;
            case '4':
                name="🔶";
                break;
            case '+':
                name="💰";
                break;
            case '-':
                name="❌";
                break;
            case '0':
                name="";
                break;
            case 'k':
                name="knock";
                break;
            case 'b':
                name="bomb";
                break;
            case 'a'://a是猫头鹰all
                name="all";
                break;
            case '5':
                name="icon5";
                break;
            case '6':
                name="icon6";
                break;
            default:
                name="";
        }
        return name;
    }

    /*    public ChessPiece(String name) {
        this.name = name;//那几个icon都是名字
        this.color = Constant.colorMap.get(name);
    }*/
    public ChessPiece(char c) {
        this.c=c;
        this.name = nameDecide(c);//那几个icon都是名字
        this.chessComponent=new ChessComponent(ChessboardComponent.CHESS_SIZE,this);
    }
    public String getName() {
        return name;
    }

    public Color getColor(){return color;}

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
