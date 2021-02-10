package kopper.tetris.shape;

import java.awt.*;

import kopper.tetris.core.*;
public abstract class Shape 
{
	public static Coord[] cloneCoords(Coord[]coords)
	{
		Coord[] temp=new Coord[coords.length];
		for(int i=0;i<temp.length;i++)
			temp[i]=coords[i].add(0,0);
		return temp;
	}
	private Coord tempAbs;
	private Coord absPos;
	private boolean visible=true;
	public Shape(int gridx,int gridy)
	{
		this(new Coord(gridx,gridy));
	}
	public Shape(Coord absolutePos)
	{
		this.absPos=absolutePos;
		this.tempAbs=new Coord(absPos.getX(),absPos.getY());
	}
	public void setVisible(boolean v)
	{
		this.visible=v;
	}
	public boolean isVisible()
	{
		return this.visible;
	}
	
	public void drawShape(Graphics2D g2d,BackgroundGrid grid)
	{
		if(this.isVisible())
		{
			for(int i=0;i<getCellCount();i++)
			{
				grid.getCell(getAbsoluteCellPos(i).getRow(), getAbsoluteCellPos(i).getColumn()).setColor(getCellColor(i));
				grid.getCell(getAbsoluteCellPos(i).getRow(), getAbsoluteCellPos(i).getColumn()).drawCell(g2d,true);
			}
		}
		
	}
	public void drawShape(Graphics2D g2d,GridCell[][]cells)
	{
		if(this.isVisible())
		{
			for(int i=0;i<getCellCount();i++)
			{
				cells[this.getAbsoluteCellPos(i).getRow()][this.getAbsoluteCellPos(i).getColumn()].setColor(this.getCellColor(i));
				cells[this.getAbsoluteCellPos(i).getRow()][this.getAbsoluteCellPos(i).getColumn()].drawCell(g2d, true);
			}
		}
		
	}
	public void translateShapeDown()
	{
		this.translateShape(0, 1);
	}
	public void translateShape(int x,int y)
	{
		this.getAbsoluteShapePos().translate(x,y);
	}
	public void clearTrial()//reset temp variables to equal legal variables
	{
		this.getTrialAbsoluteShapePos().setX(this.getAbsoluteShapePos().getX());
		this.getTrialAbsoluteShapePos().setY(this.getAbsoluteShapePos().getY());
		for(int i=0;i<this.getCellCount();i++)
			this.getTrialRelativeCellPos(i).set(this.getRelativeCellPos(i).getX(), this.getRelativeCellPos(i).getY());
	}
	public void trialTranslateShape(int x, int y)
	{
		clearTrial();
		this.getTrialAbsoluteShapePos().translate(x,y);
	}
	public void trialRotateShapeClockwise90()
	{
		clearTrial();
		int x=0;
		int y=0;
		for(int i=0;i<this.getCellCount();i++)
		{
			x=this.getRelativeCellPos(i).getX();
			y=this.getRelativeCellPos(i).getY();
			this.getTrialRelativeCellPos(i).setX(-y);
			this.getTrialRelativeCellPos(i).setY(x);
		}	
	}
	public void trialRotateShapeCounterClockwise90()
	{
		int x=0;
		int y=0;
		for(int i=0;i<this.getCellCount();i++)
		{
			x=this.getRelativeCellPos(i).getX();
			y=this.getRelativeCellPos(i).getY();
			this.getTrialRelativeCellPos(i).setX(y);
			this.getTrialRelativeCellPos(i).setY(-x);
		}	
	}
	public void rotateShapeClockwise90()
	{
		Coord relativeCellCoord;
		int x=0;
		int y=0;
		for(int i=0;i<getCellCount();i++)
		{
			relativeCellCoord=this.getRelativeCellPos(i);
			x=relativeCellCoord.getX();
			y=relativeCellCoord.getY();
			
			relativeCellCoord.setX(-y);
			relativeCellCoord.setY(x);
		}
	}
	public void rotateShapeCounterClockwise90()
	{
		Coord relativeCellCoord;
		int x=0;
		int y=0;
		for(int i=0;i<getCellCount();i++)
		{
			relativeCellCoord=this.getRelativeCellPos(i);
			x=relativeCellCoord.getX();
			y=relativeCellCoord.getY();
			
			relativeCellCoord.setX(y);
			relativeCellCoord.setY(-x);
		}
	}
	public Coord getAbsoluteShapePos()
	{
		return this.absPos;
	}
	public Coord getTrialAbsoluteShapePos()
	{
		return this.tempAbs;
	}
	public Coord getAbsoluteCellPos(int cellNum)
	{
		return getRelativeCellPos(cellNum).add(absPos);
	}
	public Coord getTrialAbsoluteCellPos(int cellNum)
	{
		return getTrialRelativeCellPos(cellNum).add(tempAbs);
	}
	public abstract Color getCellColor(int cellNum);
	public abstract Coord getRelativeCellPos(int cellNum);
	public abstract Coord getTrialRelativeCellPos(int cellNum);
	public abstract int getCellCount();
}
