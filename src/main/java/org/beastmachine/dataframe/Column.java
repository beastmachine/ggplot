package org.beastmachine.dataframe;

import java.util.HashMap;
import java.util.List;

import javax.xml.ws.Holder;

import static com.google.common.base.Preconditions.*;


/**
 * 
 * 
 * @author wheaton5
 *
 */
public abstract class Column {

	protected boolean isNumeric;
	protected boolean isFactor;
	
	public static Column getInstance(int[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnPInt(data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(Integer[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnOInt(data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(short[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnPShort(data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(Short[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnOShort(data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(long[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnPLong(data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(Long[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnOLong(data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(float[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnPFloat(data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(Float[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnOFloat(data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(double[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnPDouble(data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(Double[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnODouble(data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(String[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnString(data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(boolean[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnPBoolean(data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(Boolean[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnOBoolean(data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(byte[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnPByte(data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(Byte[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnOByte(data);
		return init(toReturn, rows, rowHolder);
	}
	
	
	
	private static Column init(Column toReturn, int rows, Holder<Integer> rowHolder) {
		checkArgument(rowHolder.value.intValue() == 0 || 
				rowHolder.value.intValue() == rows, 
				"all columns must have the same length ", rows," != "+rowHolder.value.intValue());
		rowHolder.value = rows;
		return toReturn;
	}

	
	private static class ColumnPInt extends Column{
		int[] data;
		public ColumnPInt(int[] data){
			isNumeric = true;
			this.data = data;
		}

		@Override
		public double getValue(int index) {
			return data[index];
		}

		@Override
		public String getLabel(int index) {
			checkArgument(false,"cannot call getLabel on numeric");
			return null;
		}

		@Override
		public int getFactorValue(int index) {
			checkArgument(false, "cannot call getFactorValue on numeric");
			return 0;
		}
	}
	
	private static class ColumnOInt extends Column{

		private Integer[] data;

		public ColumnOInt(Integer[] colData) {
			data = colData;
		}

		@Override
		public double getValue(int index) {
			if(data[index] == null) return Double.NaN;
			return data[index].intValue();
		}

		@Override
		public String getLabel(int index) {
			checkState(false," cannot call getLabel on numeric");
			return null;
		}

		@Override
		public int getFactorValue(int index) {
			checkState(false, " cannot call getFactorValue on numeric");
			return 0;
		}
		
	}
	
	private static class ColumnPShort extends Column{

		private short[] data;

		public ColumnPShort(short[] colData) {
			data = colData;
		}

		@Override
		public double getValue(int index) {
			return data[index];
		}

		@Override
		public String getLabel(int index) {
			checkState(false," cannot call getLabel on numeric");
			return null;
		}

		@Override
		public int getFactorValue(int index) {
			checkState(false, " cannot call getFactorValue on numeric");
			return 0;
		}
		
	}
	
	private static class ColumnOShort extends Column{

		private Short[] data;

		public ColumnOShort(Short[] colData) {
			data = colData;
		}

		@Override
		public double getValue(int index) {
			if(data[index] == null) return Double.NaN;
			return data[index].doubleValue();
		}

		@Override
		public String getLabel(int index) {
			checkState(false," cannot call getLabel on numeric");
			return null;
		}

		@Override
		public int getFactorValue(int index) {
			checkState(false, " cannot call getFactorValue on numeric");
			return 0;
		}
		
	}
	
	private static class ColumnPLong extends Column {

		private long[] data;

		public ColumnPLong(long[] data) {
			this.data = data;
		}

		@Override
		public double getValue(int index) {
			return data[index];
		}

		@Override
		public String getLabel(int index) {
			checkState(false," cannot call getLabel on numeric");
			return null;
		}

		@Override
		public int getFactorValue(int index) {
			checkState(false, " cannot call getFactorValue on numeric");
			return 0;
		}

	}
	
	private static class ColumnOLong extends Column {

		private Long[] data;

		public ColumnOLong(Long[] data) {
			this.data = data;
		}

		@Override
		public double getValue(int index) {
			if(data[index] == null) return Double.NaN;
			return data[index].doubleValue();
		}

		@Override
		public String getLabel(int index) {
			checkState(false," cannot call getLabel on numeric");
			return null;
		}

		@Override
		public int getFactorValue(int index) {
			checkState(false, " cannot call getFactorValue on numeric");
			return 0;
		}

	}

	private static class ColumnPFloat extends Column {

		private float[] data;

		public ColumnPFloat(float[] data) {
			this.data = data;
		}

		@Override
		public double getValue(int index) {
			return data[index];
		}

		@Override
		public String getLabel(int index) {
			checkState(false," cannot call getLabel on numeric");
			return null;
		}

		@Override
		public int getFactorValue(int index) {
			checkState(false, " cannot call getFactorValue on numeric");
			return 0;
		}

	}

	private static class ColumnOFloat extends Column {

		private Float[] data;

		public ColumnOFloat(Float[] data) {
			this.data = data;
		}

		@Override
		public double getValue(int index) {
			if(data[index] == null) return Double.NaN;
			return data[index].doubleValue();
		}

		@Override
		public String getLabel(int index) {
			checkState(false," cannot call getLabel on numeric");
			return null;
		}

		@Override
		public int getFactorValue(int index) {
			checkState(false, " cannot call getFactorValue on numeric");
			return 0;
		}

	}
	
	private static class ColumnPDouble extends Column {

		private double[] data;

		public ColumnPDouble(double[] data) {
			this.data = data;
		}

		@Override
		public double getValue(int index) {
			return data[index];
		}

		@Override
		public String getLabel(int index) {
			checkState(false," cannot call getLabel on numeric");
			return null;
		}

		@Override
		public int getFactorValue(int index) {
			checkState(false, " cannot call getFactorValue on numeric");
			return 0;
		}

	}
	
	private static class ColumnODouble extends Column {

		private Double[] data;

		public ColumnODouble(Double[] data) {
			this.data = data;
		}

		@Override
		public double getValue(int index) {
			if(data[index] == null) return Double.NaN;
			return 0;
		}

		@Override
		public String getLabel(int index) {
			checkState(false," cannot call getLabel on numeric");
			return null;
		}

		@Override
		public int getFactorValue(int index) {
			checkState(false, " cannot call getFactorValue on numeric");
			return 0;
		}

	}
	
	private static class ColumnString extends Column {
		private HashMap<String, Integer> mapping;
		private String[] data;
		public ColumnString(String[] data) {
			mapping = new HashMap<String, Integer>();
			int soFar = 0;
			for(String s: data){
				if(s == null){
					mapping.put("null", soFar++);
					
					continue;
				}
				checkState(!s.equals("null"), 
						"string \"null\" is a keyword in ggplot java package, cannot be string in a column" );
				if(!mapping.containsKey(s)){
					mapping.put(s, soFar++);
				}
			}
			this.data = data;
		}

		@Override
		public double getValue(int index) {
			checkState(false, "cannot call getValue on Factor");
			return 0;
		}

		@Override
		public String getLabel(int index) {
			if(data[index] == null) return "null";
			return data[index];
		}

		@Override
		public int getFactorValue(int index) {
			return mapping.get(getLabel(index));
		}

	}
	
	private static class ColumnPBoolean extends Column {

		private boolean[] data;

		public ColumnPBoolean(boolean[] data) {
			this.data = data;
		}

		@Override
		public double getValue(int index) {
			checkState(false, "cannot call getValue on Factor");
			return 0;
		}

		@Override
		public String getLabel(int index) {
			return (data[index]?"True":"False");
		}

		@Override
		public int getFactorValue(int index) {
			return (data[index]?1:0);
		}

	}
	
	private static class ColumnOBoolean extends Column {

		private Boolean[] data;

		public ColumnOBoolean(Boolean[] data) {
			this.data = data;
		}

		@Override
		public double getValue(int index) {
			checkState(false, "cannot call getValue on Factor");
			return 0;
		}

		@Override
		public String getLabel(int index) {
			if(data[index] == null) return "null";
			return (data[index]?"True":"False");
		}

		@Override
		public int getFactorValue(int index) {
			if(data[index] == null) return 2;
			return (data[index]?1:0);
		}

	}
	
	private static class ColumnPByte extends Column {

		private byte[] data;

		public ColumnPByte(byte[] data) {
			this.data = data;
		}

		@Override
		public double getValue(int index) {
			return data[index];
		}

		@Override
		public String getLabel(int index) {
			checkState(false," cannot call getLabel on numeric");
			return null;
		}

		@Override
		public int getFactorValue(int index) {
			checkState(false, " cannot call getFactorValue on numeric");
			return 0;
		}

	}
	
	private static class ColumnOByte extends Column {

		private Byte[] data;

		public ColumnOByte(Byte[] data) {
			this.data = data;
		}

		@Override
		public double getValue(int index) {
			if(data[index] == null) return Double.NaN;
			return data[index];
		}

		@Override
		public String getLabel(int index) {
			checkState(false," cannot call getLabel on numeric");
			return null;
		}

		@Override
		public int getFactorValue(int index) {
			checkState(false, " cannot call getFactorValue on numeric");
			return 0;
		}

	}
	
	public boolean isNumeric(){
		return isNumeric;
	}
	
	public boolean isFactor(){
		return isFactor;
	}
	
	public abstract double getValue(int index);
	public abstract String getLabel(int index);
	public abstract int getFactorValue(int index);
	
	
}
