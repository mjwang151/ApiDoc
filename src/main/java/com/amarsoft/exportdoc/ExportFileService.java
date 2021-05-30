package com.amarsoft.exportdoc;

import com.alibaba.fastjson.JSONObject;

import java.io.Writer;

/**
 * �����ļ�ͨ��service
 * @author kwqin
 * created on 2020��12��31�� ����4:33:26
 */
public interface ExportFileService {
	
	void handle(Writer out, JSONObject jsonParams) throws Exception;
	
	String getFileName(JSONObject jsonParams);

}
