package kopper.tetris.core;

public class Coord 
{
	private int x, y;
	public Coord()
	{
		this.x=0;
		this.y=0;
	}
	public Coord(int x, int y)
	{
		this.x=x;
		this.y=y;
	}
	public void translate(int x, int y)
	{
		this.x+=x;
		this.y+=y;
	}
	public void translate(Coord c)
	{
		translate(c.getX(),c.getY());
	}
	public Coord add(Coord c)
	{
		return new Coord(this.getX()+c.getX(),this.getY()+c.getY());
	}
	public Coord add(int x, int y)
	{
		return new Coord(this.getX()+x,this.getY()+y);
	}
	public int getX()
	{
		return x;
	}
	public void setX(int x)
	{
		this.x=x;
	}
	public int getY()
	{
		return y;
	}
	public void setY(int y)
	{
		this.y=y;
	}
	public int getRow()
	{
		return this.y;
	}
	public int getColumn()
	{
		return this.x;
	}
	public void set(int x, int y)
	{
		this.setX(x);
		this.setY(y);
	}
	public String toString()
	{
		return "x="+x+" y="+y;
	}
}
