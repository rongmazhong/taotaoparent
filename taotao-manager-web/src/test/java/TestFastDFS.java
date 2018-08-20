import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.IOException;

/**
 * 〈测试fastdfs图片上传〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/7/9
 */

public class TestFastDFS {

    @Test
    public void testUpload() {

        try {
            ClientGlobal.init("D:\\taotaoparent\\taotao-manager-web\\src\\main\\resources\\fdfs_client.conf");
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageServer storageServer = null;
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            String[] strings = storageClient.upload_file("C:\\Users\\Administrator\\Pictures\\3yingyezhizhao.jpg","jpg", null);
            for (String string : strings) {
                System.out.println(string);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }


}
