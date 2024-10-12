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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 8TH AND FINAL CODE
// THIS ALGO CREATES AND CANCELS CHILD ORDERS BASED ON THE SPREAD NARROWING AND EXPANDING
// IF THE SPREAD NARROWS TO OR BELOW 5 POINTS, CREATE 5 ORDERS FOR 200 SHARES PER ORDER,
// FOR EACH UNFILLED/PARTIALLY FILLED ORDER, IF THE BEST ASK PRICE IS ABOVE THE BUY PRICE FOR THAT
// PLUS 7 POINTS, CANCEL THAT  ORDER
// RESULTS - BASED ON 9 MARKET DATA TICKS, THE ALGO CREATED 8 BUY ORDERS, GOT 2 FULLY FILLED ORDERS,
// 1 PARTIAL FILLED, CANCELLED 5 ORDERS AND HAS 3 ACTIVE ORDERS REMAINING (1 NOT FILLED)


public class MyAlgoLogic implements AlgoLogic {
    private static final Logger logger = LoggerFactory.getLogger(MyAlgoLogic.class);

    @Override
    public Action evaluate(SimpleAlgoState state) {

        final var orderBookAsString = Util.orderBookToString(state);

        logger.info("[MYALGO] The state of the order book is:\n" + orderBookAsString);

        /** Algo **/

        // Get the best bid and best ask levels     
        final BidLevel bestBid = state.getBidAt(0);
        final AskLevel bestAsk = state.getAskAt(0);

        // Get the best bid and ask prices and their corresponding quantities
        final long bestBidPrice = bestBid.getPrice();
        final long bestAskPrice = bestAsk.getPrice();
        final long bestBidQuantity = bestBid.getQuantity();
        final long bestAskQuantity = bestAsk.getQuantity();
        final long buyQuantity = 200L;

        // Define a threshold for the spread (in price points)
        final long spread = Math.abs(bestAskPrice - bestBidPrice); // added 5/10/2024 to get absolute value of a number
        final long spreadThreshold = 5L;

        // Log the bid-ask information
        logger.info("[MYALGO] Best bid: " + bestBidQuantity + " @ " + bestBidPrice + " Best ask: " + bestAskQuantity + " @ " + bestAskPrice);
   
        
        // Retrieve the list of active child orders
        final var activeChildOrders = state.getActiveChildOrders();


        // TO CREATE CHILD ORDERS
        // If there are less than 5 active child orders, and the spread has narrowed to the defined threshold,
        // create a buy limit order at the best BID price and defined buy quantity

        // if (activeChildOrders.isEmpty()) { // created 1
        if (activeChildOrders.size() < 5) {

            if (spread <= spreadThreshold) {
                logger.info("[MYALGO] BUY CONDITIONS - Spread is " + spread + " points.");
                logger.info("BUY CONDITIONS - [MYALGO] has " + activeChildOrders.size() + " active child orders. " +
                            " Spread is below threshold. Creating a buy order for " + buyQuantity + " units @ " + bestBidPrice);
                return new CreateChildOrder(Side.BUY, buyQuantity, bestBidPrice);
            }
            else {
                // Log if the spread does not meet the threshold
                logger.info("[MYALGO] BUY CONDITIONS - Spread is " + spread + " points.");
                logger.info("[MYALGO] BUY CONDITIONS - Spread is above the buying threshold. No buy order created.");
            }
        }      
        else {
            // If there are already 5 active child orders, log it and take no action
            logger.info("BUY CONDITIONS - [MYALGO] has " + activeChildOrders.size() + " active child orders. No new orders created.");
        }             
        

        // CONDITIONS TO CANCEL A BUY ORDER        

        long orderId;
        Side orderSide;        
        long orderQuantity;        
        long eachOrderPrice;
        long orderFilledQuantity;
        long unfilledQuantity;


        // Define a price reversal threshold (in price points)            
        long priceReversalThreshold = 7L;  // was 10L          

        // amended code - now cancelling partially filled or unfilled orders
        if (!activeChildOrders.isEmpty()) {                      

            for (ChildOrder order : activeChildOrders) {
                if (order.getSide() == Side.BUY) {
                    orderId = order.getOrderId();
                    orderSide = order.getSide();
                    orderQuantity = order.getQuantity();                    
                    eachOrderPrice = order.getPrice();
                    orderFilledQuantity = order.getFilledQuantity();

                        // Check if the order is partially filled or not filled at all
                        if (orderFilledQuantity < orderQuantity) {

                                // Calculate the remaining unfilled quantity
                                unfilledQuantity = orderQuantity - orderFilledQuantity;

                                // Log the details of the unfilled or partially filled orders
                                logger.info("[MYALGO] CANCEL CONDITIONS - Partially filled or Non filled order found. " +
                                            " Order ID: " + orderId +
                                            " Side: " + orderSide +
                                            ", Ordered Qty: " + orderQuantity +
                                            ", Price: " + eachOrderPrice +                                            
                                            ", Filled Qty: " + orderFilledQuantity  +
                                            ", Unfilled Qty: " + unfilledQuantity);

                                // Cancel the unfilled part of the order if the ASK price moves up by or above the defined threshold
                                if (bestAskPrice >= (eachOrderPrice + priceReversalThreshold)) {
                                    logger.info("[MYALGO] CANCEL CONDITIONS - BesAsk is: " + bestAsk + " OrderPrice: " + eachOrderPrice);
                                    logger.info("[MYALGO] CANCEL CONDITIONS - price reversal threshold is " + priceReversalThreshold + " points.");
                                    logger.info("[MYALGO] CANCEL CONDITIONS - Ask price moved against buy order. " +
                                                " Cancelling order ID: " + orderId +
                                                ", Unfilled Qty: " + (unfilledQuantity));
                                    return new CancelChildOrder(order);
                                }
                                else {
                                    // If the ask price has not moved beyond the threshold, log that the order remains active
                                    logger.info("[MYALGO] CANCEL CONDITIONS  - Ask price is stil under threshold. Buy order to remain active");
                                }
                        }
                        else {
                            // Code to log if there are no unfilled quantities
                            logger.info("[MYALGO] CANCEL CONDITIONS - No orders need cancelling. No action taken.");
                            }
                }                     
                else {
                    // if it was not a buy order, log this and take no action
                    logger.info("[MYALGO] CANCEL CONDITIONS -  This was not a buy order. No action taken.");
                }

            }

        }  
        
        // If no active child orders present, the method returns a NoAction object
        return NoAction.NoAction;
    
    }

}





// // ORIGINAL CODE
// package codingblackfemales.gettingstarted;

// import codingblackfemales.action.Action;
// import codingblackfemales.action.NoAction;
// import codingblackfemales.algo.AlgoLogic;
// import codingblackfemales.sotw.SimpleAlgoState;
// import codingblackfemales.util.Util;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// public class MyAlgoLogic implements AlgoLogic {

//     private static final Logger logger = LoggerFactory.getLogger(MyAlgoLogic.class);

//     @Override
//     public Action evaluate(SimpleAlgoState state) {

//         var orderBookAsString = Util.orderBookToString(state);

//         logger.info("[MYALGO] The state of the order book is:\n" + orderBookAsString);

//         /********
//          *
//          * Add your logic here....
//          *
//          */

//         return NoAction.NoAction;
//     }
// }
