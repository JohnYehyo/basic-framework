pipeline {
    agent any
    options {
    	buildDiscarder logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '90', numToKeepStr: '10')
        timestamps()
    }
    tools {
        maven 'maven-3.8.3'
    }
    parameters{
        choice choices: ['False', 'True'], description: '是否更新分支', name: 'JAR_UPDATE'
    }
    stages {
        stage('更新分支') {
            when { expression { return params.JAR_UPDATE == 'True' } }
            steps {
                echo '拉取代码'
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: '14f09985-a68e-43bd-8668-b642211f9e00', url: 'git@github.com:JohnYehyo/basic-framework.git']]])
            }
        }
        stage ('不更新分支') {
            agent { node { label 'master'} }
            when { expression { return params.JAR_UPDATE == 'False' } }
            steps {
                echo "不更新分支"
            }
        }
        stage('编译打包') {
            steps {
                echo '编译打包'
                sh 'mvn clean package -Dmaven.test.skip=true'
            }
        }
        stage('发布') {
            steps {
                echo '发布'
                sshPublisher(publishers: [sshPublisherDesc(configName: '虚拟机-本地测试服务器', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: '''#!/bin/bash
                source /etc/profile
                #jar所在目录
                JAR_PATH=/opt/jar
                #jar名称
                JAR_NAME=rjsoft-web.jar

                #结束进程
                echo "查询进程id-->$JAR_NAME"
                PID=`ps -ef | grep "$JAR_NAME" | grep -v grep | awk \'{print $2}\'`
                echo "得到进程ID：$PID"
                for id in $PID
                do
                    kill -9 $id
                done
                echo "结束进程完成"

                #运行jar
                nohup java -jar $JAR_PATH/$JAR_NAME > /dev/null 2>1&''', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '/opt/jar', remoteDirectorySDF: false, removePrefix: 'rjsoft-web/target', sourceFiles: 'rjsoft-web/target/rjsoft-web.jar')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
            }
        }
    }
}