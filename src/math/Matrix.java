package math;

import util.array.Util;

import java.awt.*;

public class Matrix {


    private boolean splited;
    private Matrix original;
    private int r, c;
    private double[][] matrix;
    private Point p1, p2;

    public Matrix(double[][] matrix) throws Exception {

        this.matrix = matrix;

        this.r = matrix.length;

        if (this.r < 1) {
            throw new Exception("Invalid number of rows");
        }

        this.c = matrix[0].length;

        this.p1 = new Point(0, 0);
        this.p2 = new Point(c - 1, r - 1);
        this.original = this;

        //tester si tout les lignes ont le meme nombre de colones superieure à zero

    }

    public Matrix(int r, int c) {
        this.r = r;
        this.c = c;
        this.matrix = new double[r][c];

        this.p1 = new Point(0, 0);
        this.p2 = new Point(c - 1, r - 1);
        this.original = this;
    }

    public Matrix (Point p1, Point p2, Matrix original){
        this.p1 = p1;
        this.p2 = p2;
        this.original = original;
        this.splited = true;
    }

    static public Matrix multiply(Matrix m1, Matrix m2) throws Exception {
        return m1.multiply(m2);
    }

    /**
     * O(N^3)
     */
    public Matrix multiply(Matrix m2) throws Exception {

        if (this.c == m2.r) {

            Matrix matrixRs = new Matrix(this.r, m2.c);

            for (int y = 0; y < this.r; y++) {

                for (int x = 0; x < m2.c; x++) {

                    double sum = 0;

                    for (int i = 0; i < this.c; i++) {
                        sum += this.matrix[y][i] * m2.matrix[i][x];
                    }

                    matrixRs.setValue(y, x, sum);

                }

            }

            return matrixRs;

        } else {
            throw new Exception("The number of columns of m1 doesn't correspond to the number of rows of m2");
        }

    }


    public Matrix multiplyRec(Matrix m2) throws Exception {

        if (!(this.r == this.c && m2.r == m2.c && this.r == m2.r)) {
            throw new Exception("Ne fonctionne qu'avec des matrices carrés");
        }

        Matrix m3 = new Matrix(this.r, this.r);

        // multiplyRec(this, m2, m3, new Point(0,0), new Point(c - 1,r - 1));
        multiplyRec(this, m2, m3, new Point(0, 0), new Point(4, 4), 0);
        return m3;
    }

    private static Matrix multiplyRec(Matrix m1, Matrix m2, Matrix m3, Point p1, Point p2, int d) {

        String ident = Util.getIdent(d);

        if (p1.equals(p2)) {
            System.out.println(ident + " FINAL - " + p1 + " " + p2);
            return null;
        }

        return null;

    }

    public int cote(){
        return  (p2.x - p1.x) + 1;
    }

    public void showSplit(){

        System.out.println(toString());

        if(this.cote() == 1){
            return;
        }

        for(Matrix m : this.split()){
            m.showSplit();
        }
    }

/*
    private static Matrix multiplyRec(Matrix m1, Matrix m2, Matrix m3, Point p1, Point p2, int d){

        String ident = Util.getIdent(d);

        if(p1.equals(p2)){
            System.out.println(ident+" FINAL - "+p1+" "+p2);
            return null;
        }

        int cote = ( p2.x - p1 .x ) / 2;

        //matrice haut gauche

        int m1p1x = p1.x, m1p1y = p1.y;
        int m1p2x = p1.x + cote, m1p2y = p1.y + cote;

        Point m1P1 = new Point(m1p1x, m1p1y);
        Point m1P2 = new Point(m1p2x, m1p2y);

        System.out.println(ident+"M1 "+m1P1+" "+m1P2);

        multiplyRec(m1,m2,m3,m1P1,m1P2,d+1);

        //matrice haut droite

        int m2P1x = m1P1.x + cote + 1;
        int m2P1y = m1p1y;
        int m2P2x = p2.x;
        int m2P2y = m1P2.y;

        if(m2P1x <= p2.x) {

            Point m2P1 = new Point(m2P1x, m2P1y);
            Point m2P2 = new Point(m2P2x, m2P2y);

            System.out.println(ident + "M2 " + m2P1 + " " + m2P2);

            multiplyRec(m1, m2, m3, m2P1, m2P2, d + 1);

        }

        //matrice bas droite
        int m3P1x = p1.x;
        int m3P1y = m1P1.y + cote + 1;
        int m3P2x = m1P2.x;
        int m3P2y = p2.y;

        if(m3P1y <= p2.y) {

            Point m3P1 = new Point(m3P1x, m3P1y);
            Point m3P2 = new Point(m3P2x, m3P2y);

            System.out.println(ident + "M3 " + m3P1 + " " + m3P2);

            multiplyRec(m1, m2, m3, m3P1, m3P2, d + 1);

        }

        int m4P1x = m3P1x + cote + 1, m4P1y = m3P1y;
        int m4P2x = p2.x, m4P2y = p2.y;

        if(m4P1x <= p2.x && m4P1y <= p2.y) {

            Point m4P1 = new Point(m4P1x, m4P1y);
            Point m4P2 = new Point(m4P2x, m4P2y);

            System.out.println(ident + "M4 " + m4P1 + " " + m4P2);

            multiplyRec(m1, m2, m3, m4P1, m4P2, d + 1);

        }

        return null;

    }
*/

