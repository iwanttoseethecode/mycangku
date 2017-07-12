package com.guantang.cangkuonline.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.guantang.cangkuonline.application.MyApplication;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseOperateMethod {
	Context context;
	public DataBaseOperateMethod(Context mcontext){
		this.context=mcontext;
	}
	public boolean check_DH(String dh){
		
		DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
		Cursor c = db.rawQuery("select * from "+DataBaseHelper.TB_MoveM+" where "+DataBaseHelper.MVDH+
				"='"+dh+"'",null);
		if(c.moveToFirst()==true){
			c.close();
			dataBaseManager.closeDataBase();
			return false;
		}else{
			c.close();
			dataBaseManager.closeDataBase();
			return true;
		}
	}
	public boolean check_DDDH(String dh){
		
		DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
		Cursor c = db.rawQuery("select * from "+DataBaseHelper.TB_Order+" where "+DataBaseHelper.OrderIndex+
				"='"+dh+"'",null);
		if(c.moveToFirst()==true){
			c.close();
			dataBaseManager.closeDataBase();
			return false;
		}else{
			c.close();
			dataBaseManager.closeDataBase();
			return true;
		}
	}
	public void Add_DW(int type,String name,String addr,String fax,String yb,String net,String lxr,String phone,String tel
			,String qq,String email,String bz){
		
		DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
		db.execSQL("insert into "+DataBaseHelper.TB_Company+" ( "+DataBaseHelper.DWtype+","+DataBaseHelper.DWName+","
		        +DataBaseHelper.ADDR+","+DataBaseHelper.FAX+","+DataBaseHelper.YB+","+DataBaseHelper.Net+","+
				DataBaseHelper.LXR+","+DataBaseHelper.Phone+","+DataBaseHelper.TEL+","+DataBaseHelper.QQ+","+
		        DataBaseHelper.Email+","+DataBaseHelper.BZ+" ) values ( '"+type+"','"+name+"','"+addr+"','"+fax+"','"
				+yb+"','"+net+"','"+lxr+"','"+phone+"','"+tel+"','"+qq+"','"+email+"','"+bz+"')");
		dataBaseManager.closeDataBase();
	}
	public List<Map<String, Object>> search_Info(String tb,String[] s){
		String str="";
		for(int i=0;i<s.length;i++){
			if(i!=s.length-1){
				str=str+s[i]+",";
			}else{
				str=str+s[i];
			}
		}
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		
		DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
		Cursor c = db.rawQuery("select "+str+" from "+tb,null);
		 c.moveToFirst();
		 while (!c.isAfterLast()){
			 HashMap<String, Object> map = new HashMap<String, Object>();
			 for(int j=0;j<s.length;j++){
				 map.put(s[j], c.getString(j));
			 }
			 list.add(map);
			 c.moveToNext();
		 }
		 c.close();
		 dataBaseManager.closeDataBase();
		return list;
	}
	public List<Map<String, Object>> Gt_cp(int type,String[] s){
		String str="";
		for(int i=0;i<s.length;i++){
			if(i!=s.length-1){
				str=str+s[i]+",";
			}else{
				str=str+s[i];
			}
		}
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		
		DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
		Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_Company+" where "+DataBaseHelper.DWtype+"='"
				+type+"'",null);
		 c.moveToFirst();
		 while (!c.isAfterLast()){
			 HashMap<String, Object> map = new HashMap<String, Object>();
			 for(int j=0;j<s.length;j++){
				 map.put(s[j], c.getString(j));
			 }
			 list.add(map);
			 c.moveToNext();
		 }
		 c.close();
		 dataBaseManager.closeDataBase();
		return list;
	}
	public List<Map<String, Object>> Gt_cp(String[] s){
		String str="";
		for(int i=0;i<s.length;i++){
			if(i!=s.length-1){
				str=str+s[i]+",";
			}else{
				str=str+s[i];
			}
		}
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		
		DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
		Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_Company,null);
		 c.moveToFirst();
		 while (!c.isAfterLast()){
			 HashMap<String, Object> map = new HashMap<String, Object>();
			 for(int j=0;j<s.length;j++){
				 map.put(s[j], c.getString(j));
			 }
			 list.add(map);
			 c.moveToNext();
		 }
		 c.close();
		 dataBaseManager.closeDataBase();
		return list;
	}
	public List<Map<String, Object>> Gt_ck(String[] s){
		String str="";
		for(int i=0;i<s.length;i++){
			if(i!=s.length-1){
				str=str+s[i]+",";
			}else{
				str=str+s[i];
			}
		}
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		
		DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
		Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_CK,null);
		 c.moveToFirst();
		 while (!c.isAfterLast()){
			 HashMap<String, Object> map = new HashMap<String, Object>();
			 for(int j=0;j<s.length;j++){
				 map.put(s[j], c.getString(j));
			 }
			 list.add(map);
			 c.moveToNext();
		 }
		 c.close();
		 dataBaseManager.closeDataBase();
		return list;
	}
	public List<Map<String, Object>> Gt_ckkc_byhpid(String hpid,String[] s){
		String str="";
		for(int i=0;i<s.length;i++){
			if(i!=s.length-1){
				str=str+s[i]+",";
			}else{
				str=str+s[i];
			}
		}
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		
		DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
		Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_CKKC+" where "+DataBaseHelper.HPID
				+"='"+hpid+"'",null);
		 c.moveToFirst();
		 while (!c.isAfterLast()){
			 HashMap<String, Object> map = new HashMap<String, Object>();
			 for(int j=0;j<s.length;j++){
				 map.put(s[j], c.getString(j));
			 }
			 list.add(map);
			 c.moveToNext();
		 }
		 c.close();
		 dataBaseManager.closeDataBase();
		return list;
	}
	
	public List<Map<String,Object>> get_ckkcAndckmc(String hpid){
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
		Cursor c = db.rawQuery("select  a.ckid,a.kcsl,b.ckmc from "+DataBaseHelper.TB_CKKC+" as a left join "+DataBaseHelper.TB_CK+" as b on a.ckid = b.id where a.hpid = '"+hpid+"'", null);
		while(c.moveToNext()){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ckid", c.getString(c.getColumnIndex("ckid")));
			map.put("kcsl", c.getDouble(c.getColumnIndex("kcsl")));
			map.put("ckmc", c.getString(c.getColumnIndex("ckmc")));
			list.add(map);
		}
		c.close();
		dataBaseManager.closeDataBase();
		return list;
	}
	
	public String Gt_ckkc(String hpid,int ckid){
		String str;
		
		DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
		Cursor c = db.rawQuery("select kcsl from "+DataBaseHelper.TB_CKKC+" where "+DataBaseHelper.HPID
				+"='"+hpid+"' and "+DataBaseHelper.CKID+"='"+ckid+"'",null);
		 if(c.moveToFirst()){
			 str=c.getString(0);
		 }else{
			 str=null;
		 }
		 c.close();
		 dataBaseManager.closeDataBase();
		return str;
	}
	public String Gt_ck_name(String ckid){
		String str;
		
		DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
		Cursor c = db.rawQuery("select "+DataBaseHelper.CKMC+" from "+DataBaseHelper.TB_CK+" where "+
				DataBaseHelper.ID+"='"+ckid+"'",null);
		 if(c.moveToFirst()){
			 str=c.getString(0);
		 }else{
			 str="";
		 }
		 c.close();
		 dataBaseManager.closeDataBase();
		return str;
	}
	public List<Map<String, Object>> Gt_cp(int type,String[] s,String id){
		String str="";
		for(int i=0;i<s.length;i++){
			if(i!=s.length-1){
				str=str+s[i]+",";
			}else{
				str=str+s[i];
			}
		}
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		
		DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
		Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_Company+" where "+DataBaseHelper.DWtype+"='"
				+type+"' and "+DataBaseHelper.ID+"='"+id+"'",null);
		 c.moveToFirst();
		 while (!c.isAfterLast()){
			 HashMap<String, Object> map = new HashMap<String, Object>();
			 for(int j=0;j<s.length;j++){
				 map.put(s[j], c.getString(j));
			 }
			 list.add(map);
			 c.moveToNext();
		 }
		 c.close();
		 dataBaseManager.closeDataBase();
		return list;
	}
	
	
	
	public List<Map<String, Object>> Gt_cp(String[] s,String id){
		String str="";
		for(int i=0;i<s.length;i++){
			if(i!=s.length-1){
				str=str+s[i]+",";
			}else{
				str=str+s[i];
			}
		}
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		
		DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
		Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_Company+" where "
				+DataBaseHelper.ID+"='"+id+"'",null);
		 c.moveToFirst();
		 while (!c.isAfterLast()){
			 HashMap<String, Object> map = new HashMap<String, Object>();
			 for(int j=0;j<s.length;j++){
				 map.put(s[j], c.getString(j));
			 }
			 list.add(map);
			 c.moveToNext();
		 }
		 c.close();
		 dataBaseManager.closeDataBase();
		return list;
	}
	public String Gt_cp_id(){
		String str = null;
		
		DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
		Cursor c = db.rawQuery("select "+DataBaseHelper.ID+" from "+DataBaseHelper.TB_Company+" order by "+
				DataBaseHelper.ID+" desc",null);
		c.moveToFirst();
		str=String.valueOf(Integer.parseInt(c.getString(0)));
		c.close();
		dataBaseManager.closeDataBase();
		return str;
	}
	public List<Map<String, Object>> Gt_cp(int type,String[] s,String id,SQLiteDatabase db){
		String str="";
		for(int i=0;i<s.length;i++){
			if(i!=s.length-1){
				str=str+s[i]+",";
			}else{
				str=str+s[i];
			}
		}
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_Company+" where "+DataBaseHelper.DWtype+"='"
				+type+"' and "+DataBaseHelper.ID+"='"+id+"'",null);
		 c.moveToFirst();
		 while (!c.isAfterLast()){
			 HashMap<String, Object> map = new HashMap<String, Object>();
			 for(int j=0;j<s.length;j++){
				 map.put(s[j], c.getString(j));
			 }
			 list.add(map);
			 c.moveToNext();
		 }
		 c.close();
		return list;
	}
	public List<Map<String, Object>> queryList_CP(int type,String[] s,String text){
		String str="";
		for(int i=0;i<s.length;i++){
			if(i!=s.length-1){
				str=str+s[i]+",";
			}else{
				str=str+s[i];
			}
		}
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		
		DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
		Cursor c;
		try{
			c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_Company+" where "
					+DataBaseHelper.DWName+" like '%"+text+"%'",null);
		}catch(Exception e){
			c=null;
		}
		 c.moveToFirst();
		 while (!c.isAfterLast()){
			 HashMap<String, Object> map = new HashMap<String, Object>();
			 for(int j=0;j<s.length;j++){
				 map.put(s[j], c.getString(j));
			 }
			 list.add(map);
			 c.moveToNext();
		 }
		 c.close();
		 dataBaseManager.closeDataBase();
		return list;
	}
	public List<Map<String, Object>> Gt_Conf(String[] s){
		String str="";
		for(int i=0;i<s.length;i++){
			if(i!=s.length-1){
				str=str+s[i]+",";
			}else{
				str=str+s[i];
			}
		}
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		
		DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
		Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_Conf,null);
		 c.moveToFirst();
		 while (!c.isAfterLast()){
			 HashMap<String, Object> map = new HashMap<String, Object>();
			 for(int j=0;j<s.length;j++){
				 map.put(s[j], c.getString(j));
			 }
			 list.add(map);
			 c.moveToNext();
		 }
		 c.close();
		 dataBaseManager.closeDataBase();
		return list;
	}
