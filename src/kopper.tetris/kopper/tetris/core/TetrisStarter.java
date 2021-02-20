package kopper.tetris.core;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * This class provides the entry point to the Tetris Game application.
 * @author <a href="https://github.com/kopperknight">KopperKnight</a>
 * 
 * 
 */
public class TetrisStarter 
{
	/**
	 *  The entry point of the KopperKnight Tetris application.
	 *  @param arg The command line arguments.
	 */
	public static void main(String[]arg)
	{
		//build number will follow MAJOR.MINOR.PATCH-YYDDD format
		// where the YY is the last two digits the bulid came out
		// where the DDD  is the 001-to-365th day of the year the build came out.
		// I will initiate this game build at 0.2.9-21049 to say this is the
		//last version this game will be at in the 0.2.x-yyddd version.
		// the next version is anticipated to be 0.3.x.z-yyddd.
		
		JFrame frame =new JFrame("KopperKnight Tetris (Build 0.2.9-21050)");
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
