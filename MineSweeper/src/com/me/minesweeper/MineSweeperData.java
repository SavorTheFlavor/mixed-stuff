package com.me.minesweeper;

import java.io.File;
import java.util.Random;

public class MineSweeperData {
	public static final char separator = new File("").separatorChar;
	public static final String basePath = System.getProperty("user.dir")+"/src";
	private final Random random = new Random();
	
	public static final String blockImg = basePath + "/resources/block.png".replace('/', separator);
	public static final String flagImg = basePath + "/resources/flag.png".replace('/', separator);
	public static final String mineImg = basePath + "/resources/mine.png".replace('/', separator);
	
	public static String getNumberImg(int num) {
		//System.out.println(basePath + ("/resources/"+num+".png").replace('/', separator));
		return basePath + ("/resources/"+num+".png").replace('/', separator);
	}

	private int N;
	private int M;
	private int mineNum;  // N*M
	private boolean[][] mines;
	public boolean[][] open;
	public boolean[][] flags;
	private int[][] numbers; //周围的雷数
	
	// generate a N*M map
	public MineSweeperData(int N, int M, int mineNum){
		this.N = N;
		this.M = M;
		if(N*M < mineNum) {
			throw new IllegalArgumentException("too many mines!");
		}
		this.mineNum = mineNum;
		initMinesMatrix(mineNum);
	}
	
	private void initMinesMatrix(int mineNum) {
		this.mines = new boolean[N][M];
		this.open = new boolean[N][M];
		this.flags = new boolean[N][M];
		this.numbers = new int[N][M];
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				this.mines[i][j] = false;
				this.open[i][j] = false;
				this.flags[i][j] = false;
				this.numbers[i][j] = 0;
			}
		}
		
		//生成有序序列，再通过交换打乱..
		for(int i=0; i<mineNum; i++) {
			int x = i/M;
			int y = i%N;
			mines[x][y] = true;
		}
		//使用KnuthShuffle算法
		for(int i=0; i<N*M; i++) {
			int row = i/M;
			int col = i%N;
			int x1 = random.nextInt(M - row) + row;
			int y1 = random.nextInt(N - col) + col;
			swap(i/M,i%N,x1,y1,mines);
		}
		
		calculateNumbers(); //calculate the mines number around the mine
		
//		for(int i=0; i<mineNum; i++) {
//			// avoid duplicate
//			//随机交换....雷数过多时效率太低！
//			while(true) {
//				int x = random.nextInt(M);
//				int y = random.nextInt(N);
//				if(!mines[x][y]) {
//					mines[x][y] = true;
//					break;
//				}
//			}
//		}
	}
	
    private void calculateNumbers() {
        for(int i = 0 ; i < N ; i ++)
            for(int j = 0 ; j < M ; j ++){
            	if(mines[i][j]) {
            		numbers[i][j] = -1;
            	}else {
            		// check the 3*3 around the grid
            		for(int ii=i-1; ii<=i+1; ii++) {
            			for(int jj=j-1; jj<=j+1; jj++) {
            				if(inArea(ii, jj) && isMine(ii, jj)) {
            					numbers[i][j]++;
            				}
            			}
            		}
            	}
            }
	}

	private void swap(int x1, int y1, int x2, int y2, boolean[][] mines2) {
		if(mines2[x1][y1] ^ mines2[x2][y2]) { //xor为true才交换
			mines2[x1][y1] = mines2[x2][y2];
			mines2[x2][y2] = !mines2[x2][y2]; //取反即可
		}
	}

    
	public boolean isMine(int x, int y){
        if(!inArea(x, y))
            throw new IllegalArgumentException("Out of index in isMine function!");
        return mines[x][y];
    }
	
    public boolean inArea(int x, int y){
        return x >= 0 && x < N && y >= 0 && y < M;
    }

	public int N() {
		return N;
	}
	public int M() {
		return M;
	}
	
	public int getNumber(int i, int j) {
		return numbers[i][j];
	}

	public void openCells(int i, int j) {
		if(!inArea(i, j)) {
			return;
		}
		
		
		if(getNumber(i, j) > 0) {
			open[i][j] = true;
			return;
		}
		
		if(numbers[i][j] == 0 && !open[i][j]) {
			open[i][j] = true;
			openCells(i-1, j);
			openCells(i+1, j);
			openCells(i, j-1);
			openCells(i, j+1);
		} 
	}
	
}
