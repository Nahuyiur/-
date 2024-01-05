package view;

import Core.Mode0;
import Core.Mode1;
import Core.Mode2;
import Core.removablePostion;
import Enters.Enterframe;
import Filepart.AddWrite;
import Filepart.CreateTxtFile;
import Filepart.ReadAndOverwrite;
import controller.GameController;
import model.ChessPiece;
import model.Chessboard;
import model.ChessboardPoint;
import model.Constant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Robot;

import static Core.threeMatch.isMatched;
import static Core.threeMatch.setPieceChar;
import static Filepart.createList.createGameList;
import static Filepart.getInformation.*;
import static controller.GameController.shouldnext;
import static java.lang.Thread.*;
import static model.Chessboard.*;
import static model.Chessboard.remainingSteps;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    JButton updatebutton = new JButton("Update");
    JButton swapconfirmbutton = new JButton("Confirm Swap");
    JButton Back = new JButton("Next Step");
    JButton tipsbutton = new JButton("Tips");
    boolean bomb=true;
    boolean knock=true;
    boolean back=true;
    boolean hasnew=false;

    public  BackgroundPanel image=new BackgroundPanel();
    private final int WIDTH  ;
    private final int HEIGTH ;

    private final int ONE_CHESS_SIZE ;

    public GameController gameController ;

    public ChessboardComponent chessboardComponent;
    public static boolean isauto=false;
    public static boolean isrobot=false;

    public JLabel statusLabel;//分数

    public JLabel nameLabel;//用户姓名

    public JLabel stepsLabel;//剩余步数

    public JLabel totalScore;

    public JLabel playerStage;
    public JLabel currentcoin;

    public JLabel goal;
    public JLabel coinGoal;
    public JLabel Null;
    public Robot robot = new Robot();

    public static JLabel levelLabel;
    public Timer timer1 = new Timer(20, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 定时器任务，每秒执行一次
            if (shouldnext(gameController.getModel().threematch)) {
                Back.setBackground(Color.GREEN);
            } else {
                Back.setBackground(UIManager.getColor("Button.background"));
            }
            if(!gameController.getModel().threematch.existChange()){
                updatebutton.setBackground(Color.GREEN);
            }else{
                updatebutton.setBackground(UIManager.getColor("Button.background"));
            }
        }
    });
    public Timer timer2 = new Timer(2000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 定时器任务，每秒执行一次
            // nextStep.setLocation(HEIGTH, HEIGTH / 10 + 280);
            int x=HEIGTH+480,y=HEIGTH/10+460;
            if(isauto&&shouldnext(gameController.getModel().threematch)&&!isrobot){
                robot.mouseMove(x,y);
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
            }
        }
    });
    public Timer timer3 = new Timer(2000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            int x1=HEIGTH+480,y1=HEIGTH/10+460;//next
            int x2=HEIGTH+480,y2=HEIGTH/10+540;//tips
            int x3=HEIGTH+480,y3=HEIGTH/10+330;//update
            int x4=HEIGTH+480,y4=HEIGTH/10+400;//swap

            if(isrobot){
                if(shouldnext(gameController.getModel().threematch)){
                    robot.mouseMove(x1,y1);
                    robot.mousePress(InputEvent.BUTTON1_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_MASK);
                }else{
                    if(!gameController.getModel().threematch.existChange()){
                        robot.mouseMove(x3,y3);
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    }else{
                        robot.mouseMove(x2,y2);
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                        //希望在这里建一个内部类

                        try {
                            sleep(1000);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        robot.mouseMove(x4,y4);
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    }
                }
            }

        }
    });
    public Timer timer4 = new Timer(200, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 定时器任务，每秒执行一次
            if(Enterframe.modechoose==1){
                for (int i = 0; i < gameController.col; i++) {
                    if(gameController.getModel().threematch.chars[gameController.row-1][i]=='+'){
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                Mode1.coin++;
                                currentcoin.setText("Currentcoin: "+Mode1.coin);
                            }
                        });


                        for (int j = gameController.row-1; j >0; j--) {
                            gameController.getModel().threematch.chars[j][i]=gameController.getModel().threematch.chars[j-1][i];
                        }
                        gameController.getModel().threematch.chars[0][i]='0';
                        gameController.model.threematch.fillEmpty();
                        gameController.paintChesspiece();
                    }
                }
            }
        }
    });
    class Pauseswap implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 在此处放置内部类的操作逻辑
            // 例如，您可以在这里执行需要暂停的操作
        }
    }


    public JLabel getCurrentcoin() {
        return currentcoin;
    }

    public void setCurrentcoin(JLabel currentcoin) {
        this.currentcoin = currentcoin;
    }

    public JLabel getCoinGoal() {
        return coinGoal;
    }

    public void setCoinGoal(JLabel coinGoal) {
        this.coinGoal = coinGoal;
    }

    public JLabel getGoal() {
        return goal;
    }

    public void setGoal(JLabel goal) {
        this.goal = goal;
    }

    public JLabel getPlayerStage() {
        return playerStage;
    }

    public void setPlayerStage(JLabel playerStage) {
        this.playerStage = playerStage;
    }

    public int count;
    //写一个增加菜单条的方法
    public void addMenubar(){
       JMenuBar menuBar=new JMenuBar();
       //自动模式
       JMenu automode=new JMenu("自动模式");
        JMenu robotgame=new JMenu("人机对战");
        JMenu chooselevel = new JMenu("重选关卡");

        JMenuItem level=new JMenuItem(new JMenuItem(new AbstractAction("Level: 1") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showOptionDialog(
                        null,
                        "Do you want to start a new game? This will result in file loss.",
                        "Game result",
                        JOptionPane.YES_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new Object[]{"确定"},
                        "确定");
                //这里需要对话框分支判断;
                if (result == JOptionPane.YES_OPTION) {
                    //这里怎么把当前的frame关闭？？？请教你

                    ChessGameFrame mainFrame = null;
                    try {
                        mainFrame = new ChessGameFrame(1100, 810);
                    } catch (AWTException ex) {
                        throw new RuntimeException(ex);
                    }
                    dispose();
                    Chessboard cb=new Chessboard();
                    mainFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            int option = JOptionPane.showConfirmDialog(null,"You have not finished the game! Do you want to save the file?","",JOptionPane.YES_NO_OPTION);
                            if(option == JOptionPane.YES_OPTION){
                                List<String> list = createGameList(Enterframe.name,Chessboard.playerStage,Mode1.coin,remainingSteps,Chessboard.score1,Chessboard.score2,Chessboard.score3,Enterframe.threeMatch0);
                                AddWrite.toAdd(list);

                                String filename=CreateTxtFile.creatTxt(list);

                                ReadAndOverwrite readAndOverwrite = new ReadAndOverwrite();
                                readAndOverwrite.writeFileFromList("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\RecordingDate\\"+filename,list);

                                List<String> bestgamerecord=readAndOverwrite.readFileToList("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\Filepart\\bestrecord.txt");
                                int highestscore=getScore1(bestgamerecord)+getScore2(bestgamerecord)+getScore3(bestgamerecord);
                                int thistotalscore=getScore1(list)+getScore2(list)+getScore3(list);
                                if(thistotalscore>highestscore){
                                    readAndOverwrite.writeFileFromList("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\Filepart\\bestrecord.txt",list);
                                }
                                dispose();
                                SwingUtilities.invokeLater(() -> {
                                    Enterframe game=new Enterframe();
                                    game.initial();
                                });
                            }else{
                                dispose();
                                SwingUtilities.invokeLater(() -> {
                                    Enterframe game=new Enterframe();
                                    game.initial();
                                });
                            }
                        }
                    });


                    if(Enterframe.modechoose==0){
                        cb=new Chessboard(new Mode0(8,8));
                        Chessboard.totalScore = 0;
                        Chessboard.score1 = 0;
                        Chessboard.score2 = 0;
                        Chessboard.score3 = 0;
                        Chessboard.level = 1;
                        Chessboard.remainingSteps = 8;
                        Chessboard.levelScore = 200;
                        Chessboard.playerStage = 1;
                        Chessboard.coinGoal = 1;
                        mainFrame.addNameLabel();mainFrame.addLabel();mainFrame.addlevelLabel();mainFrame.remainingSteps();mainFrame.totalScore();mainFrame.addStage();mainFrame.addGoal();mainFrame.addNull();

                    }
                    if(Enterframe.modechoose==1){
                        cb=new Chessboard(new Mode1(8,8));
                        Chessboard.totalScore = 0;
                        Chessboard.score1 = 0;
                        Chessboard.score2 = 0;
                        Chessboard.score3 = 0;
                        Chessboard.level = 1;
                        Chessboard.remainingSteps = 8;
                        Chessboard.levelScore = 300;
                        Chessboard.playerStage = 1;
                        Chessboard.coinGoal = 1;
                        mainFrame.addNameLabel();mainFrame.addLabel();mainFrame.addlevelLabel();mainFrame.remainingSteps();mainFrame.totalScore();mainFrame.addStage();mainFrame.addGoal();mainFrame.addCoinGoal();mainFrame.addCurrentCoin();mainFrame.addNull();

                    }
                    if(Enterframe.modechoose==2){
                        cb=new Chessboard(new Mode2(8,8));
                        Chessboard.totalScore = 0;
                        Chessboard.score1 = 0;
                        Chessboard.score2 = 0;
                        Chessboard.score3 = 0;
                        Chessboard.level = 1;
                        Chessboard.remainingSteps = 8;
                        Chessboard.levelScore = 350;
                        Chessboard.playerStage = 1;
                        Chessboard.coinGoal = 1;
                        mainFrame.addNameLabel();mainFrame.addLabel();mainFrame.addlevelLabel();mainFrame.remainingSteps();mainFrame.totalScore();mainFrame.addStage();mainFrame.addGoal();mainFrame.addNull();

                    }
//                cb.pausetime =getSleeptime();

                    GameController gameController = new GameController(mainFrame.getChessboardComponent(), cb);
                    mainFrame.setGameController(gameController);
                    gameController.setStatusLabel(mainFrame.getStatusLabel());
                    gameController.setStepsLabel(mainFrame.getStepsLabel());
                    gameController.setNameLabel(mainFrame.getNameLabel());
                    gameController.setTotalScore(mainFrame.getTotalScore());
                    gameController.setPlayerStage(mainFrame.getPlayerStage());
                    gameController.setCoinGoal(mainFrame.getCoinGoal());
                    gameController.setCurrentCoin(mainFrame.getCurrentcoin());
                    gameController.setGoal(mainFrame.getGoal());
                    mainFrame.setVisible(true);
//                frame.setVisible(false);

                }
            }
        }).getAction());


        JMenuItem level2=new JMenuItem(new JMenuItem(new AbstractAction("Level: 2") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showOptionDialog(
                        null,
                        "Do you want to start a new game? This will result in file loss.",
                        "Game result",
                        JOptionPane.YES_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new Object[]{"确定"},
                        "确定");
                //这里需要对话框分支判断;
                if (result == JOptionPane.YES_OPTION) {
                    ChessGameFrame mainFrame = null;
                    try {
                        mainFrame = new ChessGameFrame(1100, 810);
                    } catch (AWTException ex) {
                        throw new RuntimeException(ex);
                    }
                    dispose();
                    Chessboard cb=new Chessboard();

                    mainFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            int option = JOptionPane.showConfirmDialog(null,"You have not finished the game! Do you want to save the file?","",JOptionPane.YES_NO_OPTION);
                            if(option == JOptionPane.YES_OPTION){
                                List<String> list = createGameList(Enterframe.name,Chessboard.playerStage,Mode1.coin,remainingSteps,Chessboard.score1,Chessboard.score2,Chessboard.score3,Enterframe.threeMatch0);
                                AddWrite.toAdd(list);

                                String filename=CreateTxtFile.creatTxt(list);

                                ReadAndOverwrite readAndOverwrite = new ReadAndOverwrite();
                                readAndOverwrite.writeFileFromList("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\RecordingDate\\"+filename,list);

                                List<String> bestgamerecord=readAndOverwrite.readFileToList("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\Filepart\\bestrecord.txt");
                                int highestscore=getScore1(bestgamerecord)+getScore2(bestgamerecord)+getScore3(bestgamerecord);
                                int thistotalscore=getScore1(list)+getScore2(list)+getScore3(list);
                                if(thistotalscore>highestscore){
                                    readAndOverwrite.writeFileFromList("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\Filepart\\bestrecord.txt",list);
                                }

                                SwingUtilities.invokeLater(() -> {
                                    Enterframe game=new Enterframe();
                                    game.initial();dispose();
                                });
                            }else{

                                SwingUtilities.invokeLater(() -> {
                                    Enterframe game=new Enterframe();
                                    game.initial();dispose();
                                });
                            }

                        }
                    });


                    if(Enterframe.modechoose==0){
                        cb=new Chessboard(new Mode0(8,8));
                        Chessboard.totalScore = 0;
                        Chessboard.score1 = 0;
                        Chessboard.score2 = 0;
                        Chessboard.score3 = 0;
                        Chessboard.level = 2;
                        Chessboard.remainingSteps = 8;
                        Chessboard.levelScore = 200;
                        Chessboard.playerStage = 4;
                        Chessboard.coinGoal = 1;
                        mainFrame.addNameLabel();mainFrame.addLabel();mainFrame.addlevelLabel();mainFrame.remainingSteps();mainFrame.totalScore();mainFrame.addStage();mainFrame.addGoal();mainFrame.addNull();

                    }
                    if(Enterframe.modechoose==1){
                        cb=new Chessboard(new Mode1(8,8));
                        Chessboard.totalScore = 0;
                        Chessboard.score1 = 0;
                        Chessboard.score2 = 0;
                        Chessboard.score3 = 0;
                        Chessboard.level = 2;
                        Chessboard.remainingSteps = 8;
                        Chessboard.levelScore = 300;
                        Chessboard.playerStage = 4;
                        Chessboard.coinGoal = 1;
                        mainFrame.addNameLabel();mainFrame.addLabel();mainFrame.addlevelLabel();mainFrame.remainingSteps();mainFrame.totalScore();mainFrame.addStage();mainFrame.addGoal();mainFrame.addCoinGoal();mainFrame.addCurrentCoin();mainFrame.addNull();

                    }
                    if(Enterframe.modechoose==2){
                        cb=new Chessboard(new Mode2(8,8));
                        Chessboard.totalScore = 0;
                        Chessboard.score1 = 0;
                        Chessboard.score2 = 0;
                        Chessboard.score3 = 0;
                        Chessboard.level = 2;
                        Chessboard.remainingSteps = 8;
                        Chessboard.levelScore = 350;
                        Chessboard.playerStage = 4;
                        Chessboard.coinGoal = 1;
                        mainFrame.addNameLabel();mainFrame.addLabel();mainFrame.addlevelLabel();mainFrame.remainingSteps();mainFrame.totalScore();mainFrame.addStage();mainFrame.addGoal();mainFrame.addNull();

                    }
