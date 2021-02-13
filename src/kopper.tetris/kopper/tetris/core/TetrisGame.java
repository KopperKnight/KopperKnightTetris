package kopper.tetris.core;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import kopper.tetris.shape.Shape;
public class TetrisGame extends JPanel implements KeyListener,FocusListener
{
	

	public static char ARROW_LEFT='\u2190';
	public static char ARROW_UP='\u2191';
	public static char ARROW_RIGHT='\u2192';
	public static char ARROW_DOWN='\u2193';

	public static class State
	{
		public static final int GAME_START_SCREEN=0;
		public static final int GAME_RUNNING=1;
		public static final int GAME_PAUSED=2;
		public static final int GAME_OVER=3;
		private int currentState=0;
		public State()//default state is Game START Screen
		{
			
		}
		public void setGameStartScreen()
		{
			currentState=GAME_START_SCREEN;
		}
		public void setGameRunning()
		{
			currentState=GAME_RUNNING;
		}
		public void setGamePaused()
		{
			currentState=GAME_PAUSED;
		}
		public void setGameOver()
		{
			currentState=GAME_OVER;
		}
		public int getCurrentState()
		{
			return currentState;
		}
		public boolean isGameRunning()
		{
			return currentState==GAME_RUNNING;
		}
		public boolean isGameStartScreen()
		{
			return currentState==GAME_START_SCREEN;
		}
		public boolean isGamePaused()
		{
			return currentState==GAME_PAUSED;
		}
		public boolean isGameOver()
		{
			return currentState==GAME_OVER;
		}
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
		if(currentState.isGameStartScreen())
			paintGameStartScreen(g2d);
		if(currentState.isGameOver())
			paintGameOverScreen(g2d);
		if(currentState.isGamePaused())
			paintGamePauseScreen(g2d);
		
	}
	private void paintGameStartScreen(Graphics2D g2d)
	{
		pauseScreen.drawStartScreen(g2d);
	}
	private void paintGameRunning(Graphics2D g2d,State state)
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
	private void paintGamePauseScreen(Graphics2D g2d)
	{
		pauseScreen.drawPauseScreen(g2d); 
	}
	private void paintGameOverScreen(Graphics2D g2d)
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
	public void drawGame(Graphics2D g2d)//done each animation tick.
	{
		grid.drawBackgroundGrid(g2d);
		currentShape.drawShape(g2d, grid);
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
