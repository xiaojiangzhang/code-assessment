package com.regular;

import ConfigPara.TypeEntity;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementPresentation;

import javax.swing.*;

//对用户选择后的代码推荐列表进行遍历
//JList<LookupElement> list
public class Classify {
    LookupElementPresentation lookupElementPresentation;
    LookupElement lookupElement;
    String IDEAcode;
    String AiXcoder;
    String Kitecode;

    public String getIDEAcode() {
        return IDEAcode;
    }

    public String getAiXcoder() {
        return AiXcoder;
    }

    public String getKitecode() {
        return Kitecode;
    }

    public Classify(LookupElement lookupElement) {
        this.lookupElement = lookupElement;
    }
    public void sorting() {
        lookupElementPresentation = LookupElementPresentation.renderElement(lookupElement);
        //图标为空默认属于IDEA
        if (lookupElementPresentation.getTailText()!=null) {//后缀不为空为多行预测
            if (lookupElementPresentation.getIcon() == null) {
                IDEAcode = lookupElementPresentation.getItemText();
            } else if (lookupElementPresentation.getIcon().toString().contains(TypeEntity.getTool1key())) {//IDEA
                System.out.println(lookupElementPresentation.getIcon());
                IDEAcode = lookupElementPresentation.getItemText() + lookupElementPresentation.getTailText();
            } else if (lookupElementPresentation.getIcon().toString().contains("EmptyIcon")) {
                IDEAcode = lookupElementPresentation.getItemText() + lookupElementPresentation.getTailText();
            } else if (lookupElementPresentation.getIcon().toString().contains(TypeEntity.getTool2key())) {//AIXCODER
                AiXcoder = lookupElementPresentation.getItemText() + lookupElementPresentation.getTailText();
            } else if (lookupElementPresentation.getIcon().toString().contains(TypeEntity.getTool3key())) {//Kite
                Kitecode = lookupElementPresentation.getItemText() + lookupElementPresentation.getTailText();
            }
        }else if (lookupElementPresentation.getTailText()==null){//后缀为空单token预测   没有图标默认为IDEA预测代码
            if (lookupElementPresentation.getIcon() == null) {
                IDEAcode = lookupElementPresentation.getItemText();
            } else if (lookupElementPresentation.getIcon().toString().contains(TypeEntity.getTool1key())) {//IDEA
                IDEAcode = lookupElementPresentation.getItemText();
            } else if (lookupElementPresentation.getIcon().toString().contains("EmptyIcon")) {
                IDEAcode = lookupElementPresentation.getItemText();
            } else if (lookupElementPresentation.getIcon().toString().contains(TypeEntity.getTool2key())) {//AIXCODER
                AiXcoder = lookupElementPresentation.getItemText();
            }
            if (lookupElementPresentation.getIcon() != null) {
                if (lookupElementPresentation.getIcon().toString().contains(TypeEntity.getTool3key())) {//Kite
                    Kitecode = lookupElementPresentation.getItemText();
                }
            }


        }
    }


}
