// 25/10/2024 - AMMENDED THE CODE TO ENSURE MORE SELL ORDERS

package codingblackfemales.gettingstarted;

import codingblackfemales.action.Action;
import codingblackfemales.action.CancelChildOrder;
import codingblackfemales.action.CreateChildOrder;
import codingblackfemales.action.NoAction;
import codingblackfemales.algo.AlgoLogic;
import codingblackfemales.sotw.ChildOrder;
import codingblackfemales.sotw.SimpleAlgoState;
import codingblackfemales.sotw.marketdata.AskLevel;
import codingblackfemales.sotw.marketdata.BidLevel;
import codingblackfemales.util.Util;
import messages.order.Side;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 9TH AND FINAL CODE - AFTER BUGS WERE FIXED FROM 17/10/2024
// TRADING ALGO THAT CREATES BUY ORDERS AND CANCELS AN ORDER BASED ON THE SPREAD NARROWING AND EXPANDING
// THE ALGO CAN ALSO MAKE A PROFIT BY BUYING SHARES WHEN PRICES ARE LOW AND SELLING THEM WHEN PRICES ARE HIGH
// IF THE SPREAD NARROWS TO OR BELOW THE SET THRESHOLD, CREATE UP TO 5 BUY ORDERS FOR 200 SHARES PER ORDER,
// FOR EACH UNFILLED/PARTIALLY FILLED BUY ORDER, IF THE BEST ASK PRICE IS ABOVE THE BUY PRICE FOR THAT SPECIFIC
// ORDER, PLUS A SET NUMBER OF POINTS, CANCEL THAT  ORDER
// IF THE BID AND ASK PRICES GO ABOVE YOUR PREVIOUS BOUGHT PRICE BY A CERTAIN THRESHOLD, SELL THE SHARES AT
// CURRENT BEST BID PRICE WITH THE AIM OF MAKING A PROFIT



public class MyProfitAlgo implements AlgoLogic {
    private static final Logger logger = LoggerFactory.getLogger(MyProfitAlgo.class);

