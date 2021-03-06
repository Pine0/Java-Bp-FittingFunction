import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Fitting class
 *
 * @author WangXuesong
 * @date 2019/04/26
 */
public class Fitting {

    /*------------------------------------------------基础矩阵计算部分--------------------------------------------------*/


    /**
     * learningRate 学习率
     * w1 输入层和隐藏层之间的权重
     * w2 隐藏层和输出层之间的权重
     * b1 输入层和隐藏层之间的偏置
     * b2 隐藏层和输出层之间的偏置
     * newY 保存当前输出的y值
     */
    private static double learningRate = 0;
    private static double[][] w1 = null;
    private static double[][] w2 = null;
    private static double[][] b1 = null;
    private static double[][] b2 = null;
    private static double[][] newY = null;

    /**
     * 初始化神经网络
     *
     * @param hiddenSize 隐藏层个数
     * @param outputSize 输出层个数
     * @param LR         学习率
     */
    Fitting(int inputSize, int hiddenSize, int outputSize, double LR) {
        w1 = random(inputSize, hiddenSize);
        b1 = zeros(1, hiddenSize);
        w2 = random(hiddenSize, outputSize);
        b2 = zeros(1, outputSize);
        learningRate = LR;
    }


    /**
     * 生成零数组
     *
     * @param len 生成零数组的高
     * @param wid 生成零数组的宽
     * @return 返回零数组
     */
    private static double[][] zeros(int len, int wid) {
        return new double[len][wid];
    }

