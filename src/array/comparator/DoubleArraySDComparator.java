package array.comparator;

import java.util.Comparator;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class DoubleArraySDComparator implements Comparator<double[]> {

	@Override
	public int compare(double[] arg0, double[] arg1) 
	{
		DescriptiveStatistics ds = new DescriptiveStatistics( arg0 ); 
		final double sd0 = ds.getStandardDeviation();
		ds = new DescriptiveStatistics( arg1 ); 
		final double sd1 = ds.getStandardDeviation(); 
		
		if( sd0 > sd1 )
			return -1; 
		else if( sd1 > sd0 )
			return 1; 
		else 
			return 0; 
	}

}
