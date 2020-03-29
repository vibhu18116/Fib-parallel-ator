import java.lang.Runnable;

class Consumer implements Runnable{

	private NumbersQueue both;

	Consumer(NumbersQueue both){
		this.both = both;
	}


	@Override
	public synchronized void run(){

		while(true){
			both.computeNumber();
		}
		
	}

	
}