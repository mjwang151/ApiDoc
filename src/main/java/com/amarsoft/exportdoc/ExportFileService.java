package com.amarsoft.exportdoc;

import com.alibaba.fastjson.JSONObject;

import java.io.Writer;

public interface ExportFileService {
	
	void handle(Writer out, JSONObject jsonParams) throws Exception;
	
	String getFileName(JSONObject jsonParams);

}
