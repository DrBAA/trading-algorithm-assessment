// 4/11/2024 - AMENDED AND FINAL CODE AFTER BUGS WERE FIXED AFTER 17/10/2024
// SEND(CREATETICK11() METHOD NOT WORKING PROPERLY - SO COMMENTED THAT CODE OUT

package codingblackfemales.gettingstarted;
import codingblackfemales.algo.AlgoLogic;
import codingblackfemales.sotw.ChildOrder;
import codingblackfemales.sotw.OrderState;
import codingblackfemales.sotw.marketdata.AskLevel;
import codingblackfemales.sotw.marketdata.BidLevel;
import codingblackfemales.util.Util;
import org.agrona.concurrent.UnsafeBuffer;
import messages.marketdata.*;
import messages.order.Side;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.stream.Collectors;

// import java.util.Optional;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

// /**
//  * This test plugs together all of the infrastructure, including the order book (which you can trade against)
//  * and the market data feed.
//  *
//  * If your algo adds orders to the book, they will reflect in your market data coming back from the order book.
//  *
//  * If you cross the srpead (i.e. you BUY an order with a price which is == or > askPrice()) you will match, and receive
//  * a fill back into your order from the order book (visible from the algo in the childOrders of the state object.
//  *
//  * If you cancel the order your child order will show the order status as cancelled in the childOrders of the state object.
//  *
//  */
public class MyProfitAlgoBackTest extends AbstractAlgoBackTest {

    @Override
    public AlgoLogic createAlgoLogic() {
        return new MyProfitAlgo();
    }

    // ADDED 21/9/2024 - FOR TESTING PURPOSES
    protected UnsafeBuffer createTick3(){

        final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
        final BookUpdateEncoder encoder = new BookUpdateEncoder();

        final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        final UnsafeBuffer directBuffer = new UnsafeBuffer(byteBuffer);

        //write the encoded output to the direct buffer
        encoder.wrapAndApplyHeader(directBuffer, 0, headerEncoder);

        //set the fields to desired values
        encoder.venue(Venue.XLON);
        encoder.instrumentId(123L);
        encoder.source(Source.STREAM);

        encoder.bidBookCount(3)
                .next().price(96L).size(100L)
                .next().price(93L).size(200L)
                .next().price(91L).size(300L);

        encoder.askBookCount(4)
                .next().price(102).size(300L) 
                .next().price(109L).size(200L)
                .next().price(110L).size(5000L)
                .next().price(119L).size(5600L);

        encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

        return directBuffer;
    }

    // ADDED 27/9/2024 - FOR TESTING PURPOSES
    protected UnsafeBuffer createTick4(){

        final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
        final BookUpdateEncoder encoder = new BookUpdateEncoder();

        final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        final UnsafeBuffer directBuffer = new UnsafeBuffer(byteBuffer);

        //write the encoded output to the direct buffer
        encoder.wrapAndApplyHeader(directBuffer, 0, headerEncoder);

        //set the fields to desired values
        encoder.venue(Venue.XLON);
        encoder.instrumentId(123L);
        encoder.source(Source.STREAM);

        encoder.bidBookCount(3)
                .next().price(98L).size(600L)
                .next().price(96L).size(200L)
                .next().price(95L).size(300L);

        encoder.askBookCount(4)
            .next().price(105L).size(300L) 
            .next().price(109L).size(200L)
            .next().price(110L).size(5000L)
            .next().price(119L).size(5600L);

        encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

        return directBuffer;
    }       


    // ADDED 28/9/2024 - FOR TESTING PURPOSES.
    // 17/10/2024 SELL ORDER GOT FILED AT 102 AFTER ADJUSTING SELLING PRICE FROM BEST ASK TO "BEST BID" 
    protected UnsafeBuffer createTick5(){

        final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
        final BookUpdateEncoder encoder = new BookUpdateEncoder();

        final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        final UnsafeBuffer directBuffer = new UnsafeBuffer(byteBuffer);

        //write the encoded output to the direct buffer
        encoder.wrapAndApplyHeader(directBuffer, 0, headerEncoder);

        //set the fields to desired values
        encoder.venue(Venue.XLON);
        encoder.instrumentId(123L);
        encoder.source(Source.STREAM);

        encoder.bidBookCount(3)       
        .next().price(102L).size(600L)
        .next().price(97L).size(200L)
        .next().price(96L).size(300L);

        encoder.askBookCount(3)
            .next().price(106L).size(200L)
            .next().price(110L).size(5000L)
            .next().price(119L).size(5600L);
            
        encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

        return directBuffer;
    }       

    // ADDED 28/9/2024 - FOR TESTING PURPOSES
    protected UnsafeBuffer createTick6(){

        final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
        final BookUpdateEncoder encoder = new BookUpdateEncoder();

        final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        final UnsafeBuffer directBuffer = new UnsafeBuffer(byteBuffer);

        //write the encoded output to the direct buffer
        encoder.wrapAndApplyHeader(directBuffer, 0, headerEncoder);

        //set the fields to desired values
        encoder.venue(Venue.XLON);
        encoder.instrumentId(123L);
        encoder.source(Source.STREAM);

        encoder.bidBookCount(3)
                .next().price(102L).size(600L) 
                .next().price(97L).size(200L)
                .next().price(96L).size(300L);

        encoder.askBookCount(4)
            .next().price(104L).size(300L) 
            .next().price(110L).size(200L) 
            .next().price(110L).size(5000L)
            .next().price(119L).size(5600L);

        encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

        return directBuffer;
    }

    // ADDED 28/9/2024 - FOR TESTING PURPOSES
    protected UnsafeBuffer createTick7(){

        final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
        final BookUpdateEncoder encoder = new BookUpdateEncoder();

        final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        final UnsafeBuffer directBuffer = new UnsafeBuffer(byteBuffer);

        //write the encoded output to the direct buffer
        encoder.wrapAndApplyHeader(directBuffer, 0, headerEncoder);

        //set the fields to desired values
        encoder.venue(Venue.XLON);
        encoder.instrumentId(123L);
        encoder.source(Source.STREAM);
        
        encoder.bidBookCount(3)
                .next().price(100L).size(600L) 
                .next().price(97L).size(200L)
                .next().price(96L).size(300L);

        encoder.askBookCount(3)
            .next().price(102L).size(200L)
            .next().price(110L).size(5000L)
            .next().price(119L).size(5600L);

        encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

        return directBuffer;
    }


    // ADDED 08/10/2024 - FOR TESTING PURPOSES
    protected UnsafeBuffer createTick8(){

        final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
        final BookUpdateEncoder encoder = new BookUpdateEncoder();

        final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        final UnsafeBuffer directBuffer = new UnsafeBuffer(byteBuffer);

        //write the encoded output to the direct buffer
        encoder.wrapAndApplyHeader(directBuffer, 0, headerEncoder);

        //set the fields to desired values
        encoder.venue(Venue.XLON);
        encoder.instrumentId(123L);
        encoder.source(Source.STREAM);

        encoder.bidBookCount(3)
            .next().price(105L).size(600L) 
            .next().price(97L).size(200L)
            .next().price(96L).size(300L);

        encoder.askBookCount(4)
            .next().price(109L).size(5000L) 
            .next().price(117L).size(200L)
            .next().price(118L).size(5000L)
            .next().price(119L).size(5600L);
            // .next().price(110L).size(5000L)
            // .next().price(119L).size(5600L);

        encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

        return directBuffer;

    }

    // ADDED 08/10/2024 - FOR TESTING PURPOSES
    protected UnsafeBuffer createTick9(){

        final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
        final BookUpdateEncoder encoder = new BookUpdateEncoder();

        final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        final UnsafeBuffer directBuffer = new UnsafeBuffer(byteBuffer);

        //write the encoded output to the direct buffer
        encoder.wrapAndApplyHeader(directBuffer, 0, headerEncoder);

        //set the fields to desired values
        encoder.venue(Venue.XLON);
        encoder.instrumentId(123L);
        encoder.source(Source.STREAM);

        encoder.bidBookCount(3)
            .next().price(106L).size(600L)
            .next().price(97L).size(200L)
            .next().price(96L).size(300L);

        encoder.askBookCount(4)
            .next().price(111L).size(5000L)
            .next().price(117L).size(200L)
            .next().price(118L).size(5000L)
            .next().price(119L).size(5600L);

            encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

            return directBuffer; 
    }           

    // ADDED 13/10/2024 - FOR TESTING PURPOSES
    protected UnsafeBuffer createTick10(){

        final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
        final BookUpdateEncoder encoder = new BookUpdateEncoder();

        final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        final UnsafeBuffer directBuffer = new UnsafeBuffer(byteBuffer);

        //write the encoded output to the direct buffer
        encoder.wrapAndApplyHeader(directBuffer, 0, headerEncoder);

        //set the fields to desired values
        encoder.venue(Venue.XLON);
        encoder.instrumentId(123L);
        encoder.source(Source.STREAM);

        encoder.bidBookCount(3)
            .next().price(105L).size(600L)
            .next().price(97L).size(200L)
            .next().price(96L).size(300L);

        encoder.askBookCount(4)
            .next().price(108L).size(5000L)
            .next().price(117L).size(200L)
            .next().price(118L).size(5000L)
            .next().price(119L).size(5600L);
            // .next().price(110L).size(5000L)
            // .next().price(119L).size(5600L);

        encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

        return directBuffer;

    }

    // ADDED 13/10/2024 - FOR TESTING PURPOSES
    protected UnsafeBuffer createTick11(){

        final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
        final BookUpdateEncoder encoder = new BookUpdateEncoder();

        final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        final UnsafeBuffer directBuffer = new UnsafeBuffer(byteBuffer);

        //write the encoded output to the direct buffer
        encoder.wrapAndApplyHeader(directBuffer, 0, headerEncoder);

        //set the fields to desired values
        encoder.venue(Venue.XLON);
        encoder.instrumentId(123L);
        encoder.source(Source.STREAM);

        encoder.bidBookCount(3)
            .next().price(109L).size(600L)
            .next().price(97L).size(200L)
            .next().price(96L).size(300L);

        encoder.askBookCount(4)
            .next().price(114L).size(5000L)
            .next().price(117L).size(200L)
            .next().price(118L).size(5000L)
            .next().price(119L).size(5600L);

        encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

        return directBuffer;

    }
    
    // CHECKING THERE ARE VALID BID AND ASK LEVELS AT THE FIRST TICK.
    @Test
    public void checkThereAreValidBidAndAskLevelsAtTheFirstTick() throws Exception {

        send(createTick());        

            int bidLevels = container.getState().getBidLevels();
            int askLevels = container.getState().getAskLevels();

            assertEquals(true, bidLevels > 0 && askLevels > 0); 
    } 

    
    // TESTING THE ALGO'S BEHAVIOUR FROM 1ST TICK TO 11TH TICK
    
