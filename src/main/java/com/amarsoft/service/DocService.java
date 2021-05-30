package com.amarsoft.service;


import com.amarsoft.bean.AssetApi;
import com.amarsoft.bean.AssetApiParams;

import java.util.List;

public interface DocService {
    AssetApi findApi(String transcode);
    List<AssetApiParams> findParams(String transcode);
}
