import draw.LossChart;
import draw.FunChart;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * Function class
 *
 * @author WangXuesong
 * @date 2019/04/26
 */
public class Function {

    /**
     * modeMap: 模式字典
     * inputSize: 输入层大小
     * hiddenSize: 隐藏层大小
     * outputSize: 输出层大小
     * learningRate: 学习率
     * start: 自变量开始值
     * end: 自变量结束值
     * count: 选取点数量
     * mode: 模式值
     */
    private static HashMap<Integer, String> modeMap= new HashMap<>(3);
    private static int inputSize;
    private static int hiddenSize;
    private static int outputSize;
    private static double learningRate;
    private static double start;
    private static double end;
    private static int count;
    private static int mode;
    private static int maxStep;

    /**
     * 初始化每个模式对应的函数
     */
    static {
        modeMap.put(0, "X1+X2");
        modeMap.put(1, "SinX");
        modeMap.put(2, "X^2");
    }


    /*-------------------------------------------------公共函数部分-----------------------------------------------------*/
    /**
     * 指定的间隔内返回均匀间隔的数字
     *
     * @param start 区间开始数
     * @param end 区间结束数
     * @param count 等间距选取数个数
     * @return arr 返回double类型的数组
     */
    private static double[][] linspace(double start, double end, int count, int size) {
        double[][] arr = new double[count][size];
        double increment = (end - start) / (count - 1);

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < size; j++) {
                arr[i][j] = start + increment * i;
            }
        }

        return arr;
    }

    /**
     * 产生x，y数据集
     *
     * @param start 横坐标开始位
     * @param end 横坐标结束位
     * @param count 点数量
     * @return 返回x，y数组
     */
    private static ArrayList<double[][]> generateData(double start, double end, int count, int mode) {
        ArrayList<double[][]> temp = new ArrayList<>(2);
        double[][] x = linspace(start, end, count, inputSize);
        double[][] y = mode == 0 ? new double[x.length][x.length] : new double[x.length][1];

        for (int i = 0; i < y.length; i++) {
           for (int j = 0; j < y[0].length; j++) {
              switch (mode) {
                  case 0:
                      y[i][j] = add(x[i][0], x[j][1]);
                      break;
                  case 1:
                      y[i][j] = sin(x[i][j]);
                      break;
                  default:
                      y[i][j] = square(x[i][j]);
              }
           }
        }
        temp.add(x);
        temp.add(y);
        return temp;
    }


    /*-------------------------------------------------目标函数部分-----------------------------------------------------*/


    /**
     * 获取sin函数值
     *
     * @param x 传入x
     * @return 返回sin(x)
     */
    private static double sin(double x) {
        return Math.sin(x);
    }

    /**
     * 获取x^2函数值
     *
     * @param x 传入x
     * @return 返回f(x)
     */
    private static double square(double x) {
        return Math.pow(x, 2);
    }

    /**
     * 获取(x1 + x2)函数值
     *
     * @param x1 传入x1
     * @param x2 传入x2
     * @return 返回f(x1, x2)
     */
    private static double add(double x1, double x2) {
        return x1 + x2;
    }

    /*-------------------------------------------------拟合函数部分-----------------------------------------------------*/

    public static String fit() throws IOException {
        //获取开始时间
        long startTime = System.currentTimeMillis();

        ClassLoader classLoader = Function.class.getClassLoader();

        InputStream in = classLoader.getResourceAsStream("conf/network.properties");

        Properties pros = new Properties();

        pros.load(in);


        inputSize = Integer.valueOf(pros.getProperty("inputSize"));
        hiddenSize = Integer.valueOf(pros.getProperty("hiddenSize"));
        outputSize = Integer.valueOf(pros.getProperty("outputSize"));
        learningRate = Double.valueOf(pros.getProperty("learningRate"));
        start = Double.valueOf(pros.getProperty("start"));
        end = Double.valueOf(pros.getProperty("end"));
        count = Integer.valueOf(pros.getProperty("count"));
        mode = Integer.valueOf(pros.getProperty("mode"));
        maxStep = Integer.valueOf(pros.getProperty("maxStep"));


        // 初始化神经网络数据
        Fitting network = new Fitting(inputSize, hiddenSize, outputSize, learningRate);


        //产生等间距数据
        ArrayList<double[][]> xyData = generateData(start, end, count, mode);

        double[][] x = xyData.get(0);
        double[][] y = xyData.get(1);

        //开始训练，最大训练次数10000
        network.train(x, y, maxStep, modeMap.get(mode));

        //输出图像y=sin(x)和y=sin(x)拟合曲线,loss值曲线
        FunChart.showChart(x,y,network.getNewY(), modeMap.get(mode));

        LossChart.showChart();

        return String.valueOf(System.currentTimeMillis() - startTime);

    }


    /*-------------------------------------------------main函数部分----------------------------------------------------*/

    public static void main(String[] args) throws IOException {

        //从配置文件中读取配置信息进行训练
        String runTime = fit();

        //控制台打印总用时
        System.out.println("训练过程结束，共用时：" + runTime + "ms。");
    }
}

