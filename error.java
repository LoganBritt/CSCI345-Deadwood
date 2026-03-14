import java.util.Random;
public class error{
	public static void main(String[] args){
		Random r = new Random();
		while(true){
			System.out.print("\u001B[32m" + r.nextInt() + "\u001B[0m");
		}
	}

}
