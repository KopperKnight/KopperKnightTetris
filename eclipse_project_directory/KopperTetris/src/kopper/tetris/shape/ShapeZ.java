package kopper.tetris.shape;
import java.awt.Color;

import kopper.tetris.core.Coord;

public class ShapeZ extends Shape 
{
	Coord[] relativeCoords= {
			new Coord(0,0),
			new Coord(1,0),
			new Coord(0,-1),
			new Coord(-1,-1)
	};
	Coord[] tempCoords= Shape.cloneCoords(relativeCoords);

	public ShapeZ(Coord absolutePos)
	{
		super(absolutePos);
	}
	public ShapeZ(int x, int y)
	{
		super(x,y);
	}
	public Color getCellColor(int cellNum)
	{
		return Color.red;
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