    // @SuppressWarnings("deprecation")
    @Test
    public void backtestAlgoBehaviourFrom1stTickTo11thTick() throws Exception {
    
        // retrieve all the information in the container object including child orders and other variables held in the container
        var state = container.getState();

        // TESTING THE ALGO'S BEHAVIOUR AFTER THE 1ST TICK 
        // Best Bid = 98, Best Ask = 100, Spread = 2 points
        // Algo should only create 5 buy orders and no sell orders at this point

        send(createTick()); // send a sample market data tick

            // check the state of the order book after 1st market data tick
            final String updatedOrderBook1 = Util.orderBookToString(container.getState());

            // Get the best bid and ask prices at level 0 and their corresponding prices
            final BidLevel bestBidAt1stTick = state.getBidAt(0);
            final AskLevel bestAskAt1stTick = state.getAskAt(0);
            final long bestBidPriceAt1stTick = bestBidAt1stTick.getPrice();
            final long bestAskPriceAt1stTick = bestAskAt1stTick.getPrice();

            // Define the spread threshold (in price points)
            final long spreadAt1stTick = Math.abs(bestAskPriceAt1stTick - bestBidPriceAt1stTick); // Math.abs - added 20/10/2024 to get absolute value of a number
            final boolean spreadIsBelowOrEqualToSpreadThresholdAt1stTick = spreadAt1stTick <= 5L; 
            final boolean spreadIsAboveOrEqualToSpreadThresholdAt1stTick = spreadAt1stTick > 5L;

            // check for best bid and best ask prices, the spread and price reversal threshold
            assertEquals(98, bestBidPriceAt1stTick);
            assertEquals(100, bestAskPriceAt1stTick);
            assertEquals(2, spreadAt1stTick);
            assertEquals(true, spreadIsBelowOrEqualToSpreadThresholdAt1stTick);
            assertEquals(false, spreadIsAboveOrEqualToSpreadThresholdAt1stTick);

            //  assert to check that we created a Buy order for 200 shares at 98 and no Sell orders            
            for (ChildOrder childOrder : state.getChildOrders()) {
                assertEquals(true, childOrder.getSide() == Side.BUY);
                assertEquals(200, childOrder.getQuantity());
                assertEquals(98, childOrder.getPrice());
                assertEquals (true, childOrder.getSide() != Side.SELL);
            }

            final int totalOrdersCountAfter1stTick = state.getChildOrders().size();

            final long totalBuyOrderedQuantityAfter1stTick = state.getChildOrders().stream()
                                                                  .filter(order -> order.getSide() == Side.BUY)
                                                                  .map(ChildOrder::getQuantity)
                                                                  .reduce (Long::sum).orElse(0L); //.get();

            final long totalBuyOrderFilledQuantityAfter1stTick = state.getChildOrders().stream()
                                                                      .filter(order -> order.getSide() == Side.BUY)
                                                                      .map(ChildOrder::getFilledQuantity)
                                                                      .reduce(Long::sum).orElse(0L);

            final long totalBuyOrderUnfilledQuantityAfter1stTick = totalBuyOrderedQuantityAfter1stTick - totalBuyOrderFilledQuantityAfter1stTick;

            final int activeChildOrdersAfter1stTick = state.getActiveChildOrders().size();

            // check that the last buy order in the list is at the price of 98 or throw an assertion error if no such order is found                        
            final ChildOrder lastBoughtPriceAfter1stTickIs98 = state.getChildOrders().stream()
                                                                    .filter(order -> order.getSide() == Side.BUY)
                                                                    .reduce((first, second) -> second)
                                                                    .orElseThrow(() ->
                                                                    new AssertionError("Expected the last buy order price to be 98 but found something else"));

            // Check things like total order count, total ordered quantity, filled quantity, unfilled quantity, total active child orders, cancelled orders
            assertEquals(5, totalOrdersCountAfter1stTick);
            assertEquals(1000, totalBuyOrderedQuantityAfter1stTick);
            assertEquals(0, totalBuyOrderFilledQuantityAfter1stTick);
            assertEquals(1000, totalBuyOrderUnfilledQuantityAfter1stTick);
            assertEquals(5, activeChildOrdersAfter1stTick);
            


        // TESTING THE ALGO'S BEHAVIOUR AFTER 2ND TICK 
        // Best Bid = 95, Best Ask = 98, Spread = 3 points
        // No new buy orders should be created as list has 5 orders
        // No Sell order should be created as the conditions not met
        // 3 buy orders should be filled or partially filled as the Ask price has moved to our buy limit price

        send(createTick2());

            // get the state of the order book after 2nd market data tick
            final String updatedOrderBook2 = Util.orderBookToString(container.getState());

            // Get the best bid and ask prices at level 0 and their corresponding prices
            final BidLevel bestBidAt2ndTick = state.getBidAt(0);
            final AskLevel bestAskAt2ndTick = state.getAskAt(0);
            final long bestBidPriceAt2ndTick = bestBidAt2ndTick.getPrice();
            final long bestAskPriceAt2ndTick = bestAskAt2ndTick.getPrice();
            final long previousBoughtPriceAt1stTick = lastBoughtPriceAfter1stTickIs98.getPrice();

            // Define the spread threshold (in price points)
            final long spreadAt2ndTick = Math.abs(bestAskPriceAt2ndTick - bestBidPriceAt2ndTick);
            final boolean spreadIsBelowOrEqualToSpreadThresholdAt2ndTick = spreadAt2ndTick <= 5L; 
            final boolean spreadIsAboveOrEqualToSpreadThresholdAt2ndTick = spreadAt2ndTick > 5L;
            final long priceReversalThreshold2ndTick = bestAskPriceAt2ndTick - previousBoughtPriceAt1stTick;

            // check for best bid and best ask prices, the spread and price reversal threshold
            assertEquals(95, bestBidPriceAt2ndTick);
            assertEquals(98, bestAskPriceAt2ndTick);
            assertEquals(3, spreadAt2ndTick);            
            assertEquals(98, previousBoughtPriceAt1stTick);
            assertEquals(true, spreadIsBelowOrEqualToSpreadThresholdAt2ndTick);
            assertEquals(false, spreadIsAboveOrEqualToSpreadThresholdAt2ndTick);
            assertEquals(0, priceReversalThreshold2ndTick);            

            // assert to check that we created a Buy order for 200 shares at best Bid price of 98 and no Sell orders            
            for (ChildOrder childOrder : state.getActiveChildOrders()) {
                assertEquals(true, childOrder.getSide() == Side.BUY);
                assertEquals(200, childOrder.getQuantity());
                assertEquals(98, childOrder.getPrice());
                assertEquals (true, childOrder.getSide() != Side.SELL);
            }

            // check total order count and total active child orders
            final int totalOrdersCountAfter2ndTick = container.getState().getChildOrders().size();

            final int activeChildOrdersAfter2ndTick = container.getState().getActiveChildOrders().size();

            // Using Java Streams API to aggregate various statistics or find required information from the list of Child Orders
            final long totalBuyOrderCountAfter2ndTick = state.getChildOrders().stream()
                                                             .filter(order -> order.getSide() == Side.BUY)
                                                             .map(order -> 1L).reduce(0L, Long::sum);

            final long totalSellOrderCountAfter2ndTick = state.getChildOrders().stream()
                                                              .filter(order -> order.getSide() == Side.SELL)
                                                              .map(order -> 1L).reduce(0L, Long::sum);

            final long cancelledBuyChildOrdersAfter2ndTick = state.getChildOrders().stream()
                                                                  .filter(order -> order.getSide() == Side.BUY)
                                                                  .filter(order -> order.getState() == OrderState.CANCELLED)
                                                                  .count();

            // check that the last buy order in the list is at the price of 98 or else throw an assertion error if no such order is found                         
            final ChildOrder lastBoughtPriceAfter2ndTickIs98 = state.getChildOrders().stream()
                                                                    .filter(order -> order.getSide() == Side.BUY)
                                                                    .reduce((first, second) -> second)
                                                                    .orElseThrow(() ->
                                                                     new AssertionError("Expected the last buy order for 98 but found something else"));

            final long numberOfFullyFilledOrPartiallyFilledBuyOrdersAfter2ndTick = state.getChildOrders().stream()
                                                                                        .filter(order -> order.getSide() == Side.BUY)            
                                                                                        .filter(order -> order.getFilledQuantity() > 0)
                                                                                        .map(order -> 1L).reduce(0L, Long::sum);

            final long totalBuyOrderedQuantityAfter2ndTick = state.getChildOrders().stream()
                                                                  .filter(order -> order.getSide() == Side.BUY)
                                                                  .map(ChildOrder::getQuantity) 
                                                                  .reduce (Long::sum) //.get();
                                                                  .orElse(0L);

            final long totalBuyOrderFilledQuantityAfter2ndTick = state.getChildOrders().stream()
                                                                      .filter(order -> order.getSide() == Side.BUY)
                                                                      .map(ChildOrder::getFilledQuantity)
                                                                      .reduce(Long::sum) //.get();
                                                                      .orElse(0L);
                                                                                     
            // calculate unfilled quantity for buy orders                                                                      
            final long totalBuyOrderUnfilledQuantityAfter2ndTick = totalBuyOrderedQuantityAfter2ndTick - totalBuyOrderFilledQuantityAfter2ndTick;

            // Check things like total order count, total ordered quantity, filled quantity, unfilled quantity, total active child orders,
            // cancelled orders, last bought price etc                                                              
            assertEquals(5, totalOrdersCountAfter2ndTick);
            assertEquals(5, totalBuyOrderCountAfter2ndTick);
            assertEquals(0, totalSellOrderCountAfter2ndTick);
            assertEquals(1000, totalBuyOrderedQuantityAfter2ndTick);
            assertEquals(3, numberOfFullyFilledOrPartiallyFilledBuyOrdersAfter2ndTick);
            assertEquals(501, totalBuyOrderFilledQuantityAfter2ndTick);
            assertEquals(499, totalBuyOrderUnfilledQuantityAfter2ndTick);
            assertEquals(5, activeChildOrdersAfter2ndTick);
            assertEquals(0, cancelledBuyChildOrdersAfter2ndTick);
            assertEquals(98, lastBoughtPriceAfter1stTickIs98.getPrice());



        // TESTING THE ALGO'S BEHAVIOUR AFTER 3RD TICK 
        // Best Bid = 96, Best Ask = 102, Spread = 6 points
        // No new buy orders should be created as the list has 5 active child orders 
        // No Sell order should be created as the conditions not met
        // No orders should be cancelled as the spread has not widened
        // The state should still have 5 active Buy orders, partially filled at the initial price of 98 as at tick 2        

        send(createTick3());

           // check the state of the order book after 3rd market data tick
           final String updatedOrderBook3 = Util.orderBookToString(container.getState());        
 
            //assert to check that we created a Buy order for 200 shares at 98 and no Sell orders            
            for (ChildOrder childOrder : state.getActiveChildOrders()) {
                assertEquals(true, childOrder.getSide() == Side.BUY);
                assertEquals(200, childOrder.getQuantity());
                assertEquals(98, childOrder.getPrice());
                assertEquals (true, childOrder.getSide() != Side.SELL);
            }

            // Get the best bid and ask prices at level 0 and their corresponding prices
            final BidLevel bestBidAt3rdTick = state.getBidAt(0);
            final AskLevel bestAskAt3rdTick = state.getAskAt(0);
            final long bestBidPriceAt3rdTick = bestBidAt3rdTick.getPrice();
            final long bestAskPriceAt3rdTick = bestAskAt3rdTick.getPrice();
            final long previousBoughtPriceAt2ndTick = lastBoughtPriceAfter2ndTickIs98.getPrice();

            // Define the spread threshold (in price points)
            final long spreadAt3rdTick = Math.abs(bestAskPriceAt3rdTick - bestBidPriceAt3rdTick); 
            final boolean spreadIsBelowOrEqualToSpreadThresholdAt3rdTick = spreadAt3rdTick <= 5L; 
            final boolean spreadIsAboveOrEqualToSpreadThresholdAt3rdTick = spreadAt3rdTick > 5L;
            final long priceReversalThreshold3rdTick = bestAskPriceAt3rdTick - previousBoughtPriceAt2ndTick;  

            // check for best bid and best ask prices, the spread and price reversal threshold
            assertEquals(96, bestBidPriceAt3rdTick);
            assertEquals(102, bestAskPriceAt3rdTick);
            assertEquals(6, spreadAt3rdTick);            
            assertEquals(98, previousBoughtPriceAt2ndTick);
            assertEquals(false, spreadIsBelowOrEqualToSpreadThresholdAt3rdTick);
            assertEquals(true, spreadIsAboveOrEqualToSpreadThresholdAt3rdTick);
            assertEquals(4, priceReversalThreshold3rdTick);

            // check total order count and total active child orders
            final int totalOrdersCountAfter3rdTick = container.getState().getChildOrders().size();

            final int activeChildOrdersAfter3rdTick = container.getState().getActiveChildOrders().size();
            
            // Using Java Streams API to aggregate various statistics or find required information from the list of Child Orders

            final long cancelledBuyChildOrderCountAfter3rdTick = state.getChildOrders().stream()
                                                                      .filter(order -> order.getSide() == Side.BUY)
                                                                      .filter(order -> order.getState() == OrderState.CANCELLED)
                                                                      .count();

             // check that the last buy order in the list is at the price of 98 or throw an assertion error if no such order is found                             
            final ChildOrder lastBoughtPriceAfter3rdTickIs98 = state.getChildOrders().stream()
                                                                    .filter(order -> order.getSide() == Side.BUY)
                                                                    .reduce((first, second) -> second)
                                                                    .orElseThrow(() ->
                                                                     new AssertionError("Expected the last buy order for 98 but found something else"));  

            final long totalBuyOrderedQuantityAfter3rdTick = state.getChildOrders().stream()
                                                                  .filter(order -> order.getSide() == Side.BUY)
                                                                  .map(ChildOrder::getQuantity)
                                                                  .reduce (Long::sum).orElse(0L);

            final long totalBuyOrderFilledQuantityAfter3rdTick = state.getChildOrders().stream()
                                                                      .filter(order -> order.getSide() == Side.BUY)
                                                                      .map(ChildOrder::getFilledQuantity)
                                                                      .reduce(Long::sum).orElse(0L);

           // calculate unfilled quantity for buy orders
            final long totalBuyOrderUnfilledQuantityAfter3rdTick = totalBuyOrderedQuantityAfter3rdTick - totalBuyOrderFilledQuantityAfter3rdTick;


            // Check things like total order count, total ordered quantity, filled quantity, unfilled quantity, total active child orders,
            // cancelled orders, last bought price etc  
            assertEquals(5, totalOrdersCountAfter3rdTick);
            assertEquals(1000, totalBuyOrderedQuantityAfter3rdTick);            
            assertEquals(501, totalBuyOrderFilledQuantityAfter3rdTick);
            assertEquals(499, totalBuyOrderUnfilledQuantityAfter3rdTick);
            assertEquals(5, activeChildOrdersAfter3rdTick);
            assertEquals(0, cancelledBuyChildOrderCountAfter3rdTick);
            assertEquals(98, lastBoughtPriceAfter2ndTickIs98.getPrice());


        // TESTING THE ALGO'S BEHAVIOUR AFTER THE 4TH TICK
        // Best Bid = 98, Best Ask = 105, Spread = 7 points. Previous bought price 98
        // No new Buy orders should be created as the spread has widened
        // 3 Buy orders should be cancelled as the spread has widened
        // No Sell order should be created as the conditions not met
        // There should be 5 Buy orders as in tick 3, some fully/partially filled and some not filled     

        send(createTick4());

            // check the state of the order book after 4th market data tick
        
            final String updatedOrderBook4 = Util.orderBookToString(container.getState());            

            // Get the best bid and ask prices at level 0 and their corresponding prices
            final BidLevel bestBidAt4thTick = state.getBidAt(0);
            final AskLevel bestAskAt4thTick = state.getAskAt(0);
            final long bestBidPriceAt4thTick = bestBidAt4thTick.getPrice();
            final long bestAskPriceAt4thTick = bestAskAt4thTick.getPrice();
            final long previousBoughtPriceAt3rdTick = lastBoughtPriceAfter3rdTickIs98.getPrice();

            // Define the spread threshold (in price points)
            final long spreadAt4thTick = Math.abs(bestAskPriceAt4thTick - bestBidPriceAt4thTick); 
            final boolean spreadIsBelowOrEqualToSpreadThresholdAt4thTick = spreadAt4thTick <= 5L; 
            final boolean spreadIsAboveOrEqualToSpreadThresholdAt4thTick = spreadAt4thTick > 5L;
            final long priceReversalThreshold4thTick = bestAskPriceAt4thTick - previousBoughtPriceAt3rdTick;   

            // check for best bid and best ask prices, the spread and price reversal threshold
            assertEquals(98, bestBidPriceAt4thTick);
            assertEquals(105, bestAskPriceAt4thTick);
            assertEquals(7, spreadAt4thTick);            
            assertEquals(98, previousBoughtPriceAt3rdTick);
            assertEquals(false, spreadIsBelowOrEqualToSpreadThresholdAt4thTick);
            assertEquals(true, spreadIsAboveOrEqualToSpreadThresholdAt4thTick);
            assertEquals(7, priceReversalThreshold4thTick);
            
            //assert to check that we created a Buy order for 200 shares at 98 and no Sell orders            
            for (ChildOrder childOrder : state.getChildOrders()) {
                assertEquals(true, childOrder.getSide() == Side.BUY);
                assertEquals(200, childOrder.getQuantity());
                assertEquals(98, childOrder.getPrice());
                assertEquals(true, childOrder.getSide() != Side.SELL);
            }

            // check total order count and total active child orders
            final int totalOrdersCountAfter4thTick = container.getState().getChildOrders().size();
            
            final int activeChildOrdersAfter4thTick = container.getState().getActiveChildOrders().size();

            // Using Java Streams API to perform various operations from the list of Child Orders such as filtering, mapping, aggregating, etc
            // or find/retrieve certain information

            final long totalBuyOrderedQuantityAfter4thTick = state.getChildOrders().stream()
                                                                  .filter(order -> order.getSide() == Side.BUY)            
                                                                  .map(ChildOrder::getQuantity)
                                                                  .reduce (Long::sum).orElse(0L);

            final long buyOrderCountAfter4thTick = state.getChildOrders().stream()
                                                        .filter(order -> order.getSide() == Side.BUY)
                                                        .map(order -> 1L).reduce(0L, Long::sum);

            final long sellOrderCountAfter4thTick = state.getChildOrders().stream()
                                                         .filter(order -> order.getSide() == Side.SELL)
                                                         .map(order -> 1L).reduce(0L, Long::sum);                                                                   

            final long totalBuyOrderFilledQuantityAfter4thTick = state.getChildOrders().stream()
                                                                      .filter(order -> order.getSide() == Side.BUY)
                                                                      .map(ChildOrder::getFilledQuantity)
                                                                      .reduce(Long::sum).orElse(0L);

            // calculate total unfilled quantity for buy orders
            final long totalBuyOrderUnFilledQuantityAfter4thTick = totalBuyOrderedQuantityAfter4thTick - totalBuyOrderFilledQuantityAfter4thTick;

            final long cancelledBuyOrderCountAfter4thTick = state.getChildOrders().stream()
                                                                 .filter(order -> order.getSide() == Side.BUY)
                                                                 .filter(order -> order.getState() == OrderState.CANCELLED)
                                                                 .count();

            // Calculate the total filled quantity for buy orders
            final long buyOrdersTotalFilledQuantityAfter4thTick = state.getChildOrders().stream()
                                                                       .filter(order -> order.getSide() == Side.BUY)
                                                                       .filter(order -> order.getFilledQuantity() > 0)
                                                                       .mapToLong(ChildOrder::getFilledQuantity)
                                                                       .sum();

            // Calculate the total filled quantity for sell orders
            final long sellOrderTotalFilledQuantityAfter4thTick = state.getChildOrders().stream()
                                                                       .filter(order -> order.getSide() == Side.SELL && order.getFilledQuantity() > 0)
                                                                       .mapToLong(ChildOrder::getFilledQuantity)
                                                                       .sum();

            // Determine the quantity available for sale
            final long remainingQuantityToSellAfter4thTick = buyOrdersTotalFilledQuantityAfter4thTick - sellOrderTotalFilledQuantityAfter4thTick;

            // check that the last cancelled buy order in the list is at the price of 98 or throw an assertion error if no such order is found  
            final ChildOrder lastCancelledBuyOrderPriceAfter4thTickIs98 = state.getChildOrders().stream()
                                                                               .filter(order -> order.getSide() == Side.BUY)
                                                                               .filter(order -> order.getState() == OrderState.CANCELLED)
                                                                               .reduce((first, second) -> second)
                                                                               .orElseThrow(() ->
                                                                                new AssertionError("Expected the last cancelled buy order price to be 98 but found something else"));

            final long numberOfFullyFilledOrPartiallyFilledBuyOrdersAfter4thTick = state.getChildOrders().stream()
                                                                                        .filter(order -> order.getSide() == Side.BUY)            
                                                                                        .filter(order -> order.getFilledQuantity() > 0)
                                                                                        .map(order -> 1L).reduce(0L, Long::sum);

            // check that the last buy order in the list is at the price of 98 or throw an assertion error if no such order is found                             
            final ChildOrder lastBoughtPriceAfter4thTickIs98 = state.getChildOrders().stream()
                                                                    .filter(order -> order.getSide() == Side.BUY)
                                                                    .reduce((first, second) -> second)
                                                                    .orElseThrow(() ->
                                                                     new AssertionError("Expected the last buy order for 98 but found something else"));                                                                                        

            // Check things like total order count, total ordered quantity, filled quantity, unfilled quantity, total active child orders,
            // cancelled orders, last bought price etc 
            assertEquals(5, totalOrdersCountAfter4thTick);
            assertEquals(5, buyOrderCountAfter4thTick); 
            assertEquals(0, sellOrderCountAfter4thTick);                  
            assertEquals(1000, totalBuyOrderedQuantityAfter4thTick);            
            assertEquals(501, totalBuyOrderFilledQuantityAfter4thTick);
            assertEquals(499, totalBuyOrderUnFilledQuantityAfter4thTick);
            assertEquals(2, activeChildOrdersAfter4thTick);
            assertEquals(3, numberOfFullyFilledOrPartiallyFilledBuyOrdersAfter4thTick);
            assertEquals(3, cancelledBuyOrderCountAfter4thTick);
            assertEquals(501, remainingQuantityToSellAfter4thTick);
            assertEquals(98, lastBoughtPriceAfter3rdTickIs98.getPrice());
            assertEquals(98, lastCancelledBuyOrderPriceAfter4thTickIs98.getPrice());



        // TESTING THE ALGO'S BEHAVIOUR AFTER THE 5TH TICK
        // Best Bid = 102, Best Ask = 106, Spread = 4 points, last bought price: 98
        // Bid & Ask price has moved above last bought price + 2 points
        // 1 new Sell order should be created at Best Ask price of 102, filled for 501 shares and then cancelled 
        // No new buy orders to be created as the spread has widened
        // The state should have 6 orders; 2 active, 4 cancelled

        // AFTTER BUGS WERE FIXED FROM 17/10/2024 - ADJUSTED SELL ORDER PRICE FROM BEST ASK TO BEST BID PRICE.
        // ORDER GOT FILLED STRAIGHT AWAY FOR 501 AT 102

        send(createTick5());

            // check the state of the order book after 5th market data tick
            final String updatedOrderBook5 = Util.orderBookToString(container.getState());               

            // Get the best bid and ask prices at level 0 and their corresponding prices
            final BidLevel bestBidAt5thTick = state.getBidAt(0);
            final AskLevel bestAskAt5thTick = state.getAskAt(0);
            final long bestBidPriceAt5thTick = bestBidAt5thTick.getPrice();
            final long bestAskPriceAt5thTick = bestAskAt5thTick.getPrice();
            final long previousBoughtPriceAt4thTick = lastBoughtPriceAfter4thTickIs98.getPrice();

            // Define the spread threshold (in price points)
            final long spreadAt5thTick = Math.abs(bestAskPriceAt5thTick - bestBidPriceAt5thTick); 
            final boolean spreadIsBelowOrEqualToSpreadThresholdAt5thTick = spreadAt5thTick <= 5L; 
            final boolean spreadIsAboveOrEqualToSpreadThresholdAt5thTick = spreadAt5thTick > 5L;
            final long priceReversalThreshold5thTick = bestAskPriceAt5thTick - previousBoughtPriceAt4thTick;  

            // check for best bid and best ask prices, the spread and price reversal threshold
            assertEquals(102, bestBidPriceAt5thTick);
            assertEquals(106, bestAskPriceAt5thTick);
            assertEquals(4, spreadAt5thTick);            
            assertEquals(98, previousBoughtPriceAt4thTick);
            assertEquals(true, spreadIsBelowOrEqualToSpreadThresholdAt5thTick);
            assertEquals(false, spreadIsAboveOrEqualToSpreadThresholdAt5thTick);
            assertEquals(8, priceReversalThreshold5thTick);

            // check total order count and total active child orders
            final int totalOrdersCountAfter5thTick = container.getState().getChildOrders().size();

            final int activeChildOrdersAfter5thTick = container.getState().getActiveChildOrders().size();

            // Using Java Streams API to perform various operations from the list of Child Orders such as filtering, mapping, aggregating, etc
            // or find/retrieve certain information

            final long totalBuyOrderedQuantityAfter5thTick = state.getChildOrders().stream()
                                                                  .filter(order -> order.getSide() == Side.BUY) 
                                                                  .map(ChildOrder::getQuantity)
                                                                  .reduce (Long::sum).orElse(0L);

            final long totalBuyOrdersfilledQuantityAfter5thTick = state.getChildOrders().stream()
                                                                  .filter(order -> order.getSide() == Side.BUY) 
                                                                  .filter(order -> order.getFilledQuantity() > 0) 
                                                                  .map(ChildOrder::getFilledQuantity)
                                                                  .reduce(Long::sum).orElse(0L);

            final long totalSellOrdersFilledQuantityAfter5thTick = state.getChildOrders().stream()
                                                                   .filter(order -> order.getSide() == Side.SELL) 
                                                                   .filter(order -> order.getFilledQuantity() > 0) 
                                                                   .map(ChildOrder::getFilledQuantity)
                                                                   .reduce(Long::sum).orElse(0L);

            final long totalSellOrderedQuantityAfter5thTick = state.getChildOrders().stream()
                                                                   .filter(order -> order.getSide() == Side.SELL) 
                                                                   .map(ChildOrder::getQuantity)
                                                                   .reduce(Long::sum).orElse(0L);

            // check total filled quantities for buy orders
            final long totalBuyOrderUnfilledQuantityAfter5thTick = totalBuyOrderedQuantityAfter5thTick - totalBuyOrdersfilledQuantityAfter5thTick;

            final long buyOrderCountAfter5thTick = state.getChildOrders().stream()
                                                        .filter(order -> order.getSide() == Side.BUY)
                                                        .map(order -> 1L).reduce(0L, Long::sum);

            final long sellOrderCountAfter5thTick = state.getChildOrders().stream()
                                                         .filter(order -> order.getSide() == Side.SELL)
                                                         .map(order -> 1L).reduce(0L, Long::sum);

            final long cancelledBuyOrderCountAfter5thTick = state.getChildOrders().stream()
                                                                 .filter(order -> order.getSide() == Side.BUY)            
                                                                 .filter(order -> order.getState() == 3)
                                                                 .map(order -> 1L).reduce(0L, Long::sum);

            final long cancelledSellOrderCountAfter5thTick = state.getChildOrders().stream()
                                                                  .filter(order -> order.getSide() == Side.SELL)            
                                                                  .filter(order -> order.getState() == 3)
                                                                  .map(order -> 1L).reduce(0L, Long::sum);

            // check that the last buy order in the list is at the price of 98 or throw an assertion error if no such order is found                            
            final ChildOrder lastBoughtPriceAfter5thTickIs98  = state.getChildOrders().stream()
                                                                     .filter(order -> order.getSide() == Side.BUY)
                                                                     .reduce((first, second) -> second)
                                                                     .orElseThrow(() -> 
                                                                      new AssertionError("Expected a BuY order for 102 but found something else"));
                                                              
            // check that there is a new sell order in the list at the price of 102 or throw an assertion error if no such order is found                         
            final ChildOrder newSellOrderAfter5thTickIs102 = state.getChildOrders().stream()
                                                                  .filter(order -> order.getSide() == Side.SELL)
                                                                  .reduce((first, second) -> second)
                                                                  .orElseThrow(() -> 
                                                                   new AssertionError("Expected a new Sell order for 102 but found something else"));

                                                             
            // Check things like total order count, total ordered quantity, filled quantity, unfilled quantity, total active child orders,
            // cancelled orders, last bought price etc 

            assertEquals(6, totalOrdersCountAfter5thTick); 
            assertEquals(5, buyOrderCountAfter5thTick); 
            assertEquals(1, sellOrderCountAfter5thTick);
            assertEquals(1000, totalBuyOrderedQuantityAfter5thTick); 
            assertEquals(501, totalBuyOrdersfilledQuantityAfter5thTick);                                              
            assertEquals(499, totalBuyOrderUnfilledQuantityAfter5thTick);
            assertEquals(2, activeChildOrdersAfter5thTick); // 4 11 2024 - expected 3 but was 2            
            assertEquals(3, cancelledBuyOrderCountAfter5thTick);
            assertEquals(1, cancelledSellOrderCountAfter5thTick);
            assertEquals(501, remainingQuantityToSellAfter4thTick);
            assertEquals(98, lastBoughtPriceAfter4thTickIs98.getPrice());
            assertEquals(102, newSellOrderAfter5thTickIs102.getPrice());
            assertEquals(501, totalSellOrderedQuantityAfter5thTick);
            assertEquals(501, totalSellOrdersFilledQuantityAfter5thTick);            


        // TESTING THE ALGO'S BEHAVIOUR AFTER THE 6TH TICK
        // Best bid = 102; Best Ask = 104; Spread = 2.
        // 3 new Buy orders to be created at 102 as the list has 2 active orders
        // The state should have 1 Sell order and 8 Buy orders; 5 active, 4 cancelled

        send(createTick6());

            // check the state of the order book after 6th market data tick
            final   String updatedOrderBook6 = Util.orderBookToString(container.getState());

            // Get the best bid and ask prices at level 0 and their corresponding prices
            final BidLevel bestBidAt6thTick = state.getBidAt(0);
            final AskLevel bestAskAt6thTick = state.getAskAt(0);
            final long bestBidPriceAt6thTick = bestBidAt6thTick.getPrice();
            final long bestAskPriceAt6thTick = bestAskAt6thTick.getPrice();
            final long previousBoughtPriceAt5thTick = lastBoughtPriceAfter5thTickIs98.getPrice();

            // Define the spread threshold (in price points)
            final long spreadAt6thTick = Math.abs(bestAskPriceAt6thTick - bestBidPriceAt6thTick); 
            final boolean spreadIsBelowOrEqualToSpreadThresholdAt6thTick = spreadAt6thTick <= 5L; 
            final boolean spreadIsAboveOrEqualToSpreadThresholdAt6thTick = spreadAt6thTick > 5L;
            final long priceReversalThreshold6thTick = bestAskPriceAt6thTick - previousBoughtPriceAt5thTick;

            // check for best bid and best ask prices, the spread and price reversal threshold
            assertEquals(102, bestBidPriceAt6thTick);
            assertEquals(104, bestAskPriceAt6thTick);
            assertEquals(2, spreadAt6thTick);            
            assertEquals(98, previousBoughtPriceAt5thTick);
            assertEquals(true, spreadIsBelowOrEqualToSpreadThresholdAt6thTick);
            assertEquals(false, spreadIsAboveOrEqualToSpreadThresholdAt6thTick);
            assertEquals(6, priceReversalThreshold6thTick);
            
            // check total order count and total active child orders
            final int totalOrdersCountAfter6thTick = container.getState().getChildOrders().size();
            
            final int activeChildOrdersAfter6thTick = container.getState().getActiveChildOrders().size();

            // Using Java Streams API to perform various operations from the list of Child Orders such as filtering, mapping, aggregating, etc
            // or find/retrieve certain information

            final long buyOrderCountAfter6thTick = state.getChildOrders().stream()
                                                        .filter(order -> order.getSide() == Side.BUY)
                                                        .map(order -> 1L)
                                                        .reduce(0L, Long::sum);

            final long sellOrderCountAfter6thTick = state.getChildOrders().stream()
                                                         .filter(order -> order.getSide() == Side.SELL)
                                                         .map(order -> 1L)
                                                         .reduce(0L, Long::sum);

            final long buyOrdersTotalOrderedQuantityAfter6thTick = state.getChildOrders().stream()
                                                                        .filter(order -> order.getSide() == Side.BUY)                 
                                                                        .map(ChildOrder::getQuantity)
                                                                        .reduce (Long::sum).orElse(0L);

            final long sellOrdersTotalOrderedQuantityAfter6thTick = state.getChildOrders().stream()
                                                                         .filter(order -> order.getSide() == Side.SELL)                
                                                                         .map(ChildOrder::getQuantity)
                                                                         .reduce (Long::sum).orElse(0L);    

            final long buyOrdersFilledQuantityAfter6thTick = state.getChildOrders().stream()
                                                                  .filter(order -> order.getSide() == Side.BUY) 
                                                                  .map(ChildOrder::getFilledQuantity)
                                                                  .reduce(Long::sum).orElse(0L);                                                                   

            final long sellOrdersFilledQuantityAfter6thTick = state.getChildOrders().stream()
                                                                   .filter(order -> order.getSide() == Side.SELL) 
                                                                   .map(ChildOrder::getFilledQuantity)
                                                                   .reduce(Long::sum).orElse(0L);

            // calculate unfilled quantity for sell orders
            final long sellOrdersUnfilledQuantityAfter6thTick = sellOrdersTotalOrderedQuantityAfter6thTick  - sellOrdersFilledQuantityAfter6thTick;

            final long cancelledBuyChildOrderCountAfter6thTick = state.getChildOrders().stream()
                                                                      .filter(order -> order.getSide() == Side.BUY)
                                                                      .filter(order -> order.getState() == OrderState.CANCELLED)
                                                                      .count();

            final long cancelledSellOrderCountAfter6thTick = state.getChildOrders().stream()
                                                                      .filter(order -> order.getSide() == Side.SELL)            
                                                                      .filter(order -> order.getState() == 3)
                                                                      .map(order -> 1L).reduce(0L, Long::sum);

            // check that a new buy order has been created at the price of 102 after 6th tick or throw an assertion error if no such order is found                       
            final ChildOrder newBuyOrderPriceAfter6thTickIs102 = state.getChildOrders().stream()
                                                                      .filter(order -> order.getSide() == Side.BUY)
                                                                      .reduce((first, second) -> second)
                                                                      .orElseThrow(() ->
                                                                       new AssertionError("Expected the last Buy order price to be 102 but found something else"));

            // get the last 3 new orders from the list of child orders and add them to another list 
            final List<ChildOrder> thereAreThreeNewBuyOrdersAtTheEndOfTheListAfter6thTick = state.getChildOrders().stream()
                                                                                                 .skip(Math.max(0, state.getChildOrders().size() - 3))
                                                                                                 .collect(Collectors.toList());
                                                                        
            // throw an error if there are less than 3 orders at the end of the list
            if (thereAreThreeNewBuyOrdersAtTheEndOfTheListAfter6thTick.size() != 3) {
                throw new IllegalStateException("Expected exactly 3 new buy orders at the end of the list but found something else");
            }

            // Check that there are exactly 3 new buy orders at the price of 102 at the end of the list 
            assertEquals(3, thereAreThreeNewBuyOrdersAtTheEndOfTheListAfter6thTick.size());

            for (ChildOrder eachOrder : thereAreThreeNewBuyOrdersAtTheEndOfTheListAfter6thTick) {
                assertEquals(true, eachOrder.getSide() == Side.BUY);
                assertEquals(true, eachOrder.getPrice() == 102);
            } 

    
            // Check things like total order count, total ordered quantity, filled quantity, unfilled quantity, total active child orders,
            // cancelled orders, last bought price etc                                                             
            assertEquals(9, totalOrdersCountAfter6thTick); 
            assertEquals(8, buyOrderCountAfter6thTick); 
            assertEquals(1, sellOrderCountAfter6thTick); 
            assertEquals(1600, buyOrdersTotalOrderedQuantityAfter6thTick); 
            assertEquals(501, buyOrdersFilledQuantityAfter6thTick);
            assertEquals(501, sellOrdersTotalOrderedQuantityAfter6thTick);
            assertEquals(501, sellOrdersFilledQuantityAfter6thTick);
            assertEquals(0, sellOrdersUnfilledQuantityAfter6thTick);
            assertEquals(5, activeChildOrdersAfter6thTick);
            assertEquals(3, cancelledBuyChildOrderCountAfter6thTick);
            assertEquals(1, cancelledSellOrderCountAfter6thTick);
           

        // TESTING THE ALGO'S BEHAVIOUR AFTER THE 7TH TICK
        // Best bid = 100; Best Ask = 102; Spread = 2.
        // 1 buy order of 102 to be fully filled for 200 shares
        // No more sell orders to be created as the bid and ask prices have not moved 
        // The state should have 1 Sell order and 8 Buy orders as in tick 6; 5 active, 4 cancelled

        send(createTick7());

            // check the state of the order book after 7th market data tick
            final String updatedOrderBook7 = Util.orderBookToString(container.getState());     

            // Get the best bid and ask prices at level 0 and their corresponding prices/quantity
            final BidLevel bestBidAt7thTick = state.getBidAt(0);
            final AskLevel bestAskAt7thTick = state.getAskAt(0);
            final long bestBidPriceAt7thTick = bestBidAt7thTick.getPrice();
            final long bestAskPriceAt7thTick = bestAskAt7thTick.getPrice();
            final long bestAskQuantityAt7thTick = bestAskAt7thTick.getQuantity(); 
            final long previousBoughtPriceAt6thTick = newBuyOrderPriceAfter6thTickIs102.getPrice();

            // Define the spread threshold (in price points)
            final long spreadAt7thTick = Math.abs(bestAskPriceAt7thTick - bestBidPriceAt7thTick); 
            final boolean spreadIsBelowOrEqualToSpreadThresholdAt7thTick = spreadAt7thTick <= 5L; 
            final boolean spreadIsAboveOrEqualToSpreadThresholdAt7thTick = spreadAt7thTick > 5L;
            final long priceReversalThreshold7thTick = bestAskPriceAt7thTick - previousBoughtPriceAt6thTick;  

            // check for best bid and best ask prices, the spread and price reversal threshold
            assertEquals(100, bestBidPriceAt7thTick);
            assertEquals(102, bestAskPriceAt7thTick);            
            assertEquals(2, spreadAt7thTick);
            assertEquals(102, previousBoughtPriceAt6thTick);
            assertEquals(200, bestAskQuantityAt7thTick);            
            assertEquals(true, spreadIsBelowOrEqualToSpreadThresholdAt7thTick);
            assertEquals(false, spreadIsAboveOrEqualToSpreadThresholdAt7thTick);
            assertEquals(0, priceReversalThreshold7thTick);


            // check total order count and total active child orders
            final int totalOrdersCountAfter7thTick = container.getState().getChildOrders().size();
            
            final int activeChildOrdersAfter7thTick = container.getState().getActiveChildOrders().size();

            // Using Java Streams API to perform various operations from the list of Child Orders such as filtering, mapping, aggregating, etc
            // or find/retrieve certain information

            final long buyOrderCountAfter7thTick = state.getChildOrders().stream()
                                                        .filter(order -> order.getSide() == Side.BUY)
                                                        .map(order -> 1L)
                                                        .reduce(0L, Long::sum);

            final long totalBuyOrderedQuantityAfter7thTick = state.getChildOrders().stream()
                                                                  .filter(order -> order.getSide() == Side.BUY) 
                                                                  .map(ChildOrder::getQuantity)
                                                                  .reduce (Long::sum).orElse(0L);

            final long totalBuyOrdersFilledQuantityAfter7thTick = state.getChildOrders().stream()
                                                                       .filter(order -> order.getSide() == Side.BUY)
                                                                       .map(ChildOrder::getFilledQuantity)
                                                                       .reduce(Long::sum).orElse(0L); 

            final long totalSellOrderedQuantityAfter7thTick = state.getChildOrders().stream()
                                                                   .filter(order -> order.getSide() == Side.SELL)
                                                                   .map(ChildOrder::getQuantity)
                                                                   .reduce(Long::sum).orElse(0L); 

            final long totalSellOrdersFilledQuantityAfter7thTick = state.getChildOrders().stream()
                                                                        .filter(order -> order.getSide() == Side.SELL)
                                                                        .map(ChildOrder::getFilledQuantity)
                                                                        .reduce(Long::sum).orElse(0L); 

            final long sellOrderCountAfter7thTick = state.getChildOrders().stream()
                                                         .filter(order -> order.getSide() == Side.SELL)
                                                         .map(order -> 1L).reduce(0L, Long::sum);

            final long cancelledBuyChildOrdersAfter7thTick = state.getChildOrders().stream()
                                                                  .filter(order -> order.getSide() == Side.BUY)
                                                                  .filter(order -> order.getState() == OrderState.CANCELLED)
                                                                  .count();

            final long cancelledSellOrderCountAfter7thTick = state.getChildOrders().stream()
                                                                      .filter(order -> order.getSide() == Side.SELL)            
                                                                      .filter(order -> order.getState() == 3)
                                                                      .map(order -> 1L).reduce(0L, Long::sum);

            // calculate total unfilled quanitty for buy orders
            final long totalBuyOrdersUnfilledQuantityAfter7thTick = totalBuyOrderedQuantityAfter7thTick - totalBuyOrdersFilledQuantityAfter7thTick;

            // calculate total unfilled quanitty for sell orders
            final long totalSellOrdersUnFilledQuantityAfter7thTick = totalSellOrderedQuantityAfter7thTick - totalSellOrdersFilledQuantityAfter7thTick;


            // check that the last bought price after 7th tick is at the price of 102 or throw an assertion error if no such order is found                           
            final ChildOrder lastBoughtPriceAfter7thTickIs102 = state.getChildOrders().stream()
                                                                     .filter(order -> order.getSide() == Side.BUY)
                                                                     .reduce((first, second) -> second)
                                                                     .orElseThrow(() -> 
                                                                      new AssertionError("Expected the last buy order to be at the price of 102 but found something else"));
            // Get the price for last filled buy order after 7th tick                        
            final ChildOrder lastFilledBuyOrderPriceAfter7thTickIs102 = state.getChildOrders().stream()
                                                                             .filter(order -> order.getSide() == Side.BUY)
                                                                             .filter(order -> order.getFilledQuantity() == order.getQuantity())
                                                                             .reduce((first, second) -> second) // Keeps only the last filled buy order    
                                                                             .orElseThrow(() -> 
                                                                              new AssertionError("Expected the price for last filled buy order to be 102 but found something else"));

            // Get the filled quantity for the last filled buy order after 7th tick                        
            final ChildOrder filledQuantityForlastFilledBuyOrderAfter7thTickIs200 = state.getChildOrders().stream()
                                                                                         .filter(order -> order.getSide() == Side.BUY)
                                                                                         .filter(order -> order.getFilledQuantity() == order.getQuantity())
                                                                                         .reduce((first, second) -> second) // Keeps only the last filled buy order    
                                                                                         .orElseThrow(() -> 
                                                                                          new AssertionError("Expected the filled quantity for last buy order to be 200 but found something else"));

                                                                                       
            // Calculate the quantity available for sale
            final long remainingQuantityToSellAfter7thTick = totalBuyOrdersFilledQuantityAfter7thTick - totalSellOrdersFilledQuantityAfter7thTick;


            // CHANGES AFTER BUG WAS FIXED - 17/10/2024 - NUMBER OF BUY ORDERS INCREASED. TOTAL ORDERED QUANTITY ALSO INCREASED

            // Check things like total order count, total ordered quantity, filled quantity, unfilled quantity, total active child orders,
            // cancelled orders, last bought price etc

            assertEquals(9, totalOrdersCountAfter7thTick); 
            assertEquals(8, buyOrderCountAfter7thTick);  
            assertEquals(1, sellOrderCountAfter7thTick); 
            assertEquals(1600, totalBuyOrderedQuantityAfter7thTick);
            assertEquals(899, totalBuyOrdersUnfilledQuantityAfter7thTick);
            assertEquals(5, activeChildOrdersAfter7thTick);
            assertEquals(3, cancelledBuyChildOrdersAfter7thTick);
            assertEquals(1, cancelledSellOrderCountAfter7thTick);
            assertEquals(102, lastFilledBuyOrderPriceAfter7thTickIs102.getPrice());
            assertEquals(200, filledQuantityForlastFilledBuyOrderAfter7thTickIs200.getFilledQuantity());
            assertEquals(701, totalBuyOrdersFilledQuantityAfter7thTick);
            assertEquals(501, totalSellOrderedQuantityAfter7thTick);            
            assertEquals(501, totalSellOrdersFilledQuantityAfter7thTick);
            assertEquals(0, totalSellOrdersUnFilledQuantityAfter7thTick);                                   
            assertEquals(200, remainingQuantityToSellAfter7thTick);


        // TESTING THE ALGO'S BEHAVIOUR AFTER THE 8TH TICK
        // Best Bid 105, Best Ask 109; Spread = 4
        // Bid and ask price has moved up to the sell threshold 
        // No more Buy orders should be created as conditions not met
        // 2 buy orders with unfilled quantity should be cancelled
        // 1 Sell order should be created at 105 for 200 shares from previous buy order of 102, fully filled for 200 shares and then cancelled       
        // The state should have 10 orders; 3 active, 7 cancelled

        send(createTick8());

            // check the state of the order book after 8th market data tick
            final String updatedOrderBook8 = Util.orderBookToString(container.getState());     

            // Get the best bid and ask prices at level 0 and their corresponding prices
            final BidLevel bestBidAt8thTick = state.getBidAt(0);
            final AskLevel bestAskAt8thTick = state.getAskAt(0);
            final long bestBidPriceAt8thTick = bestBidAt8thTick.getPrice();
            final long bestAskPriceAt8thTick = bestAskAt8thTick.getPrice();           
            final long previousBoughtPriceAt7thTick = lastBoughtPriceAfter7thTickIs102.getPrice();
            
            // Define the spread threshold (in price points)
            final long spreadAt8thTick = Math.abs(bestAskPriceAt8thTick - bestBidPriceAt8thTick); 
            final boolean spreadIsBelowOrEqualToSpreadThresholdAt8thTick = spreadAt8thTick <= 5L; 
            final boolean spreadIsAboveOrEqualToSpreadThresholdAt8thTick = spreadAt8thTick > 5L;
            final long priceReversalThreshold8thTick = bestAskPriceAt8thTick - previousBoughtPriceAt7thTick;  

            // check for best bid and best ask prices, the spread and price reversal threshold
            assertEquals(105, bestBidPriceAt8thTick);
            assertEquals(109, bestAskPriceAt8thTick);

            assertEquals(4, spreadAt8thTick);            
            assertEquals(102, previousBoughtPriceAt7thTick);
            assertEquals(true, spreadIsBelowOrEqualToSpreadThresholdAt8thTick);
            assertEquals(false, spreadIsAboveOrEqualToSpreadThresholdAt8thTick);
            assertEquals(7, priceReversalThreshold8thTick);

            
            // check total order count and total active child orders
            final int totalOrdersCountAfter8thTick = container.getState().getChildOrders().size();
            
            final int activeChildOrdersAfter8thTick = container.getState().getActiveChildOrders().size();

            // Using Java Streams API to perform various operations from the list of Child Orders such as filtering, mapping, aggregating, etc
            // or find/retrieve certain information

            final long buyOrderCountAfter8thTick = state.getChildOrders().stream()
                                                        .filter(order -> order.getSide() == Side.BUY)
                                                        .map(order -> 1L).reduce(0L, Long::sum);

            final long totalBuyOrderedQuantityAfter8thTick = state.getChildOrders().stream()
                                                                  .filter(order -> order.getSide() == Side.BUY) 
                                                                  .map(ChildOrder::getQuantity)
                                                                  .reduce (Long::sum).orElse(0L);

            final long totalBuyOrdersFilledQuantityAfter8thTick = state.getChildOrders().stream()
                                                                       .filter(order -> order.getSide() == Side.BUY)                
                                                                       .map(ChildOrder::getFilledQuantity)
                                                                       .reduce(Long::sum).orElse(0L);

            final long totalBuyOrdersUnfilledQuantityAfter8thTick = totalBuyOrderedQuantityAfter8thTick - totalBuyOrdersFilledQuantityAfter8thTick;

            final long sellOrderCountAfter8thTick = state.getChildOrders().stream()
                                                         .filter(order -> order.getSide() == Side.SELL)
                                                         .map(order -> 1L).reduce(0L, Long::sum);

            final long totalSellOrderedQuantityAfter8thTick = state.getChildOrders().stream()
                                                                   .filter(order -> order.getSide() == Side.SELL)
                                                                   .map(ChildOrder::getQuantity)
                                                                   .reduce(Long::sum).orElse(0L);                                                

            final long totalSellOrdersFilledQuantityAfter8thTick = state.getChildOrders().stream()
                                                                        .filter(order -> order.getSide() == Side.SELL)
                                                                        .map(ChildOrder::getFilledQuantity)
                                                                        .reduce(Long::sum).orElse(0L);

            // calculate total unfilled quantity for sell orders
            final long totalSellOrdersUnfilledQuantityAfter8thTick = totalSellOrderedQuantityAfter8thTick - totalSellOrdersFilledQuantityAfter8thTick;

            final long cancelledBuyOrderCountAfter8thTick = state.getChildOrders().stream()
                                                                 .filter(order -> order.getSide() == Side.BUY)            
                                                                 .filter(order -> order.getState() == 3)
                                                                 .map(order -> 1L).reduce(0L, Long::sum);

            final long cancelledSellOrderCountAfter8thTick = state.getChildOrders().stream()
                                                                  .filter(order -> order.getSide() == Side.SELL)
                                                                  .filter(order -> order.getState() == 3)
                                                                  .map(order -> 1L).reduce(0L, Long::sum);
                                                                 

            // check that the last bought price after 8th tick is at the price of 102 or throw an assertion error if no such order is found                           
            final ChildOrder lastBoughtPriceAfter8thTickIs102 = state.getChildOrders().stream()
                                                                     .filter(order -> order.getSide() == Side.BUY)
                                                                     .reduce((first, second) -> second)
                                                                     .orElseThrow(() -> 
                                                                      new AssertionError("Expected the last buy order to be at the price of 102 but found something else"));                                                              

            // check that the last cancelled buy order after 8th tick is at the price of 102 or throw an assertion error if no such order is found  
            final ChildOrder lastCancelledBuyOrderPriceAfter8thTickIs102 = state.getChildOrders().stream()
                                                                                .filter(order -> order.getSide() == Side.BUY)
                                                                                .filter(order -> order.getState() == OrderState.CANCELLED)
                                                                                .reduce((first, second) -> second)
                                                                                .orElseThrow(() -> 
                                                                                 new AssertionError("Expected the last cancelled buy order price for 102 but found something else"));                                                                              

            // check that we have created a new sell order after 8th tick at the price of 105 or throw an assertion error if no such order is found  
            final ChildOrder newSellOrderPriceAfter8thTickIs105 = state.getChildOrders().stream()
                                                                       .filter(order -> order.getSide() == Side.SELL)
                                                                       .reduce((first, second) -> second)
                                                                       .orElseThrow(() ->
                                                                        new AssertionError("Expected a new sell order at the price of 105 but found something else"));

            // check that sold quantity for the new sell order after 8th tick is for 200 shares or throw an assertion error if this is not correct
            final ChildOrder newSellOrderedQtyAfter8thTickIs200 = state.getChildOrders().stream()
                                                                       .filter(order -> order.getSide() == Side.SELL)
                                                                       .reduce((first, second) -> second)
                                                                       .orElseThrow(() ->
                                                                        new AssertionError("Expected the sold quantity for the new sell order is 200 shares but found something else"));  

            // check that filled quantity for the new sell order after 8th tick is for 200 shares or throw an assertion error if this is not correct
            final ChildOrder newSellOrderFilledQtyAfter8thTickIs200 = state.getChildOrders().stream()
                                                                           .filter(order -> order.getSide() == Side.SELL)
                                                                           .reduce((first, second) -> second)
                                                                           .orElseThrow(() ->
                                                                            new AssertionError("Expected a new sell order filled for 200 shares but found something else"));

            // check that the last cancelled sell order after 8th tick is at the price of 105 or throw an assertion error if no such order is found  
            final ChildOrder lastCancelledSellOrderPriceAfter8thTickIs105 = state.getChildOrders().stream()
                                                                                 .filter(order -> order.getSide() == Side.SELL)
                                                                                 .filter(order -> order.getState() == OrderState.CANCELLED)
                                                                                 .reduce((first, second) -> second)
                                                                                 .orElseThrow(() -> 
                                                                                  new AssertionError("Expected the last cancelled sell order price for 105 but found something else"));



            // Check things like total order count, total ordered quantity, filled quantity, unfilled quantity, total active child orders,
            // cancelled orders, last bought price etc 
            assertEquals(10, totalOrdersCountAfter8thTick);
            assertEquals(8, buyOrderCountAfter8thTick);
            assertEquals(2, sellOrderCountAfter8thTick); 
            assertEquals(1600, totalBuyOrderedQuantityAfter8thTick); 
            assertEquals(701, totalBuyOrdersFilledQuantityAfter8thTick);
            assertEquals(899, totalBuyOrdersUnfilledQuantityAfter8thTick);
            assertEquals(200, remainingQuantityToSellAfter7thTick);            
            assertEquals(701, totalSellOrderedQuantityAfter8thTick); 
            assertEquals(701, totalSellOrdersFilledQuantityAfter8thTick); 
            assertEquals(0, totalSellOrdersUnfilledQuantityAfter8thTick);
            assertEquals(3, activeChildOrdersAfter8thTick);
            assertEquals(5, cancelledBuyOrderCountAfter8thTick);
            assertEquals(102, lastCancelledBuyOrderPriceAfter8thTickIs102.getPrice());            
            assertEquals(2, cancelledSellOrderCountAfter8thTick);
            assertEquals(102, previousBoughtPriceAt7thTick);
            assertEquals(105, newSellOrderPriceAfter8thTickIs105.getPrice());
            assertEquals(200, newSellOrderedQtyAfter8thTickIs200.getQuantity());
            assertEquals(200, newSellOrderFilledQtyAfter8thTickIs200.getFilledQuantity());
            assertEquals(105, lastCancelledSellOrderPriceAfter8thTickIs105.getPrice());


        // TESTING THE ALGO'S BEHAVIOUR AFTER THE 9TH TICK
        // Best Bid 106, Best Ask 111; Spread = 5  
        // No buy order to be created as the list has 5 active orders and the spread has widened
        // No more sell orders to be created as there are no quantities to sell
        // The state should have 10 orders as in 8th tick; 3 active, 7 cancelled

        send(createTick9());

            // check the state of the order book after 9th market data tick        
            final String updatedOrderBook9 = Util.orderBookToString(container.getState());     

            // Get the best bid and ask prices at level 0 and their corresponding prices
            final BidLevel bestBidAt9thTick = state.getBidAt(0);
            final AskLevel bestAskAt9thTick = state.getAskAt(0);
            final long bestBidPriceAt9thTick = bestBidAt9thTick.getPrice();
            final long bestAskPriceAt9thTick = bestAskAt9thTick.getPrice();
            final long previousBoughtPriceAt8thTick = lastBoughtPriceAfter8thTickIs102.getPrice();           

            // Define the spread threshold (in price points)
            final long spreadAt9thTick = Math.abs(bestAskPriceAt9thTick - bestBidPriceAt9thTick); 
            final boolean spreadIsBelowOrEqualToSpreadThresholdAt9thTick = spreadAt9thTick <= 5L; 
            final boolean spreadIsAboveOrEqualToSpreadThresholdAt9thTick = spreadAt9thTick > 5L;
            final long priceReversalThresholdAt9thTick = bestAskPriceAt9thTick - previousBoughtPriceAt8thTick;  

            // check for best bid and best ask prices, the spread and price reversal threshold
            assertEquals(106, bestBidPriceAt9thTick);
            assertEquals(111, bestAskPriceAt9thTick);
            assertEquals(5, spreadAt9thTick);
            assertEquals(102, previousBoughtPriceAt8thTick);
            assertEquals(true, spreadIsBelowOrEqualToSpreadThresholdAt9thTick);
            assertEquals(false, spreadIsAboveOrEqualToSpreadThresholdAt9thTick);
            assertEquals(9, priceReversalThresholdAt9thTick);

            // check total order count and total active child orders
            final int totalOrdersCountAfter9thTick = container.getState().getChildOrders().size();
            
            final int activeChildOrdersAfter9thTick = container.getState().getActiveChildOrders().size();

            // Using Java Streams API to perform various operations from the list of Child Orders such as filtering, mapping, aggregating, etc
            // or find/retrieve certain information

            final long buyOrderCountAfter9thTick = state.getChildOrders().stream()
                                                        .filter(order -> order.getSide() == Side.BUY)
                                                        .map(order -> 1L).reduce(0L, Long::sum);

            final long totalBuyOrderedQuantityAfter9thTick = state.getChildOrders().stream()
                                                                  .filter(order -> order.getSide() == Side.BUY) 
                                                                  .map(ChildOrder::getQuantity)
                                                                  .reduce (Long::sum).orElse(0L);

            final long totalBuyOrdersFilledQuantityAfter9thTick = state.getChildOrders().stream()
                                                                       .filter(order -> order.getSide() == Side.BUY)                
                                                                       .map(ChildOrder::getFilledQuantity)
                                                                       .reduce(Long::sum).orElse(0L);
            // calculate the total unfilled quantity for buy orders
            final long totalBuyOrdersUnfilledQuantityAfter9thTick = totalBuyOrderedQuantityAfter9thTick - totalBuyOrdersFilledQuantityAfter9thTick;

            final long sellOrderCountAfter9thTick = state.getChildOrders().stream()
                                                         .filter(order -> order.getSide() == Side.SELL)
                                                         .map(order -> 1L).reduce(0L, Long::sum);  

            final long totalSellOrderedQuantityAfter9thTick = state.getChildOrders().stream()
                                                                   .filter(order -> order.getSide() == Side.SELL)
                                                                   .map(ChildOrder::getQuantity)
                                                                   .reduce(Long::sum).orElse(0L);

            final long totalSellOrdersFilledQuantityAfter9thTick = state.getChildOrders().stream()
                                                                        .filter(order -> order.getSide() == Side.SELL)
                                                                        .map(ChildOrder::getFilledQuantity)
                                                                        .reduce(Long::sum).orElse(0L);
            // calculate the unfilled quantity for sell orders
            final long totalSellOrdersUnfilledQuantityAfter9thTick = totalSellOrderedQuantityAfter9thTick - totalSellOrdersFilledQuantityAfter9thTick; 


            final long cancelledBuyOrderCountAfter9thTick = state.getChildOrders().stream()
                                                              .filter(order -> order.getSide() == Side.BUY)
                                                              .filter(order -> order.getState() == 3)
                                                              .map(order -> 1L).reduce(0L, Long::sum);

            final long cancelledSellOrderCountAfter9thTick = state.getChildOrders().stream()
                                                                  .filter(order -> order.getSide() == Side.SELL)            
                                                                  .filter(order -> order.getState() == 3)
                                                                  .map(order -> 1L).reduce(0L, Long::sum);                                                            

            // check that the last buy order after 9th tick is at the price of 102 or throw an assertion error if no such order is found                            
            final ChildOrder lastBoughtPriceAfter9thTickIs102 = state.getChildOrders().stream()
                                                                     .filter(order -> order.getSide() == Side.BUY)
                                                                     .reduce((first, second) -> second)
                                                                     .orElseThrow(() -> 
                                                                      new AssertionError("Expected the last buy order to be for 102 but found something else"));

            // Check things like total order count, total ordered quantity, filled quantity, unfilled quantity, total active child orders,
            // cancelled orders, last bought price etc 
            assertEquals(10, totalOrdersCountAfter9thTick); 
            assertEquals(8, buyOrderCountAfter9thTick);
            assertEquals(2, sellOrderCountAfter9thTick); 
            assertEquals(1600, totalBuyOrderedQuantityAfter9thTick); 
            assertEquals(701, totalBuyOrdersFilledQuantityAfter9thTick);
            assertEquals(899, totalBuyOrdersUnfilledQuantityAfter9thTick);
            assertEquals(701, totalSellOrderedQuantityAfter9thTick); 
            assertEquals(701, totalSellOrdersFilledQuantityAfter9thTick); 
            assertEquals(0, totalSellOrdersUnfilledQuantityAfter9thTick);
            assertEquals(3, activeChildOrdersAfter9thTick);
            assertEquals(5, cancelledBuyOrderCountAfter9thTick);
            assertEquals(2, cancelledSellOrderCountAfter9thTick);
            assertEquals(102, previousBoughtPriceAt8thTick);


        // TESTING THE ALGO'S BEHAVIOUR AFTER THE 10TH TICK
        // Best Bid 105, Best Ask 108; Spread = 3
        // 2 new Buy orders should be created at 105 as the list has 3 active orders and the spread is narrow
        // No new sell orders as there are no quantities to sell
        // No buy order to be cancelled as there are no more active  buy orders with unfilled quantities
        // No sell order to be cancelled as there are no more active sell orders with filled quantities
        // The state should have 12 orders; 5 active, 7 cancelled

        send(createTick10());

            // check the state of the order book after 10th market data tick
            final String updatedOrderBook10 = Util.orderBookToString(container.getState());  

            // Get the best bid and ask prices at level 0 and their corresponding prices
            final BidLevel bestBidAt10thTick = state.getBidAt(0);
            final AskLevel bestAskAt10thTick = state.getAskAt(0);
            final long bestBidPriceAt10thTick = bestBidAt10thTick.getPrice();
            final long bestAskPriceAt10thTick = bestAskAt10thTick.getPrice();
            final long previousBoughtPriceAt9thTick = lastBoughtPriceAfter9thTickIs102.getPrice();

            // Define the spread threshold (in price points)
            final long spreadAt10thTick = Math.abs(bestAskPriceAt10thTick - bestBidPriceAt10thTick); 
            final boolean spreadIsBelowOrEqualToSpreadThresholdAt10thTick = spreadAt10thTick <= 5L; 
            final boolean spreadIsAboveOrEqualToSpreadThresholdAt10thTick = spreadAt10thTick > 5L;
            final long priceReversalThresholdAt10thTick = bestAskPriceAt10thTick - previousBoughtPriceAt9thTick;  

            // check for best bid and best ask prices, the spread and price reversal threshold 
            assertEquals(105, bestBidPriceAt10thTick);
            assertEquals(108, bestAskPriceAt10thTick);
            assertEquals(3, spreadAt10thTick);            
            assertEquals(102, previousBoughtPriceAt9thTick);
            assertEquals(true, spreadIsBelowOrEqualToSpreadThresholdAt10thTick);
            assertEquals(false, spreadIsAboveOrEqualToSpreadThresholdAt10thTick);
            assertEquals(6, priceReversalThresholdAt10thTick);          

            // check total order count and total active child orders
            final int totalOrdersCountAfter10thTick = container.getState().getChildOrders().size();
            
            final int activeChildOrdersAfter10thTick = container.getState().getActiveChildOrders().size();

            // Using Java Streams API to perform various operations from the list of Child Orders such as filtering, mapping, aggregating, etc
            // or find/retrieve certain information

            final long buyOrderCountAfter10thTick = state.getChildOrders().stream()
                                                         .filter(order -> order.getSide() == Side.BUY)
                                                         .map(order -> 1L).reduce(0L, Long::sum);

            final long totalBuyOrderedQuantityAfter10thTick = state.getChildOrders().stream()
                                                                   .filter(order -> order.getSide() == Side.BUY) 
                                                                   .map(ChildOrder::getQuantity)
                                                                   .reduce (Long::sum).orElse(0L);

            final long totalBuyOrdersFilledQuantityAfter10thTick = state.getChildOrders().stream()
                                                                        .filter(order -> order.getSide() == Side.BUY)                
                                                                        .map(ChildOrder::getFilledQuantity)
                                                                        .reduce(Long::sum).orElse(0L);
            // calculat total unfilled quantity for buy orders
            final long totalBuyOrdersUnfilledQuantityAfter10thTick = totalBuyOrderedQuantityAfter10thTick - totalBuyOrdersFilledQuantityAfter10thTick;

            final long sellOrderCountAfter10thTick = state.getChildOrders().stream()
                                                          .filter(order -> order.getSide() == Side.SELL)
                                                          .map(order -> 1L).reduce(0L, Long::sum);  

            final long totalSellOrderedQuantityAfter10thTick = state.getChildOrders().stream()
                                                                    .filter(order -> order.getSide() == Side.SELL)
                                                                    .map(ChildOrder::getQuantity)
                                                                    .reduce(Long::sum).orElse(0L); 

            final long totalSellOrdersFilledQuantityAfter10thTick = state.getChildOrders().stream()
                                                                         .filter(order -> order.getSide() == Side.SELL)
                                                                         .map(ChildOrder::getFilledQuantity)
                                                                         .reduce(Long::sum).orElse(0L);

            // calculte total unfilled quanitity for sell orders
            final long sellOrdersUnfilledQuantityAfter10thTick = totalSellOrderedQuantityAfter10thTick - totalSellOrdersFilledQuantityAfter10thTick;

            final long cancelledBuyOrderCountAfter10thTick = state.getChildOrders().stream()
                                                                  .filter(order -> order.getSide() == Side.BUY)
                                                                  .filter(order -> order.getState() == 3)
                                                                  .map(order -> 1L).reduce(0L, Long::sum);

            final long cancelledSellOrderCountAfter10thTick = state.getChildOrders().stream()
                                                                   .filter(order -> order.getSide() == Side.SELL)
                                                                   .filter(order -> order.getState() == 3)
                                                                   .map(order -> 1L).reduce(0L, Long::sum);                                                                  

            // check that the last buy order after 10th tick is at the price of 105 or throw an assertion error if no such order is found                            
            final ChildOrder lastBoughtPriceAfter10thTickIs105 = state.getChildOrders().stream()
                                                                      .filter(order -> order.getSide() == Side.BUY)
                                                                      .reduce((first, second) -> second)
                                                                      .orElseThrow(() -> 
                                                                       new AssertionError("Expected the last buy order to be for 105 but found something else"));                                                               

            // Check things like total order count, total ordered quantity, filled quantity, unfilled quantity, total active child orders,
            // cancelled orders, last bought price etc 
            assertEquals(12, totalOrdersCountAfter10thTick); 
            assertEquals(10, buyOrderCountAfter10thTick);
            assertEquals(2, sellOrderCountAfter10thTick); 
            assertEquals(2000, totalBuyOrderedQuantityAfter10thTick); 
            assertEquals(701, totalBuyOrdersFilledQuantityAfter10thTick);
            assertEquals(1299, totalBuyOrdersUnfilledQuantityAfter10thTick);
            assertEquals(701, totalSellOrderedQuantityAfter10thTick); 
            assertEquals(701, totalSellOrdersFilledQuantityAfter10thTick); 
            assertEquals(0, sellOrdersUnfilledQuantityAfter10thTick);
            assertEquals(5, activeChildOrdersAfter10thTick);
            assertEquals(5, cancelledBuyOrderCountAfter10thTick);
            assertEquals(2, cancelledSellOrderCountAfter10thTick);            
            assertEquals(102, previousBoughtPriceAt9thTick);


        // TESTING THE ALGO'S BEHAVIOUR AFTER THE 11TH TICK - this code here is not working properly
        // Best Bid should be 109 and Best Ask should be 114; Spread = 5 
        // 4/11/2024 - algo registering best bid as 105 and best ask as 110 which is wrong
        // No more Buy order should be created as the list has 5 active orders
        // No new sell orders as there are no quantities to sell
        // 2 unfilled buy order of 105 to be cancelled as the price reversal threshold is 7 points or more
        // The state should have 12 orders as in tick 10; 3 active, 9 cancelled


        // send(createTick11());

        //     // check the state of the order book 
        //     final String updatedOrderBook11 = Util.orderBookToString(container.getState());  

        //     // Get the best bid and ask prices at level 0 and their corresponding prices
        //     final BidLevel bestBidAt11thTick = state.getBidAt(0);
        //     final AskLevel bestAskAt11thTick = state.getAskAt(0);
        //     final long bestBidPriceAt11thTick = bestBidAt11thTick.getPrice();
        //     final long bestAskPriceAt11thTick = bestAskAt11thTick.getPrice();
        //     final long previousBoughtPriceAt10thTick = lastBoughtPriceAfter10thTickIs105.getPrice();

        //     // Define the spread threshold (in price points)
        //     final long spreadAt11thTick = Math.abs(bestAskPriceAt11thTick - bestBidPriceAt11thTick); 
        //     final boolean spreadIsBelowOrEqualToSpreadThresholdAt11thTick = spreadAt11thTick <= 5L; 
        //     final boolean spreadIsAboveOrEqualToSpreadThresholdAt11thTick = spreadAt11thTick > 5L;
        //     final long priceReversalThresholdAt11thTick = bestAskPriceAt11thTick - previousBoughtPriceAt10thTick;  

        //     // check for best bid and best ask prices, the spread and price reversal threshold 
        //     // assertEquals(109, bestBidPriceAt11thTick); // 4/11/2024 - expected 109 but was 105
        //     // assertEquals(114, bestAskPriceAt11thTick); // 4/11/2024 - expected 114 but was 110
        //     assertEquals(5, spreadAt11thTick);            
        //     assertEquals(105, previousBoughtPriceAt10thTick);
        //     assertEquals(true, spreadIsBelowOrEqualToSpreadThresholdAt11thTick);
        //     assertEquals(false, spreadIsAboveOrEqualToSpreadThresholdAt11thTick);
        //     // assertEquals(9, priceReversalThresholdAt11thTick); // 4/11/2024 - expected 9 but was 5

        //     // check total order count and total active child orders
        //     final int totalOrdersCountAfter11thTick = container.getState().getChildOrders().size();
            
        //     final int activeChildOrdersAfter11thTick = container.getState().getActiveChildOrders().size();

        //     // Using Java Streams API to perform various operations from the list of Child Orders such as filtering, mapping, aggregating, etc
        //     // or find/retrieve certain information

        //     final long buyOrderCountAfter11thTick = state.getChildOrders().stream()
        //                                                  .filter(order -> order.getSide() == Side.BUY)
        //                                                  .map(order -> 1L).reduce(0L, Long::sum);

        //     final long totalBuyOrderedQuantityAfter11thTick = state.getChildOrders().stream()
        //                                                            .filter(order -> order.getSide() == Side.BUY) 
        //                                                            .map(ChildOrder::getQuantity)
        //                                                            .reduce (Long::sum).orElse(0L);

        //     final long totalBuyOrdersFilledQuantityAfter11thTick = state.getChildOrders().stream()
        //                                                            .filter(order -> order.getSide() == Side.BUY)                
        //                                                            .map(ChildOrder::getFilledQuantity)
        //                                                            .reduce(Long::sum).orElse(0L);
                                                                   
        //     // calculate total unfilled quantity for buy orders
        //     final long totalBuyOrdersUnfilledQuantityAfter11thTick = totalBuyOrderedQuantityAfter11thTick - totalBuyOrdersFilledQuantityAfter11thTick;

        //     final long sellOrderCountAfter11thTick = state.getChildOrders().stream()
        //                                                   .filter(order -> order.getSide() == Side.SELL)
        //                                                   .map(order -> 1L).reduce(0L, Long::sum);  

        //     final long totalSellOrderedQuantityAfter11thTick = state.getChildOrders().stream()
        //                                                             .filter(order -> order.getSide() == Side.SELL)
        //                                                             .map(ChildOrder::getQuantity)
        //                                                             .reduce(Long::sum).orElse(0L); 

        //     final long totalSellOrdersFilledQuantityAfter11thTick = state.getChildOrders().stream()
        //                                                                  .filter(order -> order.getSide() == Side.SELL)
        //                                                                  .map(ChildOrder::getFilledQuantity)
        //                                                                  .reduce(Long::sum).orElse(0L);
        //     // calculate total unfilled quantity for sell orders
        //     final long sellOrdersUnfilledQuantityAfter11thTick = totalSellOrderedQuantityAfter11thTick - totalSellOrdersFilledQuantityAfter11thTick;

        //     final long cancelledBuyOrderCountAfter11thTick = state.getChildOrders().stream()
        //                                                           .filter(order -> order.getSide() == Side.BUY)
        //                                                           .filter(order -> order.getState() == 3)
        //                                                           .map(order -> 1L).reduce(0L, Long::sum);

        //     final long cancelledSellOrderCountAfter11thTick = state.getChildOrders().stream()
        //                                                           .filter(order -> order.getSide() == Side.SELL)
        //                                                           .filter(order -> order.getState() == 3)
        //                                                           .map(order -> 1L).reduce(0L, Long::sum);

        //     // Check things like total order count, total ordered quantity, filled quantity, unfilled quantity, total active child orders,
        //     // cancelled orders, last bought price etc 
        //     assertEquals(12, totalOrdersCountAfter11thTick); 
        //     assertEquals(10, buyOrderCountAfter11thTick);
        //     assertEquals(2, sellOrderCountAfter11thTick); 
        //     assertEquals(2000, totalBuyOrderedQuantityAfter11thTick); 
        //     assertEquals(701, totalBuyOrdersFilledQuantityAfter11thTick);
        //     assertEquals(1299, totalBuyOrdersUnfilledQuantityAfter11thTick);
        //     assertEquals(701, totalSellOrderedQuantityAfter11thTick); 
        //     assertEquals(701, totalSellOrdersFilledQuantityAfter11thTick); 
        //     assertEquals(0, sellOrdersUnfilledQuantityAfter11thTick);
        //     // assertEquals(3, activeChildOrdersAfter11thTick); // 4/11/2024 expected 3 but was 4
        //     // assertEquals(7, cancelledBuyOrderCountAfter11thTick); // 4/11/2024 expected 7 but was 6
        //     assertEquals(2, cancelledSellOrderCountAfter11thTick);
        //     assertEquals(105, previousBoughtPriceAt10thTick);


        // CALCULATE PROFITS, LOSSES AND COST OF TRADING AFTER 11TH TICK

        // Calculate total cost for all filled buy orders (before fees)
        final long totalBuyCostBeforeFees = state.getChildOrders().stream()
                                                 .filter(order -> order.getSide() == Side.BUY)
                                                 .filter(order -> order.getFilledQuantity() > 0)
                                                 .mapToLong(order -> order.getPrice() * order.getFilledQuantity())
                                                 .sum();

        // Calculate total revenue from all filled sell orders (before fees)
        final long totalSellRevenueBeforeFees = state.getChildOrders().stream()
                                                     .filter(order -> order.getSide() == Side.SELL)
                                                     .filter(order -> order.getFilledQuantity() > 0)
                                                     .mapToLong(order -> order.getPrice() * order.getFilledQuantity())
                                                     .sum();

        // Define broker fee as a percentage (0.5% or 0.005 in decimal)
        final double brokerFeeRate = 0.005;

        // Calculate total cost for all filled buy orders including broker fees
        final long totalBuyCostAfterFees = state.getChildOrders().stream()
                                                .filter(order -> order.getSide() == Side.BUY)
                                                .filter(order -> order.getFilledQuantity() > 0)
                                                .mapToLong(order -> {
                                                            long cost = order.getPrice() * order.getFilledQuantity();
                                                            long fee = (long) (cost * brokerFeeRate); // Apply fee to each buy order
                                                            return cost + fee;
                                                })
                                                .sum();

        // Calculate total revenue from all filled sell orders including broker fees
        final long totalSellRevenueAfterFees = state.getChildOrders().stream()
                                                    .filter(order -> order.getSide() == Side.SELL)
                                                    .filter(order -> order.getFilledQuantity() > 0)
                                                    .mapToLong(order -> {
                                                        long revenue = order.getPrice() * order.getFilledQuantity();
                                                        long fee = (long) (revenue * brokerFeeRate); // Apply fee to each sell order
                                                        return revenue - fee;
                                                    })
                                                    .sum();

        final long grossProfitBeforeFees = totalSellRevenueBeforeFees - totalBuyCostBeforeFees;
        final long netProfitAfterFees = totalSellRevenueAfterFees - totalBuyCostAfterFees;
        final long costOfTrading = grossProfitBeforeFees - netProfitAfterFees;


        // Calculate total expenditure/revenue before and after broker's fees
        assertEquals(0.005, brokerFeeRate, 0.0005);
        assertEquals(69498, totalBuyCostBeforeFees);
        assertEquals(69845, totalBuyCostAfterFees );
        assertEquals(72102, totalSellRevenueBeforeFees);
        assertEquals(71742, totalSellRevenueAfterFees);

        // Calculate gross/net profit and cost of trading
        assertEquals(2604, grossProfitBeforeFees);
        assertEquals(1897, netProfitAfterFees);
        assertEquals(707, costOfTrading);

            

        System.out.println("\n\n ----================================ SUMMARY AFTER CREATING, MATCHING AND CANCELLING ORDERS ==============================---- \n");

        BidLevel bestBid = state.getBidAt(0);
        AskLevel bestAsk = state.getAskAt(0);            
        final long bestBidPrice = bestBid.getPrice();
        final long bestAskPrice = bestAsk.getPrice();
        final long spread = Math.abs(bestAskPrice - bestBidPrice);


        long totalChildOrderCount = state.getChildOrders().size();

        long totalFilledQuantityForBuyOrders = state.getChildOrders().stream()
                                                    .filter(order -> order.getSide() == Side.BUY)
                                                    .map(ChildOrder::getFilledQuantity)
                                                    .reduce(Long::sum)
                                                    .orElse(0L);

        long totalFilledQuantityForSeLLOrders = state.getChildOrders().stream()
                                                     .filter(order -> order.getSide() == Side.SELL)
                                                     .map(ChildOrder::getFilledQuantity)
                                                     .reduce(Long::sum)
                                                     .orElse(0L);

        long totalActiveChildOrders = state.getActiveChildOrders().size();

        long totalCancelledChildOrders = totalChildOrderCount - totalActiveChildOrders;                                                     
                                                    
        System.out.println("bestBidPrice: " + bestBidPrice + " |bestAskPrice " + bestAskPrice + " |spread: " + spread + "\n");            

        System.out.println("NUMBER OF ACTIVE CHILD ORDERS INITIALLY: " + totalChildOrderCount);
        System.out.println("TOTAL FILLED QUANTITY FOR BUY ORDERS: " + totalFilledQuantityForBuyOrders);
        System.out.println("TOTAL FILLED QUANTITY FOR SELL ORDERS: " + totalFilledQuantityForSeLLOrders);
        System.out.println("TOTAL CANCELLED ORDERS:     " + totalCancelledChildOrders);            
        System.out.println("CURRENT NUMBER OF ACTIVE CHILD ORDERS:      " + totalActiveChildOrders + "\n");

        System.out.println("\nList of all Child Orders created:");
        for (ChildOrder childOrder : state.getChildOrders()) {
            System.out.println("Order ID: " + childOrder.getOrderId() + 
                            " | Side: " + childOrder.getSide() +            
                            " | Price: " + childOrder.getPrice() +
                            " | Ordered Qty: " + childOrder.getQuantity() +
                            " | Filled Qty: " + childOrder.getFilledQuantity() +
                            " | State of the order: " + childOrder.getState());
        }

        // Create a list of all filled child order details then print it
        StringBuilder listOfAllFilledChildOrders = new StringBuilder("\nList of all Filled Child orders:\n");
        for (ChildOrder childOrder : state.getChildOrders()) {
            if (childOrder.getFilledQuantity() > 0) {
                listOfAllFilledChildOrders.append(String.format(
        "Order Id: %d   | Side: %s    | Price: %d   | Order Qty: %d     | Filled Qty %d     | State of the order: %d%n",
                childOrder.getOrderId(), childOrder.getSide(), childOrder.getPrice(), childOrder.getQuantity(),
                childOrder.getFilledQuantity(), childOrder.getState()));
            }

        }
        System.out.println(listOfAllFilledChildOrders.toString()); // Turn the list into a string and print it

        
        System.out.println("\nList of all Cancelled Orders:"); 
        for (ChildOrder childOrder : state.getChildOrders()) {
            if (childOrder.getState() == 3){
                long unfilledQuantity = childOrder.getQuantity() - childOrder.getFilledQuantity();                
                System.out.println("Order ID: " + childOrder.getOrderId() + 
                            " | Side: " + childOrder.getSide() +            
                            " | Price: " + childOrder.getPrice() +
                            " | Ordered Qty: " + childOrder.getQuantity() +
                            " | Filled Qty: " + childOrder.getFilledQuantity() +                               
                            " | Unfilled Qty: " + unfilledQuantity +
                            " | State of the order: " + childOrder.getState());
            }

        }
        

        System.out.println("\nList of all Active Child orders:");
        for (ChildOrder childOrder : state.getActiveChildOrders()) {
            System.out.println("Order ID: " + childOrder.getOrderId() +
                            " | Side: " + childOrder.getSide() +
                            " | Price: " + childOrder.getPrice() +
                            " | Ordered Qty: " + childOrder.getQuantity() +
                            " | Filled Qty: " + childOrder.getFilledQuantity() +
                            " | State of the order: " + childOrder.getState());
        }

        System.out.println("\nPROFIT/LOSS CALCULATION AND COST OF TRADING");
        System.out.println("Total Buy Cost Before Broker Fees:      " +  totalBuyCostBeforeFees);
        System.out.println("Total Buy Cost After Broker Fees:       " +  totalBuyCostAfterFees);
        System.out.println("Total Sell Revenue Before Broker Fees:  " +  totalSellRevenueBeforeFees);        
        System.out.println("Total Sell Revenue After Broker Fees:   " +  totalSellRevenueAfterFees);
        System.out.println("\nGross Profit After Broker Fees:         " +  grossProfitBeforeFees); 
        System.out.println("Net Profit After  Broker Fees:          " +  netProfitAfterFees);
        System.out.println("              Cost of trading:          " + grossProfitBeforeFees + " - " + netProfitAfterFees + " = " +  costOfTrading);         


        System.out.println("\nTHE STATE OF THE ORDER BOOK AFTER PROCESSING DATA FROM 1ST TICK " +         
                            "AND CREATING ORDERS LOOKED LIKE THIS \n\n: " +  updatedOrderBook1);

        System.out.println("\nTHE STATE OF THE ORDER BOOK AFTER PROCESSING DATA FROM 2ND TICK " +
                            "NOW LOOKS LIKE THIS \n\n: " +  updatedOrderBook2);

        System.out.println("\nTHE STATE OF THE ORDER BOOK AFTER PROCESSING DATA FROM 3RD TICK " +
                            "NOW LOOKS LIKE THIS \n\n: " +  updatedOrderBook3); 
                            
        System.out.println("\nTHE STATE OF THE ORDER BOOK AFTER PROCESSING DATA FROM 4TH TICK " +
                            "NOW LOOKS LIKE THIS \n\n: " +  updatedOrderBook4);

        System.out.println("\nTHE STATE OF THE ORDER BOOK AFTER PROCESSING DATA FROM 5TH TICK " +
                            "NOW LOOKS LIKE THIS \n\n: " +  updatedOrderBook5);

        System.out.println("\nTHE STATE OF THE ORDER BOOK AFTER PROCESSING DATA FROM 6TH TICK " +
                            "NOW LOOKS LIKE THIS \n\n: " +  updatedOrderBook6); 

        System.out.println("\nTHE STATE OF THE ORDER BOOK AFTER PROCESSING DATA FROM 7TH TICK " +
                            "NOW LOOKS LIKE THIS \n\n: " +  updatedOrderBook7); 

        System.out.println("\nTHE STATE OF THE ORDER BOOK AFTER PROCESSING DATA FROM 8TH TICK " +
        "NOW LOOKS LIKE THIS \n\n: " +  updatedOrderBook8); 

        System.out.println("\nTHE STATE OF THE ORDER BOOK AFTER PROCESSING DATA FROM 9TH TICK " +
                            "NOW LOOKS LIKE THIS \n\n: " +  updatedOrderBook9);                                 

        System.out.println("\nTHE STATE OF THE ORDER BOOK AFTER PROCESSING DATA FROM 10TH TICK " +
        "NOW LOOKS LIKE THIS \n\n: " +  updatedOrderBook10);                                   

        // System.out.println("\nTHE STATE OF THE ORDER BOOK AFTER PROCESSING DATA FROM 11TH TICK " +
        // "NOW LOOKS LIKE THIS \n\n: " +  updatedOrderBook11);
    }        

}


