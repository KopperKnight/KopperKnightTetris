package kopper.tetris.core;

/**
 * A class to represent the x,y components of a Cartesian coordinate pair.
 * The needs fulfilled by this class could have easily been fulfilled by {@link java.awt.Point}, however, implementing my own version,
 * was easier to make certain methods and operations specific to Tetris available.
 * @author <a href="https://github.com/kopperknight">KopperKnight</a>
 *
 */
public class Coord 
{
	
	/**
	 *  Represents the x component of this object's Cartesian coordinate pair.
	 */
	private int x;
	/**
	 *  Represents the y component of this object's Cartesian coordinate pair.
	 */
	private int y;
	/**
	 * Creates a new object of this class with default Cartesian coordinates of (x,y) =(0,0);
	 */
	public Coord()
	{
		this.x=0;
		this.y=0;
	}
	/**
	 * Creates a new object of this class with the specified Cartesian coordinates.
	 * @param x The x component of the coordinate.
	 * @param y The y component of the coordinate.
	 */
	public Coord(int x, int y)
	{
		this.x=x;
		this.y=y;
	}
	/**
	 * Translates the coordinate point represented by this object by adding the specified x and y values to it.
	 * @param x The amount to add to the X (or subtract if negative) to the component.
	 * @param y The amount to add to the Y (or subtract if negative) to the component.
	 */
	public void translate(int x, int y)
	{
		this.x+=x;
		this.y+=y;
	}
	/**
	 * Translates the coordinate point represented by this object by adding the specified x and y values of the specified coordinate to it.
	 * @param c The translation vector.
	 */
	public void translate(Coord c)
	{
		translate(c.getX(),c.getY());
	}
	/**
	 * Adds this coordinates to the specified coordinate and returns a newly instantiated resultant. This object is internally
	 *  unaffected by the arguments or the result. In fact, supplying arguments representing a zero vector results in a 
	 *  newly instantiated clone of this coordinate object as a result.
	 * @param c The coordinate to add.
	 * @return The resultant coordinate that is the sum of this coordinate and the argument coordinate.
	 */
	public Coord add(Coord c)
	{
		return new Coord(this.getX()+c.getX(),this.getY()+c.getY());
	}
	/**
	 * Adds this coordinates's x and y values to the specified coordinate's x and y values and 
	 * returns a newly instantiated coordinate with x and y values being the sum result. This object is internally
	 *  unaffected by the arguments or the result. In fact, supplying arguments representing a zero vector results in a 
	 *  newly instantiated clone of this coordinate object as a result.
	 * @param x The x value to add to this coordinates x value.
	 * @param y The y value to add to this coordinate y value.
	 * @return The resultant coordinate that is the sum of this coordinate and the argument coordinate.
	 */
	public Coord add(int x, int y)
	{
		return new Coord(this.getX()+x,this.getY()+y);
	}
	/**
	 * Gets the x component of this objects Cartesian coordinate representation.
	 * @return the x value of this object.
	 */
	public int getX()
	{
		return x;
	}
	/**
	 * Sets the x component of this objects Cartesian coordinate representation.
	 * @param x the new x value for this object.
	 */
	public void setX(int x)
	{
		this.x=x;
	}
	/**
	 * Gets the y component of this objects Cartesian coordinate representation.
	 * @return the y value of this object.
	 */
	public int getY()
	{
		return y;
	}
	/**
	 * Sets the y component of this objects Cartesian coordinate representation.
	 * @param y the new x value for this object.
	 */
	public void setY(int y)
	{
		this.y=y;
	}
	/**
	 * A semantic method which is the same as returning {@link Coord#getY()}.
	 * @return the y value of this coordinate's representation.
	 */
	public int getRow()
	{
		return this.y;
	}
	/**
	 * A semantic method which is the same as returning {@link Coord#getX()}.
	 * @return the x value of this coordinate's representation.
	 */
	public int getColumn()
	{
		return this.x;
	}
	/**
	 * Sets the x and y components of this objects Cartesian coordinate representation.
	 * @param x the new x value for this object.
	 * @param y the new y value for this object.
	 */
	public void set(int x, int y)
	{
		this.setX(x);
		this.setY(y);
	}
	/**
	 * Returns a one line String representation of this object of in the form of "[Coord]=(x,y)=([x],[y]);".
	 * @return The String representation of this object.
	 */
	public String toString()
	{
		return "[Coord]=(x,y)=("+x+", "+y+");";
	}
}
