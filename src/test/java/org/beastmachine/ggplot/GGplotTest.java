package org.beastmachine.ggplot;

import java.util.ArrayList;

import org.beastmachine.ggplot.GGPlot;
import org.junit.Test;

import static org.beastmachine.ggplot.GGPlot.*;

public class GGplotTest {

	@Test
	public void test() {
		GGPlot plot = ggplot(dataframe().c("x", 1,2,3,4).c("y", 1,2,3,4), aes().a(x, "x").a(y, "y")).
				geom_point().ggsave("/Users/wheaton/ggplot.pdf");
	}

}