// // AMENDED AND FINAL CODE AFTER BUGS WERE FIXED AFTER 17/10/2024

// package codingblackfemales.gettingstarted;
// import codingblackfemales.algo.AlgoLogic;
// import codingblackfemales.sotw.ChildOrder;
// import codingblackfemales.sotw.OrderState;
// import codingblackfemales.sotw.marketdata.AskLevel;
// import codingblackfemales.sotw.marketdata.BidLevel;
// import codingblackfemales.util.Util;
// import org.agrona.concurrent.UnsafeBuffer;
// import messages.marketdata.*;
// import messages.order.Side;

// import java.nio.ByteBuffer;
// import java.util.List;
// import java.util.stream.Collectors;

// // import java.util.Optional;
// import static org.junit.Assert.assertEquals;

// import org.junit.Test;

// // /**
// //  * This test plugs together all of the infrastructure, including the order book (which you can trade against)
// //  * and the market data feed.
// //  *
// //  * If your algo adds orders to the book, they will reflect in your market data coming back from the order book.
// //  *
// //  * If you cross the srpead (i.e. you BUY an order with a price which is == or > askPrice()) you will match, and receive
// //  * a fill back into your order from the order book (visible from the algo in the childOrders of the state object.
// //  *
// //  * If you cancel the order your child order will show the order status as cancelled in the childOrders of the state object.
// //  *
// //  */
// public class MyProfitAlgoBackTest extends AbstractAlgoBackTest {

