package cn.ziroom.zookeeper.pc;

import java.util.List;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import cn.ziroom.zookeeper.utils.ZKUtils;

//客户端
public class Consumer {

	public static void main(String[] args) {
		//1.获取连接
		ZKUtils.getZKConn(new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				System.out.println(event.getPath()+"==="+event.getType());
				List<String> childern = ZKUtils.getPathChildern("/ZRAMS", true);
				for (String child : childern) {
					byte[] data = ZKUtils.getData("/ZRAMS/" + child, true);
					System.out.println("路径:"+child+" | 数据:"+new String(data));
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
