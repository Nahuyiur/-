package Filepart;

import Core.threeMatch;

import java.util.ArrayList;
import java.util.List;

import static Filepart.CreateTxtFile.creatTxt;

public class FileTest {
    public static void main(String[] args) {
        String fileName = "C:\\Users\\ruiyu\\IdeaProjects\\ProjectNew\\src\\TestCase\\AskforUpdate.txt";
        List<String> tiral=new ArrayList<>();
        ReadAndOverwrite readAndOverwrite = new ReadAndOverwrite();
        tiral=readAndOverwrite.readFileToList(fileName);

        threeMatch threeMatch=new threeMatch(fileName,8,8);
        System.out.println(threeMatch.existChange());

    }
}
