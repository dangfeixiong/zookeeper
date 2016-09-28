package cn.ziroom.zookeeper.utils;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * @desc:zookeeper������
 * @author dfx
 *
 */
public class ZKUtils {
	
	//˽�й��췽��
	private ZKUtils(){
	}
	
	private static final String connStr = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
	private static final int timeOut = 2000;
	
	private static ZooKeeper zooKeeper ;
	
	//��ȡZK����
	public static ZooKeeper getZKConn(Watcher watcher){
		
		try {
			zooKeeper = new ZooKeeper(connStr, timeOut, watcher);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return zooKeeper;
	}
	
	//������ʱ�ڵ�
	public static String createTemporaryNode(String path, byte[] data){
		String result = "";
		try {
			zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result ;
	}
	
	//������ʱ��˳��Ľڵ�
	public static String createTemporary_OrderNode(String path, byte[] data){
		String result = "";
		try {
			result = zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result ;
	}
	
	//�������ýڵ�
	public static String createPermanentNode(String path, byte[] data){
		String result = "";
		try {
			result = zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result ;
	}
	
	
	//������˳������ýڵ�
	public static String createPermanent_OrderNode(String path, byte[] data){
		String result = "";
		try {
			result = zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result ;
	}
	
	
	//��ȡָ���ڵ��������ӽڵ�
	public static List<String> getPathChildern(String path,Watcher watcher){
		List<String> childern = null ;
		try {
			childern = zooKeeper.getChildren(path, watcher);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return childern;
	}
	
	//��ȡ�ӽڵ�ʱ����Ϊ����ZKʱע��ļ���
	public static List<String> getPathChildern(String path, boolean watcher){
		List<String> childern = null ;
		try {
			childern = zooKeeper.getChildren(path, watcher);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return childern;
	}
	
	
	//��ȡָ���ڵ��µ�����
	public static byte[] getData(String path, Watcher watcher){
		byte[] data = null ;
		try {
			data = zooKeeper.getData(path, watcher, null);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data ;
	}
	
	
	//��ȡָ���ڵ��µ�����[����]
	public static byte[] getData(String path, boolean watcher){
		byte[] data = null ;
		try {
			data = zooKeeper.getData(path, watcher, null);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data ;
	}
	
	
	//�ر�����
	public static void closeConn(){
		try {
			zooKeeper.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
