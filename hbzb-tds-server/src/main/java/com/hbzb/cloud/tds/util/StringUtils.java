package com.hbzb.cloud.tds.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具
 */
public class StringUtils {

    /**
     * 判断字符串是否为空
     * @param str
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        if(null == str || str.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 返回去除首尾空格的字符串
     * @param str
     * @return
     */
    public static String trimToEmpty(String str) {
        if(null == str || str.isEmpty()) {
            return "";
        }
        //单词间隔常量 " "
        if(str.equals(Character.toString((char)32))) {
            return str;
        }
        return str.trim();
    }

    /**
     * url encode
     * @param s
     * @return
     */
    public static String urlEncode(String s) {
        try {
            s = URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return s;
    }

    /**
     * url decode
     * @param s
     * @return
     */
    public static String urlDecode(String s) {
        try {
            s = URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return s;
    }

    /**
     * BASE64编码
     * @param bstr
     * @return String
     */
    @SuppressWarnings("restriction")
    public static String base64Encode(byte[] bstr) {
        String strEncode = new BASE64Encoder().encode(bstr);
        return strEncode;
    }

    /**
     * BASE64解码
     * @param str
     * @return
     */
    @SuppressWarnings("restriction")
    public static String base64Decode(String str) {
        if (isEmpty(str)) {
            return null;
        }
        String s = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = decoder.decodeBuffer(str);
            s = new String(b, "UTF8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            s = null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            s = null;
        }
        return s;
    }

    /**
     * 对象转JSON
     * @param obj
     * @return String
     */
    public static String convertToJSONString(Object obj) {
        String result = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.writeValueAsString(obj);
        } catch (JsonGenerationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 检查字符是否为合法日期时间格式
     * @param dateTime
     * @return
     */
    public static boolean isValidDate(String dateTime) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            format.setLenient(false);
            format.parse(dateTime);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 验证输入金额,精确到小数点两位
     * @param input
     * @return boolean
     */
    public static boolean isAmount(String input) {
        boolean result = false;
        Pattern pattern = Pattern.compile("^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$");
        Matcher matcher = pattern.matcher(input);
        if(matcher.matches()) result = true;
        return result;
    }

    /**
     * 验证正整数
     * @param input
     * @return boolean
     */
    public static boolean isInt(String input) {
        boolean result = false;
        Pattern pattern = Pattern.compile("^[0-9]*[1-9][0-9]*$");
        Matcher matcher = pattern.matcher(input);
        if(matcher.matches()) result = true;
        return result;
    }

    /**
     * 验证布尔值
     * @param input
     * @return boolean
     */
    public static boolean isBoolean(String input) {
        boolean result = false;
        if(input.equals("true")||input.equals("false")) result = true;
        return result;
    }

    public static void main(String[] args) {
        String s = "投标保函 河北西柏坡发电有限责任公司、河北西柏坡第二发电有限责任公司专利代理咨询服务项目";
        s = s.substring(0, 42);
        System.out.println(s);
        System.out.println(s.length());
        System.out.println(isInt("99999"));
    }
}
