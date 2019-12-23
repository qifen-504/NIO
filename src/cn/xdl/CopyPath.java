package cn.xdl;

import jdk.nashorn.internal.ir.WhileNode;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CopyPath {
    public static void main(String[] args) throws IOException {
        String srcPath = "D:\\壁纸";
        String destPath = "D:/a";
        new CopyPath().copyPath(srcPath,destPath);
    }
    public void copyPath(String srcPath,String destPath) throws IOException {
        //创建非直接缓冲区
        ByteBuffer bf= ByteBuffer.allocate(102400);
        //创建文件夹
        File src=new File(srcPath);
        File dest=new File(destPath);
        //如果文件夹不存在则创建新文件夹
        if (!dest.exists()){
            //创建包括所有必需但不存在的父目录
            dest.mkdirs();
        }
        File temp =null;
        //将旧目录的路径转换为字符串形式
        String[] list = src.list();
        //循环复制
        for (int  i = 0;i<list.length;i++){
            //判断文件路径结尾是否为"/"结尾
            if (srcPath.endsWith(File.separator)){//separator在windows里相当于'/'
                temp = new File(srcPath+list[i]);

            }else {
                temp=new File(srcPath+File.separator+list[i]);
            }
            if (temp.isFile()){
                System.out.println("开始读取"+temp);
                //创建输入流
                FileInputStream fis  =new FileInputStream(temp);
                //获取通道
                FileChannel fisChannel = fis.getChannel();
                //创建输出流
                FileOutputStream fos = new FileOutputStream(dest+"/"+temp.getName().toString());
                System.out.println("开始写入"+temp.getName().toString());
                FileChannel fosChannel = fos.getChannel();
                //边读边写
                while ((fisChannel.read(bf))!=-1){
                    //读写切换
                    bf.flip();
                    // 写入
                    fosChannel.write(bf);
                    //清空缓冲区
                    bf.clear();
                }
                //关闭流
                fosChannel.close();
                fisChannel.close();
                fos.close();
                fis.close();

            }
            //判断是否为文件夹
            if (temp.isDirectory()){
                //递归写入
                copyPath(src+"/"+list[i],dest+"/"+list[i]);
                System.out.println("复制完毕");
            }
        }
    }
}
