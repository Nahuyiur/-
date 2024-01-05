package view;


import model.ChessPiece;
import model.Constant;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class ChessComponent extends JComponent {

    public boolean selected;

    private ChessPiece chessPiece;



    public ChessComponent(int size, ChessPiece chessPiece) {
        this.selected = false;
        setSize(size-4, size-4);
        setLocation(2,2);
        setVisible(true);
        this.chessPiece = chessPiece;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ImageIcon imageIcon = Constant.getImageMap().get(this.chessPiece.getName());
        Image image=imageIcon.getImage();


        g2.drawImage(image,getWidth() / 220, getHeight() /36,this);


        if (isSelected()) { // Highlights the model if selected.
            ((Graphics2D) g).setStroke(new BasicStroke(6));
            g.setColor(Color.yellow);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
}
