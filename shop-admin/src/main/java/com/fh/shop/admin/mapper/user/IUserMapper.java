package com.fh.shop.admin.mapper.user;

import com.fh.shop.admin.param.user.UserSearchParam;
import com.fh.shop.admin.po.user.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface IUserMapper {

    public User findUserByUserName(String userName);

    void addUser(User user);

    Long findUserCount(UserSearchParam userSearchParam);

    List<User> findUserPageList(UserSearchParam userSearchParam);

    User findUser(Long id);

    void updateUser(User user);

    List<User> findAllUserList(UserSearchParam userSearchParam);

    void updateLoginTime(@Param("userId") Long userId, @Param("currTime") Date date);

    void updateLoginCount(Long userId);

    void incrLoginCount(Long userId);
}
