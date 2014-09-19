package org.beastmachine.dataframe;

import gnu.trove.list.array.TByteArrayList;
import gnu.trove.list.array.TDoubleArrayList;
import gnu.trove.list.array.TFloatArrayList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.list.array.TLongArrayList;

import java.util.HashMap;
import java.util.List;

import javax.xml.ws.Holder;

import static com.google.common.base.Preconditions.*;


/**
 * The column class models a column of a data frame
 * or the c() type vector in R.
 * 
 * @author wheaton5
 *
 */
public abstract class Column {

	protected boolean isNumeric;
	protected boolean isFactor;
	protected String colName;
	
	/**
	 * A column can be either numeric or a factor. You can call 
	 * getValue(i) on a numeric column to get the double representation
	 * of the value of the i'th value of that column.
	 * 
	 * If a column is initialized with any of the following it will be numeric
	 * unless asFactor(column) TODO not yet implemented has been used on it.
	 * byte[]
	 * Byte[]
	 * TByteArrayList
	 * 
	 * short[]
	 * Short[]
	 * TShortArrayList
	 * 
	 * int[]
	 * Integer[]
	 * TIntArrayList
	 * 
	 * long[]
	 * Long[]
	 * TLongArrayList
	 * 
	 * float[]
	 * Float[]
	 * TFloatArrayList
	 * 
	 * double[]
	 * Double[]
	 * TDoubleArrayList
	 * 
	 *  
	 * @return
	 */
	public boolean isNumeric(){
		return isNumeric;
	}
	
	/**
	 * A column can either be numeric or a factor. By factor, we mean
	 * similar to the R type "factor". A factor has "labels" and "values"
	 * The values are integer values that index into a labels array.
	 * The values are assigned according to the order of unique labels
	 * in the column.
	 * 
	 * You cannot call getValue on a factor. Use getFactorValue.
	 * 
	 * If a column is initialized with any of the following it will be a factor
	 * String[]
	 * ArrayList<String>
	 * boolean[]
	 * Boolean[]
	 * TODO not implemented BitSet
	 * 
	 * @return
	 */
	public boolean isFactor(){
		return isFactor;
	}
	
	/**
	 * Returns the name of the column.
	 * 
	 * @return
	 */
	public String getName(){
		return colName;
	}
	
	/**
	 * Gets the value of the index'th value of a numeric column.
	 * Only use on numeric column (use isNumeric())
	 * 
	 * @param index
	 * @return
	 */
	public abstract double getValue(int index);
	
	/**
	 * Gets number of rows in this column
	 * 
	 * @return
	 */
	public abstract int nRows();
	
	/**
	 * Gets number of rows in this column
	 * 
	 * @return
	 */
	public int length(){
		return nRows();
	}
	
	/**
	 * Gets number of rows in this column
	 * 
	 * @return
	 */
	public int size(){
		return nRows();
	}
	
	public double min(){
		checkState(isNumeric, "Currently min only works on numeric columns, this may change TODO");
		double min = Double.MAX_VALUE;
		for(int ii = 0; ii < length(); ii++){
			min = Math.min(min, getValue(ii));
		}
		return min;
	}
	
	public double max(){
		checkState(isNumeric, "Currently max only works on numeric columns, this may change TODO");
		double max = Double.NEGATIVE_INFINITY;
		for(int ii = 0; ii < length(); ii++){
			max = Math.max(max, getValue(ii));
		}
		return max;
	}

	/**
	 * Gets the label of the index'th value of a factor column.
	 * Only use on factor columns (use isFactor())
	 * @param index
	 * @return
	 */
	public abstract String getLabel(int index);
	
	/**
	 * Gets the integer factor value of the index'th value of a factor column.
	 * This value is determined by the order of unique labels in the column except
	 * for booleans in which case the value of false is 0 and true is 1 and null is 2.
	 * 
	 * @param index
	 * @return
	 */
	public abstract int getFactorValue(int index);

