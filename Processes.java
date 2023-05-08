import java.io.File;
import java.io.PrintWriter;
import java.util.*;

//outer class to allow sharing a variable with subclasses 
public class Processes {
	
	//shared variable initialized to 100
	private int var = 100;
	
	//calling the constructor method creates 2 class instances 
	public Processes() {
		Producer producer = new Producer();
		Consumer consumer = new Consumer();
	}
	
	public class Producer{
		
		//calling the Producer constructor method creates a thread running the runWork method
		public Producer() {
			Thread t = new Thread(() -> runWork());
			t.start();
		}
		
		//runWork method loops 4 times, each time with a 1-3 second delay, and each time 
		//passing the loop counter value to the share variable.
		private void runWork() {
			for(int i = 0; i < 4; i++) {
				randomDelay();
				var = i;
				System.out.println("Producer: var = " + i);
			}
		}
	}
	
	public class Consumer{
		//calling the Consumer constructor method creates a thread running the runWork method
		public Consumer() {
			Thread t = new Thread(() -> runWork());
			t.start();
		}
		
		//runWork method loops 5 times, each time with a 1-3 second delay, and each time
		//retrieving the current value of the shared variable and adding it to the sum
		//of all the shared variable values retrieved by the method.
		private void runWork() {
			int sum = 0;
			for(int i = 0; i < 5; i++) {
				randomDelay();
				sum = sum + var;
				System.out.println("Consumer: sum = " + sum);
			}
			createOutput(sum);
		}
	}
	
	//random delay between 1 to 3 seconds used by both threads
	public static void randomDelay() {
		Random r = new Random();
		int millisec = (r.nextInt(3)+1) * 1000;
		try {
			Thread.sleep(millisec); //delay in milliseconds 
		}catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	public static void createOutput(int sum) {
		
		//prompt user for run number so each output has different filenames 
		Scanner scan = new Scanner (System.in);
		System.out.println("Enter run number ");
		int run = scan.nextInt(); 
		
		//PrinterWriter & I/O File code copied from ICS-340 InitialCodebase 
		//by Metropolitan State University Professor Michael Stein
		File outputFile;
		PrintWriter output = null;
		
		outputFile = new File( "output" + run+ ".txt" );
		if ( outputFile.exists() ) {
			outputFile.delete();
		}
		
		try {
			output = new PrintWriter(outputFile);			
		}
		catch (Exception x ) { 
			System.err.format("Exception: %s%n", x);
			System.exit(0);
		}
		
		//output to file the statements following Assignment 2 directions 
		System.out.println("Output to file: sum = " + sum);
		output.print("Computer Operating Systems Assignment #2\n\n");
		output.print("The sum is " + sum);
		
		output.flush();
		scan.close();
	}
	
	public static void main(String[] args) {
		
		//calling the Processes constructor creates two threads,
		//the Producer thread and the Consumer thread.
		Processes runProcesses = new Processes();
	}
	
}
