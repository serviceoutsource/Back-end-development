package com.thinkgem.jeesite.common.aliGenie.service.impl;

import com.alibaba.da.coin.ide.spi.meta.ExecuteCode;
import com.alibaba.da.coin.ide.spi.meta.ResultType;
import com.alibaba.da.coin.ide.spi.standard.TaskQuery;
import com.alibaba.da.coin.ide.spi.standard.TaskResult;
import com.thinkgem.jeesite.common.aliGenie.service.MenuHandler;
import org.springframework.stereotype.Component;


/**
 * Created by Gavin on 2018/2/27.
 */
@Component
public class MenuHandlerImpl implements MenuHandler {

    @Override
    public TaskResult execute(TaskQuery taskQuery){
        TaskResult result = new TaskResult();
//        Map<String, String> paramMap = taskQuery
//                .getSlotEntities()
//                .stream()
//                .collect(Collectors
//                        .toMap(slotItem.slotItem.getIntentParameterName(), slotItem -> slotItem.getStandardValue()));
        result.setReply("欢迎使用点菜功能！");
        result.setExecuteCode(ExecuteCode.SUCCESS);
        result.setResultType(ResultType.RESULT);
        return result;
    }
}
