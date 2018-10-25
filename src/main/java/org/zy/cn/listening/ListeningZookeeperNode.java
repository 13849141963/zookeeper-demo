package org.zy.cn.listening;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zy.cn.entity.User;
import java.io.IOException;

/****
 * 监听zookeeper节点的数据变化以及节点的删除
 */
public class ListeningZookeeperNode {


    ZkClient zkClient = null;

    //连接注意事项:关闭防火墙
    @Before
    public void testBefore() {
        //连接zookeeper 集群,直接逗号隔开地址即可
        zkClient = new ZkClient("192.168.64.132:2181", 600000,60000,new SerializableSerializer());
    }

    //监听节点的变更   启动监听
    @Test
    public void testRun() {
       zkClient.subscribeDataChanges("/node1", new IZkDataListener() {
           //当节点的值被改变的时候,会自动调用此方法
           @Override
           public void handleDataChange(String nodeName, Object data) throws Exception {
               System.out.println("进入监听值被改变的方法~~~~~~");
               System.out.println("当前节点的名字:"+nodeName);
               System.out.println("当前节点的新数据:"+data);
           }
           //当节点的值被删除的时候,会自动调用此方法
           @Override
           public void handleDataDeleted(String nodeName) throws Exception {
               System.out.println("进入监听节点被删除的方法~~~~~~");
               System.out.println("当前节点的名字:"+nodeName);
           }
       });
        try {
            //监听过程中与zookeeper的连接不能间断
             System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //测试监听值的变更方法
    @Test
    public void test05() {
        //修改node1的数据
        zkClient.writeData("/node1",new User(2,"张三"));
    }

    //测试监听删除节点
    @Test
    public void test06() {
        //修改node1的数据
        zkClient.deleteRecursive("/node1");
    }


    @After
    public void testAfter() {
        //关闭连接
       zkClient.close();
    }
}
