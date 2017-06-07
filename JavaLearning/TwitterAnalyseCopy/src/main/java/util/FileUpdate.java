package util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

/**
 * Created by makisucruse on 2017/5/24.
 */
public class FileUpdate {
    public static void updateFile(String fileName) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //生成上传凭证，然后准备上传
        String accessKey = "L7opcugY8cvVpIi_buMHZ7lJg4BBnQvpG4q0OYIl";
        String secretKey = "soS3iKtlEQPnmYfPXCUavcXmK2zHnG2vl88ZnFh2";
        String bucket = "tweet";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "/Users/makisucruse/Downloads/tweetPhoto/" + fileName;
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = fileName;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//            System.out.println(putRet.key);
//            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
//            Response r = ex.response;
////            System.err.println(r.toString());
//            try {
//                System.err.println(r.bodyString());
//            } catch (QiniuException ex2) {
//                //ignore
//            }
        }
    }

    public static void updateToBaidu() {

    }

}
