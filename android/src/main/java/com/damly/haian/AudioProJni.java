package com.damly.haian;

public class AudioProJni {

    public final int RTMSG_OK       =  1;
    public final int RTMSG_KO       = -1;
    public final int VAD_STATE_SIL  =  0;
    public final int VAD_STATE_ON   =  1;
    public final int VAD_STATE_END  =  2;

    public int   m_iLVadFlag;
    public int   m_iRVadFlag;
    public float m_fLEngValue;
    public float m_fREngValue;

    private static AudioProJni sAudioProJni = null;

    private AudioProJni() {

    }

    public static AudioProJni getInstance() {
        if (sAudioProJni == null) {
            synchronized (AudioProJni.class) {
                if (sAudioProJni == null) {
                    sAudioProJni = new AudioProJni();
                }
            }
        }
        return sAudioProJni;
    }

    static {
        System.loadLibrary("audioVad");
    }

    /* ******************************************************************** */
	/* FUN                                                                  */
	/*    初始化                                                                                                                                                                                                                         */
	/* IN:                                                                  */
	/*    strAppId     AppId,由hian提供                                                                                                                                        */
	/*    p_iFrontNum  从sil到on状态需要的帧数                                                                                                                              */
	/*    p_iEndNum    从on到end状态需要的帧数                                                                                                                               */
	/*    p_fLThres    左声道阈值（灵敏度）  1-20  默认为5，值越小越灵敏                                                                         */
	/*    p_fRThres    右声道阈值（灵敏度）  1-20  默认为5，值越小越灵敏                                                                         */
	/* OUT:                                                                 */
	/*    RTMSG_KO -- 不成功                                                                                                                                                                               */
	/*    RTMSG_OK -- 成功                                                                                                                                                                                    */
	/* ******************************************************************** */
    public native int InitHianVad(String p_strAppId, int p_iFrontNum, int p_iEndNum, float p_fLThres, float p_fRThres);
    /* ******************************************************************** */
	/* FUN                                                                  */
	/*    处理一帧数据的VAD状态及能量谱                                                                                                                                                                  */
	/* IN:                                                                  */
	/*    p_nWavData   需要处理的音频数据(暂时固定长度为2*256short)                    */
	/*    p_iWavSize   需要处理的音频数据长度（暂时固定为2*256）                                                                                       */
	/* OUT:                                                                 */
	/*    RTMSG_KO -- 不成功                                                                                                                                                                               */
	/*    RTMSG_OK -- 成功                                                                                                                                                                                    */
	/* RES:                                                                 */
	/*    m_iLVadFlag   左声道Vad标志位   0--静音 1--有效语音 2--语音结束                                                               */
	/*    m_iRVadFlag   右声道Vad标志位   0--静音 1--有效语音 2--语音结束                                                               */
	/*    m_fLEngValue  左声道能量幅值                                                                                                                                                      */
	/*    m_fREngValue  右声道能量幅值                                                                                                                                                      */
	/* ******************************************************************** */
    public native int DealHianVad(short[] p_nWavData, int p_iWavSize);
    /* ******************************************************************** */
	/* FUN                                                                  */
	/*    资源释放                                                                                                                                                                                                                     */
	/* IN:                                                                  */
	/*    无                                                                                                                                                                                                                                  */
	/* OUT:                                                                 */
	/*    RTMSG_KO -- 不成功                                                                                                                                                                               */
	/*    RTMSG_OK -- 成功                                                                                                                                                                                    */
	/* ******************************************************************** */
    public native int FreeHianVad();
}
