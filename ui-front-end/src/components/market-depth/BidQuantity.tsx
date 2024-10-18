import './BidQuantity.css';

// Define the types for the props that BidQuantity will accept
interface BidQuantityProps {
  bidQuantity: number; // Accepts the bid quantity value
  bidWidth: number;    // Accepts the calculated width for the bid background
}

// The BidQuantity component will now use the bidQuantity and bidWidth props
export const BidQuantity = ({ bidQuantity, bidWidth }: BidQuantityProps) => {
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


// AMMENDMENTS - 18/10/2024
// Refactored the code on the line 12 "export const BidQuantity: React.FC<BidQuantityProps> = ({ bidQuantity, bidWidth }) => {" 
// and replaced it with "export const BidQuantity = ({ bidQuantity, bidWidth }: BidQuantityProps) => {
// This is because React.FC is ony used if the component is pulling in child components.

// Also removed the line "import React from 'react';". This is because the project is using React 18. After React 17 or higher,
// and TypeScript is properly configured, we don't need to explicitly import React anymore to use JSX syntax.

// removed the word "export on the line "export interface BidQuantityProps {"
// this is because the interface is only used locally