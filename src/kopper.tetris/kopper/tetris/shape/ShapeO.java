package kopper.tetris.shape;
import java.awt.Color;

import kopper.tetris.core.Coord;

public class ShapeO extends Shape 
{
	Coord[] relativeCoords= {
			new Coord(0,0),
			new Coord(0,-1),
			new Coord(1,-1),
			new Coord(1,0)
	};
	Coord[] tempCoords= Shape.cloneCoords(relativeCoords);

	public ShapeO(Coord absolutePos)
	{
		super(absolutePos);
	}
	public ShapeO(int x, int y)
	{
		super(x,y);
		this.rotateShapeClockwise90();
		this.rotateShapeCounterClockwise90();
		this.trialRotateShapeClockwise90();
		this.trialRotateShapeCounterClockwise90();
	}
	public void rotateShapeClockwise90(){}//do nothing, not applicable
	public void rotateShapeCounterClockwise90() {}//do nothing, not applicable
	public void trialRotateShapeClockwise90() {}//do nothing, not applicable;
	public void trialRotateShapeCounterClockwise90() {}//do nothing, not applicable.
	public Color getCellColor(int cellNum)
	{
		return Color.YELLOW;
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
