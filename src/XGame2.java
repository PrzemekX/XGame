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
import java.awt.TexturePaint;

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
	public long Time0;
	private boolean gameLose = false;
	public static boolean gamePause = false;
	private BufferedImage kosmos;
	private int mov;
	public long movTime;
	
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
		firstTime = System.currentTimeMillis();
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
        	kosmos = spriteCache.getSprite("Kosmos.png");
        	g2d.setPaint(new TexturePaint(kosmos, new Rectangle(0, mov, kosmos.getWidth(), kosmos.getHeight())));
    		g2d.fillRect(0, 0, getWidth(), getHeight());
    		for (int i = 0; i < actors.size(); i++) {
    			Actor m = (Actor) actors.get(i);
    			m.paint(g2d);
    		}
    		player.paint(g2d);
    		paintStatus(g2d);
    		if (gameLose || gamePause) { 
    			try {
    				if(gameLose)
    					paintGameOver(g2d);
    				if(gamePause)
    					paintGamePause(g2d);
    			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} }
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
		 //FPS
		 if((t - firstTime) % 20 == 0)
			 usedTime = System.currentTimeMillis() - t;
		 //Kosmos animation
		 if(movTime == 0 || t - movTime > 10)
		 {
			 movTime = System.currentTimeMillis();
			 mov+=2;
		 }
		//Shield move
		 if(Player.ShieldWork && Time0 == 0)
			 Time0 = System.currentTimeMillis();
		 if(Player.ShieldWork && t - Time0 >= 5000)
		 {
			 Player.ShieldWork = false;
			 Time0 = 0;
		 }
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
		try {
			paintScore(g);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public void paintScore(Graphics2D g) throws FileNotFoundException{
		//Score
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.setPaint(Color.green);
		g.drawString("Score:", 20, Stage.WYSOKOSC_GRY + 8);
		g.setPaint(Color.red);
		g.drawString(player.getScore() + "", 100, Stage.WYSOKOSC_GRY + 8);
		//Record
		g.setPaint(Color.green);
		g.drawString("Record:", 20, Stage.WYSOKOSC_GRY + 26);
		g.setPaint(Color.yellow);
		g.drawString(Integer.parseInt(Odczyt.Wczytaj()) + "", 100, Stage.WYSOKOSC_GRY + 26);
	}

	public void paintAmmo(Graphics2D g) {
		//Bomb
		int xBaseBomb = 280 + Player.MAX_HP + 10; //poz na osi OX
		int yBaseBomb = Stage.WYSOKOSC_GRY + 8; //poz na osi OY
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.setPaint(Color.red);
		g.drawString("Bombs(b):", xBaseBomb, yBaseBomb);
		for (int i = 0; i < player.getClusterBombs(); i++) {
			BufferedImage bomb = spriteCache.getSprite("bombUL.gif");
			g.drawImage(bomb, xBaseBomb + i * bomb.getWidth() + 52,
					Stage.WYSOKOSC_GRY, this);
		}
		//Missle
		int xBaseMissle = xBaseBomb;
		int yBaseMissle = yBaseBomb + 20;
		g.setPaint(Color.yellow);
		g.drawString("Missle(m):", xBaseMissle, yBaseMissle);
		for (int i = 0; i < player.getMissle(); i++) {
			BufferedImage missle = spriteCache.getSprite("missle1.png");
			g.drawImage(missle, xBaseMissle + i * missle.getWidth() + 52,
					yBaseMissle - 14, this);
		}
		//Shield
		int xBaseShield = xBaseBomb + 120;
		int yBaseShield = yBaseBomb;
		g.setPaint(Color.blue);
		g.drawString("Shield(s):", xBaseShield, yBaseShield);
		for (int i = 0; i < player.getShields(); i++) {
			BufferedImage shield = spriteCache.getSprite("shield.gif");
			g.drawImage(shield, xBaseShield + i * shield.getWidth() + 47,
					yBaseShield - 10, this);
		}
	}
	
	public void gameLose() {
		gameLose = true;
	}
	
	public void paintGameOver(Graphics2D g) throws FileNotFoundException{
		if(Integer.parseInt(Odczyt.Wczytaj()) > Player.score ) {
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString("GAME OVER", Stage.SZEROKOSC / 2 - 50, Stage.WYSOKOSC / 2);
		} else {
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString("GAME OVER", Stage.SZEROKOSC / 2 - 50, (Stage.WYSOKOSC / 2) - 10);
			g.setColor(Color.yellow);
			g.drawString("NEW RECORD " + Player.score, Stage.SZEROKOSC / 2 - 50, (Stage.WYSOKOSC / 2) + 10);
		}
		timer.stop();
		
		if (Integer.parseInt(Odczyt.Wczytaj()) < Player.score ) {
			Odczyt.Zapisz();
		}
	}
	
	public void paintGamePause(Graphics2D g) throws FileNotFoundException{
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("PAUSE", Stage.SZEROKOSC / 2 - 7, Stage.WYSOKOSC / 2);
		timer.stop();
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
