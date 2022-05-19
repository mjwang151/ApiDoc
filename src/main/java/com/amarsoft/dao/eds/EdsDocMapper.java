package com.amarsoft.dao.eds;

import com.amarsoft.bean.AssetApi;
import com.amarsoft.bean.AssetApiParams;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Component
public interface EdsDocMapper {

    @Select("select * from Asset_Api where api=#{transcode} and projectId='t2021040900182235' limit 1")
    AssetApi findApi(@Param("transcode") String transcode);


    @Select("select * from asset_api_params where ApiId=(select max(apiid) from Asset_Api where api=#{transcode} and projectId='t2021040900182235') and isdelete = 'N'   order by parentparamid,`level`,sortno ")
    List<AssetApiParams> findParams(@Param("transcode") String transcode);

}
