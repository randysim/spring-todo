const DashboardTodoList = (
    { title, onClick, onDelete } : 
    { title: string, onClick: () => void, onDelete: () => void }
) => {
    return (
        <div className="w-[500px] border-4 p-5 rounded-xl">
            <div className="flex justify-center text-2xl">{title}</div>
            <div className="flex justify-center space-x-4">
                <button onClick={onClick}>View</button>
                <button onClick={onDelete}>Delete</button>
            </div>
        </div>
    )
}

export default DashboardTodoList;