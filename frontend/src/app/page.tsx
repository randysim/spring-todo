"use client"
import useUser from "@/app/lib/useUser";

export default function Home() {
  let user = useUser();

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
