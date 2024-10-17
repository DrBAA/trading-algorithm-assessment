import React from 'react';

interface AskQuantityProps {
  askQuantity: number;
  askWidth: number;
}

{/* Ask Quantity with background color and dynamic width */}

export const AskQuantity: React.FC<AskQuantityProps> = ({ askQuantity, askWidth }) => {
  return (
    <span
      className="ask-quantity"
      style={{
        padding: '0 5px',
        backgroundColor: 'red', // Adjust for ask side color
        width: `${askWidth}%`,// Dynamic width
         //width: `${backgroundWidth}%`, // Dynamic width
        display: 'inline-block',
      }}
    >
      {askQuantity}
    </span>
  );
};
