import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.Timer;
import java.awt.Canvas;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.awt.event.KeyListener;

class XGame2 extends Canvas implements ActionListener, Stage, KeyListener {
    private final int DELAY = 20;
    static Timer timer;
    static boolean status = true;
    public BufferStrategy strategia;
	private SpriteCache spriteCache;
	private ArrayList actors;
	private Image dbImage;
	private Graphics dbg;
	private Player player;
	public long usedTime;
	public long firstTime;
	
	public Player getPlayer() {
		return player;
	}
	
    public XGame2() {
    	spriteCache = new SpriteCache();
    	addKeyListener(this);
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
		player = new Player(this);
		player.setX(400);
		player.setY(400);
		timer = new Timer(DELAY, this);
		firstTime = System.currentTimeMillis(); //FPS
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
    		player.paint(g2d);
    		g.setColor(Color.white);
    		if (usedTime > 0)
    			g.drawString(String.valueOf(1000 / usedTime)+" fps",5,WYSOKOSC-35);
			else g.drawString("--- fps",5,WYSOKOSC-35);
    	}
    }
    
    public void update(Graphics g)
    {
    	long t = System.currentTimeMillis();
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
		 if((t - firstTime) % 20 == 0)
			 usedTime = System.currentTimeMillis() - t;
    }
    
    public void keyPressed(KeyEvent e) {
		player.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);
	}

	public void keyTyped(KeyEvent e) {
	}
    
    public void actionPerformed(ActionEvent e) {
    	repaint();
        int i = 0;
		while (i < actors.size()) {
			Actor m = (Actor) actors.get(i);
			m.act();
			i++;
        }
		player.act();
    }


	@Override
	public void addActor(Actor a) {
		// TODO Auto-generated method stub
		
	}
}
