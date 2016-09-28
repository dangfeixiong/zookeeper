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
 * @Description:zookeeper����
 * @author dfx  
 * @date 2015-11-25 ����10:15:43 
 * @version V1.0   
 */

/**
 *zookeeper:�ṩ�ֲ�ʽЭ������
 *�����÷�:
 *		1.���Ʒ���
 *		2.�ֲ�ʽ��
 *		......
 *���ʹ���:
 *	1.���û���������
 *	2.Ϊ�û����ܵ������ṩ����
 */
public class ZKTest {
	
	private ZooKeeper zooKeeper;
	
	@Before
	public void init() throws IOException{
		//�����ַ���
		String connectString = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
		//�������ӳ�ʱʱ��
		int sessionTimeout = 2000;
		zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
		// ������б��������¼�
				@Override
				public void process(WatchedEvent event) {
					//System.out.println(event.getPath());
				}
			});
	}

	/**
	 * @desc �����ڵ�
	 * @throws UnsupportedEncodingException
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	@Test
	public void createZNode() throws UnsupportedEncodingException, KeeperException, InterruptedException{
		String path1 = "/ziroom/day1";
		byte[] data1 = "���ýڵ�".getBytes("utf-8");
		//path-->�����ڵ�·��
		//data-->�ڵ��д洢����
		//Ids.OPEN_ACL_UNSAFE-->�ڵ�Ȩ������
		//CreateMode.PERSISTENT-->�����ڵ�����
		//	----PERSISTENT(���ýڵ�)
		//	----PERSISTENT_SEQUENTIAL��˳���Զ���ŵ�Ŀ¼�ڵ�
		//	----EPHEMERAL(��ʱ�ڵ�)
		//	----EPHEMERAL_SEQUENTIAL����ʱ�Զ���Žڵ�
		String path2 = "/ziroom/day2";
		byte[] data2 = "��ʱ�ڵ�".getBytes("utf-8");
		String result1 = zooKeeper.create(path1, data1, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		String result2 = zooKeeper.create(path2, data2, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		
		//���Խڵ�����
		Thread.sleep(Long.MAX_VALUE);
		
		System.out.println("�������" + result1);
		System.out.println("�������" + result2);
	}
	
	/**
	 * @desc ��ȡ���ݽڵ㼰�ӽڵ�
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	@Test
	public void getZNode() throws KeeperException, InterruptedException{
		String path = "/";
		List<String> children = zooKeeper.getChildren(path, new Watcher() {
			//���ü���
			@Override
			public void process(WatchedEvent event) {
				System.out.println("���ݼ������:"+event.getType());
			}
		});
		
		for (String child : children) {
			System.out.println("��ȡ����·��:" + child);
		}
	}
	
	/**
	 * @desc ��ȡ�ڵ�����
	 * @throws KeeperException
	 * @throws InterruptedException
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void getZNodeData() throws KeeperException, InterruptedException, UnsupportedEncodingException{
		String path = "/ziroom/day1";
		//path-->��ȡ���ݵĽڵ�·��
		//Watcher-->ʹ�ó�ʼ��zooKeeperʱ�����ļ���
		//Stat-->ָ����ȡ���ݵİ汾��Ϣ
		byte[] data = zooKeeper.getData(path, true, null);
		System.out.println("�ڵ�������Ϊ:" + new String(data,"utf-8"));
	}
	
	/**
	 * @desc ���ýڵ�����
	 * @throws UnsupportedEncodingException
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	@Test
	public void setZNodeData() throws UnsupportedEncodingException, KeeperException, InterruptedException{
		//path-->��ȡ���ݵĽڵ�·��
		//data-->�ڵ��д洢����
		//version-->ָ�����ݵİ汾��
		String path = "/ziroom/day1";
		byte[] data = "lainjia".getBytes("utf-8");
		int version = -1;
		Stat result = zooKeeper.setData(path, data, version);
		System.out.println("���ú�������ϢΪ:"+result.getDataLength());
	}
	
	/**
	 * @desc ɾ���ڵ���Ϣ
	 * @throws InterruptedException
	 * @throws KeeperException
	 */
	@Test
	public void deleteZNode() throws InterruptedException, KeeperException{
		//path-->ɾ���ڵ�·��
		//version-->ɾ���ڵ�İ汾��Ϣ
		String path = "/ziroom/day1";
		int version = -1;
		zooKeeper.delete(path, version);
	}
	
	/**
	 * @desc �жϽڵ��Ƿ����
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
	 * @desc �ر�����
	 * @throws InterruptedException
	 */
	@After
	public void destroy() throws InterruptedException{
		//�ر�zk����
		zooKeeper.close();
	}
	
}
