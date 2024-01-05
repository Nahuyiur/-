package MusicController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.*;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class MusicEffectAdjuster extends JFrame {
   private Clip clip;

   private String filePath = "C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\音效.wav";
    public MusicEffectAdjuster() {
        setTitle("音乐效果调节");
        setSize(20, 60);
        //setLocation();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu musicMenu = new JMenu("音乐效果");
        menuBar.add(musicMenu);

        ButtonGroup buttonGroup = new ButtonGroup();

        JRadioButtonMenuItem onMenuItem = new JRadioButtonMenuItem("开");
        JRadioButtonMenuItem offMenuItem = new JRadioButtonMenuItem("关");

        // 将按钮添加到按钮组
        buttonGroup.add(onMenuItem);
        buttonGroup.add(offMenuItem);

        // 添加按钮项到菜单
        musicMenu.add(onMenuItem);
        musicMenu.add(offMenuItem);

        // 设置默认选中状态
        onMenuItem.setSelected(true);

        // 添加事件监听器
        //这个还要修改调整属性，进而影响到系统游戏
        onMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playMusic(filePath);
            }
        });

        offMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopMusic();
            }
        });
        setVisible(true);
    }
   public void playMusic(String filePath){
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }


    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MusicEffectAdjuster();
            }
        });
    }
}
