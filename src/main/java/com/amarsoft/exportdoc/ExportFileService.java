package com.amarsoft.exportdoc;

import com.alibaba.fastjson.JSONObject;

import java.io.Writer;

/**
 * 导出文件通用service
 * @author kwqin
 * created on 2020年12月31日 下午4:33:26
 */
public interface ExportFileService {
	
	void handle(Writer out, JSONObject jsonParams) throws Exception;
	
	String getFileName(JSONObject jsonParams);

}
