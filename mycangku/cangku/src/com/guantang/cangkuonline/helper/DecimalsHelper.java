package com.guantang.cangkuonline.helper;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

import com.guantang.cangkuonline.database.DataBaseHelper;
import com.guantang.cangkuonline.database.DataBaseOperateMethod;

import android.R.integer;
import android.content.Context;
import android.text.Editable;

public class DecimalsHelper {
	
	public static boolean stringIsNumBer(String string){
        if(string != null){
            if(string.matches("^(-?\\d+)(\\.\\d+)?$")){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
	
	
	/*
	 * �۸�С��λ�������û�����þ�Ĭ����λС�����������8λ����8λ��
	 *	
	 * number ��С��������λ��
	 * */
	public static String Transfloat(double f,int number){
		if(Math.round(f)-f==0){
			   return String.valueOf((long)f);
			  }else{
				  DecimalFormat df = null;
				  if(number>8){
					  df = new DecimalFormat("0000000000.00000000");
				  }else{
					  StringBuilder dexString = new StringBuilder("0000000000.");
					  for(int i=1;i<=number;i++){
						  dexString.append("0");
					  }
					  df = new DecimalFormat(dexString.toString());
				  }
				  f = Double.parseDouble(df.format(f));
				  return String.valueOf(f);
			  }
	}
	
	/**
	 * �ж�����������ַ����ķ�Χ�Ƿ���decimal(18, 8)��Χ�ڣ�����������Χ�ھͷ���true,��������������Χ�ھͷ���false
	 * @param numberString ����������ַ���
	 * 
	 * */
	public static Boolean NumBerStringIsFormat(String numberString){
		if(!numberString.isEmpty()){
			int pos = numberString.length() - 1;
			int position = numberString.toString().indexOf(".");

			if(pos-position>8 && position != -1){
				return false;
			}
			if(position==-1 && numberString.toString().length()>10){
				char[] numberStrings =numberString.toString().toCharArray();
				if(numberStrings.length>10 && !String.valueOf(numberStrings[10]).equals(".")){//�����11λ����С�����ɾ��
					return false;
				}
			}
		}
		return true;
//		if(!numberString.isEmpty()){
//			String[] numberArray = numberString.split(".");
//			int i = numberArray.length;
//			if(numberArray.length==2){
//				return (numberArray[0].length()<=10 && numberArray[1].length()<=2);
//			}else if(numberArray.length==1){
//				return numberArray[0].length()<=11;
//			}else if(numberArray.length==0){
//				return numberString.length()<=10;
//			}
//		}else{
//			return true;
//		}
//		return false;
	}
}