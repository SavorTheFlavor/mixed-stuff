package montecarlo;

import java.awt.*;
import java.util.LinkedList;
import javax.swing.*;

public class AlgoVisualizer {

    private static int DELAY = 40;

    private MontecarloData data;
    private AlgoFrame frame;
    private int N;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N){

        if(sceneWidth != sceneHeight)
            throw new IllegalArgumentException("This demo must be run in a square window!");

        this.N = N;
        this.data = new MontecarloData(sceneWidth);
        // invokeLater....
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Get Pi with Monte Carlo", sceneWidth, sceneHeight);
            new Thread(() -> {
                run();
            }).start();
        });
    }

    public void run(){

        for(int i = 0 ; i < N ; i ++){
        	if(i%1000 == 0) {
	            frame.render(data);
	            System.err.println("PI:"+data.estimatePI());
	            AlgoVisHelper.pause(DELAY);
        	}
            int x = (int)(Math.random() * frame.getCanvasWidth());
            int y = (int)(Math.random() * frame.getCanvasHeight());
            this.data.addPoint(new Point(x, y));
        }

    }

    public static void main(String[] args) {

        int sceneWidth = 800;
        int sceneHeight = 800;
        int N = 1000000;

        AlgoVisualizer vis = new AlgoVisualizer(sceneWidth, sceneHeight, N);
    }
}