/*
 * ����tb_conf����GID��ѯ��¼
 * */
public List<Map<String, Object>> Gt_ConfByGID(String searchString,String[] s){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_Conf+" where "+DataBaseHelper.GID+" = '"+searchString+"'",null);
	 c.moveToFirst();
	 while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 map.put(s[j], c.getString(j));
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	 c.close();
	 dataBaseManager.closeDataBase();
	return list;
}
	
	/**ͬ���ֿ���Ϣ�ķ���
	 * */
public void insert_into1(String tb,String[] s1,String[] s2,SQLiteDatabase db){
		String str1="",str2="'";
		for(int i=0;i<s1.length;i++){
			if(i!=s1.length-1){
				str1=str1+s1[i]+",";
			}else{
				str1=str1+s1[i];
			}
		}
		for(int i=0;i<s2.length;i++){
			if(i!=s2.length-1){
				str2=str2+s2[i]+"','";
			}else{
				str2=str2+s2[i]+"'";
			}
		}
		db.execSQL("insert into "+tb+" ("+str1+") values ("+str2+")");
	}

public void insert_into(String tb,String[] s1,String[] s2){
		String str1="",str2="'";
		for(int i=0;i<s1.length;i++){
			if(i!=s1.length-1){
				str1=str1+s1[i]+",";
			}else{
				str1=str1+s1[i];
			}
		}
		for(int i=0;i<s2.length;i++){
			if(i!=s2.length-1){
				str2=str2+s2[i]+"','";
			}else{
				str2=str2+s2[i]+"'";
			}
		}
		DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
		db.execSQL("insert into "+tb+" ("+str1+") values ("+str2+")");
		dataBaseManager.closeDataBase();
	}

public void insert_ckkc(String hpid,int ckid,float kcsl){
	
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("insert into "+DataBaseHelper.TB_CKKC+" (hpid,ckid,kcsl) values ("+hpid+","+ckid+","+kcsl+")");
	dataBaseManager.closeDataBase();
}

public String search_DJID(String dh){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select "+DataBaseHelper.HPM_ID+" from "+DataBaseHelper.TB_MoveM+" where "+
			DataBaseHelper.MVDH+"='"+dh+"'",null);
	c.moveToFirst();
	String id=c.getString(0);
	c.close();
	dataBaseManager.closeDataBase();
	return id;	
}

public String GtMax_DJID(){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select max("+DataBaseHelper.HPM_ID+") from "+DataBaseHelper.TB_MoveM,null);
	c.moveToFirst();
	String id=c.getString(0);
	c.close();
	dataBaseManager.closeDataBase();
	return id;
}

/*
 * ��ѯ�Ƿ���δ��ɵĵ���(��⡢���⡢�̵�)
 * 
 * */
public String searchUncompleteDJ(int mType){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select "+DataBaseHelper.HPM_ID+" from "+DataBaseHelper.TB_MoveM+" where "+DataBaseHelper.DJType+
			" = 0 and "+DataBaseHelper.mType+" = "+mType,null);
	String djid="";
	if(c.moveToFirst()){
		djid=c.getString(c.getColumnIndex(DataBaseHelper.HPM_ID));
	}
	c.close();
	dataBaseManager.closeDataBase();
	return djid;
}

/*
 * ��ѯ����״̬
 * 
 * Status -5 ����δ���,-4 δ�ϴ�,-3 ���ϴ�, 0 �����, 1 δͨ�����, 2 ������, 3 ���ֳ��� , 5 �ѳ��� , 7 ��������
 */
public String searchUncompleteDD(int dirc){
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select "+DataBaseHelper.ID+" from "+DataBaseHelper.TB_Order+" where "+
	DataBaseHelper.Status+" = -5 and "+DataBaseHelper.dirc+" = "+dirc, null);
	
	String ddid="";
	if(c.moveToFirst()){
		ddid = c.getString(c.getColumnIndex(DataBaseHelper.ID));
	}
	return ddid;
}

