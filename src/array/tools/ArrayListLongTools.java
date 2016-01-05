package array.tools;

import java.util.ArrayList;

public class ArrayListLongTools 
{
	/**
	 * Convenient method
	 * Returns a string with the values of the input ArrayList<Integer> delimited by tab
	 * @param input 
	 * @param delimiter 
	 * @return
	 */
	public static String arrayListToString( final ArrayList<Long> input )
	{
		return arrayListToString( input, "\t" );  
	}
	
	/**
	 * Returns a string with the values of the input ArrayList<Integer> delimited by a defined String
	 * @param input 
	 * @param delimiter 
	 * @return
	 */
	public static String arrayListToString( final ArrayList<Long> input, final String delimiter )
	{
		String s = ""; 
		for( int i = 0; i < input.size(); i++ )
			s += delimiter + input.get( i ); 
			
		return s.substring( delimiter.length() ); 
	}
	
	/**
	 * Return the max value stored in the ArrayList. 
	 * @param in
	 * @return
	 */
	public static Long max( final ArrayList<Long> in ) {
		Long max = -Long.MAX_VALUE; 
		for( Long l : in ) 
		{ 
			if( l > max )
			{
				max = l; 
			}
		}
		return max; 
	}
	
	public static void main(String[] args) 
	{
		
	}
}
