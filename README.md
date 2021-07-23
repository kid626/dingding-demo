# dingding-demo

钉钉,浙政钉（专有钉钉）的免登及扫码登录的 demo

<!-- PROJECT SHIELDS -->

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]

<!-- PROJECT LOGO -->


<p align="center">
    <a href="https://github.com/kid626/dingding-demo/">
        <img src="https://avatars.githubusercontent.com/u/39028286?s=460&u=18233327fb74b1b9a779bd63ff6e1e63f2fd0f9f&v=4" width="80" height="80" alt="这是一个头像"/>
    </a>
</p>
<h3 align="center">README模板</h3>
<p align="center">
    一个README模板去快速开始你的项目！
<br/>
<a href="https://github.com/kid626/dingding-demo/"><strong>探索本项目的文档 »</strong></a>
</p>

## 目录

- [上手指南](#1)
    - [开发前置要求](#1.1)
    - [安装步骤](#1.2)
- [文件目录说明](#2)
- [开发的架构](#3)
- [部署](#4)
- [鸣谢](#5)

<h3 id="1"> 上手指南 </h3> 

<h6 id="1.1"> **开发前置要求** </h6>

1. jdk8 环境
2. 创建好了的钉钉应用
3. 创建好了的浙政钉（专有钉钉）应用
4. 一台部署了 docker 的机器(非必须)

<h6 id="1.2"> **安装步骤** </h6>

1. Clone the repo

```sh
git clone https://github.com/kid626/dingding-demo.git
```

2. 配置 application.yml

```yml
demo:
  ding:
    appKey: ""
    appSecret: ""
    corpId: ""
    agentId:
  zzd:
    appKey: ""
    appSecret: ""
    corpId:
    # 专有钉钉 默认这个，浙政钉需要使用线上环境地址
    domainName: "openplatform.dg-work.cn"
```

3. 配置内网穿透地址

[钉钉内网穿透介绍](https://developers.dingtalk.com/document/resourcedownload/http-intranet-penetration)

ding.sh

```shell
#!/usr/bin/env bash
# 修改 subdomain 和端口号
echo "FORWARDING http://bruce.vaiwan.com/dd to http:127.0.0.1:9001/dd"
echo "FORWARDING http://bruce.vaiwan.com/zzd to http:127.0.0.1:9001/zzd"
./pierced/windows_64/ding.exe -config=./pierced/windows_64/ding.cfg -subdomain=bruce 9001
```

修改完毕后，记得修改前端静态页面的请求路径(全局搜索 bruce.vaiwan.com)

4. 启动内网穿透工具

```shell
sh ding.sh
```

5. 配置钉钉,浙政钉微应用的路径

```text
钉钉:     http://***.vaiwan.com/dd/?corpid=$CORPID$
浙政钉:    http://***.vaiwan.com/zzd/
```

6. 启动 SpringBoot 项目

<h3 id="2"> 文件目录说明 </h3> 

```
dingding-demo 

├── /libs/   钉钉，浙政钉 sdk 可以本地引用，也可以上传到自己的私服，再通过 maven 引入
├── /pierced/ 内网穿透工具
├── /src/
│  ├── /main/
│  │  └── /java/
│  │    └── /com/
│  │      └── /bruce/
│  │          └── /dingding/
│  │            └── /demo/
│  │                └── /config/
│  │                  └── WebConfig.java  控制路径跳转
│  │                  └── DingConfig.java  获取钉钉配置
│  │                  └── ZzdConfig.java  获取浙政钉配置
│  │                └── /controller/  接口层
│  │                └── /exception/  封装业务异常
│  │                └── /model/  封装用到的业务模型，可以自定义
│  │                └── /service/  钉钉，浙政钉 接口 对接 service
│  │  └── /resources/
│  │    └── /static/  静态资源文件
│  │    └── /templates/  静态页面，含钉钉，浙政钉两个页面
├── .gitignore
├── ding.sh  启动内网穿透服务脚本
├── Dockerfile  后期部署使用
├── pom.xml
└── README.md
```

<h3 id="3"> 开发的架构 </h3> 

<p>后端采用 SpringBoot </p> 
<p>前端使用 html + js</p>

<h3 id="4"> 部署 </h3> 

可以采用 idea 的 docker 插件，完成 docker 远程部署

<h3 id="5"> 鸣谢 </h3> 

<!-- links -->

[contributors-shield]: https://img.shields.io/github/contributors/kid626/dingding-demo.svg?style=flat-square

[contributors-url]: https://github.com/kid626/dingding-demo/graphs/contributors

[forks-shield]: https://img.shields.io/github/forks/kid626/dingding-demo.svg?style=flat-square

[forks-url]: https://github.com/kid626/dingding-demo/network/members

[stars-shield]: https://img.shields.io/github/stars/kid626/dingding-demo.svg?style=flat-square

[stars-url]: https://github.com/kid626/dingding-demo/stargazers

[issues-shield]: https://img.shields.io/github/issues/kid626/dingding-demo.svg?style=flat-square

[issues-url]: https://img.shields.io/github/issues/kid626/dingding-demo.svg

[license-shield]: https://img.shields.io/github/license/kid626/dingding-demo.svg?style=flat-square

[license-url]: https://github.com/kid626/dingding-demo/blob/master/LICENSE.txt