    /**
     * 生成随机数组
     *
     * @param len 生成随机数组的高
     * @param wid 生成随机数组的宽
     * @return ranArr 返回随机数组
     */
    private static double[][] random(int len, int wid) {
        double[][] ranArr = new double[len][wid];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < wid; j++) {
                ranArr[i][j] = Math.random();
            }
        }
        return ranArr;
    }


    /**
     * 将两个矩阵点乘
     *
     * @param arr1 传入矩阵1
     * @param arr2 传入矩阵2
     * @return 返回相乘之后的结果
     */
    private static double[][] dot(double[][] arr1, double[][] arr2) {
        double[][] res = new double[arr1.length][arr2[0].length];

        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                double temp = 0;

                for (int k = 0; k < arr2.length; k++) {
                    temp += arr1[i][k] * arr2[k][j];
                }

                res[i][j] = temp;
            }
        }

        return res;

    }

    /**
     * 广播加计算函数
     *
     * @param arr1     传入数组
     * @param num/arr2 传入需要广播的数字/数组
     * @return 返回广播加后的数组
     */
    private static double[][] broadcastingAdd(double[][] arr1, double num) {
        double[][] temp = new double[arr1.length][arr1[0].length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                temp[i][j] = arr1[i][j] + num;
            }
        }
        return temp;
    }

    private static double[][] broadcastingAdd(double[][] arr1, double[] arr2) {
        double[][] temp = new double[arr1.length][arr1[0].length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                temp[i][j] = arr1[i][j] + arr2[i];
            }
        }
        return temp;
    }

    private static double[][] broadcastingAdd(double[][] arr1, double[][] arr2) {
        double[][] temp = new double[arr1.length][arr1[0].length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                temp[i][j] = arr1[i][j] + arr2[i % arr2.length][j % arr2[0].length];
            }
        }
        return temp;
    }


    /**
     * 广播减计算函数
     *
     * @param arr1     传入数组
     * @param num/arr2 传入需要广播的数字/数组
     * @return 返回广播减后的数组
     */
    private static double[][] broadcastingSub(double[][] arr1, double num) {
        double[][] temp = new double[arr1.length][arr1[0].length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                temp[i][j] = arr1[i][j] - num;
            }
        }
        return temp;
    }

    private static double[][] broadcastingSub(double[][] arr1, double[] arr2) {
        double[][] temp = new double[arr1.length][arr1[0].length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                temp[i][j] = arr1[i][j] - arr2[i];
            }
        }
        return temp;
    }

    private static double[][] broadcastingSub(double[][] arr1, double[][] arr2) {
        double[][] temp = new double[arr1.length][arr1[0].length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                temp[i][j] = arr1[i][j] - arr2[i % arr2.length][j % arr2[0].length];
            }
        }
        return temp;
    }

    /**
     * 矩阵平方函数
     *
     * @param arr1 传入矩阵
     * @return 返回每个元素平方后的数组
     */
    private static double[][] squra(double[][] arr1) {
        double[][] temp = new double[arr1.length][arr1[0].length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                temp[i][j] = arr1[i][j] * arr1[i][j];
            }
        }
        return temp;
    }

    /**
     * 广播除计算函数
     *
     * @param arr1 传入数组
     * @param num  传入需要广播的数字
     * @return 返回广播减后的数组
     */
    private static double[][] broadcastingDiv(double[][] arr1, double num) {
        double[][] temp = new double[arr1.length][arr1[0].length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                temp[i][j] = arr1[i][j] / num;
            }
        }
        return temp;
    }

    /**
     * 广播乘计算函数
     *
     * @param arr1     传入数组1
     * @param num/arr2 传入数/数组2
     * @return 返回广播减后的数组
     */
    private static double[][] broadcastingMult(double[][] arr1, double num) {
        double[][] temp = new double[arr1.length][arr1[0].length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                temp[i][j] = arr1[i][j] * num;
            }
        }
        return temp;
    }

    private static double[][] broadcastingMult(double[][] arr1, double[][] arr2) {
        double[][] temp = new double[arr1.length][arr1[0].length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                temp[i][j] = arr1[i][j] * arr2[i % arr2.length][j % arr2[0].length];
            }
        }
        return temp;
    }

    /**
     * sum函数
     * <p>
     * 将double[n][1]数组元素相加
     *
     * @param arr1 传入数组
     * @return 返回sum值
     */
    private static double[][] sum(double[][] arr1) {
        double[][] sum = new double[1][arr1[0].length];
        for (int j = 0; j < arr1[0].length; j++) {
            for (int i = 0; i < arr1.length; i++) {
                sum[0][j] += arr1[i][j];
            }
        }
        return sum;
    }

    /**
     * 求矩阵元素平均
     *
     * @param arr1 传入矩阵
     * @return 返回平均数
     */
    private static double mean(double[][] arr1) {
        return sum(arr1)[0][0] / arr1.length * arr1[0].length;
    }

    /**
     * 求矩阵的转制
     *
     * @param arr1 传入矩阵
     * @return 返回转制矩阵
     */
    private static double[][] transpose(double[][] arr1) {
        double[][] temp = new double[arr1[0].length][arr1.length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                temp[i][j] = arr1[j][i];
            }
        }
        return temp;
    }


    /*---------------------------------------------------IO操作部分----------------------------------------------------*/

    /**
     * 二维数组转字符串
     *
     * @param arr1 传入二维数组
     * @return 返回字符串
     */
    private static String array2String(double[][] arr1) {
        StringBuilder str = new StringBuilder();
        DecimalFormat df = new DecimalFormat("0.0000");
        str.append("[");
        for (int i = 0; i < arr1.length; i++) {
            str.append("[");
            for (int j = 0; j < arr1[0].length; j++) {
                str.append(df.format(arr1[i][j]));
                if (j < arr1[0].length - 1) {
                    str.append(", ");
                }
            }
            if (i < arr1.length - 1) {
                str.append("], ");
            } else {
                str.append("]");
            }

        }
        str.append("]");
        return str.toString();
    }

    /**
     * 文件输出函数
     *
     * @param out  传入IO对象
     * @param x 传入数组x
     * @param y 传入数组y
     * @throws IOException 抛出IO异常
     */
    private static void output(BufferedWriter out, double[][] x, double[][] y) throws IOException {
        out.write(array2String(x));
        out.newLine();
        out.write(array2String(y));
        out.newLine();
    }
    /**
     * 文件输出函数
     *
     * @param out 传入IO对象
     * @param num 传入数字
     * @throws IOException 抛出IO异常
     */
    private static void output(BufferedWriter out, double num) throws IOException {
        out.write(String.valueOf(num));
        out.newLine();
    }


    /*------------------------------------------------神经网络实现部分--------------------------------------------------*/


    /**
     * 获取sigmoid激活函数
     *
     * @param x 传入x数组
     * @return 返回f(x)数组
     */
    private static double[][] sigmoid(double[][] x) {
        double[][] temp = new double[x.length][x[0].length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                temp[i][j] = 1 / (1 + Math.exp(-x[i][j]));
            }
        }
        return temp;
    }

    /**
     * 获取sigmoid激活函数的偏导数
     *
     * @param x 传入x数组
     * @return 返回grad(x)数组
     */
    private static double[][] sigmoidGrad(double[][] x) {
        return broadcastingMult(broadcastingSub(sigmoid(x), 1), broadcastingMult(sigmoid(x), -1));
    }


    /**
     * 计算新y值函数
     *
     * @param x 传入横坐标集
     * @return 返回新y值集合
     */
    private static double[][] pridict(double[][] x) {
        double[][] a1 = broadcastingAdd(dot(x, w1), b1);
        double[][] z1 = sigmoid(a1);
        return broadcastingAdd(dot(z1, w2), b2);
    }

    /**
     * 损失函数
     *
     * @param x 传入横坐标集
     * @param y 传入纵坐标集
     * @return 返回新的y值和loss值
     */
    private static ArrayList<Object> loss(double[][] x, double[][] y) {
        ArrayList<Object> temp = new ArrayList<>(2);
        double[][] newY = pridict(x);
        temp.add(newY);
        temp.add(mean(squra(broadcastingSub(y, newY))));
        return temp;
    }

    /**
     * 计算BP网络梯度
     *
     * @param x 传入横坐标集
     * @param y 传入纵坐标集
     * @return wb 返回用于计算时新的权重和偏置的W1，W2，B1，B2。
     */
    private static ArrayList<double[][]> gradient(double[][] x, double[][] y) {
        ArrayList<double[][]> wb = new ArrayList<>(4);
        int batchNum = x.length;

        // 前向传播
        double[][] a1 = broadcastingAdd(dot(x, w1), b1);
        double[][] z1 = sigmoid(a1);
        double[][] a2 = broadcastingAdd(dot(z1, w2), b2);

        // 反向传播
        double[][] dy = broadcastingDiv(broadcastingSub(a2, y), batchNum);
        double[][] dz1 = dot(dy, transpose(w2));
        double[][] da1 = broadcastingMult(sigmoidGrad(a1), dz1);

        double[][] W1 = dot(transpose(x), da1);
        double[][] B1 = sum(da1);
        double[][] W2 = dot(transpose(z1), dy);
        double[][] B2 = sum(dy);

        wb.add(W1);
        wb.add(W2);
        wb.add(B1);
        wb.add(B2);

        return wb;
    }



    public double[][] getNewY() {
        return newY;
    }

    /**
     * 训练函数
     *
     * @param x       传入横坐标集合
     * @param y       传入纵坐标集合
     * @param maxStep 传入最大循环次数
     * @throws IOException 抛出IO异常
     */
    public void train(double[][] x, double[][] y, int maxStep, String fun) throws IOException {
        try (
                //目标函数数据输出文件，第一行为x集合，第二行为y集合。
                BufferedWriter funFile = new BufferedWriter(new FileWriter("./data/" + fun +".txt"));

                //拟合目标函数数据输出文件，基数行为x集合，偶数行为y集合。
                BufferedWriter fitFile = new BufferedWriter(new FileWriter("./data/" + fun + "_Fit.txt"));

                //loss值数据输出文件，每一行为每一次迭代的loss值，行数和maxStep一致。
                BufferedWriter lossValueFile = new BufferedWriter(new FileWriter("./data/" + fun + "_Loss.txt"));
        ) {

            //输出标准sin(x)函数的x集合和y集合。
            output(funFile, x, y);

            for (int i = 0; i < maxStep; i++) {
                //计算BP网络梯度
                ArrayList<double[][]> wb = gradient(x, y);

                //更新权重和偏置
                w1 = broadcastingSub(w1, broadcastingMult(wb.get(0), learningRate));
                w2 = broadcastingSub(w2, broadcastingMult(wb.get(1), learningRate));
                b1 = broadcastingSub(b1, broadcastingMult(wb.get(2), learningRate));
                b2 = broadcastingSub(b2, broadcastingMult(wb.get(3), learningRate));

                //获取新的y集合和loss值
                ArrayList<Object> predLoss = loss(x, y);
                newY = (double[][]) predLoss.get(0);
                double lossValue = Math.abs((double) predLoss.get(1));

                //将新的y集合和loss值输出到文件
                output(fitFile, x, newY);
                output(lossValueFile, lossValue);
            }
        }

    }
}


