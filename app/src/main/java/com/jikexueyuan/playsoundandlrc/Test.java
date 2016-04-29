package com.jikexueyuan.playsoundandlrc;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fanlin on 2015/12/24.
 */
public class Test {
    public static void main(String[] args){
        String lrc="[00:04.43]电影《何以笙箫默》主题插曲\n";
        Matcher matcher = Pattern.compile("\\d{1,2}:\\d{1,2}\\.\\d{1,2}").matcher(lrc);//正则表达式解析时间
        if (matcher.find()) {//判断能否拿到数据
            String group = matcher.group();//从正则表达式拿到数据分组
            System.out.println(group);
        }
    }
}
