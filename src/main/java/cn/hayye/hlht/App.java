package cn.hayye.hlht;

import cn.hayye.hlht.utils.AesUtils;
import cn.hayye.hlht.utils.HMacMd5Utils;
import com.alibaba.fastjson2.JSONObject;

public class App {


    /**
     * 备注：全称未用到operatorSecret，是因为这个字段是获取token时使用，数据加密不涉及该字段
     * @param args
     */
    public static void main(String[] args) {
        App app = new App();
        app.demo_enc();
        app.demo_dec();
    }

    // 加密示例
    // 应用场景：运营商加密后发起请求
    // 数据来源于中电联协议 第四部分 附录
    public void demo_enc(){
        String operatorId = "123456789";     //运营商ID
        String operatorSecret = ""; //运营商秘钥
        String dataSecret = "1234567890abcdef";     //消息密钥
        String dataSecretIV = "1234567890abcdef";    //消息密钥初始化向量
        String sigSecret = "1234567890abcdef";      //签名秘钥
        //第1步：数据加密
        String data = "{\"total\":1,\"stationStatusInfo\":{\"operationID\":\"123456789\",\"stationID\":\"111111111111111\",\"connectorStatusInfos\":{\"connectorID\":1,\"equipmentID\":\"10000000000000000000001\",\"status\":4,\"currentA\":0,\"currentB\":0,\"currentC\":0,\"voltageA\":0,\"voltageB\":0,\"voltageC\":0,\"soc\":10,}}}";
        String data_enc = AesUtils.encryptAES(data,dataSecret,dataSecretIV);
        System.out.println(data_enc);
        //第2步：消息签名
        String timestamp = "20160729142400";
        String seq = "0001";
        String sign = HMacMd5Utils.signHex(sigSecret,operatorId,data_enc,timestamp,seq);
        System.out.println(sign);
        //第3步：组装报文
        JSONObject json = new JSONObject();
        json.put("OperatorID", operatorId);
        json.put("Data",data_enc);
        json.put("TimeStamp", timestamp);
        json.put("Seq",seq);
        json.put("Sig", sign);
        System.out.println(json);
    }

    // 解密示例
    // 应用场景：平台商接收数据并解密
    // 数据来源于加密示例
    public void demo_dec(){
        String operatorId = "123456789";     //运营商ID
        String operatorSecret = ""; //运营商秘钥
        String dataSecret = "1234567890abcdef";     //消息密钥
        String dataSecretIV = "1234567890abcdef";    //消息密钥初始化向量
        String sigSecret = "1234567890abcdef";      //签名秘钥
        String req = "{\"OperatorID\":\"123456789\",\"Data\":\"il7B0BSEjFdzpyKzfOFpvg/Se1CP802RItKYFPfSLRxJ3jf0bVl9hvYOEktPAYW2nd7S8MBcyHYyacHKbISq5iTmDzG+ivnR+SZJv3USNTYVMz9rCQVSxd0cLlqsJauko79NnwQJbzDTyLooYoIwz75qBOH2/xOMirpeEqRJrF/EQjWekJmGk9RtboXePu2rka+Xm51syBPhiXJAq0GfbfaFu9tNqs/e2Vjja/ltE1M0lqvxfXQ6da6HrThsm5id4ClZFIi0acRfrsPLRixS/IQYtksxghvJwbqOsbIsITail9Ayy4tKcogeEZiOO+4Ed264NSKmk7l3wKwJLAFjCFogBx8GE3OBz4pqcAn/ydA=\",\"TimeStamp\":\"20160729142400\",\"Seq\":\"0001\",\"Sig\":\"745166E8C43C84D37FFEC0F529C4136F\"}";
        JSONObject reqJson = JSONObject.parseObject(req);
        //第一步：验证签名
        String sign = HMacMd5Utils.signHex(sigSecret,operatorId,reqJson.getString("Data"),reqJson.getString("TimeStamp"),reqJson.getString("Seq"));
        if (!sign.equals(reqJson.get("Sig"))){
            throw new RuntimeException("签名不一致，校验失败");
        }
        //第二步：解密数据
        String dataStr = AesUtils.decryptAES(reqJson.getString("Data"),dataSecret,dataSecretIV);
        System.out.println(dataStr);
    }
}
