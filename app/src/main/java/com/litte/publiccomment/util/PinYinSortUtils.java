package com.litte.publiccomment.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by litte on 2018/1/25.
 */

public class PinYinSortUtils {
    public static String getPinYin(String name){
        try {
            String result = "";
            //1)设定汉语拼音的格式BEOIIJING（UV）
            HanyuPinyinOutputFormat hanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
            hanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);//转换为大写
            hanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//不要语音语调
            //2）根据设定好的格式，逐字进行汉字到拼音的转换
            StringBuilder stringBuilder = new StringBuilder();
            for (int i =0;i<name.length();i++) {
                char ch = name.charAt(i);
                //多音字情况 例如：单 重
                String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(ch, hanyuPinyinOutputFormat);
                if (pinyin.length>0) {
                    stringBuilder.append(pinyin[0]);
                }
            }
            result = stringBuilder.toString();
            return result;
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
            throw new RuntimeException("不正确的汉语拼音格式");
        }
    }
    public static char getLetter(String name){
        return getPinYin(name).charAt(0);
    }
}
