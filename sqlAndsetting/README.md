>  这是HOJ数据库的创建文件、数据库数据文件、服务配置文件





删除所有容器

```shell
 docker rm -f $(docker ps -aq)
```

docker-compose 

```sh
docker-compose up -d
```





## 部署前端

```SH
使用weboack打包工具执行 npm run build生产dist文件夹

docker build -t local-hoj-frontend .
```







## 部署后端服务



上传app.jar包到\My-OnlieJudge\sqlAndsetting\backend

```shell
docker build -t local-hoj-backend .
```

docker-compose.yml

```yaml
version: "1"
services:

  hoj-redis:
    image: redis:5.0.9-alpine
    container_name: hoj-redis
    restart: always
    volumes:
      - ${HOJ_DATA_DIRECTORY}/data/redis/data:/data
    network_mode: host
    ports:
      - ${REDIS_PORT:-6379}:6379
    # --requirepass 后面为redis访问密码
    command: redis-server --requirepass ${REDIS_PASSWORD:-hoj123456} --appendonly yes

  hoj-mysql:
    #仅支持amd64
    image: registry.cn-shenzhen.aliyuncs.com/hcode/hoj_database
    #支持amd64、arm64
    #image: himitzh/hoj_database
    container_name: hoj-mysql
    restart: always
    volumes:
      - ${HOJ_DATA_DIRECTORY}/data/mysql/data:/var/lib/mysql
    environment:
      - MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD:-hoj123456} # mysql数据库root账号的密码
      - TZ=Asia/Shanghai
      - NACOS_USERNAME=${NACOS_USERNAME:-root} # 后续nacos所用管理员账号
      - NACOS_PASSWORD=${NACOS_PASSWORD:-hoj123456} # 后续nacos所用管理员密码
    network_mode: host
    ports:
      - ${MYSQL_PUBLIC_PORT:-3306}:3306

    # 初始化数据库
  hoj-mysql-checker:
    #仅支持amd64
    image: registry.cn-shenzhen.aliyuncs.com/hcode/hoj_database_checker
    #支持amd64、arm64
    #image: himitzh/hoj_database_checker
    container_name: hoj-mysql-checker
    depends_on:
      - hoj-mysql
    links:
      - hoj-mysql:mysql
    environment:
      - MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD:-hoj123456}

  hoj-nacos:
    image: nacos/nacos-server:1.4.2
    container_name: hoj-nacos
    restart: always
    depends_on:
      - hoj-mysql
    environment:
      - JVM_XMX=384m
      - JVM_XMS=384m
      - JVM_XMN=192m
      - MODE=standalone
      - SPRING_DATASOURCE_PLATFORM=mysql
      - MYSQL_SERVICE_HOST=${MYSQL_HOST:-192.168.42.131}
      - MYSQL_SERVICE_PORT=3306
      - MYSQL_SERVICE_USER=root
      - MYSQL_SERVICE_PASSWORD=${MYSQL_ROOT_PASSWORD:-hoj123456} # 与上面数据库密码一致
      - MYSQL_SERVICE_DB_NAME=nacos
      - NACOS_AUTH_ENABLE=true # 开启鉴权
    network_mode: host
    ports:
      - ${NACOS_PORT:-8848}:8848
    healthcheck:
      test: curl -f http://${NACOS_HOST:-192.168.42.131}:8848/nacos/index.html || exit 1
      interval: 6s
      timeout: 10s
      retries: 10

  hoj-backend:
    # 本地
    image: local-hoj-backend
    #仅支持amd64
    # image: registry.cn-shenzhen.aliyuncs.com/hcode/hoj_backend
    #支持amd64、arm64
    #image: himitzh/hoj_backend
    container_name: local-hoj-backend
    restart: always
    depends_on:
      - hoj-redis
      - hoj-mysql
      - hoj-nacos
    volumes:
      - ${HOJ_DATA_DIRECTORY}/file:/hoj/file
      - ${HOJ_DATA_DIRECTORY}/testcase:/hoj/testcase
      - ${HOJ_DATA_DIRECTORY}/log/backend:/hoj/log/backend
    environment:
      - TZ=Asia/Shanghai
      - JAVA_OPTS=-Xms192m -Xmx384m
      - BACKEND_SERVER_PORT=${BACKEND_PORT:-6688}
      - NACOS_URL=${NACOS_HOST:-192.168.42.131}:8848
      - NACOS_USERNAME=${NACOS_USERNAME:-root} # 登录 http://ip:8848/nacos 分布式配置中心与注册中心的后台的账号
      - NACOS_PASSWORD=${NACOS_PASSWORD:-hoj123456} # 密码
      - JWT_TOKEN_SECRET=${JWT_TOKEN_SECRET:-default} # token加密秘钥 默认则生成32位随机密钥
      - JWT_TOKEN_EXPIRE=${JWT_TOKEN_EXPIRE:-86400} # token过期时间默认为24小时 86400s
      - JWT_TOKEN_FRESH_EXPIRE=${JWT_TOKEN_FRESH_EXPIRE:-43200} # token默认12小时可自动刷新
      - JUDGE_TOKEN=${JUDGE_TOKEN:-default} # 调用判题服务器的token 默认则生成32位随机密钥
      - MYSQL_HOST=${MYSQL_HOST:-192.168.42.131}
      - MYSQL_PUBLIC_HOST=${MYSQL_PUBLIC_HOST:-101.33.203.52} # 如果判题服务是分布式，请提供当前mysql所在服务器的公网ip
      - MYSQL_PUBLIC_PORT=${MYSQL_PUBLIC_PORT:-3306}
      - MYSQL_PORT=3306
      - MYSQL_DATABASE_NAME=hoj # 改动需要修改hoj-mysql镜像,默认为hoj
      - MYSQL_USERNAME=root
      - MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD:-hoj123456}
      - EMAIL_SERVER_HOST=${EMAIL_SERVER_HOST:-smtp.qq.com} # 请使用邮件服务的域名或ip
      - EMAIL_SERVER_PORT=${EMAIL_SERVER_PORT:-465} # 请使用邮件服务的端口号
      - EMAIL_USERNAME=${EMAIL_USERNAME} # 请使用对应邮箱账号
      - EMAIL_PASSWORD=${EMAIL_PASSWORD} # 请使用对应邮箱密码
      - REDIS_HOST=${REDIS_HOST:-192.168.42.131}
      - REDIS_PORT=6379
      - REDIS_PASSWORD=${REDIS_PASSWORD:-hoj123456}
      - OPEN_REMOTE_JUDGE=true # 是否开启各个remote judge
      # 开启虚拟判题请提供对应oj的账号密码 格式为 
      # username1,username2,...
      # password1,password2,...
      - HDU_ACCOUNT_USERNAME_LIST=${HDU_ACCOUNT_USERNAME_LIST}
      - HDU_ACCOUNT_PASSWORD_LIST=${HDU_ACCOUNT_PASSWORD_LIST}
      - CF_ACCOUNT_USERNAME_LIST=${CF_ACCOUNT_USERNAME_LIST}
      - CF_ACCOUNT_PASSWORD_LIST=${CF_ACCOUNT_PASSWORD_LIST}
      - POJ_ACCOUNT_USERNAME_LIST=${POJ_ACCOUNT_USERNAME_LIST}
      - POJ_ACCOUNT_PASSWORD_LIST=${POJ_ACCOUNT_PASSWORD_LIST}
      - ATCODER_ACCOUNT_USERNAME_LIST=${ATCODER_ACCOUNT_USERNAME_LIST}
      - ATCODER_ACCOUNT_PASSWORD_LIST=${ATCODER_ACCOUNT_PASSWORD_LIST}
      - SPOJ_ACCOUNT_USERNAME_LIST=${SPOJ_ACCOUNT_USERNAME_LIST}
      - SPOJ_ACCOUNT_PASSWORD_LIST=${SPOJ_ACCOUNT_PASSWORD_LIST}
      # 是否强制使用配置文件的remote judge账号覆盖原有系统的账号列表
      - FORCED_UPDATE_REMOTE_JUDGE_ACCOUNT=${FORCED_UPDATE_REMOTE_JUDGE_ACCOUNT:-false}
    network_mode: host
    ports:
      - ${BACKEND_PORT:-6688}:${BACKEND_PORT:-6688}

  local-hoj-judgeserver:
    # 本地
    image: local-hoj-judgeserver
    #仅支持amd64
    #image: registry.cn-shenzhen.aliyuncs.com/hcode/hoj_judgeserver
    #支持amd64、arm64
    #image: himitzh/hoj_judgeserver
    container_name: local-hoj-judgeserver
    restart: always
    depends_on:
      - hoj-mysql
      - hoj-nacos
    volumes:
      - ${HOJ_DATA_DIRECTORY}/testcase:/judge/test_case
      - ${HOJ_DATA_DIRECTORY}/judge/log:/judge/log
      - ${HOJ_DATA_DIRECTORY}/judge/run:/judge/run
      - ${HOJ_DATA_DIRECTORY}/judge/spj:/judge/spj
      - ${HOJ_DATA_DIRECTORY}/judge/interactive:/judge/interactive
      - ${HOJ_DATA_DIRECTORY}/log/judgeserver:/judge/log/judgeserver
    environment:
      - TZ=Asia/Shanghai
      - JAVA_OPTS=-Xms192m -Xmx384m # 修正JVM参数以便适应单机部署
      - JUDGE_SERVER_IP=${JUDGE_SERVER_IP:-192.168.42.131}
      - JUDGE_SERVER_PORT=${JUDGE_SERVER_PORT:-8088}
      - JUDGE_SERVER_NAME=${JUDGE_SERVER_NAME:-judger-1} # 判题服务的名字
      - NACOS_URL=${NACOS_HOST:-192.168.42.131}:8848
      - NACOS_USERNAME=${NACOS_USERNAME:-root}
      - NACOS_PASSWORD=${NACOS_PASSWORD:-hoj123456}
      - MAX_TASK_NUM=${MAX_TASK_NUM:--1} # -1表示最大可接收判题任务数为cpu核心数+1
      - REMOTE_JUDGE_OPEN=${REMOTE_JUDGE_OPEN:-true} # 当前判题服务器是否开启远程虚拟判题功能
      - REMOTE_JUDGE_MAX_TASK_NUM=${REMOTE_JUDGE_MAX_TASK_NUM:--1} # -1表示最大可接收远程判题任务数为cpu核心数*2+1
      - PARALLEL_TASK=${PARALLEL_TASK:-default} # 默认沙盒并行判题程序数为cpu核心数
    network_mode: host
    ports:
      - ${JUDGE_SERVER_PORT:-8088}:${JUDGE_SERVER_PORT:-8088}
      - "0.0.0.0:5050:5050" # 一般不开放安全沙盒端口

    healthcheck:
      test: curl -f http://${JUDGE_SERVER_IP:-192.168.42.131}:${JUDGE_SERVER_PORT:-8088}/version || exit 1
      interval: 30s
      timeout: 10s
      retries: 3
    privileged: true # 设置容器的权限为root
    shm_size: 512mb

  hoj-judgeserver:
    #仅支持amd64
    image: registry.cn-shenzhen.aliyuncs.com/hcode/hoj_judgeserver
    #支持amd64、arm64
    #image: himitzh/hoj_judgeserver
    container_name: hoj-judgeserver
    restart: always
    depends_on:
      - hoj-mysql
      - hoj-nacos
    volumes:
      - ${HOJ_DATA_DIRECTORY}/testcase_2:/judge/test_case
      - ${HOJ_DATA_DIRECTORY}/judge_2/log:/judge/log
      - ${HOJ_DATA_DIRECTORY}/judge_2/run:/judge/run
      - ${HOJ_DATA_DIRECTORY}/judge_2/spj:/judge/spj
      - ${HOJ_DATA_DIRECTORY}/judge_2/interactive:/judge/interactive
      - ${HOJ_DATA_DIRECTORY}/log/judgeserver_2:/judge/log/judgeserver
    environment:
      - TZ=Asia/Shanghai
      - JAVA_OPTS=-Xms192m -Xmx384m # 修正JVM参数以便适应单机部署
      - JUDGE_SERVER_IP=${JUDGE_SERVER_IP:-192.168.42.131}
      - JUDGE_SERVER_PORT=${JUDGE_SERVER_PORT:-8089}
      - JUDGE_SERVER_NAME=${JUDGE_SERVER_NAME:-judger-2} # 判题服务的名字
      - NACOS_URL=${NACOS_HOST:-192.168.42.131}:8848
      - NACOS_USERNAME=${NACOS_USERNAME:-root}
      - NACOS_PASSWORD=${NACOS_PASSWORD:-hoj123456}
      - MAX_TASK_NUM=${MAX_TASK_NUM:--1} # -1表示最大可接收判题任务数为cpu核心数+1
      - REMOTE_JUDGE_OPEN=${REMOTE_JUDGE_OPEN:-true} # 当前判题服务器是否开启远程虚拟判题功能
      - REMOTE_JUDGE_MAX_TASK_NUM=${REMOTE_JUDGE_MAX_TASK_NUM:--1} # -1表示最大可接收远程判题任务数为cpu核心数*2+1
      - PARALLEL_TASK=${PARALLEL_TASK:-default} # 默认沙盒并行判题程序数为cpu核心数
    network_mode: host
    ports:
      - ${JUDGE_SERVER_PORT:-8089}:${JUDGE_SERVER_PORT:-8089}
      - "0.0.0.0:5051:5050" # 一般不开放安全沙盒端口
    healthcheck:
      test: curl -f http://${JUDGE_SERVER_IP:-192.168.42.131}:${JUDGE_SERVER_PORT:-8089}/version || exit 1
      interval: 30s
      timeout: 10s
      retries: 3
    privileged: true # 设置容器的权限为root
    shm_size: 512mb

  local-hoj-frontend:
    image: local-hoj-frontend
    #仅支持amd64
    #image: registry.cn-shenzhen.aliyuncs.com/hcode/hoj_frontend
    #支持amd64、arm64
    #image: himitzh/hoj_frontend
    container_name: local-hoj-frontend
    restart: always
    # 开启https，请提供证书
    #volumes:
    #  - ./server.crt:/etc/nginx/etc/crt/server.crt
    #  - ./server.key:/etc/nginx/etc/crt/server.key
    # 修改前端logo
    #  - ./logo.a0924d7d.png:/usr/share/nginx/html/assets/img/logo.a0924d7d.png
    #  - ./backstage.8bce8c6e.png:/usr/share/nginx/html/assets/img/backstage.8bce8c6e.png
    environment:
      - SERVER_NAME=localhost # 域名(例如baidu.com)或localhost(本地)
      - BACKEND_SERVER_HOST=${BACKEND_HOST:-192.168.42.131} # backend后端服务地址
      - BACKEND_SERVER_PORT=${BACKEND_PORT:-6688} # backend后端服务端口号
      - USE_HTTPS=false # 使用https请设置为true
    ports:
      - "80:80"
      - "443:443"
    network_mode: host

  hoj-autohealth:  # 监控不健康的容器进行重启
    restart: always
    container_name: hoj-autohealth
    image: willfarrell/autoheal
    environment:
      - AUTOHEAL_CONTAINER_LABEL=all
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock

```



