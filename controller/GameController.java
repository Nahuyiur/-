package controller;

import Core.Mode0;
import Core.Mode1;
import Core.Mode2;
import Enters.Enterframe;
import Filepart.AddWrite;
import Filepart.CreateTxtFile;
import Filepart.ReadAndOverwrite;
import listener.GameListener;
import model.ChessPiece;
import model.Constant;
import model.Chessboard;
import model.ChessboardPoint;
import Core.threeMatch;
import view.CellComponent;
import view.ChessComponent;
import view.ChessGameFrame;
import view.ChessboardComponent;

import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static Filepart.createList.createGameList;
import static model.Chessboard.*;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and
 * onPlayerClickChessPiece()]
 */
public class GameController implements GameListener {
    public Chessboard model;//model是chessboard类型
    public ChessboardComponent view;

    // Record whether there is a selected piece before
    public ChessboardPoint selectedPoint;
    public ChessboardPoint selectedPoint2;
    private JLabel statusLabel;
    public int row=Constant.CHESSBOARD_ROW_SIZE.getNum();
    public int col=Constant.CHESSBOARD_COL_SIZE.getNum();

    private JLabel stepsLabel;

    private JLabel nameLabel;

    private JLabel totalScore;

    private JLabel playerStage;

    private JLabel coinGoal;
    protected JLabel goal;
    private JLabel currentCoin;
    public static int score1;
    public static int score2;
    public static int score3;

    public JLabel getGoal() {
        return goal;
    }

    public void setGoal(JLabel goal) {
        this.goal = goal;
    }

    public JLabel getCurrentCoin() {
        return currentCoin;
    }

    public void setCurrentCoin(JLabel currentCoin) {
        this.currentCoin = currentCoin;
    }

    public JLabel getCoinGoal() {
        return coinGoal;
    }

    public void setCoinGoal(JLabel coinGoal) {
        this.coinGoal = coinGoal;
    }

    public JLabel getPlayerStage() {
        return playerStage;
    }

    public void setPlayerStage(JLabel playerStage) {
        this.playerStage = playerStage;
    }

    public JLabel getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(JLabel totalScore) {
        this.totalScore = totalScore;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(JLabel nameLabel) {
        this.nameLabel = nameLabel;
    }

    public JLabel getStepsLabel() {
        return stepsLabel;
    }

    public void setStepsLabel(JLabel stepsLabel) {
        this.stepsLabel = stepsLabel;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;

        view.registerController(this);
        view.initiateChessComponent(model);
        view.repaint();
    }
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
    }

