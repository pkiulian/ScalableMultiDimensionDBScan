package itkd.userinterface.main;

import itkd.userinterface.components.ConsolePanel;
import itkd.userinterface.components.ControlPanel;
import itkd.userinterface.components.MainFrame;
import itkd.userinterface.components.TitlePanel;
import itkd.userinterface.data.structures.UserInterface;
import itkd.userinterface.data.structures.UtilAction;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class Application extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static MainFrame mainFrame;
	public static ControlPanel menuLeftPanel;
	public static ControlPanel centerControlPanel;
	public static ConsolePanel consolePanel;
	public static JProgressBar progress;
	static int minProgress = 0;

	static int maxProgress = 100;

	JFileChooser fc = new JFileChooser();

	private final UtilAction action = new UtilAction();

	public static void main (String[] args) {
		try {
			progress = new JProgressBar();
			Application window = new Application();

			Box menuLeftBox = new Box(BoxLayout.Y_AXIS);
			Box iconPanel = new Box(BoxLayout.Y_AXIS);

			Box loadBox = new Box(BoxLayout.X_AXIS);
			loadBox = setBoxSizes(loadBox);
			JButton loadButton = window.action.LoadData(window);
			loadBox.add(loadButton);

			Box leftControlPanelBox = new Box(BoxLayout.X_AXIS);
			leftControlPanelBox = setBoxSizes(leftControlPanelBox);

			JTextField epsValue = new JTextField();
			epsValue = window.action.textFieldMinPts(epsValue);
			JButton epsButton = window.action.calculateTheOptimalEpsilon(epsValue);

			Box leftDBSCANBox = new Box(BoxLayout.X_AXIS);
			JTextField epsField = new JTextField();
			JTextField minPtsField = new JTextField();
			epsField = window.action.textFieldMinPts(epsField);
			minPtsField = window.action.textFieldMinPts(minPtsField);
			leftDBSCANBox = setBoxSizes(leftDBSCANBox);
			JButton dBScanButton = window.action.ClusterDBScan(window, epsField,
					minPtsField);
			leftDBSCANBox.add(dBScanButton);
			leftDBSCANBox.add(epsField);
			leftDBSCANBox.add(minPtsField);

			Box frameDBScanBox = new Box(BoxLayout.X_AXIS);

			frameDBScanBox = setBoxSizes(frameDBScanBox);
			JButton frameDBScan = window.action.ClusterFrameDBScan();
			frameDBScanBox.add(frameDBScan, BorderLayout.WEST);
			leftControlPanelBox.add(epsButton);
			leftControlPanelBox.add(epsValue);
			menuLeftBox.add(loadBox);
			menuLeftBox.add(leftControlPanelBox, RIGHT_ALIGNMENT);
			menuLeftBox.add(leftDBSCANBox, RIGHT_ALIGNMENT);
			menuLeftBox.add(frameDBScanBox, RIGHT_ALIGNMENT);
			menuLeftBox.add(progress);
			menuLeftBox.add(iconPanel, BorderLayout.WEST);
			menuLeftBox.setBorder(UserInterface.border);
			menuLeftPanel.add(menuLeftBox, BorderLayout.LINE_START);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Box setBoxSizes (Box leftControlPanelBox) {
		Dimension size = new Dimension(350, 30);
		leftControlPanelBox.setMaximumSize(size);
		leftControlPanelBox.setMinimumSize(size);
		leftControlPanelBox.setPreferredSize(size);
		return leftControlPanelBox;
	}

	public Application () {
		initialize();

	}

	private void initialize () {

		TitlePanel panelTitle = new TitlePanel();
		panelTitle.setTitle();
		mainFrame = new MainFrame();
		mainFrame.setResizable(false);
		menuLeftPanel = new ControlPanel();
		menuLeftPanel.setLayout(new BoxLayout(menuLeftPanel, BoxLayout.Y_AXIS));

		centerControlPanel = new ControlPanel();
		consolePanel = new ConsolePanel();
		consolePanel.setConsoleSpace();

		mainFrame.add(panelTitle, BorderLayout.PAGE_START);
		progress.setMinimum(minProgress);
		progress.setMaximum(maxProgress);
		progress.setStringPainted(true);
		mainFrame.add(menuLeftPanel, BorderLayout.LINE_START);
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