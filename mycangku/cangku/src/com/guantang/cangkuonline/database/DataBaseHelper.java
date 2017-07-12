package com.guantang.cangkuonline.database;

import java.security.PublicKey;



public class DataBaseHelper {
	
	
	public static int DB_VERSION=9;//���ݿ⵱ǰ�汾,����ʱ���ּ�һ
	
	public static final String DB="localDataBase.db";//strorage_moblie_ol.db
	public static final String TB_HP="tb_hp"; //��Ʒ��Ϣ
	public static final String TB_CKKC="tb_ckkc";//�ֿ��棨������Ʒ�Ͳֿ⣩
	public static final String TB_CK="tb_ck";//�ֿ���Ϣ
	public static final String TB_MoveD="tb_moved";//�������ϸ
	public static final String TB_MoveM="tb_movem";//����ⵥ��
	public static final String TB_hplbTree="tb_hplbTree";//��Ʒ�б�
	public static final String TB_Company="tb_company";//������λ
	public static final String TB_Conf="tb_conf";//����
	public static final String TB_depTree="tb_depTree";//�����б�
	public static final String TB_Order="tb_order";//����
	public static final String TB_OrderDetail="tb_orderDetail";//������ϸ
	public static final String TB_PIC = "tb_pic";//��ƷͼƬ��
	public static final String TB_LOGIN = "tb_login";//��¼��Ϣ��
	public static final String TB_TRANSD = "tb_transd";//������ϸ��
	public static final String TB_TRANSM = "tb_transm";//�������ݱ�
	
	public static final String ID="id";
	public static final String HPMC="hpmc";
	public static final String HPBM="hpbm";
	public static final String HPTM="hptm";
	public static final String GGXH="ggxh";
	public static final String JLDW="jldw";
	public static final String SCCS="sccs"; //��������
	public static final String JLDW2="jldw2";
	public static final String LBS="lbs";//�������
	public static final String LBID="lbid";
	public static final String RKCKJ="rkckj";//���ο���
	public static final String CKCKJ="ckckj";//����ο���
	public static final String HPSX="hpsx";//��Ʒ����
	public static final String HPXX="hpxx";//��Ʒ����
	public static final String BZ="bz"; //��ע
	public static final String CurrKC="CurrKC";//��ǰ���
	public static final String LBIndex="LBindex";//�������
	public static final String CKCKJ2="ckckj2";
	public static final String BigNum="bignum";//�������
	public static final String KCJE="kcje";//�����
	public static final String Type="type";//�����ӱ��    1:������  0����ϵͳ����
	public static final String GXDate="gxdate";//����ʱ��
	public static final String RES1="res1";//��չ�ֶ�1
	public static final String RES2="res2";
	public static final String RES3="res3";
	public static final String RES4="res4";
	public static final String RES5="res5";
	public static final String RES6="res6";
	public static final String RESD1="resd1";//������չ�ֶ�1
	public static final String RESD2="resd2";
	public static final String HPPY="hppy";//��Ʒƴ��
	public static final String StopTag="StopTag";//ͣ�ñ��
	public static final String ImagePath="imgpath";
	public static final String YXQ = "yxq";
	
	public static final String HPID="hpid";
	public static final String CKID="ckid";
	public static final String KCSL="kcsl"; //ĳһ�ֿ�ĳ����Ʒ�Ŀ����
	
	public static final String CKMC="ckmc";
	public static final String FZR="fzr";
	public static final String TEL="tel";
	public static final String ADDR="addr";
	public static final String INACT="inact"; //Ĭ���������
	public static final String OUTACT="outact";//Ĭ�ϳ�������
	
	public static final String HPD_ID="hpd_id"; //��ϸID
	public static final String BZKC="bzkc"; //����ǰ�ܿ��
	public static final String AZKC="azkc";//�������ܿ��
	public static final String BTKC="btkc"; //����ǰ���ֿ���
	public static final String ATKC="atkc"; //�����󱾲ֿ���
	public static final String MID="mid"; //����ID
	public static final String MVDDATE="mvddt";//�����ʱ��
	public static final String MVType="dactType"; //���������
	public static final String MVDirect="mvddirect"; //������� 1���룻2����    �̵���1���룻2������
	public static final String MSL="msl";  //���������
	public static final String DJ="dj"; 
	public static final String ZJ="zj";
	public static final String MVDType="mvdType";//�������� 0�̵�1���2����3����
	public static final String DH="mvddh";
	public static final String Bkcje="bkcje"; //�����ǰ�Ŀ����
	public static final String DDDH="ordIndex";//��������
	public static final String TAX="tax";//˰��
	public static final String SQDJ="sqdj";//˰ǰ����
	public static final String SQZJ="sqzj";//˰ǰ�ܼ�
	public static final String RESF1="resf1";//��ϸ��չ�ֶ�1 ����
	public static final String RESF2="resf2";
	public static final String MSL2="msl2";//��������
	public static final String DWXS="dwxs";//����ϵ��
	
