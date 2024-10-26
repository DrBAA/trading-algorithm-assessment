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

// 9TH AND FINAL CODE - AFTER THE BUGS WERE FIXED ATFER 17/10/2024
// THIS ALGO CREATES BUY ORDERS BASED ON THE SPREAD NARROWING AND CANCELS AN ORDER BASED ON CERTAIN CONDITIONS
// IF THE SPREAD NARROWS TO BELOW OR EQUALS TO 5 PRICE POINTS, CREATE UP TO 5 BUY ORDERS FOR 200 SHARES PER ORDER.
// FOR EACH UNFILLED/PARTIALLY FILLED BUY ORDER, IF THE BEST ASK PRICE IS ABOVE OR EQUALS TO THE BUY
// PRICE FOR THAT SPECIFIC ORDER PLUS THE PRICE REVERSAL THRESHOLD, CANCEL THAT  ORDER

// RESULTS - BASED ON 9 MARKET DATA TICKS (2 FROM AbstractAlgoBackTest.java AND THE OTHER 7 FROM MyAlgoBackTest.java,
// THE ALGO CREATED 5 BUY ORDERS INITIALLY, GOT 2 FULLY FILLED ORDERS AND 1 PARTIAL FILL
// AFTER THAT, THE ALGO CONTINUED TO CANCEL ORDERS WHEN CONDITIONS WERE MET.
// THE ALGO ALSO CONTINUED TO CREATE UP TO 5 ORDERS WHEN BUY CONDITIONS WERE MET BUT GOT NO MORE FILLS.
// IN TOTAL THE ALGO CREATED 11 BUY ORDERS, GOT 2 FULLY FILLED & 1 PARTIAL FILL, CANCELED 9 ORDERS
// THE CURRENT STATE HAS 2 ACTIVE ORDERS REMAINING  AND 9 CANCELLED.


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

        // Get the best bid and ask prices at level 0 and their corresponding quantities
        final long bestBidPrice = bestBid.getPrice();
        final long bestAskPrice = bestAsk.getPrice();
        final long bestBidQuantity = bestBid.getQuantity();
        final long bestAskQuantity = bestAsk.getQuantity();


        // TO CREATE CHILD ORDERS
        // If there are less than 5 active child orders, and the spread has narrowed to the defined threshold, create up to 5 buy limit orders at the best BID price and defined buy quantity

        // Retrieve the list of active child orders
        final var activeChildOrders = state.getActiveChildOrders();

        if (activeChildOrders.size() < 5) {

            // Define the spread threshold (in price points)
            final long spread = Math.abs(bestAskPrice - bestBidPrice); // added 5/10/2024 to get absolute value of a number
            final long spreadThreshold = 5L;

            // Define the buy quantity
            final long buyQuantity = 200L;            

            if (spread <= spreadThreshold) {
                    logger.info("[MYALGO] BUY CONDITIONS - Spread is " + spread + " points.");
                    logger.info("[MYALGO] BUY CONDITIONS - Best Bid Qty " + bestBidQuantity + " units, " + "BestBid " + bestBidPrice);
                    logger.info("[MYALGO] BUY CONDITIONS - Best Ask Qty " + bestAskQuantity + " units, " + "BestAsk " + bestAskPrice);
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

        long theOrderId;
        Side theOrderSide;        
        long theOrderQuantity;        
        long theOrderPrice;
        long theOrderFilledQuantity;
        long theOrderUnfilledQuantity;   

        // amended code - now cancelling partially filled or unfilled orders
        if (!activeChildOrders.isEmpty()) {                      

            for (ChildOrder theOrder : activeChildOrders) {

                if (theOrder.getSide() == Side.BUY) {
                    theOrderId = theOrder.getOrderId();
                    theOrderSide = theOrder.getSide();
                    theOrderQuantity = theOrder.getQuantity();                    
                    theOrderPrice = theOrder.getPrice();
                    theOrderFilledQuantity = theOrder.getFilledQuantity();               

                    // Define a price reversal threshold (in price points)            
                    final long priceReversalThreshold = 7L;  // was 10L

                    // Check if the order is partially filled or not filled at all and whether Best ask price is above the defined threshold
                    if ((bestAskPrice >= (theOrderPrice + priceReversalThreshold)) && (theOrderFilledQuantity < theOrderQuantity)){

                        // Calculate the remaining unfilled quantity
                        theOrderUnfilledQuantity = theOrderQuantity - theOrderFilledQuantity;

                            // Log the details of the unfilled or partially filled orders
                            logger.info("[MYALGO] CANCEL CONDITIONS - Partially filled or Non filled order found. " +
                                        " Order ID: " + theOrderId +
                                        " Side: " + theOrderSide +
                                        ", Ordered Qty: " + theOrderQuantity +
                                        ", Price: " + theOrderPrice +                                            
                                        ", Filled Qty: " + theOrderFilledQuantity  +
                                        ", Unfilled Qty: " + theOrderUnfilledQuantity);

                            // if the ASK price moves up by or above the defined threshold, cancel the unfilled part of the order 
                            logger.info("[MYALGO] CANCEL CONDITIONS - BesAsk is: " + bestAskPrice + " theOrderPrice is  : " + theOrderPrice);
                            logger.info("[MYALGO] CANCEL CONDITIONS - price reversal threshold is " + priceReversalThreshold + " points.");
                            logger.info("[MYALGO] CANCEL CONDITIONS - Ask price moved against buy order. " +
                                        " Cancelling order ID: " + theOrderId +
                                        ", Unfilled Qty: " + (theOrderUnfilledQuantity));

                        return new CancelChildOrder(theOrder);
                        
                    }
                    else {
                        // If there are no unfilled quantities OR ask price has not moved beyond the threshold, log that the order remains active
                        logger.info("[MYALGO] CANCEL CONDITIONS  - there are no unfilled quantities or Ask price is stil under threshold. " + 
                                    "Buy order to remain active");
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
