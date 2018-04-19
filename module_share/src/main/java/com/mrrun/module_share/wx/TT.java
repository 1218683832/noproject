package com.mrrun.module_share.wx;

import android.content.Context;

import com.mrrun.module_share.Debug;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @author lipin
 * @version 1.0
 * @date 2018/04/13
 */
public class TT {

    public static final String WX_APPID = "wx9667974383";

    private IWXAPI wxApi;

    public TT(Context context) {
        this.wxApi = WXAPIFactory.createWXAPI(context, WX_APPID, true);
        wxApi.registerApp(WX_APPID);
    }

    public void share(int wxscene){
        switch (wxscene){// 分享场景
            case WXScene.WXSceneSession:// 分享到聊天界面
                share2WXSceneSession();
                break;
            case WXScene.WXSceneTimeline:// 分享到朋友圈
                share2WXSceneTimeline();
                break;
            case WXScene.WXSceneFavorite:// 添加到微信收藏
                share2WXSceneFavorite();
                break;
            default:
                break;
        }
        // 分享到聊天界面--WXSceneSession
        // 分享到朋友圈--WXSceneTimeline
        // 添加到微信收藏--WXSceneFavorite

        String text = "文字文字文字";// 分享的文本内容

        // 文字类型分享--WXTextObject
        WXTextObject wxTextObject = new WXTextObject();
        wxTextObject.text = text;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = wxTextObject;
        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        //发送的目标场景， 可以选择发送到会话 WXSceneSession 或者朋友圈 WXSceneTimeline。 默认发送到会话。
        req.scene = SendMessageToWX.Req.WXSceneSession;
        wxApi.sendReq(req);
        // 图片类型分享--WXImageObject
        // 音乐类型分享--WXMusicObject
        // 视频类型分享--WXMVideocObject
        // 网页类型分享--WXWebpageObject
        // 小程序类型分享--WXMiniProgramObject
    }

    /**
     * 添加到微信收藏
     */
    private void share2WXSceneFavorite() {
        Debug.D("添加到微信收藏");
    }

    /**
     * 分享到朋友圈
     */
    private void share2WXSceneTimeline() {
        Debug.D("分享到朋友圈");
    }

    /**
     * 分享到聊天界面
     */
    private void share2WXSceneSession() {
        Debug.D("分享到聊天界面");
    }

    /**
     *  检查微信是否安装
     */
    public boolean isWXAppInstalled(){
        return wxApi.isWXAppInstalled();
    }

    /**
     * 用于transaction字段唯一标识一个请求
     * @param type
     * @return
     */
    private String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
