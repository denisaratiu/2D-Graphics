package pkg2dgraphics;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ratid6445
 */
public class FaceAnimation extends JComponent {

    // Height and Width of our game
    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;


    // GAME VARIABLES WOULD GO HERE
    Color lightOrange = new Color(252, 179, 96);
    Color faintRed = new Color(240, 125, 125);
    Color brightRed = new Color(232, 44, 44);
    Color faintYellow = new Color (248, 252, 116);
    
    double moveMouth = 395;
    double moveGobble = 389;
    double blink = 301;
    double add = 1;
    double addAnother = 1;
    double addOther = 100;
    
    int blinkDelay = 5;
    long nextBlinkTime = 0;
    // GAME VARIABLES END HERE   

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);
        
        // GAME DRAWING GOES HERE 
        
        // create the chicken head
        //set circle color
        g.setColor(lightOrange);
        // draw circle at(225, 180) near bottome centre that is 350 wide and 350 tall
        g.fillOval(225, 180, 350, 350);
        
        //set oval color
        g.setColor(brightRed);
        // draw oval near top of circle
        // create chicken comb on top of head
        g.fillOval(351, 90, 100, 200);
        // rotate the circles
        g2d.translate(400, 140);
        g2d.rotate(Math.toRadians(-35));
        g.fillOval(0, 4, 100, 70);
        
        g.fillArc(-5, -57, 110, 100, 30, 120);
        g.fillRect(30, -33, 50, 40); 
        
        g2d.rotate(Math.toRadians(35));
        g2d.translate(0, -50);
        
        g2d.rotate(Math.toRadians(-35));
        g.fillOval(0, -15, 100, 60);
       
        g2d.rotate(Math.toRadians(35));
        g2d.translate(0, 100);
       
        g2d.rotate(Math.toRadians(-35));
        g.fillOval(-33, 12, 100, 60);
        
        g2d.rotate(Math.toRadians(35));
        g2d.translate(-400, -200);
        
        //create the eyes
        // set color to black
        g.setColor(Color.BLACK);
        g.fillOval(302, 320, 55, 55);
        g.fillOval(442, 320, 55, 55);
        
        // creating the pupils
        // set color to white
        g.setColor(Color.WHITE);
        g.fillOval(302, 330, 25, 25);
        g.fillOval(442, 330, 25, 25);
        
        // get eyelids
        // set color to orange (blend in with chicken head)
        g.setColor(lightOrange);
        g.fillRect(300, (int)blink, 77, 77);
        g.fillRect(439, (int)blink, 77, 77);
        
        // creating the gobble
        // set color to red
        g.setColor(brightRed);
        g.fillArc(366, (int)moveGobble, 40, 90, 180, 180);
        g.fillArc(396, (int)moveGobble, 40, 90, 180, 180);
        
        //make the beak / mouth
        // set color to yellow
        g.setColor(faintYellow);
        g.fillArc(350, 395, 100, 100, 360, 180);
        g.fillArc(365, (int)moveMouth, 70, 60, 180, 180);
        // GAME DRAWING ENDS HERE
    }


    // This method is used to do any pre-setup you might need to do
    // This is run before the game loop begins!
    public void  preSetup(){
       // Any of your pre setup before the loop starts should go here

    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void run() {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;

        preSetup();

        // the main game loop section
        // game will end if you set done = false;
        boolean done = false;
        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();

            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            // move the gobble up and down in time with the mouth
            moveGobble = moveGobble + add;
            if (moveGobble >= 409 || moveGobble <= 389){
                // move up and down
                add = add * -1;
            }
            // move the mouth up and down 
            moveMouth = moveMouth + addAnother;
            if (moveMouth >= 415 || moveMouth <= 395){
                // move up and down
                addAnother = addAnother * -1;
            }
            blink = blink + addOther;
            if (startTime > nextBlinkTime && (blink >= 390 || blink <= 301)){
                addOther = addOther * -1;
                nextBlinkTime = startTime + blinkDelay;
                
            }
            // GAME LOGIC ENDS HERE 
            // update the drawing (calls paintComponent)
            repaint();

            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            try {
                if (deltaTime > desiredTime) {
                    //took too much time, don't wait
                    Thread.sleep(1);
                } else {
                    // sleep to make up the extra time
                    Thread.sleep(desiredTime - deltaTime);
                }
            } catch (Exception e) {
            };
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("My Game");

        // creates an instance of my game
        FaceAnimation game = new FaceAnimation();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(game);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        
        // add listeners for keyboard and mouse
        frame.addKeyListener(new Keyboard());
        game.addMouseListener(new Mouse());
        
        // starts the game loop
        game.run();
    }

    // Used to implement any of the Mouse Actions
    private static class Mouse extends MouseAdapter {
        // if a mouse button has been pressed down
        @Override
        public void mousePressed(MouseEvent e){
            System.out.println(" X: " + e.getX() + " Y: " + e.getY());
        }
        
        // if a mouse button has been released
        @Override
        public void mouseReleased(MouseEvent e){
            
        }
        
        // if the mouse has moved positions
        @Override
        public void mouseMoved(MouseEvent e){
            
        }
    }
    
    // Used to implements any of the Keyboard Actions
    private static class Keyboard extends KeyAdapter{
        // if a key has been pressed down
        @Override
        public void keyPressed(KeyEvent e){
            
        }
        
        // if a key has been released
        @Override
        public void keyReleased(KeyEvent e){
            
        }
    }
}

