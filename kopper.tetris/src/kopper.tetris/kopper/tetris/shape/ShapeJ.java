package kopper.tetris.shape;
import java.awt.Color;

import kopper.tetris.core.Coord;

public class ShapeJ extends Shape 
{
	Coord[] relativeCoords= 
		{
			new Coord(-1,-1),
			new Coord(-1,0),
			new Coord(0,0),
			new Coord(1,0)
		};
	Coord[] tempCoords= Shape.cloneCoords(relativeCoords);
	 
	public ShapeJ(Coord absolutePos) 
	{
		super(absolutePos);
	}
	public ShapeJ(int x, int y)
	{
		super(x,y);
	}

	public Color getCellColor(int cellNum) 
	{
		return Color.blue;
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
