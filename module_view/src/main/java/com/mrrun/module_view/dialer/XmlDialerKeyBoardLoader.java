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
    private static XmlDialerKeyBoardLoader mInstance;
    private Context mContext;
    private Resources mResources;
    DkbTemplate mDkbTemplate = null;
    /**
     * The event type in parsing the xml file.
     */
    private int mXmlEventType;
    /**
     * The key type id for invalid key type. It is also used to generate next
     * valid key type id by adding 1. 无效key type id。
     */
    private static final int KEYTYPE_ID_LAST = -1;

    public static XmlDialerKeyBoardLoader getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new XmlDialerKeyBoardLoader(context);
        }
        Debug.D("XmlDialerKeyBoardLoader = " + mInstance);
        return mInstance;
    }

    private XmlDialerKeyBoardLoader(Context context) {
        this.mContext = context.getApplicationContext();
        this.mResources = context.getResources();
    }

    protected DkbTemplate loadDbkTemplate(int resourceId) {
        if (null == mContext || 0 == resourceId) {
            return null;
        }
        Resources r = mResources;
        XmlResourceParser xrp = r.getXml(resourceId);
        mDkbTemplate = new DkbTemplate(resourceId);
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
                            mDkbTemplate.setBackgrounds(dkbBg);
                            float xMargin = getFloat(xrp, XmlConstant.XmlAttrName.XML_ATTR_KEY_XMARGIN, 0);
                            float yMargin = getFloat(xrp, XmlConstant.XmlAttrName.XML_ATTR_KEY_YMARGIN, 0);
                            mDkbTemplate.setMargins(xMargin, yMargin);
                        }
                        // 2. Is it the element, "key_type"?
                        else if (XmlConstant.XmlTagName.XML_TAG_KEY_TYPE.compareTo(tagName) == 0) {
                            int id = getInteger(xrp, XmlConstant.XmlAttrName.XML_ATTR_ID, KEYTYPE_ID_LAST);
                            Drawable bg = getDrawable(xrp, XmlConstant.XmlAttrName.XML_ATTR_BG, null);
                            SoftKeyType keyType = DkbTemplate.createKeyType(id);
                            keyType.setBackgrounds(bg);
                            mDkbTemplate.addKeyType(keyType);
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
            return mDkbTemplate;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DialerKeyBoard loadDbkKeyboard(int resourceId) {
        if (null == mContext || 0 == resourceId) {
            return null;
        }
        DkbPool dkbPool = DkbPool.getInstance();
        Resources r = mResources;
        mDkbTemplate = null;
        DialerKeyBoard dialerKeyBoard = null;
        XmlResourceParser xrp = r.getXml(resourceId);
        try {
            mXmlEventType = xrp.next();
            while (mXmlEventType != XmlPullParser.END_DOCUMENT) {
                switch (mXmlEventType) {
                    case XmlPullParser.START_DOCUMENT: {
                        Debug.D("xml解析开始");
                        break;
                    }
                    case XmlPullParser.START_TAG: {
                        // 一般都是获取标签的属性值，所以在这里获取你需要的数据
                        Debug.D("当前标签是：" + xrp.getName() + "开始");
                        String tagName = xrp.getName();
                        // 1. Is it the root element, "keyboard"?
                        if (XmlConstant.XmlTagName.XML_TAG_KEYBOARD.compareTo(tagName) == 0) {
                            int dbkTemplateId = xrp.getAttributeResourceValue(null, XmlConstant.XmlAttrName.XML_ATTR_DKB_TEMPLATE, 0);
                            Debug.D("当前dbkTemplateId：" + dbkTemplateId);
                            // Try to get the template from pool. If it is not
                            // in, the pool will try to load it.
                            mDkbTemplate = dkbPool.getDkbTemplate(dbkTemplateId, mContext);
                            if (null == mDkbTemplate) {
                                return null;
                            }
                            dialerKeyBoard = new DialerKeyBoard(resourceId, mDkbTemplate);
                            float width = getFloat(xrp, XmlConstant.XmlAttrName.XML_ATTR_WIDTH, 0);
                            float height = getFloat(xrp, XmlConstant.XmlAttrName.XML_ATTR_HEIGHT, 0);
                            dialerKeyBoard.setKeyWidthAndHeight(width, height);
                        }
                        // 2. Is it the element, "row"?
                        else if (XmlConstant.XmlTagName.XML_TAG_ROW.compareTo(tagName) == 0) {
                            int rowId = getInteger(xrp, XmlConstant.XmlAttrName.XML_ATTR_ROW_ID,
                                    DialerKeyRow.ALWAYS_SHOW_ROW_ID);
                            dialerKeyBoard.beginNewRow(rowId);
                        }
                        // 3. Is it the element, "key"?
                        else if (XmlConstant.XmlTagName.XML_TAG_KEY.compareTo(tagName) == 0) {
                            DialerKey dialerKey = new DialerKey();
                            dialerKey.mKeyType = getInteger(xrp, XmlConstant.XmlAttrName.XML_ATTR_KEY_TYPE, 0);
                            dialerKey.mKeyIcon = getDrawable(xrp, XmlConstant.XmlAttrName.XML_ATTR_ICON, null);
                            dialerKey.mKeyCode = getInteger(xrp, XmlConstant.XmlAttrName.XML_ATTR_CODE, 0);
                            dialerKeyBoard.addDialerKey(dialerKey);
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
            return dialerKeyBoard;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
        int resId = xrp.getAttributeResourceValue(null, name, 0);
        Debug.D("getInteger resId = " + resId);
        if (0 == resId) {
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
        } else {
            return Integer.parseInt(mContext.getResources().getString(resId));
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
        int resId = xrp.getAttributeResourceValue(null, name, 0);
        Debug.D("getFloat resId = " + resId);
        if (0 == resId) {
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
        } else {
            return mContext.getResources().getDimension(resId);
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
        Debug.D("Drawable resId = " + resId);
        if (0 == resId) {
            return defValue;
        }
        return mResources.getDrawable(resId);
    }
}
