package Core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class circleTranslation implements Runnable {

    private JFrame frame = new JFrame("Circle Translation");
    private int centerX = 50;
    private int centerY = 50;
    private int radius = 20;

    public circleTranslation() {
        frame.setLayout(new BorderLayout());
        frame.add(new Button("hhh"),BorderLayout.EAST);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new CirclePanel());
        frame.setVisible(true);
    }

    @Override
    public void run() {
        Timer timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 更新圆的位置
                centerX += 2;
                centerY += 2;

                // 重新绘制窗口
                frame.repaint();
            }
        });

        // 启动定时器
        timer.start();
    }
    private class CirclePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // 清空画布
            //g.clearRect(0, 0, getWidth(), getHeight());

            // 绘制圆
            g.setColor(Color.BLUE);
            g.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
        }
    }

    public static void main(String[] args) {
       //SwingUtilities.invokeLater(new circleTranslation());//这样才有动画
        Thread t1=new Thread(new circleTranslation());
        t1.start();
    }
}