public String GtMax_DDDJID(){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select max("+DataBaseHelper.ID+") from "+DataBaseHelper.TB_Order,null);
	c.moveToFirst();
	String id=c.getString(0);
	c.close();
	dataBaseManager.closeDataBase();
	return id;
}

public String GtMax_DBDJID(){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select max("+DataBaseHelper.ID+") from "+DataBaseHelper.TB_TRANSM,null);
	c.moveToFirst();
	String id=c.getString(0);
	c.close();
	dataBaseManager.closeDataBase();
	return id;
}

public void Update_HPKC(String id,String num){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("update "+DataBaseHelper.TB_HP+" set "+DataBaseHelper.CurrKC+"='"+num+
			"' where "+DataBaseHelper.ID+"='"+id+"'");
	dataBaseManager.closeDataBase();
}
public void Update_CKKC(String hpid,int ckid,float num){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("update "+DataBaseHelper.TB_CKKC+" set "+DataBaseHelper.KCSL+"="+num+
			" where "+DataBaseHelper.HPID+"='"+hpid+"' and "+DataBaseHelper.CKID+"="+ckid);
	dataBaseManager.closeDataBase();
}
public Map<String, Object> Djhp_Sum(String djid){
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select count(mid) as num from "+DataBaseHelper.TB_MoveD+" where "+DataBaseHelper.MID+" = '"+djid+"'", null);
	c.moveToFirst();
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("num", c.getString(c.getColumnIndex("num")));
	c.close();
	dataBaseManager.closeDataBase();
	return map;
}

public Map<String, Object> DDhp_Sum(String ddid) {
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select count(id) as num from "
			+ DataBaseHelper.TB_OrderDetail + " where " + DataBaseHelper.orderID
			+ " = '" + ddid + "'", null);
	c.moveToFirst();
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("num", c.getString(c.getColumnIndex("num")));
	return map;
}

public Map<String, Object> diaoBohp_Sum(String djid) {
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select count(id) as num from "
			+ DataBaseHelper.TB_TRANSD + " where " + DataBaseHelper.MID
			+ " = '" + djid + "'", null);
	c.moveToFirst();
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("num", c.getString(c.getColumnIndex("num")));
	return map;
}

public List<Map<String, Object>> search_DJ_from(String date,String[] s,int flag){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	String time = date+" 00:00:00";
	Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveM+" where "+DataBaseHelper.MVDT+">'"
			+time+"' and "+DataBaseHelper.mType+"="+flag+" order by "+
					DataBaseHelper.MVDT+" desc",null);
	c.moveToFirst();
	while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 map.put(s[j], c.getString(j));
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	c.close();
	 dataBaseManager.closeDataBase();
	return list;
}
public List<Map<String, Object>> search_DJ_date(String date,String[] s,int flag){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	String time = date+" 00:00:00";
	String time2 = date +" 24:00:00";
	Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveM+" where "+DataBaseHelper.MVDT+">'"
			+time+"'and "+DataBaseHelper.MVDT+"<'"+time2+"' and "+DataBaseHelper.mType+"="+flag+" order by "+
			DataBaseHelper.MVDT+" desc",null);
	c.moveToFirst();
	while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 map.put(s[j], c.getString(j));
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	c.close();
	dataBaseManager.closeDataBase(); 
	return list;
}
public String Gt_DJ_count(int type){
	String str,time;
	SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
	time=formatter.format(new Date(System.currentTimeMillis()));
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select count(hpm_id) from "+DataBaseHelper.TB_MoveM+" where "+
	DataBaseHelper.mType+"='"+type+"' and "+DataBaseHelper.MVDT+"<'"+time+" 23:59:59' and "
	+DataBaseHelper.MVDT+">='"+time+"' and "+DataBaseHelper.MVDH+"!=''",null);
	if(c.moveToFirst()){
		str=c.getString(0);
	}else{
		str="-1";
	}
	c.close();
	dataBaseManager.closeDataBase();
	return str;
}
public String Gt_unDJ_count(int type){
	String str;
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select count(hpm_id) from "+DataBaseHelper.TB_MoveM+" where "+
	DataBaseHelper.DJType+"='"+type+"' and "+DataBaseHelper.MVDH+"!=''",null);
	if(c.moveToFirst()){
		str=c.getString(0);
	}else{
		str="-1";
	}
	c.close();
	dataBaseManager.closeDataBase();
	return str;
}

public List<Map<String, Object>> search_DJ(String wherestr,String[] s,int ckid){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c=null;
	if(ckid==-1){
		c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveM+wherestr,null);
	}else{
		wherestr = wherestr+DataBaseHelper.CKID+" = "+ckid+" order by lrsj desc";
		c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveM+wherestr,null);
	}
	c.moveToFirst();
	while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 map.put(s[j], c.getString(j));
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	c.close();
	dataBaseManager.closeDataBase(); 
	return list;
}

public String search_todayDJnum(int mType,String time,int ckid){
	
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = null;
	if(ckid == -1){
		c = db.rawQuery("select count(mType) as num from "+DataBaseHelper.TB_MoveM+" where "+DataBaseHelper.mType+"='"+mType+"' and "+
				DataBaseHelper.MVDT+"='"+time+"'",null);
	}else{
		c = db.rawQuery("select count(mType) as num from "+DataBaseHelper.TB_MoveM+" where "+DataBaseHelper.mType+"='"+mType+"' and "+
				DataBaseHelper.MVDT+"='"+time+"' and "+DataBaseHelper.CKID+" = "+ckid,null);
	}
	
	String num="";
	if(c!=null){
		c.moveToFirst();
		num = c.getString(0);
	}
	c.close();
	dataBaseManager.closeDataBase(); 
	return num;
}

