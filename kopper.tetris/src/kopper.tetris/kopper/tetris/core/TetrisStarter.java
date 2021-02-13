package kopper.tetris.core;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * This class provides the entry point to the Tetris Game application.
 * @author KopperKnight
 * 
 * 
 */
public class TetrisStarter 
{
	/**
	 *  The entry point of the KopperKnight Tetris application.
	 */
	public static void main(String[]arg)
	{
		JFrame frame =new JFrame("KopperKnight Tetris (Build 0.20210210)");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		TetrisGame game=new TetrisGame(frame);
		
		InputStream  in=TetrisStarter.class.getResourceAsStream("kopper.png");
		if(in!=null)
		{
			try
			{
				Image image=ImageIO.read(TetrisStarter.class.getResourceAsStream("kopper.png"));
				if(image!=null)
					frame.setIconImage(image);
			}
			catch(IOException e)
			{
				
			}
		}
	
		frame.getContentPane().add(game);
		frame.addKeyListener(game);
		frame.addFocusListener(game);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		//System.out.println("[\u1f48]");
		
	}

}
