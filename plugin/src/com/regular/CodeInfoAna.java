package com.regular;


import com.bean.CodeIfo;

import java.util.ArrayList;
import java.util.List;

public class CodeInfoAna {
    private List<CodeIfo> codeIfoList;
    private List<Integer> aiXcode_key_nums = new ArrayList<>();
    private List<Integer> IDEA_key_nums = new ArrayList<>();

    private double IDEA_select_sum = 0;//IDEA正确推荐项完成键数
    private int IDEA_sum = 0;//IDEA正确推荐次数
    private double IDEA_listsize = 0;//IDEA推荐列表长度
    private String IDEAcode = "";//IDEA生成代码
    private int IDEAfullLine_num = 0;//整行代码补全次数
    private int IDEAmultiLine_num = 0;
    private double IDEAcodeSize;
    private double AiXcoder_select_sum = 0;//AiXcoder正确推荐项完成键数
    private int AiXcoder_sum = 0;//AiXcoder正确推荐次数
    private double AiXcoder_listsize = 0;//AiXcoder推荐列表长度
    private String AiXcode = "";//AiXcoder生成代码
    private int AiXcoderfullLine_num = 0;
    private int AiXcodermultLine_num = 0;
    private double AiXcodeSize;

    private int i = 0;

    public CodeInfoAna(List<CodeIfo> codeInfoAnas) {
        this.codeIfoList = codeInfoAnas;
    }

    public void initAna() {
        for (i = 0; i < codeIfoList.size(); i++) {
            IDEA_listsize += Integer.parseInt(codeIfoList.get(i).getIDEAcode_num());//IDEA推荐列表长度
            AiXcoder_listsize += Integer.parseInt(codeIfoList.get(i).getAiXcode_num());//AiXcode推荐列表长度
            if (codeIfoList.get(i).getCode_from().contains("IDEA")) {
                IDEA_sum += 1;
                IDEA_select_sum += codeIfoList.get(i).getSelect_num();
                IDEA_key_nums.add(codeIfoList.get(i).getSelect_num());

                if (codeIfoList.get(i).getCoder_select().contains(")") || codeIfoList.get(i).getCoder_select().contains("}") || codeIfoList.get(i).getCoder_select().contains("\n")) {
                    IDEAfullLine_num++;
                }
            }
            if (codeIfoList.get(i).getCode_from().contains("AiXcoder")) {
                AiXcoder_sum += 1;
                AiXcoder_select_sum += codeIfoList.get(i).getSelect_num();
                aiXcode_key_nums.add(codeIfoList.get(i).getSelect_num());

                if (codeIfoList.get(i).getCoder_select().contains(")") || codeIfoList.get(i).getCoder_select().contains("}") || codeIfoList.get(i).getCoder_select().contains("\n")) {
                    AiXcoderfullLine_num++;
                }
            }
            IDEAcode += codeIfoList.get(i).getIDEAcode();
            AiXcode += codeIfoList.get(i).getAiXcode();
        }
        IDEA_select_sum = (IDEA_select_sum / IDEA_sum + 0.0);//IDEA正确推荐项平均完成键数
        AiXcoder_select_sum = (AiXcoder_select_sum / AiXcoder_sum + 0.0);//Aixcoder正确推荐项平均完成键数

        IDEA_listsize = (IDEA_listsize + 0.0) / (i + 0.0);//IDEA平均推荐列表长度
        AiXcoder_listsize = (AiXcoder_listsize + 0.0) / (i + 0.0);//aixcoder平均推荐列表长度

        IDEAcodeSize = getPrintSize(IDEAcode.getBytes().length + 0.0);//IDEA生成总代码占用空间
        AiXcodeSize = getPrintSize(AiXcode.getBytes().length + 0.0);//aixcoder生成总代码占用空间

    }

    public double getPrintSize(double size) {
//        直接以kb为单位返回
        System.out.println("生成代码空间：" + (size / 1024.0));
        return (size / 1024.0);
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
//        if (size < 1024) {
//            return size;
//        } else {
//            size = size / 1024;
//        }
//        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
//        //因为还没有到达要使用另一个单位的时候
//        //接下去以此类推
//        if (size < 1024) {
//            return size;
//        } else {
//            size = size / 1024;
//        }
//        if (size < 1024) {
//            //因为如果以MB为单位的话，要保留最后1位小数，
//            //因此，把此数乘以100之后再取余
//            size = size * 100;
//            return size;
//        } else {
//            //否则如果要以GB为单位的，先除于1024再作同样的处理
//            size = size * 100 / 1024;
//            return size;
//        }
    }

    public List<CodeIfo> getCodeIfoList() {
        return codeIfoList;
    }

    public List<Integer> getAiXcode_key_nums() {
        return aiXcode_key_nums;
    }

    public List<Integer> getIDEA_key_nums() {
        return IDEA_key_nums;
    }

    public double getIDEA_select_sum() {
        return IDEA_select_sum;
    }

    public int getIDEA_sum() {
        return IDEA_sum;
    }

    public double getIDEA_listsize() {
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

    public double getIDEAcodeSize() {
        return IDEAcodeSize;
    }

    public double getAiXcoder_select_sum() {
        return AiXcoder_select_sum;
    }

    public int getAiXcoder_sum() {
        return AiXcoder_sum;
    }

    public double getAiXcoder_listsize() {
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

    public double getAiXcodeSize() {
        return AiXcodeSize;
    }

    public int getI() {
        return i;
    }


}
