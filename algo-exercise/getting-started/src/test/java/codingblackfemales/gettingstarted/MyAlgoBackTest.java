package codingblackfemales.gettingstarted;
import codingblackfemales.algo.AlgoLogic;
import codingblackfemales.sotw.ChildOrder;
import codingblackfemales.sotw.marketdata.AskLevel;
import codingblackfemales.sotw.marketdata.BidLevel;
import codingblackfemales.util.Util;
import org.agrona.concurrent.UnsafeBuffer;
import messages.marketdata.*;
import messages.order.Side;

import java.nio.ByteBuffer;
import static org.junit.Assert.assertEquals;
import java.util.Optional;


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
public class MyAlgoBackTest extends AbstractAlgoBackTest {

    @Override
    public AlgoLogic createAlgoLogic() {
        return new MyAlgoLogic();
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
                // .next().price(108).size(300L) 
                .next().price(100).size(300L) 
                .next().price(109L).size(200L)
                .next().price(110L).size(5000L)
                .next().price(119L).size(5600L);

        encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

        return directBuffer;
    }

    // ADDED 27/9/2024 - FOR TESTING PURPOSES
    // bestAsk Price has moved up to 108 which should trigger a cancellation
    // of any partially filled or unfilled orders
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
            // .next().price(97L).size(600L)
                .next().price(98L).size(600L)
                .next().price(96L).size(200L)
                .next().price(95L).size(300L);
               //.next().price(94L).size(100L);

        encoder.askBookCount(4)
            .next().price(108L).size(300L)
            .next().price(109L).size(200L)
            .next().price(110L).size(5000L)
            .next().price(119L).size(5600L);

        encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

        return directBuffer;
    }       


    // ADDED 28/9/2024 - FOR TESTING PURPOSES - created 1 buy order and no sell order
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
            // .next().price(100L).size(600L)        
            .next().price(104L).size(600L)
            .next().price(97L).size(200L)
            .next().price(96L).size(300L);

        encoder.askBookCount(3)
            .next().price(109L).size(200L)
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
                .next().price(103L).size(600L)
                .next().price(97L).size(200L)
                .next().price(96L).size(300L);
    //                .next().price(94L).size(100L);

        encoder.askBookCount(3)
            .next().price(108L).size(300L)
            // .next().price(109L).size(200L)
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
                .next().price(102L).size(600L)
                .next().price(97L).size(200L)
                .next().price(96L).size(300L);
    //                .next().price(94L).size(100L);

        encoder.askBookCount(3)
    //        .next().price(108L).size(300L)
            .next().price(104L).size(400L)
            .next().price(110L).size(5000L)
            .next().price(119L).size(5600L);

        encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

        return directBuffer;
    }

// ADDED 28/9/2024 - FOR TESTING PURPOSES
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
            .next().price(100L).size(600L)
            .next().price(97L).size(200L)
            .next().price(96L).size(300L);
            // .next().price(94L).size(100L);

    encoder.askBookCount(3)
        // .next().price(108L).size(300L)
        .next().price(104L).size(400L)
        .next().price(110L).size(5000L)
        .next().price(119L).size(5600L);

    encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

    return directBuffer;
}

