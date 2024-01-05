package Core;

import java.util.Random;

public class Mode2 extends threeMatch{
    public Mode2(int row,int col){
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

        int n=(int)Math.sqrt(row*col)/3;
        while(n!=0){
            int x=random.nextInt(row/2)+1,y=random.nextInt(col);
            if(chars[x][y]!='-'){
                chars[x][y]='-';
                n--;
            }
        }
    }
    public Mode2(String pathname,int row,int col){
        super(pathname,row,col);
    }
    public void countDropNumber(){
        for (int j = 0; j < col; j++) {
            for (int i = row-2; i >=0; i--) {
                if(chars[i+1][j]=='0'){
                    drop[i][j]=drop[i+1][j]+1;
                }else{
                    drop[i][j]=drop[i+1][j];
                }
                if(chars[i][j]=='-')drop[i][j]=0;
            }
        }
    }
}
