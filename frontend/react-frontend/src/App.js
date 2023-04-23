import logo from './logo.svg';
import React, {useState}  from 'react'
import './App.css';
import Form from './Form'
import Graph from './Graph';

function App() {

  const [graphData, setGraphData] = useState({nodes: [], links: []});
  const returnResolvedPromise = (data) => {
    setGraphData(data);
    // console.log(data);
  }

  return (
    <div className="App">
      <h2>Critical path method</h2>
      <div>
        <Form func={returnResolvedPromise}/>
        <div className="container">
          <div className="row">
            <div className="col-10 mt-4">
              <Graph data={graphData}/>
            </div>
          </div>
        </div>
        
      </div>
    </div>
  );
}

export default App;
