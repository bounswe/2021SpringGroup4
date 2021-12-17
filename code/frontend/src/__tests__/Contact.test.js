import { render, screen } from '@testing-library/react';
import Contact from '../pages/Contact';

test('renders contact information text', () => {
  render(<Contact />);
  const linkElement = screen.getByText(/Contact Information/i);
 

  
  expect(linkElement).toBeInTheDocument();
  
});