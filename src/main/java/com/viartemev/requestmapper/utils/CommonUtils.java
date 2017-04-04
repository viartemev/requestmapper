package com.viartemev.requestmapper.utils;

public class CommonUtils {

    public static String unquote(String s) {
        return s != null && s.length() >= 2 && s.charAt(0) == '\"'
                ? s.substring(1, s.length() - 1)
                : s;
    }

    public static boolean isSimilar(String[] patternList, String[] itemList) {
        if (itemList.length < patternList.length) {
            return false;
        }
        for (int i = 0; (i < patternList.length) && (i < itemList.length); i++) {
            String pattern = patternList[i];
            String item = itemList[i];
            if ((!item.startsWith("{")) || (!item.endsWith("}"))) {
                if (!pattern.equals(item)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean contains(String[] patternList, int patternIdx, String[] itemList, int itemIdx) {
        if ((itemIdx >= itemList.length) || (patternIdx >= patternList.length)) {
            return false;
        }
        String currentItem = itemList[itemIdx];
        if (patternIdx < patternList.length) {
            int restPattern = patternList.length - patternIdx;
            int restItem = itemList.length - itemIdx;
            if (restItem < restPattern) {
                return false;
            }
            String currentPattern = patternList[patternIdx];
            if (currentItem.contains(currentPattern)) {
                if (patternIdx == patternList.length - 1) {
                    return true;
                }
                return contains(patternList, patternIdx + 1, itemList, itemIdx + 1);
            }
            return contains(patternList, patternIdx, itemList, itemIdx + 1);
        }

        return false;
    }

}
