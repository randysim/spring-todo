const DashboardTodoList = (
    { title, onClick, onDelete } : 
    { title: string, onClick: () => void, onDelete: () => void }
) => {
    return (
        <div>
            <div>{title}</div>
            <button onClick={onClick}>View</button>
            <button onClick={onDelete}>Delete</button>
        </div>
    )
}

export default DashboardTodoList;