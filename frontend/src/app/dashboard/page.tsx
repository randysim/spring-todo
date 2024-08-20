'use client'

import { useContext, useEffect, useState } from "react"
import UserContext from "../../comps/context/UserContext"
import { useRouter } from "next/navigation";

import DashboardTodoList from "../../comps/pages/dashboard/DashboardTodoList";


export default function Dashboard() {
    const user = useContext(UserContext);
    const router = useRouter();

    const [todoLists, setTodoLists] = useState<TodoList[]>([]);
    const [title, setTitle] = useState("");

    useEffect(() => {
        if (!user.loading && !user.signedIn) {
            router.push("/");
            return;
        }
    }, [user])
    
    useEffect(() => {
        const fetchUserTodolists = async () => {
            try {
                const res = await fetch("http://localhost:8080/api/v1/user/todolists", { method: "GET", credentials: "include" });
                const data = await res.json();

                setTodoLists(data);
            } catch(error) {
                router.push("/");
            }
        }

        fetchUserTodolists();
    }, []);

    const createTodoList = async () => {
        const res = await fetch(
            "http://localhost:8080/api/v1/todolists", 
            { 
                method: "POST", 
                headers: { "Content-Type": "application/json" },
                credentials: "include", 
                body: JSON.stringify({ title }) 
            }
        );

        const data = await res.json();

        if (!res.ok) {
            alert("Failed to create Todo List.")
            return;
        }

        setTodoLists([...todoLists, data]);
        setTitle("");
    }

    const deleteTodoList = async (id: number) => {
        const res = await fetch(
            `http://localhost:8080/api/v1/todolists/${id}`, 
            { 
                method: "DELETE", 
                credentials: "include" 
            }
        );

        if (!res.ok) {
            alert("Failed to delete Todo List.");
            return;
        }

        setTodoLists(todoLists.filter(todoList => todoList.id !== id));
    }
    return (
        <div>
            {todoLists.map(todoList => (
                <DashboardTodoList 
                    key={todoList.id} 
                    title={todoList.title} 
                    onClick={() => router.push(`/todolists/${todoList.id}`)}
                    onDelete={() => deleteTodoList(todoList.id)} 
                />
            ))}

            <div>
                <input className="border-2 border-black" onChange={e => setTitle(e.target.value)} value={title} />
                <button onClick={createTodoList}>Create Todo List</button>
            </div>
        </div>
    )
}