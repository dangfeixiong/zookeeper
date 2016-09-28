package cn.ziroom.zookeeper.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**   
 * @Title ZKTest.java 
 * @Package cn.ziroom.zookeeper 
 * @Description:zookeeper测试
 * @author dfx  
 * @date 2015-11-25 上午10:15:43 
 * @version V1.0   
 */

/**
 *zookeeper:提供分布式协调服务
 *常见用法:
 *		1.名称服务
 *		2.分布式锁
 *		......
 *本质功能:
 *	1.替用户保管数据
 *	2.为用户保管的数据提供监听
 */
public class ZKTest {
	
	private ZooKeeper zooKeeper;
	
	@Before
	public void init() throws IOException{
		//连接字符串
		String connectString = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
		//设置连接超时时间
		int sessionTimeout = 2000;
		zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
		// 监控所有被触发的事件
				@Override
				public void process(WatchedEvent event) {
					//System.out.println(event.getPath());
				}
			});
	}

	/**
	 * @desc 创建节点
	 * @throws UnsupportedEncodingException
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	@Test
	public void createZNode() throws UnsupportedEncodingException, KeeperException, InterruptedException{
		String path1 = "/ziroom/day1";
		byte[] data1 = "永久节点".getBytes("utf-8");
		//path-->创建节点路径
		//data-->节点中存储数据
		//Ids.OPEN_ACL_UNSAFE-->节点权限设置
		//CreateMode.PERSISTENT-->创建节点类型
		//	----PERSISTENT(永久节点)
		//	----PERSISTENT_SEQUENTIAL：顺序自动编号的目录节点
		//	----EPHEMERAL(临时节点)
		//	----EPHEMERAL_SEQUENTIAL：临时自动编号节点
		String path2 = "/ziroom/day2";
		byte[] data2 = "临时节点".getBytes("utf-8");
		String result1 = zooKeeper.create(path1, data1, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		String result2 = zooKeeper.create(path2, data2, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		
		//测试节点类型
		Thread.sleep(Long.MAX_VALUE);
		
		System.out.println("结果集：" + result1);
		System.out.println("结果集：" + result2);
	}
	
	/**
	 * @desc 获取数据节点及子节点
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	@Test
	public void getZNode() throws KeeperException, InterruptedException{
		String path = "/";
		List<String> children = zooKeeper.getChildren(path, new Watcher() {
			//设置监听
			@Override
			public void process(WatchedEvent event) {
				System.out.println("数据监听结果:"+event.getType());
			}
		});
		
		for (String child : children) {
			System.out.println("获取数据路径:" + child);
		}
	}
	
	/**
	 * @desc 获取节点数据
	 * @throws KeeperException
	 * @throws InterruptedException
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void getZNodeData() throws KeeperException, InterruptedException, UnsupportedEncodingException{
		String path = "/ziroom/day1";
		//path-->获取数据的节点路径
		//Watcher-->使用初始化zooKeeper时创建的监听
		//Stat-->指定获取数据的版本信息
		byte[] data = zooKeeper.getData(path, true, null);
		System.out.println("节点中数据为:" + new String(data,"utf-8"));
	}
	
	/**
	 * @desc 设置节点数据
	 * @throws UnsupportedEncodingException
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	@Test
	public void setZNodeData() throws UnsupportedEncodingException, KeeperException, InterruptedException{
		//path-->获取数据的节点路径
		//data-->节点中存储数据
		//version-->指定数据的版本号
		String path = "/ziroom/day1";
		byte[] data = "lainjia".getBytes("utf-8");
		int version = -1;
		Stat result = zooKeeper.setData(path, data, version);
		System.out.println("设置后数据信息为:"+result.getDataLength());
	}
	
	/**
	 * @desc 删除节点信息
	 * @throws InterruptedException
	 * @throws KeeperException
	 */
	@Test
	public void deleteZNode() throws InterruptedException, KeeperException{
		//path-->删除节点路径
		//version-->删除节点的版本信息
		String path = "/ziroom/day1";
		int version = -1;
		zooKeeper.delete(path, version);
	}
	
	/**
	 * @desc 判断节点是否存在
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	@Test
	public void exists() throws KeeperException, InterruptedException{
		String path = "/ziroom";
		Stat exists = zooKeeper.exists(path, true);
		System.out.println(exists.getDataLength());
	}
	
	/**
	 * @desc 关闭连接
	 * @throws InterruptedException
	 */
	@After
	public void destroy() throws InterruptedException{
		//关闭zk连接
		zooKeeper.close();
	}
	
}