## 部署评测服务



上传app.jar包到\My-OnlieJudge\sqlAndsetting\judgeserver（包JudgeServer (opens new window)（SpringBoot项目）成jar包也放到当前文件夹）

```shell
# /home/hrdate/桌面/project/hoj-deploy/src/judgeserver

docker build -t local-hoj-judgeserver .
```



```sh
# 运行docker-compose.yml编写的所有服务
docker compose up -d  
```



```SH
# 查看容器日志
docker logs local-hoj-judgeserver
```







默认情况下Ubutun18.04自带Python 3.6、Python2.7、GCC7.5.0、G++7.5.0

以下配置java和golang环境

```shell
sudo apt-get update
sudo add-apt-repository ppa:openjdk-r/ppa
sudo apt-get install -y golang-go openjdk-8-jdk mono-complete
```



## Token注意

**注意：若重新启动服务，模式是随机在NACOS生产，32位随机数的TOKEN**

**若后端服务启动DEV环境，且需要手动更改`Judge_Token`与Prod环境的一致**

**因为评测服务，默认使用的是Prod环境的**





## 沙箱对拍调用



```
Judger-SandBox使用的是开源项目go-judge (opens new window)Linux版本的可执行文件，更多调用方式请自行浏览go-judge
https://github.com/criyle/go-judge

其中README.md文件的
Example Request & Response
有介绍接口json格式
```

