package com.yunzhou.tdinformation.bean.lottery;

import com.yunzhou.common.http.bean.base.Entity;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.lottery
 *  @文件名:   SubscribeLotteryEntity
 *  @创建者:   lz
 *  @创建时间:  2018/10/24 18:40
 *  @描述：
 */

public class SubscribeLotteryEntity extends Entity {

    /**
     * id : 61
     * lotteryDto : {"id":1,"lotteryName":"大乐透","lotteryImg":"http://39.108.61.68:80/group1/M00/00/04/rBKZOFutGJ2AXnHhAAAvabfSCIc076.png","lotteryResult":"game_lotto_result","lotteryType":"NATIONWIDE","abbreviation":"lotto","lotteryResultDto":{"expect":"2018124","openTime":"10-22  20:34","weekDay":"星期一","openCode":"08,10,12,19,20+10,11"},"lotteryFunctionList":[{"id":1,"functionName":"开奖结果","functionTag":1,"lotteryId":1},{"id":28,"functionName":"走势图表","functionTag":2,"chartUrl":"http://192.168.0.199:9090/examples/zst/brings.html?lotteryId=1","lotteryId":1}]}
     */

    private int id;
    private LotteryEntity lotteryDto;

    public LotteryEntity getLotteryDto() {
        return lotteryDto;
    }

    public void setLotteryDto(LotteryEntity lotteryDto) {
        this.lotteryDto = lotteryDto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
