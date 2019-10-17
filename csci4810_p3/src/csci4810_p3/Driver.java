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

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.*;

public class Driver extends JFrame implements ActionListener, KeyListener{
     
    JLabel lblPoints = new JLabel("Input start and endpoints");
    JLabel lbl3DTranslate = new JLabel("Translate");
    JLabel lbl3DScale = new JLabel("<html>Scale<br/>(input x, y, z, cx, cy, and cz)<html/>");
    JLabel lbl3DRotate = new JLabel("<html>Rotate<br/>(input theta, cx, cy, and cz)<html/>");
    JLabel lblFile = new JLabel("File name");
    
    JTextField txtStartPoints = new JTextField("");
    JTextField txtEndPoints = new JTextField("");
    JTextField txt3DTranslate = new JTextField("");
    JTextField txt3DScale = new JTextField("");
    JTextField txt3DScaleCenter = new JTextField("");
    JTextField txt3DRotate = new JTextField("");
    JTextField txt3DRotateCenter = new JTextField("");
    JTextField txtFile = new JTextField("");
    
    JRadioButton xAxis = new JRadioButton("X Axis");
    JRadioButton yAxis = new JRadioButton("Y Axis");
    JRadioButton zAxis = new JRadioButton("Z Axis");
    
    JButton btnDraw = new JButton("Draw");
    JButton btnUndo = new JButton("Undo");
    JButton btnInput = new JButton("Input");
    JButton btnOutput = new JButton("Output");
    JButton btnClear = new JButton("Clear");
    
    JPanel pnlInput = new JPanel();
    JPanel pnlTransform = new JPanel();
    JPanel pnlButton = new JPanel();
    JPanel pnlFile = new JPanel();
    
    JFrame frame = new JFrame();
    
    int[][] line = new int[500][5];         //Array of start and end points 
    
    DrawLine draw = new DrawLine();         //Graphics object that displays lines
    
