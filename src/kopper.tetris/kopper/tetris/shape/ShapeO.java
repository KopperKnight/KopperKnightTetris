package kopper.tetris.shape;
import java.awt.Color;

import kopper.tetris.core.Coord;

/**
 * <H3> A Reference diagram of coordinate space of this class</H3>
 *
 *
 * <img src="doc-files/shapeO.png" alt="A Tetromino Shape Coordinate System Example">
 *
 *
 *<p>
 *Figure 1 shows how coordinates for the objects of this class are stored. Each Shape defines an Absolute Position (see bolded coordinate
 *(4,3) in Figure 1), which is the absolute position of the Shape's center most cell in relation to the upper left corner of the game screen. Then each 
 *shape defines the rest of its member cells using relative coordinates (see plainly-styled, non-bolded coordinates in Figure 1) that have an
 * origin coordinate on the center cell. The absolute position is defined as an argument during this class's instantiation using the constructor.
 * </p>
 * <p>
 * During rendering of the screen the absolute position of each member cell can be easily calculated by adding the relative coordinates to the single
 * absolute coordinate representing the Shape's Absolute Position.
 * </p>
 * @author <a href="https://github.com/kopperknight">KopperKnight</a>
 *
 */
public class ShapeO extends Shape 
{
	/**
	 * Figure 1 above shows the actual locations of these relative coordinates.
	 */
	private Coord[] relativeCoords= {
			new Coord(0,0),
			new Coord(0,-1),
			new Coord(1,-1),
			new Coord(1,0)
	};
	/**
	 * Figure 1 above shows the actual locations of these temporary relative coordinates, when initiated. This variable is used to keep track of 
	 * coordinates for the {@code trial....():} methods of this class.
	 */
	private Coord[] tempCoords= Shape.cloneCoords(relativeCoords);
	/**
	 * Creates an object with the supplied x, y Cartesian coordinate for its absolute position. See Figure 1 for what this means precisely.
	 * @param absolutePos The absolute position of this Shape.
	 */
	public ShapeO(Coord absolutePos)
	{
		super(absolutePos);
	}
	/**
	 * Creates an object with the supplied x, y Cartesian coordinate for its absolute position. See Figure 1 for what this means precisely.
	 * @param x The x component of the coordinate location.
	 * @param y The y component of the coordinate location.
	 */
	public ShapeO(int x, int y)
	{
		super(x,y);
		this.rotateShapeClockwise90();
		this.rotateShapeCounterClockwise90();
		this.trialRotateShapeClockwise90();
		this.trialRotateShapeCounterClockwise90();
	}
	/**
	 * This method is overridden in this class to do nothing. Rotating a shape that this class represents, about its center cell (or absolute position),
	 * does not come across as a rotate, but as a translation and a wobble and therefore does not make sense.
	 */
	public void rotateShapeClockwise90(){}//do nothing, not applicable
	/**
	 * This method is overridden in this class to do nothing. Rotating a shape that this class represents, about its center cell (or absolute position),
	 * does not come across as a rotate, but as a translation and a wobble and therefore does not make sense.
	 */
	public void rotateShapeCounterClockwise90() {}//do nothing, not applicable
	/**
	 * This method is overridden in this class to do nothing. Rotating a shape that this class represents, about its center cell (or absolute position),
	 * does not come across as a rotate, but as a translation and a wobble and therefore does not make sense.
	 */
	public void trialRotateShapeClockwise90() {}//do nothing, not applicable;
	/**
	 * This method is overridden in this class to do nothing. Rotating a shape that this class represents, about its center cell (or absolute position),
	 * does not come across as a rotate, but as a translation and a wobble and therefore does not make sense.
	 */
	public void trialRotateShapeCounterClockwise90() {}//do nothing, not applicable.
	/**
	 * Methods of this class always returns {@link java.awt.Color#yellow }
	 * @return  {@link java.awt.Color#yellow }.
	 */
	public Color getCellColor(int cellNum)
	{
		return Color.YELLOW;
	}
	public Coord getRelativeCellPos(int cellNum)
	{
		return relativeCoords[cellNum];
	}
	public Coord getTrialRelativeCellPos(int cellNum)
	{
		return tempCoords[cellNum];
	}

	public int getCellCount() 
	{
		return tempCoords.length;
	}

}