public List<Map<String, Object>> search_DDDJ(String wherestr,String[] s){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_Order+wherestr,null);
	c.moveToFirst();
	while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 map.put(s[j], c.getString(j));
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	c.close();
	dataBaseManager.closeDataBase(); 
	return list;
}
public List<Map<String, Object>> search_DJ_from(String date,String[] s,int flag,int op){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	String time = date;
	Cursor c = null;
	switch(op){
	case 0:
		c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveM+" where "+DataBaseHelper.MVDT+">'"
				+time+"' and "+DataBaseHelper.mType+"="+flag+" order by "+
						DataBaseHelper.MVDT+" desc",null);
		break;
	case -1:
		c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveM+" where "+DataBaseHelper.MVDT+">'"
				+time+"' and "+DataBaseHelper.mType+"="+flag+" and "+DataBaseHelper.DJType+"=-1"+" order by "+
						DataBaseHelper.MVDT+" desc",null);
		break;
	case 1:
		c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveM+" where "+DataBaseHelper.MVDT+">'"
				+time+"' and "+DataBaseHelper.mType+"="+flag+" and "+DataBaseHelper.DJType+"=1"+" order by "+
						DataBaseHelper.MVDT+" desc",null);
		break;
		default:
			break;
	}
	c.moveToFirst();
	while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 map.put(s[j], c.getString(j));
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	c.close();
	dataBaseManager.closeDataBase();
	return list;
}
public List<Map<String, Object>> search_DJ_from(String[] s,String dt1,String dt2,int flag,int op){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
//	dt1 = dt1+" 00:00:00";
	dt2 = dt2+" 24:00:00";
	Cursor c = null;
	switch(op){
	case 0:
		c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveM+" where "+DataBaseHelper.MVDT+">='"
				+dt1+"' and "+DataBaseHelper.MVDT+"<'"+dt2+"' and "+DataBaseHelper.mType+"="+flag+
				" and "+DataBaseHelper.MVDH+"!=''"+" order by "+DataBaseHelper.MVDT+" desc",null);
		break;
	case -1:
		c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveM+" where "+DataBaseHelper.MVDT+">='"
				+dt1+"' and "+DataBaseHelper.MVDT+"<'"+dt2+"' and "+DataBaseHelper.mType+"="+flag+" and "
				+DataBaseHelper.DJType+"=-1"+" and "+DataBaseHelper.MVDH+"!=''"+" order by "+DataBaseHelper.MVDT+" desc",null);
		break;
	case 1:
		c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveM+" where "+DataBaseHelper.MVDT+">='"
				+dt1+"' and "+DataBaseHelper.MVDT+"<'"+dt2+"' and "+DataBaseHelper.mType+"="+flag+" and "
				+DataBaseHelper.DJType+"=1"+" and "+DataBaseHelper.MVDH+"!=''"+" order by "+DataBaseHelper.MVDT+" desc",null);
		break;
	case 2:
		c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveM+" where "+DataBaseHelper.MVDT+">='"
				+dt1+"' and "+DataBaseHelper.MVDT+"<'"+dt2+"' and "+DataBaseHelper.DJType+"=1"+" and "+
				DataBaseHelper.MVDH+"!=''"+" order by "+DataBaseHelper.MVDT+" desc",null);
		break;
		default:
			break;
	}
	c.moveToFirst();
	while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 map.put(s[j], c.getString(j));
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	c.close();
	dataBaseManager.closeDataBase();
	return list;
}
public List<Map<String, Object>> search_DJ_date(String date,String[] s,int flag,int op){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	String time = date;
	String time2 = date +" 24:00:00";
	Cursor c = null;
	switch(op){
	case 0:
		c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveM+" where "+DataBaseHelper.MVDT+">'"
				+time+"'and "+DataBaseHelper.MVDT+"<'"+time2+"' and "+DataBaseHelper.mType+"="+flag+" order by "+
				DataBaseHelper.MVDT+" desc",null);
		break;
	case -1:
		c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveM+" where "+DataBaseHelper.MVDT+">'"
				+time+"'and "+DataBaseHelper.MVDT+"<'"+time2+"' and "+DataBaseHelper.mType+"="+flag+" and "
				+DataBaseHelper.DJType+"=-1"+" order by "+
				DataBaseHelper.MVDT+" desc",null);
		break;
	case 1:
		c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveM+" where "+DataBaseHelper.MVDT+">'"
				+time+"'and "+DataBaseHelper.MVDT+"<'"+time2+"' and "+DataBaseHelper.mType+"="+flag+" and "
				+DataBaseHelper.DJType+"=1"+" order by "+
				DataBaseHelper.MVDT+" desc",null);
		break;
		default:
			break;
	}
	c.moveToFirst();
	while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 map.put(s[j], c.getString(j));
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	c.close();
	 dataBaseManager.closeDataBase(); 
	return list;
}

public void Update_DJtype(String djid,int type){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("update "+DataBaseHelper.TB_MoveM+" set "+DataBaseHelper.DJType+"="+type+" where "
			+DataBaseHelper.HPM_ID+" = '"+djid+"'");
	dataBaseManager.closeDataBase();
}

public void Update_DDDJtype(String djid, int type) {
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("update " + DataBaseHelper.TB_Order + " set "
			+ DataBaseHelper.Status + " = "+type+ " where " + DataBaseHelper.ID
			+ "='" + djid+"'");
	dataBaseManager.closeDataBase();
}

public void Update_DDDJtype(String djid){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("update "+DataBaseHelper.TB_Order+" set "+DataBaseHelper.Status+"=-2"+" where "
			+DataBaseHelper.ID+"='"+djid+"'");
	dataBaseManager.closeDataBase();
}
public void Update_DJtype_back(String djid){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("update "+DataBaseHelper.TB_MoveM+" set "+DataBaseHelper.DJType+"=-1"+" where "
			+DataBaseHelper.HPM_ID+"='"+djid+"'");
	dataBaseManager.closeDataBase();
}

public void UpdateCurrKc_byhpid(String hpid,float msl,int mvdirect){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	if(mvdirect==1){
		db.execSQL("update "+DataBaseHelper.TB_HP+" set "+DataBaseHelper.CurrKC+" = "+DataBaseHelper.CurrKC+" + "+msl+
				" where "+DataBaseHelper.ID+ "='"+hpid+"'");
	}else if(mvdirect==2){
		db.execSQL("update "+DataBaseHelper.TB_HP+" set "+DataBaseHelper.CurrKC+" = "+DataBaseHelper.CurrKC+" - "+msl+
				" where "+DataBaseHelper.ID+ "='"+hpid+"'");
	}
	dataBaseManager.closeDataBase();
}

public List<Map<String, Object>> Gt_Moved(String djid,String[] s){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveD+" where "+DataBaseHelper.MID+"='"
			+djid+"'",null);
	c.moveToFirst();
	while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 if(s[j].equals(DataBaseHelper.ATKC)||s[j].equals(DataBaseHelper.BTKC)||s[j].equals(DataBaseHelper.MSL)){
				 map.put(s[j], String.valueOf(c.getDouble(c.getColumnIndex(s[j]))));
			 }else{
				 map.put(s[j], c.getString(c.getColumnIndex(s[j])));
			 }
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	 c.close();
	 dataBaseManager.closeDataBase();
	return list;
}

public List<Map<String, Object>> Gt_Transd(String djid,String[] s){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_TRANSD+" where "+DataBaseHelper.MID+"='"
			+djid+"'",null);
	c.moveToFirst();
	while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 if(s[j].equals(DataBaseHelper.ATKC)||s[j].equals(DataBaseHelper.BTKC)||s[j].equals(DataBaseHelper.MSL)){
				 map.put(s[j], String.valueOf(c.getDouble(c.getColumnIndex(s[j]))));
			 }else{
				 map.put(s[j], c.getString(c.getColumnIndex(s[j])));
			 }
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	 c.close();
	 dataBaseManager.closeDataBase();
	return list;
}

public List<Map<String, Object>> Gt_Transm(String djid,String[] s){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_TRANSM+" where "+DataBaseHelper.ID+"='"
			+djid+"'",null);
	c.moveToFirst();
	while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 map.put(s[j], c.getString(j));
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	c.close();
	dataBaseManager.closeDataBase();
	return list;
}

public List<Map<String, Object>> GetckrkMoved_byIdTime(String hpid,String[] s,String fromdate,String todate){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveD+" left join "+DataBaseHelper.TB_MoveM+" on mid = hpm_id where "+DataBaseHelper.HPID+"='"
			+hpid+"' and "+DataBaseHelper.MVDDT+">='"+fromdate+" 00:00:00' and "+DataBaseHelper.MVDDT+"<'"
			+todate+" 23:59:59'  order by hpd_id ",null);
	c.moveToFirst();
	while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 map.put(s[j], c.getString(j));
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	 c.close();
	 dataBaseManager.closeDataBase();
	return list;
}

public String Gt_Moved_znum(String djid){
	String str;
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select sum(msl) from "+DataBaseHelper.TB_MoveD+
			" where "+DataBaseHelper.MID+"='"+djid+"'",null);
	if(c.moveToFirst()){
		str=c.getString(0);
	}else{
		str="-1";
	}
	c.close();
	dataBaseManager.closeDataBase();
	return str;
}
public String Gt_Moved_zje(String djid){
	String str;
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select sum(zj) from "+DataBaseHelper.TB_MoveD+
			" where "+DataBaseHelper.MID+"='"+djid+"'",null);
	if(c.moveToFirst()){
		str=c.getString(0);
	}else{
		str="-1";
	}
	c.close();
	dataBaseManager.closeDataBase();
	return str;
}
public List<Map<String, Object>> Gt_Moved_details(String djid,String[] s){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveD+" as a,"+DataBaseHelper.TB_HP+
			" as b"+" where a.hpid=b.id and "+DataBaseHelper.MID+"='"
			+djid+"'",null);
	c.moveToFirst();
	while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 map.put(s[j], c.getString(j));
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	 c.close();
	 dataBaseManager.closeDataBase();
	return list;
}
public List<Map<String, Object>> Gt_Order(String djid,String[] s){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_Order+" where "+DataBaseHelper.ID+"='"
			+djid+"'",null);
	c.moveToFirst();
	while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 map.put(s[j], c.getString(j));
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	c.close();
	dataBaseManager.closeDataBase();
	return list;
}
public List<Map<String, Object>> Gt_OrderDetails(String djid,String[] s){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_OrderDetail+" where "+DataBaseHelper.orderID+"='"
			+djid+"'",null);
	c.moveToFirst();
	while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 map.put(s[j], c.getString(j));
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	c.close();
	dataBaseManager.closeDataBase();
	return list;
}
public List<Map<String, Object>> Gt_Movem(String djid,String[] s){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveM+" where "+DataBaseHelper.HPM_ID+"='"
			+djid+"'",null);
	c.moveToFirst();
	while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 map.put(s[j], c.getString(j));
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	c.close();
	dataBaseManager.closeDataBase();
	return list;
}

