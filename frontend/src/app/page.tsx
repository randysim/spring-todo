"use client"
import { useRouter } from "next/navigation";
import UserContext from "../comps/context/UserContext";
import { useContext, useEffect } from "react";

export default function Home() {
  const user = useContext(UserContext);
  const router = useRouter();

  useEffect(() => {
    if (user.signedIn) {
      router.push("/dashboard");
      return;
    }
  }, [user])

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
