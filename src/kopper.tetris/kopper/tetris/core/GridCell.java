package kopper.tetris.core;
import java.awt.*;

/**
 * This class is designed to make the painting of the game simplified.
 * Objects of this GridCell class, are designed to represent a fixed rectangular pixel area of a Graphics pane such as a JPanel.
 * Objects of this class store the x, y pixel coordinate of the upper left corner of the rectangular pixel area, the 
 * corresponding width and height in pixels from the upper left corner to the lower left corner of the rectangular pixel area and 
 * the color of the rectangular area.
 * By abstracting the painting in this module to a bunch of rectangles, all classes of this module can paint a gridcells by its corresponding 
 * location in a 2D GridCell array. The rows of the array represent the Y coordinate and the columns of the array represent the X coordinate of this
 * simplified painting system. Therefore, all the models need not know where to draw each rectangle and thus its pixel and color data (x,y,width,height,color)
 * in the Tetris gamespace, they need only to find the (x,y) location in the grid of tetris blocks to paint. This simplifies the models and painting process
 * throughout the whole project/module.
 * @author KopperKnight
 *
 */
public class GridCell
{
	private int x, y, width, height;
	private Color color;
	private static Color[] colorChoices= {
			Color.black, 	//0
			Color.blue,		//1
			Color.cyan,		//2
			Color.red,		//3
			Color.gray,		//4
			Color.orange,	//5
			Color.yellow,	//6
			Color.pink,		//7
			Color.green,	//8
			Color.magenta	//9
			};
	
	/**
	 * Returns a random color. Each color has an equal chance of being returned.
	 * The possible colors to be returned are: Black, Blue, Cyan, Red, Gray, Orange, Yellow, Pink, Green, Magenta.
	 * @return The requested random color.
	 */
	public static Color getRandomColor()
	{
		return colorChoices[(int)(Math.random()*10.0)];
	}
	
