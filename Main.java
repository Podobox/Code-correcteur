import java.util.*;
import java.io.*;

public class Main {

    public static Matrix loadMatrix(String file, int r, int c) {
        byte[] tmp =  new byte[r * c];
        byte[][] data = new byte[r][c];
        try {
            FileInputStream fos = new FileInputStream(file);
            fos.read(tmp);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < r; i++)
            for (int j = 0; j< c; j++)
                data[i][j] = tmp[i * c + j];
            return new Matrix(data);
    }
    public static void displayVectorAsMatrix(byte[] v) {
        System.out.print("[[");
        for (int i = 0; i < v.length; i++) {
            System.out.print(v[i]);
            if (i < v.length - 1) {
                System.out.print(" ");
            }
        }
        System.out.println("]]");
    }

    public static void partie1(){
        System.out.println("\n\n\n---------------------------------------------Partie 1---------------------------------------------\n");
        System.out.println("\nMatrice de controle H : ");
        Matrix hbase = loadMatrix("data/matrix-15-20-3-4", 15, 20);
        hbase.display();

        System.out.println("\nForme systématique de H : ");
        Matrix sysH = hbase.sysTransform();
        sysH.display();


        System.out.println("\nMatrice génératrice G :");
        Matrix g = sysH.genG();
        g.display();


        Matrix u = new Matrix(new byte[][]{{1,0,1,0,1}});

        System.out.println("\nMot binaire u:");
        u.display();

        Matrix x = u.multiply(g);

        System.out.println("\nEncodage de u (x=u.G) :");
        x.display();
    }

    public static void partie2(){
        System.out.println("\n\n\n---------------------------------------------Partie 2---------------------------------------------\n");

        Matrix hbase = loadMatrix("data/matrix-15-20-3-4", 15, 20);
        
        System.out.println("Forme systématique de H: \n");
        Matrix sysH = hbase.sysTransform();
        sysH.display();
        
        System.out.println("Matrice génératrice G : \n");
        Matrix g = sysH.genG();
        g.display();

        
        Matrix u = new Matrix(new byte[][]{{1, 0, 1, 0, 1}});

        System.out.println("Mot binaire u:\n");
        u.display();

        Matrix x = u.multiply(g);

        System.out.println("\nEncodage de u (x=u.G) :\n");
        x.display();

        int wc = 3;
        int wr = 4;
        TGraph graph = new TGraph(hbase, wc, wr);
        graph.display();


        System.out.println("\n\nMot codé x :");
        x.display();


        byte[][] errorVectors = {
                {0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}
        };

        for (int i = 0; i < errorVectors.length; i++) {
            Matrix errorVector = new Matrix(new byte[][]{errorVectors[i]});

            System.out.println("Veceur d'erreurs e" + (i + 1) + " :");
            errorVector.display();

            Matrix y = x.add(errorVector);

            System.out.println("\nMot de code bruité y" + (i + 1) + "=x+e" + (i + 1) + " :");
            y.display();


            Matrix syndrome = (hbase.multiply(y.transpose())).transpose();

            System.out.println("\nSyndrome de y" + (i + 1) + " :");
            syndrome.display();


            Matrix xi = graph.decode(y, 100);

            System.out.println("\nCorrection x" + (i + 1) + " de y" + (i + 1) + " :");
            xi.display();

            boolean isEqual = x.isEqualTo(xi);

            System.out.println("\nx" + (i + 1) + " = x : " + isEqual);
            System.out.println("\n------------------------------------------------\n");
        }
    }

    public static void partie3(){
        System.out.println("\n\n\n---------------------------------------------Partie 3---------------------------------------------\n");

        Matrix hbase = loadMatrix("data/matrix-2048-6144-5-15", 2048, 6144);
        
        Matrix sysH = hbase.sysTransform();
        
        Matrix g = sysH.genG();

        int nbline = g.getRows();

        byte tmp [] = new byte[nbline];
        for(int i = 0; i<tmp.length; i++){
            if(i%2 == 0){
                tmp[i] = (byte)1;
            }else{
                tmp[i] = (byte)0;
            }
        }
        byte tmp2 [][] = new byte[][]{tmp};
        Matrix u = new Matrix(tmp2);

        Matrix x = u.multiply(g);

        int wc = 5;
        int wr = 15;
        TGraph graph = new TGraph(hbase, wc, wr);


        Matrix e_test = x.errGen(124);


        graph.test(x, 124);
        System.out.println("\n");
    	graph.test(x, 134);
        System.out.println("\n");
    	graph.test(x, 144);
        System.out.println("\n");
    	graph.test(x, 154);

    }
    
    public static void main(String[] arg){
        partie1();
        partie2();
        partie3();
    }
}
