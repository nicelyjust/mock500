package com.yunzhou.tdinformation.bean.user;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.common.http.bean.user
 *  @文件名:   UserEntity
 *  @创建者:   lz
 *  @创建时间:  2018/9/29 10:34
 *  @描述：
 */

import com.yunzhou.tdinformation.user.UserManager;

public class UserEntity {
    /**
     * id : 1
     * password : null
     * jwt : null
     * username : admin
     * nickName : 社区管理员
     * avatar : NULL
     * realName : 社区管理员
     * gender : 2
     * email : wd@s.com
     * phone : 13512345678
     * birthday : 2018-09-12T16:00:00.000+0000
     * status : null
     * gid : 1
     * intro : 0
     * bravo : 0
     * goodAt : 0
     * attentionCount : 0
     * fansCount : 0
     * grade : 普通会员
     * city : null
     * idCard : null
     * payPassword : null
     * oldPayPassword : null
     * alreadySetPayPass : 0
     * watchExp : 0
     * contentsNum : 0
     * isAdmin : 1
     * bankCard : null
     */

    private int id = -1;
    private String password;
    private String jwt;
    private String username;
    private String nickName;
    private String avatar;
    private String realName;
    private int gender;
    private String email;
    private String phone;
    private String birthday;
    private String status;
    private int gid;
    private String intro;
    private int bravo;
    private String goodAt;
    private int attentionCount;
    private int fansCount;
    private String grade;
    private String city;
    private String idCard;
    private String payPassword;
    private String oldPayPassword;
    private int alreadySetPayPass;
    // 0:未登录,1:未关注,2已关注
    private int watchExp;
    private int contentsNum;
    private int isAdmin;
    private String bankCard;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getBravo() {
        return bravo;
    }

    public void setBravo(int bravo) {
        this.bravo = bravo;
    }

    public String getGoodAt() {
        return goodAt;
    }

    public void setGoodAt(String goodAt) {
        this.goodAt = goodAt;
    }

    public int getAttentionCount() {
        return attentionCount;
    }

    public void setAttentionCount(int attentionCount) {
        this.attentionCount = attentionCount;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getOldPayPassword() {
        return oldPayPassword;
    }

    public void setOldPayPassword(String oldPayPassword) {
        this.oldPayPassword = oldPayPassword;
    }

    public int getAlreadySetPayPass() {
        return alreadySetPayPass;
    }

    public void setAlreadySetPayPass(int alreadySetPayPass) {
        this.alreadySetPayPass = alreadySetPayPass;
    }

    public int getWatchExp() {
        return watchExp;
    }

    public void setWatchExp(int watchExp) {
        this.watchExp = watchExp;
    }

    public int getContentsNum() {
        return contentsNum;
    }

    public void setContentsNum(int contentsNum) {
        this.contentsNum = contentsNum;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public boolean isMine() {
        return this.id == UserManager.getInstance().getUid();
    }
}
