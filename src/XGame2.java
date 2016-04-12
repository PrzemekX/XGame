import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.Timer;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
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
    
    public void addActor(Actor a) {
		actors.add(a);
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
    		paintStatus(g2d);
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
	
	public void paintStatus(Graphics2D g) {
		paintScore(g);
		paintHP(g);
		paintAmmo(g);
	}
	
	public void paintHP(Graphics2D g) {
		g.setPaint(Color.red);
		g.fillRect(280, Stage.WYSOKOSC_GRY, Player.MAX_HP, 30);
		g.setPaint(Color.blue);
		g.fillRect(280 + player.getHp(), Stage.WYSOKOSC_GRY,
				200 - player.getHp(), 30);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.setPaint(Color.green);
		g.drawString("Hp:", 230, Stage.WYSOKOSC_GRY + 20);
	}

	public void paintScore(Graphics2D g) {
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.setPaint(Color.green);
		g.drawString("Score:", 20, Stage.WYSOKOSC_GRY + 20);
		g.setPaint(Color.red);
		g.drawString(player.getScore() + "", 100, Stage.WYSOKOSC_GRY + 20);
	}

	public void paintAmmo(Graphics2D g) {
		int xBase = 280 + Player.MAX_HP + 10;
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.setPaint(Color.red);
		g.drawString("Bombs(b):", xBase, Stage.WYSOKOSC_GRY + 8);
		for (int i = 0; i < player.getClusterBombs(); i++) {
			BufferedImage bomb = spriteCache.getSprite("bombUL.gif");
			g.drawImage(bomb, xBase + i * bomb.getWidth() + 52,
					Stage.WYSOKOSC_GRY, this);
		}
	}
	
    public void checkCollision() {
    	Rectangle playerBounds = player.getBounds();
		for (int i = 0; i < actors.size(); i++) {
			Actor a1 = (Actor) actors.get(i);
			Rectangle r1 = a1.getBounds();
			if (r1.intersects(playerBounds)) {
				player.collision(a1);
				a1.collision(player);
			}
			for (int j = i + 1; j < actors.size(); j++) {
				Actor a2 = (Actor) actors.get(j);
				Rectangle r2 = a2.getBounds();
				if (r1.intersects(r2)) {
					a1.collision(a2);
					a2.collision(a1);
				}
			}
		}
    }
    
    public void actionPerformed(ActionEvent e) {
    	checkCollision();
    	repaint();
        int i = 0;
		while (i < actors.size()) {
			Actor m = (Actor) actors.get(i);
			if (m.isMarkedForRemoval()) {
				actors.remove(i);
			} else {
				m.act();
				i++;
			}
        }
		player.act();
    }
}
