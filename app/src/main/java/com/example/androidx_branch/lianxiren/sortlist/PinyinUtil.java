package com.example.androidx_branch.lianxiren.sortlist;


import com.github.promeg.pinyinhelper.Pinyin;

public class PinyinUtil {
    public static String getPingYin(String inputString) {
        try {
            return Pinyin.toPinyin(inputString, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