	/**
	 * A convenience method which returns a 2D array that represents a giant rectangular area of pixels with a 2D grid of
	 * component rectangular pixel areas. Each component rectangular pixel area is represented by a GridCell object that has a color 
	 * randomly determend by a call to {@link GridCell#getRandomColor()}.
	 * 
	 * @param x The upper left corner's X component pixel location of the giant rectangular area.
	 * @param y The upper left corner's Y component pixel location of the giant rectangular area.
	 * @param gridWidth The lower right corner's X component location if the upper left corner is considered (0,0).
	 * @param gridHeight The lower right corner's X component location if the upper left corner is considered (0,0).
	 * @param unitCountX the number of columns of small rectangular pixel areas (represented by columns of GridCells of equal width) to be inside of this giant rectangular pixel area.
	 * @param unitCountY the number of rows of small rectangular pixel areas (represented by rows of GridCells of equal height) to be inside of this giant rectangular pixel area.
	 * @return A 2D array of GridCell's where the coordinates are equal to the following pseudocode: {@code GridCell[][] cells[rows][columns]=cells[y][x]}. 
	 */
	public static GridCell[][] createUniformGrid(int x, int y, int gridWidth, int gridHeight,int unitCountX,int unitCountY)
	{
			return createUniformGrid(x,y,gridWidth,gridHeight,unitCountX,unitCountY,colorChoices[(int)(Math.random()*10.0)]);
	}
	/**
	 * A convenience method which returns a 2D array that represents a giant rectangular area of pixels with a 2D grid of
	 * component rectangular pixel areas. Each component rectangular pixel area is represented by a GridCell object.
	 * 
	 * @param x The upper left corner's X component pixel location of the giant rectangular area.
	 * @param y The upper left corner's Y component pixel location of the giant rectangular area.
	 * @param gridWidth The lower right corner's X component location if the upper left corner is considered (0,0).
	 * @param gridHeight The lower right corner's X component location if the upper left corner is considered (0,0).
	 * @param columnCount the number of columns of small rectangular pixel areas (represented by columns of GridCells of equal width) to be inside of this giant rectangular pixel area.
	 * @param rowCount the number of rows of small rectangular pixel areas (represented by rows of GridCells of equal height) to be inside of this giant rectangular pixel area.
	 * @param color The color that all member GridCell objects of the returned 2D GridCell array will be initialized to represent.
	 * @return A 2D array of GridCell's where the coordinates are equal to the following pseudocode: {@code GridCell[][] cells[rows][columns]=cells[y][x]}. 
	 */
	public static GridCell[][] createUniformGrid(int x, int y, int gridWidth, int gridHeight,int columnCount,int rowCount,Color color)
	{
		int unitWidth=gridWidth/columnCount;
		int unitHeight=gridHeight/rowCount;
		
		GridCell[][] grid=new GridCell[rowCount][];
		for(int r=0;r<grid.length;r++)
		{
			grid[r]=new GridCell[columnCount];
			for(int c=0;c<grid[r].length;c++)
				grid[r][c]=new GridCell(x+unitWidth*c,y+unitHeight*r,unitWidth,unitHeight,color);//solved
		}
		return grid;
	}
	/**
	 * Constructs a GridCell object, which represents a rectangular area on a JPanel.
	 * The rectangular area has default location with an origin of (0,0) (the upper left corner).
	 * The rectangular area has a default width and height of 60 pixels as measured from the upper left corner.
	 * This object's default color will be randomly chosen from one of the following: {@link java.awt.Color}'s:
	 * Black, Blue, Cyan, Red, Gray, Orange, Yellow, Pink, Green, Magenta.
	 */
	public GridCell()
	{
		x=0;
		y=0;
		width=60;
		height=60;
		color=GridCell.colorChoices[(int)(Math.random()*10.0)];
	}
	/**
	 *  Constructs a GridCell object, which represents a rectangular area on a JPanel.
	 * @param x the upper left corner X component of the rectangular area.
	 * @param y the upper left corner Y component of the rectangular area.
	 * @param width the width of the rectangular area measured from the supplied X location.
	 * @param height the height of the rectangular area measured from the supplied Y location.
	 * @param color Sets the color that will be set to be painted by this clas's {@link GridCell#drawCell(Graphics2D, boolean)} method, when it is called.
	 */
	public GridCell(int x, int y, int width, int height,Color color)
	{
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.color=color;
	}
	/**
	 * Sets the color that will be set to be painted by this class's {@link GridCell#drawCell(Graphics2D, boolean)} method.
	 * @param c The color that this GridCell should hold.
	 */
	public void setColor(Color c)
	{
		this.color=c;
	}
	/**
	 * Returns the color that will be set to be painted by this class's {@link GridCell#drawCell(Graphics2D, boolean)} method.
	 * @return The color stored inside this object.
	 */
	public Color getColor()
	{
		return this.color;
	}
	/**
	 * <p>This method is named differently for various classes, but all classes that must paint 
	 * representations of their data to the window, have some variation
	 * of a method {@code drawOBJECTNAME(Graphics2D g2d);} This is that method for this class.
	 * 
	 * <p>Note: TO BE REIMPLEMENTED AS AN INTERFACE DEFINITION implemented by all drawable classes in the future.
	 * 
	 * <p>This method is where the GridCell data represented by this object is painted to represent this object.
	 * Specifically, this object keeps track of each gridcell's color and the pixels it occupies so that a filled rectangle can be drawn to screen.
	 * 
	 *  <p>This method is called inside of {@link BackgroundGrid#drawBackgroundGrid(Graphics2D)} or 
	 * inside of {@link kopper.tetris.shape.Shape#drawShape(Graphics2D, BackgroundGrid)} or 
	 * inside of {@link kopper.tetris.shape.Shape#drawShape(Graphics2D, GridCell[][])} , 
	 * which in turn is called by each respective caller methods (see their method documentation).
	 * 
	 *  * See {@link BackgroundGrid#drawBackgroundGrid(Graphics2D)}
	 * 
	 * @param g2d The graphics object ultimately supplied by overridden method  {@link TetrisGame#paintComponent(java.awt.Graphics)}
	 * @param drawoutline True if draw outline, False if outline not needed to be drawn. Background GridCells do not draw borders, they draw solid colors.
	
	 */
	public void drawCell(Graphics2D g2d,boolean drawoutline)// draw a rectangle a certain color
	{
		//Color[] fiveColors=new Color[5];
		if(drawoutline)
		{
			g2d.setColor(this.color);
			g2d.fillRoundRect(x, y, width, height, 5, 5);
			g2d.setColor(Color.lightGray);
			g2d.drawRoundRect(x, y, width, height, 5, 5);
		}
		else
		{
			g2d.setColor(this.color);
			g2d.fillRect(x, y, width, height);
		}
	}
		
	
}
