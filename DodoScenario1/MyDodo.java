import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Scanner;

/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 20-01-2017
 */
public class MyDodo extends Dodo
{
    private int myNrOfEggsHatched;
    private int waardeBlauweEi;
    private int waardeGoudenEi;
    private boolean countedEggs;

    public MyDodo() {
        super( EAST );
        myNrOfEggsHatched = 0;
        waardeBlauweEi = 10;
        waardeGoudenEi = 2;
    }

    public void eggValueSwitch(){
        int tijdelijkeWaardeEi = waardeBlauweEi;
        waardeBlauweEi = waardeGoudenEi;
        waardeGoudenEi = tijdelijkeWaardeEi;

    }

    public void act() {
        gemiddeldeEierenPerRij();
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
     * Test if Dodo can move forward, (there are no obstructions
     *    or end of world in the cell in front of her).
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can move (no obstructions ahead)
     *                 false if Dodo can't move
     *                      (an obstruction or end of world ahead)
     */
    public boolean canMove() {
        if (borderAhead() || fenceAhead() ){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Hatches the egg in the current cell by removing
     * the egg from the cell.
     * Gives an error message if there is no egg
     * 
     * <p> Initial: Dodo is somewhere in the world. There is an egg in Dodo's cell.
     * <p> Final: Dodo is in the same cell. The egg has been removed (hatched).     
     */    
    public void hatchEgg () {
        if ( onEgg() ) {
            pickUpEgg();
            myNrOfEggsHatched++;
        } else {
            showError( "There was no egg in this cell" );
        }
    }

    /**
     * Returns the number of eggs Dodo has hatched so far.
     * 
     * @return int number of eggs hatched by Dodo
     */
    public int getNrOfEggsHatched() {
        return myNrOfEggsHatched;
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
        System.out.println("moved " + nrStepsTaken +" step taken");
    }

    /**
     * Walks to edge of the world printing the coordinates at each step
     * 
     * <p> Initial: Dodo is on West side of world facing East.
     * <p> Final:   Dodo is on East side of world facing East.
     *              Coordinates of each cell printed in the console.
     */

    public void walkToWorldEdge( ){
        while( !borderAhead() ){
            move();
        }
    }

    /**
     * Test if Dodo can lay an egg.
     *          (there is not already an egg in the cell)
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can lay an egg (no egg there)
     *                 false if Dodo can't lay an egg
     *                      (already an egg in the cell)
     */

    public boolean canLayEgg( ){
        if( onEgg() ){
            return false;
        }else{

            return true;
        }
    }  

    public void turn180(){
        turnRight();
        turnRight();
    }

    public void climbOverFence(){
        if(fenceAhead()){             
            turnLeft();
            move();
            turnRight();
            move();
            move();
            turnRight();
            move();
            turnLeft();
        }
    }

    public boolean grainAhead(){
        move();
        if (onGrain()){
            stepOneCellBackwards();
            return true;
        } else {
            stepOneCellBackwards();
            return false;
        }
    }

    public void gotoEgg(){
        if(!onEgg()){
            while(!onEgg()){
                move();
            }
        }
    }

    public void goBackToStartOfRowAndFaceBack(){
        turn180();
        walkToWorldEdge();
        turn180();
    }

    public void walkToWorldEdgeClimbingOverFences(){
        while(!borderAhead()){
            if(fenceAhead()){
                climbOverFence();
                if(onNest()){
                    layEgg();
                }
            } else {
                move();
            }
        }
    }

    public void pickUpGrainsAndPrintCoordinates(){
        while(canMove()){
            if(onGrain()){
                System.out.println("X: " + getX() + " Y: " + getY());
                pickUpGrain();
            }
            move();
        }

        if(onGrain()){
            System.out.println("X: " + getX() + " Y: " + getY());
            pickUpGrain();
        }
    }

    public void stepOneCellBackwards(){
        turn180();
        move();
        turn180();
    }

    public void walkToWorldEdgeLayingEggsInNests(){
        while(!borderAhead()){
            if(onNest()){
                layEgg();
            }
            move();
        }
        if (onNest()) {
            layEgg();
        }
    }

    public void walkAroundFencedArea(){
        while (!onEgg()){
            if (fenceAhead()){
                turnLeft();
            } 
            else{
                move();
                if (!fenceAhead()){
                    turnRight();
                }
            }
        }
    }

    public void eggTrailToNest(){
        while(!onNest()){
            if(eggAhead() || nestAhead()){
                move();
            } else{
                turnLeft();
                if(eggAhead() || nestAhead()){
                    move();
                } else {
                    turn180();
                    if(eggAhead() || nestAhead()){
                        move();
                    } else {
                        turnLeft();
                        if(eggAhead() || nestAhead()){
                            move();
                        }
                    }
                }
            } 
        }
    }

    public void mazeSolver(){
        while (!onNest())   {
            turnLeft();
            if (!fenceAhead()){
                move();
            } else{
                turnRight();
                if (!fenceAhead()){
                    move();
                } else {
                    turn180();
                }
            }
        }
        showCompliment("good job, maze done!");
    }

    public void faceEast(){
        while (getDirection() != EAST){
            setDirection(EAST);
        }
    }

    public boolean validCoordinates(int x, int y){
        if (x < getWorld().getWidth() && y < getWorld().getHeight())
            return true;
        else {
            return false;
        }
    }

    public boolean locationReached(int x, int y)
    {
        return getX() == x && getY() == y;
    }

    public void goToLocation(int coordX, int coordY)
    {
        if (validCoordinates(coordX, coordY))
        {

            while (!locationReached(coordX, coordY))
            {
                if (getX() < coordX)
                {
                    setDirection(EAST);
                    move();
                }
                else if (getX() > coordX)
                {
                    setDirection(WEST);
                    move();
                }
                else if (getY() < coordY)
                {
                    setDirection(SOUTH);
                    move();
                }
                else if (getY() > coordY)
                {
                    setDirection(NORTH);
                    move();
                }
            }
        }
    }

    public int countEggsInRow(){
        int eggCounter = 0;
        if(onEgg()){
            eggCounter++;
        }

        while (!borderAhead()){
            move();
            if (onEgg()){
                eggCounter++;
            }
        }
        goBackToStartOfRowAndFaceBack();
        return eggCounter;
    }

    public void layTrailOfEggs(int n) {
        int eggsLaid = 0;

        if (n > getWorld().getWidth() || borderAhead()){
            showError("Too much egg for this world.");
        }
        else {
            while(eggsLaid < n && !borderAhead()) {
                if (canLayEgg()) {
                    layEgg();
                    eggsLaid++;
                }
                if (eggsLaid < n) {
                    move();
                }
            }
        }
    }

    public void countEggsInWorld(){
        int startX = getX();
        int startY = getY();

        int totalEggs = 0;
        int rowMostEggs = 0;
        int currentRowMostEggs = 0;

        if (countedEggs){
            return;
        }

        if (startY > 0){
            goToLocation(0, 0);
        }
        for (int row = 0; row < getWorld().getHeight(); row++){
            goToLocation(0, row);
            setDirection(EAST);
            int eggsInRow = countEggsInRow();
            totalEggs = totalEggs + eggsInRow;
            System.out.println("Total eggs in world: " + totalEggs);
            if (eggsInRow > rowMostEggs){
                rowMostEggs = eggsInRow;
                currentRowMostEggs = row;
            }
        }
        goToLocation(startX, startY);
        setDirection(EAST);
        System.out.println("highest eggs in row: " + currentRowMostEggs);

        countedEggs = true;
    }

    public void monumentOfEggs() {
        int startX = getX();
        int startY = getY();

        for (int row = 0; row < getWorld().getHeight(); row++) {
            for (int column = 0; column <= row; column++) {
                int x = startX + column;
                int y = startY + row;

                if (validCoordinates(x, y)) {
                    if (canLayEgg()) {
                        layEgg();
                    }
                    if (column < row) {
                        setDirection(EAST);
                        move();
                    }
                }
            }
            if (validCoordinates(startX, startY + row + 1)) {
                goToLocation(startX, startY + row + 1);
            }
        }
        goToLocation(startX, startY);
        setDirection(EAST);
    }

    public void stevigMonument(){
        int startX = getX();
        int startY = getY();
        int eggsInRow = 1; 
        for (int row = 0; row < getWorld().getHeight(); row ++) {
            for (int column = 0; column < eggsInRow; column++) {
                int x = startX + column;
                int y = startY + row;

                if (!borderAhead() || validCoordinates(x, y)) {
                    if (canLayEgg()) {
                        layEgg();
                    }
                    if (column < eggsInRow) {
                        setDirection(EAST);
                        move();
                    }
                }
            }
            eggsInRow *= 2;
            if (validCoordinates(startX, startY + row + 1)) {
                goToLocation(startX, startY + row + 1);
            }
        }
        goToLocation(startX, startY);
        setDirection(EAST);
    }

    public void piramideMonument(){
        int startX = getX();
        int startY = getY();
        for (int row = 0; row < getWorld().getHeight(); row++) {
            int eggsInRow = 2 * row + 1;
            int rowStartX = startX - row;
            for (int column = 0; column < eggsInRow; column++) {
                int x = rowStartX + column;
                int y = startY + row;
                if (validCoordinates(x, y)) {
                    goToLocation(x, y);
                    if (canLayEgg()) {
                        layEgg();
                    }
                }
            }
        }
        goToLocation(startX, startY);
        setDirection(EAST);
    }

    public double gemiddeldeEierenPerRij() {
        int totalEggs = 0;
        int totalRows = getWorld().getHeight();

        for (int row = 0; row < totalRows; row++) {
            for (int column = 0; column < getWorld().getWidth(); column++) {
                if (validCoordinates(column, row)) {
                    goToLocation(column, row);
                    if (onEgg()) {
                        totalEggs++;
                    }
                }
            }
        }

        double average = (double) totalEggs/totalRows;

        System.out.println("Gemiddelde eieren per rij: " + average);
        return average;
    }
}

