'use client'

import UserContext from "@/comps/context/UserContext"
import { useRouter } from "next/navigation";
import { useContext, useEffect, useState } from "react"

export default function Page({ params } : { params: { id: number }}) {
    const user = useContext(UserContext);
    const router = useRouter();

    const [tasks, setTasks] = useState<Task[]>([]);
    const [desc, setDesc] = useState("");

    useEffect(() => {
        const fetchTasks = async () => {
            try {
                const res = await fetch(`http://localhost:8080/api/v1/todolists/${params.id}/tasks`, { method: "GET", credentials: "include" });
                const data = await res.json();

                setTasks(data);
            } catch(error) {
                router.push("/");
            }
        }

        fetchTasks();
    }, [params.id]);
    
    if (!user.signedIn) {
        router.push("/");
        return;
    }

    const createTask = async () => {
        const res = await fetch(
            `http://localhost:8080/api/v1/todolists/${params.id}/tasks`, 
            { 
                method: "POST", 
                headers: { "Content-Type": "application/json" },
                credentials: "include", 
                body: JSON.stringify({ description: desc }) 
            }
        );

        const data = await res.json();

        if (!res.ok) {
            console.log(res, data);
            return;
        }

        setTasks([...tasks, data]);
        setDesc("");
    }

    return (
        <div>
            <div>{JSON.stringify(tasks)}</div>
            <div>
                <input className="border-2 border-black" type="text" value={desc} onChange={e => setDesc(e.target.value)} />
                <button onClick={createTask}>Create Task</button>
            </div>
        </div>
    )
}