package com.demon.baseui.address;

import com.demon.baseutil.search.PinYin;

public class AddressPerson {
    private String id;
    //姓名
    private String name;
    private String icon;
    private String desc;
    //拼音
    private String pinyin;
    //拼音首字母
    private String headerWord;

    public AddressPerson(String id, String name, String icon, String desc) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.desc = desc;
        this.pinyin = PinYin.getPinYin(name).toUpperCase();
        this.headerWord = pinyin.substring(0, 1);
    }

    public AddressPerson(String name) {
        this.name = name;
        this.pinyin = PinYin.getPinYin(name);
        this.headerWord = PinYin.getInitials(name).toUpperCase();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getHeaderWord() {
        return headerWord;
    }

    public void setHeaderWord(String headerWord) {
        this.headerWord = headerWord;
    }
}
