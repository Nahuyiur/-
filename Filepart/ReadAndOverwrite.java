package Filepart;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReadAndOverwrite implements FileUtil{
    public List<String> readFileToList(String filePath) {
        try {
            if (Files.exists(Path.of(filePath))) {
                return Files.readAllLines(Path.of(filePath));
            } else {
                JOptionPane.showMessageDialog(null,"文件不存在!","提示",JOptionPane.WARNING_MESSAGE);
                System.out.println("文件不存在: " + filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
        //以list<String>类型返回记录
    }

    public void writeFileFromList(String filePath, List<String> lines) {
        //这个写的逻辑是完全覆盖
        try {
            Files.write(Path.of(filePath), lines, Charset.defaultCharset());
        } catch (IOException e) {

        }
    }
}
interface FileUtil {
    public List<String> readFileToList(String filePath);
    public void writeFileFromList(String filePath, List<String> lines);
}
