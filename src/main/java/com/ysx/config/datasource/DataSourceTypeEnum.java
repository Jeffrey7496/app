package com.ysx.config.datasource;

/**
 * 定义读写库类型关键字，用于辨别库标识
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/6/18 11:22
 */
public enum DataSourceTypeEnum {
    read("read","读库"),
    write("write","写库")
    ;

    private String type;
    private String name;

    DataSourceTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
