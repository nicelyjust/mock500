package com.yunzhou.tdinformation.view.badgeview;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.view
 *  @文件名:   BadgeFactory
 *  @创建者:   lz
 *  @创建时间:  2018/10/31 16:09
 *  @描述：
 */

import android.content.Context;
import android.view.Gravity;

public class BadgeFactory {
    public static BadgeView createDot(Context context){
        return  new BadgeView(context).setWidthAndHeight(10,10).setTextSize(0).setBadgeGravity(Gravity.END| Gravity.TOP).setShape(BadgeView.SHAPE_CIRCLE);
    }
    public static BadgeView createCircle(Context context){
        return  new BadgeView(context).setWidthAndHeight(14,14).setTextSize(10).setBadgeGravity(Gravity.END| Gravity.TOP).setShape(BadgeView.SHAPE_CIRCLE);
    }
    public static BadgeView createRectangle(Context context){
        return  new BadgeView(context).setWidthAndHeight(25,20).setTextSize(12).setBadgeGravity(Gravity.END| Gravity.TOP).setShape(BadgeView.SHAPE_RECTANGLE);
    }
    public static BadgeView createOval(Context context){
        return  new BadgeView(context).setWidthAndHeight(25,20).setTextSize(12).setBadgeGravity(Gravity.END| Gravity.TOP).setShape(BadgeView.SHAPE_OVAL);
    }
    public static BadgeView createSquare(Context context){
        return  new BadgeView(context).setWidthAndHeight(20,20).setTextSize(12).setBadgeGravity(Gravity.END| Gravity.TOP).setShape(BadgeView.SHAPE_SQUARE);
    }
    public static BadgeView createRoundRect(Context context){
        return  new BadgeView(context).setWidthAndHeight(25,20).setTextSize(12).setBadgeGravity(Gravity.END| Gravity.TOP).setShape(BadgeView.SHAPTE_ROUND_RECTANGLE);
    }
    public static BadgeView create(Context context){
        return  new BadgeView(context);
    }

}
