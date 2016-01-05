package array.ordering;

import helpers.NewickTools;
import hierachical.Cluster;
import hierachical.Clusterable;
import hierachical.HierachicalClustering;
import inputOutput.TextFileAccess;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import variableContainer.DoubleArrayAndIdContainer;
import array.tools.ArrayListDoubleTools;
import array.tools.DoubleArrayTools;

public class OrderArrayListsDouble 
{
	private ArrayList<double[]> list; 
	private ArrayList<String> idList; 
	
	public void initiate( ArrayList<double[]> list, ArrayList<String> idList )
	{
		setList( list );  
		setIdList( idList ); 
	}
	
	/**
	 * Order arraylist by hierachical clustering followed by flattening of the tree. 
	 * 1. Move ids and double array values in a clusterable container. 
	 * 2. Cluster. 
	 * 3. Retrieve newick representation 
	 * 4. Flatten tree. 
	 * 5. Reorder by ids. 
	 * @param verbose
	 */
	public Cluster orderRowsByHierachicalClustering( final boolean verbose )
	{
		//Move entries to clusterable container
		ArrayList<Clusterable> entries = new ArrayList<Clusterable>(); 
		for( int i = getList().size() - 1 ; i >= 0 ; i-- )
			entries.add( new DoubleArrayAndIdContainer( getList().get( i ), getIdList().get( i ) ) ); 
			//vals.remove( i ); 
			//ids.remove( i ); 
		
		if( verbose )
			System.out.println( "Initiate clustering with " + entries.size() + " entries."); 
		
		//Cluster 
		HierachicalClustering hc = new HierachicalClustering(); 
		hc.initiate( entries ); 
		Cluster c = hc.call();
		
		//Flatten newick representation
		final String tree = c.getId() + ";"; 
		ArrayList<String> treeIds = NewickTools.flattenTree( tree ); 
		
		//Order list by flat tree.  
		HashMap<String, DoubleArrayAndIdContainer> hm = new HashMap<String, DoubleArrayAndIdContainer>(); 
		for( int i = 0; i < entries.size(); i++ )
		{
			DoubleArrayAndIdContainer ce = (DoubleArrayAndIdContainer) entries.get( i );
			//System.out.println( entries.get( i ).getId() ); 
			hm.put( ce.getId(), ce ); 
		}
		
		ArrayList<String> newIds = new ArrayList<String>(); 
		ArrayList<double[]> newList = new ArrayList<double[]>();
		DoubleArrayAndIdContainer currentEntry; 
		for( String id : treeIds )
		{
			currentEntry = hm.get( id );
			newIds.add( id ); 
			//System.out.println( id ); 
			newList.add( currentEntry.getVal() );  
		}
		this.idList = newIds; 
		this.list = newList; 
		
		return c; 
	}
	
	/**
	 * Order arraylist by minimizing the distance between sequential entries
	 * 1. Move all entries with zero variance to the front, order by value
	 *  
	 * 2. Define the first entry (i1) as the one most uniform (lowest sd). Set current index (i1) to 0.  
	 * 2. Find the entry with the max covariance to the current entry.
	 * 3. 
	 */
	public void orderRowsByMaxCorrelation( final boolean verbose )
	{	
		//Move all the zero variance entries to the front. Ordered by value.   
		final ArrayList<Integer> zeroVar = getIndicesOfEntriesWithZeroVariance();
		ArrayList<double[]> valStore = new ArrayList<double[]>();
		ArrayList<String> idStore = new ArrayList<String>(); 
		for( int i = zeroVar.size() - 1; i >= 0; i-- )
		{
			final int index = zeroVar.get( i ); 
			valStore.add( getList().get( index ) );
			getList().remove( index );  
			idStore.add( getIdList().get( index ) );
			getIdList().remove( index ); 
		}
		
		//Get indices for the next steps. i1 = entry outside of zero var entries and i2 = lowest sd for remaining 
		int i1 = valStore.size();
		int i2 = ArrayListDoubleTools.getIndexOfLowestSD( getList() ) + valStore.size();
		
		//Sort the values and add them to the beginning of the original list. 
		sortByValues( valStore, idStore );
		
		for( int i = valStore.size() - 1; i >= 0; i-- )
		{
			getList().add( 0 , valStore.get( i ) ); 
			getIdList().add( 0, idStore.get( i ) ); 
		}
		if( verbose )
		{
			for( int i = 0; i < valStore.size(); i++ )
				System.out.println( "Current line: " + i + ":\t" + getIdList().get( i ) + "\t" + DoubleArrayTools.arrayToString( getList().get( i ) ) );
		}
		
		//Swap the low sd entry to first position after zero var array. 
		swap( i1, i2 ); 
		if( verbose )
			System.out.println( "Current line: " + i1 + ":\t" + getIdList().get( i1 ) + "\t" + DoubleArrayTools.arrayToString( getList().get( i1 ) ) );
		
		//Find sequential closest samples. 
		PearsonsCorrelation pc = new PearsonsCorrelation();  
		while( i1 < getList().size() - 1  )
		{ 
			i2 = getIndexOfMaxCorrelation( i1, pc ) ;
			i1++; 
			swap( i1, i2 );
			if( verbose )
				System.out.println( "Current line: " + i1 + ":\t" + getIdList().get( i1 ) + "\t" + DoubleArrayTools.arrayToString( getList().get( i1 ) ) ); 
		}
		
	}
	
