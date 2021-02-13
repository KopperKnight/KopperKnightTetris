package kopper.tetris.core;
import java.awt.*;
public class StartPauseScreen 
{
	private String[] menuItems= {
			"[SPACE] or [P] or [H] Pause/Unpause Game.",			//0
			"[ESC] Exit.",											//1
			"[W] or ["+TetrisGame.ARROW_UP+"] Rotate Clockwise.",	//2
			"[A] or ["+TetrisGame.ARROW_LEFT+"] Move Left.",		//3
			"[S] or ["+TetrisGame.ARROW_DOWN+"] Move Down.",		//4
			"[D] or ["+TetrisGame.ARROW_RIGHT+"] Move Right.",		//5
			"[Q] or [NUM 0] Rotate Counter clockwise.",				//6
			"Tetris",												//7
			"Game Paused.",											//8
			"Press [ENTER] to Start!"								//9
	};
	private final int TITLE_ONE=7;
	private final int TITLE_TWO=8;
	private final int SUBTITLE_ONE=9;
	private final int ARC_SIZE=5;
	private Coord[] itemCenters=new Coord[menuItems.length];

	private Rectangle[] menuPos=new Rectangle[menuItems.length];

	private Rectangle[] menuItemBoxes=new Rectangle[menuItems.length];

	private Color textColor=Color.white;
	private Color backgroundColor=new Color(169,169,169,64);
	private Color foregroundColor=Color.black;
	private Font titleFont=new Font("Serif",Font.BOLD,48);
	private Font itemFont=new Font("Serif",Font.PLAIN,24);
	private int itemPadding=10;
	
	int titleFontHeight;
	int itemFontHeight;
	
	int itemSeperation=45;
	int titleSeperation=75;
	
	int titleRow=100;
	int subtitleRow=titleRow+titleSeperation;
	int firstRow=subtitleRow+titleSeperation;
	int secondRow=firstRow+itemSeperation;
	int thirdRow=secondRow+itemSeperation;
	int fourthRow=thirdRow+itemSeperation;
	int fifthRow=fourthRow+itemSeperation;
	int sixthRow=fifthRow+itemSeperation;
	int seventhRow=sixthRow+itemSeperation;
	private Dimension size;
	private boolean isSetup=false;
	private boolean initCanStart=false;
	
	public StartPauseScreen(Dimension size)
	{
		this.size=size;
		itemCenters[0]=new Coord(size.width/2,firstRow);
		itemCenters[1]=new Coord(size.width/2,secondRow);
		itemCenters[2]=new Coord(size.width/2,thirdRow);
		itemCenters[3]=new Coord(size.width/2,fourthRow);
		itemCenters[4]=new Coord(size.width/2,fifthRow);
		itemCenters[5]=new Coord(size.width/2,sixthRow);
		itemCenters[6]=new Coord(size.width/2,seventhRow);
		itemCenters[7]=new Coord(size.width/2,titleRow);
		itemCenters[8]=new Coord(size.width/2,titleRow);
		itemCenters[9]=new Coord(size.width/2,subtitleRow);
		for(int i=0;i<menuPos.length;i++)
		{
			menuPos[i]=new Rectangle();
			menuItemBoxes[i]=new Rectangle();
		}
		initCanStart=true;
	}
	public void drawPauseScreen(Graphics2D g2d)
	{
		drawScreen(g2d,true);
	}
	public void drawStartScreen(Graphics2D g2d)
	{
		drawScreen(g2d,false);
	}
	private void drawScreen(Graphics2D g2d, boolean isPaused)
	{
		if(isSetup)
		{
			Font temp=g2d.getFont();
			Color tempColor=g2d.getColor();
			g2d.setColor(backgroundColor);
			g2d.fillRect(0, 0,size.width , size.height);
			for(int i=0;i<menuItemBoxes.length;i++)
			{
				if(i==this.TITLE_ONE||i==this.TITLE_TWO||i==this.SUBTITLE_ONE)
				{			
					if(isPaused) //title two
					{
						if(i==this.TITLE_TWO)
						{
							g2d.setColor(foregroundColor);
							g2d.fillRoundRect(menuItemBoxes[i].x, menuItemBoxes[i].y,menuItemBoxes[i].width, menuItemBoxes[i].height,ARC_SIZE, ARC_SIZE);
							
							g2d.setColor(this.textColor);
							g2d.setFont(titleFont);
							g2d.drawString(menuItems[i], menuPos[i].x, menuPos[i].y);
						}
					}
					else //title one and subtitle one
					{
						if(i==this.TITLE_ONE||i==this.SUBTITLE_ONE)
						{
							g2d.setColor(foregroundColor);
							g2d.fillRoundRect(menuItemBoxes[i].x, menuItemBoxes[i].y,menuItemBoxes[i].width, menuItemBoxes[i].height,ARC_SIZE, ARC_SIZE);
							
							g2d.setColor(this.textColor);
							g2d.setFont(titleFont);
							g2d.drawString(menuItems[i], menuPos[i].x, menuPos[i].y);
						}
					}
					
				}
				else
				{
					g2d.setColor(foregroundColor);
					g2d.fillRoundRect(menuItemBoxes[i].x, menuItemBoxes[i].y,menuItemBoxes[i].width, menuItemBoxes[i].height,ARC_SIZE, ARC_SIZE);
					
					g2d.setColor(this.textColor);
					g2d.setFont(itemFont);
					g2d.drawString(menuItems[i], menuPos[i].x, menuPos[i].y);
				}
			}
			
			g2d.setFont(temp);
			g2d.setColor(tempColor);
		}
		else
		{
			if(initCanStart)
			{
				initRectangles(g2d);
				isSetup=true;
			}
		}
		
		
		
		
	}
	private void initRectangles(Graphics2D g2d)
	{
		FontMetrics titleM=	g2d.getFontMetrics(titleFont);
		FontMetrics itemM=g2d.getFontMetrics(itemFont);
		this.titleFontHeight=titleM.getHeight();
		this.itemFontHeight=itemM.getHeight();
		
		int tempHeight=0;
		int tempWidth=0;
		int boxWidth=0;
		int boxHeight=0;
		
		
		for(int i=0;i<menuPos.length;i++)
		{
			if(i!=this.TITLE_ONE&&i!=this.TITLE_TWO&&i!=this.SUBTITLE_ONE)
			{
				tempWidth=itemM.stringWidth(menuItems[i]);
				tempHeight=itemFontHeight;
			}
			if(i==this.TITLE_ONE||i==this.TITLE_TWO||i==this.SUBTITLE_ONE)
			{
				tempWidth=titleM.stringWidth(menuItems[i]);
				tempHeight=titleFontHeight;
			}
			menuPos[i].setBounds(itemCenters[i].getX()-tempWidth/2,itemCenters[i].getY()+tempHeight/4,tempWidth,tempHeight);
			boxWidth=menuPos[i].width+itemPadding;
			boxHeight=menuPos[i].height+itemPadding;
			menuItemBoxes[i].setBounds(itemCenters[i].getX()-boxWidth/2,itemCenters[i].getY()-boxHeight/2,boxWidth,boxHeight);
		}
	}
}
