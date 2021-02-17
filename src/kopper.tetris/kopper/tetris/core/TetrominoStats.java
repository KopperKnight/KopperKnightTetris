package kopper.tetris.core;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.Font;
import kopper.tetris.shape.*;
/**
 * This class keeps track of pixel data and colors and shapes to draw the score area of the Tetris Game.
 * This class is used to keep track of the {@link java.awt.Rectangle} and {@link java.awt.FontMetrics} data for each
 * text/numerical message painted to the screen, which is vital to keep the text properly positioned and spaced.
 * @author <a href="https://github.com/kopperknight">KopperKnight</a>
 *
 */
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
	/**
	 * Constructs a TetrominoStats object.
	 * @param score A reference to the current object keeping track of the game score.
	 */
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
	/**
	 * Returns a reference to the current object's whose data is used to present the numerical score to the GUI window.
	 * @return The object used to update the score.
	 */
	public TetrisScore getTetrisScore()
	{
		return score;
	}
	/**
	 * <p>This method is named differently for various classes, but all classes that must paint 
	 * representations of their data to the window, have some variation
	 * of a method {@code drawOBJECTNAME(Graphics2D g2d);} This is that method for this class.
	 * <p>Note: TO BE REIMPLEMENTED AS AN INTERFACE DEFINITION implemented by all drawable classes in the future.
	 * 
	 * <p>This method is where the TetrominoStats represented by this object is painted to represent this object.
	 * Specifically, this object keeps track of each {@link GridCell}, Color and Shape painted in the Game's Score area of the window.
	 * 
	 * <p>
	 * This method is called via helper method {@link TetrisGame#paintGameRunning(Graphics2D, kopper.tetris.core.TetrisGame.State)}, 
	 * which in turn is called by {@link TetrisGame#paintComponent(java.awt.Graphics)}, 
	 * which in turn is an overridden method of {@link javax.swing.JPanel}'s {@link javax.swing.JComponent#paintComponent(Graphics g)}.
	 *  
	 * 
	 * 
	 * @param g2d The graphics object ultimately supplied by overridden method  {@link TetrisGame#paintComponent(java.awt.Graphics)}
	 * @param isGameOver used to make the score String colors white and able to be seen through translucent red GameOver paint.
	 */
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
	/**
	 * A quick debug variable for printing to System out for debugging. An API change planned for future version to 
	 * centralize the debugging variable across the entire module will likely remove this specific variable.
	 */
	boolean debug=true;
	
	/**
	 * A helper method to calculate the proper pixel dimensions and pixel coordinates to draw the ever changing Text / Numerical Strings to the screen properly.
	 * @param g2d The graphics object from the drawing JPanel.
	 */
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