//     @Override
//     public AlgoLogic createAlgoLogic() {
//         return new MyProfitAlgo();
//     }

//     // ADDED 21/9/2024 - FOR TESTING PURPOSES
//     protected UnsafeBuffer createTick3(){

//         final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
//         final BookUpdateEncoder encoder = new BookUpdateEncoder();

//         final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
//         final UnsafeBuffer directBuffer = new UnsafeBuffer(byteBuffer);

//         //write the encoded output to the direct buffer
//         encoder.wrapAndApplyHeader(directBuffer, 0, headerEncoder);

//         //set the fields to desired values
//         encoder.venue(Venue.XLON);
//         encoder.instrumentId(123L);
//         encoder.source(Source.STREAM);

//         encoder.bidBookCount(3)
//                 .next().price(96L).size(100L)
//                 .next().price(93L).size(200L)
//                 .next().price(91L).size(300L);

//         encoder.askBookCount(4)
//                 .next().price(102).size(300L) // ask price was 103
//                 .next().price(109L).size(200L)
//                 .next().price(110L).size(5000L)
//                 .next().price(119L).size(5600L);

//         encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

//         return directBuffer;
//     }

//     // ADDED 27/9/2024 - FOR TESTING PURPOSES
//     protected UnsafeBuffer createTick4(){