	/**
	 * Order arraylist by minimizing the distance between sequential entries
	 * 1. Move all entries with zero variance to the front, order by value
	 *  
	 * 2. Define the first entry (i1) as the one most uniform (lowest sd). Set current index (i1) to 0.  
	 * 2. Find the entry with the max covariance to the current entry.
	 * 3. 
	 */
	public void orderRowsByMaxCovariance( final boolean verbose )
	{	
		//Move all the zero variance entries to the front. Ordered by value.   
		final ArrayList<Integer> zeroVar = getIndicesOfEntriesWithZeroVariance();
		ArrayList<double[]> valStore = new ArrayList<double[]>();
		ArrayList<String> idStore = new ArrayList<String>(); 
		for( int i = zeroVar.size() - 1; i >= 0; i-- )
		{
			final int index = zeroVar.get( i ); 
			valStore.add( getList().get( index ) );
			getList().remove( index );  
			idStore.add( getIdList().get( index ) );
			getIdList().remove( index ); 
		}
		
		//Get indices for the next steps. i1 = entry outside of zero var entries and i2 = lowest sd for remaining 
		int i1 = valStore.size();
		int i2 = ArrayListDoubleTools.getIndexOfLowestSD( getList() ) + valStore.size();
		
		//Sort the values and add them to the beginning of the original list. 
		sortByValues( valStore, idStore );
		
		for( int i = valStore.size() - 1; i >= 0; i-- )
		{
			getList().add( 0 , valStore.get( i ) ); 
			getIdList().add( 0, idStore.get( i ) ); 
		}
		if( verbose )
		{
			for( int i = 0; i < valStore.size(); i++ )
				System.out.println( "Current line: " + i + ":\t" + getIdList().get( i ) + "\t" + DoubleArrayTools.arrayToString( getList().get( i ) ) );
		}
		
		//Swap the low sd entry to first position after zero var array. 
		swap( i1, i2 ); 
		if( verbose )
			System.out.println( "Current line: " + i1 + ":\t" + getIdList().get( i1 ) + "\t" + DoubleArrayTools.arrayToString( getList().get( i1 ) ) );
		
		//Find sequential closest samples. 
		Covariance cov = new Covariance();   
		while( i1 < getList().size() - 1  )
		{ 
			i2 = getIndexOfMaxCovariance(i1, cov) ;
			i1++; 
			swap( i1, i2 );
			if( verbose )
				System.out.println( "Current line: " + i1 + ":\t" + getIdList().get( i1 ) + "\t" + DoubleArrayTools.arrayToString( getList().get( i1 ) ) ); 
		}
	}
	
	/**
	 * Returns a list of indices of all entries having zero variance.    
	 * @return
	 */
	private ArrayList<Integer> getIndicesOfEntriesWithZeroVariance()
	{
		ArrayList<Integer> out = new ArrayList<Integer>(); 
		DescriptiveStatistics ds = null; 
		for( int i = 0; i < getList().size(); i++ )
		{
			ds = new DescriptiveStatistics( getList().get( i ) ); 
			if( ds.getVariance() == 0  )
			{
				out.add( i );   
			}
		}
		
		return out; 
	}
	
