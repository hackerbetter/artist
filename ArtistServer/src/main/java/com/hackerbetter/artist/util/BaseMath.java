package com.hackerbetter.artist.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class BaseMath {

	private static String regex = "^(?!\\d*(\\d)\\d*\\1)[0-9]{1,10}$";
	/**
	 * 根据符号拆分进行乘法运算
	 * @param bet
	 * @param symbol
	 * @return
	 */
	public static long getSplitMultiply(String bet,String symbol) {
		long total = 1L;
		for(String betCode:bet.split(symbol)) {
			total = total * betCode.length();
		}
		return total;
	}
	
	/**
	 * 閫氳繃symbol鍒嗗壊娉ㄧ爜杩涜姝ｅ垯鍖归厤
	 * @param bet娉ㄧ爜
	 * @param symbol鍒嗗壊绗﹀悎
	 * @param regex姝ｅ垯
	 * @return
	 */
	public static boolean isMatchRegex(String bet,String symbol,String regex) {
		for(String betCode:bet.split(symbol)) {
			if(betCode.equals("-")||betCode.matches(regex))
				continue;
			else 
				return false;
		}
		return true;
	}
	
	
	/**
	 * 棣栧厛閫氳繃regex鍒ゆ柇鏁翠釜娉ㄧ爜锛屼箣鍚庨�杩噐egex2鍒ゆ柇鍒嗗壊鍚庢敞鐮�
	 * @param bet 娉ㄧ爜
	 * @param regex 鏁翠釜娉ㄧ爜鐨勬鍒�
	 * @param symbol 鍒嗗壊绗﹀悎
	 * @param regex2 鍒嗗壊鍚庢敞鐮佹鍒�
	 * @return
	 */
	public static boolean checkCodeBase(String bet, String regex, String symbol,String regex2) {
		if (bet.matches(regex))
			return isMatchRegex(bet, symbol,regex2);
		return false;
	}
	
	
	/**
	 * 计算从n中选出k个数的组合数
	 * @param n 样本总数
	 * @param k 选取样本数
	 * @return 组合数
	 */
	public static long nchoosek(int n,int k) {
		//楠岃瘉浼犲叆鍙傛暟锛宯涓嶈兘灏忎簬绛変簬0锛宬涓嶈兘灏忎簬0锛宯涓嶈兘灏忎簬k锛沰=0鐨勬椂鍊欙紝瑙勫畾缁勫悎鏁颁负1
		if(n <= 0 || k < 0 || n < k) {
			return -1;
		}
		if(k == 0 || n==k) {
			return 1;
		}
		//鎸夌収缁勫悎鏁版�璐紝鍦╧澶т簬n/2鏃讹紝璁＄畻浠巒涓�鍑簄-k鐨勫�锛屽噺灏戣绠楅噺
		if(k > n/2) {
			k = n - k;
		}
		
//		System.out.println("n:"+n);
//		System.out.println("k:"+k);
		
		//閫氳繃缁勫悎鏁板叕寮忔眰缁勫悎鏁�
		long result = multiplyByStep(n,n-k+1)/multiplyByStep(k,1);
		
		return result;
	}
	
	/**
	 * 计算从m到n,以1为步长的成绩(m:1:n),m、n为正整数
	 * @param m 正整数
	 * @param n 正整数
	 * @return 步长为1，m到n的乘机，-1表示传入的mn为负数
	 */
	public static long multiplyByStep(int m,int n) {
		//鏁版嵁楠岃瘉锛岃瀹歮鍜宯涓嶈兘灏忎簬0
		if(m < 0 || n < 0) {
			return -1;
		}
		
		//瀹氫箟榛樿杩斿洖鍊�
		long result = 1l;
		
		//m澶т簬绛変簬n锛屼粠n浠�涓烘闀夸箻鍒癿;m灏忎簬n锛屼粠m浠�涓烘闀夸箻鍒皀
		if(m >= n) {
			for(int i = n;i <= m;i++) {
				result = result * i;
			}
		}else {
			for(int i = m;i <= n;i++) {
				result = result * i;
			}
		}
		return result;
	}
	
	/**
	 * 骞傝繍绠�
	 * 
	 * @param d
	 *            搴曟暟
	 * @param z
	 *            鎸囨暟
	 * @return 璁＄畻缁撴灉
	 */
	public static long exp(long d, long z) {
		long result = 1L;
		for (int i = 0; i < z; i++) {
			result = result * d;
		}
		return result;
	}
	
	
	/**
	 * 足彩胆拖计算注数
	 * 
	 * d2:胆码中双选的个数
	 * d3:胆码中三选的个数
	 * t1:拖码中单选的个数
	 * t2:拖码中双选的个数
	 * t3:拖码中三选的个数
	 * choose：从拖码选择来构成整个投注号码的个数（9-胆码个数）
	 * @return 注数
	 */
	public static long dantuo(String bet) {
		String[] bets = bet.split("\\$");
		int dtotal=0,d2=0,d3=0,t1=0,t2=0,t3=0,choose=0;
		for(String s1:bets[0].split(",")) {
			if(!s1.equals("#")) {
				dtotal = dtotal + 1;
				if(s1.length()==2) {
					d2 = d2 + 1;
				}else if(s1.length()==3) {
					d3 = d3 + 1;
				}
			}
		}
		
		for(String s2:bets[1].split(",")) {
			if(!s2.equals("#")) {
				if(s2.length()==1) {
					t1 = t1 + 1;
				}else if(s2.length()==2) {
					t2 = t2 + 1;
				}else if(s2.length()==3) {
					t3 = t3 + 1;
				}
			}
		}
		choose = 9 - dtotal;
		long m = exp(2, d2);
		long n = exp(3, d3);
		long result = 0;
		for (int i = 0; i <= t1; i++) {
			for (int j = 0; j <= t2; j++) {
				int k = choose - i - j;
				if(k <= t3 && k >= 0) {
					long nk1, nk2, nk3, exp1, exp2;
					nk1 = (t1 == 0 ? 1 : nchoosek(t1, i));//
					nk2 = (t2 == 0 ? 1 : nchoosek(t2, j));
					nk3 = (t3 == 0 ? 1 : nchoosek(t3, k));
					exp1 = exp(2, j);
					exp2 = exp(3, k);
					result = result + nk1 * nk2 * nk3 * exp1 * exp2;
					System.out.println("i=" + i + ",j=" + j + ",k=" + k);
				}
				
			}
		}
		if (m > 0)
			result = result * m;
		if (n > 0)
			result = result * n;
		bets = null;
		return result;
	}
	
	
	
	
	//转九计算
	//选出全部的能组成任九场的注码，计算注数累加之和
	public static long convertNine(String betcode) {
		int z1=0,z2=0,z3=0;
		String[] zhumas = betcode.split(",");
		for(int i = 0;i < zhumas.length;i++) {
			if(!"#".equals(zhumas[i])) {
				if(zhumas[i].length()==1) {
					z1 = z1 + 1;
				}else if(zhumas[i].length()==2) {
					z2 = z2 + 1;
				}else if(zhumas[i].length()==3) {
					z3 = z3 + 1;
				}
			}
		}
		
		long result = 0;
		for(int i = 0;i <= z1;i++) {
			for(int j = 0;j <= z2;j++) {
				int k = 9 - i - j;
				if(k <= z3 && k >= 0) {
					long nk1, nk2, nk3, exp1, exp2;
					nk1 = (z1 == 0 ? 1 : nchoosek(z1, i));//
					nk2 = (z2 == 0 ? 1 : nchoosek(z2, j));
					nk3 = (z3 == 0 ? 1 : nchoosek(z3, k));
					exp1 = exp(2, j);
					exp2 = exp(3, k);
					result = result + nk1 * nk2 * nk3 * exp1 * exp2;
				}
			}
		}
		return result;
	}
	
	
	
	/**
	 * 排序
	 * @param src
	 * @return
	 */
	public static int[] bubbleSort(int[] src) {
		for (int i = 0,len = src.length; i < len; i++) {
			for (int j = i + 1; j < len; j++) {
				int temp;
				if (src[i] > src[j]) {
					temp = src[j];
					src[j] = src[i];
					src[i] = temp;
				}
			}
		}
		return src;
	}
	
	
	/**
	 * 查重复
	 * @param zhuma
	 * @return
	 */
	public static boolean checkDuplicate(int[] zhuma) {
		for(int i = 0,i1=zhuma.length;i < i1; i++) {
			for(int j = i+1; j < i1;j++) {
				if(zhuma[i]==(zhuma[j]))
					return false;
			}
		}
		return true;
	}
	
	
	/**
	 * 查重复
	 * @param zhuma
	 * @return
	 */
	public static boolean checkDuplicate(String betcode) {
		int[] zhuma = splitCodeToInt(betcode);
		for(int i = 0,i1=zhuma.length;i < i1; i++) {
			for(int j = i+1; j < i1;j++) {
				if(zhuma[i]==(zhuma[j]))
					return false;
			}
		}
		return true;
	}
	
	/**
	 * 查排序
	 * @param codes
	 * @return
	 */
	public static boolean checkSort(int[] codes) {
		int[] codes2 = bubbleSort(codes.clone());
		for(int i = 0,j=codes.length;i<j;i++) {
			if(codes[i]!=codes2[i]) {
				codes2 = null;
				return false;
			}
		}
		codes2 = null;
		return true;
	}
	
	
	
	/**
	 * 注码每两位拆分一次，拆分为int数组
	 * @param code
	 * @return
	 */
	public static int[] splitCodeToInt(String code) {
		int codes[] = new int[code.length()/2];
		for(int i = 0,j=codes.length;i < j;i++) {
			codes[i] = Integer.parseInt(code.substring(i*2, (i+1)*2));
		}
		return codes;
	}
	
	/**
	 * 注码每两位拆分一次，拆分为int数组
	 * @param code
	 * @return
	 */
	public static String[] splitCodeToStr(String code) {
		String codes[] = new String[code.length()/2];
		for(int i = 0,j=codes.length;i < j;i++) {
			codes[i] = code.substring(i*2, (i+1)*2);
		}
		return codes;
	}
	
	
	/**
	 * 注码每1位拆分一次，拆分为int数组
	 * @param code
	 * @return
	 */
	public static int[] splitCodeToIntStepOne(String code) {
		int codes[] = new int[code.length()];
		for(int i = 0,j=codes.length;i < j;i++) {
			codes[i] = Integer.parseInt(code.substring(i, i+1));
		}
		return codes;
	}
	
	
	/**
	 * 同时检查重复和排序
	 * @param code
	 * @return
	 */
	public static boolean checkDuplicateAndSort(String code) {
		//将字符串拆分为数组
		int[] codes = splitCodeToInt(code);
		//判断是否重复
		
		if(checkDuplicate(codes)==false) {
			codes = null;
			return false;
		}
		
		//判断是否排序
		if(checkSort(codes)==false) {
			codes = null;
			return false;
		}
		codes = null;
		return true;
	}
	
	public static boolean checkDuplicateAndSortDan(String dan,String tuo) {
		//鍚堝苟鑳嗙爜鎵樼爜锛屽垽鏂槸鍚﹂噸澶�
		int[] codes = splitCodeToInt(dan+tuo);
		if(checkDuplicate(codes)==false) {
			codes = null;
			return false;
		}
		
		
		//鍒ゆ柇鎺掑簭鑳嗙爜
		int[] dans = splitCodeToInt(dan);
		if(checkSort(dans)==false) {
			codes = null;
			return false;
		}
		//鍒ゆ柇鎺掑簭鎵樼爜
		int[] tuos = splitCodeToInt(tuo);
		if(checkSort(tuos)==false) {
			codes = null;
			return false;
		}
		codes = null;
		return true;
 	}
	
	/**
	 * 检查3D组注码是不是两个注码相等
	 * @param code
	 * @return
	 */
	public static boolean checkComb3(String code) {
		int equal = 0;
		if(code.substring(0, 2).equals(code.substring(2, 4))) {
			equal = equal + 1;
		}
		
		if(code.substring(0, 2).equals(code.substring(4, 6))) {
			equal = equal + 1;
		}
		
		if(code.substring(2, 4).equals(code.substring(4, 6))) {
			equal = equal + 1;
		}
		
		if(equal==1) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检查注码个数
	 * @param bet
	 * @return
	 */
	public static boolean checkNumber(String bet) {
		int number = Integer.parseInt(bet.substring(0, 2));
		if((bet.length()-2)/2 == number) {
			return true;
		}
		return false;
	}
	
	/**
	 * 组合计算公式
	 * @param m
	 * @param n
	 * @return  返回 C(n,m)
	 */
	public static long zuhe(int m,int n){
		long t_a = 0;
		t_a = (jiec(n).divide((jiec(n-m).multiply(jiec(m))))).longValue();
		return t_a;
	}
	
	
	/**
	 * 阶乘计算公式
	 * @param a 
	 * @return  返回a的阶乘   a!
	 */
	private static BigDecimal jiec(long a){
		BigDecimal t_a = new BigDecimal(1);
		for (int i = 1; i <= a; i++) {
			t_a = t_a.multiply(new BigDecimal(i));
		}
		return t_a;
	}
	/**
	 * 取得排列三直选和值的注码
	 * @param num
	 * @return
	 */
	public static int getArray3HeZhiNumber(int num) {
		int zxHHH[] = {1,3,6,10,15,21,28,36,45,55,63,69,73,75,75,73,69,63,55,45,36,28,21,15,10,6,3,1};
		return zxHHH[num];
	}
	
	/**
	 * 取得3D组3和值的注码
	 * @param num
	 * @return
	 */
	public static int getGroup3HeZhi3DNumber(int num) {
		int z3HHH[] = {1,2,1,3,3,3,4,5,4,5,5,4,5,5,4,5,5,4,5,4,3,3,3,1,2,1};
		return z3HHH[num-1];
	}
	
	/**
	 * 取得3D组6和值的注码
	 * @param num
	 * @return
	 */
	public static int getGroup6HeZhi3DNumber(int num) {
		int z6HHH[] = {0,0,1,1,2,3,4,5,7,8,9,10,10,10,10,9,8,7,5,4,3,2,1,1,0,0};
		return z6HHH[num-1];
	}
	
	
	/**
	 * 排三组三和值转单式
	 * @param num
	 * @return
	 */
	public static String getGroup3DanshiBetcode(int num) {
		String betcode = "";
		switch (num) {
		case 1:
			betcode = "6|0,0,1";
			break;
		case 2:
			betcode = "6|0,1,1;6|0,0,2";
			break;
		case 3:
			betcode = "6|0,0,3";
			break;
		case 4:
			betcode = "6|0,0,4;6|0,2,2;6|1,1,2";
			break;
		case 5:
			betcode = "6|0,0,5;6|1,2,2;6|1,1,3";
			break;
		case 6:
			betcode = "6|0,0,6;6|1,1,4;6|0,3,3";
			break;
		case 7:
			betcode = "6|0,0,7;6|1,1,5;6|2,2,3;6|1,3,3";
			break;
		case 8:
			betcode = "6|0,0,8;6|1,1,6;6|2,2,4;6|2,3,3;6|0,4,4";
			break;
		case 9:
			betcode = "6|0,0,9;6|1,4,4;6|2,2,5;6|1,1,7";
			break;
		case 10:
			betcode = "6|1,1,8;6|2,2,6;6|3,3,4;6|2,4,4;6|0,5,5";
			break;
		case 11:
			betcode = "6|1,1,9;6|2,2,7;6|3,3,5;6|3,4,4;6|1,5,5";
			break;
		case 12:
			betcode = "6|2,2,8;6|3,3,6;6|2,5,5;6|0,6,6";
			break;
		case 13:
			betcode = "6|2,2,9;6|3,3,7;6|4,4,5;6|3,5,5;6|1,6,6";
			break;
		case 14:
			betcode = "6|3,3,8;6|4,4,6;6|4,5,5;6|2,6,6;6|0,7,7";
			break;
		case 15:
			betcode = "6|3,3,9;6|4,4,7;6|3,6,6;6|1,7,7";
			break;
		case 16:
			betcode = "6|4,4,8;6|5,5,6;6|4,6,6;6|2,7,7;6|0,8,8";
			break;
		case 17:
			betcode = "6|4,4,9;6|5,5,7;6|5,6,6;6|3,7,7;6|1,8,8";
			break;
		case 18:
			betcode = "6|5,5,8;6|4,7,7;6|2,8,8;6|0,9,9";
			break;
		case 19:
			betcode = "6|5,5,9;6|6,6,7;6|5,7,7;6|3,8,8;6|1,9,9";
			break;
		case 20:
			betcode = "6|6,6,8;6|6,7,7;6|4,8,8;6|2,9,9";
			break;
		case 21:
			betcode = "6|6,6,9;6|5,8,8;6|3,9,9";
			break;
		case 22:
			betcode = "6|7,7,8;6|6,8,8;6|4,9,9";
			break;
		case 23:
			betcode = "6|7,7,9;6|7,8,8;6|5,9,9";
			break;
		case 24:
			betcode = "6|6,9,9";
			break;
		case 25:
			betcode = "6|8,8,9;6|7,9,9";
			break;
		case 26:
			betcode = "6|8,9,9";
			break;

		default:
			break;
		}
		
		return betcode;
	}
	
	
	/**
	 * 排三组六和值转单式
	 * @param num
	 * @return
	 */
	public static String getGroup6DanshiBetcode(int num) {
		String betcode = "";
		switch (num) {
		case 3:
			betcode = "6|0,1,2";
			break;
		case 4:
			betcode = "6|0,1,3";
			break;
		case 5:
			betcode = "6|0,1,4;6|0,2,3";
			break;
		case 6:
			betcode = "6|0,1,5;6|0,2,4;6|1,2,3";
			break;
		case 7:
			betcode = "6|0,1,6;6|0,2,5;6|0,3,4;6|1,2,4";
			break;
		case 8:
			betcode = "6|0,1,7;6|0,2,6;6|0,3,5;6|1,2,5;6|1,3,4";
			break;
		case 9:
			betcode = "6|0,1,8;6|0,2,7;6|0,3,6;6|0,4,5;6|1,2,6^6|1,3,5;6|2,3,4";
			break;
		case 10:
			betcode = "6|0,1,9;6|0,2,8;6|0,3,7;6|0,4,6;6|1,2,7^6|1,3,6;6|1,4,5;6|2,3,5";
			break;
			
		case 11:
			betcode = "6|0,5,6;6|0,3,8;6|1,4,6;6|1,2,8;6|0,4,7^6|1,3,7;6|2,4,5;6|2,3,6;6|0,2,9";
			break;
		case 12:
			betcode = "6|0,5,7;6|0,4,8;6|0,3,9;6|1,4,7;6|1,3,8^6|3,4,5;6|1,2,9;6|1,5,6;6|2,4,6;6|2,3,7";
			break;
		case 13:
			betcode = "6|0,6,7;6|0,5,8;6|0,4,9;6|1,4,8;6|1,3,9^6|1,5,7;6|3,4,6;6|2,5,6;6|2,4,7;6|2,3,8";
			break;
		case 14:
			betcode = "6|2,3,9;6|0,6,8;6|3,5,6;6|1,4,9;6|0,5,9^6|2,5,7;6|1,5,8;6|2,4,8;6|3,4,7;6|1,6,7";
			break;
		case 15:
			betcode = "6|0,6,9;6|3,5,7;6|4,5,6;6|2,5,8;6|1,5,9^6|2,6,7;6|2,4,9;6|0,7,8;6|1,6,8;6|3,4,8";
			break;
		case 16:
			betcode = "6|3,4,9;6|4,5,7;6|3,6,7;6|3,5,8;6|1,7,8^6|1,6,9;6|2,6,8;6|2,5,9;6|0,7,9";
			break;
		case 17:
			betcode = "6|2,7,8;6|4,6,7;6|4,5,8;6|3,6,8;6|3,5,9^6|1,7,9;6|2,6,9;6|0,8,9";
			break;
		case 18:
			betcode = "6|3,7,8;6|2,7,9;6|3,6,9;6|4,6,8;6|5,6,7^6|4,5,9;6|1,8,9";
			break;
		case 19:
			betcode = "6|3,7,9;6|2,8,9;6|4,6,9;6|5,6,8;6|4,7,8";
			break;
		case 20:
			betcode = "6|3,8,9;6|5,6,9;6|5,7,8;6|4,7,9";
			break;
		case 21:
			betcode = "6|6,7,8;6|5,7,9;6|4,8,9";
			break;
		case 22:
			betcode = "6|5,8,9;6|6,7,9";
			break;
		case 23:
			betcode = "6|6,8,9";
			break;
		case 24:
			betcode = "6|7,8,9";
			break;

		default:
			break;
		}
		return betcode;
	}
	
	/**
	 * 获取3D的组3复式的注数
	 * @param arrayLength   用户输入的注码数组的长度
	 * @return              注数
	 */
	public static int getGroup3Multiple3DNumber(int arrayLength) {
		int zhushu = (int)zuhe(2,arrayLength)*2;
		return zhushu;
	}
	
	/**
	 * 获取3D的组6复式的注数
	 * @param arrayLength   用户输入的注码数组的长度
	 * @return              注数
	 */
	public static int getGroup6Multiple3DNumber(int arrayLength) {
		int zhushu = (int)zuhe(3,arrayLength);
		return zhushu;
	}
	

	public static int[] dir_3D = {1,3,6,10,15,21,28,36,45,55,63,69,73,75,75,73,69,63,55,45,36,28,21,15,10,6,3,1};
	public static int[] group3_3D = {1,2,1,3,3,3,4,5,4,5,5,4,5,5,4,5,5,4,5,4,3,3,3,1,2,1};
	public static int[] group6_3D = {1,1,2,3,4,5,7,8,9,10,10,10,10,9,8,7,5,4,3,2,1,1};
	
	
	//鍙岃壊鐞冨崟寮�
	public static String ssq_reg1 = "(0[1-9]|[1-4][0-9]|50)(0[1-9]|[12][0-9]|3[0-3]){6}[~](0[1-9]|1[0-6])";
	
	//鍙岃壊鐞冪孩澶嶅紡
	public static String ssq_reg2 = "(0[1-9]|[1-4][0-9]|50)[*](0[1-9]|[12][0-9]|3[0-3]){7,20}[~](0[1-9]|1[0-6])";
	
	//鍙岃壊鐞冭摑澶嶅紡
	public static String ssq_reg3 = "(0[1-9]|[1-4][0-9]|50)[*](0[1-9]|[12][0-9]|3[0-3]){6}[~](0[1-9]|1[0-6]){2,16}";
	
	//鍙岃壊鐞冪孩钃濆寮�
	public static String ssq_reg4 = "(0[1-9]|[1-4][0-9]|50)[*](0[1-9]|[12][0-9]|3[0-3]){7,20}[~](0[1-9]|1[0-6]){2,16}";
	
	//鍙岃壊鐞冪孩鑳嗚摑鍗曞紡
	public static String ssq_reg5 = "(0[1-9]|[1-4][0-9]|50)(0[1-9]|[12][0-9]|3[0-3]){1,5}[*](0[1-9]|[12][0-9]|3[0-3]){1,20}[~](0[1-9]|1[0-6])";
	
	//鍙岃壊鐞冪孩鑳嗚摑澶嶅紡
	public static String ssq_reg6 = "(0[1-9]|[1-4][0-9]|50)(0[1-9]|[12][0-9]|3[0-3]){1,5}[*](0[1-9]|[12][0-9]|3[0-3]){1,20}[~](0[1-9]|1[0-6]){2,16}";
	
	
	
	
	
	
	public static String threeD_reg1 = "(0[1-9]|[1-4][0-9]|50)(0[0-9]){3}";// 鍗曢�鍗曞紡
	public static String threeD_reg2 = "(0[1-9]|[1-4][0-9]|50)(0[0-9]){3}";// 缁勪笁鍗曞紡
	public static String threeD_reg3 = "(0[1-9]|[1-4][0-9]|50)(0[0-9]){3}";// 缁勫叚鍗曞紡

	public static String threeD_reg4 = "(0[1-9]|[1-4][0-9]|50)(0[1-9]|10){1}(0[0-9]){1,10}\\^(0[1-9]|10){1}(0[0-9]){1,10}\\^(0[1-9]|10){1}(0[0-9]){1,10}";// 鍗曢�鎸変綅鍖呭彿
	public static String threeD_reg5 = "(0[1-9]|[1-4][0-9]|50)(0[1-9]|10){1}(0[0-9]){2,10}";// 缁勪笁澶嶅紡
	public static String threeD_reg6 = "(0[1-9]|[1-4][0-9]|50)(0[1-9]){1}(0[0-9]){4,9}";// 缁勫叚澶嶅紡

	public static String threeD_reg7 = "(0[1-9]|[1-4][0-9]|50)([01][0-9]|2[0-7])";// 鍗曢�鍖呯偣
	public static String threeD_reg8 = "(0[1-9]|[1-4][0-9]|50)(0[1-9]|1[0-9]|2[0-6])";// 缁勪笁鍖呯偣
	public static String threeD_reg9 = "(0[1-9]|[1-4][0-9]|50)(0[3-9]|1[0-9]|2[0-4])";// 缁勫叚鍖呯偣

	public static String threeD_reg10 = "(0[1-9]|[1-4][0-9]|50)(0[0-9]){1,2}[*](0[0-9]){1,9}";// 鍗曢�鍗曡儐鎷�
	public static String threeD_reg11 = "(0[1-9]|[1-4][0-9]|50)(0[1-9]){1}(0[0-9]){3,9}";// 鍗曢�鍗曞寮�
	public static String threeD_reg12 = "(0[1-9]|[1-4][0-9]|50)(0[0-9]){1}[*](0[0-9]){2,9}";// 组三胆拖
	public static String threeD_reg13 = "(0[1-9]|[1-4][0-9]|50)(0[0-9]){1,2}[*](0[0-9]){2,9}";// 组六胆拖
	
	
	
	
	public static String qilecai_reg1 = "(0[1-9]|[1-4][0-9]|50)(0[1-9]|[12][0-9]|30){7}";
	public static String qilecai_reg2 = "(0[1-9]|[1-4][0-9]|50)[*](0[1-9]|[12][0-9]|30){7,16}";
	public static String qilecai_reg3 = "(0[1-9]|[1-4][0-9]|50)(0[1-9]|[12][0-9]|30){1,6}[*](0[1-9]|[12][0-9]|30){2,20}";
	
	
//========================七星彩===========================	
	
	//七星彩正在表达式
	public static String qixingcai_reg1 = "^[0-9]{7}$";
	
	/**
	 * 注码每位拆分一次，拆分为数组
	 * @param betcode
	 * @return
	 */
	public static int[] splitCodeToIntByOne(String code) {
		int codes[] = new int[code.length()];
		for(int i = 0,j=codes.length;i < j;i++) {
			codes[i] = Integer.parseInt(code.substring(i, i+1));
		}
		return codes;
	}
	
	/**
	 * 七星彩同时检查重复和排序
	 * @param code
	 * @return
	 */
	public static boolean checkRegAndDuplicateAndSortQixingcai(String code) {
		
		if(!code.matches(regex)) {
			return false;
		}
		
		//将字符串拆分为数组
		int[] codes = splitCodeToIntByOne(code);
		//判断是否重复
		
//		if(checkDuplicate(codes)==false) {
//			codes = null;
//			return false;
//		}
		
		//判断是否排序
		if(checkSort(codes)==false) {
			codes = null;
			return false;
		}
		codes = null;
		return true;
	}
	
	
//==========================任九场============================
	public static String renjiu_reg1 = "^[013#]{14}$";
	public static String renjiu_reg2 = "((#|[013]|0[13]|1[03]|3[01]|0(13|31)|1(30|03)|3(10|01)+),){13}(#|[013]|0[13]|1[03]|3[01]|0(13|31)|1(30|03)|3(10|01)){1}";
	
	
	
	public static boolean checkRenjiuZhumaNumStandrad(String betcode) {
		int total = 0;
		
		char[] betcodeChars = betcode.toCharArray();
		for(char c:betcodeChars) {
			if(c!='#') {
				total = total + 1;
			}
		}
		
		if(total==9) {
			return true;
		}
		return false;
	}
	
	
	public static boolean checkRenjiuZhumaNumFuShiAndZhuanJiu(String betcode) {
		int total = 0;
		
		String[] betcodes = betcode.split(",");
		for(String s:betcodes) {
			if(!"#".equals(s)) {
				total = total + 1;
			}
		}
		if(total>=9) {
			return true;
		}
		return false;
	}
	
	
	public static int totalNumber(String betcode) {
		int total = 0;
		
		String[] betcodes = betcode.split(",");
		for(String s:betcodes) {
			if(!"#".equals(s)) {
				total = total + 1;
			}
		}
		return total;
	}
	 
	
	public static boolean checkRenjiuZhumaNumDantuo(String betcode) {
		String[] base = betcode.split("\\$");
		String[] dans = base[0].split(",");
		String[] tuos = base[1].split(",");
		int total = 0;
		int dan = 0;
		for(int i = 0;i<14;i++) {
			if(!dans[i].equals("#")&&!tuos[i].equals("#")) {
				return false;
			}
			if(!dans[i].equals("#")||!tuos[i].equals("#")) {
				total = total + 1;
			}
			if(!dans[i].equals("#")) {
				dan = dan + 1;
			}
		}
		if(dan<9&&total>=9) {
			return true;
		}
		return false;
		
	}
	
	
	
	/**
	 * 组合的递归算法
	 * @param a 原始数据
	 * @param n 原始数据个数
	 * @param m 选择数据个数
	 * @param b 存放被选择的数据
	 * @param M 常量，选择数据个数
	 * @param list 存放计算结果
	 */
	public static void combine(int a[], int n, int m, int b[], final int M, List<int[]> list) {
		for (int i = n; i >= m; i--) {
			b[m - 1] = i - 1;
			if (m > 1)
				combine(a, i - 1, m - 1, b, M, list);
			else {
				int[] result = new int[M];
				for (int j = M - 1; j >= 0; j--) {
					result[j] = a[b[j]];
				}
				list.add(result);
			}
		}
	}
	
	
	public static boolean isZusan(int a,int b,int c) {
		int total = 0;
		if(a==b) {
			total = total + 1;
		}
		if(b==c) {
			total = total + 1;
		}
		if(a==c) {
			total = total + 1;
		}
		if(total == 1) {
			return true;
		}
		return false;
	}
	
	
	public static boolean isZuliu(int a,int b,int c) {
		int total = 0;
		if(a==b) {
			total = total + 1;
		}
		if(b==c) {
			total = total + 1;
		}
		if(a==c) {
			total = total + 1;
		}
		if(total == 0) {
			return true;
		}
		return false;
	}
	
	public static List<List<String>> getBetcodeCollection(List<String> betcodes,
			int select) {
		// 初始化原始数据
		int[] a = new int[betcodes.size()];
		for (int i = 0; i < a.length; i++) {
			a[i] = i;
		}
		// 接收数据
		int[] b = new int[select];

		List<int[]> list = new ArrayList<int[]>();

		// 进行组合
		BaseMath.combine(a, a.length, select, b, select, list);

		// 返回数据对象
		List<List<String>> reList = new ArrayList<List<String>>();
		for (int[] result : list) {
			List<String> codeList = new ArrayList<String>();
			for (int p : result) {
				codeList.add(betcodes.get(p));
			}
			reList.add(codeList);
		}

		return reList;
	}

}
