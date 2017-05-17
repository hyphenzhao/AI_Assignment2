package extrapackage;
import java.util.ArrayList;
import java.lang.Math;

public class NaiveBayes {
	private ArrayList<Point> trainingPoints;
	
	private double yesAttributeMean[] = new double[Default.ARRAY_LENGTH];
	private double yesAttributeSD[] = new double[Default.ARRAY_LENGTH];
	private double yesAttributeData[];
	private int yesSize = 0;
	
	private double noAttributeMean[] = new double[Default.ARRAY_LENGTH];
	private double noAttributeSD[] = new double[Default.ARRAY_LENGTH];
	private double noAttributeData[];
	private int noSize = 0;
	
	public NaiveBayes(ArrayList<Point> points){
		this.trainingPoints = points;
		for(int i = 0; i < this.trainingPoints.size(); i++){
			if(this.trainingPoints.get(i).getResult())
				yesSize++;
			else
				noSize++;
		}
		noAttributeData = new double[noSize];
		yesAttributeData = new double[yesSize];
		for(int i = 0; i < Default.ARRAY_LENGTH; i++){
			int yes = 0, no = 0;
			for(int j = 0; j < this.trainingPoints.size(); j++){
				if(this.trainingPoints.get(j).getResult())
					yesAttributeData[yes++] = trainingPoints.get(j).getDataAt(i);
				else
					noAttributeData[no++] = trainingPoints.get(j).getDataAt(i);
			}
			noAttributeMean[i] = calculateMean(noAttributeData);
			noAttributeSD[i] = calculateSD(noAttributeData, noAttributeMean[i]);
			yesAttributeMean[i] = calculateMean(yesAttributeData);
			yesAttributeSD[i] = calculateSD(yesAttributeData, yesAttributeMean[i]);
		}
		
	}
	
	public boolean isValid(Point point){
		double yesProbability = (double)yesSize;
		double noProbability = (double)noSize;
		for(int i = 0; i < Default.ARRAY_LENGTH; i++){
			yesProbability *= calculateProbability(yesAttributeMean[i],
					yesAttributeSD[i],
					point.getDataAt(i));
			noProbability *= calculateProbability(noAttributeMean[i],
					noAttributeSD[i],
					point.getDataAt(i));
		}
		
		return yesProbability >= noProbability;
	}
	private double calculateProbability(double mean, double sd, double x){
		double result = 0.0;
		double exponent = 0.0;
		double index = 0.0;
		
		exponent = -Math.pow(x - mean, 2.0) / (2.0 * Math.pow(sd, 2.0));
		index = 1.0 / (sd * Math.sqrt(2.0 * Math.PI));
		result = index * Math.exp(exponent);
		
		return result;
	}
	private double calculateMean(double input[]){
		double sum = 0.0;
		for(int i = 0; i < input.length; i++){
			sum += input[i];
		}
		
		if(input.length == 0) return 0.0;
		
		return sum/input.length;
	}
	private double calculateSD(double input[], double mean){
		double sum = 0.0;
		double n = (double) input.length;
		
		for(int i = 0; i < input.length; i++){
			sum += Math.pow(input[i] - mean, 2.0);
		}
		
		if(input.length == 0) return 0.0;
		
		return Math.sqrt(sum / n);
	}
	public void test(double mean, double sd, double x) {
		System.out.println(calculateProbability(mean, sd, x));
	}
	public String toString(){
		String str = "";
		for(int i = 0; i < Default.ARRAY_LENGTH; i++){
			str += noAttributeMean[i] + "\t" + noAttributeSD[i] + "\n";
		}
		return str;
	}
}