public List<Map<String, Object>> Gt_Movem2(String djid,String[] s,String[] s2){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveM+" where "+DataBaseHelper.HPM_ID+"='"
			+djid+"'",null);
	c.moveToFirst();
	while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 map.put(s2[j], c.getString(j));
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	c.close();
	dataBaseManager.closeDataBase();
	return list;
}

public List<Map<String, Object>> Gt_Movem(String djid,String[] s,SQLiteDatabase db){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveM+" where "+DataBaseHelper.HPM_ID+"='"
			+djid+"'",null);
	c.moveToFirst();
	while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 map.put(s[j], c.getString(j));
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	c.close();
	return list;
}
public List<Map<String, Object>> Gt_Moved(String djid,String hpid,String[] s){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveD+" where "+DataBaseHelper.MID+"='"
			+djid+"' and "+DataBaseHelper.HPID+"='"+hpid+"'",null);
	c.moveToFirst();
	while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 if(s[j].equals(DataBaseHelper.ATKC)||s[j].equals(DataBaseHelper.BTKC)||s[j].equals(DataBaseHelper.MSL)){
				 map.put(s[j], String.valueOf(c.getDouble(c.getColumnIndex(s[j]))));
			 }else{
				 map.put(s[j], c.getString(c.getColumnIndex(s[j])));
			 }
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	c.close();
	dataBaseManager.closeDataBase(); 
	return list;
}

public void Del_Conf(String[] ss){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	for(int i=0;i<ss.length;i++){
		db.execSQL("delete from "+DataBaseHelper.TB_Conf+" where "+DataBaseHelper.GID+"='"
				+ss[i]+"'");
	}
	dataBaseManager.closeDataBase();
}
public void Del_Conf(String[] ss,SQLiteDatabase db ){
	
	for(int i=0;i<ss.length;i++){
		db.execSQL("delete from "+DataBaseHelper.TB_Conf+" where "+DataBaseHelper.GID+"='"
				+ss[i]+"'");
	}
	
}
public void Del_Moved_id(String id){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("delete from "+DataBaseHelper.TB_MoveD+" where "+DataBaseHelper.HPD_ID+"='"
			+id+"'");
	dataBaseManager.closeDataBase();
}

public void Del_Moved_id(String id,SQLiteDatabase db){
	db.execSQL("delete from "+DataBaseHelper.TB_MoveD+" where "+DataBaseHelper.HPD_ID+"='"
			+id+"'");
}
public void Del_Moved(String djid){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("delete from "+DataBaseHelper.TB_MoveD+" where "+DataBaseHelper.MID+"='"
			+djid+"'");
	dataBaseManager.closeDataBase();
}
public void Del_Order(String djid){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("delete from "+DataBaseHelper.TB_Order+" where "+DataBaseHelper.ID+"='"
			+djid+"'");
	dataBaseManager.closeDataBase();
}
public void Del_OrderDetails(String djid){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("delete from "+DataBaseHelper.TB_OrderDetail+" where "+DataBaseHelper.orderID+"='"
			+djid+"'");
	dataBaseManager.closeDataBase();
}
public void Del_OrderDetails(String djid,String hpid){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("delete from "+DataBaseHelper.TB_OrderDetail+" where "+DataBaseHelper.orderID+"='"
			+djid+"' and "+DataBaseHelper.HPID+"='"+hpid+"'");
	dataBaseManager.closeDataBase();
}

public List<Map<String, Object>> Gt_OrderDetails(String ddid, String hpid, String[] s) {
	String str = "";
	for (int i = 0; i < s.length; i++) {
		if (i != s.length - 1) {
			str = str + s[i] + ",";
		} else {
			str = str + s[i];
		}
	}
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select " + str + " from "
			+ DataBaseHelper.TB_OrderDetail + " where "
			+ DataBaseHelper.orderID + "='" + ddid + "' and "+DataBaseHelper.HPID+" = '"+hpid+"'", null);
	c.moveToFirst();
	while (!c.isAfterLast()) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		for (int j = 0; j < s.length; j++) {
			map.put(s[j], c.getString(j));
		}
		list.add(map);
		c.moveToNext();
	}
	c.close();
	dataBaseManager.closeDataBase();
	return list;
}

public List<Map<String, Object>> Gt_DiaoboDetails(String djid, String hpid, String[] s) {
	String str = "";
	for (int i = 0; i < s.length; i++) {
		if (i != s.length - 1) {
			str = str + s[i] + ",";
		} else {
			str = str + s[i];
		}
	}
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select " + str + " from "
			+ DataBaseHelper.TB_TRANSD + " where "
			+ DataBaseHelper.MID + "='" + djid + "' and "+DataBaseHelper.HPID+" = '"+hpid+"'", null);
	c.moveToFirst();
	while (!c.isAfterLast()) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		for (int j = 0; j < s.length; j++) {
			map.put(s[j], c.getString(j));
		}
		list.add(map);
		c.moveToNext();
	}
	c.close();
	dataBaseManager.closeDataBase();
	return list;
}

public void Del_Moved(String djid,String hpid){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("delete from "+DataBaseHelper.TB_MoveD+" where "+DataBaseHelper.MID+"='"
			+djid+"' and "+DataBaseHelper.HPID+"='"+hpid+"'");
	dataBaseManager.closeDataBase();
}

/**ɾ���õ������л�Ʒ��ϸ
 * */
public void DelAll_Moved(String djid){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("delete from "+DataBaseHelper.TB_MoveD+" where "+DataBaseHelper.MID+"='"
			+djid+"'");
	dataBaseManager.closeDataBase();
}

/*��ȡĳ��Ʒ��ϸ�ĳ��������
 * */
public String getMoved_msl(String djid,String hpid){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select "+DataBaseHelper.MSL+" from "+DataBaseHelper.TB_MoveD+" where "+DataBaseHelper.MID+"='"+djid+
			"' and "+DataBaseHelper.HPID+" ='"+hpid+"'",null);
	String str="";
	if(c.moveToFirst()){
		str=c.getString(c.getColumnIndex(DataBaseHelper.MSL));
	}
	c.close();
	dataBaseManager.closeDataBase();
	return str;
}

public void Del_DDdetails(String djid,String hpid){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("delete from "+DataBaseHelper.TB_OrderDetail+" where "+DataBaseHelper.orderID+"='"
			+djid+"' and "+DataBaseHelper.HPID+"='"+hpid+"'");
	dataBaseManager.closeDataBase();
}

public void Del_transd(String djid,String hpid){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("delete from "+DataBaseHelper.TB_TRANSD+" where "+DataBaseHelper.MID+"='"
			+djid+"' and "+DataBaseHelper.HPID+"='"+hpid+"'");
	dataBaseManager.closeDataBase();
}

public void Del_transd(String djid){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("delete from "+DataBaseHelper.TB_TRANSD+" where "+DataBaseHelper.MID+"='"
			+djid+"'");
	dataBaseManager.closeDataBase();
}

