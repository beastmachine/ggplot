package org.beastmachine.ggplot.theme;

import java.util.List;

public class LegendData {
  private String title;
  private List<String> texts;
  
  public LegendData(String title, List<String> texts) {
    this.title = title;
    this.texts = texts;
  }
  
  public String getTitle() {
    return title;
  }
  
  public String getText(int index) {
    return texts.get(index);
  }
  
  public List<String> getTexts() {
    return texts;
  }
}
