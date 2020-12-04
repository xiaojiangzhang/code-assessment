//import com.PersistentState.PersistentState;
import com.bean.SettingParamBean;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import java.io.File;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class Settings implements Configurable {
    private JPanel mPanel;
    private JTextField Tf_user;
    private JTextField Tf_password;
    private JTextField Tf_dburl;
    private JTextField Tf_driver;
    private JLabel Lb_User;
    private JLabel LB_Psd;
    private JLabel LB_Url;
    private JLabel Lb_Driver;
    private JLabel LB_DB;
    private JLabel LB_line;
    private JTextField Tf_tool1key;
    private JTextField Tf_tool2key;
    private JTextField Tf_tool3key;
    private JLabel LB_Middle;
    private JLabel LB_line2;
    private JLabel LB_tool1;
    private JLabel LB_tool2;
    private JLabel LB_tool3;
    private JPanel Panel_Head;
    private JPanel Panel_Middle;
    private JTextField Tf_tool1;
    private JTextField Tf_tool2;
    private JTextField Tf_tool3;
    private boolean is_Modified = false;
    private SettingParamBean settingParamBean;
    private SettingParamBean settingParamBeanSave = new SettingParamBean();
//    private File file = new File(PersistentState.getFilepath());


    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Coder tool setting";
    }

    @Override
    //初始化控件 2019/03/28
    public JComponent createComponent() {
//        this.persistentState = PersistentState.getInstance();
//        if (!file.exists()){
//            System.out.println("创建配置文件");
//            PersistentState.firstwriteSettingParamBean();
//        }
//        settingParamBean = PersistentState.readSettingParamBean();
//        initComponent();    //初始化组件内容
//        addComponentListener(); //添加监听
        return mPanel;
    }

    @Override
    //是否修改，激活apply按钮 2019/03/28
    public boolean isModified() {
        return this.is_Modified;
    }

    @Override
    //OK键或者Applay键 2019/03/28
    public void apply() throws ConfigurationException {
        if (this.is_Modified) {
            this.is_Modified = false;
            settingParamBeanSave.setUser(Tf_user.getText());
            settingParamBeanSave.setUser(Tf_user.getText());
            settingParamBeanSave.setPassword(Tf_password.getText());
            settingParamBeanSave.setDburl(Tf_dburl.getText());
            settingParamBeanSave.setDriver(Tf_driver.getText());
            settingParamBeanSave.setTool1(Tf_tool1.getText());
            settingParamBeanSave.setTool2(Tf_tool2.getText());
            settingParamBeanSave.setTool3(Tf_tool3.getText());
            settingParamBeanSave.setTool1key(Tf_tool1key.getText());
            settingParamBeanSave.setTool2key(Tf_tool2key.getText());
            settingParamBeanSave.setTool3key(Tf_tool3key.getText());
//            PersistentState.writeSettingParamBean(settingParamBeanSave);
//            TypeEntity.setUser(Tf_user.getText());
//            TypeEntity.setPassword(Tf_password.getText());
//            TypeEntity.setDburl(Tf_dburl.getText());
//            TypeEntity.setDriver(Tf_driver.getText());
//            TypeEntity.setTool1(Tf_tool1.getText());
//            TypeEntity.setTool2(Tf_tool2.getText());
//            TypeEntity.setTool3(Tf_tool3.getText());
//            TypeEntity.setTool1key(Tf_tool1key.getText());
//            TypeEntity.setTool2key(Tf_tool2key.getText());
//            TypeEntity.setTool3key(Tf_tool3key.getText());
        }
    }

    //初始化组件内容 2019/03/28
    public void initComponent() {
        LB_DB.setText("Database configuration");
        LB_line.setText("—————————————————————————————————————————————————————————————————");
        LB_line.setForeground(Color.gray);
        Lb_User.setText("Database user name");
        LB_Psd.setText("Password");
        LB_Url.setText("Database address");
        Lb_Driver.setText("Database driver address");
        LB_Middle.setText("Code completion tool selection");
        LB_line2.setText("—————————————————————————————————————————————————————————————");
        LB_line2.setForeground(Color.gray);
        LB_tool1.setText("Code Completion Tool1");
        LB_tool2.setText("Code Completion Tool2");
        LB_tool3.setText("Code Completion Tool3");
        Tf_user.setText(settingParamBean.getUser());
        Tf_password.setText(settingParamBean.getPassword());
        Tf_dburl.setText(settingParamBean.getDburl());
        Tf_driver.setText(settingParamBean.getDriver());
        Tf_tool1.setText(settingParamBean.getTool1());
        Tf_tool2.setText(settingParamBean.getTool2());
        Tf_tool3.setText(settingParamBean.getTool3());
        Tf_tool1key.setText(settingParamBean.getTool1key());
        Tf_tool2key.setText(settingParamBean.getTool2key());
        Tf_tool3key.setText(settingParamBean.getTool3key());
    }

    //给组件添加监听 2019/03/28
    public void addComponentListener() {
        Tf_user.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                is_Modified = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                is_Modified = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        Tf_password.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                is_Modified = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                is_Modified = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        Tf_dburl.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                is_Modified = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                is_Modified = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        Tf_driver.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                is_Modified = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                is_Modified = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        Tf_tool1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                is_Modified = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                is_Modified = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        Tf_tool2.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                is_Modified = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                is_Modified = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        Tf_tool3.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                is_Modified = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                is_Modified = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        Tf_tool1key.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                is_Modified = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                is_Modified = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        Tf_tool2key.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                is_Modified = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                is_Modified = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        Tf_tool3key.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                is_Modified = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                is_Modified = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

}
