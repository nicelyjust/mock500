package com.yunzhou.common.utils;

public class FastClickUtil {

	private static final long MAX_OVER_TIME = 1000;
	private static long clickTimer = -1;

	private FastClickUtil() {
	}

	public static boolean isFastClick() {

		if ( clickTimer > 0 ) {
			long timeOffset = System.currentTimeMillis() - clickTimer;

			if ( timeOffset < 0 ) {
				clickTimer = System.currentTimeMillis();
				return false;
			}

			if ( timeOffset <= MAX_OVER_TIME ) {
				return true;
			} else {
				clickTimer = System.currentTimeMillis();
				return false;
			}

		} else {
			clickTimer = System.currentTimeMillis();
			return false;
		}

	}

}
