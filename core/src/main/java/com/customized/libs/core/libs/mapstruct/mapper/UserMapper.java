package com.customized.libs.core.libs.mapstruct.mapper;

import com.customized.libs.core.libs.mapstruct.User;
import com.customized.libs.core.libs.mapstruct.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author yan
 */
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * 标准convert，若两个对象参数名称一致，则自动适配（自动类型转换）
     *
     * @param user
     * @return
     */
    @Mappings({
            @Mapping(source = "userName", target = "name"),
            @Mapping(source = "userAge", target = "age")
    })
    UserVo transfer(User user);

    /**
     * MapStruct List对象convert
     *
     * @param user
     * @return
     * @see UserMapper#transfer(User)  编译期间会自动适配
     */
    List<UserVo> transfer(List<User> user);
}