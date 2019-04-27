public class SinFunction {
    /**
     * @author WangXuesong
     * @date 2019-04-26 11:10
     */


    /**
     * HIDDEN_SIZE 隐藏层大小
     * OUTPUT_SIZE 输出层大小
     * w1 输入层和隐藏层之间的权重
     * w2 隐藏层和输出层之间的权重
     * b1 输入层和隐藏层之间的偏置
     * b2 隐藏层和输出层之间的偏置
     */
    private static int HIDDEN_SIZE = 0;
    private static int OUTPUT_SIZE = 0;
    private double[][] w1 = null;
    private double[][] w2 = null;
    private double[][] b1 = null;
    private double[][] b2 = null;

    /**
     * 初始化神经网络
     *
     * @param hidden 隐藏层个数
     * @param output 输出层个数
     */
    SinFunction(int hidden, int output) {
        HIDDEN_SIZE = hidden;
        OUTPUT_SIZE = output;
        w1 = random(1, HIDDEN_SIZE);
        b1 = zeros(HIDDEN_SIZE, 1);
        w2 = random(HIDDEN_SIZE, OUTPUT_SIZE);
        b2 = zeros(OUTPUT_SIZE, 1);

    }


    /**
     * 指定的间隔内返回均匀间隔的数字
     *
     * @param start 区间开始数
     * @param end 区间结束数
     * @param num 等间距选取数个数
     * @return arr 返回double类型的数组
     */
    private static double[][] linspace(int start, int end, int num) {
        double[][] arr = new double[num][1];
        double increment = (double) (end - start) / (num - 1);

        for (int i = 0; i < num; i++) {
            arr[i][0] = start + increment * i;
        }

        return arr;
    }

    /**
     * 生成零数组
     *
     * @param len 生成零数组的高
     * @param wid 生成零数组的宽
     * @return 返回零数组
     */
    private static double[][] zeros(int len, int wid) {
        assert len * wid != 0;

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
        assert len * wid != 0;

        double[][] ranArr = new double[len][wid];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < wid; j++) {
                ranArr[i][j] = Math.random();
            }
        }
        return ranArr;
    }

    /**
     * 获取sin函数值
     * @param x 传入x
     * @return 返回f(x)
     */
    private static double sin(double x) {
        return Math.sin(x);
    }

    /**
     * 获取sigmod激活函数
     * @param x 传入x
     * @return 返回f(x)
     */
    private static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    /**
     * 获取sigmod激活函数的偏导数
     * @param x 传入x
     * @return 返回grad(x)
     */
    private static double sigmodGrad(double x) {
        return Math.exp(-x) / Math.pow(1 + Math.exp(-x), 2);
    }

    /**
     * 将两个矩阵点乘
     * @param arr1 传入矩阵1
     * @param arr2 传入矩阵2
     * @return 返回相乘之后的结果
     */
    private static double[][] dot (double[][] arr1, double[][] arr2) {
        assert arr1.length != 0 && arr2.length != 0 && arr1[0].length == arr2.length;
        double[][] res = new double[arr1.length][arr2[0].length];

        for(int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                double temp = 0;

                for(int k = 0; k < arr2.length; k++) {
                    temp += arr1[i][k] * arr2[k][j];
                }

                res[i][j] = temp;
            }
        }

        return res;

    }

    /**
     * 广播加计算函数
     * @param arr1 传入数组
     * @param num/arr2 传入需要广播的数字/数组
     * @return 返回广播加后的数组
     */
    private static double[][] broadcastingAdd(double[][] arr1, double num) {
        assert arr1.length != 0;
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr1[0].length; j++) {
                arr1[i][j] += num;
            }
        }
        return arr1;
    }
    private static double[][] broadcastingAdd(double[][] arr1, double[] arr2) {
        assert arr1.length != 0 && arr2.length != 0;
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr1[0].length; j++) {
                arr1[i][j] += arr2[i];
            }
        }
        return arr1;
    }
    private static double[][] broadcastingAdd(double[][] arr1, double[][] arr2) {
        assert arr1.length != 0 && arr2.length != 0 &&
                arr1.length % arr2.length == 0 && arr1[0].length % arr2[0].length != 0;
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr1[0].length; j++) {
                arr1[i][j] += arr2[i % arr2.length][j % arr2[0].length];
            }
        }
        return arr1;
    }

    /**
     * 将double[n][1]数组元素相加
     * @param arr1 传入数组
     * @return 返回sum值
     */
    private static double sum(double[][] arr1) {
        assert arr1[0].length == 1;
        double sum = 0;
        for (int i = 0; i< arr1.length; i++) {
            sum += arr1[i][0];
        }
        return sum;
    }


    public static void main(String[] args) {
        double[][] x = linspace(-3, 3, 100);
        double[][] y = zeros(100, 1);

        double[][] arr1 = random(4, 4);
        double[][] arr2 = random(4, 1);
        double[][] arr3 = broadcastingAdd(arr1, arr2);
        System.out.println(arr1);
    }
}
