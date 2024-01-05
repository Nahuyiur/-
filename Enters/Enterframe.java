package Enters;
import Core.Mode0;
import Core.Mode1;
import Core.Mode2;
import Filepart.AddWrite;
import Filepart.CreateTxtFile;
import MusicController.BGMadjuster;
import MusicController.MusicEffectAdjuster;
import MusicController.VolumeAdjuster;
import controller.GameController;
import model.ChessPiece;
import model.Chessboard;
import Core.threeMatch;
import model.Constant;
import view.ChessGameFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Filepart.ReadAndOverwrite;

import static Filepart.createList.createGameList;
import static Filepart.getInformation.*;
import static Filepart.judgeMode.judgemode;
import static model.Chessboard.remainingSteps;

public class Enterframe extends JFrame {
    JFrame frame=new JFrame("疯狂三消！！！");
    Container c=frame.getContentPane();
    JMenuBar menuBar=new JMenuBar();
    File background0= new File("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\Enters\\Enterpicture.png");
    BufferedImage image;
    ArrayList<String> gamelist;
    public static String name;
    public int row=8;
    public int col=8;
    public int sleeptime= 800;
    public static int modechoose = 0;

    public static threeMatch threeMatch0 = new threeMatch();
    public Enterframe(){
        this.initial();
    }

