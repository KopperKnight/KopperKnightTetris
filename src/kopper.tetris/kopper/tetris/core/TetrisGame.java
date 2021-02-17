package kopper.tetris.core;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import kopper.tetris.shape.Shape;

/**
 * 
 * 
 * <p>This class combines all of the data models and drawable items (Classes with {@code drawOBJECTNAME(Graphics2D g2d);} to perform all
 * the major functions a Tetris Game would be expected to provide on an overall level. The Data models (referring to all the objects in the module)
 * are all drawn to this subclass of JPanel. 
 * </p>
 * <p>
 * In Figure 1 below, the diagram color codes what the running game screen of this TetrisGame looks like and what classes the internal objects of this
 * TetrisGame responsible for each color coded subcomponent in the diagram are instantiated from. The object instantiated from the class
 * {@link kopper.tetris.core.StartPauseScreen} has dotted outline to show that, when it is drawn, it is drawn over the entire area of the TetrisGame
 * as a translucent layer. 
 * </p>
 * <img src="doc-files/tetrisgame.png" alt="TetrisGame Object Make up and Delegation">
 * 
 * <p>
 * One need only to add this TetrisGame object to a JFrame and wireup the {@link javax.swing.JFrame}'s KeyListeners,FocusListeners to this 
 * class to make a self contained complete application that is a remake of Tetris!
 *</p>
 *
 *<p>In the Model Viewer Controller (MVC) software architecture, this class combines the model components:
 * mostly represented by {@link BackgroundGrid}, {@link Shape} (and its subclasses), with the viewer components: {@link TetrisGame},
 * which is a subclass of {@link JPanel} and heavily relies on {@link TetrisGame#paintComponent(Graphics)}, and {@code drawOBJECTNAME(Graphics2D g2d)} 
 * methods and the controller components: {@link java.awt.event.KeyListener} implemented in TetrisGame as methods.
 * </p>
 * @author <a href="https://github.com/kopperknight">KopperKnight</a>
 */
public class TetrisGame extends JPanel implements KeyListener,FocusListener
{
	

	/**
	 * The unicode character for the arrow left key on the board.
	 */
	public static char ARROW_LEFT='\u2190';
	/**
	 * The unicode character for the arrow up key on the board.
	 */
	public static char ARROW_UP='\u2191';
	/**
	 * The unicode character for the arrow right key on the board.
	 */
	public static char ARROW_RIGHT='\u2192';
	/**
	 * The unicode character for the arrow down key on the board.
	 */
	public static char ARROW_DOWN='\u2193';

	
	/**
	 * A Class which represents the state of a TetrisGame. 
	 * 
	 * Each of the FOUR states represents a different rendering flow inside of the {@link JPanel}'s overridden method  {@link TetrisGame#paintComponent(Graphics)}.
	 * 
	 * <p>Note: In future releases I hope to potentially add a FIFTH state, probably titled GAME_ANIMATION, to accommodate some attractive animations during
	 * the row deletion process of the Tetris Game. Also, looking into changing this class to an Enum declaration in future releases.
	 * @author <a href="https://github.com/kopperknight">KopperKnight</a>
	 *
	 */
	public static class State
	{
		/**
		 * <p>This is the constant representing the game when it is first started up.
		 * During this rendering flow. The following methods are called in order:
		 *  </p>
		 * <ol>
		 * 	<li> {@link TetrisGame#performOneAnimationTick()} The internal {@link javax.swing.Timer} object triggers this method 25 times per second. </li>
		 *  <li> {@link TetrisGame#repaint()} This is called 25 times per second.</li>
		 *  <li> {@link TetrisGame#paintComponent(Graphics)} is called when ever {@link TetrisGame#repaint()} is called.</li>
		 * 	
		 * 	<li> {@link TetrisGame#paintGameRunning(Graphics2D, State)}. This is called whenever {@link TetrisGame#paintComponent(Graphics)} is called.
		 * This method paints the running game data represented mostly by {@link BackgroundGrid} is always painted on the bottom.</li>
		 * 	<li> {@link TetrisGame#paintGameStartScreen(Graphics2D)}. This is always called whenever {@link TetrisGame#paintComponent(Graphics)} is called 
		 * AND during this State {@code GAME_START_SCREEN=0;}. 
		 * The start screen is painted over the game with a translucent color so that one can see the game screen behind the start screen.</li>
		 *</ol>
		 * 
		 * 
		 */
		public static final int GAME_START_SCREEN=0;
		
