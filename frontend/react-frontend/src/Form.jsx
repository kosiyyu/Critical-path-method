import { useState, Fragment } from 'react';
import  ReadOnlyRecord  from './ReadOnlyRecord'
import  EditableRecord  from './EditableRecord'
import {sendRequestForCPM} from './API'
const Form = () => {
    const returnNextCharacter = function(c) {
        return  String.fromCharCode(c.charCodeAt() + 1);
    }
    // const fullActivityList = [] // with char, name, predecessor and duration
    const [editDataFromFormField, setEditDataFromFormField] = useState({
        id: returnNextCharacter('@'),
        nameActivity: '',
        predecessors: '',
        duration: 0
    });
    const [editActivityId, setEditActivityId] = useState(null);
    const [fullActivityList, setFullActivityList] = useState([]);
    const [activityCharList, setActivityIdList] = useState(new Set(['-']));
    const [dataFromFormField, setAddFormData] = useState({
        id: returnNextCharacter('@'),
        nameActivity: '',
        predecessors: '',
        duration: 0
    })
    const checkDuration = (duration) => {
        if(duration < 0.0)
        {
            const msg = "Duration must be greater or equal 0";
            document.querySelector('#error-msg').textContent = msg;
            document.querySelector('#error-msg').classList.remove("d-none");
            return false;
        }
        return true;
    }

    const checkPredecessors = (predecessors, id) => {
        const predecessorsList = predecessors.split(",");
        let isFind = false;
        for(const predecessor of predecessorsList)
        {
            
            isFind = false;
            if(activityCharList.has(predecessor) == true)
            {
                if(String.fromCharCode(predecessor.charCodeAt()) > String.fromCharCode(id.charCodeAt()))
                {
                    const msg = "Activities must be older than her predecessors";
                    document.querySelector('#error-msg').textContent = msg;
                    document.querySelector('#error-msg').classList.remove("d-none");
                }
                isFind = true;
            }
            
            if(isFind == false)
            {
                const msg = "One of predecessors activites does not exist";
                document.querySelector('#error-msg').textContent = msg;
                document.querySelector('#error-msg').classList.remove("d-none");
                return false;
            }
        }
        return true;
    }


    // validation
    const validateData = function(activity)
    {
        console.log("validatedDAta");
        let isValid = checkDuration(activity.duration);
        if(isValid == false) return false;
        isValid = checkPredecessors(activity.predecessors, activity.id);
        if(isValid == false) return false;
        return true;
    }

    const fetchDataFromForm = (event) => {
        event.preventDefault();
        const fieldName = event.target.getAttribute('name');
        const fieldValue = event.target.value;
        const newFormData = { ...dataFromFormField};
        newFormData[fieldName] = fieldValue;
        
        setAddFormData(newFormData);
    }

    const refactorsIdAndActivityList = (activityList) => {
        let tmpActivityList = [...activityList];
        let tmpActivityCharList = [...activityCharList];
        let charId = 'A';
        let tmpCharId;
        let predecessorsList = [];
        let finalPredecessors = "";
        for(let activity of tmpActivityList)
        {
            tmpCharId = activity.id;
            activity.id = charId;
            for(let activityInner of tmpActivityList)
            {
                finalPredecessors = "";
                predecessorsList = activityInner.predecessors.split(',');
                for(let predecessor of predecessorsList)
                {
                    if(predecessor == tmpCharId)
                    {
                        finalPredecessors += charId + ','; 
                    }
                    else if(predecessor != '')
                    {
                        finalPredecessors += predecessor + ','; 
                    }
                }
                finalPredecessors = finalPredecessors.slice(0, finalPredecessors.length - 1);
                activityInner.predecessors = finalPredecessors;
            }
            charId = returnNextCharacter(charId);
        }

        // let newFullActivityList = [...tmpActivityList];
        tmpActivityCharList = new Set(["-"]);
        for(let activity of tmpActivityList)
        {
            tmpActivityCharList.add(activity.id);
        }
        setActivityIdList(tmpActivityCharList);
        return tmpActivityList;
    }

    const checkIsPossibleToRemove = (index) => {
        let tmpActivityList = [ ...fullActivityList];
        let activity = tmpActivityList[index];
        for(let i = index; i < tmpActivityList.length; i++)
        {
            if(tmpActivityList[i].predecessors.includes(activity.id))
            {
                const msg = "Activity is predecessor activity for further activities";
                document.querySelector('#error-msg').textContent = msg;
                document.querySelector('#error-msg').classList.remove("d-none");
                return false;
            }
        }
        const msg = "";
        document.querySelector('#error-msg').textContent = msg;
        document.querySelector('#error-msg').classList.add("d-none");
        return true;
    }

    const sortFirstActivitiesAndLastActivities = (listActivity) => {
        let finalActivity = [];
        let firstActivity = [];
        let restActivity = [];
        let lastActivity = [];
        let isPredecessor = false;
        for(let activity of listActivity)
        {
            isPredecessor = false;
            if(activity.predecessors.includes('-'))
                firstActivity.push(activity);
            else
            {
                for(let activityInner of listActivity)
                {
                    if(activityInner.predecessors.includes(activity.id))
                    {
                        isPredecessor = true;
                        restActivity.push(activity);
                        break;       
                    }
                }
                if(isPredecessor == false)
                {
                    lastActivity.push(activity);
                }
            }
            
        }
        finalActivity = [...firstActivity, ...restActivity, ...lastActivity];
        return finalActivity;
    }

    const handleEditFormChange = (event) => {
        event.preventDefault();
        const fieldName = event.target.getAttribute("name");
        const fieldValue = event.target.value;

        const newFormData = { ...editDataFromFormField };
        newFormData[fieldName] = fieldValue;

        setEditDataFromFormField(newFormData);
    }

    const handleFormFunction = (event) => {
        event.preventDefault();
        let newRecord = {
            id: dataFromFormField.id,
            nameActivity: dataFromFormField.nameActivity,
            predecessors: dataFromFormField.predecessors,
            duration: dataFromFormField.duration
        }
        if(validateData(newRecord) == true)
        {
            document.querySelector('#error-msg').textContent = '';
            document.querySelector('#error-msg').classList.add("d-none");
            if(activityCharList.size > 0 && Array.from(activityCharList).pop() != '-')
            {
                newRecord.id = returnNextCharacter(Array.from(activityCharList).pop());
            }
            else 
            {
                newRecord.id = returnNextCharacter('@');
            }
        
            const newFullActivityList = [...fullActivityList, newRecord];
            setFullActivityList(newFullActivityList);
            const newActivityId = activityCharList.add(newRecord.id);
            setActivityIdList(newActivityId);
            const scheduledActivities = sortFirstActivitiesAndLastActivities(newFullActivityList);
            const refactorActivities = refactorsIdAndActivityList(scheduledActivities);
            refactorActivities.forEach(cur => {validateData(cur);})
            setFullActivityList(refactorActivities);
            // setFullActivityList(scheduledActivities);
        }
        
    }

    const handleEditClick = (event, editActivity) => {
        event.preventDefault();
        setEditActivityId(editActivity.id);

        const formValues = {
            id: editActivity.id,
            nameActivity: editActivity.nameActivity,
            predecessors: editActivity.predecessors,
            duration: editActivity.duration
        }

        setEditDataFromFormField(formValues);
    }

    const handleEditFormSubmit = (event) => {
        event.preventDefault();

        const editActivity = {
            id: editActivityId,
            nameActivity: editDataFromFormField.nameActivity,
            predecessors: editDataFromFormField.predecessors,
            duration: editDataFromFormField.duration
        }

        const newFullActivityList = [...fullActivityList];
        const index = fullActivityList.findIndex((activity) => activity.id === editActivityId);
        if(validateData(editActivity) == true)
        {
            document.querySelector('#error-msg').textContent = '';
            document.querySelector('#error-msg').classList.add("d-none");
            newFullActivityList[index] = editActivity;
            setFullActivityList(newFullActivityList);
            setEditActivityId(null);
        }
    }

    const handleCancelClick = () => {
        setEditActivityId(null);
    }

    const handleDeleteClick = (activityId) => {
        const newActivities = [...fullActivityList];
        const index = fullActivityList.findIndex((activity) => activity.id === activityId);
        if(checkIsPossibleToRemove(index) == true)
        {
            newActivities.splice(index, 1);
            const refactorNewActivities = refactorsIdAndActivityList(newActivities);
            setFullActivityList(refactorNewActivities);
        }

    }

    const convertToJson = (activityList) => {
        let activityCollection = [];
        Array.prototype.forEach.call(activityList, (cur) => {
            activityCollection.push({
                id: cur.id,
                name: cur.nameActivity,
                predecessor: cur.predecessors,
                duration: cur.duration
            })});

        const jsonActivitiesData = JSON.stringify(activityCollection);
        return jsonActivitiesData; 
    }

    const clearActivitiesList = () => {
        setActivityIdList(new Set(['-']));
        setFullActivityList([]);
       
    }

    async function calcCPM() {
        const activitesList = [...fullActivityList];
        console.log(convertToJson(activitesList));
        const resolveData = await sendRequestForCPM(convertToJson).catch(console.log);
    }

    
    return(
    <>
        <div className="container">
            <div className="row">
                <div className="col-lg-6 col-md-10">
                    <form onSubmit={handleEditFormSubmit}>
                        <table className="table">
                            <thead>
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Name activity</th>
                                    <th scope="col">Predecessors</th>
                                    <th scope="col">Duration</th>
                                    <th scope="col">Action</th>
                                </tr>
                            </thead>
                            <tbody className="table-group-divider">
                                {fullActivityList.map(cur => (
                                    <Fragment>
                                        {
                                            editActivityId == cur.id ?
                                            <EditableRecord cur={editDataFromFormField} 
                                            handleEditFormChange={handleEditFormChange}
                                            handleCancelClick={handleCancelClick} />
                                            :
                                            <ReadOnlyRecord cur={cur} handleEditClick={handleEditClick} handleDeleteClick={handleDeleteClick} />
                                        }
                                    </Fragment>
                                ))}
                            
                            </tbody>
                        </table>
                    </form>
                </div>
                <div className="col-lg-6 col-md-10">
                    <div>
                        <h3>Add activity</h3>
                        <div className="alert alert-danger d-none" id="error-msg"></div>
                    </div>
                    <form onSubmit={handleFormFunction}>
                        <div className="mb-3">
                            <label htmlFor="nameActivity" className="form-label">Activity name</label>
                            <input type="text" name="nameActivity" placeholder="Enter activity name" className="form-control" id="nameActivity" onChange={fetchDataFromForm} />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="predecessors" className="form-label">Enter Predecessors</label>
                            <input type="text" name="predecessors" placeholder="Enter predecessors" className="form-control" id="predecessors" onChange={fetchDataFromForm} />
                            {/* <div className="selected-predecessors">
                                <span className="badge bg-primary m-1">No predecessor <span className="icon-delete">&#10006;</span></span>
                                <span className="badge bg-primary m-1">No predecessor <span className="icon-delete">&#10006;</span></span>
                                <span className="badge bg-primary m-1">No predecessor <span className="icon-delete">&#10006;</span></span>
                            </div>
                            <select className="form-select" id="predecessors" aria-label="predecessors">
                                <option selected>No predecessor</option>
                                <option value="1">One</option>
                                <option value="2">Two</option>
                                <option value="3">Three</option> */}
                            {/* </select> */}
                        </div>
                        <div className="mb-3">
                            <label htmlFor="duration" className="form-label">Duration</label>
                            <input type="number" name="duration" placeholder="Enter duration" className="form-control" id="duration" onChange={fetchDataFromForm} />
                        </div>

                        <button type="submit" className="btn btn-primary">Add</button>
                        <button type="button" className="btn btn-success m-3" onClick={clearActivitiesList}>Clear table</button>
                        <button type="button" className="btn btn-info m-3" onClick={calcCPM}>Calculate CPM</button>
                    </form>

                </div>
            </div>
        </div>
    </>
    )

}

export default Form