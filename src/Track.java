//Object representation of rows by which tracks are placed on in the proble,
public class Track {
	private int row;
	private int mTracks;
	private LinkedLister linked;
	private boolean isEmpty;
	private int currentSpaceOccupations;
	private int totalTracks;
	public Track(int rowNumber, int cols)//Can only be initialized in this manor 
	{
		row = rowNumber; //The linked list will be associated with a certain row number
		mTracks = cols; //The maximum endCol() value a track can hold
		linked = new LinkedLister(); //The linked list to sort and manage tracks in the row
		isEmpty = true;
		currentSpaceOccupations = 0; //Total space occcupied by the tracks in the row, intersections DO NOT double count
		totalTracks = 0; //Total number of tracks entered into row, intersecting or not
	}
	public void add(TrackRow tRow)
	{
		if(!verify(tRow))//Invalid Trow Data is discarded and not added to the row
			return;
		if(isEmpty || linked.getLength() == 0) //In case of empty linkedlist
		{
			linked.add(tRow.getStartCol(),tRow.getEndCol(), 1); //set it as the head node
			isEmpty = false;
		}
		else
		{
			linked.checkStart(tRow.getStartCol(),tRow.getEndCol()); //Check its start and end columns, and place
			//it where it seems fit, if it spans any new columns
		}
		totalTracks++;
	}
	public int getTotalTracks()
	{
		return totalTracks; //See how many valid tracks a user has entered into the row, does not filter out intersections
	}
	public int getSpaceOccupations()
	{
		if(linked.getLength() == 0)
		{
			isEmpty = true; //If list empty, immediately return 0
			return 0;
		}
		currentSpaceOccupations = linked.getSpaceOccupation();//Calculate from linked list and return
		return currentSpaceOccupations;
	}
	public int getUniqueTracks()
	{
		return linked.getLength(); //Tracks in the row that dont have any intersections, unless column width is very high
		//This value will likely get lower as more tracks are added
	}
	public String toString() //Once again not needed just for pretty and informative llu
	{
		String result = String.format("This data is for row %d in the grid of tracks!\n\n", row);
		if(linked.getLength() == 0)
		{
			result += "This row has no tracks and is therefore empty\n";
			result += String.format("Since empty, all %d spaces in row %d are available to place lamposts.\n\n", mTracks,row);
			return result;
		}
		result += String.format("There are total of %d tracks in row %d\n", totalTracks,row);
		result += String.format("This row has %d UNIQUE tracks in it!\n", linked.getLength());
		result += "Unique means none of the tracks the same space or start or end right next to each other\n";
		result += "(Basically having a starting column one space away from another track's ending column)\n\n";
		result += String.format("Out of this row's %d available spaces\n%d of those spaces are occupied by the %d tracks\n" +
							"Meaning there are only there are only %d spaces in row %d are available to place lamposts\n\n", mTracks,
							getSpaceOccupations(),totalTracks,mTracks - getSpaceOccupations(),row);
		return result;
	}
	private boolean verify(TrackRow tRow)
	{ //Since input comes from console try to filter common invalid cases i.e. endcol > mTracks;startCol < 1 ; or the rows do not match at a;;
		// Another if statement to check is if EndCol >= startCol but that can be fixed with the statement 
		//tRow.setDimensions([tRow.getRow(), tRow.getEndCol,tRow.getStartCol())
		if(tRow.getStartCol() < 1 || tRow.getEndCol() > mTracks || tRow.getEndCol() < tRow.getStartCol() || tRow.getRow() != row)
			return false;
		return true;
	}
}
