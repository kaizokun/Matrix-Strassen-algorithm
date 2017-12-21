package util.array;


public class ArrayUtil {


    public static SubArrayMax maximalSubArray(double[] values){
        return maximalSubArray(new DoubleTab(values),0,values.length - 1);
    }

    public static SubArrayMax maximalSubArray(int[] values){
        return maximalSubArray(new IntegerTab(values),0,values.length - 1);
    }


    public static SubArrayMax maximalSubArray(GenericNumberTab values, int s, int e){

        return p_maximalSubArray(values,s,e);
    }

    private static SubArrayMax p_maximalSubArray(GenericNumberTab values, int s, int e){

        if(s == e){
            return new SubArrayMax(s, s, values.get(s));
        }

        int m = (s + e) / 2;

        SubArrayMax left = p_maximalSubArray(values, s, m);
        SubArrayMax right = p_maximalSubArray(values, m + 1 , e);
        SubArrayMax middle = middleSubTab(values, s, m, e);


        if(values.compare(left.sum, right.sum) >= 0 && values.compare(left.sum, middle.sum) >= 0)
            return left;

        if(values.compare(right.sum, left.sum) >= 0 && values.compare(right.sum, middle.sum) >= 0)
            return left;

        return middle;

    }


    private static SubArrayMax middleSubTab(GenericNumberTab values, int s, int m, int e){


        Comparable leftSum = values.getMin();
        int maxLeft = m;
        Comparable sumL = values.getZero();

        for( int i = m ; i >= s ; i -- ){
            sumL = values.add(sumL, values.get(i));
            if( values.compare(sumL,leftSum) > 0 ){
                leftSum = sumL;
                maxLeft = i;
            }
        }

        Comparable rightSum = values.getMin();
        int maxRight = m;
        Comparable sumR = values.getZero();

        for( int i = m + 1 ; i <= e ; i ++ ){
            sumR = values.add(sumR, values.get(i));
            if( values.compare(sumR,rightSum) > 0 ){
                rightSum = sumR;
                maxRight = i;
            }
        }

        return new SubArrayMax(maxLeft, maxRight, values.add(leftSum, rightSum));
    }

    private static abstract class GenericNumberTab<T>{

        abstract Comparable get(int i);
        abstract Comparable add(T n1, T n2);
        abstract Comparable getMin();
        abstract Comparable getZero();

        public int compare(Comparable n1, Comparable n2) {
            return n1.compareTo(n2);
        }

    }

    private static class DoubleTab extends GenericNumberTab<Double>{

        private double[] tab;

        public DoubleTab(double[] tab) {
            this.tab = tab;
        }

        @Override
        public Double get(int i) {
            return tab[i];
        }

        @Override
        public Double add(Double n1, Double n2) {
            return n1 + n2;
        }

        @Override
        public Double getMin() {
            return Double.NEGATIVE_INFINITY;
        }

        @Override
        public Double getZero() {
            return 0.0;
        }
    }

    private static class IntegerTab extends GenericNumberTab<Integer>{

        private int[] tab;

        public IntegerTab(int[] tab) {
            this.tab = tab;
        }

        @Override
        public Integer get(int i) {
            return tab[i];
        }

        @Override
        public Integer add(Integer n1, Integer n2) {
            return n1 + n2;
        }

        @Override
        public Integer getMin() {
            return Integer.MIN_VALUE;
        }

        @Override
        public Integer getZero() {
            return 0;
        }
    }

    private static class SubArrayMax {
        private int iRight, iLeft;
        private Comparable sum;

        private SubArrayMax(int maxLeft, int maxRight, Comparable sum) {
            this.iRight = maxRight;
            this.iLeft = maxLeft;
            this.sum = sum;
        }

        @Override
        public String toString() {
            return "SubArrayMax{" +
                    "iRight=" + iRight +
                    ", iLeft=" + iLeft +
                    ", sum=" + sum +
                    '}';
        }
    }


    public static void main(String[] args) {

      intTabMax();
       //doubleTabMax();

    }

    private static void intTabMax(){
        int[] tab = new int[]{13,-3,-25,20,-3,-16,-23,18,20,-7,12,-5,-22,15,-4,7};
        System.out.println(maximalSubArray(tab));
    }

    private static void doubleTabMax(){
        double[] tab = new double[]{13,-3,-25,20,-3,-16,-23,18,20,-7,12,-5,-22,15,-4,7};
        System.out.println(maximalSubArray(tab));
    }

}
