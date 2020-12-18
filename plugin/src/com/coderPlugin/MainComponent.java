package com.coderPlugin;

import ConfigPara.TypeEntity;
import com.db.JdbcUtils;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import com.intellij.openapi.components.ProjectComponent;
import com.tools.GetMacAdd;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;

public class MainComponent implements ProjectComponent {
    private AnActionListener anActionListener = new ActionVisitor();
    public static String tableName;

    @Override
    public void projectOpened() {
        System.out.println("Application Component projectOpened");
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        MySourceDataPool.close();
        return super.clone();
    }

    @Override
    public void projectClosed() {
        System.out.println("projectClosed");
        MySourceDataPool.close();

    }


    @Override
    public void initComponent() {
//        初始化数据存储本地csv
//        String property = System.getProperties().getProperty("user.home");
//        String dataName = "\\data.csv";
//        File dataFile = new File(property + dataName);
//        TypeEntity.setCsvPath(dataFile.getPath());
//        if (!dataFile.exists()) {
//            try {
//                dataFile.createNewFile();
//                String[] header = {"time", "dataContext", "codeContext", "caretOffset", "coder_input", "coder_select", "select_num", "code_from", "IDEAcode",
//                        "IDEAcode_num", "IDEAcode_index", "AiXcode", "AiXcode_num", "AiXcoder_index", "KiteCode", "Kitecode_num", "Kitecode_index",
//                        "time_input_to_show", "time_of_select_code", "delete_behavior"};
//                DvoCSV.writeCSV(dataFile.getPath(), header);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        //初始化数据库连接池
        MySourceDataPool.init();
//        初始化线程池
        MyThreadPool.init();
        //        获取当前mac地址
        /**db：admin {id, mac, tableName}
         * 判断mac在不在db里
         * if true:
         *      current tablename = mac的table里
         * else:
         * admin 里写入mac， 和对应的tableName;
         * 再创建tableName
         * current tablename = mac的table里
         *
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetMacAdd gmc = new GetMacAdd();
                try {
                    String macAddr = gmc.getMACAddr();
                    System.out.println("本机mac地址：" + macAddr);
                    JdbcUtils ju = new JdbcUtils();
                    ju.getConnection();
                    macAddr = macAddr.replace(" ", "");
                    if (!ju.ifMacAddrExist(macAddr).equals("")) {
                        tableName = ju.ifMacAddrExist(macAddr);
                    } else {
                        tableName = "table" + macAddr;
                        ju.createTable(tableName);
                        ju.insertMacData(macAddr, tableName);
                    }
                    TypeEntity.setTableName(tableName);
                } catch (SocketException | SQLException | UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }).start();


//        初始化action监听
        ActionManager actionManager = ActionManager.getInstance();
        actionManager.addAnActionListener(anActionListener);


    }

    @Override
    public void disposeComponent() {
        System.out.println("disposeComponent");
//        关闭mysql连接池
        MySourceDataPool.close();
    }
}
