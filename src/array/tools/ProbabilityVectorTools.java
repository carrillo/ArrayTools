package array.tools;

import java.util.ArrayList;
import java.util.Random;

public class ProbabilityVectorTools 
{
	/**
	 * Makes any vector in stochastic such that Sum( in_i ) = 1
	 * @param in
	 * @return
	 */
	public static double[] probabilityVector( final double[] in )
	{
		final double[] out = in.clone(); 
		final double sum = DoubleArrayTools.sumOfArrayEntries( in ); 
		for( int i = 0; i < out.length; i++ )
			out[ i ] = out[ i ] / sum; 
		return out; 
	}
	
	/**
	 * Makes any vector in stochastic such that Sum( in_i ) = 1
	 * @param in
	 * @return
	 */
	public static double[] probabilityVector( final int[] in )
	{
		final double[] out = new double[ in.length ];
		double sum = 0; 
		for( int i = 0; i < in.length; i++ )
		{
			sum += in[ i ]; 
			out[ i ] = (double) in[ i ]; 
		} 
		
		for( int i = 0; i < out.length; i++ )
			out[ i ] = out[ i ] / sum; 
		return out; 
	}
	
	/**
	 * Makes any vector in stochastic such that Sum( in_i ) = 1
	 * @param in
	 * @return
	 */
	public static double[] probabilityVector( final long[] in )
	{
		final double[] out = new double[ in.length ];
		double sum = 0; 
		for( int i = 0; i < in.length; i++ )
		{
			sum += in[ i ]; 
			out[ i ] = (double) in[ i ]; 
		} 
		
		for( int i = 0; i < out.length; i++ )
			out[ i ] = out[ i ] / sum; 
		return out; 
	}
	
	/**
	 * Makes any vector in stochastic such that Sum( in_i ) = 1
	 * @param in
	 * @return
	 */
	public static double[] probabilityVector( final ArrayList<Long> in )
	{
		final double[] out = new double[ in.size() ];
		double sum = 0; 
		for( int i = 0; i < in.size(); i++ )
		{
			sum += in.get( i ); 
			out[ i ] = (double) in.get( i ); 
		} 
		
		for( int i = 0; i < out.length; i++ )
			out[ i ] = out[ i ] / sum; 
		return out; 
	}
	
	/**
	 * Randomly samples a given probability vector. 
	 * Returns the matching index. 
	 * @param row
	 * @param rand
	 * @return
	 */
	public static int getIndexFromStochasticRow( final double[] row, final Random rand )
	{
		final double r = rand.nextDouble(); 
		double rowSum = 0; 
		int index = -1; 
		while( r > rowSum )
		{
			index++; 
			rowSum += row[ index ]; 
		}
		return index; 
	}
	
	/**
	 * Randomly samples a given probability vector. 
	 * Returns the matching index. 
	 * @param row
	 * @param rand
	 * @return
	 */
	public static int getIndexFromStochasticRow( final double[] row )
	{
		final Random rand = new Random(); 
		return getIndexFromStochasticRow( row, rand ); 
	}
	
	/**
	 * Returns a discrete event chain drawn from a sequence of stochastic vectors. 
	 *  
	 * @param probVecSeq
	 * @return
	 */
	public static int[] getObservationsFromSequenceOfProbVectors( final ArrayList<double[]> probVecSeq )
	{
		final int[] observation = new int[ probVecSeq.size() ]; 
		
		for( int i = 0; i < probVecSeq.size(); i++ )
			observation[ i ] =  getIndexFromStochasticRow( probVecSeq.get( i ) ); 
		
		return observation; 
	}
	
	/**
	 * Returns a discrete event chain drawn from a sequence of stochastic vectors. 
	 * 
	 * Stochastic vectors are stored in rows of matrix 
	 *  
	 * @param probVecSeq
	 * @return
	 */
	@Deprecated //: Not tested
	public static int[] getObservationsFromSequenceOfProbRowVectors( double[][] probRowMatrix )
	{
		final int[] observation = new int[ probRowMatrix[ 0 ].length ]; 
		
		for( int i = 0; i < probRowMatrix.length; i++ )
			observation[ i ] =  getIndexFromStochasticRow( probRowMatrix[ i ] ); 
		
		return observation; 
	}
	
	/**
	 * Returns a discrete event chain drawn from a sequence of stochastic vectors. 
	 * 
	 * Stochastic vectors are stored in columns of matrix 
	 *  
	 * @param probVecSeq
	 * @return
	 */
	@Deprecated //: Not tested
	public static int[] getObservationsFromSequenceOfProbColVectors( double[][] probColMatrix )
	{
		final int[] observation = new int[ probColMatrix.length ]; 
		
		for( int i = 0; i < probColMatrix[ 0 ].length; i++ )
			observation[ i ] =  getIndexFromStochasticRow(  DoubleArrayTools.getColumn( probColMatrix, i ) ); 
		
		return observation; 
	}
	
