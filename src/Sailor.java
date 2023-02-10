import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Rectangle;

/* Our Sailor Class !! */
public class Sailor {

    /* Sailor Images */
    private final Image SAILOR_RIGHT = new Image("res/sailorRight.png");
    private final Image SAILOR_LEFT = new Image("res/sailorLeft.png");
    private Image SAILOR_IMAGE = SAILOR_RIGHT;

    /* Sailor Attributes */
    private final double WIDTH = SAILOR_IMAGE.getWidth();
    private final double HEIGHT = SAILOR_IMAGE.getHeight();
    private final double STEP_SIZE = 20;
    private double sailorX;
    private double sailorY;
    private double prevX;
    private double prevY;
    private double topLeftX;
    private double topLeftY;
    private Rectangle sailorRect;

    /* HP attributes */
    private final int MAX_HP = 100;
    private int currentHP = MAX_HP;
    private final double HP_X = 10;
    private final double HP_Y = 25;
    private final DrawOptions GREEN = new DrawOptions().setBlendColour(0, 0.8, 0.2);
    private final DrawOptions ORANGE = new DrawOptions().setBlendColour(0.9, 0.6, 0);
    private final DrawOptions RED = new DrawOptions().setBlendColour(1, 0, 0);
    private final Font font = new Font("res/wheaton.otf", 30);

    /* Sailor constructor */
    public Sailor(double x, double y){
        this.sailorX =x;
        this.sailorY =y;
        this.prevX = x;
        this.prevY =y;
    }

    /* Getting a HP percentage */
    public int getHP(){
        return 100*currentHP/MAX_HP;
    }

    /* Decreasing HP by a given block damage */
    public void decreaseHP(int x){
        currentHP -= x;
    }

    /* Drawing HP function */
    public void drawHP(){
        String message  = new String(getHP() + "%");

        /* Getting the colour based on HP pecentage */
        DrawOptions colour = GREEN;
        if(getHP()<65){
            colour = ORANGE;
            if(getHP()<35){
                colour = RED;
            }
        }
        font.drawString(message,HP_X,HP_Y,colour);
    }

    public void bounceSailor(){
        /* Visual Bounce back effect if player hit the box going left or right */
        if(this.sailorX!= prevX){
            swapImage();
        }
        this.sailorX = prevX;
        this.sailorY = prevY;
    }

    public void swapImage(){
        if(SAILOR_IMAGE.equals(SAILOR_LEFT)){
            SAILOR_IMAGE = SAILOR_RIGHT;
        }
        if(SAILOR_IMAGE.equals(SAILOR_RIGHT)){
            SAILOR_IMAGE = SAILOR_LEFT;
        }
    }

    /* Drawing sailor function */
    public void drawSailor(){
        SAILOR_IMAGE.draw(sailorX,sailorY);

        this.topLeftX = this.sailorX - (WIDTH/2);
        this.topLeftY = this.sailorY - (HEIGHT/2);
        sailorRect = new Rectangle(this.topLeftX,this.topLeftY,WIDTH,HEIGHT);

    }

    /* Movement changes sailors x and y but also keeps previous x and y for bounce back */
    public void left(){
        this.prevX = this.sailorX;
        this.sailorX-= STEP_SIZE;
        SAILOR_IMAGE = SAILOR_LEFT;

    }

    public void right(){
        this.prevX = this.sailorX;
        this.sailorX+= STEP_SIZE;
        SAILOR_IMAGE = SAILOR_RIGHT;
    }

    public void up(){
        this.prevY = this.sailorY;
        this.sailorY -= STEP_SIZE;
    }

    public void down(){
        this.prevY = this.sailorY;
        this.sailorY += STEP_SIZE;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public int getMAX_HP(){
        return MAX_HP;
    }
    public double getSailorX(){
        return this.sailorX;
    }
    public double getSailorY() {
        return this.sailorY;
    }

    public Rectangle getSailorRect() {
        return sailorRect;
    }
}
