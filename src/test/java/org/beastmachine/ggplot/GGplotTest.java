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
 // ggsave(ggplot(data.frame(x=x,y=y),aes(x,y))+geom_point(),"/Users/wheaton/ggplot.pdf")     
	}
	
	

}

