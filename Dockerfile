# 基础镜像
FROM openjdk:8-alpine
# 添加到工作目录
ADD target/dingding.jar /app/app.jar
# 对外暴露 9001 端口
EXPOSE 9001
# 设置工作目录
WORKDIR /app
# 设置启动命令
CMD ["/bin/sh", "-c", "java -server -Duser.timezone=GMT+08 -cp app.jar org.springframework.boot.loader.JarLauncher"]