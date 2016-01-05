package array.tools;

import java.util.ArrayList;

public class IntArrayTools 
{
	/**
	 * Convenient method to get a string representation of a 2D matrix, using standard delimiters.  
	 * @param matrix
	 * @return
	 */
	public static String matrixToString( final int[][] matrix )
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
	public static String matrixToString( final int[][] matrix, final String del1, final String del2 )
	{
		String s = "";  
		
		for( int j = 0; j < matrix.length; j++ )
		{
			if( j != 0 )
				s += del2; 
			
			for( int i = 0; i < matrix[ j ].length; i++ )
			{
				if( i != 0 )
					s += del1; 
				
				s += matrix[ j ][ i ]; 
			}
		}
		
		return s; 
	}
	
	/**
	 * Convenient method to get a string representation of an array, using standard delimiter. 
	 * @param matrix
	 * @return
	 */
	public static String arrayToString( final int[] array )
	{
		return arrayToString( array, "\t" ); 
	}
	
	/**
	 * Get String representation of 1D matrix.
	 * @param array  Input array
	 * @param delimiter delimiter to use  
	 * @return
	 */
	public static String arrayToString( final int[] array, final String delimiter )
	{
		String s = "" ; 
		for( int i : array )
		{
			s += delimiter + i; 
		}
		
		return s.substring( delimiter.length() ); 
	}
	
	/**
	 * Returns true if the array contains only the values specified. 
	 * @param array
	 * @param valueOfInterest
	 * @return
	 */
	public static boolean containsOnlyValue( final int[] array, final int valueOfInterest )
	{
		boolean contains = true; 
		
		for( int i = 0; i < array.length; i++ )
		{
			
			if( !Double.isNaN( valueOfInterest ) && array[ i ] != valueOfInterest )
			{
				contains = false; 
				break; 
			}
			else if( Double.isNaN( valueOfInterest ) && !Double.isNaN( array[ i ] ) )
			{
				contains = false; 
				break; 
			}
		}
		return contains; 
	}
	
	/**
	 * Generate the empirical cumulative distribution. 
	 * 1. Calculate probability vector. 
	 * 2. Add over probabilities. 
	 * @param in
	 * @return
	 */
	public static double[] ecdf( final int[] in )
	{
		final double[] prob = ProbabilityVectorTools.probabilityVector( in ); 
		final double[] out = new double[ prob.length ]; 
		
		out[ 0 ] = prob[ 0 ]; 
		for( int i = 1; i < out.length; i++ )
		{
			out[ i ] = out[ i - 1 ] + prob[ i ];  
		}
		
		return out; 
	}
	
	/**
	 * Returns a matrix of dimension dimX x dimY filled with value fillValue
	 * @param dimX
	 * @param dimY
	 * @param fillValue
	 * @return
	 */
	public static int[][] initiateMatrix( final int dimX, final int dimY, final int fillValue )
	{
		final int[][] out = new int[ dimX ][ dimY ]; 
		if( fillValue != 0 )
		{
			for( int i = 0; i < dimX; i++ )
			{
				for( int j = 0; j < dimY; j++)
				{
					out[ i ][ j ] = fillValue; 
				}
			}
		}
		return out; 
	}
	
	/**
	 * Returns maximum value in array 
	 * @param a
	 * @return
	 */
	public static int max( int[] a ) 
	{
		int max = -Integer.MAX_VALUE; 
		for( int i : a )
		{
			if( i > max )
			{
				max = i; 
			}
		}
		return max; 
	}
	
	/**
	 * Return the average of two arrays having the same length. 
	 * e.g. a1 = 0 2 4 a2 = 6 0 4 --> output 3 1 4
	 * Uneven numbers will be rounded
	 * @param a1
	 * @param a2
	 * @return
	 */
	public static int[] meanArray( final int[] a1, final int[] a2 )
	{
		final int[] mean = new int[ a1.length ]; 
		for( int i = 0; i < a1.length; i++ )
			mean[ i ] = ( a1[ i ] + a2[ i ] )/2;
		
		return mean; 
	}
	
