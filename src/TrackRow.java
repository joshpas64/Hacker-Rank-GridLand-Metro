
public class TrackRow {
	private int rowNumber;
	private int startCol;
	private int endCol;
	public TrackRow()
	{
		rowNumber = startCol = endCol = 0;
	}
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
	public String toString()
	{
		String result = "";
		result = String.format("This track's row number is row number: %d\n", rowNumber);
		result = result + String.format("This track's starting column is column number: %d\n",startCol);
		result = result + String.format("This track's ending column is column number: %d\n", endCol);
		return result;
	}
}
