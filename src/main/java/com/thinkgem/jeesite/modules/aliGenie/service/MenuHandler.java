package com.thinkgem.jeesite.modules.aliGenie.service;


import com.alibaba.da.coin.ide.spi.standard.TaskQuery;
import com.alibaba.da.coin.ide.spi.standard.TaskResult;

/**
 * Created by Gavin on 2018/2/27.
 */
public interface MenuHandler {
    TaskResult execute(TaskQuery taskQuery);
}