    JPanel drawArea = new MyCanvas();
    {
        try {
            image = ImageIO.read(background0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    class MyCanvas extends JPanel{
        @Override
        public void paint(Graphics g) {
            if (image!=null){
                g.drawImage(image,0,0,this);
            }
        }//draw here and can be seen
    }

    //设置菜单内容
    public void addMenu(){
        JMenu setMenu=new JMenu("设置");
        JMenu findRecord=new JMenu("查询记录");
        JMenu setBackground=new JMenu("选择背景");//设置背景的菜单
        JMenu effectSpeed=new JMenu("动画速度");

        //模式选取的菜单栏
        JMenu modeMenu=new JMenu("选择模式");
        JButton mode0 = new JButton(new AbstractAction("经典模式"){
            @Override
            public void actionPerformed(ActionEvent e) {
                modeMenu.setText("当前模式：经典模式");
                modechoose=0;
            }
        });
        JButton mode1 = new JButton(new AbstractAction("金币模式"){
            @Override
            public void actionPerformed(ActionEvent e) {
                modeMenu.setText("当前模式：金币模式");
                modechoose=1;
            }
        });
        JButton mode2= new JButton(new AbstractAction("障碍模式"){
            @Override
            public void actionPerformed(ActionEvent e) {
                modeMenu.setText("当前模式：障碍模式");
                modechoose=2;
            }
        });
        modeMenu.add(mode0);
        modeMenu.add(mode1);
        modeMenu.add(mode2);

        //难度选取以及设置
        JMenu levelmenu = new JMenu("选择关卡");
        JButton  level1= new JButton("Level 1");
        JButton level2 = new JButton("Level 2");
        JButton level3 = new JButton("Level 3");
        levelmenu.add(level1);
        levelmenu.add(level2);
        levelmenu.add(level3);
        MouseListener mouseListener0 = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Chessboard.level = 1;
                Chessboard.remainingSteps = 8;
                Chessboard.levelScore = 200;
                Chessboard.playerStage = 1;
                Chessboard.totalScore = 0;
                Chessboard.coinGoal = 1;
                levelmenu.setText("当前关卡：Level 1");
            }
        };

        MouseListener mouseListener1 = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Chessboard.level = 2;
                Chessboard.remainingSteps = 8;
                Chessboard.levelScore = 300;
                Chessboard.playerStage = 4;
                Chessboard.totalScore = 0;
                Chessboard.coinGoal = 1;
                levelmenu.setText("当前关卡：Level 2");
            }
        };
        MouseListener mouseListener2 = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Chessboard.level = 3;
                Chessboard.remainingSteps = 8;
                Chessboard.levelScore = 350;
                Chessboard.playerStage = 7;
                Chessboard.totalScore = 0;
                Chessboard.coinGoal = 1;
                levelmenu.setText("当前关卡：Level 3");

            }
        };
        level1.addMouseListener(mouseListener0);
        level2.addMouseListener(mouseListener1);
        level3.addMouseListener(mouseListener2);

        JMenuItem fast=new JMenuItem(new JMenuItem(new AbstractAction("较快") {
            @Override
            public void actionPerformed(ActionEvent e) {
                sleeptime=500;
            }
        }).getAction());
        JMenuItem mid=new JMenuItem(new JMenuItem(new AbstractAction("默认") {
            @Override
            public void actionPerformed(ActionEvent e) {
                sleeptime=800;
            }
        }).getAction());
        JMenuItem slow=new JMenuItem(new JMenuItem(new AbstractAction("较慢") {
            @Override
            public void actionPerformed(ActionEvent e) {
                sleeptime=1300;
            }
        }).getAction());
        effectSpeed.add(fast);
        effectSpeed.add(mid);
        effectSpeed.add(slow);

        //关于音乐的参数设置
        JMenuItem music=new JMenuItem(new JMenuItem(new AbstractAction("背景音乐") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BGMadjuster();
            }
        }).getAction());
        JMenuItem musicEffect=new JMenuItem(new JMenuItem(new AbstractAction("音乐效果") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MusicEffectAdjuster();
            }
        }).getAction());
        JMenuItem volume=new JMenuItem(new JMenuItem(new AbstractAction("音量") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VolumeAdjuster();
            }
        }).getAction());

        setMenu.add(music);
        setMenu.add(musicEffect);
        setMenu.add(volume);


        //调记录
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


        //选择背景的菜单设置
        JMenuItem open = new JMenuItem(new AbstractAction("打开"){
            JFileChooser chooser1 = new JFileChooser(".");
            @Override
            public void actionPerformed(ActionEvent e) {
                chooser1.showOpenDialog(frame);
                File imageFile = chooser1.getSelectedFile();
                try {
                    image = ImageIO.read(imageFile);
                    String imagePath = imageFile.getAbsolutePath();//看路径
                    drawArea.repaint();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        setBackground.add(open);

        menuBar.add(setMenu);
        menuBar.add(effectSpeed);
        menuBar.add(setBackground);
        menuBar.add(findRecord);
        menuBar.add(levelmenu);
        menuBar.add(modeMenu);

        frame.setJMenuBar(menuBar);

        //初始状态是经典模式
        mode0.setSelected(true);
        modeMenu.setText("当前模式：经典模式");
        //初始状态下设置成level1，属性全部改成level1的
        level1.setSelected(true);
        Chessboard.level = 1;
        Chessboard.remainingSteps = 8;
        Chessboard.levelScore = 200;
        Chessboard.playerStage = 1;
        Chessboard.score1 = 0;
        Chessboard.totalScore = 0;
        Chessboard.coinGoal = 1;
        Enterframe.modechoose = 0;
        Mode1.coin = 0;
        levelmenu.setText("当前关卡：Level 1");

    }

    //设置按钮

    // 判断文件是否是txt文件
    public static boolean isTxtFile(File file) {
        String fileName = file.getName();
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            String fileExtension = fileName.substring(lastDotIndex + 1);
            return fileExtension.equalsIgnoreCase("txt");
        }
        return false;
    }
    public static boolean isSizeRight(List<String> list){
        if(list.size()!=10)return false;
        for (int i = 2; i < list.size() ; i++) {
            String str=(String) list.get(i);
            if(str.length()!=8)return false;
        }
        return true;
    }
    public static boolean isCharsRight(List<String> list){
        for (int i = 2; i < list.size() ; i++) {
            String str=(String) list.get(i);
            for (int j = 0; j < str.length(); j++) {
                if(!charJudge(str.charAt(j)))return false;
            }
        }
        return true;
    }
    public static boolean charJudge(char c){
        switch (c){
            case '1':
                return true;
            case '2':
                return true;
            case '3':
                return true;
            case '4':
                return true;
            case '5':
                return true;
            case '6':
                return true;
            case '+':
                return true;
            case '-':
                return true;
            case 'a':
                return true;
            default:
                return false;
        }
    }
    public void addButtons(){//只与下方的三个按键有关
        JPanel bottom=new JPanel();
        JButton startButton=new JButton("开始游戏");
        JButton closeButton=new JButton("关闭游戏");
        JButton readButton=new JButton("读取存档");
        MouseListener reader=new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)  {
                super.mouseClicked(e);
                // 判断文件是否是txt文件
                JFileChooser chooser2 = new JFileChooser(".");
                int result=chooser2.showOpenDialog(frame);
                if(result==JFileChooser.APPROVE_OPTION) {
                    File gameRecordFile = chooser2.getSelectedFile();
                    if (!isTxtFile(gameRecordFile)) {
                        JOptionPane.showMessageDialog(null, "请选择一个txt文件！", "错误编码：101", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String gameRecordFileAbsolutePath = gameRecordFile.getAbsolutePath();//看路径
                        ReadAndOverwrite readAndOverwrite = new ReadAndOverwrite();
                        gamelist = (ArrayList<String>) readAndOverwrite.readFileToList(gameRecordFileAbsolutePath);
                        if(!isSizeRight(gamelist)) {
                            JOptionPane.showMessageDialog(null, "请选择8*8大小的棋盘！", "错误编码：102", JOptionPane.ERROR_MESSAGE);
                        }else{
                            if (!isCharsRight(gamelist)) {
                                JOptionPane.showMessageDialog(null, "存在非指定的棋子", "错误编码：103", JOptionPane.ERROR_MESSAGE);
                            }else{

                                Enterframe.name=getNames(gamelist);
                                JOptionPane.showMessageDialog(null,"读取成功！\n继续你的冒险吧,"+name+"!");
                                //这里的棋盘就应该是符合规定的
                                threeMatch cdthreematch=new threeMatch(gameRecordFileAbsolutePath,row,col);

                                Enterframe.modechoose=judgemode(gamelist);


                                ChessGameFrame mainFrame  = null;
                                try {
                                    mainFrame = new ChessGameFrame(1100, 810);
                                } catch (AWTException ex) {
                                    throw new RuntimeException(ex);
                                }
                                Chessboard cb=new Chessboard(cdthreematch);
                                GameController gameController = new GameController(mainFrame.getChessboardComponent(), cb);
                                //这里改变一下存档
                                if(modechoose==0){
                                    cb=new Chessboard(new Mode0(8,8));
                                    Enterframe.name = getNames(gamelist);
                                    Chessboard.playerStage=getStage(gamelist);
                                    Chessboard.remainingSteps=getRemainingStep(gamelist);
                                    Chessboard.totalScore=getScore1(gamelist)+getScore2(gamelist)+getScore3(gamelist);
                                    Chessboard.level = getLevel(Chessboard.playerStage);
                                    if(Chessboard.playerStage == 1 || Chessboard.playerStage == 4 || Chessboard.playerStage == 7){
                                        cb.threematch.score = getScore1(gamelist);
                                        Chessboard.score1 =  cb.threematch.score;
                                    } else if (Chessboard.playerStage == 2 || Chessboard.playerStage == 5 || Chessboard.playerStage == 8) {
                                        cb.threematch.score = getScore2(gamelist);
                                        Chessboard.score2 =  cb.threematch.score;

                                    }else {
                                        cb.threematch.score = getScore3(gamelist);
                                        Chessboard.score3 =  cb.threematch.score;
                                    }
                                    mainFrame.addNameLabel();mainFrame.addLabel();mainFrame.addlevelLabel();mainFrame.remainingSteps();mainFrame.totalScore();mainFrame.addStage();mainFrame.addGoal();mainFrame.addNull();
                                }

                                if(modechoose==1){
                                    cb=new Chessboard(new Mode1(8,8));
                                    Enterframe.name = getNames(gamelist);
                                    Chessboard.playerStage=getStage(gamelist);
                                    Chessboard.remainingSteps=getRemainingStep(gamelist);
                                    Chessboard.totalScore=getScore1(gamelist)+getScore2(gamelist)+getScore3(gamelist);
                                    Chessboard.level = getLevel(Chessboard.playerStage);
                                    Mode1.coin = getCoin(gamelist);
                                    Chessboard.coinGoal = getcoinGoal(Chessboard.playerStage);
                                    if(Chessboard.playerStage == 1 || Chessboard.playerStage == 4 || Chessboard.playerStage == 7){
                                        cb.threematch.score = getScore1(gamelist);
                                        Chessboard.score1 =  cb.threematch.score;
                                    } else if (Chessboard.playerStage == 2 || Chessboard.playerStage == 5 || Chessboard.playerStage == 8) {
                                        cb.threematch.score = getScore2(gamelist);
                                        Chessboard.score2 =  cb.threematch.score;
                                    }else {
                                        cb.threematch.score = getScore3(gamelist);
                                        Chessboard.score3 =  cb.threematch.score;
                                    }
                                    mainFrame.addNameLabel();mainFrame.addLabel();mainFrame.addlevelLabel();mainFrame.remainingSteps();mainFrame.totalScore();mainFrame.addStage();mainFrame.addGoal();mainFrame.addCoinGoal();mainFrame.addCurrentCoin();mainFrame.addNull();

                                }
                                if(modechoose==2){
                                    cb=new Chessboard(new Mode2(8,8));
                                    Enterframe.name = getNames(gamelist);
                                    Chessboard.playerStage=getStage(gamelist);
                                    Chessboard.remainingSteps=getRemainingStep(gamelist);
                                    Chessboard.totalScore=getScore1(gamelist)+getScore2(gamelist)+getScore3(gamelist);
                                    Chessboard.level = getLevel(Chessboard.playerStage);
                                    if(Chessboard.playerStage == 1 || Chessboard.playerStage == 4 || Chessboard.playerStage == 7){
                                        cb.threematch.score = getScore1(gamelist);
                                        Chessboard.score1 =  cb.threematch.score;
                                    } else if (Chessboard.playerStage == 2 || Chessboard.playerStage == 5 || Chessboard.playerStage == 8) {
                                        cb.threematch.score = getScore2(gamelist);
                                        Chessboard.score2 =  cb.threematch.score;
                                    }else {
                                        cb.threematch.score = getScore3(gamelist);
                                        Chessboard.score3 =  cb.threematch.score;
                                    }
                                    mainFrame.addNameLabel();mainFrame.addLabel();mainFrame.addlevelLabel();mainFrame.remainingSteps();mainFrame.totalScore();mainFrame.addStage();mainFrame.addGoal();mainFrame.addNull();
                                }





//
                                mainFrame.setGameController(gameController);
                                gameController.setStepsLabel(mainFrame.getStepsLabel());
                                gameController.setStatusLabel(mainFrame.getStatusLabel());
                                gameController.setNameLabel(mainFrame.getNameLabel());
                                gameController.setTotalScore(mainFrame.getTotalScore());
                                gameController.setPlayerStage(mainFrame.getPlayerStage());
                                gameController.setCoinGoal(mainFrame.getCoinGoal());
                                gameController.setCurrentCoin(mainFrame.getCurrentcoin());
                                gameController.setGoal(mainFrame.getGoal());
                                Chessboard.totalScore = 0;

                                mainFrame.setVisible(true);
                                frame.setVisible(false);
                            }
                        }
                        //属性中gamelist是array list<String>，用文件读入方法写的
                    }
                } else if (result == JFileChooser.CANCEL_OPTION) {
                    chooser2.cancelSelection();
                }
            }
        };

        MouseListener startenter=new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                name= JOptionPane.showInputDialog("你好啊，冒险者!\n为自己取一个帅气的名字吧：");
                try {
                    while(name.length() == 0) {
                        name = JOptionPane.showInputDialog("名字会作为你冒险的记录\n为自己取一个帅气的名字吧：");
                    }
                }catch (Exception j){
                }
                if(name==null)return;
                JOptionPane.showMessageDialog(null,"很好，"+name+"\n开启你传奇的冒险吧！");

                ChessGameFrame mainFrame = null;
                try {
                    mainFrame = new ChessGameFrame(1100, 810);
                } catch (AWTException ex) {
                    throw new RuntimeException(ex);
                }

               mainFrame.addWindowListener(new WindowAdapter() {
                   @Override
                   public void windowClosing(WindowEvent e) {
                        int option = JOptionPane.showConfirmDialog(frame,"You have not finished the game! Do you want to save the file?","",JOptionPane.YES_NO_OPTION);
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
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    Enterframe newEnterframe = new Enterframe();
                                    newEnterframe.setVisible(true);
                                }
                            });

                        }else{
                        }
                          SwingUtilities.invokeLater(new Runnable() {
                           @Override
                           public void run() {
                               Enterframe newEnterframe = new Enterframe();
                               newEnterframe.setVisible(true);
                           }
                       });


                   }
               });


                Chessboard cb=new Chessboard();


                if(modechoose==0){
                    cb=new Chessboard(new Mode0(8,8));
                    Chessboard.totalScore = 0;
                    Chessboard.score1 = 0;
                    Chessboard.score2 = 0;
                    Chessboard.score3 = 0;
                    mainFrame.addNameLabel();mainFrame.addLabel();mainFrame.addlevelLabel();mainFrame.remainingSteps();mainFrame.totalScore();mainFrame.addStage();mainFrame.addGoal();mainFrame.addNull();

                }
                if(modechoose==1){
                    cb=new Chessboard(new Mode1(8,8));
                    Chessboard.totalScore = 0;
                    Chessboard.score1 = 0;
                    Chessboard.score2 = 0;
                    Chessboard.score3 = 0;
                    mainFrame.addNameLabel();mainFrame.addLabel();mainFrame.addlevelLabel();mainFrame.remainingSteps();mainFrame.totalScore();mainFrame.addStage();mainFrame.addGoal();mainFrame.addCoinGoal();mainFrame.addCurrentCoin();mainFrame.addNull();

                }
                if(modechoose==2){
                    cb=new Chessboard(new Mode2(8,8));
                    Chessboard.totalScore = 0;
                    Chessboard.score1 = 0;
                    Chessboard.score2 = 0;
                    Chessboard.score3 = 0;
                    mainFrame.addNameLabel();mainFrame.addLabel();mainFrame.addlevelLabel();mainFrame.remainingSteps();mainFrame.totalScore();mainFrame.addStage();mainFrame.addGoal();mainFrame.addNull();

                }
                cb.pausetime =getSleeptime();

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
                frame.setVisible(false);
            }
        };


        MouseListener closeenter=new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setVisible(false);
            }
        };
        startButton.addMouseListener(startenter);
        closeButton.addMouseListener(closeenter);
        readButton.addMouseListener(reader);

        bottom.add(startButton);
        bottom.add(closeButton);
        bottom.add(readButton);

        c.add(bottom,BorderLayout.SOUTH);
    }
    public void initial(){
        addMenu();

        frame.setSize(1100,810);
        drawArea.setPreferredSize(new Dimension(1000,800));

        c.add(drawArea,BorderLayout.CENTER);
        addButtons();

        //显示frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
    public int getSleeptime(){
        return sleeptime;
    }

    public static int getLevel(int Stage){
        switch (Stage){
            case 1 :
                Chessboard.level = 1;
                break;
            case 2 :
                Chessboard.level = 2;
                break;
            case 3 :
                Chessboard.level = 3;
                break;
            case 4 :
                Chessboard.level = 2;
                break;
            case 5 :
                Chessboard.level = 3;
                break;
            case 6 :
                Chessboard.level = 3;
                break;
            case 7 :
                Chessboard.level = 3;
                break;
            case 8 :
                Chessboard.level = 3;
                break;
            case 9 :
                Chessboard.level = 3;
                break;
        }
        return Chessboard.level;
    }

    public static int getcoinGoal(int Stage){
        switch (Stage){
            case 1 :
               Chessboard.coinGoal = 1;
                break;
            case 2 :
                Chessboard.coinGoal = 1;
                break;
            case 3 :
                Chessboard.coinGoal = 1;
                break;
            case 4 :
                Chessboard.coinGoal = 1;
                break;
            case 5 :
                Chessboard.coinGoal = 1;
                break;
            case 6 :
                Chessboard.coinGoal = 1;
                break;
            case 7 :
                Chessboard.coinGoal = 1;
                break;
            case 8 :
                Chessboard.coinGoal = 1;
                break;
            case 9 :
                Chessboard.coinGoal = 1;
                break;
        }
        return  Chessboard.coinGoal;
    }






}
