import { createContext } from "react"

type UserContextType = {
    signedIn: boolean;
    loading: boolean;
    user: Partial<User>;
}

const UserContext = createContext<UserContextType>({
    signedIn: false,
    loading: true,
    user: {}
});

export default UserContext;

