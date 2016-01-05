package array.normalization;

import array.tools.DoubleArrayTools;

public class DoubleArrayNormalization 
{
	/**
	 * Makes values in double[] relative to the maximum value. 
	 * @param in
	 * @return
	 */
	public static double[] makeRelativeToMax( final double[] in ) 
	{
		final double max = DoubleArrayTools.getMaximum( in ); 
		final double[] out = new double[ in.length ]; 
		for( int i = 0; i < in.length; i++ ) 
			out[ i ] = in[ i ]/max; 
		return out; 
	}
	
	/**
	 * Scale double[] between min and max value. 
	 * @param in
	 * @param maxValue
	 */
	public static void scaleBetweenMinAndMax( final double[] in, final double maxValue )
	{
		final double[] minMax = DoubleArrayTools.getMinMax( in ); 
		
		for( int row = 0; row < in.length; row++ )
		{
			in[ row ] = ( in[ row ] - minMax[ 0 ] ) / ( minMax[ 1 ] - minMax[ 0 ] ) * maxValue;  	
		}
	}
	
	/**
	 * Scale matrix between min and max: (xi-min)/(max-min)
	 * @param matrix
	 */
	public static void scaleBetweenMinAndMax( double[][] matrix, final double maxValue )
	{
		final double[] minMax = DoubleArrayTools.getMinMax( matrix );
		
		for( int row = 0; row < matrix.length; row++ )
		{
			for( int col = 0; col < matrix[ 0 ].length; col++ )
			{
				matrix[ row ][ col ] = ( matrix[ row ][ col ] - minMax[ 0 ] ) / ( minMax[ 1 ] - minMax[ 0 ] ) * maxValue;  
			}
		}
	}
	public static void scaleBetweenMinAndMax( double[][] matrix )
	{
		scaleBetweenMinAndMax(matrix, 1); 
	}
	
	/**
	 * Scales each column to sum up to the same number.
	 * NaNs are ignored 
	 * @return
	 */
	public static void scaleColumnsToAddUpTo1( final double[][] matrix, final boolean keepAllZeroColums )
	{
		//Calculate the sum for each column
		final double[] colSums = new double[ matrix[ 0 ].length ];
		final boolean[] colContainsValue = new boolean[ matrix[ 0 ].length ]; 
		double currentValue; 
		for( int row = 0; row < matrix.length; row++ )
		{
			for( int col = 0; col < matrix[ 0 ].length; col++ )
			{
				currentValue = matrix[ row ][ col ]; 
				if( !Double.isNaN( currentValue ) )
					colSums[ col ] += currentValue; 
				if( currentValue != 0 )
					colContainsValue[ col ] = true; 
			}
		} 
		
		//Normalize values in each colum by the sum 
		for( int row = 0; row < matrix.length; row++ )
		{
			for( int col = 0; col < matrix[ 0 ].length; col++ )
			{
				if( keepAllZeroColums )
				{
					if( colContainsValue[ col ] )
					{
						matrix[ row ][ col ] = matrix[ row ][ col ] / colSums[ col ]; 
					}
				}
				else
				{
					matrix[ row ][ col ] = matrix[ row ][ col ] / colSums[ col ];
				}
			}
		}
		 
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		final double[] array1 = { 1.0, 3.0, 5.0 };
		
		final double[][] matrix1 = new double[][]{
				{ 0, 2, 5 },
				{ 0, 10, 2 },
				{ 0, 1, 1 },
				{ 0, Double.NaN, 5 },
		}; 
		
		
		System.out.println( DoubleArrayTools.matrixToString( matrix1 ) ); 
		System.out.println(  );
		
		scaleColumnsToAddUpTo1( matrix1, true );
		
		System.out.println( DoubleArrayTools.matrixToString( matrix1 ) );

	}

}
