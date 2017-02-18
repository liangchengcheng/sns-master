package com.lcc.osf.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by lcc on 2017/2/18.
 */
public class OSFUtils {

    public static List<Integer> toList(Set<Integer> set){
        if(set == null){
            return new ArrayList<Integer>();
        }

        List<Integer> list = new ArrayList<Integer>();
        for(Integer ele : set){
            list.add(ele);
        }
        return list;
    }
}
