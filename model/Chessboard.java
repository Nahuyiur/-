package model;

import Core.threeMatch;
import Enters.Enterframe;
import MusicController.MusicEffectAdjuster;
import model.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class store the real chess information.
 * The Chessboard has 8 * 8 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;

    public threeMatch threematch ;

    public int row=Constant.CHESSBOARD_ROW_SIZE.getNum();
    public int col=Constant.CHESSBOARD_COL_SIZE.getNum();


    public int sum = 0;

    public static int countSteps = 0;

    public static int level=1 ;

    public static int levelScore ;

    public static int remainingSteps;

    public static int totalScore ;

    public static int playerStage;

    public static  int score1;

    public static  int score2;
     public  static int score3;
    public static  int swapCount = 0;

    public static int coinGoal = 0;
    public static int pausetime =1000;
    public static MusicEffectAdjuster musicEffectAdjuster = new MusicEffectAdjuster();
    //无参构造方法
    public Chessboard() {
        this.threematch=new threeMatch(Constant.CHESSBOARD_ROW_SIZE.getNum(),Constant.CHESSBOARD_COL_SIZE.getNum());
        this.grid = new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];
        //grid数组被new出来
        initGrid();//grid数组的每一个cell元素被new出来
        initPieces();//grid中每一个cell 获得了棋子chess piece
        musicEffectAdjuster.setVisible(false);
    }
    public Chessboard(threeMatch threematch) {
        this.threematch=new threeMatch(Constant.CHESSBOARD_ROW_SIZE.getNum(),Constant.CHESSBOARD_COL_SIZE.getNum());
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                this.threematch.chars[i][j]=threematch.chars[i][j];
            }
        }
        this.grid = new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];
        //grid数组被new出来
        initGrid();//grid数组的每一个cell元素被new出来
        initPieces();//grid中每一个cell 获得了棋子chess piece
        musicEffectAdjuster.setVisible(false);
    }
    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private void initPieces() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j].setPiece(new ChessPiece(threematch.chars[i][j]));
                //如此实现了每一次初始化时的棋子全都是没有3个连着的
            }
        }
    }

    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
        //由一个位置返回所对应的棋子
    }

    public Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
        //返回网格cell
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
        //计算曼哈顿距离
    }

    public ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
        //把一个位置的棋子移除
    }

    public void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
        //在网格cell的一个位置放上棋子chess piece
    }

    public void PauseDraw(){
        Timer timer = new Timer(pausetime, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < threematch.chars.length; i++) {
                    for (int j = 0; j <threematch.chars.length ; j++) {
                        grid[i][j].getPiece().setColor(Constant.colorMap.get(ChessPiece.nameDecide(threematch.chars[i][j])));
                        grid[i][j].getPiece().setName(ChessPiece.nameDecide(threematch.chars[i][j]));
                        grid[i][j].getPiece().chessComponent.revalidate();
                        grid[i][j].getPiece().chessComponent.repaint();
                    }
                }
            }
        });

        timer.setRepeats(false);
        timer.start();
    }//设置了一个延迟画图的方法
    public void drawAgain(){
        for (int i = 0; i < threematch.chars.length; i++) {
            for (int j = 0; j <threematch.chars.length ; j++) {
                grid[i][j].getPiece().setColor(Constant.colorMap.get(ChessPiece.nameDecide(threematch.chars[i][j])));
                grid[i][j].getPiece().setName(ChessPiece.nameDecide(threematch.chars[i][j]));
                grid[i][j].getPiece().chessComponent.revalidate();
                grid[i][j].getPiece().chessComponent.repaint();
            }
        }
    }
    public void swapChessPiece(ChessboardPoint point1, ChessboardPoint point2) {

        int x1 = point1.getRow();
        int y1 = point1.getCol();

        int x2 = point2.getRow();
        int y2 = point2.getCol();

        char temps = threematch.chars[x1][y1];
        threematch.chars[x1][y1] = threematch.chars[x2][y2];
        threematch.chars[x2][y2] = temps;

        for (int i = 0; i < threematch.chars.length; i++) {
            for (int j = 0; j <threematch.chars.length ; j++) {
                grid[i][j].getPiece().setColor(Constant.colorMap.get(ChessPiece.nameDecide(threematch.chars[i][j])));
                grid[i][j].getPiece().setName(ChessPiece.nameDecide(threematch.chars[i][j]));
                grid[i][j].getPiece().chessComponent.revalidate();
                grid[i][j].getPiece().chessComponent.repaint();
            }
        }


        boolean flag = canSwap(point1, point2);
        if(flag){
            musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\音效.wav");
            threematch.caculateSum();//caculate之后才变成'0'
            this.changeScore();
            Chessboard.swapCount++;
            Enterframe.threeMatch0 = threematch;



            if(threematch.chars[x1][y1]=='a'&&threematch.chars[x2][y2]!='a'){
                threematch.removeSamePiece(threematch.chars[x2][y2]);
                threematch.chars[x1][y1]='0';
                musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\猫头鹰音效.wav");
            }
            if(threematch.chars[x1][y1]!='a'&&threematch.chars[x2][y2]=='a'){
                threematch.removeSamePiece(threematch.chars[x1][y1]);
                threematch.chars[x2][y2]='0';
                musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\猫头鹰音效.wav");
            }
            if(threematch.chars[x1][y1]=='a'&&threematch.chars[x2][y2]=='a'){
                musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\猫头鹰音效.wav");
                threematch.removAllPiece();
            }
            remainingSteps--;
            countSteps++;
            PauseDraw();
            sum++;
        }else{
            JOptionPane.showMessageDialog(null,"You failed to swap!","提示",JOptionPane.WARNING_MESSAGE);
            char temp2 = threematch.chars[x1][y1];
            threematch.chars[x1][y1] = threematch.chars[x2][y2];
            threematch.chars[x2][y2] = temp2;
            Enterframe.threeMatch0 = threematch;
            //把所有的重新画
            drawAgain();
        }
    }
    public boolean canSwap(ChessboardPoint point1, ChessboardPoint point2){
        int x1=point1.getRow(),y1=point1.getCol();
        int x2=point2.getRow(),y2=point2.getCol();

        if(((threematch.chars[x1][y1]!='a'&&threematch.chars[x2][y2]!='a')&&!threeMatch.isMatched(threematch.chars, threematch.state, threematch.row, threematch.col,threematch.isAll)||threematch.chars[x1][y1]=='-'||threematch.chars[x2][y2]=='-')){
            System.out.println("There doesn't exist 3-match!\nPlease change selection to swap.");
            return false;
        }else{
            System.out.println("Swap successfully!");
        }
        return true;
    }

    public void changeScore(){
        if(Chessboard.playerStage == 1 || Chessboard.playerStage == 4 || Chessboard.playerStage == 7){
            Chessboard.score1 = threematch.score;
        } else if (Chessboard.playerStage == 2 || Chessboard.playerStage == 5 || Chessboard.playerStage == 8) {
            Chessboard.score2 = threematch.score;
        }else{
            Chessboard.score3 = threematch.score;
        }
    }

    public Cell[][] getGrid() {
        return grid;
    }
    public threeMatch getThreematch(){//threematch是字符记录及其一系列数据
        return threematch;
    }







}
