package models;

public class Subscriptiondetails {
    private String planName;
    private boolean hasAds;
    private boolean canWatchPremium;

    public Subscriptiondetails(String planName, boolean hasAds, boolean canWatchPremium) {
        this.planName = planName;
        this.hasAds = hasAds;
        this.canWatchPremium = canWatchPremium;
    }

    public Subscriptiondetails() {}

    public String getPlanName() { return planName; }
    public boolean isHasAds() { return hasAds; }
    public boolean isCanWatchPremium() { return canWatchPremium; }
}