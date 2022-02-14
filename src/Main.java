import java.util.Scanner;
import java.util.HashMap;

public class Main {

	static HashMap<Parameter, CombineResult> cache = new HashMap<>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		
		int[] seq = new int[n];
		
		for (int i=0;i<n;i++) {
			seq[i] = sc.nextInt();
		}
		
		System.out.println(getMaxRiceball(seq, 0, n-1).maxRiceball);
		
		
	}

	private static CombineResult getMaxRiceball(int[] seq, int begin, int end) {
		// TODO Auto-generated method stub
		
		CombineResult result = new CombineResult();
		
		if (begin == end) {
			result.isCombinable = true;
			result.maxRiceball = seq[begin];
			return result;
		}
		
		Parameter key = new Parameter(begin,end);
		if (cache.containsKey(key)) {
			return cache.get(key);
		}
		
		// case 1: two parts
		for (int mid=0;mid<end-1;mid++) {
			CombineResult result1 = getMaxRiceball(seq, begin, mid);
			CombineResult result2 = getMaxRiceball(seq, mid+1, end);
			
			if (result1.isCombinable && result2.isCombinable) {
				result.isCombinable = true;
				result.maxRiceball = result1.maxRiceball+result2.maxRiceball;
				cache.put(key, result);
				return result;
			} else {
				result.isCombinable = false;
				result.maxRiceball = (result1.maxRiceball>result2.maxRiceball)?result1.maxRiceball:result2.maxRiceball;
			}
		}
		
		// case 2: 3 parts
		
		for (int mid1=0;mid1<end-2;mid1++) {
			for (int mid2 = mid1+1; mid2<end-1; mid2++) {
				CombineResult result1 = getMaxRiceball(seq, begin,mid1);
				CombineResult result2 = getMaxRiceball(seq, mid1+1,mid2);
				CombineResult result3 = getMaxRiceball(seq, mid2+1, end);
				
				if (result1.isCombinable && result2.isCombinable && result3.isCombinable && result1.maxRiceball == result3.maxRiceball) {
					result.isCombinable = true;
					result.maxRiceball = result1.maxRiceball + result2.maxRiceball + result3.maxRiceball;
					cache.put(key, result);
					return result;
				} else {
					result.isCombinable = false;
					if (result.maxRiceball < result1.maxRiceball)
						result.maxRiceball = result1.maxRiceball;
					if (result.maxRiceball < result2.maxRiceball)
						result.maxRiceball = result2.maxRiceball;
					if (result.maxRiceball < result3.maxRiceball)
						result.maxRiceball = result3.maxRiceball;
				}
			}
		}
		cache.put(key, result);
		return result;
		
	}

}

class CombineResult {
	boolean isCombinable = false;
	int maxRiceball = 0;
}

class Parameter {
	int begin;
	int end;
	
	public Parameter(int b, int e) {
		this.begin = b;
		this.end = e;
	}
}
