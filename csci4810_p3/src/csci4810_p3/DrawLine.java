package csci4810_p3;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.*;
import java.io.*;

/**
 *
 * @author HBHav
 */
public class DrawLine extends JPanel{

    double[][] p = new double[6][500];      //Original 3D object's points
    double[][] c = new double[6][500];      //Transformed points to match eye coords
    int[][] s = new int[4][500];            //3D points to be displayed onscreen
    Color[] colors = new Color[12];        //Colors to use
    int numLines = 0;                       //Number of lines in object
    
    double[] eyeCoordinates = {6, 8, 7.5};   //Location of "eye"
    double viewDistance = 60;               //Optimal viewing distance
    double screenSize = 30;                 //Screen size parameter
    double vsx = 400;                       //Dimensions of the screen
    double vcx = 400;
    double vsy = 400;
    double vcy = 400;
        
    //Constructor, only initializes color array
    public DrawLine(){
        
        setColors();
        
    }
    
    //Create array of colors to be used for lines
    private void setColors(){
        
        colors[0] = Color.LIGHT_GRAY;
        colors[1] = Color.GREEN;
        colors[2] = Color.BLUE;
        colors[3] = Color.YELLOW;
        colors[4] = Color.CYAN;
        colors[5] = Color.MAGENTA;
        colors[6] = Color.WHITE;
        colors[7] = Color.GRAY;
        colors[8] = Color.RED;
        colors[9] = Color.DARK_GRAY;
        colors[10] = Color.ORANGE;
        colors[11] = Color.PINK;
        
    }

    //Set start and end points of a line in the array of points
    public void setPoints(int x0, int y0, int z0, int x1, int y1, int z1){

        p[0][numLines] = x0;
        p[1][numLines] = y0;
        p[2][numLines] = z0;
        p[3][numLines] = x1;
        p[4][numLines] = y1;
        p[5][numLines] = z1;
        
        numLines++;

    }
    
    //Delete last line added
    public void undo(){
        
        if(numLines > 0){
            
            p[0][numLines] = 0;
            p[1][numLines] = 0;
            p[2][numLines] = 0;
            p[3][numLines] = 0;
            p[4][numLines] = 0;
            p[5][numLines] = 0;

            numLines--;
        
        }
        
    }

    //Delete all lines
    public void clear(){

        for(int i=0;i<numLines;i++){

            p[0][i] = 0;
            p[1][i] = 0;
            p[2][i] = 0;
            p[3][i] = 0;
            p[4][i] = 0;
            p[5][i] = 0;

        }

        numLines = 0;

    }

    public int[] clip(int x0, int y0, int x1, int y1){
        
        int cA, cB;
        
        int c1 = 0;
        int c2 = 0;
        int c3 = 0;
        int c4 = 0;
        
        if(x0 < 0)
            c1 = 1;
        if(x0 > 800)
            c2 = 2;
        if(y0 < 0)
            c3 = 4;
        if(y0 > 800)
            c4 = 8;
        
        cA = c1 + c2 + c3 + c4;
        
        c1 = 0;
        c2 = 0;
        c3 = 0;
        c4 = 0;
        
        if(x1 < 0)
            c1 = 1;
        if(x1 > 800)
            c2 = 2;
        if(y1 < 0)
            c3 = 4;
        if(y1 > 800)
            c4 = 8;
        
        cB = c1 + c2 + c3 + c4;
        
        int[] res = new int[4];
        
        if((cA | cB) == 0){
        
            res[0] = x0;
            res[1] = y0;
            res[2] = x1;
            res[3] = y1;
            
            return res;
        
        }
        
        else if((cA & cB) != 0){
            
            res[0] = 0;
            res[1] = 0;
            res[2] = 0;
            res[3] = 0;
            
            return res;
            
        }
        
        else{
           
            res = clip(x0, y0, 0, 0);
            if(res[0] == 0 && res[1] == 0 && res[3] == 0 && res[4] == 0)
                res = clip(x0, y0, 800, 800);
            
            return res;
        }
        
    }
    
    //Change position of "eye"
    public void setEyeCoords(double xe, double ye, double ze){
        
        eyeCoordinates[0] = xe;
        eyeCoordinates[1] = ye;
        eyeCoordinates[2] = ze;
        
        repaint();
        
    }

    //Update the graphics object
    public void redraw(){

        repaint();

    }
    
    //Print the 3D points of the object to console
    public void printLines(){

        for(int i=0;i<numLines;i++){

            System.out.println("[" + p[0][i] + " " + p[1][i] + " " + p[2][i] + "][" + p[3][i] + p[4][i] + " " + p[5][i] + " " +"]");

        }

        System.out.println("");
        
    }
    
