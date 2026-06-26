import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.1 -- 29-07-2017
 */
public class MyDodo extends Dodo
{

    public MyDodo() {
        super( EAST );
    }

    public void act() {
        makeListOfSurpriseEggs();
        findMostValueEgg();
        averageValue();
        
    }

    /**
     * Move one cell forward in the current direction.
     * 
     * <P> Initial: Dodo is somewhere in the world
     * <P> Final: If possible, Dodo has moved forward one cell
     *
     */
    public void move() {
        if ( canMove() ) {
            step();
        } else {
            showError( "I'm stuck!" );
        }
    }

    /**
     * Test if Dodo can move forward, 
     * i.e. there are no obstructions or end of world in the cell in front of her.
     * 
     * <p> Initial:   Dodo is somewhere in the world
     * <p> Final:     Same as initial situation
     * 
     * @return  boolean true if Dodo can move (thus, no obstructions ahead)
     *                  false if Dodo can't move
     *                      there is an obstruction or end of world ahead
     */
    public boolean canMove() {
        if ( borderAhead() || fenceAhead() ){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Move given number of cells forward in the current direction.
     * 
     * <p> Initial:   
     * <p> Final:  
     * 
     * @param   int distance: the number of steps made
     */
    public void jump( int distance ) {
        int nrStepsTaken = 0;               // set counter to 0
        while ( nrStepsTaken < distance ) { // check if more steps must be taken  
            move();                         // take a step
            nrStepsTaken++;                 // increment the counter
        }
    }

    /**
     * Places all the Egg objects in the world in a list.
     * 
     * @return List of Egg objects in the world
     */
    public List<Egg> getListOfEggsInWorld() {
        return getWorld().getObjects(Egg.class);
    }

    public List<Integer> createListOfNumbers() {
        return new ArrayList<> (Arrays.asList( 2, 43, 7, -5, 12, 7 ));
    }

    /**
     * Method for praciticing with lists.
     */
    public void practiceWithLists( ){
        List<Integer> listOfNumbers = createListOfNumbers();

        //the following is incorrect and is to be fixed in challenge 6.1c
        System.out.println("First element: " + listOfNumbers.get(1) ); 
    }

    public void practiceWithListsOfSurpriseEggs( ){
        List<SurpriseEgg>  listOfEgss = SurpriseEgg.generateListOfSurpriseEggs( 12, getWorld() );
    }

    public List<SurpriseEgg> makeListOfSurpriseEggs() {
        return SurpriseEgg.generateListOfSurpriseEggs(10, getWorld());
    }

    public void printCoordinatesOfEgg(Egg egg) {
        System.out.println("X: " + egg.getX() + " Y: " + egg.getY());
    }

    public void makeListSurpriseEggsPrintCoordinates(){
        List<SurpriseEgg> list = makeListOfSurpriseEggs();
        for (SurpriseEgg egg : list) {
            printCoordinatesOfEgg(egg);
        }
    }
    public void findMostValueEgg(){
        List<SurpriseEgg> list = makeListOfSurpriseEggs();
        SurpriseEgg mostValue = list.get(0);
        for (SurpriseEgg egg : list) {
            System.out.println("X: " + egg.getX() + " Y: " + egg.getY() + " Waarde: " + egg.getValue());
            if (egg.getValue() > mostValue.getValue()) {
                mostValue = egg;
            }
        }
        System.out.println("Meeste waard: X = " + mostValue.getX() + " Y = " + mostValue.getY() + " Waarde = " + mostValue.getValue());
    }
    public void averageValue(){
        List<SurpriseEgg> list = makeListOfSurpriseEggs();
        int total = 0;
        for (SurpriseEgg egg : list) {
            total += egg.getValue();
        }
        System.out.println("Gemiddelde waarde: " + (double) total / list.size());
    }
}

