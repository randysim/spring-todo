"use client"
import UserContext from "./comps/context/UserContext";
import { useContext } from "react";

export default function Home() {
  const user = useContext(UserContext);

  return (
    <div>
      <div>
        With Google: <a href="http://localhost:8080/oauth2/authorization/google">click here</a>
      </div>
      <div>
        {JSON.stringify(user)}
      </div>
    </div>
  );
}
