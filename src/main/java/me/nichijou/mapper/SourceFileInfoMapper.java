package me.nichijou.mapper;

import me.nichijou.pojo.SourceFileInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by nichijou on 8/25/17.
 */
public interface SourceFileInfoMapper {
	List<SourceFileInfo> selectNameAndSha();

	/**
	 * @param map
	 * @return
	 */
	int deleteArticles(@Param("mapData") Map<String, String> map);

	int addArticles(@Param("list") List<SourceFileInfo> fileInfos);

	int updateArticles(@Param("list") List<SourceFileInfo> updatedSourceFileInfos);
}
