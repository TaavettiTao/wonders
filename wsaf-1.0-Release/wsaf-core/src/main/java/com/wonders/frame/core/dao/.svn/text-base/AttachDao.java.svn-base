package com.wonders.frame.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.wonders.frame.core.model.bo.Attach;

public interface AttachDao extends GenericRepository<Attach, Integer> {
	@Modifying(clearAutomatically = true)
	@Query("update Attach a set a.modelName =:modelName,a.modelId =:modelId where a.id in :ids")
	public Integer updateAttachRelation(
			@Param("modelName") String modelName,
			@Param("modelId") String modelId,
			@Param("ids") List<Integer> ids);


	@Modifying(clearAutomatically = true)
	@Query("update Attach a set a.removed = 1 where a.modelName =:modelName and a.modelId =:modelId and a.groupName =:groupName")
	public Integer removeByModelNameAndModelIdAndGroupName(
			@Param("modelName") String modelName,
			@Param("modelId") String modelId,
			@Param("groupName") String groupName);

}
