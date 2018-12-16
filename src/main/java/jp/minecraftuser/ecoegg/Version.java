package jp.minecraftuser.ecoegg;


public class Version {

    public static boolean compare(String target_version, String yml_version) {
        if (yml_version == null) {
            return false;
        }
        String[] targetParts = target_version.split("\\.");
        String[] ymlParts = yml_version.split("\\.");

        int length = Math.max(targetParts.length, ymlParts.length);
        for (int i = 0; i < length; i++) {
            int target_part = i < targetParts.length ? Integer.parseInt(targetParts[i]) : 0;
            int yml_part = i < ymlParts.length ? Integer.parseInt(ymlParts[i]) : 0;
            if (target_part < yml_part)
                return true;
            if (target_part > yml_part)
                return false;
        }
        return true;

    }
}