package com.fh.shop.admin.biz.user;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.mapper.user.IUserMapper;
import com.fh.shop.admin.param.user.UserSearchParam;
import com.fh.shop.admin.po.product.Product;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.admin.util.Md5Util;
import com.fh.shop.admin.vo.user.UserVo;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("userService")
public class IUserServiceImpl implements IUserService {
    @Autowired
    private IUserMapper userMapper;

    @Override
    public User findUserByUserName(String userName) {
        User user = userMapper.findUserByUserName(userName);
        return user;
    }

    @Override
    public ServerResponse addUser(User user) {
        // 将明文密码进行加密
        user.setPassword(Md5Util.md5(user.getPassword()));
        userMapper.addUser(user);
        return ServerResponse.success();
    }

    @Override
    public DataTableResult findUserList(UserSearchParam userSearchParam) {
        // 获取总条数
        Long totalCount = userMapper.findUserCount(userSearchParam);
        // 获取分页列表
        List<User> userList = userMapper.findUserPageList(userSearchParam);
        // po转vo
        List<UserVo> userVoList = new ArrayList<>();
        for (User user : userList) {
            UserVo userVo = new UserVo();
            userVo.setId(user.getId());
            userVo.setUserName(user.getUserName());
            userVo.setAreaName(user.getShengName()+"==="+user.getShiName()+"==="+user.getXianName());
            userVoList.add(userVo);
        }
        // 返回结果
        return new DataTableResult(userSearchParam.getDraw(), totalCount, totalCount, userVoList);
    }

    @Override
    public ServerResponse findUser(Long id) {
        User user = userMapper.findUser(id);
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setUserName(user.getUserName());
        userVo.setShengId(user.getShengId());
        userVo.setShiId(user.getShiId());
        userVo.setXianId(user.getXianId());
        userVo.setAreaName(user.getShengName()+"==="+user.getShiName()+"==="+user.getXianName());
        return ServerResponse.success(userVo);
    }

    @Override
    public ServerResponse updateUser(User user) {
        userMapper.updateUser(user);
        return ServerResponse.success();
    }

    @Override
    public List<UserVo> findAllUserList(UserSearchParam userSearchParam) {
        List<User> userList = userMapper.findAllUserList(userSearchParam);
        // po转vo
        List<UserVo> userVoList = new ArrayList<>();
        for (User user : userList) {
            UserVo userVo = new UserVo();
            userVo.setId(user.getId());
            userVo.setUserName(user.getUserName());
            userVo.setAreaName(user.getShengName()+"==="+user.getShiName()+"==="+user.getXianName());
            userVoList.add(userVo);
        }
        return userVoList;
    }

    @Override
    public XSSFWorkbook buildWorkBook(List<UserVo> userVoList) {
        // 创建workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建sheet
        XSSFSheet sheet = workbook.createSheet("用户列表");
        // 创建大标题
        buildTitle(workbook, sheet);
        // 创建头
        buildHeader(sheet);
        // 创建数据行
        buildBody(userVoList, workbook, sheet);
        // 返回workbook
        return workbook;
    }

    @Override
    public void updateLoginTime(Long userId, Date date) {
        userMapper.updateLoginTime(userId, date);
    }

    @Override
    public void updateLoginCount(Long userId) {
        userMapper.updateLoginCount(userId);
    }

    @Override
    public void incrLoginCount(Long userId) {
        userMapper.incrLoginCount(userId);
    }

    private void buildTitle(XSSFWorkbook workbook, XSSFSheet sheet) {
        XSSFRow row = sheet.createRow(13);
        XSSFCell titleCell = row.createCell(3);
        titleCell.setCellValue("商品列表");
        CellRangeAddress cellRangeAddress = new CellRangeAddress(13, 14, 3, 8);
        sheet.addMergedRegion(cellRangeAddress);
        // 创建大标题样式
        XSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 22);
        font.setColor(HSSFColor.BLUE.index);
        titleStyle.setFont(font);
        titleStyle.setFillForegroundColor(HSSFColor.PINK.index);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleCell.setCellStyle(titleStyle);
    }

    private void buildBody(List<UserVo> userVoList, XSSFWorkbook workbook, XSSFSheet sheet) {
        // 创建样式
        for (int i = 0; i < userVoList.size(); i++) {
            UserVo userVo = userVoList.get(i);
            XSSFRow bodyRow = sheet.createRow(i + 16);
            bodyRow.createCell(3).setCellValue(userVo.getUserName());
            bodyRow.createCell(4).setCellValue(userVo.getAreaName());
        }
    }

    private void buildHeader(XSSFSheet sheet) {
        // 创建标题行
        XSSFRow titleRow = sheet.createRow(15);
        String[] headers = {"用户名", "地区名"};
        for (int i = 0; i < headers.length; i++) {
            titleRow.createCell(i+3).setCellValue(headers[i]);
        }
    }
}
