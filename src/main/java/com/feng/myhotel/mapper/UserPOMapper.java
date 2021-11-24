package com.feng.myhotel.mapper;

import com.feng.myhotel.entities.po.UserPO;
import com.feng.myhotel.entities.po.UserPOExample;
import com.feng.myhotel.entities.vo.LoginUserVO;
import com.feng.myhotel.entities.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserPOMapper {
    int countByExample(UserPOExample example);

    int deleteByExample(UserPOExample example);

    int deleteByPrimaryKey(Long id);

    void deleteUserByIdList(@Param("userIdList") List<Long> userIdList);

    int insert(UserPO record);

    int insertSelective(UserPO record);

    List<UserPO> selectByExample(UserPOExample example);

    UserPO selectByPrimaryKey(Long id);

    UserPO selectByLoginAccount(@Param("loginAccount") String loginAccount);

    UserPO selectByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    List<UserVO> selectUserVOList();

    void updateHeaderPictureById(@Param("id") Long id, @Param("headerPicture") String headerPicture);

    int updateByExampleSelective(@Param("record") UserPO record, @Param("example") UserPOExample example);

    int updateByExample(@Param("record") UserPO record, @Param("example") UserPOExample example);

    int updateByPrimaryKeySelective(UserPO record);

    int updateByPrimaryKey(UserPO record);

    void updateRoleByUserId(@Param("userId") Long userId, @Param("roleId") Integer roleId);
}