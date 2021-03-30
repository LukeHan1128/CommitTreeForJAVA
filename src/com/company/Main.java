package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends JFrame{
    private static ArrayList<Point> plist = new ArrayList<Point>();
    private static ArrayList<String> gitlog = new ArrayList<String>();

    private ArrayList<Color> colorUseList = new ArrayList();
    private ArrayList<Color> colorList = new ArrayList(Arrays.asList(
            Color.BLUE
            , Color.CYAN
            , Color.GREEN
            , Color.ORANGE
            , Color.PINK
            , Color.MAGENTA
            , Color.YELLOW));

    private final int DEFAULT_X = 40;
    private final int DEFAULT_Y = 40;
    private final int LINE_WIDTH = 10;
    private final int LINE_HEIGHT = 10;

    private int tBold = 4;
    private int cCircle = 8;
    private int winWidth = 800;
    private int winHeight = 1400;

    //*
    public void paint(Graphics g){
        super.paint(g);  // fixes the immediate problem.
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(tBold, BasicStroke.CAP_ROUND,0));  // 굵기

        String upCommitLine = null;
        String upLine = null;
        String underLine = null;

        int cntHeight = 0;
        for(int i=0;i<gitlog.size();++i){
            String line = gitlog.get(i).split("commit")[0];
            String commit = null;

            if(1 < gitlog.get(i).split("commit").length){
                commit = gitlog.get(i).split("commit")[1];
            }

            upLine = underLine;
            underLine = line;

            System.out.println(line);

            if(-1 < line.indexOf("*")){
                /** commit line **/
                cntHeight = cntHeight+1;

                Line2D lin = null;
                int cntBranch = line.length() / 2;
                int commitBranch = line.indexOf("* ") + 2;

                if(0 != commitBranch) commitBranch = commitBranch / 2;

                if(null == upLine){
                    /** first line **/
                    int width = DEFAULT_X + (commitBranch * LINE_WIDTH) - (LINE_WIDTH / 2);
                    int height = DEFAULT_Y + LINE_HEIGHT/2;

                    g2.setColor(colorList.get(0));
                    g2.fillOval(width - cCircle/2, height - cCircle/2, cCircle ,cCircle);
                }else{
                    /** other line **/
                    int cntUpPoint = upCommitLine.replaceAll(" ","").length();
                    int cntUpCommitPoint = upCommitLine.replaceAll(" ","").indexOf("*");

                    int cntPoint = underLine.replaceAll(" ","").length();
                    int cntCommitPoint = underLine.replaceAll(" ","").indexOf("*");

                    upLine = upLine.replaceAll(" ","");

                    /*  "/|" , "|\" , "\|" , "|/" , "|" , " \" , " /"  */
                    for(int j=0; j<upLine.length() ;++j){
                        String pipe = upLine.charAt(j)+"";

                        if("|".equals(pipe)){
                            g2.setColor(colorList.get(0));

                            lin = new Line2D.Float(
                                    DEFAULT_X + ((j+1) * LINE_WIDTH) - (LINE_WIDTH / 2),
                                    DEFAULT_Y + (LINE_HEIGHT * (cntHeight-1)) - (LINE_HEIGHT / 2),
                                    DEFAULT_X + ((j+1) * LINE_WIDTH) - (LINE_WIDTH / 2),
                                    DEFAULT_Y + (LINE_HEIGHT * cntHeight) - (LINE_HEIGHT / 2));

                            g2.draw(lin);

                        }else if("/".equals(pipe)){
                            g2.setColor(colorList.get(0));

                            lin = new Line2D.Float(
                                    DEFAULT_X + ((j+1) * LINE_WIDTH) - (LINE_WIDTH / 2),
                                    DEFAULT_Y + (LINE_HEIGHT * (cntHeight-1)) - (LINE_HEIGHT / 2),
                                    DEFAULT_X + (j * LINE_WIDTH) - (LINE_WIDTH / 2),
                                    DEFAULT_Y + (LINE_HEIGHT * cntHeight) - (LINE_HEIGHT / 2));

                            g2.draw(lin);

                        }else if("\\".equals(pipe)){
                            g2.setColor(colorList.get(0));

                            lin = new Line2D.Float(
                                    DEFAULT_X + (j * LINE_WIDTH) - (LINE_WIDTH / 2),
                                    DEFAULT_Y + (LINE_HEIGHT * (cntHeight-1)) - (LINE_HEIGHT / 2),
                                    DEFAULT_X + ((j+1) * LINE_WIDTH) - (LINE_WIDTH / 2),
                                    DEFAULT_Y + (LINE_HEIGHT * cntHeight) - (LINE_HEIGHT / 2));

                            g2.draw(lin);
                        }
                    }
                }

                g2.fillOval(DEFAULT_X + (commitBranch * LINE_WIDTH) - (LINE_WIDTH / 2) - cCircle/2
                        , DEFAULT_Y + (LINE_HEIGHT * cntHeight) - (LINE_HEIGHT / 2) - cCircle/2
                        , cCircle
                        , cCircle);

                upCommitLine = line;
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
    }

    public Main(){
        JPanel panel = new JPanel();
        getContentPane().add(panel);
        setSize(winWidth,winHeight);
        getCommitLog();

        setVisible(true);
    }

    public static void main(String []args){
        new Main();
    }
}