		/**
		 * <p>This is the constant representing the game when it running and being played. 
		 * During this rendering flow. The following methods are called in order:
		 * <ol>
		 * 	<li> {@link TetrisGame#performOneAnimationTick()} The internal {@link javax.swing.Timer} object triggers this method 25 times per second. </li>
		 *  <li> {@link TetrisGame#repaint()} This is called 25 times per second.</li>
		 *  <li> {@link TetrisGame#paintComponent(Graphics)} is called when ever {@link TetrisGame#repaint()} is called.</li>
		 * 	
		 * 	<li> {@link TetrisGame#paintGameRunning(Graphics2D, State)}. This is called whenever {@link TetrisGame#paintComponent(Graphics)} is called.
		 * This method paints the running game data represented mostly by {@link BackgroundGrid} is always painted on the bottom.</li>
		 * 	<li> {@link TetrisGame#performOneAnimationTick() }. The internals of this method always execute once for every {@link TetrisGame#TICKS_BETWEEN_GRAVITY } times
		 * that {@link TetrisGame#performOneAnimationTick() } is called
		 * AND during this State {@code GAME_RUNNING=1;}. </li>
		 *</ol>
		 * 
		 * 
		 */
		public static final int GAME_RUNNING=1;
		/**
		 * <p>This is the constant representing the game when it is paused. 
		 * During this rendering flow. The following methods are called in order:
		 * <ol>
		 * 	<li> {@link TetrisGame#performOneAnimationTick()} The internal {@link javax.swing.Timer} object triggers this method 25 times per second. </li>
		 *  <li> {@link TetrisGame#repaint()} This is called 25 times per second.</li>
		 *  <li> {@link TetrisGame#paintComponent(Graphics)} is called when ever {@link TetrisGame#repaint()} is called.</li>
		 * 	
		 * 	<li> {@link TetrisGame#paintGameRunning(Graphics2D, State)}. This is called whenever {@link TetrisGame#paintComponent(Graphics)} is called.
		 * This method paints the running game data represented mostly by {@link BackgroundGrid} is always painted on the bottom.</li>
		 * 	<li> {@link TetrisGame#paintGamePauseScreen(Graphics2D)}. This is always called whenever {@link TetrisGame#paintComponent(Graphics)} is called 
		 * AND during this State {@code GAME_PAUSED=2;}. 
		 * The pause screen is painted over the game with a translucent color so that one can see the game screen behind the start screen.</li>
		 *</ol>
		 * 
		 * 
		 */
		public static final int GAME_PAUSED=2;
		/**
		 * <p>This is the constant representing the game when it is over and the user has lost. 
		 * During this rendering flow. The following methods are called in order:
		 * <ol>
		 * 	<li> {@link TetrisGame#performOneAnimationTick()} The internal {@link javax.swing.Timer} object triggers this method 25 times per second. </li>
		 *  <li> {@link TetrisGame#repaint()} This is called 25 times per second.</li>
		 *  <li> {@link TetrisGame#paintComponent(Graphics)} is called when ever {@link TetrisGame#repaint()} is called.</li>
		 * 	
		 * 	<li> {@link TetrisGame#paintGameRunning(Graphics2D, State)}. This is called whenever {@link TetrisGame#paintComponent(Graphics)} is called.
		 * This method paints the running game data represented mostly by {@link BackgroundGrid} is always painted on the bottom.</li>
		 * 	<li> {@link TetrisGame#paintGameOverScreen(Graphics2D) }. This is always called whenever {@link TetrisGame#paintComponent(Graphics)} is called 
		 * AND during this State {@code GAME_OVER=3;}. 
		 * The game over screen is painted over the game with a translucent color so that one can see the game screen behind the start screen.</li>
		 *</ol>
		 * 
		 * 
		 */
		public static final int GAME_OVER=3;
		/**
		 * The variable storing the current state of this object.
		 */
		private int currentState=0;
		/**
		 * Create a new State object.
		 */
		public State()//default state is Game START Screen
		{
			
		}
		/**
		 * Sets the current state of this object to be {@link TetrisGame.State#GAME_START_SCREEN}
		 */
		public void setGameStartScreen()
		{
			currentState=GAME_START_SCREEN;
		}
		/**
		 * Sets the current state of this object to be {@link TetrisGame.State#GAME_RUNNING}
		 */
		public void setGameRunning()
		{
			currentState=GAME_RUNNING;
		}
		/**
		 * Sets the current state of this object to be {@link TetrisGame.State#GAME_PAUSED}
		 */
		public void setGamePaused()
		{
			currentState=GAME_PAUSED;
		}
		/**
		 * Sets the current state of this object to be {@link TetrisGame.State#GAME_OVER}
		 */
		public void setGameOver()
		{
			currentState=GAME_OVER;
		}
		/**
		 * Get the current state of this object. One of the four constants representing a game state will be returned:
		 * {@link TetrisGame.State#GAME_START_SCREEN },{@link TetrisGame.State#GAME_RUNNING},{@link TetrisGame.State#GAME_PAUSED},{@link TetrisGame.State#GAME_OVER}.
		 * @return The current state this object is in.
		 */
		public int getCurrentState()
		{
			return currentState;
		}
		/**
		 * Tests this object's current state value to see if it is equal to the {@link TetrisGame.State#GAME_RUNNING} constant.
		 * @return True if this game's current state is {@code GAME_RUNNING}.
		 */
		public boolean isGameRunning()
		{
			return currentState==GAME_RUNNING;
		}
		/**
		 * Tests this object's current state value to see if it is equal to the {@link TetrisGame.State#GAME_START_SCREEN} constant.
		 * @return True if this game's current state is {@code GAME_START_SCREEN}.
		 */
		public boolean isGameStartScreen()
		{
			return currentState==GAME_START_SCREEN;
		}
		/**
		 * Tests this object's current state value to see if it is equal to the {@link TetrisGame.State#GAME_PAUSED } constant.
		 * @return True if this game's current state is {@code GAME_PAUSED}.
		 */
		public boolean isGamePaused()
		{
			return currentState==GAME_PAUSED;
		}
		/**
		 * Tests this object's current state value to see if it is equal to the {@link TetrisGame.State#GAME_OVER} constant.
		 * @return True if this game's current state is {@code GAME_OVER}.
		 */
		public boolean isGameOver()
		{
			return currentState==GAME_OVER;
		}
		/**
		 * Returns the string representation of this object, which is a single line string with the class name and the constant field name for the variable 
		 * representing this objects current state.
		 * @return this object's string representation.
		 */
		public String toString()
		{
			String temp="TetrisGame.State";
			switch(currentState)
			{
			case GAME_START_SCREEN:	return temp+"=[GAME_START_SCREEN]";
			case GAME_PAUSED:		return temp+"=[GAME_PAUSED]";
			case GAME_OVER:			return temp+"=[GAME_OVER]";
			case GAME_RUNNING:
				default: 	 		return temp+"=[GAME_RUNNING]";
			}
		}
	}
	/**
	 * Serializable identifier. 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The current shape, there can be only a maximum of one live shape at a time and this is where it is stored.
	 */
	private Shape currentShape;
	/**
	 * The internal object that paints the game background.
	 */
	private BackgroundGrid grid;
	/**
	 * The internal object that keeps track of the game's state.
	 */
	private State currentState;
	/**
	 * The internal object that paints the score and Tetromino Shape legend at the bottom of the game screen.
	 */
	private TetrominoStats statGrid;
	/**
	 * The internal object that paints the pause screen and the start screen. It only paints the start screen at the beginning of the game,
	 * while the pause screen can be painted numerious times according to the game state logic, therefore the reason for the exclusively named field.
	 */
	private StartPauseScreen pauseScreen;
	
	
	/**
	 * The number of milliseconds between timer events, 40ms  is 25 events per second.
	 */
	private final int TIMER_PERIOD=40; //25 frames per second.
	/**
	 * The number of timer events between a single iteration in game logic, such as moving the current shape down one row.
	 */
	private final int TICKS_BETWEEN_GRAVITY=15;
	/**
	 * Keeps track of the number of animation events since the last game logic iteration. 
	 * When this value reaches the value of {@link TetrisGame#TICKS_BETWEEN_GRAVITY}, it resets to zero and keeps track of the count again.
	 * 
	 */
	private int animationTickCount=0;
	/**
	 * The spawn location of a new shape is set to the horizontal center and one cell over the top of the game screen.
	 */
	private Coord spawnCoord=new Coord(10,-1);
	/**
	 * The size in terms of height and width of this JPanel required to properly present the game.
	 */
	private Dimension preferredSize=new Dimension(660,870+90);
	/**
	 * An internal variable to hold the gridcells that make up the LEFT border of the game area.
	 */
	private GridCell[] leftWalls;
	/**
	 * An internal variable to hold the gridcells that make up the RIGHT border of the game area.
	 */
	private GridCell[] rightWalls;
	/**
	 * An internal variable to hold the gridcells that make up the TOP border of the game area.
	 */
	private GridCell[] topWalls;
	/**
	 * An internal variable to hold the gridcells that make up the BOTTOM border of the game area.
	 */
	private GridCell[] bottomWalls;
	/**
	 * An internal reference that is used to fire a window closing event, which is used to safely and properly
	 *  close this application when the user presses the ESC key.
	 */
	private JFrame parentFrame;

