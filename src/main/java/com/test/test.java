package com.test;

import org.mybatis.generator.api.ShellRunner;

public class test {
    public static void main(String [] args){
        args=new String[]{"-configfile", "src\\main\\resource\\generatorConfig.xml", "-overwrite"};
        ShellRunner.main(args);
    }
}
