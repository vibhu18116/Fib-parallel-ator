import java.util.Scanner;

class User{

	private Producer producer;
	private NumbersQueue both;
	Thread producerThread;

	User(){
		both = new NumbersQueue();
		Producer producer = new Producer(both, this);

		producerThread = new Thread(producer);
		producerThread.start();
	}

	public String askForQuery(){
		Scanner sc = new Scanner(System.in);
		String input = sc.next();

		return input;
	}

	public int numThreads(){
		Scanner sc = new Scanner(System.in);
		return sc.nextInt();
	}

	public void terminate(){
		System.exit(0);		
	}
}