	/**
	 * The internal object responsible for keeping score.
	 */
	private TetrisScore score=new TetrisScore();

	
	
	/**
	 * The internal listener used to listen to timer events and execute animation calls and subsequently game logic on top of that.
	 */
	private ActionListener timerListener=new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			performOneAnimationTick();
		}
	};
	
	/**
	 * The internal timer object used to fire periodic events and keep animation and game logic regularly and periodically executed.
	 */
	private Timer timer=new Timer(TIMER_PERIOD,timerListener);
	/**
	 * Used to listen to when this application window running this game has gained the focus of the user of the operating system.
	 */
	public void focusGained(FocusEvent e) 
	{
		
	}
	/**
	 * Listens to when this application window running this game has lost the focus of the user of the operating system and therefore,
	 * sets the game into a pause state.
	 */
	public void focusLost(FocusEvent e) 
	{
		if(currentState.isGameRunning())
		{
			this.setGamePaused();
		}
	}
	/**
	 * Not implemented in this class, used to listen for keyTyped.
	 */
	public void keyTyped(KeyEvent e) 
	{
		
	}
	/**
	 * This method is used as the application Controller in the Model-Viewer-Controller software architecture. This method is
	 * called when a user pressed a keyboard key. Depending on the key pressed, further methods are called and game logic is processed
	 * accordingly.
	 */
	public void keyPressed(KeyEvent e)
	{
	
		if(currentState.isGameStartScreen()&&e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			this.setGameRunning();
			System.out.println(currentState);
		}
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
		{
			parentFrame.dispatchEvent(new WindowEvent(parentFrame, WindowEvent.WINDOW_CLOSING));//end 
		}
		if(e.getKeyCode()==KeyEvent.VK_P||e.getKeyCode()==KeyEvent.VK_H||e.getKeyCode()==KeyEvent.VK_SPACE)
		{
			if(currentState.isGameOver()||currentState.isGameStartScreen())
			{
				System.out.println(currentState);
			}
			else
			{
				
				if(currentState.isGamePaused())
				{
					this.setGameRunning();
					System.out.println(currentState);
				}
				else if(currentState.isGameRunning())
				{
					this.setGamePaused();
					System.out.println(currentState);
				}
			}
		}
		if(currentShape!=null&&currentState.isGameRunning())
		{
			if(e.getKeyCode()==KeyEvent.VK_LEFT||e.getKeyCode()==KeyEvent.VK_A)
			{
				if(grid.canTranslate(currentShape, -1, 0))
				currentShape.translateShape(-1, 0);
			}
			else if(e.getKeyCode()==KeyEvent.VK_RIGHT||e.getKeyCode()==KeyEvent.VK_D)
			{
				if(grid.canTranslate(currentShape, 1, 0))
					currentShape.translateShape(1, 0);
			}
			else if(e.getKeyCode()==KeyEvent.VK_DOWN||e.getKeyCode()==KeyEvent.VK_S)
			{
				if(grid.canTranslate(currentShape, 0, 1))
				{
					currentShape.translateShapeDown();
					score.incrementArrowDown();
				}
				else if(grid.isShapeDead(currentShape))
				{
					if(grid.isShapeOutBounds(currentShape))
					{
						setGameOver();
					}
					else
					{
						grid.consumeShape(currentShape);
						score.incrementShape(currentShape);
						score.incrementRow(grid.detectFullRows());
						grid.removeDetectedRows();
						currentShape=null;
					}
					
				}
			}
			else if(e.getKeyCode()==KeyEvent.VK_W||e.getKeyCode()==KeyEvent.VK_UP)
			{
				if(grid.canRotateCW90(currentShape))
				currentShape.rotateShapeClockwise90();
			}
			else if(e.getKeyCode()==KeyEvent.VK_Q||e.getKeyCode()==KeyEvent.VK_NUMPAD0)
			{
				if(grid.canRotateCCW90(currentShape))
				currentShape.rotateShapeCounterClockwise90();
			}
			else
			{
				
			}
			repaint();
		}
		
	}
	/**
	 * Not implemented in this class, used to listen for keyTyped.
	 */
	public void keyReleased(KeyEvent e)
	{

	}
	
	/**
	 * Constructs a new object of this class. Every argument except for the first one, is passed directly into the member object's constructor:
	 * {@link BackgroundGrid#BackgroundGrid(int, int, int, int, int, int)}.
	 * @param parent The JFrame object that needs to be properly closed when the application ends.
	 * @param x The x component pixel location of the upper left corner of the BackgroundGrid object 
	 * @param y The y component pixel location of the upper left corner of the BackgroundGrid object
	 * @param gridwidth The width of the BackgroundGrid object in pixels.
	 * @param gridheight The height of the BackgroundGrid object in pixels.
	 * @param columns The number of columns of cells for this game
	 * @param rows The number of rows of cells for this game.
	 */
	public TetrisGame(JFrame parent,int x, int y, int gridwidth, int gridheight,int columns, int rows)
	{
		super();
		this.setSize(preferredSize);
		this.setMinimumSize(preferredSize);
		this.setPreferredSize(preferredSize);
		this.initWalls();
	
		this.parentFrame=parent;
		this.grid=new BackgroundGrid(x,y,gridwidth,gridheight,columns,rows);
		this.currentState=new State();
		this.statGrid=new TetrominoStats(score);
		this.pauseScreen=new StartPauseScreen(this.preferredSize);
	}
	/**
	 * Constructs a new object of this class. This constructor is specifically tailored for the pixel dimensions, used in
	 * {@link TetrisStarter}, {@link StartPauseScreen} and {@link TetrominoStats}. Note: future versions of this module/API will have a standard class
	 * for handling sizing across each member class that is delegated work on the TetrisGame drawing area.
     * @param parent The JFrame object that needs to be properly closed when the application ends.
	 */
	public TetrisGame(JFrame parent)
	{
		super();
		this.setSize(preferredSize);
		this.setMinimumSize(preferredSize);
		this.setPreferredSize(preferredSize);
		this.initWalls();

		this.parentFrame=parent;
		this.grid=new BackgroundGrid(30,30,600,600,20,20);
		this.currentState=new State();
		this.statGrid=new TetrominoStats(score);
		this.pauseScreen=new StartPauseScreen(this.preferredSize);
	}
	/**
	 * Used to initialize the GridCell arrays that are used to arbitrarily draw the walls one cell thick of the TetrisGame, which pad the
	 * internal BackgroundGrid object.
	 */
	private void initWalls()
	{
		leftWalls=new GridCell[22];
		rightWalls=new GridCell[22];
		topWalls=new GridCell[20];
		bottomWalls=new GridCell[20];
		for(int i=0;i<leftWalls.length;i++)
		{
			leftWalls[i]=new GridCell(0,i*30,30,30,Color.DARK_GRAY);
			rightWalls[i]=new GridCell(630,i*30,30,30,Color.DARK_GRAY);
			if(i<topWalls.length)
			{
				topWalls[i]=new GridCell(30+30*i,0,30,30,Color.DARK_GRAY);
				bottomWalls[i]=new GridCell(30+30*i,630,30,30,Color.DARK_GRAY);
			}
		}
	}
	/**
	 * Draws the walls of the edge of this class one cell thick. 
	 * @param g2d The Graphics object, readily available in the definition of {@link TetrisGame#paintComponent(Graphics)}.
	 */
	private void drawWalls(Graphics2D g2d)
	{
		for(int i=0;i<leftWalls.length;i++)
		{
			leftWalls[i].drawCell(g2d, true);
			rightWalls[i].drawCell(g2d, true);
			if(i<topWalls.length)
			{
				topWalls[i].drawCell(g2d, true);
				bottomWalls[i].drawCell(g2d, true);
			}
		}
	}
	
	/**
	 * Calls the superclass implementation first. All rendering in this module/ game application
	 * originates in this method's overrided definition.
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D)g;
		
		paintGameRunning(g2d,currentState);
		if(currentState.isGameStartScreen())
			paintGameStartScreen(g2d);
		if(currentState.isGameOver())
			paintGameOverScreen(g2d);
		if(currentState.isGamePaused())
			paintGamePauseScreen(g2d);
		
	}
	/**
	 * Paints the Game Start Screen.
	 * @param g2d The Graphics object supplied by {@link TetrisGame#paintComponent(Graphics)}
	 */
	public void paintGameStartScreen(Graphics2D g2d)
	{
		pauseScreen.drawStartScreen(g2d);
	}
	
	/**
	 * Paints the Game Running Screen. This screen is painted underneath (or before) the start, pause and game-over screens.
	 * @param g2d The Graphics object supplied by {@link TetrisGame#paintComponent(Graphics)}
	 * @param state The object keeping track of the games current state.
	 */
	public void paintGameRunning(Graphics2D g2d,State state)
	{
		grid.drawBackgroundGrid(g2d);
		statGrid.drawTetrominoStats(g2d,state.isGameOver());
		drawWalls(g2d);
		if(currentShape!=null)//shape doesnt exist until first timer event
		{
			currentShape.drawShape(g2d, grid);
		}
		
	}
	/**
	 * Paints the Game Pause Screen.
	 * @param g2d The Graphics object supplied by {@link TetrisGame#paintComponent(Graphics)}
	 */
	public void paintGamePauseScreen(Graphics2D g2d)
	{
		pauseScreen.drawPauseScreen(g2d); 
	}
	/**
	 * The game over screen "GAME OVER!" font.
	 */
	private Font gameOverFont=new Font("Serif",Font.BOLD,92);
	/**
	 * The game over screen "Press ESC to close Window!" font.
	 */
	private Font quitInstructions=new Font("Serif",Font.PLAIN,48);
	/**
	 * Paints the Game Over Screen.
	 * @param g2d The Graphics object supplied by {@link TetrisGame#paintComponent(Graphics)}
	 */
	public void paintGameOverScreen(Graphics2D g2d)
	{
		FontMetrics metrics = g2d.getFontMetrics(gameOverFont);
		int textHeight=metrics.getHeight();
		int textWidth=metrics.stringWidth("GAME OVER!");
		int textY=((int)(preferredSize.getHeight()/2))-textHeight;
		int textX=((int)(preferredSize.getWidth()/2))-textWidth/2;
		
		g2d.setColor(new Color(255,0,0,96));
		g2d.fillRect(0, 0,(int) preferredSize.getWidth(), (int)preferredSize.getHeight());
		g2d.setColor(new Color(255,255,255,128));
		g2d.fillRoundRect(10, 270, 640, 240, 10, 10);
	
		g2d.setColor(new Color(0,0,0,192)); 
		g2d.setFont(gameOverFont);
		
		g2d.drawString("GAME OVER!", textX, textY);
		
		FontMetrics m2=g2d.getFontMetrics(quitInstructions);
		int t2H=m2.getHeight();
		int t2W=m2.stringWidth("Press ESC to close Window!");
		g2d.setFont(quitInstructions);
		g2d.drawString("Press ESC to close Window!",690/2-t2W/2 ,690/2+100+t2H/2 );
	}
	/**
	 * Sets the internal current game state to {@link TetrisGame.State#GAME_START_SCREEN}.
	 */
	public void setGameStartScreen()
	{
		currentState.setGameStartScreen();
	}
	/**
	 * Sets the internal current game state to {@link TetrisGame.State#GAME_RUNNING}.
	 */
	public void setGameRunning()
	{
		if(!timer.isRunning())
		{
			timer.start();
		}
		currentState.setGameRunning();
	}
	/**
	 * Sets the internal current game state to {@link TetrisGame.State#GAME_PAUSED}.
	 */
	public void setGamePaused()
	{
		currentState.setGamePaused();
	}
	/**
	 * Sets the internal current game state to {@link TetrisGame.State#GAME_OVER}.
	 */
	public void setGameOver()
	{
		this.timer.stop();
		currentShape=null;
		currentState.setGameOver();
		repaint();
		
	}
	/**
	 * Called once every time the internal timer event fires. See {@link TetrisGame#TIMER_PERIOD}. 
	 * Only rendering explicitly occurs in this method definition, unless game logic is responding to a rendering or keyboard input,
	 * no time based game logic occurs here.
	 */
	public void performOneAnimationTick()
	{
		animationTickCount++;
		if(animationTickCount==TICKS_BETWEEN_GRAVITY)
		{
			performOneGameTick();
			animationTickCount=0;
		}
		repaint();//repaint 25 times per second.
	}
	/**
	 * Called only once every {@link TetrisGame#TICKS_BETWEEN_GRAVITY} times the {@link TetrisGame#performOneAnimationTick()} is called. 
	 * Game logic is initiated here if need be, principally the periodic translation of a Shape downwards or spawning of a Shape if it does not
	 * exist.
	 */
	public void performOneGameTick()
	{
		if(currentState.isGameRunning())
		{
			if(currentShape==null)
				currentShape=BackgroundGrid.getNextShape(spawnCoord);
			
			if(grid.canTranslateDown(currentShape))
				currentShape.translateShapeDown();
			
			if(grid.isShapeDead(currentShape))
			{
				if(grid.isShapeOutBounds(currentShape))
				{
					setGameOver();
				}
				else
				{
					grid.consumeShape(currentShape);
					score.incrementShape(currentShape);
					score.incrementRow(grid.detectFullRows());
					grid.removeDetectedRows();
					currentShape=null;
				}
				
			}
		}
	}
	
	
}
