package array.tools;

import java.util.ArrayList;

public class ArrayListStringTools 
{
	/**
	 * Convenient method returs String representing an arraylist with the standard delimiter "\t"
	 * @param array
	 * @return
	 */
	public static String arrayListToString( final ArrayList<String> array )
	{
		return arrayListToString( array, "\t" ); 
	}
	
	/**
	 * Returns a string with the values of the input ArrayList<Integer> delimited by a defined String
	 * @param input 
	 * @param delimiter 
	 * @return
	 */
	public static String arrayListToString( final ArrayList<String> input, final String delimiter )
	{
		String s = ""; 
		for( int i = 0; i < input.size(); i++ )			
			s += delimiter + input.get( i ); 
			
		return s.substring( delimiter.length() ); 
	}
	
	/**
	 * Returns String[] 
	 * @param in
	 * @return
	 */
	public static String[] toStringArray( final ArrayList<String> in )
	{
		final String[] out = new String[ in.size() ]; 
		for( int i = 0; i < in.size(); i++ )
			out[ i ] = in.get( i ); 
		return out; 
	}
	
	public static void main(String[] args) 
	{
		ArrayList<String> list = new ArrayList<String>(); 
		list.add( "A" ); 
		list.add( "B" );
		list.add( "C" );
		
		System.out.println( arrayListToString(list, ";") ); 
	}
}
