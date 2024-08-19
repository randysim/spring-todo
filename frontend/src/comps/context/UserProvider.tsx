import { ReactNode, useState, useEffect } from "react"
import UserContext from "./UserContext";

const UserProvider = ({ children}: { children: ReactNode }) => {
    const [user, setUser] = useState({ signedIn: false, loading: true, user: {} });

    useEffect(() => {
        const getAuthenticatedUser = async () => {
            try {
                let res = await fetch("http://localhost:8080/api/v1/user/authenticated", { method: "GET", credentials: "include" });
                let data = await res.json();

                setUser({ signedIn: true, loading: false, user: data });
            } catch(error) {
                setUser({ signedIn: false, loading: false, user: {} });
            }
        }

        getAuthenticatedUser();
    }, [])

    return (
        <UserContext.Provider value={user}>
            {children}
        </UserContext.Provider>
    )
}

export default UserProvider;