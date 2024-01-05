package Core;
public class removablePostion {
    public int x;
    public int y;
    public char d;

    public removablePostion(int x, int y, char d) {
        this.x = x;
        this.y = y;
        this.d = d;//d是direction
    }

    public String toString() {
        return String.format("(" + x + "," + y + "," + d + ")");
    }
}
