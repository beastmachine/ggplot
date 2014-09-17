package org.beastmachine.dataframe;

import gnu.trove.list.array.TByteArrayList;
import gnu.trove.list.array.TDoubleArrayList;
import gnu.trove.list.array.TFloatArrayList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.list.array.TLongArrayList;

import java.util.HashMap;
import java.util.List;

import javax.xml.ws.Holder;

import com.google.common.base.Preconditions;

/**
 * 
 * @author wheaton5
 *
 */
public class DataFrame {

	private int nRows;
	private HashMap<String, Column> columnsByName;
	
	/**
	 * DataFrame object that mimics R's DataFrame data structure
	 * at least for the purposes of plotting a ggplot style plot
	 */
	public DataFrame(){
		nRows = 0;
		columnsByName = new HashMap<String, Column>();
	}
	
	/**
	 * Gets the column with variable name var
	 * 
	 * @param key
	 * @return
	 */
	public Column get(String var) {
	  return columnsByName.get(var);
  }
	
	/**
	 * Checks if this DataFrame has a column of name var
	 * 
	 * @param var
	 * @return
	 */
	public boolean hasColumn(String var) {
	  return columnsByName.containsKey(var);
  }

	/**
	 * Adds a column to the DataFrame and returns that modified DataFrame
	 * for easy in-line column addition like new DataFrame().c("name",array1).c("name2",array2)
	 * 
	 * Takes a column name and 1 or more Strings or an array of Strings for
	 * the column data and returns the same DataFrame modified
	 * 
	 * This column will be treated as a factor because of its type
	 * 
	 * @param colName
	 * @param colData
	 * @return
	 */
	public DataFrame c(String colName, String ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colName, colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	/**
	 * Adds a column to the DataFrame and returns that modified DataFrame
	 * for easy in-line column addition like new DataFrame().c("name",array1).c("name2",array2)
	 * 
	 * Takes a column name and a list of Strings for
	 * the column data and returns the same DataFrame modified
	 * 
	 * This column will be treated as a factor because of its type
	 * 
	 * @param colName
	 * @param colData
	 * @return
	 */
	public DataFrame c(String colName, List<String> colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colName, colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	/**
	 * Adds a column to the DataFrame and returns that modified DataFrame
	 * for easy in-line column addition like new DataFrame().c("name",array1).c("name2",array2)
	 * 
	 * Takes a column name and one or more ints or an array of ints for
	 * the column data and returns the same DataFrame modified
	 * 
	 * This column will be treated as a numeric because of its type
	 * 
	 * @param colName
	 * @param colData
	 * @return
	 */
	public DataFrame c(String colName, int ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colName, colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	/**
	 * Adds a column to the DataFrame and returns that modified DataFrame
	 * for easy in-line column addition like new DataFrame().c("name",array1).c("name2",array2)
	 * 
	 * Takes a column name and one or more Integers or an array of Integers for
	 * the column data and returns the same DataFrame modified
	 * 
	 * This column will be treated as a numeric because of its type
	 * 
	 * @param colName
	 * @param colData
	 * @return 
	 */
	public DataFrame c(String colName, Integer ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colName, colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	/**
	 * Adds a column to the DataFrame and returns that modified DataFrame
	 * for easy in-line column addition like new DataFrame().c("name",array1).c("name2",array2)
	 * 
	 * Takes a column name and a trove TIntArrayList of ints for
	 * the column data and returns the same DataFrame modified
	 * 
	 * This column will be treated as a numeric because of its type
	 * 
	 * @param colName
	 * @param colData
	 * @return 
	 */
	public DataFrame c(String colName, TIntArrayList colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colName, colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	/**
	 * Adds a column to the DataFrame and returns that modified DataFrame
	 * for easy in-line column addition like new DataFrame().c("name",array1).c("name2",array2)
	 * 
	 * Takes a column name and one or more bytes or an array of bytes for
	 * the column data and returns the same DataFrame modified
	 * 
	 * This column will be treated as a numeric because of its type
	 * 
	 * @param colName
	 * @param colData
	 * @return 
	 */
	public DataFrame c(String colName, byte ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colName, colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	/**
	 * Adds a column to the DataFrame and returns that modified DataFrame
	 * for easy in-line column addition like new DataFrame().c("name",array1).c("name2",array2)
	 * 
	 * Takes a column name and one or more Bytes or an array of Bytes for
	 * the column data and returns the same DataFrame modified
	 * 
	 * This column will be treated as a numeric because of its type
	 * 
	 * @param colName
	 * @param colData
	 * @return 
	 */
	public DataFrame c(String colName, Byte ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colName, colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	/**
	 * Adds a column to the DataFrame and returns that modified DataFrame
	 * for easy in-line column addition like new DataFrame().c("name",array1).c("name2",array2)
	 * 
	 * Takes a column name and a trove TByteArrayList of bytes for
	 * the column data and returns the same DataFrame modified
	 * 
	 * This column will be treated as a numeric because of its type
	 * 
	 * @param colName
	 * @param colData
	 * @return 
	 */
	public DataFrame c(String colName, TByteArrayList colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colName, colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	
	/**
	 * Adds a column to the DataFrame and returns that modified DataFrame
	 * for easy in-line column addition like new DataFrame().c("name",array1).c("name2",array2)
	 * 
	 * Takes a column name and one or more floats or an array of floats
	 * the column data and returns the same DataFrame modified
	 * 
	 * This column will be treated as a numeric because of its type
	 * 
	 * @param colName
	 * @param colData
	 * @return 
	 */
	public DataFrame c(String colName, float ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colName, colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	/**
	 * Adds a column to the DataFrame and returns that modified DataFrame
	 * for easy in-line column addition like new DataFrame().c("name",array1).c("name2",array2)
	 * 
	 * Takes a column name and one or more Floats or an array of Floats
	 * the column data and returns the same DataFrame modified
	 * 
	 * This column will be treated as a numeric because of its type
	 * 
	 * @param colName
	 * @param colData
	 * @return 
	 */
	public DataFrame c(String colName, Float ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colName, colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	/**
	 * Adds a column to the DataFrame and returns that modified DataFrame
	 * for easy in-line column addition like new DataFrame().c("name",array1).c("name2",array2)
	 * 
	 * Takes a column name and a trove TFloatArrayList of floats for
	 * the column data and returns the same DataFrame modified
	 * 
	 * This column will be treated as a numeric because of its type
	 * 
	 * @param colName
	 * @param colData
	 * @return 
	 */
	public DataFrame c(String colName, TFloatArrayList colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colName, colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	/**
	 * Adds a column to the DataFrame and returns that modified DataFrame
	 * for easy in-line column addition like new DataFrame().c("name",array1).c("name2",array2)
	 * 
	 * Takes a column name and one or more doubles or an array of doubles for
	 * the column data and returns the same DataFrame modified
	 * 
	 * This column will be treated as a numeric because of its type
	 * 
	 * @param colName
	 * @param colData
	 * @return 
	 */
	public DataFrame c(String colName, double ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colName, colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	/**
	 * Adds a column to the DataFrame and returns that modified DataFrame
	 * for easy in-line column addition like new DataFrame().c("name",array1).c("name2",array2)
	 * 
	 * Takes a column name and one or more Doubles or an array of Doubles for
	 * the column data and returns the same DataFrame modified
	 * 
	 * This column will be treated as a numeric because of its type
	 * 
	 * @param colName
	 * @param colData
	 * @return 
	 */
	public DataFrame c(String colName, Double ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colName, colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	/**
	 * Adds a column to the DataFrame and returns that modified DataFrame
	 * for easy in-line column addition like new DataFrame().c("name",array1).c("name2",array2)
	 * 
	 * Takes a column name and a trove TDoubleArrayList of doubles for
	 * the column data and returns the same DataFrame modified
	 * 
	 * This column will be treated as a numeric because of its type
	 * 
	 * @param colName
	 * @param colData
	 * @return 
	 */
	public DataFrame c(String colName, TDoubleArrayList colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colName, colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	/**
	 * Adds a column to the DataFrame and returns that modified DataFrame
	 * for easy in-line column addition like new DataFrame().c("name",array1).c("name2",array2)
	 * 
	 * Takes a column name and one or more longs or an array of longs for
	 * the column data and returns the same DataFrame modified
	 * 
	 * This column will be treated as a numeric because of its type
	 * 
	 * @param colName
	 * @param colData
	 * @return 
	 */
	public DataFrame c(String colName, long ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colName, colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	/**
	 * Adds a column to the DataFrame and returns that modified DataFrame
	 * for easy in-line column addition like new DataFrame().c("name",array1).c("name2",array2)
	 * 
	 * Takes a column name and one or more Longs or an array of Longs for
	 * the column data and returns the same DataFrame modified
	 * 
	 * This column will be treated as a numeric because of its type
	 * 
	 * @param colName
	 * @param colData
	 * @return 
	 */
	public DataFrame c(String colName, Long ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colName, colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	/**
	 * Adds a column to the DataFrame and returns that modified DataFrame
	 * for easy in-line column addition like new DataFrame().c("name",array1).c("name2",array2)
	 * 
	 * Takes a column name and a trove TLongArrayList of longs for
	 * the column data and returns the same DataFrame modified
	 * 
	 * This column will be treated as a numeric because of its type
	 * 
	 * @param colName
	 * @param colData
	 * @return 
	 */
	public DataFrame c(String colName, TLongArrayList colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colName, colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	/**
	 * Adds a column to the DataFrame and returns that modified DataFrame
	 * for easy in-line column addition like new DataFrame().c("name",array1).c("name2",array2)
	 * 
	 * Takes a column name and one or more booleans or an array of booleans for
	 * the column data and returns the same DataFrame modified
	 * 
	 * This column will be treated as a factor because of its type
	 * 
	 * @param colName
	 * @param colData
	 * @return 
	 */
	public DataFrame c(String colName, boolean ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colName, colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}
	
	/**
	 * Adds a column to the DataFrame and returns that modified DataFrame
	 * for easy in-line column addition like new DataFrame().c("name",array1).c("name2",array2)
	 * 
	 * Takes a column name and one or more Booleans or an array of Booleans for
	 * the column data and returns the same DataFrame modified
	 * 
	 * This column will be treated as a factor because of its type
	 * 
	 * @param colName
	 * @param colData
	 * @return 
	 */
	public DataFrame c(String colName, Boolean ... colData){
		Holder<Integer> rowHolder = new Holder<Integer>(nRows);
		Column col = Column.getInstance(colName, colData, rowHolder);
		columnsByName.put(colName, col);
		return this;
	}

	public DataFrame c(String string, Column column) {
		int rows = column.nRows();
		if(nRows == 0){
			nRows = rows;
		}
		Preconditions.checkState(nRows == rows, 
				"Every column of dataframe must have same number rows, column ", column.getName(), " ",column.nRows()," != ", nRows);
		Preconditions.checkState(string.equals(column.getName()),"when adding a column to a dataframe directly as a column object ",
				"the names must be the same ", string," is not he same as ",column.getName());
		columnsByName.put(string, column);
		return this;
  }




	
}
