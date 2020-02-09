## 评估Sandbox自定义模块注入jetty类的性能影响
* 评估开启注入和关闭注入对Jetty Http类的CPU性能影响
* 评估开启注入后Event Listener和Advice Listener对Jetty Http类的CPU性能影响

### 代码构建
执行下面的maven命令
```bash
cd ./sandbox-modules
mvn clean package
```

### 运行目标测试应用sandbox-target
- 生成的包在`sandbox-target/target`目录下
- 测试服务为`/demo`, 处理form表单请求并返回json结果

```bash
cd ./sandbox-modules
java -jar sandbox-target/target/sandbox-target.jar
```

### 运行sandbox

* 启动sandbox参考下面官网帮助
>`https://github.com/alibaba/jvm-sandbox/blob/master/doc/JVM-SANDBOX-DEVELOPER-GUIDE-Chinese.md` 

* 激活并启动自定义module jetty-event

92022换成自己的java进程ID
```bash
./sandbox.sh -p 92022 -a 'jetty-event'
./sandbox.sh -p 92022 -d 'jetty-event/req'
./sandbox.sh -p 92022 -d 'jetty-event/para'
```

* 激活并启动自定义module jetty-advice

92022换成自己的java进程ID
```bash
./sandbox.sh -p 92022 -a 'jetty-advice'
./sandbox.sh -p 92022 -d 'jetty-advice/req'
./sandbox.sh -p 92022 -d 'jetty-advice/para'
```

### apache ab压测
- form表单提交短参数时开启注入和不开启注入CPU影响。表单参数参考代码`/sandbox-modules/sandbox-jetty/src/main/resources/post-data/post-short.data`
- form表单提交长参数时开启注入和不开启注入CPU影响。表单参数参考代码`/sandbox-modules/sandbox-jetty/src/main/resources/post-data/post-short.data`

```bash
ab -T 'application/x-www-form-urlencoded'  -n 10000 -c 10 -p post-long.data "http://localhost:8080/demo"
```

### 结论
MBP 2016（i5/8G内存/256G存储），

10并发压测form表单长参数（post-long.data)。开启注入影响明显，event listener性能比advice listener好。
- 不开启注入CPU大概>=10%
- 开启event listener注入CPU大概>=50%
- 开启advice listener注入CPU大概>=65%


10并发压测form表单短参数（post-short.data)。开启注入影响不明显。
- 不开启注入CPU大概>=7%
- 开启event listener注入CPU大概>=7%
- 开启advice listener注入CPU大概>=7%