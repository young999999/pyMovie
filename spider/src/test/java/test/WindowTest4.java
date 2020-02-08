package test;

/**
 * @author young
 * @create 2020-02-08 13:41
 */
class Window4 extends Thread {


    private static int ticket = 1000;

    private String name = "";

    public Window4(String name1) {

        this.name = name1;
    }

    public Window4() {
    }

    @Override
    public void run() {

        while (ticket > 0) {

            synchronized (Window4.class) {//Class clazz = Window2.class,Window2.class只会加载一次
                //错误的方式：this代表着t1,t2,t3三个对象
//              synchronized (this){

                if (ticket > 0) {

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(getName() + "：卖票，票号为：" + ticket + "　" + name);
                    ticket--;
                } else {
                    break;
                }
            }
        }

    }


}


public class WindowTest4 {
    public static void main(String[] args) {
        Window4 t1 = new Window4("KD");
        Window4 t2 = new Window4("KOBE");


        t1.setName("窗口1");
        t2.setName("窗口2");


        t1.start();
        t2.start();
        Window4 t3 = new Window4("LBJ");
        t3.setName("窗口3");
        t3.start();


        t3.destroy();



    }
}