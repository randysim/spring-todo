"use client"
import { useRouter } from "next/navigation";
import UserContext from "../components/context/UserContext";
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
        Sign in with Google <a href="http://localhost:8080/oauth2/authorization/google">click here</a>
      </div>
    </div>
  );
}
