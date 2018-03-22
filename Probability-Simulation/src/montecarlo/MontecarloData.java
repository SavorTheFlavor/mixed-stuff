package montecarlo;

import java.awt.Point;
import java.util.LinkedList;

public class MontecarloData {
    private Circle circle;
    private LinkedList<Point> points;
    private int insidePoints;
    
	public MontecarloData(int sceneSize) {
        this.circle = new Circle(sceneSize/2, sceneSize/2, sceneSize/2);
        this.points = new LinkedList<Point>();
        this.points.add(new Point(sceneSize/2, sceneSize/2)); // advoid divide zero or if statement
        this.insidePoints = 0;
	}
    
    public  Circle getCircle() {
    	return this.circle;
    }
    
    public Point getPoint(int i) {
    	return points.get(i);
    }
    
    public int getPointsNumber() {
    	return points.size();
    }
    
    public void addPoint(Point p) {
    	points.add(p);
    	if(circle.contain(p)) {
    		insidePoints++;
    	}
    }
    
    public double estimatePI(){
    	return 4*insidePoints/(double)points.size();
    }
}
