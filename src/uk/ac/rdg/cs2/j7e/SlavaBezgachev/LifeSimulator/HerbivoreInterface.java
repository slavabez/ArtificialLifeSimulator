package uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator;

import uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator.World.Direction;

public interface HerbivoreInterface {
	// Methods that items of the Herbivore Life Forms will possess.
	// to run away from a predator if there is one nearby
	public void runAway(Direction d);

	// follow a larger herbivore
	public void follow(Direction d);

}
