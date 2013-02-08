package uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator;

public class GrownRenewableFood extends Food {
	
	private boolean isEaten;
	GrownRenewableFood(){
		this.activity=new Thread(this);
	}
	
	
	public void run(){
		while (!runTerminated) {
			try {
				this.check();
				Thread.sleep(delay); // go to sleep for some time
			} catch (InterruptedException ex) {
				// wake up!
			}
		}
	}

	@Override
	public void check() {
		if (this.calories<=0){
			w.removeEntity(this);
			int oldX=this.xPos;
			int oldY=this.yPos;
			this.remove();
			String kindOfFood=null;
			if (this instanceof Bush){
				kindOfFood="bush";
			} else if (this instanceof Grass){
				kindOfFood="grass";
			} else if (this instanceof Tree){
				kindOfFood="tree";
			}
			EatenRenewableFood f = new EatenRenewableFood(oldX,oldY,this.w,true,kindOfFood);
			
			w.addEntity(f);
			w.setEntityOnTheMap(f.getXPos(), f.getYPos(), f);
			
		}
	}
}
