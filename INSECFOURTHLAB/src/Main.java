import java.util.Random;
import java.math.BigInteger;

public class Main {

	static int p;
	static int q;
	static int N;
	static int fN;
	
	public static void main(String[] args) {

		p = generate(30, 70);
		q = generate(70, 120);
		N = p * q;
		fN = (p - 1) * (q - 1);
		System.out.println("p = " + p);
		System.out.println("q = " + q);
		System.out.println("N = " + N);
		System.out.println("fN = " + fN);
		

	}
	
	private static int generate(int start, int end) {
		int result = 0;
		boolean goodDigit = false;
		
		do {
			result = new Random().nextInt(end - start) + start;
			
			System.out.println("Проверяем число - " + result);
			
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
}
