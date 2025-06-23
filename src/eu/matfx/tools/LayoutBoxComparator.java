package eu.matfx.tools;

import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * y before x
 * @author m.goerlich
 *
 */
public class LayoutBoxComparator implements Comparator<LayoutBox>
{
	
	private static LayoutBoxComparator instance = null;
	
	private LayoutBoxComparator()
	{
		
	}

	@Override
	public int compare(LayoutBox p0, LayoutBox p1) 
	{
		int yCompare = Double.compare(p0.getLayoutY(), p1.getLayoutY());
		if(yCompare == 0)
		{
			return Double.compare(p0.getLayoutX(), p1.getLayoutX());
		}
		return yCompare;
	}
	
	public static LayoutBoxComparator getInstance()
	{
		if(instance == null)
			instance = new LayoutBoxComparator();
		return instance;
	}
	
	public static void main(String[] args)
	{
		List<LayoutBox> list = new ArrayList<LayoutBox>();
		list.add(new LayoutBox(0, 0, 100, 50));
		list.add(new LayoutBox(75, 0, 100, 50));
		list.add(new LayoutBox(25, 0, 100, 50));
		list.add(new LayoutBox(25, 25, 100, 50));
		list.add(new LayoutBox(75, 30, 100, 50));
		
		Collections.sort( list, LayoutBoxComparator.getInstance());
		for(LayoutBox lb : list)
		{
			System.out.println("" + lb.toString());
		}
		
		
		
	}

}
