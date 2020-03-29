import java.lang.Runnable;
import java.lang.Thread;


class Producer implements Runnable{

	private Thread allConsumers[];
	private NumbersQueue both;
	private User toCater;

	Producer(NumbersQueue both, User user){
		this.both = both;
		this.toCater = user;
	}
	
	@Override
	public void run(){
		int numThreads = askForNumThreads();

		allConsumers = new Thread[numThreads];

		for (int i = 0; i<numThreads; i++){
			allConsumers[i] = new Thread(new Consumer(both));
			allConsumers[i].start();
		}

		keepCalculating();
		toCater.terminate();
	}

	private int askForNumThreads(){
		System.out.println("How many consumer threads do you want to create?");
		return toCater.numThreads();
	}


	private void keepCalculating(){

		System.out.println("Enter 'Q' to quit.");
		System.out.println("Enter 'S' to show results.");
		System.out.println("Enter number to calculate its fibbonaci.");

		while (true){
			String input = toCater.askForQuery();

			if (input.equals("Q")){
				this.terminate();
				return;
			}else if (input.equals("S")){
				this.showResults();
			}else{
				try{
					int num = Integer.parseInt(input);
					both.addNewNumber(num);
				}catch(Exception e){
					// e.printStackTrace();
					System.out.println("Invalid input. Try again.");
				}
			}
		}
	}

	private void terminate(){
		this.showResults();
	}

	private void showResults(){
		both.allResults();
	}
	
}