package array.tools;

import ij.ImageJ;
import inputOutput.TextFileAccess;

import java.io.File;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import Jama.Matrix;
import array.visualization.HeatMap;


public class DoubleArrayTools 
{
	/**
	 * Entrywise sum of two 2d matrices. 
	 * @param in1
	 * @param in2
	 * @param replaceNaNsByZeros Treat NaNs as zeros?
	 * @return
	 */
	public static double[][] addMatrices( final double[][] in1, final double[][] in2, final boolean replaceNaNsByZeros )
	{
		double[][] a = clone2DArray( in1 ); 
		double[][] b = clone2DArray( in2 ); 
		if( replaceNaNsByZeros )
		{
			substitute(a, Double.NaN, 0.0); 
			substitute(b, Double.NaN, 0.0); 
		}
		
		for( int j = 0; j < a.length; j++ )
		{
			for( int i = 0; i < a[ j ].length; i++ )
			{	
				a[ j ][ i ] += b[ j ][ i ];  				
			}
		}
		
		return a; 
	}

	/**
	 * This method adds the values in the matrix and a constant value. 
	 * Original values in matrix in1 are overwritten. 
	 */
	public static void addMatrixAndConstantValue( double[][] in1, final double constantValue )
	{
		for( int j = 0; j < in1.length; j++ )
		{
			for( int i = 0; i < in1[ j ].length; i++ )
			{	
				in1[ j ][ i ] = in1[ j ][ i ] + constantValue;  				
			}
		}
	}

	/**
	 * Sums up values for each 2D matrix position of an 2D matrix arrayList.    
	 * @param matrixList
	 * @param replaceNaNsByZeros Treat NaNs as zeros? 
	 * @return
	 */
	public static double[][] addUpMatrixList( final ArrayList<double[][]> matrixList, final boolean replaceNaNsByZeros )
	{
		double[][] a = matrixList.get( 0 ).clone();
		
		for( int i = 1; i < matrixList.size(); i++ )
		{
			a = addMatrices(a, matrixList.get( i ), replaceNaNsByZeros );			
		}
		
		return a; 
	}

	/**
	 * Convenient method to get a string representation of an array, using standard delimiter. 
	 * @param matrix
	 * @return
	 */
	public static String arrayToString( final double[] array )
	{
		return arrayToString( array, "\t"); 
	}

	/**
	 * Get String representation of 1D matrix.
	 * @param array  Input array
	 * @param delimiter delimiter to use  
	 * @return
	 */
	public static String arrayToString( final double[] array, final String delimiter )
	{
		String s = "";  
		
		for( int i = 0; i < array.length; i++ )
		{
			if( i != 0 )
				s += delimiter; 
				
			s += array[ i ]; 
		}
		
		return s; 
	}

	/**
	 * Convenient method to get a string representation of an array, using standard delimiter. 
	 * @param matrix
	 * @return
	 */
	public static String arrayToString( final Double[] array )
	{
		return arrayToString( array, "\t"); 
	}

	/**
	 * Get String representation of 1D matrix.
	 * @param array  Input array
	 * @param delimiter delimiter to use  
	 * @return
	 */
	public static String arrayToString( final Double[] array, final String delimiter )
	{
		String s = "";  
		
		for( int i = 0; i < array.length; i++ )
		{
			if( i != 0 )
				s += delimiter; 
				
			s += array[ i ]; 
		}
		
		return s; 
	}

	/**
	 * Get String representation of 1D matrix.
	 * @param array  Input array
	 * @param delimiter delimiter to use  
	 * @return
	 */
	public static String arrayToString( final double[] array, final String delimiter, final int decimalplaces )
	{
		String decPlaces = "#."; 
		for( int i = 0; i < decimalplaces; i++ )
			decPlaces += "#"; 
		DecimalFormat df = new DecimalFormat( decPlaces ); 
				
				
		String s = "";  
		for( int i = 0; i < array.length; i++ )
		{
			if( i != 0 )
				s += delimiter; 
				
			s += df.format( array[ i ] ); 
		}
		
		return s; 
	}

	/**Creates an independent copy(clone) of the boolean array.
	 * @param array The array to be cloned.
	 * @return An independent 'deep' structure clone of the array.
	 */
	public static double[][] clone2DArray(double[][] array) {
	    int rows=array.length ;
	    //int rowIs=array[0].length ;
	
	    //clone the 'shallow' structure of array
	    double[][] newArray =(double[][]) array.clone();
	    //clone the 'deep' structure of array
	    for(int row=0;row<rows;row++){
	        newArray[row]=(double[]) array[row].clone();
	    }
	
	    return newArray;
	}
	
