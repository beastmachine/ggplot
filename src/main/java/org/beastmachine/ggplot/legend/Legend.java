package org.beastmachine.ggplot.legend;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.beastmachine.dataframe.Column;
import org.beastmachine.dataframe.DataFrame;
import org.beastmachine.ggplot.Aes;
import org.beastmachine.ggplot.Aes.Aesthetic;
import org.beastmachine.ggplot.theme.LegendBox;
import org.beastmachine.ggplot.theme.Theme;

import com.google.common.base.Preconditions;


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
  
  /**
   * This static function does the heavy lifting of taking a Theme, Aes, and DataFrame (post stat?TODO not sure)
   * and creates the Legend
   * 
   * from the python ggplot they describe a legend as
   * """
A legend is a dict of type
{aesthetic: {
    'column_name': 'column-name-in-the-dataframe',
    'dict': {visual_value: legend_key},
    'scale_type': 'discrete' | 'continuous'}}
where aesthetic is one of:
  'color', 'fill', 'shape', 'size', 'linetype', 'alpha'
visual_value is a:
  color value, fill color value, linetype string,
  shape character, size value, alpha value
legend_key is either:
  - quantile-value for continuous mappings.
  - value for discrete mappings.
"""
   *
   * so that means that we 
   * 
   * unfortunately we will also need to look in the Scale in case the labels or values of these are being mapped differently, ignore this for now TODO
   * 
   * @param theme
   * @param aes
   * @param data
   * @return
   */
  public static Legend buildLegend(Theme theme, Aes aes, DataFrame data){
    LegendBox legendBox = new LegendBox(theme);
    ArrayList<LegendData> legendData = new ArrayList<LegendData>();
    Map<String, ArrayList<Aes.Aesthetic>> discreteMappings = new TreeMap<String, ArrayList<Aes.Aesthetic>>(); 
    Map<String, ArrayList<Aes.Aesthetic>> continuousMappings = new TreeMap<String, ArrayList<Aes.Aesthetic>>();
    // it is quite possible that all discrete mappings or all continuous mappings cannot be displayed in one legend in which case things
    // will have to be broken up, but for the time being we aren't actually painting the right thing anyway so we are just getting the
    // basic idea
    Aes.Aesthetic[] lengendaryAesthetics = {Aes.Aesthetic.alpha, Aes.Aesthetic.color, Aes.Aesthetic.colour, 
        Aes.Aesthetic.fill, Aes.Aesthetic.linetype, Aes.Aesthetic.shape, Aes.Aesthetic.size};
    
    for(Aes.Aesthetic aesthetic: lengendaryAesthetics){
      String var = aes.getVariable(aesthetic);
      if(var != null){
        Preconditions.checkState(data.hasColumn(var), "Mapped variable "+var+" to aesthetic "+aesthetic+" but "+var+" not found");
        Column col = data.get(var);
        if(col.isFactor()){
          Preconditions.checkState(isAestheticDiscreteMappable(aesthetic),"Aesthetic "+aesthetic+" cannot be mapped to a discrete (factor) variable");
          if(!discreteMappings.containsKey(var))
            discreteMappings.put(var, new ArrayList<Aes.Aesthetic>());
          discreteMappings.get(var).add(aesthetic);
        }
        else if(col.isNumeric()){
          Preconditions.checkState(isAestheticContinuousMappable(aesthetic),"Aesthetic "+aesthetic+" cannot be mapped to a discrete (factor) variable");
          if(!continuousMappings.containsKey(var))
            continuousMappings.put(var, new ArrayList<Aes.Aesthetic>());
          continuousMappings.get(var).add(aesthetic);
        }
        else{
          Preconditions.checkState(false,"Non numeric,non factor column not supported for legend currently variable "+var);
        }
      }
    }
    
    //legend data should probably know what colors/shapes/sizes etc map to what strings, but right now there are some strings
    
    if(discreteMappings.size() > 0){
      for(String var: discreteMappings.keySet()){
        ArrayList<String> vals = new ArrayList<String>();
        for(String label : data.get(var).getLabels()){
          vals.add(label);
        }
        legendData.add(new LegendData(var, vals));
      }
    }
    
    //TODO should support continuous vars but im lazy
    // I think that for this we need to get the quantiles of the data (or potentially roundish numbers near those quantiles?, maybe read the python?)
    if(continuousMappings.size() > 0){
      Preconditions.checkState(false, "continuous variables not supported for legends currently, im lazy and want to get something going for discrete right now");
    }
    return new Legend(legendData, legendBox);
  }

  private static boolean isAestheticContinuousMappable(Aesthetic aesthetic) {
    return (aesthetic == Aes.Aesthetic.alpha || aesthetic == Aes.Aesthetic.color || 
        aesthetic == Aes.Aesthetic.colour || aesthetic == Aes.Aesthetic.size || aesthetic == Aes.Aesthetic.fill);
  }

  // current all, but listed them in case there are more that this doesnt cover 
  private static boolean isAestheticDiscreteMappable(Aesthetic aesthetic) {
    return (aesthetic == Aes.Aesthetic.alpha || aesthetic == Aes.Aesthetic.color || aesthetic == Aes.Aesthetic.colour || 
        aesthetic == Aes.Aesthetic.shape || aesthetic == Aes.Aesthetic.size || aesthetic == Aes.Aesthetic.linetype || aesthetic == Aes.Aesthetic.fill);
  }


  
}
