package montecarlo;

import java.util.Random;

public class ThreeGatesExperiment {

	private int N;
	private Random r;

    public ThreeGatesExperiment(int N){

        if(N <= 0)
            throw new IllegalArgumentException("N must be larger than 0!");

        this.N = N;
        this.r = new Random();
    }

    public void run(boolean changeDoor){

        int wins = 0;
        for(int i = 0 ; i < N ; i ++)
            if(play(changeDoor))
                wins ++;

        System.out.println(changeDoor ? "Change" : "Not Change");
        System.out.println("winning rate: " + (double)wins/N);
    }

    private boolean play(boolean changeDoor){
    	int carDoor = r.nextInt(3);
    	int choosedDoor = r.nextInt(3);
    	// if choose the carDoor, if not change , he win, vice versa 
    	return (carDoor == choosedDoor)?!changeDoor : changeDoor;
    }
    
    public static void main(String[] args) {
    	int N = 100000;
		ThreeGatesExperiment experiment = new ThreeGatesExperiment(N);
		experiment.run(true);
		System.out.println("----------------------------------------------");
		experiment.run(false);
    }
}
