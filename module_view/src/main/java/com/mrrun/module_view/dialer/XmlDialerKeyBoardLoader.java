package com.mrrun.module_view.dialer;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;

import com.mrrun.module_view.Debug;
import com.mrrun.module_view.dialer.constant.XmlConstant;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * 解析拨号键盘模版和软键盘xml文件。
 *
 * @author lipin
 * @version 1.0
 * @date 2018/06/27
 */
public class XmlDialerKeyBoardLoader {
    private Context mContext;
    private Resources mResources;
    /**
     * The event type in parsing the xml file.
     */
    private int mXmlEventType;
    /**
     * The key type id for invalid key type. It is also used to generate next
     * valid key type id by adding 1. 无效key type id。
     */
    private static final int KEYTYPE_ID_LAST = -1;

    public XmlDialerKeyBoardLoader(Context context) {
        this.mContext = context.getApplicationContext();
        this.mResources = context.getResources();
    }

    public DkbTemplate loadDbkTemplate(int resourceId) {
        if (null == mContext || 0 == resourceId) {
            return null;
        }
        Resources r = mResources;
        XmlResourceParser xrp = r.getXml(resourceId);
        DkbTemplate dkbTemplate = new DkbTemplate(resourceId);
        try {
            mXmlEventType = xrp.next();
            while (mXmlEventType != XmlPullParser.END_DOCUMENT) {// 文档结束标志
                switch (mXmlEventType) {
                    case XmlPullParser.START_DOCUMENT: {
                        Debug.D("xml解析开始");
                        break;
                    }
                    case XmlPullParser.START_TAG: {
                        // 一般都是获取标签的属性值，所以在这里获取你需要的数据
                        Debug.D("当前标签是：" + xrp.getName() + "开始");
                        String tagName = xrp.getName();
                        // 1. Is it the root element, "dkb_template"?
                        if (XmlConstant.XmlTagName.XML_TAG_DKB_TEMPLATE.compareTo(tagName) == 0) {
                            Drawable dkbBg = getDrawable(xrp, XmlConstant.XmlAttrName.XML_ATTR_DKB_BG, null);
                            dkbTemplate.setBackgrounds(dkbBg);
                            float xMargin = getFloat(xrp, XmlConstant.XmlAttrName.XML_ATTR_KEY_XMARGIN, 0);
                            float yMargin = getFloat(xrp, XmlConstant.XmlAttrName.XML_ATTR_KEY_YMARGIN, 0);
                            dkbTemplate.setMargins(xMargin, yMargin);
                        }
                        // 2. Is it the element, "key_type"?
                        else if (XmlConstant.XmlTagName.XML_TAG_KEY_TYPE.compareTo(tagName) == 0) {
                            int id = getInteger(xrp, XmlConstant.XmlAttrName.XML_ATTR_ID, KEYTYPE_ID_LAST);
                            Drawable bg = getDrawable(xrp, XmlConstant.XmlAttrName.XML_ATTR_BG, null);
                            SoftKeyType keyType = DkbTemplate.createKeyType(id);
                            keyType.setBackgrounds(bg);
                            dkbTemplate.addKeyType(keyType);
                        }
                        break;
                    }
                    case XmlPullParser.TEXT: {
                        Debug.D("Text:" + xrp.getText());
                        break;
                    }
                    default:
                        break;
                }
                // get the next tag event type.
                mXmlEventType = xrp.next();
            }
            xrp.close();
            ;
            return dkbTemplate;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DialerKeyBoard loadDbkKeyboard(int resourceId){
        if (null == mContext || 0 == resourceId) {
            return null;
        }
        Resources r = mResources;
        XmlResourceParser xrp = r.getXml(resourceId);
        DialerKeyBoard dialerKeyBoard = new DialerKeyBoard();
        return dialerKeyBoard;
    }

    /**
     * 获取int
     *
     * @param xrp
     * @param name
     * @param defValue
     * @return
     */
    private int getInteger(XmlResourceParser xrp, String name, int defValue) {
        String s = xrp.getAttributeValue(null, name);
        if (null == s) {
            return defValue;
        }
        try {
            int ret = Integer.valueOf(s);
            return ret;
        } catch (NumberFormatException e) {
            return defValue;
        }
    }

    /**
     * 获取float
     *
     * @param xrp
     * @param name
     * @param defValue
     * @return
     */
    private float getFloat(XmlResourceParser xrp, String name, int defValue) {
        String s = xrp.getAttributeValue(null, name);
        if (null == s) {
            return defValue;
        }
        try {
            float ret;
            if (s.endsWith("%p")) {
                ret = Float.parseFloat(s.substring(0, s.length() - 2)) / 100;
            } else {
                ret = Float.parseFloat(s);
            }
            return ret;
        } catch (NumberFormatException e) {
            return defValue;
        }
    }

    /**
     * 获取Drawable
     *
     * @param xrp
     * @param name
     * @param defValue
     * @return
     */
    private Drawable getDrawable(XmlResourceParser xrp, String name, Drawable defValue) {
        int resId = xrp.getAttributeResourceValue(null, XmlConstant.XmlAttrName.XML_ATTR_DKB_BG, 0);
        if (0 == resId) {
            return defValue;
        }
        return mResources.getDrawable(resId);
    }
}
