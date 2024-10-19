import React from 'react';

import { Arrow } from './Arrow';
import './Arrow.css';


interface PriceCellProps {
  price: number;
  arrowDirection: 'up' | 'down';
  isBid: boolean; // Boolean to determine if it's for Bid or Ask
}

// Bid and Ask prices with dynamic arrow
export const PriceCell: React.FC<PriceCellProps> = ({ price, arrowDirection, isBid }) => {
  return (
    <td className={`price-cell ${isBid ? 'bid-price-cell' : 'ask-price-cell'}`}>

      {isBid && (
        <span className="arrow">
          <Arrow direction={arrowDirection} />
        </span>
      )}
      <span className="price">{price}</span>


      {!isBid && (
        <span className="arrow">
          <Arrow direction={arrowDirection} />
        </span>
      )}
    </td>
  );
};
