package kopper.tetris.shape;
import java.awt.Color;

import kopper.tetris.core.Coord;

public class ShapeS extends Shape 
{
	Coord[] relativeCoords= {
			new Coord(0,0),
			new Coord(-1,0),
			new Coord(0,-1),
			new Coord(1,-1)
	};
	Coord[] tempCoords= Shape.cloneCoords(relativeCoords);

	public ShapeS(Coord absolutePos)
	{
		super(absolutePos);
	}
	public ShapeS(int x, int y)
	{
		super(x,y);
	}
	public Color getCellColor(int cellNum)
	{
		return Color.green;
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