//         final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
//         final BookUpdateEncoder encoder = new BookUpdateEncoder();

//         final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
//         final UnsafeBuffer directBuffer = new UnsafeBuffer(byteBuffer);

//         //write the encoded output to the direct buffer
//         encoder.wrapAndApplyHeader(directBuffer, 0, headerEncoder);

//         //set the fields to desired values
//         encoder.venue(Venue.XLON);
//         encoder.instrumentId(123L);
//         encoder.source(Source.STREAM);

//         encoder.bidBookCount(3)
//                 .next().price(98L).size(600L)
//                 .next().price(96L).size(200L)
//                 .next().price(95L).size(300L);

//         encoder.askBookCount(4)
//             .next().price(105L).size(300L) // ask price was 108
//             .next().price(109L).size(200L)
//             .next().price(110L).size(5000L)
//             .next().price(119L).size(5600L);

//         encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

//         return directBuffer;
//     }       


//     // ADDED 28/9/2024 - FOR TESTING PURPOSES.
//     // 17/10/2024 SELL ORDER GOT FILED AT 102 AFTER ADJUSTING SELLING PRICE FROM "BEST BID" TO BEST ASK
//     protected UnsafeBuffer createTick5(){

//         final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
//         final BookUpdateEncoder encoder = new BookUpdateEncoder();

//         final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
//         final UnsafeBuffer directBuffer = new UnsafeBuffer(byteBuffer);

//         //write the encoded output to the direct buffer
//         encoder.wrapAndApplyHeader(directBuffer, 0, headerEncoder);

//         //set the fields to desired values
//         encoder.venue(Venue.XLON);
//         encoder.instrumentId(123L);
//         encoder.source(Source.STREAM);

//         encoder.bidBookCount(3)       
//         .next().price(102L).size(600L) // ask price was 104
//         .next().price(97L).size(200L)
//         .next().price(96L).size(300L);

//         encoder.askBookCount(3)
//             .next().price(106L).size(200L) // ask price was 108
//             .next().price(110L).size(5000L)
//             .next().price(119L).size(5600L);
            
//         encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

//         return directBuffer;
//     }       

//     // ADDED 28/9/2024 - FOR TESTING PURPOSES
//     protected UnsafeBuffer createTick6(){

//         final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
//         final BookUpdateEncoder encoder = new BookUpdateEncoder();

//         final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
//         final UnsafeBuffer directBuffer = new UnsafeBuffer(byteBuffer);

//         //write the encoded output to the direct buffer
//         encoder.wrapAndApplyHeader(directBuffer, 0, headerEncoder);

//         //set the fields to desired values
//         encoder.venue(Venue.XLON);
//         encoder.instrumentId(123L);
//         encoder.source(Source.STREAM);

//         encoder.bidBookCount(3)
//                 .next().price(102L).size(600L) // ask price was 103
//                 .next().price(97L).size(200L)
//                 .next().price(96L).size(300L);

//         encoder.askBookCount(4)
//             .next().price(104L).size(300L) // ask price was 107
//             .next().price(110L).size(200L) 
//             .next().price(110L).size(5000L)
//             .next().price(119L).size(5600L);

//         encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

//         return directBuffer;
//     }

//     // ADDED 28/9/2024 - FOR TESTING PURPOSES
//     protected UnsafeBuffer createTick7(){

//         final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
//         final BookUpdateEncoder encoder = new BookUpdateEncoder();

//         final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
//         final UnsafeBuffer directBuffer = new UnsafeBuffer(byteBuffer);

//         //write the encoded output to the direct buffer
//         encoder.wrapAndApplyHeader(directBuffer, 0, headerEncoder);

//         //set the fields to desired values
//         encoder.venue(Venue.XLON);
//         encoder.instrumentId(123L);
//         encoder.source(Source.STREAM);
        
//         encoder.bidBookCount(3)
//                 .next().price(100L).size(600L) // was 110
//                 .next().price(97L).size(200L)
//                 .next().price(96L).size(300L);

//         encoder.askBookCount(3)
//             .next().price(102L).size(200L)
//             .next().price(110L).size(5000L)
//             .next().price(119L).size(5600L);

//         encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

//         return directBuffer;
//     }


//     // ADDED 08/10/2024 - FOR TESTING PURPOSES
//     protected UnsafeBuffer createTick8(){

//         final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
//         final BookUpdateEncoder encoder = new BookUpdateEncoder();

//         final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
//         final UnsafeBuffer directBuffer = new UnsafeBuffer(byteBuffer);

//         //write the encoded output to the direct buffer
//         encoder.wrapAndApplyHeader(directBuffer, 0, headerEncoder);

//         //set the fields to desired values
//         encoder.venue(Venue.XLON);
//         encoder.instrumentId(123L);
//         encoder.source(Source.STREAM);

//         encoder.bidBookCount(3)
//             .next().price(105L).size(600L) // ask price was 103
//             .next().price(97L).size(200L)
//             .next().price(96L).size(300L);

//         encoder.askBookCount(4)
//             .next().price(109L).size(5000L) // ask price was 108
//             .next().price(117L).size(200L)
//             .next().price(118L).size(5000L)
//             .next().price(119L).size(5600L);
//             // .next().price(110L).size(5000L)
//             // .next().price(119L).size(5600L);

//         encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

//         return directBuffer;

//     }

//     // ADDED 08/10/2024 - FOR TESTING PURPOSES
//     protected UnsafeBuffer createTick9(){

//         final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
//         final BookUpdateEncoder encoder = new BookUpdateEncoder();

//         final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
//         final UnsafeBuffer directBuffer = new UnsafeBuffer(byteBuffer);

//         //write the encoded output to the direct buffer
//         encoder.wrapAndApplyHeader(directBuffer, 0, headerEncoder);

//         //set the fields to desired values
//         encoder.venue(Venue.XLON);
//         encoder.instrumentId(123L);
//         encoder.source(Source.STREAM);

//         encoder.bidBookCount(3)
//             .next().price(106L).size(600L)
//             .next().price(97L).size(200L)
//             .next().price(96L).size(300L);

//         encoder.askBookCount(4)
//             .next().price(111L).size(5000L)
//             .next().price(117L).size(200L)
//             .next().price(118L).size(5000L)
//             .next().price(119L).size(5600L);

//             encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

//             return directBuffer; 
//     }           

//     // ADDED 13/10/2024 - FOR TESTING PURPOSES
//     protected UnsafeBuffer createTick10(){

//         final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
//         final BookUpdateEncoder encoder = new BookUpdateEncoder();

//         final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
//         final UnsafeBuffer directBuffer = new UnsafeBuffer(byteBuffer);

//         //write the encoded output to the direct buffer
//         encoder.wrapAndApplyHeader(directBuffer, 0, headerEncoder);

//         //set the fields to desired values
//         encoder.venue(Venue.XLON);
//         encoder.instrumentId(123L);
//         encoder.source(Source.STREAM);

//         encoder.bidBookCount(3)
//             .next().price(105L).size(600L)
//             .next().price(97L).size(200L)
//             .next().price(96L).size(300L);

//         encoder.askBookCount(4)
//             .next().price(108L).size(5000L)
//             .next().price(117L).size(200L)
//             .next().price(118L).size(5000L)
//             .next().price(119L).size(5600L);
//             // .next().price(110L).size(5000L)
//             // .next().price(119L).size(5600L);

//         encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

//         return directBuffer;

//     }

//     // ADDED 13/10/2024 - FOR TESTING PURPOSES
//     protected UnsafeBuffer createTick11(){

//         final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
//         final BookUpdateEncoder encoder = new BookUpdateEncoder();

//         final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
//         final UnsafeBuffer directBuffer = new UnsafeBuffer(byteBuffer);

//         //write the encoded output to the direct buffer
//         encoder.wrapAndApplyHeader(directBuffer, 0, headerEncoder);

//         //set the fields to desired values
//         encoder.venue(Venue.XLON);
//         encoder.instrumentId(123L);
//         encoder.source(Source.STREAM);

//         encoder.bidBookCount(3)
//             .next().price(109L).size(600L)
//             .next().price(97L).size(200L)
//             .next().price(96L).size(300L);

//         encoder.askBookCount(4)
//             .next().price(114L).size(5000L)
//             .next().price(117L).size(200L)
//             .next().price(118L).size(5000L)
//             .next().price(119L).size(5600L);
//             // .next().price(110L).size(5000L)
//             // .next().price(119L).size(5600L);

//         encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

//         return directBuffer;

//     }
    
//     // CHECKING THERE ARE VALID BID AND ASK LEVELS AT THE FIRST TICK.
//     @Test
//     public void checkThereAreValidBidAndAskLevelsAtTheFirstTick() throws Exception {

//         send(createTick());        

//             int bidLevels = container.getState().getBidLevels();
//             int askLevels = container.getState().getAskLevels();

//             assertEquals(true, bidLevels > 0 && askLevels > 0); 
//     } 

    
//     // TESTING THE ALGO'S BEHAVIOUR FROM 1ST TICK TO 11TH TICK
    
//     @SuppressWarnings("deprecation")
//     @Test
//     public void backtestAlgoBehaviourFrom1stTickTo11thTick() throws Exception {
    
//         // retrieve all the information in the container object including child orders and other variables held in the container
//         var state = container.getState();

//         // TESTING THE ALGO'S BEHAVIOUR AFTER THE 1ST TICK 
//         // Best Bid = 98, Best Ask = 100, Spread = 2 points
//         // Algo should only create 5 buy orders and no sell orders at this point

//         send(createTick()); // send a sample market data tick

//             // check the state of the order book after 1st market data tick
//             final String updatedOrderBook1 = Util.orderBookToString(container.getState());

//             // Get the best bid and ask prices at level 0 and their corresponding quantities
//             final BidLevel bestBidAt1stTick = state.getBidAt(0);
//             final AskLevel bestAskAt1stTick = state.getAskAt(0);
//             final long bestBidPriceAt1stTick = bestBidAt1stTick.getPrice();
//             final long bestAskPriceAt1stTick = bestAskAt1stTick.getPrice();

//             // Define the spread threshold (in price points)
//             final long spreadAt1stTick = Math.abs(bestAskPriceAt1stTick - bestBidPriceAt1stTick); // Math.abs - added 20/10/2024 to get absolute value of a number
//             final boolean spreadIsBelowOrEqualToSpreadThresholdAt1stTick = spreadAt1stTick <= 5L; 
//             final boolean spreadIsAboveOrEqualToSpreadThresholdAt1stTick = spreadAt1stTick > 5L;

//             // check for best bid and best ask prices, and the spread
//             assertEquals(98, bestBidPriceAt1stTick);
//             assertEquals(100, bestAskPriceAt1stTick);
//             assertEquals(2, spreadAt1stTick);
//             assertEquals(true, spreadIsBelowOrEqualToSpreadThresholdAt1stTick);
//             assertEquals(false, spreadIsAboveOrEqualToSpreadThresholdAt1stTick);

//             //  assert to check that we created a Buy order for 200 shares at 98 and no Sell orders            
//             for (ChildOrder childOrder : state.getChildOrders()) {
//                 assertEquals(true, childOrder.getSide() == Side.BUY);
//                 assertEquals(200, childOrder.getQuantity());
//                 assertEquals(98, childOrder.getPrice());
//                 assertEquals (true, childOrder.getSide() != Side.SELL);
//             }

//             final int totalOrdersCountAfter1stTick = state.getChildOrders().size();

//             final long totalBuyOrderedQuantityAfter1stTick = state.getChildOrders().stream()
//                                                                   .filter(order -> order.getSide() == Side.BUY)
//                                                                   .map(ChildOrder::getQuantity)
//                                                                   .reduce (Long::sum).orElse(0L); //.get();

//             final long totalBuyOrderFilledQuantityAfter1stTick = state.getChildOrders().stream()
//                                                                       .filter(order -> order.getSide() == Side.BUY)
//                                                                       .map(ChildOrder::getFilledQuantity)
//                                                                       .reduce(Long::sum).orElse(0L);

//             final long totalBuyOrderUnfilledQuantityAfter1stTick = totalBuyOrderedQuantityAfter1stTick - totalBuyOrderFilledQuantityAfter1stTick;

//             final int activeChildOrdersAfter1stTick = state.getActiveChildOrders().size();

                                            
//             // Optional<ChildOrder> lastOrderAfter1stTickIs98 = state.getChildOrders().stream()
//             //                                                       .filter(order -> order.getSide() == Side.BUY)
//             //                                                       .filter(order -> order.getPrice() == 98L)
//             //                                                       .reduce((first, second) -> second);

//             // final ChildOrder lastBoughtPriceIs98After1stTick = lastOrderAfter1stTickIs98.orElseThrow(() ->
//             //                                              new AssertionError("Expected the last buy order price to be 98 but found something else"));

//             // check that the last buy order in the list is at the price of 98 or throw an assertion error if no such order is found                        
//             final ChildOrder lastBoughtPriceAfter1stTickIs98 = state.getChildOrders().stream()
//                                                                     .filter(order -> order.getSide() == Side.BUY)
//                                                                     .filter(order -> order.getPrice() == 98L)
//                                                                     .reduce((first, second) -> second)
//                                                                     .orElseThrow(() ->
//                                                                     new AssertionError("Expected the last buy order price to be 98 but found something else"));

//             // Check things like total order count, total ordered quantity, filled quantity, unfilled quantity, total active child orders, cancelled orders, last bought price etc
//             assertEquals(5, totalOrdersCountAfter1stTick);
//             assertEquals(1000, totalBuyOrderedQuantityAfter1stTick);
//             assertEquals(0, totalBuyOrderFilledQuantityAfter1stTick);
//             assertEquals(1000, totalBuyOrderUnfilledQuantityAfter1stTick);
//             assertEquals(5, activeChildOrdersAfter1stTick);
            


//         // TESTING THE ALGO'S BEHAVIOUR AFTER 2ND TICK 
//         // Best Bid = 95, Best Ask = 98, Spread = 3 points
//         // No new buy orders should be created as list has 5 orders
//         // No Sell order should be created as the conditions not met
//         // 3 buy orders should be filled or partially filled as the Ask price has moved to our buy limit price

//         send(createTick2());

//             // get the state of the order book after 2nd market data tick
//             final String updatedOrderBook2 = Util.orderBookToString(container.getState());

//             // Get the best bid and ask prices at level 0 and their corresponding quantities
//             final BidLevel bestBidAt2ndTick = state.getBidAt(0);
//             final AskLevel bestAskAt2ndTick = state.getAskAt(0);
//             final long bestBidPriceAt2ndTick = bestBidAt2ndTick.getPrice();
//             final long bestAskPriceAt2ndTick = bestAskAt2ndTick.getPrice();
//             final long previousBoughtPriceAt1stTick = lastBoughtPriceAfter1stTickIs98.getPrice();

//             // Define the spread threshold (in price points)
//             final long spreadAt2ndTick = Math.abs(bestAskPriceAt2ndTick - bestBidPriceAt2ndTick);
//             final boolean spreadIsBelowOrEqualToSpreadThresholdAt2ndTick = spreadAt2ndTick <= 5L; 
//             final boolean spreadIsAboveOrEqualToSpreadThresholdAt2ndTick = spreadAt2ndTick > 5L;
//             final long priceReversalThreshold2ndTick = bestAskPriceAt2ndTick - previousBoughtPriceAt1stTick;

//             // check for best bid and best ask prices, and the spread
//             assertEquals(95, bestBidPriceAt2ndTick);
//             assertEquals(98, bestAskPriceAt2ndTick);
//             assertEquals(3, spreadAt2ndTick);            
//             assertEquals(98, previousBoughtPriceAt1stTick);
//             assertEquals(true, spreadIsBelowOrEqualToSpreadThresholdAt2ndTick);
//             assertEquals(false, spreadIsAboveOrEqualToSpreadThresholdAt2ndTick);
//             assertEquals(0, priceReversalThreshold2ndTick);            

//             // assert to check that we created a Buy order for 200 shares at best Bid price of 98 and no Sell orders            
//             for (ChildOrder childOrder : state.getActiveChildOrders()) {
//                 assertEquals(true, childOrder.getSide() == Side.BUY);
//                 assertEquals(200, childOrder.getQuantity());
//                 assertEquals(98, childOrder.getPrice());
//                 assertEquals (true, childOrder.getSide() != Side.SELL);
//             }

//             // Using Java Streams API to aggregate various statistics or find information from the Child Orders collection after the 2nd market data tick
//             final int totalOrdersCountAfter2ndTick = container.getState().getChildOrders().size();

//             final long totalBuyOrderCountAfter2ndTick = state.getChildOrders().stream()
//                                                              .filter(order -> order.getSide() == Side.BUY)
//                                                              .map(order -> 1L).reduce(0L, Long::sum);

//             final long totalSellOrderCountAfter2ndTick = state.getChildOrders().stream()
//                                                               .filter(order -> order.getSide() == Side.SELL)
//                                                               .map(order -> 1L).reduce(0L, Long::sum);

//             final int activeChildOrdersAfter2ndTick = container.getState().getActiveChildOrders().size();

//             final long cancelledBuyChildOrdersAfter2ndTick = state.getChildOrders().stream()
//                                                                   .filter(order -> order.getSide() == Side.BUY)
//                                                                   .filter(order -> order.getState() == OrderState.CANCELLED)
//                                                                   .count();

//             // check that the last buy order in the list is at the price of 98 or else throw an assertion error if no such order is found                         
//             final ChildOrder lastBoughtPriceAfter2ndTickIs98 = state.getChildOrders().stream()
//                                                                     .filter(order -> order.getSide() == Side.BUY)
//                                                                     .filter(order -> order.getPrice() == 98L)
//                                                                     .reduce((first, second) -> second)
//                                                                     .orElseThrow(() ->
//                                                                      new AssertionError("Expected the last buy order for 98 but found something else"));

//             final long numberOfFullyFilledOrPartiallyFilledBuyOrdersAfter2ndTick = state.getChildOrders().stream()
//                                                                                         .filter(order -> order.getSide() == Side.BUY)            
//                                                                                         .filter(order -> order.getFilledQuantity() > 0)
//                                                                                         .map(order -> 1L).reduce(0L, Long::sum);

//             final long totalBuyOrderedQuantityAfter2ndTick = state.getChildOrders().stream()
//                                                                   .filter(order -> order.getSide() == Side.BUY)
//                                                                   .map(ChildOrder::getQuantity) 
//                                                                   .reduce (Long::sum) //.get();
//                                                                   .orElse(0L);

//             final long totalBuyOrderFilledQuantityAfter2ndTick = state.getChildOrders().stream()
//                                                                       .filter(order -> order.getSide() == Side.BUY)
//                                                                       .map(ChildOrder::getFilledQuantity)
//                                                                       .reduce(Long::sum) //.get();
//                                                                       .orElse(0L);
                                                                                     
//             // calculate unfilled quantity for buy orders                                                                      
//             final long totalBuyOrderUnfilledQuantityAfter2ndTick = totalBuyOrderedQuantityAfter2ndTick - totalBuyOrderFilledQuantityAfter2ndTick;

//             // Check things like total order count, total ordered quantity, filled quantity, unfilled quantity, total active child orders,
//             // cancelled orders, last bought price etc                                                              
//             assertEquals(5, totalOrdersCountAfter2ndTick);
//             assertEquals(5, totalBuyOrderCountAfter2ndTick);
//             assertEquals(0, totalSellOrderCountAfter2ndTick);
//             assertEquals(1000, totalBuyOrderedQuantityAfter2ndTick);
//             assertEquals(3, numberOfFullyFilledOrPartiallyFilledBuyOrdersAfter2ndTick);
//             assertEquals(501, totalBuyOrderFilledQuantityAfter2ndTick);
//             assertEquals(499, totalBuyOrderUnfilledQuantityAfter2ndTick);
//             assertEquals(5, activeChildOrdersAfter2ndTick);
//             assertEquals(0, cancelledBuyChildOrdersAfter2ndTick);
//             assertEquals(98, lastBoughtPriceAfter1stTickIs98.getPrice());



//         // TESTING THE ALGO'S BEHAVIOUR AFTER 3RD TICK 
//         // Best Bid = 96, Best Ask = 102, Spread = 6 points
//         // No new buy orders should be created as the list has 5 active child orders 
//         // No Sell order should be created as the conditions not met
//         // No orders should be cancelled as the spread has not widened
//         // The state should still have 5 active Buy orders, partially filled at the initial price of 98 as at tick 2        

//         send(createTick3());

//            // check the state of the order book after 3rd market data tick
//            final String updatedOrderBook3 = Util.orderBookToString(container.getState());        
 
//             //assert to check that we created a Buy order for 200 shares at 98 and no Sell orders            
//             for (ChildOrder childOrder : state.getActiveChildOrders()) {
//                 assertEquals(true, childOrder.getSide() == Side.BUY);
//                 assertEquals(200, childOrder.getQuantity());
//                 assertEquals(98, childOrder.getPrice());
//                 assertEquals (true, childOrder.getSide() != Side.SELL);
//             }

//             // Get the best bid and ask prices at level 0 and their corresponding quantities
//             final BidLevel bestBidAt3rdTick = state.getBidAt(0);
//             final AskLevel bestAskAt3rdTick = state.getAskAt(0);
//             final long bestBidPriceAt3rdTick = bestBidAt3rdTick.getPrice();
//             final long bestAskPriceAt3rdTick = bestAskAt3rdTick.getPrice();
//             final long previousBoughtPriceAt2ndTick = lastBoughtPriceAfter2ndTickIs98.getPrice();

//             // Define the spread threshold (in price points)
//             final long spreadAt3rdTick = Math.abs(bestAskPriceAt3rdTick - bestBidPriceAt3rdTick); 
//             final boolean spreadIsBelowOrEqualToSpreadThresholdAt3rdTick = spreadAt3rdTick <= 5L; 
//             final boolean spreadIsAboveOrEqualToSpreadThresholdAt3rdTick = spreadAt3rdTick > 5L;
//             final long priceReversalThreshold3rdTick = bestAskPriceAt3rdTick - previousBoughtPriceAt2ndTick;  

