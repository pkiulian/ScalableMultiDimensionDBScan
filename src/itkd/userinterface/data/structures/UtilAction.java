package itkd.userinterface.data.structures;

import itkd.algorithms.clustering.dbscan.epsilonanalysis.EpsilonEsitimation;
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
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class UtilAction {

	protected static final int MinPts = 120;
	protected static final int[] intervals = {10, 20, 30, 40, 50, 60, 70, 80, 90,
			100, 110, 120, 130, 140, 150};

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
						ProcessFile dfR = new ProcessFile(file.getPath());
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

	public JTextField textFieldMinPts () {
		JTextField textField = new JTextField();
		Dimension textDim = new Dimension(40, 17);
		textField.setMaximumSize(textDim);
		textField.setMinimumSize(textDim);
		textField.setPreferredSize(textDim);
		textField.setEditable(true);
		textField.setVisible(true);
		return textField;
	}

	public JButton calculateTheOptimalEpsilon () {
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

				XYDataset dataset = createDataset();
				final JFreeChart chart = createChart(dataset);
				ChartPanel chartPanel = new ChartPanel(chart, false);
				chartPanel.setPreferredSize(new java.awt.Dimension(500, 300));
				Application.centerControlPanel.removeAll();
				Application.centerControlPanel.add(chartPanel, BorderLayout.PAGE_START);
				Application.centerControlPanel.revalidate();

			}

			private XYDataset createDataset () {
				final XYSeries series1 = new XYSeries("First");

				EpsilonEsitimation epsEstimation = new EpsilonEsitimation();
				UserInterface.console.print(ProcessFile.tweets.getList().size());
				List<Integer> m = epsEstimation.NeighboursForAtleastAtMostNeighbours(
						ProcessFile.tweets, MinPts, intervals);
				for (int i = 0; i < m.size(); i++) {
					System.out.println(intervals[i] + ", " + m.get(i).intValue());
					series1.add(intervals[i], m.get(i).intValue());
				}
				final XYSeriesCollection dataset = new XYSeriesCollection();
				dataset.addSeries(series1);
				return dataset;
			}

			private JFreeChart createChart (final XYDataset dataset) {

				// create the chart...
				final JFreeChart chart = ChartFactory.createXYLineChart(
						"Number of core points distance interval", // chart title
						"epsilon intervals", // x axis label
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
}
