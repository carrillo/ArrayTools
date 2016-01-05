package array.tools;

public class FloatArrayTools 
{
	/**
	 * Convenient method to get a string representation of a 2D matrix, using standard delimiters.  
	 * @param matrix
	 * @return
	 */
	public static String matrixToString( final float[][] matrix )
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
	public static String matrixToString( final float[][] matrix, final String del1, final String del2 )
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
}
