import UserProfile from '../pages/UserProfile.js';
import { render as rtlRender, screen, waitFor } from '@testing-library/react';
import { QueryClient, QueryClientProvider } from 'react-query';
import userEvent from '@testing-library/user-event';

function render(children) {
    const queryClient = new QueryClient();
    return rtlRender(<QueryClientProvider client={queryClient}>{children}</QueryClientProvider>);
}

test("user trying to choose and give a badge", async () => {
    render(<UserProfile />);

    const badgePic= new badgePic("Master")

    if (badgePic = "Master") {
        expect(screen.getByTestId('badge')).toBeInTheDocument();
    }

    await waitFor(() => { });
});