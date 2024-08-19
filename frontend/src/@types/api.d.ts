interface User {
    id: number;
    username: string;
    email: string;
    picture: string;
    createdAt: string;
}

interface TodoList {
    id: number;
    title: string;
}

interface Task {
    id: number;
    description: string;
    completed: boolean;
    createdAt: string;
    due: string;
}