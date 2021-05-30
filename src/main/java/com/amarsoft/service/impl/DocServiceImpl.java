package com.amarsoft.service.impl;

import com.amarsoft.bean.AssetApi;
import com.amarsoft.bean.AssetApiParams;
import com.amarsoft.dao.als.DocMapper;
import com.amarsoft.service.DocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DocServiceImpl implements DocService {

    @Autowired
    private DocMapper docMapper;

    @Override
    public AssetApi findApi(String transcode) {
        return docMapper.findApi(transcode);
    }

    @Override
    public List<AssetApiParams> findParams(String transcode) {
        return docMapper.findParams(transcode);
    }
}
