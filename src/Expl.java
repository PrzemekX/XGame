public class Expl extends Actor {

	public Expl(Stage stage) {
		super(stage);
		this.x = x;
		this.y = y;
		setSpriteNames(new String[] {"Expl1.png", "Expl2.png", "Expl3.png", "Expl4.png", "Expl5.png", "Expl6.png", "Expl7.png", "Expl7.png"});
	}
		

	public void act() {
		t++;
		if (t % frameSpeed == 1) {
			//remove();
			t = 0;
			currentFrame = (currentFrame + 1) % spriteNames.length;
			if(currentFrame == 7)
				remove();
		}
		/*else
			remove();*/
		/*if (y < 0 || y > Stage.WYSOKOSC || x < 0 || x > Stage.SZEROKOSC)
			remove();*/
		//if(this.currentFrame)
	}

	public void collision(Actor a) {
		if (a instanceof Monster) {
			//remove();
		}
	}
}