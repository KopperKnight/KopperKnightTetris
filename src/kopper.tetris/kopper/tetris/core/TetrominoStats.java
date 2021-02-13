package kopper.tetris.core;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.Font;
import kopper.tetris.shape.*;

public class TetrominoStats 
{
	GridCell[][] cells;
	Shape tetI,tetO,tetT,tetJ,tetL,tetZ,tetS;
	Shape[] shapes=new Shape[7];
	TetrisScore score;
	Rectangle[] stringPos=new Rectangle[10];
	Rectangle[] stringBoxes=new Rectangle[10];
	String[] scoreText=new String[10];
	Coord[] coords=new Coord[10];
	Font statsFont=new Font("Serif",Font.PLAIN,24);
	Font temp;
	private int fontHeight;
	
	public TetrominoStats(TetrisScore score)
	{
		int tetrominoTextY=840;
		int secondTextY=tetrominoTextY+45;
		int thirdTextY=secondTextY+45;
		cells=GridCell.createUniformGrid(0, 660, 660, 210+90, 22, 7+3, Color.DARK_GRAY);
		tetI=new ShapeI(1,2);
		tetI.rotateShapeClockwise90();
		coords[0]=new Coord(45,tetrominoTextY);
		shapes[0]=tetI;
		
		tetO=new ShapeO(3,2);
		coords[1]=new Coord(120,tetrominoTextY);
		shapes[1]=tetO;
		
		tetT=new ShapeT(7,1);
		coords[2]=new Coord(225,tetrominoTextY);
		shapes[2]=tetT;
		
		tetJ=new ShapeJ(11,2);
		tetJ.rotateShapeCounterClockwise90();
		coords[3]=new Coord(330,tetrominoTextY);
		shapes[3]=tetJ;
		
		tetL=new ShapeL(13,2);
		tetL.rotateShapeClockwise90();
		coords[4]=new Coord(420,tetrominoTextY);
		shapes[4]=tetL;
		
		tetZ=new ShapeZ(16,2);
		tetZ.rotateShapeClockwise90();
		coords[5]=new Coord(510,tetrominoTextY);
		shapes[5]=tetZ;
		
		tetS=new ShapeS(19,2);
		tetS.rotateShapeClockwise90();
		coords[6]=new Coord(600,tetrominoTextY);
		shapes[6]=tetS;
		
		coords[7]=new Coord(165,secondTextY);//Rows Eliminated
		
		coords[8]=new Coord(165+330,secondTextY);//Fast Forward Bonus
		
		coords[9]=new Coord(330,thirdTextY);//Overall Score
		
		
		this.score=score;
		for(int i=0;i<scoreText.length;i++)
		{
			stringPos[i]=new Rectangle();
			stringBoxes[i]=new Rectangle();
			scoreText[i]="";
		}
		
	}
	public TetrisScore getTetrisScore()
	{
		return score;
	}
	public void drawTetrominoStats(Graphics2D g2d,boolean isGameOver)
	{
		updateRectangles(g2d);
		
		
		if(cells!=null)
		{
			for(int r=0;r<cells.length;r++)
				for(int c=0;c<cells[r].length;c++)
					cells[r][c].drawCell(g2d, false);
		}
		if(shapes!=null)
		{
			for(int i=0;i<shapes.length;i++)
				shapes[i].drawShape(g2d, cells);
		}
		
		temp=g2d.getFont();
		g2d.setFont(statsFont);
		Color tColor=g2d.getColor();
		for(int i=0;i<stringPos.length;i++)
		{
			g2d.setColor(Color.black);
			g2d.fillRoundRect((int)stringBoxes[i].getX(),(int) stringBoxes[i].getY(), (int)stringBoxes[i].getWidth(), (int)stringBoxes[i].getHeight(),5, 5);
			//g2d.fillRoundRect((int)stringPos[i].getX(), (int)stringPos[i].getY()-fontHeight/2, (int)stringPos[i].getWidth()+10, (int)stringPos[i].getHeight()+10, 5, 5);
			if(isGameOver)
			{
				g2d.setColor(Color.white);
			}
			else
			{	
				if(i<shapes.length)
					g2d.setColor(shapes[i].getCellColor(0));
				else
					g2d.setColor(Color.white);
			}
			g2d.drawString(scoreText[i], (int)stringPos[i].getX(), (int)stringPos[i].getY());
		}
		g2d.setFont(temp);
		g2d.setColor(tColor);
			
	}
	boolean debug=true;
	private void updateRectangles(Graphics2D g2d)
	{
		FontMetrics metrics=g2d.getFontMetrics(statsFont);
		scoreText[0]=""+score.getShapeI();
		scoreText[1]=""+score.getShapeO();
		scoreText[2]=""+score.getShapeT();
		scoreText[3]=""+score.getShapeJ();
		scoreText[4]=""+score.getShapeL();
		scoreText[5]=""+score.getShapeZ();
		scoreText[6]=""+score.getShapeS();
		
		scoreText[7]="Rows Eliminated "+score.getRows();
		//scoreText[8]="Fast Forwarded "+score.getArrowDowns()+" Times! "+TetrisGame.ARROW_DOWN+TetrisGame.ARROW_UP+TetrisGame.ARROW_LEFT+TetrisGame.ARROW_RIGHT;
		scoreText[8]="Fast Forwarded "+score.getArrowDowns()+" Times!";
	
		scoreText[9]="Overall Score: "+score.getScore();
		int fH=metrics.getHeight();
		fontHeight=fH;
		int fW=0;
		int boxW,boxH;
		int padding=10;
		if(debug)
			System.out.println("FontHeight="+fontHeight);
		for(int i=0;i<stringPos.length;i++)
		{
			fW=metrics.stringWidth(scoreText[i]);
			if(debug)
			{
				System.out.println("FontWidth="+fW);
				System.out.println(coords[i]);
			}
			
			stringPos[i].setBounds(coords[i].getX()-fW/2, coords[i].getY()+fH/4, fW, fH);
			if(debug)
				System.out.println(stringPos[i]);
			boxW=((int)stringPos[i].getWidth())+padding;
			boxH=((int)(0.5*stringPos[i].getHeight()))+padding;
			stringBoxes[i].setBounds(coords[i].getX()-boxW/2,coords[i].getY()-boxH/2,boxW,boxH);
			if(debug)
			{
				System.out.println(stringBoxes[i]);
				debug=false;
			}
		}
	}

	
	
}
