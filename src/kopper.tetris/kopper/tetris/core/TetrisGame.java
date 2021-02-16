package kopper.tetris.core;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import kopper.tetris.shape.Shape;

/**
 * 
 * @author micha
 * 
 * This class combines all of the data models and drawable items (Classes with {@code drawOBJECTNAME(Graphics2D g2d);} to perform all
 * the major functions a Tetris Game would be expected to provide on an overall level. The Data models (referring to all the objects in the module)
 * are all drawn to this subclass of JPanel. In the Model Viewer Controller software architecture, this class combines the model classes:
 * mostly represented by {@link BackgroundGrid}, {@link Shape} (and its subclasses), with the viewer classes and methods: {@link TetrisGame},
 * which is a subclass of {@link JPanel} and heavily relies on {@link TetrisGame#paintComponent(Graphics)} and {@code drawOBJECTNAME(Graphics2D g2d)} methods
 * and the controller classes and methods: {@link java.awt.event.KeyListener} implemented in TetrisGame as methods.
 * 
 * One need only to add this TetrisGame object to a JFrame and wireup the {@link javax.swing.JFrame}'s KeyListeners,FocusListeners to this 
 * class to make a self contained complete application that is a remake of Tetris!
 *
 */
public class TetrisGame extends JPanel implements KeyListener,FocusListener
{
	

	public static char ARROW_LEFT='\u2190';
	public static char ARROW_UP='\u2191';
	public static char ARROW_RIGHT='\u2192';
	public static char ARROW_DOWN='\u2193';

	
	/**
	 * A Class which represents the state of a TetrisGame. 
	 * 
	 * Each of the FOUR states represents a different rendering flow inside of the {@link JPanel}'s overridden method  {@link TetrisGame#paintComponent(Graphics)}.
	 * 
	 * <p>Note: In future releases I hope to potentially add a FIFTH state, probably titled GAME_ANIMATION, to accommodate some attractive animations during
	 * the row deletion process of the Tetris Game. Also, looking into changing this class to an Enum declaration in future releases.
	 * @author micha
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
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Shape currentShape;
	private BackgroundGrid grid;
	private State currentState;
	private TetrominoStats statGrid;
	private StartPauseScreen pauseScreen;
	
	private final int TIMER_PERIOD=40; //25 frames per second.
	private final int TICKS_BETWEEN_GRAVITY=15;
	private int animationTickCount=0;
	private Coord spawnCoord=new Coord(10,-1);
	private Dimension preferredSize=new Dimension(660,870+90);
	private GridCell[] leftWalls;
	private GridCell[] rightWalls;
	private GridCell[] topWalls;
	private GridCell[] bottomWalls;
	private JFrame parentFrame;

	private TetrisScore score=new TetrisScore();

	
	private ActionListener timerListener=new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			performOneAnimationTick();
		}
	};

	public void focusGained(FocusEvent e) 
	{
		
	}

	public void focusLost(FocusEvent e) 
	{
		if(currentState.isGameRunning())
		{
			this.setGamePaused();
		}
	}
	
	public void keyTyped(KeyEvent e) 
	{
		
	}
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
	public void keyReleased(KeyEvent e)
	{

	}
	
	private Timer timer=new Timer(TIMER_PERIOD,timerListener);
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
	Font scoreFont=new Font("Serif",Font.PLAIN,24);
	Font gameOverFont=new Font("Serif",Font.BOLD,92);
	Font quitInstructions=new Font("Serif",Font.PLAIN,48);
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
	public void paintGameStartScreen(Graphics2D g2d)
	{
		pauseScreen.drawStartScreen(g2d);
	}
	public void paintGameRunning(Graphics2D g2d,State state)
	{
		grid.drawBackgroundGrid(g2d);
		statGrid.drawTetrominoStats(g2d,state.isGameOver());
		drawWalls(g2d);
		if(currentShape!=null)//shape doesnt exist until first timer event
		{
			currentShape.drawShape(g2d, grid);
		}
		g2d.setFont(scoreFont);
		g2d.setColor(Color.black);
		
	}
	public void paintGamePauseScreen(Graphics2D g2d)
	{
		pauseScreen.drawPauseScreen(g2d); 
	}
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
	public void setGameStartScreen()
	{
		currentState.setGameStartScreen();
	}
	public void setGameRunning()
	{
		if(!timer.isRunning())
		{
			timer.start();
		}
		currentState.setGameRunning();
	}
	public void setGamePaused()
	{
		currentState.setGamePaused();
	}
	public void setGameOver()
	{
		this.timer.stop();
		currentShape=null;
		currentState.setGameOver();
		repaint();
		
	}
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
