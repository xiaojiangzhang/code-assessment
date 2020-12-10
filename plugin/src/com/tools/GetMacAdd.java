package com.tools;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class GetMacAdd {
    public String getMACAddr() throws SocketException, UnknownHostException {

        // 获得ＩＰ
        NetworkInterface netInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());

        // 获得Mac地址的byte数组
        byte[] macAddr = netInterface.getHardwareAddress();
        //System.out.print("MAC Addr:\t");

        String macAdd = "";
        // 循环输出
        for (byte b : macAddr) {
            //拼接mac地址
            macAdd += toHexString(b) + " ";
        }
        return macAdd.substring(0, macAdd.length()-1);
    }

    public static String toHexString(int integer) {

        // 将得来的int类型数字转化为十六进制数
        String str = Integer.toHexString((int) (integer & 0xff));

        // 如果遇到单字符，前置0占位补满两格
        if (str.length() == 1) {
            str = "0" + str;
        }
        return str;
    }

    public static void main(String[] args) throws SocketException, UnknownHostException {
        GetMacAdd gma = new GetMacAdd();
        System.out.println("'"+gma.getMACAddr()+"'");
    }
}
