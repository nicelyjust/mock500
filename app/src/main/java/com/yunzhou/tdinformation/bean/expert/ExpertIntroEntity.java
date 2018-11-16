package com.yunzhou.tdinformation.bean.expert;

import com.yunzhou.common.http.bean.base.Entity;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.expert
 *  @文件名:   ExpertIntroEntity
 *  @创建者:   lz
 *  @创建时间:  2018/10/15 19:27
 *  @描述：
 */

public class ExpertIntroEntity extends Entity {

    /**
     * id : 37
     * username : 18600528394
     * nickName : 宁财神
     * avatar : http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Y7-AMDHVAAAWOfD3G1Q17.jpeg
     * avaSmall : http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Y7-AeCJ5AAAWLn8rqGg58.jpeg
     * phone : 18600528394
     * intro : 业界专家，擅长通过分析亚盘、大小水位等变化竞彩。
     * bravo : 1
     * goodAt : 竞彩足球,足彩,盘口分析
     * attentionCount : 3
     * fansCount : 7
     * grade : 专家
     * city : 广东深圳
     * alreadySetPayPass : 1
     * watchExp : 0
     * contentsNum : 7
     */
    private int id;
    private String username;
    private String nickName;
    private String avatar;
    private String avaSmall;
    private String phone;
    private String intro;
    private int bravo;
    private String goodAt;
    private int attentionCount;
    private int fansCount;
    private String grade;
    private String city;
    private int alreadySetPayPass;
    // 1 已关注 2 未关注
    private int watchExp = 2;
    private int contentsNum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAvaSmall() {
        return avaSmall;
    }

    public void setAvaSmall(String avaSmall) {
        this.avaSmall = avaSmall;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
