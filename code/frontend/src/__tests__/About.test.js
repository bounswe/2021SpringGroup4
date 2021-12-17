import { render, screen } from '@testing-library/react';
import About from '../pages/About';

test('renders team members headers', () => {
  render(<About />);
  const linkElement = screen.getByText(/Team members/i);
 

  
  expect(linkElement).toBeInTheDocument();
  
});


test('renders team members', () => {
    render(<About />);
   
    const teamElement0 = screen.getByText(/Fatih Akgöz/i);
    const teamElement1 = screen.getByText(/Ece Dilara Aslan/i);
    const teamElement2 = screen.getByText(/Muhammed İrfan Bozkurt/i);
    const teamElement3 = screen.getByText(/Ali Alperen Durak/i);
    const teamElement4 = screen.getByText(/Mehmet Hilmi Dündar/i);
    const teamElement5 = screen.getByText(/Tolga Kerimoğlu/i);
    const teamElement6 = screen.getByText(/Yiğit Sarıoğlu/i);
    const teamElement7 = screen.getByText(/Yağmur Selek/i);

    expect(teamElement0).toBeInTheDocument();
    expect(teamElement1).toBeInTheDocument();
    expect(teamElement2).toBeInTheDocument();
    expect(teamElement3).toBeInTheDocument();
    expect(teamElement4).toBeInTheDocument();
    expect(teamElement5).toBeInTheDocument();
    expect(teamElement6).toBeInTheDocument();
    expect(teamElement7).toBeInTheDocument();

  });