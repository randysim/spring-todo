'use client'

import UserContext from "@/components/context/UserContext"
import ListTask from "@/components/pages/todolists/ListTask";
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
            alert("Failed to create task.");
            return;
        }

        setTasks([...tasks, data]);
        setDesc("");
    }

    const saveTodoList = async () => {
        const res = await fetch(
            `http://localhost:8080/api/v1/todolists/${params.id}`, 
            { 
                method: "PUT", 
                headers: { "Content-Type": "application/json" },
                credentials: "include", 
                body: JSON.stringify({ title: editTitle }) 
            }
        );

        const data = await res.json();

        if (!res.ok) {
            alert("Failed to save todo list");
            return;
        }

        setTitle(editTitle);
        setIsEditingTitle(false);
    }

    const saveTask = async (id: number, newDesc: string, completed: boolean) => {
        const res = await fetch(
            `http://localhost:8080/api/v1/todolists/${params.id}/tasks/${id}`, 
            { 
                method: "PUT", 
                headers: { "Content-Type": "application/json" },
                credentials: "include", 
                body: JSON.stringify({ description: newDesc, completed }) 
            }
        );

        const data = await res.json();

        if (!res.ok) {
            alert("Failed to save task");
            return;
        }

        setTasks(tasks.map(task => task.id === id ? data : task));
    }

    const deleteTask = async (id: number) => {
        const res = await fetch(
            `http://localhost:8080/api/v1/todolists/${params.id}/tasks/${id}`, 
            { 
                method: "DELETE", 
                credentials: "include" 
            }
        );

        if (!res.ok) {
            alert("Failed to delete task");
            return;
        }

        setTasks(tasks.filter(task => task.id !== id));
    }

    return (
        <div>
            <div className="ml-10">
                <button className="border-2 p-2 rounded-xl" onClick={() => router.push("/dashboard")}>Back</button>
            </div>
            {
                isEditingTitle ? 
                (
                    <div className="flex justify-center space-x-4">
                        <input className="border-2 text-4xl border-black" value={editTitle} onChange={(e) => setEditTitle(e.target.value)} />
                        <button onClick={saveTodoList}>Save</button>
                        <button onClick={() => setIsEditingTitle(false)}>Cancel</button>
                    </div>
                ) :
                (
                    <div className="flex justify-center space-x-4">
                        <h1 className="text-4xl">{title}</h1>
                        <button onClick={() => setIsEditingTitle(true)}>Edit</button>
                    </div>
                )
            }
            <div className="flex justify-center space-x-4 my-10">
                <input className="border-2 border-black" type="text" value={desc} onChange={e => setDesc(e.target.value)} />
                <button onClick={createTask}>Create Task</button>
            </div>
            <div className="flex flex-col items-center space-y-4">
                {
                    tasks.sort((a, b) => Number(a.completed) - Number(b.completed)).map(task => (
                        <ListTask 
                            key={task.id}
                            description={task.description}
                            completed={task.completed}
                            onSave={(newDesc, completed) => saveTask(task.id, newDesc, completed)}
                            onDelete={() => deleteTask(task.id)}
                        />
                    ))
                }
            </div>
        </div>
    )
}