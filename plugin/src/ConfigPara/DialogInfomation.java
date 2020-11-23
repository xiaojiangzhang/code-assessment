package ConfigPara;

public class DialogInfomation {
    private static String startTime = "";   //窗口指标的监听开始时间   2019/03/31 10:01
    private static String endTime = "";     //窗口指标的监听结束时间   2019/03/31 10:01
    private static String txtContent = "";  //窗口文本框指标信息       2019/03/31 10:22

    public static String getTxtContent() {
        return txtContent;
    }

    public static void setTxtContent(String txtContent) {
        DialogInfomation.txtContent = txtContent;
    }

    public static String getStartTime() {
        return startTime;
    }

    public static void setStartTime(String startTime) {
        DialogInfomation.startTime = startTime;
    }

    public static String getEndTime() {
        return endTime;
    }

    public static void setEndTime(String endTime) {
        DialogInfomation.endTime = endTime;
    }
}
