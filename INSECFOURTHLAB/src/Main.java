import java.util.ArrayList;
import java.util.Random;

public class Main {

	static int p;
	static int q;
	static int N;
	static int fN;
	static int e;
	static int y;
	static int d;
	static String encryptMessage = "";
	static String decryptMessage = "";
	static int hackN = 96091;
	static int hackE = 113;
	static String hackMessage = "61768, 80113, 95437, 80113, 53070, 75177, 82879";
	static String hackedMessage = "";
	
	public static void main(String[] args) {

		p = generate(30, 70);
		q = generate(70, 120);
		N = p * q;
		fN = (p - 1) * (q - 1);
		
		boolean eBool = false;
		
		do {
			e = generate(70, N);
			if (e != p && e != q && fN % e != 0)
				eBool = true;
		}while(eBool != true);
		
		advancedEuclideanAlgorithm(fN, e);
		
		System.out.println("p = " + p);
		System.out.println("q = " + q);
		System.out.println("N = " + N);
		System.out.println("fN = " + fN);
		System.out.println("e = " + e);
		System.out.println("d = " + d);
		N = 4747;
		e = 3889;
		d = 3209;
		
		encrypt("Привет");
		decrypt(encryptMessage);
		
		hack();
		

	}
	
	private static int generate(int start, int end) {
		int result = 0;
		boolean goodDigit = false;
		
		do {
			result = new Random().nextInt(end - start) + start;
			
			goodDigit = ferma(result);
		}
		while(goodDigit != true);
		return result;
	}

	
	private static boolean ferma(int x) {
		for (int i = 1; i < x; i++) {
			if (gcd(i, x) != 1)
				return false;
			if( pows(i, x-1, x) != 1)		
				return false;
		}
		return true;
	}
	
	private static int gcd(int a, int b){
		if(b==0)
			return a;
		return gcd(b, a%b);
	}
	
	private static int mul(int a, int b, int m){
		if(b == 1)
			return a;
		if(b % 2 == 0){
			int t = mul(a, b/2, m);
			return (2 * t) % m;
		}
		return (mul(a, b-1, m) + a) % m;
	}
	
	private static int pows(int a, int b, int m){
		if(b == 0)
			return 1;
		if(b % 2 == 0){
			int t = pows(a, b/2, m);
			return mul(t , t, m) % m;
		}
		return ( mul(pows(a, b-1, m) , a, m)) % m;
	}
	
	private static void advancedEuclideanAlgorithm(int A, int B) {
		int a, b, x = 0;
		ArrayList<Integer> adivb = new ArrayList<>();
		
		do {
			a = A;
			b = B;
			
			A = b;
			B = a % b;
			
			adivb.add(a / b);
		} while(a % b != 0);
		
		int [][] xy = new int[adivb.size()][2];
		
		xy[adivb.size()-1][0] = 0;
		xy[adivb.size()-1][1] = 1;
		
        for (int i = adivb.size() - 2; i >= 0 ; i--) {

            xy[i][0] = xy[i+1][1];                              
            xy[i][1] = xy[i+1][0] - xy[i+1][1] * adivb.get(i);  

            x = xy[i][0];                                       
            y = xy[i][1];
        }
        
        d = y < 0 ? y + fN : y;
        //System.out.println(x + " * " + fN + " + " + y + " * " + e + " = " + (x*fN+y*e));
	}
	
	private static void encrypt(String message) {
		char[] messageChars = message.toCharArray();
		System.out.println("Non-ecrypted message (" + message + "):");
		for (char letter : messageChars) {
			encryptMessage += encryptChar(letter) + ";";
		}
		System.out.println("\nEcrypted message:\n" + encryptMessage);
	}
	
	private static long encryptChar(char letter) {
		System.out.print((int) letter + ";");
		return pows(letter, e, N);
	}
	
	private static void decrypt(String message) {
		String[] messageChars = message.split(";");
		System.out.println("Decrypted message:");
		for (String letter : messageChars) {
			decryptMessage += decryptChar(letter);
		}
	}
	
	private static char decryptChar(String letter) {
		System.out.print(pows(Integer.parseInt(letter), d, N) + ";");
		return (char)pows(Integer.parseInt(letter), d, N);
	}
	
	private static void hack() {
		
		System.out.println("\nHacked");
		
		int size = hackN;
		int newxj = 0;
		int dividerN = 1;
		
		int[] xi = new int[size];
		int[] xj = new int[size];
		int[] xixj = new int[size];
		int[] gcd = new int[size];
		
		xi[0] = 2;
		xj[0] = 1;
		xixj[0] = xi[0] - xj[0];
		gcd[0] = 1;
		newxj = xi[0];
		
		for (int i = 1; i < size; i++) {
			xi[i] = ((xi[i-1]^2) - 1) % hackN;
			xj[i] = newxj;
			xixj[i] = Math.abs(xi[i] - xj[i]);
			gcd[i] = gcd(xixj[i], hackN);
			
			//System.out.printf("xi = %d; xj = %d; xi - xj = %d, NOD = %d\n", xi[i], xj[i], xixj[i], gcd[i]);
			
			if (gcd[i] > 1) {
				dividerN = gcd[i];
				break;
			}
			
			if (returnExp2(i + 1))
				newxj = xi[i];	
		}
		
		p = hackN / dividerN;
		fN = (int) ((p - 1) * (dividerN - 1));
		advancedEuclideanAlgorithm(fN, hackE);
		
		String[] letters = hackMessage.split(", ");
		
		for(String letter : letters) {
			int character = pows(Integer.parseInt(letter), d, hackN);
			hackedMessage += String.valueOf(character) + ";";
		}
		
		System.out.println(hackedMessage);
		
	}
	private static boolean returnExp2(int exp) {
		boolean ext2 = true;
		while(exp > 1){
			if (exp / 2 != 0)
				ext2 = false;
			exp = (int)(exp / 2);
		}
		return ext2;
	}
}
