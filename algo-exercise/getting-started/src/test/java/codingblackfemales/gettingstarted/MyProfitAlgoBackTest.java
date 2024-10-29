// AMENDED AND FINAL CODE AFTER BUGS WERE FIXED AFTER 17/10/2024

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
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
                .next().price(102).size(300L) // ask price was 103
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
            .next().price(105L).size(300L) // ask price was 108
            .next().price(109L).size(200L)
            .next().price(110L).size(5000L)
            .next().price(119L).size(5600L);

        encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

        return directBuffer;
    }       


    // ADDED 28/9/2024 - FOR TESTING PURPOSES.
    // 17/10/2024 SELL ORDER GOT FILED AT 104 AFTER ADJUSTING SELLING PRICE FROM BEST ASK TO "BEST BID"
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
        .next().price(102L).size(600L) // ask price was 104
        .next().price(97L).size(200L)
        .next().price(96L).size(300L);

        encoder.askBookCount(3)
            .next().price(106L).size(200L) // ask price was 108
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
                .next().price(102L).size(600L) // ask price was 103
                .next().price(97L).size(200L)
                .next().price(96L).size(300L);

        encoder.askBookCount(4)
            .next().price(104L).size(300L) // ask price was 107
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
                .next().price(100L).size(600L) // was 110
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
            .next().price(105L).size(600L) // ask price was 103
            .next().price(97L).size(200L)
            .next().price(96L).size(300L);

        encoder.askBookCount(4)
            .next().price(109L).size(5000L) // ask price was 108
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
            .next().price(107L).size(600L)
            .next().price(97L).size(200L)
            .next().price(96L).size(300L);

        encoder.askBookCount(4)
            .next().price(111L).size(5000L)
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
            // .next().price(110L).size(5000L)
            // .next().price(119L).size(5600L);

        encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

        return directBuffer;

    }


    @Test
    public void checkStateOfOrderBook() throws Exception {

        send(createTick());

        int bidLevels = container.getState().getBidLevels();
        int askLevels = container.getState().getAskLevels();

        assertEquals(true, bidLevels > 0 && askLevels > 0); 
    } 


    @Test
    public void backtestAlgoBehaviourFrom1stTickTo9thTick() throws Exception {
    
        var state = container.getState();

        // TESTING THE ALGO'S BEHAVIOUR AFTER THE 1ST TICK 
        // Best Bid = 98, Best Ask = 100, Spread = 2 points
        // Algo should create 5 buy orders

        send(createTick());

        final String updatedOrderBook1 = Util.orderBookToString(container.getState());

            // Get the best bid and ask prices at level 0 and their corresponding quantities
            final BidLevel bestBid1stTick = state.getBidAt(0);
            final AskLevel bestAsk1stTick = state.getAskAt(0);
            final long bestBidPrice1stTick = bestBid1stTick.getPrice();
            final long bestAskPrice1stTick = bestAsk1stTick.getPrice();

            // Define the spread threshold (in price points)
            final long spread1stTick = Math.abs(bestAskPrice1stTick - bestBidPrice1stTick); // added 20/10/2024 to get absolute value of a number
            final boolean spreadIsBelowOrEqualToSpreadThreshhold1stTick = spread1stTick <= 5L; 
            final boolean spreadIsAboveOrEqualToSpreadThreshhold1stTick = spread1stTick > 5L;

            // check for best bid and best ask prices, and the spread
            assertEquals(98, bestBidPrice1stTick);
            assertEquals(100, bestAskPrice1stTick);
            assertEquals(2, spread1stTick);
            assertEquals(true, spreadIsBelowOrEqualToSpreadThreshhold1stTick);
            assertEquals(false, spreadIsAboveOrEqualToSpreadThreshhold1stTick);

            //  assert to check that we created a Buy order for 200 shares at 98 and no Sell orders            
            for (ChildOrder childOrder : state.getChildOrders()) {
                assertEquals(true, childOrder.getSide() == Side.BUY);
                assertEquals(200, childOrder.getQuantity());
                assertEquals(98, childOrder.getPrice());
                assertEquals (true, childOrder.getSide() != Side.SELL);
            }

            // check that the last buy order in the list is at the price of 98                            
            Optional<ChildOrder> lastOrderAfter1stTickIs98 = state.getChildOrders().stream()
                                                                  .filter(order1stTick -> order1stTick.getSide() == Side.BUY)
                                                                  .filter(order1stTick -> order1stTick.getPrice() == 98L)
                                                                  .reduce((first, second) -> second);

            ChildOrder lastBoughtPriceIs98After1stTick = lastOrderAfter1stTickIs98.orElseThrow(() ->
                                                                                                  new AssertionError("Expected the last buy order for 98 but found something else"));

            // assert to check that the last bought price is 98
            assertEquals(98, lastBoughtPriceIs98After1stTick.getPrice());

            // Check the total number of child orders created,  total ordered quantity, filled quantity, unfilled quantity and total active child orders
            final int totalOrdersCountAfter1stTick = state.getChildOrders().size();

            final long totalOrderedQuantityAfter1stTick = state.getChildOrders().stream()
                                                               .map(ChildOrder::getQuantity)
                                                               .reduce (Long::sum).orElse(0L); //.get();

            final long buyOrderFilledQuantityAfter1stTick = state.getChildOrders().stream()
                                                                 .map(ChildOrder::getFilledQuantity)
                                                                 .reduce(Long::sum).orElse(0L);

            final long buyOrderUnfilledQuantityAfter1stTick = totalOrderedQuantityAfter1stTick - buyOrderFilledQuantityAfter1stTick;

            final int activeChildOrdersAfter1stTick = state.getActiveChildOrders().size();              
            
            assertEquals(5, totalOrdersCountAfter1stTick);
            assertEquals(1000, totalOrderedQuantityAfter1stTick);
            assertEquals(0, buyOrderFilledQuantityAfter1stTick);
            assertEquals(1000, buyOrderUnfilledQuantityAfter1stTick);
            assertEquals(5, activeChildOrdersAfter1stTick);


        // TESTING THE ALGO'S BEHAVIOUR AFTER 2ND TICK 
        // Best Bid = 95, Best Ask = 98, Spread = 3 points
        // Now new buy orders should be created as list has 5 orders
        // Some orders should be filled as the Ask price has moved towards our buy limit price

        send(createTick2());

            final String updatedOrderBook2 = Util.orderBookToString(container.getState());

            // Get the best bid and ask prices at level 0 and their corresponding quantities
            final BidLevel bestBid2ndTick = state.getBidAt(0);
            final AskLevel bestAsk2ndTick = state.getAskAt(0);
            final long bestBidPrice2ndTick = bestBid2ndTick.getPrice();
            final long bestAskPrice2ndTick = bestAsk2ndTick.getPrice();
            final long previousBoughtPrice1stTick = lastBoughtPriceIs98After1stTick.getPrice();

            // Define the spread threshold (in price points)
            final long spread2ndTick = Math.abs(bestAskPrice2ndTick - bestBidPrice2ndTick);
            final boolean spreadIsBelowOrEqualToSpreadThreshhold2ndTick = spread2ndTick <= 5L; 
            final boolean spreadIsAboveOrEqualToSpreadThreshhold2ndTick = spread2ndTick > 5L;
            final long priceReversalThreshhold2ndTick = bestAskPrice2ndTick - previousBoughtPrice1stTick;  

            // check for best bid and best ask prices, and the spread
            assertEquals(95, bestBidPrice2ndTick);
            assertEquals(98, bestAskPrice2ndTick);
            assertEquals(3, spread2ndTick);            
            assertEquals(98, previousBoughtPrice1stTick);
            assertEquals(true, spreadIsBelowOrEqualToSpreadThreshhold2ndTick);
            assertEquals(false, spreadIsAboveOrEqualToSpreadThreshhold2ndTick);
            assertEquals(0, priceReversalThreshhold2ndTick);            

            // assert to check that we created a Buy order for 200 shares at 98 and no Sell orders            
            for (ChildOrder childOrder : state.getActiveChildOrders()) {
                assertEquals(true, childOrder.getSide() == Side.BUY);
                assertEquals(200, childOrder.getQuantity());
                assertEquals(98, childOrder.getPrice());
                assertEquals (true, childOrder.getSide() != Side.SELL);
            }

            // check that the last buy order in the list is at the price of 98                            
            Optional<ChildOrder> lastOrderAfter2ndTickIs98 = state.getChildOrders().stream()
                                                                  .filter(order2ndTick -> order2ndTick.getSide() == Side.BUY)
                                                                  .filter(order2ndTick -> order2ndTick.getPrice() == 98L)
                                                                  .reduce((first, second) -> second);

            ChildOrder lastBoughtPriceIs98After2ndTick = lastOrderAfter2ndTickIs98.orElseThrow(() ->
                                                                                                  new AssertionError("Expected the last buy order for 98 but found something else"));

            // assert to check that the last bought price is 98
            assertEquals(98, lastBoughtPriceIs98After2ndTick.getPrice());

            //Check the total number of child orders created, total ordered quantity, filled quantity, unfilled quantity, active child orders and cancelled order count
            final int totalOrdersCountAfter2ndTick = container.getState().getChildOrders().size();

            final long buyOrderCountAfter2ndTick = state.getChildOrders().stream()
                                                        .filter(order -> order.getSide() == Side.BUY)
                                                        .map(order -> 1L).reduce(0L, Long::sum);

            final long sellOrderCountAfter2ndTick = state.getChildOrders().stream()
                                                         .filter(order -> order.getSide() == Side.SELL)
                                                         .map(order -> 1L).reduce(0L, Long::sum); 

            final long totalOrderedQuantityAfter2ndTick = state.getChildOrders()
                                                               .stream().map(ChildOrder::getQuantity) 
                                                               .reduce (Long::sum) //.get();
                                                               .orElse(0L);

            final long buyOrderFilledQuantityAfter2ndTick = state.getChildOrders()
                                                                 .stream().map(ChildOrder::getFilledQuantity)
                                                                 .reduce(Long::sum) //.get();
                                                                 .orElse(0L);

            final long buyOrderUnfilledQuantityAfter2ndTick = totalOrderedQuantityAfter2ndTick - buyOrderFilledQuantityAfter2ndTick;

            final int activeChildOrdersAfter2ndTick = container.getState().getActiveChildOrders().size();

            final long cancelledChildOrdersAfter2ndTick = state.getChildOrders().stream()
                                                               .filter(order6thTick -> order6thTick.getState() == OrderState.CANCELLED)
                                                               .count(); 

            assertEquals(5, totalOrdersCountAfter2ndTick);
            assertEquals(5, buyOrderCountAfter2ndTick);
            assertEquals(0, sellOrderCountAfter2ndTick);
            assertEquals(1000, totalOrderedQuantityAfter2ndTick);            
            assertEquals(501, buyOrderFilledQuantityAfter2ndTick);
            assertEquals(499, buyOrderUnfilledQuantityAfter2ndTick);
            assertEquals(5, activeChildOrdersAfter2ndTick);
            assertEquals(0, cancelledChildOrdersAfter2ndTick);



        // TESTING THE ALGO'S BEHAVIOUR AFTER 3RD TICK 
        // Best Bid = 96, Best Ask = 102, Spread = 6 points
        // The state should have 5 active Buy orders, partially filled at the initial price of 98 as at tick 2
        // No new buy orders should be created as the list has 5 active child orders 
        // No Sell order should be created as the bid price has not moved above previous bought price
        // No orders should be cancelled as the spread has not widened

        send(createTick3());

           final String updatedOrderBook3 = Util.orderBookToString(container.getState());        
 
            //assert to check that we created a Buy order for 200 shares at 98 and no Sell orders            
            for (ChildOrder childOrder : state.getActiveChildOrders()) {
                assertEquals(true, childOrder.getSide() == Side.BUY);
                assertEquals(200, childOrder.getQuantity());
                assertEquals(98, childOrder.getPrice());
                assertEquals (true, childOrder.getSide() != Side.SELL);
            }

            // Get the best bid and ask prices at level 0 and their corresponding quantities
            final BidLevel bestBid3rdTick = state.getBidAt(0);
            final AskLevel bestAsk3rdTick = state.getAskAt(0);
            final long bestBidPrice3rdTick = bestBid3rdTick.getPrice();
            final long bestAskPrice3rdTick = bestAsk3rdTick.getPrice();
            final long previousBoughtPrice2ndTick = lastBoughtPriceIs98After2ndTick.getPrice();

            // Define the spread threshold (in price points)
            final long spread3rdTick = Math.abs(bestAskPrice3rdTick - bestBidPrice3rdTick); 
            final boolean spreadIsBelowOrEqualToSpreadThreshhold3rdTick = spread3rdTick <= 5L; 
            final boolean spreadIsAboveOrEqualToSpreadThreshhold3rdTick = spread3rdTick > 5L;
            final long priceReversalThreshhold3rdTick = bestAskPrice3rdTick - previousBoughtPrice2ndTick;  

            // check for best bid and best ask prices, and the spread
            assertEquals(96, bestBidPrice3rdTick);
            assertEquals(102, bestAskPrice3rdTick);
            assertEquals(6, spread3rdTick);            
            assertEquals(98, previousBoughtPrice2ndTick);
            assertEquals(false, spreadIsBelowOrEqualToSpreadThreshhold3rdTick);
            assertEquals(true, spreadIsAboveOrEqualToSpreadThreshhold3rdTick);
            assertEquals(4, priceReversalThreshhold3rdTick);

            // check that the last buy order in the list is at the price of 98                            
            Optional<ChildOrder> lastOrderAfter3rdTickIs98 = state.getChildOrders().stream()
                                                                  .filter(order3rdTick -> order3rdTick.getSide() == Side.BUY)
                                                                  .filter(order3rdTick -> order3rdTick.getPrice() == 98L)
                                                                  .reduce((first, second) -> second);

            ChildOrder lastBoughtPriceIs98After3rdTick = lastOrderAfter3rdTickIs98.orElseThrow(() ->
                                                                                                  new AssertionError("Expected the last buy order for 98 but found something else"));

            // assert to check that the last bought price is 98
            assertEquals(98, lastBoughtPriceIs98After2ndTick.getPrice());
            
            final int totalOrdersCountAfter3rdTick = container.getState().getChildOrders().size();

            final long totalOrderedQuantityAfter3rdTick = state.getChildOrders()
                                                               .stream().map(ChildOrder::getQuantity)
                                                               .reduce (Long::sum).orElse(0L);

            final long buyOrderFilledQuantityAfter3rdTick = state.getChildOrders()
                                                                 .stream().map(ChildOrder::getFilledQuantity)
                                                                 .reduce(Long::sum).orElse(0L);

            final long buyOrderUnfilledQuantityAfter3rdTick = totalOrderedQuantityAfter3rdTick - buyOrderFilledQuantityAfter3rdTick;

            final int activeChildOrdersAfter3rdTick = container.getState().getActiveChildOrders().size();

            final long cancelledChildOrdersAfter3rdTick = state.getChildOrders().stream()
                                                               .filter(order6thTick -> order6thTick.getState() == OrderState.CANCELLED)
                                                               .count();             

            assertEquals(5, totalOrdersCountAfter3rdTick);
            assertEquals(1000, totalOrderedQuantityAfter3rdTick);            
            assertEquals(501, buyOrderFilledQuantityAfter3rdTick);
            assertEquals(499, buyOrderUnfilledQuantityAfter3rdTick);
            assertEquals(5, activeChildOrdersAfter3rdTick);
            assertEquals(0, cancelledChildOrdersAfter3rdTick);


        // TESTING THE ALGO'S BEHAVIOUR AFTER THE 4TH TICK
        // Best Bid = 98, Best Ask = 105, Spread = 7 points. Previous bought price 98
        // There should be 5 Buy orders as in tick 3, some filled
        // No new Buy orders should be created after the 4th tick as the spread has widened
        // 3 Buy orders should be cancelled as the spread has widened
        // No Sell order should created as the conditions not met

        send(createTick4());
        
            final String updatedOrderBook4 = Util.orderBookToString(container.getState());            

            // Get the best bid and ask prices at level 0 and their corresponding quantities
            final BidLevel bestBid4thTick = state.getBidAt(0);
            final AskLevel bestAsk4thTick = state.getAskAt(0);
            final long bestBidPrice4thTick = bestBid4thTick.getPrice();
            final long bestAskPrice4thTick = bestAsk4thTick.getPrice();
            final long previousBoughtPrice3rdTick = lastBoughtPriceIs98After3rdTick.getPrice();

            // Define the spread threshold (in price points)
            final long spread4thTick = Math.abs(bestAskPrice4thTick - bestBidPrice4thTick); 
            final boolean spreadIsBelowOrEqualToSpreadThreshhold4thTick = spread4thTick <= 5L; 
            final boolean spreadIsAboveOrEqualToSpreadThreshhold4thTick = spread4thTick > 5L;
            final long priceReversalThreshhold4thTick = bestAskPrice4thTick - previousBoughtPrice3rdTick;   

            // check for best bid and best ask prices, and the spread
            assertEquals(98, bestBidPrice4thTick);
            assertEquals(105, bestAskPrice4thTick);
            assertEquals(7, spread4thTick);            
            assertEquals(98, previousBoughtPrice3rdTick);
            assertEquals(false, spreadIsBelowOrEqualToSpreadThreshhold4thTick);
            assertEquals(true, spreadIsAboveOrEqualToSpreadThreshhold4thTick);
            assertEquals(7, priceReversalThreshhold4thTick);
            
            //assert to check that we created a Buy order for 200 shares at 98 and no Sell orders            
            for (ChildOrder childOrder : container.getState().getChildOrders()) {

                assertEquals(messages.order.Side.BUY, childOrder.getSide());
                assertNotEquals(messages.order.Side.SELL, childOrder.getSide());
                assertEquals(200, childOrder.getQuantity());
                assertEquals(98, childOrder.getPrice());
            }

            // check that the last buy order in the list is at the price of 98                            
            Optional<ChildOrder> lastOrderAfter4thTickIs98 = state.getChildOrders().stream()
                                                                  .filter(order3rdTick -> order3rdTick.getSide() == Side.BUY)
                                                                  .filter(order3rdTick -> order3rdTick.getPrice() == 98L)
                                                                  .reduce((first, second) -> second);

            ChildOrder lastBoughtPriceIs98After4thTick = lastOrderAfter4thTickIs98.orElseThrow(() ->
                                                                                                  new AssertionError("Expected the last buy order for 98 but found something else"));

            // assert to check that the last bought price is 98
            assertEquals(98, lastBoughtPriceIs98After4thTick.getPrice());
            
            final int totalOrdersCountAfter4thTick = container.getState().getChildOrders().size();

            final long totalOrderedQuantityAfter4thTick = state.getChildOrders()
                                                               .stream().map(ChildOrder::getQuantity)
                                                               .reduce (Long::sum).orElse(0L);

            final long buyOrderCountAfter4thTick = state.getChildOrders().stream()
                                                        .filter(order -> order.getSide() == Side.BUY)
                                                        .map(order -> 1L).reduce(0L, Long::sum);

            final long sellOrderCountAfter4thTick = state.getChildOrders().stream()
                                                         .filter(order -> order.getSide() == Side.SELL)
                                                         .map(order -> 1L).reduce(0L, Long::sum);                                                                   

            final long filledQuantityAfter4thTick = state.getChildOrders()
                                                         .stream().map(ChildOrder::getFilledQuantity)
                                                         .reduce(Long::sum).orElse(0L);

            final long notFilledQuantityAfter4thTick = totalOrderedQuantityAfter4thTick - filledQuantityAfter4thTick;

            final int activeChildOrdersAfter4thTick = container.getState().getActiveChildOrders().size();

            final long cancelledBuyChildOrdersAfter4thTick = state.getChildOrders().stream()
                                                                  .filter(order -> order.getSide() == Side.BUY)
                                                                  .filter(order6thTick -> order6thTick.getState() == OrderState.CANCELLED)
                                                                  .count();        
            

            assertEquals(5, totalOrdersCountAfter4thTick);
            assertEquals(5, buyOrderCountAfter4thTick); 
            assertEquals(0, sellOrderCountAfter4thTick);                  
            assertEquals(1000, totalOrderedQuantityAfter4thTick);            
            assertEquals(501, filledQuantityAfter4thTick);
            assertEquals(499, notFilledQuantityAfter4thTick);
            assertEquals(2, activeChildOrdersAfter4thTick);            
            assertEquals(3, cancelledBuyChildOrdersAfter4thTick); 


        // TESTING THE ALGO'S BEHAVIOUR AFTER THE 5TH TICK
        // Best Bid = 102, Best Ask = 106, Spread = 4 points, average bought price: 98 
        // Bid & Ask price has moved above average bought price + 2 points. Ask price has moved above previous bought price + 10 points
        // 1 new Sell order should be created as market data has moved above our average bought price
        // No new buy orders to be created  as the spread has widened
        // The state should have 6 orders; 3 active, 3 cancelled
        // AFTTER BUGS WERE FIXED FROM 17/10/2024 - ADJUSTED SELL ORDER PRICE FROM BEST ASK TO BEST BID PRICE.
        // ORDER GOT FILLED STRAIGHT AWAY FOR 501 AT 102

        send(createTick5());
            
            final String updatedOrderBook5 = Util.orderBookToString(container.getState());               

            // Get the best bid and ask prices at level 0 and their corresponding quantities
            final BidLevel bestBid5thTick = state.getBidAt(0);
            final AskLevel bestAsk5thTick = state.getAskAt(0);
            final long bestBidPrice5thTick = bestBid5thTick.getPrice();
            final long bestAskPrice5thTick = bestAsk5thTick.getPrice();
            final long previousBoughtPrice4thTick = lastBoughtPriceIs98After4thTick.getPrice();

            // Define the spread threshold (in price points)
            final long spread5thTick = Math.abs(bestAskPrice5thTick - bestBidPrice5thTick); 
            final boolean spreadIsBelowOrEqualToSpreadThreshhold5thTick = spread5thTick <= 5L; 
            final boolean spreadIsAboveOrEqualToSpreadThreshhold5thTick = spread5thTick > 5L;
            final long priceReversalThreshhold5thTick = bestAskPrice5thTick - previousBoughtPrice4thTick;  

            // check for best bid and best ask prices, and the spread
            assertEquals(102, bestBidPrice5thTick);
            assertEquals(106, bestAskPrice5thTick);
            assertEquals(4, spread5thTick);            
            assertEquals(98, previousBoughtPrice4thTick);
            assertEquals(true, spreadIsBelowOrEqualToSpreadThreshhold5thTick);
            assertEquals(false, spreadIsAboveOrEqualToSpreadThreshhold5thTick);
            assertEquals(8, priceReversalThreshhold5thTick);
            
            final int totalOrdersCountAfter5thTick = container.getState().getChildOrders().size();

            final long totalOrderedQuantityAfter5thTick = state.getChildOrders().stream()
                                                               .map(ChildOrder::getQuantity)
                                                               .reduce (Long::sum).orElse(0L);

            final long buyOrdersfilledQuantityAfter5thTick = state.getChildOrders().stream()
                                                                  .filter(order -> order.getSide() == Side.BUY) // filter by buy orders
                                                                  .filter(order -> order.getFilledQuantity() > 0) // filter by filled quantities
                                                                  .map(ChildOrder::getFilledQuantity)
                                                                  .reduce(Long::sum).orElse(0L);

            final long sellOrdersFilledQuantityAfter5thTick = state.getChildOrders().stream()
                                                                   .filter(order -> order.getSide() == Side.SELL) // filter by Sell orders
                                                                   .filter(order -> order.getFilledQuantity() > 0) // filter by filled quantities
                                                                   .map(ChildOrder::getFilledQuantity)
                                                                   .reduce(Long::sum).orElse(0L);

            final long sellOrdersTotalQuantityAfter5thTick = state.getChildOrders().stream()
                                                                  .filter(order -> order.getSide() == Side.SELL) // filter by Sell orders
                                                                  .map(ChildOrder::getQuantity)
                                                                  .reduce(Long::sum).orElse(0L);

            final long unfilledQuantityAfter5thTick = totalOrderedQuantityAfter5thTick - buyOrdersfilledQuantityAfter5thTick;

            final long buyOrderCountAfter5thTick = state.getChildOrders().stream()
                                                        .filter(order -> order.getSide() == Side.BUY)
                                                        .map(order -> 1L).reduce(0L, Long::sum);

            final long sellOrderCountAfter5thTick = state.getChildOrders().stream()
                                                         .filter(order -> order.getSide() == Side.SELL)
                                                         .map(order -> 1L).reduce(0L, Long::sum);

            final int activeChildOrdersAfter5thTick = container.getState().getActiveChildOrders().size();

            final long cancelledOrderCountAfter5thTick = state.getChildOrders().stream()
                                                              .filter(order -> order.getState() == 3)
                                                              .map(order -> 1L).reduce(0L, Long::sum);

            // check that the last buy order in the list is at the price of 102                            
            final Optional<ChildOrder> lastBuyOrderAfter5thTickIs98 = state.getChildOrders().stream()
                                                                           .filter(order5thTick -> order5thTick.getSide() == Side.BUY)
                                                                           .filter(order5thTick -> order5thTick.getPrice() == 98)
                                                                           .reduce((first, second) -> second);


            final ChildOrder lastBoughtPriceIs98After5thTick = lastBuyOrderAfter5thTickIs98.orElseThrow(() -> 
                                                                                                           new AssertionError("Expected a BuY order for 102 but found something else"));

            // assert that we have created a new sell order at the price of 104
            assertEquals(98, lastBoughtPriceIs98After5thTick.getPrice());  

                                                              
            // check that the last sell order in the list is at the price of 102                            
            final Optional<ChildOrder> lastSellOrderAfter5thTickIs102 = state.getChildOrders().stream()
                                                                             .filter(order5thTick -> order5thTick.getSide() == Side.SELL)
                                                                             .filter(order5thTick -> order5thTick.getPrice() == 102)
                                                                             .reduce((first, second) -> second);

            final ChildOrder newSellOrderAfter5thTickIs102 = lastSellOrderAfter5thTickIs102.orElseThrow(() -> 
                                                                                                           new AssertionError("Expected a Sell order for 102 but found something else"));

            // assert that we have created a new sell order at the price of 104
            assertEquals(102, newSellOrderAfter5thTickIs102.getPrice());


            // Check things like filled quantity, cancelled order count etc....

            assertEquals(6, totalOrdersCountAfter5thTick); // expected 6 but was 9
            assertEquals(5, buyOrderCountAfter5thTick); 
            assertEquals(1, sellOrderCountAfter5thTick); 
            assertEquals(501, sellOrdersTotalQuantityAfter5thTick);
            assertEquals(501, sellOrdersFilledQuantityAfter5thTick); // 17/10/2024 - EXPECTED 0 BUT WAS 501
            assertEquals(1501, totalOrderedQuantityAfter5thTick); 
            assertEquals(501, buyOrdersfilledQuantityAfter5thTick);                                              
            assertEquals(1000, unfilledQuantityAfter5thTick);
            assertEquals(3, activeChildOrdersAfter5thTick);             
            assertEquals(3, cancelledOrderCountAfter5thTick);                               



        // TESTING THE ALGO'S BEHAVIOUR AFTER THE 6TH TICK
        // Best bid = 102; Best Ask = 104; Spread = 2.
        // The state should have one Sell order and 5 Buy orders as in previous ticks; 3 cancelled, 3 active
        // 2 new Buy orders to be created as the list has 3 active orders

        send(createTick6());

            final   String updatedOrderBook6 = Util.orderBookToString(container.getState());

            // Get the best bid and ask prices at level 0 and their corresponding quantities
            final BidLevel bestBid6thTick = state.getBidAt(0);
            final AskLevel bestAsk6thTick = state.getAskAt(0);
            final long bestBidPrice6thTick = bestBid6thTick.getPrice();
            final long bestAskPrice6thTick = bestAsk6thTick.getPrice();
            final long previousBoughtPrice5thTick = lastBoughtPriceIs98After5thTick.getPrice();

            // Define the spread threshold (in price points)
            final long spread6thTick = Math.abs(bestAskPrice6thTick - bestBidPrice6thTick); 
            final boolean spreadIsBelowOrEqualToSpreadThreshhold6thTick = spread6thTick <= 5L; 
            final boolean spreadIsAboveOrEqualToSpreadThreshhold6thTick = spread6thTick > 5L;
            final long priceReversalThreshhold6thTick = bestAskPrice6thTick - previousBoughtPrice5thTick;

            // check for best bid and best ask prices, and the spread
            assertEquals(102, bestBidPrice6thTick);
            assertEquals(104, bestAskPrice6thTick);
            assertEquals(2, spread6thTick);            
            assertEquals(98, previousBoughtPrice5thTick);
            assertEquals(true, spreadIsBelowOrEqualToSpreadThreshhold6thTick);
            assertEquals(false, spreadIsAboveOrEqualToSpreadThreshhold6thTick);
            assertEquals(6, priceReversalThreshhold6thTick);

            // check that the last buy order in the list is at the price of 102                            
            final Optional<ChildOrder> lastBuyOrderAfter6thTickIs102 = state.getChildOrders().stream()
                                                                            .filter(order5thTick -> order5thTick.getSide() == Side.BUY)
                                                                            .filter(order5thTick -> order5thTick.getPrice() == 102)
                                                                            .reduce((first, second) -> second);

            final ChildOrder lastBoughtPriceIs102After6thTick = lastBuyOrderAfter6thTickIs102.orElseThrow(() ->
                                                                                                            new AssertionError("Expected a Buy order for 102 but found something else"));

            // assert that we have created a new sell order at the price of 104
            assertEquals(102, lastBoughtPriceIs102After6thTick.getPrice());  

            
            final int totalOrdersCountAfter6thTick = container.getState().getChildOrders().size();

            final long buyOrderCountAfter6thTick = state.getChildOrders().stream()
                                                        .filter(order -> order.getSide() == Side.BUY)
                                                        .map(order -> 1L)
                                                        .reduce(0L, Long::sum);

            final long sellOrderCountAfter6thTick = state.getChildOrders().stream()
                                                         .filter(order -> order.getSide() == Side.SELL)
                                                         .map(order -> 1L)
                                                         .reduce(0L, Long::sum);                


            final long buyOrdersTotalOrderedQuantityAfter6thTick = state.getChildOrders().stream()
                                                                        .filter(order -> order.getSide() == Side.BUY) // filter by Sell orders                
                                                                        .map(ChildOrder::getQuantity)
                                                                        .reduce (Long::sum).orElse(0L);

            final long sellOrdersTotalOrderedQuantityAfter6thTick = state.getChildOrders().stream()
                                                                         .filter(order -> order.getSide() == Side.SELL) // filter by Sell orders                
                                                                         .map(ChildOrder::getQuantity)
                                                                         .reduce (Long::sum).orElse(0L);                                                                               

            final long buyOrdersFilledQuantityAfter6thTick = state.getChildOrders().stream()
                                                                  .filter(order -> order.getSide() == Side.BUY) // filter by Sell orders
                                                                  .map(ChildOrder::getFilledQuantity)
                                                                  .reduce(Long::sum).orElse(0L);
                                                                    

            final long sellOrdersFilledQuantityAfter6thTick = state.getChildOrders().stream()
                                                                   .filter(order -> order.getSide() == Side.SELL) // filter by Sell orders
                                                                   .map(ChildOrder::getFilledQuantity)
                                                                   .reduce(Long::sum).orElse(0L);

            final long sellOrdersUnfilledQuantityAfter6thTick = sellOrdersTotalOrderedQuantityAfter6thTick  - sellOrdersFilledQuantityAfter6thTick;


            final int activeChildOrdersAfter6thTick = container.getState().getActiveChildOrders().size();

            final int cancelledChildOrdersAfter6thTick = totalOrdersCountAfter6thTick - activeChildOrdersAfter6thTick;

            // check that the last order in the list is at the price of 102                            
            final Optional<ChildOrder> lastBuyOrderIs102 = state.getChildOrders().stream()
                                                                .filter(order6thTick -> order6thTick.getSide() == Side.BUY)
                                                                .filter(order6thTick -> order6thTick.getPrice() == 102L)
                                                                .reduce((first, second) -> second);

            final ChildOrder newBuyOrderIs102 = lastBuyOrderIs102.orElseThrow(() ->
                                                                                new AssertionError ("Expected a buy order for 102 but found something else"));

            // assert that we have created a new buy order at the price of 102
            assertEquals(102, newBuyOrderIs102.getPrice());


            //Check things like filled quantity, cancelled order count etc....                                                             
            assertEquals(8, totalOrdersCountAfter6thTick); // 25/10/2024 - EXPECTED 8 BUT WAS 9
            assertEquals(7, buyOrderCountAfter6thTick);
            assertEquals(1, sellOrderCountAfter6thTick); 
            assertEquals(1400, buyOrdersTotalOrderedQuantityAfter6thTick);                                         
            assertEquals(501, buyOrdersFilledQuantityAfter6thTick);
            assertEquals(501, sellOrdersTotalOrderedQuantityAfter6thTick);
            assertEquals(501, sellOrdersFilledQuantityAfter6thTick);
            assertEquals(0, sellOrdersUnfilledQuantityAfter6thTick);
            assertEquals(5, activeChildOrdersAfter6thTick);
            assertEquals(3, cancelledChildOrdersAfter6thTick);


        // TESTING THE ALGO'S BEHAVIOUR AFTER THE 7TH TICK
        // Best bid = 100; Best Ask = 102; Spread = 2.
        // 2 new Buy order to be partially filled at 102
        // No more sell order to be created as there are no more cancelled orders with unfilled quantities         
        // The state should have 1 Sell order and 7 Buy orders; 5 active, 3 cancelled


        send(createTick7());

            final String updatedOrderBook7 = Util.orderBookToString(container.getState());     

            // Get the best bid and ask prices at level 0 and their corresponding quantities
            final BidLevel bestBid7thTick = state.getBidAt(0);
            final AskLevel bestAsk7thTick = state.getAskAt(0);
            final long bestBidPrice7thTick = bestBid7thTick.getPrice();
            final long bestAskPrice7thTick = bestAsk7thTick.getPrice();
            final long previousBoughtPrice6thTick = lastBoughtPriceIs102After6thTick.getPrice();

            // Define the spread threshold (in price points)
            final long spread7thTick = Math.abs(bestAskPrice7thTick - bestBidPrice7thTick); 
            final boolean spreadIsBelowOrEqualToSpreadThreshhold7thTick = spread7thTick <= 5L; 
            final boolean spreadIsAboveOrEqualToSpreadThreshhold7thTick = spread7thTick > 5L;
            final long priceReversalThreshhold7thTick = bestAskPrice7thTick - previousBoughtPrice6thTick;  

            // check for best bid and best ask prices, and the spread
            assertEquals(100, bestBidPrice7thTick);
            assertEquals(102, bestAskPrice7thTick);
            assertEquals(2, spread7thTick);
            assertEquals(102, previousBoughtPrice6thTick);
            assertEquals(true, spreadIsBelowOrEqualToSpreadThreshhold7thTick);
            assertEquals(false, spreadIsAboveOrEqualToSpreadThreshhold7thTick);
            assertEquals(0, priceReversalThreshhold7thTick);

            // check that the last order in the list is at the price of 102                            
            final Optional<ChildOrder> lastBuyOrderAfter7thTickIs102 = state.getChildOrders().stream()
                                                                            .filter(order6thTick -> order6thTick.getSide() == Side.BUY)
                                                                            .filter(order6thTick -> order6thTick.getPrice() == 102L)
                                                                            .reduce((first, second) -> second);

            final ChildOrder lastBoughtPriceIs102After7thTick = lastBuyOrderAfter7thTickIs102.orElseThrow(() -> 
                                                                                                            new AssertionError("Expected a buy order for 102 but found something else"));
                                                                
            // assert that we have created a new buy order at the price of 102
            assertEquals(102, lastBoughtPriceIs102After7thTick.getPrice());            
            
            final int totalOrdersCountAfter7thTick = container.getState().getChildOrders().size();

            final long buyOrderCountAfter7thTick = state.getChildOrders().stream()
                                                        .filter(order -> order.getSide() == Side.BUY)
                                                        .map(order -> 1L)
                                                        .reduce(0L, Long::sum);

            final long buyOrdersTotalOrderedQuantityAfter7thTick = state.getChildOrders().stream()
                                                                        .filter(order -> order.getSide() == Side.BUY) 
                                                                        .map(ChildOrder::getQuantity)
                                                                        .reduce (Long::sum).orElse(0L);

            final long buyOrdersFilledQuantityAfter7thTick = state.getChildOrders().stream()
                                                                  .filter(order -> order.getSide() == Side.BUY)
                                                                  .map(ChildOrder::getFilledQuantity)
                                                                  .reduce(Long::sum).orElse(0L); 

            final long sellOrderedTotalQuantityAfter7thTick = state.getChildOrders().stream()
                                                                   .filter(order -> order.getSide() == Side.SELL)
                                                                   .map(ChildOrder::getQuantity)
                                                                   .reduce(Long::sum).orElse(0L); 

            final long sellOrdersFilledQuantityAfter7thTick = state.getChildOrders().stream()
                                                                   .filter(order -> order.getSide() == Side.SELL)
                                                                   .map(ChildOrder::getFilledQuantity)
                                                                   .reduce(Long::sum).orElse(0L); 

            final long sellOrderCountAfter7thTick = state.getChildOrders().stream()
                                                         .filter(order -> order.getSide() == Side.SELL)
                                                         .map(order -> 1L).reduce(0L, Long::sum); 

            final long buyOrdersUnfilledQuantityAfter7thTick = buyOrdersTotalOrderedQuantityAfter7thTick - buyOrdersFilledQuantityAfter7thTick;              

            final long sellOrdersUnFilledQuantityAfter7thTick = sellOrderedTotalQuantityAfter7thTick - sellOrdersFilledQuantityAfter7thTick;

            final int activeChildOrdersAfter7thTick = container.getState().getActiveChildOrders().size();

            final int cancelledChildOrdersAfter7thTick = totalOrdersCountAfter7thTick - activeChildOrdersAfter7thTick;

            // CHANGES AFTER BUG WAS FIXED - 17/10/2024 - NUMBER OF BUY ORDERS INCREASED. TOTAL ORDERED QUANTITY ALSO INCREASED

            // Check things like filled quantity, cancelled order count etc....

            assertEquals(8, totalOrdersCountAfter7thTick); // 25/10/2024 EXPECTED 8 BUT WAS 9
            assertEquals(7, buyOrderCountAfter7thTick);  
            assertEquals(1, sellOrderCountAfter7thTick); 
            assertEquals(1400, buyOrdersTotalOrderedQuantityAfter7thTick);                                         
            assertEquals(701, buyOrdersFilledQuantityAfter7thTick);
            assertEquals(699, buyOrdersUnfilledQuantityAfter7thTick);
            assertEquals(501, sellOrderedTotalQuantityAfter7thTick);
            assertEquals(501, sellOrdersFilledQuantityAfter7thTick);
            assertEquals(0, sellOrdersUnFilledQuantityAfter7thTick);
            assertEquals(5, activeChildOrdersAfter7thTick);
            assertEquals(3, cancelledChildOrdersAfter7thTick);



        // TESTING THE ALGO'S BEHAVIOUR AFTER THE 8TH TICK
        // Best Bid 105, Best Ask 109; Spread = 4
        // No more Buy orders should be created as the spread is wide
        // 1 Sell order should be created for previous buy order of 102 and filled for 105 as the bid and ask price has moved up to the sell threshold
         // 1 buy order with unfilled quantity should be cancelled       
        // The state should have 8 child orders with partial fills as in 7th tick; 4 active, 4 cancelled

        send(createTick8());

            final String updatedOrderBook8 = Util.orderBookToString(container.getState());     

            // Get the best bid and ask prices at level 0 and their corresponding quantities
            final BidLevel bestBid8thTick = state.getBidAt(0);
            final AskLevel bestAsk8thTick = state.getAskAt(0);
            final long bestBidPrice8thTick = bestBid8thTick.getPrice();
            final long bestAskPrice8thTick = bestAsk8thTick.getPrice();
            final long previousBoughtPrice7thTick = lastBoughtPriceIs102After7thTick.getPrice();

            // Define the spread threshold (in price points)
            final long spread8thTick = Math.abs(bestAskPrice8thTick - bestBidPrice8thTick); 
            final boolean spreadIsBelowOrEqualToSpreadThreshhold8thTick = spread8thTick <= 5L; 
            final boolean spreadIsAboveOrEqualToSpreadThreshhold8thTick = spread8thTick > 5L;
            final long priceReversalThreshhold8thTick = bestAskPrice8thTick - previousBoughtPrice7thTick;  

            // check for best bid and best ask prices, and the spread
            assertEquals(105, bestBidPrice8thTick);
            assertEquals(109, bestAskPrice8thTick);
            assertEquals(4, spread8thTick);            
            assertEquals(102, previousBoughtPrice7thTick);
            assertEquals(true, spreadIsBelowOrEqualToSpreadThreshhold8thTick);
            assertEquals(false, spreadIsAboveOrEqualToSpreadThreshhold8thTick);
            assertEquals(7, priceReversalThreshhold8thTick);            

            // check that the last order in the list is at the price of 102                            
            final Optional<ChildOrder> lastBuyOrderAfter8thTickIs102 = state.getChildOrders().stream()
                                                                            .filter(order6thTick -> order6thTick.getSide() == Side.BUY)
                                                                            .filter(order6thTick -> order6thTick.getPrice() == 102L)
                                                                            .reduce((first, second) -> second);

            final ChildOrder lastBoughtPriceIs102After8thTick = lastBuyOrderAfter8thTickIs102.orElseThrow(() -> 
                                                                                                            new AssertionError("Expected a buy order for 102 but found something else"));

            // assert that we have created a new buy order at the price of 102
            assertEquals(102, lastBoughtPriceIs102After8thTick.getPrice());  
            
            final int totalOrdersCountAfter8thTick = container.getState().getChildOrders().size();

            final long buyOrderCountAfter8thTick = state.getChildOrders().stream()
                                                        .filter(order -> order.getSide() == Side.BUY)
                                                        .map(order -> 1L).reduce(0L, Long::sum);

            final long buyOrdersTotalOrderedQuantityAfter8thTick = state.getChildOrders().stream()
                                                                        .filter(order -> order.getSide() == Side.BUY) 
                                                                        .map(ChildOrder::getQuantity)
                                                                        .reduce (Long::sum).orElse(0L);

            final long buyOrdersFilledQuantityAfter8thTick = state.getChildOrders().stream()
                                                                  .filter(order -> order.getSide() == Side.BUY)                
                                                                  .map(ChildOrder::getFilledQuantity)
                                                                  .reduce(Long::sum).orElse(0L);

            final long buyOrdersUnfilledQuantityAfter8thTick = buyOrdersTotalOrderedQuantityAfter8thTick - buyOrdersFilledQuantityAfter8thTick;

            final long sellOrderCountAfter8thTick = state.getChildOrders().stream()
                                                         .filter(order -> order.getSide() == Side.SELL)
                                                         .map(order -> 1L).reduce(0L, Long::sum);  

            final long sellOrdersTotalOrderedQuantityAfter8thTick = state.getChildOrders().stream()
                                                                         .filter(order -> order.getSide() == Side.SELL)
                                                                         .map(ChildOrder::getQuantity)
                                                                         .reduce(Long::sum).orElse(0L);                                                

            final long sellOrdersFilledQuantityAfter8thTick = state.getChildOrders().stream()
                                                                   .filter(order -> order.getSide() == Side.SELL)
                                                                   .map(ChildOrder::getFilledQuantity)
                                                                   .reduce(Long::sum).orElse(0L);

            final long sellOrdersUnfilledQuantityAfter8thTick = sellOrdersTotalOrderedQuantityAfter8thTick - sellOrdersFilledQuantityAfter8thTick;

            final int activeChildOrdersAfter8thTick = container.getState().getActiveChildOrders().size();

            final long cancelledOrderCountAfter8thTick = state.getChildOrders().stream()
                                                              .filter(order -> order.getState() == 3)
                                                              .map(order -> 1L).reduce(0L, Long::sum);

            //Check things like filled quantity, cancelled order count etc...
            assertEquals(9, totalOrdersCountAfter8thTick); // 25/10/2024 - EXPECTED 8 BUT WAS 9
            assertEquals(7, buyOrderCountAfter8thTick);
            assertEquals(2, sellOrderCountAfter8thTick); 
            assertEquals(1400, buyOrdersTotalOrderedQuantityAfter8thTick); 
            assertEquals(701, buyOrdersFilledQuantityAfter8thTick);
            assertEquals(699, buyOrdersUnfilledQuantityAfter8thTick);
            assertEquals(701, sellOrdersTotalOrderedQuantityAfter8thTick); 
            assertEquals(701, sellOrdersFilledQuantityAfter8thTick); 
            assertEquals(0, sellOrdersUnfilledQuantityAfter8thTick);
            assertEquals(5, activeChildOrdersAfter8thTick);
            assertEquals(4, cancelledOrderCountAfter8thTick);


        // TESTING THE ALGO'S BEHAVIOUR AFTER THE 9TH TICK
        // Best Bid 106, Best Ask 111; Spread = 5   
        // The state should have 9 Buy orders with partial fills as in 8th tick; 5 active, 4 cancelled
        // No buy order to be created as the list has 5 active orders and the spread has widened
        // No new sell orders as there are no more buy order filled quantities to sell

        send(createTick9());

            final String updatedOrderBook9 = Util.orderBookToString(container.getState());     

            // Get the best bid and ask prices at level 0 and their corresponding quantities
            final BidLevel bestBid9thTick = state.getBidAt(0);
            final AskLevel bestAsk9thTick = state.getAskAt(0);
            final long bestBidPrice9thTick = bestBid9thTick.getPrice();
            final long bestAskPrice9thTick = bestAsk9thTick.getPrice();
            final long previousBoughtPrice8thTick = lastBoughtPriceIs102After8thTick.getPrice();            

            // Define the spread threshold (in price points)
            final long spread9thTick = Math.abs(bestAskPrice9thTick - bestBidPrice9thTick); 
            final boolean spreadIsBelowOrEqualToSpreadThreshhold9thTick = spread9thTick <= 5L; 
            final boolean spreadIsAboveOrEqualToSpreadThreshhold9thTick = spread9thTick > 5L;
            final long priceReversalThreshhold9thTick = bestAskPrice9thTick - previousBoughtPrice8thTick;  

            // check for best bid and best ask prices, and the spread
            assertEquals(106, bestBidPrice9thTick);
            assertEquals(111, bestAskPrice9thTick);
            assertEquals(5, spread9thTick);
            assertEquals(102, previousBoughtPrice8thTick);
            assertEquals(true, spreadIsBelowOrEqualToSpreadThreshhold9thTick);
            assertEquals(false, spreadIsAboveOrEqualToSpreadThreshhold9thTick);
            assertEquals(9, priceReversalThreshhold9thTick);

            // check that the last order in the list is at the price of 102                            
            final Optional<ChildOrder> lastBuyOrderAfter9thTickIs102 = state.getChildOrders().stream()
                                                                            .filter(order6thTick -> order6thTick.getSide() == Side.BUY)
                                                                            .filter(order6thTick -> order6thTick.getPrice() == 102L)
                                                                            .reduce((first, second) -> second);

            final ChildOrder lastBoughtPriceIs102After9thTick = lastBuyOrderAfter9thTickIs102.orElseThrow(() -> 
                                                                                                            new AssertionError("Expected a buy order for 102 but found something else"));

            // assert that we have created a new buy order at the price of 104
            assertEquals(102, lastBoughtPriceIs102After9thTick.getPrice());  

            final int totalOrdersCountAfter9thTick = container.getState().getChildOrders().size();

            final long buyOrderCountAfter9thTick = state.getChildOrders().stream()
                                                        .filter(order -> order.getSide() == Side.BUY)
                                                        .map(order -> 1L).reduce(0L, Long::sum);

            final long buyOrdersTotalOrderedQuantityAfter9thTick = state.getChildOrders().stream()
                                                                        .filter(order -> order.getSide() == Side.BUY) 
                                                                        .map(ChildOrder::getQuantity)
                                                                        .reduce (Long::sum).orElse(0L);

            final long buyOrdersFilledQuantityAfter9thTick = state.getChildOrders().stream()
                                                                  .filter(order -> order.getSide() == Side.BUY)                
                                                                  .map(ChildOrder::getFilledQuantity)
                                                                  .reduce(Long::sum).orElse(0L);


            final long buyOrdersUnfilledQuantityAfter9thTick = buyOrdersTotalOrderedQuantityAfter9thTick - buyOrdersFilledQuantityAfter9thTick;

            final long sellOrderCountAfter9thTick = state.getChildOrders().stream()
                                                         .filter(order -> order.getSide() == Side.SELL)
                                                         .map(order -> 1L).reduce(0L, Long::sum);  

            final long sellOrdersTotalOrderedQuantityAfter9thTick = state.getChildOrders().stream()
                                                                         .filter(order -> order.getSide() == Side.SELL)
                                                                         .map(ChildOrder::getQuantity)
                                                                         .reduce(Long::sum).orElse(0L);

            final long sellOrdersFilledQuantityAfter9thTick = state.getChildOrders().stream()
                                                                   .filter(order -> order.getSide() == Side.SELL)
                                                                   .map(ChildOrder::getFilledQuantity)
                                                                   .reduce(Long::sum).orElse(0L);

            final long sellOrdersUnfilledQuantityAfter9thTick = sellOrdersTotalOrderedQuantityAfter9thTick - sellOrdersFilledQuantityAfter9thTick; 

            final int activeChildOrdersAfter9thTick = container.getState().getActiveChildOrders().size();

            final long cancelledOrderCountAfter9thTick = state.getChildOrders().stream()
                                                              .filter(order -> order.getState() == 3)
                                                              .map(order -> 1L).reduce(0L, Long::sum);

            //Check things like filled quantity, cancelled order count etc...
            assertEquals(9, totalOrdersCountAfter9thTick); 
            assertEquals(7, buyOrderCountAfter9thTick);
            assertEquals(2, sellOrderCountAfter9thTick); 
            assertEquals(1400, buyOrdersTotalOrderedQuantityAfter9thTick); 
            assertEquals(701, buyOrdersFilledQuantityAfter9thTick);
            assertEquals(699, buyOrdersUnfilledQuantityAfter9thTick);
            assertEquals(701, sellOrdersTotalOrderedQuantityAfter9thTick); 
            assertEquals(701, sellOrdersFilledQuantityAfter9thTick); 
            assertEquals(0, sellOrdersUnfilledQuantityAfter9thTick);
            assertEquals(5, activeChildOrdersAfter9thTick);
            assertEquals(4, cancelledOrderCountAfter9thTick);


        // TESTING THE ALGO'S BEHAVIOUR AFTER THE 10TH TICK
        // Best Bid 107, Best Ask 111; Spread = 4
        // No more Buy order should be created as the spread is wide and the list has 5 active orders
        // No new sell orders as there are no more buy order filled quantities to sell
        // The state should have 9 Buy orders with partial fills as in 8th tick; 5 active, 4 cancelled

        send(createTick10());

            final String updatedOrderBook10 = Util.orderBookToString(container.getState());  

            // Get the best bid and ask prices at level 0 and their corresponding quantities
            final BidLevel bestBid10thTick = state.getBidAt(0);
            final AskLevel bestAsk10thTick = state.getAskAt(0);
            final long bestBidPrice10thTick = bestBid10thTick.getPrice();
            final long bestAskPrice10thTick = bestAsk10thTick.getPrice();
            final long previousBoughtPrice9thTick = lastBoughtPriceIs102After9thTick.getPrice();

            // Define the spread threshold (in price points)
            final long spread10thTick = Math.abs(bestAskPrice10thTick - bestBidPrice10thTick); 
            final boolean spreadIsBelowOrEqualToSpreadThreshhold10thTick = spread10thTick <= 5L; 
            final boolean spreadIsAboveOrEqualToSpreadThreshhold10thTick = spread10thTick > 5L;
            final long priceReversalThreshhold10thTick = bestAskPrice10thTick - previousBoughtPrice9thTick;  

            // check for best bid and best ask prices, and the spread
            assertEquals(107, bestBidPrice10thTick);
            assertEquals(111, bestAskPrice10thTick);
            assertEquals(4, spread10thTick);            
            assertEquals(102, previousBoughtPrice9thTick);
            assertEquals(true, spreadIsBelowOrEqualToSpreadThreshhold10thTick);
            assertEquals(false, spreadIsAboveOrEqualToSpreadThreshhold10thTick);
            assertEquals(9, priceReversalThreshhold10thTick);            

            final int totalOrdersCountAfter10thTick = container.getState().getChildOrders().size();

            final long buyOrderCountAfter10thTick = state.getChildOrders().stream()
                                                         .filter(order -> order.getSide() == Side.BUY)
                                                         .map(order -> 1L).reduce(0L, Long::sum);

            final long buyOrdersTotalOrderedQuantityAfter10thTick = state.getChildOrders().stream()
                                                                         .filter(order -> order.getSide() == Side.BUY) 
                                                                         .map(ChildOrder::getQuantity)
                                                                         .reduce (Long::sum).orElse(0L);

            final long buyOrdersFilledQuantityAfter10thTick = state.getChildOrders().stream()
                                                                   .filter(order -> order.getSide() == Side.BUY)                
                                                                   .map(ChildOrder::getFilledQuantity)
                                                                   .reduce(Long::sum).orElse(0L);

            final long buyOrdersUnfilledQuantityAfter10thTick = buyOrdersTotalOrderedQuantityAfter10thTick - buyOrdersFilledQuantityAfter10thTick;

            final long sellOrderCountAfter10thTick = state.getChildOrders().stream()
                                                          .filter(order -> order.getSide() == Side.SELL)
                                                          .map(order -> 1L).reduce(0L, Long::sum);  

            final long sellOrdersTotalOrderedQuantityAfter10thTick = state.getChildOrders().stream()
                                                                          .filter(order -> order.getSide() == Side.SELL)
                                                                          .map(ChildOrder::getQuantity)
                                                                          .reduce(Long::sum).orElse(0L); 

            final long sellOrdersFilledQuantityAfter10thTick = state.getChildOrders().stream()
                                                                    .filter(order -> order.getSide() == Side.SELL)
                                                                    .map(ChildOrder::getFilledQuantity)
                                                                    .reduce(Long::sum).orElse(0L);

            final long sellOrdersUnfilledQuantityAfter10thTick = sellOrdersTotalOrderedQuantityAfter10thTick - sellOrdersFilledQuantityAfter10thTick;

            final int activeChildOrdersAfter10thTick = container.getState().getActiveChildOrders().size();

            final long cancelledOrderCountAfter10thTick = state.getChildOrders().stream()
                                                               .filter(order -> order.getState() == 3)
                                                               .map(order -> 1L).reduce(0L, Long::sum);

            //Check things like filled quantity, cancelled order count etc...
            assertEquals(9, totalOrdersCountAfter10thTick); 
            assertEquals(7, buyOrderCountAfter10thTick);
            assertEquals(2, sellOrderCountAfter10thTick); 
            assertEquals(1400, buyOrdersTotalOrderedQuantityAfter10thTick); 
            assertEquals(701, buyOrdersFilledQuantityAfter10thTick);
            assertEquals(699, buyOrdersUnfilledQuantityAfter10thTick);
            assertEquals(701, sellOrdersTotalOrderedQuantityAfter10thTick); 
            assertEquals(701, sellOrdersFilledQuantityAfter10thTick); 
            assertEquals(0, sellOrdersUnfilledQuantityAfter10thTick);
            assertEquals(5, activeChildOrdersAfter10thTick);
            assertEquals(4, cancelledOrderCountAfter10thTick);

        send(createTick11());

        final String updatedOrderBook11 = Util.orderBookToString(container.getState()); 


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

        // Define broker fee as a percentage (0.1% or 0.001 in decimal)
        final double brokerFeeRate = 0.001;

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


        // Calculate profit before and after fees
        final long grossProfitBeforeFees = totalSellRevenueBeforeFees - totalBuyCostBeforeFees;
        final long netProfitAfterFees = totalSellRevenueAfterFees - totalBuyCostAfterFees;
        final long costOfTrading = grossProfitBeforeFees - netProfitAfterFees;

        System.out.println("\nPROFIT CALCULATION AND COST OF TRADING");
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

        System.out.println("\nTHE STATE OF THE ORDER BOOK AFTER PROCESSING DATA FROM 11TH TICK " +
        "NOW LOOKS LIKE THIS \n\n: " +  updatedOrderBook11);         
    }        

}



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
