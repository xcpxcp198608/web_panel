package com.wiatec.panel.oxm.pojo.chart.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author patrick
 */
public class LevelDistributionInfo {

    private int level5;
    private int level4;
    private int level3;
    private int level2;
    private int level1;
    private int level0;

    public int getLevel5() {
        return level5;
    }

    public void setLevel5(int level5) {
        this.level5 = level5;
    }

    public int getLevel4() {
        return level4;
    }

    public void setLevel4(int level4) {
        this.level4 = level4;
    }

    public int getLevel3() {
        return level3;
    }

    public void setLevel3(int level3) {
        this.level3 = level3;
    }

    public int getLevel2() {
        return level2;
    }

    public void setLevel2(int level2) {
        this.level2 = level2;
    }

    public int getLevel1() {
        return level1;
    }

    public void setLevel1(int level1) {
        this.level1 = level1;
    }

    public int getLevel0() {
        return level0;
    }

    public void setLevel0(int level0) {
        this.level0 = level0;
    }

    @Override
    public String toString() {
        return "LevelDistributionInfo{" +
                "level5=" + level5 +
                ", level4=" + level4 +
                ", level3=" + level3 +
                ", level2=" + level2 +
                ", level1=" + level1 +
                ", level0=" + level0 +
                '}';
    }
}