public void Del_transm(String djid){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("delete from "+DataBaseHelper.TB_TRANSM+" where "+DataBaseHelper.ID+"='"
			+djid+"'");
	dataBaseManager.closeDataBase();
}

public void Del_Movem(String djid){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("delete from "+DataBaseHelper.TB_MoveM+" where "+DataBaseHelper.HPM_ID+"='"
			+djid+"'");
	dataBaseManager.closeDataBase(); 
}

public void Del_Movem(String djid,SQLiteDatabase db){
	db.execSQL("delete from "+DataBaseHelper.TB_MoveM+" where "+DataBaseHelper.HPM_ID+"='"
			+djid+"'");
}
public void Del_CKKC(String hpid,String ckid,SQLiteDatabase db){
	db.execSQL("delete from "+DataBaseHelper.TB_CKKC+" where "+DataBaseHelper.HPID+"='"
			+hpid+"' and "+DataBaseHelper.CKID+"='"+ckid+"'");
}
public void Del_CKKC(String hpid,int ckid){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("delete from "+DataBaseHelper.TB_CKKC+" where "+DataBaseHelper.HPID+"='"
			+hpid+"' and "+DataBaseHelper.CKID+"='"+ckid+"'");
	dataBaseManager.closeDataBase(); 
}
public void Del_CP(String id){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("delete from "+DataBaseHelper.TB_Company+" where "+DataBaseHelper.ID+"='"
			+id+"'");
	dataBaseManager.closeDataBase(); 
}
public void Del_CKKC(String id){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("delete from "+DataBaseHelper.TB_CKKC+" where "+DataBaseHelper.ID+"='"
			+id+"'  ");
	dataBaseManager.closeDataBase(); 
}
public void Del_CK(String id){
	synchronized (id) {
		DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
		db.execSQL("delete from "+DataBaseHelper.TB_CK+" where "+DataBaseHelper.ID+"='"
				+id+"'");
		dataBaseManager.closeDataBase(); 
	}
}

public void Del_CK(){
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("delete from "+DataBaseHelper.TB_CK );
	dataBaseManager.closeDataBase(); 
}

public List<Map<String, Object>> Gt_movem_last(String[] s,String type){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveM+" where "+DataBaseHelper.mType+"='"
			+type+"' order by "+DataBaseHelper.MVDT+" desc ",null);
	c.moveToFirst();
	while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 map.put(s[j], c.getString(j));
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	c.close();
	dataBaseManager.closeDataBase();
	return list;
}

public void Update_MoveM(String id,String dh,String dw,String jbr,String bz,String type){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("update "+DataBaseHelper.TB_MoveM+" set "+DataBaseHelper.MVDH+"='"+dh+"',"+DataBaseHelper.DWName+"='"
			+dw+"',"+DataBaseHelper.JBR+"='"+jbr+"',"+DataBaseHelper.BZ+"='"+bz+"',"+DataBaseHelper.actType+"='"+type+
			"'  where "+DataBaseHelper.HPM_ID+"='"+id+"'");
	dataBaseManager.closeDataBase();
}
public void Update_Order(String id,String dh,String dw,String status,String bz,String lxr,String dirc,String reqdate,
		String dwid,String dep,String trades,String sqdt,String tel,String zje,String yfje,String syje,String sqr,String sqrid){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("update "+DataBaseHelper.TB_Order+" set "+DataBaseHelper.OrderIndex+"='"+dh+"',"
			+DataBaseHelper.DWName+"='"+dw+"',"+DataBaseHelper.Status+"='"+status+"',"+DataBaseHelper.BZ+"='"
			+bz+"',"+DataBaseHelper.LXR+"='"+lxr+"',"+DataBaseHelper.DWID+"='"+dwid+"',"+
			DataBaseHelper.DepName+"='"+dep+"',"+DataBaseHelper.Trades+"='"+trades+"',"
			+DataBaseHelper.Sqdt+"='"+sqdt+"',"+DataBaseHelper.TEL+"='"+tel+"',"+DataBaseHelper.ReqDate+"='"+reqdate+"',"+
			DataBaseHelper.zje+"='"+zje+"',"+DataBaseHelper.yfje+"='"+yfje+"',"+
			DataBaseHelper.syje+"='"+syje+"',"+DataBaseHelper.dirc+"='"+dirc
			+"',"+DataBaseHelper.sqr+"='"+sqr+"',"+DataBaseHelper.sqrID+"='"+sqrid+"'  where "+DataBaseHelper.ID+"='"+id+"'");
	dataBaseManager.closeDataBase();
}
public void Update_MoveM(String id,String dh,String mvdt,String dw,String jbr,String bz,String type,String ck
		,int ckid,String details,String lrr,String lrsj,int mType,String dwid,String dep,String depid,String hpzjz,
		String res1,String res2,String res3){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("update "+DataBaseHelper.TB_MoveM+" set "+DataBaseHelper.MVDH+"='"+dh+"',"+DataBaseHelper.MVDT+"='"+mvdt+"',"
			+DataBaseHelper.DWName+"='"+dw+"',"+DataBaseHelper.JBR+"='"+jbr+"',"+DataBaseHelper.BZ+"='"
			+bz+"',"+DataBaseHelper.actType+"='"+type+"',"+DataBaseHelper.CKMC+"='"+ck+"',"+
			DataBaseHelper.CKID+"='"+String.valueOf(ckid)+"',"+DataBaseHelper.mType+"='"+String.valueOf(mType)+"',"
			+DataBaseHelper.Lrr+"='"+lrr+"',"+DataBaseHelper.Lrsj+"='"+lrsj+"',"+DataBaseHelper.HPzj+"='"+hpzjz+"',"+
			DataBaseHelper.RES1+"='"+res1+"',"+DataBaseHelper.RES2+"='"+res2+"',"+DataBaseHelper.RES3+"='"+res3+"',"+
			DataBaseHelper.Details+"='"+details+"',"+DataBaseHelper.DWID+"='"+dwid+"',"+
			DataBaseHelper.DepName+"='"+dep+"',"+DataBaseHelper.DepID+"='"+depid
			+"',"+DataBaseHelper.DJType+"='-1'  where "+DataBaseHelper.HPM_ID+"='"+id+"'");
	dataBaseManager.closeDataBase();
}

public void Update_transm(String sckName,int sckid,String dckName,int dckid,String mvdh,String mvdt,String jbr,String hpnames,String bz,String hpzjz){
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("update "+DataBaseHelper.TB_TRANSM+" set "+DataBaseHelper.sckName+" = '"+sckName+"',"+DataBaseHelper.sckid+" = "+sckid+","
			+DataBaseHelper.dckName+" = '"+dckName+"',"+DataBaseHelper.dckid+" = "+dckid+","+DataBaseHelper.mvdh+" ='"+mvdh+"',"
			+DataBaseHelper.mvdt+" ='"+mvdt+"',"+DataBaseHelper.JBR+" ='"+jbr+"',"+DataBaseHelper.hpnames+" ='"+hpnames+"',"+DataBaseHelper.BZ+" ='"+bz+"',"
			+DataBaseHelper.hpzjz+" ='"+hpzjz+"'");
	dataBaseManager.closeDataBase();
}

