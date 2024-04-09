import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
/**
 * Driver for basic Sudoku UI
 * @author Chelsea Joe, Spring 2022, COSC 50
 */
public class BasicSudokuGUI extends DrawingGUI {
    private static final int width=800, height=700;     // size of the universe
    private static final Color[] rainbow = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA};
    // to color different levels differently
    ArrayList<ArrayList<Integer>> grid = new ArrayList<>();           // holds the dots
    private char mode = 'r';                        // 'r': reset; 's': solve; 'c': create


    public BasicSudokuGUI() {
        super("sudoku", width, height);
    }


    /**
     * DrawingGUI method, here drawing the quadtree
     * and if in query mode, the mouse location and any found dots
     */
    @Override
    public void handleKeyPress(char key) {
        if (key == 'c' || key == 's' || key == 'r') mode = key;
        repaint();

    }


    @Override
    public void draw(Graphics g) {
        if (mode == 'c') {
            // variables
            System.out.println("create mode\n");


            // create arraylist
            grid = Puzzle.create();

            // reset if random takes too long in create
            if (Puzzle.create() == null) {
                System.out.println("cannot create");
                mode = 'r';

            }
            else {
                System.out.println("created");
                drawPuzzle(g, grid);
            }

        }
        if (mode == 's') {
            System.out.println("solve mode\n");
            if (grid == null) {
                System.out.println("cannot solve");
                mode = 'r';
            }
            else {

                grid = Puzzle.solve(grid);
                drawPuzzle(g, grid);
            }

        }

        if (mode == 'r') {
            System.out.println("reset mode\n");

            grid = new ArrayList<>();

            // initialize array with -1's
            for (int i = 0; i < 9; i++) {
                grid.add(new ArrayList<Integer>());
                for (int j = 0; j < 9; j++) {
                    grid.get(i).add(-1);
                }
            }

            drawPuzzle(g, grid);

        }


    }

    public void drawPuzzle(Graphics g, ArrayList<ArrayList<Integer>> grid) {
        // draw rest of board
        int stringHeight = 80;
        int stringWidth = 0;
        int boardWidth = 630;
        int boardHeight = 630;
        int lineWidth = (width - boardWidth)/2;
        int lineHeight = (height - boardHeight)/2;

        // draw board
        g.drawRect(lineWidth,lineHeight, boardWidth, boardHeight);

        g.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
        for (int i = 0; i < 9; i++) {
            stringWidth = 70;
            // draw vertical lines
            g.drawLine(lineWidth, (height - boardHeight)/2, lineWidth, (height - boardHeight)/2+boardHeight);
            // draw thick lines
            if (i % 3 == 0){
                g.drawLine(lineWidth+1, (height - boardHeight)/2, lineWidth+1, (height - boardHeight)/2+boardHeight);
                g.drawLine((width - boardWidth)/2, lineHeight+1, (width - boardWidth)/2+boardWidth, lineHeight+1);
                g.drawLine(lineWidth-1, (height - boardHeight)/2, lineWidth-1, (height - boardHeight)/2+boardHeight);
                g.drawLine((width - boardWidth)/2, lineHeight-1, (width - boardWidth)/2+boardWidth, lineHeight-1);



            }
            if (i == 8){
                g.drawLine(lineWidth+ boardWidth / 9 +1, (height - boardHeight)/2, lineWidth+ boardWidth / 9 +1, (height - boardHeight)/2+boardHeight);
                g.drawLine((width - boardWidth)/2, lineHeight+ boardHeight / 9+1, (width - boardWidth)/2+boardWidth, lineHeight+ boardHeight / 9 +1);
                g.drawLine(lineWidth+ boardWidth / 9-1, (height - boardHeight)/2, lineWidth+ boardWidth / 9-1, (height - boardHeight)/2+boardHeight);
                g.drawLine((width - boardWidth)/2, lineHeight+ boardHeight / 9-1, (width - boardWidth)/2+boardWidth, lineHeight+ boardHeight / 9 -1);

            }

            // draw horizontal lines
            g.drawLine((width - boardWidth)/2, lineHeight, (width - boardWidth)/2+boardWidth, lineHeight);

            for (int j = 0; j < 9; j++) {
                if (grid != null) {
                    if (grid.get(i).get(j) == -1) {
                        break;
                    }
                    if (grid.get(i).get(j) != 0) {
                        String s = Integer.toString(grid.get(i).get(j));
                        g.drawString(s, stringWidth + 43, stringHeight);
                    }


                    stringWidth += boardWidth / 9;

                }
            }
            lineWidth += boardWidth / 9;


            lineHeight += boardHeight/ 9;

            stringHeight+= boardHeight/ 9;

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BasicSudokuGUI();
            }
        });
    }
}