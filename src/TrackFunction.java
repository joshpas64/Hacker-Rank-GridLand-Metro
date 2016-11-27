import java.io.*;
/*
I guess this could be what you would call the interface class. It sets up all the classes on the backend needed for interpreting
and processing the problem when it is initialized. In this case it just sets up answer and currentRun. But has defined, all it 
needs to get input and solve the problem which are fields:

Height, Width, Iter(Number of Tracks) [Which will be entered by the user]
tr (The track row object for each row in Height)
bReader and stokens which are used for collecting and parsing input

The run method is where main comes in and says it wants to activate the interface where the interface will launch the program
and problem to solve. In some cases, like this one, run can go on indefinitely until the user prompts it to end. By saying NO to the 
continue question. Or in a GUI, closing the window, exiting the app, hitting a button to another activity, etc.
*/

//Obviously since the parameters are entered by the user there can be many instances of errors, I have not caught them all but will
//try to add more IOException handlers later on in later branches
public class TrackFunction {
	private Track[] tr;
	private int answer;
	private int height,width,iter;
	private int currentRun;
	private BufferedReader bReader; //Used to readlines of input from the user
	private StreamTokenizer stokens;//Used to treat each string of digits separated by spaces as integers, needs an inputstream to be initialized
	// In this case stokens stream is bReader
	public TrackFunction() throws IOException
	{
		currentRun = 0; //Default of zero
		answer = 0;
	}
	public void run() throws IOException //Once outside program feels it is time to launch it will call this method
	{
		bReader = new BufferedReader(
				new InputStreamReader(System.in)); //Make a bufferedReader that uses console input as its input stream
		stokens = new StreamTokenizer(bReader); //Used to tokenize and separate strings separated by whitespace by default
		stokens.eolIsSignificant(true); //\n can be considered a unique token to evaluate and check for
		stokens.parseNumbers(); //If possible, treat each word from input as a number
		System.out.println("Welcome to the GridLand Metro Progam by super Novice joshpas! :D\n");
		System.out.println("Do you want to start?\n"); //Simple input prompts
		String prompt = getContinue(); //Deteremine if user wants to start or continue the program
		String newPrompt = prompt.replace(" ", "");//Strip whitespace
		newPrompt = newPrompt.toLowerCase();//Make one case so Uppercase or lowercase are the same
		while(newPrompt.equals("yes")) //While user wants to continue
		{
			answer = collectInput();//Get all input needed for the user to execute the problem
			System.out.println(String.format("The number of spaces available for lamps in this grid of tracks is %d spaces",answer));
			//Print and display the answer
			//Describe the layout of the grid
			describeGrid();
			prompt = getContinue(); //Get prompt to continue, if chosen to continue classes
			//fields or attributes like tr or height get reset to empty values
			newPrompt = prompt.replace(" ", "");
			newPrompt = newPrompt.toLowerCase();
		}
		bReader.close(); //Once the user is done, close streams and output bye :)
		System.out.println("\nTHANKS FOR COMING BYE BYE :D :D :D");
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
		String m = "The first line of input will contain 3 numbers (integers PLEASE!)\n";
		m += "The first number is the number of rows in the grid.\nThe second number is the number of columns un the grid.\n";
		m += "The third is the number of tracks you are going to put in the grid\nThe following lines will be for the data of each respective track\n";
		m += "The first number is the row number of the track\n";
		m += "The second and third will be its starting and ending column of the track\n";
		m = m + "If the row or column dimensions or track number you set for the grid are invalid\n" + 
		"They will default to the value of 2\n";
		m += "The rules are:\nThe rows and columns must be greater than zero.\nThe amount of tracks can be zero higher\n";
		m += "You may start setting your grid and its tracks on the line below START HERE\n\nSTART HERE";
		System.out.println(m); //Display rules and tips for setting up and solving the problem
		int somVal,getToken,currentRow; //Vals used from stream
		height = iter = width = -1; //Resetting height, width, and number of tracks
		getToken = stokens.nextToken(); //Get next word
		while(getToken != StreamTokenizer.TT_EOL)//While that word is NOT an \n char
		{
			if(getToken == StreamTokenizer.TT_NUMBER)//If the token is a numner
			{
				somVal = (int) stokens.nval;//when a the most recent token is a number the actual value 
				//of that number is stored in a StreamTokenizer nval attribute, by default its stored as a double
				if(height == -1 && somVal > 0) //If over zero and height is not set, set token as height
					height = somVal;
				else if(height > 0 && width == -1 && somVal > 0) //If height is set but width is not set, set token as widht
					width = somVal;
				else if(height > 0 && width > 0 && iter == -1 && somVal >= 0) //If height and width are set but not token,
					//set track number(iter) to stokens current nval
					iter = somVal;
			//In this case any number after the third number is discarded as well as any nonnumber input
			}
			getToken = stokens.nextToken();//Get next token
		}
		while(height == -1 || width == -1 || iter == -1)//If user entered a newline before setting all the fields give a default of 2
		{
			if(height == -1)
				height = 2;
			if(width == -1)
				width = 2;
			if(iter == -1)
				iter = 2;
		}
		tr = new Track[height]; //Reinitialize track Rows
		for(int i = 0; i < height; i++)
		{
			tr[i] = new Track(i + 1, width);
		}
		if(iter == 0) //If there are no tracks, the answer is obvious and the calculation function is ready to execute
		{
			return runFunction(height,width,0);//Skip to execution function
		}
		TrackRow[] tRows = new TrackRow[iter];//Initialize an array of tracks
		for (int i = 0; i < tRows.length; i++)
		{
			tRows[i] = new TrackRow();
		}
		for(int i = 0; i < iter; i++) //Number of lines to take input from 
		{
			getToken = stokens.nextToken(); //Get next number
			int[] dimensions = {0,0,0};//Fields of a track
			//First index[0] is row second is starting column and third is ending column
			int start = 0;
			while(getToken != StreamTokenizer.TT_EOL ) //Until Eol
			{
				if(getToken == StreamTokenizer.TT_NUMBER)//Keep getting and gathering tokens until you have a number or \n
				{
					somVal = (int) stokens.nval; 
					dimensions[start] = somVal;//Set that number to an index in dimensions
					start++;//Get ready to set the next index of dimensions
				}
				getToken = stokens.nextToken();
			}
			tRows[i].setDimensions(dimensions,3);//Now that dimensions is set, make an actual track object from it data
			// and add it to the array
			currentRow = tRows[i].getRow() - 1; //Get the tracks row and account for zero-based indexing
			if( currentRow >= 0 && currentRow < height) //Only add it to the trackrow array if it is a valid track
				tr[currentRow].add(tRows[i]);
				//Add track to the row it belongs to in the array of row objects meant for storing tracks
				// the (Track class)
		}
		return runFunction(height,width,iter); //Now tr has all its data set use it with runFunction to find the number of available
		//spaces
	}
	private void describeGrid()
	{
		String gridString = "";
		gridString += String.format("\nThese are the result of execution number: %d\n\n", currentRun);//Show how many times user has run this program
		gridString += String.format("You currently have a grid of %d row and %d columns with %d tracks\n\n", height,width,iter);
		//Display grid dimensions
		for(int i = 0; i < height; i++)
		{
			gridString += tr[i].toString();//Display layout of each row in the grid
		}
		gridString += String.format("If you calculate the available spaces for row 1 to %d it should be equal to the answer, hopefully!\n" +
		"That answer is being the current answer of: %d\n", height,answer); 
		gridString += String.format("Also if you calculate the total number of tracks for each row it should equal the " +
		"the third input of, %d, you entered\n", iter);
		//Describe the amount of spaces left for lamposts in total
		System.out.println(gridString);
		System.out.println("\nWould you like to continue ?: ");//Display continue prompt
	}
	private String getContinue() throws IOException
	{
		if(currentRun > 0)
			bReader.skip(1); //Since eol is considered a token have bReader skip it and move to next char for the StreamTokenizer
			//to turn into a token
		String prompt = bReader.readLine();
		return prompt;
	}
};
