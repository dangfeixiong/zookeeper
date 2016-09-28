package cn.ziroom.zookeeper.pc;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import cn.ziroom.zookeeper.utils.ZKUtils;

//第一个server
public class Producer2 {

	public static void main(String[] args) {
		//1.获取连接
		ZKUtils.getZKConn(new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				//服务端获取连接不用监听
			}
		});
		
		//2.创建节点
		ZKUtils.createTemporary_OrderNode("/ZRAMS/server", "server-02".getBytes());
		
		//3.doSomething
		try {
			//线程运行
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
