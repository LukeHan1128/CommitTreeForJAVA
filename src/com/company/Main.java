package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends JFrame{
    private static ArrayList<Point> plist = new ArrayList<Point>();
    private static ArrayList<String> gitlog = new ArrayList<String>();

    private int tBold = 5;
    private int cCircle = 12;
    private int lineWidth = 20;
    private int lineHeight = 20;
    private int winWidth = 1200;
    private int winHeight = 800;

    /* sample
    public void paint(Graphics g){
        super.paint(g);  // fixes the immediate problem.
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(tBold, BasicStroke.CAP_ROUND,0));  // 굵기

        Point p1 = null;
        Point p2 = null;
        for(int i=0;i<plist.size();++i){
            p2 = p1;
            p1 = plist.get(i);

            if(p2 == null) {
                g2.setColor(Color.BLUE);
                g2.fillOval(p1.x - cCircle/2, p1.y - cCircle/2, cCircle,cCircle);
            }else{
                g2.setColor(Color.BLUE);
                g2.fillOval(p2.x - cCircle/2, p2.y - cCircle/2, cCircle,cCircle);
                Line2D lin = new Line2D.Float(p1, p2);

                if(i+1 == plist.size()){
                    g2.setColor(Color.BLUE);
                    g2.fillOval(p1.x - cCircle/2, p1.y - cCircle/2, cCircle,cCircle);
                }
                g2.draw(lin);
            }
        }
    }
    //*/

    //*
    public void paint(Graphics g){
        super.paint(g);  // fixes the immediate problem.
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(tBold, BasicStroke.CAP_ROUND,0));  // 굵기

        Point ptop = null;
        Point pmiddle = null;
        Point pbottom = null;

        String line1 = null;
        String line2 = null;

        for(int i=0;i<gitlog.size();++i){
            String tree = gitlog.get(i).split("commit")[0];
            String commit = null;

            if(1 < gitlog.get(i).split("commit").length){
                commit = gitlog.get(i).split("commit")[1];
            }

            line2 = line1;
            line1 = tree;

            if(null == line2){

            }
        }
    }
    //*/

    private void getCommitLog(){
        String fname = "test.txt";
        File file = new File(fname);

        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            boolean ck = false;
            String line = "";
            while ((line = br.readLine()) != null) {
                if(-1 < line.indexOf("*") && -1 < line.indexOf("commit")) {
                    ck = true;
                    gitlog.add(line);
                }else{
                    if(ck || (-1 < line.indexOf("* ") || -1 < line.indexOf("\\ ") || -1 < line.indexOf("|/"))){
                        ck = false;
                        line = line.split("Date: ")[0]
                                .split("Author: ")[0]
                                .split("Merge: ")[0];
                        gitlog.add(line);
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        for(String s : gitlog){
            //System.out.println(s);
        }
    }

    public Main(){
        JPanel panel = new JPanel();
        getContentPane().add(panel);
        setSize(winWidth,winHeight);
        getCommitLog();

        setVisible(true);
    }

    public static void main(String []args){
        //*
        plist.add(new Point(100,100));
        plist.add(new Point(100,120));
        plist.add(new Point(120,140));
        plist.add(new Point(120,160));
        plist.add(new Point(100,180));
        plist.add(new Point(100,200));
        plist.add(new Point(120,220));
        plist.add(new Point(140,240));
        plist.add(new Point(140,260));
        plist.add(new Point(120,280));
        plist.add(new Point(100,300));
        //*/

        new Main();
    }
}