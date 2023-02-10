import bagel.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 1, 2022
 *
 * Please filling your name below
 * @Jennifer Duong
 */
public class ShadowPirate extends AbstractGame {
    /* Universal Font text in our game */
    private final Font font = new Font("res/wheaton.otf", 55);

    /* Boundaries for Window, edges, and ladder */
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static int TOP_EDGE = 60;
    private final static int BOTTOM_EDGE = 670;
    private final static int LADDER_X = 990;
    private final static int LADDER_Y = 630;


    private final static String GAME_TITLE = "ShadowPirate";

    /* Parameters for messages */
    private final String START = "PRESS SPACE TO START";
    private final double START_X = (Window.getWidth() - font.getWidth(START))/2;
    private final double START_Y = 402;

    private final String RULES = "USE ARROW KEYS TO FIND LADDER";
    private final double RULES_X = (Window.getWidth() - font.getWidth(RULES))/2;
    private final double RULES_Y = START_Y+70;

    private final String GAME_OVER = "GAME OVER";
    private final double GAME_OVER_X = (Window.getWidth() - font.getWidth(GAME_OVER))/2;
    private final double GAME_OVER_Y = 402;

    private final String GAME_WIN = "CONGRATULATIONS!";
    private final double GAME_WIN_X = (Window.getWidth() - font.getWidth(GAME_WIN))/2;
    private final double GAME_WIN_Y = 402;

    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");

    /* Setting Block Damage */
    private final int BLOCK_DAMAGE = 10;

    /* Initializing  1 Sailor and an array of Blocks*/
    private Sailor sailor;
    private Block[] Blocks = new Block[50];
    public int currBlock =0;


    /* Booleans to tell us the state of the game */
    private boolean gameOver = false;
    private boolean gameWin = false;
    private boolean gameStart = false;
    private boolean read = true;

    public ShadowPirate() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowPirate game = new ShadowPirate();
        game.run();

    }

    /**
     * Method used to read file and create objects
     */
    private void readCSV(String fileName){
        /* Loading our Buffer reader */
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String text;
            while ((text = br.readLine()) != null) {
                /* Using a comma delimiter because we have a csv file */
                String [] splits = text.split(",");

                /* Parsing our values */
                String type = splits[0];
                double x = Double.parseDouble(splits[1]);
                double y = Double.parseDouble(splits[2]);

                /* Creating new Blocks */
                if(type.equals("Block")){
                    Blocks[currBlock] = new Block(x,y);
                    currBlock++;
                }

                /* Creating our Sailor */
                if(type.equals("Sailor")){
                    sailor = new Sailor(x,y);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Collision Function
     * returns a boolean on whether the sailor is touching a box
     */
    public boolean collision(){
        for(int i =0; i < currBlock; i++){

            /* Looping through each box in the array and checks if it overlaps */
            if (sailor.getSailorRect().intersects(Blocks[i].getBlockRect())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        /* Reading our CSV file and making sailors and blocks */
        /* Using a switch to make sure it is only done once */
        if(read){
            readCSV("res/level0.csv");
            read = false;
        }

        /* If the game hasn't started and isnt over, print Start and Rules messages */
        if(!gameStart && !gameWin && !gameOver){
            font.drawString(START,START_X,START_Y);
            font.drawString(RULES,RULES_X,RULES_Y);
        }

        /* End of Game messages */
        if(gameOver || gameWin){
            gameStart = false;

            /* Printing messages */
            if (gameOver){
                font.drawString(GAME_OVER,GAME_OVER_X,GAME_OVER_Y);
            }
            if(gameWin){
                font.drawString(GAME_WIN,GAME_WIN_X,GAME_WIN_Y);
            }
        }

        /* Playing the Game */
        if(gameStart){

            /* Drawing our blocks */
            for(int i =0; i < currBlock; i++){
                Blocks[i].drawBlock();
            }

            /* Drawing our Sailor and HP percentage */
            sailor.drawSailor();
            sailor.drawHP();


            /* Checking for a Collision */
            if(collision()){

                /* Bouncing the Sailor off the block */
                sailor.bounceSailor();

                /* Decreasing the Sailors HP by the block damage */
                sailor.decreaseHP(BLOCK_DAMAGE);

                /* Printing out our log */
                System.out.printf("Block inflicts %d damage points on Sailor. Sailor’s current health: %d/%d\n",
                        BLOCK_DAMAGE, sailor.getCurrentHP(), sailor.getMAX_HP());

            }

            /* Checking user input */
            if (input.wasPressed(Keys.LEFT)) {
                sailor.left();
            }
            if (input.wasPressed(Keys.RIGHT)) {
                sailor.right();
            }
            if (input.wasPressed(Keys.UP)) {
                sailor.up();
            }
            if (input.wasPressed(Keys.DOWN)) {
                sailor.down();
            }
        }

        /* Check if player wants to start the game */
        if(input.wasPressed(Keys.SPACE)){
            gameStart = true;
        }

        /* Game is over if there is no more HP */
        if (sailor.getHP()<=0){
            gameOver = true;
        }

        /* Game is over if the player goes out of bounds */
        if(sailor.getSailorX()<0 || sailor.getSailorX()>WINDOW_WIDTH ||
                sailor.getSailorY()<TOP_EDGE || sailor.getSailorY()>BOTTOM_EDGE){
            gameOver = true;
        }

        /* Game is won if Sailor reaches the ladder */
        if(sailor.getSailorX()>LADDER_X && sailor.getSailorY()>LADDER_Y){
            gameWin = true;
        }

        /* Ending the game if ESC is pressed */
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
    }

}