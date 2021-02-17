package kopper.tetris.core;
import java.awt.*;
/**
 * <p>
 * This class represents the drawing information for both game states:
 * {@link TetrisGame.State#GAME_PAUSED} and {@link TetrisGame.State#GAME_START_SCREEN}. 
 * Specifically, this class draws the rounded rectangles that represent the Text pixel boundaries of all the screen information to be printed and 
 * the translucent layer over the (paused game logic but) ever still drawing running game screen. 
 * </p>
 * <p>
 * This class is was written to keep all of the complex pixel coordinates and all of the text pixel dimensions 
 * organized and properly calculated to be presented properly to the screen for the Pause and Start screens of this game.
 * The pause and start screens differ in the title and subtitles drawn but otherwise present the same functional information about how to play
 * and navigate the game. 
 * </p>
 * 
 * <p>
 * To see how the game over screen is drawn, one must see the one method that handles that simple screen: 
 * {@link TetrisGame#paintGameOverScreen(Graphics2D)}.
 * </p>
 * @author <a href="https://github.com/kopperknight">KopperKnight</a>
 *
 */
public class StartPauseScreen 
{
	
	/**
	 * This array holds all of the text messages to be printed to the screen, which 
	 * is preferred to individual String objects to make a quick for loop for processing possible.
	 */
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
	
	/**
	 * This array holds all of the x, y coordinatesof the centers of the text messages to be printed to the screen, 
	 * which is preferred to individual Coord objects to make a quick for loop for processing possible.
	 */
	private Coord[] itemCenters=new Coord[menuItems.length];

	/**
	 * This array holds all of the x, y coordinates and width and height of the text messages to be printed to the screen, 
	 * which is preferred to individual Rectangle objects to make a quick for loop for processing possible.
	 */
	private Rectangle[] menuPos=new Rectangle[menuItems.length];

	/**
	 * This array holds all of the x, y coordinates and width and height of the text messages contrasting background boxes
	 *  to be printed to the screen, which is preferred to individual Rectangle objects to make a quick for loop for processing possible.
	 */
	private Rectangle[] menuItemBoxes=new Rectangle[menuItems.length];

	/**
	 * Represents the text color for all text drawn to this screen. It's current value is {@link java.awt.Color#white}
	 */
	private Color textColor=Color.white;
	/**
	 * Represents the color for the whole screen, which is partially translucent to be able to see the running game underneath it.
	 */
	private Color backgroundColor=new Color(169,169,169,64);
	/**
	 * Represents the color for the contrasting text box backgrounds which make the text readable. It's current value is {@link java.awt.Color#black}
	 */
	private Color foregroundColor=Color.black;
	/**
	 * The font used for the title and subtitle of the Pause and Start screens.
	 */
	private Font titleFont=new Font("Serif",Font.BOLD,48);
	/**
	 * The font used for the information text of the pause and start screens.
	 */
	private Font itemFont=new Font("Serif",Font.PLAIN,24);
	/**
	 * The amount of pixel padding betweent the edge of the text and the contrasting background text boxes.
	 */
	private int itemPadding=10;
	
	/**
	 * Stores the calculated height of text in pixels for a the title font.
	 */
	private int titleFontHeight;
	/**
	 * Stores the calculated height of the text in pixels for the font used for informational text.
	 */
	private int itemFontHeight;
	
	/**
	 * The vertical spacing in pixels between informational text.
	 */
	private int itemSeperation=45;
	/**
	 * The vertical spacing in the pixels between the title and subtitle and the subtitle and the first informational text.
	 */
	private int titleSeperation=75;
	
	/**
	 * The y location in pixels between the top of the screen and the center of the title text.
	 */
	private int titleRow=100;
	/**
	 * The y location in pixels for the subtitle text.
	 */
	private int subtitleRow=titleRow+titleSeperation;
	/**
	 * The y location in pixels for the first row of informational text.
	 */
	private int firstRow=subtitleRow+titleSeperation;
	/**
	 * The y location in pixels for the second row of informational text.
	 */
	private int secondRow=firstRow+itemSeperation;
	/**
	 * The y location in pixels for the third row of informational text.
	 */
	private int thirdRow=secondRow+itemSeperation;
	/**
	 * The y location in pixels for the fourth row of informational text.
	 */
	private int fourthRow=thirdRow+itemSeperation;
	/**
	 * The y location in pixels for the fifth row of informational text.
	 */
	private int fifthRow=fourthRow+itemSeperation;
	/**
	 * The y location in pixels for the sixth row of informational text.
	 */
	private int sixthRow=fifthRow+itemSeperation;
	/**
	 * The y location in pixels for the seventh row of informational text.
	 */
	private int seventhRow=sixthRow+itemSeperation;
	/**
	 * The internal reference to the size of the pause and start screen that the programmer supplied the constructor upon object initialization.
	 */
	private Dimension size;
	/**
	 * A boolean to make sure that helper methods and object initialization doesnt occur too early as to cause {@link java.lang.NullPointerException}
	 */
	private boolean isSetup=false;
	/**
	 * Another boolean to make sure that helper methods and object initialization doesnt occur too early as to cause {@link java.lang.NullPointerException}
	 */
	private boolean initCanStart=false;
	
	/**
	 * Initializes a new object of this class with a screen that is properly proportioned to the specified size.
	 * @param size The size of the screen.
	 */
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
	/**
	 * A Semantic method that calls a helper method {@code drawScreen(g2d,true)}, which draws the pause screen.
	 * See {@link StartPauseScreen#drawScreen(Graphics2D, boolean)} for details.
	 * @param g2d The graphics object ultimately supplied by {@link TetrisGame#paintComponent(Graphics)}.
	 */
	public void drawPauseScreen(Graphics2D g2d)
	{
		drawScreen(g2d,true);
	}
	/**
	 * A Semantic method that calls a helper method {@code drawScreen(g2d,false)}, which draws the start screen.
	 * See {@link StartPauseScreen#drawScreen(Graphics2D, boolean)} for details.
	 * @param g2d The graphics object ultimately supplied by {@link TetrisGame#paintComponent(Graphics)}.
	 */
	public void drawStartScreen(Graphics2D g2d)
	{
		drawScreen(g2d,false);
	}
	/**
	 * Draws the rounded rectangles that represent the Text pixel boundaries of all the screen information to be printed and 
	 * the translucent layer over the paused game logic but ever still drawing running game screen. 
	 * See {@link TetrisGame#paintGameRunning(Graphics2D, kopper.tetris.core.TetrisGame.State)}.
	 * @param g2d The graphics object ultimately supplied by {@link TetrisGame#paintComponent(Graphics)}.
	 * @param isPaused  If true, the pause screen is drawn, if false, the start screen is drawn.
	 */
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
	/**
	 * Initializes the rectangles that represent the Text pixel boundaries of all the screen information to be printed.
	 * @param g2d The graphics object ultimately supplied by {@link TetrisGame#paintComponent(Graphics)}.
	 */
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
