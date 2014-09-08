package itkd.userinterface.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class TitlePanel extends ControlPanel {

	private static final long serialVersionUID = 1L;
	public TitlePanel(){
		super();
	}
	
	public TitlePanel setTitle() {		
		JLabel title = new JLabel("Scalable Multi Dimensional DBScan");
		title.setAlignmentY(JLabel.CENTER_ALIGNMENT);
		title.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		title.setBackground(Color.BLACK);
		title.setForeground(Color.WHITE);		
		this.add(title);
		this.setBackground(Color.BLACK);
		return this;
	}
}
