
public class Track {
	private int row;
	private int mTracks;
	private LinkedLister linked;
	private boolean isEmpty;
	private int currentSpaceOccupations;
	private int totalTracks;
	public Track(int rowNumber, int cols)
	{
		row = rowNumber;
		mTracks = cols;
		linked = new LinkedLister();
		isEmpty = true;
		currentSpaceOccupations = 0;
		totalTracks = 0;
	}
	public void add(TrackRow tRow)
	{
		if(!verify(tRow))
			return;
		if(isEmpty || linked.getLength() == 0)
		{
			linked.add(tRow.getStartCol(),tRow.getEndCol(), 1);
			isEmpty = false;
		}
		else
		{
			linked.checkStart(tRow.getStartCol(),tRow.getEndCol());
		}
		totalTracks++;
	}
	public int getTotalTracks()
	{
		return totalTracks;
	}
	public int getSpaceOccupations()
	{
		if(linked.getLength() == 0)
		{
			isEmpty = true;
			return 0;
		}
		currentSpaceOccupations = linked.getSpaceOccupation();
		return currentSpaceOccupations;
	}
	public int getUniqueTracks()
	{
		return linked.getLength();
	}
	public String toString()
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
	{
		if(tRow.getStartCol() < 1 || tRow.getEndCol() > mTracks || tRow.getEndCol() < tRow.getStartCol() || tRow.getRow() != row)
			return false;
		return true;
	}
}
