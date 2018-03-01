package com.thinkgem.jeesite.modules.aliGenie.web;

import com.alibaba.da.coin.ide.spi.standard.ResultModel;

import com.alibaba.da.coin.ide.spi.standard.TaskQuery;
import com.alibaba.da.coin.ide.spi.standard.TaskResult;
import com.alibaba.da.coin.ide.spi.trans.MetaFormat;
import com.thinkgem.jeesite.modules.aliGenie.service.CookerCtrlHandler;
import com.thinkgem.jeesite.modules.aliGenie.service.FoodAdviceHandler;
import com.thinkgem.jeesite.modules.aliGenie.service.FoodHandler;
import com.thinkgem.jeesite.modules.aliGenie.service.MenuHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Gavin on 2018/2/27.
 */
@Controller
public class AligenieController {
    //日志文件写入
    private static final Logger logger = LoggerFactory.getLogger(AligenieController.class);

    @Autowired
    private MenuHandler menuHandler;
    //  测试数据  {"sessionId":"136485b1-aa30-48c4-a4f3-c8b916e31374","utterance":"美食管家教我做东坡肉","skillId":8973,"skillName":"美食管家","intentId":17063,"intentName":"点菜","requestData":{},"slotEntities":[{"intentParameterName":"foodname","originalValue":"东坡肉","standardValue":"东坡肉","liveTime":0,"createTimeStamp":1519698715983,"slotValue":"东坡肉"}],"conversationRecords":[],"sessionEntries":{"CONTEXT_ENTRY_KEY_BOT_DATA_ID":{"timeToLive":0,"liveTime":0,"timeStamp":1519698715999,"value":"20866"}},"botId":20866,"domainId":15439}

    //用户主动点菜
    @RequestMapping(value = "/aliGenie/menu",method = RequestMethod.POST)
//    public @ResponseBody ResultModel<TaskResult> menuResponse(@RequestBody String taskQuery){
//        jsp页面测试时使用
    public @ResponseBody ResultModel<TaskResult> menuResponse(@RequestParam("val")String taskQuery){
        logger.info("TaskQuery:{}",taskQuery.toString());
        System.out.println(taskQuery);
        /**
         * 将开发者平台识别到的语义理解的结果（json字符串格式）转换成TaskQuery
         */
        TaskQuery query = MetaFormat.parseToQuery(taskQuery);
//        TaskQuery query = null;
        ResultModel<TaskResult> resultModel = new ResultModel<TaskResult>();
        try {
            TaskResult result = menuHandler.execute(query);
            resultModel.setReturnCode("0");
            resultModel.setReturnValue(result);
        }catch (Exception e){
            resultModel.setReturnCode("-1");
            resultModel.setReturnErrorSolution(e.getMessage());
        }
        return resultModel;
    }

    @Autowired
    private FoodAdviceHandler foodAdviceHandler;
    //系统菜品推荐
    @RequestMapping(value = "/aliGenie/foodAdvice",method = RequestMethod.POST)
//    public @ResponseBody ResultModel<TaskResult> foodAdviceResponse(@RequestBody String taskQuery){
//        jsp页面测试时使用
    public @ResponseBody ResultModel<TaskResult> foodAdviceResponse(@RequestParam("val")String taskQuery){
        logger.info("TaskQuery:{}",taskQuery.toString());
        System.out.println(taskQuery);
        /**
         * 将开发者平台识别到的语义理解的结果（json字符串格式）转换成TaskQuery
         */
        TaskQuery query = MetaFormat.parseToQuery(taskQuery);
//        TaskQuery query = null;
        ResultModel<TaskResult> resultModel = new ResultModel<TaskResult>();
        try {
            TaskResult result = foodAdviceHandler.execute(query);
            resultModel.setReturnCode("0");
            resultModel.setReturnValue(result);
        }catch (Exception e){
            resultModel.setReturnCode("-1");
            resultModel.setReturnErrorSolution(e.getMessage());
        }
        return resultModel;
    }


    @Autowired
    private CookerCtrlHandler cookerCtrlHandler;
    //厨具控制
    @RequestMapping(value = "/aliGenie/cookerCtrl",method = RequestMethod.POST)
//    public @ResponseBody ResultModel<TaskResult> cookerCtrlResponse(@RequestBody String taskQuery){
//        jsp页面测试时使用
    public @ResponseBody ResultModel<TaskResult> cookerCtrlResponse(@RequestParam("val")String taskQuery){
        logger.info("TaskQuery:{}",taskQuery.toString());
        System.out.println(taskQuery);
        /**
         * 将开发者平台识别到的语义理解的结果（json字符串格式）转换成TaskQuery
         */
        TaskQuery query = MetaFormat.parseToQuery(taskQuery);
//        TaskQuery query = null;
        ResultModel<TaskResult> resultModel = new ResultModel<TaskResult>();
        try {
            TaskResult result = cookerCtrlHandler.execute(query);
            resultModel.setReturnCode("0");
            resultModel.setReturnValue(result);
        }catch (Exception e){
            resultModel.setReturnCode("-1");
            resultModel.setReturnErrorSolution(e.getMessage());
        }
        return resultModel;
    }


    @Autowired
    private FoodHandler foodHandler;
    //食材处理
    @RequestMapping(value = "/aliGenie/foodHandle",method = RequestMethod.POST)
//    public @ResponseBody ResultModel<TaskResult> foodHandleResponse(@RequestBody String taskQuery){
//        jsp页面测试时使用
    public @ResponseBody ResultModel<TaskResult> foodHandleResponse(@RequestParam("val")String taskQuery){
        logger.info("TaskQuery:{}",taskQuery.toString());
        System.out.println(taskQuery);
        /**
         * 将开发者平台识别到的语义理解的结果（json字符串格式）转换成TaskQuery
         */
        TaskQuery query = MetaFormat.parseToQuery(taskQuery);
//        TaskQuery query = null;
        ResultModel<TaskResult> resultModel = new ResultModel<TaskResult>();
        try {
            TaskResult result = foodHandler.execute(query);
            resultModel.setReturnCode("0");
            resultModel.setReturnValue(result);
        }catch (Exception e){
            resultModel.setReturnCode("-1");
            resultModel.setReturnErrorSolution(e.getMessage());
        }
        return resultModel;
    }
}