    @Override
    public void onPlayerSwapChess() {
        //把currentchessboard改
        currentChessboard();

        if(Chessboard.countSteps == 1){
            JOptionPane.showMessageDialog(null,"You need to click next step!","Operation hint",JOptionPane.WARNING_MESSAGE);
            return;
        } else{
            if(existNull()){
                JOptionPane.showMessageDialog(null,"You need to click next step!","Operation hint",JOptionPane.WARNING_MESSAGE);
                return;
            }else{
                if(threeMatch.isMatched(model.threematch.chars,model.threematch.state,model.threematch.row,model.threematch.col,model.threematch.isAll)){
                    JOptionPane.showMessageDialog(null,"You need to click next step!","Operation hint",JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
        }

        if((Enterframe.modechoose == 0 || Enterframe.modechoose == 2) && Chessboard.remainingSteps == 0 && model.threematch.score < Chessboard.levelScore){
            model.threematch.fillEmpty();
            paintChesspiece();
            model.changeScore();
            this.statusLabel.setText("Currentscore:" + model.threematch.score);
            this.totalScore.setText("Totalscore: " + Chessboard.totalScore );
            judgeFail();
            Enterframe.threeMatch0 = model.threematch;
            musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\失败.wav");
            ChessGameFrame.isrobot=false;
            return;
        }

        if(Enterframe.modechoose == 1 && Chessboard.remainingSteps == 0 && (model.threematch.score < Chessboard.levelScore || Mode1.coin < Chessboard.coinGoal)){
            model.threematch.fillEmpty();
            paintChesspiece();
            model.changeScore();
            this.statusLabel.setText("Currentscore:" + model.threematch.score);
            this.totalScore.setText("Totalscore: " + Chessboard.totalScore );
            judgeFail();
            Enterframe.threeMatch0 = model.threematch;
            musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\失败.wav");
            ChessGameFrame.isrobot=false;
            return;
        }

        if(Enterframe.modechoose == 0 || Enterframe.modechoose == 2 ){
            Enterframe.threeMatch0 = model.threematch;
            model.changeScore();
            this.statusLabel.setText("Currentscore:" + model.threematch.score);
            this.totalScore.setText("Totalscore: " + Chessboard.totalScore );
            judgeWin();
       }else if(Enterframe.modechoose == 1){
            Enterframe.threeMatch0 = model.threematch;
            model.changeScore();
            this.statusLabel.setText("Currentscore:" + model.threematch.score);
            this.totalScore.setText("Totalscore: " + Chessboard.totalScore );
            judgeWin1();
        }


       if(selectedPoint!=null&&selectedPoint2!=null&&Chessboard.remainingSteps!= 0){
           model.swapChessPiece(selectedPoint,selectedPoint2);
            this.stepsLabel.setText("Remaining steps: " + Chessboard.remainingSteps);
            this.statusLabel.setText("Currentscore:" + model.threematch.score);
           this.totalScore.setText("Totalscore: " + Chessboard.totalScore );
           Enterframe.threeMatch0 = model.threematch;
        }else{
            JOptionPane.showMessageDialog(null,"Please choose two chesspieces to swap!","Operation hint",JOptionPane.WARNING_MESSAGE);
        }
        setNullClick();
    }

    @Override
    //添加一些必要的操作
    public void onPlayerNextStep() {
        if(!existNull() && !threeMatch.isMatched(model.threematch.chars,model.threematch.state,model.threematch.row,model.threematch.col,model.threematch.isAll)){
            Chessboard.countSteps = 0;
        }

        if(!existNull() && !threeMatch.isMatched(model.threematch.chars,model.threematch.state,model.threematch.row,model.threematch.col,model.threematch.isAll)){
            JOptionPane.showMessageDialog(null,"You need to swap first!","Operation hint",JOptionPane.WARNING_MESSAGE);
        return;
        }

        if((Enterframe.modechoose == 0 || Enterframe.modechoose == 2) && Chessboard.remainingSteps == 0 && model.threematch.score < Chessboard.levelScore){
            model.threematch.fillEmpty();
            paintChesspiece();
            model.changeScore();
            this.statusLabel.setText("Currentscore:" + model.threematch.score);
            this.totalScore.setText("Totalscore: " + Chessboard.totalScore );
            judgeFail();
            musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\失败.wav");
            ChessGameFrame.isrobot=false;
            return;
        }

        if(Enterframe.modechoose == 1 && Chessboard.remainingSteps == 0 && (model.threematch.score < Chessboard.levelScore || Mode1.coin < Chessboard.coinGoal)){
            model.threematch.fillEmpty();
            paintChesspiece();
            model.changeScore();
            this.statusLabel.setText("Currentscore:" + model.threematch.score);
            this.totalScore.setText("Totalscore: " + Chessboard.totalScore );
            judgeFail();
            musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\失败.wav");
            ChessGameFrame.isrobot=false;
            return;
        }
        if(threeMatch.isMatched(model.threematch.chars,model.threematch.state,model.threematch.row,model.threematch.col,model.threematch.isAll)) {
            model.threematch.caculateSum();
            model.changeScore();
            this.statusLabel.setText("Currentscore:" + model.threematch.score);
            this.totalScore.setText("Totalscore: " + Chessboard.totalScore );
            model.threematch.drop();
            paintChesspiece();
            model.threematch.fillEmpty();
            model.PauseDraw();
        }
        if(model.sum == 1){
            model.threematch.drop();
            model.changeScore();
            this.statusLabel.setText("Currentscore:" + model.threematch.score);
            this.totalScore.setText("Totalscore: " + Chessboard.totalScore);
            paintChesspiece();
            model.sum++;
            if(Enterframe.modechoose == 0 || Enterframe.modechoose == 2 ){
                model.changeScore();
                this.statusLabel.setText("Currentscore:" + model.threematch.score);
                this.totalScore.setText("Totalscore: " + Chessboard.totalScore );
                judgeWin();
            }else if(Enterframe.modechoose == 1) {
                model.changeScore();
                this.statusLabel.setText("Currentscore:" + model.threematch.score);
                this.totalScore.setText("Totalscore: " + Chessboard.totalScore );
                judgeWin1();
            }

        } else if(model.sum == 2 ){
            model.threematch.fillEmpty();
            model.changeScore();
            this.statusLabel.setText("Currentscore:" + model.threematch.score);
            this.totalScore.setText("Totalscore: " + Chessboard.totalScore );
            model.PauseDraw();
            model.sum = 0;
            if(Enterframe.modechoose == 0 || Enterframe.modechoose == 2 ){
                model.changeScore();
                this.statusLabel.setText("Currentscore:" + model.threematch.score);
                this.totalScore.setText("Totalscore: " + Chessboard.totalScore );
                judgeWin();
            }else if(Enterframe.modechoose ==1){
                model.changeScore();
                this.statusLabel.setText("Currentscore:" + model.threematch.score);
                this.totalScore.setText("Totalscore: " + Chessboard.totalScore );
                judgeWin1();
            }

        }
        if(!existNull() && !threeMatch.isMatched(model.threematch.chars,model.threematch.state,model.threematch.row,model.threematch.col,model.threematch.isAll)){
            Chessboard.countSteps = 0;
        }
    }
    public static boolean shouldnext(threeMatch threematch){
        if(!existNull(threematch) && !threeMatch.isMatched(threematch.chars,threematch.state,threematch.row,threematch.col,threematch.isAll)){
            return false;
        }
        return true;
    }
    public static boolean existNull(threeMatch threematch){
        for (int i = 0; i < threematch.row; i++) {
            for (int j = 0; j < threematch.col; j++) {
                if(threematch.chars[i][j]=='0')return true;
            }
        }
        return false;
    }
    public void setNullClick(){
        var point1 = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
        var point2 = (ChessComponent) view.getGridComponentAt(selectedPoint2).getComponent(0);
        point1.setSelected(false);
        point1.repaint();
        point2.setSelected(false);
        point2.repaint();

        selectedPoint=null;
        selectedPoint2=null;
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        //选定并且画出已选棋子的圈
        if (selectedPoint2 != null) {
            var distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());
            var distance2point2 = Math.abs(selectedPoint2.getCol() - point.getCol()) + Math.abs(selectedPoint2.getRow() - point.getRow());
            var point1 = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
            var point2 = (ChessComponent) view.getGridComponentAt(selectedPoint2).getComponent(0);
            if (distance2point1 == 0 && point1 != null) {
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = null;
            } else if (distance2point2 == 0 && point2 != null) {
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = null;
            } else if (distance2point1 == 1 && point2 != null) {
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            } else if (distance2point2 == 1 && point1 != null) {
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            }
            return;
        }
        if (selectedPoint == null) {
            selectedPoint = point;
            component.setSelected(true);
            component.repaint();
            return;
        }

        var distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());

        if (distance2point1 == 0) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
            return;
        }

        if (distance2point1 == 1) {
            selectedPoint2 = point;
            component.setSelected(true);
            component.repaint();
        } else {
            selectedPoint2 = null;

            var grid = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
            if (grid == null) return;
            grid.setSelected(false);
            grid.repaint();

            selectedPoint = point;
            component.setSelected(true);
            component.repaint();
        }


    }
    //public boolean result(int steps,int score,int levelScore)
    public void upDate(int mode){
        threeMatch updatethreematch=new threeMatch(row,col);
        if(mode==0)updatethreematch = new Mode0(row,col);
        if(mode==1)updatethreematch=new Mode1(row,col);
        if(mode==2)updatethreematch=new Mode2(row,col);
        for (int i = 0; i <row ; i++) {
            for (int j = 0; j < col; j++) {
                model.threematch.chars[i][j]=updatethreematch.chars[i][j];
                model.threematch.state[i][j]=true;
                model.sum = 0;
            }
        }
    }




