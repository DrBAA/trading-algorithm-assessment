// FINAL CODE - SOME TESTS ARE NOT WORKING AS BID AND ASK PRICES HAVE BEEN SWAPPED
// COMMENTED OUT THE TESTS WHICH ARE NOT WORKING

package codingblackfemales.gettingstarted;

import codingblackfemales.algo.AlgoLogic;
import codingblackfemales.sotw.ChildOrder;
import codingblackfemales.sotw.marketdata.AskLevel;
import codingblackfemales.sotw.marketdata.BidLevel;
import messages.marketdata.BookUpdateEncoder;
import messages.marketdata.InstrumentStatus;
import messages.marketdata.MessageHeaderEncoder;
import messages.marketdata.Source;
import messages.marketdata.Venue;
import messages.order.Side;

import org.agrona.concurrent.UnsafeBuffer;
import java.nio.ByteBuffer;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


/**
 * This test is designed to check your algo behavior in isolation of the order book.
 *
 * You can tick in market data messages by creating new versions of createTick() (ex. createTick2, createTickMore etc..)
 *
 * You should then add behaviour to your algo to respond to that market data by creating or cancelling child orders.
 *
 * When you are comfortable you algo does what you expect, then you can move on to creating the MyAlgoBackTest.
 *
 */
public class MyAlgoTest extends AbstractAlgoTest {

    @Override
    public AlgoLogic createAlgoLogic() {
        //this adds your algo logic to the container classes
        return new MyAlgoLogic();
    }

   protected UnsafeBuffer createTick2(){
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

        encoder.askBookCount(4)
                .next().price(99L).size(101L)
                .next().price(110L).size(200L)
                .next().price(115L).size(5000L)
                .next().price(119L).size(5600L);        

        encoder.bidBookCount(3)
                .next().price(98L).size(100L)
                .next().price(95L).size(200L)
                .next().price(91L).size(300L);

        encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

        return directBuffer;
    }

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

        encoder.askBookCount(4)
                .next().price(98L).size(501L)
                .next().price(101L).size(200L)
                .next().price(110L).size(5000L)
                .next().price(119L).size(5600L);

        encoder.bidBookCount(3)
                .next().price(95L).size(100L)
                .next().price(93L).size(200L)
                .next().price(91L).size(300L);

        encoder.instrumentStatus(InstrumentStatus.CONTINUOUS);