	/**
	 * Returns the mean over all arrays in the arraylist. 
	 * @param in
	 * @return
	 */
	public static int[] meanArray( final ArrayList<int[]> in )
	{
		final int[] mean = new int[ in.get( 0 ).length ]; 
		for( int[] ca : in )
		{
			for( int i = 0; i < mean.length; i++ )
			{
				mean[ i ] += ca[ i ]; 
			}
		}
		
		final double count = in.size();
		Double v = null; 
		for( int i = 0; i < mean.length; i++ )
		{
			v =  mean[ i ] / count;
			mean[ i ] = (int) Math.round( v );  
		}
		
		return mean; 
	}
	
	/**
	 * Returns the mean of all the values in the array.
	 * @param in
	 * @return
	 */
	public static double mean( final int[] in )
	{
		return (double) sum( in ) / in.length;
	}
	
	/**
	 * Multiplies each element in the input array with a given factor 
	 * @param in
	 * @param fact
	 * @return
	 */
	public static double[] multiplyElementwise( final int[] in, final double fact ) {
		final double[] out = new double[ in.length ]; 
		for( int i = 0; i < in.length; i++ ) {
			out[ i ] = (double) in[  i ] * fact; 
		}
		return out; 
	}
	
	/**
	 * Divides each element in the input array with a given factor 
	 * @param in
	 * @param fact
	 * @return
	 */
	public static double[] divideElementwise( final int[] in, final double fact ) {
		final double[] out = new double[ in.length ]; 
		for( int i = 0; i < in.length; i++ ) {
			out[ i ] = (double) in[  i ] / fact; 
		}
		return out; 
	}
	
	/**
	 * Returns minimum value in array 
	 * @param a
	 * @return
	 */
	public static int min( int[] a ) 
	{
		int min = Integer.MAX_VALUE; 
		for( int i : a )
		{
			if( i < min )
			{
				min = i; 
			}
		}
		return min; 
	}
	
	/**
	 * Returns the sum over all entries.
	 * @param in
	 * @return
	 */
	public static int sum( final int[] in )
	{
		int sum = 0; 
		for ( int i : in )
			sum += i; 
		return sum; 
	}
	
	/**
	 * Rotates an array: Adds the last entry to the first position and shifts all other entries by 1. 
	 * A-B-C will become C-A-B
	 * @param in
	 * @return
	 */
	public static int[] rotateArray( final int[] in )
	{
		final int[] out = new int[ in.length ]; 
		//Move last entry to first position 
		out[ 0 ] = in[ in.length - 1 ]; 
		
		for( int i = 0; i < in.length - 1; i++ )
			out[ i + 1 ] = in[ i ]; 
		
		return out; 
	}
	
	/**
	 * Tests if in1 has the same entries as in2
	 * @param in1
	 * @param in2
	 * @return
	 */
	public static boolean isEqual( final int[] in1, final int[] in2 )
	{
		//Test if the length is the same  
		if( in1.length != in2.length )
		{
			return false; 
		}
		else
		{
			//Test if the path is the same
			boolean same = true;
			for( int i = 0; i < in1.length; i++ )
			{
				if( in1[ i ] != in2[ i ] )
				{
					same = false; 
					break; 
				}
			}
			return same; 
		}
	}
	
	
	/**
	 * Test if in1 and in2 are reflected versions of the same int array.  
	 * Returns true if in1 is A-B-C and in2 is C-B-A
	 * @param path
	 * @return
	 */
	public static boolean isReflection( final int[] in1, final int[] in2 )
	{
		return isEqual(reflect( in1 ), in2); 
	}
	
	/**
	 * Reflects input array. A-B-C will be reflected to C-B-A 
	 * @param in
	 * @return
	 */
	public static int[] reflect( final int[] in  )
	{
		final int[] refl = new int[ in.length ]; 
		for( int i = 0; i < in.length; i++ )
		{
			refl[ i ] = in[ in.length - (1 + i) ]; 
		}
		return refl; 
	}
	
