package com.regular;

import com.db.JdbcUtils;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DataAnalyzer {
    private JdbcUtils jdbcUtils;
    private float IDEA_select_sum = 0;//IDEA正确推荐项完成键数
    private int IDEA_sum = 0;//IDEA正确推荐次数
    private float IDEA_listsize = 0;//IDEA推荐列表长度
    private String IDEAcode = "";//IDEA生成代码
    private int IDEAfullLine_num = 0;//整行代码补全次数
    private int IDEAmultiLine_num = 0;
    private String IDEAcodeSize;
    private float AiXcoder_select_sum = 0;//AiXcoder正确推荐项完成键数
    private int AiXcoder_sum = 0;//AiXcoder正确推荐次数
    private float AiXcoder_listsize = 0;//AiXcoder推荐列表长度
    private String AiXcode = "";//AiXcoder生成代码
    private int AiXcoderfullLine_num = 0;
    private int AiXcodermultLine_num = 0;
    private String AiXcodeSize;

    private float Kite_select_sum = 0;//AiXcoder正确推荐项完成键数
    private int Kite_sum = 0;//AiXcoder正确推荐次数
    private float Kite_listsize = 0;//AiXcoder推荐列表长度
    private String Kitecode = "";//AiXcoder生成代码
    private int KitefullLine_num = 0;
    private int KitemultLine_num = 0;
    private String KitecodeSize;
    private int i = 0;

    public void dataAnalyze(String startTime, String endTime) {
        jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql = "select * FROM data where time >='" + startTime + "'  and time <= '" + endTime + "'";
        List<Map<String, Object>> list = null;
        try {
            list = jdbcUtils.findModeResult(sql, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (i = 0; i < list.size(); i++) {
            IDEA_listsize += (int) list.get(i).get("IDEAcode_num");//IDEA推荐列表长度
            AiXcoder_listsize += (int) list.get(i).get("AiXcode_num");//AiXcode推荐列表长度
            Kite_listsize += (int) list.get(i).get("Kitecode_num");
            if (list.get(i).get("code_from").toString().contains("IDEA")) {
                IDEA_sum += 1;
                IDEA_select_sum += (int) list.get(i).get("select_num");
                if (list.get(i).get("coder_select").toString().contains(";")) {
                    IDEAfullLine_num++;
                } else if (list.get(i).get("coder_select").toString().contains("}")||list.get(i).get("coder_select").toString().contains("\n")) {
                    IDEAmultiLine_num++;
                }
            }
            if (list.get(i).get("code_from").equals("AiXcoder")) {
                AiXcoder_sum += 1;
                AiXcoder_select_sum += (int) list.get(i).get("select_num");
                if (list.get(i).get("coder_select").toString().contains(";")) {
                    AiXcoderfullLine_num++;
                } else if (list.get(i).get("coder_select").toString().contains("}")||list.get(i).get("coder_select").toString().contains("\n")) {
                    AiXcodermultLine_num++;
                }
            }
            if (list.get(i).get("code_from").toString().contains("Kite")) {
                Kite_sum += 1;
                Kite_select_sum += (int) list.get(i).get("select_num");
                if (list.get(i).get("coder_select").toString().contains(";")||list.get(i).get("coder_select").toString().contains("\n")) {
                    KitefullLine_num++;
                } else if (list.get(i).get("coder_select").toString().contains("\n")) {
                    KitemultLine_num++;
                }
            }
            IDEAcode += list.get(i).get("IDEAcode");
            AiXcode += list.get(i).get("AiXcode");
            Kitecode += list.get(i).get("Kite");
        }
        IDEA_select_sum = IDEA_select_sum / IDEA_sum;//IDEA正确推荐项平均完成键数
        AiXcoder_select_sum = AiXcoder_select_sum / AiXcoder_sum;//Aixcoder正确推荐项平均完成键数
        Kite_select_sum = Kite_select_sum / Kite_sum;//Kite正确推荐项平均完成键数

        IDEA_listsize = IDEA_listsize / i;//IDEA平均推荐列表长度
        AiXcoder_listsize = AiXcoder_listsize / i;//aixcoder平均推荐列表长度
        Kite_listsize = Kite_listsize / i;//Kite平均推荐列表长度

        IDEAcodeSize = getPrintSize(IDEAcode.getBytes().length);//IDEA生成总代码占用空间
        AiXcodeSize = getPrintSize(AiXcode.getBytes().length);//aixcoder生成总代码占用空间
        KitecodeSize=getPrintSize(Kitecode.getBytes().length);
//        System.out.println("IDEA正确推荐项平均完成键数:" + IDEA_select_sum / IDEA_sum);
//        System.out.println("aixcoder正确推荐项平均完成键数:" + AiXcoder_select_sum / AiXcoder_sum);
//        System.out.println("IDEA平均推荐列表长度:" + IDEA_listsize / i);
//        System.out.println("aixcoder平均推荐列表长度:" + AiXcoder_listsize / i);
//        System.out.println("IDEA正确推荐次数:" + IDEA_sum);
//        System.out.println("aixcoder正确推荐次数:" + AiXcoder_sum);
//        System.out.println("IDEA生成总代码占用空间:" + getPrintSize(IDEAcode.getBytes().length));
//        System.out.println("aixcoder生成总代码占用空间:" + getPrintSize(AiXcode.getBytes().length));
//        System.out.println("IDEA整行代码补全次数：" + IDEAfullLine_num);
//        System.out.println("aixcoder整行代码补全次数：" + AiXcoderfullLine_num);
//        System.out.println("IDEA多行代码补全次数：" + IDEAfullLine_num);
//        System.out.println("aixcoder多行代码补全次数：" + AiXcoderfullLine_num);
    }

    public float getKite_select_sum() {
        return Kite_select_sum;
    }

    public int getKite_sum() {
        return Kite_sum;
    }

    public float getKite_listsize() {
        return Kite_listsize;
    }

    public String getKitecode() {
        return Kitecode;
    }

    public int getKitefullLine_num() {
        return KitefullLine_num;
    }

    public int getKitemultLine_num() {
        return KitemultLine_num;
    }

    public String getKitecodeSize() {
        return KitecodeSize;
    }

    public JdbcUtils getJdbcUtils() {
        return jdbcUtils;
    }

    public float getIDEA_select_sum() {
        return IDEA_select_sum;
    }

    public int getIDEA_sum() {
        return IDEA_sum;
    }

    public float getIDEA_listsize() {
        return IDEA_listsize;
    }

    public String getIDEAcode() {
        return IDEAcode;
    }

    public int getIDEAfullLine_num() {
        return IDEAfullLine_num;
    }

    public int getIDEAmultiLine_num() {
        return IDEAmultiLine_num;
    }

    public String getIDEAcodeSize() {
        return IDEAcodeSize;
    }

    public float getAiXcoder_select_sum() {
        return AiXcoder_select_sum;
    }

    public int getAiXcoder_sum() {
        return AiXcoder_sum;
    }

    public float getAiXcoder_listsize() {
        return AiXcoder_listsize;
    }

    public String getAiXcode() {
        return AiXcode;
    }

    public int getAiXcoderfullLine_num() {
        return AiXcoderfullLine_num;
    }

    public int getAiXcodermultLine_num() {
        return AiXcodermultLine_num;
    }

    public String getAiXcodeSize() {
        return AiXcodeSize;
    }

    @Override
    public String toString() {
        return "DataAnalyzer{" +
                "jdbcUtils=" + jdbcUtils +
                ", IDEA_select_sum=" + IDEA_select_sum +
                ", IDEA_sum=" + IDEA_sum +
                ", IDEA_listsize=" + IDEA_listsize +
                ", IDEAcode='" + IDEAcode + '\'' +
                ", IDEAfullLine_num=" + IDEAfullLine_num +
                ", IDEAmultiLine_num=" + IDEAmultiLine_num +
                ", IDEAcodeSize='" + IDEAcodeSize + '\'' +
                ", AiXcoder_select_sum=" + AiXcoder_select_sum +
                ", AiXcoder_sum=" + AiXcoder_sum +
                ", AiXcoder_listsize=" + AiXcoder_listsize +
                ", AiXcode='" + AiXcode + '\'' +
                ", AiXcoderfullLine_num=" + AiXcoderfullLine_num +
                ", AiXcodermultLine_num=" + AiXcodermultLine_num +
                ", AiXcodeSize='" + AiXcodeSize + '\'' +
                ", Kite_select_sum=" + Kite_select_sum +
                ", Kite_sum=" + Kite_sum +
                ", Kite_listsize=" + Kite_listsize +
                ", Kitecode='" + Kitecode + '\'' +
                ", KitefullLine_num=" + KitefullLine_num +
                ", KitemultLine_num=" + KitemultLine_num +
                ", KitecodeSize='" + KitecodeSize + '\'' +
                ", i=" + i +
                '}';
    }

    public static String getPrintSize(long size) {
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
    }
}
