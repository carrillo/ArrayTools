package array.filter;

import infrastructure.SpliceJunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import variableContainer.DoubleArrayAndSpliceJunctionContainer;
import variableContainerComparators.DoubleArrayAndSpliceJunctionReadCountComparator;
import variableContainerComparators.DoubleArrayAndSpliceJunctionSDComparator;
import array.tools.ArrayListIntegerTools;

public class ArrayListDoubleFilter 
{
	/**
	 * Filter double[] entries in ArrayList<double[]> by mininmal or maximal allowed SD value. 
	 * @param minSD
	 * @param maxSD
	 */
	public static void filterBySD( ArrayList<double[]> inVal, ArrayList<Object> inId, final double minSD, final double maxSD )
	{
		//System.out.println( "Length before filtering: " + inVal.size() ); 
		DescriptiveStatistics ds = new DescriptiveStatistics(); 
		ArrayList<Integer> removeIndex = new ArrayList<Integer>();
		double sd; 
		for( int i = 0; i < inVal.size(); i++ )
		{
			ds = new DescriptiveStatistics( inVal.get( i ) );
			sd = ds.getStandardDeviation(); 
			if( sd < minSD || sd > maxSD )
			{ 
				removeIndex.add( i ); 
			}
			
		}
		
		int removeI = -1; 
		for( int i = removeIndex.size() - 1; i >= 0; i-- )
		{
			removeI = removeIndex.get( i ); 
			inVal.remove( removeI );
			inId.remove( removeI );
		}
		//System.out.println( "Length after filtering: " + in.size() ); 
	}
	
	/**
	 * Filter double[] entries in ArrayList<double[]> by mininmal or maximal allowed SD value. 
	 * @param minSD
	 * @param maxSD
	 */
	public static void filterByReadCount( ArrayList<double[]> inVal, ArrayList<SpliceJunction> ids, final double minReadCount, final double maxReadCount )
	{ 
		ArrayList<Integer> removeIndex = new ArrayList<Integer>();
		int readCount; 
		for( int i = 0; i < inVal.size(); i++ )
		{ 
			readCount = ArrayListIntegerTools.sum( ids.get( i ).getReadCount() );    
			if( readCount < minReadCount || readCount > maxReadCount )
			{ 
				removeIndex.add( i ); 
			}
			
		}
		
		int removeI = -1; 
		for( int i = removeIndex.size() - 1; i >= 0; i-- )
		{
			removeI = removeIndex.get( i ); 
			inVal.remove( removeI );
			ids.remove( removeI );
		}
		//System.out.println( "Length after filtering: " + in.size() ); 
	}
	
	/**
	 * Get first n entries from both lists 
	 * @param inVal
	 * @param ids
	 * @param numberOfEntries
	 */
	public static void head( final ArrayList<double[]> inVal, final ArrayList<SpliceJunction> ids, final long numberOfEntries )
	{
		
	}
	
	/**
	 * Returns the N entries with the highest SD 
	 * @param inVal
	 * @param numberOfEntries
	 */
	public static void getNHighestSDEntries( final ArrayList<double[]> inVal, final ArrayList<SpliceJunction> ids, final long numberOfEntries )
	{
		getNHighestEntriesJudgedByComparator(inVal, ids, new DoubleArrayAndSpliceJunctionSDComparator(), numberOfEntries);
	}
	
	/**
	 * Returns the N entries with the highest underlying readcount. 
	 * The read count value is carried in the last enty of the id string
	 * @param inVal
	 * @param inIds
	 * @param numberOfEntries
	 */
	public static void getNHighestReadCountEntries( final ArrayList<double[]> inVal, final ArrayList<SpliceJunction> ids, final long numberOfEntries )
	{
		getNHighestEntriesJudgedByComparator(inVal, ids, new DoubleArrayAndSpliceJunctionReadCountComparator(), numberOfEntries);
	}
	
	/**
	 * Returns the N entries with the highest score judged by past comparator 
	 * @param inVal
	 * @param numberOfEntries
	 */
	public static void getNHighestEntriesJudgedByComparator( final ArrayList<double[]> inVal, final ArrayList<SpliceJunction> ids, final Comparator<DoubleArrayAndSpliceJunctionContainer> comparator, final long numberOfEntries )
	{
		//Add ids and strings to sorting container 
		ArrayList<DoubleArrayAndSpliceJunctionContainer> list = new ArrayList<DoubleArrayAndSpliceJunctionContainer>(); 
		for( int i = 0; i < inVal.size(); i++ )
		{
			list.add( new DoubleArrayAndSpliceJunctionContainer(inVal.get( i ), ids.get( i ) ) ); 
		}
		
		//Sort the list by SD
		Collections.sort( list, comparator );
		
		//Remove all the last entries  
		do
		{
			list.remove( list.size() - 1 ); 
			
		} while( list.size() > numberOfEntries );
		
		//Remove old entries 
		inVal.clear();
		ids.clear();
		
		
		//Add sorted entries 
		System.out.println( "Add new entries" ); 
		for( DoubleArrayAndSpliceJunctionContainer entry : list )
		{
			inVal.add( entry.getVal() ); 
			ids.add( entry.getSJ() ); 
		}

	}
	
	/**
	 * Returns the N entries with the highest score judged by past comparator 
	 * @param inVal
	 * @param numberOfEntries
	 */
	public static void getNLowestEntriesJudgedByComparator( final ArrayList<double[]> inVal, final ArrayList<SpliceJunction> ids, final Comparator<DoubleArrayAndSpliceJunctionContainer> comparator, final long numberOfEntries )
	{
		//Add ids and strings to sorting container 
		ArrayList<DoubleArrayAndSpliceJunctionContainer> list = new ArrayList<DoubleArrayAndSpliceJunctionContainer>(); 
		for( int i = 0; i < inVal.size(); i++ )
		{
			list.add( new DoubleArrayAndSpliceJunctionContainer(inVal.get( i ), ids.get( i ) ) ); 
		}
		
		//Sort the list by reverse comparator
		Collections.sort( list, Collections.reverseOrder( comparator ) );
		
		
		//Remove all the last entries  
		do
		{
			list.remove( list.size() - 1 ); 
			
		} while( list.size() > numberOfEntries );
		
		//Remove old entries 
		inVal.clear();
		ids.clear();
		
		
		//Add sorted entries 
		//System.out.println( "Add new entries" ); 
		for( DoubleArrayAndSpliceJunctionContainer entry : list )
		{
			inVal.add( entry.getVal() ); 
			ids.add( entry.getSJ() ); 
		}

	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
