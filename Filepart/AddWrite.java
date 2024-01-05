package Filepart;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddWrite {
    public static void toAdd(List<String> addString){
        String fileName = "C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\Filepart\\recording.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            StringBuilder sb = new StringBuilder();//全部先改在sb里，再把sb的内容改到file中
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }//把之前的复制好，再找到末尾位置

            //在这里写内容
            sb.append("---------------------------------").append("\n");
            for (int i = 0; i < addString.size(); i++) {
                String str=(String) addString.get(i);
                sb.append(str).append("\n");
            }
            // 将修改后的内容写回文件
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
                bw.write(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //最大的作用就是修改文档
    public static void main(String[] args) {
        ReadAndOverwrite readAndOverwrite = new ReadAndOverwrite();
        List<String> addString=new ArrayList<>();
        addString=readAndOverwrite.readFileToList("C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\Filepart\\gameinformation.txt");

        toAdd(addString);
    }
}