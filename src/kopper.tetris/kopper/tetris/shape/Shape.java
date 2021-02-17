package kopper.tetris.shape;

import java.awt.*;

import kopper.tetris.core.*;

/**
 * 
 *
 *
 *A class that defines the superclass for a Tetromino shape. All subclasses should at a minimum, maintain two copies of data for 
 *relative coordinates. It is suggested that this can be done by defining {@link Shape#getRelativeCellPos(int)}, {@link Shape#getTrialRelativeCellPos(int)},
 *and {@link Shape#getCellCount()} in a subclass to return data from two arrays of {@link kopper.tetris.core.Coord} elements of equal length, respectively. 
 *Then since most normal Tetris games have a uniform color across a single Tetromino, {@link Shape#getCellColor(int)} can simply return a single color, regardless of the
 *cell index argument. Subclasses do not need to implement the absolute position or trial absolute position data, as that is handled in this super class.
 *
 *<H3> A Reference diagram of coordinate space of this class and subclasses</H3>
 *
 *
 * <img src="doc-files/shapeL.png" alt="A Tetromino Shape Coordinate System Example">
 *
 *
 *<p>
 *Figure 1 shows how coordinates for the objects of this class and subclasses are stored. Each Shape defines an Absolute Position (see bolded coordinate
 *in Figure 1), which is the absolute position of the Shape's center most cell in relation to the upper left corner of the game screen. Then each 
 *shape defines the rest of its member cells using relative coordinates (see plainly-styled, non-bolded coordinates in Figure 1) that have an
 * origin coordinate on the center cell. 
 * </p>
 * <p>
 * During rendering of the screen the absolute position of each member cell can be easily calculated by adding the relative coordinates to the single
 * absolute coordinate representing the Shape's Absolute Position.
 * </p>
 * * @author KopperKnight
 *
 */
public abstract class Shape 
{
	/**
	 * Creates a new Coord array with each object an exact duplicate of the corresponding object in the supplied Coord array.
	 * @param coords The array to duplicate.
	 * @return A duplicate array.
	 */
	public static Coord[] cloneCoords(Coord[]coords)
	{
		Coord[] temp=new Coord[coords.length];
		for(int i=0;i<temp.length;i++)
			temp[i]=coords[i].add(0,0);
		return temp;
	}
	/**
	 * A temp variable representing this Shape's position used for the trial methods.
	 */
	private Coord tempAbs;
	/**
	 * The Coord variable that represents this shape's position in relation to the upper left corner of the game screen.
	 */
	private Coord absPos;
	/**
	 * a debug variable. Set true during non-debug situations.
	 */
	private boolean visible=true;
	/**
	 * Creates an object with the supplied x, y Cartesian coordinate for its absolute position. See Figure 1 for what this means precisely.
	 * @param gridx The x component of the coordinate location.
	 * @param gridy The y component of the coordinate location.
	 */
	public Shape(int gridx,int gridy)
	{
		this(new Coord(gridx,gridy));
	}
	/**
	 * Creates an object with the supplied x, y Cartesian coordinate for its absolute position. See Figure 1 for what this means precisely.
	 * @param absolutePos The absolute position of this Shape.
	 */
	public Shape(Coord absolutePos)
	{
		this.absPos=absolutePos;
		this.tempAbs=new Coord(absPos.getX(),absPos.getY());
	}
	/**
	 * Sets this Shape's visibility boolean. Default it is visible is true.
	 * @param v True if visible, false if otherwise.
	 */
	public void setVisible(boolean v)
	{
		this.visible=v;
	}
	/**
	 * Returns this Shape's visibility boolean. Default is true. If false, then {@link Shape#drawShape(Graphics2D, BackgroundGrid)} and
	 * {@link Shape#drawShape(Graphics2D, GridCell[][])} do nothing.
	 * @return If false, then it is not drawn.
	 */
	public boolean isVisible()
	{
		return this.visible;
	}
	
