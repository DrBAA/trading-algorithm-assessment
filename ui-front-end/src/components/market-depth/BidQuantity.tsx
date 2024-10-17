// BidQuantity.tsx
import React from 'react';

// Define the types for the props that BidQuantity will accept
interface BidQuantityProps {
  bidQuantity: number; // Accepts the bid quantity value
  bidWidth: number;    // Accepts the calculated width for the bid background
}

// The BidQuantity component will now use the bidQuantity and bidWidth props
export const BidQuantity: React.FC<BidQuantityProps> = ({ bidQuantity, bidWidth }) => {
  return (
    <span
      className="bid-quantity"
      style={{
        padding: '0 5px', // Adds padding to the left for better spacing
        backgroundColor: 'blue', // Sets the background color for the bid quantity
        width: `${bidWidth}%`, // Dynamically calculated width applied to the span
        // width: `${backgroundWidth}%`, // Dynamically calculated width applied to the span      
        display: 'inline-block',
      }}
    >
      {bidQuantity} {/* Render the bid quantity */}
    </span>
  );
};

