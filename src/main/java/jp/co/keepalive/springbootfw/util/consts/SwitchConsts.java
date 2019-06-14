package jp.co.keepalive.springbootfw.util.consts;

public class SwitchConsts {

    public static String BUILD_MODE;
    public static final String BUILD_MODE_PRODUCTION = "pro";
    public static final String BUILD_MODE_STAGING = "stg";
    private static final String PROPS_KEY_BUILD_MODE = "build.mode";

//    static {
//        String mode = PropertyLoader.getInstance().getPropertyValue(PROPS_KEY_BUILD_MODE);
//        if(mode.equals(BUILD_MODE_PRODUCTION)) {
//
//        } else {
//
//        }
//    }

    /**
	 * Production用のビルド
	 *
	 * @return
	 */
	public static boolean isModeProduction(){
		return BUILD_MODE_PRODUCTION.equals(BUILD_MODE);
	}

	/**
	 * Staging用のビルド
	 *
	 * @return
	 */
	public static boolean isModeStaging(){
		return BUILD_MODE_STAGING.equals(BUILD_MODE);
	}

}