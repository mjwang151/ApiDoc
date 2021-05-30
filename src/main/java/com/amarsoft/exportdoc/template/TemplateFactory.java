package com.amarsoft.exportdoc.template;

import com.amarsoft.utils.ApplicationContextUtils;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.IOException;


public abstract class  TemplateFactory implements DocTemplate{

    protected   Configuration initConfiguration() throws IOException {
        Configuration conf = null;
        conf = new Configuration(Configuration.VERSION_2_3_31);
        conf.setDefaultEncoding("utf-8");
        String path= ApplicationContextUtils.getProperty("doctemplate.config");
        //使用FileTemplateLoader
        FileTemplateLoader templateLoader=new FileTemplateLoader(new File(path));
        conf.setTemplateLoader(templateLoader);
        return conf;
    }




}
