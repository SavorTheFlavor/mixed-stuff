package moneyproblem;
import java.awt.*;
import java.util.Arrays;

/**
 *  有100个人
 *  每人有100块钱
 *  每一轮游戏每个人随机给其他一个人(也可以是自己)一块钱
 *  
 *  最后是处于一个不稳定的状态(有的人腰缠万贯，有的人负债累累，有的人平庸无奇，而且这个"有的人"是不断变化的，可以是所有人)
 *  10000块钱分给100个人有非常多种状态，无数轮游戏后，状态分布于相对比较多的相似情况的状态
 * @author Administrator
 *
 */
public class AlgoVisualizer {

    private static int DELAY = 20;
    private int[] money;
    private AlgoFrame frame;
	private int roundPerPainting = 50; //每多少轮游戏后绘制一次

    public AlgoVisualizer(int sceneWidth, int sceneHeight){

        // 初始化数据
        money = new int[100];
        for(int i = 0 ; i < money.length ; i ++)
            money[i] = 100;

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Money Problem", sceneWidth, sceneHeight);
            new Thread(() -> {
                run();
            }).start();
        });
    }

    public void run(){

        while(true){

        	Arrays.sort(money);
            frame.render(money);
            AlgoVisHelper.pause(DELAY);
            
            for(int k=0; k<roundPerPainting ; k++) {
	            for(int i = 0 ; i < money.length; i ++){
	                    int j = (int)(Math.random() * money.length);
	                    money[i] -= 1;
	                    money[j] += 1;
	            }
            }
        }
    }

    public static void main(String[] args) {

        int sceneWidth = 1000;
        int sceneHeight = 800;

        AlgoVisualizer vis = new AlgoVisualizer(sceneWidth, sceneHeight);
    }
}