// All tracks objects with attributes of row number , starting column, and ending column
// If this was C++ a struct would most likely used in its place
public class TrackRow {
	//Since TrackRow is just a collection of attributes its implementation is basically just properly adding the right
	//getter and setter methods for each attribute
	private int rowNumber;
	private int startCol;
	private int endCol;
	public TrackRow()
	{
		rowNumber = startCol = endCol = 0;
	}
	//Looking back this method could also be overloaded fairly easy without having to deal with arrays
	/*
	public void set(int row, int sCol, int eCol)
	{
		rowNumber = row;
		startCol = sCol;
		endCol = eCol;
	}
	*/
	public void setDimensions(int[] dimensions, int iter)
	{
		for(int i = 0; i < iter; i++)
		{
			switch(i)
			{
			case 0:
				rowNumber = dimensions[0];
				break;
			case 1:
				startCol = dimensions[1];
				break;
			case 2:
				endCol = dimensions[2];
				break;
			default:
			}
		}
	}
	//Getter Methods
	public int getStartCol()
	{
		return startCol;
	}
	public int getEndCol()
	{
		return endCol;
	}
	public int getRow()
	{
		return rowNumber;
	}
	//Not needed for main program but might be hepful for testing
	public String toString()
	{
		String result = "";
		result = String.format("This track's row number is row number: %d\n", rowNumber);
		result = result + String.format("This track's starting column is column number: %d\n",startCol);
		result = result + String.format("This track's ending column is column number: %d\n", endCol);
		return result;
	}
}
