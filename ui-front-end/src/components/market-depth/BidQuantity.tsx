// BidQuantity.tsx
import React from 'react';

interface BidQuantityProps {
  bidQuantity: number;
  bidWidth: number;
}

export const BidQuantity: React.FC<BidQuantityProps> = ({ bidQuantity, bidWidth }) => {
  return (
    <span
      className="bid-quantity"
      style={{
        padding: '0 5px',
        backgroundColor: 'blue', // Adjust for bid side color
        width: `${bidWidth}%`, // Dynamic width
        // width: `${backgroundWidth}%`, // Dynamic width        
        display: 'inline-block',
      }}
    >
      {bidQuantity}
    </span>
  );
};

