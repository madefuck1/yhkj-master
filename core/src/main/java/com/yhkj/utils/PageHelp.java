package com.yhkj.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author: Loulq
 * @Date: 2019/3/5 0005 16:52
 */
@Setter
@Getter
public class PageHelp<E> {

    List<E> data;
    int count;

}
