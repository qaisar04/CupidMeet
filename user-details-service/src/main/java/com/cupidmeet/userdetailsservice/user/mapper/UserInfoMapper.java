package com.cupidmeet.userdetailsservice.user.mapper;

import com.cupidmeet.userdetailsservice.user.domain.dto.UserInfoRequest;
import com.cupidmeet.userdetailsservice.user.domain.dto.UserInfoResponse;
import com.cupidmeet.userdetailsservice.user.domain.entity.UserInfo;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserInfoMapper {

    UserInfo toEntity(UserInfoRequest request);

    UserInfoResponse toResponse(UserInfo userInfo);

    void updateEntityFromDto(UserInfoRequest request, @MappingTarget UserInfo entity);
}