//                cb.pausetime =getSleeptime();

                    GameController gameController = new GameController(mainFrame.getChessboardComponent(), cb);
                    mainFrame.setGameController(gameController);
                    gameController.setStatusLabel(mainFrame.getStatusLabel());
                    gameController.setStepsLabel(mainFrame.getStepsLabel());
                    gameController.setNameLabel(mainFrame.getNameLabel());
                    gameController.setTotalScore(mainFrame.getTotalScore());
                    gameController.setPlayerStage(mainFrame.getPlayerStage());
                    gameController.setCoinGoal(mainFrame.getCoinGoal());
                    gameController.setCurrentCoin(mainFrame.getCurrentcoin());
                    gameController.setGoal(mainFrame.getGoal());
                    mainFrame.setVisible(true);
//                frame.setVisible(false);

                }
            }
        }).getAction());


        JMenuItem level3=new JMenuItem(new JMenuItem(new AbstractAction("Level: 3") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showOptionDialog(
                        null,
                        "Do you want to start a new game? This will result in file loss.",
                        "Game result",
                        JOptionPane.YES_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new Object[]{"确定"},
                        "确定");
                //这里需要对话框分支判断;
                if (result == JOptionPane.YES_OPTION) {
                    ChessGameFrame mainFrame = null;
                    try {
                        mainFrame = new ChessGameFrame(1100, 810);
                    } catch (AWTException ex) {
                        throw new RuntimeException(ex);
                    }
                    dispose();
                    Chessboard cb=new Chessboard();

                    mainFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            int option = JOptionPane.showConfirmDialog(null,"You have not finished the game! Do you want to save the file?","",JOptionPane.YES_NO_OPTION);
                            if(option == JOptionPane.YES_OPTION){
                                List<String> list = createGameList(Enterframe.name,Chessboard.playerStage,Mode1.coin,remainingSteps,Chessboard.score1,Chessboard.score2,Chessboard.score3,Enterframe.threeMatch0);
                                AddWrite.toAdd(list);

                                String filename=CreateTxtFile.creatTxt(list);

                                ReadAndOverwrite readAndOverwrite = new ReadAndOverwrite();
                                readAndOverwrite.writeFileFromList("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\RecordingDate\\"+filename,list);

                                List<String> bestgamerecord=readAndOverwrite.readFileToList("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\Filepart\\bestrecord.txt");
                                int highestscore=getScore1(bestgamerecord)+getScore2(bestgamerecord)+getScore3(bestgamerecord);
                                int thistotalscore=getScore1(list)+getScore2(list)+getScore3(list);
                                if(thistotalscore>highestscore){
                                    readAndOverwrite.writeFileFromList("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\Filepart\\bestrecord.txt",list);
                                }

                                SwingUtilities.invokeLater(() -> {
                                    Enterframe game=new Enterframe();
                                    game.initial();dispose();
                                });
                            }else{

                                SwingUtilities.invokeLater(() -> {
                                    Enterframe game=new Enterframe();
                                    game.initial();dispose();
                                });
                            }
                        }
                    });


                    if(Enterframe.modechoose==0){
                        cb=new Chessboard(new Mode0(8,8));
                        Chessboard.totalScore = 0;
                        Chessboard.score1 = 0;
                        Chessboard.score2 = 0;
                        Chessboard.score3 = 0;
                        Chessboard.level = 3;
                        Chessboard.remainingSteps = 8;
                        Chessboard.levelScore = 200;
                        Chessboard.playerStage = 7;
                        Chessboard.coinGoal = 1;
                        mainFrame.addNameLabel();mainFrame.addLabel();mainFrame.addlevelLabel();mainFrame.remainingSteps();mainFrame.totalScore();mainFrame.addStage();mainFrame.addGoal();mainFrame.addNull();

                    }
                    if(Enterframe.modechoose==1){
                        cb=new Chessboard(new Mode1(8,8));
                        Chessboard.totalScore = 0;
                        Chessboard.score1 = 0;
                        Chessboard.score2 = 0;
                        Chessboard.score3 = 0;
                        Chessboard.level = 3;
                        Chessboard.remainingSteps = 8;
                        Chessboard.levelScore = 300;
                        Chessboard.playerStage = 7;
                        Chessboard.coinGoal = 1;
                        mainFrame.addNameLabel();mainFrame.addLabel();mainFrame.addlevelLabel();mainFrame.remainingSteps();mainFrame.totalScore();mainFrame.addStage();mainFrame.addGoal();mainFrame.addCoinGoal();mainFrame.addCurrentCoin();mainFrame.addNull();

                    }
                    if(Enterframe.modechoose==2){
                        cb=new Chessboard(new Mode2(8,8));
                        Chessboard.totalScore = 0;
                        Chessboard.score1 = 0;
                        Chessboard.score2 = 0;
                        Chessboard.score3 = 0;
                        Chessboard.level = 3;
                        Chessboard.remainingSteps = 8;
                        Chessboard.levelScore = 350;
                        Chessboard.playerStage = 7;
                        Chessboard.coinGoal = 1;
                        mainFrame.addNameLabel();mainFrame.addLabel();mainFrame.addlevelLabel();mainFrame.remainingSteps();mainFrame.totalScore();mainFrame.addStage();mainFrame.addGoal();mainFrame.addNull();

                    }
