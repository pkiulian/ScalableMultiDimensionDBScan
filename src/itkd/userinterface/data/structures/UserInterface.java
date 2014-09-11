package itkd.userinterface.data.structures;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import bsh.util.JConsole;

public class UserInterface {
	public static JConsole console = new JConsole();
	public static Border border = BorderFactory.createLineBorder(Color.YELLOW, 1);
	public static Font font = new Font("Comic Sans MS", Font.BOLD, 10);
	/** Default Value */
	public static int MinPts = 120;

}