默认监听5050端口

* 验证是否启动

访问：`http://localhost:5050/version`

* 编译

请求的url为

```sh
POST http://localhost:5050/run
```

请求参数

```shell
 {
    "cmd": [
        {
            "args": [
                "/usr/bin/g++", 
                "a.cc", 
                "-o", 
                "a"
            ], 
            "env": [
                "PATH=/usr/bin:/bin"
            ], 
            "files": [
                {
                    "content": ""
                }, 
                {
                    "name": "stdout", 
                    "max": 10240
                }, 
                {
                    "name": "stderr", 
                    "max": 10240
                }
            ], 
            "cpuLimit": 10000000000, 
            "memoryLimit": 104857600, 
            "procLimit": 50, 
            "copyIn": {
                "a.cc": {
                  "content": "#include <iostream>\nusing namespace std;\nint main() {\nint a, b;\ncin >> a >> b;\ncout << a + b << endl;\n}"
                }
            }, 
            "copyOut": [
                "stdout", 
                "stderr"
            ], 
            "copyOutCached": [
                "a.cc", 
                "a"
            ], 
            "copyOutDir": "1"
        }
    ]
}
```

1.4 返回的数据为json格式

```json
  [
         {
             "status": "Accepted",
             "exitStatus": 0,
             "time": 303225231,
             "memory": 32243712,
             "runTime": 524177700,
             "files": {
                 "stderr": "",
                 "stdout": ""
             },
             "fileIds": {
                 "a": "WDQL5TNLRRVB2KAP",
                 "a.cc": "NOHPGGDTYQUFRSLJ"
             }
         }
     ]
```

