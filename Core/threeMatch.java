package Core;

import Filepart.ReadAndOverwrite;
import model.Chessboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static Filepart.getInformation.*;
import static Filepart.getInformation.getScore3;

public class threeMatch {
    public char chars[][];
    public int score =0;
    public boolean state[][];
    public int drop[][];
    public int row=8;
    public int col=8;
    public List<removablePostion> removablePostionList=new ArrayList<>();
    public boolean isAll[][];
    Random random;

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public threeMatch(){//这个构造只能保留，去掉了就会报错
        random=new Random();
        chars =new char[row][col];
        state=new boolean[row][col];
        drop=new int[row][col];
        isAll=new boolean[row][col];
        //removablePostionList=new ArrayList<>();//每次矩阵改动后都应该清空
        score =0;
        //此处只要考虑初始化，第一次构造
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                setPieceChar(i,j, chars,random);
                state[i][j]=true;
                drop[i][j]=0;isAll[i][j]=false;
            }
        }
    }
    //很多很多的属性应该几个关键类都定义，然后在何处获得值，就用get，set传过去
    public threeMatch(String pathname,int row,int col){
        //给定路径名称，行列得到初始化的threematch
        ReadAndOverwrite readAndOverwrite = new ReadAndOverwrite();
        List<String> gamelist=new ArrayList<String>();
        gamelist= readAndOverwrite.readFileToList(pathname);
        chars=new char[row][col];
        drop=new int[row][col];
        state=new boolean[row][col];
        random=new Random();

        ReadAndOverwrite readAndOverwrite1=new ReadAndOverwrite();
        List<String> gameinformation=new ArrayList<>();
        gameinformation=readAndOverwrite1.readFileToList(pathname);
        //这里用gatinformation类中的方法可以得到信息，此处还没写好（分数一定是第一关的）
        //后续要加判断
        Chessboard.playerStage=getStage(gamelist);
        if(Chessboard.playerStage%3==1)score=getScore1(gamelist);
        if(Chessboard.playerStage%3==2)score=getScore2(gamelist);
        if(Chessboard.playerStage%3==0)score=getScore3(gamelist);

        for (int i = 2; i < gamelist.size(); i++) {
            for (int j = 0; j < col; j++) {
                chars[i-2][j]=gamelist.get(i).charAt(j);
            }
        }
        initialCountDropNumber();
        setState();
    }
    public void setState(){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                state[i][j]=true;
            }
        }
    }
    public threeMatch(int row,int col){//这个构造出来的一定是没有能消除的
        this.row=row;this.col=col;
        random=new Random();
        chars =new char[row][col];
        state=new boolean[row][col];
        drop=new int[row][col];
        isAll=new boolean[row][col];
        score =0;
        //此处只要考虑初始化，第一次构造
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                setPieceChar(i,j, chars,random);
                state[i][j]=true;
                drop[i][j]=0;isAll[i][j]=false;
            }
        }

        int jl =1;
        while(jl!= score){
            jl= score;
            isMatched(chars,state,row,col,isAll);
            caculateSum();
            drop();
            fillEmpty();
        }//逻辑是：每次都1匹配，2下落，3填充（尽管下落后可能已经存在可消除的棋子了，但还是等填充后再消除）
        //之后的交换中也建议采取这种逻辑，因为容易实现(少一层while)
        //最终得到的一定是不能消除的
        score = 0;
    }
    public static void setPieceChar(int i,int j,char a[][],Random random){
        int x=random.nextInt(6);
        switch (x){
            case 0:
                a[i][j]='1';
                break;
            case 1:
                a[i][j]='2';
                break;
            case 2:
                a[i][j]='3';
                break;
            case 3:
                a[i][j]='4';
                break;
            case 4:
                a[i][j]='5';
                break;
            case 5:
                a[i][j]='6';
                break;
        }
    }
    public void fillEmpty(){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if(chars[i][j]=='0')setPieceChar(i,j, chars,random);
            }
        }
    }
    public void showMatrix(){//仅用于展示网格状态
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.printf(chars[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();

    }

    public void setScore(int score) {
        this.score = score;
    }

    //这两个方法找的是换之前的,也就是提示玩家刷新
    public static boolean countThreeRight(char a[][],int row,int col,int x,int y){
        for (int j = 0; j < col-2; j++) {
            if(a[x][j]==a[x][j+1]&&a[x][j]==a[x][j+2]&&a[x][j]!='+'&&a[x][j]!='-')return true;
        }
        for (int i = 0; i < row-2; i++) {
            if(a[i][y]==a[i+1][y]&&a[i][y]==a[i+2][y]&&a[i][y]!='+'&&a[i][y]!='-'
            ||a[i][y+1]==a[i+1][y+1]&&a[i][y+1]==a[i+2][y+1]&&a[i][y+1]!='+'&&a[i][y+1]!='-')return true;
        }
        return false;
    }
    public static boolean countThreeDown(char a[][],int row,int col,int x,int y){
        for (int i = 0; i < row-2; i++) {
            if(a[i][y]==a[i+1][y]&&a[i][y]==a[i+2][y]&&a[i][y]!='+'&&a[i][y]!='-')return true;
        }
        for (int j = 0; j < col-2; j++) {
            if(a[x][j]==a[x][j+1]&&a[x][j]==a[x][j+2]&&a[x][j]!='+'&&a[x][j]!='-'
                    ||a[x+1][j]==a[x+1][j+1]&&a[x+1][j]==a[x+1][j+2]&&a[x+1][j]!='+'&&a[x+1][j]!='-')return true;
        }
        return false;
    }
    public boolean existChange(){//只判断，不修改矩阵
        //蹦出提示可以在这里实现
        removablePostionList.clear();
        boolean flag=false;
        for (int i = 0; i <row; i++) {
            for (int j = 0; j < col-1; j++) {//左的往右换
               char temp1= chars[i][j];
               chars[i][j]= chars[i][j+1];
               chars[i][j+1]=temp1;
               if(countThreeRight(chars,row,col,i,j)&&chars[i][j]!='-'&&chars[i][j+1]!='-'){
                   flag=true;
                   removablePostion e=new removablePostion(i,j,'r');
                   removablePostionList.add(e);
               }
               char temp2= chars[i][j];
               chars[i][j]= chars[i][j+1];
               chars[i][j+1]=temp2;
            }
        }
        for (int i = 0; i <row-1; i++) {
            for (int j = 0; j < col; j++) {//左的往右换
                char temp1= chars[i][j];
                chars[i][j]= chars[i+1][j];
                chars[i+1][j]=temp1;
                if(countThreeDown(chars,row,col,i,j)&&chars[i][j]!='-'&&chars[i+1][j]!='-'){
                    flag=true;
                    removablePostionList.add(new removablePostion(i,j,'d'));
                }
                char temp2= chars[i][j];
                chars[i][j]= chars[i+1][j];
                chars[i+1][j]=temp2;
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j=0; j<col-1;j++){
                if (chars[i][j] == 'a'&&chars[i][j+1]!='-') {
                    removablePostionList.add(new removablePostion(i,j,'r'));
                }
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j=1; j<col;j++){
                if (chars[i][j] == 'a'&&chars[i][j+1]!='-') {
                    removablePostionList.add(new removablePostion(i,j-1,'r'));
                }
            }
        }
        for (int i = 0; i < row-1; i++) {
            for (int j=0; j<col;j++){
                if (chars[i][j] == 'a'&&chars[i+1][j]!='-') {
                    removablePostionList.add(new removablePostion(i,j,'d'));
                }
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j=0; j<col;j++){
                if (chars[i][j] == 'a'&&chars[i+1][j]!='-') {
                    removablePostionList.add(new removablePostion(i-1,1,'d'));
                }
            }
        }
        return flag;
    }
    public static boolean isMatched(char a[][],boolean state[][],int row,int col,boolean isAll[][]){//只改状态false，要消除得和calculate连用
        boolean flag=false;
        //有的话注意state就会改变
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col-2; j++) {
                if(a[i][j]==a[i][j+1] && a[i][j]==a[i][j+2]&&a[i][j]!='0'&&a[i][j]!='+'&&a[i][j]!='-'&&a[i][j]!='\0'){
                    flag=true;int count=0;
                    char mode=a[i][j];
                    while(j<col&&a[i][j]==mode){
                        state[i][j]=false;
                        j++;count++;
                    }

                    if(count>=5){
                        isAll[i][j-count/2]=true;
                    }j--;
                }
            }
        }
        for (int j = 0; j < col; j++) {
            for (int i = 0; i < row-2; i++) {
                if(a[i][j]==a[i+1][j]&&a[i][j]==a[i+2][j]&&a[i][j]!='0'&&a[i][j]!='+'&&a[i][j]!='-'&&a[i][j]!='\0'){
                    flag=true;int count=0;
                    char mode=a[i][j];
                    while(i<row&&a[i][j]==mode){
                        state[i][j]=false;
                        i++;count++;
                    }

                    if(count>=5){
                        isAll[i-count/2][j]=true;
                    }i--;
                }
            }
        }
        return flag;
    }
    public void caculateSum(){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if(!state[i][j]){
                    chars[i][j]='0';//为drop做准备
                    state[i][j]=true;//每次消除后棋子格子状态都为true
                    score +=10;//计算score
                    Chessboard.totalScore += 10;
                    if(isAll[i][j]){
                        chars[i][j]='a';
                        isAll[i][j]=false;
                    }
                }
            }

        }

    }
    public void countDropNumber(){
        for (int j = 0; j < col; j++) {
            for (int i = row-2; i >=0 ; i--) {
                if(chars[i+1][j]=='0'){
                    drop[i][j]=drop[i+1][j]+1;
                }else{
                    drop[i][j]=drop[i+1][j];
                }
            }
        }
    }
    public void initialCountDropNumber(){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                drop[i][j]=0;
            }
        }
    }
    public void drop(){
        countDropNumber();//计算出每个棋子下落多少
        for (int j = 0; j < col; j++) {
            for (int i = row-2; i >=0; i--) {
                if(drop[i][j]!=0){
                    chars[i+drop[i][j]][j]= chars[i][j];
                    chars[i][j]='0';
                }

            }
        }
        initialCountDropNumber();//把下落数都变成0
    }
    public void removeSamePiece(char c){
        for (int i = 0; i <row ; i++) {
            for (int j = 0; j <col ; j++) {
                if(chars[i][j]==c){
                    chars[i][j]='0';
                    score+=10;
                    Chessboard.totalScore = Chessboard.totalScore + 10;
                }
            }
        }
    }
    public void removAllPiece(){
        for (int i = 0; i <row ; i++) {
            for (int j = 0; j <col ; j++) {
                if(chars[i][j]!='+'&&chars[i][j]!='-'){
                    chars[i][j]='0';
                    score+=10;
                    Chessboard.totalScore = Chessboard.totalScore + 10;
                }
            }
        }
    }
    public int getScore(){
        return score;
    }
}
