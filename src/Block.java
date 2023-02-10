import bagel.Image;
import bagel.util.Rectangle;

/* Our Block Class!!! */

public class Block{
    /* Draw block attributes */
    private final Image BLOCK_IMAGE = new Image("res/block.png");
    private double blockX;
    private double blockY;


    /* Rectangle attributes */
    private Rectangle blockRect;
    private double topLeftX;
    private double topLeftY;
    private final double WIDTH = BLOCK_IMAGE.getWidth();
    private final double HEIGHT = BLOCK_IMAGE.getHeight();


    public Block(double x, double y){
        this.blockX = x;
        this.blockY = y;

        /* Calculating top left value for our Rectangle */
        this.topLeftX = this.blockX - (WIDTH/2);
        this.topLeftY = this.blockY- (HEIGHT/2);
        blockRect = new Rectangle(this.topLeftX,this.topLeftY,WIDTH,HEIGHT);
    }

    public Rectangle getBlockRect() {
        return blockRect;
    }

    public void drawBlock(){
        BLOCK_IMAGE.draw(blockX, blockY);
    }
}
