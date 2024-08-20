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
    <div className="flex w-full h-[100vh] justify-center items-center flex-col">
        <h1 className="text-6xl">Sample Todo App</h1>
        <p className="text-2xl text-center w-[50%] my-10">A sample todo app featuring a nextjs frontend and a spring boot backend. sign in implemented with Google OAuth2 and Spring Security</p>
        Sign in with Google <a href="http://localhost:8080/oauth2/authorization/google">click here</a>
    </div>
  );
}
