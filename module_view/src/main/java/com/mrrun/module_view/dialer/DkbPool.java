package com.mrrun.module_view.dialer;

import android.content.Context;

import java.util.Vector;

/**
 * 软键盘内存池，该类采用单例模式，它有两个向量列表：软键盘模版列表、软键盘列表。
 *
 * @author lipin
 * @version 1.0
 * @date 2018/06/28
 */
public class DkbPool {

    private static DkbPool mInstance = null;

    private Vector<DkbTemplate> mDkbTemplates = new Vector<DkbTemplate>();
    private Vector<DialerKeyBoard> mDialerKeyBoards = new Vector<DialerKeyBoard>();

    private DkbPool() {
    }

    public static DkbPool getInstance() {
        if (mInstance == null) {
            mInstance = new DkbPool();
        }
        return mInstance;
    }

    /**
     * 获取软件盘模版。逻辑简介：首先先从mDkbTemplates列表中获取，如果没有获取到，
     * 就调用XmlDialerKeyBoardLoader解析资源文件ID为dkbTemplateId的软键盘模版xml文件，
     * 生成一个模版，并加入mDkbTemplates列表中。
     *
     * @param dkbTemplateId
     * @param context
     * @return
     */
    public DkbTemplate getDkbTemplate(int dkbTemplateId, Context context) {
        for (int i = 0; i < mDkbTemplates.size(); i++) {
            DkbTemplate dkbTemplate = mDkbTemplates.elementAt(i);
            if (dkbTemplate.getDkbTemplateId() == dkbTemplateId) {
                return dkbTemplate;
            }
        }
        if (null != context) {
            DkbTemplate dkbTemplate = XmlDialerKeyBoardLoader.getInstance(context).loadDbkTemplate(dkbTemplateId);
            if (null != dkbTemplate) {
                mDkbTemplates.add(dkbTemplate);
                return dkbTemplate;
            }
        }
        return null;
    }
}
