public class Expl extends Actor {

	public Expl(Stage stage) {
		super(stage);
		setSpriteNames(new String[] {"Expl1.png", "Expl2.png", "Expl3.png", "Expl4.png", "Expl5.png", "Expl6.png", "Expl7.png", "Expl7.png"});
	}
		

	public void act() {
		t++;
		if (t % frameSpeed == 1) {
			t = 0;
			currentFrame = (currentFrame + 1) % spriteNames.length;
			if(currentFrame == 7)
				remove();
		}
	}

	public void collision(Actor a) {
		if (a instanceof Monster) {
		}
	}
}