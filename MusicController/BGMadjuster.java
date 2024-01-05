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

public class BGMadjuster extends JFrame {
    private Clip clip;

    String filePath = "C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\MusicController\\背景音乐.wav";
    public BGMadjuster() {
        setTitle("背景音乐调节");
        setSize(20, 60);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu musicMenu = new JMenu("背景音乐");
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
                setVisible(false);
            }
        });

        offMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopMusic();
                setVisible(false);
            }
        });
        setVisible(true);
    }

    public  void playMusic(String filePath){
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    private void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
