package be.inniger.questions;

/**
 * What are the possible values of counter after both threads have ran their course?
 */
public class Question1 {

	private int counter = 0;

	public static void main(String... args) throws InterruptedException {
		Question1 question = new Question1();

		Thread thread1 = new Thread(question::incrementCounter);
		Thread thread2 = new Thread(question::incrementCounter);

		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();

		System.out.println(question.counter);
	}
	
	private void incrementCounter() {
		counter++;
	}
}
