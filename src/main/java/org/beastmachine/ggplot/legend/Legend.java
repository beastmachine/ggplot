package org.beastmachine.ggplot.legend;

import java.util.ArrayList;

import org.beastmachine.ggplot.theme.LegendBox;

public class Legend { //TODO this should be able to draw itself but suddenly I dont know what interface to use
  
  private ArrayList<LegendData> legendData;
  private LegendBox legendBox;

  public Legend(LegendData legendData, LegendBox legendBox){
    this.legendData = new ArrayList<LegendData>();
    this.legendData.add(legendData);
    this.legendBox = legendBox;
  }
  
  public Legend(ArrayList<LegendData> legendData, LegendBox legendBox){
    this.legendData = legendData;
    this.legendBox = legendBox;
  }
  
}
