import java.util.Random;



public class Test {
	public static void main(String[] args) {
		int [] spiralPoints = new int [10];
		int [] randPoints = new int [10];
		Random generator = new Random();
		
		for (int i=0; i<10; i++){
			int rand = generator.nextInt(10);
			randPoints[i]=rand;
			spiralPoints[randPoints[i]]+=1;
			System.out.println(randPoints[i]);
		}
		
		
		System.out.println();
		
		for (int i=0; i<10; i++){
			System.out.println(spiralPoints[i]);
		}
		
	}
}
