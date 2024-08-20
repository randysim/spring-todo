'use client'

import { useState, useEffect } from "react";

const ListTask = (
    {
        description,
        completed,
        onSave,
        onDelete
    } :
    {
        description: string,
        completed: boolean,
        onSave: (newDesc : string, completed: boolean) => void,
        onDelete: () => void
    }
) => {
    const [isEditing, setIsEditing] = useState(false);
    const [editDesc, setEditDesc] = useState(description);

    useEffect(() => {
        setEditDesc(description);
    }, [isEditing]);

    return (
        <div>
            <input 
                type="checkbox" 
                checked={completed} 
                onChange={() => onSave(description, !completed)}
            />
            {isEditing ?
                (
                    <input 
                        type="text"
                        className="border-2 border-black" 
                        value={editDesc} 
                        onChange={e => setEditDesc(e.target.value)}
                    />
                ) :
                (
                    <div>{description}</div>
                )
            }
            {
                isEditing ?
                (
                    <button onClick={() => {
                        onSave(editDesc, completed)
                        setIsEditing(false)
                    }}>Save</button>
                ) :
                (
                    <button onClick={() => setIsEditing(true)}>Edit</button>
                )
            }
            {
                isEditing ?
                (
                    <button onClick={() => setIsEditing(false)}>Cancel</button>
                ) :
                (
                    <button onClick={onDelete}>Delete</button>
                )
            }
        </div>
    )
}

export default ListTask;