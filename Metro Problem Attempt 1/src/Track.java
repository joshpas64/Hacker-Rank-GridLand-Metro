
public class Track {
	private int row;
	private int mTracks;
	private LinkedLister linked;
	private boolean isEmpty;
	private int currentSpaceOccupations;
	public Track(int rowNumber, int cols)
	{
		row = rowNumber;
		mTracks = cols;
		linked = new LinkedLister();
		isEmpty = true;
		currentSpaceOccupations = 0;
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
	
	private boolean verify(TrackRow tRow)
	{
		if(tRow.getStartCol() < 1 || tRow.getEndCol() > mTracks || tRow.getEndCol() < tRow.getStartCol() || tRow.getRow() != row)
			return false;
		return true;
	}
}
