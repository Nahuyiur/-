package Core;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MusicPlayertrial extends JFrame {
    private JButton playButton;
    private Clip clip;

    public MusicPlayertrial() {
        setTitle("音乐播放器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        playButton = new JButton("开");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleMusic();
            }
        });

        add(playButton, BorderLayout.CENTER);
    }

    private void toggleMusic() {
        if (clip == null) {
            playMusic();
        } else {
            stopMusic();
        }
    }

    private void playMusic() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    new File("path/to/your/music.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            playButton.setText("关");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void stopMusic() {
        if (clip.isRunning()) {
            clip.stop();
            clip.close();
            playButton.setText("开");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MusicPlayertrial().setVisible(true);
            }
        });
    }
}
