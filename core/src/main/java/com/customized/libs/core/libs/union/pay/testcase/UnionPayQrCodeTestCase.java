package com.customized.libs.core.libs.union.pay.testcase;

import com.unionpay.demo.TestCase;
import com.unionpay.exception.ApiException;
import com.unionpay.utils.CommonUtil;
import com.unionpay.utils.HttpClient;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yan
 */
public class UnionPayQrCodeTestCase {

    private static Logger logger = Logger.getLogger(TestCase.class);

    // public static String url = "http://172.21.200.50:8080/xwins/gateway/batchGenerateQrcode";
    public static String url = "https://xwm.test.95516.com/xwins/gateway/batchGenerateQrcode";

    protected static final String aesKey = "9xnaYQVQbaqW/TbboggxRg==";

    protected static String rsaKey = getPublicKeyFromPem();

    public UnionPayQrCodeTestCase() {
    }

    public static String getPublicKeyFromPem() {
        try {
            String filePath = "./pkcs8_private_key.pem";
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String s = br.readLine();
            StringBuffer privatekey = new StringBuffer();

            for (s = br.readLine(); s.charAt(0) != '-'; s = br.readLine()) {
                privatekey.append(s);
            }

            br.close();
            System.out.println("appCfgFile's key=" + privatekey.toString());
            return privatekey.toString();
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Map<String, Object> paramMap = new HashMap();
        testA(paramMap);
        String result = null;

        try {
            String type = CommonUtil.checkUrl(paramMap, url);
            logger.info("urlType:" + type);
            Map<String, String> requestMap = CommonUtil.dealWithParam(paramMap, rsaKey, "9xnaYQVQbaqW/TbboggxRg==");
            result = HttpClient.post(url, requestMap, "UTF-8");
            System.out.println("requestMap :" + requestMap);
            System.out.println("result :" + result);
        } catch (ApiException var5) {
            logger.error("error : " + var5.getErrMsg());
        } catch (Exception var6) {
            logger.error("error : " + var6.getMessage());
        }

    }

    public static void testA(Map<String, Object> paramMap) {
        System.out.println("测试方法 init...");
        normalParam(paramMap);
        Map<String, Object> bizMap = new HashMap();
        batchQrcode(bizMap);
        paramMap.put("bizContent", bizMap);
    }

    protected static void batchQrcode(Map<String, Object> bizMap) {
        Integer qrCodeNum = 1000;
        bizMap.put("qrCodeNum", String.valueOf(qrCodeNum));
    }

    protected static void batchQrcodeQuery(Map<String, Object> bizMap) {
        bizMap.put("batchNo", "00000317");
    }

    protected static void queryQrcode(Map<String, Object> bizMap) {
        bizMap.put("qrCode", "https://qr.95516.com/00010002/62422309557386655861967507435823");
    }

    protected static void modifyQrcode(Map<String, Object> bizMap) {
        bizMap.put("merId", "QRC330100000274");
        bizMap.put("qrNum", "483600006100000000");
        bizMap.put("qrCodeName", "粉色发设");
    }

    protected static void multiQrcode(Map<String, Object> bizMap) {
        List<String> list = new ArrayList();
        bizMap.put("merId", "QRC331090000042");
        bizMap.put("applyNum", "0");
        bizMap.put("applyNameList", list.toString());
    }

    protected static void image(Map<String, Object> bizMap) {
        File file = new File("C:\\21028119831105153x.jpg");
        bizMap.put("imgContent", file.toString());
        bizMap.put("merId", "QRC370200002775");
        bizMap.put("imgType", "02");
    }

    protected static void normalParam(Map<String, Object> paramMap) {
        paramMap.put("version", "1.0");
        // paramMap.put("expandcode", "01020000");
        paramMap.put("expandcode", "C0000017");
        paramMap.put("encoding", "UTF-8");
        paramMap.put("requestId", "11");
        paramMap.put("signMethod", "RSA2");
        paramMap.put("signature", "sss");
    }
}
