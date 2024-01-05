package view;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the Cell class,
 * but this class only cares how to draw Cells on ChessboardComponent
 */

public class CellComponent extends JPanel {
    //与画棋子完全无关
    private Color background;

    public CellComponent(Color background, Point location, int size) {
        setLayout(null);
        setLocation(location);
        setSize(size, size);
        this.background = background;
    }

    @Override
    protected void paintComponent(Graphics g) {
        //仅影响网格
        super.paintComponents(g);
        g.setColor(background);
        g.fillRect(1, 1, this.getWidth()-2, this.getHeight()-2);
    }
}