	/**
	 * Constructs a numeric column from an int[]
	 * @param colName
	 * @param data
	 * @param rowHolder
	 * @return
	 */
	public static Column getInstance(String colName, int[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnPInt(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	/**
	 * Constructs a numeric column from an Integer[]
	 * 
	 * @param colName
	 * @param data
	 * @param rowHolder
	 * @return
	 */
	public static Column getInstance(String colName, Integer[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnOInt(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	/**
	 * Constructs a numeric column from a TIntArrayList
	 * 
	 * @param colName
	 * @param data
	 * @param rowHolder
	 * @return
	 */
	public static Column getInstance(String colName, TIntArrayList data,
			Holder<Integer> rowHolder) {
		int rows = data.size();
		Column toReturn = new ColumnLInt(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	/**
	 * Constructs a numeric column from a short[]
	 * 
	 * @param colName
	 * @param data
	 * @param rowHolder
	 * @return
	 */
	public static Column getInstance(String colName, short[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnPShort(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	/**
	 * Constructs a numeric column from a Short[]
	 * 
	 * @param colName
	 * @param data
	 * @param rowHolder
	 * @return
	 */
	public static Column getInstance(String colName, Short[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnOShort(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	/**
	 * Constructs a numeric column from a long[]
	 * 
	 * @param colName
	 * @param data
	 * @param rowHolder
	 * @return
	 */
	public static Column getInstance(String colName, long[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnPLong(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	/**
	 * Constructs a numeric column from a Long[]
	 * 
	 * @param colName
	 * @param data
	 * @param rowHolder
	 * @return
	 */
	public static Column getInstance(String colName, Long[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnOLong(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	/**
	 * Constructs a numeric column from a TLongArrayList
	 * 
	 * @param colName
	 * @param data
	 * @param rowHolder
	 * @return
	 */
	public static Column getInstance(String colName, TLongArrayList data,
			Holder<Integer> rowHolder) {
		int rows = data.size();
		Column toReturn = new ColumnLLong(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	/**
	 * Constructs a numeric column from a float[]
	 * 
	 * @param colName
	 * @param data
	 * @param rowHolder
	 * @return
	 */
	public static Column getInstance(String colName, float[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnPFloat(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	/**
	 * Constructs a numeric column from a Float[]
	 * 
	 * @param colName
	 * @param data
	 * @param rowHolder
	 * @return
	 */
	public static Column getInstance(String colName, Float[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnOFloat(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	/**
	 * Constructs a numeric column from a TFloatArrayList
	 * 
	 * @param colName
	 * @param data
	 * @param rowHolder
	 * @return
	 */
	public static Column getInstance(String colName, TFloatArrayList data,
			Holder<Integer> rowHolder) {
		int rows = data.size();
		Column toReturn = new ColumnLFloat(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	/**
	 * Constructs a numeric column from a double[]
	 * 
	 * @param colName
	 * @param data
	 * @param rowHolder
	 * @return
	 */
	public static Column getInstance(String colName, double[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnPDouble(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	/**
	 * Constructs a numeric column from a Double[]
	 * 
	 * @param colName
	 * @param data
	 * @param rowHolder
	 * @return
	 */
	public static Column getInstance(String colName, Double[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnODouble(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	/**
	 * Constructs a numeric column from a TDoubleArrayList
	 * 
	 * @param colName
	 * @param data
	 * @param rowHolder
	 * @return
	 */
	public static Column getInstance(String colName, TDoubleArrayList data,
			Holder<Integer> rowHolder) {
		int rows = data.size();
		Column toReturn = new ColumnLDouble(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	/**
	 * Constructs a factor column from a String[]
	 * 
	 * @param colName
	 * @param data
	 * @param rowHolder
	 * @return
	 */
	public static Column getInstance(String colName, String[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnString(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	/**
	 * Constructs a factor column from a List<String>
	 * 
	 * @param colName
	 * @param data
	 * @param rowHolder
	 * @return
	 */
	public static Column getInstance(String colName, List<String> data, Holder<Integer> rowHolder){
		int rows = data.size();
		Column toReturn = new ColumnLString(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	/**
	 * Constructs a factor column from a boolean[]
	 * 
	 * @param colName
	 * @param data
	 * @param rowHolder
	 * @return
	 */
	public static Column getInstance(String colName, boolean[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnPBoolean(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	/**
	 * Constructs a factor column from a Boolean[]
	 * 
	 * @param colName
	 * @param data
	 * @param rowHolder
	 * @return
	 */
	public static Column getInstance(String colName, Boolean[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnOBoolean(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	/**
	 * Constructs a numeric column from a byte[]
	 * 
	 * @param colName
	 * @param data
	 * @param rowHolder
	 * @return
	 */
	public static Column getInstance(String colName, byte[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnPByte(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	/**
	 * Constructs a numeric column from a Byte[]
	 * 
	 * @param colName
	 * @param data
	 * @param rowHolder
	 * @return
	 */
	public static Column getInstance(String colName, Byte[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnOByte(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	/**
	 * Constructs a numeric column from a TByteArrayList
	 * 
	 * @param colName
	 * @param data
	 * @param rowHolder
	 * @return
	 */
	public static Column getInstance(String colName, TByteArrayList data,
			Holder<Integer> rowHolder) {
		int rows = data.size();
		Column toReturn = new ColumnLByte(colName, data);
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

		public ColumnPInt(String colName, int[] data){
			isNumeric = true;
			this.data = data;
			this.colName = colName;
		}

		@Override
		public double getValue(int index) {
			checkArgument(index < data.length, 
					"attempted to access data out of range ", 
					index, " >= ",data.length);
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

		@Override
    public int nRows() {
	    return data.length;
    }

	}
	
	private static class ColumnOInt extends Column{

		private Integer[] data;

		public ColumnOInt(String colName, Integer[] data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
		}

		@Override
		public double getValue(int index) {
			checkArgument(index < data.length, 
					"attempted to access data out of range ", 
					index, " >= ",data.length);
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

		@Override
    public int nRows() {
	    return data.length;
    }
		
	}
	
	private static class ColumnPShort extends Column{

		private short[] data;

		public ColumnPShort(String colName, short[] data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
		}

		@Override
		public double getValue(int index) {
			checkArgument(index < data.length, 
					"attempted to access data out of range ", 
					index, " >= ",data.length);
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

		@Override
    public int nRows() {
	    return data.length;
    }
		
	}
	
	private static class ColumnOShort extends Column{

		private Short[] data;

		public ColumnOShort(String colName, Short[] data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
		}

		@Override
		public double getValue(int index) {
			checkArgument(index < data.length, 
					"attempted to access data out of range ", 
					index, " >= ",data.length);
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

		@Override
    public int nRows() {
	    return data.length;
    }
		
	}
	
	private static class ColumnPLong extends Column {

		private long[] data;

		public ColumnPLong(String colName, long[] data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
		}

		@Override
		public double getValue(int index) {
			checkArgument(index < data.length, 
					"attempted to access data out of range ", 
					index, " >= ",data.length);
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

		@Override
    public int nRows() {
	    return data.length;
    }

	}
	
	private static class ColumnOLong extends Column {

		private Long[] data;

		public ColumnOLong(String colName, Long[] data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
		}

		@Override
		public double getValue(int index) {
			checkArgument(index < data.length, 
					"attempted to access data out of range ", 
					index, " >= ",data.length);
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

		@Override
    public int nRows() {
	    return data.length;
    }

	}

	private static class ColumnPFloat extends Column {

		private float[] data;

		public ColumnPFloat(String colName, float[] data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
		}

		@Override
		public double getValue(int index) {
			checkArgument(index < data.length, 
					"attempted to access data out of range ", 
					index, " >= ",data.length);
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

		@Override
    public int nRows() {
	    return data.length;
    }

	}

	private static class ColumnOFloat extends Column {

		private Float[] data;

		public ColumnOFloat(String colName, Float[] data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
		}

		@Override
		public double getValue(int index) {
			checkArgument(index < data.length, 
					"attempted to access data out of range ", 
					index, " >= ",data.length);
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

		@Override
    public int nRows() {
	    return data.length;
    }

	}
	
	private static class ColumnPDouble extends Column {

		private double[] data;

		public ColumnPDouble(String colName, double[] data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
		}

		@Override
		public double getValue(int index) {
			checkArgument(index < data.length, 
					"attempted to access data out of range ", 
					index, " >= ",data.length);
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

		@Override
    public int nRows() {
	    return data.length;
    }

	}
	private static class ColumnODouble extends Column {

		private Double[] data;

		public ColumnODouble(String colName, Double[] data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
		}

		@Override
		public double getValue(int index) {
			checkArgument(index < data.length, 
					"attempted to access data out of range ", 
					index, " >= ",data.length);
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

		@Override
    public int nRows() {
	    return data.length;
    }

	}
	
	private static class ColumnString extends Column {
		private HashMap<String, Integer> mapping;
		private String[] data;
		public ColumnString(String colName, String[] data) {
			isFactor = true;
			this.colName = colName;
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
			checkArgument(index < data.length, 
					"attempted to access data out of range ", 
					index, " >= ",data.length);
			if(data[index] == null) return "null";
			return data[index];
		}

		@Override
		public int getFactorValue(int index) {
			return mapping.get(getLabel(index));
		}

		@Override
    public int nRows() {
	    return data.length;
    }

	}
	
	private static class ColumnPBoolean extends Column {

		private boolean[] data;

		public ColumnPBoolean(String colName, boolean[] data) {
			isFactor = true;
			this.data = data;
			this.colName = colName;
		}

		@Override
		public double getValue(int index) {
			checkState(false, "cannot call getValue on Factor");
			return 0;
		}

		@Override
		public String getLabel(int index) {
			checkArgument(index < data.length, 
					"attempted to access data out of range ", 
					index, " >= ",data.length);
			return (data[index]?"True":"False");
		}

		@Override
		public int getFactorValue(int index) {
			checkArgument(index < data.length, 
					"attempted to access data out of range ", 
					index, " >= ",data.length);
			return (data[index]?1:0);
		}

		@Override
    public int nRows() {
	    return data.length;
    }

	}
	
	private static class ColumnOBoolean extends Column {

		private Boolean[] data;

		public ColumnOBoolean(String colName, Boolean[] data) {
			isFactor = true;
			this.data = data;
			this.colName = colName;
		}

		@Override
		public double getValue(int index) {
			checkState(false, "cannot call getValue on Factor");
			return 0;
		}

		@Override
		public String getLabel(int index) {
			checkArgument(index < data.length, 
					"attempted to access data out of range ", 
					index, " >= ",data.length);
			if(data[index] == null) return "null";
			return (data[index]?"True":"False");
		}

		@Override
		public int getFactorValue(int index) {
			checkArgument(index < data.length, 
					"attempted to access data out of range ", 
					index, " >= ",data.length);
			if(data[index] == null) return 2;
			return (data[index]?1:0);
		}

		@Override
    public int nRows() {
	    return data.length;
    }

  }
	
	private static class ColumnPByte extends Column {

		private byte[] data;

		public ColumnPByte(String colName, byte[] data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
		}

		@Override
		public double getValue(int index) {
			checkArgument(index < data.length, 
					"attempted to access data out of range ", 
					index, " >= ",data.length);
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

		@Override
    public int nRows() {
	    return data.length;
    }

  }
	private static class ColumnOByte extends Column {

		private Byte[] data;

		public ColumnOByte(String colName, Byte[] data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
		}

		@Override
		public double getValue(int index) {
			if(data[index] == null) return Double.NaN;
			checkArgument(index < data.length, 
					"attempted to access data out of range ", 
					index, " >= ",data.length);
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

		@Override
    public int nRows() {
	    return data.length;
    }

  }
	private static class ColumnLString extends Column {

		private HashMap<String, Integer> mapping;
		private List<String> data;
		
		public ColumnLString(String colName, List<String> data) {
			isFactor = true;
			this.colName = colName;
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
			checkArgument(index < data.size(), 
					"attempted to access data out of range ", 
					index, " >= ",data.size());
			if(data.get(index) == null) return "null";
			return data.get(index);
		}

		@Override
		public int getFactorValue(int index) {
			return mapping.get(getLabel(index));
		}

		@Override
    public int nRows() {
	    return data.size();
    }

  }
	
	private static class ColumnLInt extends Column {

		private TIntArrayList data;

		public ColumnLInt(String colName, TIntArrayList data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
		}

		@Override
		public double getValue(int index) {
			checkArgument(index < data.size(), 
					"attempted to access data out of range ", 
					index, " >= ",data.size());
			return data.get(index);
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

		@Override
    public int nRows() {
	    return data.size();
    }

  }
	
	private static class ColumnLByte extends Column {

		private TByteArrayList data;

		public ColumnLByte(String colName, TByteArrayList data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
		}

		@Override
		public double getValue(int index) {
			checkArgument(index < data.size(), 
					"attempted to access data out of range ", 
					index, " >= ",data.size());
			return data.get(index);
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

		@Override
    public int nRows() {
	    return data.size();
    }

  }


	private static class ColumnLFloat extends Column {

		private TFloatArrayList data;

		public ColumnLFloat(String colName, TFloatArrayList data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
		}

		@Override
		public double getValue(int index) {
			checkArgument(index < data.size(), 
					"attempted to access data out of range ", 
					index, " >= ",data.size());
			return data.get(index);
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

		@Override
    public int nRows() {
	    return data.size();
    }

  }
	
	private static class ColumnLDouble extends Column {

		private TDoubleArrayList data;

		public ColumnLDouble(String colName, TDoubleArrayList data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
		}

		@Override
		public double getValue(int index) {
			checkArgument(index < data.size(), 
					"attempted to access data out of range ", 
					index, " >= ",data.size());
			return data.get(index);
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

		@Override
    public int nRows() {
	    return data.size();
    }

  }
	
	private static class ColumnLLong extends Column {

		private TLongArrayList data;

		public ColumnLLong(String colName, TLongArrayList data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
		}

		@Override
		public double getValue(int index) {
			checkArgument(index < data.size(), "attempted to access data out of range ", index, " >= ",data.size());
			return data.get(index);
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

		@Override
    public int nRows() {
	    return data.size();
    }

  }

}
