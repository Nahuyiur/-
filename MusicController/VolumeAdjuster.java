package MusicController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;

public class VolumeAdjuster {


    /**
     * Created by Administrator on 2018/6/26 0026.
     * 系统工具类
     */
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();

    private static final Logger logger = Logger.getGlobal();
        /**
         * 控制电脑系统音量
         * <p/>
         * 约定在应用根目录下的 temp 目录中放置3个vbs文件
         * volumeMute.vbs：用于静音
         * volumeAdd.vbs：增加音量
         * volumeMinus.vbs：减小音量
         * 文件以及文件的内容采用 Java 代码动态生成，不存在时则新建，存在时则直接调用
         *
         * @param type 0：静音/取消静音    1：增加音量  2：减小音量
         */
        public static void controlSystemVolume(String type) {
            try {
                if (type == null || "".equals(type.trim())) {
                    logger.info("type 参数为空,不进行操作...");
                }
                String vbsMessage = "";
                File tempFile = null;
                Runtime runtime = Runtime.getRuntime();
                switch (type) {
                    case "0":
                        tempFile = new File("temp", "volumeMute.vbs");
                        vbsMessage = !tempFile.exists() ? "CreateObject(\"Wscript.Shell\").Sendkeys \"棴\"" : "";
                        break;
                    case "1":
                        tempFile = new File("temp", "volumeAdd.vbs");
                        vbsMessage = !tempFile.exists() ? "CreateObject(\"Wscript.Shell\").Sendkeys \"棷\"" : "";
                        break;
                    case "2":
                        tempFile = new File("temp", "volumeMinus.vbs");
                        vbsMessage = !tempFile.exists() ? "CreateObject(\"Wscript.Shell\").Sendkeys \"棶\"" : "";
                        break;
                    default:
                        return;
                }

                if (!tempFile.exists() && !vbsMessage.equals("")) {
                    if (!tempFile.getParentFile().exists()) {
                        tempFile.getParentFile().mkdirs();
                    }
                    tempFile.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "GBK");
                    outputStreamWriter.write(vbsMessage);
                    outputStreamWriter.flush();
                    outputStreamWriter.close();
                    logger.info("vbs 文件不存在，新建成功：" + tempFile.getAbsolutePath());
                }
                runtime.exec("wscript " + tempFile.getAbsolutePath()).waitFor();
                //logger.info("音量控制完成.");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();
            switch (action){
                case "+":
                    controlSystemVolume("1");
                    break;
                case "-":
                    controlSystemVolume("2");
                    break;

            }
        }

    };
    public void addButton(){
        JButton b1 = new JButton("+");
        JButton ok=new JButton("完成");
        JButton b2 = new JButton("-");
        b1.addActionListener(listener);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });
        b2.addActionListener(listener);
        panel.add(b1);
        panel.add(ok);
        panel.add(b2);
        frame.add(panel,BorderLayout.NORTH);
    }

    public VolumeAdjuster(){
        addButton();
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setVisible(true);
    }
}



