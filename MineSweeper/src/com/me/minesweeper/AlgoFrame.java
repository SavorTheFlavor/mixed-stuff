package com.me.minesweeper;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AlgoFrame extends JFrame{

    private int canvasWidth;
    private int canvasHeight;
    private  AlgoCanvas canvas;

    public AlgoFrame(String title, int canvasWidth, int canvasHeight){
   
        super(title);
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        canvas = new AlgoCanvas();
        setContentPane(canvas);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setVisible(true);
    }

    public AlgoFrame(String title){

        this(title, 1024, 768);
    }

    public int getCanvasWidth(){return canvasWidth;}
    public int getCanvasHeight(){return canvasHeight;}

    // data
    private MineSweeperData data;
	private boolean isGameOver = false;
    public void render(MineSweeperData data){
        this.data = data;
        repaint();
    }

    private class AlgoCanvas extends JPanel{

        public AlgoCanvas(){
            // Ë«»º´æ
            super(true);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D)g;

            // ¿¹¾â³Ý
            RenderingHints hints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.addRenderingHints(hints);

            // ¾ßÌå»æÖÆ
            int w = canvasWidth/data.M();
            int h = canvasHeight/data.N();

            for(int i = 0 ; i < data.N() ; i ++)
                for(int j = 0 ; j < data.M() ; j ++){
                	if(data.open[i][j]) {
	                    if(data.isMine(i, j)) {
	                        //gameOver();
	                    }
	                    else {
	                    	AlgoVisHelper.putImage(g2d, j*w, i*h, MineSweeperData.getNumberImg(data.getNumber(i, j)));
	                    }
                	}else {
		                    if(data.flags[i][j]) {
		                        AlgoVisHelper.putImage(g2d, j*w, i*h, MineSweeperData.flagImg);
		                    }
		                    else {
		                        AlgoVisHelper.putImage(g2d, j*w, i*h, MineSweeperData.blockImg);
		                    }
                	}
                }
        }


		@Override
        public Dimension getPreferredSize(){
            return new Dimension(canvasWidth, canvasHeight);
        }
    }

	public void gameOver() {
		isGameOver = true;
		Graphics g = canvas.getGraphics();
        Graphics2D g2d = (Graphics2D)g;
        int w = canvasWidth/data.M();
        int h = canvasHeight/data.N();
        for(int i = 0 ; i < data.N() ; i ++)
            for(int j = 0 ; j < data.M() ; j ++)
            	if(data.isMine(i, j))
            		AlgoVisHelper.putImage(g2d, j*w, i*h, MineSweeperData.mineImg);
	}

	public boolean isGameOver() {
		return isGameOver;
	}
}
