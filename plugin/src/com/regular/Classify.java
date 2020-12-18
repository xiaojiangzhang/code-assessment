package com.regular;

import ConfigPara.TypeEntity;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementPresentation;

import javax.swing.*;
import java.util.*;

//对用户选择后的代码推荐列表进行遍历
//JList<LookupElement> list
public class Classify {
    private List<Integer> IDEACodeIndex = new ArrayList<Integer>();
    private List<Integer> AiXcoderCodeIndex = new ArrayList<Integer>();
    private List<Integer> KiteCodeIndex = new ArrayList<Integer>();

    LookupElementPresentation lookupElementPresentation;
    LookupElement lookupElement;
    static JList list;
    List<String> aixcoder = new ArrayList<>();
    List<String> ide = new ArrayList<>();
    List<String> kite = new ArrayList<>();
    static String[] keyphs = null;
    static HashMap<String, String> baoliuzi = new HashMap<>();

    static {
        keyphs = new String[]{"False", "None", "True", "and", "as", "assert", "break", "class", "continue", "def", "del", "elif", "else", "except",
                "finally", "for", "from", "global", "if", "import", "in", "is", "lambda", "nonlocal", "not", "or", "pass", "raise",
                "return", "try", "while", "with", "yield"};
        for (int i = 0; i < keyphs.length; i++) {
            baoliuzi.put(keyphs[i], keyphs[i]);
        }
    }

    public List<String> getAixcoder() {
        return aixcoder;
    }

    public void setAixcoder(List<String> aixcoder) {
        this.aixcoder = aixcoder;
    }

    public List<String> getIde() {
        return ide;
    }

    public void setIde(List<String> ide) {
        this.ide = ide;
    }

    public List<String> getKite() {
        return kite;
    }

    public void setKite(List<String> kite) {
        this.kite = kite;
    }

    static String selectcodeFrom = "";
    static int selectIndex;

    public String getSelectcodeFrom() {
        return selectcodeFrom;
    }

    public void setSelectcodeFrom(String selectcodeFrom) {
        Classify.selectcodeFrom = selectcodeFrom;
    }

    public List<Integer> getIDEACodeIndex() {
        return IDEACodeIndex;
    }

    public void setIDEACodeIndex(List<Integer> IDEACodeIndex) {
        this.IDEACodeIndex = IDEACodeIndex;
    }

    public List<Integer> getAiXcoderCodeIndex() {
        return AiXcoderCodeIndex;
    }

    public void setAiXcoderCodeIndex(List<Integer> aiXcoderCodeIndex) {
        AiXcoderCodeIndex = aiXcoderCodeIndex;
    }

    public List<Integer> getKiteCodeIndex() {
        return KiteCodeIndex;
    }

    public void setKiteCodeIndex(List<Integer> kiteCodeIndex) {
        KiteCodeIndex = kiteCodeIndex;
    }

    public Classify(JList list, int selectedIndex) {
        this.list = list;
        this.selectIndex = selectedIndex;
    }


    public void sorting() {
        int length = list.getModel().getSize();
        for (int i = 0; i < list.getModel().getSize(); i++) {
            lookupElement = (LookupElement) list.getModel().getElementAt(i);
            lookupElementPresentation = LookupElementPresentation.renderElement(lookupElement);
            //图标为空默认属于IDEA
            String tailText = lookupElementPresentation.getTailText();
            String label = "";
            try {
                label = lookupElementPresentation.getIcon().toString();
                label.replace('J', 'j');
            } catch (Exception e) {
                System.out.println(e);
            }

//            if (lookupElementPresentation.getIcon() != null) {
//                label = lookupElementPresentation.getIcon().toString();
//            }
            String generateCode = lookupElementPresentation.getItemText();

            if (tailText != null) {
                generateCode = generateCode + tailText;
            }
            //IDEA来源
            if (i == 0 && label.equals(" ") && !baoliuzi.containsKey(generateCode)) {
                aixcoder.add(generateCode);
                AiXcoderCodeIndex.add(i);
                selectcodeFrom = "AiXcoder";
            } else if (label.contains(TypeEntity.getTool1key())) {
                ide.add(generateCode);
                selectcodeFrom = "IDEA";
                IDEACodeIndex.add(i);

            } else if (label.contains(TypeEntity.getTool2key())) {
                aixcoder.add(generateCode);
                selectcodeFrom = "AiXcoder";
                AiXcoderCodeIndex.add(i);

            } else if (label.contains(TypeEntity.getTool3key())) {
                kite.add(generateCode);
                selectcodeFrom = "Kite";
                KiteCodeIndex.add(i);

            } else if (label.equals("") && baoliuzi.containsKey(generateCode)) {
                ide.add(generateCode);
                selectcodeFrom = "IDEA";
                IDEACodeIndex.add(i);

            } else if (label.equals("")) {
                ide.add(generateCode);
                selectcodeFrom = "IDEA";
                IDEACodeIndex.add(i);
            }


        }

    }


}
