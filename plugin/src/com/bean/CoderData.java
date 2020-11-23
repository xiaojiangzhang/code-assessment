package com.bean;

import java.util.ArrayList;
import java.util.List;

public class CoderData {
    private String time;
    private String coder_input;
    private String coder_select;
    private int select_num;
    private String code_from;
    private List<String> IDEAcode;
    private int IDEAcode_num;
    private List<String> AiXcode;
    private int AiXcode_num;
    private List<String> Kitecode;

    public CoderData() {
        time = null;
        coder_input = null;
        coder_select = null;
        select_num = 1;
        code_from = null;
        IDEAcode = new ArrayList<String>();
        IDEAcode_num = 0;
        AiXcode = new ArrayList<String>();
        AiXcode_num = 0;
    }

    @Override
    public String toString() {
        return "CoderData{" +
                "time='" + time + '\'' +
                ", coder_input='" + coder_input + '\'' +
                ", coder_select='" + coder_select + '\'' +
                ", select_num=" + select_num +
                ", code_from='" + code_from + '\'' +
                ", IDEAcode=" + IDEAcode +
                ", IDEAcode_num='" + IDEAcode_num + '\'' +
                ", AiXcode=" + AiXcode +
                ", AiXcode_num='" + AiXcode_num + '\'' +
                ", Kitecode=" + Kitecode +
                ", Kitecode_num='" + Kitecode_num + '\'' +
                '}';
    }

    public List<String> getKitecode() {
        return Kitecode;
    }

    public void setKitecode(List<String> kitecode) {
        Kitecode = kitecode;
    }

    public String getKitecode_num() {
        return Kitecode_num;
    }

    public void setKitecode_num(String kitecode_num) {
        Kitecode_num = kitecode_num;
    }

    private String Kitecode_num;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCoder_input() {
        return coder_input;
    }

    public void setCoder_input(String coder_input) {
        this.coder_input = coder_input;
    }

    public String getCoder_select() {
        return coder_select;
    }

    public void setCoder_select(String coder_select) {
        this.coder_select = coder_select;
    }

    public int getSelect_num() {
        return select_num;
    }

    public void setSelect_num(int select_num) {
        this.select_num = select_num;
    }

    public String getCode_from() {
        return code_from;
    }

    public void setCode_from(String code_from) {
        this.code_from = code_from;
    }

    public List<String> getIDEAcode() {
        return IDEAcode;
    }

    public void setIDEAcode(List<String> IDEAcode) {
        this.IDEAcode = IDEAcode;
    }

    public int getIDEAcode_num() {
        return IDEAcode_num;
    }

    public void setIDEAcode_num(int IDEAcode_num) {
        this.IDEAcode_num = IDEAcode_num;
    }

    public List<String> getAiXcode() {
        return AiXcode;
    }

    public void setAiXcode(List<String> aiXcode) {
        AiXcode = aiXcode;
    }

    public int getAiXcode_num() {
        return AiXcode_num;
    }

    public void setAiXcode_num(int aiXcode_num) {
        AiXcode_num = aiXcode_num;
    }
}
