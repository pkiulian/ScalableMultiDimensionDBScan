package itkd.userinterface.components;

import itkd.userinterface.data.structures.UserInterface;

import java.awt.Dimension;

public class ConsolePanel extends ControlPanel {

	private static final long serialVersionUID = 1L;

	public ControlPanel setConsoleSpace () {

		UserInterface.console.setBorder(UserInterface.border);
		Dimension size = new Dimension(1100, 80);
		UserInterface.console.setMinimumSize(size);
		UserInterface.console.setPreferredSize(size);
		UserInterface.console.setMaximumSize(size);

		this.setMinimumSize(size);
		this.setPreferredSize(size);
		this.setMaximumSize(size);
		this.add(UserInterface.console);
		return this;
	}
}