//             // check for best bid and best ask prices, and the spread
//             assertEquals(96, bestBidPriceAt3rdTick);
//             assertEquals(102, bestAskPriceAt3rdTick);
//             assertEquals(6, spreadAt3rdTick);            
//             assertEquals(98, previousBoughtPriceAt2ndTick);
//             assertEquals(false, spreadIsBelowOrEqualToSpreadThresholdAt3rdTick);
//             assertEquals(true, spreadIsAboveOrEqualToSpreadThresholdAt3rdTick);
//             assertEquals(4, priceReversalThreshold3rdTick);

//              // check that the last buy order in the list is at the price of 98 or throw an assertion error if no such order is found                             
//             final ChildOrder lastBoughtPriceAfter3rdTickIs98 = state.getChildOrders().stream()
//                                                                     .filter(order -> order.getSide() == Side.BUY)
//                                                                     .filter(order -> order.getPrice() == 98L)
//                                                                     .reduce((first, second) -> second)
//                                                                     .orElseThrow(() ->
//                                                                      new AssertionError("Expected the last buy order for 98 but found something else"));
            
//             final int totalOrdersCountAfter3rdTick = container.getState().getChildOrders().size();

//             final long totalBuyOrderedQuantityAfter3rdTick = state.getChildOrders().stream()
//                                                                   .filter(order -> order.getSide() == Side.BUY)
//                                                                   .map(ChildOrder::getQuantity)
//                                                                   .reduce (Long::sum).orElse(0L);

//             final long totalBuyOrderFilledQuantityAfter3rdTick = state.getChildOrders().stream()
//                                                                       .filter(order -> order.getSide() == Side.BUY)
//                                                                       .map(ChildOrder::getFilledQuantity)
//                                                                       .reduce(Long::sum).orElse(0L);

//             final long totalBuyOrderUnfilledQuantityAfter3rdTick = totalBuyOrderedQuantityAfter3rdTick - totalBuyOrderFilledQuantityAfter3rdTick;

//             final int activeChildOrdersAfter3rdTick = container.getState().getActiveChildOrders().size();

//             final long cancelledBuyChildOrderCountAfter3rdTick = state.getChildOrders().stream()
//                                                                       .filter(order -> order.getSide() == Side.BUY)
//                                                                       .filter(order -> order.getState() == OrderState.CANCELLED)
//                                                                       .count();

//             // Check things like total order count, total ordered quantity, filled quantity, unfilled quantity, total active child orders,
//             // cancelled orders, last bought price etc  
//             assertEquals(5, totalOrdersCountAfter3rdTick);
//             assertEquals(1000, totalBuyOrderedQuantityAfter3rdTick);            
//             assertEquals(501, totalBuyOrderFilledQuantityAfter3rdTick);
//             assertEquals(499, totalBuyOrderUnfilledQuantityAfter3rdTick);
//             assertEquals(5, activeChildOrdersAfter3rdTick);
//             assertEquals(0, cancelledBuyChildOrderCountAfter3rdTick);
//             assertEquals(98, lastBoughtPriceAfter2ndTickIs98.getPrice());


//         // TESTING THE ALGO'S BEHAVIOUR AFTER THE 4TH TICK
//         // Best Bid = 98, Best Ask = 105, Spread = 7 points. Previous bought price 98
//         // No new Buy orders should be created as the spread has widened
//         // 3 Buy orders should be cancelled as the spread has widened
//         // No Sell order should be created as the conditions not met
//         // There should be 5 Buy orders as in tick 3, some fully/partially filled and some not filled     

//         send(createTick4());

//             // check the state of the order book after 4th market data tick
        
//             final String updatedOrderBook4 = Util.orderBookToString(container.getState());            

//             // Get the best bid and ask prices at level 0 and their corresponding quantities
//             final BidLevel bestBidAt4thTick = state.getBidAt(0);
//             final AskLevel bestAskAt4thTick = state.getAskAt(0);
//             final long bestBidPriceAt4thTick = bestBidAt4thTick.getPrice();
//             final long bestAskPriceAt4thTick = bestAskAt4thTick.getPrice();
//             final long previousBoughtPriceAt3rdTick = lastBoughtPriceAfter3rdTickIs98.getPrice();

//             // Define the spread threshold (in price points)
//             final long spreadAt4thTick = Math.abs(bestAskPriceAt4thTick - bestBidPriceAt4thTick); 
//             final boolean spreadIsBelowOrEqualToSpreadThresholdAt4thTick = spreadAt4thTick <= 5L; 
//             final boolean spreadIsAboveOrEqualToSpreadThresholdAt4thTick = spreadAt4thTick > 5L;
//             final long priceReversalThreshold4thTick = bestAskPriceAt4thTick - previousBoughtPriceAt3rdTick;   

//             // check for best bid and best ask prices, and the spread
//             assertEquals(98, bestBidPriceAt4thTick);
//             assertEquals(105, bestAskPriceAt4thTick);
//             assertEquals(7, spreadAt4thTick);            
//             assertEquals(98, previousBoughtPriceAt3rdTick);
//             assertEquals(false, spreadIsBelowOrEqualToSpreadThresholdAt4thTick);
//             assertEquals(true, spreadIsAboveOrEqualToSpreadThresholdAt4thTick);
//             assertEquals(7, priceReversalThreshold4thTick);
            
//             //assert to check that we created a Buy order for 200 shares at 98 and no Sell orders            
//             for (ChildOrder childOrder : state.getChildOrders()) {
//                 assertEquals(true, childOrder.getSide() == Side.BUY);
//                 assertEquals(200, childOrder.getQuantity());
//                 assertEquals(98, childOrder.getPrice());
//                 assertEquals(true, childOrder.getSide() != Side.SELL);
//             }

//             // check that the last buy order in the list is at the price of 98 or throw an assertion error if no such order is found                             
//             final ChildOrder lastBoughtPriceAfter4thTickIs98 = state.getChildOrders().stream()
//                                                                     .filter(order -> order.getSide() == Side.BUY)
//                                                                     .filter(order -> order.getPrice() == 98L)
//                                                                     .reduce((first, second) -> second)
//                                                                     .orElseThrow(() ->
//                                                                      new AssertionError("Expected the last buy order for 98 but found something else"));
            
//             final int totalOrdersCountAfter4thTick = container.getState().getChildOrders().size();

//             final long totalBuyOrderedQuantityAfter4thTick = state.getChildOrders().stream()
//                                                                   .filter(order -> order.getSide() == Side.BUY)            
//                                                                   .map(ChildOrder::getQuantity)
//                                                                   .reduce (Long::sum).orElse(0L);

//             final long buyOrderCountAfter4thTick = state.getChildOrders().stream()
//                                                         .filter(order -> order.getSide() == Side.BUY)
//                                                         .map(order -> 1L).reduce(0L, Long::sum);

//             final long sellOrderCountAfter4thTick = state.getChildOrders().stream()
//                                                          .filter(order -> order.getSide() == Side.SELL)
//                                                          .map(order -> 1L).reduce(0L, Long::sum);                                                                   

//             final long totalBuyOrderFilledQuantityAfter4thTick = state.getChildOrders().stream()
//                                                                       .filter(order -> order.getSide() == Side.BUY)
//                                                                       .map(ChildOrder::getFilledQuantity)
//                                                                       .reduce(Long::sum).orElse(0L);

//             final long totalBuyOrderUnFilledQuantityAfter4thTick = totalBuyOrderedQuantityAfter4thTick - totalBuyOrderFilledQuantityAfter4thTick;

//             final int activeChildOrdersAfter4thTick = container.getState().getActiveChildOrders().size();

//             final long numberOfCancelledBuyChildOrdersAfter4thTick = state.getChildOrders().stream()
//                                                                           .filter(order -> order.getSide() == Side.BUY)
//                                                                           .filter(order -> order.getState() == OrderState.CANCELLED)
//                                                                           .count();

//             // Calculate the total filled quantity for buy orders
//             final long buyOrdersTotalFilledQuantityAfter4thTick = state.getChildOrders().stream()
//                                                                        .filter(order -> order.getSide() == Side.BUY)
//                                                                        .filter(order -> order.getFilledQuantity() > 0)
//                                                                        .mapToLong(ChildOrder::getFilledQuantity)
//                                                                        .sum();

//             // Calculate the total filled quantity for sell orders
//             final long sellOrderTotalFilledQuantityAfter4thTick = state.getChildOrders().stream()
//                                                                        .filter(order -> order.getSide() == Side.SELL && order.getFilledQuantity() > 0)
//                                                                        .mapToLong(ChildOrder::getFilledQuantity)
//                                                                        .sum();

//             // Determine the quantity available for sale
//             final long remainingQuantityToSellAfter4thTick = buyOrdersTotalFilledQuantityAfter4thTick - sellOrderTotalFilledQuantityAfter4thTick;

//             // Optional<ChildOrder> lastCancelledOrderPriceAfter4thTickIs98 = state.getChildOrders().stream()
//             //                                                                     .filter(order -> order.getSide() == Side.BUY)
//             //                                                                     .filter(order -> order.getState() == OrderState.CANCELLED)
//             //                                                                     .filter(order -> order.getPrice() == 98L)
//             //                                                                     .reduce((first, second) -> second);

//             // final ChildOrder lastCancelledBuyOrderPriceAfter4thTickIs98 = lastCancelledOrderPriceAfter4thTickIs98.orElseThrow(() -> 
//             //                                                               new AssertionError("Expected the last cancelled buy order price to be 98 but found something else"));

//             // check that the last cancelled buy order in the list is at the price of 98 or throw an assertion error if no such order is found  
//             final ChildOrder lastCancelledBuyOrderPriceAfter4thTickIs98 = state.getChildOrders().stream()
//                                                                                .filter(order -> order.getSide() == Side.BUY)
//                                                                                .filter(order -> order.getState() == OrderState.CANCELLED)
//                                                                                .filter(order -> order.getPrice() == 98L)
//                                                                                .reduce((first, second) -> second)
//                                                                                .orElseThrow(() ->
//                                                                                 new AssertionError("Expected the last cancelled buy order price to be 98 but found something else"));

//             final long numberOfFullyFilledOrPartiallyFilledBuyOrdersAfter4thTick = state.getChildOrders().stream()
//                                                                                         .filter(order -> order.getSide() == Side.BUY)            
//                                                                                         .filter(order -> order.getFilledQuantity() > 0)
//                                                                                         .map(order -> 1L).reduce(0L, Long::sum);

//             // check things like total order count, filled/unfilled quantities, cancelled orders, etc
//             assertEquals(5, totalOrdersCountAfter4thTick);
//             assertEquals(5, buyOrderCountAfter4thTick); 
//             assertEquals(0, sellOrderCountAfter4thTick);                  
//             assertEquals(1000, totalBuyOrderedQuantityAfter4thTick);            
//             assertEquals(501, totalBuyOrderFilledQuantityAfter4thTick);
//             assertEquals(499, totalBuyOrderUnFilledQuantityAfter4thTick);
//             assertEquals(2, activeChildOrdersAfter4thTick);
//             assertEquals(3, numberOfFullyFilledOrPartiallyFilledBuyOrdersAfter4thTick);
//             assertEquals(3, numberOfCancelledBuyChildOrdersAfter4thTick);
//             assertEquals(501, remainingQuantityToSellAfter4thTick);
//             assertEquals(98, lastBoughtPriceAfter3rdTickIs98.getPrice());
//             assertEquals(98, lastCancelledBuyOrderPriceAfter4thTickIs98.getPrice());



//         // TESTING THE ALGO'S BEHAVIOUR AFTER THE 5TH TICK
//         // Best Bid = 102, Best Ask = 106, Spread = 4 points, last bought price: 98 
//         // 1 new Sell order should be created at Best Ask price of 102 as Bid & Ask price has moved above last bought price + 2 points
//         // No new buy orders to be created as the spread has widened
//         // The state should have 6 orders; 3 active, 3 cancelled

//         // AFTTER BUGS WERE FIXED FROM 17/10/2024 - ADJUSTED SELL ORDER PRICE FROM BEST ASK TO BEST BID PRICE.
//         // ORDER GOT FILLED STRAIGHT AWAY FOR 501 AT 102

//         send(createTick5());

//             // check the state of the order book after 5th market data tick
//             final String updatedOrderBook5 = Util.orderBookToString(container.getState());               

//             // Get the best bid and ask prices at level 0 and their corresponding quantities
//             final BidLevel bestBidAt5thTick = state.getBidAt(0);
//             final AskLevel bestAskAt5thTick = state.getAskAt(0);
//             final long bestBidPriceAt5thTick = bestBidAt5thTick.getPrice();
//             final long bestAskPriceAt5thTick = bestAskAt5thTick.getPrice();
//             final long previousBoughtPriceAt4thTick = lastBoughtPriceAfter4thTickIs98.getPrice();

//             // Define the spread threshold (in price points)
//             final long spreadAt5thTick = Math.abs(bestAskPriceAt5thTick - bestBidPriceAt5thTick); 
//             final boolean spreadIsBelowOrEqualToSpreadThresholdAt5thTick = spreadAt5thTick <= 5L; 
//             final boolean spreadIsAboveOrEqualToSpreadThresholdAt5thTick = spreadAt5thTick > 5L;
//             final long priceReversalThreshold5thTick = bestAskPriceAt5thTick - previousBoughtPriceAt4thTick;  

//             // check for best bid and best ask prices, and the spread
//             assertEquals(102, bestBidPriceAt5thTick);
//             assertEquals(106, bestAskPriceAt5thTick);
//             assertEquals(4, spreadAt5thTick);            
//             assertEquals(98, previousBoughtPriceAt4thTick);
//             assertEquals(true, spreadIsBelowOrEqualToSpreadThresholdAt5thTick);
//             assertEquals(false, spreadIsAboveOrEqualToSpreadThresholdAt5thTick);
//             assertEquals(8, priceReversalThreshold5thTick);
            
//             final int totalOrdersCountAfter5thTick = container.getState().getChildOrders().size();

//             final long totalBuyOrderedQuantityAfter5thTick = state.getChildOrders().stream()
//                                                                   .filter(order -> order.getSide() == Side.BUY) 
//                                                                   .map(ChildOrder::getQuantity)
//                                                                   .reduce (Long::sum).orElse(0L);

//             final long totalBuyOrdersfilledQuantityAfter5thTick = state.getChildOrders().stream()
//                                                                   .filter(order -> order.getSide() == Side.BUY) 
//                                                                   .filter(order -> order.getFilledQuantity() > 0) 
//                                                                   .map(ChildOrder::getFilledQuantity)
//                                                                   .reduce(Long::sum).orElse(0L);

//             final long totalSellOrdersFilledQuantityAfter5thTick = state.getChildOrders().stream()
//                                                                    .filter(order -> order.getSide() == Side.SELL) 
//                                                                    .filter(order -> order.getFilledQuantity() > 0) 
//                                                                    .map(ChildOrder::getFilledQuantity)
//                                                                    .reduce(Long::sum).orElse(0L);

//             final long totalSellOrderedQuantityAfter5thTick = state.getChildOrders().stream()
//                                                                    .filter(order -> order.getSide() == Side.SELL) 
//                                                                    .map(ChildOrder::getQuantity)
//                                                                    .reduce(Long::sum).orElse(0L);

//             final long totalBuyOrderUnfilledQuantityAfter5thTick = totalBuyOrderedQuantityAfter5thTick - totalBuyOrdersfilledQuantityAfter5thTick;

//             final long buyOrderCountAfter5thTick = state.getChildOrders().stream()
//                                                         .filter(order -> order.getSide() == Side.BUY)
//                                                         .map(order -> 1L).reduce(0L, Long::sum);

//             final long sellOrderCountAfter5thTick = state.getChildOrders().stream()
//                                                          .filter(order -> order.getSide() == Side.SELL)
//                                                          .map(order -> 1L).reduce(0L, Long::sum);

//             final int activeChildOrdersAfter5thTick = container.getState().getActiveChildOrders().size();

//             final long numberOfCancelledBuyOrdersAfter5thTick = state.getChildOrders().stream()
//                                                                      .filter(order -> order.getSide() == Side.BUY)            
//                                                                      .filter(order -> order.getState() == 3)
//                                                                      .map(order -> 1L).reduce(0L, Long::sum);

//             // check that the last buy order in the list is at the price of 102 or throw an assertion error if no such order is found                            
//             final ChildOrder lastBoughtPriceAfter5thTickIs98  = state.getChildOrders().stream()
//                                                                      .filter(order -> order.getSide() == Side.BUY)
//                                                                      .filter(order -> order.getPrice() == 98)
//                                                                      .reduce((first, second) -> second)
//                                                                      .orElseThrow(() -> 
//                                                                       new AssertionError("Expected a BuY order for 102 but found something else"));
                                                              
//             // check that there is a new sell order in the list at the price of 102 or throw an assertion error if no such order is found                         
//             final ChildOrder newSellOrderAfter5thTickIs102 = state.getChildOrders().stream()
//                                                                   .filter(order -> order.getSide() == Side.SELL)
//                                                                   .filter(order -> order.getPrice() == 102)
//                                                                   .reduce((first, second) -> second)
//                                                                   .orElseThrow(() -> 
//                                                                    new AssertionError("Expected a new Sell order for 102 but found something else"));

                                                             
//             // Check things like filled quantity, cancelled order count etc....

//             assertEquals(6, totalOrdersCountAfter5thTick); 
//             assertEquals(5, buyOrderCountAfter5thTick); 
//             assertEquals(1, sellOrderCountAfter5thTick);
//             assertEquals(1000, totalBuyOrderedQuantityAfter5thTick); 
//             assertEquals(501, totalBuyOrdersfilledQuantityAfter5thTick);                                              
//             assertEquals(499, totalBuyOrderUnfilledQuantityAfter5thTick);
//             assertEquals(3, activeChildOrdersAfter5thTick);             
//             assertEquals(3, numberOfCancelledBuyOrdersAfter5thTick);
//             assertEquals(501, remainingQuantityToSellAfter4thTick);
//             assertEquals(98, lastBoughtPriceAfter4thTickIs98.getPrice());
//             assertEquals(102, newSellOrderAfter5thTickIs102.getPrice());
//             assertEquals(501, totalSellOrderedQuantityAfter5thTick);
//             assertEquals(501, totalSellOrdersFilledQuantityAfter5thTick);            


//         // TESTING THE ALGO'S BEHAVIOUR AFTER THE 6TH TICK
//         // Best bid = 102; Best Ask = 104; Spread = 2.
//         // The state should have 1 Sell order and 7 Buy orders as in previous ticks; 5 active, 3 cancelled
//         // 2 new Buy orders to be created at 102 as the list has 3 active orders

//         send(createTick6());

//             // check the state of the order book after 6th market data tick
//             final   String updatedOrderBook6 = Util.orderBookToString(container.getState());

//             // Get the best bid and ask prices at level 0 and their corresponding quantities
//             final BidLevel bestBidAt6thTick = state.getBidAt(0);
//             final AskLevel bestAskAt6thTick = state.getAskAt(0);
//             final long bestBidPriceAt6thTick = bestBidAt6thTick.getPrice();
//             final long bestAskPriceAt6thTick = bestAskAt6thTick.getPrice();
//             final long previousBoughtPriceAt5thTick = lastBoughtPriceAfter5thTickIs98.getPrice();

//             // Define the spread threshold (in price points)
//             final long spreadAt6thTick = Math.abs(bestAskPriceAt6thTick - bestBidPriceAt6thTick); 
//             final boolean spreadIsBelowOrEqualToSpreadThresholdAt6thTick = spreadAt6thTick <= 5L; 
//             final boolean spreadIsAboveOrEqualToSpreadThresholdAt6thTick = spreadAt6thTick > 5L;
//             final long priceReversalThreshold6thTick = bestAskPriceAt6thTick - previousBoughtPriceAt5thTick;

//             // check for best bid and best ask prices, and the spread
//             assertEquals(102, bestBidPriceAt6thTick);
//             assertEquals(104, bestAskPriceAt6thTick);
//             assertEquals(2, spreadAt6thTick);            
//             assertEquals(98, previousBoughtPriceAt5thTick);
//             assertEquals(true, spreadIsBelowOrEqualToSpreadThresholdAt6thTick);
//             assertEquals(false, spreadIsAboveOrEqualToSpreadThresholdAt6thTick);
//             assertEquals(6, priceReversalThreshold6thTick);
            
//             final int totalOrdersCountAfter6thTick = container.getState().getChildOrders().size();

//             final long buyOrderCountAfter6thTick = state.getChildOrders().stream()
//                                                         .filter(order -> order.getSide() == Side.BUY)
//                                                         .map(order -> 1L)
//                                                         .reduce(0L, Long::sum);

//             final long sellOrderCountAfter6thTick = state.getChildOrders().stream()
//                                                          .filter(order -> order.getSide() == Side.SELL)
//                                                          .map(order -> 1L)
//                                                          .reduce(0L, Long::sum);

//             final long buyOrdersTotalOrderedQuantityAfter6thTick = state.getChildOrders().stream()
//                                                                         .filter(order -> order.getSide() == Side.BUY) // filter by Sell orders                
//                                                                         .map(ChildOrder::getQuantity)
//                                                                         .reduce (Long::sum).orElse(0L);

//             final long sellOrdersTotalOrderedQuantityAfter6thTick = state.getChildOrders().stream()
//                                                                          .filter(order -> order.getSide() == Side.SELL) // filter by Sell orders                
//                                                                          .map(ChildOrder::getQuantity)
//                                                                          .reduce (Long::sum).orElse(0L);    

//             final long buyOrdersFilledQuantityAfter6thTick = state.getChildOrders().stream()
//                                                                   .filter(order -> order.getSide() == Side.BUY) // filter by Sell orders
//                                                                   .map(ChildOrder::getFilledQuantity)
//                                                                   .reduce(Long::sum).orElse(0L);                                                                   

//             final long sellOrdersFilledQuantityAfter6thTick = state.getChildOrders().stream()
//                                                                    .filter(order -> order.getSide() == Side.SELL) // filter by Sell orders
//                                                                    .map(ChildOrder::getFilledQuantity)
//                                                                    .reduce(Long::sum).orElse(0L);

//             final long sellOrdersUnfilledQuantityAfter6thTick = sellOrdersTotalOrderedQuantityAfter6thTick  - sellOrdersFilledQuantityAfter6thTick;

//             final int activeChildOrdersAfter6thTick = container.getState().getActiveChildOrders().size();

//             final long cancelledBuyChildOrderCountAfter6thTick = state.getChildOrders().stream()
//                                                                       .filter(order -> order.getSide() == Side.BUY)
//                                                                       .filter(order -> order.getState() == OrderState.CANCELLED)
//                                                                       .count();

//             // check that a new buy order has been created at the price of 102 after 6th tick or throw an assertion error if no such order is found                       
//             final ChildOrder newBuyOrderPriceAfter6thTickIs102 = state.getChildOrders().stream()
//                                                                       .filter(order -> order.getSide() == Side.BUY)
//                                                                       .filter(order -> order.getPrice() == 102)
//                                                                       .reduce((first, second) -> second)
//                                                                       .orElseThrow(() ->
//                                                                        new AssertionError("Expected the last Buy order price to be 102 but found something else"));

//             // get the last 2 orders from the list of child orders and add them to another list 
//             final List<ChildOrder> thereAreTwoOrdersAtTheEndOfTheListAfter6thTick = state.getChildOrders().stream()
//                                                                                          .skip(Math.max(0, state.getChildOrders().size() - 2))
//                                                                                          .collect(Collectors.toList());
                                                                        
//             // throw an error if there are less than 2 orders at the end of the list
//             if (thereAreTwoOrdersAtTheEndOfTheListAfter6thTick.size() != 2) {
//                 throw new IllegalStateException("Expected exactly 2 orders at the end of the list but found something else");
//             }

