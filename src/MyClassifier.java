import java.util.ArrayList;

import extrapackage.FileHandler;
import extrapackage.KNearestNeighbor;
import extrapackage.NaiveBayes;
import extrapackage.Point;

public class MyClassifier {
	static public void main(String args[]){
		FileHandler trainingFileHandler = new FileHandler(args[0]);
		trainingFileHandler.processLines();
		ArrayList<Point> trainingPointsList = trainingFileHandler.getPointsList();
		FileHandler testingFileHandler = new FileHandler(args[1]);
		testingFileHandler.processLines();
		ArrayList<Point> testingPointsList = testingFileHandler.getPointsList();
		
		if(args[2].endsWith("NN")){
			String str = "";
			str += args[2].substring(0, args[2].length() - 2);
			int k = Integer.parseInt(str);
			KNearestNeighbor myKNNClassifier = new KNearestNeighbor(trainingPointsList, k);
			for(int i = 0; i < testingPointsList.size(); i++){
				if(myKNNClassifier.isValid(testingPointsList.get(i)))
					System.out.println("yes");
				else
					System.out.println("no");
			}
		} else if(args[2].endsWith("NB")) {
			NaiveBayes myNBClassifier = new NaiveBayes(trainingPointsList);
			for(int i = 0; i < testingPointsList.size(); i++){
				if(myNBClassifier.isValid(testingPointsList.get(i)))
					System.out.println("yes");
				else
					System.out.println("no");
			}
		}
	}
}
