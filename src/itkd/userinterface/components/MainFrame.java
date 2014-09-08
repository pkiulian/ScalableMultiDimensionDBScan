package itkd.userinterface.components;

import java.awt.Color;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MainFrame () {
		super(
				"SMDDBScan version 1.0.0  (Scalable Multi Dimensional DBScan) - Research Tool");
		setMainFrame();
	}

	private JFrame setMainFrame () {

		this.getContentPane().setBackground(Color.BLACK);
		this.setBounds(100, 100, 1100, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return this;
	}

}
