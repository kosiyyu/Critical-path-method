const Form = () => {

    const activityCharList = [] // char !!! unique
    const fullActivityList = [] // with char, name, predecessor and duration

    return(
    <>
        <div>
            <h1>Add activity</h1>
            <label>Add activity</label>
            <input type="text"></input>
        </div>
        <div>
            <h1>Specify activity</h1>
            <label>Name</label>
            <input type="text"></input>
            <label>Predecessor</label>
            <input type="text"></input>
            <label>Duration</label>
            <input type="number"></input>
        </div>
    </>
    )

}

export default Form