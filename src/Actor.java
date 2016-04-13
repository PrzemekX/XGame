import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Actor {
	protected int x, y;
	protected int width, height;
	protected String spriteName;
	protected Stage stage;
	protected SpriteCache spriteCache;
	protected int currentFrame;
	protected String[] spriteNames;
	protected int frameSpeed;
	protected int t;
	protected boolean markedForRemoval;

	public Actor(Stage stage) {
		this.stage = stage;
		spriteCache = stage.getSpriteCache();
		currentFrame = 0;
		frameSpeed = 2;
		t = 1;
	}

	public void paint(Graphics2D g) {
		g.drawImage(spriteCache.getSprite(spriteNames[currentFrame]), x, y,
				stage);
	}

	public int getX() {
		return x;
	}

	public void setX(int i) {
		x = i;
	}

	public int getY() {
		return y;
	}

	public void setY(int i) {
		y = i;
	}

	public String getSpriteName() {
		return spriteName;
	}

	public void setSpriteNames(String[] names) {
		spriteNames = names;
		height = 0;
		width = 0;
		for (int i = 0; i < names.length; i++) {
			BufferedImage image = spriteCache.getSprite(spriteNames[i]);
			height = Math.max(height, image.getHeight());
			width = Math.max(width, image.getWidth());
		}
	}
	
	public void remove() {
		markedForRemoval = true;
	}
	
	public boolean isMarkedForRemoval() {
		return markedForRemoval;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void setHeight(int i) {
		height = i;
	}

	public void setWidth(int i) {
		width = i;
	}
	
	public int getFrameSpeed() {
		return frameSpeed;
	}

	public void setFrameSpeed(int i) {
		frameSpeed = i;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	public void collision(Actor a) {
	}
	
	public void act() {
		t++;
		if (t % frameSpeed == 0) {
			t = 0;
			currentFrame = (currentFrame + 1) % spriteNames.length;
		}
	}
}