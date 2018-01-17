package math;

public class Test {

    public static int addition(int a, int b){

        if(a == 0){
            return b;
        }

        return successor(addition(a - 1, b));

    }

    public static int successor(int a){
        return a + 1;
    }

    public static void main(String[] args) {
        System.out.println(addition(15,15));
    }

}
