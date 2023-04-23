import react from 'react'

const ReadOnlyRow = ({cur, handleEditClick, handleDeleteClick}) => {
    return (
        <tr key={cur.id}>
            <td scope="row">{cur.id}</td>
            <td scope="row">{cur.nameActivity}</td>
            <td scope="row">{cur.predecessors}</td>
            <td scope="row">{cur.duration}</td>
            <td scope="row">
                <div className="btn-group" role="group">
                    <button type="button" className="btn btn-primary" onClick={(event) => {handleEditClick(event, cur) }}>Edit</button>
                    <button type="button" className="btn btn-danger" onClick={() => handleDeleteClick(cur.id)}>Delete</button>
                </div>
           </td>
       </tr>
    )
}

export default ReadOnlyRow