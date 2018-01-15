package math;

public class Matrix {

    private int r,c;
    private double [][] matrix;

    public Matrix(double[][] matrix) throws Exception {

        this.matrix = matrix;

        this.r = matrix.length;

        if( this.r < 1 ) {
            throw new Exception("Invalid number of rows");
        }

        this.c = matrix[0].length;

        //tester si tout les lignes ont le meme nombre de colones superieure à zero

    }

    public Matrix(int r, int c){
        this.r = r;
        this.c = c;
        this.matrix = new double[r][c];
    }

    static public Matrix multiply(Matrix m1, Matrix m2) throws Exception {
        return m1.multiply(m2);
    }

    public Matrix multiply(Matrix m2) throws Exception {

        if( this.c == m2.r ){


            Matrix matrixRs = new Matrix(this.r, m2.c);

            for( int y = 0 ; y < this.r ; y ++ ){

                for( int x = 0 ; x < m2.c ; x ++){

                    double sum = 0;

                    for( int i = 0 ; i < this.c ; i ++ ){
                        sum += this.matrix[y][i] * m2.matrix[i][x];
                    }

                    matrixRs.setValue(y,x,sum);

                }

            }


            return matrixRs;

        }else{
            throw new Exception("The number of columns of m1 doesn't correspond to the number of rows of m2");
        }

    }


    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        //StringBuilder builder = new StringBuilder((r*c) + (r*c*2) + r);

        for( int i = 0 ; i < this.r ; i ++){
            for( int j = 0 ; j < this.c ; j ++){
                builder.append('[');
                builder.append(this.matrix[i][j]);
                builder.append(']');
            }
            builder.append('\n');
        }

        return builder.toString();

    }

    public void setValue(int y, int x, double value) throws Exception {

        if( y < 0 || y >= this.r || x < 0 || x >= this.c ){
            throw new Exception("Out of range, matrice("+r+","+c+")");
        }

        this.matrix[y][x] = value;

    }

    public static void main(String[] args) {


        try {

            Matrix m1 = new Matrix(new double[][]
                            {{1, 2, 3},
                             {4, 5, 6}});

            Matrix m2 = new Matrix(new double[][]
                            {{1, 2, 3, 4},
                             {5, 6, 7, 8},
                             {9, 10, 11, 12}});


            System.out.println(m1);

            System.out.println(m2);

            System.out.println(m1.multiply(m2));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
