package array.tools;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class ArrayListDoubleTools 
{
	/**
	 * Get the median value for each position of an Collection of ArrayLists<Double>. 
	 * @param arrayListCollection
	 * @return
	 */
	public static ArrayList<Double> getMedian( final ArrayList<ArrayList<Double>> arrayListCollection )
	{
		DescriptiveStatistics ds = new DescriptiveStatistics(); 
		ArrayList<Double> output = new ArrayList<Double>();
		
		for( int i = 0; i < arrayListCollection.get( 0 ).size() ; i++ )	
		{
			ds.clear(); 
			for( int j = 0; j < arrayListCollection.size(); j++ )
			{
				ds.addValue( arrayListCollection.get( j ).get( i ) ); 
			}
			output.add( ds.getPercentile( 50.0 ) ); 
		}
		return output; 
	}
	
	/**
	 * Divides the first input array by the second input array 
	 * @param in1 
	 * @param in2  
	 * @return
	 */
	public static ArrayList<Double> divide( final ArrayList<Double> in1, final ArrayList<Double> in2 )
	{
		ArrayList<Double> out = new ArrayList<Double>(); 
		for( int i = 0; i < in1.size(); i++ )
		{
			out.add( in1.get( i ) / in1.get( i ) ); 
		}
		return out; 
	}
	
	/**
	 * Substracts the first input array by the second input array 
	 * @param in1 
	 * @param in2  
	 * @return
	 */
	public static ArrayList<Double> substract( final ArrayList<Double> in1, final ArrayList<Double> in2 )
	{
		ArrayList<Double> out = new ArrayList<Double>(); 
		for( int i = 0; i < in1.size(); i++ )
		{
			out.add( in1.get( i ) - in2.get( i ) ); 
		}
		return out; 
	}
	
	/**
	 * Returns a string with the values of the input ArrayList<Double> delimited by a defined String
	 * @param input 
	 * @param delimiter 
	 * @return
	 */
	public static String arrayListToString( final ArrayList<Double> input, final String delimiter )
	{
		String s = ""; 
		
		for( int i = 0; i < input.size(); i++ )
		{			
			if( i != 0 )
				s += delimiter;
			
			s += input.get( i ); 
		}
			
		return s; 
	}
	
	/**
	 * Calculates the arithmetic mean ArrayList<Double>.
	 * ArithmeticMean mean (A): A = 1/n * Sum (Xi) 
	 */
	public static double getArithmeticMean( final ArrayList<Double> in )
	{
		//ArithmeticMean mean (A): A = 1/n * Sum (Xi)
		double sum = 0;
		int count = 0;  
		for( Double d : in )
		{ 
			if( !Double.isNaN( d ) )
			{
				sum += d; 
				count++; 				
			}
		}
		
		return sum/count; 
	}
	
	/**
	 * Calculates the sum of ArrayList<Double>. 
	 */
	public static double sum( final ArrayList<Double> in )
	{
		double sum = 0;   
		for( Double d : in )
		{ 
			if( !Double.isNaN( d ) )
			{
				sum += d;  				
			}
		}
		
		return sum; 
	}
	
	/**
	 * Returns the index of the double[] with the lowest sd. 
	 * 
	 * @param in
	 * @return
	 */
	public static int getIndexOfLowestSD( final ArrayList<double[]> in )
	{ 
		DescriptiveStatistics ds = null;
		
		double minSD = Double.MAX_VALUE;
		double currSD = Double.MAX_VALUE; 
		int minIndex = -1;
		for( int i = 0; i < in.size(); i++ )
		{
			ds = new DescriptiveStatistics( in.get( i ) ); 
			currSD = ds.getStandardDeviation();
			if( currSD < minSD )
			{
				minSD = currSD;
				minIndex = i; 
			}
		}
		return minIndex; 
	}
	
	/**
	 * Returns the index of the double[] with the lowest sd. 
	 * 
	 * @param in
	 * @return
	 */
	public static int getIndexOfLowestVar( final ArrayList<double[]> in )
	{ 
		DescriptiveStatistics ds = null;
		
		double minVar = Double.MAX_VALUE;
		double currVar = Double.MAX_VALUE; 
		int minIndex = -1;
		for( int i = 0; i < in.size(); i++ )
		{
			ds = new DescriptiveStatistics( in.get( i ) ); 
			currVar = ds.getVariance(); 
			if( currVar < minVar )
			{
				minVar = currVar;
				minIndex = i; 
			}
		}
		return minIndex; 
	}
	
	/**
	 * Transposes the ArrayList<double[]>.
	 * 1. Initiate the new double[] which have the length of the old arrayList. 
	 * @param in
	 * @return
	 */
	public static ArrayList<double[]> transpose( final ArrayList<double[]> in )
	{
		ArrayList<double[]> out = new ArrayList<double[]>(); 
		//Initiate the new double[]
		for( int i = 0; i < in.get( 0 ).length; i++ )
			out.add( new double[ in.size() ] ); 
		
		//Fill 
		for( int i = 0; i < in.size(); i++ )
		{
			for( int j = 0; j < out.size(); j++ )
			{
				out.get( j )[ i ] = in.get( i )[ j ]; 
			}
		}
			
		return out; 
	}
	
	
	/**
	 * Calculates the geometric for each matrix position.
	 * Geometrical mean (G): LogG = 1/n * Sum (logXi) 
	 * If you want to specify how zeros are treated, add a substitute for zeros. 
	 * @param matrixList
	 * @return
	 */
	public static double[][] getGeometricMean( final ArrayList<double[][]> matrixList, final double treatZerosAs )
	{
		//Geometrical mean (G): LogG = 1/n * Sum (logXi)
		final int xDim = matrixList.get( 0 )[ 0 ].length;
		final int yDim = matrixList.get( 0 ).length; 
		
		final double[][] summedSimilarityMatrix = new double[ yDim ][ xDim ]; 
		final double[][] weightMatrix = new double[ yDim ][ xDim ];
		final double[][] nanMatrix = new double[ yDim ][ xDim ];
		
		// Sum up matrix and update weight Matrix 
		double[][] currentMatrix = null;
		double currentValue = 0.0; 
		
			//Sum over all LogXi 
			int listIndex; 
			for( listIndex = 0; listIndex < matrixList.size(); listIndex++ )
			{
				currentMatrix = matrixList.get( listIndex );
				for( int j = 0; j < yDim; j++ )
				{
					for( int i = 0; i < xDim; i++ )
					{
						currentValue = currentMatrix[ j ][ i ]; 
						if(  /*currentValue != 0 && */ !Double.isNaN( currentValue ) )
						{	
							if( currentValue == 0 )
								currentValue = treatZerosAs; 
							
							
							summedSimilarityMatrix[ j ][ i ] +=  Math.log10( currentValue ); 
							weightMatrix[ j ][ i ] += 1; 
						}
						else
						{
							nanMatrix[ j ][ i ] += 1; 
						}
					}
				}
			} 
			
			//Multiply by 1/n and invert the logarithm  
			for( int j = 0; j < yDim; j++ )
			{
				for( int i = 0; i < xDim; i++ )
				{
					summedSimilarityMatrix[ j ][ i ] = summedSimilarityMatrix[ j ][ i ] * 1/weightMatrix[ j ][ i ];
					summedSimilarityMatrix[ j ][ i ] = Math.pow( 10, summedSimilarityMatrix[ j ][ i ] ); 
				}
			}
			
			//System.out.println( DoubleArrayTools.matrixToString( weightMatrix ) + "\n");
			return summedSimilarityMatrix; 
	}
	
	/**
	 * Calculates the arithmetic mean for each matrix position.
	 * ArithmeticMean mean (A): A = 1/n * Sum (Xi)
	 * If you want to specify how zeros are treated, add a substitute for zeros. 
	 * @return
	 */
	public static double[][] getArithmeticMean( final ArrayList<double[][]> matrixList, final double treatZerosAs)
	{
		//ArithmeticMean mean (A): A = 1/n * Sum (Xi)
		final int xDim = matrixList.get( 0 )[ 0 ].length;
		final int yDim = matrixList.get( 0 ).length; 
		
		final double[][] summedSimilarityMatrix = new double[ yDim ][ xDim ]; 
		final double[][] weightMatrix = new double[ yDim ][ xDim ];
		final double[][] nanMatrix = new double[ yDim ][ xDim ];
		
		// Sum up matrix and update weight Matrix 
		double[][] currentMatrix = null;
		double currentValue = 0.0; 
		
			//Sum over all LogXi 
			int listIndex; 
			for( listIndex = 0; listIndex < matrixList.size(); listIndex++ )
			{
				currentMatrix = matrixList.get( listIndex );
				for( int j = 0; j < yDim; j++ )
				{
					for( int i = 0; i < xDim; i++ )
					{
						currentValue = currentMatrix[ j ][ i ]; 
						if(  /*currentValue != 0 && */ !Double.isNaN( currentValue ) )
						{	
							if( currentValue == 0 )
								currentValue = treatZerosAs; 
							
							
							summedSimilarityMatrix[ j ][ i ] +=  currentValue; 
							weightMatrix[ j ][ i ] += 1; 
						}
						else
						{
							nanMatrix[ j ][ i ] += 1; 
						}
					}
				}
			} 
			
			
			//Multiply by 1/n   
			for( int j = 0; j < yDim; j++ )
			{
				for( int i = 0; i < xDim; i++ )
				{
					summedSimilarityMatrix[ j ][ i ] = summedSimilarityMatrix[ j ][ i ] * 1/weightMatrix[ j ][ i ]; 
				}
			}
			
			//System.out.println( DoubleArrayTools.matrixToString( summedSimilarityMatrix ) + "\n");
			//System.out.println( DoubleArrayTools.matrixToString( weightMatrix ) + "\n");
			
			return summedSimilarityMatrix; 
	}
	
	/**
	 * Converts an ArrayList<double[]> to an double[][]
	 * @param in
	 * @return
	 */
	public static double[][] toDoubleMatrix( final ArrayList<double[]> in )
	{
		final int nrRows = in.size(); 
		final int nrCols = in.get( 0 ).length; 
		final double[][] out = new double[ nrRows ][ nrCols ]; 
		
		for( int row = 0; row < nrRows; row++ )
		{
			for( int col = 0; col < nrCols; col++ )
			{
				out[ row ][ col ] = in.get( row )[ col ]; 
			}
		}
		
		return out; 
	}
	
	/**
	 * Convert ArrayList<Double> to double[] 
	 * @param in
	 * @return
	 */
	public static double[] toArray( final ArrayList<Double> in )
	{
		final double[] out = new double[ in.size() ]; 
		for( int i = 0; i < in.size(); i++ )
		{
			out[ i ] = in.get( i ); 
		}
		return out; 
	}
	
	/**
	 * Converts an ArrayList<ArrayList<Double>> to an double[][]
	 * @param in
	 * @return
	 */
	public static double[][] toDoubleMatrixB( final ArrayList<ArrayList<Double>> in )
	{
		final int nrRows = in.size(); 
		final int nrCols = in.get( 0 ).size(); 
		final double[][] out = new double[ nrRows ][ nrCols ]; 
		
		for( int row = 0; row < nrRows; row++ )
		{
			for( int col = 0; col < nrCols; col++ )
			{
				out[ row ][ col ] = in.get( row ).get( col ); 
			}
		}
		
		return out; 
	}
	
	public static ArrayList<double[]> removeDoubleEntries( final ArrayList<double[]> in )
	{
		HashMap<String, double[]> set = new HashMap<String, double[]>(); 
		for( double[] d : in )
			set.put(DoubleArrayTools.arrayToString( d ), d ); 
		
		return new ArrayList<double[]>( set.values() ); 
	}
	
	
	public static void main(String[] args) 
	{
		final double[] array1 = { 1.0, 3.0, -5.0 };
		final double[] array2 = { 4.0, -2.0, -1.0 };
		final double[] array3 = { 4.0, -2.0, -1.0 };
		final double[] array4 = { 40.0, -20.0, -10.0 };
		ArrayList<double[]> a = new ArrayList<double[]>(); 
		a.add( array1 ); 
		a.add( array2 );
		a.add( array3 );
		a.add( array4 ); 
		
		for( int i = 0; i < a.size(); i++ )
			System.out.println( DoubleArrayTools.arrayToString( a.get( i ) ) ); 
		System.out.println( ); 
		
		ArrayList<double[]> b = transpose( a ); 
		
		for( int i = 0; i < b.size(); i++ )
			System.out.println( DoubleArrayTools.arrayToString( b.get( i ) ) ); 

		
		ArrayList<Double> c = new ArrayList<Double>(); 
		c.add( 1.0 ); c.add( 2.0 ); c.add( 3.0 ); c.add( 4.0 ); c.add( 5.0 ); 
		
		System.out.println( DoubleArrayTools.arrayToString( toArray( c ) ) ); 
	}
}
