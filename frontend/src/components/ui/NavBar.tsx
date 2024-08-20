import { useContext } from "react";
import UserContext from "../context/UserContext";

const NavBar = () => {
    const user = useContext(UserContext);

    return (
        <div className="fixed w-full h-16 flex items-center z-10">
            <div className="w-[20%]">
                <h1 className="text-2xl font-bold ml-4 cursor-pointer">Todo App</h1>
            </div>
            <div className="flex space-x-4 items-center ml-auto w-[200px]">
                {
                    user.signedIn &&
                    (
                        <>
                            <div>
                                    {user.user.username || "User"}
                            </div>
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