	/**
	 * Generate a sequence array from start to end increasing by the value defined by step. 
	 * @param start
	 * @param end
	 * @param step
	 * @return
	 */
	public static double[] createSequence( final double start, final double end, final double step )
	{
		final int length = (int) Math.floor( (  end - start ) / step ) + 1 ;
		final double[] out = new double[ length ]; 
		
		for( int i = 0; i < length; i++ )
		{
			out[ i ] = start + i*step; 
		}
		
		return out; 
	}
	
	
	/**
	 * Paste in2 after in1
	 * @param in1
	 * @param in2
	 * @return
	 */
	public static double[] paste( final double[] in1, final double[] in2 ) {
		final double[] out = new double[ ( in1.length + in2.length ) ]; 
		
		int i = 0; 
		for( double d : in1 ){
			out[ i ] = d; 
			i++; 
		}
		for( double d : in2 ) {
			out[ i ] = d; 
			i++; 
		}
		
		return out; 
	}
	
	/**
	 * Sum over all columns in matrix 
	 * @param matrix
	 * @return
	 */
	public static double[] sumOverColumns( final double[][] matrix ) 
	{
		final double[] sum = new double[ matrix.length ];
		
		for( int m = 0; m < matrix.length; m++ )
		{			
			for( int n = 0; n < matrix[ 0 ].length; n++ ) 
			{
				sum[ m ] += matrix[ m ][ n ]; 
			}
		}
		
		return sum; 
	}

	/**
	 * Returns true if the array contains the values specified. 
	 * @param array
	 * @param valueOfInterest
	 * @return
	 */
	public static boolean containsValue( final double[] array, final double valueOfInterest )
	{
		boolean contains = false; 
		
		for( Double d : array )
		{
			if( d == valueOfInterest )
			{
				contains = true; 
				break; 
			}
		}
		
		return contains; 
	}

	/**
	 * Returns true if the array contains the values specified. 
	 * @param array
	 * @param valueOfInterest
	 * @return
	 */
	public static boolean containsValue( final double[][] array, final double valueOfInterest )
	{
		boolean contains = false; 
		
		
		for( int i = 0; i < array.length; i++ )
		{
			for( int j = 0; j < array[ 0 ].length; j++ )
			{
				if( array[ i ][ j ] == valueOfInterest )
				{
					contains = true; 
					break; 
				}
				else if( Double.isNaN( valueOfInterest ) && Double.isNaN( array[ i ][ j ] ) )
				{
					contains = true; 
					break; 
				}
			}
		}
		return contains; 
	}

	/**
	 * Returns true if the array contains only the values specified. 
	 * @param matrix
	 * @param valueOfInterest
	 * @return
	 */
	public static boolean containsOnlyValue( final double[][] matrix, final double valueOfInterest )
	{
		boolean contains = true; 
		
		for( int i = 0; i < matrix.length; i++ )
		{
			for( int j = 0; j < matrix[ 0 ].length; j++ )
			{
				if( !Double.isNaN( valueOfInterest ) && matrix[ i ][ j ] != valueOfInterest )
				{
					contains = false; 
					break; 
				}
				else if( Double.isNaN( valueOfInterest ) && !Double.isNaN( matrix[ i ][ j ] ) )
				{
					contains = false; 
					break; 
				}
			}
		}
		return contains; 
	}

	/**
	 * Returns true if the array contains only the values specified. 
	 * @param array
	 * @param valueOfInterest
	 * @return
	 */
	public static boolean containsOnlyValue( final double[] array, final double valueOfInterest )
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
	 * Displays the values in a matrix using Fiji. 
	 * @param in
	 */
	public static void display2dMatrix( final double[][] in, final String title )
	{
		new ImageJ(); 
		Matrix m = new Matrix(  in ); 
		m = m.transpose(); 
		HeatMap hm = new HeatMap( m.getArray() ); 
		hm.showImage( title );
	}

	/**
	 * This method divides the values in the first matrix by values in the second matrix. 
	 * Original values in matrix in1 are overwritten. 
	 */
	public static void divideMatrices( double[][] in1, final double[][] in2 )
	{
		for( int j = 0; j < in1.length; j++ )
		{
			for( int i = 0; i < in1[ j ].length; i++ )
			{	
				in1[ j ][ i ] = in1[ j ][ i ] / in2[ j ][ i ];  				
			}
		}
	}

