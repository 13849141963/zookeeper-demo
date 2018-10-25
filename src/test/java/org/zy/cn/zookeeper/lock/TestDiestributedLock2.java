package org.zy.cn.zookeeper.lock;

public class TestDiestributedLock2 {



    static int n = 500;

    public static void secskill() {
        System.out.println(--n);
    }

    public static void main(String[] args) {

        /*Runnable runnable = new Runnable() {
            public void run() {
                DistributedLock lock = null;
                try {
                    lock = new DistributedLock("192.168.64.132:2181", "lock");
                    lock.lock();
                    secskill();
                    System.out.println(Thread.currentThread().getName() + "正在运行");
                } finally {
                    if (lock != null) {
                        lock.unlock();
                    }
                }
            }
        };*/

       /* for (int i = 0; i < 10; i++) {
            Thread t = new Thread(runnable);
            t.start();
        }*/

        DistributedLock lock = null;
        try {
            lock = new DistributedLock("192.168.64.132:2181", "lock");
            lock.lock();
        }catch (Exception s){
            if (lock != null) {
                lock.unlock();
            }
        }
    }
}
