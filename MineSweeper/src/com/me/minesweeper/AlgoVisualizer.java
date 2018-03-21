package com.me.minesweeper;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

public class AlgoVisualizer {

    private static int DELAY = 5;
    private static int blockSide = 32;

    private MineSweeperData data;
    private AlgoFrame frame;

    public AlgoVisualizer(int N, int M, int mineNumber){

        data = new MineSweeperData(N, M, mineNumber);
        int sceneWidth = M * blockSide;
        int sceneHeight = N * blockSide;

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Mine Sweeper", sceneWidth,sceneHeight);
            frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }

    private void run(){
        setData(false, -1, -1);
    }

    private void setData(boolean leftclicked, int i, int j){
    	if(data.inArea(i, j)) {
    		if(leftclicked) {
    			if(data.isMine(i, j)) {
    				frame.gameOver();
    			}else {
    				data.openCells(i, j);
    			}
    		}else {
				data.flags[i][j] = !data.flags[i][j];
			}
    	}
    	
    	if(frame.isGameOver()) {
    		return;
    	}
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }
    

	private class AlgoMouseListener extends MouseAdapter{

        @Override
        public void mouseReleased(MouseEvent event){
        	
        	if(frame.isGameOver()) {
        		return;
        	}
        	
        	//¼õÈ¥±ß¿ò
            event.translatePoint(
                    -(int)(frame.getBounds().width - frame.getCanvasWidth()),
                    -(int)(frame.getBounds().height - frame.getCanvasHeight())
            );

            Point pos = event.getPoint();

            int w = frame.getCanvasWidth() / data.M();
            int h = frame.getCanvasHeight() / data.N();

            int x = Math.round(pos.y / (float)h);
            int y = Math.round(pos.x / (float)w);
            // System.out.println(x + " , " + y);

            if(SwingUtilities.isLeftMouseButton(event))
                setData(true, x, y);
            else if(SwingUtilities.isRightMouseButton(event))
                setData(false, x, y);

        }
    }

    public static void main(String[] args) {

        int N = 20;
        int M = 20;
        int mineNumber = 40;

        AlgoVisualizer vis = new AlgoVisualizer(N, M, mineNumber);

    }
}
