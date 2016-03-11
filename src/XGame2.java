import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.Timer;
import java.awt.Canvas;
import java.io.FileNotFoundException;

class XGame2 extends Canvas implements ActionListener, Stage {
    private final int DELAY = 20;
    static Timer timer;
    static boolean status = true;
    public BufferStrategy strategia;
	private SpriteCache spriteCache;
	private ArrayList actors;
	
	private Image dbImage;
	private Graphics dbg;
    
    public XGame2() {
    	spriteCache = new SpriteCache();
        setFocusable(true);
        initGame();
    }
    
    public void initGame() {
    	actors = new ArrayList();
		for (int i = 0; i < 10; i++) {
			Monster m = new Monster(this);
			m.setX((int) (Math.random() * (Stage.SZEROKOSC - m.getWidth())));
			m.setY(i * 20);
			m.setVx((int) (Math.random() * 3) + 1);
			actors.add(m);
		}
		timer = new Timer(DELAY, this);
    }
    
    public SpriteCache getSpriteCache() {
		return spriteCache;
	}
        
    public void paint(Graphics g) {
        super.paint(g);
        if (status) {
        	Graphics2D g2d = (Graphics2D)g;
    		g2d.fillRect(0, 0, getWidth(), getHeight());
    		for (int i = 0; i < actors.size(); i++) {
    			Actor m = (Actor) actors.get(i);
    			m.paint(g2d);
    		}
    		}
    }
    
    public void update(Graphics g)
    {
		dbImage = createImage (this.getSize().width, this.getSize().height);
		 dbg = dbImage.getGraphics ();
		 if (dbImage == null)
		 {}
		 dbg.setColor (getBackground ());
		 dbg.fillRect (0, 0, this.getSize().width, this.getSize().height);
		 dbg.setColor (getForeground());
		 paint (dbg);
		 g.drawImage (dbImage, 0, 0, this);
		 paint(g);
    }
}
