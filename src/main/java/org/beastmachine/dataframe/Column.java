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
 * 
 * 
 * @author wheaton5
 *
 */
public abstract class Column {

	protected boolean isNumeric;
	protected boolean isFactor;
	protected String colName;
	
	public boolean isNumeric(){
		return isNumeric;
	}
	
	public boolean isFactor(){
		return isFactor;
	}
	
	public String getName(){
		return colName;
	}
	
	public abstract double getValue(int index);
	public abstract String getLabel(int index);
	public abstract int getFactorValue(int index);

	
	public static Column getInstance(String colName, int[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnPInt(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(String colName, Integer[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnOInt(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(String colName, TIntArrayList data,
			Holder<Integer> rowHolder) {
		int rows = data.size();
		Column toReturn = new ColumnLInt(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(String colName, short[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnPShort(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(String colName, Short[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnOShort(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(String colName, long[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnPLong(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(String colName, Long[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnOLong(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(String colName, TLongArrayList data,
			Holder<Integer> rowHolder) {
		int rows = data.size();
		Column toReturn = new ColumnLLong(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(String colName, float[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnPFloat(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(String colName, Float[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnOFloat(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(String colName, TFloatArrayList data,
			Holder<Integer> rowHolder) {
		int rows = data.size();
		Column toReturn = new ColumnLFloat(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(String colName, double[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnPDouble(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(String colName, Double[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnODouble(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(String colName, TDoubleArrayList data,
			Holder<Integer> rowHolder) {
		int rows = data.size();
		Column toReturn = new ColumnLDouble(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(String colName, String[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnString(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(String colName, List<String> data, Holder<Integer> rowHolder){
		int rows = data.size();
		Column toReturn = new ColumnLString(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(String colName, boolean[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnPBoolean(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(String colName, Boolean[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnOBoolean(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(String colName, byte[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnPByte(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
	public static Column getInstance(String colName, Byte[] data, Holder<Integer> rowHolder){
		int rows = data.length;
		Column toReturn = new ColumnOByte(colName, data);
		return init(toReturn, rows, rowHolder);
	}
	
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

		public ColumnOInt(String colName, Integer[] data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
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

		public ColumnPShort(String colName, short[] data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
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

		public ColumnOShort(String colName, Short[] data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
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

		public ColumnPLong(String colName, long[] data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
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

		public ColumnOLong(String colName, Long[] data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
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

		public ColumnPFloat(String colName, float[] data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
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

		public ColumnOFloat(String colName, Float[] data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
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

		public ColumnPDouble(String colName, double[] data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
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

		public ColumnODouble(String colName, Double[] data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
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
			return (data[index]?"True":"False");
		}

		@Override
		public int getFactorValue(int index) {
			return (data[index]?1:0);
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

		public ColumnPByte(String colName, byte[] data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
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

		public ColumnOByte(String colName, Byte[] data) {
			isNumeric = true;
			this.data = data;
			this.colName = colName;
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
			if(data.get(index) == null) return "null";
			return data.get(index);
		}

		@Override
		public int getFactorValue(int index) {
			return mapping.get(getLabel(index));
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

	}


	
}