    @Override
    public Action evaluate(SimpleAlgoState state) {

        var orderBookAsString = Util.orderBookToString(state);

        logger.info("[PROFITALGO] The state of the order book is:\n" + orderBookAsString);

        /** Algo **/

        // Get the best bid and best ask levels     
        BidLevel bestBid = state.getBidAt(0);
        AskLevel bestAsk = state.getAskAt(0);

        // Get the best bid and ask prices and their corresponding quantities
        final long bestBidPrice = bestBid.getPrice();
        final long bestAskPrice = bestAsk.getPrice();
        final long bestBidQuantity = bestBid.getQuantity();
        final long bestAskQuantity = bestAsk.getQuantity();
        final long buyQuantity = 200L;

        // Define a threshold for the spread (in price points)
        final long spread = Math.abs(bestAskPrice - bestBidPrice); // added 5/10/2024 to get absolute value of a number
        final long spreadThreshold = 3L;

        // Log the bid-ask information
        logger.info("[PROFITALGO] Best bid: " + bestBidQuantity + " @ " + bestBidPrice +
                    " Best ask: " + bestAskQuantity + " @ " + bestAskPrice);   
        
        // Retrieve the list of active child orders
        var activeChildOrders = state.getActiveChildOrders();


        // TO CREATE CHILD ORDERS
        // If there are less than 5 active buy child orders, and the spread has narrowed to the defined threshold,
        // create a buy limit order at the best BID price and defined buy quantity

        if (activeChildOrders.size() < 5) {

            if (spread <= spreadThreshold) {
                    logger.info("[PROFITALGO] BUY CONDITIONS - Spread is " + spread + " points.");
                    logger.info("BUY CONDITIONS - [PROFITALGO] has " + activeChildOrders.size() + " active child orders. " +
                                " Spread is below threshold. Creating a buy order for " + buyQuantity + " units @ " + bestBidPrice);
                return new CreateChildOrder(Side.BUY, buyQuantity, bestBidPrice);
            }
            else {
                // Log if the spread does not meet the threshold
                logger.info("[PROFITALGO] BUY CONDITIONS - Spread is " + spread + " points.");
                logger.info("[PROFITALGO] BUY CONDITIONS - Spread is above the buying threshold. No buy order created.");
            }
        }      
        else {
            // If there are already 5 active child orders, log it and take no action
            logger.info("BUY CONDITIONS - [PROFITALGO] has " + activeChildOrders.size() + 
                        " active child orders. No new orders will be created.");
        }             
        

        // CONDITIONS TO CANCEL A BUY ORDER        

        long thisOrderId;
        Side thisOrderSide;        
        long thisOrderQuantity;        
        long thisOrderPrice;
        long thisOrderFilledQuantity;
        long thisOrderUnfilledQuantity;

        // amended code - now cancelling partially filled or unfilled orders
        if (!activeChildOrders.isEmpty()) {      

            // Define a price reversal threshold (in price points)            
            final long priceReversalThreshold = 7L; // was 10L
            
            for (ChildOrder thisOrder : activeChildOrders) {

                if (thisOrder.getSide() == Side.BUY) {
                    thisOrderId = thisOrder.getOrderId();
                    thisOrderSide = thisOrder.getSide();
                    thisOrderQuantity = thisOrder.getQuantity();                    
                    thisOrderPrice = thisOrder.getPrice();
                    thisOrderFilledQuantity = thisOrder.getFilledQuantity();

                    // Check if the order is partially filled or not filled at all
                    if (thisOrderFilledQuantity < thisOrderQuantity) {

                        // Calculate the remaining unfilled quantity
                        thisOrderUnfilledQuantity = thisOrderQuantity - thisOrderFilledQuantity;

                        // Log the details of the unfilled or partially filled orders
                        logger.info("[MYALGO] CANCEL CONDITIONS - Partially filled or Non filled order found. " +
                                    " Order ID: " + thisOrderId +
                                    " Side: " + thisOrderSide +
                                    ", Ordered Qty: " + thisOrderQuantity +
                                    ", Price: " + thisOrderPrice +                                            
                                    ", Filled Qty: " + thisOrderFilledQuantity  +
                                    ", Unfilled Qty: " + thisOrderUnfilledQuantity);

                        // Cancel the unfilled part of the order if the ASK price moves to or above the defined threshold
                        if (bestAskPrice >= (thisOrderPrice + priceReversalThreshold)) {
                            logger.info("[MYALGO] CANCEL CONDITIONS - BesAsk is: " + bestAsk + " thisOrderPrice is  : " + thisOrderPrice);
                            logger.info("[MYALGO] CANCEL CONDITIONS - price reversal threshold is " + priceReversalThreshold + " points.");
                            logger.info("[MYALGO] CANCEL CONDITIONS - Ask price moved against buy order. " +
                                        " Cancelling order ID: " + thisOrderId +
                                        ", Unfilled Qty: " + (thisOrderUnfilledQuantity));
                                        
                            return new CancelChildOrder(thisOrder);
                        }
                        else {
                            // If the ask price has not moved beyond the threshold, log that the order remains active
                            logger.info("[MYALGO] CANCEL CONDITIONS  - Ask price is stil under threshold. Buy order to remain active");
                        }
                    }
                    else {
                        // Code to log if there are no unfilled quantities
                        logger.info("[PROFITALGO] CANCEL CONDITIONS - No orders need cancelling. No action taken.");
                    }
                }                     
                else {
                    // if it was not a buy order, log this and take no action
                    logger.info("[PROFITALGO] CANCEL CONDITIONS -  This was not a buy order. No action taken.");
                }

            }
        }  
        
        // THIS CODE WAS LIMITING THE ALGO TO MAKIGN ONLY ONE SELL ORDER. SO COMMENTED THE CODE OUT THEN ADDED MORE
        // CODE FURTHER BELOW  
        // if (!activeChildOrders.isEmpty()) {
        //     for (ChildOrder childOrder : activeChildOrders) {
        //         if (childOrder.getSide() == Side.SELL) { 
        //                 logger.info("[PROFITALGO] SELL CONDITIONS - A sell order exists. Take no action.");
        //             return NoAction.NoAction;  
        //         }
        //     }
        //     logger.info("[PROFITALGO] SELL CONDITIONS - The list has no sell orders. Continue with step to sell");
        // }  



        // CONDITIONS TO SELL THE SHARES FOR A PROFIT

        // Calculate the total filled quantity for buy orders
        final long buyOrdersTotalFilledQuantity = state.getChildOrders().stream()
                                                       .filter(order -> order.getSide() == Side.BUY)
                                                       .filter(order -> order.getFilledQuantity() > 0)
                                                       .mapToLong(ChildOrder::getFilledQuantity)
                                                       .sum();

        // Calculate the total filled quantity for sell orders
        final long sellOrderTotalFilledQuantity = state.getChildOrders().stream()
                                                       .filter(order -> order.getSide() == Side.SELL && order.getFilledQuantity() > 0)
                                                       .mapToLong(ChildOrder::getFilledQuantity)
                                                       .sum();

        // Determine the quantity available for sale
        final long remainingQuantityToSell = buyOrdersTotalFilledQuantity - sellOrderTotalFilledQuantity;

        // Get the last filled buy order
        final Optional<ChildOrder> lastFilledBuyOrder = state.getChildOrders().stream()
                                                             .filter(order -> order.getSide() == Side.BUY)
                                                             .filter(order -> order.getFilledQuantity() > 0)
                                                             .reduce((first, second) -> second); // Keeps only the last filled buy order

        // Calculate the average bought price from filled buy orders
        // final long averageBoughtPrice = (long) state.getChildOrders().stream()
        //                                             .filter(order -> order.getSide() == Side.BUY)
        //                                             .filter(order -> order.getFilledQuantity() > 0)
        //                                             .mapToLong(ChildOrder::getPrice)
        //                                             .average()
        //                                             .orElse(0L);

        // Extract the price of the last filled buy order, or default to 0 if no filled buy orders exist
        final long lastBoughtPrice = lastFilledBuyOrder.map(ChildOrder::getPrice).orElse(0L);


        // Set the minimum buy filled quantity required to initiate a sell
        final long minimumSellQuantityThreshold = 50;

        // Check for sufficient available quantity 
        if (remainingQuantityToSell >= minimumSellQuantityThreshold){

            // check if market conditions are favourable to sell
            if (lastBoughtPrice > 0
                && bestBidPrice > lastBoughtPrice + 2
                && bestAskPrice > lastBoughtPrice + 2) {

                    logger.info("[PROFITALGO] SELL CONDITIONS - bestBidPrice: " + bestBidPrice + 
                                " | bestAskPrice " + bestAskPrice +
                                " | averageBoughtPrice: " + lastBoughtPrice);

                    logger.info("[PROFITALGO] SELL CONDITIONS - Selling " + remainingQuantityToSell + 
                            " units at " + bestBidPrice);

                return new CreateChildOrder(Side.SELL, remainingQuantityToSell, bestBidPrice);

            }
             else {
                logger.info("[PROFITALGO] SELL CONDITIONS - Price has not moved above threshold");
            }

        } 
        else {
            logger.info("[PROFITALGO] SELL CONDITIONS - No available quantity to sell or minimum filled buy quantity not met.");
        }     

        // If the conditions are not met, return no action
        return NoAction.NoAction;

    }

}

