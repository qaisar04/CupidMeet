package kz.baltabayev.userdetailsservice.mapper;

import kz.baltabayev.userdetailsservice.model.dto.FileAttachmentResponse;
import kz.baltabayev.userdetailsservice.model.dto.UserInfoRequest;
import kz.baltabayev.userdetailsservice.model.dto.UserInfoResponse;
import kz.baltabayev.userdetailsservice.model.entity.FileAttachment;
import kz.baltabayev.userdetailsservice.model.entity.UserInfo;
import kz.baltabayev.userdetailsservice.model.types.PersonalityType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = ComponentModel.SPRING, uses = FileAttachmentMapper.class)
public interface UserInfoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(source = "personalityType", target = "personalityType", qualifiedByName = "stringToPersonalityType")
    UserInfo toEntity(UserInfoRequest request);

    @Mapping(source = "files", target = "files", qualifiedByName = "mapFiles")
    UserInfoResponse fromEntity(UserInfo userInfo);

    @Named("mapFiles")
    List<FileAttachmentResponse> mapFiles(Set<FileAttachment> files);

    @Named("stringToPersonalityType")
    default PersonalityType stringToPersonalityType(String personalityType) {
        return PersonalityType.valueOf(personalityType.toUpperCase());
    }
}