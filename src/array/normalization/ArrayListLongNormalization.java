package array.normalization;

import java.util.ArrayList;

import array.tools.ArrayListLongTools;

public class ArrayListLongNormalization 
{
	/**
	 * Makes values in double[] relative to the maximum value. 
	 * @param in
	 * @return
	 */
	public static double[] makeRelativeToMax( final ArrayList<Long> in ) 
	{
		final Long max = ArrayListLongTools.max( in ); 
		final double[] out = new double[ in.size() ]; 
		for( int i = 0; i < in.size(); i++ ) 
			out[ i ] = (double) in.get( i ) / (double) max; 
		return out; 
	}
}
