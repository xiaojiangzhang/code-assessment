package com.bean;

import com.opencsv.bean.CsvBindByName;

import java.util.ArrayList;
import java.util.List;

public class CoderData {
    @CsvBindByName
    private String time;
    @CsvBindByName
    private String dataContext;
    @CsvBindByName
    private String codeContext;
    @CsvBindByName
    private String caretOffset;
    @CsvBindByName
    private String coder_input;
    @CsvBindByName
    private String coder_select;
    @CsvBindByName
    private String select_num;
    @CsvBindByName
    private String code_from;
    @CsvBindByName
    private String IDEAcode;
    @CsvBindByName
    private String IDEAcode_num;
    @CsvBindByName
    private String IDEAcode_index;
    @CsvBindByName
    private String AiXcode;
    @CsvBindByName
    private String AiXcode_num;
    @CsvBindByName
    private String AiXcoder_index;
    @CsvBindByName
    private String KiteCode;
    @CsvBindByName
    private String Kitecode_num;
    @CsvBindByName
    private String Kitecode_index;
    @CsvBindByName
    private String time_input_to_show;
    @CsvBindByName
    private String time_of_select_code;
    @CsvBindByName
    private String delete_behavior;

    @Override
    public String toString() {
        return "CoderData{" +
                "time='" + time + '\'' +
                ", dataContext='" + dataContext + '\'' +
                ", codeContext='" + codeContext + '\'' +
                ", caretOffset='" + caretOffset + '\'' +
                ", coder_input='" + coder_input + '\'' +
                ", coder_select='" + coder_select + '\'' +
                ", select_num='" + select_num + '\'' +
                ", code_from='" + code_from + '\'' +
                ", IDEAcode='" + IDEAcode + '\'' +
                ", IDEAcode_num='" + IDEAcode_num + '\'' +
                ", IDEAcode_index='" + IDEAcode_index + '\'' +
                ", AiXcode='" + AiXcode + '\'' +
                ", AiXcode_num='" + AiXcode_num + '\'' +
                ", AiXcoder_index='" + AiXcoder_index + '\'' +
                ", KiteCode='" + KiteCode + '\'' +
                ", Kitecode_num='" + Kitecode_num + '\'' +
                ", Kitecode_index='" + Kitecode_index + '\'' +
                ", time_input_to_show='" + time_input_to_show + '\'' +
                ", time_of_select_code='" + time_of_select_code + '\'' +
                ", delete_behavior='" + delete_behavior + '\'' +
                '}';
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDataContext() {
        return dataContext;
    }

    public void setDataContext(String dataContext) {
        this.dataContext = dataContext;
    }

    public String getCodeContext() {
        return codeContext;
    }

    public void setCodeContext(String codeContext) {
        this.codeContext = codeContext;
    }

    public String getCaretOffset() {
        return caretOffset;
    }

    public void setCaretOffset(String caretOffset) {
        this.caretOffset = caretOffset;
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

    public String getSelect_num() {
        return select_num;
    }

    public void setSelect_num(String select_num) {
        this.select_num = select_num;
    }

    public String getCode_from() {
        return code_from;
    }

    public void setCode_from(String code_from) {
        this.code_from = code_from;
    }

    public String getIDEAcode() {
        return IDEAcode;
    }

    public void setIDEAcode(String IDEAcode) {
        this.IDEAcode = IDEAcode;
    }

    public String getIDEAcode_num() {
        return IDEAcode_num;
    }

    public void setIDEAcode_num(String IDEAcode_num) {
        this.IDEAcode_num = IDEAcode_num;
    }

    public String getIDEAcode_index() {
        return IDEAcode_index;
    }

    public void setIDEAcode_index(String IDEAcode_index) {
        this.IDEAcode_index = IDEAcode_index;
    }

    public String getAiXcode() {
        return AiXcode;
    }

    public void setAiXcode(String aiXcode) {
        AiXcode = aiXcode;
    }

    public String getAiXcode_num() {
        return AiXcode_num;
    }

    public void setAiXcode_num(String aiXcode_num) {
        AiXcode_num = aiXcode_num;
    }

    public String getAiXcoder_index() {
        return AiXcoder_index;
    }

    public void setAiXcoder_index(String aiXcoder_index) {
        AiXcoder_index = aiXcoder_index;
    }

    public String getKiteCode() {
        return KiteCode;
    }

    public void setKiteCode(String kiteCode) {
        KiteCode = kiteCode;
    }

    public String getKitecode_num() {
        return Kitecode_num;
    }

    public void setKitecode_num(String kitecode_num) {
        Kitecode_num = kitecode_num;
    }

    public String getKitecode_index() {
        return Kitecode_index;
    }

    public void setKitecode_index(String kitecode_index) {
        Kitecode_index = kitecode_index;
    }

    public String getTime_input_to_show() {
        return time_input_to_show;
    }

    public void setTime_input_to_show(String time_input_to_show) {
        this.time_input_to_show = time_input_to_show;
    }

    public String getTime_of_select_code() {
        return time_of_select_code;
    }

    public void setTime_of_select_code(String time_of_select_code) {
        this.time_of_select_code = time_of_select_code;
    }

    public String getDelete_behavior() {
        return delete_behavior;
    }

    public void setDelete_behavior(String delete_behavior) {
        this.delete_behavior = delete_behavior;
    }
}
