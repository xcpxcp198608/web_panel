package com.wiatec.panel.dto;



/**
 * @author patrick
 */
public class LdTrendingVoteCountInfo {

    private int trendingId;
    private int count;

    public int getTrendingId() {
        return trendingId;
    }

    public void setTrendingId(int trendingId) {
        this.trendingId = trendingId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "LdTrendingVoteCountInfo{" +
                "trendingId=" + trendingId +
                ", count=" + count +
                '}';
    }
}