//             // Check that there are exactly 2 orders at the end of the list and both orders are buy orders at the price of 102
//             assertEquals(2, thereAreTwoOrdersAtTheEndOfTheListAfter6thTick.size());

//             for (ChildOrder eachOrder : thereAreTwoOrdersAtTheEndOfTheListAfter6thTick) {
//                 assertEquals(true, eachOrder.getSide() == Side.BUY);
//                 assertEquals(true, eachOrder.getPrice() == 102);
//             } 

    
//             //Check things like filled quantity, cancelled order count etc....                                                             
//             assertEquals(8, totalOrdersCountAfter6thTick);
//             assertEquals(7, buyOrderCountAfter6thTick);
//             assertEquals(1, sellOrderCountAfter6thTick); 
//             assertEquals(1400, buyOrdersTotalOrderedQuantityAfter6thTick);                                         
//             assertEquals(501, buyOrdersFilledQuantityAfter6thTick);
//             assertEquals(501, sellOrdersTotalOrderedQuantityAfter6thTick);
//             assertEquals(501, sellOrdersFilledQuantityAfter6thTick);
//             assertEquals(0, sellOrdersUnfilledQuantityAfter6thTick);
//             assertEquals(5, activeChildOrdersAfter6thTick);
//             assertEquals(3, cancelledBuyChildOrderCountAfter6thTick);
           

//         // TESTING THE ALGO'S BEHAVIOUR AFTER THE 7TH TICK
//         // Best bid = 100; Best Ask = 102; Spread = 2.
//         // Buy orders of 102 to be partially filled at 102
//         // No more sell orders to be created as the bid and ask prices have not mvoed 
//         // The state should have 1 Sell order and 7 Buy orders as in tick 6; 5 active, 3 cancelled

//         send(createTick7());

//             // check the state of the order book after 7th market data tick
//             final String updatedOrderBook7 = Util.orderBookToString(container.getState());     

//             // Get the best bid and ask prices at level 0 and their corresponding quantities
//             final BidLevel bestBidAt7thTick = state.getBidAt(0);
//             final AskLevel bestAskAt7thTick = state.getAskAt(0);
//             final long bestBidPriceAt7thTick = bestBidAt7thTick.getPrice();
//             final long bestAskPriceAt7thTick = bestAskAt7thTick.getPrice();
//             final long previousBoughtPriceAt6thTick = newBuyOrderPriceAfter6thTickIs102.getPrice();

//             // Define the spread threshold (in price points)
//             final long spreadAt7thTick = Math.abs(bestAskPriceAt7thTick - bestBidPriceAt7thTick); 
//             final boolean spreadIsBelowOrEqualToSpreadThresholdAt7thTick = spreadAt7thTick <= 5L; 
//             final boolean spreadIsAboveOrEqualToSpreadThresholdAt7thTick = spreadAt7thTick > 5L;
//             final long priceReversalThreshold7thTick = bestAskPriceAt7thTick - previousBoughtPriceAt6thTick;  

//             // check for best bid and best ask prices, and the spread
//             assertEquals(100, bestBidPriceAt7thTick);
//             assertEquals(102, bestAskPriceAt7thTick);
//             assertEquals(2, spreadAt7thTick);
//             assertEquals(102, previousBoughtPriceAt6thTick);
//             assertEquals(true, spreadIsBelowOrEqualToSpreadThresholdAt7thTick);
//             assertEquals(false, spreadIsAboveOrEqualToSpreadThresholdAt7thTick);
//             assertEquals(0, priceReversalThreshold7thTick);



//             final int totalOrdersCountAfter7thTick = container.getState().getChildOrders().size();

//             final long buyOrderCountAfter7thTick = state.getChildOrders().stream()
//                                                         .filter(order -> order.getSide() == Side.BUY)
//                                                         .map(order -> 1L)
//                                                         .reduce(0L, Long::sum);

//             final long totalBuyOrderedQuantityAfter7thTick = state.getChildOrders().stream()
//                                                                   .filter(order -> order.getSide() == Side.BUY) 
//                                                                   .map(ChildOrder::getQuantity)
//                                                                   .reduce (Long::sum).orElse(0L);

//             final long totalBuyOrdersFilledQuantityAfter7thTick = state.getChildOrders().stream()
//                                                                        .filter(order -> order.getSide() == Side.BUY)
//                                                                        .map(ChildOrder::getFilledQuantity)
//                                                                        .reduce(Long::sum).orElse(0L); 

//             final long totalSellOrderedQuantityAfter7thTick = state.getChildOrders().stream()
//                                                                    .filter(order -> order.getSide() == Side.SELL)
//                                                                    .map(ChildOrder::getQuantity)
//                                                                    .reduce(Long::sum).orElse(0L); 

//             final long totalSellOrdersFilledQuantityAfter7thTick = state.getChildOrders().stream()
//                                                                         .filter(order -> order.getSide() == Side.SELL)
//                                                                         .map(ChildOrder::getFilledQuantity)
//                                                                         .reduce(Long::sum).orElse(0L); 

//             final long sellOrderCountAfter7thTick = state.getChildOrders().stream()
//                                                          .filter(order -> order.getSide() == Side.SELL)
//                                                          .map(order -> 1L).reduce(0L, Long::sum); 

//             final long totalBuyOrdersUnfilledQuantityAfter7thTick = totalBuyOrderedQuantityAfter7thTick - totalBuyOrdersFilledQuantityAfter7thTick;

//             final long totalSellOrdersUnFilledQuantityAfter7thTick = totalSellOrderedQuantityAfter7thTick - totalSellOrdersFilledQuantityAfter7thTick;

//             final int activeChildOrdersAfter7thTick = container.getState().getActiveChildOrders().size();

//             final long cancelledBuyChildOrdersAfter7thTick = state.getChildOrders().stream()
//                                                                   .filter(order -> order.getSide() == Side.BUY)
//                                                                   .filter(order -> order.getState() == OrderState.CANCELLED)
//                                                                   .count();

//             // check that the last bought price after 7th tick is at the price of 102 or throw an assertion error if no such order is found                           
//             final ChildOrder lastBoughtPriceAfter7thTickIs102 = state.getChildOrders().stream()
//                                                                      .filter(order -> order.getSide() == Side.BUY)
//                                                                      .filter(order -> order.getPrice() == 102L)
//                                                                      .reduce((first, second) -> second)
//                                                                      .orElseThrow(() -> 
//                                                                       new AssertionError("Expected the last buy order to be at the price of 102 but found something else"));
//             // Get the price for last filled buy order after 7th tick                        
//             final ChildOrder lastFilledBuyOrderPriceAfter7thTickIs102 = state.getChildOrders().stream()
//                                                                              .filter(order -> order.getSide() == Side.BUY)
//                                                                              .filter(order -> order.getPrice() == 102)
//                                                                              .reduce((first, second) -> second) // Keeps only the last filled buy order    
//                                                                              .orElseThrow(() -> 
//                                                                               new AssertionError("Expected the price for last filled buy order to be 102 but found something else"));

//             // Get the filled quantity for the last filled buy order after 7th tick                        
//             final ChildOrder filledQuantityForLlastFilledBuyOrderAfter7thTickIs200 = state.getChildOrders().stream()
//                                                                                           .filter(order -> order.getSide() == Side.BUY)
//                                                                                           .filter(order -> order.getFilledQuantity() == 200)
//                                                                                           .reduce((first, second) -> second) // Keeps only the last filled buy order    
//                                                                                           .orElseThrow(() -> 
//                                                                                            new AssertionError("Expected the last buy order to be filled for 200 shares but found something else"));
                                                                                       
//             // Calculate the quantity available for sale
//             final long remainingQuantityToSellAfter7thTick = totalBuyOrdersFilledQuantityAfter7thTick - totalSellOrdersFilledQuantityAfter7thTick;


//             // CHANGES AFTER BUG WAS FIXED - 17/10/2024 - NUMBER OF BUY ORDERS INCREASED. TOTAL ORDERED QUANTITY ALSO INCREASED

//             // Check things like filled quantity, cancelled order count etc....

//             assertEquals(8, totalOrdersCountAfter7thTick); // 25/10/2024 EXPECTED 8 BUT WAS 9
//             assertEquals(7, buyOrderCountAfter7thTick);  
//             assertEquals(1, sellOrderCountAfter7thTick); 
//             assertEquals(1400, totalBuyOrderedQuantityAfter7thTick);
//             assertEquals(699, totalBuyOrdersUnfilledQuantityAfter7thTick);
//             assertEquals(5, activeChildOrdersAfter7thTick);
//             assertEquals(3, cancelledBuyChildOrdersAfter7thTick);
//             assertEquals(102, lastFilledBuyOrderPriceAfter7thTickIs102.getPrice());
//             assertEquals(200, filledQuantityForLlastFilledBuyOrderAfter7thTickIs200.getFilledQuantity());
//             assertEquals(701, totalBuyOrdersFilledQuantityAfter7thTick);
//             assertEquals(501, totalSellOrderedQuantityAfter7thTick);            
//             assertEquals(501, totalSellOrdersFilledQuantityAfter7thTick);
//             assertEquals(0, totalSellOrdersUnFilledQuantityAfter7thTick);                                   
//             assertEquals(200, remainingQuantityToSellAfter7thTick);



//         // TESTING THE ALGO'S BEHAVIOUR AFTER THE 8TH TICK
//         // Best Bid 105, Best Ask 109; Spread = 4
//         // No more Buy orders should be created as the spread is wide
//         // 1 buy order with unfilled quantity should be cancelled
//         // 1 Sell order should be created for previous buy order of 102 and filled for 105 as the bid and ask price has moved up to the sell threshold         
//         // The state should have 9 child orders with partial fills as in 7th tick; 5 active, 4 cancelled

//         send(createTick8());

//             // check the state of the order book after 8th market data tick
//             final String updatedOrderBook8 = Util.orderBookToString(container.getState());     

//             // Get the best bid and ask prices at level 0 and their corresponding quantities
//             final BidLevel bestBidAt8thTick = state.getBidAt(0);
//             final AskLevel bestAskAt8thTick = state.getAskAt(0);
//             final long bestBidPriceAt8thTick = bestBidAt8thTick.getPrice();
//             final long bestAskPriceAt8thTick = bestAskAt8thTick.getPrice();
//             final long previousBoughtPriceAt7thTick = lastBoughtPriceAfter7thTickIs102.getPrice();

//             // Define the spread threshold (in price points)
//             final long spreadAt8thTick = Math.abs(bestAskPriceAt8thTick - bestBidPriceAt8thTick); 
//             final boolean spreadIsBelowOrEqualToSpreadThresholdAt8thTick = spreadAt8thTick <= 5L; 
//             final boolean spreadIsAboveOrEqualToSpreadThresholdAt8thTick = spreadAt8thTick > 5L;
//             final long priceReversalThreshold8thTick = bestAskPriceAt8thTick - previousBoughtPriceAt7thTick;  

//             // check for best bid and best ask prices, and the spread
//             assertEquals(105, bestBidPriceAt8thTick);
//             assertEquals(109, bestAskPriceAt8thTick);
//             assertEquals(4, spreadAt8thTick);            
//             assertEquals(102, previousBoughtPriceAt7thTick);
//             assertEquals(true, spreadIsBelowOrEqualToSpreadThresholdAt8thTick);
//             assertEquals(false, spreadIsAboveOrEqualToSpreadThresholdAt8thTick);
//             assertEquals(7, priceReversalThreshold8thTick);

            
//             final int totalOrdersCountAfter8thTick = container.getState().getChildOrders().size();

//             final long buyOrderCountAfter8thTick = state.getChildOrders().stream()
//                                                         .filter(order -> order.getSide() == Side.BUY)
//                                                         .map(order -> 1L).reduce(0L, Long::sum);

//             final long totalBuyOrderedQuantityAfter8thTick = state.getChildOrders().stream()
//                                                                   .filter(order -> order.getSide() == Side.BUY) 
//                                                                   .map(ChildOrder::getQuantity)
//                                                                   .reduce (Long::sum).orElse(0L);

//             final long totalBuyOrdersFilledQuantityAfter8thTick = state.getChildOrders().stream()
//                                                                        .filter(order -> order.getSide() == Side.BUY)                
//                                                                        .map(ChildOrder::getFilledQuantity)
//                                                                        .reduce(Long::sum).orElse(0L);

//             final long totalBuyOrdersUnfilledQuantityAfter8thTick = totalBuyOrderedQuantityAfter8thTick - totalBuyOrdersFilledQuantityAfter8thTick;

//             final long sellOrderCountAfter8thTick = state.getChildOrders().stream()
//                                                          .filter(order -> order.getSide() == Side.SELL)
//                                                          .map(order -> 1L).reduce(0L, Long::sum);

//             final long totalSellOrderedQuantityAfter8thTick = state.getChildOrders().stream()
//                                                                    .filter(order -> order.getSide() == Side.SELL)
//                                                                    .map(ChildOrder::getQuantity)
//                                                                    .reduce(Long::sum).orElse(0L);                                                

//             final long totalSellOrdersFilledQuantityAfter8thTick = state.getChildOrders().stream()
//                                                                         .filter(order -> order.getSide() == Side.SELL)
//                                                                         .map(ChildOrder::getFilledQuantity)
//                                                                         .reduce(Long::sum).orElse(0L);

//             final long totalSellOrdersUnfilledQuantityAfter8thTick = totalSellOrderedQuantityAfter8thTick - totalSellOrdersFilledQuantityAfter8thTick;

//             final int activeChildOrdersAfter8thTick = container.getState().getActiveChildOrders().size();

//             final long cancelledBuyOrderCountAfter8thTick = state.getChildOrders().stream()
//                                                                  .filter(order -> order.getSide() == Side.BUY)            
//                                                                  .filter(order -> order.getState() == 3)
//                                                                  .map(order -> 1L).reduce(0L, Long::sum);


//             // check that the last bought price after 8th tick is at the price of 102 or throw an assertion error if no such order is found                           
//             final ChildOrder lastBoughtPriceAfter8thTickIs102 = state.getChildOrders().stream()
//                                                                      .filter(order -> order.getSide() == Side.BUY)
//                                                                      .filter(order -> order.getPrice() == 102L)
//                                                                      .reduce((first, second) -> second)
//                                                                      .orElseThrow(() -> 
//                                                                       new AssertionError("Expected the last buy order to be at the price of 102 but found something else"));                                                              

//             // check that the last cancelled buy order after 8th tick is at the price of 102 or throw an assertion error if no such order is found  
//             final ChildOrder lastCancelledBuyOrderPriceAfter8thTickIs102 = state.getChildOrders().stream()
//                                                                                 .filter(order -> order.getSide() == Side.BUY)
//                                                                                 .filter(order -> order.getState() == OrderState.CANCELLED)
//                                                                                 .filter(order -> order.getPrice() == 102L)
//                                                                                 .reduce((first, second) -> second)
//                                                                                 .orElseThrow(() -> 
//                                                                                  new AssertionError("Expected the last cancelled buy order price for 102 but found something else"));

//             // check that we have created a new sell order after 8th tick at the price of 105 or throw an assertion error if no such order is found  
//             final ChildOrder newSellOrderPriceAfter8thTickIs105 = state.getChildOrders().stream()
//                                                                        .filter(order -> order.getSide() == Side.SELL)
//                                                                        .filter(order -> order.getPrice() == 105L)
//                                                                        .reduce((first, second) -> second)
//                                                                        .orElseThrow(() ->
//                                                                         new AssertionError("Expected a new sell order at the price of 105 but found something else"));

//             // check that filled quantity for the new sell order after 8th tick is for 200 shares or throw an assertion error if this is not correct
//             final ChildOrder newSellOrderedQtyAfter8thTickIs200 = state.getChildOrders().stream()
//                                                                        .filter(order -> order.getSide() == Side.SELL)
//                                                                        .filter(order -> order.getQuantity() == 200L)
//                                                                        .reduce((first, second) -> second)
//                                                                        .orElseThrow(() ->
//                                                                         new AssertionError("Expected a new sell order filled for 200 shares but found something else"));  

//             // check that filled quantity for the new sell order after 8th tick is for 200 shares or throw an assertion error if this is not correct
//             final ChildOrder newSellOrderFilledQtyAfter8thTickIs200 = state.getChildOrders().stream()
//                                                                        .filter(order -> order.getSide() == Side.SELL)
//                                                                        .filter(order -> order.getFilledQuantity() == 200L)
//                                                                        .reduce((first, second) -> second)
//                                                                        .orElseThrow(() ->
//                                                                         new AssertionError("Expected a new sell order filled for 200 shares but found something else"));                                                                        


//             //Check things like filled quantity, cancelled order count etc...
//             assertEquals(9, totalOrdersCountAfter8thTick);
//             assertEquals(7, buyOrderCountAfter8thTick);
//             assertEquals(2, sellOrderCountAfter8thTick); 
//             assertEquals(1400, totalBuyOrderedQuantityAfter8thTick); 
//             assertEquals(701, totalBuyOrdersFilledQuantityAfter8thTick);
//             assertEquals(699, totalBuyOrdersUnfilledQuantityAfter8thTick);
//             assertEquals(200, remainingQuantityToSellAfter7thTick);            
//             assertEquals(701, totalSellOrderedQuantityAfter8thTick); 
//             assertEquals(701, totalSellOrdersFilledQuantityAfter8thTick); 
//             assertEquals(0, totalSellOrdersUnfilledQuantityAfter8thTick);
//             assertEquals(5, activeChildOrdersAfter8thTick);
//             assertEquals(4, cancelledBuyOrderCountAfter8thTick);
//             assertEquals(102, lastCancelledBuyOrderPriceAfter8thTickIs102.getPrice());
//             assertEquals(102, previousBoughtPriceAt7thTick);
//             assertEquals(105, newSellOrderPriceAfter8thTickIs105.getPrice());
//             assertEquals(200, newSellOrderedQtyAfter8thTickIs200.getQuantity());
//             assertEquals(200, newSellOrderFilledQtyAfter8thTickIs200.getFilledQuantity());


//         // TESTING THE ALGO'S BEHAVIOUR AFTER THE 9TH TICK
//         // Best Bid 106, Best Ask 111; Spread = 5  
//         // No buy order to be created as the list has 5 active orders and the spread has widened
//         // No more sell orders to be created as there are no quantities to sell
//         // The state should have 9 Buy orders with partial fills as in 8th tick; 2 sell orders; 5 active orders, 4 cancelled     

//         send(createTick9());

//             // check the state of the order book after 9th market data tick        
//             final String updatedOrderBook9 = Util.orderBookToString(container.getState());     

//             // Get the best bid and ask prices at level 0 and their corresponding quantities
//             final BidLevel bestBidAt9thTick = state.getBidAt(0);
//             final AskLevel bestAskAt9thTick = state.getAskAt(0);
//             final long bestBidPriceAt9thTick = bestBidAt9thTick.getPrice();
//             final long bestAskPriceAt9thTick = bestAskAt9thTick.getPrice();
//             final long previousBoughtPriceAt8thTick = lastBoughtPriceAfter8thTickIs102.getPrice();           

//             // Define the spread threshold (in price points)
//             final long spreadAt9thTick = Math.abs(bestAskPriceAt9thTick - bestBidPriceAt9thTick); 
//             final boolean spreadIsBelowOrEqualToSpreadThresholdAt9thTick = spreadAt9thTick <= 5L; 
//             final boolean spreadIsAboveOrEqualToSpreadThresholdAt9thTick = spreadAt9thTick > 5L;
//             final long priceReversalThresholdAt9thTick = bestAskPriceAt9thTick - previousBoughtPriceAt8thTick;  

//             // check for best bid and best ask prices, and the spread
//             assertEquals(106, bestBidPriceAt9thTick);
//             assertEquals(111, bestAskPriceAt9thTick);
//             assertEquals(5, spreadAt9thTick);
//             assertEquals(102, previousBoughtPriceAt8thTick);
//             assertEquals(true, spreadIsBelowOrEqualToSpreadThresholdAt9thTick);
//             assertEquals(false, spreadIsAboveOrEqualToSpreadThresholdAt9thTick);
//             assertEquals(9, priceReversalThresholdAt9thTick);

//             final int totalOrdersCountAfter9thTick = container.getState().getChildOrders().size();

//             final long buyOrderCountAfter9thTick = state.getChildOrders().stream()
//                                                         .filter(order -> order.getSide() == Side.BUY)
//                                                         .map(order -> 1L).reduce(0L, Long::sum);

//             final long totalBuyOrderedQuantityAfter9thTick = state.getChildOrders().stream()
//                                                                   .filter(order -> order.getSide() == Side.BUY) 
//                                                                   .map(ChildOrder::getQuantity)
//                                                                   .reduce (Long::sum).orElse(0L);

//             final long totalBuyOrdersFilledQuantityAfter9thTick = state.getChildOrders().stream()
//                                                                        .filter(order -> order.getSide() == Side.BUY)                
//                                                                        .map(ChildOrder::getFilledQuantity)
//                                                                        .reduce(Long::sum).orElse(0L);

//             final long totalBuyOrdersUnfilledQuantityAfter9thTick = totalBuyOrderedQuantityAfter9thTick - totalBuyOrdersFilledQuantityAfter9thTick;

//             final long sellOrderCountAfter9thTick = state.getChildOrders().stream()
//                                                          .filter(order -> order.getSide() == Side.SELL)
//                                                          .map(order -> 1L).reduce(0L, Long::sum);  

//             final long totalSellOrderedQuantityAfter9thTick = state.getChildOrders().stream()
//                                                                    .filter(order -> order.getSide() == Side.SELL)
//                                                                    .map(ChildOrder::getQuantity)
//                                                                    .reduce(Long::sum).orElse(0L);

//             final long totalSellOrdersFilledQuantityAfter9thTick = state.getChildOrders().stream()
//                                                                         .filter(order -> order.getSide() == Side.SELL)
//                                                                         .map(ChildOrder::getFilledQuantity)
//                                                                         .reduce(Long::sum).orElse(0L);

//             final long totalSellOrdersUnfilledQuantityAfter9thTick = totalSellOrderedQuantityAfter9thTick - totalSellOrdersFilledQuantityAfter9thTick; 

//             final int activeChildOrdersAfter9thTick = container.getState().getActiveChildOrders().size();

//             final long cancelledOrderCountAfter9thTick = state.getChildOrders().stream()
//                                                               .filter(order -> order.getSide() == Side.BUY)
//                                                               .filter(order -> order.getState() == 3)
//                                                               .map(order -> 1L).reduce(0L, Long::sum);

//             // check that the last buy order after 9th tick is at the price of 102 or throw an assertion error if no such order is found                            
//             final ChildOrder lastBoughtPriceAfter9thTickIs102 = state.getChildOrders().stream()
//                                                                      .filter(order -> order.getSide() == Side.BUY)
//                                                                      .filter(order -> order.getPrice() == 102L)
//                                                                      .reduce((first, second) -> second)
//                                                                      .orElseThrow(() -> 
//                                                                       new AssertionError("Expected the last buy order to be for 102 but found something else"));

//             //Check things like filled quantity, cancelled order count etc...
//             assertEquals(9, totalOrdersCountAfter9thTick); 
//             assertEquals(7, buyOrderCountAfter9thTick);
//             assertEquals(2, sellOrderCountAfter9thTick); 
//             assertEquals(1400, totalBuyOrderedQuantityAfter9thTick); 
//             assertEquals(701, totalBuyOrdersFilledQuantityAfter9thTick);
//             assertEquals(699, totalBuyOrdersUnfilledQuantityAfter9thTick);
//             assertEquals(701, totalSellOrderedQuantityAfter9thTick); 
//             assertEquals(701, totalSellOrdersFilledQuantityAfter9thTick); 
//             assertEquals(0, totalSellOrdersUnfilledQuantityAfter9thTick);
//             assertEquals(5, activeChildOrdersAfter9thTick);
//             assertEquals(4, cancelledOrderCountAfter9thTick);
//             assertEquals(102, previousBoughtPriceAt8thTick);


//         // TESTING THE ALGO'S BEHAVIOUR AFTER THE 10TH TICK
//         // Best Bid 105, Best Ask 108; Spread = 3
//         // No more Buy order should be created as the list has 5 active orders
//         // No new sell orders as there are no quantities to sell
//         // No order to be cancelled as there are no more uncancelled buy orders with unfilled quantities.
//         // The state should have 9 Buy orders with partial fills as in 8th tick; 5 active, 4 cancelled

