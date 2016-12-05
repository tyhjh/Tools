package tools;

import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tyhj on 2016/12/5.
 */

public class MyHttp {

    //访问URL并获取返回信息
    public static JSONObject getJson(String data, String url, String way) {
        HttpURLConnection conn = null;
        URL mURL = null;
        if (way.equals("GET")) {
            try {
                if(data==null)
                    mURL = new URL(url);
                else
                    mURL = new URL(url + "?" + data);
                conn = (HttpURLConnection) mURL.openConnection();
                //conn.addRequestProperty("头","这里是添加头");
                conn.setRequestMethod("GET");
                conn.setReadTimeout(5000);
                conn.setConnectTimeout(30000);
                InputStream is = conn.getInputStream();
                String state = inputStream2String(is);
                //Log.e("Tag", state);
                JSONObject jsonObject = new JSONObject(state);
                if (jsonObject != null)
                    return jsonObject;
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else if (way.equals("POST")) {
            try {
                mURL = new URL(url);
                conn = (HttpURLConnection) mURL.openConnection();
                //conn.addRequestProperty("头","我就是头");
                conn.setRequestMethod("POST");
                conn.setReadTimeout(5000);
                conn.setConnectTimeout(30000);
                conn.setDoOutput(true);
                OutputStream out = conn.getOutputStream();
                out.write(data.getBytes());
                out.flush();
                out.close();
                int responseCode = conn.getResponseCode();// 调用此方法就不必再使用conn.connect()方
                if (responseCode == 200) {
                    InputStream is = conn.getInputStream();
                    String state = inputStream2String(is);
                    Log.e("Tag",state);
                    JSONObject jsonObject = new JSONObject(state);
                    if (jsonObject != null)
                        return jsonObject;
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
        return null;
    }

    //数据流转字符串
    public static String inputStream2String(InputStream is) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // 模板代码 必须熟练
        byte[] buffer = new byte[1024];
        int len = -1;
        // 一定要写len=is.read(buffer)
        // 如果while((is.read(buffer))!=-1)则无法将数据写入buffer中
        try {
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
                is.close();
                String state = os.toString();// 把流中的数据转换成字符串,采用的编码是utf-8(模拟器默认编码)
                os.close();
                return state;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

}
