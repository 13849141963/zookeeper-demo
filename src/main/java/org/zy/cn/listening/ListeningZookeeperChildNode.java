package org.zy.cn.listening;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.List;

/****
 * 监听zookeeper子节点的数目的变更
 */
public class ListeningZookeeperChildNode {


    ZkClient zkClient = null;

    //连接注意事项:关闭防火墙
    @Before
    public void testBefore() {
        //连接zookeeper 集群,直接逗号隔开地址即可
        zkClient = new ZkClient("192.168.64.132:2181", 600000,60000,new SerializableSerializer());
    }

    //监听子节点数目的变更   启动监听
    @Test
    public void testRun() {
      zkClient.subscribeChildChanges("/node1", new IZkChildListener() {
          @Override
          public void handleChildChange(String nodeName, List<String> list) throws Exception {
              System.out.println("父节点的名称:"+nodeName);
              for (String data : list) {
                  System.out.println("子节点的名称:"+data);
              }
          }
      });
        try {
            //监听过程中与zookeeper的连接不能间断
             System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //测试监听子节点数目的变更
    @Test
    public void test05() {
        //在node1节点下创建子节点node1-1
        zkClient.createPersistent("/node1/node1-2");
    }

    //测试监听删除子节点的变更
    @Test
    public void test06() {
        //修改node1的数据
        zkClient.delete("/node1/node1-1");
    }


    @After
    public void testAfter() {
        //关闭连接
       zkClient.close();
    }
}
