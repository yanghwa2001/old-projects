import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
/**
 * Driver for basic Sudoku UI
 * @author Chelsea Joe, Spring 2022, COSC 50
 */
public class FancySudokuGUI extends DrawingGUI {
    private static final int width=800, height=700;     // size of the universe
    private static final Color[] rainbow = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN};
    // to color different levels differently
    ArrayList<ArrayList<Integer>> grid = new ArrayList<>();           // holds the dots
    private char mode = 'r';                        // 'r': reset; 's': solve; 'c': create, 'b': buffer
    private char type = 'n';                        // 'n': normal; 'm': mario; 'p': professor; 'e': elitist, 'a': all(rainbow)


    // Images
    private static final int thumbWidth =67 , thumbHeight = 67; 			// setup: scaled size of thumbnails
    private static ArrayList<BufferedImage> thumbs;		// thumbnail images to display


    // used from personal CS 10 Recitation 2 code
    private static BufferedImage thumbnailify(BufferedImage image) {
        BufferedImage result = new BufferedImage(thumbWidth, thumbHeight, image.getType());

        for (int i = 0; i < result.getWidth(); i++){
            for (int j = 0; j < result.getHeight(); j++){
                result.setRGB(i,j, image.getRGB(i * image.getWidth()/thumbWidth, j * image.getHeight()/thumbHeight));
            }
        }
        return result;



    }


    public FancySudokuGUI(ArrayList<BufferedImage> images) {

        super("FANCY sudoku", width, height);
        thumbs = new ArrayList<BufferedImage>();

        // Create the thumbnails
        for (int i=0; i<27; i++) {
            thumbs.add(thumbnailify(images.get(i)));
        }
    }



    /**
     * DrawingGUI method, here drawing the quadtree
     * and if in query mode, the mouse location and any found dots
     */
    @Override
    public void handleKeyPress(char key) {
        if (key == 'c' || key == 's' || key == 'r' || key == 'b') mode = key;
        if (key == 'n' || key == 'm' || key == 'p' || key == 'e' || key == 'a') type = key;

        repaint();

    }


    @Override
    public void draw(Graphics g) {
        if (mode == 'c') {
            // variables
            System.out.println("create mode\n");


            // create arraylist
            grid = Puzzle.create();

            // wait for puzzle to me made
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException ie) {
//                Thread.currentThread().interrupt();
//            }
//


//             reset if random takes too long in create
            if (Puzzle.create() == null) {
                System.out.println("cannot create");
                mode = 'r';


            }

//            System.out.println("created");
//            drawPuzzle(g, grid);
//            mode = 'b';
            else {
                drawPuzzle(g, grid);


                System.out.println("created");
                mode = 'b';

            }

        }
        if (mode == 'b') {
            drawPuzzle(g, grid);

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
        int imageBuffer = 43;

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

            // draw normal board
            if (type == 'n') {
                g.setColor(Color.black);
                stringWidth = 70;

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
            }

            // draw rainbow board
            if (type == 'a') {
                mode = 'b';
                g.drawString("Rainbow Mode", 310, 25);

                stringWidth = 70;

                for (int j = 0; j < 9; j++) {
                    if (grid != null) {
                        if (grid.get(i).get(j) == -1) {
                            break;
                        }
                        if (grid.get(i).get(j) != 0) {
                            int random = (int) (Math.random() * 7);
                            g.setColor(rainbow[random]);
                            String s = Integer.toString(grid.get(i).get(j));
                            g.drawString(s, stringWidth + 43, stringHeight);
                        }


                        stringWidth += boardWidth / 9;

                    }
                }
                g.setColor(Color.black);

            }

            // draw mario board
            else if (type == 'm') {
                mode = 'b';
                g.drawString("Mario Mode", 310, 25);

                stringWidth = (width - boardWidth)/2;

//                System.out.println("mario type\n");



                for (int j = 0; j < 9; j++) {
                    if (grid != null) {
                        if (grid.get(i).get(j) == -1) {
                            break;
                        }
                        if (grid.get(i).get(j) != 0) {
                            if (grid.get(i).get(j) == 1){
                                g.drawImage(thumbs.get(0),stringWidth, stringHeight-imageBuffer, null);
                            }
                            else if (grid.get(i).get(j) == 2){
                                g.drawImage(thumbs.get(1),stringWidth, stringHeight-imageBuffer, null);
                            }
                            else if (grid.get(i).get(j) == 3){
                                g.drawImage(thumbs.get(2),stringWidth, stringHeight-imageBuffer, null);
                            }
                            else if (grid.get(i).get(j) == 4){
                                g.drawImage(thumbs.get(3),stringWidth, stringHeight-imageBuffer, null);
                            }
                            else if (grid.get(i).get(j) == 5){
                                g.drawImage(thumbs.get(4),stringWidth, stringHeight-imageBuffer, null);
                            }
                            else if (grid.get(i).get(j) == 6){
                                g.drawImage(thumbs.get(5),stringWidth, stringHeight-imageBuffer, null);
                            }
                            else if (grid.get(i).get(j) == 7){
                                g.drawImage(thumbs.get(6),stringWidth, stringHeight-imageBuffer, null);
                            }
                            else if (grid.get(i).get(j) == 8){
                                g.drawImage(thumbs.get(7),stringWidth, stringHeight-imageBuffer, null);
                            }
                            else if (grid.get(i).get(j) == 9){
                                g.drawImage(thumbs.get(8),stringWidth, stringHeight-imageBuffer, null);
                            }


                        }
                        stringWidth += boardWidth / 9;

                    }
                }

            }

            // draw professor board
            else if (type == 'p') {
//                mode = 'b';

                g.drawString("Professor Mode", 290, 25);

                stringWidth = (width - boardWidth)/2;

//                System.out.println("mario type\n");



                for (int j = 0; j < 9; j++) {
                    if (grid != null) {
                        if (grid.get(i).get(j) == -1) {
                            break;
                        }
                        if (grid.get(i).get(j) != 0) {
                            if (grid.get(i).get(j) == 1){
                                g.drawImage(thumbs.get(9),stringWidth, stringHeight-imageBuffer, null);
                            }
                            else if (grid.get(i).get(j) == 2){
                                g.drawImage(thumbs.get(10),stringWidth, stringHeight-imageBuffer, null);
                            }
                            else if (grid.get(i).get(j) == 3){
                                g.drawImage(thumbs.get(11),stringWidth, stringHeight-imageBuffer, null);
                            }
                            else if (grid.get(i).get(j) == 4){
                                g.drawImage(thumbs.get(12),stringWidth, stringHeight-imageBuffer, null);
                            }
                            else if (grid.get(i).get(j) == 5){
                                g.drawImage(thumbs.get(13),stringWidth, stringHeight-imageBuffer, null);
                            }
                            else if (grid.get(i).get(j) == 6){
                                g.drawImage(thumbs.get(14),stringWidth, stringHeight-imageBuffer, null);
                            }
                            else if (grid.get(i).get(j) == 7){
                                g.drawImage(thumbs.get(15),stringWidth, stringHeight-imageBuffer, null);
                            }
                            else if (grid.get(i).get(j) == 8){
                                g.drawImage(thumbs.get(16),stringWidth, stringHeight-imageBuffer, null);
                            }
                            else if (grid.get(i).get(j) == 9){
                                g.drawImage(thumbs.get(17),stringWidth, stringHeight-imageBuffer, null);
                            }


                        }
                        stringWidth += boardWidth / 9;

                    }
                }

            }
            else if (type == 'e') {
                g.drawString("Elitist Mode", 300, 25);

                stringWidth = (width - boardWidth)/2;

//                System.out.println("mario type\n");



                for (int j = 0; j < 9; j++) {
                    if (grid != null) {
                        if (grid.get(i).get(j) == -1) {
                            break;
                        }
                        if (grid.get(i).get(j) != 0) {
                            if (grid.get(i).get(j) == 1){
                                g.drawImage(thumbs.get(18),stringWidth, stringHeight-imageBuffer, null);
                            }
                            else if (grid.get(i).get(j) == 2){
                                g.drawImage(thumbs.get(19),stringWidth, stringHeight-imageBuffer, null);
                            }
                            else if (grid.get(i).get(j) == 3){
                                g.drawImage(thumbs.get(20),stringWidth, stringHeight-imageBuffer, null);
                            }
                            else if (grid.get(i).get(j) == 4){
                                g.drawImage(thumbs.get(21),stringWidth, stringHeight-imageBuffer, null);
                            }
                            else if (grid.get(i).get(j) == 5){
                                g.drawImage(thumbs.get(22),stringWidth, stringHeight-imageBuffer, null);
                            }
                            else if (grid.get(i).get(j) == 6){
                                g.drawImage(thumbs.get(23),stringWidth, stringHeight-imageBuffer, null);
                            }
                            else if (grid.get(i).get(j) == 7){
                                g.drawImage(thumbs.get(24),stringWidth, stringHeight-imageBuffer, null);
                            }
                            else if (grid.get(i).get(j) == 8){
                                g.drawImage(thumbs.get(25),stringWidth, stringHeight-imageBuffer, null);
                            }
                            else if (grid.get(i).get(j) == 9){
                                g.drawImage(thumbs.get(26),stringWidth, stringHeight-imageBuffer, null);
                            }


                        }
                        stringWidth += boardWidth / 9;

                    }
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
                // Read the images, named mario1.jpg, ... and store in list.
                ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
                for (int c=0; c<9; c++) {
                    images.add(loadImage("Images/mario"+(c)+".jpg"));
                }
                for (int v=9; v<18; v++) {
                    images.add(loadImage("Images/prof"+(v)+".jpg"));
                }
                for (int v=18; v<27; v++) {
                    images.add(loadImage("Images/ivy"+(v)+".jpg"));
                }


                new FancySudokuGUI(images);
            }
        });
    }
}
