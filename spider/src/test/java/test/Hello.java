package test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author young
 * @create 2020-02-08 11:48
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Hello implements Runnable {


    public static Hello hello=new Hello();
    private int ticket = 100;
    private String name = "";

    //1.实例化ReentrantLock
    private ReentrantLock lock = new ReentrantLock();

    public Hello(String name) {
        this.name = name;
    }

    @Override
    public void run() {

        while (true) {
            try {

                //2.调用锁定方法lock()
                lock.lock();

                if (ticket > 0) {

                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName() + "：售票，票号为：" + ticket + " " + name);
                    ticket--;
                } else {
                    break;
                }
            } finally {
                //3.调用解锁方法：unlock()
                lock.unlock();
            }

        }

    }

    public static void main(String[] args) {
        Hello hello = Hello.hello;
        hello.setName("kobe");
        Thread hello1 = new Thread(hello);


        Hello hello4 = Hello.hello;
        hello4.setName("LBJ");
        Thread hello2 = new Thread(hello4);
        hello1.setName("线程一");
        hello2.setName("线程二");

        hello1.start();
        hello2.start();


    }
}
