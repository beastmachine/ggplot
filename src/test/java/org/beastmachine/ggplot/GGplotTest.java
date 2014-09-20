package org.beastmachine.ggplot;

import java.util.ArrayList;

import org.beastmachine.ggplot.GGPlot;
import org.junit.Test;

import static org.beastmachine.ggplot.GGPlot.*;

public class GGplotTest {

	@Test
	public void test() {
		int[] v1 = {1,2,3,4};
		int[] v2 = {1,2,3,4};
		ggplot(dataframe().c("x",v1).c("y",v2),aes(x,"x",y,"y")).geom_point().ggsave("/Users/wheaton/ggplot.pdf");
	//ggplot(dataframe().c("x",x).c("y",y),aes().x("x").y("y")).geom_point().ggsave("/Users/wheaton/ggplot.pdf") 
 // ggsave(ggplot(data.frame(x=x,y=y),aes(x,y))+geom_point(),"/Users/wheaton/ggplot.pdf")     
		Object sdlfkjsd = GGplotTest.bam.boo;
	}
	
	
	public enum bam{ boo, bootwo}

}

