package kopper.tetris.core;
import kopper.tetris.shape.Shape;
import kopper.tetris.shape.ShapeI;
import kopper.tetris.shape.ShapeJ;
import kopper.tetris.shape.ShapeL;
import kopper.tetris.shape.ShapeO;
import kopper.tetris.shape.ShapeS;
import kopper.tetris.shape.ShapeT;
import kopper.tetris.shape.ShapeZ;

public class TetrisScore 
{
	private int countI=0;
	private int countO=0;
	private int countT=0;
	private int countJ=0;
	private int countL=0;
	private int countS=0;
	private int countZ=0;
	private int countRows=0;
	private int countArrowDowns=0;
	
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
	public void incrementRow(int rows)
	{
		countRows+=rows;
	}
	public void incrementArrowDown()
	{
		countArrowDowns++;
	}
	public int getShapeI()
	{
		return countI;
	}
	public int getShapeO()
	{
		return countO;
	}
	public int getShapeT()
	{
		return countT;
	}
	public int getShapeJ()
	{
		return countJ;
	}
	public int getShapeL()
	{
		return countL;
	}
	public int getShapeS()
	{
		return countS;
	}
	public int getShapeZ()
	{
		return countZ;
	}
	public int getRows()
	{
		return countRows;
	}
	public int getArrowDowns()
	{
		return countArrowDowns;
	}
	public int getScore()
	{
		return this.countI+this.countO+this.countT+this.countJ+this.countL+this.countZ+this.countS+this.countRows;
	}
	public String toString()
	{
		return "I="+countI+"   O="+countO+"   T="+countT+"   J="+countJ+"   L="+countL+"   S="+countS+"   Z="+countZ+"   R="+countRows+"   D="+countArrowDowns;
		
	}
}
