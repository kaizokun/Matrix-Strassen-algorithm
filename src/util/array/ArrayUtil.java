package util.array;

import java.util.Arrays;

public class ArrayUtil {

    public static int[] maximalSubArray(int[] values){

        return maximalSubArray(values,0,values.length - 1);
    }

    public static int[] maximalSubArray(int[] values, int s, int e){

        SubArrayMax max = p_maximalSubArray(values,s,e);
        return Arrays.copyOfRange(values, max.iLeft, max.iRight + 1);
    }

    private static SubArrayMax p_maximalSubArray(int[] values, int s, int e){

        if(s == e){
            return new SubArrayMax(s, s, values[s]);
        }

        int m = (s + e) / 2;

        SubArrayMax left = p_maximalSubArray(values, s, m);
        SubArrayMax right = p_maximalSubArray(values, m + 1 , e);
        SubArrayMax middle = midlleSubTab(values, s, m, e);

        if(left.sum >= right.sum && left.sum >= middle.sum)
            return left;

        if(right.sum >= left.sum && right.sum >= middle.sum)
            return right;

        return middle;

    }


    private static SubArrayMax midlleSubTab(int[] values, int s, int m, int e){


        int leftSum = Integer.MIN_VALUE;
        int maxLeft = m;
        int sumL = 0;

        for( int i = m ; i >= s ; i -- ){
            sumL += values[i];
            if( sumL > leftSum ){
                leftSum = sumL;
                maxLeft = i;
            }
        }

        int rightSum = Integer.MIN_VALUE;
        int maxRight = m;
        int sumR = 0;

        for( int i = m + 1 ; i <= e ; i ++ ){
            sumR += values[i];
            if( sumR > rightSum ){
                rightSum = sumR;
                maxRight = i;
            }
        }

        return new SubArrayMax(maxLeft, maxRight, leftSum + rightSum);
    }

    private static class SubArrayMax {
        private int iRight, iLeft, sum;

        private SubArrayMax(int maxLeft, int maxRight, int sum) {
            this.iRight = maxRight;
            this.iLeft = maxLeft;
            this.sum = sum;
        }


    }


    public static void main(String[] args) {

        int[] tab = new int[]{13,-3,-25,2,-3,-16,-23,18,20,-7,12,-5,-22,15,-4,7};

        for(int v : maximalSubArray(tab))
            System.out.print(v+" ");

    }

}