	/**
	 * Calculates the dot product between two arrays.
	 * a*b = Sum(ai*bi);  
	 * @param in1
	 * @param in2
	 * @return
	 */
	public static Double dotProduct( final double[] in1, final double[] in2 )
	{
		Double sum; 
		if( in1.length != in2.length )
			sum = null; 
		else
		{
			sum = 0.0;
			for( int i = 0; i < in1.length; i++ )
				sum += in1[ i ] * in2[ i ]; 
		}
		return sum; 	
	}

	public static double[] elementwiseAddition( final double[] v1, final double[] v2 )
	{
		final double[] out = new double[ v1.length ]; 
		for( int i = 0; i < v1.length; i++ )
			out[ i ] = v1[ i ] + v2[ i ]; 
		return out; 
	}
	
	/**
	 * This method dividedds the values in the first array by values in the second array. 
	 * Original values in matrix in1 are overwritten. 
	 */
	public static void elementwiseDivision( final double[] in1, final double[] in2 ) {
		for( int i = 0; i < in1.length; i++ ) {
			in1[ i ] = in1[ i ] / in2[ i ]; 
		}
	}


	/**
	 * This method multiplies the values in the first matrix by values in the second matrix. 
	 * Original values in matrix in1 are overwritten. 
	 */
	public static void elementwiseMultiplication( double[][] in1, final double[][] in2 )
	{
		for( int j = 0; j < in1.length; j++ )
		{
			for( int i = 0; i < in1[ j ].length; i++ )
			{	
				in1[ j ][ i ] = in1[ j ][ i ] * in2[ j ][ i ];  				
			}
		}
	}

	/**
	 * This method multiplies the values in the array by a constant value. 
	 * Original values in matrix in1 are overwritten. By default NAs are kept. 
	 */
	public static void elementwiseMultiplication( double[] in, final double constantValue )
	{
		for( int j = 0; j < in.length; j++ )
		{
			in[ j ] = in[ j ] * constantValue;  									
		}
	}
	
	/**
	 * This method multiplies the values in the array by a constant value. 
	 * Original values in matrix in1 are overwritten. By default NAs are kept. 
	 */
	public static void elementwiseMultiplication( Double[] in, final double constantValue )
	{
		for( int j = 0; j < in.length; j++ )
		{
			in[ j ] = in[ j ] * constantValue;  									
		}
	}

	/**
	 * This method multiplies the values in the matrix by a constant value. 
	 * Original values in matrix in1 are overwritten. By default NAs are kept. 
	 */
	public static void elementwiseMultiplication( double[][] in1, final double constantValue )
	{
		for( int j = 0; j < in1.length; j++ )
		{
			for( int i = 0; i < in1[ j ].length; i++ )
			{
				in1[ j ][ i ] = in1[ j ][ i ] * constantValue;  									
			}
		}
	}
	
	/**
	 * Returns double[] containing only values specified by excluding boundaires min and max. 
	 * @param in
	 * @param min
	 * @param max
	 * @return
	 */
	public static double[] filterByValue( final double[] in, final double min, final double max ) 
	{
		final ArrayList<Double> filteredValues = new ArrayList<Double>(); 
		for( int i = 0; i < in.length; i++ )
		{
			if( in[ i ] > min && in[ i ] < max )
			{
				filteredValues.add( in[ i ] ); 
			}
		}
		
		return ArrayListDoubleTools.toArray( filteredValues ); 
	}

	/**
	 * Returns the specified column of the matrix. 
	 * @param matrix
	 * @param colIndex
	 * @return
	 */
	public static double[] getColumn( final double[][] matrix, final int colIndex )
	{
		final double[] column = new double[ matrix.length ]; 
		for( int i = 0; i < matrix.length; i++ )
			column[ i ] = matrix[ i ][ colIndex ]; 
		return column; 
	}

	/**
	 * Calculates the eucledian distance between a1 and a2. 
	 * d = SQRT( SUM( (a1i-a2i)^2 ) ) 
	 * @param a1
	 * @param a2
	 * @return
	 */
	public static double getEucledianDistance( final double[] a1, final double[] a2 )
	{
		double sum = 0; 
		for( int i = 0; i < a1.length; i++ )
		{
			sum += Math.pow( ( a1[i]-a2[i] ), 2 ); 
		}
		return Math.sqrt( sum ); 
	}

