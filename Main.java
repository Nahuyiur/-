import Enters.Enterframe;
import controller.GameController;
import model.ChessPiece;
import model.Chessboard;
import view.ChessGameFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            Enterframe game=new Enterframe();
//            game.initial();
//        });
        new Enterframe().initial();
    }
}
