// 4/11/2024 - ADDED LOGIC TO CANCEL FULLY FILLED BUY ORDERS IN ORDER TO ENSURE MORE BUY AND SELL ORDERS
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

// import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
9TH AND FINAL CODE - AFTER BUGS WERE FIXED FROM 17/10/2024
TRADING ALGO THAT CREATES BUY ORDERS AND CANCELS AN ORDER BASED ON CERTAIN CONDITIONS BEING MET
THE ALGO CAN ALSO MAKE A PROFIT BY BUYING SHARES WHEN PRICES ARE LOW AND SELLING THEM WHEN PRICES ARE HIGH
*/


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

        // Log the bid/ask information
        logger.info("[PROFITALGO] Best bid: " + bestBidQuantity + " @ " + bestBidPrice +
                    " Best ask: " + bestAskQuantity + " @ " + bestAskPrice);   
        
        // Retrieve the list of active child orders
        var activeChildOrders = state.getActiveChildOrders();


        // TO CREATE CHILD ORDERS
        // If there are less than 5 active child orders, and the spread has narrowed to or below 3 points,
        // log relevant information then create a buy limit order at the best BID price and defined buy quantity
        
        if (activeChildOrders.size() < 5) {

            // Define the spread threshold (in price points)
            final long spread = Math.abs(bestAskPrice - bestBidPrice); // Math.abs added 5/10/2024 to get absolute value of a number
            final long spreadThreshold = 3L;

            // Define the quantity you want to buy
            final long buyQuantity = 200L;

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
        // for each buy order, if that order is partially filled or not filled at all AND the best ask price
        // moves above the buy price for that specific order by 7 points or more, cancel that  order

        long thisOrderId;
        Side thisOrderSide;        
        long thisOrderBoughtQuantity;        
        long thisOrderPrice;
        long thisOrderFilledQuantity;

        // amended code - now cancelling partially filled or unfilled orders
        if (!activeChildOrders.isEmpty()) {      

            // Define a price reversal threshold (in price points)            
            final long priceReversalThreshold = 7L; // was 10L
            
            for (ChildOrder thisOrder : activeChildOrders) {

                if (thisOrder.getSide() == Side.BUY) {
                    thisOrderId = thisOrder.getOrderId();
                    thisOrderSide = thisOrder.getSide();
                    thisOrderBoughtQuantity = thisOrder.getQuantity();                    
                    thisOrderPrice = thisOrder.getPrice();
                    thisOrderFilledQuantity = thisOrder.getFilledQuantity();

                    // Check if the order is partially filled or not filled at all
                    if (thisOrderFilledQuantity < thisOrderBoughtQuantity) {

                        // Calculate the unfilled quantity
                        final long thisOrderUnfilledQuantity = thisOrderBoughtQuantity - thisOrderFilledQuantity;

                        // Log the details of the unfilled or partially filled orders
                        logger.info("[MYALGO] CANCEL CONDITIONS - Partially filled or Non filled order found. " +
                                    " Order ID: " + thisOrderId +
                                    " Side: " + thisOrderSide +
                                    ", Ordered Qty: " + thisOrderBoughtQuantity +
                                    ", Price: " + thisOrderPrice +                                            
                                    ", Filled Qty: " + thisOrderFilledQuantity  +
                                    ", Unfilled Qty: " + thisOrderUnfilledQuantity);

                        // if the ASK price moves to or above the defined threshold of 7 points of more, log that information then cancel
                        // the unfilled part of the order
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
        
        // CONDITIONS TO CANCEL FULLY FILLED SELL ORDERS TO ENABLE THE ALGO CREATE MORE BUY AND SELL ORDERS 
        long thisSellOrderBoughtQuantity;        
        long thisSellOrderFilledQuantity;

        if (!activeChildOrders.isEmpty()) {
            for (ChildOrder thisSellOrder : activeChildOrders) {
                thisSellOrderBoughtQuantity = thisSellOrder.getQuantity();
                thisSellOrderFilledQuantity = thisSellOrder.getFilledQuantity();

                // check for fully filled sell orders and cancel them
                if (thisSellOrder.getSide() == Side.SELL
                    && thisSellOrderFilledQuantity == thisSellOrderBoughtQuantity){       
                        return new CancelChildOrder(thisSellOrder);
                } 
                else{
                logger.info("[PROFITALGO] SELL CONDITIONS - No sell orders with filled quantities. Take no action.");
                }

            }
        }
        else{
        logger.info("[PROFITALGO] SELL CONDITIONS - List is empty, take no action. Continue with step to sell");
        }


        // CONDITIONS TO SELL THE SHARES FOR A PROFIT
        // if there are sufficient shares to sell, and the bid and ask prices have gone above your previous bought price
        // by 2 points or more, sell all the shares you currently own and at current best Bid price 

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

        // Set the minimum buy filled quantity required to initiate a sell order
        final long minimumSellQuantityThreshold = 50;

        // Check if there is sufficient available quantity to sell
        if (remainingQuantityToSell >= minimumSellQuantityThreshold){

            // Extract the price of the last filled buy order, or else provide a default value of 0 if no filled buy orders are found
            final long lastBoughtPrice = state.getChildOrders().stream()
                                              .filter(order -> order.getSide() == Side.BUY)
                                              .filter(order -> order.getFilledQuantity() > 0)
                                              .reduce((first, second) -> second)
                                              .map(ChildOrder::getPrice)
                                              .orElse(0L);

            // if the bid and ask prices have gone up your previuous bought price by 2 points or more, log relevant information
            // and sell the remaining quantity of shares at best Bid price
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
                logger.info("[PROFITALGO] SELL CONDITIONS - Price has not moved above threshold. No sell order created.");
            }

        } 
        else {
            logger.info("[PROFITALGO] SELL CONDITIONS - No available quantity to sell or minimum filled buy quantity not met.  No sell order created.");
        }     

        // If the conditions are not met, return no action
        return NoAction.NoAction;

    }

}


// // 25/10/2024 - AMMENDED THE CODE TO ENSURE MORE SELL ORDERS

// package codingblackfemales.gettingstarted;

// import codingblackfemales.action.Action;
// import codingblackfemales.action.CancelChildOrder;
// import codingblackfemales.action.CreateChildOrder;
// import codingblackfemales.action.NoAction;
// import codingblackfemales.algo.AlgoLogic;
// import codingblackfemales.sotw.ChildOrder;
// import codingblackfemales.sotw.SimpleAlgoState;
// import codingblackfemales.sotw.marketdata.AskLevel;
// import codingblackfemales.sotw.marketdata.BidLevel;
// import codingblackfemales.util.Util;
// import messages.order.Side;

// // import java.util.Optional;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// // 9TH AND FINAL CODE - AFTER BUGS WERE FIXED FROM 17/10/2024
// // TRADING ALGO THAT CREATES BUY ORDERS AND CANCELS AN ORDER BASED ON THE SPREAD NARROWING AND EXPANDING
// // THE ALGO CAN ALSO MAKE A PROFIT BY BUYING SHARES WHEN PRICES ARE LOW AND SELLING THEM WHEN PRICES ARE HIGH
// // IF THE SPREAD NARROWS TO OR BELOW THE SET THRESHOLD, CREATE UP TO 5 BUY ORDERS FOR 200 SHARES PER ORDER.
// // FOR EACH UNFILLED/PARTIALLY FILLED BUY ORDER, IF THE BEST ASK PRICE IS ABOVE THE BUY PRICE FOR THAT SPECIFIC
// // ORDER BY A SET NUMBER OF POINTS, CANCEL THAT  ORDER
// // IF THE BID AND ASK PRICES GO ABOVE YOUR PREVIOUS BOUGHT PRICE BY A CERTAIN THRESHOLD, SELL ALL THE SHARES AT
// // CURRENT BEST BID PRICE WITH THE AIM OF MAKING A PROFIT



// public class MyProfitAlgo implements AlgoLogic {
//     private static final Logger logger = LoggerFactory.getLogger(MyProfitAlgo.class);

//     @Override
//     public Action evaluate(SimpleAlgoState state) {

//         var orderBookAsString = Util.orderBookToString(state);

//         logger.info("[PROFITALGO] The state of the order book is:\n" + orderBookAsString);

//         /** Algo **/

//         // Get the best bid and best ask levels     
//         BidLevel bestBid = state.getBidAt(0);
//         AskLevel bestAsk = state.getAskAt(0);

//         // Get the best bid and ask prices and their corresponding quantities
//         final long bestBidPrice = bestBid.getPrice();
//         final long bestAskPrice = bestAsk.getPrice();
//         final long bestBidQuantity = bestBid.getQuantity();
//         final long bestAskQuantity = bestAsk.getQuantity();

//         // Log the bid/ask information
//         logger.info("[PROFITALGO] Best bid: " + bestBidQuantity + " @ " + bestBidPrice +
//                     " Best ask: " + bestAskQuantity + " @ " + bestAskPrice);   
        
//         // Retrieve the list of active child orders
//         var activeChildOrders = state.getActiveChildOrders();


//         // TO CREATE CHILD ORDERS
//         // If there are less than 5 active child orders, and the spread has narrowed to or below the defined threshold, of 3
//         // points or less, log relevant information then create a buy limit order at the best BID price and defined buy quantity
        
//         if (activeChildOrders.size() < 5) {

//             // Define the spread threshold (in price points)
//             final long spread = Math.abs(bestAskPrice - bestBidPrice); // Math.abs added 5/10/2024 to get absolute value of a number
//             final long spreadThreshold = 3L;

//             // Define the quantity you want to buy
//             final long buyQuantity = 200L;

//             if (spread <= spreadThreshold) {
//                     logger.info("[PROFITALGO] BUY CONDITIONS - Spread is " + spread + " points.");
//                     logger.info("BUY CONDITIONS - [PROFITALGO] has " + activeChildOrders.size() + " active child orders. " +
//                                 " Spread is below threshold. Creating a buy order for " + buyQuantity + " units @ " + bestBidPrice);
//                 return new CreateChildOrder(Side.BUY, buyQuantity, bestBidPrice);
//             }
//             else {
//                 // Log if the spread does not meet the threshold
//                 logger.info("[PROFITALGO] BUY CONDITIONS - Spread is " + spread + " points.");
//                 logger.info("[PROFITALGO] BUY CONDITIONS - Spread is above the buying threshold. No buy order created.");
//             }
//         }      
//         else {
//             // If there are already 5 active child orders, log it and take no action
//             logger.info("BUY CONDITIONS - [PROFITALGO] has " + activeChildOrders.size() + 
//                         " active child orders. No new orders will be created.");
//         }             
        

        
//         // CONDITIONS TO CANCEL A BUY ORDER        
//         // for each  buy order, if that order is partially filled or not filled at all AND the best ask price
//         // moves above the buy price for that specific order by 7 points or more, cancel that  order

//         long thisOrderId;
//         Side thisOrderSide;        
//         long thisOrderBoughtQuantity;        
//         long thisOrderPrice;
//         long thisOrderFilledQuantity;

//         // amended code - now cancelling partially filled or unfilled orders
//         if (!activeChildOrders.isEmpty()) {      

//             // Define a price reversal threshold (in price points)            
//             final long priceReversalThreshold = 7L; // was 10L
            
//             for (ChildOrder thisOrder : activeChildOrders) {

//                 if (thisOrder.getSide() == Side.BUY) {
//                     thisOrderId = thisOrder.getOrderId();
//                     thisOrderSide = thisOrder.getSide();
//                     thisOrderBoughtQuantity = thisOrder.getQuantity();                    
//                     thisOrderPrice = thisOrder.getPrice();
//                     thisOrderFilledQuantity = thisOrder.getFilledQuantity();

//                     // Check if the order is partially filled or not filled at all
//                     if (thisOrderFilledQuantity < thisOrderBoughtQuantity) {

//                         // Calculate the unfilled quantity
//                         final long thisOrderUnfilledQuantity = thisOrderBoughtQuantity - thisOrderFilledQuantity;

//                         // Log the details of the unfilled or partially filled orders
//                         logger.info("[MYALGO] CANCEL CONDITIONS - Partially filled or Non filled order found. " +
//                                     " Order ID: " + thisOrderId +
//                                     " Side: " + thisOrderSide +
//                                     ", Ordered Qty: " + thisOrderBoughtQuantity +
//                                     ", Price: " + thisOrderPrice +                                            
//                                     ", Filled Qty: " + thisOrderFilledQuantity  +
//                                     ", Unfilled Qty: " + thisOrderUnfilledQuantity);

//                         // if the ASK price moves to or above the defined threshold of 7 points of more, log that information then cancel
//                         // the unfilled part of the order
//                         if (bestAskPrice >= (thisOrderPrice + priceReversalThreshold)) {
//                             logger.info("[MYALGO] CANCEL CONDITIONS - BesAsk is: " + bestAsk + " thisOrderPrice is  : " + thisOrderPrice);
//                             logger.info("[MYALGO] CANCEL CONDITIONS - price reversal threshold is " + priceReversalThreshold + " points.");
//                             logger.info("[MYALGO] CANCEL CONDITIONS - Ask price moved against buy order. " +
//                                         " Cancelling order ID: " + thisOrderId +
//                                         ", Unfilled Qty: " + (thisOrderUnfilledQuantity));
                                        
//                             return new CancelChildOrder(thisOrder);
//                         }
//                         else {
//                             // If the ask price has not moved beyond the threshold, log that the order remains active
//                             logger.info("[MYALGO] CANCEL CONDITIONS  - Ask price is stil under threshold. Buy order to remain active");
//                         }
//                     }

//                     // // check if the buy order is fully filled then cancel all filled buy orders from the list of active child orders  
//                     // // added this code 03/11/2024 to enable the algo to creae more buy and sell orders but didn't work. Got
//                     // // [ERROR]   MyProfitAlgoBackTest.backtestAlgoBehaviourFrom1stTickTo11thTick:462->SequencerTestCase.send:17 »
//                     // // NullPointer Cannot read field "quantity" because "level" is null

//                     // else if (thisOrderFilledQuantity == thisOrderBoughtQuantity){
//                     //     return new CancelChildOrder(thisOrder);
//                     // }  

//                     else {
//                         // Code to log if there are no unfilled quantities
//                         logger.info("[PROFITALGO] CANCEL CONDITIONS - No orders need cancelling. No action taken.");
//                     }
//                 }                     
//                 else {
//                     // if it was not a buy order, log this and take no action
//                     logger.info("[PROFITALGO] CANCEL CONDITIONS -  This was not a buy order. No action taken.");
//                 }

//             }
//         }  
        
//         // THIS CODE WAS LIMITING THE ALGO TO MAKING ONLY ONE SELL ORDER. SO COMMENTED THE CODE OUT THEN ADDED MORE
//         // CODE FURTHER BELOW  
//         // if (!activeChildOrders.isEmpty()) {
//         //     for (ChildOrder childOrder : activeChildOrders) {
//         //         if (childOrder.getSide() == Side.SELL) { 
//         //                 logger.info("[PROFITALGO] SELL CONDITIONS - A sell order exists. Take no action.");
//         //             return NoAction.NoAction;  
//         //         }
//         //     }
//         //     logger.info("[PROFITALGO] SELL CONDITIONS - The list has no sell orders. Continue with step to sell");
//         // } 



//         // CONDITIONS TO SELL THE SHARES FOR A PROFIT
//         // if there are sufficient shares to sell, and the bid and ask prices have gone above your previous bought price
//         // by 2 points or more, sell all the shares you currently own and at current best Bid price 

//         // Calculate the total filled quantity for buy orders
//         final long buyOrdersTotalFilledQuantity = state.getChildOrders().stream()
//                                                        .filter(order -> order.getSide() == Side.BUY)
//                                                        .filter(order -> order.getFilledQuantity() > 0)
//                                                        .mapToLong(ChildOrder::getFilledQuantity)
//                                                        .sum();

//         // Calculate the total filled quantity for sell orders
//         final long sellOrderTotalFilledQuantity = state.getChildOrders().stream()
//                                                        .filter(order -> order.getSide() == Side.SELL && order.getFilledQuantity() > 0)
//                                                        .mapToLong(ChildOrder::getFilledQuantity)
//                                                        .sum();

//         // Determine the quantity available for sale
//         final long remainingQuantityToSell = buyOrdersTotalFilledQuantity - sellOrderTotalFilledQuantity;

//         // Set the minimum buy filled quantity required to initiate a sell order
//         final long minimumSellQuantityThreshold = 50;

//         // final long averageBoughtPrice = (long) state.getChildOrders().stream()
//         //                                     .filter(order -> order.getSide() == Side.BUY)
//         //                                     .filter(order -> order.getFilledQuantity() > 0)
//         //                                     .mapToLong(ChildOrder::getPrice) // map to the price of the buy order
//         //                                     .average()
//         //                                     .orElse(0L); // Default to 0 if there are no valid orders

//         // Check if there is sufficient available quantity to sell
//         if (remainingQuantityToSell >= minimumSellQuantityThreshold){

//             // Extract the price of the last filled buy order, or else provide a default value of 0 if no filled buy orders are found
//             final long lastBoughtPrice = state.getChildOrders().stream()
//                                               .filter(order -> order.getSide() == Side.BUY)
//                                               .filter(order -> order.getFilledQuantity() > 0)
//                                               .reduce((first, second) -> second)
//                                               .map(ChildOrder::getPrice)
//                                               .orElse(0L);

//             // if the bid and ask prices have gone up your previuous bought price by 2 points or more, log relevant information
//             // and sell the remaining quantity of shares at best Bid price
//             if (lastBoughtPrice > 0
//                 && bestBidPrice > lastBoughtPrice + 2
//                 && bestAskPrice > lastBoughtPrice + 2) {

//                     logger.info("[PROFITALGO] SELL CONDITIONS - bestBidPrice: " + bestBidPrice + 
//                                 " | bestAskPrice " + bestAskPrice +
//                                 " | averageBoughtPrice: " + lastBoughtPrice);

//                     logger.info("[PROFITALGO] SELL CONDITIONS - Selling " + remainingQuantityToSell + 
//                             " units at " + bestBidPrice);

//                 return new CreateChildOrder(Side.SELL, remainingQuantityToSell, bestBidPrice);

//             }
//              else {
//                 logger.info("[PROFITALGO] SELL CONDITIONS - Price has not moved above threshold. No sell order created.");
//             }

//         } 
//         else {
//             logger.info("[PROFITALGO] SELL CONDITIONS - No available quantity to sell or minimum filled buy quantity not met.  No sell order created.");
//         }     

//         // If the conditions are not met, return no action
//         return NoAction.NoAction;

//     }

// }


