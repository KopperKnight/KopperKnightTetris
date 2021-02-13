package kopper.tetris.shape;
import java.awt.Color;

import kopper.tetris.core.Coord;

public class ShapeL extends Shape 
{
	Coord[] relativeCoords= {
			new Coord(-1,0),
			new Coord(0,0),
			new Coord(1,0),
			new Coord(1,-1)
	};
	Coord[] tempCoords= Shape.cloneCoords(relativeCoords);

	public ShapeL(Coord absolutePos)
	{
		super(absolutePos);
	}
	public ShapeL(int x, int y)
	{
		super(x,y);
	}
	public Color getCellColor(int cellNum)
	{
		return Color.orange;
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
