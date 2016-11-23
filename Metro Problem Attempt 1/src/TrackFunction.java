import java.io.*;
public class TrackFunction {
	private Track[] tr;
	int answer;
	public TrackFunction() throws IOException
	{
		answer = collectInput();
		System.out.println(String.format("The number of spaces available for lamps in this grid of tracks is %d spaces",answer));
	}
	private int runFunction(int row, int cols, int iterations) throws IOException
	{
		int results = row * cols;
		if(iterations == 0)
			return results;
		for(int i = 0; i < row; i++)
		{
			results = results - tr[i].getSpaceOccupations();
		}
		if(results <= 0)
			return 0;
		return results;
	}
	private int collectInput () throws IOException
	{
		BufferedReader bReader = new BufferedReader(
		new InputStreamReader(System.in));
		StreamTokenizer stokens = new StreamTokenizer(bReader);
		stokens.eolIsSignificant(true);
		stokens.parseNumbers();
		int somVal,getToken,height,iter,width,currentRow;
		height = iter = width = -1;
		getToken = stokens.nextToken();
		while(getToken != StreamTokenizer.TT_EOL)
		{
			if(getToken == StreamTokenizer.TT_NUMBER)
			{
				somVal = (int) stokens.nval;
				if(height == -1 && somVal > 0)
					height = somVal;
				else if(height > 0 && width == -1 && somVal > 0)
					width = somVal;
				else if(height > 0 && width > 0 && iter == -1 && somVal >= 0)
					iter = somVal;
			}
			getToken = stokens.nextToken();
		}
		while(height == -1 || width == -1 || iter == -1)
		{
			if(height == -1)
				height = 2;
			if(width == -1)
				width = 2;
			if(iter == -1)
				iter = 2;
		}
		tr = new Track[height];
		for(int i = 0; i < height; i++)
		{
			tr[i] = new Track(i + 1, width);
		}
		if(iter == 0)
		{
			bReader.close();
			return runFunction(height,width,0);
		}
		TrackRow[] tRows = new TrackRow[iter];
		for (int i = 0; i < tRows.length; i++)
		{
			tRows[i] = new TrackRow();
		}
		for(int i = 0; i < iter; i++)
		{
			getToken = stokens.nextToken();
			int[] dimensions = {0,0,0};
			int start = 0;
			while(getToken != StreamTokenizer.TT_EOL )
			{
				if(getToken == StreamTokenizer.TT_NUMBER)
				{
					somVal = (int) stokens.nval;
					dimensions[start] = somVal;
					start++;
				}
				getToken = stokens.nextToken();
			}
			tRows[i].setDimensions(dimensions,3);
			currentRow = tRows[i].getRow() - 1;
			if( currentRow >= 0 && currentRow < height)
				tr[currentRow].add(tRows[i]);
		}
		bReader.close();
		return runFunction(height,width,iter);
	}
};
