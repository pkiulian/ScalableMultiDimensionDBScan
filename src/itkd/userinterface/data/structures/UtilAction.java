package itkd.userinterface.data.structures;

import itkd.algorithms.clustering.dbscan.epsilonanalysis.DBScan;
import itkd.algorithms.clustering.dbscan.epsilonanalysis.EpsilonEsitimation;
import itkd.algorithms.clustering.dbscan.epsilonanalysis.FDBScan;
import itkd.data.processing.ProcessFile;
import itkd.userinterface.main.Application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class UtilAction {

	String filePath = "";
	// protected static final int[] intervals = {10, 20, 30, 40, 50, 60, 70, 80, 90,
	// 100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 200, 210, 220, 230,
	// 240, 250, 260, 270, 280, 290, 300, 310, 320, 330, 340, 350, 360, 370,
	// 380, 390, 400, 410, 420, 430, 440, 450, 460, 470, 480, 490, 500, 510,
	// 520, 530, 540, 550, 560, 570, 580, 590, 600, 610, 620, 630, 640, 650,
	// 660, 670, 680, 690, 700, 710, 720, 730, 740, 750, 760, 770, 780, 790,
	// 800, 810, 820, 830, 840, 850, 860, 870, 880, 890, 900, 910, 920, 930,
	// 940, 950, 960, 970, 980, 990, 1000};

	protected static final int[] intervals = {100, 200, 300, 400, 500, 600, 700,
			800, 900, 1000};

	public JButton LoadData (final Application window) {
		final JButton loadButton = new JButton("Step 1: Load Data");
		loadButton.setMargin(new Insets(1, 1, 1, 1));
		loadButton.setFont(UserInterface.font);
		loadButton.setForeground(Color.white);
		loadButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		loadButton.setOpaque(false);
		loadButton.setContentAreaFilled(false);
		loadButton.setBorderPainted(false);

		loadButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased (MouseEvent e) {
				UserInterface.console.setFont(UserInterface.font);
				UserInterface.console.print(
						"Free memory available for Algorithm on your JVM: "
								+ Runtime.getRuntime().freeMemory() + " bytes out of "
								+ Runtime.getRuntime().totalMemory() + "\n ", Color.BLUE);
				final JFileChooser fc = new JFileChooser();
				switch (fc.showOpenDialog(window)) {
					case JFileChooser.APPROVE_OPTION:
						JOptionPane.showMessageDialog(window,
								"Selected: " + fc.getSelectedFile(), "Application",
								JOptionPane.OK_OPTION);
						File file = fc.getSelectedFile();
						UserInterface.console.print("The file you read: " + file.getPath()
								+ "\n", Color.BLUE);
						filePath = file.getPath();
						ProcessFile dfR = new ProcessFile(filePath);
						dfR.readInputBasedOnMemorySize();
						break;
					case JFileChooser.CANCEL_OPTION:
						JOptionPane.showMessageDialog(window, "Cancelled", "Application",
								JOptionPane.OK_OPTION);
						break;

					case JFileChooser.ERROR_OPTION:
						JOptionPane.showMessageDialog(window, "Error", "mess",
								JOptionPane.OK_OPTION);
				}

			}

			@Override
			public void mousePressed (MouseEvent e) {

			}

			@Override
			public void mouseExited (MouseEvent e) {
				loadButton.setForeground(Color.white);

			}

			@Override
			public void mouseEntered (MouseEvent e) {
				loadButton.setForeground(Color.yellow);

			}

			@Override
			public void mouseClicked (MouseEvent e) {

			}

		});
		return loadButton;

	}

	public JTextField textFieldMinPts (JTextField textField) {
		Dimension textDim = new Dimension(40, 17);
		textField.setMaximumSize(textDim);
		textField.setMinimumSize(textDim);
		textField.setPreferredSize(textDim);
		textField.setEditable(true);
		textField.setVisible(true);
		return textField;
	}

	public JButton calculateTheOptimalEpsilon (final JTextField textField) {
		final JButton optimEpsButton = new JButton(
				"Step 2: Compute the optimum epsilon based on MinPts");
		optimEpsButton.setMargin(new Insets(1, 1, 1, 1));
		optimEpsButton.setFont(UserInterface.font);
		optimEpsButton.setForeground(Color.white);
		optimEpsButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		optimEpsButton.setOpaque(false);
		optimEpsButton.setContentAreaFilled(false);
		optimEpsButton.setBorderPainted(false);

		optimEpsButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased (MouseEvent e) {
				String minPtsValue = "";
				minPtsValue = textFieldMinPts(textField).getText().trim();
				System.out.println("minPtsValue " + minPtsValue);
				UserInterface.MinPts = (!minPtsValue.equals("")) ? Integer
						.parseInt(minPtsValue) : UserInterface.MinPts;
				XYDataset dataset = createDataset();
				final JFreeChart chart = createChart(dataset);
				ChartPanel chartPanel = new ChartPanel(chart, false);
				chartPanel.setPreferredSize(new java.awt.Dimension(700, 500));
				Application.centerControlPanel.removeAll();
				Application.centerControlPanel.add(chartPanel, BorderLayout.PAGE_START);
				Application.centerControlPanel.revalidate();

			}

			private XYDataset createDataset () {
				final XYSeries series = new XYSeries("evolution of core points");

				EpsilonEsitimation epsEstimation = new EpsilonEsitimation();
				UserInterface.console.print(ProcessFile.getTweets().getList().size());
				List<Integer> m = epsEstimation.NeighboursForAtleastAtMostNeighbours(
						ProcessFile.getTweets(), UserInterface.MinPts, intervals);
				for (int i = 0; i < m.size(); i++) {
					System.out.println(intervals[i] + ", " + m.get(i).intValue());
					series.add(intervals[i], m.get(i).intValue());
				}
				final XYSeriesCollection dataset = new XYSeriesCollection();
				dataset.addSeries(series);
				return dataset;
			}

			@Override
			public void mousePressed (MouseEvent e) {

			}

			@Override
			public void mouseExited (MouseEvent e) {
				optimEpsButton.setForeground(Color.white);

			}

			@Override
			public void mouseEntered (MouseEvent e) {
				optimEpsButton.setForeground(Color.yellow);
			}

			@Override
			public void mouseClicked (MouseEvent e) {

			}

		});
		optimEpsButton.setVisible(true);
		return optimEpsButton;
	}

	private JFreeChart createChart (final XYDataset dataset) {

		final JFreeChart chart = ChartFactory.createXYLineChart(
				"Number of core points distance interval", // chart title
				"epsilon intervals in meters for MinPts = " + UserInterface.MinPts
						+ " ", // x axis label
				"number of core points on interval", // y axis label
				dataset, // data
				PlotOrientation.VERTICAL, true, // include legend
				false, // tooltips
				false // urls
				);

		chart.setBackgroundPaint(Color.white);
		final XYPlot plot = chart.getXYPlot();
		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesLinesVisible(0, true);
		renderer.setSeriesShapesVisible(0, true);
		plot.setRenderer(renderer);

		return chart;
	}

	public JButton ClusterDBScan (final Application window, final JTextField eps,
			final JTextField minPts) {
		final JButton dbScanButton = new JButton(
				"Step 3: Execute DBScan giving eps and minPts");
		dbScanButton.setMargin(new Insets(1, 1, 1, 1));
		dbScanButton.setFont(UserInterface.font);
		dbScanButton.setForeground(Color.white);
		dbScanButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		dbScanButton.setOpaque(false);
		dbScanButton.setContentAreaFilled(false);
		dbScanButton.setBorderPainted(false);
		dbScanButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked (MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered (MouseEvent e) {
				dbScanButton.setForeground(Color.yellow);

			}

			@Override
			public void mouseExited (MouseEvent e) {
				dbScanButton.setForeground(Color.white);

			}

			@Override
			public void mousePressed (MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased (MouseEvent e) {
				String epsValue = textFieldMinPts(eps).getText().trim();
				String minPtsValue = textFieldMinPts(minPts).getText().trim();
				System.out.println(epsValue + "  " + minPtsValue);
				if (!epsValue.isEmpty() && !minPtsValue.isEmpty()) {
					DBScan dbScan = new DBScan(Double.parseDouble(epsValue), Integer
							.parseInt(minPtsValue));
					final JFileChooser fc = new JFileChooser();
					switch (fc.showOpenDialog(window)) {
						case JFileChooser.APPROVE_OPTION:
							JOptionPane.showMessageDialog(window,
									"Selected: " + fc.getSelectedFile(), "Application",
									JOptionPane.OK_OPTION);
							File file = fc.getSelectedFile();
							UserInterface.console.print(
									"The file you read: " + file.getPath() + "\n", Color.BLUE);
							filePath = file.getPath();
							ProcessFile dfR = new ProcessFile(filePath);
							dfR.readInputBasedOnMemorySize();
							long start = System.currentTimeMillis();
							dbScan.clusterData();
							long end = System.currentTimeMillis();
							UserInterface.console.print("needed time for DBScan: "
									+ (end - start) + " miliseconds \n");
							filePath = setFilePath(filePath);
							dfR.insertInFile(filePath);
							DefaultCategoryDataset dataset = createBarDataSet();
							final JFreeChart chart = createChartBar(dataset);
							ChartPanel chartPanel = new ChartPanel(chart, false);
							chartPanel.setPreferredSize(new java.awt.Dimension(700, 500));
							Application.centerControlPanel.removeAll();
							Application.centerControlPanel.add(chartPanel,
									BorderLayout.PAGE_START);
							Application.centerControlPanel.revalidate();

							break;
						case JFileChooser.CANCEL_OPTION:
							JOptionPane.showMessageDialog(window, "Cancelled", "Application",
									JOptionPane.OK_OPTION);
							break;

						case JFileChooser.ERROR_OPTION:
							JOptionPane.showMessageDialog(window, "Error", "mess",
									JOptionPane.OK_OPTION);
					}

				} else {
					JOptionPane.showMessageDialog(window,
							"Please insert the Eps and MinPts", "Warning",
							JOptionPane.OK_OPTION);
				}
			}
		});
		return dbScanButton;
	}

	protected JFreeChart createChartBar (DefaultCategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createBarChart(
				"Comparison between Clusters", "Clusters", "Number of Tweets", dataset,
				PlotOrientation.VERTICAL, false, true, false);
		chart.setBackgroundPaint(Color.white);
		return chart;
	}

	protected DefaultCategoryDataset createBarDataSet () {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Set<Integer> ClusterIdSet = ProcessFile.getSetOfCluster();
		UserInterface.console.println("CLusters:  " + ClusterIdSet.size());
		for (Integer i : ClusterIdSet) {
			int nrOfTweets = ProcessFile
					.getNrOfObjectsWithGiveClusterId(i.intValue());
			dataset.setValue(nrOfTweets, "number of tweets", i);
		}
		return dataset;
	}

	protected String setFilePath (String filePath) {
		int i = filePath.length() - 1;
		StringBuilder sb = new StringBuilder(filePath);
		while (sb.charAt(i) != '\\') {
			sb.deleteCharAt(i);
			i--;
		}
		return sb.toString().trim();
	}

	public JButton ClusterFrameDBScan (final Application window,
			final JTextField eps, final JTextField minPts) {
		final JButton frameDBScan = new JButton("Step 4: Execute Frame DBScan ");
		frameDBScan.setMargin(new Insets(1, 1, 1, 1));
		frameDBScan.setFont(UserInterface.font);
		frameDBScan.setForeground(Color.white);
		frameDBScan.setAlignmentX(Component.RIGHT_ALIGNMENT);
		frameDBScan.setOpaque(false);
		frameDBScan.setContentAreaFilled(false);
		frameDBScan.setBorderPainted(false);
		frameDBScan.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked (MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered (MouseEvent e) {
				frameDBScan.setForeground(Color.yellow);

			}

			@Override
			public void mouseExited (MouseEvent e) {
				frameDBScan.setForeground(Color.white);

			}

			@Override
			public void mousePressed (MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased (MouseEvent arg0) {
				final JFileChooser fc = new JFileChooser();
				String epsValue = textFieldMinPts(eps).getText().trim();
				String minPtsValue = textFieldMinPts(minPts).getText().trim();

				if (!epsValue.isEmpty() && !minPtsValue.isEmpty()) {
					FDBScan frameDBScan = new FDBScan(Double.parseDouble(epsValue),
							Integer.parseInt(minPtsValue));

					switch (fc.showOpenDialog(window)) {
						case JFileChooser.APPROVE_OPTION:
							JOptionPane.showMessageDialog(window,
									"Selected: " + fc.getSelectedFile(), "Application",
									JOptionPane.OK_OPTION);
							File file = fc.getSelectedFile();
							UserInterface.console.print(
									"The file you read: " + file.getPath() + "\n", Color.BLUE);
							filePath = file.getPath();
							ProcessFile dfR = new ProcessFile(filePath);
							frameDBScan.clusterData();
							// dfR.readInputBasedOnMemorySize();
							// long start = System.currentTimeMillis();
							// dbScan.clusterData();
							// long end = System.currentTimeMillis();
							// UserInterface.console.print("needed time for DBScan: "
							// + (end - start) + " miliseconds \n");
							// filePath = setFilePath(filePath);
							// dfR.insertInFile(filePath);

							DefaultCategoryDataset dataset = createBarDataSet();
							final JFreeChart chart = createChartBar(dataset);
							ChartPanel chartPanel = new ChartPanel(chart, false);
							chartPanel.setPreferredSize(new java.awt.Dimension(700, 500));
							Application.centerControlPanel.removeAll();
							Application.centerControlPanel.add(chartPanel,
									BorderLayout.PAGE_START);
							Application.centerControlPanel.revalidate();

							break;
						case JFileChooser.CANCEL_OPTION:
							JOptionPane.showMessageDialog(window, "Cancelled", "Application",
									JOptionPane.OK_OPTION);
							break;

						case JFileChooser.ERROR_OPTION:
							JOptionPane.showMessageDialog(window, "Error", "mess",
									JOptionPane.OK_OPTION);
					}
				} else {
					JOptionPane.showMessageDialog(window,
							"Please insert the Eps and MinPts", "Warning",
							JOptionPane.OK_OPTION);
				}
			}

		});

		return frameDBScan;
	}
}
