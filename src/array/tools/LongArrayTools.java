package array.tools;

import java.util.ArrayList;


public class LongArrayTools 
{
	/**
	 * Convenient method to get a string representation of a 2D matrix, using standard delimiters.  
	 * @param matrix
	 * @return
	 */
	public static String matrixToString( final long[][] matrix )
	{
		return matrixToString( matrix, "\t", "\n" ); 
	}
	
	/**
	 * Get String representation of a 2D matrix. 
	 * @param matrix Input matrix
	 * @param del1 First delimiter (e.g. tab: "\t") 
	 * @param del2 First delimiter (e.g. carriage return: "\n") 
	 * @return 
	 */
	public static String matrixToString( final long[][] matrix, final String del1, final String del2 )
	{
		String s = "";  
		
		for( int j = 0; j < matrix.length; j++ )
		{
			if( j != 0 )
				s += del1; 
			
			for( int i = 0; i < matrix[ j ].length; i++ )
			{
				if( i != 0 )
					s += del1; 
				
				s += matrix[ j ][ i ]; 
			}
		}
		
		return s; 
	}
	
	public static long[] parseIntArray( final int[] in )
	{
		final long[] out = new long[ in.length ]; 
		
		for( int i = 0; i < in.length; i++ )
		{
			out[ i ] = in[ i ]; 
		}
		
		return out; 
	}
	
	public static long[] parseLongArray( final long[] in )
	{
		final long[] out = new long[ in.length ]; 
		
		for( int i = 0; i < in.length; i++ )
		{
			out[ i ] = in[ i ]; 
		}
		
		return out; 
	}
	
	public static long[] parseLongArrayList( final ArrayList<Long> in )
	{
		final long[] out = new long[ in.size() ]; 
		
		for( int i = 0; i < in.size(); i++ )
		{
			out[ i ] = in.get( i ); 
		}
		
		return out; 
	}
	
	/**
	 * Convenient method to get a string representation of an array, using standard delimiter. 
	 * @param matrix
	 * @return
	 */
	public static String arrayToString( final long[] array )
	{
		return arrayToString( array, "\t" ); 
	}
	
	/**
	 * Get String representation of 1D matrix.
	 * @param array  Input array
	 * @param delimiter delimiter to use  
	 * @return
	 */
	public static String arrayToString( final long[] array, final String delimiter )
	{
		String s = "" ; 
		
		for( long i : array )
		{
			s += delimiter + i; 
		}
		
		return s.substring( delimiter.length() ); 
	}
	
	/**
	 * Returns the sum of all elements. 
	 * @param a
	 * @return
	 */
	public static long sum( final long[] a )
	{
		long sum = 0; 
		for( Long l : a )
			sum += l ; 
		return sum; 
	}
	
	/**
	 * Divides each value in long[] by a constant long value. 
	 * @param a
	 * @return
	 */
	public static double[] divide( final long[] a, final long val )
	{
		final double[] out = new double[ a.length ]; 
		for( int i = 0; i < a.length; i++ )
			out[ i ] = (double) a[ i ] / val; 
		return out; 
	}
	
	public static void main(String[] args) 
	{

		final long[] array1 = { 1, 2, 0, 4, 0 }; 
		final long[] array2 = { 3, 5, 0, 1, 0 };
		final long[] array3 = { 7, 5, 4, 1, 6 };
		
		System.out.println( arrayToString( array1 ) ); 
		
		final long sum = sum( array1 ); 
		
		System.out.println( DoubleArrayTools.arrayToString( divide( array1, sum) ) );

		
	}
}