// ADDED 06/10/2024 - FOR TESTING PURPOSES
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
//                .next().price(94L).size(100L);

    encoder.askBookCount(4)
        .next().price(110L).size(400L)
        .next().price(110L).size(5400L)
        .next().price(119L).size(5600L)
        .next().price(125L).size(5600L);

    encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

    return directBuffer;
}
    
        @Test
        public void checkStateOfOrderBook() throws Exception {

        var state = container.getState();

            send(createTick());

            int bidLevels = state.getBidLevels();
            int askLevels = state.getAskLevels();

            assertEquals(true, bidLevels > 0 && askLevels > 0); 
        } 



        @Test
        public void backtestAlgoBehaviourFrom1stTickTo9thTick() throws Exception {

        final var state = container.getState();

            // TESTING THE ALGO'S BEHAVIOUR AFTER THE 1ST TICK 
            // Best Bid = 98, Best Ask = 100, Spread = 2 points
            // Algo should create 5 buy orders. No fills. No Sells

            send(createTick());

                final String updatedOrderBook1 = Util.orderBookToString(state);

                //assert to check we created 5 Buy orders for 200 shares at 98 and no Sell orders 
                for (ChildOrder childOrder : state.getActiveChildOrders()) {
                    assertEquals(true, childOrder.getSide() == Side.BUY);
                    assertEquals(200, childOrder.getQuantity());
                    assertEquals(98, childOrder.getPrice());
                    assertEquals(true, childOrder.getSide() != Side.SELL);
                } 


                final int totalOrdersCountAfter1stTick = state.getChildOrders().size();

                final int activeChildOrdersAfter1stTick = state.getActiveChildOrders().size();

                final long totalOrderedQuantityAfter1stTick = state.getChildOrders().stream()
                                                                                    .map(ChildOrder::getQuantity)
                                                                                    .reduce (Long::sum).orElse(0L);

                final long filledQuantityAfter1stTick = state.getChildOrders().stream()
                                                                              .map(ChildOrder::getFilledQuantity)
                                                                              .reduce(Long::sum).orElse(0L);

                final long unfilledQuantityAfter1stTick = totalOrderedQuantityAfter1stTick - filledQuantityAfter1stTick;

                // Check the total number of child orders created, total active child orders, total ordered quantity, 
                // filled quantity, unfilled quantity and cancelled order count                
               
                assertEquals(5, totalOrdersCountAfter1stTick);
                assertEquals(1000, totalOrderedQuantityAfter1stTick);
                assertEquals(0, filledQuantityAfter1stTick);
                assertEquals(1000, unfilledQuantityAfter1stTick);
                assertEquals(5, activeChildOrdersAfter1stTick);                


            // TESTING THE ALGO'S BEHAVIOUR AFTER 2ND TICK 
            // Best Bid = 95, Best Ask = 98, Spread = 3 points
            // Now new buy orders should be created
            // Some previous orders should be filled as the Ask price has moved towards our buy limit price

            send(createTick2());

                final String updatedOrderBook2 = Util.orderBookToString(state);

                //assert to check that we created a Buy order for 200 shares at 98 and no Sell orders            
                for (ChildOrder childOrder : state.getActiveChildOrders()) {
                    assertEquals(true, childOrder.getSide() == Side.BUY);
                    assertEquals(200, childOrder.getQuantity());
                    assertEquals(98, childOrder.getPrice());
                    assertEquals (true, childOrder.getSide() != Side.SELL);
                }

                // Check the total number of child orders created, total active child orders, total ordered quantity, filled quantity,
                // unfilled quantity and cancelled order count

                final int totalOrdersCountAfter2ndTick = state.getChildOrders().size();
                final int activeChildOrdersAfter2ndTick = state.getActiveChildOrders().size();

                final long totalOrderedQuantityAfter2ndTick = state.getChildOrders().stream()
                                                                                    .map(ChildOrder::getQuantity)
                                                                                    .reduce (Long::sum).orElse(0L);

                final long filledQuantityAfter2ndTick = state.getChildOrders().stream()
                                                                              .map(ChildOrder::getFilledQuantity)
                                                                              .reduce(Long::sum).orElse(0L);

                final long unfilledQuantityAfter2ndTick = totalOrderedQuantityAfter2ndTick - filledQuantityAfter2ndTick;

                // assert that we have 5 active child orders, total ordered quantity of 1000, total filled quantity of 501
                // total unfilled quantity of 499 and 0 cancelled orders.

                assertEquals(5, totalOrdersCountAfter2ndTick);
                assertEquals(1000, totalOrderedQuantityAfter2ndTick);            
                assertEquals(501, filledQuantityAfter2ndTick);
                assertEquals(499, unfilledQuantityAfter2ndTick);
                assertEquals(5, activeChildOrdersAfter2ndTick); 


            // TESTING THE ALGO'S BEHAVIOUR AFTER 3RD TICK 
            // Best Bid = 96, Best Ask = 100, Spread = 4 points         
            // 5 Buy orders should be created and partially filled at the initial price of 98 as at tick 2
            // No new buy orders should be created as the list has 5 active child orders 
            // No Sell order should be created
            // No orders should be cancelled as the spread has not widened

            send(createTick3());

                final String updatedOrderBook3 = Util.orderBookToString(state);  

                //  assert to check that we created a Buy order for 200 shares at 98 and no Sell orders            
                for (ChildOrder childOrder : state.getChildOrders()) {
                    assertEquals(true, childOrder.getSide() == Side.BUY);
                    assertEquals(200, childOrder.getQuantity());
                    assertEquals(98, childOrder.getPrice());
                    assertEquals (true, childOrder.getSide() != Side.SELL);
                }                  
                
                final int totalOrdersCountAfter3rdTick = state.getChildOrders().size();
                final int activeChildOrdersAfter3rdTick = state.getActiveChildOrders().size();
                final int cancelledChildOrdersAfter3rdTick = totalOrdersCountAfter3rdTick - activeChildOrdersAfter3rdTick;

                // the count() method will return 0 when no orders pass the filter condition
                final long totalNumberOfFilledOrdersAfter3rdTick = state.getChildOrders().stream()
                                                                                         .filter(childOrder -> childOrder.getFilledQuantity() > 0)
                                                                                         .count();

                final long totalOrderedQuantityAfter3rdTick = state.getChildOrders().stream()
                                                                                    .map(ChildOrder::getQuantity)
                                                                                    .reduce (Long::sum).orElse(0L);

                final long filledQuantityAfter3rdTick = state.getChildOrders().stream()
                                                                              .map(ChildOrder::getFilledQuantity)
                                                                              .reduce(Long::sum).orElse(0L);

                final long unfilledQuantityAfter3rdTick = totalOrderedQuantityAfter3rdTick - filledQuantityAfter3rdTick;

                assertEquals(5, totalOrdersCountAfter3rdTick);
                assertEquals(1000, totalOrderedQuantityAfter3rdTick);            
                assertEquals(501, filledQuantityAfter3rdTick);
                assertEquals(499, unfilledQuantityAfter3rdTick);
                assertEquals(3, totalNumberOfFilledOrdersAfter3rdTick);
                assertEquals(5, activeChildOrdersAfter3rdTick);                 
                assertEquals(0, cancelledChildOrdersAfter3rdTick);   


            // TESTING THE ALGO'S BEHAVIOUR AFTER THE 4TH TICK
            // Best Bid = 98, Best Ask = 108, Spread = 10 points. PriceReversalThreshold = 7
            // The state should have 5 Buy orders with partial fills as in tick 3
            // No new Buy orders should be created as the spread has widened
            // 3 partially filled buy orders should be cancelled as the price has reversed by over 7 points
            // No Sell order should be created as the best bid price has not moved up 

            send(createTick4());

                final String updatedOrderBook4 = Util.orderBookToString(state);

                //assert to check that we created a Buy order for 200 shares at 98 and no Sell orders            
                for (ChildOrder childOrder : state.getChildOrders()) {
                    assertEquals(true, childOrder.getSide() == Side.BUY);
                    assertEquals(200, childOrder.getQuantity());
                    assertEquals(98, childOrder.getPrice());
                    assertEquals (true, childOrder.getSide() != Side.SELL);
                }

                final int totalOrdersCountAfter4thTick = state.getChildOrders().size();
                final int activeChildOrdersAfter4thTick = state.getActiveChildOrders().size();
                final int cancelledChildOrdersAfter4thTick = totalOrdersCountAfter4thTick - activeChildOrdersAfter4thTick;

                final long totalNumberOfFilledOrdersAfter4thTick = state.getChildOrders().stream()
                                                                        .filter(childOrder -> childOrder.getFilledQuantity() > 0)
                                                                        .count();

                final long totalOrderedQuantityAfter4thTick = state.getChildOrders().stream()                                                            
                                                                                    .map(ChildOrder::getQuantity)
                                                                                    .reduce (Long::sum).orElse(0L); 

                final long filledQuantityAfter4thTick = state.getChildOrders().stream()                                                        
                                                                              .map(ChildOrder::getFilledQuantity)
                                                                              .reduce(Long::sum).orElse(0L);

                final long unFilledQuantityAfter4thTick = totalOrderedQuantityAfter4thTick - filledQuantityAfter4thTick;

                assertEquals(5, totalOrdersCountAfter4thTick);
                assertEquals(1000, totalOrderedQuantityAfter4thTick);            
                assertEquals(501, filledQuantityAfter4thTick);
                assertEquals(499, unFilledQuantityAfter4thTick);
                assertEquals(3, totalNumberOfFilledOrdersAfter4thTick);
                assertEquals(2, activeChildOrdersAfter4thTick);                                 
                assertEquals(3, cancelledChildOrdersAfter4thTick);


            // TESTING THE ALGO'S BEHAVIOUR AFTER THE 5TH TICK
            // Best Bid = 104, Best Ask = 109, Spread = 5 points,
            // 1 new buy order to be created at 104            
            // The state should have 6 Buy orders with partial fills as in tick 4; 3 cancelled and 3 active

            send(createTick5());

                final String updatedOrderBook5 = Util.orderBookToString(state);     

                // assert to check that we created Buy orders for 200 shares and no Sell

                for (ChildOrder childOrder : state.getChildOrders()) {
                    assertEquals(200, childOrder.getQuantity());                     
                }

                final int totalOrdersCountAfter5thTick = state.getChildOrders().size();
                final int activeChildOrdersAfter5thTick = state.getActiveChildOrders().size();
                // int cancelledChildOrdersAfter5thTick = totalOrdersCountAfter5thTick - activeChildOrdersAfter5thTick;

                final long totalOrderedQuantityAfter5thTick = state.getChildOrders().stream()
                                                                                    .map(ChildOrder::getQuantity)
                                                                                    .reduce (Long::sum).orElse(0L);

                final long filledQuantityAfter5thTick = state.getChildOrders().stream()
                                                                              .map(ChildOrder::getFilledQuantity)
                                                                              .reduce(Long::sum).orElse(0L);

                final long unfilledQuantityAfter5thTick = totalOrderedQuantityAfter5thTick - filledQuantityAfter5thTick;   

                final long buyOrderCountAfter5thTick = state.getChildOrders().stream()
                                                                             .filter(order -> order.getSide() == Side.BUY)
                                                                             .map(order -> 1L)
                                                                             .reduce(0L, Long::sum); //sets a default value of 0 for
                                                                                                             // when no elements match the filter condition

                final long sellOrderCountAfter5thTick = state.getChildOrders().stream()
                                                                              .filter(order -> order.getSide() == Side.SELL)
                                                                              .map(order -> 1L).reduce(0L, Long::sum);

                final long cancelledOrderCountAfter5thTick = state.getChildOrders().stream()
                                                                                   .filter(order -> order.getState() == 3)
                                                                                   .map(order -> 1L).reduce(0L, Long::sum);


                // check that the last order in the list is at the price of 104                            
                Optional<ChildOrder> lastOrderIs104 = state.getChildOrders().stream()
                                                                            .filter(order5thTick -> order5thTick.getSide() == Side.BUY)
                                                                            .filter(order5thTick -> order5thTick.getPrice() == 104L)
                                                                            .reduce((first, second) -> second);

                ChildOrder order = lastOrderIs104.orElseThrow(() -> new AssertionError("Expected the last buy order for 104 but found something else"));

               // assert that we have created a new buy order at the price of 103
                assertEquals(104, order.getPrice()); 


                assertEquals(6, totalOrdersCountAfter5thTick); 
                assertEquals(6, buyOrderCountAfter5thTick); 
                assertEquals(0, sellOrderCountAfter5thTick);          
                assertEquals(1200, totalOrderedQuantityAfter5thTick); 
                assertEquals(501, filledQuantityAfter5thTick);
                assertEquals(699, unfilledQuantityAfter5thTick); 
                assertEquals(3, activeChildOrdersAfter5thTick);             
                assertEquals(3, cancelledOrderCountAfter5thTick); 


            // TESTING THE ALGO'S BEHAVIOUR AFTER THE 6TH TICK
            // Best Bid 103, Best Ask 108; Spread = 5.
            // 1 more Buy order should be created at 103           
            // The state should have 7 Buy orders with partial fills as in 5th tick; 3 cancelled and 4 active
            // No sell orders

            send(createTick6());
                final   String updatedOrderBook6 = Util.orderBookToString(state);     

                final int totalOrdersCountaAfter6thTick = state.getChildOrders().size();
                final int activeChildOrdersAfter6thTick = state.getActiveChildOrders().size();
                final int cancelledChildOrdersAfter6thTick = totalOrdersCountaAfter6thTick - activeChildOrdersAfter6thTick;

                final long totalOrderedQuantityAfter6thTick = state.getChildOrders().stream()
                                                                              .map(ChildOrder::getQuantity)
                                                                              .reduce (Long::sum).orElse(0L);

                final long filledQuantityAfter6thTick = state.getChildOrders().stream()
                                                                              .map(ChildOrder::getFilledQuantity)
                                                                              .reduce(Long::sum).orElse(0L);

                final long unfilledQuantityAfter6thTick = totalOrderedQuantityAfter6thTick - filledQuantityAfter6thTick;   

                final long buyOrderCountAfter6thTick = state.getChildOrders().stream()
                                                                             .filter(order6thTick -> order6thTick.getSide() == Side.BUY)
                                                                             .map(order6thTick -> 1L).reduce(0L, Long::sum);

                final long sellOrderCountAfter6thTick = state.getChildOrders().stream()
                                                                              .filter(order6thTick -> order6thTick.getSide() == Side.SELL)
                                                                              .map(order6thTick -> 1L).reduce(0L, Long::sum);

                // check that the last order in the list is at the price of 103                            
                Optional<ChildOrder> lastOrderIs103 = state.getChildOrders().stream()
                                                                            .filter(order6thTick -> order6thTick.getSide() == Side.BUY)
                                                                            .filter(order6thTick -> order6thTick.getPrice() == 103L)
                                                                            .reduce((first, second) -> second);

                ChildOrder newBuyOrderIs103 = lastOrderIs103.orElseThrow(() -> new AssertionError("Expected the last buy order for 103 but found something else"));

               // assert that we have created a new buy order at the price of 103
                assertEquals(103, newBuyOrderIs103.getPrice()); 

                // assert the total orders, filled quantities, cancelled quantities, etc
                assertEquals(7, totalOrdersCountaAfter6thTick);
                assertEquals(7, buyOrderCountAfter6thTick);
                assertEquals(0, sellOrderCountAfter6thTick);                
                assertEquals(1400, totalOrderedQuantityAfter6thTick);
                assertEquals(501, filledQuantityAfter6thTick);
                assertEquals(899, unfilledQuantityAfter6thTick);
                assertEquals(4, activeChildOrdersAfter6thTick);                
                assertEquals(3, cancelledChildOrdersAfter6thTick);



            // TESTING THE ALGO'S BEHAVIOUR AFTER THE 7TH TICK
            // Best Bid 102, Best Ask 106; Spread = 4.
            // 1 more Buy order should be created at 102 after 7th tick        
            // The state should now have 8 Buy orders with partial fills as in 6th tick; 3 cancelled, 5 active
            // No sell orders

            send(createTick7());
                final   String updatedOrderBook7 = Util.orderBookToString(state);     

                final int totalOrdersCountAfter7thTick = state.getChildOrders().size();
                final int activeChildOrdersAfter7thTick = state.getActiveChildOrders().size();
 
                final long totalOrderedQuantityAfter7thTick = state.getChildOrders()
                                                                   .stream().map(ChildOrder::getQuantity)
                                                                   .reduce (Long::sum).orElse(0L);

                final long filledQuantityAfter7thTick = state.getChildOrders()
                                                             .stream().map(ChildOrder::getFilledQuantity)                                                       
                                                             .reduce(Long::sum).orElse(0L);

                final long unfilledQuantityAfter7thTick = totalOrderedQuantityAfter7thTick - filledQuantityAfter7thTick;

                final long buyOrderCountAfter7thTick = state.getChildOrders()
                                                            .stream().filter(order7thTick -> order7thTick.getSide() == Side.BUY)
                                                            .map(order7thTick -> 1L).reduce(0L, Long::sum);

                final long sellOrderCountAfter7thTick = state.getChildOrders()
                                                             .stream().filter(order7thTick -> order7thTick.getSide() == Side.SELL)
                                                             .map(order7thTick -> 1L).reduce(0L, Long::sum);

                final int cancelledChildOrdersAfter7thTick = totalOrdersCountAfter7thTick - activeChildOrdersAfter7thTick;

                // check that the last order in the list is at the price of 102                            
                final Optional<ChildOrder> lastBuyOrderIs102 = state.getChildOrders().stream()
                                                                                     .filter(order7thTick -> order7thTick.getSide() == Side.BUY)
                                                                                     .filter(order7thTick -> order7thTick.getPrice() == 102L)
                                                                                     .reduce((first, second) -> second);

                final ChildOrder newBuyOrderIs102 = lastBuyOrderIs102.orElseThrow(() -> new AssertionError("Expected the last buy order to be 102 but found something else"));

               // assert that we have created a new buy order at the price of 102
                assertEquals(102, newBuyOrderIs102.getPrice()); 

                //Check things like total order count, total active child orders, sell orders, filled quantity, unfilled quantity, cancelled orders etc...
                assertEquals(8, totalOrdersCountAfter7thTick);
                assertEquals(8, buyOrderCountAfter7thTick);
                assertEquals(0, sellOrderCountAfter7thTick);                
                assertEquals(1600, totalOrderedQuantityAfter7thTick);            
                assertEquals(501, filledQuantityAfter7thTick);
                assertEquals(1099, unfilledQuantityAfter7thTick);               
                assertEquals(5, activeChildOrdersAfter7thTick);
                assertEquals(3, cancelledChildOrdersAfter7thTick); 


            // TESTING THE ALGO'S BEHAVIOUR AFTER THE 8TH TICK
            // Best Bid 100, Best Ask 104; Spread = 4   
            // The state should have 8 Buy orders with partial fills as in 7th tick; 5 active, 3 cancelled
            // No more Buy order should be created as the active child orders are now 5
            // No sell orders                

            send(createTick8());
                final String updatedOrderBook8 = Util.orderBookToString(state);     

                final int totalOrdersCountAfter8thTick = state.getChildOrders().size();

                final int activeChildOrdersAfter8thTick = state.getActiveChildOrders().size();
 
                final long totalOrderedQuantityAfter8thTick = state.getChildOrders()
                                                                   .stream()
                                                                   .map(ChildOrder::getQuantity)
                                                                   .reduce (Long::sum).orElse(0L);

                final long filledQuantityAfter8thTick = state.getChildOrders()
                                                             .stream()
                                                             .map(ChildOrder::getFilledQuantity)
                                                             .reduce(Long::sum).orElse(0L);

                final long unfilledQuantityAfter8thTick = totalOrderedQuantityAfter8thTick - filledQuantityAfter7thTick;   

                final long buyOrderCountAfter8thTick = state.getChildOrders()
                                                            .stream()
                                                            .filter(order8thTick -> order8thTick.getSide() == Side.BUY)
                                                            .map(order8thTick -> 1L).reduce(0L, Long::sum);

                final long sellOrderCountAfter8thTick = state.getChildOrders()
                                                             .stream()
                                                             .filter(order8thTick -> order8thTick.getSide() == Side.SELL)
                                                             .map(order8thTick -> 1L).reduce(0L, Long::sum);

                final int cancelledChildOrdersAfter8thTick = totalOrdersCountAfter8thTick - activeChildOrdersAfter8thTick;

                 //Check things like filled quantity, cancelled order count etc...
                assertEquals(8, totalOrdersCountAfter8thTick);
                assertEquals(8, buyOrderCountAfter8thTick);
                assertEquals(0, sellOrderCountAfter8thTick);                
                assertEquals(1600, totalOrderedQuantityAfter8thTick);            
                assertEquals(501, filledQuantityAfter8thTick);
                assertEquals(1099, unfilledQuantityAfter8thTick);
                assertEquals(5, activeChildOrdersAfter8thTick);                
                assertEquals(3, cancelledChildOrdersAfter8thTick);


            // TESTING THE ALGO'S BEHAVIOUR AFTER THE 9TH TICK
            // Best Bid 106; Best Ask 110; Spread is 4, PriceReversal threshold is above 7
            // 1 more order should be cancelled 
            // The state should have 8 Buy orders with partial fills as in 8th tick; 3 active, 5 cancelled
            // No Buy order should be created as the list has 5 orders
            // No sell orders 

            send(createTick9());
                final   String updatedOrderBook9 = Util.orderBookToString(state);     

                final int totalOrdersCountAfter9thTick = state.getChildOrders().size();

                final int activeChildOrdersAfter9thTick = state.getActiveChildOrders().size();

                final long totalOrderedQuantityAfter9thTick = state.getChildOrders().stream()
                                                                   .map(ChildOrder::getQuantity)
                                                                   .reduce (Long::sum).orElse(0L);

                final long filledQuantityAfter9thTick = state.getChildOrders()
                                                             .stream()
                                                             .map(ChildOrder::getFilledQuantity)
                                                             .reduce(Long::sum).orElse(0L);

                final long unfilledQuantityAfter9thTick = totalOrderedQuantityAfter9thTick - filledQuantityAfter9thTick;   

                final long buyOrderCountAfter9thTick = state.getChildOrders()
                                                            .stream()
                                                            .filter(order9thTick -> order9thTick.getSide() == Side.BUY)
                                                            .map(order9thTick -> 1L)
                                                            .reduce(0L, Long::sum);

                final long sellOrderCountAfter9thTick = state.getChildOrders()
                                                             .stream()
                                                             .filter(order9thTick -> order9thTick.getSide() == Side.SELL)
                                                             .map(order9thTick -> 1L)
                                                             .reduce(0L, Long::sum);

                final int cancelledChildOrdersAfter9thTick = totalOrdersCountAfter9thTick - activeChildOrdersAfter9thTick;

                //Check things like filled quantity, cancelled order count etc...
                assertEquals(8, totalOrdersCountAfter9thTick);
                assertEquals(8, buyOrderCountAfter9thTick);
                assertEquals(0, sellOrderCountAfter9thTick);                
                assertEquals(1600, totalOrderedQuantityAfter9thTick);            
                assertEquals(501, filledQuantityAfter9thTick);
                assertEquals(1099, unfilledQuantityAfter9thTick);
                assertEquals(3, activeChildOrdersAfter9thTick);
                assertEquals(5, cancelledChildOrdersAfter9thTick);


            System.out.println("\n\n ----================================ SUMMARY AFTER CREATING, MATCHING AND CANCELLING ORDERS ==============================---- \n");

            // // System.out.println("All Child Orders:");
            // // state.getChildOrders().forEach(childOrder -> {
            // //     System.out.println("Order ID: " + childOrder.getOrderId() + 
            // //                        " | Price: " + childOrder.getPrice() +
            // //                        " | Total Ordered Quantity: " + childOrder.getQuantity() +                               
            // //                        " | Total Filled Quantity: " + childOrder.getFilledQuantity() +
            // //                        " | State: " + childOrder.getState());            
            // // });

        
            final BidLevel bestBid = state.getBidAt(0);
            final AskLevel bestAsk = state.getAskAt(0);            
            final long bestBidPrice = bestBid.getPrice();
            final long bestAskPrice = bestAsk.getPrice();
            final long spread = Math.abs(bestAskPrice - bestBidPrice);


            final long totalChildOrderCount = state.getChildOrders().size();
            final long totalActiveChildOrders = state.getActiveChildOrders().size();
            final long totalCancelledChildOrders = totalChildOrderCount - totalActiveChildOrders;
            final long totalFilledQuantity = state.getChildOrders().stream()
                                                                   .map(ChildOrder::getFilledQuantity)
                                                                   .reduce(Long::sum).get();

            System.out.println("bestBidPrice: " + bestBidPrice + " |bestAskPrice " + bestAskPrice + " |spread: " + spread + "\n");            

            System.out.println("NUMBER OF ACTIVE CHILD ORDERS INITIALLY: " + totalChildOrderCount);
            System.out.println("TOTAL FILLED QUANTITY: " + totalFilledQuantity);
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


            System.out.println("\nList of all Filled Orders:"); 
            for (ChildOrder childOrder : state.getChildOrders()) {
                if (childOrder.getFilledQuantity() > 0){
                    final long unfilledQuantity = childOrder.getQuantity() - childOrder.getFilledQuantity();                
                    System.out.println("Order ID: " + childOrder.getOrderId() + 
                                " | Side: " + childOrder.getSide() +            
                                " | Price: " + childOrder.getPrice() +
                                " | Ordered Qty: " + childOrder.getQuantity() +
                                " | Filled Qty: " + childOrder.getFilledQuantity() +
                                " | Unfilled Qty: " + unfilledQuantity +
                                " | State of the order: " + childOrder.getState());
                }

            }


            System.out.println("\nList of all Cancelled Orders:"); // ammended code to only list cancelled partially or unfilled orders
            for (ChildOrder childOrder : state.getChildOrders()) {
                if (childOrder.getState() == 3){
                    final long unfilledQuantity = childOrder.getQuantity() - childOrder.getFilledQuantity();                
                    System.out.println("Order ID: " + childOrder.getOrderId() + 
                                " | Side: " + childOrder.getSide() +            
                                " | Price: " + childOrder.getPrice() +
                                " | Ordered Qty: " + childOrder.getQuantity() +
                                " | Filled Qty: " + childOrder.getFilledQuantity() +                               
                                " | Unfilled Qty: " + unfilledQuantity +
                                " | State of the order: " + childOrder.getState());
                }

            }


            System.out.println("\nList of all Active Child Orders:");
            for (ChildOrder childOrder : state.getActiveChildOrders()) {
                System.out.println("Order ID: " + childOrder.getOrderId() +
                                " | Side: " + childOrder.getSide() +
                                " | Price: " + childOrder.getPrice() +
                                " | Ordered Qty: " + childOrder.getQuantity() +
                                " | Filled Qty: " + childOrder.getFilledQuantity() +
                                " | State of the order: " + childOrder.getState());
            }

            System.out.println("\n");

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
        }

}




// ORIGINAL CODE FROM GITHUB REPO
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
// mvn test -pl :getting-started -DMyAlgoBackTest 
// mvn -Dtest=MyAlgoBackTest test --projects algo-exercise/getting-started
// mvn clean test --projects algo-exercise/getting-started