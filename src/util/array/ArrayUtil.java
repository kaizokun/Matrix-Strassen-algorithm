package util.array;


public class ArrayUtil {


    /**
     * O(n)
     */
    public static SubArrayMax maximalSubArrayV2(double[] values) {

        int i,//current index
                s,//potentiel start
                r,//right index of tab max
                l,//left index of tab max
                max,//max sum
                sum;//current sum

        sum = max = i = r = l = s = 0;

        while (i < values.length) {

            sum += values[i];

            if (sum > max) {

                max = sum;

                //change only if we find a new max after a negative sum
                l = s;

                r = i;

            } else if (sum < 0) {
                //if the sum is negative with restart just after the current index i
                //pour the new potential start and a null sum
                sum = 0;
                s = i + 1;
            }

            i++;
        }

        return new SubArrayMax(l, r, max);

    }

    /**
     * sub tab max
     * O(n log n)
     */
    public static SubArrayMax maximalSubArray(double[] values) {
        return maximalSubArray(values, 0, values.length - 1);
    }

    /**
     * sub tab max
     * O(n log n)
     */
    public static SubArrayMax maximalSubArray(int[] values) {

        double values_d[] = new double[values.length];

        for (int i = 0; i < values.length; i++) {
            values_d[i] = values[i];
        }

        return maximalSubArray(values_d, 0, values.length - 1);
    }


    public static SubArrayMax maximalSubArray(double values[], int s, int e) {

        return p_maximalSubArray(values, s, e);
    }

    private static SubArrayMax p_maximalSubArray(double values[], int s, int e) {

        if (s == e) {
            return new SubArrayMax(s, e, values[s]);
        }

        int m = (s + e) / 2;

        SubArrayMax left = p_maximalSubArray(values, s, m);
        SubArrayMax right = p_maximalSubArray(values, m + 1, e);
        SubArrayMax middle = middleSubTab(values, s, m, e);


        if (left.sum >= right.sum && left.sum >= middle.sum)
            return left;

        if (right.sum >= left.sum && right.sum >= middle.sum)
            return left;

        return middle;

    }


    private static SubArrayMax middleSubTab(double values[], int s, int m, int e) {


        double leftSum = Double.MIN_VALUE;
        int maxLeft = m;
        double sumL = 0;

        for (int i = m; i >= s; i--) {
            sumL = sumL + values[i];
            if (sumL > leftSum) {
                leftSum = sumL;
                maxLeft = i;
            }
        }

        double rightSum = Double.MIN_VALUE;
        int maxRight = m;
        double sumR = 0;

        for (int i = m + 1; i <= e; i++) {
            sumR = sumR + values[i];
            if (sumR > rightSum) {
                rightSum = sumR;
                maxRight = i;
            }
        }

        return new SubArrayMax(maxLeft, maxRight, leftSum + rightSum);
    }

    private static class SubArrayMax {
        private int iRight, iLeft;
        private double sum;

        private SubArrayMax(int maxLeft, int maxRight, double sum) {
            this.iRight = maxRight;
            this.iLeft = maxLeft;
            this.sum = sum;
        }

        @Override
        public String toString() {
            return "SubArrayMax{" +
                    "iLeft=" + iLeft +
                    ", iRight=" + iRight +
                    ", sum=" + sum +
                    '}';
        }
    }


    public static void main(String[] args) {

        intTabMax();
        doubleTabMax();
        doubleTabMaxV2();
    }

    private static void doubleTabMaxV2() {
        double[] tab = new double[]{13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7};
        System.out.println(maximalSubArrayV2(tab));
    }


    private static void intTabMax() {
        int[] tab = new int[]{13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7};
        System.out.println(maximalSubArray(tab));
    }

    private static void doubleTabMax() {
        double[] tab = new double[]{13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7};
        System.out.println(maximalSubArray(tab));
    }

}
