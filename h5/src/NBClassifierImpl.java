/***************************************************************************************
  CS540 - Section 2
  Homework Assignment 5: Naive Bayes

  NBClassifierImpl.java
  This is the main class that implements functions for Naive Bayes Algorithm!
  ---------
  	*Free to modify anything in this file, except the class name 
  	You are required:
  		- To keep the class name as NBClassifierImpl for testing
  		- Not to import any external libraries
  		- Not to include any packages 
	*Notice: To use this file, you should implement 2 methods below.

	@author: TA 
	@date: April 2017
*****************************************************************************************/

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class NBClassifierImpl implements NBClassifier {
	
	private int nFeatures; 		// The number of features including the class 
	private int[] featureSize;	// Size of each features
	private List<List<Double[]>> logPosProbs;	// parameters of Naive Bayes
	private double logposclass;
	private double lognegclass;
	
	/**
     * Constructs a new classifier without any trained knowledge.
     */
	public NBClassifierImpl() {

	}

	/**
	 * Construct a new classifier 
	 * 
	 * @param int[] sizes of all attributes
	 */
	public NBClassifierImpl(int[] features) {
		this.nFeatures = features.length;
		
		// initialize feature size
		this.featureSize = features.clone();

		this.logPosProbs = new ArrayList<List<Double[]>>(this.nFeatures);
	}


	/**
	 * Read training data and learn parameters
	 * 
	 * @param int[][] training data
	 */
	@Override
	public void fit(int[][] data) {
		// Calculate marginal probability
		double poscount = 0;
		double negcount = 0;
		for(int i = 0; i < data.length; i ++) {
			if(data[i][nFeatures - 1] == 1) {
				poscount++;
			} else negcount++;
		}
		
		double posclass = (poscount+1)/(data.length + 2); 
		double negclass = (negcount+1)/(data.length + 2); 	//binary n = 2
		
		logposclass = Math.log(posclass);
		lognegclass = Math.log(negclass);
		
		//System.out.println("posclass : " + posclass + " negclass: " + negclass);
		
		//Calculate conditional probability
		double[][] posvals = new double[nFeatures - 1][];
		double[][] negvals = new double[nFeatures - 1][];
		
		//Initializing arrays
		for(int i = 0; i < nFeatures - 1; i ++) {
			int nvals = featureSize[i];
			posvals[i] = new double[nvals];
			negvals[i] = new double[nvals];
			
			List<Double[]> values = new ArrayList<Double[]>();
	
				int val = featureSize[i];
				for(int k = 0; k < val; k ++) {
					Double[] temp = new Double[2];
					values.add(temp);
				}
			
			logPosProbs.add(values);
			
		}
		
		for(int i = 0; i < data.length; i ++) {		//instance
			for (int j = 0 ; j < data[0].length - 1; j++) {	 //attribute
				if(data[i][nFeatures - 1] == 1) {
					posvals[j][data[i][j]] ++;
				} else if(data[i][nFeatures - 1] == 0) {
					negvals[j][data[i][j]] ++;
				}
			}
		}
		
		//Get Log probabilities 
		for(int i = 0; i < logPosProbs.size(); i ++) {
			for(int j = 0 ; j < logPosProbs.get(i).size(); j ++) {
				double probs1 = (posvals[i][j] + 1)/(poscount + featureSize[i]);
				double probs0 = (negvals[i][j] + 1)/(negcount + featureSize[i]);
				logPosProbs.get(i).get(j)[1] = Math.log(probs1);
				logPosProbs.get(i).get(j)[0] = Math.log(probs0);
			}
		}
	
	}

	/**
	 * Classify new dataset
	 * 
	 * @param int[][] test data
	 * @return Label[] classified labels
	 */
	@Override
	public Label[] classify(int[][] instances) {
		
		int nrows = instances.length;
		Label[] yPred = new Label[nrows]; // predicted data

		// Probability that it is positive : log(P(Y = 1|e) = log(P(a|pos)) + log (P(b|pos)) + log(P(pos))
		// Probability that it's negative
		// Take max of 2 values
		for(int i = 0 ; i < nrows; i ++) {
			int[] instance = instances[i];
			double posp = 0;
			double posn = 0;
			for(int j = 0; j < nFeatures - 1; j++) {
				int att = instance[j];
				List<Double[]> vals = logPosProbs.get(j);
				Double[] logpos = vals.get(att);
				posn = posn + logpos[0];
				posp = posp + logpos[1];
			}
			posp = posp + logposclass;
			posn = posn + lognegclass;
			if(posp >= posn) {
				yPred[i] = Label.Positive;
			} else yPred[i] = Label.Negative;
		}

		return yPred;
	}
}