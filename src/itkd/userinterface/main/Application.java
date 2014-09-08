package itkd.userinterface.main;

import itkd.userinterface.components.ConsolePanel;
import itkd.userinterface.components.ControlPanel;
import itkd.userinterface.components.MainFrame;
import itkd.userinterface.components.TitlePanel;
import itkd.userinterface.data.structures.UserInterface;
import itkd.userinterface.data.structures.UtilAction;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class Application extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static MainFrame mainFrame;
	public static ControlPanel leftControlPanel;
	public static ControlPanel centerControlPanel;
	public static ConsolePanel consolePanel;
	public static JProgressBar progress;
	static int min = 0;

	static int max = 100;

	JFileChooser fc = new JFileChooser();

	private final UtilAction action = new UtilAction();

	public static void main (String[] args) {
		try {
			progress = new JProgressBar();
			Application window = new Application();
			Box iconPanel = new Box(BoxLayout.Y_AXIS);
			iconPanel.add(window.action.LoadData(window));
			ControlPanel leftControlPanelBox = new ControlPanel();
			// leftControlPanelBox.setLayout(new FlowLayout(FlowLayout.LEFT));
			leftControlPanelBox.add(window.action.calculateTheOptimalEpsilon());
			leftControlPanelBox.add(window.action.textFieldMinPts());
			iconPanel.add(leftControlPanelBox);
			iconPanel.add(progress);
			leftControlPanel.add(iconPanel, BorderLayout.WEST);
			leftControlPanel.setBorder(UserInterface.border);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Application () {
		initialize();

	}

	private void initialize () {

		TitlePanel panelTitle = new TitlePanel();
		panelTitle.setTitle();
		mainFrame = new MainFrame();
		mainFrame.setResizable(false);
		leftControlPanel = new ControlPanel();
		leftControlPanel
				.setLayout(new BoxLayout(leftControlPanel, BoxLayout.Y_AXIS));

		centerControlPanel = new ControlPanel();
		consolePanel = new ConsolePanel();
		consolePanel.setConsoleSpace();

		mainFrame.add(panelTitle, BorderLayout.PAGE_START);
		progress.setMinimum(min);
		progress.setMaximum(max);
		progress.setStringPainted(true);
		mainFrame.add(leftControlPanel, BorderLayout.LINE_START);
		mainFrame.add(centerControlPanel, BorderLayout.CENTER);
		mainFrame.add(consolePanel, BorderLayout.PAGE_END);
		mainFrame.setVisible(true);
	}

	public static void updateBar (int newValue) {
		Application.progress.setValue(newValue);
		System.out.println("update bar inside " + Application.progress.getValue());
		progress.setString("Visual interpretation for eps "
				+ Integer.toString(newValue) + "%");
		progress.repaint();
		progress.update(Application.progress.getGraphics());
	}
}