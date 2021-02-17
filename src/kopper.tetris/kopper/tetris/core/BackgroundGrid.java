package kopper.tetris.core;
import java.awt.Color;

import java.awt.Graphics2D;

import kopper.tetris.shape.*;

/**
 * 
 *
 *
 * 
 *<p> 	A class that defines the background area of the Tetris game. All pixels of the Tetris game area reside within a
 *{@link GridCell}. This class paints the entire area of the Tetris game. The {@link Shape} class then paints over the background
 * its information according to the {@link Coord} objects inside the {@link Shape} model. When a shape is consumed by an object of this class using 
 * {@link BackgroundGrid#consumeShape(Shape)}, that object's data for cell color and cell location are inputed into this objects internal model representation
 * of the background of the Tetris game. Therefore, this class draws the background of the Tetris game and occupied cells left over by "dead" colored cells
 * of consumed "dead" Shapes. Programmatically, the shape object consumed by {@link BackgroundGrid#consumeShape(Shape)} is dereferenced and collected by the 
 * Garbage Collector, while conceptually it and its dead cells stitched into this BackgroundGrid's internal model will be refered to as a "Dead Shape".
 * </p>
 * @author <a href="https://github.com/kopperknight">KopperKnight</a>
 */
public class BackgroundGrid 
{
	