    public void setValue(int y, int x, double value) throws Exception {

        if (y < 0 || y >= this.r || x < 0 || x >= this.c) {
            throw new Exception("Out of range, matrice(" + r + "," + c + ")");
        }

        this.matrix[y][x] = value;

    }

    private Matrix[] split() {

        Matrix parts[] = new Matrix[4];

        int cote = (p2.x - p1.x) / 2;

        //matrice haut gauche

        int m1p1x = p1.x, m1p1y = p1.y;
        int m1p2x = p1.x + cote, m1p2y = p1.y + cote;

        Point m1P1 = new Point(m1p1x, m1p1y);
        Point m1P2 = new Point(m1p2x, m1p2y);

        parts[0] = new Matrix(m1P1, m1P2, this.original);

        //matrice haut droite

        int m2P1x = m1P1.x + cote + 1;
        int m2P1y = m1p1y;
        int m2P2x = p2.x;
        int m2P2y = m1P2.y;

        Point m2P1 = new Point(m2P1x, m2P1y);
        Point m2P2 = new Point(m2P2x, m2P2y);

        parts[1] = new Matrix(m2P1, m2P2, this.original);

        //matrice bas gauche

        int m3P1x = p1.x;
        int m3P1y = m1P1.y + cote + 1;
        int m3P2x = m1P2.x;
        int m3P2y = p2.y;

        Point m3P1 = new Point(m3P1x, m3P1y);
        Point m3P2 = new Point(m3P2x, m3P2y);

        parts[2] = new Matrix(m3P1, m3P2, this.original);

        //matrice bas droite

        int m4P1x = m3P1x + cote + 1, m4P1y = m3P1y;
        int m4P2x = p2.x, m4P2y = p2.y;

        Point m4P1 = new Point(m4P1x, m4P1y);
        Point m4P2 = new Point(m4P2x, m4P2y);

        parts[3] = new Matrix(m4P1, m4P2, this.original);

        return parts;

    }




    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        //StringBuilder builder = new StringBuilder((r*c) + (r*c*2) + r);

        for (int i = this.p1.y; i <= this.p2.y; i++) {
            for (int j = this.p1.x; j <= this.p2.x; j++) {
                //builder.append('[');
                builder.append(String.format("[%2.0f]",this.original.matrix[i][j]));
                //builder.append(']');
            }
            builder.append('\n');
        }

        return builder.toString();

    }


    public static void main(String[] args) {

        splitTest();

    }

    public static void splitTest(){

        try {

            Matrix m1 = new Matrix(new double[][]{  {1, 2, 3, 4},
                                                    {5, 6, 7, 8},
                                                    {9, 10, 11, 12},
                                                    {13, 14, 15, 16}});

            m1.showSplit();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void testMatrixMulRec() {


        try {

            Matrix m1 = new Matrix(new double[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}});
            Matrix m2 = new Matrix(new double[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}});

            m1.multiplyRec(m2);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void testMatrixMul() {


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

            System.out.println(Matrix.multiply(m1, m2));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
