package com.mrrun.module_okhttp3;

import com.mrrun.module_okhttp3.simpleexample.execute.FileDownloadExample;
import com.mrrun.module_okhttp3.simpleexample.execute.FileUpExample;
import com.mrrun.module_okhttp3.simpleexample.execute.GetExample;
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
}
