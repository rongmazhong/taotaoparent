package com.taotao.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class FastDFSClientTest {

    @Test
    public void uploadFile() {
        try {
            FastDFSClient fastDFSClient = new FastDFSClient
                    ("D:\\taotaoparent\\taotao-manager-web\\src\\main\\resources\\fdfs_client.conf");
            String path =  fastDFSClient.uploadFile("C:\\Users\\Administrator\\Pictures\\test.jpg");
            System.out.println(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}