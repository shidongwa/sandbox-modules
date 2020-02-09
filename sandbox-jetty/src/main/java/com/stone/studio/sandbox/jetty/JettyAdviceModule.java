package com.stone.studio.sandbox.jetty;

import com.alibaba.jvm.sandbox.api.Information;
import com.alibaba.jvm.sandbox.api.Module;
import com.alibaba.jvm.sandbox.api.annotation.Command;
import com.alibaba.jvm.sandbox.api.listener.ext.Advice;
import com.alibaba.jvm.sandbox.api.listener.ext.AdviceListener;
import com.alibaba.jvm.sandbox.api.listener.ext.EventWatchBuilder;
import com.alibaba.jvm.sandbox.api.resource.ModuleEventWatcher;
import org.kohsuke.MetaInfServices;

import javax.annotation.Resource;

@MetaInfServices(Module.class)
@Information(id = "jetty-advice", isActiveOnLoad = false, version = "1.0.0", author = "Stone")
public class JettyAdviceModule extends CommonModule {
    @Resource
    private ModuleEventWatcher moduleEventWatcher;

    @Command("req")
    public void requestAdvice() {
        new EventWatchBuilder(moduleEventWatcher)
                .onClass("org.eclipse.jetty.server.handler.HandlerWrapper")
                .onBehavior("handle")
                .withParameterTypes("java.lang.String", "org.eclipse.jetty.server.Request",
                        "javax.servlet.http.HttpServletRequest", "javax.servlet.http.HttpServletResponse")
                .onWatch(new AdviceListener() {

                    @Override
                    protected void before(Advice advice) throws Throwable {
//                        System.out.println("req before advice occur");
                    }

                    @Override
                    protected void afterReturning(Advice advice) {
//                        System.out.println("req return advice occur");
                    }

                    @Override
                    protected void afterThrowing(Advice advice) {
//                        System.out.println("req throw advice occur");
                    }

                });
    }

    @Command("para")
    public void parameterAdvice() {
        new EventWatchBuilder(moduleEventWatcher)
                .onClass("org.eclipse.jetty.server.HttpInput")
                .onBehavior("read")
//                .withParameterTypes("byte[]", "int", "int")
                .onWatch(new AdviceListener() {

                    @Override
                    protected void before(Advice advice) throws Throwable {
//                        System.out.println("para before advice occur");
                    }

                    @Override
                    protected void afterReturning(Advice advice) {
//                        System.out.println("para return advice occur");
                    }

                    @Override
                    protected void afterThrowing(Advice advice) {
//                        System.out.println("para throw advice occur");
                    }
                });
    }
}
