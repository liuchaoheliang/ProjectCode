package com.froad.util;

import java.util.ArrayList;
import java.util.List;
/**
 * Collections utils
 * @author kevin
 *
 */
public final class CollectionsUtil {

	/**
	 * 每次插入数据库最大记录数
	 */
	public static final int MAX_INSERT_SIZE = 1000;
	
	/**
     * 分割List
     * @param list 待分割的list
     * @param pageSize 每段list的大小
     * @return List<<List<T>>
     */
    public static <T> List<List<T>> splitList(List<T> list, int pageSize) {
        int listSize = list.size(); 
        int page = (listSize + (pageSize - 1)) / pageSize;
        List<List<T>> listArray = new ArrayList<List<T>>();
        for (int i = 0; i < page; i++) {
            List<T> subList = new ArrayList<T>(); 
            for (int j = 0; j < listSize; j++) {
                int pageIndex = ((j + 1) + (pageSize - 1)) / pageSize;
                if (pageIndex == (i + 1)) {
                    subList.add(list.get(j)); 
                }
                if ((j + 1) == ((j + 1) * pageSize)) {
                    break;
                }
            }
            listArray.add(subList);
        }
        return listArray;
    }

}
