package com.regular;
//纠正用户在选择代码时输入队列中的无效值
public class CoderInputCorrect {
       private String first;
       private String end;
       public CoderInputCorrect(String first, String end){
            this.first =first;
            this.end=end;
       }
       public String correct(){
           if(first.length()<1){
               return first;
           }
           String match=end.substring(0,1);
           int fromIndex = first.length();
           int index = first.lastIndexOf(match);
           if (index==-1){
               return first;
           }
           System.out.println(first.substring(index,fromIndex));
           return first.substring(index,fromIndex);
       }

}