	/**
	 * <p>This method is named differently for various classes, but all classes that must paint 
	 * representations of their data to the window, have some variation
	 * of a method {@code drawOBJECTNAME(Graphics2D g2d);} This is that method for this class.
	 * <p>Note: TO BE REIMPLEMENTED AS AN INTERFACE DEFINITION implemented by all drawable classes in the future.
	 * 
	 * <p>This method is where the background model data represented by this object is painted to represent this object.
	 * Specifically, this object keeps track of each gridcell's color, each cell making up this Shape's shape relative to this Shape's origin.
	 * 
	 * <p>
	 * This method is called inside {@link TetrisGame#paintGameRunning(Graphics2D, kopper.tetris.core.TetrisGame.State) },
	 * or inside of {@link TetrominoStats#drawTetrominoStats(Graphics2D, boolean) },
	 *  which in turn is called by each respective caller methods (see their method documentation).
	 * 
	 * 
	 * @param g2d The graphics object ultimately supplied by overridden method  {@link TetrisGame#paintComponent(java.awt.Graphics)}
	 * @param grid The grid that represents the background. This is used to prevent Exceptions that would occur if an off screen location
	 * represented by the shape were called. 
	 * 
	 * 
	 */
	public void drawShape(Graphics2D g2d,BackgroundGrid grid)
	{
		if(this.isVisible())
		{
			for(int i=0;i<getCellCount();i++)
			{
				grid.getCell(getAbsoluteCellPos(i).getRow(), getAbsoluteCellPos(i).getColumn()).setColor(getCellColor(i));
				grid.getCell(getAbsoluteCellPos(i).getRow(), getAbsoluteCellPos(i).getColumn()).drawCell(g2d,true);
			}
		}
		
	}
	/**
	 * <p>This method is named differently for various classes, but all classes that must paint 
	 * representations of their data to the window, have some variation
	 * of a method {@code drawOBJECTNAME(Graphics2D g2d);} This is that method for this class.
	 * <p>Note: TO BE REIMPLEMENTED AS AN INTERFACE DEFINITION implemented by all drawable classes in the future.
	 * 
	 * <p>This method is where the background model data represented by this object is painted to represent this object.
	 * Specifically, this object keeps track of each gridcell's color, whether it is occupied by a cell of a dead shape
	 * or empty space and the background color of empty space. 
	 * 
	 * <p>
	 * This method is called via helper method {@link TetrisGame#paintGameRunning(Graphics2D, kopper.tetris.core.TetrisGame.State)}, which in turn is called by {@link TetrisGame#paintComponent(java.awt.Graphics)}, 
	 * which in turn is an overridden method of {@link javax.swing.JComponent#paintComponent(Graphics g)}.
	 * @param g2d The graphics object ultimately supplied by overridden method  {@link TetrisGame#paintComponent(java.awt.Graphics)}
	 * @param cells This is used for drawing shapes that are not on top of the {@link BackgroundGrid} object. For example, drawing the
	 * {@link TetrominoStats#drawTetrominoStats(Graphics2D, boolean)} requires this method.
	 */
	public void drawShape(Graphics2D g2d,GridCell[][]cells)
	{
		if(this.isVisible())
		{
			for(int i=0;i<getCellCount();i++)
			{
				cells[this.getAbsoluteCellPos(i).getRow()][this.getAbsoluteCellPos(i).getColumn()].setColor(this.getCellColor(i));
				cells[this.getAbsoluteCellPos(i).getRow()][this.getAbsoluteCellPos(i).getColumn()].drawCell(g2d, true);
			}
		}
		
	}
	/**
	 * Moves the absolute position of this object downwards one row. The same as calling {@code translateShape(0,1)}.
	 */
	public void translateShapeDown()
	{
		this.translateShape(0, 1);
	}
	/**
	 * Moves the absolute position of this object by adding the x and y parameters to the existing x and y components of this Shape's absolute position.
	 * @param x The value to add to the X component (negative value is subtraction).
	 * @param y The value to add to the Y component (negative value is subtraction).
	 */
	public void translateShape(int x,int y)
	{
		this.getAbsoluteShapePos().translate(x,y);
	}
	/**
	 * Makes the trial coordinates equal in value (still separate objects in memory) to the actual coordinate data of this Shape. Effectively clearing
	 * or erasing any trial moves made previously.
	 */
	public void clearTrial()//reset temp variables to equal legal variables
	{
		this.getTrialAbsoluteShapePos().setX(this.getAbsoluteShapePos().getX());
		this.getTrialAbsoluteShapePos().setY(this.getAbsoluteShapePos().getY());
		for(int i=0;i<this.getCellCount();i++)
			this.getTrialRelativeCellPos(i).set(this.getRelativeCellPos(i).getX(), this.getRelativeCellPos(i).getY());
	}
	/**
	 * This method translates this Shape just like {@link Shape#translateShape(int, int)}, except it does so on temp variable copies of this Shape's 
	 * actual position.If a trial operation has already been called previously, this clears it so that the trial move will represent only one potential
	 *  move away from current actual Shape's internal data state. It is important to test collisions between Shape and other grid coordinates of the game by making moves before they actually happen.
	 * @param x The value to add to the X component (negative value is subtraction).
	 * @param y The value to add to the Y component (negative value is subtraction).
	 * 
	 */
	public void trialTranslateShape(int x, int y)
	{
		clearTrial();
		this.getTrialAbsoluteShapePos().translate(x,y);
	}
	/**
	 * This method rotates this Shape just like {@link Shape#rotateShapeClockwise90() }, except it does so on temp variable copies of this Shape's 
	 * actual position. If a trial operation has already been called previously, this clears it so that the trial move will represent only one potential
	 *  move away from current actual Shape's internal data state.It is important to test collisions between Shape and other grid coordinates of the game by making moves before they actually happen.
	 */
	public void trialRotateShapeClockwise90()
	{
		clearTrial();
		int x=0;
		int y=0;
		for(int i=0;i<this.getCellCount();i++)
		{
			x=this.getRelativeCellPos(i).getX();
			y=this.getRelativeCellPos(i).getY();
			this.getTrialRelativeCellPos(i).setX(-y);
			this.getTrialRelativeCellPos(i).setY(x);
		}	
	}
	/**
	 * This method rotates this Shape just like {@link Shape#rotateShapeCounterClockwise90() }, except it does so on temp variable copies of this Shape's 
	 * actual position. If a trial operation has already been called previously, this clears it so that the trial move will represent only one potential
	 *  move away from current actual Shape's internal data state. It is important to test collisions between Shape and other grid coordinates of the
	 *  game by making moves before they actually happen.
	 */
	public void trialRotateShapeCounterClockwise90()
	{
		clearTrial();
		int x=0;
		int y=0;
		for(int i=0;i<this.getCellCount();i++)
		{
			x=this.getRelativeCellPos(i).getX();
			y=this.getRelativeCellPos(i).getY();
			this.getTrialRelativeCellPos(i).setX(y);
			this.getTrialRelativeCellPos(i).setY(-x);
		}	
	}
	/**
	 * This method rotates this Shape 90 degrees in the clockwise direction. The axis of rotation is the absolute position coordinate of this Shape.
	 */
	public void rotateShapeClockwise90()
	{
		Coord relativeCellCoord;
		int x=0;
		int y=0;
		for(int i=0;i<getCellCount();i++)
		{
			relativeCellCoord=this.getRelativeCellPos(i);
			x=relativeCellCoord.getX();
			y=relativeCellCoord.getY();
			
			relativeCellCoord.setX(-y);
			relativeCellCoord.setY(x);
		}
	}
	/**
	 * This method rotates this Shape 90 degrees in the counter clockwise direction. The axis of rotation is the absolute position coordinate of this Shape.
	 */
	public void rotateShapeCounterClockwise90()
	{
		Coord relativeCellCoord;
		int x=0;
		int y=0;
		for(int i=0;i<getCellCount();i++)
		{
			relativeCellCoord=this.getRelativeCellPos(i);
			x=relativeCellCoord.getX();
			y=relativeCellCoord.getY();
			
			relativeCellCoord.setX(y);
			relativeCellCoord.setY(-x);
		}
	}
	/**
	 * Returns this shape's absolute position, which is always the axis of rotation and typically the center cell of the Shape's numerous cells.
	 * @return The position of this object's center cell in relation to the upper left corner cell of the game window.
	 */
	public Coord getAbsoluteShapePos()
	{
		return this.absPos;
	}
	/**
	 * Works like (@link Shape#getAbsoluteShapePos()}, except it returns the trial version. If no trial translation or trial rotation method 
	 * has been previously called or if (@link Shape#clearTrial()} has been called, then the Coord returned will represent the exact same 
	 * Coord as that returned by {@link Shape#getAbsoluteShapePos()}. In essence, a trial method makes pretend move that and this method returns
	 * the absolute center cell of this Shape if it had actually made that move in reality. This method can be thought of as returning
	 *  the future position of this Shape's center cell.
	 * @return The future position of the center cell of this object in relation to the upper left corner cell of the game.
	 */
	public Coord getTrialAbsoluteShapePos()
	{
		return this.tempAbs;
	}
	/**
	 * This method adds the cell center returned by {@link Shape#getAbsoluteShapePos()} to each of this cell's relative coordinates.
	 * A Shape is represented by two major sets of data. First, its absolute position data pointing to this Shape's center cell in relation
	 *  to the upper left corner of the game screen's grid.
	 * Second, an array or index of non-center cells that describe their location relatively with respect to the center cell of the Shape.
	 * This method converts all of those relative coordinates to absolute ones by returning new Coordinate objects that add the relative and absolute
	 * position data for all the cells of this Shape together.
	 * 
	 *  @param cellNum The index of the cell that makes up this Shape object.
	 * @return The cell location in relation to the upper left corner grid coordinate of the game screen grid.
	 */
	public Coord getAbsoluteCellPos(int cellNum)
	{
		return getRelativeCellPos(cellNum).add(absPos);
	}
	/**
	 * Works like (@link Shape#getAbsoluteCellPos(int) }, except it returns the trial version. If no trial translation or trial rotation method 
	 * has been previously called or if (@link Shape#clearTrial()} has been called, then the Coord returned will represent the exact same 
	 * Coord as that returned by {@link Shape#getAbsoluteCellPos(int) }. In essence, a trial method makes pretend move that and this method returns
	 * the {@code cellNum'th} cell of this Shape if it had actually made that move in reality. This method can be thought of as returning
	 *  the future position of this Shape's {@code cellNum'th} cell.
	 *  @param cellNum The index of the cell that makes up this Shape object.
	 * @return The future position of the cell of this object in relation to the upper left corner cell of the game.
	 */
	public Coord getTrialAbsoluteCellPos(int cellNum)
	{
		return getTrialRelativeCellPos(cellNum).add(tempAbs);
	}
	/**
	 * Returns the cell color for this Shape's {@code cellNum}'th cell. 
	 * @param cellNum the cell in question.
	 * @return the color for the cell in question.
	 */
	public abstract Color getCellColor(int cellNum);
	/**
	 * Returns the relative coordinate for the cell in question. This is the coordinate of this cell in relation to this Shape's center cell.
	 * @param cellNum the cell in question.
	 * @return the relative coordinate of the cell in question.
	 */
	public abstract Coord getRelativeCellPos(int cellNum);
	/**
	 * Works like {@link Shape#getRelativeCellPos(int)}, except in a trial temporary way. If no trial translation or trial rotation called previously, 
	 * or if {@link Shape#clearTrial()} just immediately and previously called, this method returns the same as {@link Shape#getRelativeCellPos(int)}.
	 * Otherwise, this method returns the relative temporary coordinate for the cell in question. This is the temporary coordinate of this cell
	 *  in relation to this Shape's temporary center cell. The temporary data is used for collision detection before collisions actually occur.
	 * @param cellNum the cell in question.
	 * @return the temporary relative coordinate of the cell in question.
	 */
	public abstract Coord getTrialRelativeCellPos(int cellNum);
	/**
	 * This method returns the total number of cells that make up this Shape.
	 * This method provides the link between future subclasses and the logic performed in this super class. All FOR loops,
	 * and cell operations defined in this superclass use this method to figure out the number of iterations of loops to perform.
	 * @return the number of cells that make up this shape.
	 */
	public abstract int getCellCount();
}
