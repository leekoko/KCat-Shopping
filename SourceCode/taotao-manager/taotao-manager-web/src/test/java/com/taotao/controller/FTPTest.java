package com.taotao.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.taotao.common.utils.FtpUtil;
/**
 * 测试往图片服务器添加图片,因为图片服务器没有架设,所以该功能暂时不能用  
 * @author xint1ao
 *
 */

public class FTPTest{
	@Test
	public void testFtpClient() throws SocketException, IOException{
		//创建一个FtpClient对象  
		FTPClient ftpClient=new FTPClient();
		//创建ftp连接,默认21端口
		ftpClient.connect("192.168.25.133",21);
		//登陆ftp服务器,使用用户名和密码  
		ftpClient.login("ftpuser", "ftpuser");
		//上传文件
		//读取本地文件
		FileInputStream inputStream=new FileInputStream(new File("E:\\test.jpg"));
		//设置上传的路径
		ftpClient.changeWorkingDirectory("/home/ftpuser/www/images");
		//修改上传文件的格式  
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		//参数一:服务端文档名
		//参数二:上传文件的inputStream
		ftpClient.storeFile("hello1.jpg", inputStream);
		//关闭连接
		ftpClient.logout();
	}
	@Test
	public void testFtpUtil() throws FileNotFoundException{
		//读取本地文件
		FileInputStream inputStream=new FileInputStream(new File("E:\\test.jpg"));
		FtpUtil.uploadFile("192.168.25.133", 21, "ftpuser", "ftpuser", "/home/ftpuser/www/images", "/2015/09/04", "hello.jpg", inputStream);
	}
	
	
	
}
