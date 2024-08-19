'use client'

import UserContext from "@/comps/context/UserContext"
import { useRouter } from "next/navigation";
import { useContext, useEffect, useState } from "react"

export default function Page({ params } : { params: { id: number }}) {
    const user = useContext(UserContext);
    const router = useRouter();

    const [tasks, setTasks] = useState<Task[]>([]);
    const [desc, setDesc] = useState("");

    const [title, setTitle] = useState("");
    const [editTitle, setEditTitle] = useState("");
    const [isEditingTitle, setIsEditingTitle] = useState(false);

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

    useEffect(() => {
        const fetchTodoList = async () => {
            try {
                const res = await fetch(`http://localhost:8080/api/v1/todolists/${params.id}`, { method: "GET", credentials: "include" });
                const data = await res.json();

                setTitle(data.title);
            } catch(error) {
                router.push("/");
            }
        }

        fetchTodoList();
    }, [params.id]);

    useEffect(() => {
        setEditTitle(title);
    }, [isEditingTitle]);

    useEffect(() => {
        if (!user.loading && !user.signedIn) {
            router.push("/");
            return;
        }
    }, [user]);

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
            {
                isEditingTitle ? 
                (
                    <div>
                        <input className="border-2 border-black" value={editTitle} onChange={(e) => setTitle(e.target.value)} />
                        <button onClick={() => setIsEditingTitle(false)}>Save</button>
                    </div>
                ) :
                (
                    <div>
                        <h1>{title}</h1>
                        <button onClick={() => setIsEditingTitle(true)}>Edit</button>
                    </div>
                )
            }
            <div>{JSON.stringify(tasks)}</div>
            <div>
                <input className="border-2 border-black" type="text" value={desc} onChange={e => setDesc(e.target.value)} />
                <button onClick={createTask}>Create Task</button>
            </div>
        </div>
    )
}