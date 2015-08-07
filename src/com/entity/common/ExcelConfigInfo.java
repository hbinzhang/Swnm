package com.entity.common;

public class ExcelConfigInfo {
	
	//自用 zone
	public static String ZONE_DATA_NAME="ZONEDATAS";//map中存放防区主信息key值
	public static String ZONE_DEFENCE_DATA_NAME="DEFENCEDATAS";//map中存放防区型光纤信息key值
	public static String ZONE_INTE_DATA_NAME="INTEDATAS";//map中存放一体化信息key值
	public static String ZONE_PULSE_DATA_NAME="PULSEDATAS";//map中存放高压脉冲信息key值
	public static String ZONE_POSITION_DATA_NAME="POSITIONDATAS";//map中存放定位型光纤信息key值
	public static String ZONE_IPC_DATA_NAME="IPCDATAS";//map中存放摄像头信息key值
	public static String ZONE_SOUND_DATA_NAME="SOUNDDATAS";//map中存放音频信息key值
	
	//video
	public static String IPC_DATA_NAME="IPCS";//map中存放IPC主信息key值
	public static String NVR_DATA_NAME="NVRS";//map中存放NVR信息key值
	public static String IVA_DATA_NAME="IVAS";//map中存放IVA信息key值
	public static String DECODER_DATA_NAME="DECODERS";//map中存放DECODER信息key值
	
	//自用以及前台交互用
	public static String FENCE_DATA_ERROR="FENCEDATAERROR";//map中存放围栏excel表数据错误行号的key值
	public static String ZONE_DATA_ERROR="ZONEDATAERROR";//map中存放防区excel表数据错误行号的key值
	public static String ZONE_DEFENCE_DATA_ERROR="DEFENCEDATAERROR";//map中存放防区型光纤excel表数据错误行号的key值
	public static String ZONE_INTE_DATA_ERROR="INTEDATAERROR";//map中存放一体化excel表数据错误行号的key值
	public static String ZONE_PULSE_DATA_ERROR="PULSEDATAERROR";//map中存放高压脉冲excel表数据错误行号的key值
	public static String ZONE_POSITION_DATA_ERROR="POSITIONDATAERROR";//map中存放定位型光纤excel表数据错误行号的key值
	public static String ZONE_IPC_DATA_ERROR="IPCDATAERROR";//map中存放摄像头excel表数据错误行号的key值
	public static String ZONE_SOUND_DATA_ERROR="SOUNDDATAERROR";//map中存放音频excel表数据错误行号的key值
	
	//video
	public static String IPC_DATA_ERROR="IPCDATAERROR";//map中存放IPC excel表数据错误行号的key值
	public static String NVR_DATA_ERROR="NVRDATAERROR";//map中存放NVR excel表数据错误行号的key值
	public static String IVA_DATA_ERROR="IVADATAERROR";//map中存放IVA excel表数据错误行号的key值
	public static String DECODER_DATA_ERROR="DECODERDATAERROR";//map中存放DECODER excel表数据错误行号的key值
	
	public static String INSERT_FENCE_DATA_ERROR="INSERT_FENCEDATAERROR";//map中存放围栏插入数据库错误行号的key值
	public static String INSERT_ZONE_DATA_ERROR="INSERT_ZONEDATAERROR";//map中存放防区插入数据库错误行号的key值
	public static String INSERT_ZONE_DEFENCE_DATA_ERROR="INSERT_DEFENCEDATAERROR";//map中存放防区型光纤插入数据库错误行号的key值
	public static String INSERT_ZONE_INTE_DATA_ERROR="INSERT_INTEDATAERROR";//map中存放一体化插入数据库错误行号的key值
	public static String INSERT_ZONE_PULSE_DATA_ERROR="INSERT_PULSEDATAERROR";//map中存放高压脉冲插入数据库错误行号的key值
	public static String INSERT_ZONE_POSITION_DATA_ERROR="INSERT_POSITIONDATAERROR";//map中存放定位型光纤插入数据库错误行号的key值
	public static String INSERT_ZONE_IPC_DATA_ERROR="INSERT_IPCDATAERROR";//map中存放插入摄像头数据库错误行号的key值
	public static String INSERT_ZONE_SOUND_DATA_ERROR="INSERT_SOUNDDATAERROR";//map中存放插入音频数据库错误行号的key值
	
	//video
	public static String INSERT_IPC_DATA_ERROR="INSERT_IPCDATAERROR";//map中存放IPC插入数据库错误行号的key值
	public static String INSERT_NVR_DATA_ERROR="INSERT_NVRDATAERROR";//map中存放NVR插入数据库错误行号的key值
	public static String INSERT_IVA_DATA_ERROR="INSERT_IVADATAERROR";//map中存放IVA插入数据库错误行号的key值
	public static String INSERT_DECODER_DATA_ERROR="INSERT_DECODERDATAERROR";//map中存放DECODER插入数据库错误行号的key值
	
	public static int FENCE_CELL_NUM=15;
	public static String FENCE_FIRST_CELL_NAME="围栏ID";
	
	public static int ZONE_CELL_NUM=11;
	public static String ZONE_FIRST_CELL_NAME="防区ID";
	
	public static int ZONE_DEFENCE_CELL_NUM=4;
	public static String ZONE_DEFENCE_FIRST_CELL_NAME="防区型震动光纤防区ID";
	
	public static int ZONE_INTE_CELL_NUM=4;
	public static String ZONE_INTE_FIRST_CELL_NAME="一体化防区ID";
	
	public static int ZONE_PULSE_CELL_NUM=4;
	public static String ZONE_PULSE_FIRST_CELL_NAME="高压脉冲防区ID";
	
	public static int ZONE_POSITION_CELL_NUM=7;
	public static String ZONE_POSITION_FIRST_CELL_NAME="定位型震动光纤防区ID";
	
	public static int ZONE_IPC_CELL_NUM=5;
	public static String ZONE_IPC_FIRST_CELL_NAME="摄像头防区ID";
	
	public static int ZONE_SOUND_CELL_NUM=4;
	public static String ZONE_SOUND_FIRST_CELL_NAME="音频防区ID";
	
	//video
	public static int IPC_CELL_NUM=19;
	public static String IPC_FIRST_CELL_NAME="IPCID";
	
	public static int NVR_CELL_NUM=16;
	public static String NVR_FIRST_CELL_NAME="NVRID";
	
	public static int IVA_CELL_NUM=6;
	public static String IVA_FIRST_CELL_NAME="IVAID";
	
	public static int DECODER_CELL_NUM=10;
	public static String DECODER_FIRST_CELL_NAME="DECODERID";

}
