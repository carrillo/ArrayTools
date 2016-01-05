package array.infrastructure;

import java.util.ArrayList;

import array.tools.DoubleArrayTools;

/**
 * Flexible access to values in a square matrix. 
 * Allows for removal and substitution of rows and columns without remaking the whole array. 
 * Used for updating a distance matrix while clustering.  
 * @author carrillo
 *
 */
public class FlexibleMatrix 
{
	private ArrayList<Double> values;
	private int nrOfRows, nrOfCols; 
	
	/**
	 * Fills the value list. 
	 * Order: Complete rows. 
	 * @param matrix
	 */
	public void initiate( final double[][] matrix )
	{
		//Fill value array with numbers. Order: 
		this.values = new ArrayList<Double>();  
		this.nrOfRows = matrix.length; 
		this.nrOfCols = matrix[ 0 ].length; 
		for( double[] row : matrix )
			for( double v : row )
				this.values.add( v ); 
	}
	
	/**
	 * Returns the matrix as an double[][] 
	 * @return
	 */
	public double[][] toDoubleArray()
	{
		final double[][] out = new double[ this.nrOfRows ][ this.nrOfCols ]; 
		
		int row = 0; 
		int col = 0;  
		for( Double v : this.values )
		{
			if( row > ( nrOfCols - 1 ) )
			{
				row = 0; 
				col++; 
			}
			out[ col ][ row ] = v; 
			row++; 
		}
		
		return out; 
	}
	
	/**
	 * Gets the value defined by their position in the matrix. 
	 * @param row
	 * @param col
	 * @return
	 */
	public double getValue( final int row, final int col )
	{
		return this.values.get( ( row * this.nrOfCols ) + col );  
	}
	
	/**
	 * Returns the specified row as a double[]
	 * @param row
	 * @return
	 */
	public double[] getRow( final int row )
	{
		final double[] out = new double[ this.nrOfCols ];
		int j = 0; 
		for( int i = ( row * this.nrOfCols ); i < ( ( row + 1 ) * this.nrOfCols ); i++  )
		{
			out[ j ] = this.values.get( i ); 
			j++; 
		}
		return out; 
	}
	
	/**
	 * Returns the specified column as a double[]
	 * @param col
	 * @return
	 */
	public double[] getColumn( final int col )
	{
		final double[] out = new double[ this.nrOfRows ];
		int j = 0; 
		for( int i = col; i < this.values.size(); i = i + this.nrOfCols )
		{
			out[ j ] = this.values.get( i ); 
			j++; 
		}
		
		return out; 
	}
	
	/**
	 * Removes the specified row
	 * @param row
	 */
	public void removeRow( final int row )
	{
		//Remove values from list 
		for( int i = ( ( row + 1 ) * this.nrOfCols ) - 1; i >= ( row * this.nrOfCols ); i--  )
			this.values.remove( i );  
		
		//Update nr of rows 
		this.nrOfRows--; 
	}
	
	/**
	 * Removes the specified column
	 * @param row
	 */
	public void removeColumn( final int col )
	{
		//Remove values from list 
		for( int i = ( ( this.nrOfRows - 1 ) * nrOfCols ) + col; i >= col; i = i - this.nrOfCols )
			this.values.remove( i );  
		
		//Update nr of rows 
		this.nrOfCols--; 
	}
	
	/**
	 * Substitutes values in the specified row with the given values. 
	 * @param row
	 * @param newRow
	 * @return
	 */
	public boolean substituteRow( final int row, final double[] newRow )
	{
		if( newRow.length != nrOfCols )
			return false; 
		else
		{
			int j = nrOfCols - 1 ; 
			for( int i = ( ( row + 1 ) * this.nrOfCols ) - 1; i >= ( row * this.nrOfCols ); i--  )
			{
				this.values.remove( i );
				this.values.add( i, newRow[ j ] ); 
				j--; 
			}
			return true; 
		}
	}
	
	/**
	 * Substitutes values in the specified column with the given values. 
	 * @param row
	 * @param newRow
	 * @return
	 */
	public boolean substituteColumn( final int col, final double[] newCol )
	{
		if( newCol.length != nrOfRows )
			return false; 
		else
		{
			int j = nrOfRows - 1 ; 
			for( int i = ( ( this.nrOfRows - 1 ) * nrOfCols ) + col; i >= col; i = i - this.nrOfCols )
			{
				this.values.remove( i );
				this.values.add( i, newCol[ j ] ); 
				j--; 
			}
			return true; 
		}
	}
	
	
	public static void main(String[] args) 
	{
		final double[][] matrix = new double[][]{
				{ 1, 2, 3 },
				{ 4, 5, 6 },
				{ 4, 5, 6 },
				{ 7, 8, 9 }
		};

		FlexibleMatrix fsm = new FlexibleMatrix(); 
		fsm.initiate( matrix ); 
		System.out.println( DoubleArrayTools.matrixToString( fsm.toDoubleArray() ) + "\n" );
		
		final double[] newCol = new double[] { 4,3,2,1 }; 
		fsm.substituteColumn( 2, newCol ); 
		System.out.println( DoubleArrayTools.matrixToString( fsm.toDoubleArray() ) );
	}

}