* **运行与评测 ※**

请求的url为

> ```
> POST http://localhost:5050/run
> ```

* 请求参数

> 数据格式为json

```json
{
    "cmd": [{
        "args": ["a"],
        "env": ["PATH=/usr/bin:/bin","LANG=en_US.UTF-8","LC_ALL=en_US.UTF-8","LANGUAGE=en_US:en"],
        "files": [{
            "src": "/judge/test_case/problem_1010/1.in"
        }, {
            "name": "stdout",
            "max": 10240
        }, {
            "name": "stderr",
            "max": 10240
        }],
        "cpuLimit": 10000000000,
        "realCpuLimit":30000000000,
        "stackLimit":134217728,
        "memoryLimit": 104811111,
        "procLimit": 50,
        "copyIn": {
            "a":{"fileId":"WDQL5TNLRRVB2KAP"}
        },
        "copyOut": ["stdout", "stderr"]
    }]
}
```

* 返回的数据为json格式

```json
[{
  "status": "Accepted",
  "exitStatus": 0,
  "time": 3171607,
  "memory": 475136,
  "runTime": 110396333,
  "files": {
    "stderr": "",
    "stdout": "23\n"
  }
}]
```



## docker-compose快速部署运行



```yaml
version: "3"
services:

  hoj-redis:
    image: redis:5.0.9-alpine
    container_name: hoj-redis
    restart: always
    volumes:
      - ${HOJ_DATA_DIRECTORY}/data/redis/data:/data
    network_mode: host
    ports:
      - ${REDIS_PORT:-6379}:6379
    # --requirepass 后面为redis访问密码
    command: redis-server --requirepass ${REDIS_PASSWORD:-hoj123456} --appendonly yes
        
  hoj-mysql:
    #仅支持amd64
    image: registry.cn-shenzhen.aliyuncs.com/hcode/hoj_database
    #支持amd64、arm64
    #image: himitzh/hoj_database
    container_name: hoj-mysql
    restart: always
    volumes:
      - ${HOJ_DATA_DIRECTORY}/data/mysql/data:/var/lib/mysql
    environment:
      - MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD:-hoj123456} # mysql数据库root账号的密码
      - TZ=Asia/Shanghai
      - NACOS_USERNAME=${NACOS_USERNAME:-root} # 后续nacos所用管理员账号
      - NACOS_PASSWORD=${NACOS_PASSWORD:-hoj123456} # 后续nacos所用管理员密码
    network_mode: host
    ports:
      - ${MYSQL_PUBLIC_PORT:-3306}:3306

    # 初始化数据库
  hoj-mysql-checker:
    #仅支持amd64
    image: registry.cn-shenzhen.aliyuncs.com/hcode/hoj_database_checker
    #支持amd64、arm64
    #image: himitzh/hoj_database_checker
    container_name: hoj-mysql-checker
    depends_on:
      - hoj-mysql
    links:
      - hoj-mysql:mysql
    environment:
      - MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD:-hoj123456}

  hoj-nacos:
    image: nacos/nacos-server:1.4.2
    container_name: hoj-nacos
    restart: always
    depends_on: 
      - hoj-mysql
    environment:
      - JVM_XMX=384m
      - JVM_XMS=384m
      - JVM_XMN=192m
      - MODE=standalone
      - SPRING_DATASOURCE_PLATFORM=mysql
      - MYSQL_SERVICE_HOST=${MYSQL_HOST:-192.168.42.131}
      - MYSQL_SERVICE_PORT=3306
      - MYSQL_SERVICE_USER=root
      - MYSQL_SERVICE_PASSWORD=${MYSQL_ROOT_PASSWORD:-hoj123456} # 与上面数据库密码一致
      - MYSQL_SERVICE_DB_NAME=nacos 
      - NACOS_AUTH_ENABLE=true # 开启鉴权
    network_mode: host
    ports:
      - ${NACOS_PORT:-8848}:8848
    healthcheck:
      test: curl -f http://${NACOS_HOST:-192.168.42.131}:8848/nacos/index.html || exit 1
      interval: 6s
      timeout: 10s
      retries: 10
    
  hoj-backend:
    # 本地
    image: local-hoj-backend
    #仅支持amd64
    # image: registry.cn-shenzhen.aliyuncs.com/hcode/hoj_backend
    #支持amd64、arm64
    #image: himitzh/hoj_backend
    container_name: local-hoj-backend
    restart: always
    depends_on:
      - hoj-redis
      - hoj-mysql
      - hoj-nacos
    volumes:
      - ${HOJ_DATA_DIRECTORY}/file:/hoj/file
      - ${HOJ_DATA_DIRECTORY}/testcase:/hoj/testcase
      - ${HOJ_DATA_DIRECTORY}/log/backend:/hoj/log/backend
    environment:
      - TZ=Asia/Shanghai
      - JAVA_OPTS=-Xms192m -Xmx384m
      - BACKEND_SERVER_PORT=${BACKEND_PORT:-6688}
      - NACOS_URL=${NACOS_HOST:-192.168.42.131}:8848
      - NACOS_USERNAME=${NACOS_USERNAME:-root} # 登录 http://ip:8848/nacos 分布式配置中心与注册中心的后台的账号
      - NACOS_PASSWORD=${NACOS_PASSWORD:-hoj123456} # 密码
      - JWT_TOKEN_SECRET=${JWT_TOKEN_SECRET:-default} # token加密秘钥 默认则生成32位随机密钥
      - JWT_TOKEN_EXPIRE=${JWT_TOKEN_EXPIRE:-86400} # token过期时间默认为24小时 86400s
      - JWT_TOKEN_FRESH_EXPIRE=${JWT_TOKEN_FRESH_EXPIRE:-43200} # token默认12小时可自动刷新
      - JUDGE_TOKEN=${JUDGE_TOKEN:-default} # 调用判题服务器的token 默认则生成32位随机密钥
      - MYSQL_HOST=${MYSQL_HOST:-172.20.0.3}
      - MYSQL_PUBLIC_HOST=${MYSQL_PUBLIC_HOST} # 如果判题服务是分布式，请提供当前mysql所在服务器的公网ip
      - MYSQL_PUBLIC_PORT=${MYSQL_PUBLIC_PORT:-3306}
      - MYSQL_PORT=3306
      - MYSQL_DATABASE_NAME=hoj # 改动需要修改hoj-mysql镜像,默认为hoj
      - MYSQL_USERNAME=root
      - MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD:-hoj123456}
      - EMAIL_SERVER_HOST=${EMAIL_SERVER_HOST:-smtp.qq.com} # 请使用邮件服务的域名或ip
      - EMAIL_SERVER_PORT=${EMAIL_SERVER_PORT:-465} # 请使用邮件服务的端口号
      - EMAIL_USERNAME=${EMAIL_USERNAME:-535523596@qq.com} # 请使用对应邮箱账号
      - EMAIL_PASSWORD=${EMAIL_PASSWORD:-ceoxkatslrkicbah} # 请使用对应邮箱密码
      - REDIS_HOST=${REDIS_HOST:-192.168.42.131}
      - REDIS_PORT=6379
      - REDIS_PASSWORD=${REDIS_PASSWORD:-hoj123456}
      - OPEN_REMOTE_JUDGE=true # 是否开启各个remote judge
      # 开启虚拟判题请提供对应oj的账号密码 格式为 
      # username1,username2,...
      # password1,password2,...
      - HDU_ACCOUNT_USERNAME_LIST=${HDU_ACCOUNT_USERNAME_LIST}
      - HDU_ACCOUNT_PASSWORD_LIST=${HDU_ACCOUNT_PASSWORD_LIST}
      - CF_ACCOUNT_USERNAME_LIST=${CF_ACCOUNT_USERNAME_LIST}
      - CF_ACCOUNT_PASSWORD_LIST=${CF_ACCOUNT_PASSWORD_LIST}
      - POJ_ACCOUNT_USERNAME_LIST=${POJ_ACCOUNT_USERNAME_LIST}
      - POJ_ACCOUNT_PASSWORD_LIST=${POJ_ACCOUNT_PASSWORD_LIST}
      - ATCODER_ACCOUNT_USERNAME_LIST=${ATCODER_ACCOUNT_USERNAME_LIST}
      - ATCODER_ACCOUNT_PASSWORD_LIST=${ATCODER_ACCOUNT_PASSWORD_LIST}
      - SPOJ_ACCOUNT_USERNAME_LIST=${SPOJ_ACCOUNT_USERNAME_LIST}
      - SPOJ_ACCOUNT_PASSWORD_LIST=${SPOJ_ACCOUNT_PASSWORD_LIST}
      # 是否强制使用配置文件的remote judge账号覆盖原有系统的账号列表
      - FORCED_UPDATE_REMOTE_JUDGE_ACCOUNT=${FORCED_UPDATE_REMOTE_JUDGE_ACCOUNT:-false}
    network_mode: host
    ports:
      - ${BACKEND_PORT:-6688}:${BACKEND_PORT:-6688}
  
  local-hoj-judgeserver:
    # 本地
    image: local-hoj-judgeserver
    #仅支持amd64
    #image: registry.cn-shenzhen.aliyuncs.com/hcode/hoj_judgeserver
    #支持amd64、arm64
    #image: himitzh/hoj_judgeserver
    container_name: local-hoj-judgeserver
    restart: always
    depends_on:
      - hoj-mysql
      - hoj-nacos
    volumes:
      - ${HOJ_DATA_DIRECTORY}/testcase:/judge/test_case
      - ${HOJ_DATA_DIRECTORY}/judge/log:/judge/log
      - ${HOJ_DATA_DIRECTORY}/judge/run:/judge/run
      - ${HOJ_DATA_DIRECTORY}/judge/spj:/judge/spj
      - ${HOJ_DATA_DIRECTORY}/judge/interactive:/judge/interactive
      - ${HOJ_DATA_DIRECTORY}/log/judgeserver:/judge/log/judgeserver
    environment:
      - TZ=Asia/Shanghai
      - JAVA_OPTS=-Xms192m -Xmx384m # 修正JVM参数以便适应单机部署
      - JUDGE_SERVER_IP=${JUDGE_SERVER_IP:-192.168.42.131}
      - JUDGE_SERVER_PORT=${JUDGE_SERVER_PORT:-8088}
      - JUDGE_SERVER_NAME=${JUDGE_SERVER_NAME:-judger-1} # 判题服务的名字
      - NACOS_URL=${NACOS_HOST:-192.168.42.131}:8848
      - NACOS_USERNAME=${NACOS_USERNAME:-root}
      - NACOS_PASSWORD=${NACOS_PASSWORD:-hoj123456}
      - MAX_TASK_NUM=${MAX_TASK_NUM:--1} # -1表示最大可接收判题任务数为cpu核心数+1
      - REMOTE_JUDGE_OPEN=${REMOTE_JUDGE_OPEN:-true} # 当前判题服务器是否开启远程虚拟判题功能
      - REMOTE_JUDGE_MAX_TASK_NUM=${REMOTE_JUDGE_MAX_TASK_NUM:--1} # -1表示最大可接收远程判题任务数为cpu核心数*2+1
      - PARALLEL_TASK=${PARALLEL_TASK:-default} # 默认沙盒并行判题程序数为cpu核心数
    network_mode: host
    ports:
      - ${JUDGE_SERVER_PORT:-8088}:${JUDGE_SERVER_PORT:-8088}
      - "0.0.0.0:5050:5050" # 一般不开放安全沙盒端口

    healthcheck:
      test: curl -f http://${JUDGE_SERVER_IP:-192.168.42.131}:${JUDGE_SERVER_PORT:-8088}/version || exit 1
      interval: 30s
      timeout: 10s
      retries: 3
    privileged: true # 设置容器的权限为root
    shm_size: 512mb

  hoj-judgeserver:
    #仅支持amd64
    image: registry.cn-shenzhen.aliyuncs.com/hcode/hoj_judgeserver
    #支持amd64、arm64
    #image: himitzh/hoj_judgeserver
    container_name: hoj-judgeserver
    restart: always
    depends_on:
      - hoj-mysql
      - hoj-nacos
    volumes:
      - ${HOJ_DATA_DIRECTORY}/testcase_2:/judge/test_case
      - ${HOJ_DATA_DIRECTORY}/judge_2/log:/judge/log
      - ${HOJ_DATA_DIRECTORY}/judge_2/run:/judge/run
      - ${HOJ_DATA_DIRECTORY}/judge_2/spj:/judge/spj
      - ${HOJ_DATA_DIRECTORY}/judge_2/interactive:/judge/interactive
      - ${HOJ_DATA_DIRECTORY}/log/judgeserver_2:/judge/log/judgeserver
    environment:
      - TZ=Asia/Shanghai
      - JAVA_OPTS=-Xms192m -Xmx384m # 修正JVM参数以便适应单机部署
      - JUDGE_SERVER_IP=${JUDGE_SERVER_IP:-192.168.42.131}
      - JUDGE_SERVER_PORT=${JUDGE_SERVER_PORT:-8089}
      - JUDGE_SERVER_NAME=${JUDGE_SERVER_NAME:-judger-2} # 判题服务的名字
      - NACOS_URL=${NACOS_HOST:-192.168.42.131}:8848
      - NACOS_USERNAME=${NACOS_USERNAME:-root}
      - NACOS_PASSWORD=${NACOS_PASSWORD:-hoj123456}
      - MAX_TASK_NUM=${MAX_TASK_NUM:--1} # -1表示最大可接收判题任务数为cpu核心数+1
      - REMOTE_JUDGE_OPEN=${REMOTE_JUDGE_OPEN:-true} # 当前判题服务器是否开启远程虚拟判题功能
      - REMOTE_JUDGE_MAX_TASK_NUM=${REMOTE_JUDGE_MAX_TASK_NUM:--1} # -1表示最大可接收远程判题任务数为cpu核心数*2+1
      - PARALLEL_TASK=${PARALLEL_TASK:-default} # 默认沙盒并行判题程序数为cpu核心数
    network_mode: host
    ports:
      - ${JUDGE_SERVER_PORT:-8089}:${JUDGE_SERVER_PORT:-8089}
      - "0.0.0.0:5051:5050" # 一般不开放安全沙盒端口
    healthcheck:
      test: curl -f http://${JUDGE_SERVER_IP:-192.168.42.131}:${JUDGE_SERVER_PORT:-8089}/version || exit 1
      interval: 30s
      timeout: 10s
      retries: 3
    privileged: true # 设置容器的权限为root
    shm_size: 512mb
    
  local-hoj-frontend:
    image: local-hoj-frontend
    #仅支持amd64
    #image: registry.cn-shenzhen.aliyuncs.com/hcode/hoj_frontend
    #支持amd64、arm64
    #image: himitzh/hoj_frontend
    container_name: local-hoj-frontend
    restart: always
    # 开启https，请提供证书
    #volumes:
    #  - ./server.crt:/etc/nginx/etc/crt/server.crt
    #  - ./server.key:/etc/nginx/etc/crt/server.key
    # 修改前端logo
    #  - ./logo.a0924d7d.png:/usr/share/nginx/html/assets/img/logo.a0924d7d.png
    #  - ./backstage.8bce8c6e.png:/usr/share/nginx/html/assets/img/backstage.8bce8c6e.png
    environment:
      - SERVER_NAME=localhost # 域名(例如baidu.com)或localhost(本地)
      - BACKEND_SERVER_HOST=${BACKEND_HOST:-192.168.42.131} # backend后端服务地址
      - BACKEND_SERVER_PORT=${BACKEND_PORT:-6688} # backend后端服务端口号
      - USE_HTTPS=false # 使用https请设置为true
    ports:
      - "80:80"
      - "443:443"
    network_mode: host
    
  hoj-autohealth:  # 监控不健康的容器进行重启
    restart: always
    container_name: hoj-autohealth
    image: willfarrell/autoheal
    environment:
      - AUTOHEAL_CONTAINER_LABEL=all
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock

```