public String GetHpzjz(String djid){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select sum(zj) from "+DataBaseHelper.TB_MoveD+" where "+DataBaseHelper.MID+"='"
			+djid+"'",null);
	c.moveToFirst();
	String str=c.getString(0);
	dataBaseManager.closeDataBase();
	return str;
}
public void Update_MoveD_type(String id,String type){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("update "+DataBaseHelper.TB_MoveD+" set "+DataBaseHelper.MVType+"='"+type+
			"'  where "+DataBaseHelper.MID+"='"+id+"'");
	dataBaseManager.closeDataBase();
}
public void insert_into_fromfile(String tb,String[] s1,String[] s2,SQLiteDatabase db){
	String str1="",str2="'";
//	for(int i=0;i<s1.length;i++){
//		if(i!=s1.length-1){
//			str1=str1+s1[i]+",";
//		}else{
//			str1=str1+s1[i];
//		}
//	}
//	for(int i=0;i<s2.length;i++){
//		if(i!=s2.length-1){
//			str2=str2+s2[i]+"','";
//		}else{
//			str2=str2+s2[i]+"'";
//		}
//	}
//	db.execSQL("insert into "+tb+" ("+str1+") values ("+str2+")");
	ContentValues values = new ContentValues();
	for(int i=0;i<s2.length;i++){
		values.put(s1[i], s2[i]);
	}
	db.insert(tb, null, values);
}
public void insert_into_fromfile(String tb,String[] s1,String[] s2,SQLiteDatabase db,String time){
//	String str1="",str2="'";
//	for(int i=0;i<s1.length;i++){
//		str1=str1+s1[i]+",";
//	}
//	str1=str1+DataBaseHelper.GXDate;
//	for(int i=0;i<s2.length;i++){
//		str2=str2+s2[i]+"','";
//	}
//	str2=str2+time+"'";
	ContentValues values = new ContentValues();
			for(int i=0;i<s2.length;i++){
				values.put(s1[i], s2[i]);
			}
			values.put(DataBaseHelper.GXDate, time);
	db.insert(tb, null, values);
//	db.execSQL("insert into "+tb+" ("+str1+") values ("+str2+")");
}
public boolean isCheck_bm(String bm,SQLiteDatabase db){
	Cursor c = db.rawQuery("select "+DataBaseHelper.ID+" from "+DataBaseHelper.TB_HP+" where "+DataBaseHelper.HPBM+"='"
			+bm+"'",null);
	if(c.moveToFirst()){
		c.close();
		return true;
	}else{
		c.close();
		return false;
	}
}
public boolean isCheck_id(String id,SQLiteDatabase db){
	Cursor c = db.rawQuery("select "+DataBaseHelper.ID+" from "+DataBaseHelper.TB_HP+" where "+DataBaseHelper.ID+"='"
			+id+"'",null);
	if(c.moveToFirst()){
		c.close();
		return true;
	}else{
		c.close();
		return false;
	}
}
public boolean isCK_exist(String ckmc){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select * from "+DataBaseHelper.TB_CK+" where "
				+DataBaseHelper.CKMC+"='"+ckmc+"'",null);
	if(c.moveToFirst()){
		c.close();
		dataBaseManager.closeDataBase();
		return true;
	}else{
		c.close();
		dataBaseManager.closeDataBase();
		return false;
	}
}
public void  update_ggtime(String time,String id,SQLiteDatabase db){
	db.execSQL("update  "+DataBaseHelper.TB_HP+" set "+DataBaseHelper.GXDate+"='"
			+time+"'  where "+DataBaseHelper.ID+"='"+id+"'");
}
public String Gt_hpid(String bm,SQLiteDatabase db){
	String str;
	Cursor c = db.rawQuery("select "+DataBaseHelper.ID+" from "+DataBaseHelper.TB_HP+" where "+DataBaseHelper.HPBM+"='"
			+bm+"'",null);
	c.moveToFirst();
	str=c.getString(0);
	c.close();
	return str;
}
public void del_tableContent(String table){
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);
	SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("delete from "+table);
	dataBaseManager.closeDataBase();
	
}
public void del_repeat(String bm,SQLiteDatabase db){
	db.execSQL("delete from "+DataBaseHelper.TB_HP+" where "+DataBaseHelper.HPBM+"='"
			+bm+"'");
}
public void del_repeat_byid(String id,SQLiteDatabase db){
	db.execSQL("delete from "+DataBaseHelper.TB_HP+" where "+DataBaseHelper.ID+"='"
			+id+"'");
}
public void update_moved(String hpid,String old_id,SQLiteDatabase db){
	db.execSQL("update "+DataBaseHelper.TB_MoveD+" set"+DataBaseHelper.HPID+"='"
			+hpid+"' where "+DataBaseHelper.HPID+"='"+old_id+"'");
}
public List<Map<String, Object>> GetLB(String lbs,SQLiteDatabase db){
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	Cursor c = db.rawQuery("select * from "+DataBaseHelper.TB_hplbTree+" where "+DataBaseHelper.LBS+"='"
			+lbs+"'",null);
	 c.moveToFirst();
	 while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 map.put("id", c.getString(0));
		 map.put("name", c.getString(1));
		 map.put("lev", c.getString(2));
		 map.put("pid", c.getString(3));
		 map.put("ord", c.getString(4));
		 map.put("index", c.getString(5));
		 list.add(map);
		 c.moveToNext();
	 }
	 c.close();
	return list;
}

public List<Map<String, Object>> search_by_hpid(String[] s,String hpid){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveD+" where "+DataBaseHelper.HPID+"='"
			+hpid+"'",null);
	c.moveToFirst();
	while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 map.put(s[j], c.getString(j));
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	c.close();
	dataBaseManager.closeDataBase();
	return list;
}
public List<Map<String, Object>> search_by_hpid(String[] s,String hpid,SQLiteDatabase db ){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveD+" where "+DataBaseHelper.HPID+"='"
			+hpid+"'",null);
	c.moveToFirst();
	while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 map.put(s[j], c.getString(j));
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	c.close();
	return list;
}
public List<Map<String, Object>> search_by_hpid(String[] s,String hpid,String dt1,String dt2){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	dt1=dt1+" 00:00:00";
	dt2=dt2+" 24:00:00";
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveD+" where "+DataBaseHelper.HPID+"='"
			+hpid+"' and "+DataBaseHelper.MVDDATE+">'"+dt1+"' and "+DataBaseHelper.MVDDATE+"<'"+dt2+"' order by "
			+DataBaseHelper.MVDDATE+" desc ",null);
	c.moveToFirst();
	while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 map.put(s[j], c.getString(j));
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	c.close();
	dataBaseManager.closeDataBase();
	return list;
}
public List<Map<String, Object>> search_by_hpid_moved_churu(String[] s,String hpid,String dt1,String dt2){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	dt1=dt1+" 00:00:00";
	dt2=dt2+" 24:00:00";
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveD+" where "+DataBaseHelper.HPID+"='"
			+hpid+"'  and "+DataBaseHelper.MVDDATE+">'"+dt1+"' and "+
			DataBaseHelper.MVDDATE+"<'"+dt2+"' order by "
			+DataBaseHelper.MVDDATE+" desc ",null);
	c.moveToFirst();
	while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 map.put(s[j], c.getString(j));
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	dataBaseManager.closeDataBase();
	return list;
}
public List<Map<String, Object>> search_moved(String[] s,int type,String dt1,String dt2){
	String str="";
	for(int i=0;i<s.length;i++){
		if(i!=s.length-1){
			str=str+s[i]+",";
		}else{
			str=str+s[i];
		}
	}
	dt1=dt1+" 00:00:00";
	dt2=dt2+" 24:00:00";
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c = db.rawQuery("select "+str+" from "+DataBaseHelper.TB_MoveD+" where "+DataBaseHelper.MVDType+"="
			+type+" and "+DataBaseHelper.MVDDATE+">'"+dt1+"' and "+DataBaseHelper.MVDDATE+"<'"+dt2+"' order by "
			+DataBaseHelper.MVDDATE+" desc ",null);
	c.moveToFirst();
	while (!c.isAfterLast()){
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 for(int j=0;j<s.length;j++){
			 map.put(s[j], c.getString(j));
		 }
		 list.add(map);
		 c.moveToNext();
	 }
	c.close();
	dataBaseManager.closeDataBase();
	return list;
}
public void Update_MoveM(String detail,String id){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("update "+DataBaseHelper.TB_MoveM+" set "+DataBaseHelper.hpDetails+"='"+detail+"'  where "+DataBaseHelper.HPM_ID+
			"='"+id+"'");
	dataBaseManager.closeDataBase();
}
public void Update_MoveM(String detail,String id,SQLiteDatabase db ){
	db.execSQL("update "+DataBaseHelper.TB_MoveM+" set "+DataBaseHelper.hpDetails+"='"+detail+"'  where "+DataBaseHelper.HPM_ID+
			"='"+id+"'");
}
public void Update_MoveD(String msl,String id,String hpid,String dj,String zj){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("update "+DataBaseHelper.TB_MoveD+" set "+DataBaseHelper.MSL+"='"+msl+"',"+DataBaseHelper.DJ+"='"+dj+"',"
			+DataBaseHelper.ZJ+"='"+zj+"'  where "+DataBaseHelper.MID+
			"='"+id+"' and "+DataBaseHelper.HPID+"='"+hpid+"'");
	dataBaseManager.closeDataBase();
}
public void Update_MoveD(String djid,String dh,String type,int ckid){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("update "+DataBaseHelper.TB_MoveD+" set "+DataBaseHelper.DH+"='"+dh+"',"+DataBaseHelper.MVType
			+"='"+type+"',"+DataBaseHelper.CKID+"='"+String.valueOf(ckid)+"'  where "+DataBaseHelper.MID+
			"='"+djid+"'");
	dataBaseManager.closeDataBase();
}
public void Update_MoveD(String msl,String id,String hpid){
	
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("update "+DataBaseHelper.TB_MoveD+" set "+DataBaseHelper.MSL+"='"+msl+"'  where "+DataBaseHelper.MID+
			"='"+id+"' and "+DataBaseHelper.HPID+"='"+hpid+"'");
	dataBaseManager.closeDataBase();
}

