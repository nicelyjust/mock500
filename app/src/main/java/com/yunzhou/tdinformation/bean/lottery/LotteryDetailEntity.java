package com.yunzhou.tdinformation.bean.lottery;

import com.yunzhou.common.http.bean.base.Entity;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.lottery
 *  @文件名:   LotteryDetailEntity
 *  @创建者:   lz
 *  @创建时间:  2018/10/29 19:59
 *  @描述：
 */

public class LotteryDetailEntity extends Entity {

    /**
     * nationalSales : 2.34亿
     * currentAward : 122205817
     * rolling : 65.22亿
     * bonusSituationDtoList : [{"prize":"一等奖","winningConditions":"5+2","numberOfWinners":"6","singleNoteBonus":"8116254","isSuperAddition":1,"additionNumber":"2","additionBonus":"4869752"},{"prize":"二等奖","winningConditions":"5+1","numberOfWinners":"91","singleNoteBonus":"109271","isSuperAddition":1,"additionNumber":"35","additionBonus":"65562"},{"prize":"三等奖","winningConditions":"5+0,4+2","numberOfWinners":"756","singleNoteBonus":"5345","isSuperAddition":1,"additionNumber":"224","additionBonus":"3207"},{"prize":"四等奖","winningConditions":"4+1,3+2","numberOfWinners":"34055","singleNoteBonus":"200","isSuperAddition":1,"additionNumber":"12548","additionBonus":"100"},{"prize":"五等奖","winningConditions":"4+0,3+1,2+2","numberOfWinners":"660584","singleNoteBonus":"10","isSuperAddition":1,"additionNumber":"235926","additionBonus":"5"},{"prize":"六等奖","winningConditions":"3+0,1+2,2+1,0+2","numberOfWinners":"6184000","singleNoteBonus":"5","isSuperAddition":1,"additionNumber":"-2","additionBonus":"-2"}]
     */

    private String nationalSales;
    private String currentAward;
    private String rolling;
    private List<BonusSituationDtoListBean> bonusSituationDtoList;

    public String getNationalSales() {
        return nationalSales;
    }

    public void setNationalSales(String nationalSales) {
        this.nationalSales = nationalSales;
    }

    public String getCurrentAward() {
        return currentAward;
    }

    public void setCurrentAward(String currentAward) {
        this.currentAward = currentAward;
    }

    public String getRolling() {
        return rolling;
    }

    public void setRolling(String rolling) {
        this.rolling = rolling;
    }

    public List<BonusSituationDtoListBean> getBonusSituationDtoList() {
        return bonusSituationDtoList;
    }

    public void setBonusSituationDtoList(List<BonusSituationDtoListBean> bonusSituationDtoList) {
        this.bonusSituationDtoList = bonusSituationDtoList;
    }

    public static class BonusSituationDtoListBean extends Entity{
        /**
         * prize : 一等奖
         * winningConditions : 5+2
         * numberOfWinners : 6
         * singleNoteBonus : 8116254
         * isSuperAddition : 1
         * additionNumber : 2
         * additionBonus : 4869752
         */

        private String prize;
        private String winningConditions;
        private String numberOfWinners;
        private String singleNoteBonus;
        private int isSuperAddition;
        private String additionNumber;
        private String additionBonus;

        public String getPrize() {
            return prize;
        }

        public void setPrize(String prize) {
            this.prize = prize;
        }

        public String getWinningConditions() {
            return winningConditions;
        }

        public void setWinningConditions(String winningConditions) {
            this.winningConditions = winningConditions;
        }

        public String getNumberOfWinners() {
            return numberOfWinners;
        }

        public void setNumberOfWinners(String numberOfWinners) {
            this.numberOfWinners = numberOfWinners;
        }

        public String getSingleNoteBonus() {
            return singleNoteBonus;
        }

        public void setSingleNoteBonus(String singleNoteBonus) {
            this.singleNoteBonus = singleNoteBonus;
        }

        public int getIsSuperAddition() {
            return isSuperAddition;
        }

        public void setIsSuperAddition(int isSuperAddition) {
            this.isSuperAddition = isSuperAddition;
        }

        public String getAdditionNumber() {
            return additionNumber;
        }

        public void setAdditionNumber(String additionNumber) {
            this.additionNumber = additionNumber;
        }

        public String getAdditionBonus() {
            return additionBonus;
        }

        public void setAdditionBonus(String additionBonus) {
            this.additionBonus = additionBonus;
        }
    }
}