//                cb.pausetime =getSleeptime();

                    GameController gameController = new GameController(mainFrame.getChessboardComponent(), cb);
                    mainFrame.setGameController(gameController);
                    gameController.setStatusLabel(mainFrame.getStatusLabel());
                    gameController.setStepsLabel(mainFrame.getStepsLabel());
                    gameController.setNameLabel(mainFrame.getNameLabel());
                    gameController.setTotalScore(mainFrame.getTotalScore());
                    gameController.setPlayerStage(mainFrame.getPlayerStage());
                    gameController.setCoinGoal(mainFrame.getCoinGoal());
                    gameController.setCurrentCoin(mainFrame.getCurrentcoin());
                    gameController.setGoal(mainFrame.getGoal());
                    mainFrame.setVisible(true);
//                frame.setVisible(false);

                }
            }
        }).getAction());






        chooselevel.add(level);
        chooselevel.add(level2);
        chooselevel.add(level3);
        menuBar.add(chooselevel);



        JMenuItem roboton=new JMenuItem(new JMenuItem(new AbstractAction("人机") {
            @Override
            public void actionPerformed(ActionEvent e) {
                isrobot=true;
            }
        }).getAction());
        JMenuItem robotoff=new JMenuItem(new JMenuItem(new AbstractAction("玩家") {
            @Override
            public void actionPerformed(ActionEvent e) {
                isrobot=false;
            }
        }).getAction());


        roboton.setMnemonic('Q');
        roboton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isrobot=true;
            }
        });

        robotoff.setMnemonic('S');
        robotoff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isrobot=false;
            }
        });


        robotgame.add(roboton);
        robotgame.add(robotoff);
        menuBar.add(robotgame);
       JMenuItem autoon=new JMenuItem(new JMenuItem(new AbstractAction("自动") {
            @Override
            public void actionPerformed(ActionEvent e) {

                isauto=true;
            }
        }).getAction());
       JMenuItem autooff=new JMenuItem(new JMenuItem(new AbstractAction("手动") {
            @Override
            public void actionPerformed(ActionEvent e) {
                isauto=false;
            }
        }).getAction());

        autoon.setMnemonic('D');
        autoon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isauto=true;
            }
        });

        autooff.setMnemonic('F');
        autooff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isauto=false;
            }
        });


        automode.add(autoon);
       automode.add(autooff);
       menuBar.add(automode);

       //动画速度
       JMenu effectSpeed=new JMenu("动画速度");
       JMenuItem fast=new JMenuItem(new JMenuItem(new AbstractAction("较快") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Chessboard.pausetime=500;
            }
       }).getAction());
       JMenuItem mid=new JMenuItem(new JMenuItem(new AbstractAction("默认") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Chessboard.pausetime=800;
            }
       }).getAction());
       JMenuItem slow=new JMenuItem(new JMenuItem(new AbstractAction("较慢") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Chessboard.pausetime=1300;
            }
       }).getAction());
        effectSpeed.add(fast);
        effectSpeed.add(mid);
        effectSpeed.add(slow);

        JMenu findRecord=new JMenu("查询记录");
        JMenuItem recording=new JMenuItem(new JMenuItem(new AbstractAction("游戏记录") {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 指定文件路径
                String filePath = "C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\Filepart\\recording.txt";
                // 创建File对象
                File file = new File(filePath);
                if (file.exists()) {
                    openFile(file);
                } else {
                    System.out.println("文件不存在: " + filePath);
                }
            }
            private static void openFile(File file) {
                // 获取Desktop实例
                Desktop desktop = Desktop.getDesktop();

                try {
                    // 使用默认关联的应用程序打开文件
                    desktop.open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).getAction());
        findRecord.add(recording);

        JMenuItem bestrecording=new JMenuItem(new JMenuItem(new AbstractAction("最高纪录") {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 指定文件路径
                String filePath = "C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\Filepart\\bestrecord.txt";

                // 创建File对象
                File file = new File(filePath);

                // 检查文件是否存在
                if (file.exists()) {
                    openFile(file);
                } else {
                    System.out.println("文件不存在: " + filePath);
                }
            }

            private static void openFile(File file) {
                // 获取Desktop实例
                Desktop desktop = Desktop.getDesktop();

                try {
                    // 使用默认关联的应用程序打开文件
                    desktop.open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).getAction());
        findRecord.add(bestrecording);

        JMenu setBackground=new JMenu("选择背景");
        JMenuItem open = new JMenuItem(new AbstractAction("打开"){
            JFileChooser chooser1 = new JFileChooser(".");
            @Override
            public void actionPerformed(ActionEvent e) {
                chooser1.showOpenDialog(null);
                File imageFile = chooser1.getSelectedFile();
                try {
                    String imagePath = imageFile.getAbsolutePath();//看路径
                    image.backgroundImage = new ImageIcon(imagePath).getImage();
                    image.repaint();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        setBackground.add(open);

        menuBar.add(effectSpeed);
        menuBar.add(setBackground);
        menuBar.add(findRecord);
        setJMenuBar(menuBar);
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

    public boolean existNulls(){
        for (int i = 0; i < gameController.getModel().threematch.chars.length; i++) {
            for (int j = 0; j <gameController.getModel().threematch.chars.length ; j++) {
                if(gameController.getModel().threematch.chars[i][j] == '0'){
                    return true;
                }
            }
        }
        return false;
    }
    public ChessGameFrame(int width, int height) throws AWTException {
        setTitle("玩家："+ Enterframe.name); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        //在这里改背景
        setContentPane(image);
        setLayout(new BorderLayout());

        //尝试新添加一个背景
        addMenubar();
        addChessboard();
//        addLabel();
        addUpdateButton();
        addNewButton();
//        remainingSteps();
        addSwapConfirmButton();
        addNextStepButton();
        addTipsButton();
        //加了hammer后存在一些问题
        addKnock();addBomb();addBackButton();
//        addlevelLabel();
//        addNameLabel();
//        totalScore();
//        addStage();
//        addGoal();
//        if(Enterframe.modechoose == 1){
//            addCoinGoal();
//        }
//        addNull();


        timer1.setRepeats(true);
        timer1.start();
        timer2.setRepeats(true);
        timer2.start();
        timer3.setRepeats(true);
        timer3.start();
        timer4.setRepeats(true);
        timer4.start();
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                timer1.stop();
                timer2.stop();
                timer3.stop();
                timer4.stop();
            }
        });
    }


    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }

    /**
     * 在游戏面板中添加标签
     */
   public void addNameLabel() {
        this.nameLabel = new JLabel("Name: " + Enterframe.name);
        nameLabel.setLocation(HEIGTH, HEIGTH / 100);
//        System.out.println("已执行");
        nameLabel.setSize(200, 60);
        nameLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        nameLabel.setForeground(Color.RED);
        add(nameLabel);
    }


    public void addLabel() {
       if(Chessboard.playerStage == 1 || Chessboard.playerStage == 4 || Chessboard.playerStage == 7){
           this.statusLabel = new JLabel("Currentscore: " + Chessboard.score1);
       } else if (Chessboard.playerStage == 2 || Chessboard.playerStage == 5 || Chessboard.playerStage == 8) {
           this.statusLabel = new JLabel("Currentscore: " + Chessboard.score2);
       }else {
           this.statusLabel = new JLabel("Currentscore: " + Chessboard.score3);
       }
       statusLabel.setLocation(HEIGTH, HEIGTH / 10 +450);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        statusLabel.setForeground(Color.RED);
        add(statusLabel);
    }
    public void addlevelLabel() {
        this.levelLabel = new JLabel("level: " + Chessboard.level );
        levelLabel.setLocation(HEIGTH/2 , HEIGTH / 100);
        levelLabel.setSize(200, 60);
        levelLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        levelLabel.setForeground(Color.RED);
        add(levelLabel);
    }
    public void remainingSteps() {
        this.stepsLabel = new JLabel("Remaining steps: " + Chessboard.remainingSteps);
        stepsLabel.setLocation(HEIGTH, HEIGTH/10 + 500);
        stepsLabel.setSize(400, 60);
        stepsLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        stepsLabel.setForeground(Color.RED);
        add(stepsLabel);
    }
    public void totalScore() {
        this.totalScore = new JLabel("Totalscore: " + Chessboard.totalScore);
        totalScore.setLocation(HEIGTH, HEIGTH/100 + 60 );
        totalScore.setSize(400, 60);
        totalScore.setFont(new Font("Rockwell", Font.BOLD, 20));
        totalScore.setForeground(Color.RED);
        add(totalScore);
    }
    public void addStage() {
        if(Chessboard.level == 1){
            this.playerStage = new JLabel("Stage: " + Chessboard.playerStage%3);
        }
        if(Chessboard.level == 2){
            this.playerStage = new JLabel("Stage: " + Chessboard.playerStage%3);
        }
        if(Chessboard.level == 3){
            this.playerStage = new JLabel("Stage: " + (Chessboard.playerStage%3));
        }
        playerStage.setLocation(HEIGTH, HEIGTH/100 + 140);
        playerStage.setSize(400, 60);
        playerStage.setFont(new Font("Rockwell", Font.BOLD, 20));
        playerStage.setForeground(Color.RED);
        add(playerStage);
    }
    public void addGoal() {
        this.goal = new JLabel("Goal: " + Chessboard.levelScore);
       goal.setLocation(HEIGTH, HEIGTH/100 + 100);
       goal.setSize(400, 60);
        goal.setFont(new Font("Rockwell", Font.BOLD, 20));
        goal.setForeground(Color.RED);
        add(goal);
    }
    public void addCoinGoal() {
        this.coinGoal = new JLabel("CoinGoal: " + Chessboard.coinGoal);
        coinGoal.setLocation(HEIGTH +120, HEIGTH/100 + 100);
        coinGoal.setSize(400, 60);
        coinGoal.setFont(new Font("Rockwell", Font.BOLD, 20));
        coinGoal.setForeground(Color.RED);
        add(coinGoal);
    }
    public void addCurrentCoin() {
       this.currentcoin = new JLabel("Currentcoin: " + Mode1.coin);
        currentcoin.setLocation(HEIGTH, HEIGTH/10 + 475);
        currentcoin.setSize(200, 60);
        currentcoin.setFont(new Font("Rockwell", Font.BOLD, 20));
        currentcoin.setForeground(Color.RED);
        add(currentcoin);
    }

    public void addNull() {
        this.Null = new JLabel("" );
        Null.setLocation(HEIGTH, HEIGTH/10 + 100 );
        Null.setSize(400, 60);
        Null.setFont(new Font("Rockwell", Font.BOLD, 20));
        Null.setForeground(Color.RED);
        add(Null);
    }


    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

   public void addUpdateButton() {
        updatebutton.addActionListener(e -> {
           if(existNulls()){
               JOptionPane.showMessageDialog(null,"You need to click next step!","Operation hint",JOptionPane.WARNING_MESSAGE);
                   return;
           }
            if(count < 3){
               int x = Chessboard.totalScore;
                gameController.upDate(Enterframe.modechoose);
               count++;
               Chessboard.totalScore = x;
               totalScore.setText("Totalscore: " + Chessboard.totalScore);
               gameController.getModel().drawAgain();

           }else{
               JOptionPane.showMessageDialog(null,"You have used the tips three times!","",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        updatebutton.setLocation(HEIGTH, HEIGTH / 10 + 120);
        updatebutton.setSize(200, 60);
        updatebutton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(updatebutton);
    }
    public void addNewButton(){
       JButton New=new JButton("New");
       New.setLocation(HEIGTH+200, HEIGTH / 10 + 120);
       New.setSize(80, 60);
       New.setFont(new Font("Rockwell", Font.BOLD, 20));
       add(New);
       New.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if(!hasnew){
                   if(gameController.selectedPoint==null){
                       JOptionPane.showMessageDialog(null,"Choose one piece to renewed!","Operation hint",JOptionPane.WARNING_MESSAGE);
                   }else{
                       var point1 = (ChessComponent) gameController.view.getGridComponentAt(gameController.selectedPoint).getComponent(0);

                       if(gameController.selectedPoint!=null&&gameController.selectedPoint2==null||gameController.selectedPoint2!=null&&gameController.selectedPoint==null){
                           int x=0,y=0;
                           if(gameController.selectedPoint!=null){
                               x=gameController.selectedPoint.getRow();y=gameController.selectedPoint.getCol();
                           }else{
                               x=gameController.selectedPoint2.getRow();y=gameController.selectedPoint2.getCol();
                           }
                           if(gameController.getModel().threematch.chars[x][y]=='-'||gameController.getModel().threematch.chars[x][y]=='+'){
                               JOptionPane.showMessageDialog(null,"This piece can't be renewed!","Operation hint",JOptionPane.WARNING_MESSAGE);
                           }else{
                               hasnew=true;
                               char c=gameController.model.threematch.chars[x][y];
                               while(gameController.model.threematch.chars[x][y]==c){
                                   setPieceChar(x,y,gameController.model.threematch.chars, new Random());
                               }
                               gameController.PauseDrawChesspiece();
                               boolean flag=true;
                               while (flag){
                                   flag=isMatched(gameController.model.threematch.chars,gameController.model.threematch.state,gameController.row, gameController.col,gameController.model.threematch.isAll);
                                   gameController.getModel().threematch.caculateSum();
                                   gameController.getModel().threematch.drop();
                                   gameController.PauseDrawChesspiece();
                                   gameController.getModel().threematch.fillEmpty();
                                   gameController.PauseDrawChesspiece();
                               }
                           }
                       }else{
                           JOptionPane.showMessageDialog(null,"Choose one piece to renewed!","Operation hint",JOptionPane.WARNING_MESSAGE);
                       }
                       if(gameController.selectedPoint2!=null){
                           var pointx = (ChessComponent) gameController.view.getGridComponentAt(gameController.selectedPoint2).getComponent(0);
                           pointx.setSelected(false);
                           pointx.repaint();
                       }

                       gameController.selectedPoint=null;
                       gameController.selectedPoint2=null;

                       point1.setSelected(false);
                       point1.repaint();
                   }
               }else{
                   JOptionPane.showMessageDialog(null,"Renew has been used!","Operation hint",JOptionPane.WARNING_MESSAGE);
               }
           }
       });
    }
    private void addSwapConfirmButton() {
        swapconfirmbutton.addActionListener((e) -> chessboardComponent.swapChess());
        swapconfirmbutton.setLocation(HEIGTH, HEIGTH / 10 + 200);
        swapconfirmbutton.setSize(200, 60);
        swapconfirmbutton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(swapconfirmbutton);
    }

    public void addNextStepButton() {
        Back.addActionListener((e) -> chessboardComponent.nextStep());
        Back.setLocation(HEIGTH, HEIGTH / 10 + 280);
        Back.setSize(200, 60);
        Back.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(Back);
    }
    public void addKnock(){
        JButton knockbutton=new JButton("Knock");//把hammer图片icon放进去
        knockbutton.setLocation(HEIGTH-50, HEIGTH / 10 + 440);
        knockbutton.setSize(100, 30);
        knockbutton .setFont(new Font("Rockwell", Font.BOLD, 20));
        add(knockbutton);
        knockbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //找到唯一的
                //没有和两个都报错
                //消除，移动
                swapCount++;
                ReadAndOverwrite readAndOverwrite = new ReadAndOverwrite();
                List<String> currentgame=new ArrayList<>();
                currentgame=createGameList(Enterframe.name,Chessboard.playerStage,Mode1.coin,remainingSteps,Chessboard.score1,Chessboard.score2,Chessboard.score3,gameController.getModel().threematch);
                readAndOverwrite.writeFileFromList("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\Filepart\\CurrentChessboard.txt",currentgame);
                if(knock){
                    if(gameController.selectedPoint==null){
                        JOptionPane.showMessageDialog(null,"Choose one piece to remove!","Operation hint",JOptionPane.WARNING_MESSAGE);
                    }else{
                        var point1 = (ChessComponent) gameController.view.getGridComponentAt(gameController.selectedPoint).getComponent(0);

                        if(gameController.selectedPoint!=null&&gameController.selectedPoint2==null||gameController.selectedPoint2!=null&&gameController.selectedPoint==null){
                            int x=0,y=0;
                            if(gameController.selectedPoint!=null){
                                x=gameController.selectedPoint.getRow();y=gameController.selectedPoint.getCol();
                            }else{
                                x=gameController.selectedPoint2.getRow();y=gameController.selectedPoint2.getCol();
                            }
                            if(gameController.getModel().threematch.chars[x][y]=='-'||gameController.getModel().threematch.chars[x][y]=='+'){
                                JOptionPane.showMessageDialog(null,"This piece can't be removed!","Operation hint",JOptionPane.WARNING_MESSAGE);
                            }else{
                                knock=false;
                                gameController.getModel().threematch.chars[x][y]='k';
                                gameController.paintChesspiece();
                                musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\锤子音效1.wav");

                                gameController.getModel().threematch.chars[x][y]='0';
                                gameController.getModel().threematch.drop();
                                gameController.PauseDrawChesspiece();

                                gameController.getModel().threematch.fillEmpty();
                                gameController.PauseDrawChesspiece();
                                boolean flag=true;
                                while (flag){
                                    flag=isMatched(gameController.model.threematch.chars,gameController.model.threematch.state,gameController.row, gameController.col,gameController.model.threematch.isAll);
                                    gameController.getModel().threematch.caculateSum();
                                    gameController.getModel().threematch.drop();
                                    gameController.PauseDrawChesspiece();
                                    gameController.getModel().threematch.fillEmpty();
                                    gameController.PauseDrawChesspiece();
                                }
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Choose one piece to remove!","Operation hint",JOptionPane.WARNING_MESSAGE);
                        }
                        var pointx = (ChessComponent) gameController.view.getGridComponentAt(gameController.selectedPoint2).getComponent(0);
                        gameController.selectedPoint=null;
                        gameController.selectedPoint2=null;

                        point1.setSelected(false);
                        point1.repaint();

                        pointx.setSelected(false);
                        pointx.repaint();
                    }

                }else{
                    JOptionPane.showMessageDialog(null,"Knock has been used!","Operation hint",JOptionPane.WARNING_MESSAGE);
                }

            }
        });
    }
    public static boolean isInboundary(int row,int col,int x,int y){
        if(x>=0&&x<row&&y>=0&&y<=col)return true;
        return false;
    }
    public void addBomb(){
        JButton bombbutton=new JButton("Bomb");//把hammer图片icon放进去
        bombbutton.setLocation(HEIGTH+50, HEIGTH / 10 + 440);
        bombbutton.setSize(100, 30);
        bombbutton .setFont(new Font("Rockwell", Font.BOLD, 20));
        add(bombbutton);
        bombbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //找到唯一的
                //没有和两个都报错
                //消除，移动
                swapCount++;
                ReadAndOverwrite readAndOverwrite = new ReadAndOverwrite();
                List<String> currentgame=new ArrayList<>();
                currentgame=createGameList(Enterframe.name,Chessboard.playerStage,Mode1.coin,remainingSteps,Chessboard.score1,Chessboard.score2,Chessboard.score3,gameController.getModel().threematch);
                readAndOverwrite.writeFileFromList("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\Filepart\\CurrentChessboard.txt",currentgame);
                if(bomb){
                    if(gameController.selectedPoint==null){
                        JOptionPane.showMessageDialog(null,"Choose one piece to bomb!","Operation hint",JOptionPane.WARNING_MESSAGE);
                    }else {
                        var point1 = (ChessComponent) gameController.view.getGridComponentAt(gameController.selectedPoint).getComponent(0);

                        if (gameController.selectedPoint != null && gameController.selectedPoint2 != null) {
                            JOptionPane.showMessageDialog(null, "Choose one piece to bomb!", "Operation hint", JOptionPane.WARNING_MESSAGE);
                        } else {
                            int x = 0, y = 0;
                            x = gameController.selectedPoint.getRow();
                            y = gameController.selectedPoint.getCol();
                            if (gameController.getModel().threematch.chars[x][y] == '-' || gameController.getModel().threematch.chars[x][y] == '+') {
                                JOptionPane.showMessageDialog(null, "This piece can't be bombed!", "Operation hint", JOptionPane.WARNING_MESSAGE);
                            } else {
                                if (isInboundary(gameController.row, gameController.col, x - 1, y) && gameController.getModel().threematch.chars[x - 1][y] != '-')
                                    gameController.getModel().threematch.chars[x - 1][y] = '0';
                                if (isInboundary(gameController.row, gameController.col, x, y - 1) && gameController.getModel().threematch.chars[x][y - 1] != '-')
                                    gameController.getModel().threematch.chars[x][y - 1] = '0';
                                if (isInboundary(gameController.row, gameController.col, x + 1, y) && gameController.getModel().threematch.chars[x + 1][y] != '-')
                                    gameController.getModel().threematch.chars[x + 1][y] = '0';
                                if (isInboundary(gameController.row, gameController.col, x, y + 1) && gameController.getModel().threematch.chars[x][y + 1] != '-')
                                    gameController.getModel().threematch.chars[x][y + 1] = '0';

                                bomb = false;
                                gameController.getModel().threematch.chars[x][y] = 'b';//可以加一张图片
                                gameController.paintChesspiece();
                                musicEffectAdjuster.playMusic("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\爆炸.wav");

                                gameController.getModel().threematch.chars[x][y] = '0';
                                gameController.getModel().threematch.drop();
                                gameController.PauseDrawChesspiece();

                                gameController.getModel().threematch.fillEmpty();
                                gameController.PauseDrawChesspiece();
                                boolean flag = true;
                                while (flag) {
                                    flag = isMatched(gameController.model.threematch.chars, gameController.model.threematch.state, gameController.row, gameController.col, gameController.model.threematch.isAll);
                                    gameController.getModel().threematch.caculateSum();
                                    gameController.getModel().threematch.drop();
                                    gameController.PauseDrawChesspiece();

                                    gameController.getModel().threematch.fillEmpty();
                                    gameController.PauseDrawChesspiece();
                                }
                                gameController.selectedPoint = null;
                                gameController.selectedPoint2 = null;

                                point1.setSelected(false);
                                point1.repaint();
                            }
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Bomb has been used!","Operation hint",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
    public void addBackButton() {
        JButton Backbutton=new JButton("Back");
        Backbutton.setLocation(HEIGTH+150, HEIGTH / 10 + 440);
        Backbutton.setSize(100, 30);
        Backbutton .setFont(new Font("Rockwell", Font.BOLD, 20));
        add(Backbutton);
        Backbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Chessboard.swapCount == 0){
                    JOptionPane.showMessageDialog(null,"You can not use BACK!","Operation hint",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(back) {
                    back=false;
                    //在每次swap后记录当前的
                    ReadAndOverwrite readAndOverwrite = new ReadAndOverwrite();
                    List<String> currentlist=new ArrayList<>();
                    currentlist=readAndOverwrite.readFileToList("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\Filepart\\CurrentChessboard.txt");
                    char[][] currentchars=getChars(currentlist);

                    for (int i = 0; i < gameController.row; i++) {
                        for (int j = 0; j < gameController.col; j++) {
                            gameController.model.threematch.chars[i][j]=currentchars[i][j];
                        }
                    }
                        Enterframe.name = getNames(currentlist);
                        Chessboard.playerStage=getStage(currentlist);
                        Chessboard.remainingSteps=getRemainingStep(currentlist);
                        Chessboard.totalScore=getScore1(currentlist)+getScore2(currentlist)+getScore3(currentlist);
                        Chessboard.level = Enterframe.getLevel(Chessboard.playerStage);
                        Mode1.coin = getCoin(currentlist);
                        Chessboard.coinGoal = Enterframe.getcoinGoal(Chessboard.playerStage);

                        if(Chessboard.playerStage == 1 || Chessboard.playerStage == 4 || Chessboard.playerStage == 7){
                            Chessboard.score1   = getScore1(currentlist);
                            statusLabel.setText("Currentscore: " + Chessboard.score1);
                        } else if (Chessboard.playerStage == 2 || Chessboard.playerStage == 5 || Chessboard.playerStage == 8) {
                            Chessboard.score2   = getScore2(currentlist);
                            statusLabel.setText("Currentscore: " + Chessboard.score2);
                        }else {
                            Chessboard.score3   = getScore3(currentlist);
                            statusLabel.setText("Currentscore: " + Chessboard.score3);
                        }
                        nameLabel.setText("Name: " + Enterframe.name);
                        stepsLabel.setText("Remaining steps: " + Chessboard.remainingSteps);
                        totalScore.setText("Totalscore: " + Chessboard.totalScore);
                        playerStage.setText("Stage: " + Chessboard.playerStage%3);
                         goal.setText("Goal: " + Chessboard.levelScore);
                         if(currentcoin != null){
                            currentcoin.setText("Currentcoin: " + Mode1.coin);
                        }
                       if(coinGoal != null){
                           coinGoal.setText("CoinGoal: " + Chessboard.coinGoal);
                       }

                        gameController.model.drawAgain();
                    //把所有信息读入
                    //加判断哪一个score是这一局的score
                    //重新画出所有的棋子
                }else{
                    JOptionPane.showMessageDialog(null,"Back has been used!","Operation hint",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
    public void PauseDrawChesspiece(){
        Timer timer = new Timer(pausetime, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < gameController.model.threematch.chars.length; i++) {
                    for (int j = 0; j <  gameController.model.threematch.chars.length; j++) {
                        gameController.model.getGrid()[i][j].getPiece().setColor(Constant.getColorMap().get(ChessPiece.nameDecide(gameController.model.threematch.chars[i][j])));
                        gameController.model.getGrid()[i][j].getPiece().setName(ChessPiece.nameDecide(gameController.model.threematch.chars[i][j]));
                        gameController.model.getGrid()[i][j].getPiece().chessComponent.revalidate();
                        gameController.model.getGrid()[i][j].getPiece().chessComponent.repaint();
                    }
                }
            }
        });

        timer.setRepeats(false);
        timer.start();
    }//设置了一个延迟画图的方法
    private void addTipsButton() {
        tipsbutton.setLocation(HEIGTH, HEIGTH / 10 + 360);
        tipsbutton.setSize(200, 60);
        tipsbutton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(tipsbutton);

        tipsbutton.addActionListener(e -> {
           if(gameController.getModel().threematch.existChange()){
               int num=gameController.getModel().threematch.removablePostionList.size();
               Random random=new Random();
               int n=random.nextInt(num);
               removablePostion tipposition=gameController.getModel().threematch.removablePostionList.get(n);
               int x1=tipposition.x,y1=tipposition.y;
               int dr=0,dc=0;
               if(tipposition.d=='r'){
                   dc=1;dr=0;
               }else{
                   dr=1;dc=0;
               }
               int x2=x1+dr,y2=y1+dc;

               gameController.selectedPoint=new ChessboardPoint(x1,y1);
               gameController.selectedPoint2=new ChessboardPoint(x2,y2);

               var point1 = (ChessComponent) gameController.view.getGridComponentAt(gameController.selectedPoint).getComponent(0);
               var point2 = (ChessComponent) gameController.view.getGridComponentAt(gameController.selectedPoint2).getComponent(0);
               point1.setSelected(true);
               point1.repaint();
               point2.setSelected(true);
               point2.repaint();
           }
        });
    }


    public JLabel getStepsLabel() {
        return stepsLabel;
    }

    public void setStepsLabel(JLabel stepsLabel) {
        this.stepsLabel = stepsLabel;
    }
    public JLabel getLevelLabel() {
        return levelLabel;
    }
    public void setLevelLabel(JLabel levelLabel) {
        this.levelLabel = levelLabel;
    }
}
class BackgroundPanel extends JPanel {
    public Image backgroundImage;
    public BackgroundPanel(){
        setBackgroundImage("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\Enters\\Enterbackground.png");
    }

    public void setBackgroundImage(String imagePath) {
        this.backgroundImage = new ImageIcon(imagePath).getImage();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        }else{
            System.out.println("没画出来");
        }
    }

}


