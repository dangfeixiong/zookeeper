package cn.ziroom.zookeeper.pc;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import cn.ziroom.zookeeper.utils.ZKUtils;

//��һ��server
public class Producer2 {

	public static void main(String[] args) {
		//1.��ȡ����
		ZKUtils.getZKConn(new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				//����˻�ȡ���Ӳ��ü���
			}
		});
		
		//2.�����ڵ�
		ZKUtils.createTemporary_OrderNode("/ZRAMS/server", "server-02".getBytes());
		
		//3.doSomething
		try {
			//�߳�����
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
