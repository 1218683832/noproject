package com.mrrun.module_okhttp3;

import com.mrrun.module_okhttp3.simpleexample.execute.FileDownloadExample;
import com.mrrun.module_okhttp3.simpleexample.execute.FileUpExample;
import com.mrrun.module_okhttp3.simpleexample.execute.GetExample;
import com.mrrun.module_okhttp3.simpleexample.execute.HeaderExample;
import com.mrrun.module_okhttp3.simpleexample.execute.PostFormExample;
import com.mrrun.module_okhttp3.simpleexample.execute.PostJsonExample;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ExecuteUnitTest {

    @Test
    public void getExampleTest() {
        GetExample example = new GetExample();
        String url = "https://www.baidu.com/";
        String str = example.execute(url);
        System.out.println(str);
    }

    @Test
    public void postJsonExampleTest() {
        PostJsonExample example = new PostJsonExample();
        String url = "https://www.baidu.com/";
        String str = example.execute(url, "json1");
        System.out.println(str);
    }

    @Test
    public void postFormExampleTest() {
        PostFormExample example = new PostFormExample();
        String url = "https://www.baidu.com/";
        Map<String, String> formMap = new HashMap<String, String>();
        formMap.put("username", "xiaoxiao");
        formMap.put("password", "123456789");
        String str = example.execute(url, formMap);
        System.out.println(str);
    }

    @Test
    public void postFormExampleTest1() {
        PostFormExample example = new PostFormExample();
        String url = "http://120.198.250.218:8020/fetionlhsyquery";
        Map<String, String> formMap = new HashMap<String, String>();
        formMap.put("vvm2", "phone=15889939263&&os=25&chn=5100&appid=cmccuc&passid=421272567&token=84840100013402003C31613262314F545530526B56454F4552455244557A4E6A42424E7A673140687474703A2F2F3231312E3133362E31302E3133313A383038302F4030310300040109D853040006303130303031FF0020a35bec8965dbc606c27ecb8a9e0e8d43");
        String str = example.execute(url, formMap);
        System.out.println(str);
    }

    @Test
    public void fileUpExampleTest() {
        FileUpExample example = new FileUpExample();
        String url = "https://www.baidu.com/";
        String str = example.execute(url);
        System.out.println(str);
    }

    @Test
    public void fileDownloadExampleTest() {
        FileDownloadExample example = new FileDownloadExample();
        String url = "https://www.baidu.com/";
        String str = example.execute(url);
        System.out.println(str);
    }

    @Test
    public void postExampleTest() {
        HeaderExample example = new HeaderExample();
        String url = "http://120.198.250.218:8020/fetionlhsyquery";
        // String str = example.execute(url, "vvm2", "phone=15889939263&&os=25&chn=5100&appid=cmccuc&passid=421272567&token=84840100013402003C31613262314F545530526B56454F4552455244557A4E6A42424E7A673140687474703A2F2F3231312E3133362E31302E3133313A383038302F4030310300040109D853040006303130303031FF0020a35bec8965dbc606c27ecb8a9e0e8d43");
        String str = example.execute(url, "vvm2", "I love you!");
        System.out.println(str);
    }
}
