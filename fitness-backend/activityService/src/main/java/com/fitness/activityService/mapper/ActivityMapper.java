package com.fitness.activityService.mapper;

import com.fitness.activityService.dto.ActivityRequest;
import com.fitness.activityService.dto.ActivityResponse;
import com.fitness.activityService.model.Activity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ActivityMapper {

    ActivityMapper INSTANCE = Mappers.getMapper(ActivityMapper.class);

    Activity toEntity(ActivityRequest request);

    ActivityResponse toDto(Activity activity);

    List<ActivityResponse> toDto(List<Activity> activities);


}