	/**
	 * Test if in1 and in2 are rotated versions of the same int array.  
	 * Returns true if in1 is A-B-C and in2 is A-B-C, C-A-B or B-C-A
	 * @param path
	 * @return
	 */
	public static boolean isRotation( final int[] in1, final int[] in2 )
	{
		//Test if the path length is the same and have the same parent 
		final int length1 = in1.length ;  
		if( length1 != in2.length )
		{
			return false; 
		}
		//Test if rotation
		else
		{
			int[] currentOrder = in1.clone();
			boolean isEqual = isEqual( currentOrder, in2 ); 
			if( isEqual )
			{
				return true; 
			}
			else 
			{
				for( int i = 0; i < length1 -1 ; i++ )
				{
					currentOrder = rotateArray( currentOrder );
					isEqual = isEqual( currentOrder, in2 ); 
					
					if( isEqual )
						break; 
				}			 
				return isEqual; 
			}				
		}
	}
	
	/**
	 * Removes positions where both arrays are zero. 
	 * @param a1
	 * @param a2
	 */
	public static ArrayList<int[]> removeZerosInBothArrays( int[] a1, int[] a2 )
	{
		ArrayList<Integer> keepInd = new ArrayList<Integer>(); 
		for( int i = 0; i < a1.length; i++ )
		{
			if( a1[ i ] != 0 || a2[ i ] != 0 )
				keepInd.add( i ); 
		}
		
		final int[] n1 = new int[ keepInd.size() ]; 
		final int[] n2 = new int[ keepInd.size() ];
		
		for( int i = 0; i < keepInd.size(); i++ )
		{
			n1[ i ] = a1[ keepInd.get( i ) ]; 
			n2[ i ] = a2[ keepInd.get( i ) ]; 
		}
		
		ArrayList<int[]> out = new ArrayList<int[]>(); 
		out.add( n1 ); 
		out.add( n2 ); 
		
		return out;  
	}
	
	
	/**
	 * Returns subarray from startIndex to endIndex. Both indices are including
	 * @param in
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public static int[] subarray( final int[] in, final int startIndex, final int endIndex )
	{
		final int[] out = new int[ ( endIndex - startIndex ) + 1 ];  
		for( int i = startIndex; i <= endIndex; i++ )
		{
			out[ i - startIndex ] = in[ i ]; 
		}
		return out; 
	}
	
	/**
	 * Converts to ArrayList. 
	 * @param in
	 * @return
	 */
	public static ArrayList<Integer> toArrayList( final int[] in )
	{
		final ArrayList<Integer> out = new ArrayList<Integer>(); 
		for( int i = 0; i < in.length; i++ )
		{
			out.add( in[ i ] ); 
		}
		return out; 
	}
	
	/**
	 * converts to double array.
	 * @param in
	 * @return
	 */
	public static double[] todoubleArray( final int[] in )
	{
		final double[] out = new double[ in.length ]; 
		for( int i = 0; i < in.length; i++ )
			out[ i ] = (double) in[ i ]; 
		return out; 
	}
	
	/**
	 * converts to Double array.
	 * @param in
	 * @return
	 */
	public static Double[] toDoubleArray( final int[] in )
	{
		final Double[] out = new Double[ in.length ]; 
		for( int i = 0; i < in.length; i++ )
			out[ i ] = (double) in[ i ]; 
		return out; 
	}
	
	public static void main(String[] args) 
	{

		final int[] array1 = { 1, 2, 0, 4, 0 }; 
		final int[] array2 = { 3, 5, 0, 1, 0 };
		final int[] array3 = { 7, 5, 4, 1, 6 };
		
		ArrayList<int[]> list = new ArrayList<int[]>(); 
		list.add( array1 ); 
		list.add( array2 ); 
		list.add( array3 ); 
		
		
		System.out.println( ArrayListGenericTools.toString( toArrayList( array1), ";") ); 
		 

		
	}
}
