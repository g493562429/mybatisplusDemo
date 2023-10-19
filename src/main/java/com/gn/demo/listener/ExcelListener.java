package com.gn.demo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.gn.demo.Utils.SpringContextUtil;
import com.gn.demo.enums.ServiceEnum;
import com.gn.demo.handler.AbstractHandle;
import com.gn.demo.service.impl.ConcreteStrategy;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * excel表监听器
 *
 * @author gn
 * @since 2023-04-20
 */
@Slf4j
public class ExcelListener<T> extends AnalysisEventListener<T> {

    /**
     * 业务逻辑策略类
     */
    private ConcreteStrategy<T> concreteStrategy = new ConcreteStrategy<T>();

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    private final List<T> dataList = Lists.newArrayList();

//    public ExcelListener() {
//        this.concreteStrategy = new ConcreteStrategy<T>();
//    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param demoData        one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param analysisContext analysisContext
     */
    @Override
    public void invoke(T demoData, AnalysisContext analysisContext) {
        log.info("excelListener.invoke.demoData=[{}]", JSON.toJSONString(demoData));
        // 读取Excel数据时的钩子函数，将数据保存到dataList中
        dataList.add(demoData);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (dataList.size() >= BATCH_COUNT) {
            handleData();
            // 存储完成清理 list
            dataList.clear();
        }
    }

    private void handleData() {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }
        String name = dataList.get(0).getClass().getSimpleName();
        Class serviceByClassName = ServiceEnum.getServiceByClassName(name);
        AbstractHandle<T> abstractHandle = (AbstractHandle<T>) SpringContextUtil.getBean(serviceByClassName);
        ConcreteStrategy<T> tConcreteStrategy = new ConcreteStrategy<>(abstractHandle);
        tConcreteStrategy.doAnything(dataList);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        handleData();
        log.info("所有数据解析完成！");
    }
}
