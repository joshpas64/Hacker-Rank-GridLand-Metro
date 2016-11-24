import java.io.*;
public class Metro {
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		/*
		LineNumberReader in = new LineNumberReader(new InputStreamReader(System.in));
		System.out.println("Just doing some testing");
		String start = in.readLine();
		int[] commands = {0,0,0};
		char character;
		String currentStr = "";
		int startIndex = 0;
		for(int i = 0; i < start.length(); i++)
		{
			character = start.charAt(i);
			int asciiVal = (int) character;
			if(asciiVal > 47 && asciiVal < 58)
			{
				currentStr = currentStr + character;
			}
			else
			{
				commands[startIndex] = Integer.parseInt(currentStr);
				currentStr = "";
				startIndex++;
			}
		}
		if(commands[commands.length - 1] == 0)
			commands[commands.length - 1] = Integer.parseInt(currentStr);
		TrackFunction tr = new TrackFunction(commands);
		in.close();
		System.out.println("HELLO THERE :P");
		*/
		/*
		LinkedLister linked = new LinkedLister();
		int[] dim1 = {0,1};
		int[] dim2 = {5,6};
		int[] dim5 = {3,3};
		int[] dim3 = {12,15};
		int[] dim4 = {22,25};
		linked.add(dim1, 1);
		linked.add(dim5, 0);
		linked.add(dim2,0);
		linked.add(dim3, 0);
		linked.add(dim4, 0);
		linked.checkStart(4,4);
		linked.checkStart(2,3);
		linked.checkStart(1, 4);
		linked.checkStart(8,10);
		linked.checkStart(6,6);
		linked.checkStart(12,15);
		System.out.print(linked);
		*/
		TrackFunction tf = new TrackFunction();
	}

}
