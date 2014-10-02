package org.beastmachine.ggplot;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import static org.beastmachine.ggplot.GGPlot.*;

public class GGplotTest {

	@Test
	public void test() {
		int[] v1 = {1,2,3,4};
		int[] v2 = {1,2,3,4};
		System.out.println("current corking directory "+new File("./").getAbsolutePath());
		GGPlot g = ggplot(dataframe().c("x",v1).c("y",v2),aes().x("x").y("y")).geom_point();
		ggsave(g, "./src/main/resources/ggplot.pdf");  
	}


	@Test
	public void testDataFrameChanger() {
		int[] v1 = {1,2,3,4};
		int[] v2 = {4,3,2,1};
		System.out.println("current corking directory "+new File("./").getAbsolutePath());
		
		GGPlot g = ggplot(dataframe().c("x",v1).c("y",v2),aes().x("x").y("y")).geom_point(data(dataframe().c("x",v2).c("y",v1)));
		
		ggsave(g, "./src/main/resources/ggplotdatachanger.pdf"); 
	}
	
}

