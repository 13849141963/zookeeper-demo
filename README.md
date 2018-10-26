# zookeeper-demo
java操作zookeeper增删改查,监听节点得数据变更,子节点数目的变更,以及用zookeeper实现分布式锁的解决方案


ListeningZookeeperNode       监听zookeeper节点的数据变化以及节点的删除
ListeningZookeeperChildNode  监听zookeeper子节点的数目的变更
DistributedLock              分布式锁类
TestDiestributedLock         测试类
TestDiestributedLock2        测试类

基于ZooKeeper分布式锁的流程:1.在zookeeper指定节点（locks）下创建临时顺序节点node_n
                          2.获取locks下所有子节点children
			  3.对子节点按节点自增序号从小到大排序
			  4.判断本节点是不是第一个子节点，若是，则获取锁；若不是，则监听比该节点小的那个节点的删除事件
			  5.若监听事件生效，则回到第二步重新进行判断，直到获取到锁

 存在的情况  问题一:当两个用户对一条数据进行修改时,此时第一个请求进入,创建临时有序节点,处理业务,而此时第二个请求进来发现已经创建了节点,等待节点消失,
                  问题来了:等待超时没获取锁要提醒用户:"该数据正在处理中~~~~~"
            问题二:第一个用户没有在规定的时间内完成操作的话临时顺序节点会消失,此时第二个用户进行操作,第一个操作的用户得到的反馈数据是什么
            
 完善:分布式锁           