    public Chessboard getModel() {
        return model;
    }

    public void setModel(Chessboard model) {
        this.model = model;
    }



    public boolean existNull(){
        for (int i = 0; i < model.threematch.chars.length; i++) {
            for (int j = 0; j <model.threematch.chars[1].length ; j++) {
                if(model.threematch.chars[i][j] == '0'){
                    return true;
                }
            }
        }
        return false;

    }
    public void judgeWin(){
        if(Chessboard.remainingSteps == 0 && model.threematch.score >= Chessboard.levelScore&&Chessboard.playerStage == 1){
            Enterframe.threeMatch0 = model.threematch;
            addrecording();
            if(existNull()){
                model.threematch.fillEmpty();
                paintChesspiece();
            }
            musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\胜利.wav");
            ChessGameFrame.isrobot=false;
            int result = JOptionPane.showOptionDialog(
                    null,
                    "You Succeeded!\nDo you want to enter the next level?",
                    "Game result",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"确定", "取消"},
                    "确定");
            //这里需要对话框分支判断;
            if (result == JOptionPane.YES_OPTION) {
                upDate(Enterframe.modechoose);
                paintChesspiece();
                Chessboard.playerStage++;
                Chessboard.level = 2;
                Chessboard.levelScore = 300;
                Chessboard.remainingSteps = 8;
                model.threematch.score = 0;
                ChessGameFrame.isrobot = false;
                ChessGameFrame.isauto = false;
                this.statusLabel.setText("Currentscore: 0");
                this.goal.setText("Goal: "+  Chessboard.levelScore);
                this.playerStage.setText("Stage: 2" );
                this.stepsLabel.setText("Remaining steps: " + Chessboard.remainingSteps);
                ChessGameFrame.levelLabel.setText("Level: " + Chessboard.level);
            } else {
                JOptionPane.showMessageDialog(null,"Thanks for playing! ","",JOptionPane.INFORMATION_MESSAGE);
                Enterframe.threeMatch0 = model.threematch;
                ChessGameFrame.isrobot = false;
                ChessGameFrame.isauto = false;
                return;
            }
        }
        if(Chessboard.remainingSteps == 0 && model.threematch.score >= Chessboard.levelScore&&Chessboard.playerStage == 2){
            Enterframe.threeMatch0 = model.threematch;
            addrecording();
            if(existNull()){
                model.threematch.fillEmpty();
               paintChesspiece();
            }
            musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\胜利.wav");
            ChessGameFrame.isrobot=false;
            int result = JOptionPane.showOptionDialog(
                    null,
                    "You Succeeded!\nDo you want to enter the next level?",
                    "Game result",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"确定", "取消"},
                    "确定");
            //这里需要对话框分支判断;
            if (result == JOptionPane.YES_OPTION) {
                upDate(Enterframe.modechoose);
                paintChesspiece();
                Chessboard.playerStage++;
                Chessboard.level = 3;
                Chessboard.levelScore =350;
                Chessboard.remainingSteps = 8;
                model.threematch.score = 0;
                ChessGameFrame.isrobot = false;
                ChessGameFrame.isauto = false;
                this.statusLabel.setText("Currentscore: 0");
                this.goal.setText("Goal: "+  Chessboard.levelScore);
                this.playerStage.setText("Stage: 3" );
                this.stepsLabel.setText("Remaining steps: " + Chessboard.remainingSteps);
                ChessGameFrame.levelLabel.setText("Level: " + Chessboard.level);
            } else {
                JOptionPane.showMessageDialog(null,"Thanks for playing! ","",JOptionPane.INFORMATION_MESSAGE);
                Enterframe.threeMatch0 = model.threematch;
                ChessGameFrame.isrobot = false;
                ChessGameFrame.isauto = false;
                return;
            }
        }
        if(Chessboard.remainingSteps == 0 && model.threematch.score >= Chessboard.levelScore&&Chessboard.playerStage == 3){
            Enterframe.threeMatch0 = model.threematch;
            addrecording();
            addRecordingData();
            if(existNull()){
                model.threematch.fillEmpty();
               paintChesspiece();
            }
            ChessGameFrame.isrobot = false;
            ChessGameFrame.isauto = false;
            JOptionPane.showMessageDialog(null,"YOU have completed all challenges! ","",JOptionPane.INFORMATION_MESSAGE);
            musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\胜利.wav");
            return;
            }
        if(Chessboard.remainingSteps == 0 && model.threematch.score >= Chessboard.levelScore&&Chessboard.playerStage == 4){
            Enterframe.threeMatch0 = model.threematch;
            addrecording();
            if(existNull()){
                model.threematch.fillEmpty();
               paintChesspiece();
            }
            musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\胜利.wav");
            ChessGameFrame.isrobot=false;
            int result = JOptionPane.showOptionDialog(
                    null,
                    "You Succeeded!\nDo you want to enter the next level?",
                    "Game result",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"确定", "取消"},
                    "确定");
            //这里需要对话框分支判断;
            if (result == JOptionPane.YES_OPTION) {
                upDate(Enterframe.modechoose);
                paintChesspiece();
                Chessboard.playerStage++;
                Chessboard.level = 3;
                Chessboard.levelScore =350;
                Chessboard.remainingSteps = 8;
                model.threematch.score = 0;
                ChessGameFrame.isrobot = false;
                ChessGameFrame.isauto = false;
                this.goal.setText("Goal: "+  Chessboard.levelScore);
                this.statusLabel.setText("Currentscore: 0");
                this.playerStage.setText("Stage: 2" );
                this.stepsLabel.setText("Remaining steps: " + Chessboard.remainingSteps);
                ChessGameFrame.levelLabel.setText("Level: " + Chessboard.level);
            } else {
                JOptionPane.showMessageDialog(null,"Thanks for playing! ","",JOptionPane.INFORMATION_MESSAGE);
                Enterframe.threeMatch0 = model.threematch;
                ChessGameFrame.isrobot = false;
                ChessGameFrame.isauto = false;
                return;
            }
        }
        if(Chessboard.remainingSteps == 0 && model.threematch.score >= Chessboard.levelScore&&Chessboard.playerStage == 5){
            Enterframe.threeMatch0 = model.threematch;
            addrecording();
            if(existNull()){
                model.threematch.fillEmpty();
                paintChesspiece();
            }
            musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\胜利.wav");
            ChessGameFrame.isrobot=false;
            int result = JOptionPane.showOptionDialog(
                    null,
                    "You Succeeded!\nDo you want to enter the next level?",
                    "Game result",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"确定", "取消"},
                    "确定");
            //这里需要对话框分支判断;
            if (result == JOptionPane.YES_OPTION) {
                upDate(Enterframe.modechoose);
               paintChesspiece();
               Chessboard.playerStage++;
                Chessboard.level = 3;
                Chessboard.levelScore =350;
                Chessboard.remainingSteps = 8;
                model.threematch.score = 0;
                ChessGameFrame.isrobot = false;
                ChessGameFrame.isauto = false;
                this.goal.setText("Goal: "+  Chessboard.levelScore);
                this.statusLabel.setText("Currentscore: 0");
                this.playerStage.setText("Stage: 3" );
                this.stepsLabel.setText("Remaining steps: " + Chessboard.remainingSteps);
                ChessGameFrame.levelLabel.setText("Level: " + Chessboard.level);
            } else {
                JOptionPane.showMessageDialog(null,"Thanks for playing! ","",JOptionPane.INFORMATION_MESSAGE);
                Enterframe.threeMatch0 = model.threematch;
                ChessGameFrame.isrobot = false;
                ChessGameFrame.isauto = false;
                return;
            }
        }
        if(Chessboard.remainingSteps == 0 && model.threematch.score >= Chessboard.levelScore&&Chessboard.playerStage == 6){
            Enterframe.threeMatch0 = model.threematch;
            addrecording();
            addRecordingData();
            if(existNull()){
                model.threematch.fillEmpty();
                paintChesspiece();
            }
            JOptionPane.showMessageDialog(null,"YOU have completed all challenges! ","",JOptionPane.INFORMATION_MESSAGE);
            musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\胜利.wav");
            ChessGameFrame.isrobot = false;
            ChessGameFrame.isauto = false;
            return;
        } if(Chessboard.remainingSteps == 0 && model.threematch.score >= Chessboard.levelScore&&(Chessboard.playerStage == 7 || Chessboard.playerStage == 8)){
            Enterframe.threeMatch0 = model.threematch;
            addrecording();
            if(existNull()){
                model.threematch.fillEmpty();
               paintChesspiece();
            }
            musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\胜利.wav");
            ChessGameFrame.isrobot=false;
            int result = JOptionPane.showOptionDialog(
                    null,
                    "You Succeeded!\nDo you want to enter the next level?",
                    "Game result",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"确定", "取消"},
                    "确定");
            //这里需要对话框分支判断;
            if (result == JOptionPane.YES_OPTION) {
                upDate(Enterframe.modechoose);
                paintChesspiece();
                Chessboard.playerStage++;
                Chessboard.level = 3;
                Chessboard.levelScore =350;
                Chessboard.remainingSteps = 8;
                model.threematch.score = 0;
                ChessGameFrame.isrobot = false;
                ChessGameFrame.isauto = false;
                this.goal.setText("Goal: "+  Chessboard.levelScore);
                this.statusLabel.setText("Currentscore: 0");
                if(Chessboard.playerStage == 8){
                    this.playerStage.setText("Stage: 2");
                }else{
                    this.playerStage.setText("Stage: 3");
                }
                this.stepsLabel.setText("Remaining steps: " + Chessboard.remainingSteps);
                this.goal.setText("Goal: "+  Chessboard.levelScore);
                ChessGameFrame.levelLabel.setText("Level: " + Chessboard.level);
            } else {
                JOptionPane.showMessageDialog(null,"Thanks for playing! ","",JOptionPane.INFORMATION_MESSAGE);
                Enterframe.threeMatch0 = model.threematch;
                ChessGameFrame.isrobot = false;
                ChessGameFrame.isauto = false;
                return;
            }

        }
        if(Chessboard.remainingSteps == 0 && model.threematch.score >= Chessboard.levelScore&&Chessboard.playerStage == 9){
            Enterframe.threeMatch0 = model.threematch;
            addrecording();
            addRecordingData();
            if(existNull()){
                model.threematch.fillEmpty();
                paintChesspiece();
            }
            JOptionPane.showMessageDialog(null,"YOU have completed all challenges! ","",JOptionPane.INFORMATION_MESSAGE);
            musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\胜利.wav");
            ChessGameFrame.isrobot = false;
            ChessGameFrame.isauto = false;
        }
    }
    public void judgeWin1(){
        if(Chessboard.remainingSteps == 0 && model.threematch.score >= Chessboard.levelScore && Chessboard.playerStage == 1 && Mode1.coin >= Chessboard.coinGoal){
            Enterframe.threeMatch0 = model.threematch;
            addrecording();
            if(existNull()){
                model.threematch.fillEmpty();
                paintChesspiece();
            }
            musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\胜利.wav");
            ChessGameFrame.isrobot=false;
            int result = JOptionPane.showOptionDialog(
                    null,
                    "You Succeeded!\nDo you want to enter the next level?",
                    "Game result",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"确定", "取消"},
                    "确定");
            //这里需要对话框分支判断;
            if (result == JOptionPane.YES_OPTION) {
                upDate(Enterframe.modechoose);
                model.threematch.fillEmpty();
                paintChesspiece();
                Chessboard.playerStage++;
                Chessboard.level = 2;
                Chessboard.levelScore = 300;
                Chessboard.remainingSteps = 8;
                Chessboard.coinGoal = 1;
                model.threematch.score = 0;
                Mode1.coin = 0;
                ChessGameFrame.isrobot = false;
                ChessGameFrame.isauto = false;
                this.currentCoin.setText("Currentcoin: 0" );
                this.coinGoal.setText("CoinGoal: 1");
                this.goal.setText("Goal: "+  Chessboard.levelScore);
                this.statusLabel.setText("Currentscore: 0");
                this.playerStage.setText("Stage: 2" );
                this.stepsLabel.setText("Remaining steps: " + Chessboard.remainingSteps);
                ChessGameFrame.levelLabel.setText("Level: " + Chessboard.level);
            } else {
                JOptionPane.showMessageDialog(null,"Thanks for playing! ","",JOptionPane.INFORMATION_MESSAGE);
                Enterframe.threeMatch0 = model.threematch;
                ChessGameFrame.isrobot = false;
                ChessGameFrame.isauto = false;
                return;
            }
        }
        if(Chessboard.remainingSteps == 0 && model.threematch.score >= Chessboard.levelScore&&Chessboard.playerStage == 2&&Mode1.coin >= Chessboard.coinGoal){
            Enterframe.threeMatch0 = model.threematch;
            addrecording();
            if(existNull()){
                model.threematch.fillEmpty();
                paintChesspiece();
            }
            musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\胜利.wav");
            int result = JOptionPane.showOptionDialog(
                    null,
                    "You Succeeded!\nDo you want to enter the next level?",
                    "Game result",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"确定", "取消"},
                    "确定");
            //这里需要对话框分支判断;
            if (result == JOptionPane.YES_OPTION) {
                upDate(Enterframe.modechoose);
                model.threematch.fillEmpty();
                paintChesspiece();
                Chessboard.playerStage++;
                Chessboard.level = 3;
                Chessboard.levelScore =350;
                Mode1.coin = 0;
                Chessboard.remainingSteps = 8;
                model.threematch.score = 0;
                Chessboard.coinGoal = 1;
                ChessGameFrame.isrobot = false;
                ChessGameFrame.isauto = false;
                this.currentCoin.setText("Currentcoin: 0");
                this.coinGoal.setText("CoinGoal: 1");
                this.goal.setText("Goal: "+  Chessboard.levelScore);
                this.statusLabel.setText("Currentscore: 0");
                this.playerStage.setText("Stage: 3" );
                this.stepsLabel.setText("Remaining steps: " + Chessboard.remainingSteps);
                ChessGameFrame.levelLabel.setText("Level: " + Chessboard.level);

            } else {
                JOptionPane.showMessageDialog(null,"Thanks for playing! ","",JOptionPane.INFORMATION_MESSAGE);
                Enterframe.threeMatch0 = model.threematch;
                ChessGameFrame.isrobot = false;
                ChessGameFrame.isauto = false;
                return;
            }
        }
        if(Chessboard.remainingSteps == 0 && model.threematch.score >= Chessboard.levelScore&&Chessboard.playerStage == 3 &&Mode1.coin >=Chessboard.coinGoal){
            addrecording();
            addRecordingData();
            if(existNull()){
                model.threematch.fillEmpty();
                paintChesspiece();
            }
            ChessGameFrame.isrobot = false;
            ChessGameFrame.isauto = false;
            Enterframe.threeMatch0 = model.threematch;
            JOptionPane.showMessageDialog(null,"YOU have completed all challenges! ","",JOptionPane.INFORMATION_MESSAGE);
            musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\胜利.wav");
            return;
        }
        if(Chessboard.remainingSteps == 0 && model.threematch.score >= Chessboard.levelScore&&Chessboard.playerStage == 4&&Mode1.coin >= Chessboard.coinGoal){
            Enterframe.threeMatch0 = model.threematch;
            addrecording();
            if(existNull()){
                model.threematch.fillEmpty();
                paintChesspiece();
            }
            musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\胜利.wav");
            ChessGameFrame.isrobot=false;
            int result = JOptionPane.showOptionDialog(
                    null,
                    "You Succeeded!\nDo you want to enter the next level?",
                    "Game result",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"确定", "取消"},
                    "确定");
            //这里需要对话框分支判断;
            if (result == JOptionPane.YES_OPTION) {
                upDate(Enterframe.modechoose);
                model.threematch.fillEmpty();
                paintChesspiece();
                Chessboard.playerStage++;
                Chessboard.level = 3;
                Chessboard.levelScore =350;
                Mode1.coin = 0;
                Chessboard.remainingSteps = 8;
                model.threematch.score = 0;
                Chessboard.coinGoal = 1;
                ChessGameFrame.isrobot = false;
                ChessGameFrame.isauto = false;
                this.currentCoin.setText("Currentcoin: 0");
                this.coinGoal.setText("CoinGoal: 1");
                this.goal.setText("Goal: "+  Chessboard.levelScore);
                this.statusLabel.setText("Currentscore: 0");
                this.playerStage.setText("Stage: 2") ;
                this.stepsLabel.setText("Remaining steps: " + Chessboard.remainingSteps);
                ChessGameFrame.levelLabel.setText("Level: " + Chessboard.level);

            } else {
                JOptionPane.showMessageDialog(null,"Thanks for playing! ","",JOptionPane.INFORMATION_MESSAGE);
                Enterframe.threeMatch0 = model.threematch;
                ChessGameFrame.isrobot = false;
                ChessGameFrame.isauto = false;
                return;
            }
        }
        if(Chessboard.remainingSteps == 0 && model.threematch.score >= Chessboard.levelScore&&Chessboard.playerStage == 5 &&Mode1.coin >= Chessboard.coinGoal){
            Enterframe.threeMatch0 = model.threematch;
            addrecording();
            if(existNull()){
                model.threematch.fillEmpty();
                paintChesspiece();
            }
            musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\胜利.wav");
            ChessGameFrame.isrobot=false;
            int result = JOptionPane.showOptionDialog(
                    null,
                    "You Succeeded!\nDo you want to enter the next level?",
                    "Game result",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"确定", "取消"},
                    "确定");
            //这里需要对话框分支判断;
            if (result == JOptionPane.YES_OPTION) {
                upDate(Enterframe.modechoose);
                model.threematch.fillEmpty();
                paintChesspiece();
                Chessboard.playerStage++;
                Chessboard.level = 3;
                Chessboard.levelScore =350;
                Chessboard.remainingSteps = 8;
                Mode1.coin = 0;
                model.threematch.score = 0;
                Chessboard.coinGoal = 1;
                ChessGameFrame.isrobot = false;
                ChessGameFrame.isauto = false;
                this.currentCoin.setText("Currentcoin: 0");
                this.coinGoal.setText("CoinGoal: 1");
                this.goal.setText("Goal: "+  Chessboard.levelScore);
                this.statusLabel.setText("Currentscore: 0");
                this.playerStage.setText("Stage: 3" );
                this.stepsLabel.setText("Remaining steps: " + Chessboard.remainingSteps);
                ChessGameFrame.levelLabel.setText("Level: " + Chessboard.level);

            } else {
                JOptionPane.showMessageDialog(null,"Thanks for playing! ","",JOptionPane.INFORMATION_MESSAGE);
                Enterframe.threeMatch0 = model.threematch;
                ChessGameFrame.isrobot = false;
                ChessGameFrame.isauto = false;
                return;
            }
        }
        if(Chessboard.remainingSteps == 0 && model.threematch.score >= Chessboard.levelScore&&Chessboard.playerStage == 6&&Mode1.coin >Chessboard.coinGoal){
            Enterframe.threeMatch0 = model.threematch;
            addrecording();
            addRecordingData();
            if(existNull()){
                model.threematch.fillEmpty();
                paintChesspiece();
            }   ChessGameFrame.isrobot = false;
            ChessGameFrame.isauto = false;
            JOptionPane.showMessageDialog(null,"YOU have completed all challenges! ","",JOptionPane.INFORMATION_MESSAGE);
            musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\胜利.wav");
        } if(Chessboard.remainingSteps == 0 && model.threematch.score >= Chessboard.levelScore&&(Chessboard.playerStage == 7 || Chessboard.playerStage == 8) &&Mode1.coin  >= Chessboard.coinGoal){
            addrecording();
            if(existNull()){
                model.threematch.fillEmpty();
                paintChesspiece();
            }
            musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\胜利.wav");
            ChessGameFrame.isrobot=false;
            int result = JOptionPane.showOptionDialog(
                    null,
                    "You Succeeded!\nDo you want to enter the next level?",
                    "Game result",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"确定", "取消"},
                    "确定");
            //这里需要对话框分支判断;
            if (result == JOptionPane.YES_OPTION) {
                upDate(Enterframe.modechoose);
                model.threematch.fillEmpty();
                paintChesspiece();
                Chessboard.playerStage++;
                Chessboard.level = 3;
                Chessboard.levelScore =350;
                Chessboard.remainingSteps = 8;
                Mode1.coin = 0;
                model.threematch.score = 0;
                Chessboard.coinGoal = 1;
                ChessGameFrame.isrobot = false;
                ChessGameFrame.isauto = false;
                this.currentCoin.setText("Currentcoin: 0");
                this.coinGoal.setText("CoinGoal: 1");
                this.goal.setText("Goal: "+  Chessboard.levelScore);
                this.statusLabel.setText("Currentscore: 0");
                if(Chessboard.playerStage == 8){
                    this.playerStage.setText("Stage: 2" );
                }else{
                    this.playerStage.setText("Stage: 3");
                }
                this.stepsLabel.setText("Remaining steps: " + Chessboard.remainingSteps);
                ChessGameFrame.levelLabel.setText("Level: " + Chessboard.level);


            } else {
                JOptionPane.showMessageDialog(null,"Thanks for playing! ","",JOptionPane.INFORMATION_MESSAGE);
                Enterframe.threeMatch0 = model.threematch;
                ChessGameFrame.isrobot = false;
                ChessGameFrame.isauto = false;
                return;
            }

        }
        if(Chessboard.remainingSteps == 0 && model.threematch.score >= Chessboard.levelScore&&Chessboard.playerStage == 9 &&Mode1.coin >= Chessboard.coinGoal){
            addrecording();
            addrecording();
            if(existNull()){
                model.threematch.fillEmpty();
                paintChesspiece();
            }
            ChessGameFrame.isrobot = false;
            ChessGameFrame.isauto = false;
            Enterframe.threeMatch0 = model.threematch;
            JOptionPane.showMessageDialog(null,"YOU have completed all challenges! ","",JOptionPane.INFORMATION_MESSAGE);
            musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\胜利.wav");
            ChessGameFrame.isrobot=false;
        }
    }

