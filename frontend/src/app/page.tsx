"use client"
import { useEffect, useState } from "react";

export default function Home() {
  let [authUser, setAuthUser] = useState("");

  useEffect(() => {
    const getAuthenticatedUser = async () => {
      try {
        let res = await fetch("http://localhost:8080/api/v1/user/authenticated", { method: "GET", credentials: "include"});
        console.log(res)
        let json = await res.json();
        setAuthUser(JSON.stringify(json));
      } catch (error) {
        console.log(error)
        setAuthUser("No authenticated user");
      }
    }
    
    getAuthenticatedUser();
  }, [])

  return (
    <div>
      <div>
        With Google: <a href="http://localhost:8080/oauth2/authorization/google">click here</a>
      </div>
      <div>
        {authUser}
      </div>
    </div>
  );
}
