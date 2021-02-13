package kopper.tetris.shape;
import java.awt.Color;

import kopper.tetris.core.Coord;

public class ShapeT extends Shape
{
	Coord[] relativeCoords= {
			new Coord(-1,0),
			new Coord(0,0),
			new Coord(0,1),
			new Coord(1,0)
	};
	
	Coord[] tempCoords= Shape.cloneCoords(relativeCoords);
	public ShapeT(Coord absolutePos) 
	{
		super(absolutePos);
	}
	public ShapeT(int x, int y)
	{
		super(x,y);	
	}
	public Color getCellColor(int cellNum)
	{
		return Color.magenta;
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