	/**
	 * Returns the index of the maximum value contained in the array. 
	 * @param array
	 * @return
	 */
	public static int getIndexOfMaximum( final double[] array )
	{
		double max = -Double.MAX_VALUE; 
		int index = -1; 
		for( int i = 0; i < array.length; i++ )
		{
			if( array[ i ] > max )
			{
				max = array[ i ]; 
				index = i; 
			}
		}
		return index; 
	}
	
	/**
	 * Returns the index of the maximum value contained in the array. 
	 * @param array
	 * @return
	 */
	public static int getIndexOfMaximum( final Double[] array )
	{
		double max = -Double.MAX_VALUE; 
		int index = -1; 
		for( int i = 0; i < array.length; i++ )
		{
			if( array[ i ] > max )
			{
				max = array[ i ]; 
				index = i; 
			}
		}
		return index; 
	}

	/**
	 * Returns the index of the minimum value contained in the matrix. NaN are ignored. 
	 * @param array
	 * @return
	 */
	public static int[] getIndexOfMinimum( final double[][] matrix )
	{
		double min = Double.MAX_VALUE; 
		int[] index = new int[] {-1,-1} ; 
		
		for( int i = 0; i < matrix.length; i++ )
		{
			for( int j = 0; j < matrix[ 0 ].length; j++ )
			{
				if( Double.isNaN( matrix[ i ][ j ] ) ) 
					continue; 
				
				if( matrix[ i ][ j ] < min )
				{
					min = matrix[ i ][ j ]; 
					index[ 0 ] = i; 
					index[ 1 ] = j; 
				}
			}
		}
		
		return index; 
	}

	/**
	 * Returns the index of the maximum value contained in the matrix. NaN are ignored. 
	 * @param array
	 * @return
	 */
	public static int[] getIndexOfMaximum( final double[][] matrix )
	{
		double max = -Double.MAX_VALUE; 
		int[] index = new int[] {-1,-1} ; 
		
		for( int i = 0; i < matrix.length; i++ )
		{
			for( int j = 0; j < matrix[ 0 ].length; j++ )
			{
				if( Double.isNaN( matrix[ i ][ j ] ) ) 
					continue; 
				
				if( matrix[ i ][ j ] > max )
				{
					max = matrix[ i ][ j ]; 
					index[ 0 ] = i; 
					index[ 1 ] = j; 
				}
			}
		}	
		return index; 
	}

	/**
	 * Returns the indices of the sorted list. Specify if entries should be sorted increasing or decreasing
	 * @param arrayToSort
	 * @return
	 */
	public static int[] getIndicesOfSortedList( final double[] arrayToSort, final boolean increasing )
	{
		//Get sorted index of eigenValues
		TreeMap<Double, List<Integer>> map = new TreeMap<Double, List<Integer>>();
		for(int i = 0; i < arrayToSort.length; i++) 
		{
			//Get array with index
		    List<Integer> ind = map.get( arrayToSort[i] );
		    if(ind == null){
		        ind = new ArrayList<Integer>();
		        map.put(arrayToSort[i], ind);
		    }
		    ind.add(i);
		}
	
	
		// Now flatten the list
		List<Integer> indices = new ArrayList<Integer>();
		for(List<Integer> arr : map.values()) 
		{
		    indices.addAll(arr);
		}
		
		final int[] out = new int[ indices.size() ];
	
		for( int i = 0; i < indices.size(); i++  )
		{
			if( increasing )
				out[ i ] = indices.get( i );
			else 
				out[ i ] = indices.get( indices.size() - ( i + 1 ) ); 
		}
		
		return out; 
				
	}

	/**
	 * Returns the maximum value contained in the matrix. NaN are ignored. 
	 * @param array
	 * @return
	 */
	public static double getMaximum( final double[][] matrix )
	{
		double max = -Double.MAX_VALUE;  
		
		for( int i = 0; i < matrix.length; i++ )
		{
			for( int j = 0; j < matrix[ 0 ].length; j++ )
			{
				if( Double.isNaN( matrix[ i ][ j ] ) ) 
					continue; 
				
				if( matrix[ i ][ j ] > max )
				{
					max = matrix[ i ][ j ];  
				}
			}
		}
		
		return max; 
	}