public void judgeFail(){
    int result = JOptionPane.showOptionDialog(
            null,
            "You failed!\nDo you want to save the file?",
            "Game result",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            new Object[]{"确定", "取消"},
            "确定");
    //这里需要对话框分支判断;
    if (result == JOptionPane.YES_OPTION) {
        addrecording();
        addRecordingData();
        ChessGameFrame.isrobot = false;
        ChessGameFrame.isauto = false;
        System.exit(0);
    } else {
        ChessGameFrame.isrobot = false;
        ChessGameFrame.isauto = false;
        System.exit(0);
    }
    }
    public  void addrecording(){
        List<String> list = createGameList(Enterframe.name,Chessboard.playerStage,Mode1.coin,remainingSteps,Chessboard.score1,Chessboard.score2,Chessboard.score3,model.threematch);
        AddWrite.toAdd(list);
        Enterframe.threeMatch0 = model.threematch;
    }
    public void addRecordingData(){
        List<String> list = createGameList(Enterframe.name,Chessboard.playerStage,Mode1.coin,remainingSteps,Chessboard.score1,Chessboard.score2,Chessboard.score3,model.threematch);
        CreateTxtFile.creatTxt(list);
        Enterframe.threeMatch0 = model.threematch;
    }



    public void paintChesspiece(){
        for (int i = 0; i < model.threematch.chars.length; i++) {
            for (int j = 0; j <  model.threematch.chars.length; j++) {
                model.getGrid()[i][j].getPiece().setColor(Constant.getColorMap().get(ChessPiece.nameDecide(model.threematch.chars[i][j])));
                model.getGrid()[i][j].getPiece().setName(ChessPiece.nameDecide(model.threematch.chars[i][j]));
                model.getGrid()[i][j].getPiece().chessComponent.revalidate();
                model.getGrid()[i][j].getPiece().chessComponent.repaint();
            }
        }
    }

    public  void currentChessboard(){
        ReadAndOverwrite readAndOverwrite = new ReadAndOverwrite();
        List<String> currentgame=new ArrayList<>();
        currentgame=createGameList(Enterframe.name,Chessboard.playerStage,Mode1.coin,remainingSteps,Chessboard.score1,Chessboard.score2,Chessboard.score3,model.threematch);
        readAndOverwrite.writeFileFromList("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\Filepart\\CurrentChessboard.txt",currentgame);
    }

    public void PauseDrawChesspiece(){
        Timer timer = new Timer(pausetime, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < model.threematch.chars.length; i++) {
                    for (int j = 0; j <  model.threematch.chars.length; j++) {
                        model.getGrid()[i][j].getPiece().setColor(Constant.getColorMap().get(ChessPiece.nameDecide(model.threematch.chars[i][j])));
                        model.getGrid()[i][j].getPiece().setName(ChessPiece.nameDecide(model.threematch.chars[i][j]));
                        model.getGrid()[i][j].getPiece().chessComponent.revalidate();
                        model.getGrid()[i][j].getPiece().chessComponent.repaint();
                    }
                }
            }
        });

        timer.setRepeats(false);
        timer.start();
    }//设置了一个延迟画图的方法

}






