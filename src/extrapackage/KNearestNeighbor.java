package extrapackage;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class KNearestNeighbor{
	private ArrayList<Point> points;
	private int numberOfNeighbors;
	private Comparator c = new Comparator<Point>() {
		@Override
		public int compare(Point p1, Point p2) {
			double d1 = p1.getDistance();
			double d2 = p2.getDistance();
			if(d1 < d2) return 1;
			return -1;
		}
		
	};
	
	public KNearestNeighbor(ArrayList<Point> points, int numberOfNeighbors){
		this.points = points;
		this.numberOfNeighbors = numberOfNeighbors;
	}

	public boolean isValid(Point p){
		ArrayList<Point> priorQue = new ArrayList<Point>();
		int yes = 0, no = 0;
		for(int i = 0; i < points.size(); i++){
			double tDistance = euclideanDistanceBetween(points.get(i), p);
			points.get(i).setDistance(tDistance);
			
//			System.out.println(i+ ":" + points.get(i).getDistance());
			
			if(priorQue.size() < numberOfNeighbors){
				priorQue.add(points.get(i));
				continue;
			}
			
			if(priorQue.size() == numberOfNeighbors){
				Collections.sort(priorQue, c);
			}
			
			for(int j = 0; j < priorQue.size(); j++){
				if(tDistance < priorQue.get(j).getDistance()){
					priorQue.set(j, points.get(i));
					Collections.sort(priorQue, c);  
					break;
				}
			}
		}

		for(int i = 0; i < priorQue.size(); i++){
//			System.out.println(priorQue.get(i).getDistance() + ": " + priorQue.get(i).getResult());
			if(priorQue.get(i).getResult())
				yes++;
			else
				no++;
		}		
//		System.out.println(yes);
//		System.out.println(no);
		return yes >= no;
	}

	public double euclideanDistanceBetween(Point a, Point b){
		double sum = 0;
		for(int i = 0; i < Default.ARRAY_LENGTH; i++){
			sum += Math.pow(a.getDataAt(i) - b.getDataAt(i), 2.0);
		}
		return Math.sqrt(sum);
	}
}