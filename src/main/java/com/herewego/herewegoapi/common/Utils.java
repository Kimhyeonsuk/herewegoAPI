package com.herewego.herewegoapi.common;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<Integer> gameUnitStringToList(String gameUnitStr){
        List<Integer> gameUnitList = new ArrayList<>();

        String[] unitArr = gameUnitStr.split(",");
        for(String unitStr : unitArr) {
            int unit = Integer.parseInt(unitStr);
            gameUnitList.add(unit);
        }

        return gameUnitList;
    }
}
