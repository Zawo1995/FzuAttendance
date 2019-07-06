package com.framework;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by zlx on 2018/8/16.
 * 时间定时器（隔一段时间刷新数据库）
 * 回单超时
 */
public class TimerListener implements ServletContextListener {


    public void contextInitialized(ServletContextEvent sce) {
        //新建一个定时管理器
        //获得Spring容器
        /*WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        //从Spring容器中获得SelectDataServlet的实例
        ReceiptService receiptService = ctx.getBean(ReceiptService.class);

        //新建一个定时管理器
        new AppTimerManage(receiptService);*/
    }

    public TimerListener() {
        super();
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}
