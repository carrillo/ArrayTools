package array.normalization;

import array.tools.DoubleArrayTools;
import array.tools.IntArrayTools;

public class IntArrayNormalization 
{
	/**
	 * Make values in int[] relative to total sum. 
	 * @param in
	 * @return
	 */
	public static double[] makeRelativeTo1( final int[] in )
	{
		final double sum = IntArrayTools.sum( in ); 
		final double[] out = new double[ in.length ]; 
		for( int i = 0; i < in.length; i++ )
			out[ i ] = (double) in[ i ] / sum; 
		return out; 
	}
	
	/**
	 * Makes values in int[] relative to the maximum value. 
	 * @param in
	 * @return
	 */
	public static double[] makeRelativeToMax( final int[] in ) 
	{
		final double max = IntArrayTools.max( in ); 
		final double[] out = new double[ in.length ]; 
		for( int i = 0; i < in.length; i++ ) 
			out[ i ] = (double) in[ i ]/max; 
		return out; 
	}
	
	public static void main(String[] args) 
	{
		final int[] a = new int[]{ 1,3,10,1 }; 
		System.out.println( DoubleArrayTools.arrayToString( makeRelativeToMax( a ), "\t", 4  ) ); 
	}
}
