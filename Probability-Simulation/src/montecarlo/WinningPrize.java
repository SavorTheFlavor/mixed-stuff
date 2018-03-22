package montecarlo;

import java.util.Random;

/**
 *  打开一个宝箱获取传奇武器的概率为20%
 *  有5个宝箱，全部打开获得(至少一把)传奇武器的概率为？
 */
public class WinningPrize {

	private int N;
	private float chance; 
	private int boxNum;
	private Random r;

    public WinningPrize(int N, int boxNum, float chance){
    	this.boxNum = boxNum;
    	this.chance = chance;
        this.N = N;
        this.r = new Random();
    }

    public void run(){

        int wins = 0;
        for(int i = 0 ; i < N ; i ++)
            if(play())
                wins ++;

        System.out.println("winning rate: " + (double)wins/N);
    }

    private boolean play(){
    	for(int i=0; i<boxNum; i++) {
    		if(r.nextDouble() < chance) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public static void main(String[] args) {
    	int N = 1000000;
    	int boxNum = 5;
    	float chance = (float)0.2;
		WinningPrize experiment = new WinningPrize(N, boxNum, chance);
		experiment.run();
    }
}
