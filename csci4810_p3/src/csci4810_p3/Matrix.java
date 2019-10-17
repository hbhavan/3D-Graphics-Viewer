package csci4810_p3;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author HBHav
 */
public class Matrix {
    
    final private int myRows;
    final private int myCols;
    final private double[][] mat;           //Array representation of matrix
    
    //Constructor to make a Matrix with specified rows and columns
    public Matrix(int rows, int cols){
        
        myRows = rows;
        myCols = cols;
        
        mat = new double[myRows][myCols];
        
        for(int i=0;i<myRows;i++)
            for(int j=0;j<myCols;j++)
                mat[i][j] = 0;
        
    }
    
    //Constructor to make a copy of given Matrix
    public Matrix(Matrix m){
        
        myRows = m.getRows();
        myCols = m.getCols();
        
        mat = new double[myRows][myCols];
        
        for(int i=0;i<myRows;i++)
            for(int j=0;j<myCols;j++)
                mat[i][j] = m.at(i,j);
        
    }
    
    //Matrix multiplication
    public Matrix mult(Matrix m){
        
        Matrix x = new Matrix(myRows, m.getCols());
        
        
        for(int i=0;i<myRows;i++){
            for(int j=0;j<myCols;j++){
                double result = 0;
                for(int k=0;k<m.getRows();k++){
                   
                    result += (mat[i][k] * m.at(k,j));
                    x.set(i,j,result);
        
                }
            }
        }
        
        return x;
        
    }
    
    //Translation Matrix
    public static Matrix translate(double x, double y, double z){
        
        Matrix t = new Matrix(4, 4);
        
        t.set(0, 0, 1);
        t.set(1, 1, 1);
        t.set(2, 2, 1);
        t.set(3, 0, x);
        t.set(3, 1, y);
        t.set(3, 2, z);
        t.set(3, 3, 1);
        
        return t;
        
    }
    
    //Basic scale Matrix 
    public static Matrix basicScale(double x, double y, double z){
        
        Matrix t = new Matrix(4, 4);
        
        t.set(0, 0, x);
        t.set(1, 1, y);
        t.set(2, 2, z);
        t.set(3, 3, 1);

        return t;
        
    }
    
    //General scale Matrix around specified center point
    public static Matrix scale(double x, double y, double z, int cx, int cy, int cz){
       
        Matrix t;
        
        Matrix a = translate(-cx, -cy, -cz);
        Matrix b = basicScale(x, y, z);
        Matrix c = translate(cx, cy, cz);
                        
        t = a.mult(b).mult(c);
       
        return t;
        
    }
    
    //X axis rotation Matrix
    public static Matrix rotateX(double theta, int cx, int cy, int cz){
        
        Matrix a = translate(-cx, -cy, -cz);
        Matrix r = new Matrix(4, 4);
        Matrix b = translate(cx, cy, cz);
        
        r.set(0, 0, 1);
        r.set(1, 1, Math.cos(theta));
        r.set(1, 2, Math.sin(theta));
        r.set(2, 1, -Math.sin(theta));
        r.set(2, 2, Math.cos(theta));
        r.set(3, 3, 1);
        
        Matrix t = a.mult(r).mult(b);
        
        return t;
        
    }
    
    //Y-axis rotation Matrix
    public static Matrix rotateY(double theta, int cx, int cy, int cz){
        
        Matrix a = translate(-cx, -cy, -cz);
        Matrix r = new Matrix(4, 4);
        Matrix b = translate(cx, cy, cz);
        
        r.set(0, 0, Math.cos(theta));
        r.set(0, 2, Math.sin(theta));
        r.set(1, 1, 1);
        r.set(2, 0, -Math.sin(theta));
        r.set(2, 2, Math.cos(theta));
        r.set(3, 3, 1);
        
        Matrix t = a.mult(r).mult(b);
        
        return t;        
        
    }
    
    //Z axis rotation Matrix
    public static Matrix rotateZ(double theta, int cx, int cy, int cz){
        
        Matrix a = translate(-cx, -cy, -cz); 
        Matrix r = new Matrix(4, 4);
        Matrix b = translate(cx, cy, cz);  
        
        r.set(0, 0, Math.cos(theta));
        r.set(0, 1, Math.sin(theta));
        r.set(1, 0, -Math.sin(theta));
        r.set(1, 1, Math.cos(theta));
        r.set(2, 2, 1);
        r.set(3, 3, 1);
        
        Matrix t = a.mult(r).mult(b);
        
        return t;
        
    }
    
    //Return rows of Matrix
    public int getRows(){
        return myRows;
    }
    
    //Return columns of Matrix
    public int getCols(){
        return myCols;
    }
    
    //Get value at specified index of the Matrix
    public double at(int x, int y){
        return mat[x][y];
    }
    
    //Set value at specified index of the Matrix
    public void set(int x, int y, double val){
        mat[x][y] = val;
    }
    
    //Display the Matrix in the console
    public void print(){
        
        for(int i=0;i<myRows;i++){
            
            System.out.print("[");
            
            for(int j=0;j<myCols;j++)
                System.out.print(mat[i][j] + " ");
            
            System.out.print("]\n");
        }
        
        System.out.println();
        
    }
    
}
