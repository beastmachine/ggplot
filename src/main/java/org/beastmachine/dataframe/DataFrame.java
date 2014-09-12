package org.beastmachine.dataframe;

import gnu.trove.list.array.TByteArrayList;
import gnu.trove.list.array.TDoubleArrayList;
import gnu.trove.list.array.TFloatArrayList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.list.array.TLongArrayList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.ws.Holder;

import com.google.common.base.Preconditions;

public class DataFrame {

	private List<Column> columnList;
	private int nRows;
	private HashMap<String, Column> columnsByName;
	
	public DataFrame(){
		nRows = 0;
		columnList = new ArrayList<Column>();
		columnsByName = new HashMap<String, Column>();
	}

	public DataFrame c(String colName, String ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	public DataFrame c(String colName, List<String> colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	public DataFrame c(String colName, int ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	public DataFrame c(String colName, Integer ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	public DataFrame c(String colName, TIntArrayList colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	public DataFrame c(String colName, byte ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	public DataFrame c(String colName, Byte ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	public DataFrame c(String colName, TByteArrayList colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	
	
	public DataFrame c(String colName, float ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	public DataFrame c(String colName, Float ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	public DataFrame c(String colName, TFloatArrayList colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	public DataFrame c(String colName, double ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	public DataFrame c(String colName, Double ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	public DataFrame c(String colName, TDoubleArrayList colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	public DataFrame c(String colName, long ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	public DataFrame c(String colName, Long ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	public DataFrame c(String colName, TLongArrayList colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	public DataFrame c(String colName, boolean ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	public DataFrame c(String colName, Boolean ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	
}