	/**
	 * Returns the maximum value of the array
	 * @param array
	 * @return
	 */
	public static double getMaximum( final double[] array )
	{
		double max = -Double.MAX_VALUE;  
		for( int i = 0; i < array.length; i++ )
		{
			if( array[ i ] > max )
				max = array[ i ]; 
			
		}
		return max;
	}

	/**
	 * Returns the maximum value of the array
	 * @param array
	 * @return
	 */
	public static double getMinimum( final double[] array )
	{
		double min = Double.MAX_VALUE;  
		for( int i = 0; i < array.length; i++ )
		{
			if( array[ i ] < min )
				min = array[ i ]; 
			
		}
		return min;
	}
	
	/**
	 * Returns the maximum value of the array
	 * @param array
	 * @return
	 */
	public static double getMaximum( final Double[] array )
	{
		double max = -Double.MAX_VALUE;  
		for( int i = 0; i < array.length; i++ )
		{
			if( array[ i ] > max )
				max = array[ i ]; 
			
		}
		return max;
	}

	/**
	 * Returns an array holding min, max value of matrix
	 * @param matrix
	 * @return
	 */
	public static double[] getMinMax( double[][] matrix )
	{
		final double[] minMax = new double[]{ Double.MAX_VALUE, -Double.MAX_VALUE  };
		double v; 
		for( int row = 0; row < matrix.length; row++ )
		{
			for( int col = 0; col < matrix[ 0 ].length; col++ )
			{
				v = matrix[ row ][ col ]; 
				if( v < minMax[ 0 ] )
					minMax[ 0 ] = v; 
				if( v > minMax[ 1 ] )
					minMax[ 1 ] = v; 
			}
		}
		
		return minMax; 
	}

	/**
	 * Returns an array holding min, max value of matrix
	 * @param matrix
	 * @return
	 */
	public static double[] getMinMax( double[] array )
	{
		final double[] minMax = new double[]{ Double.MAX_VALUE, -Double.MAX_VALUE  };
		double v; 
		for( int row = 0; row < array.length; row++ )
		{
			
			v = array[ row ]; 
			if( v < minMax[ 0 ] )
				minMax[ 0 ] = v; 
			if( v > minMax[ 1 ] )
				minMax[ 1 ] = v;
		}
		
		return minMax; 
	}

