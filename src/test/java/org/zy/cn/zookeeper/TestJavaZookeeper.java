package org.zy.cn.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zy.cn.entity.User;

import java.util.List;

/****
 * java 操作 zookeeper
 */
public class TestJavaZookeeper {


    ZkClient zkClient = null;

    //连接注意事项:关闭防火墙
    @Before
    public void testBefore() {
        //连接zookeeper 集群,直接逗号隔开地址即可
        zkClient = new ZkClient("192.168.64.132:2181", 600000,60000,new SerializableSerializer());
    }
    //查看根根节点的子节点
    @Test
    public void test01() {
        List<String> children = zkClient.getChildren("/");
        for (String child : children) {
            System.out.println(child);
        }
    }
    //创建持久性子节点
    @Test
    public void test02() {
        //创建成功返回节点名字   //创建失败抛异常
        String s = zkClient.create("/node1", "节点内容", CreateMode.PERSISTENT);
        System.out.println(s);
    }

    //创建持久性子节点
    @Test
    public void test03() {
        //创建成功返回节点名字   //创建失败抛异常 存在该节点:KeeperErrorCode = NodeExists for /node1
        //创建持久性子节点
        String s1 = zkClient.create("/node1", "节点内容", CreateMode.PERSISTENT);
        //创建持久性顺序子节点
        String s2 = zkClient.create("/node1/node", "节点内容", CreateMode.PERSISTENT_SEQUENTIAL);
        //创建临时节点 关闭连接之后该节点消失
        String s3 = zkClient.create("/node1/node1", "节点内容", CreateMode.EPHEMERAL);
        //创建临时节点 关闭连接之后该节点消失  通过断点可以查看
        zkClient.createEphemeral("/node5");
        //创建临时性顺序节点 关闭连接之后该节点消失  通过断点可以查看
        String s4 = zkClient.create("/node1/node3", "节点内容", CreateMode.EPHEMERAL_SEQUENTIAL);
        //删除节点 返回值为是否删除成功
        boolean delete = zkClient.delete("/node1/node");
        //System.out.println(s1);
    }

    //查看节点的状态
    @Test
    public void test04() {
        //查看节点的状态
        Stat stat = new Stat();
        Object status = zkClient.readData("/node1", stat);
        System.out.println(status);
        System.out.println(stat);
    }

    //存储对象
    @Test
    public void test05() {
        zkClient.writeData("/node2",new User(1,"张三"));
        //自动将对象反序列化
        Stat stat = new Stat();
        Object status = zkClient.readData("/node2", stat);
        System.out.println(status);
        System.out.println(stat);
    }

    @After
    public void testAfter() {
        //关闭连接
        zkClient.close();
    }
}