	/**
	 * Calculates the information content stored in a propability vector 
	 * The entropy is a measure how well the information is spread over all 
	 * possible states. 
	 * 
	 * States i, Probability P_i
	 * 
	 * S = Sum( P_i * ln( P_i ) ) 
	 * 
	 * @param stochasticVector
	 * @return
	 */
	public static double getEntropy( final double[] stochasticVector )
	{
		double entropy = 0; 
		for( Double d : stochasticVector )
		{
			if( d != 0 )
				entropy += -d * Math.log( d ); 
		}
		return entropy; 
	}
	
	/**
	 * Calculates the information content stored in a propability vector 
	 * The normalized entropy / efficiency is a measure how well the information is spread over all 
	 * possible states. 
	 * 
	 * States i, Probability P_i
	 * 
	 * S = Sum ( P_i * ln( P_i ) / ln( n ) ) 
	 * 
	 * @param stochasticVector
	 * @return
	 */
	public static double getEfficiency( final double[] stochasticVector )
	{
		double efficiency = 0; 
		final double maxEntropy = Math.log( stochasticVector.length );  
		for( Double d : stochasticVector )
		{
			if( d != 0 )
				efficiency += - ( d * Math.log( d ) ) / maxEntropy ; 
		}
		return efficiency; 
	}
	
	/**
	 * Calculates the information content stored in a propability vector 
	 * The entropy is a measure how well the information is spread over all 
	 * possible states. 
	 * 
	 * States i, Probability P_i
	 * 
	 * S = P_i * ln( P_i ) 
	 * 
	 * @param stochasticVector
	 * @return
	 */
	public static double getEntropy( final Double[] stochasticVector )
	{
		double entropy = 0; 
		for( Double d : stochasticVector )
		{
			if( d != 0 )
				entropy += -d * Math.log( d ); 
		}
		return entropy; 
	}
	
	/** 
	 *  Normalizes the columns in the matrix to 1. 
	 */
	public static double[][] makeColumnStochastic( final double[][] in )
	{
		final double[] colSum = new double[ in[ 0 ].length ]; 
		for( int row = 0; row < in.length; row++ )
		{
			for( int col = 0; col < in[ row ].length; col++ )
			{
				colSum[ col ] += in[ row ][ col ]; 
			}
		}
		final double[][] out = in.clone();
		for( int row = 0; row < in.length; row++ )
		{
			for( int col = 0; col < in[ row ].length; col++ )
			{
				out[ row ][ col ] = in[ row ][ col ] / colSum[ col ]; 
			}
		}
		return out; 
	}

	
	public static void main(String[] args) 
	{
		final double[] row1 = { 0.5, 0.5 }; 
		final double[] row2 = { 1, 0.0, 0.0 };
		final double[] row3 = { 0.2, 0.2, 0.2, 0.2, 0.2 };
		
		final ArrayList<double[]> probVecSeq = new ArrayList<double[]>(); 
		probVecSeq.add( row1 ); 
		probVecSeq.add( row2 ); 
		probVecSeq.add( row3 ); 
		
		//for( double[] d : probVecSeq )
			//System.out.println( getEntropy( d ) ); 
		
		final double[][] matrix1 = new double[][]{
				  { 0.0, 0.908597820519329,	0.9348686513077822,	0.932407031890514, 0.9280261140145634,	0.9384882238428602 },
				  { 0.908597820519329,	Double.NaN,	0.8962549951762211,	0.9695756559868266,	0.8531231610175372,	0.9005486194348331 },
				  { 0.9348686513077822,	0.8962549951762211,	0.0,	0.9651262503390295,	0.899104002646973,	0.858505533294865 },
				  { 0.932407031890514,	0.9695756559868266,	0.9651262503390295,	0.0,	0.9427823996475913,	0.9473669049367176 },
				  { 0.9280261140145634,	0.8531231610175372,	0.899104002646973,	0.9427823996475913,	0.0,	0.8783195043804446 },
				  { 0.9384882238428602,	0.9005486194348331,	0.858505533294865,	0.9473669049367176,	0.8783195043804446,	0.0 }, 
				}; 
		
		
		System.out.println( DoubleArrayTools.matrixToString( makeColumnStochastic( matrix1 ) ) ); 
		
		//System.out.println( IntArrayTools.arrayToString( getObservationsFromSequenceOfProbVectors( probVecSeq ) ) ); 
	}
}
