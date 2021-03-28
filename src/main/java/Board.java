import tiles.Grid;
import tiles.GridPos;
import tiles.Tile;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel {
    private int BOARD_WIDTH = 1;
    private int BOARD_HEIGHT = 1;//width and height set in initBoard
    private final int PERIOD_INTERVAL = 500;//milliseconds between draws

    private boolean isPaused = false;
    private JLabel statusbar;
    private final Grid grid;

    public Board(GraphicalInterface parent, Grid g) {
        grid = g;
        initBoard(parent);
    }

    private void initBoard(GraphicalInterface parent) {
        setFocusable(true);
        statusbar = parent.getStatusBar();
        addKeyListener(new KeyboardManager());
        addMouseListener(new MouseManager());
        BOARD_HEIGHT = grid.maxDimension();
        BOARD_WIDTH = grid.maxDimension();
    }

    private int squareWidth() {

        return (int) getSize().getWidth() / BOARD_WIDTH;
    }

    private int squareHeight() {

        return (int) getSize().getHeight() / BOARD_HEIGHT;
    }

    private Tile getTileFromPoint(Point p){
        int x = p.x/squareWidth();
        int y = p.y/squareHeight();
        return grid.getGridPos(x,y).getTile();
    }

    void start() {
        //keep this timer around for future use
        Timer timer = new Timer(PERIOD_INTERVAL, new GameCycle());
        timer.start();
    }

    private void pause() {

        isPaused = !isPaused;

        if (isPaused) {

            statusbar.setText("paused");
        } else {

            statusbar.setText("not paused");
        }

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        Color[] colors = {new Color(0, 0, 0), new Color(204, 102, 102),
                new Color(102, 204, 102), new Color(102, 102, 204),
                new Color(204, 204, 102), new Color(204, 102, 204),
                new Color(102, 204, 204), new Color(218, 170, 0)
        };

        var size = getSize();
        int boardTop = (int) size.getHeight() - BOARD_HEIGHT * squareHeight();

        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {

                GridPos pos = grid.getGridPos(j, i);
                Tile t = pos.getTile();

                if (t != null) {
                    drawSquare(g, j * squareWidth(),
                            boardTop + i * squareHeight(), colors[Math.abs(t.hashCode()) % 8]);
                }
            }
        }
    }

    private void drawSquare(Graphics g, int x, int y, Color color) {

        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);

        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + 1);
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            doGameCycle();
        }
    }

    private void doGameCycle() {

        update();
        repaint();
    }

    private void update() {

        if (isPaused) {

            return;
        }

    }

    class KeyboardManager extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int keycode = e.getKeyCode();


            if (keycode == KeyEvent.VK_P) {
                pause();
            }
        }
    }

    class MouseManager extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            Point p = e.getPoint();
            Tile t = getTileFromPoint(p);
            if(t == null){
                statusbar.setText(" ");
            }else {
                statusbar.setText(t.toString());
            }
        }
    }
}
