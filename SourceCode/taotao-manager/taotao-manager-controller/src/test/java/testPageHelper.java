import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;

public class testPageHelper {
	@Test
	public void testPageHelper() {
		//创建一个spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		//获取Mapper代理对象
		TbItemMapper mapper = applicationContext.getBean(TbItemMapper.class);
		//执行查询并分页
		TbItemExample example = new TbItemExample();
		PageHelper.startPage(1, 10);
		List<TbItem> list = mapper.selectByExample(example);
		//取商品列表   
		for (TbItem tbItem : list) {
			System.out.println(tbItem.getTitle());
		}
		//取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		System.out.println("总共有商品信息条数："+total);
	}

}
