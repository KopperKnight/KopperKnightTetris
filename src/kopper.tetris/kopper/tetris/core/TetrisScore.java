package kopper.tetris.core;
import kopper.tetris.shape.Shape;
import kopper.tetris.shape.ShapeI;
import kopper.tetris.shape.ShapeJ;
import kopper.tetris.shape.ShapeL;
import kopper.tetris.shape.ShapeO;
import kopper.tetris.shape.ShapeS;
import kopper.tetris.shape.ShapeT;
import kopper.tetris.shape.ShapeZ;
/**
 * A class that keeps track of the score.
 * Since a formula for the composite score in Tetris is not standardized across many implementations I have played, this class simply keeps track 
 * of the number of shapes of each type that has been stacked or placed to the Tetris pile (or added to the {#link BackgroundGrid} object for painting by it) and the number of rows successfully
 * filled 100 percent (and thus deleted). The composite score is left to be implemented later on in subsequent versions of this game.
 * The score can not be reset as per design for now. If one needs to reset the score, one may choose to reinstantiate a {@code new TetrisScore()} object
 * at will.
 * @author KopperKnight
 *
 */
public class TetrisScore 
{
	/**
	 * Keeps the internal count of the number of shapes for an "I" shaped Tetromino placed on the Tetris structure or pile.
	 */
	private int countI=0;
	/**
	 * Keeps the internal count of the number of shapes for an "O" shaped Tetromino placed on the Tetris structure or pile.
	 */
	private int countO=0;
	/**
	 * Keeps the internal count of the number of shapes for an "T" shaped Tetromino placed on the Tetris structure or pile.
	 */
	private int countT=0;
	/**
	 * Keeps the internal count of the number of shapes for an "J" shaped Tetromino placed on the Tetris structure or pile.
	 */
	private int countJ=0;
	/**
	 * Keeps the internal count of the number of shapes for an "L" shaped Tetromino placed on the Tetris structure or pile.
	 */
	private int countL=0;
	/**
	 * Keeps the internal count of the number of shapes for an "S" shaped Tetromino placed on the Tetris structure or pile.
	 */
	private int countS=0;
	/**
	 * Keeps the internal count of the number of shapes for an "Z" shaped Tetromino placed on the Tetris structure or pile.
	 */
	private int countZ=0;
	/**
	 * Keeps the internal count of the number of rows successfully filled and therefore deleted as part of the game logic.
	 */
	private int countRows=0;
	/**
	 * Keeps the internal count of the number of times the shape was advanced downwards via keyboard command (and not counting the natural periodic
	 * game logic commanded translation of Tetromino shapes downwards by one box per regular period).
	 */
	private int countArrowDowns=0;
	
	
	/**
	 *  Adds only one integer point to the internal variable representing the specific subclass of the Shape parameter. This method
	 *  is defined to process subclasses of Shape of the following types: {@link ShapeI}, {@link ShapeO}, {@link ShapeT}, {@link ShapeJ}, {@link ShapeL}, 
	 *  {@link ShapeS}, {@link ShapeZ}, which are the default shapes of any popular implementation of Tetris. 
	 *  To process any other subclasses of Shape, requires this class and this method to be overridden in a new sub class.
	 * @param s an object that is a subclass of Shape.
	 */
	public void incrementShape(Shape s)
	{
		if(s instanceof ShapeI)
		{
			countI++;
		}
		else if(s instanceof ShapeO)
		{
			countO++;
		}
		else if(s instanceof ShapeT)
		{
			countT++;
		}
		else if(s instanceof ShapeJ)
		{
			countJ++;
		}
		else if(s instanceof ShapeL)
		{
			countL++;
		}
		else if(s instanceof ShapeS)
		{
			countS++;
		}
		else if(s instanceof ShapeZ)
		{
			countZ++;
		}
	}
	/**
	 *   Adds only one integer point to the internal variable representing the number of rows successfully filled (and thus deleted). 
	 * @param rows the number of rows deleted in the most recent deletion.
	 */
	public void incrementRow(int rows)
	{
		countRows+=rows;
	}
	/**
	 *  Adds only one integer point to the internal variable representing the number of times any Tetromino shape has been advanced
	 *  downwards due to keyboard/user input.
	 */
	public void incrementArrowDown()
	{
		countArrowDowns++;
	}
	
	/**
	 * Returns the internal count of the number of shapes for an "I" shaped Tetromino placed on the Tetris structure or pile.
	 * @return the "I" score.
	 */
	public int getShapeI()
	{
		return countI;
	}
	/**
	 * Returns the internal count of the number of shapes for an "O" shaped Tetromino placed on the Tetris structure or pile.
	 * @return the "O" score.
	 */
	public int getShapeO()
	{
		return countO;
	}
	/**
	 * Returns the internal count of the number of shapes for an "T" shaped Tetromino placed on the Tetris structure or pile.
	 * @return the "T" score.
	 */
	public int getShapeT()
	{
		return countT;
	}
	/**
	 * Returns the internal count of the number of shapes for an "J" shaped Tetromino placed on the Tetris structure or pile.
	 * @return the "J" score.
	 */
	public int getShapeJ()
	{
		return countJ;
	}
	/**
	 * Returns the internal count of the number of shapes for an "L" shaped Tetromino placed on the Tetris structure or pile.
	 * @return the "L" score.
	 */
	public int getShapeL()
	{
		return countL;
	}
	/**
	 * Returns the internal count of the number of shapes for an "S" shaped Tetromino placed on the Tetris structure or pile.
	 * @return the "S" score.
	 */
	public int getShapeS()
	{
		return countS;
	}
	/**
	 * Returns the internal count of the number of shapes for an "Z" shaped Tetromino placed on the Tetris structure or pile.
	 * @return the "Z" score.
	 */
	public int getShapeZ()
	{
		return countZ;
	}
	/**
	 * Returns the internal count of the number of rows successfully filled and thus deleted as part of the game logic.
	 * @return the number of rows filled and deleted.
	 */
	public int getRows()
	{
		return countRows;
	}
	/**
	 * Returns the internal count of the number of times any Tetromino shape has been advanced downwards due to keyboard/user input.
	 * @return the count of keyboard inputs downwards.
	 */
	public int getArrowDowns()
	{
		return countArrowDowns;
	}
	/**
	 * Returns the total score. Currently, this is defined as a Sum of all the individual internal variables of this object, 
	 * except that returned by {@code getArrowDowns()}
	 * Returns the same as {@code getShapeI()+getShapeO()+getShapeT()+getShapeJ()+getShapeL()+getShapeZ()+getShapeS()+getRows();}
	 * @return The total score.
	 */
	public int getScore()
	{
		return this.countI+this.countO+this.countT+this.countJ+this.countL+this.countZ+this.countS+this.countRows;
	}
	/**
	 * Returns this object's String representation, which is an itemized single line string denoting all the scores and component scores of this object.
	 * @return this object's String representation.
	 */
	public String toString()
	{
		return "I="+countI+"   O="+countO+"   T="+countT+"   J="+countJ+"   L="+countL+"   S="+countS+"   Z="+countZ+"   R="+countRows+"   D="+countArrowDowns;
		
	}
}
