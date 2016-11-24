import java.io.*;
public class TrackFunction {
	private Track[] tr;
	int answer;
	private int height,width,iter;
	private int currentRun;
	public TrackFunction() throws IOException
	{
		currentRun = 0;
		answer = collectInput();
		System.out.println(String.format("The number of spaces available for lamps in this grid of tracks is %d spaces",answer));
		describeGrid();
	}
	private int runFunction(int row, int cols, int iterations) throws IOException
	{
		int results = row * cols;
		currentRun++;
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
		String m = "Welcome to the GridLand Metro Progam by super Novice joshpas! :D\n";
		m += "The first line of input will contain 3 numbers (integers PLEASE!)\n";
		m += "The first number is the number of rows in the grid.\nThe second number is the number of columns un the grid.\n";
		m += "The third is the number of tracks you are going to put in the grid\nThe following lines will be for the data of each respective track\n";
		m += "The first number is the row number of the track\n";
		m += "The second and third will be its starting and ending column of the track\n";
		m = m + "If the row or column dimensions or track number you set for the grid are invalid\n" + 
		"They will default to the value of 2\n";
		m += "The rules are:\nThe rows and columns must be greater than zero.\nThe amount of tracks can be zero higher\n";
		m += "You may start setting your grid and its tracks on the line below START HERE\n\nSTART HERE";
		System.out.println(m);
		BufferedReader bReader = new BufferedReader(
		new InputStreamReader(System.in));
		StreamTokenizer stokens = new StreamTokenizer(bReader);
		stokens.eolIsSignificant(true);
		stokens.parseNumbers();
		int somVal,getToken,currentRow;
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
	public void describeGrid()
	{
		String gridString = "";
		gridString += String.format("\nThese are the result of execution number: %d\n\n", currentRun);
		gridString += String.format("You currently have a grid of %d row and %d columns with %d tracks\n\n", height,width,iter);
		for(int i = 0; i < height; i++)
		{
			gridString += tr[i].toString();
		}
		gridString += String.format("If you calculate the available spaces for row 1 to %d it should be equal to the answer, hopefully!\n" +
		"That answer is being the current answer of: %d\n", height,answer);
		gridString += String.format("Also if you calculate the total number of tracks for each row it should equal the " +
		"the third input of, %d, you entered\n", iter);
		System.out.println(gridString);
	}
};
