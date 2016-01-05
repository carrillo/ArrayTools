package array.tools;

import inputOutput.TextFileAccess;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StringArrayTools 
{
	/**
	 * Convenient method to get a string representation of an array, using standard delimiter. 
	 * @param matrix
	 * @return
	 */
	public static String arrayToString( final String[] array )
	{
		return arrayToString( array, "\t" ); 
	}
	
	/**
	 * Get String representation of 1D matrix.
	 * @param array  Input array
	 * @param delimiter delimiter to use  
	 * @return
	 */
	public static String arrayToString( final String[] array, final String delimiter )
	{
		String s = "" ; 
		
		for( String string : array )
		{
			s += delimiter + string; 
		}
		
		return s.substring( delimiter.length() ); 
	}
	
	/**
	 * Returns a ArrayList string version of the String[] 
	 * @param in
	 * @return
	 */
	public static ArrayList<String> stringArrayToArrayList( final String[] in )
	{
		ArrayList<String> out = new ArrayList<String>(); 
		for( String s : in )
			out.add( s ); 
		
		return out;
	}
	
	/**
	 * Checks if array 1 and 2 are permutated (i.e. have same entries in different order). 
	 * Order is not important. 
	 * @param a1
	 * @param a2
	 * @return
	 */
	public static boolean arePermutated( final String[] a1, final String[] a2 )
	{
		boolean out = true; 
		if( a1.length == a2.length )
		{
			List<String> al1 = Arrays.asList( a1.clone() );
			Collections.sort( al1 ); 
			List<String> al2 = Arrays.asList( a2.clone() );
			Collections.sort( al2 );
			
			for( int i = 0; i < al1.size(); i++ )
			{
				if( !al1.get( i ).equals( al2.get( i ) ) )
				{
					out = false; 
					break; 
				}
			}
		}
		else 
		{
			out = false; 			
		}
		
		return out; 
	}
	
	/**
	 * Convenient method to get a string representation of a 2D matrix, using standard delimiters. 
	 * @param matrix
	 * @return
	 */
	public static String matrixToString( final String[][] matrix )
	{
		return matrixToString(matrix, "\t", "\n" ); 
	}
	
	/**
	 * Get String representation of a 2D matrix. 
	 * @param matrix Input matrix
	 * @param del1 First delimiter (e.g. tab: "\t") 
	 * @param del2 First delimiter (e.g. carriage return: "\n") 
	 * @return 
	 */
	public static String matrixToString( final String[][] matrix, final String del1, final String del2 )
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
	 * Writes array to file specified
	 * @param array
	 * @param file
	 */
	public static void writeToFile( final String[][] matrix, final File file )
	{
		PrintWriter out = TextFileAccess.openFileWrite( file ); 
		
		for( int i = 0; i < matrix.length; i++ )
			out.println( arrayToString( matrix[ i ] ) );
		
		out.flush(); 
		out.close(); 
	}
	
	
	public static void main(String[] args) 
	{
		final String[] a1 = { "AA", "BA", "CA", "DA" }; 
		final String[] a2 = { "DA", "CA", "BA", "AA" };
		
		System.out.println( arePermutated( a1 , a2 ) );  
	}
}