    //Print the 2D screen coordinates to console
    public void printCoords(){
        
        for(int i=0;i<numLines;i++){

            System.out.println("[" + s[0][i] + " " + s[1][i] + "][" + s[2][i] + " " + s[3][i] + "]");

        }

        System.out.println("");
        
    }
    
    //Return the array of start and end points
    public double[][] getLines(){

        return p;

    }

    //Return the number of lines in the object
    public int getNumLines(){

        return numLines;

    }
    
    //Calculation for viewing transformation
    private double getCosY(double x, double y){
        
        double r = y / (Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2) ));
        
        return r;
        
    }
    
    //Calculation for viewing transformation
    private double getSinY(double x, double y){
        
        double r = x / (Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2) ));
        
        return r;
        
    }
     
    //Calculation for viewing transformation   
    private double getCosX(double x, double y, double z){
                
        double r1 = (Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2) ));
        double r2 = (Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)));
        
        return r1 / r2;
        
    }
        
    //Calculation for viewing transformation
    private double getSinX(double x, double y, double z){
        
        double r = z / (Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)));
        return r;
        
    }
    
    /**
     * This matrix takes the object in 3D space and transforms it to represent
     * it as if being viewed from the "eye" coordinate
     */
    private Matrix viewMatrix(double xe, double ye, double ze){
        
        Matrix v;
        
        Matrix t1 = Matrix.translate(-xe, -ye, -ze);
        
        Matrix t2 = new Matrix(4, 4);
        t2.set(0, 0, 1);
        t2.set(1, 2, -1);
        t2.set(2, 1, 1);
        t2.set(3, 3, 1);
            
        Matrix t3 = new Matrix(4, 4);
        double cosY = getCosY(xe, ye);
        double sinY = getSinY(xe, ye);        
        t3.set(0, 0, -cosY);
        t3.set(0, 2, sinY);
        t3.set(1, 1, 1);
        t3.set(2, 0, -sinY);
        t3.set(2, 2, -cosY);
        t3.set(3, 3, 1);
        
        Matrix t4 = new Matrix(4, 4);
        double cosX = getCosX(xe, ye, ze);
        double sinX = getSinX(xe, ye, ze);
        t4.set(0, 0, 1);
        t4.set(1, 1, cosX);
        t4.set(1, 2, sinX);
        t4.set(2, 1, -sinX);
        t4.set(2, 2, cosX);
        t4.set(3, 3, 1);
        
        Matrix t5 = new Matrix(4, 4);
        t5.set(0, 0, 1);
        t5.set(1, 1, 1);
        t5.set(2, 2, -1);
        t5.set(3, 3, 1);
        
        v = t1.mult(t2).mult(t3).mult(t4).mult(t5);
            
        return v;        
        
    }
    
    //Matrix to convert points into clipping coordinates
    private Matrix clipMatrix(){
        
        Matrix n;
        double ds = viewDistance / screenSize;
        
        n = Matrix.basicScale(ds, ds, 1);
            
        return n;
        
    }

    /**
     * Calculates the final matrix that applies eye coordinate transformations
     * and applies it to all the points
     */
    private void getClippingCoordinates(){
        
        Matrix h = viewMatrix(eyeCoordinates[0], eyeCoordinates[1], eyeCoordinates[2]).mult(clipMatrix());
            
        Matrix a = new Matrix (1, 4);
        a.set(0, 3, 1);
        Matrix b;
        
        for(int i=0;i<numLines;i++){
            
            a.set(0, 0, p[0][i]);
            a.set(0, 1, p[1][i]);
            a.set(0, 2, p[2][i]);
            
            b = a.mult(h);
            
            c[0][i] = b.at(0,0);
            c[1][i] = b.at(0,1);
            c[2][i] = b.at(0,2);
            
            a.set(0, 0, p[3][i]);
            a.set(0, 1, p[4][i]);
            a.set(0, 2, p[5][i]);
            
            b = a.mult(h);
            
            c[3][i] = b.at(0,0);
            c[4][i] = b.at(0,1);
            c[5][i] = b.at(0,2);
            
        }
        
    }
    
    //Convert 3D transformed points into 2D screen coordinates
    public void getScreenCoordinates(){
        
        for(int i=0;i<numLines;i++){
            
            s[0][i] = (int)(((c[0][i] / c[2][i]) * vsx)  + vcx);
            s[1][i] = (int)(((c[1][i] / c[2][i]) * vsy)  + vcy);
            s[2][i] = (int)(((c[3][i] / c[5][i]) * vsx)  + vcx);
            s[3][i] = (int)(((c[4][i] / c[5][i]) * vsy)  + vcy);
            
        }
        
    }
    
    //Applies a 3D transformation using matrices
    public void applyTransformation(Matrix m){

        Matrix a = new Matrix(1, 4);
        Matrix d = new Matrix(1, 4);
        a.set(0, 3, 1);
        d.set(0, 3, 1);
        Matrix b;
        Matrix e;

        for(int i=0;i<numLines;i++){

            a.set(0, 0, p[0][i]);
            a.set(0, 1, p[1][i]);
            a.set(0, 2, p[2][i]);

            b = a.mult(m);

            p[0][i] = b.at(0,0);
            p[1][i] = b.at(0,1);
            p[2][i] = b.at(0,2);
            
            d.set(0, 0, p[3][i]);
            d.set(0, 1, p[4][i]);
            d.set(0, 2, p[5][i]);

            e = d.mult(m);

            p[3][i] = e.at(0,0);
            p[4][i] = e.at(0,1);
            p[5][i] = e.at(0,2);
            
        }

        repaint();

    }    
     
    //Draw lines on the screen
    @Override
    public void paintComponent(Graphics g){

        Image img = render();
        g.drawImage(img, 0, 0, this);

    }
    
    //Bresenham's algorithm to display the lines using 2D screen coordinates
    private Image render(){
        
        BufferedImage buffImg = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
        Graphics g = buffImg.getGraphics();
        
        getClippingCoordinates();
        getScreenCoordinates();
        
        
        
        for(int j=0; j<numLines;j++){

           int startX = s[0][j];
           int startY = s[1][j];
           
           int endX = s[2][j];
           int endY = s[3][j];

           int dX = endX - startX;
           int dY = endY - startY;

            int x;
            int y;

            if(dX == 0){

                if(endY < startY){

                    int tempY = startY;
                    startY = endY;
                    endY = tempY;

                    dY = endY - startY;

                }

                x = startX;

                for(int i=0;i<(dY);i++){
                    y = i + startY;
                    g.setColor(colors[j%12]);
                    g.drawLine(x, y, x, y);
                }

            }

            else if(dY == 0){

                if(endX < startX){

                    int tempX = startX;
                    startX = endX;
                    endX = tempX;

                    dX = endX - startX;

                }

                y = startY;

                for(int i=0;i<(dX);i++){
                    x = i + startX;
                    g.setColor(colors[j%12]);
                    g.drawLine(x, y, x, y);
                }

            }

            else if(Math.abs(dX) >= Math.abs(dY)){

                if(endX < startX){

                    int tempX = startX;
                    startX = endX;
                    endX = tempX;

                    int tempY = startY;
                    startY = endY;
                    endY = tempY;

                    dX = endX - startX;
                    dY = endY - startY;

                }

                int m = 1;

                if(dY < 0){
                    m = -1;
                    dY = -dY;
                }

                int e = 0;

                x = startX;
                y = startY;

                while(x < endX){

                    g.setColor(colors[j%12]);
                    g.drawLine(x, y, x, y);

                    e = e + dY;

                    if((e<<2) > dX){
                        y = y + m;
                        e = e - dX;
                    }

                    x = x + 1;

                }

            }

            else if(Math.abs(dX) < Math.abs(dY)){

                if(endY < startY){

                    int tempX = startX;
                    startX = endX;
                    endX = tempX;

                    int tempY = startY;
                    startY = endY;
                    endY = tempY;

                    dX = endX - startX;
                    dY = endY - startY;

                }

                int m = 1;

                if(dX < 0){
                    m = -1;
                    dX = -dX;
                }

                int e = 0;

                x = startX;
                y = startY;

                while(y < endY){

                    g.setColor(colors[j%12]);
                    g.drawLine(x, y, x, y);

                    e = e + dX;

                    if((e<<2) > dY){
                        x = x + m;
                        e = e - dY;
                    }

                    y = y + 1;

                }

            }

        }

        return buffImg;

    }
    
    //File input for lines
    public int input(double[][] lines, int num){

        p = lines;
        numLines = num;

        return num;

    }
    
    //File output for lines
     public int output(String fileName, double[][] lines, int num){

        try{

            FileOutputStream fos = new FileOutputStream(fileName);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            if(fileName.equals(""))
                throw new FileNotFoundException("File not found");

            int count = 0;

            for(int i=0;i<num;i++){

                bw.write("" + lines[0][i] + " " + lines[1][i] + " " + lines[2][i] + " " + 
                        lines[3][i] + " " + lines[4][i] + " " + lines[5][i]);
                bw.newLine();

                count++;

            }
            
            bw.close();

        }
        catch(FileNotFoundException exp){
            System.out.println("Error: " + exp.toString());
        }
        catch(IOException exp){
            System.out.println("Error: " + exp.toString());
        }

        return num;

    }

}
