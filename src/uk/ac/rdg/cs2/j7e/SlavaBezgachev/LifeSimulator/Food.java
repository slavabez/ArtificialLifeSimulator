package uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator;

public abstract class Food extends Entity {
	protected double calories;
	protected World w;
	
	Food(){
		activity=new Thread(this);
	}
	
	public void setCalories(double d){
		this.calories=d;
	}
	
	public double getCalories(){
		return this.calories;
	}
	public void remove(){
		this.stop();
		w.setEntityOnTheMap(this.xPos, this.yPos, new EmptyCell(this.xPos,this.yPos,this.w));
	}
}