	/**
	 * Sorts the double[] by their values, starting with the first element and going to the last element. 
	 * @return
	 */
	private void sortByValues( ArrayList<double[]> vals, ArrayList<String> ids )
	{
		ArrayList<DoubleArrayAndIdContainer> entries = new ArrayList<DoubleArrayAndIdContainer>(); 
		for( int i = vals.size() - 1 ; i >= 0 ; i-- )
		{
			entries.add( new DoubleArrayAndIdContainer(vals.get( i ), ids.get( i ) ) );
			//System.out.println( entries.get( entries.size() - 1 ) ); 
			vals.remove( i ); 
			ids.remove( i ); 
		}
		
		Comparator<DoubleArrayAndIdContainer> comp = new Comparator<DoubleArrayAndIdContainer>() {
			
			@Override
			public int compare(DoubleArrayAndIdContainer vi1, DoubleArrayAndIdContainer vi2) 
			{
				final double[] o1 = vi1.getVal(); 
				final double[] o2 = vi2.getVal(); 
				
				int i = 0; 
				double e1 = o1[ i ]; 
				double e2 = o2[ i ];
				
				while( e1 == e2 && i < o1.length - 1 )
				{
					i++; 
					e1 = o1[ i ]; 
					e2 = o2[ i ]; 
				}
				
				if( e1 > e2 )
					return 1; 
				if( e1 < e2 )
					return -1; 
				else
					return 0;
			}
		}; 
		
		Collections.sort( entries, comp ); 
		
		for( DoubleArrayAndIdContainer v : entries )
		{ 
			vals.add( v.getVal() ); 
			ids.add( v.getId() ); 
		}
		 
	}
	
	
	/**
	 * Returns the index of the double[] with the max correlation to the one provided
	 * @param i1
	 * @return
	 */
	private int getIndexOfMaxCorrelation( final int i1, PearsonsCorrelation pc )
	{
		
		final double[] a1 = getList().get( i1 ); 
		
		double maxCorr = -Double.MAX_VALUE; 
		int index = -1; 
		double currentCorr = -Double.MAX_VALUE;  
		for( int i = i1 + 1; i < getList().size(); i++ )
		{
			final double[] a2 = getList().get( i );  
			
			currentCorr = pc.correlation( a1, a2 ) ;
			
			if( currentCorr > maxCorr )
			{
				maxCorr = currentCorr; 
				index = i;   
			}
		}
		
		//System.out.println( "Rows: " + i1 + " and " + index + " max correlation: " + maxCorr );
		//System.out.println( "Arrays: " + DoubleArrayTools.arrayToString( a1 ) + "\t" + DoubleArrayTools.arrayToString( maxCorrArray ) );
		
		return index; 
	}
	
	/**
	 * Returns the index of the double[] with the max covariance to the one provided
	 * @param i1
	 * @return
	 */
	private int getIndexOfMaxCovariance( final int i1, Covariance cov )
	{
		
		final double[] a1 = getList().get( i1 ); 
		
		double maxCov = -Double.MAX_VALUE; 
		int index = -1; 
		double currentCov = -Double.MAX_VALUE; 
		for( int i = i1 + 1; i < getList().size(); i++ )
		{
			currentCov = cov.covariance( a1, getList().get( i ), true );
			
			if( currentCov > maxCov )
			{
				maxCov = currentCov; 
				index = i;  
			}
		}
		
		return index; 
	}
	
	/**
	 * Order arraylist by minimizing the distance between sequential entries
	 * 1. Define the first entry (i1) as the one most uniform (lowest sd). Set current index (i1) to 0.  
	 * 2. Find the entry closest to the current entry.
	 * 3. 
	 */
	public void orderRowsByMinDistance( final boolean verbose )
	{
		//Find the most homogenous sample and put it to first position
		int i1 = 0; 
		int i2 = ArrayListDoubleTools.getIndexOfLowestSD( getList() ); 
		swap(i1, i2); 
		if( verbose )
			System.out.println( "Current line: " + i1 + ":\t" + getIdList().get( i1 ) + "\t" + DoubleArrayTools.arrayToString( getList().get( i1 ) ) ); 
		
		
		//Find sequential closest samples.
		//int count = 0; 
		while( i1 < getList().size() - 1  )
		{ 
			i2 = getIndexOfMinDistance( i1 ) ;
			i1++; 
			swap( i1, i2 );
			if( verbose )
				System.out.println( "Current line: " + i1 + ":\t" + getIdList().get( i1 ) + "\t" + DoubleArrayTools.arrayToString( getList().get( i1 ) ) );
		}
		
	}
	