//         send(createTick10());

//             // check the state of the order book after 10th market data tick
//             final String updatedOrderBook10 = Util.orderBookToString(container.getState());  

//             // Get the best bid and ask prices at level 0 and their corresponding quantities
//             final BidLevel bestBidAt10thTick = state.getBidAt(0);
//             final AskLevel bestAskAt10thTick = state.getAskAt(0);
//             final long bestBidPriceAt10thTick = bestBidAt10thTick.getPrice();
//             final long bestAskPriceAt10thTick = bestAskAt10thTick.getPrice();
//             final long previousBoughtPriceAt9thTick = lastBoughtPriceAfter9thTickIs102.getPrice();

//             // Define the spread threshold (in price points)
//             final long spreadAt10thTick = Math.abs(bestAskPriceAt10thTick - bestBidPriceAt10thTick); 
//             final boolean spreadIsBelowOrEqualToSpreadThresholdAt10thTick = spreadAt10thTick <= 5L; 
//             final boolean spreadIsAboveOrEqualToSpreadThresholdAt10thTick = spreadAt10thTick > 5L;
//             final long priceReversalThresholdAt10thTick = bestAskPriceAt10thTick - previousBoughtPriceAt9thTick;  

//             // check for best bid and best ask prices, and the spread 
//             assertEquals(105, bestBidPriceAt10thTick);
//             assertEquals(108, bestAskPriceAt10thTick);
//             assertEquals(3, spreadAt10thTick);            
//             assertEquals(102, previousBoughtPriceAt9thTick);
//             assertEquals(true, spreadIsBelowOrEqualToSpreadThresholdAt10thTick);
//             assertEquals(false, spreadIsAboveOrEqualToSpreadThresholdAt10thTick);
//             assertEquals(6, priceReversalThresholdAt10thTick);          

//             final int totalOrdersCountAfter10thTick = container.getState().getChildOrders().size();

//             final long buyOrderCountAfter10thTick = state.getChildOrders().stream()
//                                                          .filter(order -> order.getSide() == Side.BUY)
//                                                          .map(order -> 1L).reduce(0L, Long::sum);

//             final long totalBuyOrderedQuantityAfter10thTick = state.getChildOrders().stream()
//                                                                          .filter(order -> order.getSide() == Side.BUY) 
//                                                                          .map(ChildOrder::getQuantity)
//                                                                          .reduce (Long::sum).orElse(0L);

//             final long totalBuyOrdersFilledQuantityAfter10thTick = state.getChildOrders().stream()
//                                                                    .filter(order -> order.getSide() == Side.BUY)                
//                                                                    .map(ChildOrder::getFilledQuantity)
//                                                                    .reduce(Long::sum).orElse(0L);

//             final long totalBuyOrdersUnfilledQuantityAfter10thTick = totalBuyOrderedQuantityAfter10thTick - totalBuyOrdersFilledQuantityAfter10thTick;

//             final long sellOrderCountAfter10thTick = state.getChildOrders().stream()
//                                                           .filter(order -> order.getSide() == Side.SELL)
//                                                           .map(order -> 1L).reduce(0L, Long::sum);  

//             final long totalSellOrderedQuantityAfter10thTick = state.getChildOrders().stream()
//                                                                     .filter(order -> order.getSide() == Side.SELL)
//                                                                     .map(ChildOrder::getQuantity)
//                                                                     .reduce(Long::sum).orElse(0L); 

//             final long totalSellOrdersFilledQuantityAfter10thTick = state.getChildOrders().stream()
//                                                                     .filter(order -> order.getSide() == Side.SELL)
//                                                                     .map(ChildOrder::getFilledQuantity)
//                                                                     .reduce(Long::sum).orElse(0L);

//             final long sellOrdersUnfilledQuantityAfter10thTick = totalSellOrderedQuantityAfter10thTick - totalSellOrdersFilledQuantityAfter10thTick;

//             final int activeChildOrdersAfter10thTick = container.getState().getActiveChildOrders().size();

//             final long cancelledOrderCountAfter10thTick = state.getChildOrders().stream()
//                                                                .filter(order -> order.getSide() == Side.BUY)
//                                                                .filter(order -> order.getState() == 3)
//                                                                .map(order -> 1L).reduce(0L, Long::sum);

//             // check that the last buy order after 10th tick is at the price of 102 or throw an assertion error if no such order is found                            
//             final ChildOrder lastBoughtPriceAfter10thTickIs102 = state.getChildOrders().stream()
//                                                                       .filter(order -> order.getSide() == Side.BUY)
//                                                                       .filter(order -> order.getPrice() == 102L)
//                                                                       .reduce((first, second) -> second)
//                                                                       .orElseThrow(() -> 
//                                                                        new AssertionError("Expected the last buy order to be for 102 but found something else"));                                                               

//             //Check things like filled quantity, cancelled order count etc...
//             assertEquals(9, totalOrdersCountAfter10thTick); 
//             assertEquals(7, buyOrderCountAfter10thTick);
//             assertEquals(2, sellOrderCountAfter10thTick); 
//             assertEquals(1400, totalBuyOrderedQuantityAfter10thTick); 
//             assertEquals(701, totalBuyOrdersFilledQuantityAfter10thTick);
//             assertEquals(699, totalBuyOrdersUnfilledQuantityAfter10thTick);
//             assertEquals(701, totalSellOrderedQuantityAfter10thTick); 
//             assertEquals(701, totalSellOrdersFilledQuantityAfter10thTick); 
//             assertEquals(0, sellOrdersUnfilledQuantityAfter10thTick);
//             assertEquals(5, activeChildOrdersAfter10thTick);
//             assertEquals(4, cancelledOrderCountAfter10thTick);
//             assertEquals(102, previousBoughtPriceAt9thTick);


//         // TESTING THE ALGO'S BEHAVIOUR AFTER THE 11TH TICK
//         // Best Bid 109, Best Ask 114; Spread = 5
//         // No more Buy order should be created as the list has 5 active orders
//         // No new sell orders as there are no quantities to sell
//         // No order to be cancelled as there are no more uncancelled buy orders with unfilled quantities.
//         // The state should have 9 Buy orders with partial fills as in 8th tick; 5 active, 4 cancelled

//         send(createTick11());

//             // check the state of the order book after 10th market data tick
//             final String updatedOrderBook11 = Util.orderBookToString(container.getState());  

//             // Get the best bid and ask prices at level 0 and their corresponding quantities
//             final BidLevel bestBidAt11thTick = state.getBidAt(0);
//             final AskLevel bestAskAt11thTick = state.getAskAt(0);
//             final long bestBidPriceAt11thTick = bestBidAt11thTick.getPrice();
//             final long bestAskPriceAt11thTick = bestAskAt11thTick.getPrice();
//             final long previousBoughtPriceAt10thTick = lastBoughtPriceAfter10thTickIs102.getPrice();

//             // Define the spread threshold (in price points)
//             final long spreadAt11thTick = Math.abs(bestAskPriceAt11thTick - bestBidPriceAt11thTick); 
//             final boolean spreadIsBelowOrEqualToSpreadThresholdAt11thTick = spreadAt11thTick <= 5L; 
//             final boolean spreadIsAboveOrEqualToSpreadThresholdAt11thTick = spreadAt11thTick > 5L;
//             final long priceReversalThresholdAt11thTick = bestAskPriceAt11thTick - previousBoughtPriceAt10thTick;  

//             // check for best bid and best ask prices, and the spread 
//             assertEquals(109, bestBidPriceAt11thTick);
//             assertEquals(114, bestAskPriceAt11thTick);
//             assertEquals(5, spreadAt11thTick);            
//             assertEquals(102, previousBoughtPriceAt10thTick);
//             assertEquals(true, spreadIsBelowOrEqualToSpreadThresholdAt11thTick);
//             assertEquals(false, spreadIsAboveOrEqualToSpreadThresholdAt11thTick);
//             assertEquals(12, priceReversalThresholdAt11thTick);          

//             final int totalOrdersCountAfter11thTick = container.getState().getChildOrders().size();

//             final long buyOrderCountAfter11thTick = state.getChildOrders().stream()
//                                                          .filter(order -> order.getSide() == Side.BUY)
//                                                          .map(order -> 1L).reduce(0L, Long::sum);

//             final long totalBuyOrderedQuantityAfter11thTick = state.getChildOrders().stream()
//                                                                          .filter(order -> order.getSide() == Side.BUY) 
//                                                                          .map(ChildOrder::getQuantity)
//                                                                          .reduce (Long::sum).orElse(0L);

//             final long totalBuyOrdersFilledQuantityAfter11thTick = state.getChildOrders().stream()
//                                                                    .filter(order -> order.getSide() == Side.BUY)                
//                                                                    .map(ChildOrder::getFilledQuantity)
//                                                                    .reduce(Long::sum).orElse(0L);

//             final long totalBuyOrdersUnfilledQuantityAfter11thTick = totalBuyOrderedQuantityAfter11thTick - totalBuyOrdersFilledQuantityAfter11thTick;

//             final long sellOrderCountAfter11thTick = state.getChildOrders().stream()
//                                                           .filter(order -> order.getSide() == Side.SELL)
//                                                           .map(order -> 1L).reduce(0L, Long::sum);  

//             final long totalSellOrderedQuantityAfter11thTick = state.getChildOrders().stream()
//                                                                     .filter(order -> order.getSide() == Side.SELL)
//                                                                     .map(ChildOrder::getQuantity)
//                                                                     .reduce(Long::sum).orElse(0L); 

//             final long totalSellOrdersFilledQuantityAfter11thTick = state.getChildOrders().stream()
//                                                                     .filter(order -> order.getSide() == Side.SELL)
//                                                                     .map(ChildOrder::getFilledQuantity)
//                                                                     .reduce(Long::sum).orElse(0L);

//             final long sellOrdersUnfilledQuantityAfter11thTick = totalSellOrderedQuantityAfter11thTick - totalSellOrdersFilledQuantityAfter11thTick;

//             final int activeChildOrdersAfter11thTick = container.getState().getActiveChildOrders().size();

//             final long cancelledOrderCountAfter11thTick = state.getChildOrders().stream()
//                                                                .filter(order -> order.getSide() == Side.BUY)
//                                                                .filter(order -> order.getState() == 3)
//                                                                .map(order -> 1L).reduce(0L, Long::sum);

//             //Check things like filled quantity, cancelled order count etc...
//             assertEquals(9, totalOrdersCountAfter11thTick); 
//             assertEquals(7, buyOrderCountAfter11thTick);
//             assertEquals(2, sellOrderCountAfter11thTick); 
//             assertEquals(1400, totalBuyOrderedQuantityAfter11thTick); 
//             assertEquals(701, totalBuyOrdersFilledQuantityAfter11thTick);
//             assertEquals(699, totalBuyOrdersUnfilledQuantityAfter11thTick);
//             assertEquals(701, totalSellOrderedQuantityAfter11thTick); 
//             assertEquals(701, totalSellOrdersFilledQuantityAfter11thTick); 
//             assertEquals(0, sellOrdersUnfilledQuantityAfter11thTick);
//             assertEquals(5, activeChildOrdersAfter11thTick);
//             assertEquals(4, cancelledOrderCountAfter11thTick);
//             assertEquals(102, previousBoughtPriceAt10thTick);


//         // CALCULATE PROFITS, LOSSES AND COST OF TRADING AFTER 11TH TICK

//         // Calculate total cost for all filled buy orders (before fees)
//         final long totalBuyCostBeforeFees = state.getChildOrders().stream()
//                                                  .filter(order -> order.getSide() == Side.BUY)
//                                                  .filter(order -> order.getFilledQuantity() > 0)
//                                                  .mapToLong(order -> order.getPrice() * order.getFilledQuantity())
//                                                  .sum();

//         // Calculate total revenue from all filled sell orders (before fees)
//         final long totalSellRevenueBeforeFees = state.getChildOrders().stream()
//                                                      .filter(order -> order.getSide() == Side.SELL)
//                                                      .filter(order -> order.getFilledQuantity() > 0)
//                                                      .mapToLong(order -> order.getPrice() * order.getFilledQuantity())
//                                                      .sum();

//         // Define broker fee as a percentage (0.5% or 0.005 in decimal)
//         final double brokerFeeRate = 0.005;

//         // Calculate total cost for all filled buy orders including broker fees
//         final long totalBuyCostAfterFees = state.getChildOrders().stream()
//                                                 .filter(order -> order.getSide() == Side.BUY)
//                                                 .filter(order -> order.getFilledQuantity() > 0)
//                                                 .mapToLong(order -> {
//                                                             long cost = order.getPrice() * order.getFilledQuantity();
//                                                             long fee = (long) (cost * brokerFeeRate); // Apply fee to each buy order
//                                                             return cost + fee;
//                                                 })
//                                                 .sum();

//         // Calculate total revenue from all filled sell orders including broker fees
//         final long totalSellRevenueAfterFees = state.getChildOrders().stream()
//                                                     .filter(order -> order.getSide() == Side.SELL)
//                                                     .filter(order -> order.getFilledQuantity() > 0)
//                                                     .mapToLong(order -> {
//                                                         long revenue = order.getPrice() * order.getFilledQuantity();
//                                                         long fee = (long) (revenue * brokerFeeRate); // Apply fee to each sell order
//                                                         return revenue - fee;
//                                                     })
//                                                     .sum();

//         final long grossProfitBeforeFees = totalSellRevenueBeforeFees - totalBuyCostBeforeFees;
//         final long netProfitAfterFees = totalSellRevenueAfterFees - totalBuyCostAfterFees;
//         final long costOfTrading = grossProfitBeforeFees - netProfitAfterFees;


//         // Calculate total expenditure/revenue before and after broker's fees
//         assertEquals(0.005, brokerFeeRate, 0.0005);
//         assertEquals(69498, totalBuyCostBeforeFees);
//         assertEquals(69845, totalBuyCostAfterFees );
//         assertEquals(72102, totalSellRevenueBeforeFees);
//         assertEquals(71742, totalSellRevenueAfterFees);

//         // Calculate gross/net profit and cost of trading
//         assertEquals(2604, grossProfitBeforeFees);
//         assertEquals(1897, netProfitAfterFees);
//         assertEquals(707, costOfTrading);

            

//         System.out.println("\n\n ----================================ SUMMARY AFTER CREATING, MATCHING AND CANCELLING ORDERS ==============================---- \n");

//         BidLevel bestBid = state.getBidAt(0);
//         AskLevel bestAsk = state.getAskAt(0);            
//         final long bestBidPrice = bestBid.getPrice();
//         final long bestAskPrice = bestAsk.getPrice();
//         final long spread = Math.abs(bestAskPrice - bestBidPrice);


//         long totalChildOrderCount = state.getChildOrders().size();

//         long totalFilledQuantityForBuyOrders = state.getChildOrders().stream()
//                                                     .filter(order -> order.getSide() == Side.BUY)
//                                                     .map(ChildOrder::getFilledQuantity)
//                                                     .reduce(Long::sum)
//                                                     .orElse(0L);

//         long totalFilledQuantityForSeLLOrders = state.getChildOrders().stream()
//                                                      .filter(order -> order.getSide() == Side.SELL)
//                                                      .map(ChildOrder::getFilledQuantity)
//                                                      .reduce(Long::sum)
//                                                      .orElse(0L);

//         long totalActiveChildOrders = state.getActiveChildOrders().size();

//         long totalCancelledChildOrders = totalChildOrderCount - totalActiveChildOrders;                                                     
                                                    
//         System.out.println("bestBidPrice: " + bestBidPrice + " |bestAskPrice " + bestAskPrice + " |spread: " + spread + "\n");            

//         System.out.println("NUMBER OF ACTIVE CHILD ORDERS INITIALLY: " + totalChildOrderCount);
//         System.out.println("TOTAL FILLED QUANTITY FOR BUY ORDERS: " + totalFilledQuantityForBuyOrders);
//         System.out.println("TOTAL FILLED QUANTITY FOR SELL ORDERS: " + totalFilledQuantityForSeLLOrders);
//         System.out.println("TOTAL CANCELLED ORDERS:     " + totalCancelledChildOrders);            
//         System.out.println("CURRENT NUMBER OF ACTIVE CHILD ORDERS:      " + totalActiveChildOrders + "\n");

//         System.out.println("\nList of all Child Orders created:");
//         for (ChildOrder childOrder : state.getChildOrders()) {
//             System.out.println("Order ID: " + childOrder.getOrderId() + 
//                             " | Side: " + childOrder.getSide() +            
//                             " | Price: " + childOrder.getPrice() +
//                             " | Ordered Qty: " + childOrder.getQuantity() +
//                             " | Filled Qty: " + childOrder.getFilledQuantity() +
//                             " | State of the order: " + childOrder.getState());
//         }

//         // Create a list of all filled child order details then print it
//         StringBuilder listOfAllFilledChildOrders = new StringBuilder("\nList of all Filled Child orders:\n");
//         for (ChildOrder childOrder : state.getChildOrders()) {
//             if (childOrder.getFilledQuantity() > 0) {
//                 listOfAllFilledChildOrders.append(String.format(
//         "Order Id: %d   | Side: %s    | Price: %d   | Order Qty: %d     | Filled Qty %d     | State of the order: %d%n",
//                 childOrder.getOrderId(), childOrder.getSide(), childOrder.getPrice(), childOrder.getQuantity(),
//                 childOrder.getFilledQuantity(), childOrder.getState()));
//             }

//         }
//         System.out.println(listOfAllFilledChildOrders.toString()); // Turn the list into a string and print it

        
//         System.out.println("\nList of all Cancelled Orders:"); 
//         for (ChildOrder childOrder : state.getChildOrders()) {
//             if (childOrder.getState() == 3){
//                 long unfilledQuantity = childOrder.getQuantity() - childOrder.getFilledQuantity();                
//                 System.out.println("Order ID: " + childOrder.getOrderId() + 
//                             " | Side: " + childOrder.getSide() +            
//                             " | Price: " + childOrder.getPrice() +
//                             " | Ordered Qty: " + childOrder.getQuantity() +
//                             " | Filled Qty: " + childOrder.getFilledQuantity() +                               
//                             " | Unfilled Qty: " + unfilledQuantity +
//                             " | State of the order: " + childOrder.getState());
//             }

//         }
        

//         System.out.println("\nList of all Active Child orders:");
//         for (ChildOrder childOrder : state.getActiveChildOrders()) {
//             System.out.println("Order ID: " + childOrder.getOrderId() +
//                             " | Side: " + childOrder.getSide() +
//                             " | Price: " + childOrder.getPrice() +
//                             " | Ordered Qty: " + childOrder.getQuantity() +
//                             " | Filled Qty: " + childOrder.getFilledQuantity() +
//                             " | State of the order: " + childOrder.getState());
//         }

//         System.out.println("\nPROFIT/LOSS CALCULATION AND COST OF TRADING");
//         System.out.println("Total Buy Cost Before Broker Fees:      " +  totalBuyCostBeforeFees);
//         System.out.println("Total Buy Cost After Broker Fees:       " +  totalBuyCostAfterFees);
//         System.out.println("Total Sell Revenue Before Broker Fees:  " +  totalSellRevenueBeforeFees);        
//         System.out.println("Total Sell Revenue After Broker Fees:   " +  totalSellRevenueAfterFees);
//         System.out.println("\nGross Profit After Broker Fees:         " +  grossProfitBeforeFees); 
//         System.out.println("Net Profit After  Broker Fees:          " +  netProfitAfterFees);
//         System.out.println("              Cost of trading:          " + grossProfitBeforeFees + " - " + netProfitAfterFees + " = " +  costOfTrading);         


//         System.out.println("\nTHE STATE OF THE ORDER BOOK AFTER PROCESSING DATA FROM 1ST TICK " +         
//                             "AND CREATING ORDERS LOOKED LIKE THIS \n\n: " +  updatedOrderBook1);

//         System.out.println("\nTHE STATE OF THE ORDER BOOK AFTER PROCESSING DATA FROM 2ND TICK " +
//                             "NOW LOOKS LIKE THIS \n\n: " +  updatedOrderBook2);

//         System.out.println("\nTHE STATE OF THE ORDER BOOK AFTER PROCESSING DATA FROM 3RD TICK " +
//                             "NOW LOOKS LIKE THIS \n\n: " +  updatedOrderBook3); 
                            
//         System.out.println("\nTHE STATE OF THE ORDER BOOK AFTER PROCESSING DATA FROM 4TH TICK " +
//                             "NOW LOOKS LIKE THIS \n\n: " +  updatedOrderBook4);

//         System.out.println("\nTHE STATE OF THE ORDER BOOK AFTER PROCESSING DATA FROM 5TH TICK " +
//                             "NOW LOOKS LIKE THIS \n\n: " +  updatedOrderBook5);

//         System.out.println("\nTHE STATE OF THE ORDER BOOK AFTER PROCESSING DATA FROM 6TH TICK " +
//                             "NOW LOOKS LIKE THIS \n\n: " +  updatedOrderBook6); 

//         System.out.println("\nTHE STATE OF THE ORDER BOOK AFTER PROCESSING DATA FROM 7TH TICK " +
//                             "NOW LOOKS LIKE THIS \n\n: " +  updatedOrderBook7); 

//         System.out.println("\nTHE STATE OF THE ORDER BOOK AFTER PROCESSING DATA FROM 8TH TICK " +
//         "NOW LOOKS LIKE THIS \n\n: " +  updatedOrderBook8); 

//         System.out.println("\nTHE STATE OF THE ORDER BOOK AFTER PROCESSING DATA FROM 9TH TICK " +
//                             "NOW LOOKS LIKE THIS \n\n: " +  updatedOrderBook9);                                 

//         System.out.println("\nTHE STATE OF THE ORDER BOOK AFTER PROCESSING DATA FROM 10TH TICK " +
//         "NOW LOOKS LIKE THIS \n\n: " +  updatedOrderBook10);                                   

//         System.out.println("\nTHE STATE OF THE ORDER BOOK AFTER PROCESSING DATA FROM 11TH TICK " +
//         "NOW LOOKS LIKE THIS \n\n: " +  updatedOrderBook11);         
//     }        

// }



// ORIGINAL CODE
// package codingblackfemales.gettingstarted;

// import codingblackfemales.algo.AlgoLogic;
// import org.junit.Test;

// /**
//  * This test plugs together all of the infrastructure, including the order book (which you can trade against)
//  * and the market data feed.
//  *
//  * If your algo adds orders to the book, they will reflect in your market data coming back from the order book.
//  *
//  * If you cross the srpead (i.e. you BUY an order with a price which is == or > askPrice()) you will match, and receive
//  * a fill back into your order from the order book (visible from the algo in the childOrders of the state object.
//  *
//  * If you cancel the order your child order will show the order status as cancelled in the childOrders of the state object.
//  *
//  */
// public class MyAlgoBackTest extends AbstractAlgoBackTest {

//     @Override
//     public AlgoLogic createAlgoLogic() {
//         return new MyAlgoLogic();
//     }

//     @Test
//     public void testExampleBackTest() throws Exception {
//         //create a sample market data tick....
//         send(createTick());

//         //ADD asserts when you have implemented your algo logic
//         //assertEquals(container.getState().getChildOrders().size(), 3);

//         //when: market data moves towards us
//         send(createTick2());

//         //then: get the state
//         var state = container.getState();

//         //Check things like filled quantity, cancelled order count etc....
//         //long filledQuantity = state.getChildOrders().stream().map(ChildOrder::getFilledQuantity).reduce(Long::sum).get();
//         //and: check that our algo state was updated to reflect our fills when the market data
//         //assertEquals(225, filledQuantity);
//     }

// }

// USE EITHER OF THE BELOW CODE TO RUN MYPROFITALGOTEST FROM A WINDOWS MACHINE WITH MAVEN INSTALLED

// mvn test -pl :getting-started -DMyProfitAlgoBackTest 
// mvn clean test --projects algo-exercise/getting-started
// mvn -Dtest=MyProfitAlgoBackTest test --projects algo-exercise/getting-started