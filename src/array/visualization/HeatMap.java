package array.visualization;

import ij.ImageJ;
import ij.ImagePlus;
import ij.io.FileSaver;

import java.io.IOException;

import net.imglib2.RandomAccess;
import net.imglib2.img.Img;
import net.imglib2.img.ImgFactory;
import net.imglib2.img.cell.CellImgFactory;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.real.DoubleType;

public class HeatMap 
{
	public double[][] values; 
	
	public HeatMap( final double[][] values )
	{
		setValues( values );
		//DoubleArrayTools.elementwiseMultiplication(values, 100);
		//DoubleArrayNormalization.scaleBetweenMinAndMax( getValues(), 100 ); 
	}
	
	/**
	 * Shows image using fiji
	 */
	public void showImage( final String title )
	{
		ImageJFunctions.show( getImage() ).setTitle( title ); 
	}
	
	/**
	 * Saves image using imageJ
	 */
	public void saveImage( final String file )
	{
		ImagePlus ips = ImageJFunctions.show( getImage() );
		FileSaver fs = new FileSaver( ips ); 
		fs.saveAsTiff( file ); 
	}
	
	public Img<DoubleType> getImage()
	{
		// create the ImgFactory based on cells (cellsize = 5x5x5...x5) that will
        // instantiate the Img
        final ImgFactory< DoubleType > imgFactory = new CellImgFactory< DoubleType >( 5 );
        
        // create an 2d-Img with dimensions the of the value_array 
        final Img< DoubleType > img1 = imgFactory.create( new long[]{ getValues().length, getValues()[ 0 ].length }, new DoubleType() );
        //System.out.println( "Create image with dimension: " + img1.dimension( 0 ) + " " + img1.dimension( 1 ) ); 
        RandomAccess<DoubleType> ra = img1.randomAccess();  
        
        for( int row = 0; row < getValues().length; row++ )
        {
        	for( int col = 0; col < getValues()[ 0 ].length; col++ )
        	{
        		ra.setPosition( new int[]{ row, col } ); 
        		//System.out.println( "Current cursor: " + ra.getIntPosition( 0 ) + " " + ra.getIntPosition( 1 ) );
        		ra.get().set( getValues()[ row ][ col ] );  
        	}
        }
     
        
        return img1; 
	}
	
	public static void main(String[] args) throws IOException
	{
		final double[][] matrix1 = new double[][]{
				  { 0.0, 0.908597820519329,	0.9348686513077822,	0.932407031890514, 0.9280261140145634,	0.9384882238428602 },
				  { 0.908597820519329,	Double.NaN,	0.8962549951762211,	0.9695756559868266,	0.8531231610175372,	0.9005486194348331 },
				  { 0.9348686513077822,	0.8962549951762211,	0.0,	0.9651262503390295,	0.899104002646973,	0.858505533294865 },
				  { 0.932407031890514,	0.9695756559868266,	0.9651262503390295,	0.0,	0.9427823996475913,	0.9473669049367176 },
				  { 0.9280261140145634,	0.8531231610175372,	0.899104002646973,	0.9427823996475913,	0.0,	0.8783195043804446 },
				  { 0.9384882238428602,	0.9005486194348331,	0.858505533294865,	0.9473669049367176,	0.8783195043804446,	0.0 }, 
				}; 
		
		
		HeatMap hm = new HeatMap( matrix1 ); 
		new ImageJ(); 
		hm.showImage( "Test" );  
	}
	
	public void setValues( final double[][] values ) { this.values = values; } 
	public double[][] getValues() { return this.values; } 
}