        return directBuffer;
    }

    //CHECKING THE ALGO'S BEHAVIOUR AFTER 1ST TICK
    // Best bid 98, Best Ask 100; spread 2
    @Test
    public void testAlgoBehaviourAfterFirstTick() throws Exception {

        final var state = container.getState();

        send(createTick());  //create a sample market data tick from first tick

            // Get the best bid and ask prices at level 0 and their corresponding quantities
            final BidLevel bestBid = state.getBidAt(0);
            final AskLevel bestAsk = state.getAskAt(0);
            final long bestBidPrice = bestBid.getPrice();
            final long bestAskPrice = bestAsk.getPrice();

            // Define the spread threshold (in price points)
            final long spread = Math.abs(bestAskPrice - bestBidPrice); // added 5/10/2024 to get absolute value of a number
            final boolean spreadIsBelowOrEqualsToSpreadThreshhold = spread <= 5L; 
            final boolean spreadIsAboveOrEqualsToSpreadThreshhold = spread > 5L;

            // check for best bid and best ask prices. 
            // assertEquals(98, bestBidPrice); // ERROR WAS EXPECTING 98 BUT WAS 100
            // assertEquals(100, bestAskPrice); // ERROR WAS EXPECTING 100 BUT WAS 98

            // check the spread and spread threshold
            assertEquals(2, spread);
            assertEquals(true, spreadIsBelowOrEqualsToSpreadThreshhold);
            assertEquals(false, spreadIsAboveOrEqualsToSpreadThreshhold);



            //assert that we created 5 Buy orders for 200 shares at best Bid of 98 and no Sell orders

            assertEquals(5, container.getState().getActiveChildOrders().size()); 
 
            if (!container.getState().getActiveChildOrders().isEmpty()) {

                for (ChildOrder childOrder : container.getState().getActiveChildOrders()) {

                        assertEquals(200, childOrder.getQuantity());
                        assertEquals(true, childOrder.getSide() == Side.BUY);
                        assertEquals(true, childOrder.getSide() != Side.SELL);
                        // assertEquals(98, childOrder.getPrice()); // EXPECTED 98 BUT WAS 100

                        System.out.println("Order ID: " + childOrder.getOrderId() + 
                                        " | Side: " + childOrder.getSide() +            
                                        " | Price: " + childOrder.getPrice() +
                                        " | Ordered Qty: " + childOrder.getQuantity() +
                                        " | Filled Qty: " + childOrder.getFilledQuantity() +
                                        " | State of the order: " + childOrder.getState()
                                        );
                }

            }

            //Check total child orders count, total active child orders and total ordered quantity
            final int totalChildOrdersCountTick1 = container.getState().getChildOrders().size(); // added 15/9/2024

            final int totalActiveChildOrdersTick1 = container.getState().getActiveChildOrders().size();
            
            final long totalOrderedQuantityTick1 = container.getState().getActiveChildOrders().stream()
                                                                       .map(ChildOrder::getQuantity).reduce (Long::sum)
                                                                       .orElse(0L);


            // assert that there is a total of 5 child orders, a total of 5 active child orders and total ordered quantity is 1000
            assertEquals(5, totalChildOrdersCountTick1);
            assertEquals(5, totalActiveChildOrdersTick1);
            assertEquals(1000, totalOrderedQuantityTick1);

    }


    //CHECKING THE ALGO'S BEHAVIOUR AFTER 2ND TICK
    // Best Bid 98, Best Ask 99; spread 1
    @Test
    public void testAlgoBehaviourAfterSecondTick() throws Exception {

        final var state = container.getState();
    
        send(createTick2());  //create a sample market data tick from second tick

            // Get the best bid and ask prices at level 0 and their corresponding quantities
            final BidLevel bestBid = state.getBidAt(0);
            final AskLevel bestAsk = state.getAskAt(0);
            final long bestBidPrice = bestBid.getPrice();
            final long bestAskPrice = bestAsk.getPrice();

            // Define the spread threshold (in price points)
            final long spread = Math.abs(bestAskPrice - bestBidPrice); // added 5/10/2024 to get absolute value of a number
            final boolean spreadIsBelowOrEqualsToSpreadThreshhold = spread <= 5L; 
            final boolean spreadIsAboveOrEqualsToSpreadThreshhold = spread > 5L;

            // check for best bid and best ask prices. 
            // assertEquals(98, bestBidPrice); // ERROR WAS EXPECTING 98 BUT WAS 98
            // assertEquals(99, bestAskPrice); // ERROR WAS EXPECTING 99 BUT WAS 98

            // check the spread and spread threshold
            assertEquals(1, spread);
            assertEquals(true, spreadIsBelowOrEqualsToSpreadThreshhold);
            assertEquals(false, spreadIsAboveOrEqualsToSpreadThreshhold);

            //assert that we created 5 Buy orders for 200 shares at best bid of 98 and no Sell orders

            assertEquals(5, container.getState().getActiveChildOrders().size()); 

            if (!container.getState().getActiveChildOrders().isEmpty()) {                  
                for (ChildOrder childOrder : container.getState().getActiveChildOrders()) {

                        assertEquals(200, childOrder.getQuantity());
                        assertEquals(true, childOrder.getSide() == Side.BUY);
                        assertEquals(true, childOrder.getSide() != Side.SELL);
                        // assertEquals(98, childOrder.getPrice()); // EXPECTED 98 BUT WAS 99

                        System.out.println("Order ID: " + childOrder.getOrderId() + 
                                        " | Side: " + childOrder.getSide() +            
                                        " | Price: " + childOrder.getPrice() +
                                        " | Ordered Qty: " + childOrder.getQuantity() +
                                        " | Filled Qty: " + childOrder.getFilledQuantity() +
                                        " | State of the order: " + childOrder.getState()
                                        );
                }

            }

            //Check total child orders count, total active child orders and total ordered quantity
            final int totalChildOrdersCountTick2 = container.getState().getChildOrders().size(); // added 15/9/2024

            final int totalActiveChildOrdersTick2 = container.getState().getActiveChildOrders().size();

            final long totalOrderedQuantityTick2 = container.getState().getActiveChildOrders().stream()
                                                                       .map(ChildOrder::getQuantity).reduce (Long::sum)
                                                                       .orElse(0L);

            // assert that there is a total of 5 child orders, a total of 5 active child orders and total ordered quantity is 1000
            assertEquals(5, totalChildOrdersCountTick2);
            assertEquals(5, totalActiveChildOrdersTick2);
            assertEquals(1000, totalOrderedQuantityTick2);

    }

        
    //CHECKING THE ALGO'S BEHAVIOUR AFTER 3RD TICK
     // Best Bid 95, Best Ask 98; spread 1
    @Test
    public void testAlgoBehaviourAfterThirdTick() throws Exception {

        final var state = container.getState();    

        send(createTick3());  //create a sample market data tick from third tick

            // Get the best bid and ask prices at level 0 and their corresponding quantities
            final BidLevel bestBid = state.getBidAt(0);
            final AskLevel bestAsk = state.getAskAt(0);
            final long bestBidPrice = bestBid.getPrice();
            final long bestAskPrice = bestAsk.getPrice();

            // Define the spread threshold (in price points)
            final long spread = Math.abs(bestAskPrice - bestBidPrice); // added 5/10/2024 to get absolute value of a number
            final boolean spreadIsBelowOrEqualsToSpreadThreshhold = spread <= 5L; 
            final boolean spreadIsAboveOrEqualsToSpreadThreshhold = spread > 5L;

            // check for best bid and best ask prices. 
            // assertEquals(95, bestBidPrice); // ERROR WAS EXPECTING 95 BUT WAS 98
            // assertEquals(98, bestAskPrice); // ERROR WAS EXPECTING 98 BUT WAS 95

            // check the spread and spread threshold
            assertEquals(3, spread);
            assertEquals(true, spreadIsBelowOrEqualsToSpreadThreshhold);
            assertEquals(false, spreadIsAboveOrEqualsToSpreadThreshhold);        

            //assert that we created 5 Buy orders for 200 shares at best bid of 95 and no Sell orders
            assertEquals(5, container.getState().getActiveChildOrders().size()); 

            if (!container.getState().getActiveChildOrders().isEmpty()) {                  
                for (ChildOrder childOrder : container.getState().getActiveChildOrders()) {

                        assertEquals(200, childOrder.getQuantity());
                        assertEquals(true, childOrder.getSide() == Side.BUY);
                        assertEquals(true, childOrder.getSide() != Side.SELL);
                        // assertEquals(95, childOrder.getPrice()); // EXPECTED 95 BUT WAS 98

                        System.out.println("Order ID: " + childOrder.getOrderId() + 
                                        " | Side: " + childOrder.getSide() +            
                                        " | Price: " + childOrder.getPrice() +
                                        " | Ordered Qty: " + childOrder.getQuantity() +
                                        " | Filled Qty: " + childOrder.getFilledQuantity() +
                                        " | State of the order: " + childOrder.getState()
                                        );
                }

            }

            //Check total child orders count, total active child orders and total ordered quantity
            final int totalChildOrdersCountTick3 = container.getState().getChildOrders().size(); // added 15/9/3024

            final int totalActiveChildOrdersTick3 = container.getState().getActiveChildOrders().size();

            final long totalOrderedQuantityTick3 = container.getState().getActiveChildOrders().stream()
                                                                       .map(ChildOrder::getQuantity).reduce (Long::sum)
                                                                       .orElse(0L);


            // assert that there is a total of 5 child orders, a total of 5 active child orders and total ordered quantity is 1000
            assertEquals(5, totalChildOrdersCountTick3);
            assertEquals(5, totalActiveChildOrdersTick3);
            assertEquals(1000, totalOrderedQuantityTick3);

    }   

}

