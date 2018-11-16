package com.yunzhou.tdinformation.setting;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.user
 *  @文件名:   SettingMessageFragment
 *  @创建者:   lz
 *  @创建时间:  2018/11/1 9:46
 *  @描述：
 */

import android.view.View;
import android.widget.RelativeLayout;

import com.yunzhou.common.utils.L;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.mine.widget.ToggleButton;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingMessageFragment extends AbsSettingFragment {
    @BindView(R.id.tb_open_lottery)
    ToggleButton mTbOpenLottery;
    @BindView(R.id.rl_open_lottery)
    RelativeLayout mRlOpenLottery;
    @BindView(R.id.tb_lucky_lottery)
    ToggleButton mTbLuckyLottery;
    @BindView(R.id.rl_lucky_lottery)
    RelativeLayout mRlLuckyLottery;
    @BindView(R.id.tb_battle_msg)
    ToggleButton mTbBattleMsg;
    @BindView(R.id.rl_battle_msg)
    RelativeLayout mRlBattleMsg;
    @BindView(R.id.tb_account_msg)
    ToggleButton mTbAccountMsg;
    @BindView(R.id.rl_account_msg)
    RelativeLayout mRlAccountMsg;
    @BindView(R.id.tb_activity_msg)
    ToggleButton mTbActivityMsg;
    @BindView(R.id.rl_activity_msg)
    RelativeLayout mRlActivityMsg;
    /**
     *  0 - 活动消息类推送 1 - 账号类推送 2 - 赛事类推送 3 - 中奖类推送 4 - 开奖类推送
     */
    private boolean[] messageToggles = {false, false, true, false, true};
    @Override
    public String getTitle() {
        return "推送消息设置";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting_message;
    }

    @Override
    protected void initWidget(View root) {

        mTbActivityMsg.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                messageToggles[0] = on;
            }
        });
        mTbAccountMsg.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                messageToggles[1] = on;
            }
        });
        mTbBattleMsg.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                messageToggles[2] = on;
            }
        });
        mTbLuckyLottery.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                messageToggles[3] = on;
            }
        });
        mTbOpenLottery.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                messageToggles[4] = on;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        L.d("wtf");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        L.d("wtf");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.d("wtf");
    }

    @OnClick({R.id.rl_open_lottery, R.id.rl_lucky_lottery, R.id.rl_battle_msg, R.id.rl_account_msg, R.id.rl_activity_msg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_open_lottery:
                mTbOpenLottery.toggle();
                break;
            case R.id.rl_lucky_lottery:
                mTbLuckyLottery.toggle();
                break;
            case R.id.rl_battle_msg:
                mTbBattleMsg.toggle();
                break;
            case R.id.rl_account_msg:
                mTbAccountMsg.toggle();
                break;
            case R.id.rl_activity_msg:
                mTbActivityMsg.toggle();
                break;
        }
    }

    /**
     *  todo 保存设置
     */
    public void saveSetting() {

    }
}
