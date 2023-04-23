import react from 'react'

const EditableRow = ({cur, handleEditFormChange, handleCancelClick}) => {
    return (
        <tr>
            <td>{cur.id}</td>
            <td scope="row"><input type="text" name="nameActivity" placeholder="Enter activity name" className="form-control" id="nameActivity" value={cur.nameActivity} onChange={handleEditFormChange} /></td>
            <td scope="row"><input type="text" name="predecessors" placeholder="Enter predecessors" className="form-control" id="predecessors" value={cur.predecessors} onChange={handleEditFormChange} /></td>
            <td scope="row"><input type="number" name="duration" placeholder="Enter duration" className="form-control" id="duration" value={cur.duration} onChange={handleEditFormChange} /></td>
            <td scope="row">
                <button type="submit" className="btn btn-primary">Save</button>
                <button className="btn btn-secondary" onClick={handleCancelClick}>Cancel</button>
           </td>
       </tr>      
    )
}

export default EditableRow