package cn.ziroom.zookeeper.pc;

import java.util.List;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import cn.ziroom.zookeeper.utils.ZKUtils;

//�ͻ���
public class Consumer {

	public static void main(String[] args) {
		//1.��ȡ����
		ZKUtils.getZKConn(new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				System.out.println(event.getPath()+"==="+event.getType());
				List<String> childern = ZKUtils.getPathChildern("/ZRAMS", true);
				for (String child : childern) {
					byte[] data = ZKUtils.getData("/ZRAMS/" + child, true);
					System.out.println("·��:"+child+" | ����:"+new String(data));
				}
			}
		});
		
		//2.doSomething
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
