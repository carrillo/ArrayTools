package array.tools;

import java.util.ArrayList;

public class ArrayListGenericTools 
{
	/**
	 * Returns ArrayList<T> toString concatenated by a tab. 
	 * @param in
	 * @return
	 */
	public static <T> String toString( final ArrayList<T> in )
	{
		return toString( in, "\t" ); 
	}
	/**
	 * Returns a string with the values of the input ArrayList<T> delimited by a defined String
	 * @param input ArrayList of type T which toString output is concatenated/ 
	 * @param delimiter Deliminator character of output String. 
	 * @return
	 */
	public static <T> String toString( final ArrayList<T> in, final String delimiter )
	{
		String s = ""; 
		for( int i = 0; i < in.size(); i++ )
			s += delimiter + in.get( i ); 
			
		return s.substring( delimiter.length() ); 
	}
}
