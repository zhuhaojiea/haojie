package com.fh.shop.admin.biz.user;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.user.UserSearchParam;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.admin.vo.user.UserVo;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IUserService {

    public User findUserByUserName(String userName);

    ServerResponse addUser(User user);

    DataTableResult findUserList(UserSearchParam userSearchParam);

    ServerResponse findUser(Long id);

    ServerResponse updateUser(User user);

    List<UserVo> findAllUserList(UserSearchParam userSearchParam);

    XSSFWorkbook buildWorkBook(List<UserVo> userVoList);

    void updateLoginTime(Long userId, Date date);

    void updateLoginCount(Long userId);

    void incrLoginCount(Long userId);
}
