
import tiles.Grid;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
Java Tetris game clone

Author: Jan Bodnar
Website: http://zetcode.com
 */
public class GraphicalInterface extends JFrame {

    private JLabel statusbar;
    private final Grid g;

    public GraphicalInterface(Grid grid) {
        g = grid;
        initUI();
    }

    private void initUI() {
        statusbar = new JLabel(" ");
        add(statusbar, BorderLayout.SOUTH);

        var board = new Board(this, g);
        add(board);
        board.start();

        setTitle("Procedural Planning");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    JLabel getStatusBar() {
        return statusbar;
    }
}
