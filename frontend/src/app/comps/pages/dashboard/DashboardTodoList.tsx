const DashboardTodoList = (
    { title, onClick } : 
    { title: string, onClick: () => void }
) => {
    return (
        <div>
            <div>{title}</div>
            <button onClick={onClick}>View</button>
        </div>
    )
}

export default DashboardTodoList;