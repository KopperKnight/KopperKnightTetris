package kopper.tetris.core;
import java.awt.*;
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
	public static Color getRandomColor()
	{
		return colorChoices[(int)(Math.random()*10.0)];
	}
	public static GridCell[][] createUniformGrid(int x, int y, int gridWidth, int gridHeight,int unitCountX,int unitCountY)
	{
			return createUniformGrid(x,y,gridWidth,gridHeight,unitCountX,unitCountY,colorChoices[(int)(Math.random()*10.0)]);
	}
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
	public GridCell()
	{
		x=0;
		y=0;
		width=60;
		height=60;
		color=GridCell.colorChoices[(int)(Math.random()*10.0)];
	}
	public GridCell(int x, int y, int width, int height,Color color)
	{
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.color=color;
	}
	public void setColor(Color c)
	{
		this.color=c;
	}
	public Color getColor()
	{
		return this.color;
	}
	
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
