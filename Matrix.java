import java.util.*;
import java.io.*;

public class Matrix {
    private byte[][] data = null;
    private int rows = 0, cols = 0;
    
    public Matrix(int r, int c) {
        data = new byte[r][c];
        rows = r;
        cols = c;
    }
    
    public Matrix(byte[][] tab) {
        rows = tab.length;
        cols = tab[0].length;
        data = new byte[rows][cols];
        for (int i = 0 ; i < rows ; i ++)
            for (int j = 0 ; j < cols ; j ++) 
                data[i][j] = tab[i][j];
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getCols() {
        return cols;
    }
    
    public byte getElem(int i, int j) {
        return data[i][j];
    }
    
    public void setElem(int i, int j, byte b) {
        data[i][j] = b;
    }
    
    public boolean isEqualTo(Matrix m){
        if ((rows != m.rows) || (cols != m.cols))
            return false;
        for (int i = 0; i < rows; i++) 
            for (int j = 0; j < cols; j++) 
                if (data[i][j] != m.data[i][j])
                    return false;
                return true;
    }
    
    public void shiftRow(int a, int b){
        byte tmp = 0;
        for (int i = 0; i < cols; i++){
            tmp = data[a][i];
            data[a][i] = data[b][i];
            data[b][i] = tmp;
        }
    }
    
    public void shiftCol(int a, int b){
        byte tmp = 0;
        for (int i = 0; i < rows; i++){
            tmp = data[i][a];
            data[i][a] = data[i][b];
            data[i][b] = tmp;
        }
    }
     
    public void display() {
        System.out.print("[");
        for (int i = 0; i < rows; i++) {
            if (i != 0) {
                System.out.print(" ");
            }
            
            System.out.print("[");
            
            for (int j = 0; j < cols; j++) {
                System.out.printf("%d", data[i][j]);
                
                if (j != cols - 1) {
                    System.out.print(" ");
                }
            }
            
            System.out.print("]");
            
            if (i == rows - 1) {
                System.out.print("]");
            }
            
            System.out.println();
        }
        System.out.println();
    }
    
    public Matrix transpose() {
        Matrix result = new Matrix(cols, rows);
        
        for (int i = 0; i < rows; i++) 
            for (int j = 0; j < cols; j++) 
                result.data[j][i] = data[i][j];
    
        return result;
    }
    
    public Matrix add(Matrix m){
        Matrix r = new Matrix(rows,m.cols);
        
        if ((m.rows != rows) || (m.cols != cols))
            System.out.printf("Erreur d'addition\n");
        
        for (int i = 0; i < rows; i++) 
            for (int j = 0; j < cols; j++) 
                r.data[i][j] = (byte) ((data[i][j] + m.data[i][j]) % 2);
        return r;
    }
    
    public Matrix multiply(Matrix m){
        Matrix r = new Matrix(rows,m.cols);
        
        if (m.rows != cols)
            System.out.printf("Erreur de multiplication\n");
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                r.data[i][j] = 0;
                for (int k = 0; k < cols; k++){
                    r.data[i][j] =  (byte) ((r.data[i][j] + data[i][k] * m.data[k][j]) % 2);
                }
            }
        }
        
        return r;
    }

    //exercice3:
    public void addRow(int a, int b){
        for(int i = 0; i < cols; i++){
            data[b][i] = (byte)((data[a][i] + data[b][i]) % 2);
        }
    }
    public void addCol(int a, int b){
        for(int i = 0; i < rows; i++){
            data[i][b] = (byte)((data[i][a] + data[i][b]) % 2);
        }
    }


    //exercice4:
    public Matrix sysTransform() {
        Matrix transformed = new Matrix(this.data);
        int n = this.cols; 
        int r = this.rows;
        int identitySize = n - r;

        for (int i = 0; i < r; i++) {

            if (transformed.data[i][i + identitySize] != 1) {
                for (int k = i + 1; k < r; k++) {
                    if (transformed.data[k][i + identitySize] == 1) {
                        transformed.shiftRow(i, k);
                        break;
                    }
                }
            }

            for (int j = 0; j < r; j++) {
                if (i != j && transformed.data[j][i + identitySize] == 1) {
                    for (int k = 0; k < n; k++) {
                        transformed.data[j][k] ^= transformed.data[i][k];
                    }
                }
            }
        }

        return transformed;
    }

    //exercice 5:
    public Matrix genG() {
        int r = this.rows;
        int n = this.cols;
        int identitySize = n - r;

        byte[][] g = new byte[identitySize][n];

        for (int i = 0; i < identitySize; i++) {
            g[i][i] = 1;
        }

        for (int i = 0; i < identitySize; i++) {
            for (int j = 0; j < r; j++) {
                g[i][j + identitySize] = this.data[j][i];
            }
        }

        return new Matrix(g);
    }
    
    
    public byte[][] getData() {
        return data;
    }

    public Matrix errGen(int w)
    {
    	byte[][] tab_err = new byte[1][this.cols];
    	for(int i=0; i < this.cols; i++)
    	{
    		tab_err[0][i] = 0;
    	}
    	
    	Random random = new Random();
    	for(int i=0; i < w; i++)
    	{
    		int indice = random.nextInt(this.cols);
    		
    		if(tab_err[0][indice] == 0)
    		{
    			tab_err[0][indice] = 1;
    		}
    		else
    		{
    			i--;
    		}
    	}
    	Matrix err = new Matrix(tab_err);
    	return err;
    }



}

