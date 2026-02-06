package models;

public class SubscriptionFactory {
    public static Subscriptiondetails getDetails(String type) {
        if (type == null) type = "NONE";
        return switch (type.toUpperCase()) {
            case "PREMIUM" -> new Subscriptiondetails("PREMIUM", false, true);
            case "STANDARD" -> new Subscriptiondetails("STANDARD", false, false);
            case "BASIC" -> new Subscriptiondetails("BASIC", true, false);
            default -> new Subscriptiondetails("NONE", true, false);
        };
    }
}
