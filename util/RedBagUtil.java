package myutil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RedBagUtil {
	private final static float MINMONEY = 0.01f;//最少一毛钱
	private final static float MAXMONEY = 200f;//最多两百块
	private final static float TIMES = 2.3f;//最大金额是平均值的TIMES倍
	
	private static boolean bagIsRight(float money, int count){
		float average = money/count;
		if(average < MINMONEY){
			return false;
		}
		if(average > MAXMONEY){
			return false;
		}
		return true;
	}
	/**
	 * 产生随机红包
	 * @param money
	 * @param mins
	 * @param maxs
	 * @param count
	 * @return
	 */
	private static float randomRedBag(float money, float mins, float maxs, int count){
		if(count == 1){
			return (float)(Math.ceil(money*100)/100);
		}
		if(mins == maxs){
			return mins;
		}
		float max = maxs > money?money:maxs;
		float one = (float)Math.random()*(max - mins)+mins;
		one = (float)Math.floor(one*100)/100;
		float moneyOther = money - one;
		if(bagIsRight(moneyOther, count - 1)){
			return one;
		}
		else{
			//重新分配
			double average = moneyOther/(count-1);
			if(average < MINMONEY){
				return randomRedBag(money, mins, one, count);
			}
			if(average > MAXMONEY){
				return randomRedBag(money, one, maxs, count);
			}
		}
		return one; 
	}
	
	public static List<Float> splitRedBags(float money, int count){
		if(!bagIsRight(money, count)){
			return null;
		}
		List<Float> bags = new ArrayList<>();
		float max = (float)TIMES*(money/count);
		max = max>MAXMONEY?MAXMONEY:max;
		for (int i = 0; i < count; i++) {
			float one = randomRedBag(money, MINMONEY, max, count - i);
			bags.add(one);
			money-=one;
		}
		return bags;
	}
	
	public static void main(String[] args) {
		/*测试数据准备*/
		String[] people = {
				"萝莉","小姐","先生","屌丝","大叔","美女","女神","猫","苍老师"
		};
		Random r = new Random();
		float money = 300;
		int count = 20;
		//获取红包
		List<Float> redbags = splitRedBags(money,count);
		System.out.println("总金额"+money+"块");
		for (int i = 0; i < redbags.size(); i++) {
			String guy = people[r.nextInt(people.length)];
			System.out.println(guy+"获得了"+redbags.get(i)+"块钱！");
		}
	}
}
