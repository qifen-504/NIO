package cn.xdl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Copy {
    public static void main(String[] args) {
        String from = "D:/a.txt";
        String to = "D:/b.txt";
        try {

        //获取输入输出流
        FileInputStream fis = new FileInputStream(from);
        FileOutputStream fos = new FileOutputStream(to);
        //获取通道
        FileChannel fisChannel = fis.getChannel();
        FileChannel fosChannel = fos.getChannel();
        //获取缓冲区
        ByteBuffer bf= ByteBuffer.allocate(1024);
        //循环读取数据
        while (fisChannel.read(bf)!=-1){
            int read = fisChannel.read(bf);
            //读写模式切换
            bf.flip();
            //写入数据
            fosChannel.write(bf);
            //清空缓冲区
            bf.clear();
        }
        System.out.println("复制完毕");

        }catch (IOException e ){
            e.printStackTrace();
        }
    }
}
