import './AskQuantity.css';

// defining an interface to ensure that the AskQuantity component can only accept the correct
// types of props i.e a number in this case. this prevents the wrong data type being
// accidentally passed to the component. If any other type is passed (like a string or boolean),
// TypeScript will throw an error, preventing potential runtime errors

interface AskQuantityProps {
  askQuantity: number;
  askWidth: number;
}

//declaring a new functional component (AskQuantity) - with background color and dynamic width
//The function receives the props (askQuantity and askWidth), and then returns JSX
//"export" means this component is being exported, which makes it available for import and use in other files

export const AskQuantity = ({ askQuantity, askWidth }: AskQuantityProps) => {
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



// AMMENDMENTS - 18/10/2024
// Refactored the code on the line 12 "export const AskQuantity: React.FC<AskQuantityProps> = ({ askQuantity, askWidth }) => {" 
// and replaced it with "export const AskQuantity = ({ askQuantity, askWidth }: AskQuantityProps) => {
// This is because React.FC is ony used if the component is pulling in child components.

// Also removed the line "import React from 'react';". This is because the project is using React 18. After React 17 or higher,
// and TypeScript is properly configured, we don't need to explicitly import React anymore to use JSX syntax.

// removed the word "exportS on the line "export interface AskQuantityProps {"
// this is because the interface is only used locally