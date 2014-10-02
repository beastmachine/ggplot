package org.beastmachine.ggplot;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.google.common.base.Preconditions;

public class Aes {
  private Map<Aesthetic, String> mapping;

  public Aes() {
    mapping = new TreeMap<Aesthetic, String>();
  }
  
  private Aes a(Aesthetic key, String val) {
    mapping.put(key, val);
    return this;
  }
  
  public Aes adj(String val){
  	this.a(Aesthetic.adj, val);
  	return this;
  }
  
  public Aes alpha(String val){
  	this.a(Aesthetic.alpha, val);
  	return this;
  }
  
  public Aes angle(String val){
  	this.a(Aesthetic.angle, val);
  	return this;
  }
  
  public Aes bg(String val){
  	this.a(Aesthetic.bg, val);
  	return this;
  }
  
  public Aes cex(String val){
  	this.a(Aesthetic.cex, val);
  	return this;
  }
  
  public Aes col(String val){
  	this.a(Aesthetic.col, val);
  	return this;
  }
  
  public Aes color(String val){
  	this.a(Aesthetic.color, val);
  	return this;
  }
  
  public Aes colour(String val){
  	this.a(Aesthetic.colour, val);
  	return this;
  }
  
  public Aes fg(String val){
  	this.a(Aesthetic.fg, val);
  	return this;
  }
  
  public Aes fill(String val){
  	this.a(Aesthetic.fill, val);
  	return this;
  }
  
  public Aes group(String val){
  	this.a(Aesthetic.group, val);
  	return this;
  }
  
  public Aes hjust(String val){
  	this.a(Aesthetic.hjust, val);
  	return this;
  }
  
  public Aes label(String val){
  	this.a(Aesthetic.label, val);
  	return this;
  }
  
  public Aes linetype(String val){
  	this.a(Aesthetic.linetype, val);
  	return this;
  }
  
  public Aes lower(String val){
  	this.a(Aesthetic.lower, val);
  	return this;
  }
  
  public Aes lty(String val){
  	this.a(Aesthetic.lty, val);
  	return this;
  }
  
  public Aes lwd(String val){
  	this.a(Aesthetic.lwd, val);
  	return this;
  }
  
  public Aes max(String val){
  	this.a(Aesthetic.max, val);
  	return this;
  }
  
  public Aes middle(String val){
  	this.a(Aesthetic.middle, val);
  	return this;
  }
  
  public Aes min(String val){
  	this.a(Aesthetic.min, val);
  	return this;
  }
  
  public Aes order(String val){
  	this.a(Aesthetic.order, val);
  	return this;
  }
  
  public Aes pch(String val){
  	this.a(Aesthetic.pch, val);
  	return this;
  }
  
  public Aes radius(String val){
  	this.a(Aesthetic.radius, val);
  	return this;
  }
  
  public Aes sample(String val){
  	this.a(Aesthetic.sample, val);
  	return this;
  }
  
  public Aes shape(String val){
  	this.a(Aesthetic.shape, val);
  	return this;
  }
  
  public Aes size(String val){
  	this.a(Aesthetic.size, val);
  	return this;
  }
  
  public Aes srt(String val){
  	this.a(Aesthetic.srt, val);
  	return this;
  }
  
  public Aes upper(String val){
  	this.a(Aesthetic.upper, val);
  	return this;
  }
  
  public Aes vjust(String val){
  	this.a(Aesthetic.vjust, val);
  	return this;
  }
  
  public Aes weight(String val){
  	this.a(Aesthetic.weight, val);
  	return this;
  }
  
  public Aes width(String val){
  	this.a(Aesthetic.width, val);
  	return this;
  }
  
  public Aes x(String val){
  	this.a(Aesthetic.x, val);
  	return this;
  }
  
  public Aes xend(String val){
  	this.a(Aesthetic.xend, val);
  	return this;
  }
  
  public Aes xmax(String val){
  	this.a(Aesthetic.xmax, val);
  	return this;
  }
  
  public Aes xmin(String val){
  	this.a(Aesthetic.xmin, val);
  	return this;
  }
  
  public Aes xintercept(String val){
  	this.a(Aesthetic.xintercept, val);
  	return this;
  }
  
  public Aes y(String val){
  	this.a(Aesthetic.y, val);
  	return this;
  }
  
  public Aes yend(String val){
  	this.a(Aesthetic.yend, val);
  	return this;
  }
  
  public Aes ymax(String val){
  	this.a(Aesthetic.ymax, val);
  	return this;
  }
  
  public Aes ymin(String val){
  	this.a(Aesthetic.ymin, val);
  	return this;
  }
  
  public Aes yintercept(String val){
  	this.a(Aesthetic.yintercept, val);
  	return this;
  }
  
  public Aes z(String val){
  	this.a(Aesthetic.z, val);
  	return this;
  }
  
  public Set<Aesthetic> getSetAesthetics(){
  	return mapping.keySet();
  }

  public String getVariable(Aesthetic aes) {
    Preconditions.checkNotNull(aes);
    String variable = mapping.get(aes);
//    Preconditions.checkNotNull(variable,
//        "GgAesthetics object does not have value for "+aes.name()); TODO I think this shouldl be caught later on, null here just means that it isnt set
    return variable;
  }

  public enum Aesthetic {
    adj, alpha, angle, bg, cex, col, color, colour, fg, fill, group,
    height, hjust, label, linetype, lower, lty, lwd, max, middle, min,
    order, pch, radius, sample, shape, size, srt, upper, vjust, weight,
    width, x, xend, xmax, xmin, xintercept, y, yend, ymax, ymin,
    yintercept, z;
  }
}
