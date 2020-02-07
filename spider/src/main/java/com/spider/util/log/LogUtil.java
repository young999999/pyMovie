package com.spider.util.log;

import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author young
 * @create 2020-02-05 22:10
 */
public class LogUtil {


    public static void fileWriter(File filename, String content) {
        FileWriter fw = null;
        try {
            //1.提供File类的对象，指明写出到的文件
            File file = filename;

            //2.提供FileWriter的对象，用于数据的写出
            fw = new FileWriter(file, true);

            //3.写出的操作
            fw.write(content);
            fw.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.流资源的关闭
            if (fw != null) {

                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public static File creatFile(String fileName) {
        File file = new File(fileName);
        //文件存在
        if (file.exists()) {
            file.delete();
//            System.out.println("删除成功");
//            System.out.println("文件存在");
        } else {
            //文件的创建
            try {
                file.createNewFile();
//                System.out.println("创建成功");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;

    }

    @Test
    public void FileReader() {
        FileReader fr = null;
        try {
            //1.实例化File类的对象，指明要操作的文件
            File file = new File("./hello1.txt");//相较于当前Module
            //2.提供具体的流
            fr = new FileReader(file);

            //3.数据的读入
            //read():返回读入的一个字符。如果达到文件末尾，返回-1
            //方式一：
//        int data = fr.read();
//        while(data != -1){
//            System.out.print((char)data);
//            data = fr.read();
//        }

            //方式二：语法上针对于方式一的修改
            int data;
            while ((data = fr.read()) != -1) {
                System.out.print((char) data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.流的关闭操作
//            try {
//                if(fr != null)
//                    fr.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            //或
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
        File file = LogUtil.creatFile("./日志.txt");
        LogUtil.fileWriter(file,"我的\n");

    }
}