## 环境变量

```properties
# hoj全部数据存储的文件夹位置（默认当前路径生成hoj文件夹）
HOJ_DATA_DIRECTORY=./hoj_data

# redis的配置
REDIS_HOST=101.33.203.52
REDIS_PORT=6379
REDIS_PASSWORD=hoj123456

# mysql的docker内网ip
MYSQL_HOST=101.33.203.52
# mysql暴露的端口号
MYSQL_PUBLIC_PORT=3306
# mysql的密码
MYSQL_ROOT_PASSWORD=hoj123456
# 如果判题服务是分布式，请提供当前mysql所在服务器的公网ip
MYSQL_PUBLIC_HOST=101.33.203.52

# nacos的配置
NACOS_HOST=101.33.203.52
NACOS_PORT=8848
NACOS_USERNAME=root
NACOS_PASSWORD=hoj123456

# backend后端服务的配置
BACKEND_HOST=101.33.203.52
BACKEND_PORT=6688
# token加密秘钥 默认则生成32位随机密钥
JWT_TOKEN_SECRET=default
# token过期时间默认为24小时 86400s
JWT_TOKEN_EXPIRE=86400
# token默认12小时可自动刷新
JWT_TOKEN_FRESH_EXPIRE=43200
# 调用判题服务器的token 默认则生成32位随机密钥
JUDGE_TOKEN=test_token
# 请使用邮件服务的域名或ip
EMAIL_SERVER_HOST=smtp.qq.com
EMAIL_SERVER_PORT=465
EMAIL_USERNAME=535523596@qq.com
EMAIL_PASSWORD=rmxwzibymbybbjdi
# 开启虚拟判题请提供对应oj的账号密码 格式为 
# username1,username2,...
# password1,password2,...
HDU_ACCOUNT_USERNAME_LIST=
HDU_ACCOUNT_PASSWORD_LIST=
CF_ACCOUNT_USERNAME_LIST=
CF_ACCOUNT_PASSWORD_LIST=
POJ_ACCOUNT_USERNAME_LIST=
POJ_ACCOUNT_PASSWORD_LIST=
ATCODER_ACCOUNT_USERNAME_LIST=
ATCODER_ACCOUNT_PASSWORD_LIST=
SPOJ_ACCOUNT_USERNAME_LIST=
SPOJ_ACCOUNT_PASSWORD_LIST=
# 是否强制使用上面配置的账号覆盖系统原有的账号列表
FORCED_UPDATE_REMOTE_JUDGE_ACCOUNT=false

# judgeserver的配置
JUDGE_SERVER_IP=101.33.203.52
JUDGE_SERVER_PORT=8088
JUDGE_SERVER_NAME=judger-1
JUDGE2_SERVER_IP=101.33.203.52
JUDGE2_SERVER_PORT=8089
JUDGE2_SERVER_NAME=judger-2
# -1表示可接收最大判题任务数为cpu核心数+1
MAX_TASK_NUM=-1
# 当前判题服务器是否开启远程虚拟判题功能
REMOTE_JUDGE_OPEN=true
# -1表示可接收最大远程判题任务数为cpu核心数*2+1
REMOTE_JUDGE_MAX_TASK_NUM=-1
# 默认沙盒并行判题程序数为cpu核心数
PARALLEL_TASK=default

# docker network的配置

```





## 为什么都部署在ubuntu中？

在win10运行，和虚拟机分开运行的话，部署在不同网段，http请求被被拒绝了

安全沙箱go-judge适合ubuntu16及以上的运行环境