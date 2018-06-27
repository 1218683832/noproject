package com.mrrun.module_share.wx.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mrrun.module_share.Debug;
import com.mrrun.module_share.wx.WXShareManager;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @author lipin
 * @version 1.0
 * @date 2018/04/13
 * <p>
 * 微信分析回调
 * 新建wxapi包并新建WXEntryActivity类，注意名字和包名必须是wxapi和WXEntryActivity
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    /**
     * 微信分享getType为0
     */
    public int WX_SHARE = 0;
    /**
     * 微信登录getType为1
     */
    public int WX_LOGIN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(this, WXShareManager.WX_APPID, true);
        // 接收到分享以及登录的intent传递handleIntent方法，处理结果
        iwxapi.handleIntent(getIntent(), this);
    }

    /**
     * 微信请求结果回调处理
     *
     * @param baseReq
     */
    @Override
    public void onReq(BaseReq baseReq) {
        Debug.D("微信请求结果回调处理");
    }

    /**
     * 微信响应结果回调处理
     *
     * @param baseResp
     */
    @Override
    public void onResp(BaseResp baseResp) {
        Debug.D("微信响应结果回调处理");
        if (baseResp.getType() == 1) {
            Debug.D("------------登陆回调------------");
            SendAuth.Resp resp = (SendAuth.Resp) baseResp;
            Debug.D("------------登陆回调的结果------------：" + new Gson().toJson(resp));
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:// 微信登录成功
                    String code = String.valueOf(resp.code);
                    // 获取用户信息
                    // getAccessToken(code);
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:// 微信登录拒绝授权
                    Toast.makeText(WXEntryActivity.this, "微信登录拒绝授权", Toast.LENGTH_LONG).show();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:// 微信登录取消
                    Toast.makeText(WXEntryActivity.this, "微信登录取消", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        } else if (baseResp.getType() == 0) {// 分享成功回调
            Debug.D("------------分享回调------------");
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK: // 分享成功
                    Toast.makeText(WXEntryActivity.this, "分享成功", Toast.LENGTH_LONG).show();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:// 分享取消
                    Toast.makeText(WXEntryActivity.this, "分享取消", Toast.LENGTH_LONG).show();
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:// 分享拒绝
                    Toast.makeText(WXEntryActivity.this, "分享拒绝", Toast.LENGTH_LONG).show();
                    break;
            }
        }
        this.finish();
    }
}