	/**
	 * Returns a matrix of dimension dimX x dimY filled with value fillValue
	 * @param dimX
	 * @param dimY
	 * @param fillValue
	 * @return
	 */
	public static double[][] initiateMatrix( final int dimX, final int dimY, final double fillValue )
	{
		final double[][] out = new double[ dimX ][ dimY ]; 
		if( fillValue != 0.0 )
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
	 * Tests if in1 has the same entries as in2
	 * @param in1
	 * @param in2
	 * @return
	 */
	public static boolean isEqual( final double[] in1, final double[] in2 )
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
	 * Test if in1 and in2 are rotated versions of the same int array.  
	 * Returns true if in1 is A-B-C and in2 is A-B-C, C-A-B or B-C-A
	 * @param path
	 * @return
	 */
	public static boolean isRotation( final double[] in1, final double[] in2 )
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
			double[] currentOrder = in1.clone();
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
	 * Test if in1 and in2 are reflected versions of the same int array.  
	 * Returns true if in1 is A-B-C and in2 is C-B-A
	 * @param path
	 * @return
	 */
	public static boolean isReflection( final double[] in1, final double[] in2 )
	{
		return isEqual(reflect( in1 ), in2); 
	}

	/**
	 * Convenient method to get a string representation of a 2D matrix, using standard delimiters. 
	 * @param matrix
	 * @return
	 */
	public static String matrixToString( final double[][] matrix )
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
	public static String matrixToString( final double[][] matrix, final String del1, final String del2 )
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
	 * Returns mean value of matrix. NaN are ignored. 
	 * @param in
	 * @return
	 */
	public static double mean( final double[][] matrix )
	{
		double v; 
		double sum = 0.0;  
		int count = 0; 
		for( int j = 0; j < matrix.length; j++ )
		{
			for( int i = 0; i < matrix[ j ].length; i++ )
			{	
				v = matrix[ j ][ i ];
				if( !Double.isNaN( v ) )
				{
					sum += v; 
					count++; 
				}
			}
		}
		return sum/count; 
	}

	/**
	 * Returns mean value of array. NaN are ignored. 
	 * @param in
	 * @return
	 */
	public static double mean( final double[] in )
	{
		double v; 
		double sum = 0.0;  
		int count = 0; 
		for( int j = 0; j < in.length; j++ )
		{				
			v = in[ j ];
			if( !Double.isNaN( v ) )
			{
				sum += v; 
				count++; 
			}
			
		}
		return sum/count; 
	}

	/**
	 * Returns mean value of array. NaN are ignored. 
	 * @param in
	 * @return
	 */
	public static double[] meanArray( final double[] in1, final double[] in2 )
	{ 
		double[] out = null; 
		if( in1.length == in1.length )
		{
			out = new double[ in1.length ]; 
			for( int i = 0; i < in1.length; i++ )
			{
				out[ i ] = ( in1[ i ] + in2[ i ] )/2; 
			}
		}
		return out; 
	}

	/**
	 * Normalizes values in array by its mean value. 
	 * Original values are overwritten
	 * @param in
	 */
	public static void normalizeByMean( double[] in )
	{
		final double f = ( 1/mean( in ) );
		System.out.println( f ); 
		elementwiseMultiplication( in, f ); 
	}
	
	/**
	 * Reflects input array. A-B-C will be reflected to C-B-A 
	 * @param in
	 * @return
	 */
	public static double[] reflect( final double[] in  )
	{
		final double[] refl = new double[ in.length ]; 
		for( int i = 0; i < in.length; i++ )
		{
			refl[ i ] = in[ in.length - (1 + i) ]; 
		}
		return refl; 
	}

	/**
	 * Rotates an array: Adds the last entry to the first position and shifts all other entries by 1. 
	 * A-B-C will become C-A-B
	 * @param in
	 * @return
	 */
	public static double[] rotateArray( final double[] in )
	{
		final double[] out = new double[ in.length ]; 
		//Move last entry to first position 
		out[ 0 ] = in[ in.length - 1 ]; 
		
		for( int i = 0; i < in.length - 1; i++ )
			out[ i + 1 ] = in[ i ]; 
		
		return out; 
	}
	
	
	/**
	 * Save 2d matrix as tiff 
	 * @param in
	 */
	public static void save2dMatrixAsTif( final double[][] in, final String fileName )
	{
		new ImageJ(); 
		Matrix m = new Matrix(  in ); 
		m = m.transpose(); 
		HeatMap hm = new HeatMap( m.getArray() ); 
		hm.saveImage( fileName );
	}
	
	/**
	 * Sort double array 
	 * @param in
	 * @return
	 */
	public static double[] sort( final double[] in )
	{
		ArrayList<Double> array = DoubleArrayTools.toArrayList( in ); 
		Collections.sort( array );
		return ArrayListDoubleTools.toArray( array ); 
	}
	
	/**
	 * This method substitutes values in matrix by a defined double value. 
	 * @param matrix Matrix to modify 
	 * @param valueToSubstitute Value to look for
	 * @param newValue Target value
	 */
	public static void substitute( final double[][] matrix, final double valueToSubstitute, final double newValue )
	{
		
		for( int j = 0; j < matrix.length; j++ )
			{
				for( int i = 0; i < matrix[ j ].length; i++ )
				{
					if( Double.isNaN( valueToSubstitute ) )
					{
						if( Double.isNaN( matrix[ j ][ i ] ) )
							matrix[ j ][ i ] = newValue; 
					}
					else
					{
						if( matrix[ j ][ i ] == valueToSubstitute )
							matrix[ j ][ i ] = newValue; 
					}
				}
			}			 
	}

	/**
	 * Sums all matrix entries. 
	 * @param matrix
	 * @return
	 */
	public static double sumOfMatrixEntries( final double[][] matrix )
	{
		double sum = 0.0;  
		for( int j = 0; j < matrix.length; j++ )
		{
			for( int i = 0; i < matrix[ j ].length; i++ )
			{	
				sum += matrix[ j ][ i ];			
			}
		}
		
		return sum; 
	}

	/**
	 * Sums all array entries. 
	 * @param matrix
	 * @return
	 */
	public static double sumOfArrayEntries( final double[] array )
	{
		double sum = 0.0;  
		for( int j = 0; j < array.length; j++ )
			sum += array[ j ];			
		
		return sum; 
	}

	/**
	 * Converts double[][] to ArrayList<double[]>. 
	 * @param in
	 * @return
	 */
	public static ArrayList<double[]> toArrayList( final double[][] in )
	{
		ArrayList<double[]> out = new ArrayList<double[]>(); 
		for( int i = 0; i < in.length; i++ )
			out.add( in[ i ] ); 
		return out; 
	}
	
	/**
	 * Converts double[] to ArrayList<Double>. 
	 * @param in
	 * @return
	 */
	public static ArrayList<Double> toArrayList( final double[] in )
	{
		ArrayList<Double> out = new ArrayList<Double>(); 
		for( int i = 0; i < in.length; i++ )
			out.add( in[ i ] ); 
		return out; 
	}
	
	/**
	 * Removes zeros from vector 
	 * @param in
	 * @return
	 */
	public static double[] removeZeros( final double[] in )
	{
		final ArrayList<Double> tmp = new ArrayList<Double>(); 
		for( int i = 0; i < in.length; i++ )
		{
			if( in[ i ] != 0 )
			{				
				tmp.add( in[ i ] ); 
			}
		}
		final double[] out = new double[ tmp.size() ]; 
		for( int i = 0; i < tmp.size(); i++ ) 
		{
			out[ i ] = tmp.get( i ); 
		}
		
		return out; 
	}
	
	
	/**
	 * Writes array to file specified
	 * @param array
	 * @param file
	 */
	public static void writeToFile( final double[] array, final File file )
	{
		PrintWriter out = TextFileAccess.openFileWrite( file ); 
		for( Double d : array )
			out.println( d ); 
		
		out.close(); 
	}
	
	/**
	 * Writes array to file specified
	 * @param array
	 * @param file
	 */
	public static void writeToFile( final Double[] array, final File file )
	{
		PrintWriter out = TextFileAccess.openFileWrite( file ); 
		for( Double d : array )
			out.println( d ); 
		
		out.flush(); 
		out.close(); 
	}
	
	/**
	 * Writes array to file specified
	 * @param array
	 * @param file
	 */
	public static void writeToFile( final double[][] matrix, final File file )
	{
		PrintWriter out = TextFileAccess.openFileWrite( file ); 
		
		for( int i = 0; i < matrix.length; i++ )
			out.println( arrayToString( matrix[ i ] ) );
		
		out.flush(); 
		out.close(); 
	}
	
	/**
	 * 
	 * @param args
	 */
	
	public static void main(String[] args) 
	{
		final double[] array1 = { 1.098383839, 3.03030303003344, 5.83838383838 };
		final double[] array2 = { 4.0, -2.0, -1.0 };
		final double[] array3 = { 4.0, 0, -1.0 };
		
		//System.out.println(  getMinimum(array2) );
		
		//System.out.println( arrayToString(array1, "\t", 3 ) );
		
		final double[][] matrix1 = new double[][]{
				  { 0.0, 0.908597820519329,	0.9348686513077822,	0.932407031890514, 0.9280261140145634,	0.9384882238428602 },
				  { 0.908597820519329,	Double.NaN,	0.8962549951762211,	0.9695756559868266,	0.8531231610175372,	0.9005486194348331 },
				  { 0.9348686513077822,	0.8962549951762211,	0.0,	0.9651262503390295,	0.899104002646973,	0.858505533294865 },
				  { 0.932407031890514,	0.9695756559868266,	0.9651262503390295,	0.0,	0.9427823996475913,	0.9473669049367176 },
				  /*{ 0.9280261140145634,	0.8531231610175372,	0.899104002646973,	0.9427823996475913,	0.0,	0.8783195043804446 },
				  { 0.9384882238428602,	0.9005486194348331,	0.858505533294865,	0.9473669049367176,	0.8783195043804446,	0.0 },*/ 
				}; 
		
		final double[][] matrix2 = new double[][]{
				{ 1, 1, 1, 1 },
				{ 1, 1, Double.NaN, 1 },
				{ 1, 1, 1, 1 },
				{ 1, 1, 1, 1 },
		};  
		
		//System.out.println( matrixToString( matrix1 ) ); 
		//System.out.println( arrayToString( sumOverColumns( matrix1 ) ) ); 
		
		//System.out.println( arrayToString( getColumn( matrix1, 0 ) ) ); 
		
		//System.out.println( DoubleArrayTools.arrayToString( meanArray(array1, array2) ) ); 
		
		//System.out.println( a1 + " " + a2 ); 
		
		System.out.println( DoubleArrayTools.arrayToString( filterByValue(array1, 1, 4 ) ) ); 
	}
}
