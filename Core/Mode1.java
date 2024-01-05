package Core;
import Filepart.ReadAndOverwrite;
import model.Chessboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static Filepart.getInformation.getCoin;
import static Filepart.getInformation.getScore1;

public class Mode1 extends threeMatch {
    public static int coin=0;
    public int coincol=-1;
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    public Mode1(String pathname,int row,int col){
        super(pathname,row,col);
    }
    public void setState(){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                state[i][j]=true;
            }
        }
    }
    public Mode1(int row,int col){//这个构造出来的一定是没有能消除的
        this.row=row;this.col=col;
        random=new Random();
        chars =new char[row][col];
        state=new boolean[row][col];
        drop=new int[row][col];
        score =0;
        //此处只要考虑初始化，第一次构造
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                setPieceChar(i,j, chars,random);
                state[i][j]=true;
                drop[i][j]=0;
            }
        }

        int jl=1;
        while(jl!= score){
            jl= score;
            isMatched(chars,state,row,col,isAll);
            caculateSum();
            drop();
            fillEmpty();
        }
        score = 0;
    }
    public void fillEmpty(){
        if(!existCoin()){
            boolean flg=true;
            while(flg){
                int x=random.nextInt(row/2),y=random.nextInt(col);
                if(chars[x][y]=='0'){
                    chars[x][y]='+';
                    flg=false;
                }
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if(chars[i][j]=='0')setPieceChar(i,j, chars,random);
            }
        }
    }
    public boolean existCoin(){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if(chars[i][j]=='+')return true;
            }
        }
        return false;
    }

    //这两个方法找的是换之前的,也就是提示玩家刷新
    public void drop(){
        for (int i = 0; i < col; i++) {
            if(chars[row-1][i]=='+')drop[row-1][i]++;
        }
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
        if(existMoneyDrop())drop();

    }
    public boolean dropMoney(){//与calculatesum方法放在一起使用,后面接着drop
        boolean flg=false;
        for (int i = 0; i < col; i++) {
            if(chars[row-1][i]=='+'){
                //chars[row-1][i]='0';
                flg=true;
                coin++;
                coincol=col;
            }
        }
        return flg;
    }
    public boolean existMoneyDrop(){
        for (int i = 0; i < col; i++) {
            if(chars[row-1][i]=='+'){
                Mode1.coin++;
                return true;
            }
        }
        return false;
    }
    public int getScore(){
        return score;
    }
}