/**
 * ����ʶ���ж������Ƿ񲿷�ƥ�䡣
 * @return Map<String, Integer> ��0Ԫ�� Ϊ1�ǲ���ƥ�䣬Ϊ0����ȫƥ��,Ϊ-1�ǲ���ƥ��������������ǲ���ƥ���������4��Ԫ�أ��ֱ��ǻ�Ʒ��ʼ����Ʒ������������ʼ������������
 * */
public Map<String, Integer> getTMMacth(){
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor cursor = db.rawQuery("select "+DataBaseHelper.ItemID+","+DataBaseHelper.ItemV+" from "+DataBaseHelper.TB_Conf+" where "+DataBaseHelper.GID+" = "+"'����ʶ��'"
			+ " and ("+DataBaseHelper.ItemID+"='ƥ�䷽ʽ' or "+DataBaseHelper.ItemID+"='��Ʒ��ʼ' or "+DataBaseHelper.ItemID+"='��Ʒ��ֹ' or "+DataBaseHelper.ItemID+"='������ʼ' or "
			+DataBaseHelper.ItemID+"='������ֹ')", null);
	Map<String, Integer> MatchMap = new HashMap<String, Integer>();
	if(cursor.moveToFirst()){
		do{
			String itemIdString = cursor.getString(cursor.getColumnIndex(DataBaseHelper.ItemID));
			String itemVString = cursor.getString(cursor.getColumnIndex(DataBaseHelper.ItemV));
			if(itemIdString.equals("ƥ�䷽ʽ")){
				if(itemVString.equals("����ƥ��")){
					MatchMap.put("partMatch", 1);
				}else{
					MatchMap.put("partMatch", 0);
				}
			}else if(itemIdString.equals("��Ʒ��ʼ")){
				if(itemVString.matches("^[1-9]+\\d*$")){//ƥ���ǲ���������
					MatchMap.put("hpks", Integer.parseInt(cursor.getString(cursor.getColumnIndex(DataBaseHelper.ItemV))));
				}else{
					MatchMap.put("partMatch", -1);
					dataBaseManager.closeDataBase();
					return MatchMap;
				}
			}else if(itemIdString.equals("��Ʒ��ֹ")){
				if(itemVString.matches("^[1-9]+\\d*$")){//ƥ���ǲ���������
					MatchMap.put("hpjz",Integer.parseInt(cursor.getString(cursor.getColumnIndex(DataBaseHelper.ItemV))));
				}else{
					MatchMap.put("partMatch", -1);
					dataBaseManager.closeDataBase();
					return MatchMap;
				}
			}else if(itemIdString.equals("������ʼ")){
				if(itemVString.matches("^[1-9]+\\d*$")){//ƥ���ǲ���������
					MatchMap.put("slks",Integer.parseInt(cursor.getString(cursor.getColumnIndex(DataBaseHelper.ItemV))));
				}else{
					MatchMap.put("partMatch", -1);
					dataBaseManager.closeDataBase();
					return MatchMap;
				}
			}else if(itemIdString.equals("������ֹ")){
				if(itemVString.matches("^[1-9]+\\d*$")){//ƥ���ǲ���������
					MatchMap.put("sljz",Integer.parseInt(cursor.getString(cursor.getColumnIndex(DataBaseHelper.ItemV))));
				}else{
					MatchMap.put("partMatch", -1);
					dataBaseManager.closeDataBase();
					return MatchMap;
				}
			}
		}while(cursor.moveToNext());
	}else{
		MatchMap.put("partMatch", 0);
		dataBaseManager.closeDataBase();
		return MatchMap;
	}
	dataBaseManager.closeDataBase();
	return MatchMap;
}

public void saveLoginInfo(String company,String username,String password,String miwenpassword){
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();

	db.execSQL("delete from "+DataBaseHelper.TB_LOGIN+" where "+DataBaseHelper.company+"='"+company+"' and "+DataBaseHelper.username+"='"+username+"'");
	db.execSQL("insert into "+DataBaseHelper.TB_LOGIN+" ("+DataBaseHelper.company+","+DataBaseHelper.username+","+DataBaseHelper.password+","+DataBaseHelper.miwenpassword+
			") values ('"+company+"','"+username+"','"+password+"','"+miwenpassword+"')");
	dataBaseManager.closeDataBase();
}

public List<Map<String,Object>> getLoginInfo_byCompany(String company){
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	Cursor c= null;
	if(company==null||company.equals("")){
		c=db.rawQuery("select * from "+DataBaseHelper.TB_LOGIN,null);
	}else{
		c=db.rawQuery("select * from "+DataBaseHelper.TB_LOGIN+" where "+DataBaseHelper.company+"='"+company+"'",null);
	}
	List<Map<String,Object>> mList = new ArrayList<Map<String,Object>>();
	if(c.moveToFirst()){
		do{
			Map<String,Object> map = new HashMap<String, Object>();
			int num=c.getColumnCount();
			for(int i=0;i<num;i++){
				map.put(c.getColumnName(i), c.getString(i));
			}
			mList.add(map);
		}while(c.moveToNext());
	}
	dataBaseManager.closeDataBase();
	return mList;
}

public void deleteLoginInfo(String company,String username){
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	db.execSQL("delete from "+DataBaseHelper.TB_LOGIN+" where "+DataBaseHelper.company+"='"+company+"' and "+DataBaseHelper.username+"='"+username+"'");
	dataBaseManager.closeDataBase();
}

public List<Map<String, Object>> getDJRes(){
	DataBaseManager dataBaseManager = DataBaseManager.getInstance(context);SQLiteDatabase db = dataBaseManager.openDataBase();
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	Cursor c = db.rawQuery("select "+DataBaseHelper.ItemID+","+DataBaseHelper.ItemV+" from "+DataBaseHelper.TB_Conf
			+" where "+DataBaseHelper.GID+"='�����Զ����ֶ�' ",null);
	Map<String,Object> map = new HashMap<String, Object>();
	while(c.moveToNext()){
		map.put(c.getString(0), c.getString(1));
		list.add(map);
	}
	c.close();
	dataBaseManager.closeDataBase();
	return list;
}

}