    //Constructor
    public Driver(String title){
       
       super(title);
       
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));       
       frame.setMinimumSize(new Dimension(1016, 838));
       frame.setTitle(title);
       frame.setVisible(true);
       
    }
    
    //UI elements for drawing lines
    public void addInputComponents(Container pane){
        
        pane.setLayout(new BoxLayout(pnlInput, BoxLayout.Y_AXIS));
        
        pnlInput.setPreferredSize(new Dimension(200, 800));
        pnlInput.setMaximumSize(new Dimension(200, 800));
        pnlInput.setBorder(BorderFactory.createTitledBorder("ENTER INPUT"));
        
        lblPoints.setPreferredSize(new Dimension(100, 15));
        lblPoints.addKeyListener(this);
	txtStartPoints.setPreferredSize(new Dimension(100, 15));
        txtEndPoints.setPreferredSize(new Dimension(100, 15));
        
        pane.add(lblPoints);
        pane.add(txtStartPoints);
        pane.add(txtEndPoints);
        addGeneralComponents(pnlTransform);
        addButtonComponents(pnlButton);
        pane.add(pnlTransform);
        pane.add(pnlButton);     
        
    }
    
    //UI elements for applying transformatuons
    public void addGeneralComponents(Container pane){
        
        pane.setLayout(new BoxLayout(pnlTransform, BoxLayout.Y_AXIS));
        
        pnlTransform.setPreferredSize(new Dimension(185, 470));
        pnlTransform.setMinimumSize(new Dimension(185, 470));
        pnlTransform.setMaximumSize(new Dimension(185, 470));        
        pnlTransform.setBorder(BorderFactory.createTitledBorder("GENERAL"));
        
        lbl3DTranslate.setPreferredSize(new Dimension(100, 40));
        txt3DTranslate.setPreferredSize(new Dimension(100, 15));
        txt3DTranslate.setMaximumSize(new Dimension(100, 15));
	lbl3DScale.setPreferredSize(new Dimension(100, 40));
        txt3DScale.setPreferredSize(new Dimension(100, 15));
        txt3DScaleCenter.setPreferredSize(new Dimension(100, 15));
        lbl3DRotate.setPreferredSize(new Dimension(100, 40));
        txt3DRotate.setPreferredSize(new Dimension(100, 15));
        txt3DRotateCenter.setPreferredSize(new Dimension(100, 15));
        
        pane.add(lbl3DTranslate);
        pane.add(txt3DTranslate);
        pane.add(lbl3DScale);
        pane.add(txt3DScale);
        pane.add(txt3DScaleCenter);
        pane.add(lbl3DRotate);
        pane.add(txt3DRotate);
        pane.add(txt3DRotateCenter);
        
        ButtonGroup group = new ButtonGroup();
        group.add(xAxis);
        group.add(yAxis);
        group.add(zAxis);
        pane.add(xAxis);
        pane.add(yAxis);
        pane.add(zAxis);
        
    }
    
    //Buttons to apply changes
    public void addButtonComponents(Container pane){
        
        pnlButton.setLayout(new BoxLayout(pnlButton, BoxLayout.Y_AXIS));
        pnlButton.setPreferredSize(new Dimension(240, 235));
        pnlButton.setMinimumSize(new Dimension(240, 235));
        pnlButton.setMaximumSize(new Dimension(240, 235));
        
        btnDraw.setMaximumSize(new Dimension(100, 30));
        btnUndo.setMaximumSize(new Dimension(100, 30));
        btnClear.setMaximumSize(new Dimension(100, 30));
        
        pane.add(btnDraw);      
        pane.add(btnUndo);
        pane.add(btnClear);
        addFileComponents(pnlFile);
        pane.add(pnlFile);
        
        btnDraw.addActionListener(this);
        btnUndo.addActionListener(this);
        btnClear.addActionListener(this);
        
    }
    
    //UI elements for file IO
    public void addFileComponents(Container pane){
        
        pnlFile.setLayout(new BoxLayout(pnlFile, BoxLayout.Y_AXIS));
        pnlFile.setPreferredSize(new Dimension(200, 120));
        pnlFile.setMinimumSize(new Dimension(200, 120));
        pnlFile.setMaximumSize(new Dimension(200, 120));
        pnlFile.setBorder(BorderFactory.createTitledBorder("FILE IO"));
        
        lblFile.setPreferredSize(new Dimension(100, 20));
        txtFile.setPreferredSize(new Dimension(100, 15));
        btnInput.setMaximumSize(new Dimension(100, 30));
        btnOutput.setMaximumSize(new Dimension(100, 30));
        
        pane.add(lblFile);
        pane.add(txtFile);
        pane.add(btnInput);
        pane.add(btnOutput);
        
        btnInput.addActionListener(this);
        btnOutput.addActionListener(this);        
        
    }
    
    //Generate all UI elements
    public void createGUI(){
        
        addInputComponents(pnlInput);
                
        frame.add(pnlInput);
        frame.getContentPane().add(draw);
        frame.pack();
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
                 
    }  
    
    /**
     * Applies transformations using magnitude and center point
     * Or draws a line using specified start and end points
     * from text field inputs
     * @param e 
     */
    @Override 
    public void actionPerformed(ActionEvent e){
        
        if(e.getSource() == btnDraw){
            
            String startPoints = txtStartPoints.getText();
            String endPoints = txtEndPoints.getText();
            
            if(!(startPoints.equals("") && endPoints.equals(""))){
                
                String[] strStartVal = startPoints.split(" ");
                String[] strEndVal = endPoints.split(" ");
                int[] startVal = new int[strStartVal.length];
                int[] endVal = new int[strEndVal.length];
                
                //Print 3D points of object by inputting "p" in both textfields
                if(strStartVal.length == 1 && startPoints.equalsIgnoreCase("p") && endPoints.equalsIgnoreCase("p")){
                    
                    draw.printLines();
                    txtStartPoints.setText("");
                    txtEndPoints.setText("");
                
                }    
                
                //Change eye coordinates by inputting "e" into first textfield
                else if(strStartVal.length == 1 && startPoints.equalsIgnoreCase("e")){
                    
                    for(int i=0;i<strEndVal.length;i++)
                        endVal[i] = Integer.parseInt(strEndVal[i]);
                    
                    draw.setEyeCoords(endVal[0], endVal[1], endVal[2]);
                    txtStartPoints.setText("");
                    txtEndPoints.setText("");
                
                }    
                    
                else if(strStartVal.length == 3 && strEndVal.length == 3){
                    
                    for(int i=0;i<strStartVal.length;i++)
                        startVal[i] = Integer.parseInt(strStartVal[i]);
                   
                    for(int i=0;i<strEndVal.length;i++)
                            endVal[i] = Integer.parseInt(strEndVal[i]);                  
                    
                    txtStartPoints.setText("");
                    txtEndPoints.setText("");
                   
                    makeLine(startVal[0], startVal[1], startVal[2], 
                            endVal[0], endVal[1], endVal[2]);

                }       
                
                else
                    System.out.println("Invalid input");

            }
            
            String inputA;
            String inputB;
            
            if(!(inputA = txt3DTranslate.getText()).equals("")){
                
                String[] strPointVal = inputA.split(" ");
                                
                if(strPointVal.length == 3){
                    
                    int[] pointVal = new int[strPointVal.length];
                    for(int i=0;i<pointVal.length;i++)
                        pointVal[i] = Integer.parseInt(strPointVal[i]);
                    
                    
                    Matrix t = Matrix.translate(pointVal[0], pointVal[1], pointVal[2]);
                   
                    draw.applyTransformation(t);
                    
                    txt3DTranslate.setText("");                   
                    
                }
                
                else
                    System.out.println("Invalid input");
                
                
            }
            
            if(!(inputA = txt3DScale.getText()).equals("")){
                
                String[] strPointVal = inputA.split(" ");
                
                inputB = txt3DScaleCenter.getText();
                String[] strCenterVal = inputB.split(" ");
                
                if(strPointVal.length == 3 && strCenterVal.length == 3){
                    
                    double[] pointVal = new double[strPointVal.length];
                    for(int i=0;i<strPointVal.length;i++)
                        pointVal[i] = Double.parseDouble(strPointVal[i]);
                    
                    int[] centerVal = new int[strCenterVal.length];
                    for(int i=0;i<strCenterVal.length;i++)
                        centerVal[i] = Integer.parseInt(strCenterVal[i]);
                    
                    Matrix t = Matrix.scale(pointVal[0], pointVal[1], pointVal[2],
                                    centerVal[0], centerVal[1], centerVal[2]);

                    draw.applyTransformation(t);
                    
                    txt3DScale.setText("");
                    txt3DScaleCenter.setText("");
                    
                }
                
                else
                    System.out.println("Invalid input");               
               
            }
            
            if(!(inputA = txt3DRotate.getText()).equals("")){
                
                String[] strPointVal = inputA.split(" ");
                
                inputB = txt3DRotateCenter.getText();
                String[] strCenterVal = inputB.split(" ");
                
                if(strPointVal.length == 1 && strCenterVal.length == 3){
                    
                    double[] pointVal = new double[strPointVal.length];
                    for(int i=0;i<strPointVal.length;i++)
                        pointVal[i] = Double.parseDouble(strPointVal[i]);
                    
                    int[] centerVal = new int[strCenterVal.length];
                    for(int i=0;i<strCenterVal.length;i++)
                         centerVal[i] = Integer.parseInt(strCenterVal[i]);
                    
                    Matrix t;
                    
                    if(xAxis.isSelected()){
                        
                        t = Matrix.rotateX(pointVal[0],
                                    centerVal[0], centerVal[1], centerVal[2]);
                        draw.applyTransformation(t);
                        
                    }
                    else if(yAxis.isSelected()){
                        
                        t = Matrix.rotateY(pointVal[0],
                                    centerVal[0], centerVal[1], centerVal[2]);
                        draw.applyTransformation(t);
                        
                    }
                    else if(zAxis.isSelected()){
                        
                        t = Matrix.rotateZ(pointVal[0],
                                    centerVal[0], centerVal[1], centerVal[2]); 
                        draw.applyTransformation(t);
                        
                    }
                    else
                        System.out.println("Please select the axis of rotation");
                    
                   
                    
                    txt3DRotate.setText("");
                    txt3DRotateCenter.setText("");
                    
                }
               
                else
                    System.out.println("Invalid Input");
                
            }
            
            draw.redraw();
            
        }
        
        //Deletes last line drawn
        if(e.getSource() == btnUndo){
            
            draw.undo();
            draw.redraw();
            
        }
        
        //Deletes all lines
        if(e.getSource() == btnClear){
            
            draw.clear();
            draw.redraw();
        
        }
        
        //Input from file
        if(e.getSource() == btnInput){
            
           double[][] lines = new double[6][500];
           int num = 0;
           String fileName;
           
           if(!(fileName = txtFile.getText()).equals("")){
                
               try{
                   
                    Scanner scan = new Scanner(new File(fileName));

                    while(scan.hasNextLine()){
                        
                        String[] q = scan.nextLine().split(" ");
                        

                        lines[0][num] = Double.parseDouble(q[0]);
                        lines[1][num] = Double.parseDouble(q[1]);
                        lines[2][num] = Double.parseDouble(q[2]);
                        lines[3][num] = Double.parseDouble(q[3]);
                        lines[4][num] = Double.parseDouble(q[4]);
                        lines[5][num] = Double.parseDouble(q[5]);
                        
                        num++;

                    }

                    int ret = draw.input(lines, num);            

                    System.out.println("Read " + ret + " lines from " + fileName + "\n");
                    draw.redraw();   

                    txtFile.setText("");
               
               }
               
               catch(FileNotFoundException exp){
                   
                   System.out.println("Error: " + exp.toString());
                   
               }
               
               
           }
               
           else
               System.out.println("Please enter a filename");
            
        }
        
        //Output to file
        if(e.getSource() == btnOutput){
            
            double[][] lines = draw.getLines();
            int num = draw.getNumLines();
            String fileName;
            
            if(!(fileName = txtFile.getText()).equals("")){
                
                int ret = draw.output(fileName, lines, num);
                
                System.out.println("Wrote " + ret + " lines to " + fileName + "\n"); 
                
                txtFile.setText("");
            }
            
            else
              System.out.println("Please enter a filename");
            
        }
        
    }
    
    /**
     * Allows object to be transformed by minor amounts using keys
     * @param k 
     */
    @Override
    public void keyPressed(KeyEvent k){
        
        if(k.getKeyChar() == 'k'){
                Matrix t = Matrix.rotateX(0.01, 0, 0, 0);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 'i'){
                Matrix t = Matrix.rotateX(-0.01, 0, 0, 0);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 'l'){
                Matrix t = Matrix.rotateY(0.01, 0, 0, 0);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 'j'){
                Matrix t = Matrix.rotateY(-0.01, 0, 0, 0);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 'o'){
                Matrix t = Matrix.rotateZ(0.01, 0, 0, 0);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 'u'){
                Matrix t = Matrix.rotateZ(-0.01, 0, 0, 0);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 'a'){
                Matrix t = Matrix.translate(0.1, 0, 0);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 'd'){
                Matrix t = Matrix.translate(-0.1, 0, 0);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 'e'){
                Matrix t = Matrix.translate(0, 0.1, 0);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 'q'){
                Matrix t = Matrix.translate(0, -0.1, 0);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 's'){
                Matrix t = Matrix.translate(0, 0, 0.1);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 'w'){
                Matrix t = Matrix.translate(0, 0, -0.1);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 'z'){
                Matrix t = Matrix.scale(0.99, 0.99, 0.99, 0, 0, 0);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 'x'){
                Matrix t = Matrix.scale(1.01, 1.01, 1.01, 0, 0, 0);
                draw.applyTransformation(t);
            }
        
    }
    
    @Override
    public void keyTyped(KeyEvent k){
    
        
        
    }
    
    @Override
    public void keyReleased(KeyEvent k){
    
            if(k.getKeyChar() == 'k'){
                Matrix t = Matrix.rotateX(0.01, 0, 0, 0);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 'i'){
                Matrix t = Matrix.rotateX(-0.01, 0, 0, 0);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 'l'){
                Matrix t = Matrix.rotateY(0.01, 0, 0, 0);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 'j'){
                Matrix t = Matrix.rotateY(-0.01, 0, 0, 0);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 'o'){
                Matrix t = Matrix.rotateZ(0.01, 0, 0, 0);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 'u'){
                Matrix t = Matrix.rotateZ(-0.01, 0, 0, 0);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 'a'){
                Matrix t = Matrix.translate(0.1, 0, 0);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 'd'){
                Matrix t = Matrix.translate(-0.1, 0, 0);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 'e'){
                Matrix t = Matrix.translate(0, 0.1, 0);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 'q'){
                Matrix t = Matrix.translate(0, -0.1, 0);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 's'){
                Matrix t = Matrix.translate(0, 0, 0.1);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 'w'){
                Matrix t = Matrix.translate(0, 0, -0.1);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 'z'){
                Matrix t = Matrix.scale(0.99, 0.99, 0.99, 0, 0, 0);
                draw.applyTransformation(t);
            }
            if(k.getKeyChar() == 'x'){
                Matrix t = Matrix.scale(1.01, 1.01, 1.01, 0, 0, 0);
                draw.applyTransformation(t);
            }
    
    }
    
    
    //Create a line on the program using the given start and end points
    public void makeLine(int x0, int y0, int z0, int x1, int y1, int z1){
        
        draw.setPoints(x0, y0, z0, x1, y1, z1);
            
    }
    
    public static void main(String[] args){
         
        Driver d = new Driver("3D Graphics");
        d.createGUI();
        
    }
    
}
