package array.tools;

import java.util.ArrayList;

public class ArrayListIntegerTools 
{
	/*
	 * Tests if arraylist contains only positive values. 
	 */
	public static boolean allPositiveOrZero( final ArrayList<Integer> in )
	{
		boolean allPositive = true; 
		for( Integer i : in )
		{
			if( i < 0 )
			{
				allPositive = false; 
				break; 
			}
		}
		return allPositive; 
	}
	
	/*
	 * Returns a string with the values of the input ArrayList<Integer> delimited by a defined String
	 * @param input 
	 * @param delimiter 
	 * @return
	 */
	public static String arrayListToString( final ArrayList<Integer> input, final String delimiter )
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
	public static String arrayListToString( final ArrayList<Integer> input )
	{
		return arrayListToString(input, "\t"); 
	}
	
	/**
	 * Returns true if the arraylist contains the values specified. 
	 * @param array
	 * @param valueOfInterest
	 * @return
	 */
	public static boolean containsValue( final ArrayList<Integer> array, final double valueOfInterest )
	{
		boolean contains = false; 
		
		for( Integer i : array )
		{
			if( i == valueOfInterest )
			{
				contains = true; 
				break; 
			}
		}
		
		return contains; 
	}
	/**
	 * Tests if in1 has the same entries as in2
	 * @param in1
	 * @param in2
	 * @return
	 */
	public static boolean isEqual( final ArrayList<Integer> in1, final ArrayList<Integer> in2 )
	{
		//Test if the length is the same  
		if( in1.size() != in2.size())
		{
			return false; 
		}
		else
		{
			//Test if the path is the same
			boolean same = true;
			for( int i = 0; i < in1.size(); i++ )
			{
				if( in1.get( i ) != in2.get( i ) )
				{
					same = false; 
					break; 
				}
			}
			return same; 
		}
	}
	
	/**
	 * Returns the maximum value hold in the arraylist
	 * @param in
	 * @return
	 */
	public static int max( final ArrayList<Integer> in ) {
		int max = -Integer.MAX_VALUE; 
		for( Integer i : in ) {
			if( i > max )
				max = i; 
		}
		
		return max; 
	}
	
	public static double[] mean( ArrayList<ArrayList<Integer>> inList ) 
	{
		final double[] out = new double[ inList.get( 0 ).size() ]; 
		
		//Sum up all values. 
		for( ArrayList<Integer > in : inList ) 
		{
			for( int i = 0; i < in.size(); i++ ) 
			{
				out[ i ] += (double) in.get( i ); 
			}
		}
		
		//Divide by the number of elements.
		
		for( int i = 0; i < out.length; i++ ) {
			out[ i ] = out[ i ] / inList.size(); 
		}
		
		return out; 
	}
	
	/**
	 * Sums up all the values in the ArrayList
	 * @param in
	 * @return
	 */
	public static int sum( final ArrayList<Integer> in )
	{
		int out = 0; 
		for( Integer i : in )
			out += i; 
		return out; 
	}
	/**
	 * Returns a int[] from an ArrayList<Integer> 
	 * @param a
	 * @return
	 */
	public static int[] toArray( final ArrayList<Integer> a )
	{
		final int[] out = new int[ a.size() ]; 
		for( int i = 0; i < a.size(); i++ )
			out[ i ] = a.get( i ); 
		return out; 
	}
	
	/**
	 * Returns a double[] from an ArrayList<Integer> 
	 * @param a
	 * @return
	 */
	public static double[] toDoubleArray( final ArrayList<Integer> a )
	{
		final double[] out = new double[ a.size() ]; 
		for( int i = 0; i < a.size(); i++ )
			out[ i ] = (double) a.get( i ); 
		return out; 
	}
	
	public static void main(String[] args) 
	{
		final ArrayList<Integer> list1 = new ArrayList<Integer>(); 
		list1.add( 3 ); 
		list1.add( 2 );
		list1.add( 20 );
		
		final ArrayList<Integer> list2 = new ArrayList<Integer>(); 
		list2.add( 4 ); 
		list2.add( 4 );
		list2.add( 22 );
		
		final ArrayList<ArrayList<Integer>> inList = new ArrayList<ArrayList<Integer>>(); 
		inList.add( list1 ); 
		inList.add( list2 ); 
		
		System.out.println( DoubleArrayTools.arrayToString( mean( inList ) ) );  
	}
}
