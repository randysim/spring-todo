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
        onSave: (newDesc : string) => void,
        onDelete: () => void
    }
) => {
    return (
        <div>
            
        </div>
    )
}

export default ListTask;