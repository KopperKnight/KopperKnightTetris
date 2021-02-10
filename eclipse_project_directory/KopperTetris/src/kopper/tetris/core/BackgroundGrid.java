package kopper.tetris.core;
import java.awt.Color;
import java.awt.Graphics2D;
import kopper.tetris.shape.*;
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
	public static Shape getNextShape(Coord coord)
	{
		return getNextShape(coord.getX(),coord.getY());
	}
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
	public int getRowCount()
	{
		return this.rows;
	}
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
