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
		//System.out.println(decryptMessage);

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
		System.out.print("Non-ecrypt message: ");
		for (char letter : messageChars) {
			encryptMessage += encryptChar(letter) + ";";
		}
		System.out.println("");
		System.out.println("Encrypt message: " + encryptMessage);
	}
	
	private static int encryptChar(char letter) {
		int result = letter;
//		for (int i = 0; i < e - 1; i++) {
//			result = result * letter;
//			result = result % N;
//		}
		result = (result^e) % N;
		System.out.print((int) letter + ";");
		return result;
	}
	
	private static void decrypt(String message) {
		String[] messageChars = message.split(";");
		for (String letter : messageChars) {
			decryptMessage += decryptChar(letter);
		}
	}
	
	private static int decryptChar(String letter) {
		char result;
		int character = Integer.parseInt(letter);
		result = (char) ((character^d) % N);
		System.out.println();
		return character;
	}
}
