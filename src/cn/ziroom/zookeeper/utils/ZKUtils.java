package cn.ziroom.zookeeper.utils;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * @desc:zookeeper工具类
 * @author dfx
 *
 */
public class ZKUtils {
	
	//私有构造方法
	private ZKUtils(){
	}
	
	private static final String connStr = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
	private static final int timeOut = 2000;
	
	private static ZooKeeper zooKeeper ;
	
	//获取ZK连接
	public static ZooKeeper getZKConn(Watcher watcher){
		
		try {
			zooKeeper = new ZooKeeper(connStr, timeOut, watcher);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return zooKeeper;
	}
	
	//创建临时节点
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
	
	//创建临时有顺序的节点
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
	
	//创建永久节点
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
	
	
	//创建有顺序的永久节点
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
	
	
	//获取指定节点下所有子节点
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
	
	//获取子节点时监听为连接ZK时注册的监听
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
	
	
	//获取指定节点下的数据
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
	
	
	//获取指定节点下的数据[重载]
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
	
	
	//关闭连接
	public static void closeConn(){
		try {
			zooKeeper.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