	public static final String HPM_ID="hpm_id";//����ID
	public static final String MVDH="mvdh";//���ݵ���
	public static final String MVDDH="mvddh";//��ϸ����
	public static final String MVDT="mvdt";//���ݱ���������
	public static final String MVDDT="mvddt";//��ϸ����������
	public static final String DepName="depName";//��������
	public static final String DepID="depId";//����ID
	public static final String DWName="dwName";//�Է���λ����
	public static final String JBR="jbr";//������
	public static final String hpDetails="hpDetails";//������Ʒ����
	public static final String Details="Details";//������Ʒ����
	public static final String actType="actType";//���������
	public static final String mType="mType";//��������  0�̵�1���2����3����
	public static final String DWID="dwid";//��λID
	public static final String bigJLDW="bigJLDW";//�Ƿ������˵�λ����
	public static final String DJType="DJType";//����״̬  -1��δ�ϴ���δ����  1�����ϴ����ѵ�����0����û�б���
	public static final String Lrr="lrr";
	public static final String Lrsj="lrsj";
	public static final String Tjsj="tjsj";
	public static final String HPzj="hpzj";
	
	public static final String Name="name";
	public static final String Lev="lev";//������
	public static final String PID="PID";//���ڵ�ID
	public static final String Ord="ord";//���
	public static final String Sindex="sindex";//����
	
	public static final String PY="py"; 
	public static final String Email="email";
	public static final String Net="net";
	public static final String LXR="lxr";
	public static final String DWtype="dwtype";
	public static final String YB="yb";//�ʱ�
	public static final String QQ="qq";
	public static final String FAX="fax";
	public static final String Phone="phone";
	
	
	public static final String GID="GID";
	public static final String ItemID="ItemID";
	public static final String ItemV="ItemV";
	
	public static final String depLevel="deplevel";//������
	public static final String depOrder="depOrder";//���
	public static final String depindex="depindex";//����
	
	public static final String Status="status";
	public static final String Country="country";
	public static final String State="state";
	public static final String CountryCode="CountryCode";
	public static final String City="city";
	public static final String OrderIndex="orderindex";
	public static final String Trades="trades";
	public static final String Sqdt="sqdt";
	public static final String ZipCode="zipcode";
	public static final String Cw="cw";
	public static final String Addr2="addr2";
	public static final String PRI="PRI";
	public static final String zje="zje";
	public static final String yfje="yfje";
	public static final String syje="syje";
	public static final String dirc="dirc";
	public static final String shdt="shdt";
	public static final String shr="shr";
	public static final String shyj="shyj";//������
	public static final String shrID="shrID";
	public static final String sqrID="sqrID";
	public static final String sqr="sqr";
	public static final String ReqDate="ReqDate";
	public static final String sjzje="sjzje";
	public static final String jsfs="jsfs";
		
	public static final String orderID="orderId";
	public static final String ord="ord";
	public static final String zxsl="zxsl";
	public static final String SL="sl";
	public static final String Notes="notes";
	public static final String ddirc="ddirc";
	public static final String yjCbdj="yjCbdj";
	public static final String sjCbdj="sjCbdj";
	public static final String lrje="lrje";
	public static final String yjCbzj="yjCbzj";
	
	public static final String company="company";
	public static final String username="username";
	public static final String password = "password";
	public static final String miwenpassword = "miwenpassword";
	public static final String neturl = "neturl";
	public static final String loginflag = "loginflag";
	public static final String lastlogin = "lastlogin";
	
	public static final String ImageURL = "ImageURL";
	public static final String CompressImageURL = "CompressImageURL";
	public static final String UpLoadStatus = "UpLoadStatus";
	
	public static final String sckName = "sckName";
	public static final String sckid = "sckid";
	public static final String dckName = "dckName";
	public static final String dckid = "dckid";
	public static final String mvdh = "mvdh";
	public static final String mvdt = "mvdt";
	public static final String hpnames = "hpnames";//��ƷժҪ
	public static final String hpzjz = "hpzjz";
	public static final String ActStatus = "ActStatus";
	public static final String Note = "note";
	
}