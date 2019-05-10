package draw;

import javax.swing.JFrame;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;

/**
 * SinxChart class
 *
 * @author LuLiyun
 * @date 2019/05/01
 */
public class SinxChart {

	public static void showChart(double[][] x, double[][] y, double[][] newY) {

		double[][] dataset1 = new double[2][x.length];
		double[][] dataset2 = new double[2][x.length];
		for (int i = 0; i < x.length; i++) {
			dataset1[0][i] = x[i][0];
			dataset1[1][i] = y[i][0];

			dataset2[0][i] = x[i][0];
			dataset2[1][i] = newY[i][0];
		}
		// Sin(x)
		DefaultXYDataset xyDataset1 = new DefaultXYDataset();
		// Sin(x)拟合
		DefaultXYDataset xyDataset2 = new DefaultXYDataset();
		xyDataset1.addSeries("SinX", dataset1);
		xyDataset2.addSeries("SinX_Fix", dataset2);

		XYItemRenderer renderer1 = new XYShapeRenderer();
		//x轴
		NumberAxis xAxis = new NumberAxis("X 值");
		//y轴
		NumberAxis yAxis = new NumberAxis("Y 值");
		//x轴坐标
		NumberTickUnit xUnit = new NumberTickUnit(0.2);
		//y轴坐标
		NumberTickUnit yUnit = new NumberTickUnit(0.2);
		xAxis.setTickUnit(xUnit);
		yAxis.setTickUnit(yUnit);

		// create plot 构造散点图
		XYPlot plot = new XYPlot(xyDataset1, xAxis, yAxis, renderer1);

		// Add second data set and rendererScatter 添加第二个数据集
		XYItemRenderer renderer2 = new XYShapeRenderer();
		plot.setDataset(1, xyDataset2);
		plot.setRenderer(1, renderer2);

		JFreeChart chart = new JFreeChart("SinX Chart", JFreeChart.DEFAULT_TITLE_FONT, plot, true);

		ChartFrame frame = new ChartFrame("SinX 散点图", chart, true);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
