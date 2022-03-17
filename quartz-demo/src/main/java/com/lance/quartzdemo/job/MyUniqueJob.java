package com.lance.quartzdemo.job;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.lance.quartzdemo.config.MyApplicationContextHolder;
import com.lance.quartzdemo.constant.JobDataMapConstants;
import com.lance.quartzdemo.reflect.JobParam;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Slf4j
public class MyUniqueJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String methodName = jobDataMap.getString(JobDataMapConstants.methodName);
        String className = jobDataMap.getString(JobDataMapConstants.className);
        String methodParams = jobDataMap.getString(JobDataMapConstants.methodParams);

        try {
            Class<?> aClass = Class.forName(className);

            log.info("MyUniqueJob doing job");

            Object taskTargetObject = MyApplicationContextHolder.getApplicationContext().getBean(aClass);


            if (StringUtils.isEmpty(methodParams)) {
                Method declaredMethod = aClass.getDeclaredMethod(methodName);
                declaredMethod.invoke(taskTargetObject);
            }else {

                List<JobParam> paramList = new Gson().fromJson(methodParams, new TypeToken<List<JobParam>>() {
                }.getType());

                paramList.stream().forEach(this::constructValueAndType);
                Object[] paramValues = paramList.stream().map(JobParam::getParamValue).toArray();
                Class[] paramClasses = paramList.stream().map(JobParam::getClazz).toArray(Class[]::new);

                Method declaredMethod = aClass.getDeclaredMethod(methodName, paramClasses);
                declaredMethod.invoke(taskTargetObject, paramValues);

            }


        } catch (ClassNotFoundException | NoSuchMethodException e) {
            log.error("1", e);
        } catch (InvocationTargetException e) {
            log.error("2", e);
        } catch (IllegalAccessException e) {
            log.error("3", e);
        }

    }



    private void constructValueAndType(JobParam jobParam) {
        String type = jobParam.getType();
        String value = jobParam.getValue();

        switch (type) {
            case "string":
                jobParam.setClazz(String.class);
                jobParam.setParamValue(value);

                break;
            case "int":
                jobParam.setClazz(Integer.class);
                jobParam.setParamValue(Integer.parseInt(value));

                break;
            case "long":
                jobParam.setClazz(Long.class);
                jobParam.setParamValue(Long.parseLong(value));
                break;
        }

    }
}
