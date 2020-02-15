package com.stone.studio.sandbox.jetty;

import com.alibaba.jvm.sandbox.api.Information;
import com.alibaba.jvm.sandbox.api.Module;
import com.alibaba.jvm.sandbox.api.annotation.Command;
import com.alibaba.jvm.sandbox.api.event.Event;
import com.alibaba.jvm.sandbox.api.listener.EventListener;
import com.alibaba.jvm.sandbox.api.listener.ext.EventWatchBuilder;
import com.alibaba.jvm.sandbox.api.resource.ModuleEventWatcher;
import org.kohsuke.MetaInfServices;

import javax.annotation.Resource;

@MetaInfServices(Module.class)
@Information(id = "jetty-event", isActiveOnLoad = false, version = "1.0.0", author = "Stone")
public class JettyEventModule extends CommonModule {
    @Resource
    private ModuleEventWatcher moduleEventWatcher;

    @Command("req")
    public void getJettyRequest() {

        new EventWatchBuilder(moduleEventWatcher)
                .onClass("org.eclipse.jetty.server.Server")
                .onBehavior("handle")
                // 匹配参数 HttpChannel 和 AbstractHttpConnection
                .withParameterTypes("org.eclipse.jetty.server.*")
                .onWatch(                // 监听THROWS事件并且改变原有方法抛出异常为正常返回
                        new EventListener() {
                            @Override
                            public void onEvent(Event event) throws Throwable {
                                if(event.type == Event.Type.BEFORE) {
//                                    System.out.println("req before event occur");
                                } else if(event.type == Event.Type.RETURN) {
//                                    System.out.println("req return event occur");
                                } else if(event.type == Event.Type.THROWS) {
//                                    System.out.println("req throw event occur");
                                }
                            }
                        }, Event.Type.BEFORE, Event.Type.RETURN, Event.Type.THROWS);
    }

    @Command("para")
    public void getJettyParameter() {

        new EventWatchBuilder(moduleEventWatcher)
                .onClass("org.eclipse.jetty.server.HttpInput")
                .onBehavior("read")
                .withParameterTypes("*", "*", "*")
                .onWatch(
                        // 监听THROWS事件并且改变原有方法抛出异常为正常返回
                        new EventListener() {
                            @Override
                            public void onEvent(Event event) throws Throwable {
                                if(event.type == Event.Type.BEFORE) {
//                                    System.out.println("para before event occur");
                                } else if(event.type == Event.Type.RETURN) {
//                                    System.out.println("para return evfir ent occur");
                                } else if(event.type == Event.Type.THROWS) {
//                                    System.out.println("para throw event occur");
                                }
                            }
                        }, Event.Type.BEFORE, Event.Type.RETURN, Event.Type.THROWS
                );
    }
}
