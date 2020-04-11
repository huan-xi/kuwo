package com.huanxi.music.common.message;

import java.util.Collection;

/**
 * @author huangjiawei
 */
public class OutputUtils {

    /**
     * 返回错误消息,错误提示
     * @param msg
     * @return
     */
    public static AbstractMessage error(String msg) {
        return new OutputMessage(msg, -1);
    }

    /**
     */
    public static AbstractMessage output(String msg,int code) {
        return new OutputMessage(msg, code);
    }
    /**
     * 返回列表数据
     * @param collection 数据集合
     * @param total 总数
     * @return
     */
    public static AbstractMessage outputList(Collection collection,Long total) {
        return new OutputListMessage(collection,total);
    }
    /**
     * 返回成功信息
     * @param msg
     * @return
     */
    public static AbstractMessage success(Object msg) {
        return new SuccessMessage(msg);
    }

    /**
     * 返回警告信息
     * @param msg
     * @return
     */
    public static AbstractMessage waring(String msg) {
        return new OutputMessage(msg, -2);
    }

    public static AbstractMessage success() {
        return success("");
    }


    public static AbstractMessage error() {
        return error("");
    }
}