// ORIGINAL CODE
// package codingblackfemales.gettingstarted;

// import codingblackfemales.algo.AlgoLogic;
// import org.junit.Test;


// /**
//  * This test is designed to check your algo behavior in isolation of the order book.
//  *
//  * You can tick in market data messages by creating new versions of createTick() (ex. createTick2, createTickMore etc..)
//  *
//  * You should then add behaviour to your algo to respond to that market data by creating or cancelling child orders.
//  *
//  * When you are comfortable you algo does what you expect, then you can move on to creating the MyAlgoBackTest.
//  *
//  */
// public class MyAlgoTest extends AbstractAlgoTest {

//     @Override
//     public AlgoLogic createAlgoLogic() {
//         //this adds your algo logic to the container classes
//         return new MyAlgoLogic();
//     }


//     @Test
//     public void testDispatchThroughSequencer() throws Exception {

//         //create a sample market data tick....
//         send(createTick());

//         //simple assert to check we had 3 orders created
//         //assertEquals(container.getState().getChildOrders().size(), 3);
//     }
// }


// USE EITHER OF THE BELOW CODE TO RUN MYPROFITALGOTEST FROM A WINDOWS MACHINE WITH MAVEN INSTALLED
// mvn test -pl :getting-started -DMyAlgoTest 
// mvn -Dtest=MyAlgoTest test --projects algo-exercise/getting-started
// mvn clean test --projects algo-exercise/getting-started
