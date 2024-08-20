import { useContext } from "react";
import UserContext from "../context/UserContext";

const NavBar = () => {
    const user = useContext(UserContext);

    return (
        <div className="fixed w-full h-16 flex items-center z-10">
            <div>
                <h2>Todo App</h2>
            </div>
            <div>
                {
                    user.signedIn &&
                    (
                        <>
                            {user.user.username || "User"}
                            <a href="http://localhost:8080/logout">Sign out</a>
                        </>
                    )
                }
                {!user.signedIn &&
                    <a href="http://localhost:8080/oauth2/authorization/google">Sign in with Google</a>
                }
            </div>
        </div>
    )
}

export default NavBar;