	/**
	 * Returns the index of the double[] with the minimal distance to the one provided
	 * @param i1
	 * @return
	 */
	private int getIndexOfMinDistance( final int i1 )
	{
		final double[] a1 = getList().get( i1 );  
		
		double minDistance = Double.MAX_VALUE; 
		int index = -1; 
		double currentD = -1; 
		
		for( int i = i1 + 1; i < getList().size(); i++ )
		{
			currentD = DoubleArrayTools.getEucledianDistance(a1, getList().get( i ) ); 
			if( currentD < minDistance )
			{
				minDistance = currentD; 
				index = i; 
				if( minDistance == 0 )
					break; 
			}
		}
		
		return index; 
	}
	
	/**
	 * Just orders the array by sd of the double[] 
	 */
	public void orderRowsBySD()
	{
		Comparator<double[]> comp = new Comparator<double[]>( ) {

			protected DescriptiveStatistics DS = new DescriptiveStatistics(); 
			
			@Override
			public int compare(double[] o1, double[] o2) {
				DS = new DescriptiveStatistics( o1 ); 
				final double sd1 = DS.getStandardDeviation(); 
				DS = new DescriptiveStatistics( o2 ); 
				final double sd2 = DS.getStandardDeviation();
				
				if( sd1 > sd2 )
					return 1; 
				if( sd1 < sd2 )
					return -1; 
				else 
					return 0;
			}
		};
		
		Collections.sort( getList(), comp ); 
	}
	
	private void swap( final int i1, final int i2 )
	{
		//Swap list entries 
		final double[] a1 = getList().get( i1 ).clone();
		final double[] a2 = getList().get( i2 ).clone();
		
		getList().remove( i2 ); 
		getList().add( i2, a1 ); 
		
		getList().remove( i1 ); 
		getList().add( i1, a2 );
		
		//Swap id list entries 
		final String id1 = getIdList().get( i1 ); 
		final String id2 = getIdList().get( i2 );
		
		getIdList().remove( i2 );
		getIdList().add( i2, id1 ); 
		
		getIdList().remove( i1 ); 
		getIdList().add( i1, id2 );
	}
	
	public void print()
	{
		for( int i = 0; i < getList().size(); i++ )
		{
			System.out.println( getIdList().get( i ) + "\t" + DoubleArrayTools.arrayToString( getList().get( i ) ) );
		}
		System.out.println("");
	}
	
	public void write( final File file )
	{
		PrintWriter out = TextFileAccess.openFileWrite( file ); 
		
		for( int i = 0; i < getList().size(); i++ )
		{
			out.println( getIdList().get( i ) + "\t" + DoubleArrayTools.arrayToString( getList().get( i ) ) );
		}
		
		out.close(); 
	}
	
	//Setter
	public void setList( ArrayList<double[]> list ) { this.list = list;  }
	public void setIdList( final ArrayList<String> idList ) { this.idList = idList;  }
	
	//Getter 
	public ArrayList<double[]> getList() { return this.list; } 
	public ArrayList<String> getIdList() { return this.idList; } 
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		final double[] a1 = new double[]{ 0, 1, 2, 3, 4 };
		final double[] a2 = new double[]{ 3, 3, 3, 3, 3 };
		final double[] a3 = new double[]{ 2, 2, 2, 2, 3 };
		final double[] a4 = new double[]{ 4, 3, 2, 1, 0 };
		final double[] a5 = new double[]{ 2, 2, 2, 2, 2 };
		final double[] a6 = new double[]{ 2, 2, 2, 2, 2 };
		final ArrayList<double[]> al = new ArrayList<double[]>();
		al.add( a1 ); 
		al.add( a2 );
		al.add( a3 );
		//al.add( a4 );
		//al.add( a5 );
		//al.add( a6 );
		
		final ArrayList<String> ids = new ArrayList<String>(); 
		ids.add( "A" );
		ids.add( "B" );
		ids.add( "C" );
		//ids.add( "D" );
		//ids.add( "E" );
		//ids.add( "F" );
		
		OrderArrayListsDouble oa = new OrderArrayListsDouble();
		oa.initiate( al, ids );
		
		
		Cluster c = oa.orderRowsByHierachicalClustering( true ); 
		
		//oa.print(); 
	}

}
