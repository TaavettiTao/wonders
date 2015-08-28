package com.wonders.frame.console.utils;

import java.util.Collection;
import java.util.Map;

/**
* 判断Collection(List和Set),Map等集合类型是否为空，是否含有空值。
* 判断String是否为空，参考ApacheCommonsLang-StringUtils。
*
* @author mengjie
*/
public class EmptyUtils {

    /**
     * 判断Collection(List和Set) 是否为空
     *
     * @param collection
     *            List或Set类型的集合
     * @return 如果collection是 null或size=0，返回true；否则，返回false。
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    /**
     * 判断map是否为空
     *
     * @param map
     *            键值对数据类型
     * @return 如果map是 null或size=0，返回true；否则，返回false。
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.size() == 0;
    }

    /**
     * 判断一个数组是否为空。
     *
     * @param array
     *            对象数组
     * @return 如果数组为null或者数组元素个数为0，返回true；否则，返回false。
     */
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断Collection(List和Set) 不为空
     *
     * @param collection
     *            List或Set类型的集合
     * @return 如果collection不等于null且size>0，返回true；否则，返回false。
     */
    public static boolean notEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断map不为空
     *
     * @param map
     *            键值对数据类型
     * @return 如果map不为 null且size>0，返回true；否则，返回false。
     */
    public static boolean notEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 判断一个数组不为空。
     *
     * @param array
     *            对象数组
     * @return 如果数组为null或者数组元素个数为0，返回false；否则，返回true。
     */
    public static boolean notEmpty(Object[] array) {
        return !isEmpty(array);
    }

}