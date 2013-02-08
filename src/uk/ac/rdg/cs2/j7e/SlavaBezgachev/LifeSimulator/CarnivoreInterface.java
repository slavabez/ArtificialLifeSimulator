package uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator;
/**
 * Carnivore interface. Consists of only one method: attack(LifeForm). Can be enchanced if the simulator is developed further
 * @author Slava
 *
 */
public interface CarnivoreInterface {
	// Interface for a carnivore species. Contains the methods a carnivore life form will possess.
	
	/**
	 * Attacking a life form.
	 * @param l - the life form being attacked
	 */
	void attack(LifeForm l);
	
	
}
