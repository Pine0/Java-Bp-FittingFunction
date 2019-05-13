package draw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JFrame;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;

/**
 * LossChart class
 *
 * @author LuLiyun
 * @date 2019/05/01
 */
public class LossChart {

	public static void showChart(String fun) throws IOException {
		// 建立数据集
		DefaultXYDataset xyDataset = new DefaultXYDataset();
		double[][] data = getData(fun);
		xyDataset.addSeries("Loss Value", data);

		/*
		 * LOSS CHART: 标题
		 * N: x轴
		 * Lg(loss): y轴
		 * xyDataset: 数据集
		 * legend: true
		 * tooltips: false
		 * urls: false
		 */
		JFreeChart chart = ChartFactory.createScatterPlot("LOSS CHART", "N", "Lg(loss)",
				xyDataset, PlotOrientation.VERTICAL, true, false, false);

		ChartFrame frame = new ChartFrame("Loss散点图", chart, true);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/**
	 * 读数据，把loss值存到lossArray数组中
	 * @return losData
	 * @throws IOException 抛出IO异常
	 */
	public static double[][] getData(String fun) throws IOException {

		File file = new File("./data/" + fun + "_Loss.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));

		String line = null;
		Vector<Double> lossValue = new Vector<Double>();
		while ((line = br.readLine()) != null) {
			lossValue.add(Double.parseDouble(line));
		}

		// 迭代次数
		int n = lossValue.size();
		double[][] lossData = new double[2][n];
		if (!lossValue.isEmpty()) {
			for (int i = 0; i < n; i++) {
				// x轴
				lossData[0][i] = i;
				// y轴
				lossData[1][i] = Math.log(lossValue.get(i)) / Math.log(10.0);
			}
		}

		br.close();

		return lossData;
	}

}
