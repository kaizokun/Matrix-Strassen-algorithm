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

    public Matrix(Point p1, Point p2, Matrix original) {
        this.p1 = p1;
        this.p2 = p2;
        this.original = original;
        this.splited = true;
        this.r = p2.x - p1.x + 1;
        this.c = this.r;
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

    public int cote() {
        return (p2.x - p1.x) + 1;
    }

    public void showSplit() {

        System.out.println(toString());

        if (this.cote() == 1) {
            return;
        }

        for (Matrix m : this.splitFour()) {
            m.showSplit();
        }
    }

    private static Matrix strassen(Matrix a, Matrix b){


        Matrix c = new Matrix(a.r, a.c);

        //si matrice de taille 1
        if (a.r == 1) {

            c.matrix[0][0] = a.original.matrix[a.p1.y][a.p1.x] * b.original.matrix[b.p1.y][b.p1.x];

            // System.out.println(id+" Resultat Matrice");
            // System.out.println(mc.toString(id));

            return c;
        }

        Matrix[] A = a.splitFour();
        Matrix[] B = b.splitFour();
        Matrix[] C = c.splitFour();

        /*
        * 11 12
        * 21 22
        *
        * */

        final int i11 = 0, i12 = 1, i21 = 2, i22 = 3;

        int size = a.c / 2;

        try {

            Matrix s1 = new Matrix(size, size);
            Matrix s2 = new Matrix(size, size);
            Matrix s3 = new Matrix(size, size);
            Matrix s4 = new Matrix(size, size);
            Matrix s5 = new Matrix(size, size);
            Matrix s6 = new Matrix(size, size);
            Matrix s7 = new Matrix(size, size);
            Matrix s8 = new Matrix(size, size);
            Matrix s9 = new Matrix(size, size);
            Matrix s10 = new Matrix(size, size);

           // System.out.println("Test");
           // System.out.println(s1+" "+B[i12]+" "+B[i22]);
           // System.out.println("Test2");

            substract(s1, B[i12], B[i22]);
            add(s2, A[i11], A[i12]);
            add(s3,A[i21],A[i22]);
            substract(s4,B[i21],B[i11]);
            add(s5,A[i11],A[i22]);
            add(s6,B[i11],B[i22]);
            substract(s7,A[i12],A[i22]);
            add(s8,B[i21],B[i22]);
            substract(s9,A[i11],A[i21]);
            add(s10,B[i11],B[i12]);

            Matrix P1 = strassen(A[i11], s1);
            Matrix P2 = strassen(s2, B[i22]);
            Matrix P3 = strassen(s3, B[i11]);
            Matrix P4 = strassen(A[i22], s4);
            Matrix P5 = strassen(s5, s6);
            Matrix P6 = strassen(s7, s8);
            Matrix P7 = strassen(s9, s10);

            //C11
            add(C[i11],P5,P4);
            substract(C[i11],C[i11],P2);
            add(C[i11],C[i11],P6);

            //C12
            add(C[i12],P1,P2);

            //C21
            add(C[i21],P3,P4);

            //C22
            add(C[i22],P5,P1);
            substract(C[i22],C[i22],P3);
            substract(C[i22],C[i22],P7);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return c;

    }

    private static Matrix multiplyRec(Matrix ma, Matrix mb, int d) {

       /* String id = Util.getIdent(d);

        System.out.println(id+"xxxxxxxxxxxxxx Multiply xxxxxxxxxxxxxxxx ");
        System.out.println(ma.toString(id));
        System.out.println(mb.toString(id));
*/
        //nouvelle matrice de taille identique à celles reçus en parametre
        Matrix mc = new Matrix(ma.r, ma.c);

        //si matrice de taille 1
        if (ma.r == 1) {

            mc.matrix[0][0] = ma.original.matrix[ma.p1.y][ma.p1.x] * mb.original.matrix[mb.p1.y][mb.p1.x];

            // System.out.println(id+" Resultat Matrice");
            // System.out.println(mc.toString(id));

            return mc;
        }

        //decouper les deux matrices en quatres
        //[1][2]
        //[3][4]

        Matrix[] mas = ma.splitFour();
        Matrix[] mbs = mb.splitFour();

        //Créer une matrice vide de même taille que m1 et m2
        //multiplier les differentes parties de matrices
        //mc.1 = ma.1 * mb.1 + ma.2 * mb.3
        //mc.2 = ma.1 * mb.2 + ma.2 * mb.4
        //mc.3 = ma.3 * mb.1 + ma.4 * mb.3
        //mc.4 = ma.3 * mb.2 + ma.4 * mb.4

        Matrix mcs[] = mc.splitFour();

        Matrix.add(mcs[0], Matrix.multiplyRec(mas[0], mbs[0], d + 1), Matrix.multiplyRec(mas[1], mbs[2], d + 1));

        Matrix.add(mcs[1], Matrix.multiplyRec(mas[0], mbs[1], d + 1), Matrix.multiplyRec(mas[1], mbs[3], d + 1));

        Matrix.add(mcs[2], Matrix.multiplyRec(mas[2], mbs[0], d + 1), Matrix.multiplyRec(mas[3], mbs[2], d + 1));

        Matrix.add(mcs[3], Matrix.multiplyRec(mas[2], mbs[1], d + 1), Matrix.multiplyRec(mas[3], mbs[3], d + 1));
/*
        System.out.println(id+" ============= Resultat Matrice ============= ");
        System.out.println(mc.toString(id));
*/
        return mc;

    }

    private static void add(Matrix mc, Matrix ma, Matrix mb) {

        for (int i = 0; i < mc.r; i++) {

            int rc = mc.p1.y + i;
            int ia = ma.p1.y + i;
            int ib = mb.p1.y + i;

            for (int j = 0; j < mc.r; j++) {

                int cc = mc.p1.x + j;
                int ja = ma.p1.x + j;
                int jb = mb.p1.x + j;

                mc.original.matrix[rc][cc] = ma.original.matrix[ia][ja] + mb.original.matrix[ib][jb];
            }
        }

    }

    private static void substract(Matrix mc, Matrix ma, Matrix mb) {

        for (int i = 0; i < mc.r; i++) {

            int rc = mc.p1.y + i;
            int ia = ma.p1.y + i;
            int ib = mb.p1.y + i;

            for (int j = 0; j < mc.r; j++) {

                int cc = mc.p1.x + j;
                int ja = ma.p1.x + j;
                int jb = mb.p1.x + j;

                mc.original.matrix[rc][cc] = ma.original.matrix[ia][ja] - mb.original.matrix[ib][jb];
            }
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

    private Matrix[] splitFour() {

        Matrix parts[] = new Matrix[4];

        int length = (p2.x - p1.x) / 2;

        //matrice haut gauche
        /*   0  1  2  3
        *  0[1][ ][ ][ ]
        *  1[ ][2][ ][ ]
        *  2[ ][ ][ ][ ]
        *  3[ ][ ][ ][ ]
        * */

        Point m1P1 = new Point(p1.x, p1.y);
        Point m1P2 = new Point(p1.x + length, p1.y + length);

        parts[0] = new Matrix(m1P1, m1P2, this.original);

        //matrice haut droite
        /*   0  1  2  3
        *  0[ ][ ][1][ ]
        *  1[ ][ ][ ][2]
        *  2[ ][ ][ ][ ]
        *  3[ ][ ][ ][ ]
        * */
        Point m2P1 = new Point(m1P1.x + length + 1, m1P1.y);
        Point m2P2 = new Point(p2.x, m1P2.y);

        parts[1] = new Matrix(m2P1, m2P2, this.original);

        //matrice bas gauche
        /*   0  1  2  3
        *  0[ ][ ][ ][ ]
        *  1[ ][ ][ ][ ]
        *  2[1][ ][ ][ ]
        *  3[ ][2][ ][ ]
        * */
        Point m3P1 = new Point(p1.x, m1P1.y + length + 1);
        Point m3P2 = new Point(m1P2.x, p2.y);

        parts[2] = new Matrix(m3P1, m3P2, this.original);

        //matrice bas droite
        /*   0  1  2  3
        *  0[ ][ ][ ][ ]
        *  1[ ][ ][ ][ ]
        *  2[ ][ ][1][ ]
        *  3[ ][ ][ ][2]
        * */
        Point m4P1 = new Point(m3P1.x + length + 1, m3P1.y);
        Point m4P2 = new Point(p2.x, p2.y);

        parts[3] = new Matrix(m4P1, m4P2, this.original);

        return parts;

    }


    public String toString(String dec) {

        StringBuilder builder = new StringBuilder();

        //StringBuilder builder = new StringBuilder((r*c) + (r*c*2) + r);

        builder.append("\n" + dec + "Matrice : " + r + " x " + c + "\n");

        builder.append(dec + "P1 : " + p1 + " - P2 " + p2 + "\n\n");

        for (int i = this.p1.y; i <= this.p2.y; i++) {
            builder.append(dec);
            for (int j = this.p1.x; j <= this.p2.x; j++) {
                //builder.append('[');
                builder.append(String.format("[%3.0f]", this.original.matrix[i][j]));
                //builder.append(']');
            }
            builder.append('\n');
        }

        return builder.toString();
    }

    @Override
    public String toString() {

        return toString("");

    }


    public static void main(String[] args) {



       testMulRecStrassen();
       testMulRec();

    }

    public static void testMulRecStrassen() {

        /*
        *
        *
        * 	C1	C2	C3	C4
        1	34	44	54	64
        2	82	108	134	160
        3	34	44	54	64
        4	82	108	134	160

        * */

        try {

            Matrix m1 = new Matrix(new double[][]{
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {1, 2, 3, 4},
                    {5, 6, 7, 8}});

            Matrix m2 = new Matrix(new double[][]{
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {1, 2, 3, 4},
                    {5, 6, 7, 8}});

            Matrix m3 = Matrix.strassen(m1, m2);

            System.out.println(m3);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void testMulRec() {

        /*
        *
        *
        * 	C1	C2	C3	C4
        1	34	44	54	64
        2	82	108	134	160
        3	34	44	54	64
        4	82	108	134	160

        * */

        try {

            Matrix m1 = new Matrix(new double[][]{
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {1, 2, 3, 4},
                    {5, 6, 7, 8}});

            Matrix m2 = new Matrix(new double[][]{
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {1, 2, 3, 4},
                    {5, 6, 7, 8}});

            Matrix m3 = Matrix.multiplyRec(m1, m2, 0);

            System.out.println(m3);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void splitTest() {

        try {

            Matrix m1 = new Matrix(new double[][]{{1, 2, 3, 4},
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
