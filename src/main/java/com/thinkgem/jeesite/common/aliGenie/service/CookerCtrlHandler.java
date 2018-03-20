package com.thinkgem.jeesite.common.aliGenie.service;

import com.alibaba.da.coin.ide.spi.standard.TaskQuery;
import com.alibaba.da.coin.ide.spi.standard.TaskResult;

/**
 * Created by Gavin on 2018/3/1.
 */
public interface CookerCtrlHandler {
    TaskResult execute(TaskQuery taskQuery);
}
