package com.yao.util;

/**
 * 数学工具类
 */
public class MathUtil {

	/**
	 * 格式化数字  保留两位
	 * @param n
	 * @return
	 */
	public static float format2Bit(float n){
		java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
		String result = df.format(n);
		return Float.parseFloat(result);
	}
	
	public static void main(String[] args) {
		System.out.println(format2Bit(2f));
	}
}
