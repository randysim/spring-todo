import { createContext } from "react"

type UserContextType = {
    signedIn: boolean;
    user: Partial<User>;
}

const UserContext = createContext<UserContextType>({
    signedIn: false,
    user: {}
});

export default UserContext;

