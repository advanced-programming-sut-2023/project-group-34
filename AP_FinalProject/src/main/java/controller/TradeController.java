package controller;

import model.Trade;
import model.enums.make_able.Resources;
import model.user.User;

import java.util.regex.Matcher;

public class TradeController {
    public static String showTradeDetails (Trade trade) {
        String finalString = "";
        finalString = finalString.concat("Sender: " + trade.getSender().getName() + "  ");
        if (trade.getOffered() == null) finalString = finalString.concat("Resource offered: Nothing");
        else
            finalString = finalString.concat("Resource offered: " + trade.getOfferedNumber() + " " + trade.getOffered() + "  ");
        if (trade.getWanted() == null) finalString = finalString.concat("Resource wanted: Nothing ");
        else finalString = finalString.concat("Resource wanted: " + trade.getWantedNumber() + " " + trade.getWanted());
        finalString = finalString.concat(" Message: " + trade.getMessage());
        return finalString;
    }
    
    public static String showTradeNotification () {
        String finalString = "";
        
        if (GameController.currentGame.getCurrentGovernment().getOwner().getNotificationsList().isEmpty())
            return "You have no new requests";
        
        while (GameController.currentGame.getCurrentGovernment().getOwner().getNotificationsList().size() > 0) {
            Trade trade = GameController.currentGame.getCurrentGovernment().getOwner().getNotificationsList().poll();
            if (trade.isAccepted())
                finalString = finalString.concat("Trade Accepted: " + showTradeDetails(trade) + " Acceptor: " +
                        trade.getReceiver().getName() + "\n");
            else finalString = finalString.concat("New Request: " + showTradeDetails(trade) + "\n");
        }
        return finalString;
    }
    
    public static String trade (String wantedName, String offeredName, int wantedAmount, int offeredAmount) {
        
        if (wantedAmount < 0 || offeredAmount < 0) return "Invalid amount, trading failed";
        
        if (wantedName.equals("null") && wantedAmount != 0)
            return "This is a donation and you cannot set an amount, trade failed";
        
        if (offeredName.equals("null") && offeredAmount != 0)
            return "You are begging people so you cannot set an amount, trade failed";
        
        if (checkEnoughStorageForTrade(offeredName, offeredAmount))
            return "You do not have enough resources to make this trade, trading failed";

        return "Trade request sent successfully";
    }
    
    public static void tradeWork (String wanted, int wantedAmount, String offered, int offeredAmount, String receiverName, String message) {
        Resources wantedResource = null;
        Resources offeredResource = null;
        for (Resources resources : Resources.values()) {
            if (resources.getName().equals(wanted)) {
                wantedResource = resources;
            }
            if (resources.getName().equals(offered)) {
                offeredResource = resources;
            }
        }
        
        
        Trade trade = new Trade(wantedResource, wantedAmount, offeredResource, offeredAmount,
                GameController.currentGame.getCurrentGovernment().getOwner(), message);
        GameController.currentGame.addAllTrades(trade);
        if (!receiverName.equals("all")) {
            GameController.getPlayerByUsername(receiverName).addToMyTrades(trade);
            GameController.getPlayerByUsername(receiverName).putNotificationList(trade);
        } else {
            for (User player : GameController.currentGame.getPlayers()) {
                player.addToMyTrades(trade);
                player.putNotificationList(trade);
            }
        }
        
    }
    
    private static boolean checkEnoughStorageForTrade (String name, int amount) {
        for (Resources resources : Resources.values()) {
            if (resources.getName().equals(name)) {
                if ((resources.getAmount(GameController.currentGame.getCurrentGovernment()) - amount < 0)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static String showTradeList () {
        String finalString = "";
        int counter = 1;
        if (GameController.currentGame.getCurrentGovernment().getOwner().getMyTrades().isEmpty()) return "You have no offers";
        
        for (Trade trade : GameController.currentGame.getCurrentGovernment().getOwner().getMyTrades()) {
            if (!trade.isAccepted()) {
                finalString = finalString.concat(counter + ". ");
                counter++;
                finalString = finalString.concat(showTradeDetails(trade) + "\n");
            }
        }
        return finalString;
    }
    
    static void AcceptingTradeWork (Trade trade) {
        trade.setAccepted(true);
        int offeredAmount;
        String offeredName;
        int wantedAmount;
        String wantedName;
        if (trade.getWanted() != null) {
            wantedAmount = trade.getWantedNumber();
            wantedName = trade.getWanted().getName();
        } else {
            wantedAmount = 0;
            wantedName = "WOOD";
        }
        if (trade.getOffered() != null) {
            offeredAmount = trade.getOfferedNumber();
            offeredName = trade.getOffered().getName();
        } else {
            offeredAmount = 0;
            offeredName = "WOOD";
        }
        
        for (Resources resources : Resources.values()) {
            if (resources.getName().equals(offeredName)) {
                GameController.currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.put(resources,
                        GameController.currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.get(resources) + offeredAmount);
                if (GameController.currentGame.getCurrentGovernment().getStorageDepartment().resourcesOccupied() >
                        GameController.currentGame.getCurrentGovernment().getStorageDepartment().getResourcesMaxCapacity())
                    GameController.currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.put(resources,
                            GameController.currentGame.getCurrentGovernment().getStorageDepartment().getResourcesMaxCapacity());
                trade.getSender().getGovernment().getStorageDepartment().resourcesStorage.put(resources,
                        trade.getSender().getGovernment().getStorageDepartment().resourcesStorage.get(resources) - offeredAmount);
                
            }
        }
        
        for (Resources resources : Resources.values()) {
            if (resources.getName().equals(wantedName)) {
                GameController.currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.put(resources,
                        GameController.currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.get(resources) - wantedAmount);
                trade.getSender().getGovernment().getStorageDepartment().resourcesStorage.put(resources,
                        trade.getSender().getGovernment().getStorageDepartment().resourcesStorage.get(resources) + wantedAmount);
                if (trade.getSender().getGovernment().getStorageDepartment().resourcesOccupied() >
                        trade.getSender().getGovernment().getStorageDepartment().getResourcesMaxCapacity())
                    trade.getSender().getGovernment().getStorageDepartment().resourcesStorage.put(resources,
                            trade.getSender().getGovernment().getStorageDepartment().getResourcesMaxCapacity());
            }
        }
    }
    
    public static String showTradeHistory () {
        String finalString = "";
        finalString = finalString.concat("Trades Sent: " + "\n");
        for (Trade trade : GameController.currentGame.getAllTrades()) {
            if (trade.getSender().getName().equals(GameController.currentGame.getCurrentGovernment().getOwner().getName()))
                finalString = finalString.concat(showTradeDetails(trade) + "\n");
            if (trade.isAccepted()) {
                finalString = finalString.concat("This trade was accepted" + "\n");
            }
        }
        
        finalString = finalString.concat("Trades Accepted: " + "\n");
        for (Trade trade : GameController.currentGame.getCurrentGovernment().getOwner().getMyTrades()) {
            if (trade.isAccepted() && trade.getReceiver().getName().equals(GameController.currentGame.getCurrentGovernment().getOwner().getName()))
                finalString = finalString.concat(showTradeDetails(trade) + "\n");
        }
        return finalString;
    }
    
    public static String acceptTradeItem (Trade trade) {
        Resources wanted = trade.getWanted();
        int wantedAmount = trade.getWantedNumber();

        if (wanted != null && wantedAmount > wanted.getAmount(GameController.currentGame.getCurrentGovernment()))
            return "You do not have enough resources to accept this trade, accepting trade failed";
        AcceptingTradeWork(trade);
        return "Trade finished successfully";
    }
}
