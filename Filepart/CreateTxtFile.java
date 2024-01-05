package Filepart;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static Filepart.TimeRecording.nowTime;
import static Filepart.TimeRecording.nowTimeFileName;

public class CreateTxtFile {
    public static String creatTxt(List<String> list){
        // 指定文件目录和文件名
        String directoryPath = "C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\RecordingData";
        String fileName = nowTimeFileName()+".txt";

        // 创建文件对象
        File directory = new File(directoryPath);
        File file = new File(directory, fileName);

        try {
            // 检查目录是否存在，如果不存在则创建
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // 检查文件是否存在，如果不存在则创建
            if (!file.exists()) {
                file.createNewFile();
            }

            // 使用 BufferedWriter 写入内容到文件
            //这里要进行修改
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // 写入内容
                for (int i = 0; i < list.size(); i++) {
                    String str=list.get(i);
                    writer.write(str);
                    writer.write("\n");
                }
            }

            System.out.println("该游戏记录创建成功: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}

