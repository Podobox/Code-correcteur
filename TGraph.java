import java.util.*;
import java.io.*;

public class TGraph {
    private int n_r; 
    private int w_r;
    private int n_c;
    private int w_c;
    private int left[][];
    private int right[][];

    public TGraph(Matrix H, int wc, int wr){
        this.n_r = H.getRows();
        this.n_c = H.getCols();
        this.w_r = wr;
        this.w_c = wc;
        this.left = new int[n_r][w_r + 1];
        this.right = new int[n_c][w_c + 1];

        for (int i = 0; i < n_r; i++) {
            for (int j = 0; j <= w_r; j++) {
                left[i][j] = 0;
            }
        }
        for (int i = 0; i < n_c; i++) {
            for (int j = 0; j <= w_c; j++) {
                right[i][j] = 0;
            }
        }

        for (int i = 0; i < n_r; i++) {
            for (int j = 0; j < n_c; j++) {
                if (H.getElem(i,j) == 1) {
                    for (int k = 1; k <= w_r; k++) {
                        if (left[i][k] == 0) {
                            if(j == 0){
                                left[i][k] = -1;
                            }else{
                                left[i][k] = j;
                            }
                            break;
                        }
                    }
                    for (int k = 1; k <= w_c; k++) {
                        if (right[j][k] == 0) {
                            if(i == 0){
                                right[j][k] = -1;
                            }else{
                                right[j][k] = i;
                            }
                            break;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < n_r; i++) {
            for (int j = 0; j <= w_r; j++) {
                if(left[i][j] == -1){
                    left[i][j] = 0;
                }
            }
        }
        for (int i = 0; i < n_c; i++) {
            for (int j = 0; j <= w_c; j++) {
                if(right[i][j] == -1){
                    right[i][j] = 0;
                }
            }
        }
    }


    public void display() {
        System.out.println("Left Table: \n");
        for(int i=0; i<n_r; i++){
            for(int j=0; j<=w_r; j++){
                System.out.print(left[i][j] + " | ");
            }
            System.out.println();
        }
        
        System.out.println("\nRight Table: \n");
        for(int i=0; i<n_c; i++){
            for(int j=0; j<=w_c; j++){
                System.out.print(right[i][j] + " | ");
            }
            System.out.println();
        }
    }



    public Matrix decode(Matrix code, int rounds)
    {
        byte[][] x = new byte[code.getRows()][code.getCols()];
        byte[][] codeData = code.getData();
        int[] count = new int[n_c];

        for(int j = 0; j < n_c ; j++ ){
            right[j][0] = codeData[0][j];
        }

        for (int r = 0 ; r < rounds ; r++ )
        {
            boolean iscorrect = true;

            for (int i = 0 ; i < n_r ; i++ )
            {
                byte sum = 0;
                for (int k = 1 ; k < w_r + 1; k++ )
                    sum = (byte) ((sum + right[left[i][k]][0]) % 2);
                left[i][0] = sum;
            }

            for (int[] row : left) 
            {
                if (row[0] == (byte)1) 
                {
                    iscorrect = false;
                    break;
                }
            }

            if (iscorrect)
            {
                for(int i = 0; i < n_c; i++)
                    x[0][i] = (byte) right[i][0];

                return new Matrix(x);
            }

            int max = 0;
            for(int i = 0 ; i < n_c ; i++ )
            {
                count[i] = 0;
                for(int k=1; k <= w_c; k++)
                {
                    count[i] += left[right[i][k]][0];
                }
                if(count[i] > max)
                {
                    max = count[i];
                }
            }

            for(int i = 0 ; i < n_c ; i++ )
            {
                if(count[i] == max)
                {
                    right[i][0] = (byte) (1 - right[i][0]);
                }
            }
        }

        for(int j = 0 ; j < n_c ; j++ )
            x[0][j] = -1;

        return new Matrix(x);
    }



    public void test(Matrix x, int w)
	{
		double count_error = 0;
		double count_echec = 0;
    	for(int i=0; i < 10000; i++)
    	{
    		Matrix e = x.errGen(w);
    		Matrix y = x.add(e);
    		
    		Matrix result = this.decode(y, 200);
    		
    		boolean equal = true;
    		int cols = x.getCols();
    		for(int j=0; j < cols; j++)
    		{
    			if(result.getElem(0,0)==-1){
                    count_echec++;
                    break;
                }

                if(x.getElem(0, j) != result.getElem(0, j))
                {
                    count_error++;
                }
                
    		}
    		
    	}
    	System.out.print("w = " + w + ": " + "echec: "+ count_echec/100  + "\n    ");
    	int len = String.valueOf(w).length();
    	for(int i=0; i < len; i++)
    	{
    		System.out.print(" ");
    	}
    	System.out.println("erreur: " + count_error/100);
	}
}