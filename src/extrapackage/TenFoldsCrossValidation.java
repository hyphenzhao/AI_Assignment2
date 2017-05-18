package extrapackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TenFoldsCrossValidation {
	private ArrayList<Point> pointsList;
	private double accuracyOneNN = 0.0;
	private double accuracyFiveNN = 0.0;
	private double accuracyNB = 0.0;
	private Comparator c = new Comparator<Point>() {
		@Override
		public int compare(Point p1, Point p2) {
			int f1 = p1.getFoldNo();
			int f2 = p2.getFoldNo();
			if(f1 > f2) return 1;
			return -1;
		}
		
	};
	
	public TenFoldsCrossValidation(ArrayList<Point> pointsList){
		this.pointsList = pointsList;
		int yes = 0, no = 0;
		for(int i = 0; i < this.pointsList.size(); i++){
			if(this.pointsList.get(i).getResult()){
				this.pointsList.get(i).setFoldNo(yes % 10);
				yes++;
			}else{
				this.pointsList.get(i).setFoldNo(no % 10);
				no++;
			}
		}
		Collections.sort(this.pointsList, c);
	}
	
	public void startValidation(){
		for(int i = 0; i < 10; i++){
			ArrayList<Point> trainingPointsList = new ArrayList<Point>();
			ArrayList<Point> testingPointsList = new ArrayList<Point>();
			for(int j = 0; j < pointsList.size(); j++){
				if(pointsList.get(j).getFoldNo() == i){
					testingPointsList.add(pointsList.get(j));
				}else{
					trainingPointsList.add(pointsList.get(j));
				}
			}
			
			accuracyOneNN += validKNN(trainingPointsList, testingPointsList, 1);
			accuracyFiveNN += validKNN(trainingPointsList, testingPointsList, 5);
			accuracyNB += validNB(trainingPointsList, testingPointsList);
		}
		
		double total = (double)pointsList.size(); 
		accuracyOneNN = accuracyOneNN / total;
		accuracyFiveNN = accuracyFiveNN / total;
		accuracyNB = accuracyNB / total;
	}
	
	public double validKNN(ArrayList<Point> trainingPointsList, 
			ArrayList<Point> testingPointsList, int k){
		double correctness = 0.0;
		
		KNearestNeighbor myKNNClassifier = new KNearestNeighbor(trainingPointsList, k);
		for(int i = 0; i < testingPointsList.size(); i++){
			if(myKNNClassifier.isValid(testingPointsList.get(i)) == testingPointsList.get(i).getResult())
				correctness += 1.0;
		}
		
		return correctness;
	}
	
	public double validNB(ArrayList<Point> trainingPointsList, 
			ArrayList<Point> testingPointsList){
		double correctness = 0.0;
		
		NaiveBayes myNBClassifier = new NaiveBayes(trainingPointsList);
		for(int i = 0; i < testingPointsList.size(); i++){
			if(myNBClassifier.isValid(testingPointsList.get(i)) == testingPointsList.get(i).getResult())
				correctness += 1.0;
		}
		
		return correctness;
	}
	
	public String toString(){
		String str = "";
		
		str += "My1NN Accuracy: " + this.accuracyOneNN * 100.0 + "%\n";
		str += "My5NN Accuracy: " + this.accuracyFiveNN * 100.0 + "%\n";
		str += "MyNB Accuracy: " + this.accuracyNB * 100.0 + "%\n";
				
		return str;
	}
}
