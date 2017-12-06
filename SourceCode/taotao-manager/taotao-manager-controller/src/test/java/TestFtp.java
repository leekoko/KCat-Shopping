import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.taotao.common.utils.FtpUtil;

public class TestFtp {
	public static void main(String[] args) throws Exception {
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect("192.168.175.128");
		ftpClient.login("ftpuser", "ftpuser");
		FileInputStream inputStream = new FileInputStream(new File("D:\\20171108b.jpg"));
		ftpClient.changeWorkingDirectory("/home/ftpuser/www/images"); //设置上传路径
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.storeFile("testftpImg.jpg", inputStream);
		inputStream.close();
		
		ftpClient.logout();
	}
	
	@Test
	public void testFtpUtil() throws Exception{
		FileInputStream inputStream = new FileInputStream(new File("D:\\A.jpg"));
		FtpUtil.uploadFile("192.168.175.128", 21, "ftpuser", "ftpuser", "/home/ftpuser/www/images", "2017/12/06", "hai.jpg", inputStream);
		
	}
	
}
