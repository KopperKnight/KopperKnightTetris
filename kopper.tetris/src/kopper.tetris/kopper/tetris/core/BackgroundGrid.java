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
 * of the background of the Tetris game. Therefore, this class draws the background of the Tetris game and occupied cells left over by dead consumed Shapes.
 * </p>
 *  @author KopperKnight
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
	 * grid of cells. The shape is now painted as if it were the background represented by this object.
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
	public boolean canTranslateDown(Shape s)
	{
		return canTranslate(s,0,1);
	}
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
	public boolean isOffLimits(int r,int c)
	{
		if(c>=0&&c<columns&&r>=0&&r<rows)
			return isOccupied[r][c];
		else
			return c<0||c>=columns||r>=rows;//ceiling is unoccupied to infinity;
	}
	public boolean isShapeDead(Shape s)
	{
		return !canTranslateDown(s);
	}
	public void detectFullRowsAndDelete()
	{
		detectFullRows();
		removeDetectedRows();
	}
	public int detectFullRows()
	{
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
	public int getDetectFullRowCount()
	{
		return this.rowRemovalIndicesCount;
	}
	public void removeDetectedRows()
	{
		for(int i=0;i<this.rowRemovalIndicesCount;i++)
		{
			removeRow(this.rowRemovalIndices[i]);
		}
		this.rowRemovalIndicesCount=0;
	}
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
	}
	
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
