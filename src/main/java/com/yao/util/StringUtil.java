package com.yao.util;

/**
 * 字符串工具类
 */
public class StringUtil {

	/**
	 * 判断是否是空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str==null||"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断是否不是空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		if((str!=null)&&!"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 生成四位编号
	 * @param code
	 * @return
	 */
	public static String formatCode(String code){
		int length=code.length();
		Integer num=Integer.parseInt(code.substring(length-4,length))+1;
		String codeNum=num.toString();
		int codeLength=codeNum.length();
		for(int i=4;i>codeLength;i--){
			codeNum="0"+codeNum;
		}
		return codeNum;
	}
	
	public static void main(String[] args) {
		System.out.println(formatCode("JH201712010002"));
	}
}
