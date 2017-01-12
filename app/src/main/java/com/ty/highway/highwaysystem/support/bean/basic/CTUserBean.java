package com.ty.highway.highwaysystem.support.bean.basic;

import java.io.Serializable;

/**
 * 用户信息
 * Created by ${dzm} on 2015/9/7 0007.
 */
public class CTUserBean implements Serializable{
    private  String UserID;
    private  String UserName;
    private  String DptId;
    private  String DptName;
    private  String UserAccount;
    private  String ERoleId;
    private  String FtpPath;
    private  String FtpIp;
    private  String FtpUser;
    private  String FtpPwd;
    private  boolean IsLogin ;
    private  String legalizeKey ;
    private  String FtpReadPath;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getDptId() {
        return DptId;
    }

    public void setDptId(String dptId) {
        DptId = dptId;
    }

    public String getDptName() {
        return DptName;
    }

    public void setDptName(String dptName) {
        DptName = dptName;
    }

    public String getUserAccount() {
        return UserAccount;
    }

    public void setUserAccount(String userAccount) {
        UserAccount = userAccount;
    }

    public String getERoleId() {
        return ERoleId;
    }

    public void setERoleId(String ERoleId) {
        this.ERoleId = ERoleId;
    }

    public String getFtpPath() {
        return FtpPath;
    }

    public void setFtpPath(String ftpPath) {
        FtpPath = ftpPath;
    }

    public String getFtpIp() {
        return FtpIp;
    }

    public void setFtpIp(String ftpIp) {
        FtpIp = ftpIp;
    }

    public String getFtpUser() {
        return FtpUser;
    }

    public void setFtpUser(String ftpUser) {
        FtpUser = ftpUser;
    }

    public String getFtpPwd() {
        return FtpPwd;
    }

    public void setFtpPwd(String ftpPwd) {
        FtpPwd = ftpPwd;
    }

    public boolean isLogin() {
        return IsLogin;
    }

    public void setIsLogin(boolean isLogin) {
        IsLogin = isLogin;
    }

    public String getLegalizeKey() {
        return legalizeKey;
    }

    public void setLegalizeKey(String legalizeKey) {
        this.legalizeKey = legalizeKey;
    }

    public String getFtpReadPath() {
        return FtpReadPath;
    }

    public void setFtpReadPath(String ftpReadPath) {
        FtpReadPath = ftpReadPath;
    }
}
