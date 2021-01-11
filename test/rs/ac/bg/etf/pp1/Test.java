package rs.ac.bg.etf.pp1;

public class Test {

	static int x;
	static char y;
	static boolean bt;
	static boolean bt2;
	static int niz[];

	public static void main(String[] args) {
		niz = new int[3];
		niz[0] = 5;
		niz[1] = 0;
		niz[2] = 0;
		bt = false;
		bt2 = true;
		
		if(bt2){
			if(bt2 && niz[0] > 0 || bt && niz[0] < 10){
				do{
					niz[0] = niz[0] - 1;
					if(niz[0] % 2 == 0) {
						System.out.print(true);
						System.out.print(' ');	
					} else {
						System.out.print(false);
						System.out.print(' ');	
					}
				} while(niz[0] != 0);
			}
		}
		
		do{
			do{
				niz[1] = niz[1] + 1;
			}while(niz[1] < 4);
			
			do{
				niz[2] = niz[2] + 1;
				continue;
				//break;
			}while(niz[2] < 3);
			
			niz[0] = niz[0] + 1;
			continue;
			//break;
		}while(niz[0] < 5);

		y = bt ? 'a' : 'b';
		x = bt2 ? 5+4 : 1;

//		System.out.print(eol);
//		System.out.print(chr(niz[0] + 64));
//		System.out.print(chr(niz[1] + 64));
//		System.out.print(chr(niz[2] + 64));
//		System.out.print(eol);
//		System.out.print(len(niz));
//		System.out.print(eol);
		System.out.println();
		System.out.print(niz[0] + 64);
		System.out.print(niz[1] + 64);
		System.out.print(niz[2] + 64);
		//System.out.print(eol);
		//System.out.print(len(niz));
		//System.out.print(eol);
		System.out.print(y);
		System.out.print(x); 
		
	}
}


