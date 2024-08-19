'use client'

import { useContext, useEffect, useState } from "react"
import UserContext from "../comps/context/UserContext"
import { useRouter } from "next/navigation";


export default function Dashboard() {
    const user = useContext(UserContext);
    const router = useRouter();

    const [todoLists, setTodoLists] = useState([]);

    useEffect(() => {
        if (!user.signedIn) {
            router.push("/");
            return;
        }
    }, [user])
    
    useEffect(() => {
        const fetchUserTodolists = async () => {
            const res = await fetch("http://localhost:8080/api/v1/user/todolists", { method: "GET", credentials: "include" });
            const data = await res.json();

            setTodoLists(data);
        }

        fetchUserTodolists();
    }, [])

    return (
        <div>
            {JSON.stringify(todoLists)}
        </div>
    )
}