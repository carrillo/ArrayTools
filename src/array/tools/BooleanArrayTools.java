package array.tools;

public class BooleanArrayTools 
{
	/**
	 * Convenient method to get a string representation of a 2D matrix, using standard delimiters.  
	 * @param matrix
	 * @return
	 */
	public static String matrixToString( final boolean[][] matrix )
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
	public static String matrixToString( final boolean[][] matrix, final String del1, final String del2 )
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
	public static String arrayToString( final boolean[] array )
	{
		return arrayToString( array, "\t" ); 
	}
	
	/**
	 * Get String representation of 1D matrix.
	 * @param array  Input array
	 * @param delimiter delimiter to use  
	 * @return
	 */
	public static String arrayToString( final boolean[] array, final String delimiter )
	{
		String s = "" ; 
		
		for( boolean i : array )
		{
			s += delimiter + i; 
		}
		
		return s.substring( delimiter.length() ); 
	}
}