	private Color[][]internalStructure;
	private boolean[][]isOccupied;
	private Color backgroundColor;
	private int rows, columns;
	private GridCell[][]cells;
	private int[] rowRemovalIndices;
	private int rowRemovalIndicesCount=0;
	private GridCell imaginaryCell=new GridCell()
	{
		public void drawCell(Graphics2D g2d,boolean drawOutline)
		{
			//do nothing on purpose. off screen location.
		}
	};
	/**
	 * Returns a Shape of one of equal chance of being one of the following six subclass of {@link Shape}: {@link ShapeI}, {@link ShapeJ}, {@link ShapeL},
	 * {@link ShapeO}, {@link ShapeS}, {@link ShapeT}, {@link ShapeZ}. This is a convenience method to be called upon when the game wants to spawn a new Shape.
	 * 
	 * @param coord the absolute location of the returned shape should be located.
	 * @return A new Shape with location provided in parameters and an instance of one of the six Shape subclasses.
	 * 
	 */
	public static Shape getNextShape(Coord coord)
	{
		return getNextShape(coord.getX(),coord.getY());
	}
	/**
	 *  
	 * Returns a Shape of one of equal chance of being one of the following six subclass of {@link Shape}: {@link ShapeI}, {@link ShapeJ}, {@link ShapeL},
	 * {@link ShapeO}, {@link ShapeS}, {@link ShapeT}, {@link ShapeZ}. This is a convenience method to be called upon when the game wants to spawn a new Shape. 
	 * This method is an overloaded version of {@link BackgroundGrid#getNextShape(Coord)} and simply supplies the integer parameters to a new {@link Coord} object and calls that method.
	 * 
	 * @param x The y component of the coordinate where the requested shape should be located.
	 * @param y The y component of the coordinate where the requested shape should be located.
	 * @return A new Shape with location provided in parameters and an instance of one of the six Shape subclasses.
	 * 
	 */
	public static Shape getNextShape(int x, int y)
	{
		int i=(int)(Math.random()*7.0);
		switch(i)
		{
			case 0:return new ShapeI(x,y);
			case 1:return new ShapeJ(x,y);
			case 2:return new ShapeL(x,y);
			case 3:return new ShapeO(x,y);
			case 4:return new ShapeS(x,y);
			case 5:return new ShapeT(x,y);
			default:return new ShapeZ(x,y);
		}
	}
	/**
	 *  Constructs a new Background Grid object.
	 * @param x the Xth pixel location of the upper left corner of the pixel area represented by this object. (x,y)=(0,0) represents the upper left most corner of GUI.
	 * @param y the Yth pixel location of the upper left corner of the pixel area represented by this object. (x,y=(0,0) represents the upper left most corner of the GUI.
	 * @param gridWidth the number of pixels wide the rectangular area represented by this object is.
	 * @param gridHeight the number of pixels tall the rectangular area represented by this object is.
	 * @param columns the number of columns of tetris blocks to be represented in this object. The {@link GridCell} created by this objects internal model will have the
	 * following property: {@code cellwidth=(gridWidth-x)/columns;}
	 * @param rows the number of rows of tetris blocks to be represented in this object. The {@link GridCell} created by this objects internal model will have the
	 * following property: {@code cellheight=(gridHeight-y)/rows;}
	 */
	public BackgroundGrid(int x,int y, int gridWidth, int gridHeight,int columns, int rows)
	{
		this.rows=rows;
		this.columns=columns;
		backgroundColor=Color.black;
		cells=GridCell.createUniformGrid(x, y, gridWidth, gridHeight, columns, rows,backgroundColor);
		rowRemovalIndices=new int[this.rows];
		internalStructure=new Color[this.rows][];
		isOccupied=new boolean[this.rows][];
		for(int r=0;r<internalStructure.length;r++)
		{
			internalStructure[r]=new Color[columns];
			isOccupied[r]=new boolean[columns];
			for(int c=0;c<columns;c++)
			{
				internalStructure[r][c]=backgroundColor;
				isOccupied[r][c]=false;
			}
		}
	}
	/**
	 * Returns the cell represented by the cell coordinate provided. If the coordinate is outside the bounds of the paintable area, 
	 * a dumby cell, representing all out of bounds cells with an overridden  {@link GridCell#drawCell(Graphics2D, boolean)} and functionless method, is returned.
	 * 
	 * @param ro The row of the desired cell's location in the grid of this class's internal grid model of {@link GridCell} objects to retrieve
	 * @param col The column of the desired cell's location in the grid of this class's internal grid model of {@link GridCell} objects to retrieve
	 * @return the cell at the row, col location or in (col,row) if in (x,y) notation.
	 */
	public GridCell getCell(int ro,int col)
	{
		if(col>=0&&col<columns&&ro>=0&&ro<rows)
		{
			return cells[ro][col];
		}
		else
		{
			return imaginaryCell;
		}
		
	}
	/**
	 * Gets the number of rows present in this model's internal grid data.
	 * @return the number of rows in this object.
	 */
	public int getRowCount()
	{
		return this.rows;
	}
	/**
	 * Gets the number of columns present in this model's internal grid data.
	 * @return the number of columns in this object.
	 */
	public int getColumnCount()
	{
		return this.columns;
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
	 * which in turn is an overridden method of{@link javax.swing.JPanel}'s {@link javax.swing.JComponent#paintComponents(java.awt.Graphics) }.
	 * @param g2d The graphics object ultimately supplied by overridden method  {@link TetrisGame#paintComponent(java.awt.Graphics)}
	 */
	public void drawBackgroundGrid(Graphics2D g2d)
	{
		for(int r=0;r<cells.length;r++)
			for(int c=0;c<cells[r].length;c++)
			{	cells[r][c].setColor(backgroundColor);
				cells[r][c].drawCell(g2d, false); 
				cells[r][c].setColor(internalStructure[r][c]);
				cells[r][c].drawCell(g2d,isOccupied[r][c]);
			}
	}
	/**
	 * Takes the individual colors and cell coordinates of the supplied {@link Shape} object copies those colors and coordinates into the internal grid model of this objects
	 * grid of cells. The shape is now painted as if it were the background represented by this object. Conceptually, this shape will be referred to as a dead shape. 
	 * However, programmatically, the shape object is collected by the Garbage Collector and the reference to it is assigned a new Shape in the code for {@link TetrisGame}.
	 * @param s The shape to make part of the background.
	 */
	public void consumeShape(Shape s)
	{
		int row=0;
		int column=0;
		for(int i=0;i<s.getCellCount();i++)
		{
			row=s.getAbsoluteCellPos(i).getRow();
			column=s.getAbsoluteCellPos(i).getColumn();
			
			if(row>=0&&row<this.rows&&column>=0&&column<this.columns)
			{
				internalStructure[row][column]=s.getCellColor(i);
				isOccupied[row][column]=true;
			}
			else
			{
				System.out.println("Consuming a shape outside of grid. Nothing added to Grid.");// END OF GAME SITUATIOn
			}
			
		}
		
	}
	/**
	 * This method calls the supplied Shape's method {@link Shape#trialTranslateShape(int, int)} and compares the returned {@link Coord}
	 * object data to this object's internal model (a 2D boolean array) to determine if the the coordinates are supplied by a trial translation
	 * are already occupied. 
	 * 
	 * @param s The shape being tested for collision with occupied cells.
	 * @param x The translation vector x component. Typically of magnitude one with + or - determining direction.
	 * @param y The translation vector y component. Typically of magnitude one with + or - determining direction.
	 * @return True if none of the trial coordinates collide with occupied cells and False if otherwise, as defined by the private data of this {@link BackgroundGrid} object.
	 */
	public boolean canTranslate(Shape s,int x, int y)
	{
		s.trialTranslateShape(x, y);
		Coord temp;
		for(int i=0;i<s.getCellCount();i++)
		{
			temp=s.getTrialAbsoluteCellPos(i);
			if(isOffLimits(temp.getRow(),temp.getColumn()))
				return false;
		}
		return true;
	}
	/**
	 * A convenience method which calls {@link BackgroundGrid#canTranslate(Shape, int, int)} with a downward vector of (x,y)=(0,1).
	 * @param s The shape being tested for collision with occupied cells.
	 * @return True if none of the trial coordinates collide with occupied cells and False if otherwise, as defined by the private data of this {@link BackgroundGrid} object.
	 */
	public boolean canTranslateDown(Shape s)
	{
		return canTranslate(s,0,1);
	}
	/**
	 * This method calls the supplied Shape's method {@link Shape#trialRotateShapeClockwise90() } and compares the returned {@link Coord}
	 * object data to this object's internal model (a 2D boolean array) to determine if the the coordinates are supplied by a trial translation
	 * are already occupied. 
	 * 
	 * @param s The shape being tested for collision with occupied cells.
	 * @return True if none of the trial coordinates collide with occupied cells and False if otherwise, as defined by the private data of this {@link BackgroundGrid} object.
	 */
	public boolean canRotateCW90(Shape s)
	{
		s.trialRotateShapeClockwise90();
		Coord temp;
		for(int i=0;i<s.getCellCount();i++)
		{
			temp=s.getTrialAbsoluteCellPos(i);
			if(isOffLimits(temp.getRow(),temp.getColumn()))
				return false;
		}
		return true;
	}
	/**
	 * 
	 * This method calls the supplied Shape's method {@link Shape#trialRotateShapeCounterClockwise90() } and compares the returned {@link Coord}
	 * object data to this object's internal model (a 2D boolean array) to determine if the the coordinates are supplied by a trial translation
	 * are already occupied. 
	 * 
	 * @param s The shape being tested for collision with occupied cells.
	 * @return True if none of the trial coordinates collide with occupied cells and False if otherwise, as defined by the private data of this {@link BackgroundGrid} object.
	 */
	public boolean canRotateCCW90(Shape s)
	{
		s.trialRotateShapeCounterClockwise90();
		Coord temp;
		for (int i=0;i<s.getCellCount();i++)
		{
			temp=s.getTrialAbsoluteCellPos(i);
			if(isOffLimits(temp.getRow(),temp.getColumn()))
				return false;
		}
		return true;
	}
	/**
	 * This method determines of a cell is off limits to a live {@link Shape}. When the row and column supplied are with in the bounds of the
	 * drawable background, it returns the value of the internal boolean 2D array, which represents whether a dead cell occupies a location or not. 
	 * Otherwise, when the row and column supplied are not within the drawable background, then the location is considered off limits in all cases
	 *  EXCEPT when the {@code row<0}, which is the top of the screen (and where shapes spawn in the game off screen). This is the method used to keep shapes
	 *  from going out of bounds left or right or falling down through the bottom of the game, when no dead shapes are on the bottom to stop a Shape.
	 * @param r The row in question (also known as the Y coordinate in Cartesian setup).
	 * @param c The column in question (also known as the X coordinate in Cartesian setup).
	 * @return returns True if location is occupied by a dead Shape, is off screen to the LEFT or RIGHT or BOTTOM of the game. All locations that
	 * have no dead shapes or are ABOVE the game off screen return false.
	 */
	public boolean isOffLimits(int r,int c)
	{
		if(c>=0&&c<columns&&r>=0&&r<rows)
			return isOccupied[r][c];
		else
			return c<0||c>=columns||r>=rows;//ceiling is unoccupied to infinity;
	}
	/**
	 * A Convenience method which calls {@link BackgroundGrid#canTranslateDown(Shape) } and negates it.
	 * In other words, a Shape is considered dead if it cannot move downwards anymore.
	 * @param s The shape being tested as dead or not.
	 * @return True if shape is dead and cannot move down; False if otherwise; Is the opposite value returned by {@link BackgroundGrid#canTranslateDown(Shape)}.
	 */
	public boolean isShapeDead(Shape s)
	{
		return !canTranslateDown(s);
	}
	/**
	 * Detects any horizontal rows that are completely full of dead Shape's and removes them. 
	 * This method calls and combines {@link BackgroundGrid#detectFullRows()} and {@link BackgroundGrid#removeDetectedRows()} into this one method.
	 * The same exact results can be achieved by calling these two methods in the order stated.
	 */
	public void detectFullRowsAndDelete()
	{
		detectFullRows();
		removeDetectedRows();
	}
	/**
	 * This method searches all the rows for rows that are completely full of dead Shape's. The resulting indices are stored privately in this BackgroundGrid
	 * Object. 
	 * @return Returns the total number of rows counted to be full.
	 */
	public int detectFullRows()
	{
		this.rowRemovalIndicesCount=0;
		for(int r=0;r<this.rows;r++)//must search and add indices in lowest (first) to highest (last) order for removal to properly.
		{
			if(isRowFull(r))
			{
				this.rowRemovalIndices[this.rowRemovalIndicesCount]=r;
				this.rowRemovalIndicesCount++;
			}
		}
		return this.rowRemovalIndicesCount;
	}
	/**
	 * Returns the total number of rows counted to be full the last time {@link BackgroundGrid#detectFullRows()} was called. If it wasn't ever called,
	 * or if rows detected previously have already been deleted by calling {@link BackgroundGrid#removeDetectedRows() }, then it returns Zero.
	 * @return total number of rows counted to be full. Zero if never called before, or cleared previously.
	 */
	public int getDetectFullRowCount()
	{
		return this.rowRemovalIndicesCount;
	}
	/**
	 * Removes all rows previously detected by the call {@link BackgroundGrid#detectFullRows()}. If none were detected, then the method will do nothing as 
	 * it operates on a for loop and the initial index value of for loop is 0 and the test {@code 0<0} will fail before method's executes.
	 * This method will clear the number of rows detected to be full back to zero. Another call to {@link BackgroundGrid#detectFullRows() } must be called 
	 * again or else the number of full rows internally stored will remain at zero. This method removes rows in order of lowest numbered row (top of screen)
	 * first to the highest number row (bottom of the screen) last. This method calls {@link BackgroundGrid#removeRow(int) } in a for loop. Do not override that
	 * method without considering this method also.
	 */
	public void removeDetectedRows()
	{
		for(int i=0;i<this.rowRemovalIndicesCount;i++)
		{
			removeRow(this.rowRemovalIndices[i]);
		}
		//this.rowRemovalIndicesCount=0;
	}
	/**
	 * This method is called in a for loop by {@link BackgroundGrid#removeDetectedRows() }. This method removes the specified row
	 * All lower numbered rows less than the specified row number (higher on the screen) are shifted to one row higher numbered row (moved down on screen by one row).
	 * This method reduces the count of detected full rows by one, REGARDLESS of if the row specified is FULL or NOT. 
	 * If the row removed is not full, a call to {@link BackgroundGrid#detectFullRows()} should be called again to refresh the row count to the correct amount,
	 * otherwise a glitch can occur as the full row count would not be accurate in this situation anymore.
	 * If the number of detected rows is already zero, this method does not reduce the count of detected full rows any lower than zero.
	 * @param row The specified row to be deleted, can be full or not or empty.
	 * 
	 * 
	 */
	public void removeRow(int row)
	{
		int deletedRow=row;
		
		for(int r=deletedRow;r>0;r--)
		{
			for(int c=0;c<isOccupied[r].length;c++)
			{
				isOccupied[r][c]=isOccupied[r-1][c];
				internalStructure[r][c]=internalStructure[r-1][c];
				
				if(r==1)
				{
					isOccupied[0][c]=false;
					internalStructure[0][c]=backgroundColor;
				}
			}
		}
		this.rowRemovalIndicesCount--;
		
		if(this.rowRemovalIndicesCount<0)
		{
			this.rowRemovalIndicesCount=0;
		}
	}
	
	/**
	 * Determines if the number of dead shape cells in a row matches the number of row cells.
	 * This method is required by {@link BackgroundGrid#detectFullRows()} for it to function properly.
	 * @param row The row in question.
	 * @return True if the row is fully occupied by dead shape's cells, False otherwise.
	 */
	public boolean isRowFull(int row)
	{
		if(isOccupied.length>0)
		{
			for(int col=0;col<isOccupied[0].length;col++)
			{
				if(!isOccupied[row][col])
					return false;
			}
			return true;
		}
		else
			return false;
		
	}
	/**
	 * Determines if a shape is completely in the drawable area. If even one cell of a given Shape is not in the drawable area,
	 * then it returns true.
	 * This method is used to test one of the GameOver conditions and is used in the methods {@link TetrisGame#performOneGameTick()}
	 *  and {@link TetrisGame#keyPressed(java.awt.event.KeyEvent)}.
	 * @param s The shape in question.
	 * @return True if even one cell of a given shape is outside the drawable area. False otherwise.
	 */
	public boolean isShapeOutBounds(Shape s)
	{
		int row=0;
		int column=0;
		for(int i=0;i<s.getCellCount();i++)
		{
			row=s.getAbsoluteCellPos(i).getRow();
			column=s.getAbsoluteCellPos(i).getColumn();
			
			if(!(row>=0&&row<this.rows&&column>=0&&column<this.columns))
				return true;
		}
		return false;
	}
}
